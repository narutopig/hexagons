package io.github.narutopig.hexagons;

import io.github.narutopig.hexagons.entity.Entity;
import io.github.narutopig.hexagons.entity.Sprite;
import io.github.narutopig.hexagons.entity.Vector;
import io.github.narutopig.hexagons.util.KeyHandler;
import io.github.narutopig.hexagons.util.Util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    final int width = 1280;
    final int height = 720;
    final int FPS = 60;
    public final KeyHandler keyHandler = new KeyHandler();
    public final List<Entity> entities = new ArrayList<>();
    final double sixtyDegrees = -60;
    int pogTimer = 0;
    int hitTimer = 0;

    Thread gameThread;

    private long lastUpdate;
    private List<Note> notes;
    private final char[] keys = new char[]{'a', 's', 'd', 'j', 'k', 'l'};
    private final List<List<Integer>> timestamp = new ArrayList<>();
    private int frames = -180;

    // score
    int misses = 0;
    int combo = 0;
    int score = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(135, 206, 235));
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;

        for (int i = 0; i < 6; i++) {
            timestamp.add(new ArrayList<>());
        }

        for (Note note : notes) {
            timestamp.get(note.col).add(note.start);
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currTime;

        while (gameThread != null) {
            currTime = System.nanoTime();

            delta += (currTime - lastTime) / drawInterval;
            lastTime = currTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }
        }
    }

    public void update() {
        long curr = System.currentTimeMillis();
        int twoHundred = 200; // for speed of note

        Sprite hexagon = new Sprite(Main.smallImage);
        Point centered = Main.getPositionCentered(hexagon, this.width / 2, this.height / 2);
        for (Note note : notes) {
            if (note.getStart() - 96 == frames) {
                double angle = Math.toRadians(sixtyDegrees * note.getCol());
                new Entity(
                        this, keyHandler, hexagon,
                        centered.x, centered.y,
                        twoHundred * Math.cos(angle), twoHundred * Math.sin(angle),
                        "note0" + note.getCol()
                );
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.tag.startsWith("note")) {
                if (entity.getCenter().distance(new Vector(width / 2.0, height / 2.0)) >= 320) {
                    if (entity.tag.startsWith("note0")) {
                        misses++;
                        combo = 0;
                        new Entity(this, keyHandler, new Sprite(Main.miss), width / 2.0 - 128, 0, 0, 0, "hit");
                        hitTimer = 15;
                    }

                    entities.remove(entity);
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            if (keyHandler.getKey(keys[i])) {
                int noteIndex = Util.nextNote(timestamp, i, frames);

                int diff = Math.abs(frames - timestamp.get(i).get(noteIndex));
                if (diff <= 30) {
                    String tag = Objects.requireNonNull(Util.getNoteEntity(entities, noteIndex, i)).tag;
                    if (tag.charAt(4) == '1') continue;
                    Entity noteEntity = Util.getNoteEntity(entities, noteIndex, i);
                    Objects.requireNonNull(noteEntity).tag = tag.substring(0, 4) + "1" + tag.charAt(5);
                    combo++;

                    if (combo == 10) {
                        new Entity(this, keyHandler, new Sprite(Main.pog), 0, height / 2.0, 0, 0, "pog");
                        pogTimer = 120;
                    }

                    int hitRating = (4 - diff / 10) * 100;

                    if (hitRating > 300) hitRating = 300;

                    score += hitRating;

                    if (hitRating == 100) {
                        new Entity(this, keyHandler, new Sprite(Main.hit100), width / 2.0 - 128, 0, 0, 0, "hit");
                    } else if (hitRating == 200) {
                        new Entity(this, keyHandler, new Sprite(Main.hit200), width / 2.0 - 128, 0, 0, 0, "hit");
                    } else if (hitRating == 300) {
                        new Entity(this, keyHandler, new Sprite(Main.hit300), width / 2.0 - 128, 0, 0, 0, "hit");
                    }

                    hitTimer = 30;
                }
            }
        }

        for (Entity e : entities) {
            e.update(curr - lastUpdate);
        }

        if (hitTimer > 0) hitTimer--;
        else entities.remove(Util.getEntityByTag(entities, "hit"));
        if (pogTimer > 0) pogTimer--;
        else entities.remove(Util.getEntityByTag(entities, "pog"));
        lastUpdate = curr;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawStringTopLeft(g2, "Combo: " + combo + "\nScore: " + score + "\nMisses: " + misses, 0, 0);

        for (Entity e : entities) {
            e.paint(g2);
        }

        g2.dispose();
    }

    public void start() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip clip = AudioSystem.getClip();

        InputStream is = GamePanel.class.getResourceAsStream("/audio/Untitled.wav");
        InputStream buf = new BufferedInputStream(is);
        AudioInputStream ais = AudioSystem.getAudioInputStream(buf);
        clip.open(ais);

        clip.setFramePosition(0);
        clip.start();

        gameThread = new Thread(this);
        gameThread.start();

        lastUpdate = System.currentTimeMillis();
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public static void drawStringTopLeft(Graphics g, String s, int x, int y) {
        FontMetrics fm = g.getFontMetrics(g.getFont());
        int yOffset = fm.getAscent();
        g.drawString(s, x, y + yOffset);
    }
}

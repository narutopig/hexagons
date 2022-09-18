package io.github.narutopig.game;

import io.github.narutopig.game.entity.Entity;
import io.github.narutopig.game.entity.Sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static BufferedImage hexagonImage;
    static BufferedImage hexa;
    static BufferedImage hexs;
    static BufferedImage hexd;
    static BufferedImage hexj;
    static BufferedImage hexk;
    static BufferedImage hexl;

    static {
        try {
            hexagonImage = readImage("/hexagon.png");
            hexa = readImage("/hexagona.png");
            hexs = readImage("/hexagons.png");
            hexd = readImage("/hexagond.png");
            hexj = readImage("/hexagonj.png");
            hexk = readImage("/hexagonk.png");
            hexl = readImage("/hexagonl.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BufferedImage smallImage = getScaledImage(hexagonImage, 0.25);
    static BufferedImage largeImage = getScaledImage(hexagonImage, 3);
    static BufferedImage hita = getScaledImage(hexa, 0.25);
    static BufferedImage hits = getScaledImage(hexs, 0.25);
    static BufferedImage hitd = getScaledImage(hexd, 0.25);
    static BufferedImage hitj = getScaledImage(hexj, 0.25);
    static BufferedImage hitk = getScaledImage(hexk, 0.25);
    static BufferedImage hitl = getScaledImage(hexl, 0.25);

    private static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    private static BufferedImage getScaledImage(BufferedImage srcImg, double scale) {
        return getScaledImage(srcImg, (int) (srcImg.getWidth() * scale), (int) (srcImg.getHeight() * scale));
    }

    public static BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(Main.class.getResourceAsStream(path));
    }

    public static Point getPositionCentered(Sprite sprite, int x, int y) {
        Rectangle bounds = sprite.getBounds();

        return new Point(x - bounds.width / 2, y - bounds.height / 2);
    }

    public static void main(String[] args) {
        List<Note> notes = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            notes.add(new Note(i * 48, i * 48, Math.abs(random.nextInt()) % 6));
        }

        JFrame window = new JFrame();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1280, 720);

        GamePanel gp = new GamePanel();
        window.add(gp);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.setTitle("Rhythm Game");

        Sprite smallHexagon = new Sprite(smallImage);
        Sprite largeHexagon = new Sprite(largeImage);

        int w = window.getWidth();
        int h = window.getHeight();

        Point centeredSmall = getPositionCentered(smallHexagon, w / 2, h / 2);
        Point centered = getPositionCentered(largeHexagon, w / 2, h / 2);

        new Entity(gp, gp.keyHandler, largeHexagon, centered.x, centered.y, 0, 0);
        new Entity(gp, gp.keyHandler, smallHexagon, centeredSmall.x, centeredSmall.y, 0, 0);

        // hit areas
        new Entity(gp, gp.keyHandler, new Sprite(hita), 910, 335, 0, 0);
        new Entity(gp, gp.keyHandler, new Sprite(hits), 760, 75, 0, 0);
        new Entity(gp, gp.keyHandler, new Sprite(hitd), 460, 75, 0, 0);
        new Entity(gp, gp.keyHandler, new Sprite(hitj), 310, 335, 0, 0);
        new Entity(gp, gp.keyHandler, new Sprite(hitk), 460, 598, 0, 0);
        new Entity(gp, gp.keyHandler, new Sprite(hitl), 760, 598, 0, 0);

        gp.setNotes(notes);

        try {
            gp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

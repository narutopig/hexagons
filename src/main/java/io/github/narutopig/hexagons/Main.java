package io.github.narutopig.hexagons;

import io.github.narutopig.hexagons.entity.Entity;
import io.github.narutopig.hexagons.entity.Sprite;

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
    static BufferedImage blueImage;
    static BufferedImage hit100;
    static BufferedImage hit200;
    static BufferedImage hit300;
    static BufferedImage miss;
    static BufferedImage pog;

    static {
        try {
            hexagonImage = readImage("/sprites/hexagon.png");
            hexa = readImage("/sprites/hexagona.png");
            hexs = readImage("/sprites/hexagons.png");
            hexd = readImage("/sprites/hexagond.png");
            hexj = readImage("/sprites/hexagonj.png");
            hexk = readImage("/sprites/hexagonk.png");
            hexl = readImage("/sprites/hexagonl.png");
            hit100 = readImage("/sprites/100.png");
            hit200 = readImage("/sprites/200.png");
            hit300 = readImage("/sprites/300.png");
            miss = readImage("/sprites/miss.png");
            pog = readImage("/sprites/pog.png");
            blueImage = getScaledImage(readImage("/sprites/bluehexagon.png"), 0.25);
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
        new Entity(gp, gp.keyHandler, new Sprite(blueImage), centeredSmall.x, centeredSmall.y, 0, 0);

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

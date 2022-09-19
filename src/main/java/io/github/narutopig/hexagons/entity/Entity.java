package io.github.narutopig.hexagons.entity;

import io.github.narutopig.hexagons.GamePanel;
import io.github.narutopig.hexagons.util.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    List<Sprite> sprites;
    int currentSprite;
    public Vector position;
    Vector speed; // in pixels per second
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public String tag;

    private static List<Sprite> listOf(Sprite sprite) {
        List<Sprite> l = new ArrayList<>();

        l.add(sprite);

        return l;
    }

    public Entity(GamePanel gp, KeyHandler kh, List<Sprite> sprites, double x, double y, double xSpeed, double ySpeed, String tag) {
        currentSprite = 0;
        this.sprites = sprites;
        gamePanel = gp;
        keyHandler = kh;
        position = new Vector(x, y);
        speed = new Vector(xSpeed, ySpeed);
        this.tag = tag;
        gp.addEntity(this);
    }

    public Entity(GamePanel gp, KeyHandler kh, Sprite sprite, double x, double y, double xSpeed, double ySpeed) {
        this(gp, kh, listOf(sprite), x, y, xSpeed, ySpeed, "");
    }

    public Entity(GamePanel gp, KeyHandler kh, Sprite sprite, double x, double y, double xSpeed, double ySpeed, String tag) {
        this(gp, kh, listOf(sprite), x, y, xSpeed, ySpeed, tag);
    }

    public void update(long delta) {
        double scale = delta / 1000.0;

        position = position.add(speed.scale(scale));
    }

    public void setSprite(int sprite) {
        currentSprite = sprite;
    }

    public Sprite getSprite() {
        return sprites.get(currentSprite);
    }

    public void paint(Graphics2D g2) {
        int w = getSprite().bounds.width;
        int h = getSprite().bounds.height;

        g2.drawImage(getSprite().image, (int) position.getX(), (int) position.getY(), w, h, null);
    }

    public Vector getCenter() {
        return this.position.add(new Vector(getSprite().bounds.width / 2.0, getSprite().bounds.height / 2.0));
    }
}
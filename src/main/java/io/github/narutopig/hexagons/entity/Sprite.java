package io.github.narutopig.hexagons.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    BufferedImage image;
    Rectangle bounds;

    public Sprite(BufferedImage image) {
        this.image = image;
        this.bounds = image.getData().getBounds();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

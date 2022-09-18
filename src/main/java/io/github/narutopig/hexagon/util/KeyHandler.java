package io.github.narutopig.hexagon.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {
    private final Map<Character, Boolean> keyMap = new HashMap<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (keyMap.getOrDefault(e.getKeyChar(), false)) {
            keyMap.put(e.getKeyChar(), false);
        } else {
            keyMap.put(e.getKeyChar(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyMap.put(e.getKeyChar(), false);
    }

    public boolean getKey(char c) {
        return keyMap.getOrDefault(c, false);
    }
}
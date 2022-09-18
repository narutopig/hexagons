package io.github.narutopig.hexagon.util;

import io.github.narutopig.hexagon.entity.Entity;

import java.util.List;

public class Util {
    public static Entity getNoteEntity(List<Entity> entities, int index, int col) {
        int i = 0;
        Entity first = null;
        for (Entity entity : entities) {
            if (!entity.tag.startsWith("note")) {
                continue;
            }
            first = entity;
            if (Integer.parseInt(String.valueOf(entity.tag.charAt(5))) == col) {
                if (index == i) return entity;
                i++;
            }
        }

        return first;
    }

    public static int nextNote(List<List<Integer>> notes, int col, int val) {
        List<Integer> stuff = notes.get(col);
        Integer[] seq = stuff.toArray(new Integer[0]);

        return firstOccurrence(seq, val);
    }

    public static int firstOccurrence(Integer[] sequence, int x) {
        if (x < sequence[0]) return 0;

        for (int i = 0; i < sequence.length - 1; i++) {
            if (x <= sequence[i]) {
                return i;
            }
        }

        return sequence.length - 1;
    }
}

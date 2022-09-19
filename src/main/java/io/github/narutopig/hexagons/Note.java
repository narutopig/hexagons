package io.github.narutopig.hexagons;

public class Note {
    // in frames, assuming 60 fps
    int start;
    int end;
    int col; // 0-5

    public Note(int start, int end, int col) {
        this.start = start;
        this.end = end;
        this.col = col;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getCol() {
        return col;
    }
}

package tools;

public class Coordinates {
    private int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isInBounds(int supX, int supY) {
        return this.x >= 0 && this.x < supX && this.y >= 0 && this.y < supY;
    }
}
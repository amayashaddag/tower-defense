package tools;

public class Coordinates implements Cloneable {
    private final int x, y;

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

    /* Tests wether 2 coordinates are the same */

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (!(o instanceof Coordinates))
            return false;
        Coordinates c = (Coordinates) o;
        return this.x == c.x && this.y == c.y;
    }

    /*
     * Returns wether the current coordinate is in the square of radius r and center
     * c
     */

    public boolean isInRange(Coordinates center, int range) {
        if (center == null)
            return false;
        return Math.abs(this.x - center.x) <= range && Math.abs(this.y - center.y) <= range;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
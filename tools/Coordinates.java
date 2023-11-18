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
    /*Setters */
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public boolean isInBounds(int supX, int supY) {
        return this.x >= 0 && this.x < supX && this.y >= 0 && this.y < supY;
    }

    /* Tests wether 2 coordinates are the same */
    
    @Override
    public boolean equals(Object o) {
        if(!super.equals(o)) return false;
        if(!(o instanceof Coordinates)) return false;
        Coordinates c = (Coordinates) o;
        return this.x == c.x && this.y == c.y;
    }

    /* Returns wether the current coordinate is in the square of radius r and center c */

    public boolean isInRange(Coordinates center, int range) {
        if(center == null) return false;
        return Math.abs(this.x - center.x) <= range && Math.abs(this.y - center.y) <= range;
    }
}
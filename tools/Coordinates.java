package tools;

import model.Direction;

public class Coordinates implements Cloneable {
    private final double x, y;

    public static final Coordinates NORTH_UNIT = new Coordinates(-1, 0);
    public static final Coordinates SOUTH_UNIT = new Coordinates(1, 0);
    public static final Coordinates EAST_UNIT = new Coordinates(0, 1);
    public static final Coordinates WEST_UNIT = new Coordinates(0, -1);

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public boolean isInBounds(double supX, double supY) {
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
    
    public boolean greaterOrEquals(Coordinates c) {
        return this.x >= c.x && this.y >= c.y;
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

    public IntCoordinates round() {
        return new IntCoordinates((int) Math.round(this.x), (int) Math.round(this.y));
    }

    public Coordinates times(double value) {
        return new Coordinates(this.x * value, this.y * value);
    }

    public static Coordinates getUnit(Direction d) {
        switch(d) {
            case EAST: return EAST_UNIT;
            case NORTH: return NORTH_UNIT;
            case SOUTH: return SOUTH_UNIT;
            case WEST: return WEST_UNIT;
            default: return null;
        }
    }

    public Coordinates plus(Coordinates c) {
        return new Coordinates(x + c.x, y + c.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean distanceFromCenterIsInRange(double d) {
        return Math.abs(this.x - ((int) Math.round(this.x))) < d 
        && Math.abs(this.y - ((int) Math.round(this.y))) < d; 
    }
}
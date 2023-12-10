package tools;

import model.Direction;

public class IntCoordinates {
    private final int x, y;

    public IntCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public IntCoordinates plus(Direction d) {
        int x = 0, y = 0;
        switch(d) {
			case EAST:
                y = 1;
				break;
			case NORTH:
                x = -1;
				break;
			case SOUTH:
                x += 1;
				break;
			case WEST:
                y =- 1;
				break;
			default:
				break;

        }
        return new IntCoordinates(this.x + x, this.y + y);
    }

    public boolean isInBounds(double supX, double supY) {
        return this.x >= 0 && this.x < supX && this.y >= 0 && this.y < supY;
    }

    public Direction getDirectionFrom(IntCoordinates c) {
        if(this.x > c.x) return Direction.SOUTH;
        else if(this.x < c.x) return Direction.NORTH;
        else if(this.y > c.y) return Direction.EAST;
        else return Direction.WEST;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}

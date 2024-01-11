package model;

import java.util.LinkedList;
import java.util.List;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public List<Direction> potentialDirections() {
        List<Direction> directions = new LinkedList<>();
        switch(this) {
			case EAST:
                directions.add(NORTH);
                directions.add(SOUTH);
                directions.add(EAST);
				break;
			case NORTH:
                directions.add(NORTH);
                directions.add(WEST);
                directions.add(EAST);
				break;
			case SOUTH:
                directions.add(SOUTH);
                directions.add(WEST);
                directions.add(EAST);
				break;
			case WEST:
                directions.add(NORTH);
                directions.add(SOUTH);
                directions.add(WEST);
				break;
			default:
				break;

        }
        return directions;
    }

    public static Direction[] allDirections() {
        return new Direction[] {NORTH, SOUTH, EAST, WEST};
    }
}

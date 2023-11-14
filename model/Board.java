package model;


import tools.*;

import java.util.List;
import java.util.LinkedList;

public class Board {
    private Cell[][] grid;
    private int width, height;

    private Coordinates baseCoordinates;
    private Coordinates startingCoordinates;

    private Base currentBase;
    private List<Tower> currentTowers;
    private List<Mob> currentMobs;

    public Board(Cell[][] grid, Coordinates baseCoordinates, Coordinates startingCoordinates, Base base) {
        this.grid = grid;
        this.width = grid.length;
        this.height = this.width > 0 ? grid[0].length : 0;
        this.baseCoordinates = baseCoordinates;
        this.startingCoordinates = startingCoordinates;
        this.currentBase = base;
        this.currentTowers = new LinkedList<Tower>();
        this.currentMobs = new LinkedList<Mob>();
    }

    public Coordinates getBaseCoordinates() {
        return this.baseCoordinates;
    }

    public Coordinates getStartingCoordinates() {
        return this.startingCoordinates;
    }

    public Base getCurrentBase() {
        return this.currentBase;
    }

    public List<Tower> getCurrentTowers() {
        return this.currentTowers;
    }

    public List<Mob> getCurrentMobs() {
        return this.currentMobs;
    }

    public Cell getCell(Coordinates c) {
        if(c.isInBounds(width, height)) {
            return grid[c.getX()][c.getY()];
        }
        return null;
    }
}
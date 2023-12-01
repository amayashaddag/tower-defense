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

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Cell getCell(Coordinates c) {
        if (c.isInBounds(width, height)) {
            IntCoordinates roundedCoordinates = c.round();
            return grid[roundedCoordinates.getX()][roundedCoordinates.getY()];
        }
        return null;
    }

    public Tower getTower(Coordinates c) {
        for (Tower tower : this.currentTowers) {
            Coordinates towerPosition = tower.getPosition();
            if (towerPosition != null && towerPosition.equals(c)) {
                return tower;
            }
        }
        return null;
    }

    public Mob getMob(Coordinates c) {
        for (Mob mob : this.currentMobs) {
            Coordinates mobPosition = mob.getPosition();
            if (mobPosition != null && mobPosition.equals(c)) {
                return mob;
            }
        }
        return null;
    }

    public boolean addMob(Mob mob) {
        if (mob == null)
            return false;
        mob.setPosition(startingCoordinates);
        this.currentMobs.add(mob);
        return true;
    }

    public boolean addTower(Tower tower) {
        Coordinates position = tower.getPosition();
        if (position == null) {
            return false;
        }
        IntCoordinates roundedCoordinates = position.round();
        if(this.grid[roundedCoordinates.getX()][roundedCoordinates.getY()].isPath()) return false;
        this.currentTowers.add(tower);
        return true;
    }

    /* Returns the list of mobs that are in range from a specific center */

    public List<Mob> getMobsInRange(Coordinates center, int range) {
        if (center == null)
            return null;

        List<Mob> mobsInRange = new LinkedList<Mob>();
        for (Mob mob : this.currentMobs) {
            if (mob.getPosition().isInRange(center, range)) {
                mobsInRange.add(mob);
            }
        }

        return mobsInRange;
    }

    public static Board boardExample() {
        return new Board(new Cell[][] {
                {
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                },
                {
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),

                },
                {
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, true),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                },
                {
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                },
                {
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                }
        }, new Coordinates(2, 4), new Coordinates(0, 0), new Base(50));
    }

    @Override
    public String toString() {
        String horizontalSpace = "    ", s = horizontalSpace;
        for(int i = 0; i < this.height; i++) {
            s += String.format("  %d   ", i + 1);
        }
        s += "\n";
        char letter = 'A';
        for (int i = 0; i < this.width; i++) {
            s += horizontalSpace;
            for (int j = 0; j < this.height; j++) {
                for (int k = 0; k < 3; k++) {
                    s += this.grid[i][j].toString() + " ";
                }
            }
            s += "\n " + letter + "  ";
            for (int j = 0; j < this.height; j++) {
                s += this.grid[i][j].toString() + " ";
                if (this.grid[i][j].isPath()) {
                    Mob mob = this.getMob(new Coordinates(i, j));
                    if (mob != null)
                        s += "M ";
                    else
                        s += this.grid[i][j].toString() + " ";
                } else {
                    Tower t = this.getTower(new Coordinates(i, j));
                    if (t != null)
                        s += "T ";
                    else
                        s += this.grid[i][j].toString() + " ";
                }
                s += this.grid[i][j].toString() + " ";
            }
            s += "\n" + horizontalSpace;
            for (int j = 0; j < this.height; j++) {
                for (int k = 0; k < 3; k++) {
                    s += this.grid[i][j].toString() + " ";
                }
            }
            s += "\n";
            letter++;
        }
        return s;
    }
}
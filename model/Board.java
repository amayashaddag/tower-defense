package model;

import tools.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class Board {
    private Cell[][] grid;
    private int width, height;

    private Coordinates baseCoordinates;
    private Coordinates startingCoordinates;

    private Base currentBase;
    private List<Tower> currentTowers;
    private List<Mob> currentMobs;

    private Random random;

    public Board(Cell[][] grid, Coordinates baseCoordinates, Coordinates startingCoordinates, Base base) {
        this.grid = grid;
        this.height = grid.length;
        this.width = this.height > 0 ? grid[0].length : 0;
        this.baseCoordinates = baseCoordinates;
        this.startingCoordinates = startingCoordinates;
        this.currentBase = base;
        this.currentTowers = new LinkedList<Tower>();
        this.currentMobs = new LinkedList<Mob>();
        this.random = new Random();
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
        if (c.isInBounds(height, width)) {
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
        initMobPosition(mob);
        return true;
    }

    private void initMobPosition(Mob m) {
        if(m == null) return;
        for(Direction d : Direction.allDirections()) {
            IntCoordinates c = m.getPosition().round().plus(d);
            if(c.isInBounds(height, width) 
            && this.grid[c.getX()][c.getY()].isPath()) {
                m.setDirection(d);
                return;
            }
        }
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

    public Mob getMobTargetInRange(Coordinates center, int range) {
        List<Mob> mobsInRange = this.getMobsInRange(center, range);
        if(mobsInRange == null || mobsInRange.isEmpty()) return null;
        return mobsInRange.get(0);
    }
    
    /* The idea in this function is that the adjacent cells to reach are all
    the adjacent cells except the one in the opposite direction */
    
    public List<IntCoordinates> adjacentCellsToReach(IntCoordinates currentCell, Direction direction) {
        List<IntCoordinates> cells = new LinkedList<>();
        for(Direction d : direction.potentialDirections()) {
            IntCoordinates cell = currentCell.plus(d);
            if(cell.isInBounds(height, width) 
            && this.grid[cell.getX()][cell.getY()].isPath()) {
                cells.add(cell);
            }
        }
        return cells;
    }

    public void updateMobsPosition() {
        for(Mob m : this.currentMobs) {
            List<IntCoordinates> adjacentCells = this.adjacentCellsToReach(m.getPosition().round(), m.getDirection());
            if(adjacentCells.size() > 0) {
                IntCoordinates nextPosition = adjacentCells.get(random.nextInt(adjacentCells.size()));
                Direction direction = nextPosition.getDirectionFrom(m.getPosition().round());
                m.setDirection(direction);
                m.setPosition(new Coordinates(nextPosition.getX(), nextPosition.getY()));
            }
        }
    }

    public static Board boardExample() {
        return new Board(new Cell[][] {
            {
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false)
            },
            {
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false)
            },
            {
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false)
            },
            {
                new Cell(true, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false)
            },
            {
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false)
            },
            {
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false)
            },
            {
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(false, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, false),
                new Cell(true, true)
            }
        }, new Coordinates(6, 10), new Coordinates(0, 0), new Base(50));
    }

    @Override
    public String toString() {
        String horizontalSpace = "    ", s = horizontalSpace;
        for(int i = 0; i < this.width; i++) {
            s += String.format("  %d   ", i + 1);
        }
        s += "\n";
        char letter = 'A';
        for (int i = 0; i < this.height; i++) {
            s += horizontalSpace;
            for (int j = 0; j < this.width; j++) {
                for (int k = 0; k < 3; k++) {
                    s += this.grid[i][j].toString() + " ";
                }
            }
            s += "\n " + letter + "  ";
            for (int j = 0; j < this.width; j++) {
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
            for (int j = 0; j < this.width; j++) {
                for (int k = 0; k < 3; k++) {
                    s += this.grid[i][j].toString() + " ";
                }
            }
            s += "\n";
            letter++;
        }
        return s;
    }

    public String cellType(int i, int j) {
        if(i < 0 || i > height || j < 0 || j > width
        || !this.grid[i][j].isPath()) return "NAP";

        if(i > 0 && j > 0 && i < height - 1 && j < width - 1
        && this.grid[i - 1][j].isPath()
        && this.grid[i][j - 1].isPath()
        && this.grid[i + 1][j].isPath()
        && this.grid[i][j + 1].isPath()) return "PLUS";
        
        if(j > 0 && i < height - 1 && j < width - 1 
        && this.grid[i][j - 1].isPath()
        && this.grid[i + 1][j].isPath()
        && this.grid[i][j + 1].isPath()) return "SOUTH-T";

        if(i > 0 && j > 0 && j < width - 1
        && this.grid[i - 1][j].isPath()
        && this.grid[i][j - 1].isPath()
        && this.grid[i][j + 1].isPath()) return "NORTH-T";

        if(i > 0 && j > 0 && i < height - 1
        && this.grid[i - 1][j].isPath()
        && this.grid[i][j - 1].isPath()
        && this.grid[i + 1][j].isPath()) return "WEST-T";

        if(i > 0 && i < height - 1 && j < width - 1 
        && this.grid[i - 1][j].isPath()
        && this.grid[i + 1][j].isPath()
        && this.grid[i][j + 1].isPath()) return "EAST-T";

        if(i > 0 && j > 0
        && this.grid[i - 1][j].isPath()
        && this.grid[i][j - 1].isPath()) return "NORTH-WEST";

        if(i > 0 && j < width - 1 
        && this.grid[i - 1][j].isPath()
        && this.grid[i][j + 1].isPath()) return "NORTH-EAST";

        if(i < height - 1 && j < width - 1 
        && this.grid[i + 1][j].isPath()
        && this.grid[i][j + 1].isPath()) return "SOUTH-EAST";

        if(i < height - 1 && j > 0 
        && this.grid[i + 1][j].isPath()
        && this.grid[i][j - 1].isPath()) return "SOUTH-WEST";

        if((i > 0 && this.grid[i - 1][j].isPath())
        || (i < height - 1 && this.grid[i + 1][j].isPath())) return "VERTICAL";

        else return "HORIZONTAL";
    }
}
package model;

import tools.*;
import towers.Tower;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import items.Item;
import mobs.Mob;

public class Board {
    private Cell[][] grid;
    private int width, height;

    private final Coordinates baseCoordinates;
    private Coordinates startingCoordinates;

    private Base currentBase;
    private List<Tower> currentTowers;
    private List<Item> currentItems;
    private List<Mob> currentMobs;

    private Random random;

    private static final double MAX_TOLERATED_DISTANCE_FROM_CENTER = 0.1;
    public static final double SPEED_EQUATION_FACTOR = 0.7E-9;

    public static final int  EASY_MODE_BASE_HP = 25;
    public static final int MEDIUM_MODE_BASE_HP = 10;
    public static final int  HARD_MODE_BASE_HP = 5;
    
    public static final Coordinates EASY_MODE_BASE_COORDINATES = new Coordinates(6, 10);
    public static final Coordinates MEDIUM_MODE_BASE_COORDINATES = new Coordinates(6,10);
    public static final Coordinates HARD_MODE_BASE_COORDINATES = new Coordinates(3,10);
    
    public static final Coordinates EASY_MODE_STARTING_COORDINATES = new Coordinates(0,0);
    public static final Coordinates MEDIUM_MODE_STARTING_COORDINATES = new Coordinates(0,0);
    public static final Coordinates HARD_MODE_STARTING_COORDINATES = new Coordinates(3,0);




    public Board(Cell[][] grid, Coordinates baseCoordinates, Coordinates startingCoordinates, Base base) {
        this.grid = grid;
        this.height = grid.length;
        this.width = this.height > 0 ? grid[0].length : 0;
        this.baseCoordinates = baseCoordinates;
        this.startingCoordinates = startingCoordinates;
        this.currentBase = base;
        this.currentTowers = new LinkedList<Tower>();
        this.currentMobs = new LinkedList<Mob>();
        this.currentItems = new LinkedList<>();
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

    public List<Item> getCurrentItems() {
        return this.currentItems;
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
            IntCoordinates mobPosition = mob.getPosition().round();
            if (mobPosition != null && mobPosition.equals(c.round())) {
                return mob;
            }
        }
        return null;
    }

    public List<Mob> mobsToEliminate() {
        List<Mob> mobsToEliminate = new LinkedList<>();
        for (Mob m : currentMobs) {
            if (m.isDead()) {
                mobsToEliminate.add(m);
            }
        }
        return mobsToEliminate;
    }

    public void removeDeadMobs() {
        List<Mob> mobsToEliminate = mobsToEliminate();
        for (Mob m : mobsToEliminate) {
            currentMobs.remove(m);
        }
    }

    public boolean addMob(Mob mob) {
        if (mob == null)
            return false;
        mob.setPosition(startingCoordinates);
        this.currentMobs.add(mob);
        initMobPosition(mob);
        return true;
    }

    public int getCurrentMobsNumber() {
        return this.currentMobs.size();
    }

    private void initMobPosition(Mob m) {
        if (m == null)
            return;
        for (Direction d : Direction.allDirections()) {
            IntCoordinates c = m.getPosition().round().plus(d);
            if (c.isInBounds(height, width)
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
        if (this.grid[roundedCoordinates.getX()][roundedCoordinates.getY()].isPath())
            return false;
        this.currentTowers.add(tower);
        return true;
    }

    public void addItem(Item i) {
        Coordinates position = i.getPosition();
        if (position == null) {
            return;
        }
        IntCoordinates roundedCoordinates = position.round();
        if (this.grid[roundedCoordinates.getX()][roundedCoordinates.getY()].isPath())
            return;
        this.currentItems.add(i);
    }

    public void removeItem(Item i) {

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
        if (mobsInRange == null || mobsInRange.isEmpty())
            return null;
        return mobsInRange.get(random.nextInt(mobsInRange.size()));
    }

    /*
     * The idea in this function is that the adjacent cells to reach are all
     * the adjacent cells except the one in the opposite direction
     */

    public List<IntCoordinates> adjacentCellsToReach(IntCoordinates currentCell, Direction direction) {
        List<IntCoordinates> cells = new LinkedList<>();
        for (Direction d : direction.potentialDirections()) {
            IntCoordinates cell = currentCell.plus(d);
            if (cell.isInBounds(height, width)
                    && this.grid[cell.getX()][cell.getY()].isPath()) {
                cells.add(cell);
            }
        }
        return cells;
    }

    private void moveOneCell(Mob m) {
        List<IntCoordinates> adjacentCells = this.adjacentCellsToReach(m.getPosition().round(), m.getDirection());
        if (!adjacentCells.isEmpty()) {
            IntCoordinates nextCell = adjacentCells.get(random.nextInt(adjacentCells.size()));
            Direction direction = nextCell.getDirectionFrom(m.getPosition().round());
            m.setDirection(direction);
            m.setPosition(m.getPosition().plus(Coordinates.getUnit(direction)));
        }
    }

    public void eliminateMobs() {
        List<Mob> mobsToEliminate = new LinkedList<>();

        for (Mob m : currentMobs) {
            if (m.isToEliminate(baseCoordinates)) {
                mobsToEliminate.add(m);
            }
        }

        for (Mob m : mobsToEliminate) {
            currentMobs.remove(m);
        }
    }

    public void updateMobsPosition() {

        for (Mob m : this.currentMobs) {
            List<IntCoordinates> adjacentCells = this.adjacentCellsToReach(m.getPosition().round(), m.getDirection());
            if (!adjacentCells.isEmpty()) {
                moveOneCell(m);

                if (m.getPosition().greaterOrEquals(baseCoordinates)) {
                    m.attackBase(currentBase);
                }

            }
        }

        eliminateMobs();
    }

    public void updateMobsPosition(long deltaT) {
        for (Mob m : this.currentMobs) {
            List<IntCoordinates> adjacentCells = this.adjacentCellsToReach(m.getPosition().round(), m.getDirection());
            if (!adjacentCells.isEmpty()) {
                IntCoordinates currentCell = m.getPosition().round();

                IntCoordinates nextCell = adjacentCells.get(this.random.nextInt(adjacentCells.size()));
                Direction direction = nextCell.getDirectionFrom(currentCell);

                if (m.getPosition().distanceFromCenterIsInRange(MAX_TOLERATED_DISTANCE_FROM_CENTER)
                        && !m.isInLastVisitedCells(nextCell)) {
                    m.setDirection(direction);
                    m.addToLastVisitedCells(nextCell);
                    m.addToLastVisitedCells(currentCell);
                }
                Coordinates nextPos = m.getPosition().plus(
                        Coordinates.getUnit(m.getDirection()).times(deltaT * m.getSpeed() * SPEED_EQUATION_FACTOR));
                if (!nextPos.round().equals(currentCell)) {
                    m.addToLastVisitedCells(currentCell);
                }
                m.setPosition(nextPos);

                if ((m.getPosition().round()).equals(baseCoordinates.round())) {
                    m.attackBase(currentBase);
                }
            }
        }

        eliminateMobs();
    }

    public static Board boardEasyMode() {
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
            
        }, EASY_MODE_BASE_COORDINATES, EASY_MODE_STARTING_COORDINATES, new Base(EASY_MODE_BASE_HP));
    }

    public static Board boardMediumMode() {
        return new Board(new Cell[][] {
                {
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false)
                },
                {
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false)
                },
                {
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false)
                },
                {
                        new Cell(true, false),
                        new Cell(false, false),
                        new Cell(false, false),
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
                        new Cell(false, false),
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
                        new Cell(false, false),
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
                        new Cell(true,false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, true)
                }
        }, MEDIUM_MODE_BASE_COORDINATES, MEDIUM_MODE_STARTING_COORDINATES, new Base(MEDIUM_MODE_BASE_HP));
    }
     public static Board boardHardMode() {
        return new Board(new Cell[][] {
                {
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false)
                },
                {
                        new Cell(false, false),
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
                        new Cell(false, false),
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
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, false),
                        new Cell(true, true)
                },
                {
                        new Cell(false, false),
                        new Cell(true, false),
                        new Cell(false, false),
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
                        new Cell(false, false),
                        new Cell(true, false),
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
                        new Cell(false,false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false),
                        new Cell(false, false)
                }
        }, HARD_MODE_BASE_COORDINATES, HARD_MODE_STARTING_COORDINATES , new Base(HARD_MODE_BASE_HP));
    }


    @Override
    public String toString() {
        String horizontalSpace = "    ", s = horizontalSpace;
        for (int i = 0; i < this.width; i++) {
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
                        s += mob.toString() + " ";
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

    /* Needed in graphical interface display */

    public String cellType(int i, int j) {

        if (this.grid[i][j].isBase())
            return "BASE";

        if (i < 0 || i > height || j < 0 || j > width
                || !this.grid[i][j].isPath())
            return "NAP";

        if (i > 0 && j > 0 && i < height - 1 && j < width - 1
                && this.grid[i - 1][j].isPath()
                && this.grid[i][j - 1].isPath()
                && this.grid[i + 1][j].isPath()
                && this.grid[i][j + 1].isPath())
            return "PLUS";

        if (j > 0 && i < height - 1 && j < width - 1
                && this.grid[i][j - 1].isPath()
                && this.grid[i + 1][j].isPath()
                && this.grid[i][j + 1].isPath())
            return "SOUTH-T";

        if (i > 0 && j > 0 && j < width - 1
                && this.grid[i - 1][j].isPath()
                && this.grid[i][j - 1].isPath()
                && this.grid[i][j + 1].isPath())
            return "NORTH-T";

        if (i > 0 && j > 0 && i < height - 1
                && this.grid[i - 1][j].isPath()
                && this.grid[i][j - 1].isPath()
                && this.grid[i + 1][j].isPath())
            return "WEST-T";

        if (i > 0 && i < height - 1 && j < width - 1
                && this.grid[i - 1][j].isPath()
                && this.grid[i + 1][j].isPath()
                && this.grid[i][j + 1].isPath())
            return "EAST-T";

        if (i > 0 && j > 0
                && this.grid[i - 1][j].isPath()
                && this.grid[i][j - 1].isPath())
            return "NORTH-WEST";

        if (i > 0 && j < width - 1
                && this.grid[i - 1][j].isPath()
                && this.grid[i][j + 1].isPath())
            return "NORTH-EAST";

        if (i < height - 1 && j < width - 1
                && this.grid[i + 1][j].isPath()
                && this.grid[i][j + 1].isPath())
            return "SOUTH-EAST";

        if (i < height - 1 && j > 0
                && this.grid[i + 1][j].isPath()
                && this.grid[i][j - 1].isPath())
            return "SOUTH-WEST";

        if ((i > 0 && this.grid[i - 1][j].isPath())
                || (i < height - 1 && this.grid[i + 1][j].isPath()))
            return "VERTICAL";

        else
            return "HORIZONTAL";
    }
}
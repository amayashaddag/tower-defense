package model;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

import tools.*;

public class Game {

    private Player currentPlayer;
    private Board currentBoard;
    private boolean gameFinished;
    private Scanner scanner;
    private List<Triplet> waves;
    private Random random;
    private final static int MOB_SPAWNING_DELAY = 800;

    public Game(Player currentPlayer, Board currentBoard, List<Triplet> waves) {
        this.currentPlayer = currentPlayer;
        this.currentBoard = currentBoard;
        this.gameFinished = false;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.waves = waves;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public boolean gameFinished() {
        return this.gameFinished;
    }

    // FIXME : Fix reading coordinates while having a more than 2 integer in y
    // coordinates

    public Coordinates readCoordinates() {
        System.out.print("Enter coordinates : ");
        String input = this.scanner.next();
        if (input.length() < 2) {
            System.out.println("Error! Wrong format.");
            return this.readCoordinates();
        }
        int x = input.charAt(0) - 65, y = input.charAt(1) - 49;
        Coordinates c = new Coordinates(x, y);
        if (!c.isInBounds(currentBoard.getHeight(), currentBoard.getWidth())) {
            System.out.println(c);
            System.out.println("Error! Wrong format.");
            return this.readCoordinates();
        }
        return c;
    }

    public void addTower() {
        System.out.println(this.currentPlayer.getTowersInventory());
        System.out.print("Enter inventory slot : ");
        int towersInventoryIndex = scanner.nextInt();
        Tower t;
        try {
            t = this.currentPlayer.getTowerFromIndex(towersInventoryIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error! Slot doesn't exist.");
            return;
        }
        Coordinates c = this.readCoordinates();
        if (c == null)
            return;
        t.setPosition(c);
        if (!this.currentBoard.addTower(t)) {
            System.out.println("Error! Tower can't be added to board.");
        }
    }

    public boolean roundFinished() {
        return currentBoard.getCurrentBase().getHp() <= 0
                || (waves.isEmpty() && currentBoard.getCurrentMobs().isEmpty());
    }

    public void startRound() {

        System.out.print("\033[H\033[2J");
        System.out.println(this.currentBoard);

        initRound();
        int iter = 1;
        while (!this.roundFinished()) {

            System.out.print("\033[H\033[2J");
            System.out.println(this.currentBoard);
            System.out.println(currentBoard.getCurrentBase());

            if (!waves.isEmpty()) {
                addMob();
            }
            for (Tower t : currentBoard.getCurrentTowers()) {
                Mob mob = currentBoard.getMobTargetInRange(t.getPosition(), t.getRange());
                if (mob != null && (iter % (t.getRateOfFire()) == 0)) {
                    if (t instanceof SingleTargetDamage targetTower) {
                        targetTower.attack(mob);
                    } else if (t instanceof ZoneDamage zoneTower) {
                        List<Mob> mobsInRange = currentBoard.getMobsInRange(mob.getPosition(), t.getRange());
                        zoneTower.attack(mobsInRange);
                    }

                    currentBoard.removeDeadMobs();
                }
            }
            iter++;
            currentBoard.updateMobsPosition();
            scanner.nextLine();

        }
    }

    private int chooseMobLevel() {
        Triplet currentWave = waves.get(0);
        int mobLevel = 0;
        do {
            mobLevel = random.nextInt(Mob.MAX_LEVEL + 1);
        } while (currentWave.get(mobLevel) == 0);
        return mobLevel;
    }

    public void addMob() {
        Triplet currentWave = waves.get(0);
        int mobLevel = chooseMobLevel();
        Mob mob = new Mob(mobLevel);
        currentWave.decrement(mobLevel);
        if (currentWave.isNull()) {
            waves.remove(0);
        }
        currentBoard.addMob(mob);
    }

    public void initRound() {
        boolean answer = false;
        do {
            System.out.println("Do you want to place a tower? (y | n).");
            String playerAction = this.scanner.nextLine();
            if (playerAction.equals("y")) {
                this.addTower();
                answer = true;
            } else {
                answer = false;
            }
            System.out.print("\033[H\033[2J");
            System.out.println(this.currentBoard);
        } while (answer);

    }

    /* On est dans Round */
    public Timer MakeRound(int nbMobLevel0, int nbMobLevel1, int nbMobLevel2) {
        Timer Round = new Timer(MOB_SPAWNING_DELAY, new ActionListener() {
            Triplet wave = new Triplet(nbMobLevel0, nbMobLevel1, nbMobLevel2);

            @Override
            public void actionPerformed(ActionEvent e) {
                if (wave.isNull()) {
                    ((Timer) e.getSource()).setRepeats(false);
                }
                if (wave.getX() > 0) {
                    Game.this.currentBoard.addMob(new Mob(0));
                    wave.decrement(0);
                } else {
                    if (wave.getY() > 0) {
                        Game.this.currentBoard.addMob(new Mob(1));
                        wave.decrement(1);

                    } else {
                        Game.this.currentBoard.addMob(new Mob(2));
                        wave.decrement(2);
                    }
                }
            }
        });
        return Round;
    }

}
package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

import tools.*;

public class Game {

    private Player currentPlayer;
    private Board currentBoard;
    private Scanner scanner;
    private int indexCurrentWave;
    private List<Triplet> waves;
    private Random random;
    private final static int MOB_SPAWNING_DELAY = 1000;

    public Game(Player currentPlayer, Board currentBoard, List<Triplet> waves) {
        this.currentPlayer = currentPlayer;
        this.currentBoard = currentBoard;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.waves = waves;
        this.indexCurrentWave = 0;
    }

    public int getIndexCurrentWave() {
        return indexCurrentWave;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public int nbRounds() {
        return waves.size();
    }

    public void nextRound() {
        indexCurrentWave++;
    }

    public boolean gameFinished() {
        return indexCurrentWave >= nbRounds() || roundLost();
    }

    public boolean playerHasEnoughCreditFor(int price) {
        return currentPlayer.hasEnoughCredit(price);
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

    public void getCreditsFromMobs() {
        for (Mob mob : currentBoard.mobsToEliminate()) {
            System.out.println("cc");
            currentPlayer.wonCredit(mob.getLevel()+1);
        }
    }

    public boolean roundFinishedText() {
        return currentBoard.getCurrentBase().getHp() <= 0
                || (waves.isEmpty() && currentBoard.getCurrentMobs().isEmpty());
    }

    public void startRound() {

        System.out.print("\033[H\033[2J");
        System.out.println(this.currentBoard);

        initRound();
        int iter = 1;
        while (!this.roundFinishedText()) {

            System.out.print("\033[H\033[2J");
            System.out.println(this.currentBoard);
            System.out.println(currentBoard.getCurrentBase());

            if (!waves.isEmpty()) {
                addMob();
            }
            for (Tower t : currentBoard.getCurrentTowers()) {
                Mob mob = currentBoard.getMobTargetInRange(t.getPosition(), t.getRange());
                if (mob != null && (iter % (t.getRateOfFire()) == 0)) {
                    if (t instanceof SingleTargetDamage) {
                        SingleTargetDamage targetTower = (SingleTargetDamage) t;
                        targetTower.attack(mob);
                    } else if (t instanceof ZoneDamage) {
                        ZoneDamage zoneTower = (ZoneDamage) t;
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

    void genereteMobLevel(int x, Triplet wave) {
        Game.this.currentBoard.addMob(new Mob(x));
        wave.decrement(x);

    }

    void generateMobsInOrder(Triplet wave) {
        if (wave.getX() > 0) {
            genereteMobLevel(0, wave);

        } else {
            if (wave.getY() > 0) {
                genereteMobLevel(1, wave);

            } else {
                if (wave.getZ() > 0) {
                    genereteMobLevel(2, wave);

                }
            }
        }
    }

    public Triplet getCurrentWave() {
        return this.waves.get(indexCurrentWave);
    }

    public boolean roundLost() {
        return this.currentBoard.getCurrentBase().baseLost();
    }

    public boolean roundWon() {
        return getCurrentWave().isNull() && currentBoard.getCurrentMobs().isEmpty();
    }

    public boolean roundFinished() {
        return roundLost() || roundWon();
    }

    /* On est dans Round */
    public void startRound(int index) {
        Triplet wave = this.waves.get(index);
        Timer Round = new Timer(MOB_SPAWNING_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wave.isNull()) {
                    ((Timer) e.getSource()).setRepeats(false);

                } else {
                    generateMobsInOrder(wave);
                }
            }
        });
        Round.start();
    }

    public void startCurrentRound() {
        startRound(indexCurrentWave);
    }

    private static List<Triplet> easyModeRound() {
        List<Triplet> rounds = new ArrayList<>();
        rounds.add(new Triplet(10, 0, 0));
        rounds.add(new Triplet(10, 2, 0));
        rounds.add(new Triplet(10, 5, 0));
        return rounds;
    }

    private static List<Triplet> mediumModeRound() {
        List<Triplet> rounds = new ArrayList<>();
        rounds.add(new Triplet(10, 0, 0));
        rounds.add(new Triplet(10, 2, 5));
        rounds.add(new Triplet(5, 5, 10));
        rounds.add(new Triplet(5, 7, 10));
        rounds.add(new Triplet(2, 5, 10));
        return rounds;
    }

    // FIXME : Set different maps for differents modes : easy, medium, hard ...

    public static Game getEasyModeGame(Player player) {
        Board easyModeBoard = Board.boardExample();
        List<Triplet> easyModeRound = easyModeRound();
        Game easyModeGame = new Game(player, easyModeBoard, easyModeRound);
        return easyModeGame;
    }

    public static Game getMediumModeGame(Player player) {
        Board mediumModeBoard = Board.boardExample();
        List<Triplet> mediumModeRound = mediumModeRound();
        Game mediumModeGame = new Game(player, mediumModeBoard, mediumModeRound);
        return mediumModeGame;
    }

}
package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;

import mobs.Mob;

import java.awt.event.ActionEvent;

import tools.*;
import towers.Tower;

public class Game {

    private Player currentPlayer;
    private Board currentBoard;
    private Scanner scanner;

    private List<Triplet> waves;
    private Random random;
    private boolean marathonMode;
    private Difficulty roundDifficulty;

    private final static int MOB_SPAWNING_DELAY = 1000;
    public final static Triplet MARATHON_SEED = new Triplet(3, 2, 1);

    public Game(Player currentPlayer, Board currentBoard, List<Triplet> waves,Difficulty roundDifficulty) {
        this.currentPlayer = currentPlayer;
        this.currentBoard = currentBoard;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.waves = waves;
        this.marathonMode = false;
        this.roundDifficulty = roundDifficulty;
    }
    public Game(Player currentPlayer,Board currentBoard,List<Triplet> waves){
        this.currentPlayer=currentPlayer;
        this.currentBoard=currentBoard;
        this.waves=waves;
        this.marathonMode=true;
    }
    public boolean isMarathonMode() {
        return marathonMode;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public void nextRound() {
        this.waves.remove(0);
    }

    public List<Triplet> getWaves() {
        return waves;
    }
    public Difficulty getRoundDifficulty() {
        return roundDifficulty;
    }

    public boolean gameFinished() {
        return this.waves.isEmpty() || roundLost();
    }

    public boolean playerHasEnoughCreditFor(int price) {
        return currentPlayer.hasEnoughCredit(price);
    }

    public void addWave(Triplet wave) {
        this.waves.add(this.waves.size(), new Triplet(wave.getX(), wave.getY(), wave.getZ()));
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
            currentPlayer.wonCredit((mob.getLevel() + 1) * 2);
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
        return this.waves.get(0);
    }

    public boolean roundLost() {
        return this.currentBoard.getCurrentBase().baseLost();
    }

    public boolean roundWon() {
        return (getCurrentWave().isNull()) && currentBoard.getCurrentMobs().isEmpty();
    }

    public boolean roundFinished() {
        return roundLost() || roundWon();
    }

    /* On est dans Round */
    public void startRound(Triplet wave) {
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
        startRound(getCurrentWave());
    }

    private static List<Triplet> easyModeRound() {
        List<Triplet> rounds = new ArrayList<>();
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

    private static List<Triplet> hardModeRound() {
        List<Triplet> rounds = new ArrayList<>();
        rounds.add(new Triplet(10, 0, 0));
        rounds.add(new Triplet(10, 2, 5));
        rounds.add(new Triplet(5, 5, 10));
        rounds.add(new Triplet(5, 7, 10));
        rounds.add(new Triplet(2, 5, 10));
        return rounds;
    }

    public Triplet nextWave(Triplet initialWave, int n) {
        int x0 = initialWave.getX(), y0 = initialWave.getY(), z0 = initialWave.getZ();
        int x = (int) (x0 / (n + 1)), y = y0, z = (int) (z0 * (n + 1));
        return new Triplet(x, y, z);
    }

    public static Game getEasyModeGame(Player player) {
        Board easyModeBoard = Board.boardEasyMode();
        List<Triplet> easyModeRound = easyModeRound();
        Game easyModeGame = new Game(player, easyModeBoard, easyModeRound,Difficulty.EASY);
        return easyModeGame;
    }

    public static Game getMediumModeGame(Player player) {
        Board mediumModeBoard = Board.boardMediumMode();
        List<Triplet> mediumModeRound = mediumModeRound();
        Game mediumModeGame = new Game(player, mediumModeBoard, mediumModeRound,Difficulty.MEDIUM);
        return mediumModeGame;
    }

    public static Game getHardModeGame(Player player) {
        Board hardModeBoard = Board.boardHardMode();
        List<Triplet> hardModeRound = hardModeRound();
        Game hardModeGame = new Game(player, hardModeBoard, hardModeRound,Difficulty.HARD);
        return hardModeGame;
    }

    public static Game getMarathonMode(Player player) {
        Board easyModeBoard = Board.boardEasyMode();
        List<Triplet> marathonMode = new LinkedList<>();
        marathonMode.add(new Triplet(MARATHON_SEED.getX(), MARATHON_SEED.getY(), MARATHON_SEED.getZ()));
        Game marathonModeGame = new Game(player, easyModeBoard, marathonMode);
        return marathonModeGame;
    }
}
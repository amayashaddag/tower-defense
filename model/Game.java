package model;

import java.util.Scanner;

import tools.*;

public class Game {
    private Player currentPlayer;
    private Board currentBoard;
    private boolean gameFinished;
    private Scanner scanner;

    public Game(Player currentPlayer, Board currentBoard) {
        this.currentPlayer = currentPlayer;
        this.currentBoard = currentBoard;
        this.gameFinished = false;
        this.scanner = new Scanner(System.in);
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

    public Coordinates readCoordinates() {
        System.out.print("Enter coordinates : ");
        String input = this.scanner.next();
        if (input.length() < 2) {
            System.out.println("Error! Wrong format.");
            return this.readCoordinates();
        }
        int x = input.charAt(0) - 65, y = input.charAt(1) - 49;
        Coordinates c = new Coordinates(x, y);
        if (!c.isInBounds(currentBoard.getWidth(), currentBoard.getHeight())) {
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
        t.setPosition(c);
        if (!this.currentBoard.addTower(t)) {
            System.out.println("Error! Tower can't be added to board.");
        }
    }

    public void playerAction() { 
        System.out.println(this.currentBoard);
        System.out.println("Enter 't' to put a tower, 'q' to quit.");

        char instruction = this.scanner.next().charAt(0);
        if(instruction == 'q') return;
        else {
            if(instruction == 't') {
                this.addTower();
            }
        }
    }
}

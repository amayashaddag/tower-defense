package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.*;

public class MapGraphicsFactory {

    public static final String LAND_REPOSITORY = "resources/land/";
    public static final String LAND_EASY_MAP_REPOSITORY_ = LAND_REPOSITORY + "easy-mode-land/";
    public static final String LAND_MEDIUM_MAP_REPOSITORY_ = LAND_REPOSITORY + "medium-mode-land/";
    public static final String LAND_HARD_MAP_REPOSITORY_ = LAND_REPOSITORY + "hard-mode-land/";

    

    public static Image[][] loadEasyMap(Board currentBoard) {
        Image[][] map = new Image[currentBoard.getHeight()][currentBoard.getWidth()];
        for (int i = 0; i < currentBoard.getHeight(); i++) {
            for (int j = 0; j < currentBoard.getWidth(); j++) {
                ImageIcon image;
                String url = LAND_EASY_MAP_REPOSITORY_ + urlToFrame(i, j, currentBoard);
                image = new ImageIcon(url);
                map[i][j] = image.getImage();
            }
        }
        return map;
    }
    public static Image[][] loadMediumMap(Board currentBoard) {
        Image[][] map = new Image[currentBoard.getHeight()][currentBoard.getWidth()];
        for (int i = 0; i < currentBoard.getHeight(); i++) {
            for (int j = 0; j < currentBoard.getWidth(); j++) {
                ImageIcon image;
                String url = LAND_MEDIUM_MAP_REPOSITORY_ + urlToFrame(i, j, currentBoard);
                image = new ImageIcon(url);
                map[i][j] = image.getImage();
            }
        }
        return map;
    }
    public static Image[][] loadHardMap(Board currentBoard) {
        Image[][] map = new Image[currentBoard.getHeight()][currentBoard.getWidth()];
        for (int i = 0; i < currentBoard.getHeight(); i++) {
            for (int j = 0; j < currentBoard.getWidth(); j++) {
                ImageIcon image;
                String url = LAND_HARD_MAP_REPOSITORY_ + urlToFrame(i, j, currentBoard);
                image = new ImageIcon(url);
                map[i][j] = image.getImage();
            }
        }
        return map;
    }

    private static String urlToFrame(int i, int j, Board currentBoard) {
        switch (currentBoard.cellType(i, j)) {
            case "BASE" : 
                return "base.png";
            case "PLUS":
                return "plusPath.png";
            case "NORTH-T":
                return "north-t-path.png";
            case "SOUTH-T":
                return "south-t-path.png";
            case "EAST-T":
                return "east-t-path.png";
            case "WEST-T":
                return "west-t-path.png";
            case "NORTH-WEST":
                return "north-west-path.png";
            case "SOUTH-WEST":
                return "south-west-path.png";
            case "NORTH-EAST":
                return "north-east-path.png";
            case "SOUTH-EAST":
                return "south-east-path.png";
            case "HORIZONTAL":
                return "horizontal-path.png";
            case "VERTICAL":
                return "vertical-path.png";
            default:
                return "grass.png";
        }
    }
}

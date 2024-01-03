package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import assets.Button;
import control.GameControl;
import model.Game;
import model.Player;
import shop.ShopView;
import view.GameView;

public class MenuView extends JPanel {
    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 720;

    public MenuView(Player player) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        HorizontalLayout normalModes = new HorizontalLayout();
        VerticalLayout navigationButtons = new VerticalLayout();
        this.add(normalModes);
        this.add(navigationButtons);

        this.player = player;
    }

    private class HorizontalLayout extends JPanel {
        public HorizontalLayout() {
            this.setSize(WINDOW_WIDTH * 60 / 100, 50);
            Button easyModeButton = Button.smallMenuButton(Button.EASY_BUTTON_LABEL);
            Button mediumModeButton = Button.smallMenuButton(Button.MEDIUM_BUTTON_LABEL);
            Button hardModeButton = Button.smallMenuButton(Button.HARD_BUTTON_LABEL);

            easyModeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Game easyGame = Game.getEasyModeGame(player);
                    startGame(easyGame);
                }
            });

            mediumModeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Game mediumGame = Game.getMediumModeGame(player);
                    startGame(mediumGame);
                }
            });

            hardModeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Game hardGame = Game.getHardModeGame(player); 
                    startGame(hardGame);               
                }
            });

            this.add(easyModeButton);
            this.add(mediumModeButton);
            this.add(hardModeButton);
        }

    }

    private class VerticalLayout extends JPanel {
        public VerticalLayout() {
            this.setSize(WINDOW_WIDTH * 60 / 100, 500);
            Button marathonButton = Button.largeMenuButton(Button.MARATHON_BUTTON_LABEL);
            Button shopButton = Button.largeMenuButton(Button.SHOP_BUTTON_LABEL);
            Button exitButton = Button.largeMenuButton(Button.EXIT_BUTTON_LABEL);

            marathonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Game marathonGame = Game.getMarathonMode(player);
                    startGame(marathonGame);
                }
            });

            shopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    ShopView shopView = new ShopView(player);
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(MenuView.this);
                    parentFrame.setContentPane(shopView);
                    parentFrame.repaint();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(MenuView.this);
                    parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING));
                }
            });

            this.add(marathonButton);
            this.add(shopButton);
            this.add(exitButton);
        }
    }

    public void startGame(Game selectedGame) {
        GameView gameView = new GameView(selectedGame);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(gameView);
        parentFrame.setSize(GameView.WINDOW_WIDTH, GameView.WINDOW_HEIGHT);
        parentFrame.repaint();

        GameControl gameControl = new GameControl(selectedGame, gameView);
        gameControl.startGame();
    }

    private Player player;

}

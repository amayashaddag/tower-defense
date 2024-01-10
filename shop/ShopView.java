package shop;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.TimerTask;
import java.util.LinkedList;

import assets.*;
import menu.MenuView;
import model.*;
import view.*;

public class ShopView extends JPanel {

    private final static int PADDING = 16;
    private final static int DISPLAY_MESSAGE_DURATION = 3000;
    private final static int SLOT_WIDTH = GameView.IMAGE_WIDTH * 2, SLOT_HEIGHT = GameView.IMAGE_HEIGHT * 7 / 2;
    private final static int FONT_SIZE = 24;

    public class ShopSlot extends JPanel {
        private WeaponDisplay weaponDisplay;
        private Slot slot;
        private Button upgradeButton;
        private Button unlockButton;

        public ShopSlot(Slot slot) {

            this.weaponDisplay = new WeaponDisplay(slot.getIndex());
            this.slot = slot;

            boolean isUnlocked = player.isUnlocked(slot.getIndex());
            boolean isUpgradable = player.isUpgradable(slot.getIndex());

            int uprgadingCost = slot.getWeapon().getUpgradingCost();
            String upgradingCostText = String.valueOf(uprgadingCost);
            LabeledIcon upgradingCostLabel = new LabeledIcon(upgradingCostText, IconsGraphicsFactory.loadCoinIcon(),
                    Colors.SHOP_LABEL_COLOR);

            this.upgradeButton = Button.shopButton(Button.UPGRADE_BUTTON_LABEL);
            this.upgradeButton.setEnabled(isUpgradable && isUnlocked);
            this.upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Weapon weapon = slot.getWeapon();
                    int upgradingCost = weapon.getUpgradingCost();
                    if (!player.hasEnoughCredit(upgradingCost)) {
                        displayMessage(InterfaceMessages.NOT_ENOUGH_CREDIT);
                        ShopView.this.repaint();
                        return;
                    }
                    weapon.upgrade();
                    player.lostCredit(upgradingCost);
                    try {
                        player.savePlayerData();
                    } catch (Exception e) {
                        displayMessage(InterfaceMessages.SAVING_DATA_ERROR);
                        ShopView.this.repaint();
                    }

                    updatePlayerCredit();
                    if (!weapon.upgradable()) {
                        upgradeButton.setEnabled(false);
                    }
                    weaponDisplay.reloadImage(slot.getIndex());
                    repaint();
                }
            });
            int unlockingCost = slot.getWeapon().getUnlockingCost();
            String unlockingCostText = String.valueOf(unlockingCost);
            LabeledIcon unlockingCostLabel = new LabeledIcon(unlockingCostText, IconsGraphicsFactory.loadCoinIcon(),
                    Colors.SHOP_LABEL_COLOR);

            this.unlockButton = Button.shopButton(Button.UNLOCK_BUTTON_LABEL);
            this.unlockButton.setEnabled(!isUnlocked);
            this.unlockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Weapon weapon = slot.getWeapon();
                    int unlockingCost = weapon.getUnlockingCost();
                    if (!player.hasEnoughCredit(unlockingCost)) {
                        displayMessage(InterfaceMessages.NOT_ENOUGH_CREDIT);
                        ShopView.this.repaint();
                        return;
                    }
                    player.lostCredit(unlockingCost);
                    try {
                        player.savePlayerData();
                    } catch (Exception e) {
                        displayMessage(InterfaceMessages.SAVING_DATA_ERROR);
                        repaint();
                    }

                    slot.unlock();
                    updatePlayerCredit();
                    unlockButton.setEnabled(false);
                    upgradeButton.setEnabled(true);
                }
            });

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.add(weaponDisplay);
            this.add(Box.createRigidArea(new Dimension(0, PADDING)));
            this.add(upgradeButton);
            this.add(upgradingCostLabel);
            this.add(Box.createRigidArea(new Dimension(0, PADDING)));
            this.add(unlockButton);
            this.add(unlockingCostLabel);
            this.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
        }

        public WeaponDisplay getWeaponDisplay() {
            return weaponDisplay;
        }

        public Slot getSlot() {
            return slot;
        }
    }

    private Player player;
    private LabeledIcon playerCreditLabel;
    private List<ShopSlot> slots;
    private String interfaceMessage;

    public ShopView(Player player) {
        super();
        this.player = player;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.slots = new LinkedList<>();

        JPanel slotsPanel = new JPanel();

        int playerCredit = player.getCredit();
        String playerCreditText = String.valueOf(playerCredit);
        this.playerCreditLabel = new LabeledIcon(playerCreditText, IconsGraphicsFactory.loadCoinIcon(),
                Colors.SHOP_LABEL_COLOR);
        this.add(playerCreditLabel);

        for (Slot s : player.getItemsInventory()) {
            ShopSlot shopSlot = new ShopSlot(s);
            slots.add(shopSlot);
            slotsPanel.add(shopSlot);
        }
        for (Slot s : player.getTowersInventory()) {
            ShopSlot shopSlot = new ShopSlot(s);
            slots.add(shopSlot);
            slotsPanel.add(shopSlot);
        }
        slotsPanel.setSize(MenuView.WINDOW_WIDTH, SLOT_HEIGHT);
        ;
        this.add(slotsPanel);
        Button goToMenuButton = Button.largeMenuButton(Button.GO_TO_MENU_BUTTON_LABEL);
        this.add(goToMenuButton);
        goToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                MenuView menuView = new MenuView(player);
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ShopView.this);
                parentFrame.setContentPane(menuView);
                parentFrame.setSize(MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
                parentFrame.repaint();
                try {
                    player.savePlayerData();
                } catch (Exception e) {
                    displayMessage(InterfaceMessages.SAVING_DATA_ERROR);
                }
            }
        });
        this.setSize(MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
    }

    public List<ShopSlot> getSlots() {
        return slots;
    }

    private void updatePlayerCredit() {
        int playerCredit = player.getCredit();
        String playerCreditText = String.valueOf(playerCredit);
        this.playerCreditLabel.setText(playerCreditText);
    }

    public void displayMessage(String message) {
        this.interfaceMessage = message;
        java.util.Timer displayDuration = new java.util.Timer();
        displayDuration.schedule(new TimerTask() {
            @Override
            public void run() {
                interfaceMessage = null;
                ShopView.this.repaint();
            }
        }, DISPLAY_MESSAGE_DURATION);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(Fonts.sansSerifBoldFont(FONT_SIZE));
        if (interfaceMessage == null) {
            return;
        }
        FontMetrics fontMetrics = g.getFontMetrics();
        int x = (fontMetrics.stringWidth(interfaceMessage)) / 2;
        int y = getHeight() - (fontMetrics.getHeight() + fontMetrics.getAscent());
        g.setColor(Colors.SHOP_INTERFACE_MESSAGE_COLOR);
        g.drawString(this.interfaceMessage, x, y);
    }
}

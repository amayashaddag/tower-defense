package shop;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.LinkedList;

import assets.*;
import menu.MenuView;
import model.*;
import view.*;

public class ShopView extends JPanel {

    private final static int PADDING = 16;

    public class ShopSlot extends JPanel {
        private WeaponDisplay weaponDisplay;
        private Slot slot;
        private Button upgradeButton;
        private Button unlockButton;

        private final static int SLOT_WIDTH = GameView.IMAGE_WIDTH * 2, SLOT_HEIGHT = GameView.IMAGE_HEIGHT * 7 / 2;

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
                        // FIXME : Show not enough credit message
                        return;
                    }
                    weapon.upgrade();
                    player.lostCredit(upgradingCost);
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
                        // FIXME : Show not enough credit message
                        return;
                    }
                    player.lostCredit(unlockingCost);
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
        slotsPanel.setSize(MenuView.WINDOW_WIDTH, ShopSlot.SLOT_HEIGHT);
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
}

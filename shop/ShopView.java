package shop;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

        private final static int SLOT_WIDTH = GameView.IMAGE_WIDTH * 2, SLOT_HEIGHT = GameView.IMAGE_HEIGHT * 3;

        public ShopSlot(Slot slot) {
            this.weaponDisplay = new WeaponDisplay(slot.getIndex());
            this.slot = slot;

            boolean isUnlocked = player.isUnlocked(slot.getIndex());
            boolean isUpgradable = player.isUpgradable(slot.getIndex());

            this.upgradeButton = Button.shopButton(Button.UPGRADE_BUTTON_LABEL);
            this.upgradeButton.setEnabled(isUpgradable && isUnlocked);
            this.upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Weapon weapon = slot.getWeapon();
                    int upgradingCost = weapon.getUpgradingCost();
                    if (!player.hasEnoughCredit(upgradingCost)) {
                        //FIXME : Show not enough credit message
                        System.out.println("Not enough credit");
                        return;
                    }
                    weapon.upgrade();
                    player.lostCredit(upgradingCost);
                    if (!weapon.upgradable()) {
                        upgradeButton.setEnabled(false);
                    }
                    System.out.println("Upgraded !");
                }
            });

            this.unlockButton = Button.shopButton(Button.UNLOCK_BUTTON_LABEL);
            this.unlockButton.setEnabled(!isUnlocked);
            this.unlockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Weapon weapon = slot.getWeapon();
                    int unlockingCost = weapon.getUnlockingCost();
                    if (!player.hasEnoughCredit(unlockingCost)) {
                        // FIXME : Show not enough credit message
                        System.out.println("Not enough credit");
                        return;
                    }
                    player.lostCredit(unlockingCost);
                    slot.unlock();
                    unlockButton.setEnabled(false);
                    upgradeButton.setEnabled(true);
                    System.out.println("Unlocked !");
                }
            });

            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setAlignmentX(CENTER_ALIGNMENT);
            this.add(weaponDisplay);
            this.add(Box.createRigidArea(new Dimension(0, PADDING)));
            this.add(upgradeButton);
            this.add(Box.createRigidArea(new Dimension(0, PADDING)));
            this.add(unlockButton);
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
    private List<ShopSlot> slots;

    public ShopView(Player player) {
        super();
        this.player = player;
        this.slots = new LinkedList<>();
        for (Slot s : player.getItemsInventory()) {
            ShopSlot shopSlot = new ShopSlot(s);
            slots.add(shopSlot);
            this.add(shopSlot);
        }
        for (Slot s : player.getTowersInventory()) {
            ShopSlot shopSlot = new ShopSlot(s);
            slots.add(shopSlot);
            this.add(shopSlot);
        }
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
        this.setLayout(new FlowLayout());
        this.setSize(MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
    }

    public List<ShopSlot> getSlots() {
        return slots;
    }
}

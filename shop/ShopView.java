package shop;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.LinkedList;

import assets.*;
import model.*;
import view.*;

public class ShopView extends JFrame {

    private final static int PADDING = 16;

    public class ShopSlot extends JPanel {
        private WeaponDisplay weaponDisplay;
        private Slot slot;
        private Button upgradeButton;
        private Button unlockButton;

        private final static int SLOT_WIDTH = GameView.IMAGE_WIDTH * 2, SLOT_HEIGHT = GameView.IMAGE_HEIGHT * 2;

        public ShopSlot(Slot slot) {
            this.weaponDisplay = new WeaponDisplay(slot.getIndex());
            this.slot = slot;

            boolean isUnlocked = player.isUnlocked(slot.getIndex());
            boolean isUpgradable = player.isUpgradable(slot.getIndex());

            this.upgradeButton = new Button("UPGRADE");
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

            this.unlockButton = new Button("UNLOCK");
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
        this.setLayout(new FlowLayout());
        //FIXME : Enlever les valeurs numériques ici
        this.setSize(800, 600);
    }

    public List<ShopSlot> getSlots() {
        return slots;
    }
}

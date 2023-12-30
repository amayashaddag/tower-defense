package shopview;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.LinkedList;

import assets.*;
import model.*;
import view.*;

public class ShopView extends JFrame {

    public class ShopSlot extends JPanel {
        private Image image;
        private String index;
        private Button upgradeButton;
        private Button unlockButton;

        public ShopSlot(String index) {
            this.image = EntityGraphicsFactory.loadSlot(index, true);
            this.index = index;
            boolean isUnlocked = player.isUnlocked(index);

            this.upgradeButton = new Button("UPGRADE");
            this.upgradeButton.setEnabled(isUnlocked);
            this.upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO : Implement Upgrading method
                }
            });

            this.unlockButton = new Button("UNLOCK");
            this.unlockButton.setEnabled(!isUnlocked);
            this.unlockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO : Implement unlocking method
                }
            });

            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            this.add(upgradeButton);
            this.add(unlockButton);
        }

        public Image getImage() {
            return image;
        }

        public String getIndex() {
            return index;
        }

        @Override
        public void paintComponent(Graphics g) {
            
        }
    }

    private Player player;
    private List<ShopSlot> slots;

    public ShopView(Player player) {
        super();
        this.player = player;
        this.slots = new LinkedList<>();
        this.slots.add(new ShopSlot("B"));
        for (ShopSlot s : slots) {
            this.add(s);
        }
        this.setSize(800, 600);
    }

    public List<ShopSlot> getSlots() {
        return slots;
    }
}

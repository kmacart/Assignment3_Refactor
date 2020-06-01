package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The type Background image.
 */
public class BackgroundImage {
    private Image img;

    /**
     * B img j label.
     *
     * @return the j label
     */
    public JLabel bImg() {
        try {
            img = ImageIO.read(new File("images/blackjack_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JLabel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
            }
        };
    }

    /**
     * Sets img.
     *
     * @return the img
     */
    public Container setImg() {
        try {
            img = ImageIO.read(new File("images/blackjack_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Container() {
            public void paintComponents(Graphics g) {
                super.paintComponents(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
            }
        };
    }
}

package view;

import model.GameModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The type Start page.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    /**
     * Instantiates a new Game frame.
     *
     * @param model the model
     */
    public GameFrame(GameModel model) {

        // Set the close operation to exit when the window is closed. And set the title to be Blackjack
        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            // Set the background image on the panel.
            BackgroundPane background = new BackgroundPane();
            background.setBackground(ImageIO.read(new File("images/blackjack_background.png")));
            setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the layout to be BorderLayout
        setLayout(new BorderLayout());

        // Add all components to the frame.
        setJMenuBar(new GameMenu(model));
        this.add(new navToolbar(model), BorderLayout.PAGE_START);
        this.add(new PlayersPanel(model), BorderLayout.WEST);
        this.add(new GamePanel(model), BorderLayout.CENTER);
        this.add(new StatusBar(model), BorderLayout.PAGE_END);

        // Set the size and visibility.
        setSize(1150, 900);
        setVisible(true);
    }
}
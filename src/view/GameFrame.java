package view;

import model.GameModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * The type Start page.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements PropertyChangeListener {
    private GameModel model;

    /**
     * Instantiates a new Game frame.
     *
     * @param model the model
     */
    public GameFrame(GameModel model) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.model = model;

        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
try {
    BackgroundPane background = new BackgroundPane();
    background.setBackground(ImageIO.read(new File("images/blackjack_background.png")));
    setContentPane(background);
} catch (IOException e) {
    e.printStackTrace();
}

        setLayout(new BorderLayout());

        setJMenuBar(new GameMenu(model));
        this.add(new navToolbar(model), BorderLayout.PAGE_START);
        this.add(new PlayersPanel(model), BorderLayout.WEST);
        this.add(new GamePanel(model), BorderLayout.CENTER);
        this.add(new statusBar(model), BorderLayout.PAGE_END);

        model.getCallBack().addPropertyChangeListener(this);
        setSize(1150, 900);
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // resizes the window if necessary when we add new circle
        if(evt.getPropertyName() == GUICallback.NEW_PLAYER_ADDED) {
            validate();
        }

        if(evt.getPropertyName() == GUICallback.PLAYER_REMOVED) {
            validate();
        }

        if(evt.getPropertyName() == GUICallback.PLAYER_DEAL)
        {
            validate();
        }

        if(evt.getPropertyName() == GUICallback.HOUSE_BUST)
        {
            validate();
        }
    }
}
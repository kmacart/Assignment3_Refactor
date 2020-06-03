package view;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The type Start page.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements PropertyChangeListener {
    private GameModel model;

    public GameFrame(GameModel model) {
        this.model = model;

        setTitle("Blackjack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(new GameMenu(model));
        this.add(new PlayersPanel(model), BorderLayout.WEST);
        this.add(new GamePanel(model), BorderLayout.CENTER);
        model.getCallBack().addPropertyChangeListener(this);
        setSize(1150,900);
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
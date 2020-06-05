package view;

import model.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The type Status bar.
 */
public class statusBar extends JMenuBar implements PropertyChangeListener {
    private JLabel noPlayers;
    private GameModel gameModel;

    /**
     * Instantiates a new Status bar.
     *
     * @param model the model
     */

    // Create a new status bar with no players, blackjack and the creator's (me!) name.
    public statusBar(GameModel model) {
        gameModel = model;
        setLayout(new GridLayout(1, 3));

        JPanel noPlayersC = new JPanel();
        noPlayersC.setBackground(Color.GRAY);
        noPlayers = new JLabel("# of Players: " + model.getGameEngine().getAllPlayers().size());
        noPlayersC.setBorder(new LineBorder(Color.black, 1));
        noPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        noPlayers.setBorder(new EmptyBorder(5, 5, 5, 5));
        noPlayersC.add(noPlayers);
        add(noPlayersC);

        JPanel creatorC = new JPanel();
        creatorC.setBackground(Color.GRAY);
        creatorC.setBorder(new LineBorder(Color.black, 1));
        JLabel creator = new JLabel("@ Kira Macarthur");
        creator.setBorder(new EmptyBorder(5, 5, 5, 5));
        creator.setHorizontalAlignment(SwingConstants.CENTER);
        creatorC.add(creator);
        add(creatorC);

        JPanel timeC = new JPanel();
        timeC.setBackground(Color.GRAY);
        timeC.setBorder(new LineBorder(Color.black, 1));
        JLabel time = new JLabel("Blackjack");
        time.setBorder(new EmptyBorder(5, 5, 5, 5));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        timeC.add(time);
        add(timeC);

        model.getCallBack().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            noPlayers.setText("# of Players: " + gameModel.getGameEngine().getAllPlayers().size());
            noPlayers.repaint();
            noPlayers.revalidate();
        }

    }
}

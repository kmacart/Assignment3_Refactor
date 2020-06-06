package view;

import model.GameModel;
import model.Player;

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
    private JLabel status;
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

        JPanel statusC = new JPanel();
        statusC.setBackground(Color.GRAY);
        statusC.setBorder(new LineBorder(Color.black, 1));
        status = new JLabel("New Game Started");
        status.setBorder(new EmptyBorder(5, 5, 5, 5));
        status.setHorizontalAlignment(SwingConstants.CENTER);
        statusC.add(status);
        add(statusC);

        JPanel creatorC = new JPanel();
        creatorC.setBackground(Color.GRAY);
        creatorC.setBorder(new LineBorder(Color.black, 1));
        JLabel creator = new JLabel("@ Kira Macarthur");
        creator.setBorder(new EmptyBorder(5, 5, 5, 5));
        creator.setHorizontalAlignment(SwingConstants.CENTER);
        creatorC.add(creator);
        add(creatorC);

        model.getCallBack().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            noPlayers.setText("# of Players: " + gameModel.getGameEngine().getAllPlayers().size());
            noPlayers.repaint();
            noPlayers.revalidate();
        }
        if (evt.getPropertyName().equals(GUICallback.PLAYER_DEAL)) {
            Player player = (Player) evt.getNewValue();
            status.setText("Dealing to " + player.getName());

        }
        if (evt.getPropertyName().equals(GUICallback.CHANGE_PLAYER)) {
            String playerName = (String) evt.getNewValue();
            status.setText("Current Player: " + playerName);
        }

    }
}

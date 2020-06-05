package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The type Nav toolbar.
 */
public class navToolbar extends JPanel implements PropertyChangeListener {

    JButton addPlayer = new JButton("Add Player");
    JButton dealAll = new JButton("Deal All");
    JButton dealHouse = new JButton("Deal House");
    JButton newGame = new JButton("New Game");
    JComboBox speed = new JComboBox();
    JComboBox cb = new JComboBox();
    GameModel model;

    /**
     * Action performed.
     *
     * @param e the e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dealAll) {
            if (model.getGameEngine().getAllPlayers().isEmpty()) {
                showMessageDialog(JOptionPane.getRootFrame(), "At least one player must exist and have a bet before dealing the house.", "No Players.", JOptionPane.ERROR_MESSAGE);
            } else {
                int no_bet = 0;
                for (Player player : model.getGameEngine().getAllPlayers()) {
                    if (player.getBet() != Bet.NO_BET && player.getHand().getNumberOfCards() == 0) {
                        model.dealPlayer(player);
                    } else if (player.getBet() == Bet.NO_BET) {
                        no_bet++;
                    }
                }
                if (no_bet == model.getGameEngine().getAllPlayers().size()) {
                    showMessageDialog(JOptionPane.getRootFrame(), "At least one player must place a bet before dealing to the house.", "No Bets", JOptionPane.ERROR_MESSAGE);
                } else {
                    processDealHouse();
                }
            }
        } else if (e.getSource() == dealHouse) {
            processDealHouse();
        } else if (e.getSource() == cb) {
            model.changePlayer(cb.getSelectedItem().toString());
        } else if (e.getSource() == newGame) {
            model.newGame();
        } else if (e.getSource() == speed) {
            if (speed.getSelectedIndex() == 0) {
                model.setDelay(1500);
            } else if (speed.getSelectedIndex() == 1) {
                model.setDelay(500);
            } else {
                model.setDelay(150);
            }
        }
    }

    private void processDealHouse() {
        boolean outstandingBets = false;

        if (model.getGameEngine().getAllPlayers().isEmpty()) {
            showMessageDialog(JOptionPane.getRootFrame(), "At least one player must exist and have a bet before dealing the house.", "No Players.", JOptionPane.ERROR_MESSAGE);
        } else {
            int num_no_bet = 0;
            int num_players = 0;
            for (Player player: model.getGameEngine().getAllPlayers()) {
                num_players++;
                if (player.getBet() == Bet.NO_BET) {
                    num_no_bet++;
                }
                if (player.getBet() != Bet.NO_BET && player.getHand().getNumberOfCards() == 0) {
                    showMessageDialog(JOptionPane.getRootFrame(), "The house cannot be dealt until all players with bets have been dealt.", "Outstanding Player Bets.",JOptionPane.ERROR_MESSAGE);
                    outstandingBets = true;
                }
            }
            if (num_no_bet == num_players) {
                showMessageDialog(JOptionPane.getRootFrame(), "At least one player must place a bet before dealing the house.", "No Player Bets.", JOptionPane.ERROR_MESSAGE);
            } else if (!outstandingBets) {
                model.dealHouse();
            }
        }
    }

    /**
     * Instantiates a new Nav toolbar.
     *
     * @param ge the ge
     */
    public navToolbar(GameModel ge) {
        speed.addItem("Slow Deal Speed");
        speed.addItem("Medium Deal Speed");
        speed.addItem("Fast Deal Speed");

        speed.addActionListener(this::actionPerformed);

        addPlayer.addActionListener(e -> new AddPlayerDialog(model));
        add(addPlayer);

        this.setBackground(Color.DARK_GRAY);
        model = ge;
        newGame.addActionListener(this::actionPerformed);
        add(newGame);

        dealAll.addActionListener(this::actionPerformed);
        add(dealAll);

        dealHouse.addActionListener(this::actionPerformed);
        add(dealHouse);

        add(speed);

        cb.addItem("House");
        cb.setEditable(false);
        cb.addActionListener(this::actionPerformed);
        add(cb);
        model.getCallBack().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player player = (Player) evt.getNewValue();
            cb.addItem(player.getName());
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_REMOVED)) {
            cb.removeAllItems();
            cb.addItem("House");
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            for (Player player : players) {
                cb.addItem(player.getName());
            }
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL)) {
            Player player = (Player) evt.getNewValue();
            if (cb.getSelectedItem() != player.getName()) {
                cb.setSelectedItem(player.getName());
            }
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL)) {
            if (cb.getSelectedItem() != "House") {
                cb.setSelectedItem("House");
            }
            validate();
        }
    }
}

package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.showMessageDialog;

public class navToolbar extends JPanel {
    JButton dealAll = new JButton("Deal All");
    JButton dealHouse = new JButton("Deal House");
    JComboBox cb = new JComboBox();
    GameModel model;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dealAll) {
            for (Player player : model.getGameEngine().getAllPlayers()) {
                model.dealPlayer(player, model.getDelay());
            }

            model.dealHouse(model.getDelay());
        }
        else if (e.getSource() == dealHouse) {
            processDealHouse();
        }
    }

    private void processDealHouse()
    {
        Boolean outstandingBets = false;

        if (model.getGameEngine().getAllPlayers().isEmpty()) {
            showMessageDialog(JOptionPane.getRootFrame(), "At least one player must place a bet before dealing the house.", "No Player Bets.",JOptionPane.ERROR_MESSAGE);
        }
        else {
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
                showMessageDialog(JOptionPane.getRootFrame(), "At least one player must place a bet before dealing the house.", "No Player Bets.",JOptionPane.ERROR_MESSAGE);
            }
            else if (!outstandingBets) {
                model.dealHouse(1000);
            }
        }
    }

    public navToolbar(GameModel ge, JPanel parent) {
        model = ge;
        dealAll.addActionListener(this::actionPerformed);
        add(dealAll);

        dealHouse.addActionListener(this::actionPerformed);
        add(dealHouse);

        cb.addItem("House");
        cb.setEditable(false);

        add(cb);
    }
}

package model;

import model.card.Card;
import model.card.Deck;
import model.card.Hand;
import view.GameCallback;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 * The type Gui callback.
 */
public class GUICallback implements GameCallback {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    public static final String NEW_PLAYER_ADDED = "New Player Added";
    public static final String PLAYER_REMOVED = "Player Removed";
    public static final String PLAYER_DEAL = "Player Dealt Card";
    public static final String HOUSE_DEAL = "House Dealt Card";

    @Override
    public void addPlayer(Player player) {
        this.pcs.firePropertyChange(NEW_PLAYER_ADDED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been added to the game.", "Player Added",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void removePlayer(Player player) {
        this.pcs.firePropertyChange(PLAYER_REMOVED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been removed from the game.", "Player Removed",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void betUpdated(Player player) {
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has bet updated.", "Player Bet Updated   ",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void newDeck(Deck deck) {
        showMessageDialog(JOptionPane.getRootFrame(), "A new deck has been created", "Deck created",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void playerCard(Player player, Card card) {
        String path = "/images/DeckOfCards/" + card.getValue() + "_of_" + card.getSuit() + ".png";
        this.pcs.firePropertyChange(PLAYER_DEAL, null, player);
    }

    @Override
    public void playerBust(Player player, Card card) {

    }

    @Override
    public void houseCard(Hand houseHand, Card card) {
        String path = "/images/DeckOfCards/" + card.getValue() + "_of_" + card.getSuit() + ".png";
        this.pcs.firePropertyChange(HOUSE_DEAL, null, houseHand);
    }

    @Override
    public void houseBust(Hand houseHand, Card card) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
}

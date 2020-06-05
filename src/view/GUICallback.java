package view;

import model.Player;
import model.card.Card;
import model.card.Deck;
import model.card.Hand;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 * The type Gui callback.
 */
public class GUICallback implements GameCallback {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    /**
     * The constant NEW_PLAYER_ADDED.
     */
    public static final String NEW_PLAYER_ADDED = "New Player Added";
    /**
     * The constant PLAYER_REMOVED.
     */
    public static final String PLAYER_REMOVED = "Player Removed";
    /**
     * The constant PLAYER_DEAL.
     */
    public static final String PLAYER_DEAL = "Player Dealt Card";
    /**
     * The constant HOUSE_DEAL.
     */
    public static final String HOUSE_DEAL = "House Dealt Card";
    /**
     * The constant PLAYER_BUST.
     */
    public static final String PLAYER_BUST = "Player Bust";
    /**
     * The constant HOUSE_BUST.
     */
    public static final String HOUSE_BUST = "House Bust";
    /**
     * The constant BET_UPDATED.
     */
    public static final String BET_UPDATED = "Bet Updated";
    /**
     * The constant CHANGE_PLAYER.
     */
    public static final String CHANGE_PLAYER = "Change Player";
    /**
     * The constant NEW_DECK.
     */
    public static final String NEW_DECK = "New Deck";
    /**
     * The constant NEW_GAME.
     */
    public static final String NEW_GAME = "New Game";

    @Override
    public void addPlayer(Player player) {
        this.pcs.firePropertyChange(NEW_PLAYER_ADDED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been added to the game.", "Player Added", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void removePlayer(Player player) {
        this.pcs.firePropertyChange(PLAYER_REMOVED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been removed from the game.", "Player Removed",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void betUpdated(Player player) {
        this.pcs.firePropertyChange(BET_UPDATED, null, player);
    }

    @Override
    public void newDeck(Deck deck) {
        this.pcs.firePropertyChange(NEW_DECK, null, null);
    }

    /**
     * New game.
     */
    public void newGame() {
        this.pcs.firePropertyChange(NEW_GAME, null, null);
    }

    @Override
    public void playerCard(Player player, Card card) {
        String path = "/images/DeckOfCards/" + card.getValue() + "_of_" + card.getSuit() + ".png";
        this.pcs.firePropertyChange(PLAYER_DEAL, null, player);
    }

    @Override
    public void playerBust(Player player, Card card) {
        this.pcs.firePropertyChange(PLAYER_BUST, null, player);
    }

    @Override
    public void houseCard(Hand houseHand, Card card) {
        String path = "/images/DeckOfCards/" + card.getValue() + "_of_" + card.getSuit() + ".png";
        this.pcs.firePropertyChange(HOUSE_DEAL, null, houseHand);
    }

    /**
     * Change player.
     *
     * @param player the player
     */
    public void changePlayer(String player) {
        this.pcs.firePropertyChange(CHANGE_PLAYER, null, player);
    }

    @Override
    public void houseBust(Hand houseHand, Card card) {
        this.pcs.firePropertyChange(HOUSE_BUST, null, houseHand);
    }

    /**
     * Add property change listener.
     *
     * @param listener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove property change listener.
     *
     * @param listener the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
}

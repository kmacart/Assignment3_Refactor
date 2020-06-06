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
        // When a player has been added, fire off the NEW_PLAYER_ADDED property change
        this.pcs.firePropertyChange(NEW_PLAYER_ADDED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been added to the game.", "Player Added", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void removePlayer(Player player) {
        // When a player has been removed, fire off the PLAYER_REMOVED property change
        this.pcs.firePropertyChange(PLAYER_REMOVED, null, player);
        showMessageDialog(JOptionPane.getRootFrame(), player.getName() + " has been removed from the game.", "Player Removed",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void betUpdated(Player player) {
        // When a bet has been updated, fire off the BET_UPDATED property change.
        this.pcs.firePropertyChange(BET_UPDATED, null, player);
    }

    @Override
    public void newDeck(Deck deck) {
        // When a new deck has been created, fire off the NEW_DECK property change.
        this.pcs.firePropertyChange(NEW_DECK, null, null);
    }

    /**
     * New game.
     */
    public void newGame() {
        // When a new game has been created, fire off the NEW_GAME property change.
        this.pcs.firePropertyChange(NEW_GAME, null, null);
    }

    @Override
    public void playerCard(Player player, Card card) {
        // When a card has been dealt to a player, fire off the PLAYER_DEAL property change.
        this.pcs.firePropertyChange(PLAYER_DEAL, card, player);
    }

    @Override
    public void playerBust(Player player, Card card) {
        // When a player has busted, fire off the PLAYER_BUST property change.
        this.pcs.firePropertyChange(PLAYER_BUST, null, player);
    }

    @Override
    public void houseCard(Hand houseHand, Card card) {
        // When a house has been dealt a card, fire off the HOUSE_DEAL property change.
        this.pcs.firePropertyChange(HOUSE_DEAL, null, houseHand);
    }

    /**
     * Change player.
     *
     * @param player the player
     */
    public void changePlayer(String player) {
        // When the player has been changed, fire off the CHANGE_PLAYER property change.
        this.pcs.firePropertyChange(CHANGE_PLAYER, null, player);
    }

    @Override
    public void houseBust(Hand houseHand, Card card) {
        // When the house busts, fire off the HOUSE_BUST property change.
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
}

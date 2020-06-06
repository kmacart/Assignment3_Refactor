package view;

import model.GameModel;
import model.Player;
import model.card.Card;
import model.card.Hand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;

import static java.awt.BorderLayout.PAGE_END;
import static javax.swing.SwingConstants.CENTER;

/**
 * The type Game panel.
 */
public class GamePanel extends JPanel implements PropertyChangeListener {
    private GameModel model;
    /**
     * The P cards.
     */
    JPanel pCards = new JPanel(new GridLayout(1, 0));
    private Player currentPlayer;
    private Hand houseHand;
    /**
     * The Container.
     */
    JPanel container = new JPanel(new BorderLayout());
    /**
     * The Players panel.
     */
    JPanel playersPanel = new JPanel(new BorderLayout());
    /**
     * The Player score.
     */
    JLabel playerScore = new JLabel();
    /**
     * The Results panel.
     */
    JPanel resultsPanel;

    /**
     * Instantiates a new Game panel.
     *
     * @param model the model
     */
    public GamePanel(GameModel model) {

        // Set the class-wide variable
        this.model = model;

        // Stylise the frame.
        setSize(1150, 900);
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(50, 50, 50, 50));

        // Create a new player card layout and add the player panel to the container.
        createPlayersCardLayout();
        container.add(playersPanel, BorderLayout.CENTER);

        // Create a new results panel, stylise it, and add it to the frame.
        resultsPanel = new ResultsPanel(model);
        resultsPanel.setVisible(false);
        container.add(resultsPanel, PAGE_END);
        add(container, BorderLayout.NORTH);
        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Stylise the playerScore and add it to the frame.
        playerScore.setFont(new Font("Monaco", Font.BOLD, 20));
        playerScore.setBorder(new EmptyBorder(10, 10, 10, 10));
        playerScore.setHorizontalAlignment(CENTER);
        resultsPanel.setBackground(Color.GRAY);
        container.setBackground(Color.GRAY);
        container.add(playerScore, BorderLayout.PAGE_START);

        // Add a ProperChangeListener.
        model.getCallBack().addPropertyChangeListener(this);
    }

    // Create a new Card Layout and add it to the playerPanel.
    private void createPlayersCardLayout() {
        playersPanel.add(pCards, BorderLayout.CENTER);
        playerScore.setText("Player Score: 0");
    }

    // Set the current player via it's playerName.
    private void setCurrentPlayer(String playerName) {
        currentPlayer = null;
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        Iterator<Player> playersIter = players.iterator();

        // Find the player via it's playerName and set the class-wide variable.
        while (currentPlayer == null && playersIter.hasNext()) {
            Player player = playersIter.next();
            if (player.getName().equals(playerName)) {
                currentPlayer = player;
            }
        }

        // If there's a current player and the player's been dealt to, update the cards and update the score.
        if (currentPlayer != null && currentPlayer.getHand().getNumberOfCards() > 0) {
            updateCards(currentPlayer.getHand().getCards(), pCards);
            playerScore.setText("Current Score: ".concat(Integer.toString(currentPlayer.getHand().getScore())));
        } else {
            pCards.removeAll();
            playerScore.setText("Current Score: 0");
        }

    }

    // Update the cards.
    private void updateCards(Collection<Card> cards, JPanel panel) {

        // Remove all cards from the panel.
        panel.removeAll();

        // For each card, get the image, scale it and add it to the screen.
        for (Card currentCard : cards) {
            addCard(currentCard, pCards);
        }
        panel.setBackground(Color.GRAY);
    }

    private void addCard(Card currentCard, JPanel panel) {
        String cardValue = currentCard.getRank().toString();
        String cardSuit = currentCard.getSuit().toString();

        Image cardImg = new ImageIcon("images/DeckOfCards/" + cardValue + "_of_" + cardSuit + ".png").getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
        ImageIcon cardIcon = new ImageIcon(cardImg);
        JLabel card = new JLabel();
        card.setIcon(cardIcon);
        card.setBackground(Color.GRAY);
        panel.add(card);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // If a new player was added, set the current player to that.
        if(evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player player = (Player) evt.getNewValue();
            if (currentPlayer == null) {
                currentPlayer = player;
            }
        }

        // If a player has been dealt to, update the cards and the score.
        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL)) {
            Player player = (Player) evt.getNewValue();
            updateCards(player.getHand().getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(player.getHand().getScore())));
            validate();
        }

        // If the house is being dealt to, update the house's cards and score.
        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL)) {
            houseHand = (Hand) evt.getNewValue();
            updateCards(houseHand.getCards(), pCards);
            playerScore.setText("House Score: ".concat(Integer.toString(houseHand.getScore())));
            validate();
        }

        // If the house busts, show the resultsPanel.
        if(evt.getPropertyName().equals(GUICallback.HOUSE_BUST )) {
            resultsPanel.setVisible(true);
            validate();
        }

        // If the current player has been changed
        if(evt.getPropertyName().equals(GUICallback.CHANGE_PLAYER)) {
            String playerName = (String) evt.getNewValue();
            // If the player is the house, and the house's hand isn't null and the hand has more than 0 cards, update the frame to display the house's cards and score.
            if (playerName.equals("House")) {
                if (houseHand != null && houseHand.getNumberOfCards() > 0) {
                    updateCards(houseHand.getCards(), pCards);
                    playerScore.setText("Player Score: ".concat(Integer.toString(houseHand.getScore())));
                    // Otherwise, reset the frame.
                } else {
                    pCards.removeAll();
                    playerScore.setText("Player Score: 0");
                }
                // Otherwise, set the current player to be the inputted player.
            } else {
                setCurrentPlayer(playerName);
            }
            validate();
        }

        // If a new game has been created, reset the frame.
        if(evt.getPropertyName().equals(GUICallback.NEW_GAME)) {
            pCards.removeAll();
            playerScore.setText("Player Score: 0");
            resultsPanel.removeAll();
            validate();
        }
    }

}
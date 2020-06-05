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
        this.model = model;
        setSize(1150, 900);

        setLayout(new BorderLayout());

        createPlayersCardLayout();
        container.add(playersPanel, BorderLayout.CENTER);
        resultsPanel = new ResultsPanel(model);
        setOpaque(false);
        setBorder(new EmptyBorder(50, 50, 50, 50));
        resultsPanel.setVisible(false);
        container.add(resultsPanel, PAGE_END);
        add(container, BorderLayout.NORTH);
        container.setBorder(new EmptyBorder(10, 10, 10, 10));

        playerScore.setFont(new Font("Monaco", Font.BOLD, 20));
        playerScore.setBorder(new EmptyBorder(10, 10, 10, 10));
        playerScore.setHorizontalAlignment(CENTER);
        resultsPanel.setBackground(Color.GRAY);
        container.setBackground(Color.GRAY);
        container.add(playerScore, BorderLayout.PAGE_START);

        model.getCallBack().addPropertyChangeListener(this);
    }

    private void createPlayersCardLayout() {
        playersPanel.add(pCards, BorderLayout.CENTER);
        playerScore.setText("Player Score: 0");
    }

    private void setCurrentPlayer(String playerName) {
        currentPlayer = null;
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        Iterator<Player> playersIter = players.iterator();
        while (currentPlayer == null && playersIter.hasNext()) {
            Player player = playersIter.next();
            if (player.getName().equals(playerName)) {
                currentPlayer = player;
            }
        }
        if (currentPlayer != null && currentPlayer.getHand().getNumberOfCards() > 0) {
            updateCards(currentPlayer.getHand().getCards(), pCards);
            playerScore.setText("Current Score: ".concat(Integer.toString(currentPlayer.getHand().getScore())));
        }

    }

    private void updateCards(Collection<Card> cards, JPanel panel) {

        panel.removeAll();

        for (Card currentCard: cards) {
            String cardValue = currentCard.getRank().toString();
            String cardSuit = currentCard.getSuit().toString();

            Image cardImg = new ImageIcon("images/DeckOfCards/" + cardValue + "_of_" + cardSuit + ".png").getImage();
            Image temp = cardImg.getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            ImageIcon cardIcon = new ImageIcon(temp);
            JLabel card = new JLabel();
            card.setIcon(cardIcon);
            panel.add(card);

            panel.setBackground(Color.GRAY);
            card.setBackground(Color.GRAY);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player player = (Player) evt.getNewValue();
            if (currentPlayer == null) {
                currentPlayer = player;
            }
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL)) {
            Player player = (Player) evt.getNewValue();
            updateCards(player.getHand().getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(currentPlayer.getHand().getScore())));
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL)) {
            houseHand = (Hand) evt.getNewValue();
            updateCards(houseHand.getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(houseHand.getScore())));
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_BUST )) {
            resultsPanel.setVisible(true);
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.CHANGE_PLAYER)) {
            String playerName = (String) evt.getNewValue();
            if (playerName == "House") {
                if (houseHand != null && houseHand.getNumberOfCards() > 0) {
                    updateCards(houseHand.getCards(), pCards);
                    playerScore.setText("Player Score: ".concat(Integer.toString(houseHand.getScore())));
                } else {
                    pCards.removeAll();
                    playerScore.setText("Player Score: 0");
                }
            } else {
                setCurrentPlayer(playerName);
            }
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.NEW_GAME)) {
            pCards.removeAll();
            playerScore.setText("Player Score: 0");
            resultsPanel = new ResultsPanel(model);
            validate();
        }
    }

}
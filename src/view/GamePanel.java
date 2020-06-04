package view;

import model.GameModel;
import model.Player;
import model.card.Card;
import model.card.Hand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;

import static java.awt.BorderLayout.PAGE_END;

public class GamePanel extends JPanel implements PropertyChangeListener {
    private GameModel model;
    JPanel pCards = new JPanel(new GridLayout(1, 0));
    private Player currentPlayer;
    private Hand houseHand;
    JPanel container = new JPanel(new BorderLayout());
    JPanel playersPanel = new JPanel(new BorderLayout());
    JLabel playerScore = new JLabel();
    JPanel resultsPanel;

    public GamePanel(GameModel model) {
        this.model = model;
        setSize(1150, 900);
        createPlayersCardLayout();
        container.add(playersPanel,BorderLayout.PAGE_START);
        resultsPanel = new ResultsPanel(model);
        resultsPanel.setVisible(false);
        container.add(resultsPanel, PAGE_END);
        add(container);

        model.getCallBack().addPropertyChangeListener(this);
    }

    private void createPlayersCardLayout() {
        playersPanel.add(pCards, BorderLayout.CENTER);
        playerScore.setText("Player Score: 0");
        playersPanel.add(playerScore, PAGE_END);
    }

    private void setCurrentPlayer(String playerName)
    {
        currentPlayer = null;
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        Iterator<Player> playersIter = players.iterator();
        while (currentPlayer == null && playersIter.hasNext())
        {
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

        for (Card currentCard: cards)
        {
            String cardValue = currentCard.getRank().toString();
            String cardSuit = currentCard.getSuit().toString();

            Image cardImg = new ImageIcon("images/DeckOfCards/" + cardValue + "_of_" + cardSuit + ".png").getImage();
            Image temp = cardImg.getScaledInstance(120,160,Image.SCALE_SMOOTH);
            ImageIcon cardIcon = new ImageIcon(temp);
            JLabel card = new JLabel();
            JLabel cardContainer = new JLabel();
            cardContainer.setBorder(new EmptyBorder(5,5,5,5));
            card.setBorder(new LineBorder(Color.black,1));
            card.setIcon(cardIcon);
            panel.add(card);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player player = (Player) evt.getNewValue();
            if (currentPlayer == null) {
                currentPlayer = player;
            }
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL))
        {
            Player player = (Player) evt.getNewValue();
            updateCards(player.getHand().getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(currentPlayer.getHand().getScore())));
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL))
        {
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
                }
                else {
                    pCards.removeAll();
                    playerScore.setText("Player Score: 0");
                }
            }
            else {
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

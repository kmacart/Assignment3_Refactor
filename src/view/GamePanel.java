package view;

import model.GUICallback;
import model.GameModel;
import model.Player;
import model.bet.Bet;
import model.card.Card;
import model.card.Hand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;

import static java.awt.BorderLayout.PAGE_END;
import static javax.swing.JOptionPane.showMessageDialog;

public class GamePanel extends JPanel implements PropertyChangeListener, ActionListener {
    private GameModel model;
    JPanel pCards = new JPanel(new GridLayout(1, 0));
    private Player currentPlayer;
    private Hand houseHand;
    JPanel container = new JPanel();
    JPanel playersPanel = new JPanel();
    JPanel cards = new JPanel(new CardLayout());
    JPanel comboBoxPane = new JPanel(); //use FlowLayout
    JComboBox cb = new JComboBox();
    JPanel playersContainer = new JPanel();
    JLabel playerScore = new JLabel();
    JLabel houseScore = new JLabel();

    public GamePanel(GameModel model) {
        this.model = model;
        setSize(1150, 900);

        playersContainer.setLayout(new BoxLayout(playersContainer, BoxLayout.PAGE_AXIS));
        createPlayersCardLayout();

        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(playersContainer,PAGE_END);
        container.add(new ResultsPanel(model), PAGE_END);
        add(container);

        model.getCallBack().addPropertyChangeListener(this);

    }

    private void createPlayersCardLayout() {
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));

        cb.addItem("House");
        cb.setEditable(false);
        cb.addActionListener(this);

        comboBoxPane.add(cb);
        cards.setVisible(true);
        playersPanel.add(comboBoxPane, BorderLayout.PAGE_START);

        JButton deal = new JButton("Deal House");

        deal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean outstandingBets = false;

                for (Player player: model.getGameEngine().getAllPlayers()) {
                    if (player.getBet() != Bet.NO_BET && player.getHand().getNumberOfCards() == 0)
                    {
                        showMessageDialog(JOptionPane.getRootFrame(), "The house cannot be dealt until all players with bets have been dealt.", "Outstanding Player Bets.",JOptionPane.ERROR_MESSAGE);
                        outstandingBets = true;
                    }
                }
                if (!outstandingBets) {
                    model.dealHouse(1000);
                }
            }
        });
        playersPanel.add(deal, PAGE_END);
        playersPanel.add(pCards, BorderLayout.CENTER);
        playerScore.setText("Player Score: 0");
        playersPanel.add(playerScore, PAGE_END);
        playersContainer.add(playersPanel);
    }

    public void actionPerformed(ActionEvent e) {
        currentPlayer = null;
        pCards.removeAll();
        playerScore.setText("Current Score: 0");
        if (cb.getSelectedItem() == "House" && houseHand != null) {
            updateCards(houseHand.getCards(), pCards);
            playerScore.setText("Current Score: ".concat(Integer.toString(houseHand.getScore())));
        }
        else {
            setCurrentPlayer();
        }
        validate();
    }

    private void setCurrentPlayer()
    {
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        Iterator<Player> playersIter = players.iterator();
        while (currentPlayer == null && playersIter.hasNext())
        {
            Player player = playersIter.next();
            if (player.getName().equals(cb.getSelectedItem())) {
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
            Image temp = cardImg.getScaledInstance(90,130,Image.SCALE_SMOOTH);
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
        if(evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED))
        {
            Player player = (Player) evt.getNewValue();
            cb.addItem(player.getName());
            if (currentPlayer == null) {
                currentPlayer = player;
                cb.setSelectedItem(player.getName());
            }
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_REMOVED))
        {
        cb.removeAllItems();
        cb.addItem("House");
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        for (Player player : players)
        {
            cb.addItem(player.getName());
        }
        validate();
    }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL))
        {
            Player player = (Player) evt.getNewValue();
            if (cb.getSelectedItem() != player.getName()) {
                cb.setSelectedItem(player.getName());
            }
            updateCards(player.getHand().getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(currentPlayer.getHand().getScore())));
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL))
        {
            if (cb.getSelectedItem() != "House") {
                cb.setSelectedItem("House");
            }
            houseHand = (Hand) evt.getNewValue();
            updateCards(houseHand.getCards(), pCards);
            playerScore.setText("Player Score: ".concat(Integer.toString(houseHand.getScore())));
            validate();
        }
    }

}

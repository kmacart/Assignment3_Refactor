package view;

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
    JPanel container = new JPanel(new BorderLayout());
    JPanel playersPanel = new JPanel(new BorderLayout());
    JComboBox cb = new JComboBox();
    JLabel playerScore = new JLabel();
    JLabel houseScore = new JLabel();
    JButton dealAll = new JButton("Deal All");
    JButton dealHouse = new JButton("Deal House");
    JPanel toolbar = new JPanel();

    public GamePanel(GameModel model) {
        this.model = model;
        setSize(1150, 900);

//        playersContainer.setLayout(new BoxLayout(playersContainer, BoxLayout.PAGE_AXIS));
        createPlayersCardLayout();

//        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(playersPanel,BorderLayout.PAGE_START);
        container.add(new ResultsPanel(model), PAGE_END);

        add(container);

        model.getCallBack().addPropertyChangeListener(this);

    }

    private void createPlayersCardLayout() {
//        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
        toolbar.add(dealAll);
        toolbar.add(dealHouse);

        cb.addItem("House");
        cb.setEditable(false);
        cb.addActionListener(this);

        toolbar.add(cb);
        toolbar.setBackground(Color.darkGray);
        playersPanel.add(toolbar, BorderLayout.PAGE_START);

        dealAll.addActionListener(this);
        dealHouse.addActionListener(this);

        playersPanel.add(pCards, BorderLayout.CENTER);
        playerScore.setText("Player Score: 0");
        playersPanel.add(playerScore, PAGE_END);
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
        else {
            currentPlayer = null;
            pCards.removeAll();
            playerScore.setText("Current Score: 0");
            if (cb.getSelectedItem() == "House" && houseHand != null) {
                updateCards(houseHand.getCards(), pCards);
                playerScore.setText("Current Score: ".concat(Integer.toString(houseHand.getScore())));
            } else {
                setCurrentPlayer();
            }
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

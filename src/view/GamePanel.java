package view;

import model.GUICallback;
import model.GameModel;
import model.Player;
import model.card.Card;
import model.card.Hand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;

import static java.awt.BorderLayout.PAGE_END;
import static java.awt.BorderLayout.PAGE_START;

public class GamePanel extends JPanel implements PropertyChangeListener, ItemListener {
    private GameModel model;
    private GridBagConstraints c = new GridBagConstraints();
    JPanel hCards = new JPanel(new GridLayout(1, 0));
    JPanel pCards;
    private Player currentPlayer;
    JPanel container = new JPanel(new BorderLayout());
    JPanel playersPanel = new JPanel();
    JPanel cards = new JPanel(new CardLayout());
    JPanel comboBoxPane = new JPanel(); //use FlowLayout
    JComboBox cb = new JComboBox();

    public GamePanel(GameModel model) {
        this.model = model;
        JPanel houseContainer = new JPanel();
        houseContainer.setLayout(new BoxLayout(houseContainer, BoxLayout.PAGE_AXIS));
        createPlayersCardLayout();
        setSize(1150, 900);

        JButton house = new JButton("House");
        house.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.dealHouse(500);
            }
        });

        houseContainer.add(house);
        houseContainer.add(hCards);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 5;

        container.add(houseContainer,PAGE_START);
        container.add(this.playersPanel,PAGE_END);

        hCards.setVisible(true);

        add(container);

        model.getCallBack().addPropertyChangeListener(this);

    }

    //TODO Separate this out into components so it works better.

    private void createPlayersCardLayout() {
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        cards.setVisible(true);
        playersPanel.add(comboBoxPane, BorderLayout.PAGE_START);
        playersPanel.add(cards, BorderLayout.CENTER);
        JButton deal = new JButton("Deal Card");

        deal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.dealPlayer(currentPlayer, 1000);
            }
        });
        playersPanel.add(deal, PAGE_END);
    }

    private void addPlayerCard(Player player)
    {
        JPanel card = new JPanel();
        pCards  = new JPanel(new GridLayout(1, 0));
        card.add(pCards);
        cards.add(card, player.getName());
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
        currentPlayer = null;
        pCards.removeAll();
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        Iterator<Player> playersIter = players.iterator();
        while (currentPlayer == null && playersIter.hasNext())
        {
            Player player = playersIter.next();
            if (player.getName().equals((String)evt.getItem())) {
                currentPlayer = player;
            }
        }
        if (currentPlayer != null && currentPlayer.getHand().getNumberOfCards() > 0) {
            updateCards(currentPlayer.getHand().getCards(), pCards);
        }
        validate();
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
            addPlayerCard(player);
            cb.addItem(player.getName());
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_REMOVED))
        {
//            playersDropDown.removeAllItems();
//            Collection<Player> players = model.getGameEngine().getAllPlayers();
//            for (Player player : players)
//            {
//                playersDropDown.addItem(player.getName());
//            }
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.PLAYER_DEAL))
        {
            Player player = (Player) evt.getNewValue();
            updateCards(player.getHand().getCards(), pCards);
            validate();
        }

        if(evt.getPropertyName().equals(GUICallback.HOUSE_DEAL))
        {
            Hand houseHand = (Hand) evt.getNewValue();
            updateCards(houseHand.getCards(), hCards);
            validate();
        }
    }

}

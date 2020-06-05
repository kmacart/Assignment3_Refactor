package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import static java.awt.BorderLayout.PAGE_START;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The type Players panel.
 */
public class PlayersPanel extends JPanel implements PropertyChangeListener {
    private GameModel model;

    /**
     * Instantiates a new Players panel.
     *
     * @param model the model
     */
    public PlayersPanel(GameModel model) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.model = model;
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        for (Player player : players) {
            addPlayer(player);
        }
        setBackground(Color.lightGray);
        model.getCallBack().addPropertyChangeListener(this);
        setOpaque(false);
    }

    /**
     * Add player.
     *
     * @param player the player
     */
    public void addPlayer(Player player) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JPanel pPanelBorder = new JPanel();
        pPanelBorder.setBorder(new LineBorder(Color.black, 5));

        JPanel playerPanel = new JPanel(new BorderLayout()); // The main panel.
        playerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel pName = new JLabel(player.getName());
        pName.setFont(new Font("Monaco",Font.BOLD,16));
        pName.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel playerContainer = new JPanel(new GridLayout(1,2));
        JPanel rightHandInfo = new JPanel(new GridLayout(4,1));

        playerPanel.add(pName,PAGE_START);

        if (player.getId().endsWith("1")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-1.png");
            playerContainer.add(new JLabel(pIcon));
        } else if (player.getId().endsWith("2")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-2.png");
            playerContainer.add(new JLabel(pIcon));
        } else if (player.getId().endsWith("3")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-3.png");
            playerContainer.add(new JLabel(pIcon));
        } else {
            ImageIcon pIcon = new ImageIcon("images/player_icon-4.png");
            playerContainer.add(new JLabel(pIcon));
        }

        rightHandInfo.add(new JLabel("Points: " + player.getTotalPoints()));
        rightHandInfo.add(new JLabel("Bet: " + player.getBet().toString()));

        JPanel buttons = new JPanel(new GridLayout(1,2));

        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getGameEngine().removePlayer(player.getId());
            }
        });
        buttons.add(remove);

        JButton bet = new JButton("Update Bet");

        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateBetDialog(model, player);
            }
        });
        buttons.add(bet);

        rightHandInfo.add(buttons);

        JButton deal = new JButton("Deal");
        deal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getBet() == Bet.NO_BET) {
                    showMessageDialog(JOptionPane.getRootFrame(), "A bet needs to be placed before cards can be dealt.", "No Bet",JOptionPane.ERROR_MESSAGE);
                } else {
                    model.dealPlayer(player);
                }
            }
        });
        rightHandInfo.add(deal);

        mainPanel.add(pPanelBorder);
        pPanelBorder.add(playerPanel);
        playerPanel.add(playerContainer);
        playerContainer.add(rightHandInfo);

        add(mainPanel,PAGE_START);
        playerPanel.setVisible(true);
        playerPanel.revalidate();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName() == GUICallback.NEW_PLAYER_ADDED) {
            Player player = (Player) evt.getNewValue();
            addPlayer(player);
            validate();
        }

        if(evt.getPropertyName() == GUICallback.PLAYER_REMOVED) {
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            for (Player player : players) {
                addPlayer(player);
            }
            validate();
        }

        if(evt.getPropertyName() == GUICallback.BET_UPDATED) {
            //TODO FIX THIS SO NOT REMOVING ALL AND RE_ADDING
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            for (Player player : players) {
                addPlayer(player);
            }
            validate();
        }
    }

}

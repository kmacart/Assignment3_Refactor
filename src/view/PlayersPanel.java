package view;

import model.GUICallback;
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

import static java.awt.BorderLayout.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class PlayersPanel extends JPanel implements PropertyChangeListener {
    private GameModel model;

    public PlayersPanel(GameModel model) {
        this.setLayout(new GridLayout(0,1));
        this.model = model;
        Collection<Player> players = model.getGameEngine().getAllPlayers();
        for (Player player : players)
        {
            addPlayer(player);
        }
        setBackground(Color.lightGray);
        model.getCallBack().addPropertyChangeListener(this);
    }

    public void addPlayer(Player player) {
        JPanel pPanelC = new JPanel();
        JPanel pPanelCon = new JPanel();
        pPanelCon.setBackground(Color.LIGHT_GRAY);
        pPanelC.setBorder(new LineBorder(Color.black, 5));
        JPanel playerpanel = new JPanel(new GridLayout(1,0));
        playerpanel.setBackground(Color.GRAY);
        playerpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        playerpanel.setMaximumSize(new Dimension(300, 150));

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        controlPanel.add(new JLabel("Points: " + player.getTotalPoints()), PAGE_START);
        controlPanel.add(new JLabel("Player Bet: " + player.getBet().toString()), CENTER);

        JPanel pButtons = new JPanel(new GridLayout(3, 1));
        pButtons.setBackground(Color.GRAY);

        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getGameEngine().removePlayer(player.getId());
            }
        });
        JButton bet = new JButton("Update Bet");

        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateBetDialog(model, player);
            }
        });

        JButton deal = new JButton("Deal");
        deal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getBet() == Bet.NO_BET) {
                    showMessageDialog(JOptionPane.getRootFrame(), "A bet needs to be placed before cards can be dealt.", "No Bet",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    model.dealPlayer(player, 1000);
                }
            }
        });

        pButtons.setBorder(new EmptyBorder(10, 10, 10, 10));

        pButtons.add(remove);
        pButtons.add(bet);
        pButtons.add(deal);
        pButtons.setVisible(true);

        controlPanel.setBackground(Color.GRAY);
        controlPanel.add(pButtons, SOUTH);

        JLabel pName = new JLabel(player.getName());
        pName.setFont(new Font("Monaco", Font.PLAIN, 18));
        pName.setHorizontalAlignment(SwingConstants.CENTER);
        playerpanel.add(pName, PAGE_START);
        playerpanel.add(controlPanel, EAST);

        if (player.getId().endsWith("1")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-1.png");
            playerpanel.add(new JLabel(pIcon), WEST);
        } else if (player.getId().endsWith("2")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-2.png");
            playerpanel.add(new JLabel(pIcon), WEST);
        } else if (player.getId().endsWith("3")) {
            ImageIcon pIcon = new ImageIcon("images/player_icon-3.png");
            playerpanel.add(new JLabel(pIcon), WEST);
        } else {
            ImageIcon pIcon = new ImageIcon("images/player_icon-4.png");
            playerpanel.add(new JLabel(pIcon), WEST);
        }
        pPanelC.add(playerpanel);
        pPanelCon.add(pPanelC);
        add(pPanelCon);
        playerpanel.setVisible(true);
        controlPanel.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if(evt.getPropertyName() == GUICallback.NEW_PLAYER_ADDED)
        {
            Player player = (Player) evt.getNewValue();
            addPlayer(player);
            validate();
        }

        if(evt.getPropertyName() == GUICallback.PLAYER_REMOVED)
        {
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            for (Player player : players)
            {
                addPlayer(player);
            }
            validate();
        }

        if(evt.getPropertyName() == GUICallback.BET_UPDATED)
        {
            //TODO FIX THIS SO NOT REMOVING ALL AND RE_ADDING
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            for (Player player : players)
            {
                addPlayer(player);
            }
            validate();
        }
    }

}

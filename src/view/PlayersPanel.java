package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import static java.awt.BorderLayout.PAGE_START;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The JPanel PlayersPanel. It displays on the left side of the screen and displays information about all players.
 * It also allows you to deal to players, remove players, and update player's bets.
 */
public class PlayersPanel extends JPanel implements PropertyChangeListener {

    // Declare all class variables beforehand.
    private GameModel model;
    private Player player;

    // The buttons on the player panel.
    private JButton remove = new JButton("Remove");
    private JButton bet = new JButton("Update Bet");
    private JButton deal = new JButton("Deal");

    // To avoid anonymous inner classes, I created an overarching actionPerformed method which deals with all ActionListeners and executes code accordingly.
    public void actionPerformed(ActionEvent e) {
        // If the class was called from the remove button, remove that player from the game.
        if (e.getSource() == remove) {
            model.getGameEngine().removePlayer(player.getId());

            // Else, if the class was called from the bet button, display an UpdateBetDialog popup.
        } else if (e.getSource() == bet) {
            new UpdateBetDialog(model, player);

            // Else, if the class was called from the deal button, if the player has not placed a bet, display an error. Else, deal cards to that player.
        } else if (e.getSource() == deal) {
            if (player.getBet() == Bet.NO_BET) {
                showMessageDialog(JOptionPane.getRootFrame(), "A bet needs to be placed before cards can be dealt.", "No Bet", JOptionPane.ERROR_MESSAGE);
            } else {
                model.dealPlayer(player);
            }
        }
    }

    /**
     * Instantiates a new Players panel.
     *
     * @param model the model
     */
    public PlayersPanel(GameModel model) {

        // Set the layout of the parent component.
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Store the game model in a class-wide variable.
        this.model = model;

        // Collect and store the collection of players from the game engine.
        Collection<Player> players = model.getGameEngine().getAllPlayers();

        // Add each player from the players collection into the view.
        players.forEach(this::addPlayer);

        // Stylising
        setBackground(Color.lightGray);
        setOpaque(false);

        // Add the propertychangelistener.
        model.getCallBack().addPropertyChangeListener(this);
    }

    /**
     * Add player.
     *
     * @param player the player
     */
    public void addPlayer(Player player) {

        // Store the local variable into a class-wide one.
        this.player = player;

        // Create a new panel and set the background colour to be light gray.
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Create another panel. This panel acts purely as a container that allows another border to be added on.
        JPanel pPanelBorder = new JPanel();
        pPanelBorder.setBorder(new LineBorder(Color.black, 5));

        // Create the main panel and set an empty border around the edges.
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Get the player name and store it in a JLabel. Stylise the JLabel.
        JLabel pName = new JLabel(player.getName());
        pName.setFont(new Font("Monaco", Font.BOLD, 16));
        pName.setHorizontalAlignment(SwingConstants.CENTER);

        // Create two new JPanels that will be nested within the main panel.
        JPanel playerContainer = new JPanel(new GridLayout(1, 2));
        JPanel rightHandInfo = new JPanel(new GridLayout(4, 1));

        // Add the player name to the start of the player panel.
        playerPanel.add(pName,PAGE_START);

        // Get the icon that the player chose and add it to the view.
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

        // Add the player's points and the player's bet to the view.
        rightHandInfo.add(new JLabel("Points: " + player.getTotalPoints()));
        rightHandInfo.add(new JLabel("Bet: " + player.getBet().toString()));

        // Create a new JPanel that will store the two buttons.
        JPanel buttons = new JPanel(new GridLayout(1, 2));

        // Add an ActionListener to the remove button and add the remove button to the buttons panel.
        remove.addActionListener(this::actionPerformed);
        buttons.add(remove);

        // Add an ActionListener to the bet button and add the bet button to the buttons panel.
        bet.addActionListener(this::actionPerformed);
        buttons.add(bet);

        // Add the two buttons to the right hand side of the player info..
        rightHandInfo.add(buttons);

        // Add an ActionListener to the deal button and add the deal button to the RightHandInfo panel.
        deal.addActionListener(this::actionPerformed);
        rightHandInfo.add(deal);

        // Nest the panels and add it to the main view.
        mainPanel.add(pPanelBorder);
        pPanelBorder.add(playerPanel);
        playerPanel.add(playerContainer);
        playerContainer.add(rightHandInfo);
        add(mainPanel, PAGE_START);

        // Set the player panel to be visible and revalidate it.
        playerPanel.revalidate();
        playerPanel.setVisible(true);
    }


    // The PropertyChange Events
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // If a new player got added to the game, add the player to the view.
        if (evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player currPlayer = (Player) evt.getNewValue();
            addPlayer(currPlayer);
            validate();
        }

        // If a player was removed from the game, remove all players and re-add the updated collection in.
        if (evt.getPropertyName().equals(GUICallback.PLAYER_REMOVED)) {
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            players.forEach(this::addPlayer);
            validate();
        }

        // If a player's bet was updated, remove all players and re-add the updated collection in to refresh player information.
        if (evt.getPropertyName().equals(GUICallback.BET_UPDATED)) {
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            players.forEach(this::addPlayer);
            validate();
        }

        // If a player's bet was updated, remove all players and re-add the updated collection in to refresh player information.
        if (evt.getPropertyName().equals(GUICallback.HOUSE_BUST)) {
            removeAll();
            Collection<Player> players = model.getGameEngine().getAllPlayers();
            players.forEach(this::addPlayer);
            validate();
        }
    }

}

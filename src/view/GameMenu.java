package view;


import model.GameModel;
import model.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

/**
 * The type Game menu.
 */
public class GameMenu extends JMenuBar implements PropertyChangeListener {
    private JMenu playerInfo = new JMenu("Players");

    /**
     * Instantiates a new Game menu.
     *
     * @param model the model
     */
    public GameMenu(GameModel model) {
        // Create a new file menu and set the mnemonic.
        JMenu file = new JMenu("Game");
        file.setMnemonic(KeyEvent.VK_Q);

        // Add a quit option and set the mnemonic.
        JMenuItem exit = new JMenuItem("Quit");
        exit.setMnemonic(KeyEvent.VK_Q);

        // Add an action listener to exit.
        exit.addActionListener(e -> System.exit(0));

        // Add the JMenus.
        file.add(exit);
        add(file);

        // Create a new file menu and set the mnemonic.
        JMenu players = new JMenu("Players");
        players.setMnemonic(KeyEvent.VK_P);
        players.setContentAreaFilled(true);

        // Create a new JMenuItem for Add Player.
        JMenuItem addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(e -> new AddPlayerDialog(model));
        players.add(addPlayer);

        // Get the collection of players and add each player to the menu.
        Collection<Player> allPlayers = model.getGameEngine().getAllPlayers();
        for (Player player: allPlayers) {
            JMenu playerMenu = new JMenu(player.getName());
            JMenuItem playerStats = new JMenuItem(playerMenu.toString());
            playerMenu.add(playerStats);
            playerInfo.add(playerMenu);
        }

        // Add the JMenus.
        players.add(playerInfo);
        add(players);

        // Add the PropertyChangeListener.
        model.getCallBack().addPropertyChangeListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // If a new player is added, add it to the menu.
        if (evt.getPropertyName().equals(GUICallback.NEW_PLAYER_ADDED)) {
            Player player = (Player) evt.getNewValue();
            JMenu playerMenu = new JMenu(player.getName());
            JMenuItem playerStats = new JMenuItem(player.toString());
            playerMenu.add(playerStats);
            playerInfo.add(playerMenu);
        }

    }
}

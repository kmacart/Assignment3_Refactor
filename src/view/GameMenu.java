package view;

import com.sun.glass.events.KeyEvent;
import model.GameModel;
import model.Player;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

public class GameMenu extends JMenuBar implements PropertyChangeListener {
    private JMenu playerInfo = new JMenu("Players");

    public GameMenu(GameModel model) {
        // File menu
        JMenu file = new JMenu("Game");
        file.setMnemonic(KeyEvent.VK_G);

        JMenuItem exit = new JMenuItem("Quit");
        exit.setMnemonic(KeyEvent.VK_E);
        exit.addActionListener(e -> {System.exit(0);});
        file.add(exit);
        add(file);

        JMenu players = new JMenu("Players");
        players.setMnemonic(KeyEvent.VK_P);
        players.setContentAreaFilled(true);

        JMenuItem addPlayer = new JMenuItem("Add Player");
        addPlayer.addActionListener(e -> new AddPlayerDialog(model));
        players.add(addPlayer);

        Collection<Player> allPlayers = model.getGameEngine().getAllPlayers();
        for (Player player: allPlayers)
        {
            JMenu playerMenu = new JMenu(player.getName());
            JMenuItem playerStats = new JMenuItem(playerMenu.toString());
            playerMenu.add(playerStats);
            playerInfo.add(playerMenu);
        }

        players.add(playerInfo);
        add(players);


        model.getCallBack().addPropertyChangeListener(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName() == GUICallback.NEW_PLAYER_ADDED) {
            Player player = (Player) evt.getNewValue();

            JMenu playerMenu = new JMenu(player.getName());
            JMenuItem playerStats = new JMenuItem(player.toString());
            playerMenu.add(playerStats);
            playerInfo.add(playerMenu);
        }

    }
}

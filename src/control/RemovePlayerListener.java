package control;

import model.GameModel;
import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemovePlayerListener implements ActionListener {
    GameModel gameModel;
    String player;
    JPanel parent;


    public RemovePlayerListener(GameModel model, Player dealPlayer, JPanel panel) {
        gameModel = model;
        player = dealPlayer.getId();
        parent = panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        gameModel.getGameEngine().removePlayer(player);
        parent.repaint();
    }
}

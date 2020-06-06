package control;

import model.GameModel;
import model.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DealPlayerListener implements ActionListener {
    GameModel gameModel;
    Player player;


    public DealPlayerListener(GameModel model, Player dealPlayer) {
        gameModel = model;
        player = dealPlayer;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        gameModel.dealPlayer(player);
    }
}

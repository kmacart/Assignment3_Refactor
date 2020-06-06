package control;

import model.GameModel;
import model.Player;
import view.UpdateBetDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateBetListener implements ActionListener {
    GameModel gameModel;
    Player player;


    public UpdateBetListener(GameModel model, Player dealPlayer) {
        gameModel = model;
        player = dealPlayer;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        new UpdateBetDialog(gameModel, player);
    }
}

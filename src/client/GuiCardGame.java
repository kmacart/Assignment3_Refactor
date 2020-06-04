package client;

import model.GameModel;
import view.GameFrame;

import javax.swing.*;

public class GuiCardGame {
    private static GameModel gameModel;

    public static void main(String[] args) {

        gameModel = new GameModel();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new GameFrame(gameModel);
            }
        });
    }
}

package client;

import model.GameModel;
import view.GameFrame;

import javax.swing.*;

/**
 * The type Gui card game.
 */
public class GuiCardGame {
    private static GameModel gameModel;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        gameModel = new GameModel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame(gameModel);
            }
        });
    }
}

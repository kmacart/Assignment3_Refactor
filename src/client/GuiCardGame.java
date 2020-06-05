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

        // Create a new gameModel and save it in the static class-wide variable.
        gameModel = new GameModel();

        // Create a new thread that has the GUI elements running.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame(gameModel);
            }
        });
    }
}

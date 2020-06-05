package control;

import model.GameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Add player listener.
 */
public class AddPlayerListener implements ActionListener {
    private GameEngine model;

    /**
     * Instantiates a new Add player listener.
     *
     * @param model the model
     */
    public AddPlayerListener(GameEngine model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

    }

}

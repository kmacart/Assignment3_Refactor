package control;

import model.GameEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlayerListener implements ActionListener {
    private GameEngine model;

    public AddPlayerListener(GameEngine model)
    {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {

    }

}

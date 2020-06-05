package control;

import model.GameModel;
import model.Player;
import model.PlayerImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlayerListener implements ActionListener {
    // Class-wide variables
    private ButtonGroup icon;
    private JTextField pName;
    private JTextField points;
    private GameModel model;
    private JDialog container;

    public AddPlayerListener(ButtonGroup icons, JTextField name, GameModel gameModel, JTextField pPoints, JDialog c) {
        // Save local variables to class-wide ones.
        icon = icons;
        pName = name;
        model = gameModel;
        points = pPoints;
        container = c;
    }

    public void actionPerformed(ActionEvent e) {
        // Declare all variables.
        boolean error = false; // A boolean that stores whether or not there's an error.
        int iconNo; // The icon that the player chose
        int pPoints = 0; // The player's points.

        // Save the user's icon choice in a variable.
        switch (icon.getSelection().getActionCommand()) {
            case "Totoro":
                iconNo = 1;
                break;
            case "Snoopy":
                iconNo = 2;
                break;
            case "Jiji":
                iconNo = 3;
                break;
            case "Korra":
                iconNo = 4;
                break;
            default:
                iconNo = 0;
                break;
        }

        // Try to parse points as an integer. If we get a NumberFormatException error, display an error.
        try {
            pPoints = Integer.parseInt(points.getText());
        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(container, "Player Points must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        }

        // If there isn't any errors, add the Player to the game engine.
        if (!error) {
            Player player = new PlayerImpl("P" + (this.model.getGameEngine().getAllPlayers().size() + 1) + "-" + iconNo, pName.getText(), pPoints);
            this.model.addPlayer(player);
        }

        // Get rid of the JDialogBox.
        container.dispose();
    }
}

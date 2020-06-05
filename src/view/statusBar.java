package view;

import model.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * The type Status bar.
 */
public class statusBar extends JMenuBar {
    /**
     * Instantiates a new Status bar.
     *
     * @param model the model
     */
    public statusBar(GameModel model) {
        setLayout(new GridLayout(1, 3));

        JPanel noPlayersC = new JPanel();
        noPlayersC.setBackground(Color.GRAY);
        noPlayersC.setBorder(new LineBorder(Color.black, 1));
        JLabel noPlayers = new JLabel("# of Players: " + model.getGameEngine().getAllPlayers().size());
        noPlayers.setHorizontalAlignment(SwingConstants.CENTER);
        noPlayers.setBorder(new EmptyBorder(5, 5, 5, 5));
        noPlayersC.add(noPlayers);
        add(noPlayersC);

        JPanel creatorC = new JPanel();
        creatorC.setBackground(Color.GRAY);
        creatorC.setBorder(new LineBorder(Color.black, 1));
        JLabel creator = new JLabel("@ Kira Macarthur");
        creator.setBorder(new EmptyBorder(5, 5, 5, 5));
        creator.setHorizontalAlignment(SwingConstants.CENTER);
        creatorC.add(creator);
        add(creatorC);

        JPanel timeC = new JPanel();
        timeC.setBackground(Color.GRAY);
        timeC.setBorder(new LineBorder(Color.black, 1));
        JLabel time = new JLabel("Blackjack");
        time.setBorder(new EmptyBorder(5, 5, 5, 5));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        timeC.add(time);
        add(timeC);


    }

}

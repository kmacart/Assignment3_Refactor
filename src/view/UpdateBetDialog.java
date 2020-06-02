package view;

import model.GameModel;
import model.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The type Add player.
 */
public class UpdateBetDialog extends JDialog {
    private GameModel model;
    private Player player;

    /**
     * The Title.
     */
    JLabel title = new JLabel("Update Bet");

    /**
     * The Points.
     */
    JTextField amount = new JTextField("Enter bet amount");
    /**
     * The Submit.
     */
    JButton submit;
    /**
     * The Frame.
     */
    boolean error = false;

    /**
     * Action performed.
     *
     * @param e the e
     */
    public void actionPerformed(ActionEvent e) {
        int pAmount = 0;

        do {
            try {
                pAmount = Integer.parseInt(amount.getText());
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Bet amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                this.error = true;
            }

            if (!error) {
                this.model.getGameEngine().placeBet(player.getId(), pAmount);
            }
        } while (error);
        this.dispose();
    }

    /**
     * Instantiates a new Add player.
     */
    public UpdateBetDialog(GameModel model, Player player) {
        this.model = model;
        this.player = player;


        setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        FormLayout form = new FormLayout();
        panel.setLayout(new GridBagLayout());
        getContentPane().setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridBagLayout());

        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);

        form.addLabel("Bet Amount", panel);
        form.addLastField(amount, panel);

        submit = new JButton("Submit");
        submit.setBorder(new EmptyBorder(10,10,10,10));
        form.addLastField(submit, panel);
        submit.addActionListener(this::actionPerformed);

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(new BackgroundImage().bImg());

        setSize(780,300);

        getContentPane().add(panel);
        pack();
        repaint();
        setVisible(true);
    }

}

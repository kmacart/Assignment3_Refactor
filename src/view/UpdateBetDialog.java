package view;

import model.GameModel;
import model.Player;
import model.card.Suit;

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
    private JLabel title = new JLabel("Update Bet");

    /**
     * The Points.
     */
    private JTextField amount = new JTextField("Enter bet amount");

    private Suit[] suits = {Suit.DIAMONDS,Suit.CLUBS,Suit.HEARTS,Suit.SPADES};
    private JComboBox suit = new JComboBox(suits);

    private String[] betTypes = {"Score Bet","Suit Bet",};
    private JComboBox betType = new JComboBox(betTypes);
    private JPanel panel = new JPanel();
    private FormLayout form = new FormLayout();
    private JLabel suitLabel = new JLabel("Bet Suit");

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

        if (e.getSource() == submit) {
            do {
                try {
                    pAmount = Integer.parseInt(amount.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(this, "Bet amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    this.error = true;
                }

                if (!error) {
                    if (betType.getSelectedIndex() == 1) {
                        try {
                            this.model.getGameEngine().placeBet(player.getId(), pAmount, suits[suit.getSelectedIndex()]);
                        } catch (IllegalArgumentException e1) {
                            JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        try {
                            this.model.getGameEngine().placeBet(player.getId(), pAmount);
                        } catch (IllegalArgumentException e2) {
                            JOptionPane.showMessageDialog(this, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } while (error);
            this.dispose();
        } else if (e.getSource() == betType) {
            if (betType.getSelectedIndex() == 1) {
                suitLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                suitLabel.setBorder(new EmptyBorder(0,0,10,10));
                suitLabel.setVisible(true);
                suit.setVisible(true);
                revalidate();
            } else if (betType.getSelectedIndex() == 0) {
                suitLabel.setVisible(false);
                suit.setVisible(false);
                revalidate();
            }
        }
    }

    /**
     * Instantiates a new Add player.
     *
     * @param model  the model
     * @param player the player
     */
    public UpdateBetDialog(GameModel model, Player player) {
        this.model = model;
        this.player = player;

        setLayout(new GridBagLayout());
        panel.setLayout(new GridBagLayout());
        getContentPane().setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridBagLayout());

        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);

        form.addLabel("Bet Type",panel);
        form.addLastField(betType,panel);

        form.addLabel("Bet Amount", panel);
        form.addLastField(amount, panel);

        form.addLabelC(suitLabel, panel);
        suitLabel.setVisible(false);
        form.addLastField(suit, panel);
        suit.setVisible(false);

        betType.addActionListener(this::actionPerformed);

        submit = new JButton("Submit");
        submit.setBorder(new EmptyBorder(10,10,10,10));
        form.addFullField(submit, panel);
        submit.addActionListener(this::actionPerformed);

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(new BackgroundImage().bImg());

        panel.setSize(780,1000);

        getContentPane().add(panel);
        pack();
        repaint();
        setVisible(true);
    }

}

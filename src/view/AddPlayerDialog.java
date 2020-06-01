package view;

import model.GameModel;
import model.Player;
import model.PlayerImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The type Add player.
 */
public class AddPlayerDialog extends JDialog {
    private GameModel model;

    /**
     * The Points.
     */
    JTextField points = new JTextField("Enter player points");
    /**
     * The Title.
     */
    JLabel title = new JLabel("Add Player");
    /**
     * The Icons.
     */
    ButtonGroup icons = new ButtonGroup();
    /**
     * The Icon 1.
     */
    JRadioButton icon1 = new JRadioButton("Totoro");
    /**
     * The Icon 2.
     */
    JRadioButton icon2 = new JRadioButton("Snoopy");
    /**
     * The Icon 3.
     */
    JRadioButton icon3 = new JRadioButton("Jiji");
    /**
     * The Icon 4.
     */
    JRadioButton icon4 = new JRadioButton("Korra");
    /**
     * The Submit.
     */
    JButton submit;
    /**
     * The Name.
     */
    JTextField name = new JTextField();
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
        int iconNo;
        int pPoints = 0;

        do {
            if (icon1.isSelected()) {
                iconNo = 1;
            } else if (icon2.isSelected()) {
                iconNo = 2;
            } else if (icon3.isSelected()) {
                iconNo = 3;
            } else if (icon4.isSelected()) {
                iconNo = 4;
            } else {
                iconNo = 0;
            }

            try {
                pPoints = Integer.parseInt(points.getText());
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(this, "Player Points must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                this.error = true;
            }

            if (!error) {
                Player player = new PlayerImpl("P" + (this.model.getGameEngine().getAllPlayers().size() + 1) + "-" + iconNo, name.getText(), pPoints);
                this.model.addPlayer(player);
            }
        } while (error);
        this.dispose();
    }

    /**
     * Instantiates a new Add player.
     */
    public AddPlayerDialog(GameModel model) {
        this.model = model;

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

        form.addLabel("Name", panel);
        form.addLastField(name, panel);

        form.addLabel("Points", panel);
        form.addLastField(points, panel);

        form.addLabel("Icon", panel);

        ImageIcon img1 = new ImageIcon("images/player_icon-1.png");
        ImageIcon img1border = new ImageIcon("images/player_icon-1_border.png");
        icon1.setIcon(img1);
        icon1.setSelectedIcon(img1border);
        icon1.setHorizontalAlignment(SwingConstants.CENTER);
        icon1.setSelected(true);

        ImageIcon img2 = new ImageIcon("images/player_icon-2.png");
        ImageIcon img2border = new ImageIcon("images/player_icon-2_border.png");
        icon2.setIcon(img2);
        icon2.setSelectedIcon(img2border);

        ImageIcon img3 = new ImageIcon("images/player_icon-3.png");
        ImageIcon img3border = new ImageIcon("images/player_icon-3_border.png");
        icon3.setIcon(img3);
        icon3.setSelectedIcon(img3border);

        ImageIcon img4 = new ImageIcon("images/player_icon-4.png");
        ImageIcon img4border = new ImageIcon("images/player_icon-4_border.png");
        icon4.setIcon(img4);
        icon4.setSelectedIcon(img4border);

        icons.add(icon1);
        icons.add(icon2);
        icons.add(icon3);
        icons.add(icon4);

        GridLayout gl = new GridLayout(1, 4, 10, 0);

        JPanel iconPane = new JPanel(gl);
        iconPane.setPreferredSize(new Dimension(440, 100));

        iconPane.add(icon2);
        iconPane.add(icon3);
        iconPane.add(icon4);
        form.addLastField(iconPane, panel);

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

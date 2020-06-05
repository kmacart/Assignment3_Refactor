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
    // The model. This has been parsed into the constructor. It allows us to get to the game engine and peform some basic functionalities.
    private GameModel model;

    /**
     * The Points.
     */
    // A JTextField that allows the player to enter in how many points they want.
    JTextField points = new JTextField("Enter player points");

    /**
     * The Title.
     */
    // The title of the jdialog.
    JLabel title = new JLabel("Add Player");

    /**
     * The Icons.
     */
    // The overarching button group that allows the player to select an icon.
    ButtonGroup icons = new ButtonGroup();

    /**
     * The Icon 1.
     */
    // The Totoro icon.
    JRadioButton icon1 = new JRadioButton("Totoro");

    /**
     * The Icon 2.
     */
    // The Snoopy icon
    JRadioButton icon2 = new JRadioButton("Snoopy");

    /**
     * The Icon 3.
     */
    // The Jiji Icon (Kiki's Delivery Service)
    JRadioButton icon3 = new JRadioButton("Jiji");

    /**
     * The Icon 4.
     */
    // The Korra Icon.
    JRadioButton icon4 = new JRadioButton("Korra");

    /**
     * The Submit.
     */
    // The Submit button.
    JButton submit;

    /**
     * The Name.
     */
    // A JTextField that allows the player to enter in their name.
    JTextField name = new JTextField();

    /**
     * The Error.
     */
    // A boolean that states whether or not an error has occured.
    boolean error = false;

    /**
     * Action performed.
     * @param e the e
     *
     * <p>To remove anonymous inner classes, there is one actionPerformed that is referenced to from all ActionListeners within the class.</p>
     */
    public void actionPerformed(ActionEvent e) {

        // Declare all variables.
        int iconNo; // The icon that the player chose
        int pPoints = 0; // The player's points.

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
                error = true;
            }

            if (!error) {
                Player player = new PlayerImpl("P" + (this.model.getGameEngine().getAllPlayers().size() + 1) + "-" + iconNo, name.getText(), pPoints);
                this.model.addPlayer(player);
            }
        } while (error);
        this.dispose();
    }

    /**
     * Instantiates a new Add player dialog.
     *
     * @param model the model that is parsed around the game.
     *              <p>
     *              The constructor - it creates a new dialog box.
     */
    public AddPlayerDialog(GameModel model) {
        this.model = model;

        // Set the layout of the parent frame to be GridBagLayout.
        setLayout(new GridBagLayout());

        // Declare all variables.
        JPanel panel = new JPanel();
        FormLayout form = new FormLayout();

        // Set the layout of panel and add it to the parent frame.
        panel.setLayout(new GridBagLayout());
        getContentPane().setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);

        // Set the layout of panel to be a GridBagLayout.
        panel.setLayout(new GridBagLayout());

        // Make title look better and add it to the parent frame.
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.PAGE_START);

        // Create the form.
        form.addLabel("Name", panel);
        form.addLastField(name, panel);

        form.addLabel("Points", panel);
        form.addLastField(points, panel);

        form.addLabel("Icon", panel);

        // Add the icons to the form.
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

        // Add the icons to the icon group.
        icons.add(icon1);
        icons.add(icon2);
        icons.add(icon3);
        icons.add(icon4);

        // Create a new GridLayout with 4 columns. There's a gap of 10 in between each icon.
        GridLayout gl = new GridLayout(1, 4, 10, 0);

        // Create a new JPanel using the gridlayout above to store my icons in.
        JPanel iconPane = new JPanel(gl);
        iconPane.setPreferredSize(new Dimension(440, 100));

        // Add the icons to the JPanel.
        iconPane.add(icon1);
        iconPane.add(icon2);
        iconPane.add(icon3);
        iconPane.add(icon4);
        form.addLastField(iconPane, panel);

        // Add the submit button to the form.
        submit = new JButton("Submit");
        submit.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.addLastField(submit, panel);
        // Add an action listener on the submit button that links to the actionPerformed method above.
        submit.addActionListener(this::actionPerformed);

        // Add an empty border onto the panel.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Set the background image of the parent frame.
        getContentPane().add(new BackgroundImage().bImg());

        // Set the size of the parent frame.
        setSize(780, 300);

        // Add the panel to the parent frame.
        getContentPane().add(panel);

        // Display the jdialog
        pack();
        repaint();
        setVisible(true);
    }

}

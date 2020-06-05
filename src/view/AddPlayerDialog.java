package view;

import control.AddPlayerListener;
import model.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The type Add player.
 */
public class AddPlayerDialog extends JDialog {

    // A JTextField that allows the player to enter in how many points they want.
    JTextField points = new JTextField("Enter player points");

    // The title of the jdialog.
    JLabel title;

    // The overarching button group that allows the player to select an icon.
    ButtonGroup icons;

    // The Totoro icon.
    JRadioButton icon1 = new JRadioButton("Totoro");

    // The Snoopy icon
    JRadioButton icon2 = new JRadioButton("Snoopy");

    // The Jiji Icon (Kiki's Delivery Service)
    JRadioButton icon3 = new JRadioButton("Jiji");

    // The Korra Icon.
    JRadioButton icon4 = new JRadioButton("Korra");

    // The Submit button.
    JButton submit;

    // A JTextField that allows the player to enter in their name.
    JTextField name = new JTextField();

    /**
     * Instantiates a new Add player dialog.
     *
     * @param model the model that is parsed around the game.
     *              <p>
     *              The constructor - it creates a new dialog box.
     */
    public AddPlayerDialog(GameModel model) {

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
        title = new JLabel("Add Player");
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
        icon1.setActionCommand("Totoro");

        ImageIcon img2 = new ImageIcon("images/player_icon-2.png");
        ImageIcon img2border = new ImageIcon("images/player_icon-2_border.png");
        icon2.setIcon(img2);
        icon2.setSelectedIcon(img2border);
        icon2.setActionCommand("Snoopy");

        ImageIcon img3 = new ImageIcon("images/player_icon-3.png");
        ImageIcon img3border = new ImageIcon("images/player_icon-3_border.png");
        icon3.setIcon(img3);
        icon3.setSelectedIcon(img3border);
        icon3.setActionCommand("Jiji");

        ImageIcon img4 = new ImageIcon("images/player_icon-4.png");
        ImageIcon img4border = new ImageIcon("images/player_icon-4_border.png");
        icon4.setIcon(img4);
        icon4.setSelectedIcon(img4border);
        icon4.setActionCommand("Korra");


        // Add the icons to the icon group.
        icons = new ButtonGroup();
        icons.add(icon1);
        icons.add(icon2);
        icons.add(icon3);
        icons.add(icon4);

        // Create a new GridLayout with 4 columns. There's a gap of 10 in between each icon.
        GridLayout gl = new GridLayout(1, 4, 10, 0);

        // Create a new JPanel using the gridlayout above to store my icons in.
        JPanel iconPane = new JPanel(gl);
        iconPane.setPreferredSize(new Dimension(470, 100));

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
        submit.addActionListener(new AddPlayerListener(icons, name, model, points, this));

        // Add an empty border onto the panel.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Set the size of the parent frame.
        setSize(780, 300);

        // Add the panel to the parent frame.
        getContentPane().add(panel);

        // Display the JDialog
        pack();
        repaint();
        setVisible(true);
    }

}

//package view;
//
//import model.GameModel;
//import model.Player;
//import model.bet.Bet;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//
//import static java.awt.BorderLayout.PAGE_START;
//import static javax.swing.JOptionPane.showMessageDialog;
//
//public class playerInfoPanel extends JPanel {
//    // The buttons on the player panel.
//    private JButton remove = new JButton();
//    private JButton bet = new JButton();
//    private JButton deal = new JButton();
//    private GameModel model;
//    private Player player;
//
//    // To avoid anonymous inner classes, I created an overarching actionPerformed method which deals with all ActionListeners and executes code accordingly.
//    public void actionPerformed(ActionEvent e) {
//        // If the class was called from the remove button, remove that player from the game.
//        if (e.getSource() == remove) {
//            model.getGameEngine().removePlayer(player.getId());
//
//            // Else, if the class was called from the bet button, display an UpdateBetDialog popup.
//        } else if (e.getSource() == bet) {
//            new UpdateBetDialog(model, player);
//
//            // Else, if
//                model.dealPlayer(player);
//            }
//        }
//
//    /**
//     * Add player.
//     *
//     * @param player the player
//     */
//    public JPanel addPlayer(Player player) {
//        this.player = player;
//
//        remove = new JButton("Remove");
//        bet = new JButton("Update Bet");
//        deal = new JButton("Deal");
//
//        // Create a new panel and set the background colour to be light gray.
//        JPanel mainPanel = new JPanel();
//        mainPanel.setBackground(Color.LIGHT_GRAY);
//
//        // Create another panel. This panel acts purely as a container that allows another border to be added on.
//        JPanel pPanelBorder = new JPanel();
//        pPanelBorder.setBorder(new LineBorder(Color.black, 5));
//
//        // Create the main panel and set an empty border around the edges.
//        JPanel playerPanel = new JPanel(new BorderLayout());
//        playerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//        // Get the player name and store it in a JLabel. Stylise the JLabel.
//        JLabel pName = new JLabel(player.getName());
//        pName.setFont(new Font("Monaco", Font.BOLD, 16));
//        pName.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // Create two new JPanels that will be nested within the main panel.
//        JPanel playerContainer = new JPanel(new GridLayout(1, 2));
//        JPanel rightHandInfo = new JPanel(new GridLayout(4, 1));
//
//        // Add the player name to the start of the player panel.
//        playerPanel.add(pName,PAGE_START);
//
//        // Get the icon that the player chose and add it to the view.
//        if (player.getId().endsWith("1")) {
//            ImageIcon pIcon = new ImageIcon("images/player_icon-1.png");
//            playerContainer.add(new JLabel(pIcon));
//        } else if (player.getId().endsWith("2")) {
//            ImageIcon pIcon = new ImageIcon("images/player_icon-2.png");
//            playerContainer.add(new JLabel(pIcon));
//        } else if (player.getId().endsWith("3")) {
//            ImageIcon pIcon = new ImageIcon("images/player_icon-3.png");
//            playerContainer.add(new JLabel(pIcon));
//        } else {
//            ImageIcon pIcon = new ImageIcon("images/player_icon-4.png");
//            playerContainer.add(new JLabel(pIcon));
//        }
//
//        // Add the player's points and the player's bet to the view.
//        rightHandInfo.add(new JLabel("Points: " + player.getTotalPoints()));
//        rightHandInfo.add(new JLabel("Bet: " + player.getBet().toString()));
//
//        // Create a new JPanel that will store the two buttons.
//        JPanel buttons = new JPanel(new GridLayout(1, 2));
//
//        // Add an ActionListener to the remove button and add the remove button to the buttons panel.
//        remove.addActionListener(this::actionPerformed);
//        buttons.add(remove);
//
//        // Add an ActionListener to the bet button and add the bet button to the buttons panel.
//        bet.addActionListener(this::actionPerformed);
//        buttons.add(bet);
//
//        // Add the two buttons to the right hand side of the player info..
//        rightHandInfo.add(buttons);
//
//        // Add an ActionListener to the deal button and add the deal button to the RightHandInfo panel.
//        deal.addActionListener(this::actionPerformed);
//        rightHandInfo.add(deal);
//
//        // Nest the panels and add it to the main view.
//        mainPanel.add(pPanelBorder);
//        pPanelBorder.add(playerPanel);
//        playerPanel.add(playerContainer);
//        playerContainer.add(rightHandInfo);
//
//        // Set the player panel to be visible and revalidate it.
//        playerPanel.revalidate();
//
//        return pPanelBorder;
//    }
//}

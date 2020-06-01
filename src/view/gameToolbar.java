package view;

import model.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Tool bar.
 */
public class gameToolbar extends JMenuBar {
    /**
     * Tool bar j menu bar.
     *
     * @param frame the frame
     * @return the j menu bar
     */
    public gameToolbar(JFrame frame, GameEngine model) {
        JMenuItem newgame;
        JMenuItem endgame;
        JMenuItem exit;

        exit = new JMenuItem("Exit");
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.addActionListener(e -> { System.exit(0); });


        newgame = new JMenuItem("New Game");
        newgame.setIcon(new ImageIcon("images/newgame_icon.png"));
        newgame.setHorizontalAlignment(SwingConstants.CENTER);
        newgame.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          int confirmnew = JOptionPane.showConfirmDialog(frame,"Start a new game?");
                                          if (confirmnew == 0) {
                                              frame.setVisible(false);
//                                              JFrame inprogress = new InProgressGame().inProgressGame();
//                                              inprogress.setVisible(true);
                                          }
                                      }
                                  }
        );

//        newgame.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                int confirmnew = JOptionPane.showConfirmDialog(frame,"Start a new game?");
//                if (confirmnew == 0) {
//                    frame.setVisible(false);
//                    JFrame inprogress = new InProgressGame().inProgressGame();
//                    inprogress.setVisible(true);
//                }
//            }
//        });


        endgame = new JMenuItem("End Game");
        endgame.setIcon(new ImageIcon("images/exitgame_icon.png"));
        endgame.setHorizontalAlignment(SwingConstants.CENTER);

//        endgame.addActionListener(new ActionListener() {
        ////            public void actionPerformed(ActionEvent e) {
        ////                int confirm = JOptionPane.showConfirmDialog(frame,"Are you sure you want to end the game?");
        ////                if (confirm == 0) {
        ////                    frame.setVisible(false);
        ////                    JFrame newgame = new NewGame().MainPage();
        ////                    newgame.setVisible(true);
        ////                    model.resetAllBetsAndHands();
        ////                }
        ////            }
//        });

        add(newgame);
        add(endgame);
        add(exit);
    }

}

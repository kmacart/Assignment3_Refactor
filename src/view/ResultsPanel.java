package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;
import model.card.Hand;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The type Results panel.
 */
public class ResultsPanel extends JPanel implements PropertyChangeListener {
    private GameModel model;

    /**
     * Instantiates a new Results panel.
     *
     * @param model the model
     */
    public ResultsPanel(GameModel model) {
        this.model = model;
        model.getCallBack().addPropertyChangeListener(this);
    }

    private void showPlayerResults(Hand houseHand) {
        for (Player player : model.getGameEngine().getAllPlayers()) {
            player.applyBetResult(houseHand);
            if (player.getBet() != Bet.NO_BET) {
                JPanel resultPanel = new JPanel();
                resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));

                JLabel name = new JLabel(player.getName());
                name.setFont(new Font("Monaco", Font.BOLD, 20));
                name.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel result = new JLabel("Result: " + player.getBet().getResult().toString().toLowerCase());
                result.setFont(new Font("Monaco", Font.PLAIN, 16));

                JLabel betOutcome = new JLabel("Bet Outcome: " + player.getBet().getOutcome());
                betOutcome.setFont(new Font("Monaco", Font.PLAIN, 16));

                resultPanel.setBackground(Color.GRAY);

                resultPanel.add(name);
                resultPanel.add(result);
                resultPanel.add(betOutcome);
                add(resultPanel, BorderLayout.CENTER);
            }
        }
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GUICallback.HOUSE_BUST)) {
            Hand houseHand = (Hand) evt.getNewValue();
            showPlayerResults(houseHand);
            validate();
        }
    }
}

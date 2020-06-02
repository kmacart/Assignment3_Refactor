package view;

import model.GUICallback;
import model.GameModel;
import model.Player;
import model.card.Hand;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ResultsPanel extends JPanel implements PropertyChangeListener {
    private GameModel model;

    public ResultsPanel(GameModel model) {
        this.model = model;
        model.getCallBack().addPropertyChangeListener(this);

    }

    private void showPlayerResults() {
        for (Player player : model.getGameEngine().getAllPlayers()) {
            JPanel resultPanel = new JPanel();
            resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
            JLabel name = new JLabel(player.getName());
            JLabel result = new JLabel("Result: " + player.getBet().getResult());
            JLabel betOutcome = new JLabel("Bet Outcome: " + player.getBet().getOutcome());
            resultPanel.add(name);
            resultPanel.add(result);
            resultPanel.add(betOutcome);
            add(resultPanel);
        }
        setBackground(Color.BLUE);
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GUICallback.HOUSE_BUST)) {
            Hand houseHand = (Hand) evt.getNewValue();
            for (Player player: this.model.getGameEngine().getAllPlayers())
            {
                player.applyBetResult(houseHand);
            }
            showPlayerResults();
            model.getGameEngine().resetAllBetsAndHands();
            validate();
        }
    }
}

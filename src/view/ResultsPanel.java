package view;

import model.GameModel;
import model.Player;
import model.bet.Bet;
import model.card.Hand;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static view.GUICallback.BET_UPDATED;

public class ResultsPanel extends JPanel implements PropertyChangeListener {
    private GameModel model;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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
                JLabel result = new JLabel("Result: " + player.getBet().getResult());
                JLabel betOutcome = new JLabel("Bet Outcome: " + player.getBet().getOutcome());
                resultPanel.add(name);
                resultPanel.add(result);
                resultPanel.add(betOutcome);
                add(resultPanel);
                this.pcs.firePropertyChange(BET_UPDATED,null,player);
            }
        }
        setBackground(Color.BLUE);
        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GUICallback.HOUSE_BUST)) {
            Hand houseHand = (Hand) evt.getNewValue();
            showPlayerResults(houseHand);
            model.getGameEngine().resetAllBetsAndHands();
            validate();
        }
    }
}

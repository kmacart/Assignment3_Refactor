package model;

import model.card.DeckImpl;
import view.ConsoleLoggerCallback;
import view.GUICallback;

import javax.swing.*;

/* The type Game model. */
public class GameModel {
    private GameEngineImpl gameEngine;
    private GUICallback callback;
    private int delay = 1500;
    private Player currentPlayer = null;

    /**
     * Sets delay.
     *
     * @param time the time
     */
    public void setDelay(int time) {
        delay = time;
    }

    /**
     * Sets current player.
     *
     * @param player the player
     */
    public void setCurrentPlayer(Player player) {
        try {
            currentPlayer = player;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Instantiates a new Game model.
     */
    public GameModel() {
        try {
            this.gameEngine = new GameEngineImpl();
            this.callback = new GUICallback();
            this.gameEngine.registerCallback(callback);
            ConsoleLoggerCallback logger = new ConsoleLoggerCallback(gameEngine);
            gameEngine.registerCallback(logger);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gets call back.
     *
     * @return the call back
     */
    public GUICallback getCallBack() {
        return this.callback;
    }

    /**
     * Gets game engine.
     *
     * @return the game engine
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Add player.
     *
     * @param player the player
     */
    public void addPlayer(Player player) {
        try {
            gameEngine.addPlayer(player);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * New game.
     */
    public void newGame() {
        try {
            gameEngine.resetAllBetsAndHands();
            gameEngine.deck = DeckImpl.createShuffledDeck();
            this.callback.newGame();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Change player.
     *
     * @param playerName the player name
     */
    public void changePlayer(String playerName) {
        try {
            this.callback.changePlayer(playerName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deal player.
     *
     * @param player the player
     */
    public void dealPlayer(Player player) {
        new Thread() {
            @Override
            public void run() {
                try {
                    gameEngine.dealPlayer(player.getId(), delay);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }

    /**
     * Deal house.
     */
    public void dealHouse() {
        new Thread() {
            @Override
            public void run() {
                try {
                    gameEngine.dealHouse(delay);
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }

    public void dealAll() {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (Player player : gameEngine.getAllPlayers()) {
                        dealPlayer(player);
                    }
                    dealHouse();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }
}

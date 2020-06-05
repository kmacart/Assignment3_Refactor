package model;

import view.ConsoleLoggerCallback;
import view.GUICallback;

/**
 * The type Game model.
 */
public class GameModel {
    private GameEngine gameEngine;
    private GUICallback callback;
    private int delay;
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
        currentPlayer = player;
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
        this.gameEngine = new GameEngineImpl();
        this.callback = new GUICallback();
        this.gameEngine.registerCallback(callback);
        ConsoleLoggerCallback logger = new ConsoleLoggerCallback(gameEngine);
        gameEngine.registerCallback(logger);
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
        gameEngine.addPlayer(player);
    }

    /**
     * New game.
     */
    public void newGame() {
        gameEngine.resetAllBetsAndHands();
        this.callback.newGame();
    }

    /**
     * Change player.
     *
     * @param playerName the player name
     */
    public void changePlayer(String playerName) {
        this.callback.changePlayer(playerName);
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
                gameEngine.dealPlayer(player.getId(), delay);
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
                gameEngine.dealHouse(delay);
            }
        }.start();
    }
}

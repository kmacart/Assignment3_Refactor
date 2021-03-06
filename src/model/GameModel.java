package model;

import view.ConsoleLoggerCallback;
import view.GUICallback;

public class GameModel {
    private GameEngine gameEngine;
    private GUICallback callback;
    private int delay;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int time) {
        delay = time;
    }

    public GameModel() {
        this.gameEngine = new GameEngineImpl();
        this.callback = new GUICallback();
        this.gameEngine.registerCallback(callback);
        ConsoleLoggerCallback logger = new ConsoleLoggerCallback(gameEngine);
        gameEngine.registerCallback(logger);
    }

    public GUICallback getCallBack() {
        return this.callback;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void addPlayer(Player player) {
        gameEngine.addPlayer(player);
    }

    public void newGame() {
        gameEngine.resetAllBetsAndHands();
        this.callback.newGame();
    }

    public void changePlayer(String playerName) {
        this.callback.changePlayer(playerName);
    }

    public void dealPlayer(Player player) {
        new Thread() {
            @Override
            public void run() {
                gameEngine.dealPlayer(player.getId(), delay);
            }
        }.start();
    }

    public void dealHouse() {
        new Thread()
        {
            @Override
            public void run()
            {
                gameEngine.dealHouse(delay);
            }
        }.start();
    }
}

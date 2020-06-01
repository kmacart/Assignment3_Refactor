package model;

public class GameModel {
    private GameEngine gameEngine;
    private GUICallback callback;

    public GameModel()
    {
        this.gameEngine = new GameEngineImpl();
        this.callback = new GUICallback();
        this.gameEngine.registerCallback(callback);
    }

    public GUICallback getCallBack() {
        return this.callback;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void addPlayer(Player player)
    {
        gameEngine.addPlayer(player);
    }

    public void dealPlayer(Player player, int delay)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                gameEngine.placeBet(player.getId(), 10);
                gameEngine.dealPlayer(player.getId(), delay);
            }
        }.start();
    }

    public void dealHouse(int delay)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                gameEngine.dealHouse(delay);
            }
        }.start();
    }}

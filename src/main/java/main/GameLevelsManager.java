package main;

import entity.Ball;
import entity.Door;
import entity.Player;
import tiles.TileManager;

public class GameLevelsManager {
    private GameLevel[] gameLevels;
    private GamePanel gamePanel;
    private TileManager tileManager;
    private Player player;

    public GameLevelsManager(GamePanel gamePanel, TileManager tileManager, Player player){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.player = player;
        initializeGameLevels();
    }

    private void initializeGameLevels(){
        int numOfLevels = 1;
        gameLevels = new GameLevel[numOfLevels];

        GameLevel gameLevel1 = createMap1();
        gameLevels[0] = gameLevel1;
    }

    private GameLevel createMap1(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 330, 270);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 560, 238);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 570, 296);
        Door door2 = new Door(gamePanel, tileManager, balls, 400, 232);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors);
    }

    GameLevel getCurrentLevel(){
        return gameLevels[0];
    }
}

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
    private GameLevel currentLevel;

    public GameLevelsManager(GamePanel gamePanel, TileManager tileManager, Player player){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.player = player;
        initializeGameLevels();
    }

    private void initializeGameLevels(){
        int numOfLevels = 2;
        gameLevels = new GameLevel[numOfLevels];

        GameLevel gameLevel1 = createMap1();
        gameLevels[0] = gameLevel1;
        //GameLevel gameLevel2 = createMap2();
        //gameLevels[1] = gameLevel2;

        currentLevel = gameLevel1;
    }

    private boolean checkIfLevelWasCleared(GameLevel gameLevel){
        boolean levelCleared = true;
        for(Door door : gameLevel.getDoors()){
            if(!door.isClosed()){
                levelCleared = false;
                break;
            }
        }
        return levelCleared;
    }

    GameLevel getCurrentLevel(){
        if(checkIfLevelWasCleared(currentLevel)){
            currentLevel = createMap2();
        }
        return currentLevel;
    }

    private GameLevel createMap1(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 330, 270);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 560, 238);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 570, 296);
        Door door2 = new Door(gamePanel, tileManager, balls, 400, 232);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map1");
    }

    private GameLevel createMap2(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 330, 270);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 560, 238);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 570, 296);
        Door door2 = new Door(gamePanel, tileManager, balls, 400, 232);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map2");
    }

}

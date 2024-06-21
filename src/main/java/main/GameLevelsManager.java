package main;

import entity.Ball;
import entity.Door;
import entity.Player;
import tiles.TileManager;

import java.lang.reflect.Method;

public class GameLevelsManager {
    private GamePanel gamePanel;
    private TileManager tileManager;
    private Player player;
    private GameLevel currentLevel;
    private int currentLevelNum;

    public GameLevelsManager(GamePanel gamePanel, TileManager tileManager, Player player){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.player = player;

        currentLevel = createMap1();
        currentLevelNum = 1;
    }

    private void getNextLevel() {
        switch (currentLevelNum) {
            case 1:
                currentLevel = createMap2();
                break;
            case 2:
                currentLevel = createMap3();
                break;
        }
        currentLevelNum++;
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

    public GameLevel getCurrentLevel(){
        if(checkIfLevelWasCleared(currentLevel)){
            getNextLevel();
        }
        return currentLevel;
    }

    private GameLevel createMap1(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 330, 270);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 560, 206);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 500, 296);
        Door door2 = new Door(gamePanel, tileManager, balls, 400, 232);
        Door[] doors = {door1, door2};

        player.setPositionX(348);
        player.setPositionY(200);

        return new GameLevel(balls, doors, "map1");
    }

    private GameLevel createMap2(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 224, 142);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 580, 334);
        Ball ball3 = new Ball(gamePanel, tileManager, player, 672, 174);
        Ball[] balls = {ball1, ball2, ball3};

        Door door1 = new Door(gamePanel, tileManager, balls, 256, 264);
        Door door2 = new Door(gamePanel, tileManager, balls, 786, 136);
        Door[] doors = {door1, door2};

        player.setPositionX(288);
        player.setPositionY(128);

        return new GameLevel(balls, doors, "map2");
    }

    private GameLevel createMap3(){
        Ball ball1 = new Ball(gamePanel, tileManager, player, 224, 142);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 580, 334);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 256, 264);
        Door door2 = new Door(gamePanel, tileManager, balls, 786, 136);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map2");
    }

}

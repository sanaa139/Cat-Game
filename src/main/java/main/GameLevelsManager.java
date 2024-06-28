package main;

import entity.Ball;
import entity.Door;
import entity.Player;
import tiles.TileManagerGame;

public class GameLevelsManager {
    private GamePanel gamePanel;
    private TileManagerGame tileManager;
    private GameLevel currentLevel;
    public int currentLevelNum;

    public GameLevelsManager(GamePanel gamePanel, TileManagerGame tileManager){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;

        currentLevel = null;
        currentLevelNum = 0;
    }

    private void getNextLevel() {
        currentLevelNum++;
        switch (currentLevelNum) {
            case 1:
                System.out.println("created map1");
                currentLevel = createMap2();
                break;
            case 2:
                System.out.println("created map2");
                currentLevel = createMap2();
                break;
            case 3:
                System.out.println("created map3");
                currentLevel = createMap3();
                break;
        }
    }

    private boolean checkIfLevelWasCleared(GameLevel gameLevel){
        boolean levelCleared = true;
        if(gameLevel == null){
            System.out.println("game level null");
            return levelCleared;
        }else{
            for(Door door : gameLevel.getDoors()){
                if(!door.isClosed()){
                    levelCleared = false;
                    break;
                }
            }
            return levelCleared;
        }
    }

    public GameLevel getCurrentOrNextLevel(){
        if(checkIfLevelWasCleared(currentLevel)){
            System.out.println("lvl num: " + currentLevelNum + ", lvl cleared true");
            getNextLevel();
        }
        return currentLevel;
    }

    public GameLevel getCurrentLevel(){
        return currentLevel;
    }

    private GameLevel createMap1(){
        Player player = new Player(gamePanel, gamePanel.keyHandler, tileManager, 348, 200);
        Ball ball1 = new Ball(gamePanel, tileManager, player, 330, 270);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 560, 206);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 500, 296);
        Door door2 = new Door(gamePanel, tileManager, balls, 400, 232);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map1", player);
    }

    private GameLevel createMap2(){
        Player player =  new Player(gamePanel, gamePanel.keyHandler, tileManager, 288, 128);
        Ball ball1 = new Ball(gamePanel, tileManager, player, 224, 142);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 580, 334);
        Ball ball3 = new Ball(gamePanel, tileManager, player, 672, 174);
        Ball[] balls = {ball1, ball2, ball3};

        Door door1 = new Door(gamePanel, tileManager, balls, 256, 264);
        Door door2 = new Door(gamePanel, tileManager, balls, 786, 136);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map2", player);
    }

    private GameLevel createMap3(){
        Player player = new Player(gamePanel, gamePanel.keyHandler, tileManager, 100, 100);
        Ball ball1 = new Ball(gamePanel, tileManager, player, 224, 142);
        Ball ball2 = new Ball(gamePanel, tileManager, player, 580, 334);
        Ball[] balls = {ball1, ball2};

        Door door1 = new Door(gamePanel, tileManager, balls, 256, 264);
        Door door2 = new Door(gamePanel, tileManager, balls, 786, 136);
        Door[] doors = {door1, door2};

        return new GameLevel(balls, doors, "map3", player);
    }



}

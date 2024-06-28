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

    private void loadNextLevel() {
        currentLevelNum++;
        switch (currentLevelNum) {
            case 1:
                System.out.println("created map1");
                currentLevel = createMap1();
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

    public void checkIfLevelWasCleared(){
        System.out.println("checking if level was cleared");
        boolean levelCleared = true;
        if (currentLevel != null) {
            for(Door door : currentLevel.getDoors()){
                if(!door.isClosed()){
                    System.out.println("is door closed: " + door.isClosed());
                    levelCleared = false;
                    break;
                }
            }
        }

        if(levelCleared || currentLevel == null){
            System.out.println("level was cleared");
            loadNextLevel();
        }
    }


    public GameLevel getCurrentLevel(){
        return currentLevel;
    }

    private GameLevel createMap1(){
        Player player = new Player(gamePanel, gamePanel.keyHandler, tileManager, 348, 256);
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

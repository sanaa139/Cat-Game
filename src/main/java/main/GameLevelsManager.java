package main;

import entity.*;
import tiles.TileManager;

public class GameLevelsManager {
    private final GamePanel gamePanel;
    private final TileManager tileManager;
    private final KeyHandler keyHandler;
    private GameLevel currentLevel;
    private int currentLevelNum;

    public GameLevelsManager(GamePanel gamePanel, TileManager tileManager, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.keyHandler = keyHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    private void loadNextLevel() {
        currentLevelNum++;
        switch (currentLevelNum) {
            case 1:
                currentLevel = createMap1();
                break;
            case 2:
                currentLevel = createMap2();
                break;
            case 3:
                currentLevel = createMap3();
                break;
        }
    }

    public void checkIfLevelWasCleared() {
        boolean levelCleared = true;
        if (currentLevel != null) {
            for(Entity entity : currentLevel.getEntities()){
                if(entity instanceof Door door){
                    if(!door.isClosed()){
                        levelCleared = false;
                        break;
                    }
                }
            }
        }

        if(levelCleared || currentLevel == null){
            if(currentLevelNum == 3){
                gamePanel.setState(GamePanel.GameState.MENU);
                //gamePanel.menu.getPlayButton().setVisible(true);
                currentLevel = null;
                currentLevelNum = 0;
            }else {
                loadNextLevel();
            }
        }
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public void restartCurrentLevel() {
        GameLevel gameLevel = getCurrentLevel();
        for(Entity entity : gameLevel.getEntities()){
            entity.restart();
        }
    }

    private GameLevel createMap1() {
        return GameLevel.builder()
                .withMap("map1")
                .withTileManager(tileManager)
                .withEntity(EntityCreation.createPlayer(keyHandler, 450, 256))
                .withEntity(EntityCreation.createBall(420, 276))
                .withEntity(EntityCreation.createBall(540, 212))
                .withEntity(EntityCreation.createDoor(500, 296))
                .withEntity(EntityCreation.createDoor(340, 232))
                .withEntity(EntityCreation.createBox(510, 323))
                .build();
    }

    private GameLevel createMap2() {
        return GameLevel.builder()
                .withMap("map2")
                .withTileManager(tileManager)
                .withEntity(EntityCreation.createPlayer(keyHandler, 224.0, 128.0))
                .withEntity(EntityCreation.createBall(260.0, 148.0))
                .withEntity(EntityCreation.createBall(585.0, 340.0))
                .withEntity(EntityCreation.createBall(672.0, 180.0))
                .withEntity(EntityCreation.createDoor(256.0, 264.0))
                .withEntity(EntityCreation.createDoor(786.0, 136.0))
                .withEntity(EntityCreation.createBox(290.0, 131.0))
                .build();
    }


    private GameLevel createMap3() {
        return GameLevel.builder()
                .withMap("map3")
                .withTileManager(tileManager)
                .withEntity(EntityCreation.createPlayer(keyHandler, 192, 320))
                .withEntity(EntityCreation.createBall(430, 404))
                .withEntity(EntityCreation.createBall(704, 148))
                .withEntity(EntityCreation.createBall(325, 212))
                .withEntity(EntityCreation.createDoor(520, 232))
                .withEntity(EntityCreation.createDoor(192, 296))
                .withEntity(EntityCreation.createBox(512, 163))
                .build();
    }
}

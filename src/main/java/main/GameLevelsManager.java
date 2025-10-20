package main;

import entity.*;
import tiles.TiledMapLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class GameLevelsManager {
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;
    private GameLevel currentLevel;
    private int currentLevelNum = -1;
    private final ArrayList<GameLevel> levels;

    public GameLevelsManager(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        levels = loadLevels();
    }

    private ArrayList<GameLevel> loadLevels(){
        ArrayList<GameLevel> levels = new ArrayList<>();

        List<Supplier<GameLevel>> levelSuppliers = List.of(
                this::createLevel1,
                this::createLevel2,
                this::createLevel3
        );

        for (int i = 0; i < levelSuppliers.size(); i++) {
            try {
                GameLevel level = levelSuppliers.get(i).get();
                levels.add(level);
                Logger.getLogger(getClass().getName()).info("Added Level " + (i + 1));
            } catch (IllegalStateException | IllegalArgumentException e ) {
                Logger.getLogger(getClass().getName())
                        .severe("Failed to create Level " + (i + 1) + ": " + e.getMessage());
            }
        }

        return levels;
    }


    private void loadNextLevel() {
        currentLevelNum++;
        currentLevel = levels.get(currentLevelNum);
        restartCurrentLevel();
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

        if(levelCleared && currentLevelNum == levels.size() - 1) {
            gamePanel.setState(GamePanel.GameState.MENU);
            currentLevel = null;
            currentLevelNum = -1;
        }else if(levelCleared || currentLevel == null){
            loadNextLevel();
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

    private GameLevel createLevel1() {
        return GameLevel.builder()
                .withMap("level1")
                .withTiledMapLoader(new TiledMapLoader())
                .withEntity(EntityCreation.createPlayer(keyHandler, 450, 256))
                .withEntity(EntityCreation.createBall(420, 276))
                .withEntity(EntityCreation.createBall(540, 212))
                .withEntity(EntityCreation.createDoor(500, 296))
                .withEntity(EntityCreation.createDoor(340, 232))
                .withEntity(EntityCreation.createBox(510, 323))
                .build();
    }

    private GameLevel createLevel2() {
        return GameLevel.builder()
                .withMap("level2")
                .withTiledMapLoader(new TiledMapLoader())
                .withEntity(EntityCreation.createPlayer(keyHandler, 224.0, 128.0))
                .withEntity(EntityCreation.createBall(260.0, 148.0))
                .withEntity(EntityCreation.createBall(585.0, 340.0))
                .withEntity(EntityCreation.createBall(672.0, 180.0))
                .withEntity(EntityCreation.createDoor(230.0, 264.0))
                .withEntity(EntityCreation.createDoor(786.0, 136.0))
                .withEntity(EntityCreation.createBox(290.0, 131.0))
                .build();
    }


    private GameLevel createLevel3() {
        return GameLevel.builder()
                .withMap("level3")
                .withTiledMapLoader(new TiledMapLoader())
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

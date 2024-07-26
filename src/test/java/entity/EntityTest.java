package entity;

import main.GamePanel;
import main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tiles.Tile;
import tiles.TileManagerGame;
import tiles.TileManagerMenu;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    GamePanel gamePanel;
    TileManagerGame tileManager;

    @BeforeEach
    void setUp(){
        gamePanel = new GamePanel();
        tileManager = new TileManagerGame();
    }

    @Test
    void moveTest() {
        Player player = new Player(tileManager, 348, 256);
        player.move(2, 0);
        assertEquals(player.getPositionX(), 350);
        assertEquals(player.getPositionY(), 256);
    }

    @Test
    void moveTest2() {
        Player player = new Player(tileManager, 348, 256);
        player.move(-2, 0);
        assertEquals(player.getPositionX(), 346);
        assertEquals(player.getPositionY(), 256);
    }

    @Test
    void applyGravityTest(){
        Player player = new Player(tileManager, 348, 230);
        int colIndex = (int) (player.getHitbox().getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);
        double oldPositionY = -1;
        while(player.positionY != oldPositionY) {
            oldPositionY = player.positionY;
            player.applyGravity(1.000002);
            colIndex = (int) (player.getHitbox().getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
            rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);
        }
        assertEquals(player.getPositionX(), 348);
        assertEquals(player.getPositionY(), 256);
    }

    @Test
    void applyGravityTest2(){
        Player player = new Player(tileManager, 462, 210);
        KeyHandler keyHandler = new KeyHandler();
        int colIndex = (int) (player.getHitbox().getLowerWallLine().getX2() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY2() + 1) / GamePanel.TILE_SIZE);
        while(!tileManager.getTilesArray()[colIndex][rowIndex].isCollisional() && (tileManager.getDecorationsTilesArray()[colIndex][rowIndex] == null || !tileManager.getDecorationsTilesArray()[colIndex][rowIndex].isCollisional())) {
            player.update(1.000002, keyHandler);
            colIndex = (int) (player.getHitbox().getLowerWallLine().getX2() / GamePanel.TILE_SIZE);
            rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY2() + 1) / GamePanel.TILE_SIZE);
        }
        assertEquals(player.getPositionX(), 462);
        assertEquals(player.getPositionY(), 224);
    }

    @Test
    void applyGravityTest3(){
        Player player = new Player(tileManager, 596, 170);
        KeyHandler keyHandler = new KeyHandler();
        int colIndex = (int) (player.getHitbox().getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);
        while(!tileManager.getTilesArray()[colIndex][rowIndex].isCollisional() && (tileManager.getDecorationsTilesArray()[colIndex][rowIndex] == null || !tileManager.getDecorationsTilesArray()[colIndex][rowIndex].isCollisional())) {
            player.update(1.000002, keyHandler);
            colIndex = (int) (player.getHitbox().getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
            rowIndex = (int) ((player.getHitbox().getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);
        }
        assertEquals(player.getPositionX(), 596);
        assertEquals(player.getPositionY(), 192);
    }
}
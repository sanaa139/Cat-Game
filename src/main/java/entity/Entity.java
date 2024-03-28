package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManager;

import java.awt.*;

public class Entity {
    protected double positionX, positionY, velocityX, velocityY;
    protected float gravity;
    protected int size;
    protected Image image;
    protected String direction;

    protected GamePanel gamePanel;
    protected TileManager tileManager;

    Entity(GamePanel gamePanel, TileManager tileManager){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        velocityX = 0;
        velocityY = 2;
        gravity = 0.2f;
    }

    public boolean canMove(double x, double y, String direction){
        int colIndex;
        if(direction.equals("right")){
            colIndex = (int) ((positionX + x + size) / gamePanel.getTileSize());
        }else{
            colIndex = (int) ((positionX + x) / gamePanel.getTileSize());
        }
        int rowIndex = (int) ((positionY + y) / gamePanel.getTileSize());

        if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()){
            return false;
        }
        return true;
    }

    public void move(double deltaX, double deltaY){
        positionX += deltaX;
        positionY += deltaY;
    }

    public void applyGravity(double deltaTime){
        int colIndex = (int) (positionX / gamePanel.getTileSize());
        int rowIndex = (int) ((positionY + velocityY + gamePanel.getTileSize()) / gamePanel.getTileSize());
        Tile tile = tileManager.getTilesArray()[colIndex][rowIndex];
        if(!tile.isCollisional()){
            velocityY += gravity * deltaTime;
        }else{
            velocityY = 0;
        }
    }


    public void jump(double deltaTime){
        velocityY = -2;
        System.out.println("Entity(x=" + positionX + ", y=" + positionY + ")");

        /*
        if(positionY >= 200){
            velocityX = 2;
            velocityY = -2;
            direction = "stay";
        }*/
    }
}


package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManager;

import java.awt.*;
import java.sql.SQLOutput;

public class Entity {
    protected double positionX, positionY, velocityX, velocityY;
    protected float gravityX, gravityY;
    protected int height, width;
    protected Image image;
    protected String direction;

    protected GamePanel gamePanel;
    protected TileManager tileManager;

    Entity(GamePanel gamePanel, TileManager tileManager){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        velocityX = 0;
        velocityY = -5;
        gravityX = 0;
        gravityY = 0.2f;

    }

    public boolean canMove(double x, double y, String direction){
        int colIndex;
        if(direction.equals("right")){
            colIndex = (int) ((positionX + x + width) / gamePanel.getTileSize());
        }else{
            colIndex = (int) ((positionX + x) / gamePanel.getTileSize());
        }
        int rowIndex = (int) ((positionY + y) / gamePanel.getTileSize());

        if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()){
            System.out.println("col: " + (colIndex + 1) + " row: " + (rowIndex + 1) + " collisional");
            return false;
        }
        System.out.println("col: " + (colIndex + 1) + " row: " + (rowIndex + 1) + " NOT collisional");
        return true;
    }

    public void move(double velX, double velY){
        positionX += velX;
        positionY += velY;
    }

    public void applyGravity(double deltaTime){
        int colIndex = (int) (positionX / gamePanel.getTileSize());
        int rowIndex = (int) ((positionY + velocityY + gamePanel.getTileSize()) / gamePanel.getTileSize());
        Tile tile = tileManager.getTilesArray()[colIndex][rowIndex];
        if(!tile.isCollisional()){
            velocityY += gravityY * deltaTime;
        }else{
            System.out.println("Collision with Tile(ypixels="+rowIndex*gamePanel.getTileSize()+"x=" +colIndex+"y="+rowIndex+"collisional="+tile.isCollisional()+"). Player Standing on Player(standingX="+(positionY + gamePanel.getTileSize())+")" );
            velocityY = 0;
        }
    }


    public void jump(double deltaTime){
        int colIndex = (int) ((positionX + (deltaTime * velocityX)) / gamePanel.getTileSize());
        int rowIndex = (int) ((positionY + (deltaTime * velocityY) + height) / gamePanel.getTileSize());

        if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()){
            double x1 = positionX;
            double y1 = positionY + gamePanel.getTileSize() - 1;
            double x2 = x1 + (deltaTime * velocityX);
            double y2 = y1 + (deltaTime * velocityY);
            double x3 = tileManager.getTilesArray()[colIndex][rowIndex].getUpperWallLine().getX1();
            double y3 = tileManager.getTilesArray()[colIndex][rowIndex].getUpperWallLine().getY1();
            double x4 = tileManager.getTilesArray()[colIndex][rowIndex].getUpperWallLine().getX2();
            double y4 = tileManager.getTilesArray()[colIndex][rowIndex].getUpperWallLine().getY2();

            double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if((x1 + alpha * (x2 - x1)) == (x3 + beta * (x4 - x3)) && (y1 + alpha * (y2 - y1)) == (y3 + alpha * (y4 - y3))){
                positionX = x1 + alpha * (x2 - x1);
                positionY = y1 + alpha * (y2 - y1) - height;

                velocityX = 0;
                velocityY = -5;
            }
        }else{
            positionX += deltaTime * velocityX;
            positionY += deltaTime * velocityY;

            velocityX += deltaTime * gravityX;
            velocityY += deltaTime * gravityY;
        }
    }
}


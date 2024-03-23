package entity;

import main.GamePanel;
import tiles.TileManager;

import java.awt.*;

public class Entity {
    protected double positionX, positionY, velocityX, velocityY;
    protected float gravity;
    protected Image image;
    protected String direction;
    protected boolean jumpEnd;

    protected GamePanel gamePanel;
    protected TileManager tileManager;

    Entity(GamePanel gamePanel, TileManager tileManager){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        velocityX = 0;
        velocityY = 0;
        gravity = 2f;
        jumpEnd = true;
    }

    public boolean canMove(double x, double y, String direction){
        int colIndex;
        if(direction.equals("right")){
            colIndex = (int) ((positionX + x + gamePanel.getTileSize() - 8) / gamePanel.getTileSize());
        }else{
            colIndex = (int) ((positionX + x + 6) / gamePanel.getTileSize());
        }
        int rowIndex = (int) ((positionY + y) / gamePanel.getTileSize());

        if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()){
            System.out.println("NIE MOZNA WEJSC");
            return false;
        }
        System.out.println("MOZNA WEJSC");
        return true;
    }

    public void move(double deltaX, double deltaY){
        positionX += deltaX;
        positionY += deltaY;
    }

    public void applyGravity(double deltaTime){

            positionY += velocityY;


        velocityY += gravity * deltaTime;
    }


    public void jump(double deltaTime){
        positionX += velocityX + velocityX * deltaTime;
        positionY += velocityY + velocityY * deltaTime;
        System.out.println("x:" + positionX);
        System.out.println("y:" + positionY);

        velocityY += gravity * deltaTime;

        /*
        if(positionY >= 200){
            velocityX = 2;
            velocityY = -2;
            direction = "stay";
        }*/
    }
}


package entity;

import main.GamePanel;
import main.KeyHandler;
import tiles.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    LoadImagesManager loadImagesManager;
    private int counter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager){
        super(gamePanel, tileManager);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        loadImagesManager = new LoadImagesManager("player");
        positionX = 400;
        positionY = 200;
        image = loadImagesManager.getLeft1();
        direction = "stay";
        counter = 0;
    }

    public void update(){
        if(keyHandler.leftPressed){
            direction = "left";
            if(this.canMove(-2, 0, direction)) {
                this.move(-2, 0);
            }
        }
        if(keyHandler.rightPressed){
            direction = "right";
            if(this.canMove(2, 0, direction)) {
                this.move(2, 0);
            }
        }
        if(!keyHandler.leftPressed && !keyHandler.rightPressed){
            direction = "stay";
        }
        if(keyHandler.upPressed || !jumpEnd){
            this.jumpEnd = false;
            System.out.println("JESTESMY W UP");
            this.jump((double) 1 /gamePanel.FPS);
        }
        this.applyGravity((double) 1/gamePanel.FPS);
        counter++;
    }


    public void draw(Graphics2D g2d){
        if(counter >= 10){
            switch(direction){

                case "left":
                    if(!image.equals(loadImagesManager.getLeft2())){
                        image = loadImagesManager.getLeft2();
                    }else{
                        image = loadImagesManager.getLeft1();
                    }
                    break;
                case "right":
                    if(!image.equals(loadImagesManager.getRight2())){
                        image = loadImagesManager.getRight2();
                    }else{
                        image = loadImagesManager.getRight1();
                    }
                    break;
                case "stay":
                    if(image.equals(loadImagesManager.getLeft1()) || image.equals(loadImagesManager.getLeft2())){
                        image = loadImagesManager.getLeftInactive();
                    }else if(image.equals(loadImagesManager.getRight1()) || image.equals(loadImagesManager.getRight2())){
                        image = loadImagesManager.getRightInactive();
                    }
            }
            counter = 0;
        }
        g2d.drawImage(image, (int)positionX, (int)positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}

package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    LoadImagesManager loadImagesManager;
    private int counter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        loadImagesManager = new LoadImagesManager("player");
        positionX = 100;
        positionY = 200;
        image = loadImagesManager.getLeft1();
        direction = "stay";
        counter = 0;
    }

    public void update(){
        if(keyHandler.leftPressed){
            direction = "left";
            this.move(-2, 0);
        }
        if(keyHandler.rightPressed){
            direction = "right";
            this.move(2, 0);
        }
        if(!keyHandler.leftPressed && !keyHandler.rightPressed){
            direction = "stay";
        }
        if(keyHandler.upPressed){
            direction = "up";
        }
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
                case "up":
                    System.out.println("JESTESMY W UP");
                    this.jump((double) 1 /gamePanel.FPS);

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
        g2d.drawImage(image, (int)positionX, (int)positionY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}

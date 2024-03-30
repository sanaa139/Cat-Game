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
        this.height = gamePanel.getTileSize();
        this.width = 10 * gamePanel.getScale();
        this.positionX = 320;
        this.positionY = 256;
        this.image = loadImagesManager.getLeft1();
        this.direction = "stay";
        counter = 0;
    }

    public void update(double deltaTime){
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
        if(keyHandler.upPressed){
            // if standing in place
            System.out.println("JESTESMY W UP");
            this.jump(deltaTime);
        }
        //this.applyGravity(deltaTime);
        //this.applyVelocity(deltaTime);
        counter++;
    }

    private void applyVelocity(double deltaTime) {
        this.positionX += deltaTime*this.velocityX;
        this.positionY += deltaTime*this.velocityY;
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
        g2d.drawImage(image, (int)positionX, (int)positionY, this.width, this.height, null);
    }

    public void restart() {
        positionX = 500;
        positionY = 125;
    }
}

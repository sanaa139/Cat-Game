package entity;

import main.GamePanel;
import main.KeyHandler;
import tiles.Tile;
import tiles.TileManager;
import tiles.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;
    LoadImagesManager loadImagesManager;
    public Ball ball;
    private int counter;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, TileManager tileManager){
        super(gamePanel, tileManager);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        loadImagesManager = new LoadImagesManager("player");
        this.height = gamePanel.getTileSize();
        this.width = gamePanel.getTileSize();
        this.positionX = 348;
        this.positionY = 200;

        updateHitBox();

        this.image = loadImagesManager.getLeft1();
        this.direction = "stay";
        counter = 0;
    }

    public void updateHitBox(){
        int n = 7;
        this.hitbox.setLeftWallLine(new Vector(this.positionX + n, this.positionY, this.positionX + n, this.positionY + height - 1));
        this.hitbox.setRightWallLine(new Vector(this.positionX + width - n, this.positionY, this.positionX + width - n, this.positionY + height - 1));
        this.hitbox.setUpperWallLine(new Vector(this.positionX + n, this.positionY, this.positionX + width - n, this.positionY));
        this.hitbox.setLowerWallLine(new Vector(this.positionX + n, this.positionY + height, this.positionX + width - n, this.positionY + height));
    }

    public void update(double deltaTime){
        if(keyHandler.leftPressed){
            direction = "left";
            this.canMove(-2,0,direction);
        }
        if(keyHandler.rightPressed){
            direction = "right";
            this.canMove(2,0,direction);
        }
        if(!keyHandler.leftPressed && !keyHandler.rightPressed){
            direction = "stay";
        }

        if(keyHandler.upPressed){
            this.jump();
        }
        this.applyGravity(deltaTime);
        counter++;
        updateHitBox();
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
                    break;
            }
            counter = 0;
        }
        g2d.drawImage(image, (int)positionX, (int)positionY, this.width, this.height, null);
        g2d.setColor(Color.RED);
        g2d.fillRect((int) this.hitbox.getLeftWallLine().getX1(), (int) this.hitbox.getLeftWallLine().getY1(), (int) (this.hitbox.getUpperWallLine().getX2() - this.hitbox.getUpperWallLine().getX1()),  (int) (this.hitbox.getRightWallLine().getY2() - this.hitbox.getLeftWallLine().getY1()));
    }

    public void restart() {
        positionX = 500;
        positionY = 125;
    }

    private void jump(){
        if(velocityY == 0){
            velocityY = -5;
        }
    }
}

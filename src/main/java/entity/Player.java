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
        this.padding = 7;

        updateHitBox();

        this.image = loadImagesManager.getLeft1();
        this.direction = "stay";
        counter = 0;
    }

    public void updateHitBox(){
        this.hitbox.setLeftWallLine(new Vector(this.positionX + this.padding, this.positionY, this.positionX + this.padding, this.positionY + height - 1));
        this.hitbox.setRightWallLine(new Vector(this.positionX + width - this.padding, this.positionY, this.positionX + width - this.padding, this.positionY + height - 1));
        this.hitbox.setUpperWallLine(new Vector(this.positionX + this.padding, this.positionY, this.positionX + width - this.padding, this.positionY));
        this.hitbox.setLowerWallLine(new Vector(this.positionX + this.padding, this.positionY + height, this.positionX + width - this.padding, this.positionY + height));
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
        this.checkForCeilingCollision(deltaTime);
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
        //g2d.setColor(Color.RED);
        //g2d.fillRect((int) this.hitbox.getLeftWallLine().getX1(), (int) this.hitbox.getLeftWallLine().getY1(), (int) (this.hitbox.getUpperWallLine().getX2() - this.hitbox.getUpperWallLine().getX1()),  (int) (this.hitbox.getRightWallLine().getY2() - this.hitbox.getLeftWallLine().getY1()));
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

    private void checkForCeilingCollision(double deltaTime) {
        int colIndex = (int) (hitbox.getUpperWallLine().getX1() / gamePanel.getTileSize());
        int rowIndex = (int) ((hitbox.getUpperWallLine().getY1() + velocityY) / gamePanel.getTileSize());
        int colIndex2 = (int) (hitbox.getUpperWallLine().getX2() / gamePanel.getTileSize());
        int rowIndex2 = (int) ((hitbox.getUpperWallLine().getY2() + velocityY) / gamePanel.getTileSize());
        if(velocityY != 0) {
            if (tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()) {
                checkForCeiling(hitbox.getUpperWallLine().getX1(), hitbox.getUpperWallLine().getY1(), deltaTime, tileManager.getTilesArray(), colIndex, rowIndex, "left");
            } else if (tileManager.getTilesArray()[colIndex2][rowIndex2].isCollisional()) {
                checkForCeiling(hitbox.getUpperWallLine().getX2(), hitbox.getUpperWallLine().getY2(), deltaTime, tileManager.getTilesArray(), colIndex2, rowIndex2, "right");
            } else {
                if (tileManager.getDecorationsTilesArray()[colIndex][rowIndex] != null) {
                    checkForCeiling(hitbox.getUpperWallLine().getX1(), hitbox.getUpperWallLine().getY1(), deltaTime, tileManager.getDecorationsTilesArray(), colIndex, rowIndex, "left");
                } else if (tileManager.getDecorationsTilesArray()[colIndex2][rowIndex2] != null) {
                    checkForCeiling(hitbox.getUpperWallLine().getX2(), hitbox.getUpperWallLine().getY2(), deltaTime, tileManager.getDecorationsTilesArray(), colIndex2, rowIndex2, "right");
                }
            }
        }
    }


    private void checkForCeiling(double x1, double y1, double deltaTime, Tile[][] arr, int colIndex, int rowIndex, String direction){
        double x2 = x1;
        double y2 = y1 + (deltaTime * velocityY);
        double x3 = arr[colIndex][rowIndex].getLowerWallLine().getX1();
        double y3 = arr[colIndex][rowIndex].getLowerWallLine().getY1();
        double x4 = arr[colIndex][rowIndex].getLowerWallLine().getX2();
        double y4 = arr[colIndex][rowIndex].getLowerWallLine().getY2();

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;
            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                if(direction.equals("left")) {
                    positionX = x1 + alpha * (x2 - x1) - padding;
                }else{
                    positionX = x1 + alpha * (x2 - x1) - padding - (hitbox.getUpperWallLine().getX2() - hitbox.getUpperWallLine().getX1()) + 1;
                }
                positionY = y1 + alpha * (y2 - y1) + 1;
                velocityY = 0.5;
            }
        }
    }
}

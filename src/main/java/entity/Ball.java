package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManagerGame;
import tiles.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Ball extends Entity {
    private Player player;
    private boolean enteredTheDoor, touched, isOnTheFloor;
    private String direction;
    private int currentMOvement;
    private int maximumMovement = 40;

    public Ball(GamePanel gamePanel, TileManagerGame tileManager, Player player, double positionX, double positionY) {
        super(gamePanel, tileManager);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.player = player;
        this.positionX = positionX;
        this.positionY = positionY;

        width = 20;
        height = 18;
        enteredTheDoor = false;
        updateHitBox();
    }

    public void update(double deltaTime) {
        this.applyGravity(deltaTime);
        touched();
        onTheFloor();
        jumpedOn(deltaTime);
        updateHitBox();
    }

    public void updateHitBox() {
        this.hitbox.setLeftWallLine(new Vector(this.positionX, this.positionY, this.positionX, this.positionY + height - 1));
        this.hitbox.setRightWallLine(new Vector(this.positionX + width - 1, this.positionY, this.positionX + width - 1, this.positionY + height - 1));
        this.hitbox.setUpperWallLine(new Vector(this.positionX, this.positionY, this.positionX + width - 1, this.positionY));
        this.hitbox.setLowerWallLine(new Vector(this.positionX, this.positionY + height, this.positionX + width - 1, this.positionY + height));
    }

    private void touched() {
        double x1 = player.positionX + player.width - 5;
        double y1 = player.positionY + player.height - 1;
        double x2 = x1 + 1;
        double y2 = y1;
        double x3 = positionX;
        double y3 = positionY;
        double x4 = positionX;
        double y4 = positionY + height - 1;

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if (divider != 0) {
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if (alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                System.out.println("TOUCHED");
                if (!move(2, 0)) {
                    direction = "left";
                    touched = true;
                }
            }
        }

        x1 = player.positionX + 4;
        x2 = x1 - 1;
        x3 = positionX + width - 1;
        y3 = positionY;
        x4 = positionX + width - 1;
        y4 = positionY + height - 1;

        divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if (divider != 0) {
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if (alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                System.out.println("TOUCHED");
                if (!move(-2, 0)) {
                    direction = "right";
                    touched = true;
                }
            }
        }
    }

    private void onTheFloor() {
        int colIndex = (int) (hitbox.getLowerWallLine().getX1() / gamePanel.getTileSize());
        int rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + 0.1) / gamePanel.getTileSize());

        if(!tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()){
            touched = false;

        }

        isOnTheFloor = tileManager.getTilesArray()[colIndex][rowIndex].isCollisional();

    }

    private boolean isOnTheFloor(int colIndex, int rowIndex, Tile[][] arr){
        double x1 = hitbox.getLowerWallLine().getX1();
        double y1 = hitbox.getLowerWallLine().getY1();
        double x2 = x1;
        double y2 = y1 + 0.1;
        double x3 = arr[colIndex][rowIndex].getUpperWallLine().getX1();
        double y3 = arr[colIndex][rowIndex].getUpperWallLine().getY1();
        double x4 = arr[colIndex][rowIndex].getUpperWallLine().getX2();
        double y4 = arr[colIndex][rowIndex].getUpperWallLine().getY2();

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

        if(divider != 0) {
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;
            if (alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                return true;
            }
        }
        return false;

    }
    private void jumpedOn(double deltaTime){
        double x1 = player.positionX + (double) player.width /2;
        double y1 = player.positionY + player.height - 1;
        double x2 = x1 + (deltaTime * player.velocityX);
        double y2 = y1 + (deltaTime * player.velocityY);
        double x3 = positionX;
        double y3 = positionY;
        double x4 = positionX + width - 1;
        double y4 = positionY;

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                player.velocityX = 0;
                player.velocityY = -3 - 0.5 * player.velocityY;
            }
        }
    }

    public void draw(Graphics2D g2d){
        if(!enteredTheDoor) {
            if(touched && isOnTheFloor) {
                if(direction.equals("right")){
                    this.positionX += 6;
                }else if(direction.equals("left")){
                    this.positionX -= 6;
                }
                this.currentMOvement += 6;


                if(currentMOvement >= maximumMovement){
                    touched = false;
                }

            }
            g2d.drawImage(image, (int) positionX, (int) positionY, width, height, null);
            //g2d.setColor(Color.GREEN);
            //g2d.fillRect((int) this.hitbox.getLeftWallLine().getX1(), (int) this.hitbox.getLeftWallLine().getY1(), (int) (this.hitbox.getUpperWallLine().getX2() - this.hitbox.getUpperWallLine().getX1()),  (int) (this.hitbox.getRightWallLine().getY2() - this.hitbox.getLeftWallLine().getY1()));
        }
    }

    public double getPositionX(){
        return positionX;
    }

    public double getPositionY(){
        return positionY;
    }

    public void setEnteredTheDoor(boolean enteredTheDoor){
        this.enteredTheDoor = enteredTheDoor;
    }
}

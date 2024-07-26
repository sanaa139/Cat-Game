package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManagerGame;
import tiles.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Ball extends Entity {
    private final Player player;
    private boolean enteredTheDoor, bounceBackFromHittingAWall, isOnTheFloor;
    private String bounceBackFromHittingAWallDirection;
    private int numberOfStepsTakenFromBouncingBack;
    private final int maximumNumberOfStepsAllowedFromBouncingBack = 40;

    public Ball(TileManagerGame tileManager, Player player, double positionX, double positionY) {
        super(tileManager);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/entity/ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.player = player;
        this.positionX = positionX;
        this.positionY = positionY;
        width = image.getWidth(null);
        height = image.getHeight(null);

        enteredTheDoor = false;
        updateHitBox();
    }

    public void update(double deltaTime) {
        this.applyGravity(deltaTime);
        performMovementIfTouchedByPlayer();
        checkIfIsOnTheFloor();
        jumpedOn(deltaTime);
        updateHitBox();
    }

    public void updateHitBox() {
        double x1 = this.positionX;
        double x2 = this.positionX + width - 1;
        double y1 = this.positionY;
        double y2 = this.positionY + height - 1;
        this.hitbox.setLeftWallLine(new Vector(x1, y1, x1, y2));
        this.hitbox.setRightWallLine(new Vector(x2, y1, x2, y2));
        this.hitbox.setUpperWallLine(new Vector(x1, y1, x2, y1));
        this.hitbox.setLowerWallLine(new Vector(x1, y2, x2, y2));
    }

    private void performMovementIfTouchedByPlayer() {
        if(checkIfBallWasTouchedFromItsLeftSide()) return;
        checkIfBallWasTouchedFromItsRightSide();

    }

    private boolean checkIfBallWasTouchedFromItsLeftSide(){
        double x1 = player.getHitbox().getLowerWallLine().getX2();
        double y1 = player.getHitbox().getLowerWallLine().getY2();
        double x2 = x1 + 1;
        double y2 = y1;
        double x3 = hitbox.getLeftWallLine().getX1();
        double y3 = hitbox.getLeftWallLine().getY1();
        double x4 = hitbox.getLeftWallLine().getX2();
        double y4 = hitbox.getLeftWallLine().getY2();

        return calculateIfBallGotTouched(x1, y1, x2, y2, x3, y3, x4, y4, "left", 2);
    }

    private boolean checkIfBallWasTouchedFromItsRightSide(){
        double x1 = player.getHitbox().getLowerWallLine().getX1();
        double y1 = player.getHitbox().getLowerWallLine().getY1();
        double x2 = x1 - 1;
        double y2 = y1;
        double x3 = hitbox.getRightWallLine().getX1();
        double y3 = hitbox.getRightWallLine().getY1();
        double x4 = hitbox.getRightWallLine().getX2();
        double y4 = hitbox.getRightWallLine().getY2();

        return calculateIfBallGotTouched(x1, y1, x2, y2, x3, y3, x4, y4, "right", -2);
    }

    private boolean calculateIfBallGotTouched(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, String direction, double movementOnTheXAxis){
        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if (divider != 0) {
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if (alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                if (!move(movementOnTheXAxis, 0)) {
                    bounceBackFromHittingAWallDirection = direction;
                    bounceBackFromHittingAWall = true;
                    return true;
                }
            }
        }
        return false;
    }

    private void checkIfIsOnTheFloor() {
        int colIndex = (int) (hitbox.getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);

        int colIndex2 = (int) (hitbox.getLowerWallLine().getX2() / GamePanel.TILE_SIZE);
        int rowIndex2 = (int) ((hitbox.getLowerWallLine().getY2() + 1) / GamePanel.TILE_SIZE);

        if(!tileManager.getTilesArray()[colIndex][rowIndex].isCollisional() && !tileManager.getTilesArray()[colIndex2][rowIndex2].isCollisional()){
            bounceBackFromHittingAWall = false;
        }

        isOnTheFloor = tileManager.getTilesArray()[colIndex][rowIndex].isCollisional() || tileManager.getTilesArray()[colIndex2][rowIndex2].isCollisional();
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
            if(bounceBackFromHittingAWall && isOnTheFloor) {
                System.out.println("touched and on the floor");
                if(bounceBackFromHittingAWallDirection.equals("right")){
                    this.positionX += 6;
                }else if(bounceBackFromHittingAWallDirection.equals("left")){
                    this.positionX -= 6;
                }
                this.numberOfStepsTakenFromBouncingBack += 6;

                if(numberOfStepsTakenFromBouncingBack >= maximumNumberOfStepsAllowedFromBouncingBack){
                    numberOfStepsTakenFromBouncingBack = 0;
                    bounceBackFromHittingAWall = false;
                }

            }
            g2d.drawImage(image, (int) positionX, (int) positionY, width, height, null);
            g2d.setColor(Color.GREEN);
            g2d.fillRect((int) this.hitbox.getLeftWallLine().getX1(), (int) this.hitbox.getLeftWallLine().getY1(), (int) (this.hitbox.getUpperWallLine().getX2() - this.hitbox.getUpperWallLine().getX1()),  (int) (this.hitbox.getRightWallLine().getY2() - this.hitbox.getLeftWallLine().getY1()));
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

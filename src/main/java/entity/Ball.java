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
        super(tileManager, positionX, positionY);
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/entity/ball.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.player = player;
        setWidth(getImage().getWidth(null));
        setHeight(getImage().getHeight(null));

        enteredTheDoor = false;
        updateHitBox();
    }

    public void update(double deltaTime) {
        applyGravity(deltaTime);
        performMovementIfTouchedByPlayer();
        checkIfIsOnTheFloor();
        jumpedOn(deltaTime);
        updateHitBox();
    }

    public void updateHitBox() {
        double x1 = getPositionX();
        double x2 = getPositionX() + getWidth() - 1;
        double y1 = getPositionY();
        double y2 = getPositionY() + getHeight() - 1;
        getHitbox().setLeftWallLine(new Vector(x1, y1, x1, y2));
        getHitbox().setRightWallLine(new Vector(x2, y1, x2, y2));
        getHitbox().setUpperWallLine(new Vector(x1, y1, x2, y1));
        getHitbox().setLowerWallLine(new Vector(x1, y2, x2, y2));
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
        double x3 = getHitbox().getLeftWallLine().getX1();
        double y3 = getHitbox().getLeftWallLine().getY1();
        double x4 = getHitbox().getLeftWallLine().getX2();
        double y4 = getHitbox().getLeftWallLine().getY2();

        return calculateIfBallGotTouched(x1, y1, x2, y2, x3, y3, x4, y4, "left", 2);
    }

    private boolean checkIfBallWasTouchedFromItsRightSide(){
        double x1 = player.getHitbox().getLowerWallLine().getX1();
        double y1 = player.getHitbox().getLowerWallLine().getY1();
        double x2 = x1 - 1;
        double y2 = y1;
        double x3 = getHitbox().getRightWallLine().getX1();
        double y3 = getHitbox().getRightWallLine().getY1();
        double x4 = getHitbox().getRightWallLine().getX2();
        double y4 = getHitbox().getRightWallLine().getY2();

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
        int colIndex = (int) (getHitbox().getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((getHitbox().getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);

        int colIndex2 = (int) (getHitbox().getLowerWallLine().getX2() / GamePanel.TILE_SIZE);
        int rowIndex2 = (int) ((getHitbox().getLowerWallLine().getY2() + 1) / GamePanel.TILE_SIZE);

        if(!getTileManager().getTilesArray()[colIndex][rowIndex].isCollisional() && !getTileManager().getTilesArray()[colIndex2][rowIndex2].isCollisional()){
            bounceBackFromHittingAWall = false;
        }

        isOnTheFloor = getTileManager().getTilesArray()[colIndex][rowIndex].isCollisional() || getTileManager().getTilesArray()[colIndex2][rowIndex2].isCollisional();
    }


    private void jumpedOn(double deltaTime){
        double x1 = player.getPositionX() + (double) player.getWidth() /2;
        double y1 = player.getPositionY() + player.getHeight() - 1;
        double x2 = x1 + (deltaTime * player.getVelocityX());
        double y2 = y1 + (deltaTime * player.getVelocityY());
        double x3 = getPositionX();
        double y3 = getPositionY();
        double x4 = getPositionX() + getWidth() - 1;
        double y4 = getPositionY();

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                player.setVelocityX(0);
                player.setVelocityY(-3 - 0.5 * player.getVelocityY());
            }
        }
    }

    public void draw(Graphics2D g2d){
        if(!enteredTheDoor) {
            if(bounceBackFromHittingAWall && isOnTheFloor) {
                System.out.println("touched and on the floor");
                if(bounceBackFromHittingAWallDirection.equals("right")){
                    setPositionX(getPositionX() + 6);
                }else if(bounceBackFromHittingAWallDirection.equals("left")){
                    setPositionX(getPositionX() - 6);
                }
                numberOfStepsTakenFromBouncingBack += 6;

                if(numberOfStepsTakenFromBouncingBack >= maximumNumberOfStepsAllowedFromBouncingBack){
                    numberOfStepsTakenFromBouncingBack = 0;
                    bounceBackFromHittingAWall = false;
                }

            }
            g2d.drawImage(getImage(), (int) getPositionX(), (int) getPositionY(), getWidth(), getHeight(), null);
        }
    }

    public void setEnteredTheDoor(boolean enteredTheDoor){
        this.enteredTheDoor = enteredTheDoor;
    }
}

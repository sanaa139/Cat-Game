package entity;

import main.GamePanel;
import main.KeyHandler;
import tiles.Tile;
import tiles.TileManagerGame;
import tiles.Vector;
import java.awt.*;

public class Player extends Entity{
    private final LoadImagesManager loadImagesManager;
    private int counter;

    public Player(TileManagerGame tileManager, double positionX, double positionY){
        super(tileManager, positionX, positionY);
        loadImagesManager = new LoadImagesManager("player");

        setHeight(GamePanel.TILE_SIZE);
        setWidth(GamePanel.TILE_SIZE);
        setPadding(7);
        updateHitBox();

        setImage(loadImagesManager.getLeftInactive());
        setDirection("stay");
        counter = 0;
    }

    private void updateHitBox(){
        double x1 = getPositionX() + getPadding();
        double x2 = getPositionX() + getWidth() - getPadding();
        double y1 = getPositionY();
        double y2 = getPositionY() + getHeight() - 1;
        getHitbox().setLeftWallLine(new Vector(x1, y1, x1, y2));
        getHitbox().setRightWallLine(new Vector(x2, y1, x2, y2));
        getHitbox().setUpperWallLine(new Vector(x1, y1, x2, y1));
        getHitbox().setLowerWallLine(new Vector(x1, y2, x2, y2));
    }

    public void update(double deltaTime, KeyHandler keyHandler){
        if(keyHandler.leftPressed){
            setDirection("left");
            move(-2,0);
        }
        if(keyHandler.rightPressed){
            setDirection("right");
            move(2,0);
        }
        if(!keyHandler.leftPressed && !keyHandler.rightPressed){
            setDirection("stay");
        }
        if(keyHandler.upPressed){
            jump();
        }
        checkForCeilingCollision(deltaTime);
        applyGravity(deltaTime);
        counter++;
        updateHitBox();
    }

    public void draw(Graphics2D g2d){
        if(counter >= 10){
            switch(getDirection()){
                case "left":
                    if(getImage().equals(loadImagesManager.getLeft1())){
                        setImage(loadImagesManager.getLeft2());
                    }else if(getImage().equals(loadImagesManager.getLeft2())){
                        setImage(loadImagesManager.getLeft3());
                    }else if(getImage().equals(loadImagesManager.getLeft3())){
                        setImage(loadImagesManager.getLeft4());
                    }else if(getImage().equals(loadImagesManager.getLeft4())){
                        setImage(loadImagesManager.getLeft1());
                    }else{
                        setImage(loadImagesManager.getLeft1());
                    }
                    break;
                case "right":
                    if(getImage().equals(loadImagesManager.getRight1())){
                        setImage(loadImagesManager.getRight2());
                    }else if(getImage().equals(loadImagesManager.getRight2())){
                        setImage(loadImagesManager.getRight3());
                    }else if(getImage().equals(loadImagesManager.getRight3())){
                        setImage(loadImagesManager.getRight4());
                    }else if(getImage().equals(loadImagesManager.getRight4())){
                        setImage(loadImagesManager.getRight1());
                    }else{
                        setImage(loadImagesManager.getRight1());
                    }
                    break;
                case "stay":
                    if(getImage().equals(loadImagesManager.getLeft1()) || getImage().equals(loadImagesManager.getLeft2()) || getImage().equals(loadImagesManager.getLeft3()) || getImage().equals(loadImagesManager.getLeft4())){
                        setImage(loadImagesManager.getLeftInactive());
                    }else if(getImage().equals(loadImagesManager.getRight1()) || getImage().equals(loadImagesManager.getRight2()) || getImage().equals(loadImagesManager.getRight3()) || getImage().equals(loadImagesManager.getRight4())){
                        setImage(loadImagesManager.getRightInactive());
                    }
                    break;
            }
            counter = 0;
        }
        g2d.drawImage(getImage(), (int)getPositionX(), (int)getPositionY(), getWidth(), getHeight(), null);
    }

    private void jump(){
        if(getVelocityY() == 0){
            setVelocityY(-5);
        }
    }

    private void checkForCeilingCollision(double deltaTime) {
        int colIndex = (int) (getHitbox().getUpperWallLine().getX1() / GamePanel.TILE_SIZE);
        int rowIndex = (int) ((getHitbox().getUpperWallLine().getY1() + getVelocityY()) / GamePanel.TILE_SIZE);
        int colIndex2 = (int) (getHitbox().getUpperWallLine().getX2() / GamePanel.TILE_SIZE);
        int rowIndex2 = (int) ((getHitbox().getUpperWallLine().getY2() + getVelocityY()) / GamePanel.TILE_SIZE);
        if(getVelocityY() != 0) {
            if (getTileManager().getTilesArray()[colIndex][rowIndex].isCollisional()) {
                checkIfHitCeiling(getHitbox().getUpperWallLine().getX1(), getHitbox().getUpperWallLine().getY1(), deltaTime, getTileManager().getTilesArray(), colIndex, rowIndex, "left");
            } else if (getTileManager().getTilesArray()[colIndex2][rowIndex2].isCollisional()) {
                checkIfHitCeiling(getHitbox().getUpperWallLine().getX2(), getHitbox().getUpperWallLine().getY2(), deltaTime, getTileManager().getTilesArray(), colIndex2, rowIndex2, "right");
            } else {
                if (getTileManager().getDecorationsTilesArray()[colIndex][rowIndex] != null && getTileManager().getDecorationsTilesArray()[colIndex][rowIndex].isCollisional()) {
                    checkIfHitCeiling(getHitbox().getUpperWallLine().getX1(), getHitbox().getUpperWallLine().getY1(), deltaTime, getTileManager().getDecorationsTilesArray(), colIndex, rowIndex, "left");
                } else if (getTileManager().getDecorationsTilesArray()[colIndex2][rowIndex2] != null && getTileManager().getDecorationsTilesArray()[colIndex2][rowIndex2].isCollisional()) {
                    checkIfHitCeiling(getHitbox().getUpperWallLine().getX2(), getHitbox().getUpperWallLine().getY2(), deltaTime, getTileManager().getDecorationsTilesArray(), colIndex2, rowIndex2, "right");
                }
            }
        }
    }

    private void checkIfHitCeiling(double x1, double y1, double deltaTime, Tile[][] arr, int colIndex, int rowIndex, String direction){
        double x2 = x1;
        double y2 = y1 + (deltaTime * getVelocityY());
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
                    setPositionX(x1 + alpha * (x2 - x1) - getPadding());
                }else{
                    setPositionX(x1 + alpha * (x2 - x1) - getPadding() - (getHitbox().getUpperWallLine().getX2() - getHitbox().getUpperWallLine().getX1()) + 1);
                }
                setPositionY(y1 + alpha * (y2 - y1) + 1);
                setVelocityY(0.5);
            }
        }
    }
}
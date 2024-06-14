package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManager;
import tiles.Vector;

import java.awt.*;
import java.sql.SQLOutput;

public class Entity {
    protected double positionX, positionY, velocityX, velocityY;
    protected float gravityX, gravityY;
    protected int height, width;
    protected Image image;
    protected String direction;

    protected GamePanel gamePanel;
    protected TileManager tileManager;

    protected HitBox hitbox;
    protected int padding;

    Entity(GamePanel gamePanel, TileManager tileManager){
        this.gamePanel = gamePanel;
        this.tileManager = tileManager;
        this.hitbox = new HitBox();
        velocityX = 0;
        velocityY = 0;
        gravityX = 0;
        gravityY = 0.2f;
    }


    private double linesIntersectionPoint(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                return alpha;
            }
        }
        return -1;
    }

    private boolean doLinesIntersects(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, String direction){
        double alpha = linesIntersectionPoint(x1, y1, x2, y2, x3, y3, x4, y4);
        if(alpha != -1){
            if(direction.equals("left")) {
                positionX = x1 + alpha * (x2 - x1) + 1 - padding;
            }else {
                positionX = x1 + alpha * (x2 - x1) - width - 1 + padding;
            }

            if(y2 > positionY){
                positionY = y1 + alpha * (y2 - y1) - height + 1;
            }else{
                positionY = y1 + alpha * (y2 - y1);
            }
            return false;
        }
        return true;
    }

    private boolean doLinesIntersects(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
        double alpha = linesIntersectionPoint(x1, y1, x2, y2, x3, y3, x4, y4);
        if(alpha != -1){
            return true;
        }
        return false;
    }

    public boolean canMove(double velocityX, double velocityY){
        if(velocityX < 0) {
            int colIndex = (int) ((hitbox.getLeftWallLine().getX1() + velocityX) / gamePanel.getTileSize());
            int rowIndex = (int) ((hitbox.getLeftWallLine().getY1() + velocityY) / gamePanel.getTileSize());
            int rowIndex2 = (int) ((hitbox.getLeftWallLine().getY2() + velocityY) / gamePanel.getTileSize());

            if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()) {
                return doLinesIntersects(
                        hitbox.getLeftWallLine().getX1(),
                        hitbox.getLeftWallLine().getY1(),
                        hitbox.getLeftWallLine().getX1() + velocityX,
                        hitbox.getLeftWallLine().getY1() + velocityY,
                        tileManager.getTilesArray()[colIndex][rowIndex].getRightWallLine().getX1(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getRightWallLine().getY1(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getRightWallLine().getX2(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getRightWallLine().getY2(),
                        "left"
                );
            }else if(tileManager.getTilesArray()[colIndex][rowIndex2].isCollisional()){
                return doLinesIntersects(
                        hitbox.getLeftWallLine().getX2(),
                        hitbox.getLeftWallLine().getY2(),
                        hitbox.getLeftWallLine().getX2() + velocityX,
                        hitbox.getLeftWallLine().getY2() + velocityY,
                        tileManager.getTilesArray()[colIndex][rowIndex2].getRightWallLine().getX1(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getRightWallLine().getY1(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getRightWallLine().getX2(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getRightWallLine().getY2(),
                        "left"
                );
            }else {
                for (int i = 0; i <= height - 1; i++) {
                    rowIndex = (int) ((positionY + i) / gamePanel.getTileSize());
                    if(tileManager.getDecorationsTilesArray()[colIndex][rowIndex] != null && tileManager.getDecorationsTilesArray()[colIndex][rowIndex].isCollisional()) {
                        if (doLinesIntersects(
                                hitbox.getLeftWallLine().getX1(),
                                hitbox.getLeftWallLine().getY1() + i,
                                hitbox.getLeftWallLine().getX1() + velocityX,
                                hitbox.getLeftWallLine().getY1() + i + velocityY,
                                tileManager.getDecorationsTilesArray()[colIndex][rowIndex].getRightWallLine().getX1(),
                                tileManager.getDecorationsTilesArray()[colIndex][rowIndex].getRightWallLine().getY1(),
                                tileManager.getDecorationsTilesArray()[colIndex][rowIndex].getRightWallLine().getX2(),
                                tileManager.getDecorationsTilesArray()[colIndex][rowIndex].getRightWallLine().getY2()
                        )) {
                            return false;
                        }
                    }

                }
                move(velocityX, velocityY);
                return true;
            }
        }else{
            int colIndex = (int) ((hitbox.getRightWallLine().getX1() + velocityX) / gamePanel.getTileSize());
            int rowIndex = (int) ((hitbox.getRightWallLine().getY1() + velocityY) / gamePanel.getTileSize());
            int rowIndex2 = (int) ((hitbox.getRightWallLine().getY2() + velocityY) / gamePanel.getTileSize());

            if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()) {
                return doLinesIntersects(
                        hitbox.getRightWallLine().getX1(),
                        hitbox.getRightWallLine().getY1(),
                        hitbox.getRightWallLine().getX1() + velocityX,
                        hitbox.getRightWallLine().getY1() + velocityY,
                        tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getX1(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getY1(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getX2(),
                        tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getY2(),
                        "right"
                );
            }else if(tileManager.getTilesArray()[colIndex][rowIndex2].isCollisional()){
                return doLinesIntersects(
                        hitbox.getRightWallLine().getX2(),
                        hitbox.getRightWallLine().getY2(),
                        hitbox.getRightWallLine().getX2() + velocityX,
                        hitbox.getRightWallLine().getY2() + velocityY,
                        tileManager.getTilesArray()[colIndex][rowIndex2].getLeftWallLine().getX1(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getLeftWallLine().getY1(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getLeftWallLine().getX2(),
                        tileManager.getTilesArray()[colIndex][rowIndex2].getLeftWallLine().getY2(),
                        "right"
                );
            }else {
                for (int i = 0; i <= height - 1; i++) {
                    rowIndex = (int) ((positionY + i) / gamePanel.getTileSize());
                    if (tileManager.getDecorationsTilesArray()[colIndex][rowIndex] != null && tileManager.getDecorationsTilesArray()[colIndex][rowIndex].isCollisional()) {
                        if (doLinesIntersects(
                                hitbox.getRightWallLine().getX1(),
                                hitbox.getRightWallLine().getY1() + i,
                                hitbox.getRightWallLine().getX1() + velocityX,
                                hitbox.getRightWallLine().getY1() + i + velocityY,
                                tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getX1(),
                                tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getY1(),
                                tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getX2(),
                                tileManager.getTilesArray()[colIndex][rowIndex].getLeftWallLine().getY2()
                        )) {
                            return false;
                        }
                    }

                }
                move(velocityX, velocityY);
                return true;
            }
        }
    }

    public void move(double velX, double velY){
        positionX += velX;
        positionY += velY;
    }

    protected void applyGravity(double deltaTime){
        if(velocityY == 0){
            int colIndex = (int) (hitbox.getLowerWallLine().getX1() / gamePanel.getTileSize());
            int rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + 0.1) / gamePanel.getTileSize());
            Tile tile = tileManager.getTilesArray()[colIndex][rowIndex];
            Tile decorationTile = tileManager.getDecorationsTilesArray()[colIndex][rowIndex];

            int colIndex2 = (int) (hitbox.getLowerWallLine().getX2() / gamePanel.getTileSize());
            int rowIndex2 = (int) ((hitbox.getLowerWallLine().getY2() + 0.1) / gamePanel.getTileSize());
            Tile tile2 = tileManager.getTilesArray()[colIndex2][rowIndex2];
            Tile decorationTile2 = tileManager.getDecorationsTilesArray()[colIndex2][rowIndex2];

            if(!tile.isCollisional() && !tile2.isCollisional() && (decorationTile == null || !decorationTile.isCollisional()) && (decorationTile2 == null || !decorationTile2.isCollisional())){
                positionY += deltaTime * velocityY;
                velocityY += deltaTime * gravityY;
            }else if(tile.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getTilesArray());
            }else if(tile2.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getTilesArray());
            }else if(decorationTile != null && decorationTile.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getDecorationsTilesArray());
            }else if(decorationTile2 != null && decorationTile2.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getDecorationsTilesArray());
            }

        }else {
            int colIndex = (int) (hitbox.getUpperWallLine().getX1() / gamePanel.getTileSize());
            int rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + velocityY) / gamePanel.getTileSize());
            Tile tile = tileManager.getTilesArray()[colIndex][rowIndex];
            Tile decorationTile = tileManager.getDecorationsTilesArray()[colIndex][rowIndex];

            int colIndex2 = (int) (hitbox.getUpperWallLine().getX2() / gamePanel.getTileSize());
            int rowIndex2 = (int) ((hitbox.getLowerWallLine().getY2() + velocityY) / gamePanel.getTileSize());
            Tile tile2 = tileManager.getTilesArray()[colIndex2][rowIndex2];
            Tile decorationTile2 = tileManager.getDecorationsTilesArray()[colIndex2][rowIndex2];

            if(!tile.isCollisional() && !tile2.isCollisional() && (decorationTile == null || !decorationTile.isCollisional()) && (decorationTile2 == null || !decorationTile2.isCollisional())) {
                positionY += deltaTime * velocityY;
                velocityY += deltaTime * gravityY;
            }else if(tile.isCollisional()) {
                fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getTilesArray());
            }else if(tile2.isCollisional()) {
                fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getTilesArray());
                positionX -= hitbox.getUpperWallLine().getX2() - hitbox.getUpperWallLine().getX1();
            }else if(decorationTile != null && decorationTile.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getDecorationsTilesArray());
            }else if(decorationTile2 != null && decorationTile2.isCollisional()){
                fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getDecorationsTilesArray());
                positionX -= hitbox.getUpperWallLine().getX2() - hitbox.getUpperWallLine().getX1();
            }
        }
    }

    private void fallOnTheGround(double x1, double y1, int colIndex, int rowIndex, double deltaTime, Tile[][] arr){
        /*double x1 = hitbox.getLowerWallLine().getX1();
        double y1 = hitbox.getLowerWallLine().getY1();*/
        double x2 = x1;//+ (deltaTime * velocityX);
        double y2 = y1 + (deltaTime * velocityY);
        double x3 = arr[colIndex][rowIndex].getUpperWallLine().getX1();
        double y3 = arr[colIndex][rowIndex].getUpperWallLine().getY1();
        double x4 = arr[colIndex][rowIndex].getUpperWallLine().getX2();
        double y4 = arr[colIndex][rowIndex].getUpperWallLine().getY2();

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;
            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                positionX = x1 + alpha * (x2 - x1) - padding;
                positionY = y1 + alpha * (y2 - y1) - height;

                velocityX = 0;
                velocityY = 0;
            }
        }/*else {
            x1 = hitbox.getLowerWallLine().getX2();
            y1 = hitbox.getLowerWallLine().getY2();
            x2 = x1 + (deltaTime * velocityX);
            y2 = y1 + (deltaTime * velocityY);

            divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

            if(divider != 0){
                double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
                double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;
                if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                    positionX = x1 + alpha * (x2 - x1) + 1 - padding - width;
                    positionY = y1 + alpha * (y2 - y1) - height;

                    velocityX = 0;
                    velocityY = 0;
                }
            }
        }*/


    }

    public void setVelocityX(double velocityX){
        this.velocityX = velocityX;
    }

    public double getVelocityX(){
        return velocityX;
    }

    public void setVelocityY(double velocityY){
        this.velocityY = velocityY;
    }

    public double getVelocityY(){
        return velocityY;
    }

    public void setPositionX(double positionX){
        this.positionX = positionX;
    }

    public double getPositionX(){
        return positionX;
    }

    public void setPositionY(double positionY){
        this.positionY = positionY;
    }

    public double getPositionY(){
        return positionY;
    }


}


package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManagerGame;

import java.awt.*;

public class Entity {
    private double positionX, positionY, velocityX, velocityY;
    private float gravityX, gravityY;
    private int height, width;
    private Image image;
    private String direction;
    private TileManagerGame tileManager;
    private HitBox hitbox;
    private int padding;

    Entity(TileManagerGame tileManager, double positionX, double positionY){
        this.tileManager = tileManager;
        hitbox = new HitBox();
        velocityX = 0;
        velocityY = 0;
        gravityX = 0;
        gravityY = 0.2f;
        this.positionX = positionX;
        this.positionY = positionY;
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

    public boolean move(double velocityX, double velocityY){
        if(velocityX < 0) {
            int colIndex = (int) ((hitbox.getLeftWallLine().getX1() + velocityX) / GamePanel.TILE_SIZE);
            int rowIndex = (int) ((hitbox.getLeftWallLine().getY1() + velocityY) / GamePanel.TILE_SIZE);
            int rowIndex2 = (int) ((hitbox.getLeftWallLine().getY2() + velocityY) / GamePanel.TILE_SIZE);

            if(tileManager.getTilesArray()[colIndex][rowIndex].isCollisional()) {
                System.out.println(tileManager.getTilesArray()[colIndex][rowIndex].name);
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
                    rowIndex = (int) ((positionY + i) / GamePanel.TILE_SIZE);
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
                performMove(velocityX, velocityY);
                return true;
            }
        }else{
            int colIndex = (int) ((hitbox.getRightWallLine().getX1() + velocityX) / GamePanel.TILE_SIZE);
            int rowIndex = (int) ((hitbox.getRightWallLine().getY1() + velocityY) / GamePanel.TILE_SIZE);
            int rowIndex2 = (int) ((hitbox.getRightWallLine().getY2() + velocityY) / GamePanel.TILE_SIZE);

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
                    rowIndex = (int) ((positionY + i) / GamePanel.TILE_SIZE);
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
                performMove(velocityX, velocityY);
                return true;
            }
        }
    }

    private void performMove(double velX, double velY){
        positionX += velX;
        positionY += velY;
    }


    protected void applyGravity(double deltaTime){
        int option = 0;
        int rowIndex, rowIndex2;

        if(velocityY == 0) {
            rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + 1) / GamePanel.TILE_SIZE);
            rowIndex2 = (int) ((hitbox.getLowerWallLine().getY2() + 1) / GamePanel.TILE_SIZE);
        }else {
            rowIndex = (int) ((hitbox.getLowerWallLine().getY1() + velocityY) / GamePanel.TILE_SIZE);
            rowIndex2 = (int) ((hitbox.getLowerWallLine().getY2() + velocityY) / GamePanel.TILE_SIZE);
            option = 2;
        }
        
        int colIndex = (int) (hitbox.getLowerWallLine().getX1() / GamePanel.TILE_SIZE);
        int colIndex2 = (int) (hitbox.getLowerWallLine().getX2() / GamePanel.TILE_SIZE);

        Tile tile = tileManager.getTilesArray()[colIndex][rowIndex];
        Tile decorationTile = tileManager.getDecorationsTilesArray()[colIndex][rowIndex];
        Tile tile2 = tileManager.getTilesArray()[colIndex2][rowIndex2];
        Tile decorationTile2 = tileManager.getDecorationsTilesArray()[colIndex2][rowIndex2];

        if(!tile.isCollisional() && !tile2.isCollisional() && (decorationTile == null || !decorationTile.isCollisional()) && (decorationTile2 == null || !decorationTile2.isCollisional())){
            positionY += deltaTime * velocityY;
            velocityY += deltaTime * gravityY;
        }else if(tile.isCollisional()){
            fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getTilesArray());
        }else if(tile2.isCollisional()){
            fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getTilesArray());
            if(option == 2) {
                positionX -= hitbox.getUpperWallLine().getX2() - hitbox.getUpperWallLine().getX1();
            }
        }else if(decorationTile != null && decorationTile.isCollisional()){
            fallOnTheGround(hitbox.getLowerWallLine().getX1(), hitbox.getLowerWallLine().getY1(), colIndex, rowIndex, deltaTime, tileManager.getDecorationsTilesArray());
        }else if(decorationTile2 != null && decorationTile2.isCollisional()){
            fallOnTheGround(hitbox.getLowerWallLine().getX2(), hitbox.getLowerWallLine().getY2(), colIndex2, rowIndex2, deltaTime, tileManager.getDecorationsTilesArray());
            if(option == 2) {
                positionX -= hitbox.getUpperWallLine().getX2() - hitbox.getUpperWallLine().getX1();
            }
        }
    }

    private void fallOnTheGround(double x1, double y1, int colIndex, int rowIndex, double deltaTime, Tile[][] arr){
        double x2 = x1;
        double y2 = y1 + (deltaTime * velocityY);
        double x3 = arr[colIndex][rowIndex].getUpperWallLine().getX1();
        double y3 = arr[colIndex][rowIndex].getUpperWallLine().getY1();
        double x4 = arr[colIndex][rowIndex].getUpperWallLine().getX2();
        double y4 = arr[colIndex][rowIndex].getUpperWallLine().getY2();

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);

        if(divider != 0) {
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;
            if (alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
                positionX = x1 + alpha * (x2 - x1) - padding;
                positionY = y1 + alpha * (y2 - y1) - height;

                velocityX = 0;
                velocityY = 0;
            }
        }
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }
    
    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public HitBox getHitbox() {
        return hitbox;
    }

    public void setHitbox(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public float getGravityX() {
        return gravityX;
    }

    public void setGravityX(float gravityX) {
        this.gravityX = gravityX;
    }

    public float getGravityY() {
        return gravityY;
    }

    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public TileManagerGame getTileManager() {
        return tileManager;
    }

    public void setTileManager(TileManagerGame tileManager) {
        this.tileManager = tileManager;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }
}


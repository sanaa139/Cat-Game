package entity;

import tiles.TileManagerGame;
import tiles.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Door extends Entity{
    private Image[] sprites;
    private Ball[] ballsArray;
    private int counter;
    private boolean closed;
    public Door(TileManagerGame tileManager, Ball[] ballsArray, double positionX, double positionY){
        super(tileManager, positionX, positionY);
        this.ballsArray = ballsArray;

        sprites = new Image[4];
        loadSprites();
        setImage(sprites[0]);
        setWidth(getImage().getWidth(null));
        setHeight(getImage().getHeight(null));

        setPadding(13);
        updateHitbox();
    }

    public void update(){
        for(Ball ball : ballsArray){
            if(ball != null) {
                if (((ball.getPositionX() >= getHitbox().getLeftWallLine().getX1() && ball.getPositionX() < getHitbox().getRightWallLine().getX1()) ||
                        (ball.getPositionX() <= getHitbox().getRightWallLine().getX1() && ball.getPositionX() > getHitbox().getLeftWallLine().getX1()))
                        && ball.getPositionY() >= getHitbox().getUpperWallLine().getY1()) {
                    ball.setPositionX(400);
                    ball.setPositionY(0);
                    ball.setEnteredTheDoor(true);
                    closed = true;
                }
            }
        }
    }

    private void updateHitbox(){
        double x1 = getPositionX() + getPadding();
        double x2 = getPositionX() + getWidth() - 1 - getPadding();
        double y1 = getPositionY() + getPadding() * 2;
        double y2 = getPositionY() + getHeight() - 1;
        getHitbox().setLeftWallLine(new Vector(x1, y1, x1, y2));
        getHitbox().setRightWallLine(new Vector(x2, y1, x2, y2));
        getHitbox().setUpperWallLine(new Vector(x1, y1, x2, y1));
        getHitbox().setLowerWallLine(new Vector(x1, y2, x2, y2));
    }

    private void loadSprites(){
        Image img;
        for(int i = 1; i <= sprites.length; i++){
            try {
                img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/doors/door" + i + ".png")));
                sprites[i-1] = img;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2d){
        if(closed) {
            if (counter >= 10) {
                if (getImage().equals(sprites[0])) {
                    setImage(sprites[1]);
                }else if(getImage().equals(sprites[1])){
                    setImage(sprites[2]);
                }else if(getImage().equals(sprites[2])){
                    setImage(sprites[3]);
                }
                counter = 0;
            }
        }
        g2d.drawImage(getImage(), (int)getPositionX(), (int)getPositionY(), getWidth(), getHeight(), null);
        counter++;
    }

    public boolean isClosed(){
        return closed;
    }
}

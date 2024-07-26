package entity;

import main.GamePanel;
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
    public Door(GamePanel gamePanel, TileManagerGame tileManager, Ball[] ballsArray, double positionX, double positionY){
        super(gamePanel, tileManager);
        this.ballsArray = ballsArray;

        this.positionX = positionX;
        this.positionY = positionY;

        sprites = new Image[4];
        loadSprites();
        image = sprites[0];
        width = image.getWidth(null);
        height = image.getHeight(null);

        this.padding = 13;
        updateHitbox();
    }

    public void update(){
        for(Ball ball : ballsArray){
            if(ball != null) {
                if (((ball.getPositionX() >= this.hitbox.getLeftWallLine().getX1() && ball.getPositionX() < this.hitbox.getRightWallLine().getX1()) ||
                        (ball.getPositionX() <= this.hitbox.getRightWallLine().getX1() && ball.getPositionX() > this.hitbox.getLeftWallLine().getX1()))
                        && ball.getPositionY() >= hitbox.getUpperWallLine().getY1()) {
                    ball.setPositionX(400);
                    ball.setPositionY(0);
                    ball.setEnteredTheDoor(true);
                    closed = true;
                }
            }
        }
    }

    private void updateHitbox(){
        double x1 = this.positionX + padding;
        double x2 = this.positionX + width - 1 - padding;
        double y1 = this.positionY + padding * 2;
        double y2 = this.positionY + height - 1;
        this.hitbox.setLeftWallLine(new Vector(x1, y1, x1, y2));
        this.hitbox.setRightWallLine(new Vector(x2, y1, x2, y2));
        this.hitbox.setUpperWallLine(new Vector(x1, y1, x2, y1));
        this.hitbox.setLowerWallLine(new Vector(x1, y2, x2, y2));
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
                if (image.equals(sprites[0])) {
                    image = sprites[1];
                }else if(image.equals(sprites[1])){
                    image = sprites[2];
                }else if(image.equals(sprites[2])){
                    image = sprites[3];
                }
                counter = 0;
            }
        }
        g2d.drawImage(image, (int)positionX, (int)positionY, width, height, null);
        counter++;
    }

    public boolean isClosed(){
        return closed;
    }
}

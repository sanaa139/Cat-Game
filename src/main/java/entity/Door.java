package entity;

import main.GamePanel;
import tiles.TileManagerGame;
import tiles.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Door extends Entity{
    private Image[] sprites;
    private Ball[] ballsArray;
    private int counter;
    private boolean closed;
    public Door(GamePanel gamePanel, TileManagerGame tileManager, Ball[] ballsArray, double positionX, double positionY){
        super(gamePanel, tileManager);
        this.ballsArray = ballsArray;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/doors/door4.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        this.positionX = positionX;
        this.positionY = positionY;
        width = 46;
        height = 56;

        sprites = new Image[4];
        loadSprites();
        image = sprites[0];

        this.padding = 13;
        updateHitbox();
    }

    public void update(double deltaTime){
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
        this.hitbox.setLeftWallLine(new Vector(this.positionX + padding, this.positionY + padding * 2, this.positionX + padding, this.positionY + height - 1 ));
        this.hitbox.setRightWallLine(new Vector(this.positionX + width - 1 - padding, this.positionY + padding * 2, this.positionX + width - 1 - padding, this.positionY + height - 1));
        this.hitbox.setUpperWallLine(new Vector(this.positionX + padding, this.positionY + padding * 2, this.positionX + width - 1 - padding, this.positionY + padding * 2));
        this.hitbox.setLowerWallLine(new Vector(this.positionX + padding, this.positionY + height, this.positionX + width - 1 - padding, this.positionY + height));
    }

    private void loadSprites(){
        Image img;
        for(int i = 1; i <= sprites.length; i++){
            try {
                img = ImageIO.read(getClass().getResourceAsStream("/doors/door" + i + ".png"));
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
        //g2d.setColor(Color.RED);
        //g2d.fillRect((int) this.hitbox.getLeftWallLine().getX1(), (int) this.hitbox.getLeftWallLine().getY1(), (int) (this.hitbox.getUpperWallLine().getX2() - this.hitbox.getUpperWallLine().getX1()),  (int) (this.hitbox.getRightWallLine().getY2() - this.hitbox.getLeftWallLine().getY1()));
    }

    public boolean isClosed(){
        return closed;
    }
}

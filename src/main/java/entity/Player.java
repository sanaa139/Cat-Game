package entity;

import main.KeyHandler;
import java.awt.*;

public class Player extends MovingEntity {
    private Image left1, left2, left3, left4, left_inactive, right1, right2, right3, right4, right_inactive;
    private int counter;
    private final KeyHandler keyHandler;

    public Player(KeyHandler keyHandler, double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);

        this.keyHandler = keyHandler;
        loadSprites();
        updateHitBox();
        setName("Player");

        setImage(left_inactive);
    }

    @Override
    public void update(double deltaTime){
        if(keyHandler.leftPressed){
            setSpriteDirection(SpriteDirection.LEFT);
            setVelocityX(-2);
        }
        if(keyHandler.rightPressed){
            setSpriteDirection(SpriteDirection.RIGHT);
            setVelocityX(2);
        }
        if(!keyHandler.leftPressed && !keyHandler.rightPressed){
            setSpriteDirection(SpriteDirection.STAY);
        }
        if(keyHandler.upPressed){
            jump();
        }
        counter++;
        updateHitBox();
    }

    @Override
    public void draw(Graphics2D g2d){
        if(counter >= 10){
            switch(getSpriteDirection()){
                case LEFT:
                    updateImageToWalkingImage(left1, left2, left3, left4);
                    break;
                case RIGHT:
                    updateImageToWalkingImage(right1, right2, right3, right4);
                    break;
                case STAY:
                    if(getImage().equals(left1) || getImage().equals(left2) || getImage().equals(left3) || getImage().equals(left4)){
                        setImage(left_inactive);
                    }else if(getImage().equals(right1) || getImage().equals(right2) || getImage().equals(right3) || getImage().equals(right4)){
                        setImage(right_inactive);
                    }
                    break;
            }
            counter = 0;
        }
        g2d.drawImage(getImage(), (int)getPositionX(), (int)getPositionY(), getWidth(), getHeight(), null);
    }

    private void updateImageToWalkingImage(Image img1, Image img2, Image img3, Image img4){
        if(getImage().equals(img1)){
            setImage(img2);
        }else if(getImage().equals(img2)){
            setImage(img3);
        }else if(getImage().equals(img3)){
            setImage(img4);
        }else if(getImage().equals(img4)){
            setImage(img1);
        }else{
            setImage(img1);
        }
    }

    private void jump(){
        if(getVelocityY() == 0){
            setVelocityY(-5);
        }
    }

    @Override
    protected void loadSprites() {
        String folderName = "entity";
        String entityName = "player";
        String extensionType = "png";

        left1 = loadSprite(folderName, entityName, "left1", extensionType);
        left2 = loadSprite(folderName, entityName, "left2", extensionType);
        left3 = loadSprite(folderName, entityName, "left3", extensionType);
        left4 = loadSprite(folderName, entityName, "left4", extensionType);
        left_inactive = loadSprite(folderName, entityName, "left_inactive", extensionType);
        right1 = loadSprite(folderName, entityName, "right1", extensionType);
        right2 = loadSprite(folderName, entityName, "right2", extensionType);
        right3 = loadSprite(folderName, entityName, "right3", extensionType);
        right4 = loadSprite(folderName, entityName, "right4", extensionType);
        right_inactive = loadSprite(folderName, entityName, "right_inactive", extensionType);
    }
}
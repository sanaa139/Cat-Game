package entity;

import java.awt.*;

public class Ball extends BouncingEntity {
    private boolean enteredTheDoor;

    public Ball( double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);

        loadSprites();
        updateHitBox();
        setName("Ball");
    }

    @Override
    public void restart(){
        super.restart();
        enteredTheDoor = false;
        setCollidable(true);
    }

    @Override
    public CollisionResponseType onCollision(Entity other, MovementDirection direction, double deltaTime) {
        if(other instanceof Door door){
            enteredTheDoor = true;
            setCollidable(false);
            door.setClosed(true);
            return CollisionResponseType.STOP;
        }else{
            return super.onCollision(other, direction, deltaTime);
        }
    }

    @Override
    public void draw(Graphics2D g2d){
        if(!enteredTheDoor) {
            g2d.drawImage(getImage(), (int) getPositionX(), (int) getPositionY(), getWidth(), getHeight(), null);
        }
    }

    public boolean enteredTheDoor(){
        return enteredTheDoor;
    }

    @Override
    protected void loadSprites() {
        setImage(loadSprite("entity", "ball", "ball", "png"));
    }
}

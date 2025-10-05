package entity;

import java.awt.*;
import java.io.IOException;

public class Box extends PushableEntity {
    public Box(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);

        loadSprites();
        updateHitBox();
        setName("Box");
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.drawImage(getImage(), (int) getPositionX(), (int) getPositionY(), getWidth(), getHeight(), null);
    }

    @Override
    protected void loadSprites() {
        setImage(loadSprite("entity", "box", "box", "png"));
    }

}

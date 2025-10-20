package entity;

import main.Drawable;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class Entity implements Drawable {
    private double positionX, positionY, velocityX, velocityY;
    private final double initialPositionX, initialPositionY;
    private float gravityY = 0.2f;;
    private final int width, height, padding;
    private Image image;
    private Rectangle hitbox;
    private boolean isCollidable;
    private int drawingPriority = 1;
    private String name;

    public Entity(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.isCollidable = isCollidable;

        hitbox = new Rectangle((int) (positionX + padding), (int) (positionY + padding), width - 2 * padding, height - padding);
    }

    protected abstract void loadSprites();
    protected abstract void update(double deltaTime);

    protected Image loadSprite(String baseFolder, String subFolder, String fileName, String fileExtension) {
        try {
            return LoadImagesManager.loadImage(baseFolder, subFolder, fileName, fileExtension);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName())
                    .severe("Failed to load image for " + getClass().getSimpleName() + ": " + e.getMessage());
            return LoadImagesManager.createPlaceholderImage(Color.MAGENTA);
        }
    }

    public void restart(){
        positionX = initialPositionX;
        positionY = initialPositionY;
        velocityX = 0;
        velocityY = 0;
        updateHitBox();
    }

    public void updateHitBox() {
        hitbox.setLocation((int)(positionX) + padding, (int)(positionY) + padding);
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
        updateHitBox();
    }
    
    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
        updateHitBox();
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

    public float getGravityY() {
        return gravityY;
    }

    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getPadding() {
        return padding;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public void setCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public int getDrawingPriority() {
        return drawingPriority;
    }

    public void setDrawingPriority(int drawingPriority) {
        this.drawingPriority = drawingPriority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


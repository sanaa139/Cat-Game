package tiles;

import java.awt.*;

public class Tile {
    private final Image image;
    private final int width;
    private final int height;
    public int localId;

    private boolean isCollidable;
    private Rectangle hitbox;

    public Tile(Image image, boolean isCollidable, int height, int width){
        this.image = image;
        this.isCollidable = isCollidable;

        this.height = height;
        this.width = width;

        hitbox = new Rectangle(width, height);
    }

    public void setPosition(int posX, int posY){
        if(posX >= 0 && posY >= 0) {
            hitbox.setLocation(posX, posY);
        }
    }

    public Image getImage(){
        return image;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isCollidable(){
        return isCollidable;
    }

    public void setIsCollidable(boolean isCollidable){
        this.isCollidable = isCollidable;
    }

    public Rectangle getPosition(){
        return hitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
}

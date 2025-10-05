package tiles;

import entity.LoadImagesManager;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private final Image image;
    private final int width, height;
    private final String name;

    private boolean isCollidable;
    private Rectangle hitbox;

    public Tile(Image image, boolean isCollidable, String name, int height, int width){
        this.image = image;
        this.isCollidable = isCollidable;

        this.name = name;
        this.height = height;
        this.width = width;

        hitbox = new Rectangle(width, height);
    }

    public static Tile empty(String name, boolean isCollidable, int height, int width) {
        BufferedImage emptyImage = LoadImagesManager.createPlaceholderImage(Color.WHITE);
        return new Tile(emptyImage, isCollidable, name, height, width);
    }

    public Tile(Tile tile) {
        this(tile.image, tile.isCollidable, tile.name, tile.height, tile.width);
    }

    public void setPosition(int posX, int posY){
        hitbox.setLocation(posX, posY);
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

    public String getName(){
        return name;
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

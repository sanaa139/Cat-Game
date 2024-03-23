package tiles;

import java.awt.*;

public class Tile {
    private Image image;
    private boolean collisional;

    public Tile(Image image){
        this.image = image;
        collisional = false;
    }
    public Tile(Image image, boolean collisional){
        this.image = image;
        this.collisional = collisional;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public boolean isCollisional(){
        return collisional;
    }

    public void setCollisional(boolean collisional){
        this.collisional = collisional;
    }
}

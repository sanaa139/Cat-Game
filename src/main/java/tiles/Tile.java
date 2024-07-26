package tiles;

import main.GamePanel;

import javax.swing.text.Position;
import java.awt.*;

public class Tile {
    private Image image;
    private boolean collisional;
    private final Vector position;
    private final int height, width;
    private final Vector upperWallLine, rightWallLine, lowerWallLine, leftWallLine;
    public String name;

    public Tile(Image image, boolean collisional, String name){
        this.image = image;
        position = new Vector();
        this.collisional = collisional;

        this.name = name;
        height = GamePanel.TILE_SIZE;
        width = GamePanel.TILE_SIZE;

        upperWallLine = new Vector();
        rightWallLine = new Vector();
        leftWallLine = new Vector();
        lowerWallLine = new Vector();
    }

    public Tile(Image image, boolean collisional, String name, int height, int width){
        this.image = image;
        position = new Vector();
        this.collisional = collisional;

        this.name = name;
        this.height = height;
        this.width = width;

        upperWallLine = new Vector();
        rightWallLine = new Vector();
        leftWallLine = new Vector();
        lowerWallLine = new Vector();
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

    public void setPosition(int posX, int posY){
        position.setX1(posX);
        position.setY1(posY);

        upperWallLine.setX1(position.getX1());
        upperWallLine.setY1(position.getY1());
        upperWallLine.setX2(position.getX1() + width - 1);
        upperWallLine.setY2(position.getY1());

        rightWallLine.setX1(position.getX1() + width - 1);
        rightWallLine.setY1(position.getY1());
        rightWallLine.setX2(position.getX1() + width - 1);
        rightWallLine.setY2(position.getY1() + height - 1);

        leftWallLine.setX1(position.getX1());
        leftWallLine.setY1(position.getY1());
        leftWallLine.setX2(position.getX1());
        leftWallLine.setY2(position.getY1() + height - 1);

        lowerWallLine.setX1(position.getX1());
        lowerWallLine.setY1(position.getY1() + height - 1);
        lowerWallLine.setX2(position.getX1() + width - 1);
        lowerWallLine.setY2(position.getY1() + height - 1);
    }

    public Vector getUpperWallLine(){
        return upperWallLine;
    }

    public Vector getRightWallLine(){
        return rightWallLine;
    }

    public Vector getLeftWallLine(){
        return leftWallLine;
    }
    public Vector getLowerWallLine(){
        return lowerWallLine;
    }

    public Vector getPosition(){
        return position;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public Tile copy() {
        return new Tile(image, collisional, name, height, width);
    }
}

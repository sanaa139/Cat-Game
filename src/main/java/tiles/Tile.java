package tiles;

import main.GamePanel;

import javax.swing.text.Position;
import java.awt.*;

public class Tile {
    private Image image;
    private boolean collisional;
    private final Vector position;
    private Vector upperWallLine, rightWallLine, lowerWallLine, leftWallLine;
    private GamePanel gamePanel;

    public String name;

    public Tile(Image image, GamePanel gamePanel, String name){
        this.image = image;
        this.gamePanel = gamePanel;
        position = new Vector();
        collisional = false;

        this.name = name;

        upperWallLine = new Vector();
    }
    public Tile(Image image, GamePanel gamePanel, boolean collisional, String name){
        this.image = image;
        this.gamePanel = gamePanel;
        position = new Vector();
        this.collisional = collisional;

        this.name = name;

        upperWallLine = new Vector();
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
        upperWallLine.setX2(position.getX1() + gamePanel.getTileSize() - 1);
        upperWallLine.setY2(position.getY1());
    }

    public Vector getUpperWallLine(){
        return upperWallLine;
    }

    public Vector getPosition(){
        return position;
    }

    public Tile copy() {
        return new Tile(image, gamePanel, collisional, name);
    }
}

package entity;

import java.awt.*;

public class Door extends Entity{
    private final Image[] sprites;
    private int counter;
    private boolean closed;
    public Door(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);

        sprites = new Image[4];
        loadSprites();
        setImage(sprites[0]);

        updateHitBox();
        setDrawingPriority(2);
        setName("Door");
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void restart(){
        super.restart();
        closed = false;
        setImage(sprites[0]);
    }

    @Override
    protected void loadSprites(){
        for(int i = 1; i <= sprites.length; i++){
            sprites[i-1] = loadSprite("entity", "door", "door" + i, "png");
        }
    }


    @Override
    public void draw(Graphics2D g2d){
        if(closed) {
            if (counter >= 10) {
                if (getImage().equals(sprites[0])) {
                    setImage(sprites[1]);
                }else if(getImage().equals(sprites[1])){
                    setImage(sprites[2]);
                }else if(getImage().equals(sprites[2])){
                    setImage(sprites[3]);
                }
                counter = 0;
            }
        }

        g2d.drawImage(getImage(), (int)getPositionX(), (int)getPositionY(), getWidth(), getHeight(), null);
        counter++;
    }

    public boolean isClosed(){
        return closed;
    }

    public void setClosed(boolean closed){
        this.closed = closed;
    }
}

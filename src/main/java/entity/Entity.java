package entity;

import java.awt.*;

public class Entity {
    protected double positionX, positionY, velocityX, velocityY;
    protected float gravity;
    protected Image image;
    protected String direction;

    Entity(){
        velocityX = 2;
        velocityY = -2;
        gravity = 0.5f;
    }

    public void move(double deltaX, double deltaY){
        positionX += deltaX;
        positionY += deltaY;
    }

    public void jump(double deltaTime){

        positionX += velocityX + velocityX * deltaTime;
        positionY += velocityY + velocityY * deltaTime;

        velocityY += gravity * deltaTime;

        /*
        if(positionY >= 200){
            velocityX = 2;
            velocityY = -2;
            direction = "stay";
        }*/
    }
}


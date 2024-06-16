package main;

import entity.Ball;
import entity.Door;

public class GameLevel {
    private final Ball[] balls;
    private final Door[] doors;

    public GameLevel(Ball[] balls, Door[] doors){
        this.balls = balls;
        this.doors = doors;
    }

    Ball[] getBalls(){
        return balls;
    }

    Door[] getDoors(){
        return doors;
    }
}

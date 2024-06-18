package main;

import entity.Ball;
import entity.Door;
import tiles.TileManager;

public class GameLevel {
    private final Ball[] balls;
    private final Door[] doors;
    private String map;

    public GameLevel(Ball[] balls, Door[] doors, String map){
        this.balls = balls;
        this.doors = doors;
        this.map = map;
    }

    public Ball[] getBalls(){
        return balls;
    }

    public Door[] getDoors(){
        return doors;
    }

    public String getMap(){
        return map;
    }
}

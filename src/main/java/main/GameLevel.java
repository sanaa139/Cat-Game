package main;

import entity.Ball;
import entity.Door;
import entity.Player;

public class GameLevel {
    private final Ball[] balls;
    private final Door[] doors;
    private String map;
    private Player player;

    public GameLevel(Ball[] balls, Door[] doors, String map, Player player){
        this.balls = balls;
        this.doors = doors;
        this.map = map;
        this.player = player;
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
    public Player getPlayer(){
        return player;
    }
}

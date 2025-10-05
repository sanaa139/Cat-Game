package main;

import entity.Ball;
import entity.Box;
import entity.Door;
import entity.Player;

public class EntityCreation {
    public static final int PLAYER_WIDTH = GamePanel.TILE_SIZE;
    public static final int PLAYER_HEIGHT = GamePanel.TILE_SIZE;
    public static final int PLAYER_PADDING = 7;
    public static final int DOOR_WIDTH = 46;
    public static final int DOOR_HEIGHT = 56;
    public static final int DOOR_PADDING = 13;
    public static final int BALL_WIDTH = 12;
    public static final int BALL_HEIGHT = 12;
    public static final int BALL_PADDING = 0;
    public static final int BOX_WIDTH = 31;
    public static final int BOX_HEIGHT = 29;
    public static final int BOX_PADDING = 0;
    public static final boolean PLAYER_COLLISIONAL = true;
    public static final boolean BALL_COLLISIONAL = true;
    public static final boolean DOOR_COLLISIONAL = false;
    public static final boolean BOX_COLLISIONAL = true;

    public static Player createPlayer(KeyHandler keyHandler, double posX, double posY) {
        return new Player(keyHandler, posX, posY, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_PADDING, PLAYER_COLLISIONAL);
    }

    public static Player createPlayer(KeyHandler keyHandler, double posX, double posY, int width, int height, int padding, boolean isCollisional) {
        return new Player(keyHandler, posX, posY, width, height, padding, isCollisional);
    }

    public static Ball createBall(double posX, double posY) {
        return new Ball(posX, posY, BALL_WIDTH, BALL_HEIGHT, BALL_PADDING, BALL_COLLISIONAL);
    }

    public static Ball createBall(double posX, double posY, int width, int height, int padding, boolean isCollisional) {
        return new Ball(posX, posY, width, height, padding, isCollisional);
    }

    public static Door createDoor(double posX, double posY) {
        return new Door(posX, posY, DOOR_WIDTH, DOOR_HEIGHT, DOOR_PADDING, DOOR_COLLISIONAL);
    }

    public static Door createDoor(double posX, double posY, int width, int height, int padding, boolean isCollisional) {
        return new Door(posX, posY, width, height, padding, isCollisional);
    }

    public static Box createBox(double posX, double posY) {
        return new Box(posX, posY, BOX_WIDTH, BOX_HEIGHT, BOX_PADDING, BOX_COLLISIONAL);
    }

    public static Box createBox(double posX, double posY, int width, int height, int padding, boolean isCollisional) {
        return new Box(posX, posY, width, height, padding, isCollisional);
    }
}

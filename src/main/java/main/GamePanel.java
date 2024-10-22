package main;

import entity.Ball;
import entity.Door;
import entity.Player;
import tiles.TileManager;
import tiles.TileManagerGame;
import tiles.TileManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLOutput;

public class GamePanel extends JPanel implements Runnable{
    public enum GameState {
        MENU, GAME
    }
    private GameState state = GameState.MENU;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_COL_NUM = 32;
    public static final int MAX_ROW_NUM = 16;
    public static final int SCREEN_WIDTH = MAX_COL_NUM * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAX_ROW_NUM * TILE_SIZE;
    public volatile boolean buttonClicked = false;

    KeyHandler keyHandler = new KeyHandler();
    TileManagerGame tileManagerGame = new TileManagerGame();
    Menu menu = new Menu(this);
    GameLevelsManager gameLevelsManager = new GameLevelsManager(this, tileManagerGame);

    public GamePanel(){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(null);
        addKeyListener(keyHandler);
        setFocusable(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    Thread gameThread;
    public int FPS = 60;

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = -1;
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        GameLevel gameLevel;

        while(gameThread != null){
            if(state == GameState.MENU && buttonClicked){
                state = GameState.GAME;
                buttonClicked = false;
                lastTime = -1;
            }
            if(state == GameState.GAME){
                if(lastTime == -1){
                    lastTime = System.nanoTime();
                }
                gameLevelsManager.checkIfLevelWasCleared();
                gameLevel = gameLevelsManager.getCurrentLevel();
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if(delta >= 1){
                    if(gameLevel != null) {
                        update(delta, gameLevel.getBalls(), gameLevel.getDoors(), gameLevel.getPlayer());
                    }
                    repaint();
                    delta--;
                    drawCount++;
                }
            }

            if(timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(double deltaTime, Ball[] balls, Door[] doors, Player player){
        player.update(deltaTime, keyHandler);
        for(Ball ball : balls){
            ball.update(deltaTime);
        }
        for(Door door : doors){
            door.update();
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        switch(state){
            case MENU:
                menu.getTileManagerMenu().draw(g2d);
                break;
            case GAME:
                GameLevel gameLevel = gameLevelsManager.getCurrentLevel();
                if(gameLevel != null){
                    tileManagerGame.setMap(gameLevelsManager.getCurrentLevel().getMap());
                    tileManagerGame.draw(g2d);
                    for (Door door : gameLevel.getDoors()) {
                        door.draw(g2d);
                    }
                    gameLevelsManager.getCurrentLevel().getPlayer().draw(g2d);
                    for (Ball ball : gameLevel.getBalls()) {
                        ball.draw(g2d);
                    }
                }
                break;
        }
    }

    public void setState(GameState state){
        this.state = state;
    }

}

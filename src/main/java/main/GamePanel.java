package main;

import entity.Ball;
import entity.Door;
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
        MENU, GAME, END,
    }
    private GameState state = GameState.MENU;
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 2;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int MAX_COL_NUM = 32;
    private static final int MAX_ROW_NUM = 16;
    private static final int SCREEN_WIDTH = MAX_COL_NUM * TILE_SIZE;
    private static final int SCREEN_HEIGHT = MAX_ROW_NUM * TILE_SIZE;

    KeyHandler keyHandler = new KeyHandler();
    TileManagerGame tileManagerGame = new TileManagerGame(this);
    Menu menu = new Menu(this);
    GameLevelsManager gameLevelsManager = new GameLevelsManager(this, tileManagerGame);

    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setLayout(null);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        gameThread = new Thread(this);
        gameThread.start();

        System.out.println(gameLevelsManager.currentLevelNum);
    }

    Thread gameThread;
    public int FPS = 60;

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;


        while(gameThread != null){
            switch(state){
                case MENU:
                    System.out.println("MENU");
                    break;
                case GAME:
                    //System.out.println("GAMEEEE");
                    GameLevel gameLevel = null;
                    if(gameLevelsManager.currentLevelNum != 0){
                        gameLevel = gameLevelsManager.getCurrentLevel();
                    }
                    currentTime = System.nanoTime();
                    delta += (currentTime - lastTime) / drawInterval;
                    timer += (currentTime - lastTime);
                    lastTime = currentTime;

                    if(delta >= 1){
                        if(gameLevel != null) {
                            update(delta, gameLevel.getBalls(), gameLevel.getDoors());
                        }
                        repaint();
                        delta--;
                        drawCount++;
                    }
                    break;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(double deltaTime, Ball[] balls, Door[] doors){
        if(gameLevelsManager.getCurrentLevel() != null){
            gameLevelsManager.getCurrentLevel().getPlayer().update(deltaTime);
        }
        for(Ball ball : balls){
            ball.update(deltaTime);
        }
        for(Door door : doors){
            door.update(deltaTime);
        }
    }


    public void paintComponent(Graphics g){
        System.out.println("paint");
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        switch(state){
            case MENU:
                menu.getTileManagerMenu().draw(g2d);
                break;
            case GAME:
                GameLevel gameLevel = gameLevelsManager.getCurrentLevel();
                tileManagerGame.setMap(gameLevelsManager.getCurrentLevel().getMap());
                tileManagerGame.draw(g2d);
                for (Door door : gameLevel.getDoors()) {
                    door.draw(g2d);
                }
                gameLevelsManager.getCurrentLevel().getPlayer().draw(g2d);
                for (Ball ball : gameLevel.getBalls()) {
                    ball.draw(g2d);
                }
                break;
        }
    }

    public int getTileSize(){
        return TILE_SIZE;
    }

    public int getMaxColNum(){
        return MAX_COL_NUM;
    }

    public int getMaxRowNum(){
        return MAX_ROW_NUM;
    }

    public int getScale(){
        return SCALE;
    }
    public int getScreenWidth(){
        return SCREEN_WIDTH;
    }
    public int getScreenHeight(){
        return SCREEN_HEIGHT;
    }

    public void setState(GameState state){
        this.state = state;
    }

}

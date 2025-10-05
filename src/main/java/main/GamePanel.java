package main;

import entity.*;
import tiles.TileManager;
import tiles.TileManagerGame;
import tiles.TileManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    public enum GameState {
        MENU, GAME
    }
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_COL_NUM = 32;
    public static final int MAX_ROW_NUM = 16;
    public static final int SCREEN_WIDTH = MAX_COL_NUM * TILE_SIZE;
    public static final int SCREEN_HEIGHT = MAX_ROW_NUM * TILE_SIZE;

    private final KeyHandler keyHandler = new KeyHandler();
    private final TileManager tileManagerGame = new TileManagerGame("map1");
    private final GameLevelsManager gameLevelsManager = new GameLevelsManager(this, tileManagerGame, keyHandler);

    private volatile GameState state = GameState.MENU;
    Menu menu = new Menu(this, new TileManagerMenu());
    Game game = new Game(this, gameLevelsManager);
    Thread gameThread;
    public int FPS = 60;


    public GamePanel(){
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(null);
        addKeyListener(keyHandler);
        setFocusable(true);
        setDoubleBuffered(true);
    }

    public void startGameThread() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = -1;
        long currentTime;

        long timer = 0;
        GameLevel gameLevel;

        while(gameThread != null){
            if(state == GameState.MENU && menu.isPlayButtonClicked()){
                state = GameState.GAME;
                menu.setPlayButtonClicked(false);
                lastTime = -1;
            }
            if(state == GameState.GAME && game.isRestartButtonClicked()){
                gameLevelsManager.restartCurrentLevel();
                game.setRestartButtonClicked(false);
                repaint();
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
                        gameLevel.update(delta);
                    }
                    repaint();
                    delta--;
                }
            }

            if(timer >= 1000000000){
                timer = 0;
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        switch(state){
            case MENU:
                menu.draw(g2d);
                break;
            case GAME:
                game.draw(g2d);
                break;
        }
    }

    public void setState(GameState state){
        this.state = state;
    }
}

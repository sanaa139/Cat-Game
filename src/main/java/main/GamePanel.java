package main;

import entity.Ball;
import entity.Door;
import entity.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class GamePanel extends JPanel implements Runnable{
    private final int originalTileSize = 16;
    private int scale = 2;
    private final int tileSize = originalTileSize * scale;
    private final int maxColNum = 32;
    private final int maxRowNum = 16;
    private final int screenWidth = maxColNum * tileSize;
    private final int screenHeight = maxRowNum * tileSize;

    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler, tileManager);

    GameLevelsManager gameLevelsManager = new GameLevelsManager(this, tileManager, player);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        gameThread = new Thread(this);
        gameThread.start();
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
            GameLevel gameLevel = gameLevelsManager.getCurrentLevel();
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                if (keyHandler.restartPressed){
                    player.restart();
                }
                update(delta, gameLevel.getBalls(), gameLevel.getDoors());
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(double deltaTime, Ball[] balls, Door[] doors){
        player.update(deltaTime);
        for(Ball ball : balls){
            ball.update(deltaTime);
        }
        for(Door door : doors){
            door.update(deltaTime);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GameLevel gameLevel = gameLevelsManager.getCurrentLevel();
        tileManager.setMap(gameLevelsManager.getCurrentLevel().getMap());
        tileManager.draw(g2);
        for(Door door : gameLevel.getDoors()) {
            door.draw(g2);
        }
        player.draw(g2);
        for(Ball ball : gameLevel.getBalls()){
            ball.draw(g2);
        }
        g2.dispose();
    }

    public int getTileSize(){
        return tileSize;
    }

    public int getMaxColNum(){
        return maxColNum;
    }

    public int getMaxRowNum(){
        return maxRowNum;
    }

    public int getScale(){
        return scale;
    }
}

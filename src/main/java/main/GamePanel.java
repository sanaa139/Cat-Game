package main;

import entity.Ball;
import entity.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public final int originalTileSize = 16;
    public int scale = 2;
    private final int tileSize = originalTileSize * scale;
    private final int maxColNum = 32;
    private final int maxRowNum = 16;
    private final int screenWidth = maxColNum * tileSize;
    private final int screenHeight = maxRowNum * tileSize;

    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler, tileManager);
    Ball ball = new Ball(this, tileManager);

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
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
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

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2);
        ball.draw(g2);
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
}

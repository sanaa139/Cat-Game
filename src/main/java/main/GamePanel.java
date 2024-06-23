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

public class GamePanel extends JPanel implements Runnable, MouseListener{
    private final int originalTileSize = 16;
    private int scale = 2;
    private final int tileSize = originalTileSize * scale;
    private final int maxColNum = 32;
    private final int maxRowNum = 16;
    private final int screenWidth = maxColNum * tileSize;
    private final int screenHeight = maxRowNum * tileSize;

    KeyHandler keyHandler = new KeyHandler();
    TileManagerGame tileManagerGame = new TileManagerGame(this);
    TileManagerMenu tileManagerMenu = new TileManagerMenu(this);
    Menu menu;
    private boolean inMenu;
    GameLevelsManager gameLevelsManager = new GameLevelsManager(this, tileManagerGame);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        addMouseListener(this);
        createMenu();

        gameThread = new Thread(this);
        gameThread.start();

        System.out.println(gameLevelsManager.currentLevelNum);
    }

    private void createMenu(){
        inMenu = true;
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

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(double deltaTime, Ball[] balls, Door[] doors){
        if(gameLevelsManager.getCurrentLevel() != null){
            System.out.println("current lvl not null");
            System.out.println(gameLevelsManager.currentLevelNum);
            System.out.println(inMenu);
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
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(!inMenu) {
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
        }else {
            tileManagerMenu.draw(g2d);
        }
        g2d.dispose();
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

    @Override
    public void mouseClicked(MouseEvent e) {
        for(Button button : tileManagerMenu.getButtons()){
            if(button.getBounds().contains(e.getPoint())){
                System.out.println("MYSZKA KLIKNELAAAAAAAA");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

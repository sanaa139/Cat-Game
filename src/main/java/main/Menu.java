package main;

import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {
    private final TileManager tileManager;
    private boolean isPlayButtonClicked;
    private final Button playButton;

    public Menu(GamePanel gamePanel, TileManager tileManager){
        this.tileManager = tileManager;

        int buttonWidth = 120;
        int buttonHeight = 60;
        int buttonX = GamePanel.SCREEN_WIDTH / 2 - buttonWidth / 2;
        int buttonY = GamePanel.SCREEN_HEIGHT / 2 - buttonHeight / 2;

        playButton = new Button(buttonX, buttonY, buttonWidth, buttonHeight, "PLAY", 28);


        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (playButton.getButtonBounds().contains(e.getPoint())) {
                    isPlayButtonClicked = true;
                }
            }
        });
    }

    public void draw(Graphics2D g2d) {
        tileManager.draw(g2d);
        playButton.drawButton(g2d);
    }


    public boolean isPlayButtonClicked(){
        return isPlayButtonClicked;
    }

    public void setPlayButtonClicked(boolean isPlayButtonClicked){
        this.isPlayButtonClicked = isPlayButtonClicked;
    }

    public TileManager getTileManager(){
        return tileManager;
    }
}

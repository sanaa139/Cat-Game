package main;

import entity.Entity;
import entity.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

public class Game {
    private final GameLevelsManager gameLevelsManager;
    private boolean isRestartButtonClicked;
    private final Button restartButton;

    public Game(GamePanel gamePanel, GameLevelsManager gameLevelsManager){
        this.gameLevelsManager = gameLevelsManager;

        int buttonWidth = 200;
        int buttonHeight = 60;
        int buttonX = 10;
        int buttonY = 10;

        restartButton = new Button(buttonX, buttonY, buttonWidth, buttonHeight, "RESTART LEVEL", 22);

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (restartButton.getButtonBounds().contains(e.getPoint())) {
                    isRestartButtonClicked = true;
                }
            }
        });
    }

    public void draw(Graphics2D g2d) {
        GameLevel gameLevel = gameLevelsManager.getCurrentLevel();
        if(gameLevel != null){
            gameLevelsManager.getTileManager().draw(g2d);
            for(Entity entity : gameLevel.getEntities().stream()
                    .sorted(Comparator.comparingInt(Entity::getDrawingPriority).reversed())
                    .toList()){
                entity.draw(g2d);
            }
        }
        restartButton.drawButton(g2d);
    }

    public boolean isRestartButtonClicked(){
        return isRestartButtonClicked;
    }

    public void setRestartButtonClicked(boolean isRestartButtonClicked){
        this.isRestartButtonClicked = isRestartButtonClicked;
    }
}

package main;

import tiles.TiledMapLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {
    private final TiledMapLoader mapLoader;
    private boolean isPlayButtonClicked;
    private final Button playButton;

    public Menu(GamePanel gamePanel, TiledMapLoader mapLoader) {
        this.mapLoader = mapLoader;

        try {
            mapLoader.loadMapFromResources("assets/levels/menu.tmx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        mapLoader.drawMap(g2d);
        playButton.drawButton(g2d);
    }

    public boolean isPlayButtonClicked(){
        return isPlayButtonClicked;
    }

    public void setPlayButtonClicked(boolean isPlayButtonClicked){
        this.isPlayButtonClicked = isPlayButtonClicked;
    }
}

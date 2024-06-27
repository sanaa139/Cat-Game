package main;

import tiles.Tile;
import tiles.TileManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu {
    public JButton playButton;
    private TileManagerMenu tileManagerMenu;

    public Menu(GamePanel gamePanel){
        this.tileManagerMenu = new TileManagerMenu(gamePanel);
        createPLayButton(gamePanel);
    }

    private void createPLayButton(GamePanel gamePanel){
        playButton = new JButton("PLAY");
        int buttonWidth = 100;
        int buttonHeight = 50;
        playButton.setBounds(gamePanel.getScreenWidth() / 2 - buttonWidth / 2, gamePanel.getScreenHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
        playButton.setFont(new Font("Serif",Font.BOLD,20));
        playButton.setBackground(new Color(255,128,0));
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        gamePanel.add(playButton);
        playButton.setVisible(true);
        playButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setState(GamePanel.GameState.GAME);
                playButton.setVisible(false);
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
        });
    }


    TileManagerMenu getTileManagerMenu(){
        return tileManagerMenu;
    }

}

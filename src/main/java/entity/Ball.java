package entity;

import main.GamePanel;
import tiles.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Ball extends Entity{
    public Ball(GamePanel gamePanel, TileManager tileManager){
        super(gamePanel, tileManager);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/ball.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        positionX = 350;
        positionY = 255.96666666666664;
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(image, (int)positionX, (int)positionY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }
}

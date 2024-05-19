package entity;

import main.GamePanel;
import tiles.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Ball extends Entity{
    Player player;
    public Ball(GamePanel gamePanel, TileManager tileManager, Player player){
        super(gamePanel, tileManager);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/ball.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        positionX = 450;
        positionY = 200;
        width = 10;
        height = 9;
        this.player = player;
    }

    public void update(double deltaTime){
        this.applyGravity(deltaTime);
        touched();
        jumpedOn(deltaTime);
    }

    private void touched(){
        double x1 = player.positionX + player.width - 5;
        double y1 = player.positionY + player.height - 1;
        double x2 = x1 + 1;
        double y2 = y1;
        double x3 = positionX;
        double y3 = positionY;
        double x4 = positionX;
        double y4 = positionY + height - 1;

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                move(2, 0);
            }
        }

        x1 = player.positionX + 4;
        x2 = x1 - 1;
        x3 = positionX + width - 1;
        y3 = positionY;
        x4 = positionX + width - 1;
        y4 = positionY + height - 1;

        divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                move(-2, 0);
            }
        }
    }

    private void jumpedOn(double deltaTime){
        double x1 = player.positionX + (double) player.width /2;
        double y1 = player.positionY + player.height - 1;
        double x2 = x1 + (deltaTime * player.velocityX);
        double y2 = y1 + (deltaTime * player.velocityY);
        double x3 = positionX;
        double y3 = positionY;
        double x4 = positionX + width - 1;
        double y4 = positionY;

        double divider = (x4 - x3) * (y2 - y1) - (y4 - y3) * (x2 - x1);
        if(divider != 0){
            double alpha = ((x4 - x3) * (y3 - y1) - (y4 - y3) * (x3 - x1)) / divider;
            double beta = ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) / divider;

            if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1){
                player.velocityX = 0;
                player.velocityY = -3 - 0.5 * player.velocityY;
            }
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(image, (int)positionX, (int)positionY, width, height, null);
    }
}

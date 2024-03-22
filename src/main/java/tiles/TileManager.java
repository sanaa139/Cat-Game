package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class TileManager {
    private GamePanel gamePanel;
    private Image[][] tilesArray;
    private Image tile;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        tilesArray = new Image[gamePanel.getMaxRowNum()][gamePanel.getMaxColNum()];
        try{
            tile = ImageIO.read(getClass().getResourceAsStream("/tiles" + "/tile.jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        /*g2d.drawImage(tile, gamePanel.getTileSize() * 8, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 9, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 10, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 11, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 12, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 13, gamePanel.getTileSize() * 8, gamePanel.getTileSize(), gamePanel.getTileSize(), null);*/

        g2d.drawImage(tile, gamePanel.getTileSize() * 5, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 6, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 7, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 8, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 9, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 10, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 11, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 12, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 13, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 14, gamePanel.getTileSize() * 12, gamePanel.getTileSize(), gamePanel.getTileSize(), null);

        g2d.drawImage(tile, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 0, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        tilesArray[0][2] = tile;

        tilesArray[12][5] = tile;
        tilesArray[12][6] = tile;
        tilesArray[12][7] = tile;
        tilesArray[12][8] = tile;
        tilesArray[12][9] = tile;
        tilesArray[12][10] = tile;
        tilesArray[12][11] = tile;
        tilesArray[12][12] = tile;
        tilesArray[12][13] = tile;
        tilesArray[12][14] = tile;

        g2d.drawImage(tile, gamePanel.getTileSize() * 5, gamePanel.getTileSize() * 11, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        g2d.drawImage(tile, gamePanel.getTileSize() * 14, gamePanel.getTileSize() * 11, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        tilesArray[11][14] = tile;
        tilesArray[11][5] = tile;
    }

    public Image[][] getTilesArray() {
        return tilesArray;
    }
}

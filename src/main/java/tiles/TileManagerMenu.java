package tiles;

import entity.LoadImagesManager;
import main.GamePanel;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TileManagerMenu extends TileManager{
    private Tile backgroundMenuTile;
    private Tile[][] menuTilesArray;
    private String mapName;
    public TileManagerMenu(){
        this.mapName = "map1";

        menuTilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
        loadSprites();
        createMap();
    }

    @Override
    protected void loadSprites() {
        backgroundMenuTile = loadTile("tiles", "background", "background", "png", false,"BLUE_BACKGROUND", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    @Override
    public void createMap(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("maps/menuMaps/" + mapName + "/menu_" + mapName + ".txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
                    Tile tile = switch (words[i]) {
                        case "B" -> new Tile(backgroundMenuTile);
                        default -> null;
                    };

                    tile.setPosition(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE);
                    menuTilesArray[col][row] = tile;
                    i++;
                }
                row++;
                line = reader.readLine();
            }
        }catch(IOException e){

        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (Tile[] tiles : menuTilesArray) {
            for (Tile tile : tiles) {
                if (tile != null) {
                    Rectangle position = tile.getPosition();
                    g2d.drawImage(tile.getImage(), position.x, position.y, tile.getWidth(), tile.getHeight(), null);
                }
            }
        }
    }

}

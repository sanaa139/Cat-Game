package tiles;

import main.GamePanel;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TileManagerMenu implements TileManager{
    private Tile backgroundMenuTile;
    private Tile[][] menuTilesArray;
    private String mapName;
    public TileManagerMenu(){
        this.mapName = "menu";

        menuTilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
        initializeTiles();
        createMap();
    }

    private void initializeTiles(){
        try{
            backgroundMenuTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/background.jpg")), false,"BLUE_BACKGROUND");
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void createMap(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("maps/" + mapName + ".txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            int row = 0;

            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
                    Tile tile = switch (words[i]) {
                        case "B" -> backgroundMenuTile.copy();
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
        for (int col = 0; col < menuTilesArray.length; col++) {
            for (int row = 0; row < menuTilesArray[col].length; row++) {
                if(menuTilesArray[col][row] != null) {
                    Vector position = menuTilesArray[col][row].getPosition();
                    g2d.drawImage(menuTilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), menuTilesArray[col][row].getWidth(), menuTilesArray[col][row].getHeight(), null);
                }
            }
        }
    }

}

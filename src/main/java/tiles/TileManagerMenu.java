package tiles;

import main.GamePanel;
import main.Button;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TileManagerMenu implements TileManager{
    private GamePanel gamePanel;
    private Tile backgroundMenuTile;
    private Button playButton;
    private Tile[][] menuTilesArray;
    private String mapName;
    private Button[] buttons;
    public TileManagerMenu(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.mapName = "menu";

        menuTilesArray = new Tile[gamePanel.getMaxColNum()][gamePanel.getMaxRowNum()];
        buttons = new Button[1];
        initializeTiles();
        createMap();
    }

    private void initializeTiles(){
        try{
            backgroundMenuTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/backgroundMenu.jpg")), gamePanel, false,"BLUE_BACKGROUND");
            playButton = new Button(ImageIO.read(getClass().getResourceAsStream("/tiles/background/playButton.jpg")), 400, 250,100, 30);
            buttons[0] = playButton;
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
                for (int col = 0; col < gamePanel.getMaxColNum(); col++) {
                    Tile tile = switch (words[i]) {
                        case "B" -> backgroundMenuTile.copy();
                        default -> null;
                    };

                    tile.setPosition(col * gamePanel.getTileSize(), row * gamePanel.getTileSize());
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
        playButton.draw(g2d);
    }

    public Button[] getButtons(){
        return buttons;
    }
}
package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TileManager {
    private GamePanel gamePanel;
    private Tile[][] tilesArray;
    private final Tile[] wallsArray;
    private final Tile[] wallsCornersArray;
    private final Tile[] backgroundsWallsArray;
    private Tile backgroundTile;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tilesArray = new Tile[gamePanel.getMaxColNum()][gamePanel.getMaxRowNum()];
        wallsArray = new Tile[8];
        wallsCornersArray = new Tile[4];
        backgroundsWallsArray = new Tile[13];

        loadSprites();
        createMap();
    }

    private void loadSprites(){
        try {
            backgroundTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/background.jpg")), gamePanel, "BLACK_BACKGROUND");
        }catch(IOException e){
            e.printStackTrace();
        }

        loadWallSprites();
    }

    private void loadWallSprites(){
        try{
            Tile tile;
            for(int i = 1; i <= wallsArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wall/wall" + i + ".jpg")), gamePanel, true, "WALL " + i);
                wallsArray[i - 1] = tile;
            }
            for(int i = 1; i <= wallsCornersArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wallCorners/corner" + i + ".jpg")), gamePanel,true, "WALL_CORNER " + i);
                wallsCornersArray[i - 1] = tile;
            }
            for(int i = 1; i <= backgroundsWallsArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/backgroundWall" + i + ".jpg")), gamePanel, "BACKGROUND " + i);
                backgroundsWallsArray[i - 1] = tile;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createMap(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("maps/map1.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            int row = 0;

            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for(int col = 0; col < gamePanel.getMaxColNum(); col++){
                    Tile tile = switch (words[i]) {
                        case "W1" -> wallsArray[0].copy();
                        case "W2" -> wallsArray[1].copy();
                        case "W3" -> wallsArray[2].copy();
                        case "W4" -> wallsArray[3].copy();
                        case "W5" -> wallsArray[4].copy();
                        case "W6" -> wallsArray[5].copy();
                        case "W7" -> wallsArray[6].copy();
                        case "W8" -> wallsArray[7].copy();
                        case "C1" -> wallsCornersArray[0].copy();
                        case "C2" -> wallsCornersArray[1].copy();
                        case "C3" -> wallsCornersArray[2].copy();
                        case "C4" -> wallsCornersArray[3].copy();
                        case "BW1" -> backgroundsWallsArray[0].copy();
                        case "BW2" -> backgroundsWallsArray[1].copy();
                        case "BW3" -> backgroundsWallsArray[2].copy();
                        case "BW4" -> backgroundsWallsArray[3].copy();
                        case "BW5" -> backgroundsWallsArray[4].copy();
                        case "BW6" -> backgroundsWallsArray[5].copy();
                        case "BW7" -> backgroundsWallsArray[6].copy();
                        case "BW8" -> backgroundsWallsArray[7].copy();
                        case "BW9" -> backgroundsWallsArray[8].copy();
                        case "BW10" -> backgroundsWallsArray[9].copy();
                        case "BW11" -> backgroundsWallsArray[10].copy();
                        case "BW12" -> backgroundsWallsArray[11].copy();
                        case "BW13" -> backgroundsWallsArray[12].copy();
                        default -> backgroundTile.copy();
                    };
                    tile.setPosition(col * gamePanel.getTileSize(), row * gamePanel.getTileSize());
                    tilesArray[col][row] = tile;
                    i++;
                }
                row++;
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        for(int col = 0; col < tilesArray.length; col++){
            for(int row = 0; row < tilesArray[col].length; row++){
                Vector position = tilesArray[col][row].getPosition();
                //System.out.print(tilesArray[col][row].name + ": col = " + col + ", row = " + row + ", posX = " + position.getX1() + ", posY: = " + position.getY1() + " ");
                g2d.drawImage(tilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            }
            //System.out.println();
        }
        //System.out.println("KONIEC PRINTU TILE");
    }

    public Tile[][] getTilesArray() {
        return tilesArray;
    }
}

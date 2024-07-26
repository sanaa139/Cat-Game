package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TileManagerGame implements TileManager{
    private Tile[][] tilesArray;
    private Tile[][] decorationsTilesArray;
    private final Tile[] wallsArray;
    private final Tile[] wallsCornersArray;
    private final Tile[] backgroundsWallsArray;
    private final Tile[] decorationsArray;
    private Tile backgroundTile;
    private String mapName;

    public TileManagerGame(){
        tilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
        decorationsTilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
        wallsArray = new Tile[8];
        wallsCornersArray = new Tile[4];
        backgroundsWallsArray = new Tile[17];
        decorationsArray = new Tile[17];

        loadSprites();
        mapName = "map1";
        createMap();
    }

    private void loadSprites(){
        try {
            backgroundTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/background.jpg")), false,"BLACK_BACKGROUND");
        }catch(IOException e){
            e.printStackTrace();
        }

        loadWallSprites();
        loadDecorationsSprites();
    }

    private void loadWallSprites(){
        try{
            Tile tile;
            for(int i = 1; i <= wallsArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wall/wall" + i + ".jpg")), true, "WALL " + i);
                wallsArray[i - 1] = tile;
            }
            for(int i = 1; i <= wallsCornersArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wallCorners/corner" + i + ".jpg")),true, "WALL_CORNER " + i);
                wallsCornersArray[i - 1] = tile;
            }
            for(int i = 1; i <= backgroundsWallsArray.length; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/backgroundWall" + i + ".jpg")), false,"BACKGROUND " + i);
                backgroundsWallsArray[i - 1] = tile;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void loadDecorationsSprites(){
        try{
            Tile tile;
            for(int i = 1; i <= 3; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/platform" + i + ".png")), true, "PLATFORM " + i, 15, GamePanel.TILE_SIZE);
                decorationsArray[i - 1] = tile;
            }
            tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/platform4.png")), true, "PLATFORM4", 14, GamePanel.TILE_SIZE);
            decorationsArray[3] = tile;
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/banner" + i + ".png")), false, "BANNER " + i, 32, GamePanel.TILE_SIZE);
                decorationsArray[i + 3] = tile;
            }
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/window1_" + i + ".png")), false, "WINDOW1_ " + i, 32, GamePanel.TILE_SIZE);
                decorationsArray[i + 7] = tile;
            }
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/window2_" + i + ".png")), false, "WINDOW2_ " + i, 32, GamePanel.TILE_SIZE);
                decorationsArray[i + 11] = tile;
            }
            tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/box.png")), true, "BOX", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
            decorationsArray[16] = tile;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void createMap(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("maps/" + mapName + "/" + mapName + ".txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            int row = 0;

            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
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
                        case "BW14" -> backgroundsWallsArray[13].copy();
                        case "BW15" -> backgroundsWallsArray[14].copy();
                        case "BW16" -> backgroundsWallsArray[15].copy();
                        case "BW17" -> backgroundsWallsArray[16].copy();
                        default -> backgroundTile.copy();
                    };
                    tile.setPosition(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE);
                    tilesArray[col][row] = tile;
                    i++;
                }
                row++;
                line = reader.readLine();
            }
            reader.close();

            inputStream = classLoader.getResourceAsStream("maps/" + mapName + "/decorations_" + mapName + ".txt");
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(streamReader);

            row = 0;

            line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
                    Tile tile = switch (words[i]) {
                        case "P1" -> decorationsArray[0].copy();
                        case "P2" -> decorationsArray[1].copy();
                        case "P3" -> decorationsArray[2].copy();
                        case "P4" -> decorationsArray[3].copy();
                        case "BN1" -> decorationsArray[4].copy();
                        case "BN2" -> decorationsArray[5].copy();
                        case "BN3" -> decorationsArray[6].copy();
                        case "BN4" -> decorationsArray[7].copy();
                        case "WN1_1" -> decorationsArray[8].copy();
                        case "WN1_2" -> decorationsArray[9].copy();
                        case "WN1_3" -> decorationsArray[10].copy();
                        case "WN1_4" -> decorationsArray[11].copy();
                        case "WN2_1" -> decorationsArray[12].copy();
                        case "WN2_2" -> decorationsArray[13].copy();
                        case "WN2_3" -> decorationsArray[14].copy();
                        case "WN2_4" -> decorationsArray[15].copy();
                        case "BOX" -> decorationsArray[16].copy();
                        default -> null;
                    };
                    if (tile != null) {
                        tile.setPosition(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE);
                        decorationsTilesArray[col][row] = tile;
                    } else {
                        decorationsTilesArray[col][row] = null;
                    }
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

    public Tile[][] getTilesArray() {
        return tilesArray;
    }

    public Tile[][] getDecorationsTilesArray() {
        return decorationsTilesArray;
    }

    public void setMap(String mapName){
        if(!this.mapName.equals(mapName)) {
            this.mapName = mapName;
            createMap();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (int col = 0; col < tilesArray.length; col++) {
            for (int row = 0; row < tilesArray[col].length; row++) {
                Vector position;
                if(tilesArray[col][row] != null) {
                    position = tilesArray[col][row].getPosition();
                    g2d.drawImage(tilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), tilesArray[col][row].getWidth(), tilesArray[col][row].getHeight(), null);
                }
                if (decorationsTilesArray[col][row] != null) {
                    position = decorationsTilesArray[col][row].getPosition();
                    g2d.drawImage(decorationsTilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), decorationsTilesArray[col][row].getWidth(), decorationsTilesArray[col][row].getHeight(), null);
                }
            }
        }
    }
}

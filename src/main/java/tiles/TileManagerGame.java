package tiles;

import main.GamePanel;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TileManagerGame extends TileManager {
    private final Tile[][] mapTilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
    private final Tile[][] mapDecorativeTilesArray = new Tile[GamePanel.MAX_COL_NUM][GamePanel.MAX_ROW_NUM];
    private final Tile[] wallsArray = new Tile[8];
    private final Tile[] wallsCornersArray = new Tile[4];
    private final Tile[] backgroundsWallsArray = new Tile[17];
    private final Tile[] decorationsArray = new Tile[16];
    private Tile backgroundTile;

    public TileManagerGame(String mapName){
        this.mapName = mapName;
        loadSprites();
        createMap();
        setMapLayers(mapTilesArray, mapDecorativeTilesArray);
    }

    @Override
    protected void loadSprites(){
        backgroundTile = loadTile("tiles", "background", "background", "png", false,"BLACK_BACKGROUND", GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);

        loadWallSprites();
        loadDecorationsSprites();
    }

    private void loadWallSprites(){
        for(int i = 1; i <= wallsArray.length; i++){
            wallsArray[i - 1] = loadTile("tiles", "wall", "wall" + i, "png", true, "WALL " + i, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        }
        for(int i = 1; i <= wallsCornersArray.length; i++){
            wallsCornersArray[i - 1] = loadTile("tiles", "wallCorners", "corner" + i, "png", true, "WALL_CORNER " + i, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        }
        for(int i = 1; i <= backgroundsWallsArray.length; i++){
            backgroundsWallsArray[i - 1] = loadTile("tiles", "background", "backgroundWall" + i, "png", false, "BACKGROUND " + i, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
        }
    }

    private void loadDecorationsSprites(){
        for(int i = 1; i <= 3; i++){
            decorationsArray[i - 1] = loadTile("tiles", "decorations", "platform" + i, "png", true, "PLATFORM " + i, 15, GamePanel.TILE_SIZE);
        }
        decorationsArray[3] = loadTile("tiles", "decorations", "platform4", "png", true, "PLATFORM4", 14, GamePanel.TILE_SIZE);;
        for(int i = 1; i <= 4; i++){
            decorationsArray[i + 3] = loadTile("tiles", "decorations", "banner" + i, "png", false, "BANNER " + i, 32, GamePanel.TILE_SIZE);
        }
        for(int i = 1; i <= 4; i++){
            decorationsArray[i + 7] = loadTile("tiles", "decorations", "window1_" + i, "png", false, "WINDOW1_ " + i, 32, GamePanel.TILE_SIZE);
        }
        for(int i = 1; i <= 4; i++){
            decorationsArray[i + 11] = loadTile("tiles", "decorations", "window2_" + i, "png", false, "WINDOW2_ " + i, 32, GamePanel.TILE_SIZE);
        }
    }

    @Override
    public void createMap(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("maps/GameMaps/" + mapName + "/" + mapName + ".txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            int row = 0;
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
                    Tile tile = switch (words[i]) {
                        case "W1" -> new Tile(wallsArray[0]);
                        case "W2" -> new Tile(wallsArray[1]);
                        case "W3" -> new Tile(wallsArray[2]);
                        case "W4" -> new Tile(wallsArray[3]);
                        case "W5" -> new Tile(wallsArray[4]);
                        case "W6" -> new Tile(wallsArray[5]);
                        case "W7" -> new Tile(wallsArray[6]);
                        case "W8" -> new Tile(wallsArray[7]);
                        case "C1" -> new Tile(wallsCornersArray[0]);
                        case "C2" -> new Tile(wallsCornersArray[1]);
                        case "C3" -> new Tile(wallsCornersArray[2]);
                        case "C4" -> new Tile(wallsCornersArray[3]);
                        case "BW1" -> new Tile(backgroundsWallsArray[0]);
                        case "BW2" -> new Tile(backgroundsWallsArray[1]);
                        case "BW3" -> new Tile(backgroundsWallsArray[2]);
                        case "BW4" -> new Tile(backgroundsWallsArray[3]);
                        case "BW5" -> new Tile(backgroundsWallsArray[4]);
                        case "BW6" -> new Tile(backgroundsWallsArray[5]);
                        case "BW7" -> new Tile(backgroundsWallsArray[6]);
                        case "BW8" -> new Tile(backgroundsWallsArray[7]);
                        case "BW9" -> new Tile(backgroundsWallsArray[8]);
                        case "BW10" -> new Tile(backgroundsWallsArray[9]);
                        case "BW11" -> new Tile(backgroundsWallsArray[10]);
                        case "BW12" -> new Tile(backgroundsWallsArray[11]);
                        case "BW13" -> new Tile(backgroundsWallsArray[12]);
                        case "BW14" -> new Tile(backgroundsWallsArray[13]);
                        case "BW15" -> new Tile(backgroundsWallsArray[14]);
                        case "BW16" -> new Tile(backgroundsWallsArray[15]);
                        case "BW17" -> new Tile(backgroundsWallsArray[16]);
                        default -> new Tile(backgroundTile);
                    };
                    tile.setPosition(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE);
                    mapTilesArray[col][row] = tile;
                    i++;
                }
                row++;
                line = reader.readLine();
            }
            reader.close();

            inputStream = classLoader.getResourceAsStream("maps/GameMaps/" + mapName + "/decorations_" + mapName + ".txt");
            streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(streamReader);

            row = 0;
            line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                int i = 0;
                for (int col = 0; col < GamePanel.MAX_COL_NUM; col++) {
                    Tile tile = switch (words[i]) {
                        case "P1" -> new Tile(decorationsArray[0]);
                        case "P2" -> new Tile(decorationsArray[1]);
                        case "P3" -> new Tile(decorationsArray[2]);
                        case "P4" -> new Tile(decorationsArray[3]);
                        case "BN1" -> new Tile(decorationsArray[4]);
                        case "BN2" -> new Tile(decorationsArray[5]);
                        case "BN3" -> new Tile(decorationsArray[6]);
                        case "BN4" -> new Tile(decorationsArray[7]);
                        case "WN1_1" -> new Tile(decorationsArray[8]);
                        case "WN1_2" -> new Tile(decorationsArray[9]);
                        case "WN1_3" -> new Tile(decorationsArray[10]);
                        case "WN1_4" -> new Tile(decorationsArray[11]);
                        case "WN2_1" -> new Tile(decorationsArray[12]);
                        case "WN2_2" -> new Tile(decorationsArray[13]);
                        case "WN2_3" -> new Tile(decorationsArray[14]);
                        case "WN2_4" -> new Tile(decorationsArray[15]);
                        default -> null;
                    };
                    if (tile != null) {
                        tile.setPosition(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE);
                        mapDecorativeTilesArray[col][row] = tile;
                    } else {
                        mapDecorativeTilesArray[col][row] = null;
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

    public Tile[][] getMapTilesArray() {
        return mapTilesArray;
    }

    public Tile[][] getMapDecorativeTilesArray() {
        return mapDecorativeTilesArray;
    }

    @Override
    public void draw(Graphics2D g2d) {
        for (int col = 0; col < mapTilesArray.length; col++) {
            for (int row = 0; row < mapTilesArray[col].length; row++) {
                Rectangle position;
                if(mapTilesArray[col][row] != null) {
                    position = mapTilesArray[col][row].getPosition();
                    g2d.drawImage(mapTilesArray[col][row].getImage(), (int) position.x, (int) position.y, mapTilesArray[col][row].getWidth(), mapTilesArray[col][row].getHeight(), null);
                    //g2d.setColor(Color.GRAY);
                    //g2d.drawRect(mapTilesArray[col][row].getHitbox().x, mapTilesArray[col][row].getHitbox().y, mapTilesArray[col][row].getHitbox().width, mapTilesArray[col][row].getHitbox().height);
                    //g2d.setColor(Color.BLACK);
                    //g2d.drawString(col + ", " + row, mapTilesArray[col][row].getHitbox().x, mapTilesArray[col][row].getHitbox().y + 10);
                }
                if (mapDecorativeTilesArray[col][row] != null) {
                    position = mapDecorativeTilesArray[col][row].getPosition();
                    g2d.drawImage(mapDecorativeTilesArray[col][row].getImage(), (int) position.x, (int) position.y, mapDecorativeTilesArray[col][row].getWidth(), mapDecorativeTilesArray[col][row].getHeight(), null);
                    //g2d.setColor(Color.GREEN);
                    //g2d.drawRect(mapDecorativeTilesArray[col][row].getHitbox().x, mapDecorativeTilesArray[col][row].getHitbox().y, mapDecorativeTilesArray[col][row].getHitbox().width, mapDecorativeTilesArray[col][row].getHitbox().height);
                }
            }
        }
    }
}

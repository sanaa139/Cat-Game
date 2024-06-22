package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

public class TileManager {
    private GamePanel gamePanel;
    private Tile[][] tilesArray;
    private Tile[][] decorationsTilesArray;
    private Tile[][] menuTilesArray;
    private final Tile[] wallsArray;
    private final Tile[] wallsCornersArray;
    private final Tile[] backgroundsWallsArray;
    private final Tile[] decorationsArray;
    private Tile backgroundTile;
    private Tile backgroundMenuTile;
    private String map;

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        tilesArray = new Tile[gamePanel.getMaxColNum()][gamePanel.getMaxRowNum()];
        decorationsTilesArray = new Tile[gamePanel.getMaxColNum()][gamePanel.getMaxRowNum()];
        menuTilesArray = new Tile[gamePanel.getMaxColNum()][gamePanel.getMaxRowNum()];
        wallsArray = new Tile[8];
        wallsCornersArray = new Tile[4];
        backgroundsWallsArray = new Tile[17];
        decorationsArray = new Tile[16];

        loadSprites();
        map = "menu";
        createMap();
    }

    private void loadSprites(){
        try {
            backgroundTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/background.jpg")), gamePanel, false,"BLACK_BACKGROUND");
        }catch(IOException e){
            e.printStackTrace();
        }

        loadWallSprites();
        loadDecorationsSprites();
        loadMenuSprites();
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
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/backgroundWall" + i + ".jpg")), gamePanel, false,"BACKGROUND " + i);
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
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/platform" + i + ".png")), gamePanel, true, "PLATFORM " + i, 15, gamePanel.getTileSize());
                decorationsArray[i - 1] = tile;
            }
            tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/platform4.png")), gamePanel, true, "PLATFORM4", 14, gamePanel.getTileSize());
            decorationsArray[3] = tile;
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/banner" + i + ".png")), gamePanel, false, "BANNER " + i, 32, gamePanel.getTileSize());
                decorationsArray[i + 3] = tile;
            }
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/window1_" + i + ".png")), gamePanel, false, "WINDOW1_ " + i, 32, gamePanel.getTileSize());
                decorationsArray[i + 7] = tile;
            }
            for(int i = 1; i <= 4; i++){
                tile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/decorations/window2_" + i + ".png")), gamePanel, false, "WINDOW2_ " + i, 32, gamePanel.getTileSize());
                decorationsArray[i + 11] = tile;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void loadMenuSprites(){
        try {
            backgroundMenuTile = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/background/backgroundMenu.jpg")), gamePanel, false,"BLUE_BACKGROUND");
            System.out.println(backgroundMenuTile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createMap(){
        try {
            if(!map.equals("menu")) {
                ClassLoader classLoader = getClass().getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream("maps/" + map + "/" + map + ".txt");
                System.out.println(inputStream);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader);

                int row = 0;

                String line = reader.readLine();
                while (line != null) {
                    String[] words = line.split("\\s+");
                    int i = 0;
                    for (int col = 0; col < gamePanel.getMaxColNum(); col++) {
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
                        tile.setPosition(col * gamePanel.getTileSize(), row * gamePanel.getTileSize());
                        tilesArray[col][row] = tile;
                        i++;
                    }
                    row++;
                    line = reader.readLine();
                }
                reader.close();

                inputStream = classLoader.getResourceAsStream("maps/" + map + "/decorations_" + map + ".txt");
                streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                reader = new BufferedReader(streamReader);

                row = 0;

                line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    String[] words = line.split("\\s+");
                    int i = 0;
                    for (int col = 0; col < gamePanel.getMaxColNum(); col++) {
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
                            default -> null;
                        };
                        if (tile != null) {
                            tile.setPosition(col * gamePanel.getTileSize(), row * gamePanel.getTileSize());
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
            }else {
                ClassLoader classLoader = getClass().getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream("maps/" + map + ".txt");
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        System.out.println(map);
        if(!map.equals("menu")) {
            for (int col = 0; col < tilesArray.length; col++) {
                for (int row = 0; row < tilesArray[col].length; row++) {
                    Vector position = tilesArray[col][row].getPosition();
                    g2d.drawImage(tilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), tilesArray[col][row].getWidth(), tilesArray[col][row].getHeight(), null);
                    if (decorationsTilesArray[col][row] != null) {
                        g2d.drawImage(decorationsTilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), decorationsTilesArray[col][row].getWidth(), decorationsTilesArray[col][row].getHeight(), null);
                    }
                }
            }
        }else{
            System.out.println(map);
            for (int col = 0; col < tilesArray.length; col++) {
                for (int row = 0; row < tilesArray[col].length; row++) {
                    Vector position = menuTilesArray[col][row].getPosition();
                    System.out.print(menuTilesArray[col][row].name);
                    g2d.drawImage(menuTilesArray[col][row].getImage(), (int) position.getX1(), (int) position.getY1(), menuTilesArray[col][row].getWidth(), menuTilesArray[col][row].getHeight(), null);
                }
                System.out.println();
            }
        }
    }

    public Tile[][] getTilesArray() {
        return tilesArray;
    }

    public Tile[][] getDecorationsTilesArray() {
        return decorationsTilesArray;
    }

    public void setMap(String map){
        if(!this.map.equals(map)) {
            this.map = map;
            createMap();
        }
    }
}

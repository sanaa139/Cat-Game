package tiles;

import entity.LoadImagesManager;
import main.Drawable;
import main.GamePanel;

import java.io.IOException;
import java.util.logging.Logger;

public abstract class TileManager implements Drawable {
    private Tile[][][] mapLayers;
    protected String mapName;

    protected abstract void loadSprites();
    public abstract void createMap();

    public void setMap(String mapName){
        if(!this.mapName.equals(mapName)) {
            this.mapName = mapName;
            createMap();
        }
    }

    public Tile[][][] getMapLayers() {
        return mapLayers;
    }

    void setMapLayers(Tile[][]... tiles) {
        mapLayers = new Tile[tiles.length][GamePanel.MAX_COL_NUM][GamePanel.MAX_COL_NUM];

        for(int i = 0;i < tiles.length; i++){
            mapLayers[i] = tiles[i];
        }
    }

    protected Tile loadTile(String baseFolder, String subFolder, String fileName, String fileExtension, boolean isCollidable, String tileName, int height, int width) {
        try {
            return new Tile(
                    LoadImagesManager.loadImage(baseFolder, subFolder, fileName, fileExtension),
                    isCollidable,
                    tileName,
                    height,
                    width
            );
        } catch (IOException e) {
            Logger.getLogger(getClass().getName())
                    .severe("Failed to load tile '" + tileName + "': " + e.getMessage());
            return Tile.empty(tileName, isCollidable, height, width);
        }
    }
}

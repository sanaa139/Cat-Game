package tiles;

import org.w3c.dom.*;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class TiledMapLoader {
    private final List<Layer> layers = new ArrayList<>();
    private final List<TileSet> tileSets = new ArrayList<>();

    public record Layer(int height,
                        int width,
                        int tileHeight,
                        int tileWidth,
                        Tile[][] mapTiles) {}

    public record TileSet (int firstGid,
                           String source,
                           String name,
                           int tileWidth,
                           int tileHeight,
                           int columns,
                           BufferedImage image,
                           Map<Integer, BufferedImage> tileImages,
                           Map<Integer, TileData> tiles) {}

    public record TileData(int localId,
                           int hitboxWidth,
                           int hitboxHeight,
                           boolean isCollidable) {}

    public void loadMapFromResources(String tmxResourcePath) throws Exception {
        try (InputStream is = getResourceAsStream(tmxResourcePath)) {
            if (is == null) throw new RuntimeException("TMX not found in resources: " + tmxResourcePath);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            int tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
            int tileHeight = Integer.parseInt(root.getAttribute("tileheight"));

            NodeList tsList = doc.getElementsByTagName("tileset");
            for (int i = 0; i < tsList.getLength(); i++) {
                Element tsElem = (Element) tsList.item(i);
                String source = tsElem.getAttribute("source");
                int firstGid = tsElem.hasAttribute("firstgid") ? Integer.parseInt(tsElem.getAttribute("firstgid")) : 1;

                TileSet ts = loadTileSetFromResource(source, firstGid);
                tileSets.add(ts);
            }

            NodeList layers = doc.getElementsByTagName("layer");
            for (int i = 0; i < layers.getLength(); i++) {
                Element layer = (Element) layers.item(i);

                int width = Integer.parseInt(layer.getAttribute("width"));
                int height = Integer.parseInt(layer.getAttribute("height"));
                Tile[][] mapTiles = new Tile[height][width];

                Element data = (Element) layer.getElementsByTagName("data").item(0);
                String raw = data.getTextContent().trim();
                String[] tokens = raw.replaceAll("\\s+", "").split(",");
                int filled = 0;
                for (String tok : tokens) {
                    if (tok == null || tok.isEmpty()) continue;
                    int gid = Integer.parseInt(tok);
                    int y = filled / width;
                    int x = filled % width;

                    TileSet chosenTileSet = null;
                    for (TileSet ts : tileSets) {
                        if (gid >= ts.firstGid()) {
                            chosenTileSet = ts;
                        }
                    }

                    if(chosenTileSet != null) {
                        int localId = gid - chosenTileSet.firstGid();
                        TileData tileSelected = chosenTileSet.tiles.get(localId);
                        Tile tile = new Tile(chosenTileSet.tileImages.get(localId), tileSelected.isCollidable, chosenTileSet.tileHeight, chosenTileSet.tileWidth);
                        tile.getHitbox().setSize(tileSelected.hitboxWidth, tileSelected.hitboxHeight);

                        tile.localId = localId;

                        mapTiles[y][x] = tile;
                        mapTiles[y][x].setPosition(x * chosenTileSet.tileWidth, y * chosenTileSet.tileHeight);
                    }
                    filled++;
                }
                this.layers.add(new Layer(height, width, tileHeight, tileWidth, mapTiles));
            }
        }
    }

    private TileSet loadTileSetFromResource(String tsxResourcePath, int firstGid) throws Exception {
        try (InputStream is = getResourceAsStream(tsxResourcePath)) {
            if (is == null) throw new RuntimeException("Tileset (.tsx) not found: " + tsxResourcePath);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();

            String name = root.getAttribute("name");
            int tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
            int tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
            int columns = root.hasAttribute("columns") ? Integer.parseInt(root.getAttribute("columns")) : 0;

            Element imageElem = (Element) root.getElementsByTagName("image").item(0);
            String imgSource = imageElem.getAttribute("source");
            String base = "";
            int idx = tsxResourcePath.lastIndexOf('/');
            if (idx >= 0) base = tsxResourcePath.substring(0, idx + 1);
            String resolvedImg = base + imgSource;

            BufferedImage image;
            try (InputStream imgStream = getResourceAsStream(resolvedImg)) {
                if (imgStream == null) throw new RuntimeException("Tileset image not found: " + resolvedImg);
                image = ImageIO.read(imgStream);
            }

            int cols = columns > 0 ? columns : (image.getWidth() / tileWidth);
            int rows = image.getHeight() / tileHeight;
            Map<Integer, BufferedImage> tileImages = new HashMap<>();
            for (int ry = 0; ry < rows; ry++) {
                for (int cx = 0; cx < cols; cx++) {
                    int localId = ry * cols + cx;
                    if (cx * tileWidth + tileWidth <= image.getWidth()
                            && ry * tileHeight + tileHeight <= image.getHeight()) {
                        BufferedImage sub = image.getSubimage(
                                cx * tileWidth,
                                ry * tileHeight,
                                tileWidth,
                                tileHeight
                        );
                        tileImages.put(localId, sub);
                    }
                }
            }

            Map<Integer, TileData> tiles = loadTilesOfTileSet(root, tileWidth, tileHeight);
            return new TileSet(firstGid, tsxResourcePath, name, tileWidth, tileHeight, columns, image, tileImages, tiles);
        }
    }

    private static Map<Integer, TileData> loadTilesOfTileSet(Element root, int tileWidth, int tileHeight) {
        NodeList tileNodes = root.getElementsByTagName("tile");
        Map<Integer, TileData> tiles = new HashMap<>();
        for (int t = 0; t < tileNodes.getLength(); t++) {
            Element tileElem = (Element) tileNodes.item(t);
            int localId = Integer.parseInt(tileElem.getAttribute("id"));
            int hitboxWidth = tileWidth;
            int hitboxHeight = tileHeight;
            boolean collidable = false;

            NodeList propNodes = tileElem.getElementsByTagName("property");
            for (int p = 0; p < propNodes.getLength(); p++) {
                Element prop = (Element) propNodes.item(p);
                String propName = prop.getAttribute("name");
                String val = prop.getAttribute("value");
                if ("collidable".equals(propName) && "true".equalsIgnoreCase(val)) collidable = true;
            }

            NodeList objectgroups = tileElem.getElementsByTagName("objectgroup");
            for (int og = 0; og < objectgroups.getLength(); og++) {
                Element ogElem = (Element) objectgroups.item(og);
                NodeList objects = ogElem.getElementsByTagName("object");
                for (int oi = 0; oi < objects.getLength(); oi++) {
                    Element oElem = (Element) objects.item(oi);
                    hitboxWidth = Integer.parseInt(oElem.getAttribute("width"));
                    hitboxHeight = Integer.parseInt(oElem.getAttribute("height"));


                    NodeList propNodes2 = oElem.getElementsByTagName("property");
                    for (int p2 = 0; p2 < propNodes2.getLength(); p2++) {
                        Element prop2 = (Element) propNodes2.item(p2);
                        if ("collidable".equals(prop2.getAttribute("name"))
                                && "true".equalsIgnoreCase(prop2.getAttribute("value"))) {
                            collidable = true;
                        }
                    }
                }
            }
            tiles.put(localId, new TileData(localId, hitboxWidth, hitboxHeight, collidable));
        }
        return tiles;
    }

    private InputStream getResourceAsStream(String resourcePath) {
        String rp = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
        return TiledMapLoader.class.getResourceAsStream(rp);
    }

    public void drawMap(Graphics g) {
        for(Layer layer : layers){
            if (layer == null) continue;
            for (int y = 0; y < layer.height; y++) {
                for (int x = 0; x < layer.width; x++) {
                    if (layer.mapTiles[y][x] != null) {
                        g.drawImage(layer.mapTiles[y][x].getImage(), x * layer.tileWidth, y * layer.tileHeight, layer.tileWidth, layer.tileHeight, null);
                    }
                }
            }
        }
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public List<TileSet> getTileSets() {
        return tileSets;
    }
}

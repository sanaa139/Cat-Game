package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadImagesManager{
    public static Image loadImage(String baseFolder, String subFolder, String fileName, String fileExtension) throws IOException {
        String path = "/" + baseFolder + "/" + subFolder + "/" + fileName + "."  + fileExtension;
        var stream = LoadImagesManager.class.getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return ImageIO.read(stream);
    }

    public static BufferedImage createPlaceholderImage(Color backgroundColor) {
        int width = GamePanel.TILE_SIZE, height = GamePanel.TILE_SIZE;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        String text = "?";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int x = (width - textWidth) / 2;
        int y = (height + textHeight) / 2;

        g2d.drawString(text, x, y);

        g2d.dispose();
        return img;
    }
}

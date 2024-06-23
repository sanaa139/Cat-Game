package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;

public class Button extends JPanel{
    private Image image;
    private int x, y, width, height;
    public Button(Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2d) {
        super.paintComponent(g2d);

        g2d.drawImage(image, x, y, width, height, null);
    }
}

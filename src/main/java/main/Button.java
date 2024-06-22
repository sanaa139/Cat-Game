package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;

public class Button extends JPanel{
    private Shape shape;
    public Button(int x, int y) {
        shape = new Rectangle(x, y, 50, 50);
    }

    protected void draw(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        g2.draw(shape);
    }

    public Shape getShape(){
        return shape;
    }
}

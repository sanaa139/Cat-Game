package main;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton{
    public Button(String text, int x, int y, int width, int height) {
        super(text);
        this.setBounds(x, y, width, height);
    }
}

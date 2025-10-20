package main;

import java.awt.*;

public class Button {
    private final String text;
    private final int fontSize;
    private final Rectangle buttonBounds;

    public Button(int posX, int posY, int width, int height, String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize;

        buttonBounds = new Rectangle(posX, posY, width, height);
    }

    public void drawButton(Graphics2D g2d) {
        Color baseColor = new Color(255, 128, 0);
        Color borderColor = new Color(0, 0, 0);

        g2d.setColor(baseColor);
        g2d.fillRoundRect(buttonBounds.x, buttonBounds.y,
                buttonBounds.width, buttonBounds.height, 20, 20);

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(borderColor);
        g2d.drawRoundRect(buttonBounds.x, buttonBounds.y,
                buttonBounds.width, buttonBounds.height, 20, 20);

        g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
        g2d.setColor(Color.WHITE);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = buttonBounds.x + (buttonBounds.width - fm.stringWidth(text)) / 2;
        int textY = buttonBounds.y + ((buttonBounds.height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, textX, textY);
    }

    public Rectangle getButtonBounds() {
        return buttonBounds;
    }
}

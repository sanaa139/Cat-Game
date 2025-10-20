package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean leftPressed, rightPressed, upPressed;

    private void switchVariables(int code, boolean b){
        switch (code) {
            case KeyEvent.VK_A -> leftPressed = b;
            case KeyEvent.VK_D -> rightPressed = b;
            case KeyEvent.VK_W -> upPressed = b;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switchVariables(code, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switchVariables(code, false);
    }
}

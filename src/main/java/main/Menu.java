package main;

import tiles.Tile;
import tiles.TileManager;

public class Menu {
    public Button[] buttons;
    private String mapName;
    private Tile[][] map;
    private TileManager tileManager;
    public Menu(TileManager tileManager, Button... buttons){
        this.tileManager = tileManager;
        this.buttons = new Button[buttons.length];
        for(int i = 0; i < buttons.length; i++){
            this.buttons[i] = buttons[i];
        }
    }

    public void draw(){

    }
}

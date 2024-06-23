package main;

import tiles.Tile;
import tiles.TileManagerMenu;

public class Menu {
    public Button[] buttons;
    private String mapName;
    private Tile[][] map;
    private TileManagerMenu tileManagerMenu;
    public Menu(TileManagerMenu tileManagerMenu, Button... buttons){
        this.tileManagerMenu = tileManagerMenu;
        this.buttons = new Button[buttons.length];
        for(int i = 0; i < buttons.length; i++){
            this.buttons[i] = buttons[i];
        }
    }

    public void draw(){

    }
}

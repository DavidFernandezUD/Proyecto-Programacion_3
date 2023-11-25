package main.objects;

import main.interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    

    public void setObjects() {
		
    	gamePanel.obj[0] = new OBJ_Sign();
    	gamePanel.obj[0].worldX = 6 * gamePanel.tileSize;
    	gamePanel.obj[0].worldY = 32 * gamePanel.tileSize;
		
	}
}
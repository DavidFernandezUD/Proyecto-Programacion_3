package main.objects;

import main.GamePanel;


public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    

    public void setObjects() {
		
    	gamePanel.obj[0] = new OBJ_Sign();
    	gamePanel.obj[0].worldX = (6) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[0].worldY = (31) * gamePanel.tileSize;
		
	}
}
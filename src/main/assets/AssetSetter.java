package main.assets;

import main.GamePanel;

/** Manages main.assets and places them in the map.
 * @author marcos.martinez@opendeusto.es*/
public class AssetSetter {

    GamePanel gamePanel;

	/** Creates an asset setter object.*/
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

	/** Stores the main.assets in the main.assets array and manually locates
	 * them around the map.*/
    public void setAssets() {

    	// SIGNS
    	gamePanel.assets[0] = new ASSET_Sign();
    	gamePanel.assets[0].worldX = (6) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[0].worldY = (31) * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[0]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 1!";
 	
    	gamePanel.assets[1] = new ASSET_Sign();
    	gamePanel.assets[1].worldX = (28) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[1].worldY = (27) * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[1]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 2!";	
    	
    	gamePanel.assets[2] = new ASSET_Sign();
    	gamePanel.assets[2].worldX = (31) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[2].worldY = (11) * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[2]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 3!";
    	
    	gamePanel.assets[3] = new ASSET_Sign();
    	gamePanel.assets[3].worldX = (18) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[3].worldY = (6) * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[3]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 4!";
    	
    	// GRAVES
    	gamePanel.assets[4] = new ASSET_Grave();
    	gamePanel.assets[4].worldX = (2) * gamePanel.tileSize;
    	gamePanel.assets[4].worldY = (26) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[4]).text = "Aquí yace El Fary";
    	
    	gamePanel.assets[5] = new ASSET_Grave();
    	gamePanel.assets[5].worldX = (1) * gamePanel.tileSize;
    	gamePanel.assets[5].worldY = (26) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[5]).text = "Aquí yace Frank Sinatra";
    	
    	// CHESTS
    	gamePanel.assets[6] = new ASSET_Chest();
    	gamePanel.assets[6].worldX = (21) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[6].worldY = (2) * gamePanel.tileSize;  	 
		
	}
}
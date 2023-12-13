package main.objects;

import main.GamePanel;


public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    

    public void setObjects() {
		
    	// MAP 1
    	
    	// Signs
    	gamePanel.obj[0] = new OBJ_Sign();
    	gamePanel.obj[0].worldX = (6) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[0].worldY = (31) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.obj[0]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 1!";
    	
    	
    	gamePanel.obj[1] = new OBJ_Sign();
    	gamePanel.obj[1].worldX = (28) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[1].worldY = (27) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.obj[1]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 2!";
    	
    	gamePanel.obj[2] = new OBJ_Sign();
    	gamePanel.obj[2].worldX = (31) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[2].worldY = (11) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.obj[2]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 3!";
    	
    	gamePanel.obj[3] = new OBJ_Sign();
    	gamePanel.obj[3].worldX = (18) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[3].worldY = (6) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.obj[3]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 4!";
    	
    	// Graves
    	gamePanel.obj[4] = new OBJ_Grave();
    	gamePanel.obj[4].worldX = (2) * gamePanel.tileSize;
    	gamePanel.obj[4].worldY = (26) * gamePanel.tileSize;
    	((OBJ_Grave) gamePanel.obj[4]).text = "Aquí yace El Fary";
    	
    	gamePanel.obj[5] = new OBJ_Grave();
    	gamePanel.obj[5].worldX = (1) * gamePanel.tileSize;
    	gamePanel.obj[5].worldY = (26) * gamePanel.tileSize;
    	((OBJ_Grave) gamePanel.obj[5]).text = "Aquí yace Frank Sinatra";
    	
    	// Chests
    	gamePanel.obj[6] = new OBJ_Chest();
    	gamePanel.obj[6].worldX = (21) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[6].worldY = (2) * gamePanel.tileSize;  	
    	
    	gamePanel.obj[7] = new OBJ_Chest();
    	gamePanel.obj[7].worldX = (25) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.obj[7].worldY = (27) * gamePanel.tileSize;  
		
	}
}
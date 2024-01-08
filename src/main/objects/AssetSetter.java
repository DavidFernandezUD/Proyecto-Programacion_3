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
    	gamePanel.objects[0] = new OBJ_Sign();
    	gamePanel.objects[0].worldX = (6) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.objects[0].worldY = (31) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.objects[0]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 1!";
    	
    	
    	gamePanel.objects[1] = new OBJ_Sign();
    	gamePanel.objects[1].worldX = (28) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.objects[1].worldY = (27) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.objects[1]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 2!";
    	
    	gamePanel.objects[2] = new OBJ_Sign();
    	gamePanel.objects[2].worldX = (31) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.objects[2].worldY = (11) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.objects[2]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 3!";
    	
    	gamePanel.objects[3] = new OBJ_Sign();
    	gamePanel.objects[3].worldX = (18) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.objects[3].worldY = (6) * gamePanel.tileSize;
    	((OBJ_Sign) gamePanel.objects[3]).text = "¡Si puedes leer esto el commit ha sido todo \nun éxito 4!";
    	
    	// Graves
    	gamePanel.objects[4] = new OBJ_Grave();
    	gamePanel.objects[4].worldX = (2) * gamePanel.tileSize;
    	gamePanel.objects[4].worldY = (26) * gamePanel.tileSize;
    	((OBJ_Grave) gamePanel.objects[4]).text = "Aquí yace El Fary";
    	
    	gamePanel.objects[5] = new OBJ_Grave();
    	gamePanel.objects[5].worldX = (1) * gamePanel.tileSize;
    	gamePanel.objects[5].worldY = (26) * gamePanel.tileSize;
    	((OBJ_Grave) gamePanel.objects[5]).text = "Aquí yace Frank Sinatra";
    	
    	// Chests
    	gamePanel.objects[6] = new OBJ_Chest();
    	gamePanel.objects[6].worldX = (21) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.objects[6].worldY = (2) * gamePanel.tileSize;  	 
		
	}
}
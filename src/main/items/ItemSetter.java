package main.items;

import main.GamePanel;
import main.entities.Player;

public class ItemSetter {
	
	GamePanel gamePanel;
	
	public ItemSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setItem () {
		
		gamePanel.items[0] = new ITEM_woodenSword();
		gamePanel.items[0].worldX = (25) * gamePanel.tileSize;
		gamePanel.items[0].worldY = (32) * gamePanel.tileSize;
		
		gamePanel.items[1] = new ITEM_ironSword();
		gamePanel.items[1].worldX = (26) * gamePanel.tileSize;
		gamePanel.items[1].worldY = (32) * gamePanel.tileSize;
		
		gamePanel.items[2] = new ITEM_goldenSword();
		gamePanel.items[2].worldX = (27) * gamePanel.tileSize;
		gamePanel.items[2].worldY = (32) * gamePanel.tileSize;
		
		gamePanel.items[3] = new ITEM_bloodySword();
		gamePanel.items[3].worldX = (28) * gamePanel.tileSize;
		gamePanel.items[3].worldY = (32) * gamePanel.tileSize;
		
		gamePanel.items[4] = new ITEM_bow();
		gamePanel.items[4].worldX = (29) * gamePanel.tileSize;
		gamePanel.items[4].worldY = (32) * gamePanel.tileSize;
		
		gamePanel.items[5] = new ITEM_apple();
		gamePanel.items[5].worldX = (25) * gamePanel.tileSize;
		gamePanel.items[5].worldY = (31) * gamePanel.tileSize;
		
		gamePanel.items[6] = new ITEM_redPotion();
		gamePanel.items[6].worldX = (26) * gamePanel.tileSize;
		gamePanel.items[6].worldY = (31) * gamePanel.tileSize;
		
		gamePanel.items[7] = new ITEM_purplePotion();
		gamePanel.items[7].worldX = (27) * gamePanel.tileSize;
		gamePanel.items[7].worldY = (31) * gamePanel.tileSize;
		
		gamePanel.items[8] = new ITEM_shield();
		gamePanel.items[8].worldX = (28) * gamePanel.tileSize;
		gamePanel.items[8].worldY = (31) * gamePanel.tileSize;
		
	}
	
}

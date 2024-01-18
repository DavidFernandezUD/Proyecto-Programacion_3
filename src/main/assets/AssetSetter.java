package main.assets;

import main.GamePanel;
import main.items.ITEM_apple;
import main.items.ITEM_bloodySword;
import main.items.ITEM_bow;
import main.items.ITEM_goldenSword;
import main.items.ITEM_ironSword;
import main.items.ITEM_purplePotion;
import main.items.ITEM_redPotion;
import main.items.ITEM_shield;
import main.items.ITEM_woodenSword;
import main.items.SuperItem;

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
    	
    	int i = 0;

    	// SIGNS	
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 88 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 77 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Shadowvale";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 66 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 81 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Bloodstone";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 91 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 61 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Grimhaven";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 78 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 56 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Cursed Hollow";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 75 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 27 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Raven's End";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 92 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 17 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Blackthorn";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 42 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 24 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Veilstead";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 49 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 68 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Nightshade";
    	i++;
    	gamePanel.assets[i] = new ASSET_Sign();
    	gamePanel.assets[i].worldX = 52 * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = 68 * gamePanel.tileSize;
    	((ASSET_Sign) gamePanel.assets[i]).text = "Ashenfell";
    	i++;
	
    	// GRAVES
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (61) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (26) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Seraphina Drakon";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (59) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (26) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Malachai Vesper";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (57) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (26) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Isolde Darkthorn";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (61) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (24) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Eldritch Nightshade";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (59) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (24) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Valeria Blackthorn";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (57) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (24) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Lucian Shadowheart";
    	i++;  	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (23) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (47) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Morgana Graves";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (28) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (42) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Aric Grimshaw";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (21) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (34) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Lyra Darkspire";
    	i++;  	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (22) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (85) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Thaddeus Bloodstone";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (21) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (85) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Elysia Ravenshadow";
    	i++;  	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (38) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (88) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Dorian Ashenfell";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (37) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (88) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Dorian Ashenfell";
    	i++;    	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (62) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (76) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Celestia Voidstrider";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (61) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (76) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Vespera Moonshadow";
    	i++;    	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (30) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (83) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Theronn Lobs";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (29) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (83) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Morrigan Darkthorn";
    	i++;   	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (64) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (56) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Elowen Nightshade";
    	i++;    	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (57) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (37) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Roderick Shadowgrave";
    	i++;   	
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (42) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (43) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Seraphiel Nocturne";
    	i++;
    	gamePanel.assets[i] = new ASSET_Grave();
    	gamePanel.assets[i].worldX = (40) * gamePanel.tileSize;
    	gamePanel.assets[i].worldY = (43) * gamePanel.tileSize;
    	((ASSET_Grave) gamePanel.assets[i]).text = "Here lies Alaric Stoneheart";
    	i++;
   	
    	// CHESTS
     	ASSET_Chest chest1 = new ASSET_Chest();
    	chest1.setItems(new SuperItem[] {new ITEM_apple(), new ITEM_apple(), new ITEM_woodenSword()});
    	gamePanel.assets[i] = chest1;
    	gamePanel.assets[i].worldX = (81) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (52) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest2 = new ASSET_Chest();
    	chest2.setItems(new SuperItem[] {new ITEM_redPotion(), new ITEM_ironSword(), new ITEM_apple()});
    	gamePanel.assets[i] = chest2;
    	gamePanel.assets[i].worldX = (34) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (56) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest3 = new ASSET_Chest();
    	chest3.setItems(new SuperItem[] {new ITEM_goldenSword(), new ITEM_apple(), new ITEM_apple(), new ITEM_shield()});
    	gamePanel.assets[i] = chest3;
    	gamePanel.assets[i].worldX = (31) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (86) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest4 = new ASSET_Chest();
    	chest4.setItems(new SuperItem[] {new ITEM_bloodySword(), new ITEM_apple(), new ITEM_redPotion()});
    	gamePanel.assets[i] = chest4;
    	gamePanel.assets[i].worldX = (25) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (32) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest5 = new ASSET_Chest();
    	chest5.setItems(new SuperItem[] {new ITEM_purplePotion(), new ITEM_apple(), new ITEM_apple()});
    	gamePanel.assets[i] = chest5;
    	gamePanel.assets[i].worldX = (24) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (32) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest6 = new ASSET_Chest();
    	chest6.setItems(new SuperItem[] {new ITEM_apple(), new ITEM_bow(), new ITEM_redPotion()});
    	gamePanel.assets[i] = chest6;
    	gamePanel.assets[i].worldX = (65) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (48) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest7 = new ASSET_Chest();
    	chest7.setItems(new SuperItem[] {new ITEM_purplePotion(), new ITEM_redPotion(), new ITEM_shield()});
    	gamePanel.assets[i] = chest7;
    	gamePanel.assets[i].worldX = (66) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (41) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest8 = new ASSET_Chest();
    	chest8.setItems(new SuperItem[] {new ITEM_redPotion(), new ITEM_apple(), new ITEM_apple(), new ITEM_ironSword()});
    	gamePanel.assets[i] = chest8;
    	gamePanel.assets[i].worldX = (65) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (41) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest9 = new ASSET_Chest();
    	chest9.setItems(new SuperItem[] {new ITEM_purplePotion(), new ITEM_apple(), new ITEM_goldenSword()});
    	gamePanel.assets[i] = chest9;
    	gamePanel.assets[i].worldX = (41) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (30) * gamePanel.tileSize; 
    	i++;
    	ASSET_Chest chest10 = new ASSET_Chest();
    	chest10.setItems(new SuperItem[] {new ITEM_apple(), new ITEM_apple(), new ITEM_bow()});
    	gamePanel.assets[i] = chest10;
    	gamePanel.assets[i].worldX = (40) * gamePanel.tileSize - gamePanel.tileSize/4;
    	gamePanel.assets[i].worldY = (57) * gamePanel.tileSize; 
    	i++;
    	
		
	}
}
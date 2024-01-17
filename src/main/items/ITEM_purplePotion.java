package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Purple Potion item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_purplePotion extends SuperItem {
	
	public ITEM_purplePotion() {
		name = "Purple Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/purplePotion.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Purple Potion Sprite", e);
		}
		
		description = "[" + name + "]\nHeals you completely.";
		
	}

}

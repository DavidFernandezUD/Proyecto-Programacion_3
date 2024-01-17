package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Red Potion item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_redPotion extends SuperItem {
	
	public ITEM_redPotion() {
		name = "Red Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/redPotion.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading red Potion Sprite", e);
		}
		
		description = "[" + name + "]\nHeals two and a half hearts.";
	}

}

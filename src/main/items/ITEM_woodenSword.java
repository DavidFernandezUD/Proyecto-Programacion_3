package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Wooden Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_woodenSword extends SuperItem {
	
	public ITEM_woodenSword() {
		
		name = "Wooden Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/woodenSword.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Wooden Sword Sprite", e);
		}
		
		description = "[" + name + "]\nIncreases damage by .";
	}

}

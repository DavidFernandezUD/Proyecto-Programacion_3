package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Iron Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_ironSword extends SuperItem {
	
	public ITEM_ironSword() {
		
		name = "Iron Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/ironSword.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Iron Sword Sprite", e);
		}
		
		description = "[" + name + "]\nIncreases damage by .";
	}

}

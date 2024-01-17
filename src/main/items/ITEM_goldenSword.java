package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Golden Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_goldenSword extends SuperItem {
	
	public ITEM_goldenSword() {
		
		name = "Golden Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/goldenSword.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Golden Sword Sprite", e);
		}
		
		description = "[" + name + "]\nIncreases damage by .";
	}

}

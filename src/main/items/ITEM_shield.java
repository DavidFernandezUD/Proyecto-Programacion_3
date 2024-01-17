package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Shield item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_shield extends SuperItem {
	
	public ITEM_shield() {
		name = "Shield";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/shield.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Shield Sprite", e);
		}
		
		description = "[" + name + "]\nGives you  more of defense.";
	}

}

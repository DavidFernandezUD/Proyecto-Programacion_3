package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Bloody Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_bloodySword extends SuperItem {
	
	public ITEM_bloodySword() {
		
		name = "Bloody Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/bloodySword.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Bloody Sword Sprite", e);
		}
		
		description = "[" + name + "]\nIncreases damage by .";
		
	}

}

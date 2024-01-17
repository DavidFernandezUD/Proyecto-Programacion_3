package main.items;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Bow item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_bow extends SuperItem {
	
	public ITEM_bow() {
		
		name = "Bow";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/bow.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Bow Sprite", e);
		}
		
		description = "[" + name + "]\nEach arrow does  damage.";
		
	}

}

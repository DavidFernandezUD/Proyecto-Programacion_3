package main.items;

import main.Game;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.logging.Level;

/** Apple item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_apple extends SuperItem {
	
	public ITEM_apple() {
		name = "Apple";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/apple.png"));
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Apple Sprite", e);
		}
		
		description = "[" + name + "]\nHeals one heart.";
		
	}

}

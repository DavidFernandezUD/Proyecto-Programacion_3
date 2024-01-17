package main.items;

import javax.imageio.ImageIO;

/** Apple item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_apple extends SuperItem {
	
	public ITEM_apple() {
		name = "Apple";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/apple.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nHeals one heart.";
		
	}

}

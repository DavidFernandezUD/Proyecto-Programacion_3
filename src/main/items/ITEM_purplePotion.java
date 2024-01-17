package main.items;

import javax.imageio.ImageIO;

/** Purple Potion item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_purplePotion extends SuperItem {
	
	public ITEM_purplePotion() {
		name = "Purple Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/purplePotion.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nHeals you completely.";
		
	}

}

package main.items;

import javax.imageio.ImageIO;

/** Red Potion item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_redPotion extends SuperItem {
	
	public ITEM_redPotion() {
		name = "Red Potion";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/redPotion.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nHeals two and a half hearts.";
	}

}

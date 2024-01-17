package main.items;

import javax.imageio.ImageIO;

/** Iron Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_ironSword extends SuperItem {
	
	public ITEM_ironSword() {
		
		name = "Iron Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/ironSword.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nIncreases damage by .";
	}

}

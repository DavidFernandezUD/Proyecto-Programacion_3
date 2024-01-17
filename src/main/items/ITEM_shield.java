package main.items;

import javax.imageio.ImageIO;

/** Shield item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_shield extends SuperItem {
	
	public ITEM_shield() {
		name = "Shield";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/shield.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nGives you  more of defense.";
	}

}

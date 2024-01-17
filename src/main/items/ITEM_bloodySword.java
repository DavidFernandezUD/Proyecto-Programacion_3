package main.items;

import javax.imageio.ImageIO;

/** Bloody Sword item.
 * @author marcos.martinez@opendeusto.es*/
public class ITEM_bloodySword extends SuperItem {
	
	public ITEM_bloodySword() {
		
		name = "Bloody Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/bloodySword.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nIncreases damage by .";
		
	}

}

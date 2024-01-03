package main.items;

import javax.imageio.ImageIO;

public class ITEM_ironSword extends SuperItem {
	
	public ITEM_ironSword() {
		
		name = "Iron Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/ironSword.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

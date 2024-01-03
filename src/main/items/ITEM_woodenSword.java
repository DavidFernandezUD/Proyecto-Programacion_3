package main.items;

import javax.imageio.ImageIO;

public class ITEM_woodenSword extends SuperItem {
	
	public ITEM_woodenSword() {
		
		name = "Wooden Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/woodenSword.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

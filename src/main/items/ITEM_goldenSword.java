package main.items;

import javax.imageio.ImageIO;

public class ITEM_goldenSword extends SuperItem {
	
	public ITEM_goldenSword() {
		
		name = "Golden Sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/goldenSword.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nIncreases damage by .";
	}

}

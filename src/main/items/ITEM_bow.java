package main.items;

import javax.imageio.ImageIO;

public class ITEM_bow extends SuperItem {
	
	public ITEM_bow() {
		
		name = "Bow";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/res/items/bow.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		description = "[" + name + "]\nEach arrow does  damage.";
		
	}

}

package main.assets;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.items.SuperItem;

/**
 * Chest asset.
 * 
 * @author marcos.martinez@opendeusto.es
 */
public class ASSET_Chest extends SuperAsset {

	public ArrayList<SuperItem> chestItems = new ArrayList<>();
	public final int maxChestItems = 20;

	public ASSET_Chest() {

		name = "Chest";

		solidArea = new Rectangle(0, 0, 16 * 6, 64 * 2);
	}

	/**
	 * Sets the chest's items.
	 * 
	 * @param itemList Array object of SuperItem objects that contains the items to be added to the chest.
	 */
	public void setItems(SuperItem[] itemList) {
		if (chestItems.size() < maxChestItems) {
			if (itemList.length <= maxChestItems) {
				for (SuperItem si: itemList) {
					if (si != null) {
						chestItems.add(si);
					}					
				}
			}
		}
	}

}

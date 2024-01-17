package main.assets;

import java.awt.Rectangle;

/** Chest asset.
 * @author marcos.martinez@opendeusto.es*/
public class ASSET_Chest extends SuperAsset {
	
	public ASSET_Chest() {
		
		name = "Chest";
		
		solidArea = new Rectangle(0, 0, 16*6, 64*2);
	}

}

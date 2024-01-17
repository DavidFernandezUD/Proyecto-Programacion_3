package main.assets;

import java.awt.Rectangle;

/** Grave asset.
 * @author marcos.martinez@opendeusto.es*/
public class ASSET_Grave extends SuperAsset {
	
	public String text;
	
	public ASSET_Grave() {
		
		name = "Grave";
		
//		solidArea = new Rectangle(0, 0, 16*6, 64*2);
		solidArea = new Rectangle(0, 0, 64, 115);
	}	
}

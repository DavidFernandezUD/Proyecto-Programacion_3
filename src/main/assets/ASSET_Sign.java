package main.assets;

import java.awt.Rectangle;

public class ASSET_Sign extends SuperAsset {
	
	public String text;
	
	public ASSET_Sign() {
		
		name = "Sign";
		
		solidArea = new Rectangle(0, 0, 16*6, 64*2);
	}
}

package main.objects;

import java.awt.Rectangle;

public class OBJ_Grave extends SuperObject {
	
	public String text;
	
	public OBJ_Grave() {
		
		name = "Grave";
		
//		solidArea = new Rectangle(0, 0, 16*6, 64*2);
		solidArea = new Rectangle(0, 0, 64, 115);
		
		// TODO: fix grave reading overlap
		
	}

	
}

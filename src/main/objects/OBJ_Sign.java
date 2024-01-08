package main.objects;

import java.awt.Rectangle;

public class OBJ_Sign extends SuperObject{
	
	public String text;
	
	public OBJ_Sign() {
		
		name = "Sign";
		
		solidArea = new Rectangle(0, 0, 16*6, 64*2);


	}
	

}

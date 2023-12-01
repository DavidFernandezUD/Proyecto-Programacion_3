package main.objects;

import java.awt.Rectangle;

import main.GamePanel;


public class OBJ_Sign extends SuperObject{
	
	public GamePanel gamePanel;
	
	public OBJ_Sign() {
		
		name = "Sign";
		
		solidArea = new Rectangle(0, 0, 16*6, 64*2);
		
		setDialogue();
		

	}
	
	public void setDialogue() {
		
		dialogues[0] = "Tu eres el acaqueilla salegere bandoleros poeruque";
	}
	
	public void speak() {
//		gamePanel.dialogScreen.currentDialogue = 
		
		// TODO: fix sign dialogue
	}
}

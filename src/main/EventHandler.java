package main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class EventHandler {

	GamePanel gamePanel;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		eventRect = new Rectangle();
		eventRect.x = 23;
		eventRect.y = 23;
		eventRect.width = 2;
		eventRect.height = 2;
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}
	
	public void checkEvent() {
		if (hit(25, 32, "up") == true) {
			readSign();
		}
	}
	
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		
		gamePanel.player.collisionBox.x = gamePanel.player.worldX + gamePanel.player.collisionBox.x;
		gamePanel.player.collisionBox.y = gamePanel.player.worldY + gamePanel.player.collisionBox.y;
		eventRect.x = eventCol*gamePanel.tileSize + eventRect.x;
		eventRect.y = eventRow*gamePanel.tileSize + eventRect.y;
		
		if (gamePanel.player.collisionBox.intersects(eventRect)) {
			if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gamePanel.player.collisionBox.x = gamePanel.player.collisionBoxDefaultX;
		gamePanel.player.collisionBox.y = gamePanel.player.collisionBoxDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		
		return hit;
	}
	
	public void readSign() {
		if (gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER) == true && gamePanel.player.collisionBox.intersects(new Rectangle(25, 32, gamePanel.tileSize, gamePanel.tileSize))) {
			System.out.println("Hola");
			gamePanel.dialogueState = true;
			gamePanel.dialogueScreen.currentDialogue = "Just a sign...";
		}	
	}
}

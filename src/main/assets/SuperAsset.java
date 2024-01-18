package main.assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;

/** Base class for asset objects.
 * @author marcos.martinez@opendeusto.es*/
public class SuperAsset {

//	public String dialogue;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea;
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;

	public boolean debug = true;

	/** Draws interaction bow of main.assets for debugging purposes.*/
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		g2.setColor(new Color(0, 0, 139, 127));
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		if (debug) {
			if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
					&& worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
					&& worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
					&& worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
				g2.fillRect(screenX, screenY, solidArea.width, solidArea.height);
			}
		}
	}
}
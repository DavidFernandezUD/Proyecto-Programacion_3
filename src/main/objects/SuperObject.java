package main.objects;

import java.awt.Rectangle;

public class SuperObject {
	
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    
    
}
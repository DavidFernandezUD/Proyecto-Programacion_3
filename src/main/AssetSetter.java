package main;

import object.OBJ_Sign;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Sign();
        gp.obj[0].worldX = 32 * gp.tileSize;
        gp.obj[0].worldY = 51 * gp.tileSize;

    }

}
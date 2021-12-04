package Object;

import Entities.Enemy;
import Main.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objectManagement.obj[0] = new Crate();
        gp.objectManagement.obj[0].worldX= 10*gp.tileSize;
        gp.objectManagement.obj[0].worldY= 10*gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new Enemy(gp);
        gp.npc[0].worldX = gp.tileSize*8;
        gp.npc[0].worldY = gp.tileSize*8;
    }
}
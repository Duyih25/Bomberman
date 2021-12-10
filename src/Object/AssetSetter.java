package Object;

import Entities.BlackDevil;
import Entities.Enemy;
import Entities.RedDevil;
import Main.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objectManagement.obj[0] = new Crate(gp);
        gp.objectManagement.obj[0].worldX= 10*gp.tileSize;
        gp.objectManagement.obj[0].worldY= 10*gp.tileSize;
    }

    public void setNPC() {
        gp.npc[0] = new BlackDevil(gp);
        gp.npc[0].worldX = gp.tileSize*8;
        gp.npc[0].worldY = gp.tileSize*8;
        gp.npc[1] = new RedDevil(gp);
        gp.npc[1].worldX = gp.tileSize*6;
        gp.npc[1].worldY = gp.tileSize*6;
    }
}

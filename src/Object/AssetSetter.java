package Object;

import Entities.BlackDevil;
import Entities.RedDevil;
import Main.GamePanel;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.objectManagement.obj.add(new Block(gp));
        gp.objectManagement.obj.get(gp.objectManagement.obj.size() - 1).worldX= 10*gp.tileSize;
        gp.objectManagement.obj.get(gp.objectManagement.obj.size() - 1).worldY= 10*gp.tileSize;

        //them tam item bomb
        gp.objectManagement.obj.add(new BombItem(gp, 6*64, 6*64));
        gp.objectManagement.obj.add(new SpeedItem(gp, 7*64, 6*64));
        gp.objectManagement.obj.add(new BombItem(gp, 8*64, 6*64));
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

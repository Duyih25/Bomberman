package Object;

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
}

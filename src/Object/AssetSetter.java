package Object;

import Entities.BlackDevil;
import Entities.RedDevil;
import Entities.YellowDevil;
import Main.GamePanel;

import java.awt.image.BufferedImage;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(BufferedImage image) {
        int x = 0,y = 0;
        while (x < image.getWidth() && y < image.getHeight()) {

            int pixel = image.getRGB(x,y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;

            if (red == 255 && green == 255 && blue == 255) {
                gp.objectManagement.blockList.add(new Block(gp, x * 64, y * 64, false));
            } else if (red == 204 && green == 204 && blue == 204) {
                gp.objectManagement.blockList.add(new Block(gp, x * 64, y * 64, true));

            }
            x += 1;
            if (x == image.getWidth()) {
                x = 0;
                y++;
            }
        }

        //them tam item bomb
        gp.objectManagement.obj.add(new BombItem(gp, 6*64, 6*64));
        gp.objectManagement.obj.add(new SpeedItem(gp, 7*64, 6*64));
        gp.objectManagement.obj.add(new FlameItem(gp, 8*64, 6*64));
    }

    public void setNPC() {
        gp.npc[0] = new BlackDevil(gp);
        gp.npc[0].worldX = gp.tileSize*8;
        gp.npc[0].worldY = gp.tileSize*8;
        gp.npc[1] = new RedDevil(gp);
        gp.npc[1].worldX = gp.tileSize*6;
        gp.npc[1].worldY = gp.tileSize*6;
        gp.npc[2] = new YellowDevil(gp);
        gp.npc[2].worldX = gp.tileSize*13;
        gp.npc[2].worldY = gp.tileSize*13;
    }
}

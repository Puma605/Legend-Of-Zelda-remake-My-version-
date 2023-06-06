package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_GoldCoin extends Entity {
    GamePanel gp;
    public static final String OBJ_NAME = "Gold Coin";
    public OBJ_GoldCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typePickUpOnly;
        name = OBJ_NAME;
        value = 1;
        down1 = setup("/objects/GoldCoin", GamePanel.TileSize, GamePanel.TileSize);
    }

    public boolean use(Entity entity) {
     gp.playSoundEffect(1);
     entity.coin += value;
     return true;
    }

    
}

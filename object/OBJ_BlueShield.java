package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueShield extends Entity {

    public static final String OBJ_NAME = "Blue Shield";
    public OBJ_BlueShield(GamePanel gp) {
        super(gp);
         
        price = 50;
        type = typeShield;
        defenseValue = 2;
        name = OBJ_NAME;
        down1 = setup("/objects/BlueShield", GamePanel.TileSize, GamePanel.TileSize);
        description = "[" + name + "]"+"\n A nice new blue shield.";
        
    }
}

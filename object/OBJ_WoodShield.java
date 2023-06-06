package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_WoodShield extends Entity{
    public static final String OBJ_NAME = "Wood Shield";
    public OBJ_WoodShield(GamePanel gp) {
        super(gp);
        price = 25;
        type = typeShield;
        name = OBJ_NAME;
        down1 = setup("/objects/WoodShield", GamePanel.TileSize, GamePanel.TileSize);
        defenseValue = 1;
        description = "["+ name +"]"+ "\nA wooden shield.";
    }
    
}

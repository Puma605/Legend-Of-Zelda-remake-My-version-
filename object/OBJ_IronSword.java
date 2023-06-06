package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_IronSword extends Entity {

    public static final String OBJ_NAME = "Iron Sword";
    public OBJ_IronSword(GamePanel gp) {
        super(gp);
        
        price = 25;
        type = typeSword;
        name = OBJ_NAME;
        down1 = setup("/objects/IronSword", GamePanel.TileSize, GamePanel.TileSize);
        attackValue = 1;
        description ="["+ name +"]"+ "\nA trusty iron sword.";
    }
    
}

package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_MasterSword extends Entity {
    public static final String OBJ_NAME = "Master Sword";
    public OBJ_MasterSword(GamePanel gp) {
        super(gp);
        
        price = -1;
        type = typeSword;
        name = OBJ_NAME;
        down1 = setup("/objects/MasterSword", GamePanel.TileSize, GamePanel.TileSize);
        attackValue = 4;
        description ="["+ name +"]"+ "\nThe legendary master sword.";
        setDialogue();
    }
    public void setDialogue() {
        Dialogues[0][0] = "You pick up the legendary Master Sword!.";
    }
}

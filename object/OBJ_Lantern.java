package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    public static final String OBJ_NAME = "Lantern";
    public OBJ_Lantern(GamePanel gp) {
        super(gp);
         
        type = typeLight;
        name = OBJ_NAME;
        down1 = setup("/objects/lantern",GamePanel.TileSize,GamePanel.TileSize);
        description ="["+ name +"]"+ "\nEmits light.";
        price = 150;
        lightRadius = 400;
    }
    
}

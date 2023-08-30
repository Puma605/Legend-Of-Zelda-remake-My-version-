package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Powder extends Entity {
    GamePanel gp;
    
    public static final String OBJ_NAME = "Merchant's Powder";
    public OBJ_Powder(GamePanel gp) {
            super(gp);
            this.gp = gp;
    
    
            price = 1000;
            type = typeConsumable;
            name = OBJ_NAME;
            value = 3;
            down1 = setup("/objects/MerchantPowder",GamePanel.TileSize,GamePanel.TileSize);
            description ="["+ name +"]"+ "\nMysterious powder.";
            stackable = false;
    
            setDialogue();
        }
    
        public void setDialogue(){
            Dialogues[0][0] = "You smell a little bit of it, and it makes\nyou hyper...";
        }
        public boolean use(Entity entity) {
            startDialogue(this, 0, false);
            return false;
        }
        
    }

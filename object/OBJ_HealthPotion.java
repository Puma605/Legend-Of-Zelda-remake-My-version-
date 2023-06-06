package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HealthPotion extends Entity{
    GamePanel gp;

    public static final String OBJ_NAME = "Health Potion";
    
    public OBJ_HealthPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;


        price = 10;
        type = typeConsumable;
        name = OBJ_NAME;
        value = 3;
        down1 = setup("/objects/HealthPotion",GamePanel.TileSize,GamePanel.TileSize);
        description ="["+ name +"]"+ "\nRecovers " + value + " life.";
        stackable = true;

        setDialogue();
    }

    public void setDialogue(){
        Dialogues[0][0] = "You drank a " + name + "!\n Your life has been recovered by " + value;
    }
    public boolean use(Entity entity) {
        startDialogue(this, 0);
        entity.life += value;
        if (entity.life > entity.maxLife) 
            entity.life = entity.maxLife;
        
        gp.playSoundEffect(2);
        return true;
    }
    
}

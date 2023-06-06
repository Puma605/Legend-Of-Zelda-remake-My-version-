package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public static final String OBJ_NAME = "Key";
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = typeConsumable;
        price = 75;
        name = OBJ_NAME;
        down1 = setup("/objects/key",GamePanel.TileSize,GamePanel.TileSize);
        description ="["+ name +"]"+ "\nProbably opens a door.";
        stackable = true;

        setDialogue();
    }


    public void setDialogue(){
       Dialogues[0][0] = "You used the key!";
       Dialogues[1][0] = "There is nothing to unlock.";
    }
    @Override
    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, OBJ_Door.OBJ_NAME);

        if (objIndex != 999) {
            startDialogue(this, 0);
            gp.playSoundEffect(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }else{
            startDialogue(this, 1);
            return false;
        }
    }
    
}

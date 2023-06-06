package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_IronDoor extends Entity {
  public static final String OBJ_NAME = "Iron Door";
    GamePanel gp;
    public OBJ_IronDoor(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = typeObstacle;
        name = OBJ_NAME;
        down1 = setup("/objects/IronDoor",GamePanel.TileSize,GamePanel.TileSize);
        collision = true;

        solidArea.setBounds(0, 16, 48, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue(){
      Dialogues[0][0] = "It won't budge...";
    }
    @Override
    public void interact() {
      startDialogue(this, 0);  
    }

}

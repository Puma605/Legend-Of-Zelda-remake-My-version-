package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
  public static final String OBJ_NAME = "Door";
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = typeObstacle;
        name = OBJ_NAME;
        down1 = setup("/objects/Door01",GamePanel.TileSize,GamePanel.TileSize);
        collision = true;

        solidArea.setBounds(0, 16, 48, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue(){
      Dialogues[0][0] = "You need a key to open this!";
    }
    @Override
    public void interact() {
      startDialogue(this, 0);  
    }

}

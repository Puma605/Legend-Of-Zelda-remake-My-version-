package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
  public static final String OBJ_NAME = "Chest";
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        super(gp);
        this.gp = gp;
        type = typeObstacle;
        name = OBJ_NAME;
        image = setup("/objects/Chest01Closed",GamePanel.TileSize,GamePanel.TileSize);
        image2 = setup("/objects/Chest01Unlocked",GamePanel.TileSize,GamePanel.TileSize);
        down1 = image;
        collision = true;

        solidArea.setBounds(4, 16, 40, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setDialogue(){

    }
    @Override
    public void interact() {
      if(!opened){
        gp.playSoundEffect(3);
        StringBuilder sb = new StringBuilder();
        sb.append("You found a " + Loot.name + "!");
        
         if (!gp.player.canObtainItem(Loot)) 
            sb.append("\n But you cannot carry anymore items!");
        else
            opened = true;
        
        Dialogues[0][0] = sb.toString();
        startDialogue(this, 0);
      }else{
        Dialogues[1][0] = "Its empty.";
        startDialogue(this, 1);
      }
      down1 = image2;
    }

}

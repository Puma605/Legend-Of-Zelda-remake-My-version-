package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_RockPodium extends Entity {
    public static final String OBJ_NAME = "Rock Podium";
      GamePanel gp;
      public OBJ_RockPodium(GamePanel gp){
          super(gp);
          this.gp = gp;
          type = typeObstacle;
          name = OBJ_NAME;
          down1 = setup("/objects/RockPodium",GamePanel.TileSize,GamePanel.TileSize);
          collision = true;
  
          solidArea.setBounds(0, 16, 48, 32);
          solidAreaDefaultX = solidArea.x;
          solidAreaDefaultY = solidArea.y;
  
          setDialogue();
      }
  
      public void setDialogue(){
        Dialogues[0][0] = "The placement of all the rocks have been reset.";
      }
      @Override
      public void interact() {
        startDialogue(this, 0,false);  
        gp.aPlacer.setNpc();
      }
  
  }

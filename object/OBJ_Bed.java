package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bed extends Entity {
   GamePanel gp;
 public static final String OBJ_NAME = "Bed";
    public OBJ_Bed(GamePanel gp) {
        super(gp);
        this.gp = gp;

        solidArea.setBounds(0,0,48,48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        type = typeObstacle;
        name = OBJ_NAME;
        down1 = setup("/objects/BedBottom01",GamePanel.TileSize,GamePanel.TileSize);
        collision = true;
    }
    @Override
    public void interact() {
      gp.gameState = GamePanel.sleepState;
      gp.playSoundEffect(12);
    }

    
}

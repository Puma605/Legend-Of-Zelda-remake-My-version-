package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_FireBall extends Projectile {
    GamePanel gp;
    public static final String OBJ_NAME = "Fire Ball";
    public OBJ_FireBall(GamePanel gp) {
      super(gp);
      this.gp = gp;
      name = OBJ_NAME;

      speed = 5;
      maxLife = 80;
      life = maxLife;
      attack = 2;
      useCost = 1;
      alive = false;
      getImage();

    }
    public void getImage() {
        up1 = setup("/projectiles/FBUp1", GamePanel.TileSize, GamePanel.TileSize);
        up2 = setup("/projectiles/FBUp2", GamePanel.TileSize, GamePanel.TileSize);
        down1 = setup("/projectiles/FBDown1", GamePanel.TileSize, GamePanel.TileSize);
        down2 = setup("/projectiles/FBDown2", GamePanel.TileSize, GamePanel.TileSize);
        left1 = setup("/projectiles/FBLeft1", GamePanel.TileSize, GamePanel.TileSize);
        left2 = setup("/projectiles/FBLeft2", GamePanel.TileSize, GamePanel.TileSize);
        right1 = setup("/projectiles/FBRight1", GamePanel.TileSize, GamePanel.TileSize);
        right2 = setup("/projectiles/FBRight2", GamePanel.TileSize, GamePanel.TileSize);
    }
    
}

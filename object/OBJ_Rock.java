package object;

import entity.Projectile;
import main.GamePanel;
import java.awt.Color;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public static final String OBJ_NAME = "rock";
      public OBJ_Rock(GamePanel gp) {
      super(gp);
      this.gp = gp;

      name = OBJ_NAME;
      speed = 3;
      maxLife = 30;
      
      life = maxLife;
      attack = 2;
      useCost = 1;
      alive = false;

      getImage();
    }
    

    
    public void getImage() {
        up1 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        up2 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        down1 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        down2 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        left1 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        left2 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        right1 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
        right2 = setup("/projectiles/RockProjectile", GamePanel.TileSize, GamePanel.TileSize);
    }

    public Color getParticleColor(){
        return new Color(40,50,0);
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }


}

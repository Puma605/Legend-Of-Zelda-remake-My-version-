package object;


import entity.Projectile;
import main.GamePanel;

public class OBJ_BombExplosion extends Projectile {

    GamePanel gp;
    public OBJ_BombExplosion(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
      speed = 0;
      maxLife = 10;
      life = maxLife;
      name = "Explosion";
      attack = 10;
      alive = false;
      solidArea.setBounds(0,0,48,48);
      solidAreaDefaultX = solidArea.x;
      solidAreaDefaultY = solidArea.y;
      getImage();
    }

    public void getImage() {
    up1 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    up2 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    down1 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    down2 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    left1 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    left2 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    right1 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    right2 = setup("/projectiles/ExplosionArea", GamePanel.TileSize, GamePanel.TileSize);
    }
    
}

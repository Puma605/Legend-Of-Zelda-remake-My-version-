package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Bomb extends Projectile {
    Projectile[] explosionArea = new Projectile[9];
    GamePanel gp;
    public static final String OBJ_NAME = "Bomb";
    public OBJ_Bomb(GamePanel gp) {
      
      super(gp);
      this.gp = gp;
      name = OBJ_NAME;

      type = typeConsumable;
      price = 5;
      speed = 5;
      maxLife = 30;
      life = maxLife;
      dying = false;
      attack = 0;
      useCost = 1;
      alive = false;
      explosive = true;
      description ="["+ name +"]"+ "\nA deadly explosive.";
      for (int i = 0; i < explosionArea.length; i++) 
      explosionArea[i] = new OBJ_BombExplosion(gp);
      stackable = true;
      getImage();

    }
    
    public void getImage() {
        up1 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        up2 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        down1 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        down2 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        left1 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        left2 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        right1 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
        right2 = setup("/projectiles/Bomb1", GamePanel.TileSize, GamePanel.TileSize);
     dying1 = setup("/projectiles/Bomb2", GamePanel.TileSize, GamePanel.TileSize);
     dying2 = setup("/projectiles/Bomb3", GamePanel.TileSize, GamePanel.TileSize);
     dying3 = setup("/projectiles/BombExplosion", GamePanel.TileSize, GamePanel.TileSize);
    }
    public void setAction() {
    if (!alive && !dying) {


        explosionArea[1].set(worldX-48, worldY,"up",true,this);
        gp.projectile.add(explosionArea[1]);

        explosionArea[2].set(worldX-48, worldY+48,"up",true,this);
        gp.projectile.add(explosionArea[2]);

        explosionArea[3].set(worldX-48, worldY-48,"up",true,this);
        gp.projectile.add(explosionArea[3]);
        
        explosionArea[4].set(worldX+48, worldY,"up",true,this);
        gp.projectile.add(explosionArea[4]);

        explosionArea[5].set(worldX+48, worldY+48,"up",true,this);
        gp.projectile.add(explosionArea[5]);

        explosionArea[6].set(worldX+48, worldY-48,"up",true,this);
        gp.projectile.add(explosionArea[6]);

        explosionArea[7].set(worldX, worldY+48,"up",true,this);
        gp.projectile.add(explosionArea[7]);

        explosionArea[8].set(worldX, worldY-48,"up",true,this);
        gp.projectile.add(explosionArea[8]);
        
    }
  }

  @Override
  public boolean use(Entity entity){
    Dialogues[0][0] = "You put "+ amount + " bombs in the bomb pouch!";
    startDialogue(this, 0,false);
    entity.ammo += amount;
    gp.playSoundEffect(1);
    return true;
  }
}

package entity;

import main.GamePanel;

public class Projectile extends Entity {

    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
        
    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;

    }
    public void update() {

        if (user == gp.player) {
            collisionOn = false;
            gp.cChecker.checkProjectileCollisionTile(this);

           int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
           if (iTileIndex != 999 && gp.iTile[gp.currentMap][iTileIndex].destructible && gp.iTile[gp.currentMap][iTileIndex].isCorrectItem(this)) {
            gp.iTile[gp.currentMap][iTileIndex] = gp.iTile[gp.currentMap][iTileIndex].getDestroyedForm();
            alive = false;
        }

           int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
           if (monsterIndex!= 999) {
            if (explosive) {
                dying = true;
            }else{
             gp.player.damageMonster(monsterIndex, attack);
             alive = false;
            }
           }

           if (collisionOn &&explosive){
            dying = true;
           }else if (collisionOn) {
            alive = false;
           }
           


        }else if (user.name == "Bomb") {

           int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
           if (iTileIndex != 999 && gp.iTile[gp.currentMap][iTileIndex].destructible && gp.iTile[gp.currentMap][iTileIndex].isCorrectItem(this)) {
            generateParticle(gp.iTile[gp.currentMap][iTileIndex], gp.iTile[gp.currentMap][iTileIndex]);
            gp.iTile[gp.currentMap][iTileIndex].checkDrop();
            gp.iTile[gp.currentMap][iTileIndex] = gp.iTile[gp.currentMap][iTileIndex].getDestroyedForm();
            alive = false;
        }
           int objIndex = gp.cChecker.checkObject(this, false);
           if (objIndex != 999) {
            alive = false;
           }
           int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
           if (monsterIndex!= 999) {
             gp.player.damageMonster(monsterIndex, attack);
             alive = false;
            }

            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if (!gp.player.invincible && contactPlayer) {
                damagePlayer(attack);
                alive = false;
            }
            




           }else if (user!= gp.player||user.name != "Bomb") {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if (!gp.player.invincible && contactPlayer) {
                generateParticle(user.projectile, gp.player);
                damagePlayer(attack);
                alive = false;
            }
        }



        switch (direction) {
            case "up":worldY-=speed;break;
            case "down":worldY+=speed;break;
            case "left":worldX-=speed;break;
            case "right":worldX+=speed;break;
        }

        life--;
        if (life <= 0 && explosive)
        dying = true;
       else if (life <= 0) 
        alive = false;
        
        
        

        spriteCounter++;
        if (spriteCounter>12) {
            if (spriteNum == 1) 
                spriteNum = 2;

            else if (spriteNum == 2) 
                spriteNum = 1;
            
            spriteCounter = 0;
        }
    }
    public boolean haveResource(Entity user) {
        return user.ammo >= useCost;
    }
    public void subtractResource(Entity entity) {
        entity.ammo -= useCost;
    }

    
    
}

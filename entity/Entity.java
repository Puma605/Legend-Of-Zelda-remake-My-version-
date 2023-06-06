package entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.Color;
public class Entity {
    GamePanel gp;
    public int worldX,worldY;
    public int speed;

    //sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
    attackLeft2, attackRight1, attackRight2, guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage dying1,dying2,dying3;
    public int spriteCounter = 0;
    public int spriteNum =1;
    public BufferedImage image, image1, image2;
    public Entity linkedEntity;

    //configs
    public String direction = "down";
    public boolean collisionOn = false;
    public boolean collision = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean explosive = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public boolean onPath = false;
    public boolean opened;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean knockedBack = false;
    public boolean enraged = false;
    public boolean sleep = false;
    public boolean tempENTITY = false;
    public boolean drawing = true;
    

    //counters
    public int knockBackCounter;
    public String knockBackDirection;
    int dyingCounter;
    public int actionLockCounter = 0;
    public int shotAvailableCounter = 0;
    public int guardCounter;
    int offBalanceCounter;
    public boolean offBalance;


    // solid area
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultY,solidAreaDefaultX;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    
    //invible frames
    public boolean invincible = false;
    public int invincibleFrames = 0;

    //dialogues
    public String Dialogues[][] = new String[20][20];
    public int dialogueIndex = 0;
    public int dialogueSet = 0;

    



    //entity types
    public int type;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeShield = 4;
    public final int typeConsumable = 5;
    public final int typePickUpOnly = 6;
    public final int typeObstacle = 7;
    public final int typeLight = 8;


    //character status

    public int maxLife;
    public int life;
    public int level;
    public int maxMana;
    public int mana;
    public int ammo;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1Duration;
    public int motion2Duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;

    //item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public String name;
    public int value;
    public int price;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;
    public Entity Loot;




    public Entity(GamePanel gp){
     this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX +solidArea.x)/GamePanel.TileSize;
    }
    public int getRow(){
        return (worldY +solidArea.y)/GamePanel.TileSize;
    }
    public int getXDistance(Entity Target){
        return Math.abs(getCenterX() - Target.worldX);
    }
    public int getYDistance(Entity Target){
        return Math.abs(getCenterY() - Target.worldY);
    }
    public int getCenterX(){
       return worldX+ up1.getWidth()/2;
    }
    public int getCenterY(){
        return worldY+ up1.getHeight()/2;
    }
    public int getTileDistance(Entity target){
         return (getXDistance(target) + getYDistance(target))/GamePanel.TileSize;
    }
    public int getGoalCol(Entity target){
        return (target.worldX + target.solidArea.x)/GamePanel.TileSize;
     }
    public int getGoalRow(Entity target){
       return (target.worldY + target.solidArea.y)/GamePanel.TileSize;
    }
    public void setAction() {}
    public void move(String direction){}
    public void damageReaction(){}
    public void talk(){
        if (name == "Ruebs Merchant") 
        dialogueIndex = 0;   
    }
    public void facePlayer() {
    
        switch (gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;

        }
    }
    public void startDialogue(Entity entity, int setNum){
        gp.UI.npc = entity;
        dialogueSet = setNum;
        gp.gameState = GamePanel.dialogueState;
    }
    public void interact(){}
    public boolean use(Entity entity) {
        return false;
    }
    public void checkDrop(){}
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i]== null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void setItems() {
    }
    public void setLoot(Entity loot){
        this.Loot = loot;
      }
    public Color getParticleColor(){
        return new Color(65,50,30);
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
    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife,-2,-1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife,2,-1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife,-2,1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife,2,1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == typeMonster && contactPlayer && !gp.player.invincible) 
           damagePlayer(attack);
    }
    public void update() {
        if (!sleep) {
        if (attacking) 
            attacking();
        
        else{
        setAction();
        checkCollision();


        if (!collisionOn) {
            switch (direction) {
                case "up": worldY-=speed; break;
                case "down": worldY+=speed; break;
                case "left": worldX-=speed; break;
                case "right": worldX+=speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter>10) {
            if (spriteNum==1) {
                spriteNum=2;
            }else if (spriteNum==2) {
                spriteNum =1;
            }
            spriteCounter = 0;
        } 
    }
}


        if (invincible) {
            invincibleFrames++;
            if (invincibleFrames> 40) {
                invincible = false;
                invincibleFrames= 0;
            }
        }

        if (shotAvailableCounter<30) 
        shotAvailableCounter++;
    
        if(offBalance){
        offBalanceCounter++;
        if (offBalanceCounter > 60) {
            offBalance = false;
            offBalanceCounter = 0;
        }
        }
    }
    public void checkIfStopChasing(Entity target, int distance){
        if (getTileDistance(target) > distance) 
           onPath = false; 
    }
    public void checkIfStartChasing(Entity target, int distance){
        if (getTileDistance(target) < distance){
        int random = new Random().nextInt(100)+1;
           if (random > 50) 
            onPath = true;
        }
    }
    public void checkIfShootsProjectile(int rate, int shotInterval){
        int i = new Random().nextInt(rate)+1;
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
        projectile.set(worldX, worldY,direction,true,this);
        gp.projectile.add(projectile);
        shotAvailableCounter = 0;
        }

    }
    public void checkIfAttacking(int rate, int straight, int horizontal){
      boolean targetInRange = false;
      int xDis = getXDistance(gp.player);
      int yDis = getYDistance(gp.player);

      switch (direction) {
        case "up":
            if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) 
                targetInRange = true;
            break;
        case "down":
            if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) 
                targetInRange = true;
            break;
        case "left":
            if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) 
                targetInRange = true;
            break;
        case "right":
            if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) 
                targetInRange = true;
            break;
      }

      if (targetInRange) {
        int i = new Random().nextInt(rate);
        if (i == 0) {
            attacking = true;
            spriteNum = 1;
            spriteCounter = 0;
            shotAvailableCounter = 0;
        }
      }
    }
    public void getRandomDirection(){
        actionLockCounter++;
        if (actionLockCounter==120) {
            Random random = new Random();
        int i = random.nextInt(100)+1;
        if (i<=25) 
            direction= "up";
        else if (i>25&&i<=50) 
            direction = "down";
        else if (i>50 && i<=75) 
            direction = "left";
        else if (i>75&&i<=100){
            direction = "right";
        }
        actionLockCounter = 0;
         }
    }
    public void moveTowardPlayer(int Interval){
 
        actionLockCounter++;
        if (actionLockCounter >= Interval) {
            if (getXDistance(gp.player) > getYDistance(gp.player)){
                 if (gp.player.getCenterX() < getCenterX()) 
                    direction = "left";
                 else
                    direction = "right";
                 
            }else if(getXDistance(gp.player) < getYDistance(gp.player)) {
                if (gp.player.getCenterY() < getCenterY()) 
                    direction = "up";
                 else
                    direction = "down";
            }
            actionLockCounter = 0;
        }
    }
    public String getOppositeDirection(String Direction){
        String opposite = "";
        switch (Direction) {
            case "up":opposite = "down";break;
            case "down":opposite = "up";break;
            case "left":opposite = "right";break;
            case "right":opposite = "left";break;
        }
        return opposite;
    }
    public void attacking() {
        spriteCounter++;

        if (spriteCounter<=motion1Duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1Duration && spriteCounter <= motion2Duration) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":worldY -= attackArea.height;break;
                case "down":worldY += attackArea.height;break;
                case "left":worldX -= attackArea.width;break;
                case "right":worldX += attackArea.width;break;
            }
            //attack area becomes solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == typeMonster) {
                if (gp.cChecker.checkPlayer(this)) 
                    damagePlayer(attack);
                
            }else{//player

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            gp.player.damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            gp.player.damageInteractiveTile(iTileIndex);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;




        }
        if (spriteCounter > motion2Duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
        
        int damage = attack - gp.player.defense;

        String canGuardDirection = getOppositeDirection(direction);
        if (gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {
            damage = 0;

            //parry
            if (gp.player.guardCounter < 10) {
                gp.playSoundEffect(14);
                offBalance = true;
                spriteCounter =- 60;
            }else{
            gp.playSoundEffect(13);
            }

            //KB
            gp.player.knockedBack = true;
            gp.player.knockBackDirection = direction;
            gp.player.knockBackCounter = 0;
        }else{
        gp.playSoundEffect(6);
        if (damage < 0) 
            damage = 1;
        }
        if (damage != 0)
            gp.player.transparent = true;
        
        
        gp.player.life -= damage; 
        gp.player.invincible = true; 
        }
        
    }
    public void draw(Graphics2D g2){
        int screenX = worldX-gp.player.worldX+gp.player.screenX;
        int screenY = worldY-gp.player.worldY+gp.player.screenY;
        if (worldX + GamePanel.TileSize*5 > gp.player.worldX - gp.player.screenX&&
          worldX - GamePanel.TileSize < gp.player.worldX + gp.player.screenX&&
          worldY + GamePanel.TileSize*5 > gp.player.worldY - gp.player.screenY&&
          worldY - GamePanel.TileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;
     

        switch (direction) {
            case "up":
            if (attacking) {
            tempScreenY = screenY - up1.getHeight();
            if (spriteNum == 1) image = attackUp1;
            if (spriteNum==2) image = attackUp2; 
            }else{
            if (spriteNum == 1) image = up1;
            if (spriteNum==2) image = up2;
            }
           break;

            case "down":
            if (attacking) {
            if (spriteNum == 1) image = attackDown1;
            if (spriteNum==2) image = attackDown2; 
            }else{
            if (spriteNum == 1) image = down1;
            if (spriteNum==2) image = down2;
            }
               
                break;
            case "left":
            if (attacking) {
            tempScreenX = screenX -left1.getWidth();
                if (spriteNum == 1) image = attackLeft1;
                if (spriteNum==2) image = attackLeft2; 
            }else{
            if (spriteNum == 1) image = left1;
            if (spriteNum==2) image = left2;
            }
                break;
            case "right":
            if (attacking) {
                if (spriteNum == 1) image = attackRight1;            
                if (spriteNum==2) image = attackRight2; 
            }else{
            if (spriteNum == 1) image = right1;
            if (spriteNum==2) image = right2;
            }
                
                break;
            default:
                break;
        }
            
            if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            }

            if (dying) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
            dyingAnimation(g2,screenX,screenY);
            if (!alive) {
                dyingCounter = 0;
                dying = false;
                if (explosive){
                    setAction();
                    gp.playSoundEffect(10);
                }
                
            }
            
        }else{   
          g2.drawImage(image, tempScreenX, tempScreenY,null);
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
        }
    }
    
    
    public void dyingAnimation(Graphics2D g2, int screenX, int screenY) {
        
        dyingCounter++;
        int i  = 7;
        if (dyingCounter <= i*3)
            image = dying1;
       
        
        if (dyingCounter > i*3 && dyingCounter <= i*5)
            image = dying2;
        
        
        if (dyingCounter > i*5 && dyingCounter <= i*7) 
            image = dying3;
        
        if (dyingCounter > i*7) {
            alive = false;
        }
        g2.drawImage(image, screenX, screenY,null);
        
        
    }
    public BufferedImage setup(String packagePath, int Width, int Height){
        
    UtilityTool uTool = new UtilityTool();
        BufferedImage  scaledImage = null;
        try {
         scaledImage = ImageIO.read(getClass().getResourceAsStream(packagePath+".png"));
        scaledImage = uTool.scaleImage(scaledImage, Width, Height);
     
           } catch (IOException e) {
           e.printStackTrace();

     }
         return scaledImage;
        }


    public void searchPath(int goalCol, int goalRow){
      int startCol  = (worldX+solidArea.x)/GamePanel.TileSize;
      int startRow  = (worldY+solidArea.y)/GamePanel.TileSize;

      gp.PF.setNodes(startCol, startRow, goalCol, goalRow, this);
      if (gp.PF.search()) {
        //next worldx & worldy
        int nextX = gp.PF.pathList.get(0).col * GamePanel.TileSize;
        int nextY = gp.PF.pathList.get(0).row * GamePanel.TileSize;

        int enLeftX = worldX + solidArea.x;
        int enRightX = worldX + solidArea.x + solidArea.width;
        int enTopY = worldY + solidArea.y;
        int enBottomY = worldY + solidArea.y + solidArea.height;

        if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + GamePanel.TileSize) 
            direction = "up";
        else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + GamePanel.TileSize) 
            direction = "down";
        else if (enTopY >= nextY && enBottomY < nextY+ GamePanel.TileSize) {
            //entity can go either left or right
            if (enLeftX > nextX) 
                direction = "left";
            
            if (enLeftX < nextX) 
                direction = "right";
            
        }else if (enTopY > nextY && enLeftX > nextX) {
            //either up or left
            direction = "up";
            checkCollision();
            if (collisionOn) 
                direction = "left";
            
        }else if (enTopY > nextY && enLeftX < nextX) {
            //either up or right
            direction = "up";
            checkCollision();
            if (collisionOn) 
                direction = "right";
    
        }else if (enTopY < nextY && enLeftX > nextX) {
            //either down or left
            direction = "down";
            checkCollision();
            if (collisionOn) 
                direction = "left";

        }else if (enTopY < nextY && enLeftX < nextX) {
            //either down or right
            direction = "down";
            checkCollision();
            if (collisionOn) 
                direction = "right";
        }

      }
      if (onPath) {
      //int nextCol = gp.PF.pathList.get(0).col;
      //int nextRow = gp.PF.pathList.get(0).row;
      int entityCol = (worldX + solidArea.x)/GamePanel.TileSize;
      int entityRow = (worldY + solidArea.y)/GamePanel.TileSize;
      if (entityCol == goalCol && entityRow == goalRow) 
            onPath = false; 
      }
      
    }

    public int getDetected(Entity user, Entity target[][], String targetName){
         int index = 999;
        //check surronding obj
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up": nextWorldY = user.getTopY()-gp.player.speed-20;break;
            case "down": nextWorldY = user.getBottomY()+gp.player.speed+20;break;
            case "left": nextWorldX = user.getLeftX()-gp.player.speed-20;break;
            case "right": nextWorldX = user.getRightX()+gp.player.speed+20;break;
        }
        int col  = nextWorldX/GamePanel.TileSize;
        int row = nextWorldY/GamePanel.TileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i]!=null) {
                if (target[gp.currentMap][i].getCol() == col &&
                target[gp.currentMap][i].getRow() == row &&
                target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }
}

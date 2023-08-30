package entity;


import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Bomb;
import object.OBJ_IronSword;
import object.OBJ_WoodShield;
import quests.OM_QuestOne;
import quests.OM_QuestTwo;
import quests.Quest;
import quests.Ruebs_QuestOne;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;



public class Player extends Entity {
    KeyHandler KeyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public final int inventorySize = 20;
    public boolean lightUpdated = false;
    public boolean canPush = true;
    
    int imgType = 0;
    final int walkIMG = 1;
    final int pushIMG = 2;

    int previousNPCX = -1;
    int previousNPCY = -1;

    public Quest currentQuest = gp.QHandler.quests[0];


    public Player(GamePanel gp, KeyHandler KeyH){
     super(gp);

     this.KeyH = KeyH;

     screenX = GamePanel.screenWidth/2-(GamePanel.TileSize/2);
     screenY = GamePanel.screenHeight/2-(GamePanel.TileSize/2);

     solidArea = new Rectangle(8,16,25,25);
     solidAreaDefaultY =solidArea.y;
     solidAreaDefaultX =solidArea.x;

     attackArea.setSize(36, 36);

     motion1Duration = 5;
     motion2Duration = 25;

     setDefaultValues();
     getImage();
     getAttackImage();
     getGuardImage();
     setItems();
     setDialogue();
    }

    public void setDefaultValues() {
        worldX = GamePanel.TileSize*23;
        worldY = GamePanel.TileSize*23;
        speed = 4;

        maxLife = 6;
        life = maxLife;

        ammo = 0;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 10;
        coin = 0;
        currentWeapon = new OBJ_IronSword(gp);
        currentShield = new OBJ_WoodShield(gp);
        currentLight = null;
        projectile = new OBJ_Bomb(gp);

    }
    public void setDefaultPositions() {
        gp.currentMap = 0;//9 9 for map 1
        worldX= GamePanel.TileSize*23;
        worldY = GamePanel.TileSize*21; 
        
    }
    public void setDialogue(){
        Dialogues[0][0] = "You are level " + level + " now!\n You feel stronger";
    }
    public void restoreStatus() {
        life = maxLife;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockedBack = false;
        lightUpdated = true;
    }
    public void setItems(){
      inventory.clear();
      currentWeapon = new OBJ_IronSword(gp);
      currentShield = new OBJ_WoodShield(gp);
      attack = getAttack();
      defense = getDefense();
      inventory.add(currentWeapon);
      inventory.add(currentShield);
    }
    public int searchInventory(String name){
         int index = 999;
         for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name == name) 
                return index = i;
         }
         return index; 
    }

    public int getAttack(){
      return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getImage() {
        imgType = walkIMG;
        up1 = setup("/player/Up1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/player/Up2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/player/Down1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/player/Down2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/player/Left1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/player/Left2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/player/Right1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/player/Right2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void getSleepingImage() {
        up1 = null;
        up2 = null;
        down1 = null;
        down2 = null;
        left1 = null;
        left2 = null;
        right1 = null;
        right2 = null;
    }
    public void getAttackImage() {
        attackUp1 = setup("/player/AttackUp1",GamePanel.TileSize,GamePanel.TileSize*2);
        attackUp2 = setup("/player/AttackUp2",GamePanel.TileSize,GamePanel.TileSize*2);
        attackDown1 = setup("/player/AttackDown1",GamePanel.TileSize,GamePanel.TileSize*2);
        attackDown2 = setup("/player/AttackDown2",GamePanel.TileSize,GamePanel.TileSize*2);
        attackLeft1 = setup("/player/AttackLeft1",GamePanel.TileSize*2,GamePanel.TileSize);
        attackLeft2 = setup("/player/AttackLeft2",GamePanel.TileSize*2,GamePanel.TileSize);
        attackRight1 = setup("/player/AttackRight1",GamePanel.TileSize*2,GamePanel.TileSize);
        attackRight2 = setup("/player/AttackRight2",GamePanel.TileSize*2,GamePanel.TileSize);
    }
    public void getGuardImage(){
        guardUp = setup("/player/UpGuard", GamePanel.TileSize, GamePanel.TileSize);
        guardDown = setup("/player/DownGuard", GamePanel.TileSize, GamePanel.TileSize);
        guardLeft = setup("/player/LeftGuard", GamePanel.TileSize, GamePanel.TileSize);
        guardRight = setup("/player/RightGuard", GamePanel.TileSize, GamePanel.TileSize);
    }
    public void getPushImage() {
        imgType = pushIMG;
        up1 = setup("/player/UpPush01",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/player/UpPush02",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/player/DownPush01",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/player/DownPush02",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/player/LeftPush01",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/player/LeftPush02",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/player/RightPush01",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/player/RightPush02",GamePanel.TileSize,GamePanel.TileSize);
    }
    public int getCurrentWeaponSlot() {
        for (int i = 0; i < inventory.size(); i++) 
            if (inventory.get(i) == currentWeapon) 
                return i;
        return -1;
    }
    public int getCurrentShieldSlot() {
        for (int i = 0; i < inventory.size(); i++) 
            if (inventory.get(i) == currentShield) 
                return i;
        return -1;
    }
    public void update(){
        if (currentQuest != null){
        currentQuest.update();

        if (currentQuest.checkIfQuestComplete()) 
            currentQuest = null;
        }
        if(!gp.KeyH.spacePressed)
        guarding = false;
        if (knockedBack) {
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);
            
     
     
            if (collisionOn) {
              knockBackCounter = 0;
              knockedBack = false;
            }else if (!collisionOn) {
             
               switch (knockBackDirection) {
                 case "up":worldY-=2;break;
                 case "down":worldY+=2;break;
                 case "left":worldX-=2;break;
                 case "right":worldX+=2;break;
               }
            }
     
            knockBackCounter++;
            if (knockBackCounter == 10) {
             knockBackCounter = 0;
             knockedBack = false;
            } 
        }else if (attacking) {
            attacking();
        }else if(KeyH.spacePressed){
          guarding = true;
          guardCounter++;
        }else if (KeyH.upPressed||KeyH.downPressed||
          KeyH.leftPressed||KeyH.rightPressed||KeyH.enterPressed) {
        if (KeyH.upPressed) {
            direction = "up";
        }
        else if (KeyH.downPressed) {
            direction = "down";
        }
        else if (KeyH.leftPressed) {
            direction = "left";
        }
        else if (KeyH.rightPressed) {
            direction = "right";
        }


        collisionOn = false;
        //check tile collision
        gp.cChecker.checkTile(this);

        //check tobj collision
       int objIndex =  gp.cChecker.checkObject(this, true);
       PickUpObj(objIndex);

       //check npc collision
       int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
       interactNPC(npcIndex);

       int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
       contactMonster(monsterIndex);

       //check itile COLLISION
       gp.cChecker.checkEntity(this, gp.iTile);

       //check events
       gp.eventHandler.checkEvent();

        if (!collisionOn&& !KeyH.enterPressed) {
            switch (direction) {
                case "up": worldY-=speed; break;
                case "down": worldY+=speed; break;
                case "left": worldX-=speed; break;
                case "right": worldX+=speed; break;

            }
        }

        if (KeyH.enterPressed && !attackCanceled) {
            gp.playSoundEffect(7);
            attacking = true;
            spriteCounter = 0;
        }
        attackCanceled = false;

        gp.KeyH.enterPressed = false;
        guarding = false;
        guardCounter = 0;


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


        if (gp.KeyH.shotKeyPressed && !projectile.dying &&
         !projectile.alive && shotAvailableCounter==30 && projectile.haveResource(this) ) {
            //set basic settings
            projectile.set(worldX, worldY, direction, true, this);

            //subtract cost
            projectile.subtractResource(this);
            //add to the arraylist
            gp.projectile.add(projectile);
            shotAvailableCounter = 0;
            gp.playSoundEffect(9);
        }

        if (invincible) {
            invincibleFrames++;
            if (invincibleFrames> 60) {
                invincible = false;
                transparent = false;
                invincibleFrames= 0;
            }
        }

        if (shotAvailableCounter< 30) 
            shotAvailableCounter ++;
        
        if (life > maxLife) 
            life = maxLife;

        if (life <= 0){
            transparent = false;
            gp.gameState = GamePanel.gameOverState;
            gp.UI.commandNumber = -1;
            gp.stopMusic();
            gp.playMusic(11);
        }
        
        
        
    }
    public void PickUpObj(int index) {
        if (index!=999) {
        //pick up only
        if (gp.obj[gp.currentMap][index].type == typePickUpOnly) {
            gp.obj[gp.currentMap][index].use(this);
            gp.obj[gp.currentMap][index] = null;
        }else if(gp.obj[gp.currentMap][index].type == typeObstacle){
          if (gp.KeyH.enterPressed) {
            attackCanceled = true;
            gp.obj[gp.currentMap][index].interact();
          }


        }else{
           //inventory
            if (canObtainItem(gp.obj[gp.currentMap][index])) {
                gp.playSoundEffect(1);
            }
            gp.obj[gp.currentMap][index] = null; 
        }

        
        }
    }
    public void interactNPC(int index) {
        if (previousNPCX != -1 || previousNPCY != -1) {
            
        
        int xDistance = Math.abs(gp.player.worldX - previousNPCX );
        int yDistance = Math.abs(gp.player.worldY - previousNPCY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > GamePanel.TileSize) //check if more than a tile away
         canPush = true;
         else
         canPush = false;
        }
        if (index!=999) {
            if (gp.KeyH.enterPressed) {
            attackCanceled = true;
            gp.npc[gp.currentMap][index].talk();
        }
        gp.npc[gp.currentMap][index].move(direction);
        previousNPCX = gp.npc[gp.currentMap][index].worldX;
        previousNPCY = gp.npc[gp.currentMap][index].worldY;
    }
    }

    public void contactMonster(int index) {

        if(index != 999){
            if (!invincible && !gp.monster[gp.currentMap][index].dying) {
            gp.playSoundEffect(6);
            int damage = gp.monster[gp.currentMap][index].attack - defense;
            if (damage <= 0) 
                damage = 1;

               life -= damage; 
               invincible = true;
               transparent = true;
            }
            
        }
    }
    public void damageMonster(int index, int attack){
       if (index != 999) {
        if (!gp.monster[gp.currentMap][index].invincible) {


            gp.playSoundEffect(5);

            if (gp.monster[gp.currentMap][index].offBalance) {
                attack *= 2;
            }
            int damage = attack - gp.monster[gp.currentMap][index].defense;
            if (damage <= 0) 
                damage = 1;
            
            gp.monster[gp.currentMap][index].life -=damage;
            gp.monster[gp.currentMap][index].invincible = true;
            gp.monster[gp.currentMap][index].damageReaction();

            if (gp.monster[gp.currentMap][index].life<= 0 ) {
                gp.monster[gp.currentMap][index].dying = true;
                exp += gp.monster[gp.currentMap][index].exp;

                if (currentQuest!= null){
                if(currentQuest.QuestName == OM_QuestOne.QUEST_NAME &&
                gp.monster[gp.currentMap][index].name == "Green Chuchu") 
                currentQuest.incrementCounter(); 
                if (currentQuest.QuestName == Ruebs_QuestOne.QUEST_NAME &&
                gp.monster[gp.currentMap][index].name == "Moblin") {
                currentQuest.incrementCounter();     
                }
                }   
                
                checkLevelUp();
            }
        }
       }
    }
    public void damageInteractiveTile(int index) {
        if (index != 999 && gp.iTile[gp.currentMap][index].destructible && gp.iTile[gp.currentMap][index].isCorrectItem(this)) {
            generateParticle(gp.iTile[gp.currentMap][index], gp.iTile[gp.currentMap][index]);
            gp.iTile[gp.currentMap][index].playSE();
            gp.iTile[gp.currentMap][index] = gp.iTile[gp.currentMap][index].getDestroyedForm();
            gp.iTile[gp.currentMap][index].checkDrop();  
        }
    }

    public void checkLevelUp() {
        if (exp>=nextLevelExp) {
            if (currentQuest!=null && currentQuest.QuestName == OM_QuestTwo.QUEST_NAME &&
            currentQuest.currentObjective == currentQuest.Objectives[2]) 
                currentQuest.NextObjective();
            
            level++;
            nextLevelExp = nextLevelExp *3;
            maxLife+= 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSoundEffect(8);
            gp.gameState = GamePanel.dialogueState;
            exp = 0;
            setDialogue();
            startDialogue(this, 0,false);

        }
    }
    public void selectItem() {
        int itemIndex = gp.UI.getItemIndexOnSlot(gp.UI.playerSlotCol,gp.UI.playerSlotRow);
        if (itemIndex < inventory.size()) {
            Entity selectedItem  = inventory.get(itemIndex);
            switch (selectedItem.type) {
                case typeSword:
                currentWeapon = selectedItem;
                attack = getAttack();
                break;

                case typeShield:
                currentShield = selectedItem;
                defense = getDefense();
                break;

                case typeConsumable:
                if(selectedItem.use(this)){
                    if (selectedItem.amount > 1 && selectedItem.name != "Bomb") 
                        selectedItem.amount--;
                    else  
                        inventory.remove(itemIndex);
                    
                }
                break;
                case typeLight:
                if (currentLight == selectedItem) 
                    currentLight = null;
                else
                    currentLight = selectedItem;
                

                lightUpdated = true;
                break;
            }
        }
    }
    public int searchItemInInventory(String itemName){
      int itemIndex = 999;

      for (int i = 0; i < inventory.size(); i++) {
        if (inventory.get(i).name == itemName) {
            itemIndex = i;
            break;
        }
      }

      return itemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        Entity newItem = gp.EG.getObject(item.name);
        if (newItem.stackable) {
            int index = searchItemInInventory(item.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }else{//new item, need to check vacancy
               if (inventory.size() != inventorySize) {
                   inventory.add(newItem);
                   canObtain = true;
               }
            }
        }else{//not stackable
            if (inventory.size() != inventorySize) {
                inventory.add(newItem);
                canObtain = true;
            } 
        }
        return canObtain;

    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
     
        if (!canPush && imgType == walkIMG) //fix drawing too many times a sec
           getPushImage();
        else if (canPush && imgType == pushIMG)
           getImage();
        

        switch (direction) {
            case "up":
            if (attacking) {
            tempScreenY = screenY - GamePanel.TileSize;
            if (spriteNum == 1) image = attackUp1;
            if (spriteNum==2) image = attackUp2; 
            }else if(guarding){
            image = guardUp;
            }else{
            if (spriteNum == 1) image = up1;
            if (spriteNum==2) image = up2;
            }
           break;

            case "down":
            if (attacking) {
            if (spriteNum == 1) image = attackDown1;
            if (spriteNum==2) image = attackDown2; 
            }else if(guarding){
            image = guardDown;
            }else{
            if (spriteNum == 1) image = down1;
            if (spriteNum==2) image = down2;
            }
               
                break;
            case "left":
            if (attacking) {
            tempScreenX = screenX - GamePanel.TileSize;
                if (spriteNum == 1) image = attackLeft1;
                if (spriteNum==2) image = attackLeft2; 
            }else if(guarding){
            image = guardLeft;
            }else{
            if (spriteNum == 1) image = left1;
            if (spriteNum==2) image = left2;
            }
                break;
            case "right":
            if (attacking) {
                if (spriteNum == 1) image = attackRight1;            
                if (spriteNum==2) image = attackRight2; 
            }else if(guarding){
            image = guardRight;
            }else{
            if (spriteNum == 1) image = right1;
            if (spriteNum==2) image = right2;
            }    
                break;
        }

        if (transparent) 
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        if (drawing) 
            g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        
    }



    
}

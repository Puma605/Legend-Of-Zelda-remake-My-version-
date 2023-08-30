package monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_Bomb;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;
import object.OBJ_IronDoor;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;
    public static final String MON_NAME = "Skeleton Lord";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = MON_NAME;
        speed = 1;
        maxLife = 35;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50; 
        sleep = true;


        int size = GamePanel.TileSize*5;//if scale changed then this also needs to change
        solidArea.setBounds(48,48,size-48*2,size-48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.setSize(170, 170);
        motion1Duration = 40;
        motion2Duration = 85;
        getImage();
        getAttackImage();
        getDeathImage();
        setDialogue();
    }
    
    public void getImage() {
        int scale = 5;
        if (!enraged) {
        up1 = setup("/monsters/SLUp1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        up2 = setup("/monsters/SLUp2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        down1 = setup("/monsters/SLDown1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        down2 = setup("/monsters/SLDown2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        left1 = setup("/monsters/SLLeft1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        left2 = setup("/monsters/SLLeft2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        right1 = setup("/monsters/SLRight1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        right2 = setup("/monsters/SLRight2",GamePanel.TileSize*scale,GamePanel.TileSize*scale); 
        }else if (enraged) {
        up1 = setup("/monsters/SLPhase2Up1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        up2 = setup("/monsters/SLPhase2Up2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        down1 = setup("/monsters/SLPhase2Down1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        down2 = setup("/monsters/SLPhase2Down2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        left1 = setup("/monsters/SLPhase2Left1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        left2 = setup("/monsters/SLPhase2Left2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        right1 = setup("/monsters/SLPhase2Right1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        right2 = setup("/monsters/SLPhase2Right2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);   
        }
        
    }
    public void getAttackImage() {
        int scale = 5;
        if (!enraged) {
        attackUp1 = setup("/monsters/SLAttackUp1",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackUp2 = setup("/monsters/SLAttackUp2",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackDown1 = setup("/monsters/SLAttackDown1",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackDown2 = setup("/monsters/SLAttackDown2",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackLeft1 = setup("/monsters/SLAttackLeft1",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackLeft2 = setup("/monsters/SLAttackLeft2",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackRight1 = setup("/monsters/SLAttackRight1",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackRight2 = setup("/monsters/SLAttackRight2",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);   
        }else if(enraged){
        attackUp1 = setup("/monsters/SLPhase2AttackUp1",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackUp2 = setup("/monsters/SLPhase2AttackUp2",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackDown1 = setup("/monsters/SLPhase2AttackDown1",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackDown2 = setup("/monsters/SLPhase2AttackDown2",GamePanel.TileSize*scale,GamePanel.TileSize*scale*2);
        attackLeft1 = setup("/monsters/SLPhase2AttackLeft1",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackLeft2 = setup("/monsters/SLPhase2AttackLeft2",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackRight1 = setup("/monsters/SLPhase2AttackRight1",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);
        attackRight2 = setup("/monsters/SLPhase2AttackRight2",GamePanel.TileSize*scale*2,GamePanel.TileSize*scale);   
        }
        
    }
    public void getDeathImage() {
        int scale = 5;
        dying1 = setup("/monsters/SLDeath1",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        dying2 = setup("/monsters/SLDeath2",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
        dying3 = setup("/monsters/SLDeath3",GamePanel.TileSize*scale,GamePanel.TileSize*scale);
    }

    public void setDialogue(){
       Dialogues[0][0] = "Who dares to enter my chamber?";
       Dialogues[0][1] = "I will kill you for disturbing my\nsleep, puny boy.";
       Dialogues[0][2] = "Do not worry, I will make it quick.";

    }
    public void setAction() {
        if (!enraged && life < maxLife/4) {
            enraged = true;
            getImage();
            getAttackImage();
            speed++;
            attack+=4;
        }

        if (getTileDistance(gp.player) < 10) 
           moveTowardPlayer(60);
        else
        getRandomDirection();

       if (!attacking) 
         checkIfAttacking(60, GamePanel.TileSize*7, GamePanel.TileSize*5);
       
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {
        gp.bossBattleOn = false;
        Progress.SLDEAD = true;
        gp.stopMusic();
        gp.playMusic(16);
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i]!= null && gp.obj[gp.currentMap][i].name == OBJ_IronDoor.OBJ_NAME) {
                gp.obj[gp.currentMap][i] = null;
                gp.playSoundEffect(18);
            }
        }
        int i = new Random().nextInt(200)+1;
        if (i < 100) 
            dropItem(new OBJ_GoldCoin(gp));
        if (i >= 100 && i < 150) 
            dropItem(new OBJ_Heart(gp));
        if (i >= 150 && i < 160) 
            dropItem(new OBJ_BlueShield(gp));
        if (i >= 160 && i <=200) {
            dropItem(new OBJ_Bomb(gp));
        }
    }
}

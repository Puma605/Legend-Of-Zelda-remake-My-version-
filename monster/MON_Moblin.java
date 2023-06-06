package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_Bomb;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;

public class MON_Moblin extends Entity {
    GamePanel gp;
    public MON_Moblin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = "Moblin";
        speed = 1;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10; 

        solidArea.setBounds(4,4,40,44);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.setSize(48, 48);
        motion1Duration = 40;
        motion2Duration = 85;
        getImage();
        getAttackImage();
        getDeathImage();
    }
    
    public void getImage() {
        up1 = setup("/monsters/MoblinUp1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/monsters/MoblinUp2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/monsters/MoblinDown1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/monsters/MoblinDown2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/monsters/MoblinLeft1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/monsters/MoblinLeft2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/monsters/MoblinRight1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/monsters/MoblinRight2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void getAttackImage() {
        attackUp1 = setup("/monsters/MoblinAttackUp1",GamePanel.TileSize,GamePanel.TileSize*2);
        attackUp2 = setup("/monsters/MoblinAttackUp2",GamePanel.TileSize,GamePanel.TileSize*2);
        attackDown1 = setup("/monsters/MoblinAttackDown1",GamePanel.TileSize,GamePanel.TileSize*2);
        attackDown2 = setup("/monsters/MoblinAttackDown2",GamePanel.TileSize,GamePanel.TileSize*2);
        attackLeft1 = setup("/monsters/MoblinAttackLeft1",GamePanel.TileSize*2,GamePanel.TileSize);
        attackLeft2 = setup("/monsters/MoblinAttackLeft2",GamePanel.TileSize*2,GamePanel.TileSize);
        attackRight1 = setup("/monsters/MoblinAttackRight1",GamePanel.TileSize*2,GamePanel.TileSize);
        attackRight2 = setup("/monsters/MoblinAttackRight2",GamePanel.TileSize*2,GamePanel.TileSize);
    }
    public void getDeathImage() {
        dying1 = setup("/monsters/MoblinDown1",GamePanel.TileSize,GamePanel.TileSize);
        dying2 = setup("/monsters/MoblinDeath2",GamePanel.TileSize,GamePanel.TileSize);
        dying3 = setup("/monsters/MoblinDeath3",GamePanel.TileSize,GamePanel.TileSize);
    }

    public void setAction() {

        if (onPath) {
        checkIfStopChasing(gp.player, 10);
        searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        }else{
        checkIfStartChasing(gp.player, 5);
        getRandomDirection();
       }

       //check if attacking
       if (!attacking) {
         checkIfAttacking(50, GamePanel.TileSize*4, GamePanel.TileSize);
       }
    }

    public void damageReaction() {

        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
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

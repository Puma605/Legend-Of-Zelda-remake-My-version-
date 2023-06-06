package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;
import object.OBJ_Rock;

public class MON_GreenChuchu extends Entity {

    GamePanel gp;
    public MON_GreenChuchu(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = "Green Chuchu";
        projectile = new OBJ_Rock(gp);
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 4;
        defense = 0;
        exp = 2; 


        solidArea.setBounds(3,18,42,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getDeathImage();
    }
    
    public void getImage() {
        up1 = setup("/monsters/GreenChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/monsters/GreenChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/monsters/GreenChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/monsters/GreenChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/monsters/GreenChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/monsters/GreenChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/monsters/GreenChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/monsters/GreenChuchu2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void getDeathImage() {
        dying1 = setup("/monsters/GreenChuchuDeath1",GamePanel.TileSize,GamePanel.TileSize);
        dying2 = setup("/monsters/GreenChuchuDeath2",GamePanel.TileSize,GamePanel.TileSize);
        dying3 = setup("/monsters/GreenChuchuDeath3",GamePanel.TileSize,GamePanel.TileSize);
    }

    public void setAction() {

        if (onPath) {
        checkIfStopChasing(gp.player, 10);
        searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        checkIfShootsProjectile(350, 20);

        }else{
        checkIfStartChasing(gp.player, 5);
        getRandomDirection();
       }
    }

    public void damageReaction() {

        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(200)+1;
        if (i < 75) 
            dropItem(new OBJ_GoldCoin(gp));
        
        if (i >= 75 && i < 125) 
            dropItem(new OBJ_Heart(gp));
        if (i == 135) {
            dropItem(new OBJ_BlueShield(gp));
        }
    }
    
}

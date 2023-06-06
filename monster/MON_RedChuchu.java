package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;

public class MON_RedChuchu extends Entity {
    GamePanel gp;
    public MON_RedChuchu(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = "Red Chuchu";
        speed = 1;
        maxLife = 8;
        life = maxLife;
        attack = 6;
        defense = 0;
        exp = 5; 


        solidArea.setBounds(3,18,42,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getDeathImage();
    }
    
    public void getImage() {
        up1 = setup("/monsters/RedChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/monsters/RedChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/monsters/RedChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/monsters/RedChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/monsters/RedChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/monsters/RedChuchu2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/monsters/RedChuchu1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/monsters/RedChuchu2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void getDeathImage() {
        dying1 = setup("/monsters/RedChuchuDeath1",GamePanel.TileSize,GamePanel.TileSize);
        dying2 = setup("/monsters/RedChuchuDeath2",GamePanel.TileSize,GamePanel.TileSize);
        dying3 = setup("/monsters/RedChuchuDeath3",GamePanel.TileSize,GamePanel.TileSize);
    }

    public void setAction() {

        if (onPath) {
        checkIfStopChasing(gp.player, 10);
        searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
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
        if (i < 100) 
            dropItem(new OBJ_GoldCoin(gp));
        
        if (i >= 100 && i < 160) 
            dropItem(new OBJ_Heart(gp));
        if (i == 199) {
            dropItem(new OBJ_BlueShield(gp));
        }
    }
}

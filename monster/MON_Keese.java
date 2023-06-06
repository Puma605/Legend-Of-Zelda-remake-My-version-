package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;

public class MON_Keese extends Entity {

    GamePanel gp;
    public MON_Keese(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = "Keese";
        speed = 1;
        maxLife = 6;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 4; 


        solidArea.setBounds(3,15,42,21);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getDeathImage();
    }
    
    public void getImage() {
        up1 = setup("/monsters/Bat1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/monsters/Bat2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/monsters/Bat1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/monsters/Bat2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/monsters/Bat1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/monsters/Bat2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/monsters/Bat1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/monsters/Bat2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void getDeathImage() {
        dying1 = setup("/monsters/BatDeath1",GamePanel.TileSize,GamePanel.TileSize);
        dying2 = setup("/monsters/BatDeath2",GamePanel.TileSize,GamePanel.TileSize);
        dying3 = setup("/monsters/BatDeath3",GamePanel.TileSize,GamePanel.TileSize);
    }

    public void setAction() {
        getRandomDirection();
       
    }

    public void damageReaction() {
        actionLockCounter = 0;
        switch (gp.player.direction) {
            case "up":direction = "down";break;
            case "down":direction = "up";break;
            case "left":direction = "right";break;
            case "right":direction = "left";break;
        }
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


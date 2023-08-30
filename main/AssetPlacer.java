package main;

import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_OldMan;
import entity.NPC_RuebsMerchant;
import entity.NPC_Traveller;
import interactiveTile.IT_DamagedWall;
import interactiveTile.IT_DryTree;
import interactiveTile.IT_MetalPlate;
import monster.MON_Keese;
import monster.MON_GreenChuchu;
import monster.MON_Moblin;
import monster.MON_RedChuchu;
import monster.MON_SkeletonLord;
import object.*;


public class AssetPlacer {
    GamePanel gp;
    public AssetPlacer(GamePanel gp){
     this.gp = gp;
    }
    public void setObject(){
       int mapNum = 0;
       int i = 0;
       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_Powder(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*38;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*7;
       i++;
       gp.obj[mapNum][i] = new OBJ_Door(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*17;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*38;
       i++;
       

       mapNum++;
       i = 0;
       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_WoodShield(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*14;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*4;
       i++;

       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_HealthPotion(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*6;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*3;
       gp.obj[mapNum][i].opened = true;
       i++;

       gp.obj[mapNum][i] = new OBJ_Bed(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*4;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*4;
       i++;

       mapNum = 2;
       i = 0;
       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_DungeonMap(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*40;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*41;
       i++;
       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_Bomb(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*13;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*16;
       i++;
       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_HealthPotion(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*26;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*34;
       i++;

       gp.obj[mapNum][i] = new OBJ_Chest(gp);
       gp.obj[mapNum][i].setLoot(new OBJ_BlueShield(gp));
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*27;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*15;
       i++;
       gp.obj[mapNum][i] = new OBJ_IronDoor(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*18;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*23;
       i++;
       gp.obj[mapNum][i] = new OBJ_RockPodium(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*17;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*24;
       i++;

       mapNum = 3;
       i = 0;
       gp.obj[mapNum][i] = new OBJ_IronDoor(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*25;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*15;
       i++;
       gp.obj[mapNum][i] = new OBJ_MasterSword(gp);
       gp.obj[mapNum][i].worldX = GamePanel.TileSize*25;
       gp.obj[mapNum][i].worldY = GamePanel.TileSize*8;
       i++;

    }
    public void setQuestNPC(){

        if (Progress.OM_QuestTwoComplete) { 
        gp.npc[0][9] = new NPC_Traveller(gp);
        gp.npc[0][9].worldX = GamePanel.TileSize*39;
        gp.npc[0][9].worldY = GamePanel.TileSize*40;
        }
    }
    public void setNpc() {
        int i = 0;
        int mapNum = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = GamePanel.TileSize*21;
        gp.npc[mapNum][i].worldY = GamePanel.TileSize*21;

        

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_RuebsMerchant(gp);
        gp.npc[mapNum][i].worldX = GamePanel.TileSize*11;
        gp.npc[mapNum][i].worldY = GamePanel.TileSize*4;

        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = GamePanel.TileSize*20;
        gp.npc[mapNum][i].worldY = GamePanel.TileSize*25;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = GamePanel.TileSize*11;
        gp.npc[mapNum][i].worldY = GamePanel.TileSize*20;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = GamePanel.TileSize*23;
        gp.npc[mapNum][i].worldY = GamePanel.TileSize*14;
        i++;

    }
    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_GreenChuchu(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*21;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*38;

        i++;
        if (Progress.OM_QuestTwoComplete) 
        gp.monster[mapNum][i] = new MON_RedChuchu(gp);  
        else
        gp.monster[mapNum][i] = new MON_GreenChuchu(gp);

        gp.monster[mapNum][i].worldX = GamePanel.TileSize*23;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*42;

        i++;
        gp.monster[mapNum][i] = new MON_GreenChuchu(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*24;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*37;

        i++;
        gp.monster[mapNum][i] = new MON_GreenChuchu(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*34;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*42;

        i++;
        if (Progress.OM_QuestTwoComplete) 
        gp.monster[mapNum][i] = new MON_RedChuchu(gp);  
        else
        gp.monster[mapNum][i] = new MON_GreenChuchu(gp);
        
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*38;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*42;

        i++;
        gp.monster[mapNum][i] = new MON_Moblin(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*38;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*9;


        mapNum = 2;
        i = 0;
        gp.monster[mapNum][i] = new MON_Keese(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*34;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*39;
        i++;
        gp.monster[mapNum][i] = new MON_Keese(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*36;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*25;
        i++;
        gp.monster[mapNum][i] = new MON_Keese(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*39;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*26;
        i++;
        gp.monster[mapNum][i] = new MON_Keese(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*28;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*11;
        i++;
        gp.monster[mapNum][i] = new MON_Keese(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*10;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*19;
        i++;
        gp.monster[mapNum][i] = new MON_Moblin(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*28;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*19;
        i++;


        mapNum = 3;
        i = 0;
        if (!Progress.SLDEAD) {
        gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
        gp.monster[mapNum][i].worldX = GamePanel.TileSize*23;
        gp.monster[mapNum][i].worldY = GamePanel.TileSize*16;
        i++;  
        }
        

    }

    public void setInteractiveTiles() {
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,27,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,28,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,29,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,30,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,31,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,32,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,33,12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,12,23);
        i++;
        

        mapNum = 2;
        i = 0;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 18, 30);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 17, 31);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 17, 32);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 17, 34);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 18, 34);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 10, 22);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 10, 24);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 38, 18);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 38, 19);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 38, 20);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 38, 21);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 18, 13);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 18, 14);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 22, 28);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 30, 28);
        i++;
        gp.iTile[mapNum][i] = new IT_DamagedWall(gp, 32, 28);
        i++;

        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);
        i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);
        i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31);
        i++;
        

    }
}

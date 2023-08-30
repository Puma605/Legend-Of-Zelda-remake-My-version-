package main;

import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_IronDoor;
import object.OBJ_MasterSword;

public class CutSceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    int counter = 0;
    float alpha = 0f;
    int y;


    //scene number
    public final int NA = 0;
    public final int SkeletonLord = 1;
    public final int MasterSword = 2;


    public CutSceneManager(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;


        switch (sceneNum) {
            case SkeletonLord:sceneSkeletonLord();break;
            case MasterSword:sceneMasterSword();;break;
        }
    }

    public void sceneSkeletonLord(){
        if (scenePhase == 0) {
            gp.bossBattleOn = true;

            //set iron door
            for (int i = 0; i < gp.obj[1].length; i++) {
                if (gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = new OBJ_IronDoor(gp);
                    gp.obj[gp.currentMap][i].worldX = GamePanel.TileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = GamePanel.TileSize * 28;
                    gp.obj[gp.currentMap][i].tempENTITY = true;
                    gp.playSoundEffect(18);
                    break;
                }
            }

            //search for vacant slot for dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = "up";
                    break;
                }
            }

            gp.player.drawing = false;
            scenePhase++;
        }
        if (scenePhase == 1) {
            gp.player.worldY -= 2;
          
            if (gp.player.worldY < GamePanel.TileSize*16) 
                scenePhase++;
        }
        if (scenePhase == 2) {
            //search for boss
            for (int i = 0; i < gp.monster[1].length; i++) {
                if (gp.monster[gp.currentMap][i] != null &&
                         gp.monster[gp.currentMap][i].name == MON_SkeletonLord.MON_NAME) {
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.UI.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if (scenePhase == 3) {
            //speaking
            gp.UI.drawDialogueScreen();
        }
        if(scenePhase == 4){
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] != null 
                && gp.npc[gp.currentMap][i].name == PlayerDummy.NPCNAME) {
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }
            gp.player.drawing = true;

            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = GamePanel.playState;
            gp.stopMusic();
            gp.playMusic(20);
        }
    }
    public void sceneMasterSword() {
        if (scenePhase == 0) {
            gp.stopMusic();
            gp.UI.npc = new OBJ_MasterSword(gp);
            scenePhase++;
        }
        if (scenePhase == 1) {
            gp.UI.drawDialogueScreen();
        }
        if (scenePhase == 2) {
            gp.playSoundEffect(4);
            scenePhase++;
        }
        if (scenePhase == 3) {
            if (wait(480)) {
                scenePhase++;
            }
        }
        if (scenePhase == 4) {
            gp.playSoundEffect(18);
            gp.tileM.mapTileNum[3][25][7] = 88;//tile right infront of master sword turned to stairs
            scenePhase++;
        }
        if (scenePhase == 5) {
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = GamePanel.playState;
        }
    }

    public boolean wait(int target){
       boolean counterReached = false;

       counter++;
       if (counter>=target) {
          counterReached = true;
          counter = 0;
       }
       return counterReached;
    }
}

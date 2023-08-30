package main;

import data.Progress;
import entity.Entity;
import quests.OM_QuestTwo;
import quests.TR_QuestOne;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    Entity eventMaster;

    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
     this.gp = gp;

     eventRect = new EventRect[GamePanel.maxMap][GamePanel.MaxWorldCol][GamePanel.MaxWorldRow];

     eventMaster = new Entity(gp);
     int map = 0;
     int col = 0;
     int row = 0;
     while (map< GamePanel.maxMap && col < GamePanel.MaxWorldCol && row < GamePanel.MaxWorldRow) {
        eventRect[map][col][row] = new EventRect();
        eventRect[map][col][row].x = 23;
        eventRect[map][col][row].y = 23;
        eventRect[map][col][row].width = 2;
        eventRect[map][col][row].height = 2;

        eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
        eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

        col++;
        if (col == GamePanel.MaxWorldCol) {
            col = 0;
            row++;
            if (row == GamePanel.MaxWorldRow) {
                row = 0;
                map++;
            }
        }
     }

     setDialogue();
    }


    public void setDialogue(){
         eventMaster.Dialogues[0][0] = "You tripped!";

         eventMaster.Dialogues[1][0] = "The water healed you!\nProgress has been saved.";
    }


    public void checkEvent() {
        //check if player character is more the 1 tile away
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > GamePanel.TileSize) 
         canTouchEvent = true;
        


        if (canTouchEvent) {
        if (hit(0,23, 12, "up")) 
            healingPool(GamePanel.dialogueState);

        else if (hit(0, 11, 11, "any"))
            teleport(1,9,9,GamePanel.houseArea);



        else if (hit(1, 9, 10, "any")) 
            teleport(0,12,11,GamePanel.overWorldArea);

        else if (hit(1, 11, 6, "any")||hit(1, 12, 5, "any")||
        hit(1, 10, 5, "any")) 
            talkThroughTable(gp.npc[1][0]);

        else if (hit(0, 15, 38, "any")) 
            teleport(2,41,9,GamePanel.dungeonArea);//to first floor

        else if (hit(2, 9, 41, "any")) 
            teleport(0,38,15,GamePanel.overWorldArea);//to outside

        else if (hit(2, 8, 7, "any")) 
            teleport(3,41,26,GamePanel.dungeonArea);//to second floor

        else if (hit(3, 26, 41, "any")) 
            teleport(2,7,8,GamePanel.dungeonArea);//to first floor

        else if (hit(3, 25, 27, "any"))
            SkeletonLordCutScene();
        else if (hit(3, 25, 8, "any") && !TR_QuestOne.masterSwordObtained) 
            MasterSwordCutScene();
        else if (hit(3, 25, 7, "any")) 
            teleport(0,38,15,GamePanel.overWorldArea);//to outside
        }
        
        
        

    }

    public boolean hit(int map ,int eventCol,int eventRow, String reqDirection) {
        boolean hit = false;
        if(map == gp.currentMap){
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        eventRect[map][eventCol][eventRow].x = eventCol*GamePanel.TileSize + eventRect[map][eventCol][eventRow].x;
        eventRect[map][eventCol][eventRow].y = eventRow*GamePanel.TileSize + eventRect[map][eventCol][eventRow].y;
        if (gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow])&& !(eventRect[map][eventCol][eventRow].eventDone)) {
            if (gp.player.direction == reqDirection || reqDirection.equals("any")) 
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
        eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }


        

        return hit;
    }


    public void damagePit(int gameState) {
        gp.gameState = gameState;
        eventMaster.startDialogue(eventMaster, 0,false);
        gp.player.life -= 1;
        canTouchEvent = false;

    }


    public void healingPool(int gameState) {
        if (gp.KeyH.enterPressed) {
            if(gp.player.currentQuest != null && gp.player.currentQuest.QuestName == OM_QuestTwo.QUEST_NAME &&
            gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[0])
                 gp.player.currentQuest.NextObjective();
            
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            eventMaster.startDialogue(eventMaster, 1,false);
            gp.player.life = gp.player.maxLife;
            gp.playSoundEffect(2);
            gp.aPlacer.setMonster();
            gp.saveLoad.save();
        }
    }
    public void talkThroughTable(Entity Npc){
        if (gp.KeyH.enterPressed){
        gp.gameState = GamePanel.dialogueState;
        gp.player.attackCanceled = true;
        Npc.talk();  
        } 
    }
    public void teleport(int mapNum, int row, int col, int area){
      gp.gameState = GamePanel.transitionState;
      gp.nextArea = area;
      tempMap = mapNum;
      tempCol = col;
      tempRow = row;
      canTouchEvent = false;
      gp.playSoundEffect(12);
    }
    public void addDialogue(Entity npc, String dialogue){
        for (int i = 0; i < npc.Dialogues.length; i++) {
            if (npc.Dialogues[0][i]==null) {
                npc.Dialogues[0][i] = dialogue;
                break;
            }
        }
    }
    public void SkeletonLordCutScene(){
          if (!gp.bossBattleOn && !Progress.SLDEAD) {
            gp.gameState = GamePanel.cutSceneState;
            gp.CSManager.sceneNum = gp.CSManager.SkeletonLord;
            
            if (gp.player.currentQuest.QuestName == TR_QuestOne.QUEST_NAME && gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[2]) 
            gp.player.currentQuest.NextObjective();
          }
    }
    public void MasterSwordCutScene(){
          if (gp.player.currentQuest.QuestName == TR_QuestOne.QUEST_NAME) 
            gp.player.currentQuest.NextObjective();
          TR_QuestOne.masterSwordObtained = true;
          gp.gameState = GamePanel.cutSceneState;
          gp.CSManager.sceneNum = gp.CSManager.MasterSword;
  }
}

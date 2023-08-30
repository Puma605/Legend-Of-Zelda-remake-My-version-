package entity;


import java.util.Random;

import data.Progress;
import main.GamePanel;
import object.OBJ_Key;
import quests.TR_QuestOne;

public class NPC_Traveller extends Entity {

    public NPC_Traveller(GamePanel gp) {
        super(gp);
        solidArea.setBounds(1, 1, 47, 47);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        dialogueSet = -1;
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/npc/TR_Up1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/npc/TR_Up2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/npc/TR_Down1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/npc/TR_Down2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/npc/TR_Left1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/npc/TR_Left2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/npc/TR_Right1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/npc/TR_Right2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void setDialogue() {
        Dialogues[0][0] = "Hello?\nCan I help you?";
        Dialogues[1][0] = "Can you come back a different time?";




        QuestDialogues[0][0] = "Hello there!\nAre you also a fellow treasure hunter?";
        QuestDialogues[0][1] = "I heard that there is a cave somewhere here\nwhere the legendary Master sword is hidden!";
        QuestDialogues[0][2] = "My guess is that its the cave blocked by the\ndoor...";
        QuestDialogues[0][3] = "But I found this old key next to the bushes over\nthere! It just might open it.";
        QuestDialogues[0][4] = "Normally id go to the cave immediatly to get the\nsword, but I heard the Skeleton Lord is\ngaurding it...";
        QuestDialogues[0][5] = "So here take this key and get the Master\nsword!";

        
        QuestDialogues[1][0] = "Is... is that...\nthe legendary Master Sword!?";//break here + OM backstab
        QuestDialogues[1][1] = "That means you defeated the Skeleton Lord!";
        QuestDialogues[1][2] = "I didnt know you had it in you.";
        QuestDialogues[1][3] = "Well I guess the teasure has been found...\nThere isnt much to do here anymore...";
    }
    

    public void setAction() {
        int goalCol;
        int goalRow;
        if (onPath) {
        
        goalCol = 8;
        goalRow = 12;

        searchPath(goalCol,goalRow);
        }else{
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
    
        }

    public void handleQuests(){
    if (gp.player.currentQuest == null && Progress.OM_QuestTwoComplete && !Progress.TR_QuestOneComplete) {//start quest
        if(gp.player.canObtainItem(new OBJ_Key(gp))){
        gp.player.currentQuest = new TR_QuestOne(gp);
        questDialogueSet = 0;
        startDialogue(this, questDialogueSet, true);
        }else{
         dialogueSet = 1;
         startDialogue(this, dialogueSet, false);
        }

    }
    if (gp.player.currentQuest != null && gp.player.currentQuest.QuestName == TR_QuestOne.QUEST_NAME && gp.player.currentQuest.currentObjective!= gp.player.currentQuest.Objectives[5]) {
        questDialogueSet = 0;
        startDialogue(this, questDialogueSet, true);
    }else if (gp.player.currentQuest != null && gp.player.currentQuest.QuestName == TR_QuestOne.QUEST_NAME && gp.player.currentQuest.currentObjective!= gp.player.currentQuest.Objectives[5]) {
        questDialogueSet = 1;
        startDialogue(this, questDialogueSet, true);
        gp.player.currentQuest.NextObjective();
    }
    }

    public void talk() {
       facePlayer();

       if (gp.player.currentQuest == null && Progress.OM_QuestTwoComplete && !Progress.TR_QuestOneComplete) {
          handleQuests();
       }else if (gp.player.currentQuest.QuestName == TR_QuestOne.QUEST_NAME) {
          handleQuests();
       }else{
        startDialogue(this, dialogueSet, false);
          dialogueSet++;

       if(Dialogues[dialogueSet][0] == null)
        dialogueSet--;
       }
       }
       
       
    }
        

    
    

    


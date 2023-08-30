package entity;


import java.util.Random;

import data.Progress;
import main.GamePanel;
import object.OBJ_Bomb;
import object.OBJ_Lantern;
import quests.OM_QuestOne;
import quests.OM_QuestTwo;
import quests.Ruebs_QuestOne;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        solidArea.setBounds(1, 1, 47, 47);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


        dialogueSet = 0;
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/npc/OM_Up1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/npc/OM_Up2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/npc/OM_Down1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/npc/OM_Down2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/npc/OM_Left1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/npc/OM_Left2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/npc/OM_Right1",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/npc/OM_Right2",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void setDialogue() {

        Dialogues[0][0] = "The road ahead is very dangerous,\nare you sure you want to continue?";
        Dialogues[0][1] = "Press ENTER to attack!";
        Dialogues[0][2] = "Beware of the green Chuchus, they will charge\nin your direction once you attack.";

        Dialogues[1][0] = "How are you still alive?";
        Dialogues[1][1] = "I thought you would be dead by now...";
        Dialogues[1][2] = "Now I owe that tree 10 gold!";
        Dialogues[1][3] = "Well I guess I underestimated you Jerry.\n I wont make that mistake again.";
        Dialogues[1][4] = "I guess ill see you around, bye Barry!\n(Im sure youre not survivng the night...)";

        Dialogues[2][0] = "Still here?";
        Dialogues[2][1] = "Well I might aswell give you some tips.";
        Dialogues[2][2] = "Press SPACE to use your shield,\nif you time it right you can parry any enemy!";
        Dialogues[2][3] = "The Moblin is a formidable opponent,\nfight him with extreme caution!";



        //quest dialogue
        
                
        QuestDialogues[0][0] = "Hello there! I see you are new to these lands.";
        QuestDialogues[0][1] = "Welcome to Rile island, this place is where\nyou will die. probably.";
        QuestDialogues[0][2] = "This island is very dangerous and is a home\nto many monsters.";
        QuestDialogues[0][3] = "If you kill 3 Chuchus and I just might give\nyou some tips on how to survive.";

        QuestDialogues[1][0] = "Well you really did it...";
        QuestDialogues[1][1] = "To be honset I thought you would die\nImmediatly. Well as I promised I will give\nyou some tips.";
        QuestDialogues[1][2] = "You should also talk to Ruebs the merhcant.\nHe lives in the old house northwest.";
        QuestDialogues[1][3] = "Take this lantern just in case it gets dark!";
        QuestDialogues[1][4] = "Before you go there I must tell you that there\nis a big dry bush blocking the way.";




        QuestDialogues[2][0] = "Hey you! whatever your name is, I made\nan incredible discovery!";
        QuestDialogues[2][1] = "If you go up the path and drink the water,\nit will replenish your health, but it will\nalso revive the monsters!";
        QuestDialogues[2][2] = "Why dont you go and try it?";

        QuestDialogues[3][0] = "Dont you just feel rejuvenated!";
        QuestDialogues[3][1] = "Another discovery I made is the more monsters\nyou kill, the more experience you get,\nand if you get enough experience you might just\nlevel up!";
        QuestDialogues[3][2] = "The stronger the monster the more experience\nyou gain.";
        QuestDialogues[3][3] = "Here go try it!";

        QuestDialogues[4][0] = "Aren't you stronger?";
        QuestDialogues[4][1] = "What am I saying of course you are!";
        QuestDialogues[4][2] = "You know most people dont listen to my tips...\nSo I decided to give you something...";
        QuestDialogues[4][3] = "Take these deadly explosives!\nThey are fun to use...";
        QuestDialogues[4][4] = "Also go to talk to the traveller that just arrived!";

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
        if (gp.player.currentQuest == null && Ruebs_QuestOne.QuestComplete && !OM_QuestTwo.QuestComplete) {
            questDialogueSet = 2;
            startDialogue(this, questDialogueSet, true);
            gp.player.currentQuest = new OM_QuestTwo(gp);
        }
        else if (gp.player.currentQuest != null && gp.player.currentQuest.QuestName == OM_QuestOne.QUEST_NAME) {
            startDialogue(this, questDialogueSet, true); 

          //complete first quest
        if (gp.player.currentQuest.currentObjective==gp.player.currentQuest.Objectives[0]) 
             gp.player.currentQuest.Objectives[0].ObjectiveComplete = true;
    
         //dialogue after chuchu
        else if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[2]){ 
            Entity OMGift = new OBJ_Lantern(gp);
            if(gp.player.canObtainItem(OMGift)){
            questDialogueSet++;
            gp.player.currentQuest.currentObjective.ObjectiveComplete = true;  
            }

          } 
        }
        else if(gp.player.currentQuest != null && gp.player.currentQuest.QuestName == OM_QuestTwo.QUEST_NAME){
            //health pool
            if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[0]) {
            questDialogueSet = 2;
            startDialogue(this, questDialogueSet, true);  
            }
            //level up
            if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[1]) {
            questDialogueSet = 3;
            startDialogue(this, questDialogueSet, true);  
            gp.player.currentQuest.NextObjective();
            }
            if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[2]) {
            questDialogueSet = 3;
            startDialogue(this, questDialogueSet, true);  
            }
            //bombs
            if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[3]) {
            questDialogueSet = 4;
            startDialogue(this, questDialogueSet, true);  
            gp.player.currentQuest.NextObjective();

            Entity OMGift = new OBJ_Bomb(gp);
            if(!gp.player.canObtainItem(OMGift))
              ammo+=10;
            else
              gp.player.inventory.get(gp.player.searchInventory(OMGift.name)).amount = 10;
            
            gp.aPlacer.setQuestNPC();
            
            }
        }
        
    }
    public void talk() {
       facePlayer();
       
       if (gp.player.currentQuest != null && (!Progress.OM_QuestOneComplete || (Progress.Ruebs_QuestOneComplete && !Progress.OM_QuestTwoComplete)))
          handleQuests();
        else if(gp.player.currentQuest == null && Ruebs_QuestOne.QuestComplete && !OM_QuestTwo.QuestComplete) 
          handleQuests();
        else{
          startDialogue(this, dialogueSet, false);
          dialogueSet++;

       if(Dialogues[dialogueSet][0] == null)
        dialogueSet--;
       }    
    }   
}

package entity;

import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_Bomb;
import object.OBJ_HealthPotion;
import object.OBJ_IronSword;
import object.OBJ_Lantern;
import object.OBJ_Powder;
import object.OBJ_WoodShield;
import quests.OM_QuestOne;
import quests.Ruebs_QuestOne;

public class NPC_RuebsMerchant extends Entity {
    public NPC_RuebsMerchant(GamePanel gp) {
        super(gp);
        name = "Ruebs Merchant";
        direction = "down";
        speed = 1;
        questDialogueSet = 0;
        getImage();
        setDialogue();
        setItems();
    }
    public void getImage() {
        up1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void setDialogue() {
        Dialogues[0][0] = "If you want I can sell you some stuff...\nI might just buy whatever you have\nif its worth my time.";
        Dialogues[1][0] = "Be sure to come again...";
        Dialogues[2][0] = "You need more coins!";
        Dialogues[3][0] = "Your inventory is full!";
        Dialogues[4][0] = "You cannot sell an equiped item!";
        Dialogues[5][0] = "You cannot sell this item!";


        QuestDialogues[0][0] = "Who are you?";

        QuestDialogues[1][0] = "Oh hello...\nyou're new around here.";
        QuestDialogues[1][1] = "Normally I sell valuable items but im missing\nsomething important.";
        QuestDialogues[1][2] = "I forgot my... um... powder outside while\ngathering resources, the old man told me\nthat the Moblin took it.";
        QuestDialogues[1][3] = "If you can get it for me I will compensate you\nfor your troubles...";
        QuestDialogues[1][4] = "This powder is very important to me...\nit makes me happy...";
        QuestDialogues[1][5] = "But before you get it, kill the Moblin that\ntook it.";

        QuestDialogues[2][0] = "My powder!";
        QuestDialogues[2][1] = "Thank you so much, please take these 50 coins.";
        QuestDialogues[2][2] = "Im impressed you were able to beat the moblin,\nyou might just be worth something.";
        QuestDialogues[2][3] = "If you ever need anything just talk to me and i'll\ngive it to you... for a price...";
        QuestDialogues[2][4] = "And if it ever gets to dark please dont be shy to\nuse the bed to the right.";
        QuestDialogues[2][5] = "But first I need to umm... smell the powder...";
    }
    public void setItems() {
        inventory.add(new OBJ_HealthPotion(gp));
        inventory.add(new OBJ_WoodShield(gp));
        inventory.add(new OBJ_BlueShield(gp));
        inventory.add(new OBJ_IronSword(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Bomb(gp));
    }

    public void handleQuests(){
      if (gp.player.currentQuest == null && OM_QuestOne.QuestComplete && !Ruebs_QuestOne.QuestComplete ) {
            questDialogueSet = 1;
            gp.player.currentQuest = new Ruebs_QuestOne(gp);
            startDialogue(this, questDialogueSet, true);
        }
        if (gp.player.currentQuest != null){
            if(gp.player.currentQuest.QuestName == Ruebs_QuestOne.QUEST_NAME) {
                if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[0]) 
                    startDialogue(this, questDialogueSet, true);

                if (gp.player.currentQuest.currentObjective == gp.player.currentQuest.Objectives[2]){
                    int index = gp.player.searchInventory(OBJ_Powder.OBJ_NAME);
                    if (index != 999) {
                        gp.player.inventory.remove(index);
                        questDialogueSet = 2;
                        gp.player.coin += 50;
                        startDialogue(this, questDialogueSet, true);
                        gp.player.currentQuest.NextObjective();
                    }else{
                        questDialogueSet = 1;
                        startDialogue(this, questDialogueSet, true);
                    }
                }

            }
        }
    }
    @Override
    public void talk() {
        facePlayer();
        //start quest
        gp.UI.npc = this;
        
        handleQuests();
        if (!OM_QuestOne.QuestComplete){
            startDialogue(this, questDialogueSet, true);
        }
        
        if (Ruebs_QuestOne.QuestComplete){
        gp.UI.isQuest = false;
        gp.gameState = GamePanel.tradeState;
        }
        
        
        
    }

}

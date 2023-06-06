package entity;


import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
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
        Dialogues[0][0] = "Hello there! I see you are \nnew to these lands.";
        Dialogues[0][1] = "The road ahead is very dangerous,\nare you sure you want to continue?";
        Dialogues[0][2] = "If you go up the path and drink the water,\nit will replenish your health, but it will also\n revive the monsters!";
        Dialogues[0][3] = "Press ENTER to attack!";
        Dialogues[0][4] = "Beware of the green Chuchus, they will charge\nin your direction once you attack.";

        Dialogues[1][0] = "How are you still alive?";
        Dialogues[1][1] = "I thought you would be dead by now...";
        Dialogues[1][2] = "Now I owe that tree 10 gold!";
        Dialogues[1][3] = "Well I guess I underestimated you Jerry.\n I wont make that mistake again.";
        Dialogues[1][4] = "I guess ill see you around, bye Barry!\n(Im sure youre not survivng the night...)";

        Dialogues[2][0] = "Still here?";
        Dialogues[2][1] = "Well I might aswell give you some tips.";
        Dialogues[2][2] = "Press SPACE to use your shield,\nif you time it right you can parry any enemy!";
        Dialogues[2][3] = "The Moblin is a formidable opponent,\nfight him with extreme caution!";
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
    public void talk() {
       facePlayer();
       startDialogue(this, dialogueSet);

       dialogueSet++;

       if(Dialogues[dialogueSet][0] == null)
        dialogueSet--;
       
       
       
    }
        

    
    

    
}

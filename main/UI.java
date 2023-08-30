package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

import entity.Entity;
import object.OBJ_Bomb;
import object.OBJ_GoldCoin;
import object.OBJ_Heart;


import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica;


    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public boolean isQuest = false;

    BufferedImage fullHeart,halfHeart,emptyHeart,bombImage,coin;

    public String currentDialogue;

    public int commandNumber = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    int cnt = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    public UI(GamePanel gp){
        this.gp = gp;
        
        
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {

            e.printStackTrace();
        }

        //create hud obj
        Entity Heart = new OBJ_Heart(gp);
        fullHeart = Heart.image;
        halfHeart = Heart.image1;
        emptyHeart = Heart.image2;

        bombImage = new OBJ_Bomb(gp).down1;
        Entity coin = new OBJ_GoldCoin(gp);
        this.coin = coin.down1;


    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        if (gp.gameState == GamePanel.playState) {
            drawPlayerLife();
            drawBombAmmo(gp.player.ammo);
            drawQuestInfo();
        }else if(gp.gameState == GamePanel.pauseState){
          drawPauseScreen();
          drawPlayerLife();
          drawBombAmmo(gp.player.ammo);
        }else if (gp.gameState == GamePanel.dialogueState) {
            drawDialogueScreen();
        }else if(gp.gameState == GamePanel.titleState){
           drawTitleScreen();
        }else if(gp.gameState == GamePanel.characterState){
            drawCharacterScreen();
            drawInventory(gp.player,true);
         }else if(gp.gameState == GamePanel.optionsState){
            drawOptionsScreen();
         }else if(gp.gameState == GamePanel.gameOverState){
            drawGameOverScreen();
         }else if(gp.gameState == GamePanel.transitionState){
            drawTransition();
         }else if(gp.gameState == GamePanel.tradeState){
            drawTradeScreen();
         }else if(gp.gameState == GamePanel.sleepState){
            drawSleepScreen();
         }
    }
    public void drawPlayerLife() {
        int x = GamePanel.TileSize/2;
        int y = GamePanel.TileSize/2;
        int i = 0;
        //blank
        while (i < gp.player.maxLife/2) {
            g2.drawImage(emptyHeart, x, y,null);
            i++;
            x += GamePanel.TileSize;
        }

        x = GamePanel.TileSize/2;
        y = GamePanel.TileSize/2;
        i = 0;
        while (i< gp.player.life) {
            g2.drawImage(halfHeart, x, y, null);
            i++;

            if (i < gp.player.life) 
            g2.drawImage(fullHeart, x, y, null);
            
            i++;
            x += GamePanel.TileSize;
        }

    }
    public void drawQuestInfo(){
        int x = GamePanel.TileSize/2;
        int y = 2* GamePanel.TileSize;


        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
        g2.drawString("CURRENT OBJECTIVE", x, y);

        y += GamePanel.TileSize/2;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,28F));
        g2.setColor(Color.YELLOW);

        if (gp.player.currentQuest !=null) 
            g2.drawString(gp.player.currentQuest.currentObjective.ObjectiveName, x, y);
        else
            g2.drawString("No current objective.", x, y);
        
    }
    public void drawTitleScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,85F));

        String text = "Legend of Zelda";
        int x = getXforCenteredText(text);
        int y = GamePanel.TileSize *3;

        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        //title
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //link image
        x = GamePanel.screenWidth/2 - (GamePanel.TileSize);
        y += GamePanel.TileSize*2;
        g2.drawImage(gp.player.down1, x,y,GamePanel.TileSize*2,GamePanel.TileSize*2,null);

        //menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += GamePanel.TileSize*3.5;
        g2.drawString(text, x, y);
        if (commandNumber == 0) 
            g2.drawString(">", x-GamePanel.TileSize, y);
        

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += GamePanel.TileSize;
        g2.drawString(text, x, y);
        if (commandNumber == 1) 
            g2.drawString(">", x-GamePanel.TileSize, y);

        text = "QUIT";
        x = getXforCenteredText(text);
        y += GamePanel.TileSize;
        g2.drawString(text, x, y);
        if (commandNumber == 2) 
            g2.drawString(">", x-GamePanel.TileSize, y);
    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(100F));
        String text = "Paused";

        int x = getXforCenteredText(text);
        int y = GamePanel.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {
        //window
        int x = GamePanel.TileSize*2;
        int y = GamePanel.TileSize/2;
        int width = GamePanel.screenWidth - (GamePanel.TileSize*4);
        int height = GamePanel.TileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += GamePanel.TileSize;
        y += GamePanel.TileSize;

        if (isQuest) 
            drawQuestDialogue(x, y, width, height);
        else{
        if (npc.Dialogues[npc.dialogueSet][npc.dialogueIndex] != null){
            // currentDialogue = npc.Dialogues[npc.dialogueSet][npc.dialogueIndex];
            char characters[] = npc.Dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if (charIndex < characters.length) {
                if(charIndex % 2 == 0)gp.playSoundEffect(15);
                String s = String.valueOf(characters[charIndex]);
                combinedText+=s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gp.KeyH.enterPressed && (gp.gameState == GamePanel.dialogueState || gp.gameState == GamePanel.cutSceneState )) {
                charIndex = 0;
                combinedText = "";
                npc.dialogueIndex++;
                gp.KeyH.enterPressed = false;
            }
        }else{
            npc.dialogueIndex = 0;
            if (gp.gameState == GamePanel.dialogueState) 
               gp.gameState =GamePanel.playState;
            if (gp.gameState == GamePanel.cutSceneState) 
                gp.CSManager.scenePhase++;
            
        }


        for (String line : currentDialogue.split("\n")) {

            g2.drawString(line, x, y); 
            y+=40;
        }
    }
        
    }
    public void drawQuestDialogue(int x, int y, int width, int height){
        if (npc.QuestDialogues[npc.questDialogueSet][npc.questDialogueIndex] != null){
            // currentDialogue = npc.Dialogues[npc.dialogueSet][npc.dialogueIndex];
            char characters[] = npc.QuestDialogues[npc.questDialogueSet][npc.questDialogueIndex].toCharArray();

            if (charIndex < characters.length) {
                if(charIndex % 2 == 0)gp.playSoundEffect(15);
                String s = String.valueOf(characters[charIndex]);
                combinedText+=s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gp.KeyH.enterPressed && gp.gameState == GamePanel.dialogueState) {
                charIndex = 0;
                combinedText = "";
                npc.questDialogueIndex++;
                gp.KeyH.enterPressed = false;
            }
        }else{
            npc.questDialogueIndex = 0;
            if (gp.gameState == GamePanel.dialogueState){
               gp.gameState = GamePanel.playState;
            // if (gp.player.currentQuest.currentObjective.ObjectiveComplete) 
            //    gp.player.currentQuest.NextObjective();
            }
            
        }


        for (String line : currentDialogue.split("\n")) {

            g2.drawString(line, x, y); 
            y+=40;
        }
    }

    public void drawCharacterScreen(){
      //create frame
      final int frameX = GamePanel.TileSize *2 ;
      final int frameY = GamePanel.TileSize;
      final int frameWidth = GamePanel.TileSize*5;
      final int frameHeight= GamePanel.TileSize*10;
      drawSubWindow(frameX, frameY, frameWidth, frameHeight);

      //text
      g2.setColor(Color.white);
      g2.setFont(g2.getFont().deriveFont(32F));

      int textX = frameX + 20;
      int textY = frameY + GamePanel.TileSize;
      final int lineHeight = 35;

      //names
      g2.drawString("Level", textX, textY);
      textY += lineHeight;
      g2.drawString("Life", textX, textY);
      textY += lineHeight;
      g2.drawString("Strength", textX, textY);
      textY += lineHeight;
      g2.drawString("Dexterity", textX, textY);
      textY += lineHeight;
      g2.drawString("Attack", textX, textY);
      textY += lineHeight;
      g2.drawString("Defense", textX, textY);
      textY += lineHeight;
      g2.drawString("Exp", textX, textY);
      textY += lineHeight;
      g2.drawString("Next Level", textX, textY);
      textY += lineHeight;
      g2.drawString("Coins", textX, textY);
      textY += lineHeight + 20;
      g2.drawString("Weapon", textX, textY);
      textY += lineHeight + 15;
      g2.drawString("Shield", textX, textY);

      //values
      int tailX = frameX + frameWidth - 30;
      textY = frameY + GamePanel.TileSize;
      String value;
      value = String.valueOf(gp.player.level);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);
      
      textY += lineHeight;
      value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.strength);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.dexterity);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.attack);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.defense);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.exp);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.nextLevelExp);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      value = String.valueOf(gp.player.coin);
      textX = getXforAlignToRightText(value, tailX);
      g2.drawString(value, textX, textY);

      textY += lineHeight;
      g2.drawImage(gp.player.currentWeapon.down1,tailX-GamePanel.TileSize, textY-14, null);

      textY += 48;
      g2.drawImage(gp.player.currentShield.down1,tailX-GamePanel.TileSize, textY-14, null);


    }
    public void drawInventory(Entity entity, boolean cursor) {
        //frame
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;
        if (entity == gp.player) {
            frameX = GamePanel.TileSize *9;
            frameY = GamePanel.TileSize;
            frameWidth = GamePanel.TileSize*6;
            frameHeight= GamePanel.TileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }else{
            frameX = GamePanel.TileSize *2;
            frameY = GamePanel.TileSize;
            frameWidth = GamePanel.TileSize*6;
            frameHeight= GamePanel.TileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow; 
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = GamePanel.TileSize+3;

        //draw players items
        for (int i = 0; i < entity.inventory.size(); i++) {
            //equip cursor
            if (entity.inventory.get(i) == entity.currentWeapon ||
            entity.inventory.get(i) == entity.currentShield ||
            entity.inventory.get(i)==entity.currentLight) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, GamePanel.TileSize, GamePanel.TileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

           //display amount
           if (entity.inventory.get(i).amount > 1 && entity == gp.player) {
               g2.setFont(g2.getFont().deriveFont(32F));
               int amountX;
               int amountY;

               String s = "" + entity.inventory.get(i).amount;
               amountX = getXforAlignToRightText(s, slotX+44);
               amountY = slotY+ GamePanel.TileSize;

               //shadow
               g2.setColor(new Color(60,60,60));
               g2.drawString(s, amountX, amountY);
               //number
               g2.setColor(Color.white);
               g2.drawString(s, amountX-3, amountY-3);
           }

            slotX+= slotSize;

            if (i == 4|| i == 9|| i == 14) {
                slotX = slotXStart;
                slotY+= slotSize;
            }
        }

        //cursor
        if (cursor) {
        int cursorX = slotXStart + slotSize*slotCol;
        int cursorY = slotYStart + slotSize*slotRow;
        int cursorWidth = GamePanel.TileSize;
        int cursorHeight = GamePanel.TileSize;

        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


        //description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = GamePanel.TileSize*3;

        //draw desc text
        int textX = dFrameX + 20;
        int textY = dFrameY + GamePanel.TileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
 
        int itemIndex = getItemIndexOnSlot(slotCol,slotRow);
        if (itemIndex< entity.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
            
        }    
        }
        

    }
    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = GamePanel.TileSize*4;
        int frameY = GamePanel.TileSize;
        int frameHeight = GamePanel.TileSize*10;
        int frameWidth = GamePanel.TileSize*8;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:optionsTop(frameX,frameY);break;
            case 1:OptionsControl(frameX, frameY);break;
            case 2:endGameConfirmation(frameX, frameY);break;
        }
        gp.KeyH.enterPressed = false;
    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110F));

        text = "Game Over";
        g2.setColor(Color.darkGray);
        x = getXforCenteredText(text);
        y = GamePanel.TileSize*4;
        g2.drawString(text, x, y);//shadow

        g2.setColor(Color.red);
        g2.drawString(text, x-4, y-4);//main

        //retry
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenteredText(text);
        y += GamePanel.TileSize*4;
        g2.drawString(text, x, y);
        if (commandNumber == 0) {
            g2.drawString(">", x-40, y);
        }


        //back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNumber == 1) {
            g2.drawString(">", x-40, y);
        }

    }
    public void drawSleepScreen(){
        counter++;

        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if (gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f; 
                gp.player.getSleepingImage();
            }
        }
        if (counter >= 120) {
            gp.player.getImage();
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if (gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.gameState =GamePanel.playState;
            }
        }

    }
    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);
        if (counter == 50) {
            counter = 0;
            gp.gameState =GamePanel.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldX = gp.eventHandler.tempCol* GamePanel.TileSize;
            gp.player.worldY = gp.eventHandler.tempRow* GamePanel.TileSize;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }
    public void drawTradeScreen(){
      switch (subState) {
        case 0:tradeSelect();break;
        case 1:tradeBuy();break;
        case 2:tradeSell();break;
      }
      gp.KeyH.enterPressed = false;
    }
   
    public void tradeSelect(){
      npc.dialogueSet = 0;
      drawDialogueScreen();
      int x = GamePanel.TileSize*11;
      int y = GamePanel.TileSize * 4+24;
      int width = GamePanel.TileSize * 3;
      int height = (int) (GamePanel.TileSize*3.5);
      drawSubWindow(x, y, width, height);

      //draw text
      x+= GamePanel.TileSize;
      y +=GamePanel.TileSize;
      g2.drawString("Buy", x, y);
      if (commandNumber==0) {
        g2.drawString(">", x-24, y);
        if (gp.KeyH.enterPressed) 
            subState = 1;
      }
      y+=GamePanel.TileSize;
      g2.drawString("Sell", x, y);
      if (commandNumber==1) {
        g2.drawString(">", x-24, y);
        if (gp.KeyH.enterPressed) 
            subState = 2;
      }
      y+=GamePanel.TileSize;
      g2.drawString("Back", x, y);
      if (commandNumber==2) {
        g2.drawString(">", x-24, y);
        if (gp.KeyH.enterPressed){
          commandNumber = 0;
          combinedText = "";
          charIndex = 0;
          npc.startDialogue(npc,1,false);
        }
      }



    }
    public void tradeBuy() {
        //player inventoy
        drawInventory(gp.player, false);
        //draw npc
        drawInventory(npc, true);

        //hint window
        int x = GamePanel.TileSize*2;
        int y = GamePanel.TileSize*9;
        int width = GamePanel.TileSize*6;
        int height = GamePanel.TileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);


        //player coin
        x = GamePanel.TileSize*9;
        height = GamePanel.TileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Wallet: "+ gp.player.coin, x+24, y+60);

        //draw price
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex<npc.inventory.size()){
            x = (int)(GamePanel.TileSize * 5.5);
            y = (int)(GamePanel.TileSize * 5.5);
            width = (int)(GamePanel.TileSize*2.5);
            height = GamePanel.TileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32,32,null);
            int price = npc.inventory.get(itemIndex).price;
            String text = ""+ price;
            x = getXforAlignToRightText(text, GamePanel.TileSize*8-20);
            g2.drawString(text, x, y+34);

            //buying an item
            if (gp.KeyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price>gp.player.coin) {
                    subState = 0;
                    combinedText = "";
                    charIndex = 0;
                    npc.startDialogue(npc,2,false);
                }else{
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) 
                        gp.player.coin -= npc.inventory.get(itemIndex).price; 
                    else{
                        subState = 0;
                        combinedText = "";
                        charIndex = 0;
                        npc.startDialogue(npc,3,false);
                    }
                }
            }
        }
    }
    public void tradeSell() {
        //draw player inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;
 
         //hint window
         x = GamePanel.TileSize*2;
         y = GamePanel.TileSize*9;
         width = GamePanel.TileSize*6;
         height = GamePanel.TileSize*2;
         drawSubWindow(x, y, width, height);
         g2.drawString("[ESC] Back", x+24, y+60);
 
 
         //player coin
         x = GamePanel.TileSize*9;
         height = GamePanel.TileSize*2;
         drawSubWindow(x, y, width, height);
         g2.drawString("Wallet: "+ gp.player.coin, x+24, y+60);
 
         //draw price
         int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
         if(itemIndex<gp.player.inventory.size()){
             x = (int)(GamePanel.TileSize * 12.5);
             y = (int)(GamePanel.TileSize * 5.5);
             width = (int)(GamePanel.TileSize*2.5);
             height = GamePanel.TileSize;
             drawSubWindow(x, y, width, height);
             g2.drawImage(coin, x+10, y+8, 32,32,null);

             int price = (int) (gp.player.inventory.get(itemIndex).price*0.7);
             String text = ""+ price;
             x = getXforAlignToRightText(text, GamePanel.TileSize*15-20);
             g2.drawString(text, x, y+34);
 
             //selling an item
             if (gp.KeyH.enterPressed) {
                if (gp.player.inventory.get(itemIndex)==gp.player.currentWeapon||
                gp.player.inventory.get(itemIndex)==gp.player.currentShield) {
                    commandNumber = 0;
                    subState = 0;
                    combinedText = "";
                    charIndex = 0;
                    npc.startDialogue(npc,4,false);
                }else if(gp.player.inventory.get(itemIndex).price < 0){
                    commandNumber = 0;
                    subState = 0;
                    combinedText = "";
                    charIndex = 0;
                    npc.startDialogue(npc,5,false);
                }else{
                    if (gp.player.inventory.get(itemIndex).amount > 1) 
                     gp.player.inventory.get(itemIndex).amount--;
                    else
                     gp.player.inventory.remove(itemIndex);
                    
                    gp.player.coin += price; 
                    
                }
             }
         }
    }
    public void optionsTop(int frameX, int frameY) {
        int textX;
        int textY;

        String Text = "Options";
        textX = getXforCenteredText(Text);
        textY = frameY + GamePanel.TileSize;
        g2.drawString(Text, textX, textY);

        //miniMap
        textX = frameX + GamePanel.TileSize;
        textY += (int)(GamePanel.TileSize*1.5);
        g2.drawString("MiniMap", textX, textY);
        if (commandNumber == 0){
            if (gp.KeyH.enterPressed) {
                if (gp.map.miniMapOn) {
                gp.map.miniMapOn = false;
                }else if (!gp.map.miniMapOn) {
                gp.map.miniMapOn = true;
            }
        }
        g2.drawString(">", textX-25, textY);
        }

        //music
        textY += GamePanel.TileSize;
        g2.drawString("Music", textX, textY);
        if (commandNumber == 1) 
        g2.drawString(">", textX-25, textY);
        

        //se
        textY += GamePanel.TileSize;
        g2.drawString("SE", textX, textY);
        if (commandNumber == 2) 
        g2.drawString(">", textX-25, textY);

        //controls
        textY += GamePanel.TileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNumber == 3){
        g2.drawString(">", textX-25, textY);
        if (gp.KeyH.enterPressed) {
            subState = 1;
            commandNumber = 0;
        }
        }


        //end game
        textY += GamePanel.TileSize*2;
        g2.drawString("End Game", textX, textY);
        if (commandNumber == 4){
        g2.drawString(">", textX-25, textY);
        if (gp.KeyH.enterPressed) {
            subState = 2;
            commandNumber = 0;
        }
        }

        //back
        textY += GamePanel.TileSize;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 5){
        g2.drawString(">", textX-25, textY);
        if (gp.KeyH.enterPressed) {
            gp.gameState =GamePanel.playState;
            commandNumber = 0;
        }
    }


        //minimap checkbox
        textX = frameX + (int)(GamePanel.TileSize*4.5) + 95;
        textY = frameY + (int)(GamePanel.TileSize*1.5) + 24;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.map.miniMapOn) {
            g2.fillRect(textX, textY, 24, 24);
        }


        //music volume slider
        textX = frameX +  (int)(GamePanel.TileSize * 4.5);
        textY = frameY + (int)(GamePanel.TileSize*2.5) + 24;
        
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24*gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE volume slider
        textY +=GamePanel.TileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24*gp.SoundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);


        gp.config.saveConfig();
    }
    public void OptionsControl(int frameX, int frameY) {
        int textX;
        int textY;

        //title
        String Text = "Controls";
        textX = getXforCenteredText(Text);
        textY = frameY+GamePanel.TileSize;
        g2.drawString(Text, textX, textY);

        textX = frameX + GamePanel.TileSize;
        textY += GamePanel.TileSize;
        g2.drawString("Move", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Action/Attack", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Throw Bomb", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Character Screen", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Pause", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Options", textX, textY);

        textY += GamePanel.TileSize;
        g2.drawString("Map", textX, textY);

        textX = frameX + GamePanel.TileSize*6;
        textY = frameY + GamePanel.TileSize*2;
        g2.drawString("WASD", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("ENTER", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("F", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("C", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("P", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("ESC", textX, textY);

        textY +=GamePanel.TileSize;
        g2.drawString("M", textX, textY);

        //back
        textX = frameX + GamePanel.TileSize;
        textY = frameY + GamePanel.TileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNumber == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed){
                subState = 0;
                commandNumber = 3;
            }
        }

    }
    public void endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + GamePanel.TileSize;
        int textY = frameY + GamePanel.TileSize*2;

        currentDialogue = "Quit the game and return \nto the title screen?";

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += GamePanel.TileSize*4;
        g2.drawString(text, textX, textY);
        if (commandNumber== 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed) {
                gp.UI.commandNumber = -1;
                subState = 0;
                gp.gameState = GamePanel.titleState;
                gp.restartGame(true);
                gp.music.stop();
            }
        }

        //no
        text = "No";
        textY += GamePanel.TileSize;
        g2.drawString(text, textX, textY);
        if (commandNumber== 1) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed) {
                subState = 0;
                commandNumber = 3;
            }
        }

    }

    public void drawBombAmmo(int ammo){
        final int frameX = GamePanel.TileSize  *14;
        final int frameY = GamePanel.TileSize/2;
        final int frameWidth = GamePanel.TileSize + GamePanel.TileSize/2;
        final int frameHeight= GamePanel.TileSize + GamePanel.TileSize/2;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        g2.drawImage(bombImage, frameX+10, frameY+15, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        g2.drawString(""+ammo, frameX+frameWidth-27, frameY+frameHeight-10);
    }
    public void drawSubWindow(int x,int y,int width,int height) {
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }   
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        return slotCol + (slotRow *5);
    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return GamePanel.screenWidth/2-length/2;
    }
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }


}

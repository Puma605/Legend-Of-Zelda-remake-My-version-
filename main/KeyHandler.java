package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener{
    public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed,shotKeyPressed,spacePressed;
    public boolean checkDrawTime = false;

    GamePanel gp;

    public KeyHandler(GamePanel gp){
   this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //while in title state
        if (gp.gameState == GamePanel.titleState) 
        titleState(code);
        
        //while in playstate
        else if (gp.gameState == GamePanel.playState) 
        playState(code);
        
        //while in pausestate
        else if (gp.gameState == GamePanel.pauseState) 
        pauseState(code);

        //while in dialoguestate
        else if (gp.gameState == GamePanel.dialogueState || gp.gameState == GamePanel.cutSceneState) 
        dialogueState(code);
        
        //character state
        else if (gp.gameState == GamePanel.characterState) 
            characterState(code);

        //options state
        else if (gp.gameState == GamePanel.optionsState) 
        optionsState(code);

        //game over state
        else if (gp.gameState == GamePanel.gameOverState) 
        gameOverState(code);
        
        //trade state
        else if (gp.gameState == GamePanel.tradeState) 
        tradeState(code);

        //map state
        else if (gp.gameState == GamePanel.mapState) 
        mapState(code);
        
        
        
    }
    public void titleState(int code) {
        if (code==KeyEvent.VK_W) {
            gp.UI.commandNumber--;
            if (gp.UI.commandNumber<0) 
                gp.UI.commandNumber = 2;
            
            }
        
        if (code==KeyEvent.VK_S) {
        gp.UI.commandNumber++;
        if (gp.UI.commandNumber>2) 
            gp.UI.commandNumber = 0;
         
        }
        if (code==KeyEvent.VK_ENTER) {
            //new game
         if (gp.UI.commandNumber == 0) {
            if (gp.music.clip!= null) //check if there is music playing
                gp.stopMusic();
            
            gp.gameState = GamePanel.playState;
            gp.restartGame(true);
            gp.playMusic(0);
         }else if (gp.UI.commandNumber == 1) {//load game
            gp.saveLoad.load();
            gp.gameState = GamePanel.playState;
            gp.playMusic(0);
         }
         else if (gp.UI.commandNumber == 2) {//quit
            System.exit(0);
         }
        }
    }
    public void playState(int code) {
        
        if (code==KeyEvent.VK_W) 
            upPressed = true;
        
        if (code==KeyEvent.VK_S) 
            downPressed = true;
        
        if (code==KeyEvent.VK_A) 
            leftPressed = true;
        
        if (code==KeyEvent.VK_D) 
            rightPressed = true;
        
        if (code==KeyEvent.VK_P) 
            gp.gameState = GamePanel.pauseState;

        if (code==KeyEvent.VK_ENTER) 
            enterPressed = true;

        if (code==KeyEvent.VK_C) 
            gp.gameState = GamePanel.characterState;
        
        if (code==KeyEvent.VK_F) 
            shotKeyPressed = true;

        if (code==KeyEvent.VK_ESCAPE) 
            gp.gameState = GamePanel.optionsState;

        if (code==KeyEvent.VK_M) 
            gp.gameState = GamePanel.mapState;

        if (code==KeyEvent.VK_SPACE) 
            spacePressed = true;
         
        if (code==KeyEvent.VK_T) {
            if (!checkDrawTime){
                checkDrawTime = true;
                gp.tileM.drawPath = true;
            }
            else if (checkDrawTime){
                checkDrawTime = false;
                gp.tileM.drawPath = false;
            }
            
            
          }
    }

    public void pauseState(int code) {
        if (code==KeyEvent.VK_P) 
        gp.gameState = GamePanel.playState;
    }
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) 
        enterPressed = true;
    }
    public void characterState(int code) {
        if (code == KeyEvent.VK_C) 
        gp.gameState = GamePanel.playState;
        

        if (code == KeyEvent.VK_ENTER) 
            gp.player.selectItem();
        
        playerInventory(code);
    }
    public void optionsState(int code) {
       if (code== KeyEvent.VK_ESCAPE) 
         gp.gameState = GamePanel.playState;
        
       if (code== KeyEvent.VK_ENTER) 
         enterPressed = true;

         int maxCommandNum = 0;
         switch (gp.UI.subState) {
            case 0: maxCommandNum = 5; break;
            case 1:break;
            case 2:maxCommandNum = 1;break;
         }
        if (code== KeyEvent.VK_W) {
          gp.UI.commandNumber--;
          if (gp.UI.commandNumber < 0) 
            gp.UI.commandNumber = maxCommandNum;
        }
        if (code== KeyEvent.VK_S) {
            gp.UI.commandNumber++;
            if (gp.UI.commandNumber > maxCommandNum) 
              gp.UI.commandNumber = 0;
        }
        if (code== KeyEvent.VK_A) {
          if (gp.UI.subState == 0) {
             if (gp.UI.commandNumber == 1 && gp.music.volumeScale > 0){
                gp.music.volumeScale--;  
                gp.music.checkVolume();
             }
            if (gp.UI.commandNumber == 2 && gp.SoundEffect.volumeScale > 0)
               gp.SoundEffect.volumeScale--;  
        }
    }
        if (code== KeyEvent.VK_D) {
            if (gp.UI.subState == 0) {
                if (gp.UI.commandNumber == 1 && gp.music.volumeScale < 5){
                   gp.music.volumeScale++; 
                   gp.music.checkVolume(); 
                }
                if (gp.UI.commandNumber == 2 && gp.SoundEffect.volumeScale < 5)
                   gp.SoundEffect.volumeScale++;  
             }
            }   
    }
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.UI.commandNumber--;
            if (gp.UI.commandNumber< 0) 
              gp.UI.commandNumber = 1;    
        }
        if (code == KeyEvent.VK_S) {
            gp.UI.commandNumber++;
            if (gp.UI.commandNumber > 1) 
              gp.UI.commandNumber = 0;    
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.UI.commandNumber == 0) {
                gp.gameState = GamePanel.playState;
                gp.restartGame(false);
                gp.stopMusic();
                gp.playMusic(0);
            }else if (gp.UI.commandNumber == 1) {
                gp.gameState = GamePanel.titleState;
                gp.restartGame(true); 
            }
        }
    }
    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) 
            enterPressed = true;
        
        if (gp.UI.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.UI.commandNumber--;
                if (gp.UI.commandNumber<0) 
                    gp.UI.commandNumber = 2;     
            }
            if (code == KeyEvent.VK_S) {
                gp.UI.commandNumber++;
                if (gp.UI.commandNumber>2) 
                    gp.UI.commandNumber = 0;     
            }      
      } 
      if (gp.UI.subState == 1) {
        npcInventory(code);
        if (code == KeyEvent.VK_ESCAPE) {
            gp.UI.subState =0;
        }
      }
      if (gp.UI.subState == 2) {
        playerInventory(code);
        if (code == KeyEvent.VK_ESCAPE) {
            gp.UI.subState =0;
        }
      }
    }
    public void mapState(int code){
        if (code==KeyEvent.VK_M) 
        gp.gameState = GamePanel.playState;

        if (code==KeyEvent.VK_EQUALS) {
           if (gp.map.scale != 4) 
            gp.map.scale++;
        }
        if (code==KeyEvent.VK_MINUS) {
           if (gp.map.scale != 1) 
            gp.map.scale--;
        }

        if (code==KeyEvent.VK_W){
            if (gp.map.yOffset < 350) 
                gp.map.yOffset+=5; 
        }
    
        if (code==KeyEvent.VK_S){
            if (gp.map.yOffset > -350)    
                gp.map.yOffset-=5;
        }
    
        if (code==KeyEvent.VK_A){
            if (gp.map.xOffset < 350) 
                gp.map.xOffset+=5;
        }
    
        if (code==KeyEvent.VK_D){
            if (gp.map.xOffset > -350) 
                gp.map.xOffset-=5;
        }
        
    }




    public void playerInventory(int code) {
    if (code == KeyEvent.VK_W) {
            if (gp.UI.playerSlotRow != 0) 
              gp.UI.playerSlotRow--;  
            
            
        }
        if (code == KeyEvent.VK_A) {
            if (gp.UI.playerSlotCol != 0) 
                gp.UI.playerSlotCol--;
            
            
        }
        if (code == KeyEvent.VK_S) {
            if (gp.UI.playerSlotRow != 3) 
               gp.UI.playerSlotRow++; 
            
        }
        if (code == KeyEvent.VK_D) {
            if (gp.UI.playerSlotCol != 4) 
            gp.UI.playerSlotCol++;
            
        }    
    }
    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
                if (gp.UI.npcSlotRow != 0) 
                  gp.UI.npcSlotRow--;  
                
                
            }
            if (code == KeyEvent.VK_A) {
                if (gp.UI.npcSlotCol != 0) 
                    gp.UI.npcSlotCol--;
                
                
            }
            if (code == KeyEvent.VK_S) {
                if (gp.UI.npcSlotRow != 3) 
                   gp.UI.npcSlotRow++; 
                
            }
            if (code == KeyEvent.VK_D) {
                if (gp.UI.npcSlotCol != 4) 
                gp.UI.npcSlotCol++;
                
            }    
        }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code==KeyEvent.VK_W) 
            upPressed = false;
        
        if (code==KeyEvent.VK_S) 
            downPressed = false;
        
        if (code==KeyEvent.VK_A) 
            leftPressed = false;
        
        if (code==KeyEvent.VK_D) 
            rightPressed = false;

        if (code==KeyEvent.VK_F) 
            shotKeyPressed = false;

        if (code==KeyEvent.VK_SPACE) 
            spacePressed = false;
        
        if (code==KeyEvent.VK_ENTER) 
            enterPressed = false;
        
    }



    
}

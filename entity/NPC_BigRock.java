package entity;


import java.util.ArrayList;

import interactiveTile.IT_MetalPlate;
import interactiveTile.interactiveTile;
import main.GamePanel;
import object.OBJ_IronDoor;

public class NPC_BigRock extends Entity{

    public final static String NPC_NAME = "Big Rock";
    public NPC_BigRock(GamePanel gp) {
        super(gp);

        solidArea.setBounds(2, 6, 44, 40);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        name = NPC_NAME;

        dialogueSet = -1;
        direction = "down";
        speed = 4;
        getImage();
        setDialogue();
    }
    public void getImage() {
        up1 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/npc/InteractiveRock",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void setDialogue() {
        Dialogues[0][0] = "Its a big rock.";
    }
    @Override
    public void update(){

    }
    public void talk() {
       facePlayer();
       startDialogue(this, dialogueSet);

       dialogueSet++;

       if(Dialogues[dialogueSet][0] == null)
        dialogueSet--;   
    }
    public void detectPlate(){
        ArrayList<interactiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();
        //create plate list
        for (int i = 0; i< gp.iTile[1].length; i++) {
            if (gp.iTile[gp.currentMap][i]!=null &&
            gp.iTile[gp.currentMap][i].name != null &&
            gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.IT_NAME)) 
                plateList.add(gp.iTile[gp.currentMap][i]);  
        }
        //create rocklist
        for (int i = 0; i< gp.npc[1].length; i++) {
            if (gp.npc[gp.currentMap][i]!=null &&
            gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.NPC_NAME)) 
                rockList.add(gp.npc[gp.currentMap][i]);  
        }

        
        int counter = 0;
        //scan plate list
        for (int i = 0; i < plateList.size(); i++) {
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8) {
                if (linkedEntity == null) {
                linkedEntity = plateList.get(i);
                gp.playSoundEffect(3); 
                }
            }else{
                if (linkedEntity == plateList.get(i)) 
                linkedEntity = null;
            }
        }
        //scan rock list
        for (int i = 0; i < rockList.size(); i++) {
            if (rockList.get(i).linkedEntity != null) 
                counter++;
        }

        if (counter == rockList.size()) {
            for (int index = 0; index < gp.obj[1].length; index++) {
                if (gp.obj[gp.currentMap][index] != null &&
                gp.obj[gp.currentMap][index].name.equals(OBJ_IronDoor.OBJ_NAME)) {
                    gp.obj[gp.currentMap][index] = null;
                    gp.playSoundEffect(18);
                }
            }
        }

    }

    @Override
    public void move(String direction){
        this.direction = direction;
        checkCollision();
        if (!collisionOn) {
            switch (direction) {
                case "up":worldY-=speed;break;
                case "down":worldY+=speed;break;
                case "left":worldX-=speed;break;
                case "right":worldX+=speed;break;
            }
        }
        detectPlate();
    }
}

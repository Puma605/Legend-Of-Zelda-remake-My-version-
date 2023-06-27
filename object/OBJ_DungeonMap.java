package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_DungeonMap extends Entity {
    GamePanel gp;
    
    public static final String OBJ_NAME = "Dungeon Map";
    public static boolean USED = false;
    public OBJ_DungeonMap(GamePanel gp) {
            super(gp);
            this.gp = gp;
    
    
            price = 1000;
            type = typeConsumable;
            name = OBJ_NAME;
            value = 3;
            down1 = setup("/objects/DungeonMap01",GamePanel.TileSize,GamePanel.TileSize);
            description ="["+ name +"]"+ "\nAn old map";
            stackable = false;
    
            setDialogue();
        }
    
        public void setDialogue(){
            Dialogues[0][0] = "You used the " + name + "!\nYou now know the dungeons layout.";
        }
        public boolean use(Entity entity) {
            startDialogue(this, 0);
            USED = true;
            return true;
        }
        
    }
    


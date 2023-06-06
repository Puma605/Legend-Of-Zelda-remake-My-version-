package interactiveTile;

import entity.Entity;
import main.GamePanel;

public class interactiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public interactiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        return isCorrectItem;
    }
    public void playSE() {
        
    }
    public interactiveTile getDestroyedForm() {
        return null;
    }

    public void update() {
        
    }

    

    
    
}

package interactiveTile;

import entity.Entity;
import main.GamePanel;
import java.awt.Color;

public class IT_DryTree extends interactiveTile {

    GamePanel gp;
    public IT_DryTree(GamePanel gp,int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = GamePanel.TileSize * col;
        this.worldY = GamePanel.TileSize * row;

        down1 = setup("/interactiveTiles/DryBush01", GamePanel.TileSize, GamePanel.TileSize);
        destructible = true;
    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.name == "Explosion") 
            isCorrectItem = true;
        

        return isCorrectItem;
    }
    public void playSE() {
        gp.playSoundEffect(7);
    }
    public interactiveTile getDestroyedForm() {
        interactiveTile tile  = new IT_BushStump(gp, worldX/GamePanel.TileSize, worldY/GamePanel.TileSize);
        return tile;
    }

    public Color getParticleColor(){
        return new Color(65,50,30);
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }
    
    
}

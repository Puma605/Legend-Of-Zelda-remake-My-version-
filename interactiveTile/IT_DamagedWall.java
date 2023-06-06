package interactiveTile;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Bomb;

import java.awt.Color;
import java.util.Random;

public class IT_DamagedWall extends interactiveTile {
    GamePanel gp;
    public IT_DamagedWall(GamePanel gp,int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = GamePanel.TileSize * col;
        this.worldY = GamePanel.TileSize * row;

        down1 = setup("/interactiveTiles/DamagedBrickWall", GamePanel.TileSize, GamePanel.TileSize);
        destructible = true;
    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.name == "Explosion") 
            isCorrectItem = true;
        

        return isCorrectItem;
    }
    public void playSE() {
        gp.playSoundEffect(19);
    }
    public interactiveTile getDestroyedForm() {
        interactiveTile tile  = null;
        return tile;
    }

    public Color getParticleColor(){
        return new Color(65,65,65);
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
    public void checkDrop() {
        int i = new Random().nextInt(100)+1;
        if (i < 20) 
            dropItem(new OBJ_Bomb(gp));
    }
}

package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
    public static final String NPCNAME = "Dummy";
    public PlayerDummy(GamePanel gp) {
        super(gp);

        name = NPCNAME;
        getImage();
    }
    public void getImage() {
        up1 = setup("/player/Up1",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/player/Up2",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/player/Down1",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/player/Down2",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/player/Left1",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/player/Left2",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/player/Right1",GamePanel.TileSize, GamePanel.TileSize);
        right2 = setup("/player/Right2",GamePanel.TileSize,GamePanel.TileSize);
    }
    
}

package interactiveTile;

import main.GamePanel;

public class IT_MetalPlate extends interactiveTile{

    GamePanel gp;
    public final static String IT_NAME = "Metal Plate";
    public IT_MetalPlate(GamePanel gp,int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = GamePanel.TileSize * col;
        this.worldY = GamePanel.TileSize * row;
        name = IT_NAME;

        down1 = setup("/interactiveTiles/MetalPlate", GamePanel.TileSize, GamePanel.TileSize);

        solidArea.setBounds(0, 0, 0, 0);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
}

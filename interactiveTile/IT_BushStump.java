package interactiveTile;


import main.GamePanel;

public class IT_BushStump extends interactiveTile {
    

    GamePanel gp;
    public IT_BushStump(GamePanel gp,int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = GamePanel.TileSize * col;
        this.worldY = GamePanel.TileSize * row;
        name = "Bush Stump";

        down1 = setup("/interactiveTiles/BushStump", GamePanel.TileSize, GamePanel.TileSize);

        solidArea.setBounds(0, 0, 0, 0);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

}

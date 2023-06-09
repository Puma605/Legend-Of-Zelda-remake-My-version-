package tile;

import main.GamePanel;
import object.OBJ_DungeonMap;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;

public class Map extends TileManager {

    GamePanel gp;
    BufferedImage WorldMap[];
    public boolean miniMapOn;
    public int scale = 1;
    public int xOffset = 0;
    public int yOffset = 1;

    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }
    public void createWorldMap() {
        WorldMap = new BufferedImage[GamePanel.maxMap];
        int worldMapWidth = GamePanel.TileSize * GamePanel.MaxWorldCol;
        int worldMapHeight = GamePanel.TileSize * GamePanel.MaxWorldRow;

        for (int i = 0; i < GamePanel.maxMap; i++) {
            WorldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)WorldMap[i].getGraphics();
            

            int col = 0;
            int row = 0;
            while (col < GamePanel.MaxWorldCol && row < GamePanel.MaxWorldRow) {
                int tileNum = mapTileNum[i][col][row];
                int x = GamePanel.TileSize * col;
                int y = GamePanel.TileSize * row;
                g2.drawImage(tile[tileNum].image, x, y,null);

                col++;
                if (col == GamePanel.MaxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2){
        if (gp.currentArea == GamePanel.dungeonArea && !OBJ_DungeonMap.USED) {
        }else{
        g2.setColor(Color.black);
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

        //drawMap
        int width = 550 * this.scale;
        int height = 550 * this.scale;
        int x = GamePanel.screenWidth/2 - width/2 +xOffset;
        int y = GamePanel.screenHeight/2 - height/2 + yOffset;
        g2.drawImage(WorldMap[gp.currentMap], x, y, width,height,null);

        //draw player
        double scale = (double)(GamePanel.TileSize* GamePanel.MaxWorldCol)/width;
        int playerX = (int)(x + gp.player.worldX/scale);
        int playerY = (int)(y + gp.player.worldY/scale);
        int playerSize = (int)(GamePanel.TileSize/3)*this.scale;
        g2.drawImage(gp.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);

        //hint
        g2.setFont(gp.UI.maruMonica.deriveFont(32f));
        g2.setColor(Color.WHITE);
        g2.drawString("Press + to zoom in", 515, 490);
        g2.drawString("Press - to zooom out", 515, 520);
        g2.drawString("Press M to close", 515, 550); 
        }
        
    }

    public void drawMiniMap(Graphics2D g2) {
        if (miniMapOn) {
            if (gp.currentArea == GamePanel.dungeonArea && !OBJ_DungeonMap.USED) {  
            }else{
            int width = 200;
            int height = 200;
            int x = GamePanel.screenWidth-width;
            int y = GamePanel.screenHeight-height; 

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8F));
            g2.drawImage(WorldMap[gp.currentMap], x, y, width, height, null);


        //draw player
        double scale = (double)(GamePanel.TileSize* GamePanel.MaxWorldCol)/width;
        int playerX = (int)(x + gp.player.worldX/scale);
        int playerY = (int)(y + gp.player.worldY/scale);
        int playerSize = (int)(GamePanel.TileSize/3);
        g2.drawImage(gp.player.down1, playerX-6, playerY-6, playerSize, playerSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
            }
        }
    }
}

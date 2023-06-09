package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

  GamePanel gp;
  public Tile[] tile;
  public int mapTileNum[][][];
  public boolean drawPath = false;

  public TileManager(GamePanel gp){
  this.gp = gp;
   tile = new Tile[90];
   mapTileNum = new int[GamePanel.maxMap][GamePanel.MaxWorldCol][GamePanel.MaxWorldRow];

   getTileImage();
   loadMap("/maps/World02.txt",0);
   loadMap("/maps/House01Interior.txt",1);
   loadMap("/maps/dungeon01.txt",2);
   loadMap("/maps/dungeon02.txt",3);
   
  }  
  public void getTileImage() {
     

        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "Wall02", true);
        setup(41, "Bush01", true);
        setup(42, "HouseLeft2", true);
        setup(43, "HouseLeft1", true);
        setup(44, "HouseMiddle", true);
        setup(45, "HouseRight1", false);
        setup(46, "HouseRight2", true);
        setup(47, "HouseLeft2R1", true);
        setup(48, "HouseLeft1R1", true);
        setup(49, "HouseMiddleR1", true);
        setup(50, "HouseRight1R1", true);
        setup(51, "HouseRight2R1", true);
        setup(52, "HouseLeft2R2", true);
        setup(53, "HouseLeft1R2", true);
        setup(54, "HouseMiddleR2", true);
        setup(55, "HouseRight1R2", true);
        setup(56, "HouseRight2R2", true);
        setup(57, "HouseLeft2R3", true);
        setup(58, "HouseLeft1R3", true);
        setup(59, "HouseMiddleR3", true);
        setup(60, "HouseRight1R3", true);
        setup(61, "HouseRight2R3", true);
        setup(62, "HouseLeft2R4", true);
        setup(63, "HouseLeft1R4", true);
        setup(64, "HouseMiddleR4", true);
        setup(65, "HouseRight1R4", true);
        setup(66, "HouseRight2R4", true);
        setup(67, "Empty", true);
        setup(68, "Floor01", false);
        setup(69, "BedTop01", true);
        setup(70, "BedBottom01", true);
        setup(71, "Drawers01", true);
        setup(72, "Stool01", true);
        setup(73, "Table01", true);
        setup(74, "HouseExit", false);
        setup(75, "House01TopWall", true);
        setup(76, "House01BottomWall", true);
        setup(77, "House01LeftWall", true);
        setup(78, "House01RightWall", true);
        setup(79, "House01TopLeftWallCorner", true);
        setup(80, "House01TopRightWallCorner", true);
        setup(81, "House01BottomLeftWallCorner", true);
        setup(82, "House01BottomRightWallCorner", true);
        setup(83, "House01TopLeftCornerConnect", true);
        setup(84, "House01TopRightCornerConnect", true);
        setup(85, "House01BottomLeftCornerConnect", true);
        setup(86, "House01BottomRightCornerConnect", true);
        setup(87, "BrickWall", true);
        setup(88, "StairsDown", false);
        setup(89, "StairsUp", false);

  } 
  public void setup(int index, String imagePath, boolean collision) {
    UtilityTool uTool = new UtilityTool();

    try {
      tile[index] = new Tile();
      tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imagePath+".png"));
      tile[index].image = uTool.scaleImage(tile[index].image, GamePanel.TileSize, GamePanel.TileSize);
      tile[index].collision = collision;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void loadMap(String path, int map) {
    
    try {
    InputStream is = getClass().getResourceAsStream(path);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
    int col = 0;
    int row = 0;
    while (col<GamePanel.MaxWorldCol  &&  row<GamePanel.MaxWorldRow) {
            String line = br.readLine();
        
            while (col<GamePanel.MaxWorldCol) {
                String numbers[] = line.split(" ");
                
                int number = Integer.parseInt(numbers[col]);

                mapTileNum[map][col][row] = number;
                col++;
                
            }

            if (col==GamePanel.MaxWorldCol) {
              col = 0;
              row++;
          }
            
    }
    br.close();
     } catch (Exception e) {

    e.printStackTrace();
   }
   
  }
  public void draw(Graphics2D g2) {
    int worldCol = 0;
    int worldRow =0;

   while (worldCol<GamePanel.MaxWorldCol&&worldRow< GamePanel.MaxWorldRow) {
    int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

    int worldX = worldCol*GamePanel.TileSize;
    int worldY = worldRow*GamePanel.TileSize;

    int screenX = worldX-gp.player.worldX+gp.player.screenX;
    int screenY = worldY-gp.player.worldY+gp.player.screenY;

    if (worldX + GamePanel.TileSize > gp.player.worldX - gp.player.screenX&&
      worldX - GamePanel.TileSize < gp.player.worldX + gp.player.screenX&&
      worldY + GamePanel.TileSize > gp.player.worldY - gp.player.screenY&&
      worldY - GamePanel.TileSize < gp.player.worldY + gp.player.screenY) {
        
      g2.drawImage(tile[tileNum].image, screenX, screenY,null);
    }
    

    worldCol++;


    if (worldCol==GamePanel.MaxWorldCol) {
      worldCol = 0;

      worldRow++;
    }
   }

   if (drawPath) {
    g2.setColor(new Color(255,0,0,70));
    for (int i = 0; i <gp.PF.pathList.size(); i++) {
      int worldX = gp.PF.pathList.get(i).col * GamePanel.TileSize;
      int worldY = gp.PF.pathList.get(i).row * GamePanel.TileSize;
  
      int screenX = worldX-gp.player.worldX+gp.player.screenX;
      int screenY = worldY-gp.player.worldY+gp.player.screenY;

      g2.fillRect(screenX, screenY, GamePanel.TileSize, GamePanel.TileSize);
    }
   }
  }
}

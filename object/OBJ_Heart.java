package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public static final String OBJ_NAME = "Heart";
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = typePickUpOnly;
        value = 2;
        name = OBJ_NAME;

        down1 = setup("/objects/FullHeart",GamePanel.TileSize,GamePanel.TileSize);
        image = setup("/objects/FullHeart",GamePanel.TileSize,GamePanel.TileSize);
        image1 = setup("/objects/HalfHeart",GamePanel.TileSize,GamePanel.TileSize);
        image2 = setup("/objects/EmptyHeart",GamePanel.TileSize,GamePanel.TileSize);

    }
    public boolean use(Entity entity) {
        gp.playSoundEffect(2);
        entity.life += value;
        return true;
    }

}

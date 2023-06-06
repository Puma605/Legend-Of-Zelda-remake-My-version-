package entity;

import main.GamePanel;
import object.OBJ_BlueShield;
import object.OBJ_Bomb;
import object.OBJ_HealthPotion;
import object.OBJ_IronSword;
import object.OBJ_Key;
import object.OBJ_WoodShield;

public class NPC_RuebsMerchant extends Entity {
    public NPC_RuebsMerchant(GamePanel gp) {
        super(gp);
        name = "Ruebs Merchant";
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
        setItems();
    }
    public void getImage() {
        up1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        up2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        down1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        down2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        left1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        left2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        right1 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
        right2 = setup("/npc/RuebsMerchant",GamePanel.TileSize,GamePanel.TileSize);
    }
    public void setDialogue() {
        Dialogues[0][0] = "If you want I can sell you some stuff...\nI might just buy whatever you have\nif its worth my time.";
        Dialogues[1][0] = "Be sure to come again...";
        Dialogues[2][0] = "You need more coins!";
        Dialogues[3][0] = "Your inventory is full!";
        Dialogues[4][0] = "You cannot sell an equiped item!";
        Dialogues[5][0] = "You cannot sell this item!";
    }
    public void setItems() {
        inventory.add(new OBJ_HealthPotion(gp));
        inventory.add(new OBJ_WoodShield(gp));
        inventory.add(new OBJ_BlueShield(gp));
        inventory.add(new OBJ_IronSword(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Bomb(gp));
    }
    @Override
    public void talk() {
        facePlayer();
        gp.gameState = GamePanel.tradeState;
        gp.UI.npc = this;
        
    }

}

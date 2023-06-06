package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
    GamePanel gp;
    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }
    public Entity getObject(String itemName){
        Entity obj = null;
  
        switch (itemName) {
          case OBJ_WoodShield.OBJ_NAME:obj = new OBJ_WoodShield(gp); break;
          case OBJ_Bomb.OBJ_NAME:obj = new OBJ_Bomb(gp); break;
          case OBJ_FireBall.OBJ_NAME:obj = new OBJ_FireBall(gp);
          case OBJ_Rock.OBJ_NAME:obj = new OBJ_Rock(gp);
          case OBJ_Lantern.OBJ_NAME:obj = new OBJ_Lantern(gp); break;
          case OBJ_BlueShield.OBJ_NAME:obj = new OBJ_BlueShield(gp); break;
          case OBJ_Boots.OBJ_NAME:obj = new OBJ_Boots(gp); break;
          case OBJ_HealthPotion.OBJ_NAME:obj = new OBJ_HealthPotion(gp); break;
          case OBJ_IronSword.OBJ_NAME:obj = new OBJ_IronSword(gp); break;
          case OBJ_MasterSword.OBJ_NAME:obj = new OBJ_MasterSword(gp); break;
          case OBJ_IronDoor.OBJ_NAME:obj = new OBJ_IronDoor(gp); break;
          case OBJ_Key.OBJ_NAME:obj = new OBJ_Key(gp); break;
          case OBJ_Chest.OBJ_NAME:obj = new OBJ_Chest(gp); break;
          case OBJ_Bed.OBJ_NAME:obj = new OBJ_Bed(gp); break;
          case OBJ_Door.OBJ_NAME:obj = new OBJ_Door(gp); break;
          case OBJ_RockPodium.OBJ_NAME:obj = new OBJ_RockPodium(gp); break;
          case OBJ_GoldCoin.OBJ_NAME:obj = new OBJ_GoldCoin(gp); break;
          case OBJ_Heart.OBJ_NAME:obj = new OBJ_Heart(gp); break;
          case OBJ_DungeonMap.OBJ_NAME:obj = new OBJ_DungeonMap(gp); break;
        }
        return obj;
      }
}

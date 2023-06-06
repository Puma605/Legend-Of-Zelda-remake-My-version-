package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import main.GamePanel;

public class SaveLoad {
    GamePanel gp;
    public SaveLoad(GamePanel gp){
        this.gp = gp;
    }
    public void save(){
      try {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

        DataStorage ds = new DataStorage();

        //PLAYER STATS
        ds.level = gp.player.level;
        ds.maxLife = gp.player.maxLife;
        ds.life = gp.player.life;
        ds.ammo = gp.player.ammo;
        ds.strength = gp.player.strength;
        ds.dexterity = gp.player.dexterity;
        ds.attack = gp.player.attack;
        ds.defense = gp.player.defense;
        ds.coin = gp.player.coin;
        ds.exp = gp.player.exp;
        ds.nextLevelExp = gp.player.nextLevelExp;


        //PLAYER INVENTORY
        for (int i = 0; i < gp.player.inventory.size(); i++) {
          ds.itemNames.add(gp.player.inventory.get(i).name);
          ds.itemQuantity.add(gp.player.inventory.get(i).amount);
        }
        ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
        ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

        
        //OBJECTS ON MAP
        ds.mapObjectNames = new String[GamePanel.maxMap][gp.obj[1].length];
        ds.mapObjectWorldX = new int[GamePanel.maxMap][gp.obj[1].length];
        ds.mapObjectWorldY = new int[GamePanel.maxMap][gp.obj[1].length];
        ds.mapObjectLootNames = new String[GamePanel.maxMap][gp.obj[1].length];
        ds.objectOpened = new boolean[GamePanel.maxMap][gp.obj[1].length];

        for (int mapNum = 0; mapNum < GamePanel.maxMap; mapNum++) {
          for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[mapNum][i]==null) 
              ds.mapObjectNames[mapNum][i] = "NA";
            else{
              ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
              ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
              ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
              if (gp.obj[mapNum][i].Loot!=null) 
              ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].Loot.name;
              ds.objectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
            }
          }
        }

        //write the data
        oos.writeObject(ds);
      }catch (IOException e) {
        System.out.println("Save Exception");
      }

    }
    
    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.level = ds.level;
            gp.player.maxLife = ds.life;
            gp.player.life = ds.life;
            gp.player.ammo = ds.ammo;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.attack = ds.attack;
            gp.player.defense = gp.player.defense;
            gp.player.coin = ds.coin;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;


            //PLAYER INVENTORY
            gp.player.inventory.clear();
            for (int i = 0; i < ds.itemNames.size(); i++) {
              gp.player.inventory.add(gp.EG.getObject(ds.itemNames.get(i)));
              gp.player.inventory.get(i).amount = ds.itemQuantity.get(i);
            }
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();


            //MAP OBJECTS
            for (int mapNum = 0; mapNum < GamePanel.maxMap; mapNum++) {
              for (int i = 0; i < gp.obj[1].length; i++) {
                if (ds.mapObjectNames[mapNum][i].equals("NA")) 
                  gp.obj[mapNum][i] = null;
                else{
                  gp.obj[mapNum][i] = gp.EG.getObject(ds.mapObjectNames[mapNum][i]);
                  gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                  gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                  if(ds.mapObjectLootNames[mapNum][i] != null)
                  gp.obj[mapNum][i].Loot = gp.EG.getObject(ds.mapObjectLootNames[mapNum][i]);
                  gp.obj[mapNum][i].opened = ds.objectOpened[mapNum][i];
                  if (gp.obj[mapNum][i].opened) 
                  gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                  
                }
              }
            }
        } catch (Exception e) {
          System.out.println(e);
            System.out.println("Load Exception");
        }
    }
}

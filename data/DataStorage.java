package data;

import java.io.Serializable;
import java.util.ArrayList;



public class DataStorage implements Serializable {
    //player stats
    int maxLife;
    int life;
    int level;
    int ammo;
    int strength;
    int dexterity;
    int attack;
    int defense;
    int exp;
    int nextLevelExp;
    int coin;

    //player items
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemQuantity = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;


    //quests
    boolean OM_QuestOneComplete;
    boolean OM_QuestTwoComplete;
    boolean Ruebs_QuestOneComplete;
    boolean TR_QuestOneComplete;
    boolean SLDEAD;
    String CurrentQuestName;
    boolean[] ObjectivesComplete = new boolean[10];

    //map objects
    String mapObjectNames[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootNames[][];
    boolean objectOpened[][];

}

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import AI.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import enviroment.EnviromentManager;
import interactiveTile.interactiveTile;
import tile.Map;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    static final int originalTileSize = 16;
    static final int scale = 3;
    public static final int TileSize = originalTileSize*scale;

    public static final int MaxScreenCol = 16;
    public static final int MaxScreenRow = 12;
    public static final int screenWidth = TileSize*MaxScreenCol;//768
    public static final int screenHeight = TileSize*MaxScreenRow;//576

    public static final int MaxWorldCol = 50;
    public static final int MaxWorldRow = 50;

    public static final int maxMap = 10;
    public int currentMap = 0;

    int FPS = 60;

    //System
    public TileManager tileM = new TileManager(this);
    public KeyHandler KeyH =new KeyHandler(this);
    Sound music = new Sound();
    Sound SoundEffect = new Sound();
    public PathFinder PF = new PathFinder(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetPlacer aPlacer = new AssetPlacer(this);
    public UI UI = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    EnviromentManager eManager = new EnviromentManager(this);
    Map map = new Map(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread;
    public EntityGenerator EG = new EntityGenerator(this);
    CutSceneManager CSManager = new CutSceneManager(this);

    //entity
    public Player player = new Player(this, KeyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public interactiveTile iTile[][] = new interactiveTile[maxMap][50];
    public ArrayList<Entity> projectile = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //states
    public int gameState;
    public static final int titleState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int dialogueState = 3;
    public static final int characterState = 4;
    public static final int optionsState = 5;
    public static final int gameOverState = 6;
    public static final int transitionState = 7;
    public static final int tradeState = 8;
    public static final int sleepState = 9;
    public static final int mapState = 10;
    public static final int cutSceneState = 11;

    public boolean bossBattleOn =false;
    //areas
    public int currentArea;
    public int nextArea;
    public static final int overWorldArea = 0;
    public static final int dungeonArea = 1;
    public static final int houseArea = 2;


    int playerX =100;
    int playerY =100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        aPlacer.setObject();
        aPlacer.setNpc();
        aPlacer.setMonster();
        aPlacer.setInteractiveTiles();
        eManager.setup();
        gameState = titleState;
        currentArea = overWorldArea;
    }

    public void restartGame(boolean restart){
        currentArea = overWorldArea;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions();
        player.restoreStatus();
        aPlacer.setNpc();
        aPlacer.setMonster();

        if (restart) {
        player.setDefaultValues();
        player.setItems();
        aPlacer.setObject();
        aPlacer.setInteractiveTiles(); 
        eManager.lighting.resetDay(); 
        }
        
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawIntervals = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
             delta += (currentTime - lastTime)/ drawIntervals;
             lastTime = currentTime;

        if (delta>= 1) {
            update();
            repaint();
            delta--;
         }
            
        }
        
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i]!=null) 
                    npc[currentMap][i].update();
                
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i]!=null){
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) 
                        monster[currentMap][i].update();
                    
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                    
                }
            }

            for (int i = 0; i < projectile.size(); i++) {
                    if (projectile.get(i)!=null){
                        if (projectile.get(i).alive&& !projectile.get(i).dying) 
                            projectile.get(i).update();
                        
                        if(!projectile.get(i).alive){
                            projectile.remove(i);
                        }
                    }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i)!=null){
                    if (particleList.get(i).alive) 
                    particleList.get(i).update();
                    
                    if(!particleList.get(i).alive)
                        particleList.remove(i);
                    
                }
        }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) 
                    iTile[currentMap][i].update();
                
            }
        
        eManager.update();
        }else if (gameState == pauseState) {
            
        }
       
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


        long drawStart = 0;
        if (KeyH.checkDrawTime){
           drawStart = System.nanoTime(); }
        
        
           //title
           if (gameState == titleState) {
            UI.draw(g2);
           }else if(gameState == mapState){
           map.drawFullMapScreen(g2);

           }else{
        tileM.draw(g2);

        for (int i = 0; i < iTile[1].length; i++) {
            if (iTile[currentMap][i] != null) 
               iTile[currentMap][i].draw(g2);    
        }
        
        //entities
        entityList.add(player);

        //npc
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i]!=null) 
                entityList.add(npc[currentMap][i]);   
        }

        //obj
        for (int i = 0; i < obj[1].length; i++) {
            if (obj[currentMap][i]!= null) 
                entityList.add(obj[currentMap][i]);
        }
        //monster
        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i]!= null) 
                entityList.add(monster[currentMap][i]);
        }

        for (int i = 0; i < projectile.size(); i++) {
            if (projectile.get(i)!= null) 
                entityList.add(projectile.get(i));
        }
        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i)!= null) 
                entityList.add(particleList.get(i));
        }
        
        //sort
        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            }
            
        });

        //draw entities
        for (int i = 0; i < entityList.size(); i++) 
            entityList.get(i).draw(g2);
        
        entityList.clear();


        eManager.draw(g2);

        map.drawMiniMap(g2);

        UI.draw(g2);

        CSManager.draw(g2);

        
           }
        

        if (KeyH.checkDrawTime) {
         long drawEnd = System.nanoTime();
         long passedTime = drawEnd-drawStart;
         g2.setColor(Color.white);
         g2.drawString("Draw Time:" + passedTime, 10,400);
         System.out.println("Draw Time:" + passedTime);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int i) {
        SoundEffect.setFile(i);
        SoundEffect.play();
    }
    public void changeArea(){
        if(nextArea != currentArea){
            stopMusic();
            if (nextArea == overWorldArea) 
                playMusic(0);
            
            if (nextArea == houseArea) 
                playMusic(17);
            
            if (nextArea == dungeonArea) 
                playMusic(16);
            
        }
        currentArea = nextArea;
        aPlacer.setMonster();
    }
    public void removeTempEntity(){
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].tempENTITY) 
                    obj[mapNum][i] = null;
                
            }
        }
    }
}

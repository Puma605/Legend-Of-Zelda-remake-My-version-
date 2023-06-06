package enviroment;

import main.GamePanel;
import java.awt.Graphics2D;

public class EnviromentManager {
    GamePanel gp;
    public Lighting lighting;

    public EnviromentManager(GamePanel gp){
     this.gp = gp;
    }
    public void setup() {
        lighting = new Lighting(gp);
    }
    public void update(){
        lighting.update();
    }
    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
    
}

package enviroment;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.AlphaComposite;

public class Lighting {
    GamePanel gp;
    BufferedImage DarknessFilter;
    int dayCounter;
    public float filterAlpha = 0f;
    public final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp){
        this.gp = gp;
        setLightSource();
    }
    public void resetDay(){
      dayState =  day;
      filterAlpha = 0f;
    }
    public void setLightSource() {
        DarknessFilter = new BufferedImage(GamePanel.screenWidth,GamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)DarknessFilter.getGraphics();


        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0,0,0,0.95f));
        }else{
        //center of circle
        int centerX = gp.player.screenX + GamePanel.TileSize/2;
        int centerY = gp.player.screenY + GamePanel.TileSize/2;

        // Create a gradation effect
		Color color[] = new Color[12];
		float fraction[] = new float[12];
		
		color[0] = new Color(0,0,0,0.1f);
		color[1] = new Color(0,0,0,0.42f);
		color[2] = new Color(0,0,0,0.52f);
		color[3] = new Color(0,0,0,0.61f);
		color[4] = new Color(0,0,0,0.69f);
		color[5] = new Color(0,0,0,0.76f);
		color[6] = new Color(0,0,0,0.82f);
		color[7] = new Color(0,0,0,0.87f);
		color[8] = new Color(0,0,0,0.91f);
		color[9] = new Color(0,0,0,0.94f);
		color[10] = new Color(0,0,0,0.96f);
		color[11] = new Color(0,0,0,0.98f);
		
		fraction[0] = 0f;
		fraction[1] = 0.4f;
		fraction[2] = 0.5f;
		fraction[3] = 0.6f;
		fraction[4] = 0.65f;
		fraction[5] = 0.7f;
		fraction[6] = 0.75f;
		fraction[7] = 0.8f;
		fraction[8] = 0.85f;
		fraction[9] = 0.9f;
		fraction[10] = 0.95f;
		fraction[11] = 1f;
		
		// Create a gradation paint settings
		RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (gp.player.currentLight.lightRadius/2), fraction, color);
		
		// Set the gradient data on g2
		g2.setPaint(gPaint);
        }


        g2.fillRect(0,0,GamePanel.screenWidth,GamePanel.screenHeight);
        g2.dispose();
    }
    public void update(){
        if (gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }

        //check the state of the day
        if (dayState == day) {
            dayCounter++;
            if (dayCounter > 9000) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if (dayState == dusk) {
            filterAlpha += 0.0001f;
            if (filterAlpha > 1f) {
                filterAlpha = 1;
                dayState = night;
            }
        }
        if (dayState == night) {
            dayCounter++;
            if (dayCounter > 9000) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if (dayState == dawn) {
            filterAlpha -= 0.0001f;

            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = day; 
            }
        }
    }
    public void draw(Graphics2D g2) {
        if (gp.currentArea==GamePanel.overWorldArea) 
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha)); 
        if (gp.currentArea==GamePanel.dungeonArea || gp.currentArea == GamePanel.overWorldArea) 
            g2.drawImage(DarknessFilter, 0, 0, null);
        
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}

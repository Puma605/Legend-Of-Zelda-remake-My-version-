package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    GamePanel gp;
    public Config(GamePanel gp){
      this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //minimap on or off
            bw.write(String.valueOf(gp.map.miniMapOn));
            bw.newLine();

            //music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //se volume
            bw.write(String.valueOf(gp.SoundEffect.volumeScale));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String tempString = br.readLine();

            //minimap on or off
            if (tempString.equalsIgnoreCase("true")) 
                gp.map.miniMapOn = true;
            else
                gp.map.miniMapOn = false; 

            tempString = br.readLine();
            

            //music volume
            gp.music.volumeScale = Integer.parseInt(tempString);
            tempString = br.readLine();

            //se volume
            gp.SoundEffect.volumeScale = Integer.parseInt(tempString);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

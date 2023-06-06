package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    Float volume;

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/Overworld.wav");
        soundURL[1] = getClass().getResource("/sound/Coin.wav");
        soundURL[2] = getClass().getResource("/sound/PowerUp.wav");
        soundURL[3] = getClass().getResource("/sound/Unlock.wav");
        soundURL[4] = getClass().getResource("/sound/Fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/swingWeapon.wav");
        soundURL[8] = getClass().getResource("/sound/LevelUpFanfare.wav");
        soundURL[9] = getClass().getResource("/sound/Fire.wav");
        soundURL[10] = getClass().getResource("/sound/BombExplosion.wav");
        soundURL[11] = getClass().getResource("/sound/GameOver.wav");
        soundURL[12] = getClass().getResource("/sound/Stairs.wav");   
        soundURL[13] = getClass().getResource("/sound/blocked.wav");
        soundURL[14] = getClass().getResource("/sound/parry.wav");   
        soundURL[15] = getClass().getResource("/sound/speak.wav"); 
        soundURL[16] = getClass().getResource("/sound/Dungeon01.wav"); 
        soundURL[17] = getClass().getResource("/sound/House01.wav"); 
        soundURL[18] = getClass().getResource("/sound/DoorOpen.wav"); 
        soundURL[19] = getClass().getResource("/sound/WallBreak.wav"); 
        soundURL[20] = getClass().getResource("/sound/SLMusic.wav"); 
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {

        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
    public void checkVolume(){
      switch (volumeScale) {
        case 0: volume = -80f; break;
        case 1: volume = -20f;break;
        case 2: volume = -12f;break;
        case 3: volume = -5f;break;
        case 4: volume = 1f;break;
        case 5: volume = 6f;break;
      }
      fc.setValue(volume);
    }
}

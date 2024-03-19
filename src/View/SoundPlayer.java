package View;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

    static Clip currentMusic;
    static boolean sfxEnabled = true;
    static boolean musicEnabled = true;
    static boolean uiEnabled = true;
    static boolean masterEnabled = true;
    public static int sfxSound = -30;
    public static int musicSound = -30;
    public static int uiSound = -30;
    public static int masterSound = -30;

    public static void joueSon(int x, int y) {
        if (masterEnabled) {
            String s = "";
            if (x==0 && musicEnabled) {
                switch (y) {
                    default:
                        s="src/resources/ingame.wav";
                        break;
                }
            }
            if (x==1 && sfxEnabled) {
                switch (y) {
                    case 1:
                        s = "src/resources/teleporteur.wav";
                        break;
                    default :
                        s="src/resources/sound_piece.wav";
                        break;
                }
            }
            if (x==2 && uiEnabled) {
                switch (y) {
                    default:
                        s="src/resources/click.wav";
                        break;
                }
            }
            if (!s.equals("") && x==0) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
                    currentMusic = AudioSystem.getClip();
                    currentMusic.open(audioInputStream);
                    changeVolumeMusic();
                    currentMusic.start();
                } catch (Exception ex) {}
            }
            else if (!s.equals("") && x==1) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
                    Clip c = AudioSystem.getClip();
                    c.open(audioInputStream);
                    FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(sfxSound);
                    c.start();
                } catch (Exception ex) {}
            }
            else if (!s.equals("") && x==2) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s));
                    Clip c = AudioSystem.getClip();
                    c.open(audioInputStream);
                    FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(uiSound);
                    c.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void stopSon() {
        try {
            currentMusic.stop();
            currentMusic.close();
        } catch (Exception ex) {}
    }

    public static void loop() {
        try {
            currentMusic.loop(-1);
        } catch (Exception e) {}
    }

    public static void changeVolumeMusic() {
        try {
            FloatControl volume = (FloatControl) currentMusic.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(musicSound);
            System.out.println(volume.toString());
        } catch (Exception ex) {}
    }
}

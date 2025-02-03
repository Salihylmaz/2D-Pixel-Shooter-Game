import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {

    public synchronized void playBulletSound() {
        new Thread(() -> {
            try {
                File soundFile = new File("res/sounds/bulletsfx.wav"); //Dosyayı bulması icin
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public synchronized void playGameOverSound() {
        new Thread(() -> {
            try {
                File soundFile = new File("res/sounds/arcade-retro-game-over.wav"); // Game over ses dosyasının yolu
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

}

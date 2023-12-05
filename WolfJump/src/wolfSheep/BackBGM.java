package wolfSheep;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BackBGM {
	private Clip clip;
	public BackBGM(String pathName, boolean icLoop) {
		try {
			clip = AudioSystem.getClip();
			File file = new File(pathName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	        gainControl.setValue(-15.0f);
	        if (icLoop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
	public void start() {
		if (clip != null) {
            clip.start();
            //adjustVolume(-30.0f);  // 초기 볼륨 조절
        }
	}
	public void stop() {
    	if (clip != null && clip.isRunning()) {
            clip.stop();
    	}
	}
	public boolean isPlaying() {
        return clip != null && clip.isRunning();
  }
}
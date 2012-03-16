import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.*;


public class SoundMachine {
	Clip clip;
	
	public SoundMachine(InputStream inputStream) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(inputStream);
			clip = AudioSystem.getClip();
			clip.open(ais);
			FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(gain.getMaximum());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		clip.loop(10);
	}
}

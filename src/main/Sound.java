package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	private FloatControl gainControl;
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/main/res/sounds/The Never Ending Story.wav");
		
	}
	
	public void setFile(int i) {
	
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			 gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void setVolume(float value) {
		if (gainControl != null) {
	    gainControl.setValue(value);
	    }
	}
	
}

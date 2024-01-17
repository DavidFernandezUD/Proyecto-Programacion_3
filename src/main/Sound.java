package main;

import java.net.URL;
import java.util.logging.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/** Class to manage sounds and music.
 * @author marcos.martinez@opendeusto.es*/
public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	private FloatControl gainControl;

	/** Creates a Sound object and loads the URLs of the used sound files in an array.*/
	public Sound() {
		// Main song
		soundURL[0] = getClass().getResource("/main/res/sounds/The Never Ending Story.wav");
		// Inventory cursor sound
		soundURL[1] = getClass().getResource("/main/res/sounds/cursor.wav");
		// Healing sound
		soundURL[2] = getClass().getResource("/main/res/sounds/healing.wav");
		// Apple eating sound
		soundURL[3] = getClass().getResource("/main/res/sounds/apple-bite.wav");
		// Potion drinking sound
		soundURL[4] = getClass().getResource("/main/res/sounds/potion-drink.wav");
	}

	/** Selects the stored audio file at a specified index
	 * for later use.
	 * @param i index of the audio file to be selected in the soundURL array.*/
	public void setFile(int i) {
	
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			 gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			
		} catch (Exception e) {
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Sound", e);
		}
		
	}

	/** Plays a previously selected sound clip.*/
	public void play() {
		
		clip.start();
	}

	/** Loops the currently selected audio clip. Useful for looping music.*/
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}

	/** Stops playing the current audio clip.*/
	public void stop() {
	
		clip.stop();
	}

	/** Sets the volume of the audio.*/
	public void setVolume(float value) {
		if (gainControl != null) {
	    gainControl.setValue(value);
	    }
	}
	
}

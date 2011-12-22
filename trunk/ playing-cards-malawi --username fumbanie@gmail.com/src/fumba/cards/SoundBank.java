package fumba.cards;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Manages all the game sounds and effects (FX).
 * <p>
 * <i>Copyright (c) 1998, 2011 Oracle. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies
 * this distribution.</i>
 * </p>
 * 
 * @author Fumbani Chibaka,
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class SoundBank {

	/** Sound made when a playable card is put on the play deck **/
	public static final int CARDPLAY_ERROR = 1;

	/** Sound made when a playable card is put on the play deck **/
	public static final int START_GAME = 2;

	/**
	 * A collection of sounds that can be loaded into memory from resources in
	 * the APK (Application Package File)
	 **/
	private SoundPool soundPool;

	/** Collection of all sounds with their respective keys **/
	private HashMap<Integer, Integer> soundPoolMap;

	/** Allows access to application-specific resources and classes **/
	private Context context;

	/** Maximum number of simultaneous streams for the sound pool **/
	private int maxStreams;

	/** Audio stream type **/
	private int streamType;

	/**
	 * The sample rate converter quality. Cuurrently has no effect- use 0 for
	 * default
	 **/
	private int srcQuality;

	/**
	 * Initiates sound bank and populates it with sounds from applications
	 * resources
	 * 
	 * @param context
	 *            Application-specific resources and classes
	 */
	SoundBank(Context context) {
		this.context = context;
		this.maxStreams = 4; // play max of four sounds at a time
		this.streamType = AudioManager.STREAM_MUSIC;
		this.srcQuality = 100;
		this.soundPoolMap = new HashMap<Integer, Integer>();

		this.initSounds();
	}

	/**
	 * Loads the sounds from the raw resources into the sound pool.
	 */
	private void initSounds() {
		soundPool = new SoundPool(this.maxStreams, this.streamType,
				this.srcQuality);

		int input1 = soundPool.load(this.context, R.raw.error, 1); // context,
																	// raw_source,
																	// priority
		int input2 = soundPool.load(this.context, R.raw.bee, 1);

		soundPoolMap.put(CARDPLAY_ERROR, input1);
		soundPoolMap.put(START_GAME, input2);
	}

	/**
	 * Plays the sound
	 * 
	 * @param sound
	 *            int acting as the key of the sound
	 */
	public void playSound(int sound) {
		/*
		 * Updated: The next 4 lines calculate the current volume in a scale of
		 * 0.0 to 1.0
		 */
		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		/* Play the sound with the correct volume */
		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	}

	/**
	 * Sound played when a player attempts to make an invalid move
	 */
	public void rejectSound() {
		this.playSound(CARDPLAY_ERROR);
	}

	/**
	 * Sound played when the start button is pressed
	 */
	public void startSound() {
		this.playSound(START_GAME);
	}

}

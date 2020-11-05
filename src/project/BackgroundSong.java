package project;

import edu.princeton.cs.introcs.StdAudio;

public class BackgroundSong {

	private final String name;

	public BackgroundSong(String name) {
		this.name    = name;
	}
	
	public void playOnce() {
		play(false);
	}
	
	public void playAlways() {
		play(true);
	}

	/**
	 * Starts playing the song in a new thread so that the caller
	 *    can proceed without waiting for the song to end
	 */
	private void play(boolean isContinuous) {
		new Thread() {
			public void run() {
				if (isContinuous)
					StdAudio.loop("sound/"+name);
				else
					StdAudio.play("sound/"+name);

			}
		}.start();
	}

	public static void main(String[] args) {
		new BackgroundSong("Spongebob-remix.au").playOnce();
	}

}

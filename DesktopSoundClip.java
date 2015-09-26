import java.applet.AudioClip;
import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * An implementation of AudioClip, optimized for desktop apps. The Sun-provided
 * AudioClip for Applets is a bit buggy.
 * 
 * @author DragShot
 */
public class DesktopSoundClip implements AudioClip {

	Clip clip = null;
	AudioInputStream sound;
	boolean loaded = false;
	int lfrpo = -1;
	int cntcheck = 0;

	/**
	 * Creates an unloaded, empty SoundClip.
	 */
	public DesktopSoundClip() {
	}

	/**
	 * Creates a SoundClip from an array of bytes.
	 * 
	 * @param is
	 *            An array of bytes with the audio file data.
	 */
	public DesktopSoundClip(final byte[] is) {
		try {
			final ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(is);
			sound = AudioSystem.getAudioInputStream(bytearrayinputstream);
			sound.mark(is.length);
			AudioFormat format = sound.getFormat();
			// ULAW format is not directly supported, it needs to be wrapped.
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
						format.getFrameRate(), true); // big endian
				sound = AudioSystem.getAudioInputStream(format, sound);
				sound.mark(is.length * 2);
			}
			final DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			loaded = true;
		} catch (final Exception exception) {
			System.out.println(new StringBuilder().append("Loading Clip error: ").append(exception).toString());
			loaded = false;
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void play() {
		if (loaded)
			try {
				if (!clip.isOpen()) {
					try {
						clip.open(sound);
					} catch (final Exception exception) {
					}
					clip.loop(0);
				} else
					clip.loop(1);
				lfrpo = -1;
				cntcheck = 5;
			} catch (final Exception exception) {
			}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void loop() {
		if (loaded)
			try {
				if (!clip.isOpen())
					try {
						clip.open(sound);
					} catch (final Exception exception) {
					}
				clip.loop(70);
				lfrpo = -2;
				cntcheck = 0;
			} catch (final Exception exception) {
			}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void stop() {
		if (loaded)
			try {
				clip.stop();
				lfrpo = -1;
			} catch (final Exception exception) {
			}
	}

	/**
	 * Checks the line state and closes it if it's not in use anymore. This
	 * helps to save memory and CPU resources in general, overall in cases where
	 * a lot of sounds are played.
	 */
	public void checkopen() {
		if (loaded && clip.isOpen() && lfrpo != -2)
			if (cntcheck == 0) {
				final int i = clip.getFramePosition();
				if (lfrpo == i && !clip.isRunning()) {
					try {
						clip.close();
						sound.reset();
					} catch (final Exception exception) {
					}
					lfrpo = -1;
				} else
					lfrpo = i;
			} else
				cntcheck--;
	}
}
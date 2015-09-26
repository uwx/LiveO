import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AudioClip;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
//import sun.applet.AppletAudioClip;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of <code>AppletContext</code>, optimized for desktop apps.
 * It's not complete though, only the methods needed by Nfm2 are implemented.
 * 
 * @author DragShot
 */
public class DesktopContext implements AppletContext, Runnable {
	List<DesktopSoundClip> clips = Collections.synchronizedList(new LinkedList<DesktopSoundClip>());
	Thread clipper;

	/**
	 * Small procedure to close the unused audio lines.
	 */
	@Override
	public void run() {
		while (true) {
			for (final DesktopSoundClip clip : clips)
				clip.checkopen();
			try {
				Thread.sleep(100);
			} catch (final InterruptedException ex) {
			}
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public AudioClip getAudioClip(final URL url) {
		try {
			final InputStream in = url.openStream();
			int size = in.available(), read = 0;
			final byte[] buffer = new byte[size];
			while (size > 0) {
				read = in.read(buffer, 0, size);
				size -= read;
			}
			in.close();
			final DesktopSoundClip clip = new DesktopSoundClip(buffer);
			clips.add(clip);
			if (clipper == null) {
				clipper = new Thread(this, "Clip stopper service");
				clipper.start();
			}
			return clip;
		} catch (final Exception ex) {
		}
		return new DesktopSoundClip();
		// return new AppletAudioClip(url);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public Image getImage(final URL url) {
		return Toolkit.getDefaultToolkit().getImage(url);
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public Applet getApplet(final String name) {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public Enumeration<Applet> getApplets() {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void showDocument(final URL url) {
		if (Desktop.isDesktopSupported())
			try {
				Desktop.getDesktop().browse(url.toURI());
			} catch (final Exception ex) {
			}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void showDocument(final URL url, final String target) {
		showDocument(url);
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public void showStatus(final String status) {
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public void setStream(final String key, final InputStream stream) throws IOException {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public InputStream getStream(final String key) {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * This method is not implemented.
	 */
	@Override
	public Iterator<String> getStreamKeys() {
		throw new UnsupportedOperationException("Not supported.");
	}

}
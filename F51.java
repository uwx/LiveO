
// TODO ...
// 3D "axis" display
// auto rotation
// display another "ghost car"
// steal dragshot's "polygon selector"
// better instructions
// some sort of "virtual keyboard"
// more UI stuff
// update the readme

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   F51.java

// IF THERE'S NO FOCUS REMEMBER TO CLICK THE RENDER PANEL, DAWG

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class F51 extends JPanel implements KeyListener, MouseListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -765181569317530304L;

	public F51() {
		show3 = false;
		right = false;
		left = false;
		up = false;
		down = false;
		forward = false;
		back = false;
		rotl = false;
		rotr = false;
		plus = false;
		minus = false;
		aa = false;
		doComponentStuff();
		addKeyListener(this);
		addMouseListener(this);
		//addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
		begin();
		laterComponentStuff();
	}

	public void doComponentStuff() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		//
		setBackground(new Color(0, 0, 0));

		//rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setLayout(null);
		//dr = new DebugRunner();
		//dr.start();

		offImage = new BufferedImage(700, 475, BufferedImage.TYPE_INT_ARGB);
		if (offImage != null)
			rd = offImage.createGraphics();

		//timer.setDelay(delay);
	}

	int counted = 0;

	public void laterComponentStuff() {

		final ActionListener animate = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				repaint();
			}
		};
		final ActionListener count = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent ae) {
				counted++;
			}
		};
		// 33.33 - 30 fps
		final Timer timer = new Timer(50, animate);
		timer.start();
		final Timer counter = new Timer(1, count);
		counter.start();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		rd.setColor(Color.black);
		rd.fillRect(0, 0, 700, 475);
		whileTrueLoop();

		g.drawImage(offImage, 0, 0, null);
	}

	static File contofile = new File("./o.rad");

	public void remake(final String text) throws Exception {
		final int storeowxz = o.wxz;
		final int storeoxz = o.xz;
		final int storeoxy = o.xy;
		final int storeozy = o.zy;
		final int storeoy = o.y;
		final int storeoz = o.z;
		o = new ContO(text, medium, 350, 150, 600);
		o.wxz = storeowxz;
		o.xz = storeoxz;
		o.xy = storeoxy;
		o.zy = storeozy;
		o.y = storeoy;
		o.z = storeoz;
	}

	public void begin() {
		medium = new Medium();
		try {
			final Scanner s = new Scanner(contofile);
			final String content = s.useDelimiter("\\Z").next();
			s.close();
			o = new ContO(content, medium, 350, 150, 600);
		} catch (final FileNotFoundException e) {
			RunApp.postMsg("Hey! You need an \"o.rad\" file in your LiveO folder! Sorry!");
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
		    if (o == null)
		        System.exit(3);
		}
		o.y = 120;
		o.z += 200;
		medium.y -= 300;
		medium.zy += 10;
	}

	public void whileTrueLoop() {
		medium.d(rd);
		o.d(rd);
		if (show3)
			medium.d3p(rd);
		if (forward)
			o.wxz += 5;
		if (back)
			o.wxz -= 5;
		if (rotr)
			o.xz -= 5;
		if (rotl)
			o.xz += 5;
		if (left)
			o.xy -= 5;
		if (right)
			o.xy += 5;
		if (up)
			o.zy -= 5;
		if (down)
			o.zy += 5;
		if (plus)
			o.y += 5;
		if (minus)
			o.y -= 5;
		if (in)
			o.z += 10;
		if (out)
			o.z -= 10;
		if (aa)
			((Graphics2D) rd).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else
			((Graphics2D) rd).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		rd.setColor(new Color(0, 0, 0));
	}

	public void switchmode() {
		if (medium.mode == 0)
			medium.mode = 1;
		else
			medium.mode = 0;
	}

	public void showthree() {
		if (show3)
			show3 = false;
		else
			show3 = true;
	}

	Graphics rd;
	BufferedImage offImage;
	Thread gamer;
	ContO o;
	boolean aa;
	boolean right;
	boolean left;
	boolean up;
	boolean down;
	boolean forward;
	boolean back;
	boolean rotl;
	boolean rotr;
	boolean plus;
	boolean minus;
	boolean in;
	boolean out;
	boolean show3;
	static boolean trans;
	Medium medium;

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		final int i = e.getKeyCode();
		if (i == KeyEvent.VK_NUMPAD8 || i == KeyEvent.VK_8)
			forward = true;
		if (i == KeyEvent.VK_NUMPAD2 || i == KeyEvent.VK_2)
			back = true;
		if (i == KeyEvent.VK_NUMPAD6 || i == KeyEvent.VK_6)
			rotr = true;
		if (i == KeyEvent.VK_NUMPAD4 || i == KeyEvent.VK_4)
			rotl = true;
		if (i == KeyEvent.VK_M)
			plus = true;
		if (i == KeyEvent.VK_N)
			minus = true;
		if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY)
			in = true;
		if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE)
			out = true;
		if (i == KeyEvent.VK_LEFT)
			left = true;
		if (i == KeyEvent.VK_RIGHT)
			right = true;
		if (i == KeyEvent.VK_DOWN)
			down = true;
		if (i == KeyEvent.VK_UP)
			up = true;
		if (i == KeyEvent.VK_O)
			trans = !trans;
		if (i == KeyEvent.VK_W)
			Medium.wire = !Medium.wire;
		if (i == KeyEvent.VK_P)
			Medium.pointwire = !Medium.pointwire;
		if (i == KeyEvent.VK_T)
			show3 = !show3;
		if (i == KeyEvent.VK_Z)
			switchmode();

	}

	@Override
	public void keyReleased(final KeyEvent e) {
		final int i = e.getKeyCode();
		if (i == KeyEvent.VK_NUMPAD8 || i == KeyEvent.VK_8)
			forward = false;
		if (i == KeyEvent.VK_NUMPAD2 || i == KeyEvent.VK_2)
			back = false;
		if (i == KeyEvent.VK_NUMPAD6 || i == KeyEvent.VK_6)
			rotr = false;
		if (i == KeyEvent.VK_NUMPAD4 || i == KeyEvent.VK_4)
			rotl = false;
        if (i == KeyEvent.VK_M)
            plus = false;
        if (i == KeyEvent.VK_N)
            minus = false;
		if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY)
			in = false;
		if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE)
			out = false;
		if (i == KeyEvent.VK_LEFT)
			left = false;
		if (i == KeyEvent.VK_RIGHT)
			right = false;
		if (i == KeyEvent.VK_DOWN)
			down = false;
		if (i == KeyEvent.VK_UP)
			up = false;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		requestFocus();
		requestFocusInWindow();
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {

	}

}

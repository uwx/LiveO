
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   F51.java

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class F51 extends JPanel implements KeyListener {
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
		//addMouseListener(this);
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


		ActionListener animate = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        };
        ActionListener count = new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
                counted++;
            }
        };
        // 33.33 - 30 fps
        Timer timer = new Timer(50,animate);
        timer.start();
        Timer counter = new Timer(1, count);
        counter.start();
	}

	@Override
	public void paintComponent(final Graphics g) {
        super.paintComponent(g);

        rd.setColor(Color.black);
		rd.fillRect(0,0,700,475);
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
		o = new ContO(text, medium, 350, 150, 600, this);
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
			o = new ContO(content, medium, 350, 150, 600, this);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		o.y = 120;
		o.z += 200;
		medium.y -= 300;
		medium.zy += 10;
	}

	public void whileTrueLoop()  {
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_NUMPAD8 || i == KeyEvent.VK_8)
			forward = true;
		if (i == KeyEvent.VK_NUMPAD2 || i == KeyEvent.VK_2)
			back = true;
		if (i == KeyEvent.VK_NUMPAD4 || i == KeyEvent.VK_4)
			rotr = true;
		if (i == KeyEvent.VK_NUMPAD6 || i == KeyEvent.VK_6)
			rotl = true;
		if (i == KeyEvent.VK_PLUS)
			plus = true;
		if (i == KeyEvent.VK_MINUS)
			minus = true;
		if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE)
			in = true;
		if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY)
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
			o.wire = !o.wire;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == KeyEvent.VK_NUMPAD8 || i == KeyEvent.VK_8)
			forward = false;
		if (i == KeyEvent.VK_NUMPAD2 || i == KeyEvent.VK_2)
			back = false;
		if (i == KeyEvent.VK_NUMPAD4 || i == KeyEvent.VK_4)
			rotr = false;
		if (i == KeyEvent.VK_NUMPAD6 || i == KeyEvent.VK_6)
			rotl = false;
		if (i == KeyEvent.VK_PLUS)
			plus = false;
		if (i == KeyEvent.VK_MINUS)
			minus = false;
		if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE)
			in = false;
		if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY)
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

}

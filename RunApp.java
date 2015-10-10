import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RunApp extends Panel {

	public class RefreshThread extends Thread {
		@Override
		public void run() {
			while (true) {
				applet.remake();
				t.countPolys();
				System.out.println("autorefresh'd!");
				try {
					sleep(1000L);
				} catch (final InterruptedException e) {
				}
			}
		}
	}

	public RunApp() {

		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		frame = new JFrame("LiveO");// Change this to the name of your
									// preference

		frame.setBackground(new Color(0, 0, 0));
		//frame.setIgnoreRepaint(true);
		frame.setIconImages(getIcons());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent windowevent) {
				exitsequance();
			}
		});
		/*
		 * if(i == 1006) left = true; if(i == 1007) right = true; if(i == 1005)
		 * down = true; if(i == 1004) up = true; if(i == 86 || i == 111) trans =
		 * !trans;
		 */

		frame.setResizable(false);// If you plan to make you game support
									// changes in resolution, you can comment
									// out this line.
		// frame.pack();
		// frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		panel_3 = new JPanel();
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_3.add(panel_2, BorderLayout.WEST);

		slider = new JSlider();
		slider.setMinimum(-360);
		slider.setMaximum(360);
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				applet.o.zy = -slider.getValue();
			}
		});
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		slider.setOrientation(SwingConstants.VERTICAL);
		panel_2.add(slider);
		panel_1 = new JPanel();
		panel_3.add(panel_1, BorderLayout.SOUTH);

		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.remake();
				t.countPolys();
			}
		});

		button_1 = new JButton("<<");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.o.xz -= 50;
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		slider_1 = new JSlider();
		slider_1.setMinimum(-360);
		slider_1.setMaximum(360);
		slider_1.setValue(0);
		slider_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				applet.o.xy = -slider_1.getValue();
			}
		});
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.add(slider_1);
		panel_1.add(button_1);

		button = new JButton("<");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.o.xz -= 25;
			}
		});
		panel_1.add(button);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(btnNewButton);

		button_2 = new JButton(">");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.o.xz += 25;
			}
		});

		btnReset = new JButton("Reset");
		panel_1.add(btnReset);
		btnReset.setHorizontalAlignment(SwingConstants.LEFT);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.o.x = 350;
				applet.o.y = 120;
				applet.o.z = 800;
				applet.o.xz = 0;
				applet.o.xy = 0;
				applet.o.zy = 0;
				applet.o.wxz = 0;
			}
		});
		panel_1.add(button_2);

		button_3 = new JButton(">>");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.o.xz += 50;
			}
		});
		panel_1.add(button_3);

		btnTransGlass = new JButton("Trans. Glass");
		panel_1.add(btnTransGlass);

		btnAa = new JButton("AA");
		panel_1.add(btnAa);
		btnAa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.aa = !applet.aa;
			}
		});
		
		btnLights = new JButton("Lights");
		btnLights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applet.medium.lightson = !applet.medium.lightson;
			}
		});
		panel_1.add(btnLights);

		chckbxAutorefresh = new JCheckBox("Auto-refresh");
		panel_1.add(chckbxAutorefresh);
		applet = new F51();
		t = new TextEditor(applet, this);
		panel_3.add(applet, BorderLayout.CENTER);
		applet.setPreferredSize(new java.awt.Dimension(700, 475));// The
																	// resolution
																	// of your
																	// game goes
																	// here
		applet.setStub(new DesktopStub());

		chckbxAutorefresh.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(final ActionEvent e) {
				if (chckbxAutorefresh.isSelected() && rt == null) {
					rt = new RefreshThread();
					rt.start();
				} else {
					rt.stop();
					rt = null;
				}
			}
		});
		btnTransGlass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				F51.trans = !F51.trans;
			}
		});

		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setVisible(true);
		applet.init();
		applet.start();
		//t.countPolys();
	}
	
	boolean show = false;
	ContO storeo;
	
	public void showSelectedPolygons(final String benis, final String selection) {
		try {
			if (!show) {
				show = true;
				storeo = applet.o;
				
				int benis_scalez = 0;
				int benis_scalex = 0;
				int benis_scaley = 0;
				int benis_div = 0;
				int benis_idiv = 0;
				int benis_iwid = 0;

				BufferedReader reader = new BufferedReader(new StringReader(benis));
				String benis2 = reader.readLine();
				
				while(benis2 != null) {
					benis2 = benis2.trim();
					System.out.println(benis2.startsWith("div"));
					if (benis2.startsWith("div"))
						benis_div = applet.o.getvalue("div", benis2, 0);
					if (benis2.startsWith("iwid"))
						benis_iwid = applet.o.getvalue("iwid", benis2, 0);
					if (benis2.startsWith("idiv"))
						benis_idiv = applet.o.getvalue("idiv", benis2, 0);
					if (benis2.startsWith("ScaleZ"))
						benis_scalez = applet.o.getvalue("ScaleZ", benis2, 0);
					if (benis2.startsWith("ScaleX"))
						benis_scalex = applet.o.getvalue("ScaleX", benis2, 0);
					if (benis2.startsWith("ScaleY"))
						benis_scaley = applet.o.getvalue("ScaleY", benis2, 0);
					benis2 = reader.readLine();
				}
				reader.close();
				
				//System.out.println(benis_div);
				//System.out.println(selection);
				
				String realselection = "MaxRadius(300)";
				if (benis_scalez != 0)
					realselection = realselection + "\r\n" + "ScaleZ(" + benis_scalez + ")";
				if (benis_scalex != 0)
					realselection = realselection + "\r\n" + "ScaleX(" + benis_scalex + ")";
				if (benis_scaley != 0)
					realselection = realselection + "\r\n" + "ScaleY(" + benis_scaley + ")";
				if (benis_div != 0)
					realselection = realselection + "\r\n" + "div(" + benis_div + ")";
				if (benis_iwid != 0)
					realselection = realselection + "\r\n" + "iwid(" + benis_iwid + ")";
				if (benis_idiv != 0)
					realselection = realselection + "\r\n" + "idiv(" + benis_idiv + ")";
				//System.out.println(realselection);
				realselection = realselection + "\r\n" + selection;
				//System.out.println(realselection);
				
				DataInputStream stream = new DataInputStream(new ByteArrayInputStream(realselection.getBytes(/*StandardCharsets.UTF_8*/)));
				applet.o = new ContO(stream, applet.medium, 350, 150, 600, applet);
				applet.o.wxz = storeo.wxz;
				applet.o.xz = storeo.xz;
				applet.o.xy = storeo.xy;
				applet.o.zy = storeo.zy;
				applet.o.y = storeo.y;
				applet.o.z = storeo.z;
			} else {
				show = false;
				applet.o = storeo;
				storeo = null;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not show selected polygons! Error:\r\n\r\n" + e);
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1337L;
	static JFrame frame;
	static F51 applet;
	public static ArrayList<Image> icons;
	private final JButton button, button_1, button_2, btnNewButton, button_3, btnTransGlass, btnAa, btnReset;
	private RefreshThread rt;
	private final JCheckBox chckbxAutorefresh;
	private final JSlider slider, slider_1;
	private final JPanel panel, panel_2, panel_1;
	private final TextEditor t;
	private final JPanel panel_3;
	private JButton btnLights;

	/**
	 * Fetches icons of 16, 32 and 48 pixels from the 'data' folder.
	 */
	public static ArrayList<Image> getIcons() {
		if (icons == null) {
			icons = new ArrayList<Image>();
			final int[] resols = { 16, 32, 48 };
			for (final int res : resols)
				icons.add(Toolkit.getDefaultToolkit().createImage("data/ico_" + res + ".png"));
		}
		return icons;
	}

	public static void main(final String[] strings) {
		System.runFinalizersOnExit(true);
		System.out.println("Nfm2-Mod Console");// Change this to the messgae of
												// your preference
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception ex) {
			System.out.println("Could not setup System Look&Feel: " + ex.toString());
		}
		new RunApp();
		// startup();

	}

	public static void exitsequance() {
		applet.stop();
		frame.removeAll();
		try {
			Thread.sleep(200L);
		} catch (final Exception exception) {
		}
		applet.destroy();
		applet = null;
		System.exit(0);
	}

	public static String getString(final String tag, final String str, final int id) {
		int k = 0;
		String s3 = "";
		for (int j = tag.length() + 1; j < str.length(); j++) {
			final String s2 = "" + str.charAt(j);
			if (s2.equals(",") || s2.equals(")")) {
				k++;
				j++;
			}
			if (k == id)
				s3 += str.charAt(j);
		}
		return s3;
	}

	public static int getInt(final String tag, final String str, final int id) {
		return Integer.parseInt(getString(tag, str, id));
	}
}
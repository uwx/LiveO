import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RunApp extends Panel {

	/**
	 * Hides error popups
	 */
	static boolean suppressErrorMessages = false;

	public class RefreshThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					applet.remake(t.text.getText());
					t.countPolys();
				} catch (Exception e) {
					// DON'T WARN!
					//System.err.println("Error loading ContO: " + e);
					//JOptionPane.showMessageDialog(frame, "Error loading ContO: " + e);
				}
				System.out.println("autorefresh'd!");
				try {
					sleep(1000L);
				} catch (final InterruptedException e) {
				}
			}
		}
	}

	static File carfolder = new File("./");

	public RunApp() {
		Storage.load();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Storage.save();
				System.out.println("goodbye");
			}
		});

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
		panel_1 = new JPanel();
		panel_3.add(panel_1, BorderLayout.SOUTH);

		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					applet.remake(t.text.getText());
					t.countPolys();
				} catch (Exception er) {
					System.err.println("Error loading ContO: " + er);
					JOptionPane.showMessageDialog(frame, "Error loading ContO: " + er + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
					er.printStackTrace();
				}
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
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
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
		applet = new F51();
		t = new TextEditor(applet, this);
		panel_3.add(applet, BorderLayout.CENTER);
		applet.setPreferredSize(new java.awt.Dimension(700, 475));// The
																	// resolution
																	// of your
																	// game goes
																	// here
		applet.setStub(new DesktopStub());

		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		panel_3.add(tabbedPane, BorderLayout.EAST);

		panel_6 = new JPanel();
		tabbedPane.addTab("Controls", null, panel_6, null);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_2 = new JPanel();
		panel_6.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		panel_8 = new JPanel();
		panel_2.add(panel_8);

		slider = new JSlider();
		slider.setAlignmentX(Component.RIGHT_ALIGNMENT);
		slider.setMinimum(-360);
		slider.setMaximum(360);
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				applet.o.zy = -slider.getValue();
			}
		});
		slider.setOrientation(SwingConstants.VERTICAL);

		slider_1 = new JSlider();
		slider_1.setMinimum(-360);
		slider_1.setMaximum(360);
		slider_1.setValue(0);

		slider_2 = new JSlider();
		slider_2.setValue(0);
		slider_2.setMinimum(-360);
		slider_2.setMaximum(360);
		slider_2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				applet.o.xz = -slider_2.getValue();
			}
		});
		final GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(gl_panel_8.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_8
				.createSequentialGroup().addGap(1)
				.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup().addGap(1).addComponent(slider_1,
								GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_8.createSequentialGroup().addGap(1).addComponent(slider_2,
								GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
				.addGap(1)));
		gl_panel_8.setVerticalGroup(gl_panel_8.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_8
				.createSequentialGroup().addGap(1)
				.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_8.createSequentialGroup()
								.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
				.addGap(1)));
		panel_8.setLayout(gl_panel_8);
		slider_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				applet.o.xy = -slider_1.getValue();
			}
		});

		panel_7 = new JPanel();
		panel_2.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));

		chckbxAutorefresh = new JCheckBox("Auto-refresh");
		panel_7.add(chckbxAutorefresh);
		chckbxAutorefresh.setAlignmentX(Component.CENTER_ALIGNMENT);
		chckbxAutorefresh.setAlignmentY(Component.TOP_ALIGNMENT);
		chckbxAutorefresh.setVerticalAlignment(SwingConstants.TOP);

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

		btnLights = new JButton("Lights");
		panel_7.add(btnLights);
		btnLights.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLights.setAlignmentY(Component.TOP_ALIGNMENT);
		btnLights.setVerticalAlignment(SwingConstants.BOTTOM);

		btnTransGlass = new JButton("Trans. Glass");
		btnTransGlass.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(btnTransGlass);

		btnAa = new JButton("Antialiasing");
		btnAa.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(btnAa);
		btnAa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.aa = !applet.aa;
			}
		});
		btnTransGlass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				F51.trans = !F51.trans;
			}
		});
		btnLights.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				applet.medium.lightson = !applet.medium.lightson;
			}
		});

		final List<File> dong = new ArrayList<File>();
		try {
			Files.walk(Paths.get("./wheels/")).forEach(new Consumer<Path>() {
				@Override
				public void accept(final Path filePath) {
					if (Files.isRegularFile(filePath))
						dong.add(filePath.toFile());
				}
			});
		} catch (final IOException e1) {
		}
		File[] fArray = new File[dong.size()];
		fArray = dong.toArray(fArray);
		final String[] sArray = new String[dong.size()];
		for (int i = 0; i < fArray.length; i++)
			sArray[i] = fArray[i].getName();

		panel_5 = new JPanel();
		tabbedPane.addTab("Models", null, panel_5, null);
		final GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 35, 87, 10, 0 };
		gbl_panel_5.rowHeights = new int[] { 32, 0, 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		panel_4 = new JPanel();
		panel_4.getLayout();
		final GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 0;
		panel_5.add(panel_4, gbc_panel_4);

		lblWheel = new JLabel("Wheel:");
		panel_4.add(lblWheel);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(sArray));
		panel_4.add(comboBox);
		//t.countPolys();
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					try {
						final String item = (String) event.getItem();
						Wheels.wheelfile = item;
						applet.remake(t.text.getText());
						t.countPolys();
					} catch (Exception er) {
						System.err.println("Error loading ContO: " + er);
						JOptionPane.showMessageDialog(frame, "Error loading ContO: " + er + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
						er.printStackTrace();
					}
					System.out.println("autorefresh'd!");
				}
			}
		});

		panel_13 = new JPanel();
		final FlowLayout flowLayout_1 = (FlowLayout) panel_13.getLayout();
		flowLayout_1.setHgap(13);
		final GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.insets = new Insets(0, 0, 5, 5);
		gbc_panel_13.anchor = GridBagConstraints.WEST;
		gbc_panel_13.gridx = 1;
		gbc_panel_13.gridy = 1;
		panel_5.add(panel_13, gbc_panel_13);

		lblCar = new JLabel("Car:");
		panel_13.add(lblCar);

		makeRadCombobox();
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(carSArray));
		//t.countPolys();
		comboBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					final int item = comboBox_1.getSelectedIndex();
					final File file = carFArray[item];
					//final File[] files = fd.getFiles();
					if (file.exists() && !file.isDirectory()) {
						try {
							F51.contofile = file;
							t.loadFile();
							t.countPolys();
							applet.remake(t.text.getText());
						} catch (Exception e) {
							System.err.println("Error loading ContO: " + e);
							JOptionPane.showMessageDialog(frame, "Error loading ContO: " + e + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
							e.printStackTrace();
						}
					}
				}
			}
		});
		panel_13.add(comboBox_1);

		btnOpenCarFolder = new JButton("Select car folder");
		btnOpenCarFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser("./");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				final int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					final File selectedFile = fileChooser.getSelectedFile();
					if (selectedFile.exists() && selectedFile.isDirectory())
						carfolder = selectedFile;
					//System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				}
				makeRadCombobox();
				comboBox_1.setModel(new DefaultComboBoxModel<String>(carSArray));
			}
		});
		final GridBagConstraints gbc_btnOpenCarFolder = new GridBagConstraints();
		gbc_btnOpenCarFolder.insets = new Insets(0, 0, 0, 5);
		gbc_btnOpenCarFolder.gridx = 1;
		gbc_btnOpenCarFolder.gridy = 2;
		panel_5.add(btnOpenCarFolder, gbc_btnOpenCarFolder);

		panel_9 = new JPanel();
		tabbedPane.addTab("Car", null, panel_9, null);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.Y_AXIS));

		panel_15 = new JPanel();
		panel_9.add(panel_15);

		panel_10 = new JPanel();
		panel_10.setAlignmentY(Component.TOP_ALIGNMENT);

		lblNewLabel = new JLabel("div");
		panel_10.add(lblNewLabel);

		textField = new JTextField();
		panel_10.add(textField);
		textField.setColumns(10);

		panel_11 = new JPanel();
		panel_11.setAlignmentY(Component.TOP_ALIGNMENT);

		lblIdiv = new JLabel("idiv");
		panel_11.add(lblIdiv);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel_11.add(textField_1);

		panel_12 = new JPanel();
		panel_12.setAlignmentY(0.0f);

		lblIwid = new JLabel("iwid");
		panel_12.add(lblIwid);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		panel_12.add(textField_2);

		btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) { //can't do this without try catch... for whatever reason
				try {
					t.setDiv(Integer.valueOf(textField.getText()));
				} catch (final NumberFormatException er) {
					System.err.println("div is empty");
				}
				try {
					t.setiDiv(Integer.valueOf(textField_1.getText()));
				} catch (final NumberFormatException er) {
					System.err.println("idiv is empty");
				}
				try {
					t.setiWid(Integer.valueOf(textField_2.getText()));
				} catch (final NumberFormatException er) {
					System.err.println("iwid is empty");
				}
			}
		});
		final GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addComponent(panel_12, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(31, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_15.createSequentialGroup().addContainerGap(77, Short.MAX_VALUE)
						.addComponent(btnSet).addGap(69)));
		gl_panel_15.setVerticalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15.createSequentialGroup().addContainerGap()
						.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
				.addComponent(btnSet).addContainerGap()));
		panel_15.setLayout(gl_panel_15);

		panel_14 = new JPanel();
		panel_9.add(panel_14);

		btnSetColor = new JButton("Set 1st color");
		btnSetColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFrame f = new JFrame("Color picker");
				f.setBackground(new Color(0, 0, 0));
				//frame.setIgnoreRepaint(true);
				f.setIconImages(getIcons());
				final JColorChooser tcc = new JColorChooser();
				tcc.getSelectionModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(final ChangeEvent e) {
						final Color newColor = tcc.getColor();
						t.setColor(newColor, false);
					}
				});
				f.getContentPane().add(tcc);
				f.pack();
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		});

		final JButton btnSetndColor = new JButton("Set 2nd color");
		btnSetndColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFrame f = new JFrame("Color picker");
				f.setBackground(new Color(0, 0, 0));
				//frame.setIgnoreRepaint(true);
				f.setIconImages(getIcons());
				final JColorChooser tcc = new JColorChooser();
				tcc.getSelectionModel().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(final ChangeEvent e) {
						final Color newColor = tcc.getColor();
						t.setColor(newColor, true);
					}
				});
				f.getContentPane().add(tcc);
				f.pack();
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		});
		final GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_14.createSequentialGroup().addGap(12)
						.addGroup(gl_panel_14.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnSetColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSetndColor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panel_14
				.setVerticalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_14.createSequentialGroup().addContainerGap().addComponent(btnSetColor)
								.addGap(4).addComponent(btnSetndColor)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_14.setLayout(gl_panel_14);

		/*List<File> dong = new ArrayList<File>();
		try {
			Files.walk(Paths.get("./wheels/")).forEach(filePath -> {
			    if (Files.isRegularFile(filePath)) {
			    	dong.add(filePath.toFile());
			    }
			});
		} catch (IOException e1) {
		}*/
		/*File[] fArray = new File[dong.size()];
		fArray = dong.toArray(fArray);
		String[] sArray = new String[dong.size()];
		for (int i = 0; i < fArray.length; i++)
		{
			sArray[i] = fArray[i].getName();
		}*/

		final int item = comboBox_1.getSelectedIndex();
		final File file = carFArray[item];
		//final File[] files = fd.getFiles();
		if (file.exists() && !file.isDirectory()) {
			F51.contofile = file;
			//applet.remake();
			t.countPolys();
			t.loadFile();
		} else
			JOptionPane.showMessageDialog(frame,
					"There seems to have been a problem loading the ContO, please try again manually");

		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setVisible(true);
		applet.init();
		applet.start();
		/*try { //we have to wait because applet
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
		} // load real conto
		refreshComboboxContO();*/

	}

	static String[] carSArray;
	static File[] carFArray;

	public static void makeRadCombobox() {
		carSArray = null;
		final List<File> dong = new ArrayList<File>();
		try {
			Files.walk(Paths.get(carfolder.toURI())).forEach(new Consumer<Path>() {
				@Override
				public void accept(final Path filePath) {
					if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".rad"))
						dong.add(filePath.toFile());
				}
			});
		} catch (final IOException e1) {
		}
		carFArray = new File[dong.size()];
		carFArray = dong.toArray(carFArray);
		carSArray = new String[dong.size()];
		for (int i = 0; i < carFArray.length; i++)
			carSArray[i] = carFArray[i].getName();
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

				final BufferedReader reader = new BufferedReader(new StringReader(benis));
				String benis2 = reader.readLine();

				while (benis2 != null) {
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

				final DataInputStream stream = new DataInputStream(
						new ByteArrayInputStream(realselection.getBytes(/*StandardCharsets.UTF_8*/)));
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
		} catch (final Exception e) {
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
	private final JButton btnLights;
	private final JPanel panel_4;
	private final JComboBox<String> comboBox;
	private final JLabel lblWheel;
	private final JTabbedPane tabbedPane;
	private final JPanel panel_5;
	private final JPanel panel_6;
	private final JPanel panel_7;
	private final JPanel panel_8;
	private final JSlider slider_2;
	private final JPanel panel_9;
	private final JTextField textField;
	private final JLabel lblNewLabel;
	private final JPanel panel_10;
	private final JPanel panel_11;
	private final JLabel lblIdiv;
	private final JTextField textField_1;
	private final JPanel panel_12;
	private final JLabel lblIwid;
	private final JTextField textField_2;
	private final JPanel panel_13;
	private final JLabel lblCar;
	private final JComboBox<String> comboBox_1;
	private final JButton btnOpenCarFolder;
	private final JPanel panel_14;
	private final JButton btnSetColor;
	private final JPanel panel_15;
	private final JButton btnSet;

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

	public static void postMsg(final String msg) {
		if (!suppressErrorMessages)
			JOptionPane.showMessageDialog(frame, msg);
	}
}
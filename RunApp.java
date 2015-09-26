import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RunApp extends Panel implements KeyListener {
	
	public class RefreshThread extends Thread {
		@Override
		public void run() {
			while (true) {
				applet.remake();
				System.out.println("autorefresh'd!");
				try {
					sleep(1000L);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public RunApp() {
		
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
        frame = new JFrame("LiveO");//Change this to the name of your preference
        
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        frame.setIconImages(getIcons());
        applet = new F51();
        applet.setPreferredSize(new java.awt.Dimension(700, 475));//The resolution of your game goes here
        applet.setStub(new DesktopStub());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
                exitsequance();
            }
        });
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        frame.getContentPane().add(applet);
        panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        btnNewButton = new JButton("Refresh");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.remake();
        	}
        });
        /*if(i == 1006)
            left = true;
        if(i == 1007)
            right = true;
        if(i == 1005)
            down = true;
        if(i == 1004)
            up = true;
        if(i == 86 || i == 111)
            trans = !trans;*/
        
        button_1 = new JButton("<<");
        button_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.o.xz -= 50;
        	}
        });
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        slider_1 = new JSlider();
        slider_1.setMinimum(-360);
        slider_1.setMaximum(360);
        slider_1.setValue(0);
        slider_1.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		applet.o.xy = -slider_1.getValue();
        	}
        });
        panel_1.add(slider_1);
        panel_1.add(button_1);
        
        button = new JButton("<");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.o.xz -= 25;
        	}
        });
        panel_1.add(button);
        btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
        panel_1.add(btnNewButton);
        
        button_2 = new JButton(">");
        button_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.o.xz += 25;
        	}
        });
        
        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                applet.o.x = 350;
                applet.o.y = 120;
                applet.o.z = 800;
                applet.o.xz = 0;
                applet.o.xy = 0;
                applet.o.zy = 0;
                applet.o.wxz = 0;
        	}
        });
        panel_1.add(btnReset);
        panel_1.add(button_2);
        
        button_3 = new JButton(">>");
        button_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.o.xz += 50;
        	}
        });
        panel_1.add(button_3);
        
        btnTransGlass = new JButton("Trans. Glass");
        btnTransGlass.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.trans = !applet.trans;
        	}
        });
        
        btnAa = new JButton("AA");
        btnAa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		applet.aa = !applet.aa;
        	}
        });
        panel_1.add(btnAa);
        btnTransGlass.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_1.add(btnTransGlass);
        
        chckbxAutorefresh = new JCheckBox("Auto-refresh");
        chckbxAutorefresh.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
        		if (chckbxAutorefresh.isSelected() && rt == null) {
        			rt = new RefreshThread();
        			rt.start();
        		} else {
        			rt.stop();
        			rt = null;
        		}
        	}
        });
        panel_1.add(chckbxAutorefresh);
        

        panel_2 = new JPanel();
        frame.getContentPane().add(panel_2, BorderLayout.WEST);
        
        slider = new JSlider();
        slider.setMinimum(-360);
        slider.setMaximum(360);
        slider.setValue(0);
        slider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		applet.o.zy = -slider.getValue();
        	}
        });
        slider.setOrientation(SwingConstants.VERTICAL);
        panel_2.add(slider);
        
        frame.setResizable(false);//If you plan to make you game support changes in resolution, you can comment out this line.
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
        
        applet.init();
        applet.start();
        
		t = new TextEditor(applet);
	}

    /**
	 * 
	 */
	private static final long serialVersionUID = 1337L;
	static JFrame frame;
    static F51 applet;
    public static ArrayList<Image> icons;
    private JButton button, button_1, button_2, btnNewButton, button_3, btnTransGlass, btnAa, btnReset;
    private RefreshThread rt;
    private JCheckBox chckbxAutorefresh;
    private JSlider slider, slider_1;
    private JPanel panel, panel_2, panel_1;
    private TextEditor t;

    /**
    * Fetches icons of 16, 32 and 48 pixels from the 'data' folder.
    */
    public static ArrayList<Image> getIcons() {
        if (icons == null) {
            icons = new ArrayList<Image>();
            int[] resols = {16, 32, 48};
            for (int res : resols) {
                icons.add(Toolkit.getDefaultToolkit().createImage("data/ico_" + res + ".png"));
            }
        }
        return icons;
    }

    public static void main(String[] strings) {
        System.runFinalizersOnExit(true);
        System.out.println("Nfm2-Mod Console");//Change this to the messgae of your preference
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Could not setup System Look&Feel: " + ex.toString());
        }
        new RunApp();
        //startup();

    }

    public static void exitsequance() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(200L);
        } catch (Exception exception) {}
        applet.destroy();
        applet = null;
        System.exit(0);
    }

    public static String getString(String tag, String str, int id) {
        int k = 0;
        String s3 = "";
        for (int j = tag.length() + 1; j < str.length(); j++) {
            String s2 = "" + str.charAt(j);
            if (s2.equals(",") || s2.equals(")")) {
                k++;
                j++;
            }
            if (k == id) {
                s3 += str.charAt(j);
            }
        }
        return s3;
    }

    public static int getInt(String tag, String str, int id) {
        return Integer.parseInt(getString(tag, str, id));
    }
    

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		System.out.println(i);
		if(i == 56)
            applet.forward = true;
        if(i == 50)
        	applet.back = true;
        if(i == 119)
            if(applet.o.wire)
            	applet.o.wire = false;
            else
            	applet.o.wire = true;
        if(i == 54)
        	applet.rotr = true;
        if(i == 52)
        	applet.rotl = true;
        if(i == 43)
        	applet.plus = true;
        if(i == 45)
        	applet.minus = true;
        if(i == 42)
        	applet.in = true;
        if(i == 47)
        	applet.out = true;
        if(i == 1006)
        	applet.left = true;
        if(i == 1007)
        	applet.right = true;
        if(i == 1005)
        	applet.down = true;
        if(i == 1004)
        	applet.up = true;
        if(i == 86 || i == 111)
        	applet.trans = !applet.trans;
        
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		System.out.println(i);
		if(i == 56)
			applet.forward = false;
        if(i == 50)
        	applet.back = false;
        if(i == 54)
        	applet.rotr = false;
        if(i == 52)
        	applet.rotl = false;
        if(i == 43)
        	applet.plus = false;
        if(i == 45)
        	applet.minus = false;
        if(i == 42)
        	applet.in = false;
        if(i == 47)
        	applet.out = false;
        if(i == 1006)
        	applet.left = false;
        if(i == 1007)
        	applet.right = false;
        if(i == 1005)
        	applet.down = false;
        if(i == 1004)
        	applet.up = false;
        
	}
}
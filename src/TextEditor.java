import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

/*
 * A simple Text Editor.  This demonstrates the use of a
 * JFileChooser for the user to select a file to read from or write to.
 * It also demonstrates reading and writing text files.
 */
final class TextEditor implements ActionListener {
    // Size of editing text area.
    private static final int NUM_ROWS = 25;
    private static final int NUM_COLS = 50;
    private final int fontsize = 24;

    // Buttons to save and load files.
    private final JButton saveButton, loadButton;
    private JMenuItem newButton;

    // Area where the user does the editing
    final RSyntaxTextArea text;
    private final JTextField textField;
    private final JLabel lblFontSize;
    private final LiveO f51;
    private final JButton btnMirrorXAxis, btnMirrorYAxis, btnMirrorZAxis, prevButton, nextButton;
    private final JButton btnShowSelection;

    private final JTextField searchField;
    private final JCheckBox regexCB;
    private final JCheckBox matchCaseCB;
    private final JPanel panel;
    private final JLabel lblPolys;
    private final JPanel panel_1;
    private final JMenuItem btnSolidifyRoad;
    private final JMenuItem btnSolidifyWall;
    private final JMenuItem btnGet;
    private final JMenuItem btnSolidifyRoofna;
    private final JMenuItem btnSolidifyWallOld;
    private final JMenuItem btnSolidifyRoadOld;
    private final JPanel panel_3;
    private final JMenuBar menuBar;
    private final JMenu mnFile;
    private final JMenuItem mntmLoad;
    private final JMenu mnSettings;
    private final JPanel panel_4;
    private final JMenu mnTools;
    private boolean fffff = false;
    private final JCheckBox chckbxSchizznti;
    private final JPanel panel_2;
    JFrame frame;

    // Creates the GUI

    TextEditor(final LiveO f51, final RunApp runapp) {
        this.f51 = f51;
        frame = new JFrame("Editor");

        final Toolkit tk = Toolkit.getDefaultToolkit();
        final Dimension screenSize = tk.getScreenSize();
        final Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());

        frame.setSize(screenSize.width / 2, screenSize.height - scnMax.bottom);

        text = new RSyntaxTextArea(NUM_ROWS, NUM_COLS);
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                countPolys();
            }
        });
        text.setFont(new Font("Courier New", Font.PLAIN, fontsize));
        final RTextScrollPane textScroller = new RTextScrollPane(text);
        final Container contentPane = frame.getContentPane();
        contentPane.add(textScroller, BorderLayout.CENTER);

        // create an Action doing what you want
        final AbstractAction saveAction = new AbstractAction("doSomething") {

            private static final long serialVersionUID = -1507795546151323861L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("triggered the action");
                saveFile();
            }

        };
        // configure the Action with the accelerator (aka: short cut)
        saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

        // create an Action doing what you want
        final AbstractAction reloadAction = new AbstractAction("doSomething") {

            private static final long serialVersionUID = -1507795546151323861L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("triggered the action");
                loadFile();
            }

        };
        // configure the Action with the accelerator (aka: short cut)
        reloadAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));

        // create an Action doing what you want
        final AbstractAction newAction = new AbstractAction("doSomething") {

            private static final long serialVersionUID = -1507795546151323861L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("triggered the action");
                if (!fffff)
                    newFile();
                fffff = !fffff;
            }

        };
        // configure the Action with the accelerator (aka: short cut)
        newAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));

        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        lblPolys = new JLabel("0 Polys, 0 Points");
        panel.add(lblPolys);

        panel_3 = new JPanel();
        frame.getContentPane().add(panel_3, BorderLayout.NORTH);
        panel_3.setLayout(new BorderLayout(0, 0));

        // Create a toolbar with searching options.
        final JPanel toolBar = new JPanel();
        panel_3.add(toolBar, BorderLayout.SOUTH);
        toolBar.setLayout(new BorderLayout(0, 0));

        panel_1 = new JPanel();
        toolBar.add(panel_1, BorderLayout.NORTH);
        searchField = new JTextField(30);
        panel_1.add(searchField);
        nextButton = new JButton("Find Next");
        panel_1.add(nextButton);
        nextButton.setActionCommand("FindNext");
        prevButton = new JButton("Find Previous");
        panel_1.add(prevButton);
        prevButton.setActionCommand("FindPrev");
        regexCB = new JCheckBox("Regex");
        panel_1.add(regexCB);
        matchCaseCB = new JCheckBox("Match Case");
        panel_1.add(matchCaseCB);
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                nextButton.doClick(0);
            }
        });
        final JPanel buttonPanel = new JPanel();
        panel_3.add(buttonPanel);

        saveButton = new JButton(saveAction);
        saveButton.setText("Save");

        saveButton.getActionMap().put("saveAction", saveAction);
        saveButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put((KeyStroke) saveAction.getValue(Action.ACCELERATOR_KEY), "saveAction");

        loadButton = new JButton(reloadAction);
        loadButton.setText("Reload");

        loadButton.getActionMap().put("reloadAction", reloadAction);
        loadButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put((KeyStroke) reloadAction.getValue(Action.ACCELERATOR_KEY), "reloadAction");
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        btnMirrorXAxis = new JButton("Mirror X Axis");
        btnMirrorXAxis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mirror(3);
            }
        });
        buttonPanel.add(btnMirrorXAxis);

        btnMirrorYAxis = new JButton("Mirror Y Axis");
        btnMirrorYAxis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mirror(4);
            }
        });
        buttonPanel.add(btnMirrorYAxis);

        btnMirrorZAxis = new JButton("Mirror Z Axis");
        btnMirrorZAxis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mirror(5);
            }
        });
        buttonPanel.add(btnMirrorZAxis);

        btnShowSelection = new JButton("View Selection");
        btnShowSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                runapp.showSelectedPolygons(text.getText(), text.getSelectedText());
            }
        });
        buttonPanel.add(btnShowSelection);

        menuBar = new JMenuBar();
        panel_3.add(menuBar, BorderLayout.NORTH);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmLoad = new JMenuItem("Open");
        mntmLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
                fd.setDirectory(new File("./").getPath());
                fd.setFile("*.rad");
                fd.setVisible(true);
                //String filename = fd.getFile();
                final File[] files = fd.getFiles();
                if (files.length == 0)
                    System.out.println("You cancelled the choice");
                else
                    try {
                        System.out.println("You chose " + files[0]);
                        LiveO.contofile = files[0];
                        loadFile();
                        countPolys();
                        f51.remake(text.getText());
                    } catch (final Exception er) {
                        System.err.println("Error loading ContO: " + er);
                        RunApp.postMsg("Error loading ContO: " + er
                                + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                        er.printStackTrace();
                    }
            }
        });
        mnFile.add(mntmLoad);

        newButton = new JMenuItem(newAction);
        mnFile.add(newButton);
        newButton.setText("New");

        mnSettings = new JMenu("Settings");
        menuBar.add(mnSettings);

        panel_4 = new JPanel();
        mnSettings.add(panel_4);

        lblFontSize = new JLabel("Font size");

        textField = new JTextField();
        textField.setToolTipText("Enter to apply");
        textField.setText("24");
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final int fsize = Integer.parseInt(textField.getText());
                    text.setFont(new Font("Courier New", Font.PLAIN, fsize));
                } catch (final Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid number " + textField.getText());
                }
            }
        });
        textField.setColumns(3);
        panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel_4.add(lblFontSize);
        panel_4.add(textField);

        panel_2 = new JPanel();
        mnSettings.add(panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

        chckbxSchizznti = new JCheckBox("Hide error msgs");
        chckbxSchizznti.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                    ;
                RunApp.suppressErrorMessages = false;
                if (e.getStateChange() == ItemEvent.SELECTED)
                    ;
                RunApp.suppressErrorMessages = true;
            }
        });
        panel_2.add(chckbxSchizznti);

        mnTools = new JMenu("Tools");
        menuBar.add(mnTools);

        btnSolidifyRoofna = new JMenuItem("Solidify roof (N/A)");
        mnTools.add(btnSolidifyRoofna);

        btnSolidifyRoad = new JMenuItem("Solidify road");
        mnTools.add(btnSolidifyRoad);

        btnSolidifyRoadOld = new JMenuItem("Solidify road old");
        mnTools.add(btnSolidifyRoadOld);

        btnSolidifyWall = new JMenuItem("Solidify wall");
        mnTools.add(btnSolidifyWall);

        btnSolidifyWallOld = new JMenuItem("Solidify wall old");
        mnTools.add(btnSolidifyWallOld);

        btnGet = new JMenuItem("get <tracks>");
        mnTools.add(btnGet);
        btnGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getTracks();
            }
        });
        btnSolidifyWallOld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                solidifyWallOld();
            }
        });
        btnSolidifyWall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                solidifyWall();
            }
        });
        btnSolidifyRoadOld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                solidifyRoadOld();
            }
        });
        btnSolidifyRoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                solidifyRoad();
            }
        });
        btnSolidifyRoofna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                solidifyRoof();
            }
        });

        newButton.getActionMap().put("newAction", newAction);
        newButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put((KeyStroke) newAction.getValue(Action.ACCELERATOR_KEY), "newAction");
        newButton.addActionListener(newAction);

        saveButton.addActionListener(saveAction);
        loadButton.addActionListener(reloadAction);

        ///idk mang i like nice sizes
        //frame.pack();
        frame.setVisible(true);

    }

    /**
     * does that initializing stuff
     */
    void fourTwenty() {
        loadFile();
        countPolys();
        getRadius();
    }

    // Listener for button clicks that loads the
    // specified files and puts it in the
    // editor.
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == prevButton || event.getSource() == nextButton) {
            final String command = event.getActionCommand();
            final boolean forward = "FindNext".equals(command);

            // Create an object defining our search parameters.
            final SearchContext context = new SearchContext();
            final String text = searchField.getText();
            if (text.length() == 0)
                return;
            context.setSearchFor(text);
            context.setMatchCase(matchCaseCB.isSelected());
            context.setRegularExpression(regexCB.isSelected());
            context.setSearchForward(forward);
            context.setWholeWord(false);

            final boolean found = SearchEngine.find(this.text, context).wasFound();
            if (!found)
                JOptionPane.showMessageDialog(null, "Text not found");
        }
    }

    private void solidifyRoof() {
        try {
            final Object[] radius = getSelectedRadius();

            //final String output = "";
            //final String outend = "";

            final String radx = "radx(" + radius[0] + ")";
            final String rady = "rady(" + radius[1] + ")";
            final String radz = "radz(" + radius[2] + ")";

            JOptionPane.showMessageDialog(null,
                    "<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz + "\r\n</track>\r\n ");
            text.setText("<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz + "\r\n</track>\r\n "
                    + text.getText());

        } catch (final NullPointerException e) {
            JOptionPane.showMessageDialog(null, "You didn't select anything!");
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(null, "rip");
        }
    }

    private void solidifyWall() {
        try {
            final Object[] radius = getSelectedRadius();

            /*int pretx = (Math.abs((int)radius[0]) - Math.abs((int)radius[3]));
            int prety = (Math.abs((int)radius[1]) - Math.abs((int)radius[4]));
            int pretz = (Math.abs((int)radius[2]) - Math.abs((int)radius[5]));
            
            String outxy = "";
            if (pretx < 0)
            	outxy = "xy(-90)";
            else
            	outxy = "xy(90)";
            
            String outzy = "";
            if (pretz < 0)
            	outzy = "zy(-90)";
            else
            	outzy = "zy(90)";
            */
            //String rtx = "tx(" + pretx + ")";
            //String rty = "ty(" + prety + ")";
            //String rtz = "tz(" + pretz + ")";

            final String begintrack = "<track>";
            final String endtrack = "</track>";
            final String startradx = "radx(";
            final String startradz = "radz(";
            final String startrady = "rady(";
            final String starttx = "tx(";
            final String starttz = "tz(";
            final String startty = "ty(";
            //final String startxy = "xy(";
            //final String startzy = "zy(";
            final String end = ")";
            final String l = "\r\n";

            final int radx[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int radz[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int rady[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int tx[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int ty[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int tz[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final String xy[] = new String[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final String zy[] = new String[4]; // CUBE HAS 4 SIDES MOTHERFUCKER

            final int xcoord = (int) radius[0];
            final int zcoord = (int) radius[2];
            final int ycoord = (int) radius[1];

            radx[0] = xcoord;
            radz[0] = zcoord;
            rady[0] = ycoord;
            tx[0] = -zcoord;
            ty[0] = 0;
            tz[0] = 0; // may not be
            xy[0] = "xy(90)";
            zy[0] = ""; // may not be

            radx[1] = xcoord;
            radz[1] = zcoord;
            rady[1] = ycoord;
            tx[1] = zcoord;
            ty[1] = 0;
            tz[1] = 0; // may not be
            xy[1] = "xy(-90)"; //negative
            zy[1] = ""; // may not be

            radx[2] = zcoord;
            radz[2] = xcoord;
            rady[2] = zcoord / 2;
            tx[2] = 0;
            ty[2] = 0;
            tz[2] = zcoord;
            xy[2] = ""; // may not be
            zy[2] = "zy(-90)"; //negative

            radx[3] = zcoord;
            radz[3] = xcoord;
            rady[3] = zcoord / 2;
            tx[3] = 0;
            ty[3] = 0;
            tz[3] = -zcoord;
            xy[3] = ""; // may not be
            zy[3] = "zy(90)";

            final String out = begintrack + l + xy[0] + l + zy[0] + l + startradx + radx[0] + end + l + startradz
                    + radz[0] + end + l + startrady + rady[0] + end + l + starttx + tx[0] + end + l + startty + ty[0]
                    + end + l + starttz + tz[0] + end + l + endtrack + l + l + begintrack + l + xy[1] + l + zy[1] + l
                    + startradx + radx[1] + end + l + startradz + radz[1] + end + l + startrady + rady[1] + end + l
                    + starttx + tx[1] + end + l + startty + ty[1] + end + l + starttz + tz[1] + end + l + endtrack + l
                    + l + begintrack + l + xy[2] + l + zy[2] + l + startradx + radx[2] + end + l + startradz + radz[2]
                    + end + l + startrady + rady[2] + end + l + starttx + tx[2] + end + l + startty + ty[2] + end + l
                    + starttz + tz[2] + end + l + endtrack + l + l + begintrack + l + xy[3] + l + zy[3] + l + startradx
                    + radx[3] + end + l + startradz + radz[3] + end + l + startrady + rady[3] + end + l + starttx
                    + tx[3] + end + l + startty + ty[3] + end + l + starttz + tz[3] + end + l + endtrack + l + l;
            System.out.println(out);

            ////////////new

            /*
            <track>
            xy(90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(-zcoord)
            ty(0)
            </track>
            
            <track>
            xy(-90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(zcoord)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2) //may not be, may be ycoord
            tx(0)
            ty(0)
            tz(zcoord)
            </track>
            
            <track>
            zy(90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(-zcoord)
            </track>
            
            */

            //////////// old

            /*
            <track>
            xy(90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(-ycoord)
            ty(0)
            </track>
            
            
            <track>
            xy(-90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(ycoord)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(zcoord)
            </track>
            
            <track>
            zy(90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(-zcoord)
            </track>
            */

            ////////////original

            /*
            
            <track>
            xy(90)
            radx(200)
            radz(700)
            rady(400)
            tx(-700)
            ty(0)
            </track>
            
            <track>
            xy(-90)
            radx(200)
            radz(700)
            rady(400)
            tx(700)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(700)
            radz(200)
            rady(350)
            tx(0)
            ty(0)
            tz(700)
            </track>
            
            <track>
            zy(90)
            radx(700)
            radz(200)
            rady(350)
            tx(0)
            ty(0)
            tz(-700)
            </track>
            
            */

            ////////////flat

            /*
            <track>
            
            xy(90)
            radx(1)
            rady(2)
            radz(40)
            tx(-10)
            ty(0)
            tz(0)
            </track>
            
            */

            //final String radx = "radx(" + Math.abs(pretx) + ")";
            //final String rady = "rady(" + Math.abs(prety) + ")";
            //final String radz = "radz(" + Math.abs(pretz) + ")";

            JOptionPane.showMessageDialog(null, out);
            text.setText(out + l + l + text.getText());
        } catch (final NullPointerException e) {
            JOptionPane.showMessageDialog(null, "You didn't select anything!");
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(null, "rip");
        }

    }

    private void solidifyWallOld() {
        try {
            final Object[] radius = getSelectedRadius();

            /*int pretx = (Math.abs((int)radius[0]) - Math.abs((int)radius[3]));
            int prety = (Math.abs((int)radius[1]) - Math.abs((int)radius[4]));
            int pretz = (Math.abs((int)radius[2]) - Math.abs((int)radius[5]));
            
            String outxy = "";
            if (pretx < 0)
            	outxy = "xy(-90)";
            else
            	outxy = "xy(90)";
            
            String outzy = "";
            if (pretz < 0)
            	outzy = "zy(-90)";
            else
            	outzy = "zy(90)";
            */
            //String rtx = "tx(" + pretx + ")";
            //String rty = "ty(" + prety + ")";
            //String rtz = "tz(" + pretz + ")";

            final String begintrack = "<track>";
            final String endtrack = "</track>";
            final String startradx = "radx(";
            final String startradz = "radz(";
            final String startrady = "rady(";
            final String starttx = "tx(";
            final String starttz = "tz(";
            final String startty = "ty(";
            final String startxy = "xy(";
            final String startzy = "zy(";
            final String end = ")";
            final String l = "\r\n";

            final int radx[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int radz[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int rady[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int tx[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int ty[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int tz[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int xy[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER
            final int zy[] = new int[4]; // CUBE HAS 4 SIDES MOTHERFUCKER

            final int xcoord = (int) radius[0];
            final int zcoord = (int) radius[2];
            final int ycoord = (int) radius[1];

            radx[0] = xcoord;
            radz[0] = zcoord;
            rady[0] = ycoord;
            tx[0] = -zcoord;
            ty[0] = 0;
            tz[0] = 0; // may not be
            xy[0] = 90;
            zy[0] = 0; // may not be

            radx[1] = xcoord;
            radz[1] = zcoord;
            rady[1] = ycoord;
            tx[1] = zcoord;
            ty[1] = 0;
            tz[1] = 0; // may not be
            xy[1] = -90; //negative
            zy[1] = 0; // may not be

            radx[2] = zcoord;
            radz[2] = xcoord;
            rady[2] = zcoord / 2;
            tx[2] = 0;
            ty[2] = 0;
            tz[2] = zcoord;
            xy[2] = 0; // may not be
            zy[2] = -90; //negative

            radx[3] = zcoord;
            radz[3] = xcoord;
            rady[3] = zcoord / 2;
            tx[3] = 0;
            ty[3] = 0;
            tz[3] = -zcoord;
            xy[3] = 0; // may not be
            zy[3] = 90;

            final String out = begintrack + l + startxy + xy[0] + end + l + startzy + zy[0] + end + l + startradx
                    + radx[0] + end + l + startradz + radz[0] + end + l + startrady + rady[0] + end + l + starttx
                    + tx[0] + end + l + startty + ty[0] + end + l + starttz + tz[0] + end + l + endtrack + l + l
                    + begintrack + l + startxy + xy[1] + end + l + startzy + zy[1] + end + l + startradx + radx[1] + end
                    + l + startradz + radz[1] + end + l + startrady + rady[1] + end + l + starttx + tx[1] + end + l
                    + startty + ty[1] + end + l + starttz + tz[1] + end + l + endtrack + l + l + begintrack + l
                    + startxy + xy[2] + end + l + startzy + zy[2] + end + l + startradx + radx[2] + end + l + startradz
                    + radz[2] + end + l + startrady + rady[2] + end + l + starttx + tx[2] + end + l + startty + ty[2]
                    + end + l + starttz + tz[2] + end + l + endtrack + l + l + begintrack + l + startxy + xy[3] + end
                    + l + startzy + zy[3] + end + l + startradx + radx[3] + end + l + startradz + radz[3] + end + l
                    + startrady + rady[3] + end + l + starttx + tx[3] + end + l + startty + ty[3] + end + l + starttz
                    + tz[3] + end + l + endtrack + l + l;
            System.out.println(out);

            ////////////new

            /*
            <track>
            xy(90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(-zcoord)
            ty(0)
            </track>
            
            <track>
            xy(-90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(zcoord)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2) //may not be, may be ycoord
            tx(0)
            ty(0)
            tz(zcoord)
            </track>
            
            <track>
            zy(90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(-zcoord)
            </track>
            
            */

            //////////// old

            /*
            <track>
            xy(90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(-ycoord)
            ty(0)
            </track>
            
            
            <track>
            xy(-90)
            radx(xcoord)
            radz(zcoord)
            rady(ycoord)
            tx(ycoord)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(zcoord)
            </track>
            
            <track>
            zy(90)
            radx(zcoord)
            radz(xcoord)
            rady(zcoord/2)
            tx(0)
            ty(0)
            tz(-zcoord)
            </track>
            */

            ////////////original

            /*
            
            <track>
            xy(90)
            radx(200)
            radz(700)
            rady(400)
            tx(-700)
            ty(0)
            </track>
            
            <track>
            xy(-90)
            radx(200)
            radz(700)
            rady(400)
            tx(700)
            ty(0)
            </track>
            
            <track>
            zy(-90)
            radx(700)
            radz(200)
            rady(350)
            tx(0)
            ty(0)
            tz(700)
            </track>
            
            <track>
            zy(90)
            radx(700)
            radz(200)
            rady(350)
            tx(0)
            ty(0)
            tz(-700)
            </track>
            
            */

            ////////////flat

            /*
            <track>
            
            xy(90)
            radx(1)
            rady(2)
            radz(40)
            tx(-10)
            ty(0)
            tz(0)
            </track>
            
            */

            //final String radx = "radx(" + Math.abs(pretx) + ")";
            //final String rady = "rady(" + Math.abs(prety) + ")";
            //final String radz = "radz(" + Math.abs(pretz) + ")";

            JOptionPane.showMessageDialog(null, out);
            text.setText(out + l + l + text.getText());
        } catch (final NullPointerException e) {
            JOptionPane.showMessageDialog(null, "You didn't select anything!");
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(null, "rip");
        }

    }

    private void solidifyRoad() {
        //Object[] radius = getSelectedRadius();

        Object maxX = null;
        Object maxY = null;
        Object maxZ = null;
        Object minX = null;
        Object minY = null;
        Object minZ = null;
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith("p(")) {
                    final int curX = getvalue("p", benis2, 0);
                    if (maxX != null)
                        if (curX > (int) maxX)
                            maxX = curX;
                        else {
                        }
                    else
                        maxX = curX;
                    if (minX != null)
                        if (curX < (int) minX)
                            minX = curX;
                        else {
                        }
                    else
                        minX = curX;
                    final int curY = getvalue("p", benis2, 1);
                    if (maxY != null)
                        if (curY > (int) maxY)
                            maxY = curY;
                        else {
                        }
                    else
                        maxY = curY;
                    if (minY != null)
                        if (curY < (int) minY)
                            minY = curY;
                        else {
                        }
                    else
                        minY = curY;
                    final int curZ = getvalue("p", benis2, 2);
                    if (maxZ != null)
                        if (curZ > (int) maxZ)
                            maxZ = curZ;
                        else {
                        }
                    else
                        maxZ = curZ;
                    if (minZ != null)
                        if (curZ < (int) minZ)
                            minZ = curZ;
                        else {
                        }
                    else
                        minZ = curZ;
                }
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
        } finally {
            /*text.setText("max(" +
            			 maxX + "," + maxY + "," + maxZ + ")\r\n" +
            			 "min(" +
            			 minX + "," + minY + "," + minZ + ")\r\n" +
            			 text.getText()
            			 );*/
            System.out.println("maxX " + maxX);
            System.out.println("maxY " + maxY);
            System.out.println("maxZ " + maxZ);
            System.out.println("minX " + minX);
            System.out.println("minY " + minY);
            System.out.println("minZ " + minZ);
        }

        final String radx = "radx(" + (Math.abs((int) maxX) - Math.abs((int) minX)) / 2 + ")";
        final String rady = "rady(" + (Math.abs((int) maxY) - Math.abs((int) minY)) / 2 + ")";
        final String radz = "radz(" + (Math.abs((int) maxZ) - Math.abs((int) minZ)) / 2 + ")";

        final String pretx = "tx(" + (Math.abs((int) maxX) - Math.abs((int) minX)) + ")";
        final String prety = "ty(" + (Math.abs((int) maxY) - Math.abs((int) minY)) + ")";
        final String pretz = "tz(" + (Math.abs((int) maxZ) - Math.abs((int) minZ)) + ")";

        JOptionPane.showMessageDialog(null, "<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz
                + "\r\n" + pretx + "\r\n" + prety + "\r\n" + pretz + "\r\n" + "</track>\r\n");
        text.setText("<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz + "\r\n" + pretx + "\r\n"
                + prety + "\r\n" + pretz + "\r\n" + "</track>\r\n" + text.getText());

    }

    private void solidifyRoadOld() {
        try {
            final Object[] radius = getSelectedRadius();

            //final String output = "";
            //final String outend = "";

            final String radx = "radx(" + radius[0] + ")";
            final String rady = "rady(" + radius[1] + ")";
            final String radz = "radz(" + radius[2] + ")";

            JOptionPane.showMessageDialog(null,
                    "<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz + "\r\n</track>\r\n ");
            text.setText("<track>\r\nc(100,100,100)\r\n" + radx + "\r\n" + rady + "\r\n" + radz + "\r\n</track>\r\n "
                    + text.getText());
        } catch (final NullPointerException e) {
            JOptionPane.showMessageDialog(null, "You didn't select anything!");
        }
    }

    private void getTracks() {
        int trax = 0;
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith("<track>"))
                    trax++;
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
        } finally {
            JOptionPane.showMessageDialog(null, "no. <tracks> found: " + trax);
        }
    }

    public Object[] getRadius() {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        int minX = 0;
        int minY = 0;
        int minZ = 0;
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith("p(")) {
                    final int curX = getvalue("p", benis2, 0);
                    if (curX > maxX)
                        maxX = curX;
                    if (curX < minX)
                        minX = curX;
                    final int curY = getvalue("p", benis2, 1);
                    if (curY > maxY)
                        maxY = curY;
                    if (curY < minY)
                        minY = curY;
                    final int curZ = getvalue("p", benis2, 2);
                    if (curZ > maxZ)
                        maxZ = curZ;
                    if (curZ < minZ)
                        minZ = curZ;
                }
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
        } finally {
            /*text.setText("max(" +
            			 maxX + "," + maxY + "," + maxZ + ")\r\n" +
            			 "min(" +
            			 minX + "," + minY + "," + minZ + ")\r\n" +
            			 text.getText()
            			 );*/
            System.out.println("maxX " + maxX);
            System.out.println("maxY " + maxY);
            System.out.println("maxZ " + maxZ);
            System.out.println("minX " + minX);
            System.out.println("minY " + minY);
            System.out.println("minZ " + minZ);
        }

        return new Object[] {
                maxX, maxY, maxZ, minX, minY, minZ
        };
    }

    public Object[] getSelectedRadius() {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;
        int minX = 0;
        int minY = 0;
        int minZ = 0;
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getSelectedText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith("p(")) {
                    final int curX = getvalue("p", benis2, 0);
                    if (curX > maxX)
                        maxX = curX;
                    if (curX < minX)
                        minX = curX;
                    final int curY = getvalue("p", benis2, 1);
                    if (curY > maxY)
                        maxY = curY;
                    if (curY < minY)
                        minY = curY;
                    final int curZ = getvalue("p", benis2, 2);
                    if (curZ > maxZ)
                        maxZ = curZ;
                    if (curZ < minZ)
                        minZ = curZ;
                }
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
        } finally {
            /*text.setText("max (selection) (" +
            			 maxX + "," + maxY + "," + maxZ + ")\r\n" +
            			 "min (selection) (" +
            			 minX + "," + minY + "," + minZ + ")\r\n" +
            			 text.getText()
            			 );*/
            //JOptionPane.showMessageDialog(null, "maxX: " + maxX);
            //JOptionPane.showMessageDialog(null, "maxY: " + maxY);
            //JOptionPane.showMessageDialog(null, "maxZ: " + maxZ);
            //JOptionPane.showMessageDialog(null, "minX: " + minX);
            //JOptionPane.showMessageDialog(null, "minY: " + minY);
            //JOptionPane.showMessageDialog(null, "minZ: " + minZ);
            System.out.println("maxX " + maxX);
            System.out.println("maxY " + maxY);
            System.out.println("maxZ " + maxZ);
            System.out.println("minX " + minX);
            System.out.println("minY " + minY);
            System.out.println("minZ " + minZ);
        }
        return new Object[] {
                maxX, maxY, maxZ, minX, minY, minZ
        };
    }

    public int getvalueintext(final String s) {
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith(s))
                    return getvalue(s, benis2, 0);
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("could not get value " + s + " in text");
    }

    private int getvalue(final String s, final String s1, final int i) {
        int k = 0;
        String s3 = "";
        for (int j = s.length() + 1; j < s1.length(); j++) {
            final String s2 = new StringBuilder().append("").append(s1.charAt(j)).toString();
            if (s2.equals(",") || s2.equals(")")) {
                k++;
                j++;
            }
            if (k == i)
                s3 = new StringBuilder().append(s3).append(s1.charAt(j)).toString();
        }

        return Integer.valueOf(s3).intValue();
    }

    void countPolys() {
        int polys = 0;
        int points = 0;
        boolean flag = false;
        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            while (benis2 != null) {
                if (benis2.startsWith("<p>"))
                    flag = true;
                if (benis2.startsWith("</p>") && flag) {
                    flag = false;
                    polys++;
                }
                if (benis2.startsWith("p("))
                    points++;
                benis2 = reader.readLine();
            }

        } catch (final IOException e) {
        } finally {
            lblPolys.setText("" + polys + " Polys, " + points + " Points");
        }
    }

    private void newFile() { //doesn't (and shouldn't) save
        final int opt = JOptionPane.showConfirmDialog(null, "This will discard all unsaved changes!", "Create new file",
                JOptionPane.OK_CANCEL_OPTION);

        if (opt == JOptionPane.OK_OPTION) {
            final FileDialog fd = new FileDialog(RunApp.frame, "Choose a file", FileDialog.SAVE);
            fd.setDirectory(new File("./").getPath());
            fd.setFile("*.rad");
            fd.setVisible(true);
            //String filename = fd.getFile();
            final File[] files = fd.getFiles();
            if (files.length == 0)
                System.out.println("You cancelled the choice");
            else {
                System.out.println("You chose " + files[0]);
                LiveO.contofile = files[0];
                text.setText(
                        "MaxRadius(300)\r\nshadow()\r\ndiv(24)\r\n\r\n\r\n\r\n\r\n\r\nw(-33,0,55,11,10,10,1)\r\nw(33,0,55,11,-10,10,1)\r\nw(-31,-1,-60,1,16,11,1)\r\nw(31,-1,-60,1,-16,11,1)");
                try {
                    f51.remake(text.getText());
                } catch (final Exception e) {
                    JOptionPane.showMessageDialog(RunApp.frame,
                            "Something went horribly wrong.\r\nTell rafa the \"new\" dialog threw an exception and give him the full console log");
                    e.printStackTrace();
                }
                countPolys();
                saveFile();
            }
        }
        //f51.remake();
        //countPolys();
    }

    // Display a file chooser so the user can select a file
    // to save to. Then write the contents of the text area
    // to that file. Does nothing if the user cancels out
    // of the file chooser.
    void saveFile() {
        final File file = LiveO.contofile;

        try {
            // Now write to the file
            final PrintWriter output = new PrintWriter(new FileWriter(file));
            output.println(text.getText());
            output.close();
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Can't save file " + e.getMessage());
        }
        try {
            f51.remake(text.getText());
        } catch (final Exception er) {
            System.err.println("Error loading ContO: " + er);
            JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                    + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
            er.printStackTrace();
        }
    }

    private void mirror(final int axis) {
        if (axis == 3 || axis == 4 || axis == 5) {
            final String string = new StringBuilder().append("").append(text.getSelectedText()).append("\n").toString();
            String output = "\n\n";
            output = output + "// Mirror of the polygons above along the ";
            if (axis == 3)
                output = output + "X axis:";
            if (axis == 4)
                output = output + "Y axis:";
            if (axis == 5)
                output = output + "Z axis:";
            output = new StringBuilder().append(output).append("\n\n").toString();
            int i_200_ = 0;
            int i_201_ = string.indexOf("\n", 0);
            while (i_201_ != -1 && i_200_ < string.length()) {
                String string_202_ = string.substring(i_200_, i_201_);
                string_202_ = string_202_.trim();
                i_200_ = i_201_ + 1;
                i_201_ = string.indexOf("\n", i_200_);
                if (string_202_.startsWith("fs(-"))
                    string_202_ = new StringBuilder().append("fs(")
                            .append(string_202_.substring(4, string_202_.length())).append("").toString();
                else if (string_202_.startsWith("fs("))
                    string_202_ = new StringBuilder().append("fs(-")
                            .append(string_202_.substring(3, string_202_.length())).append("").toString();
                if (axis == 3)
                    if (string_202_.startsWith("p(-"))
                        string_202_ = new StringBuilder().append("p(")
                                .append(string_202_.substring(3, string_202_.length())).append("").toString();
                    else if (string_202_.startsWith("p("))
                        string_202_ = new StringBuilder().append("p(-")
                                .append(string_202_.substring(2, string_202_.length())).append("").toString();
                if (axis == 4 && string_202_.startsWith("p(")) {
                    final int i_203_ = string_202_.indexOf(",", 0);
                    if (i_203_ >= 0)
                        if (string_202_.startsWith(",-", i_203_))
                            string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_203_))
                                    .append(",").append(string_202_.substring(i_203_ + 2, string_202_.length()))
                                    .append("").toString();
                        else if (string_202_.startsWith(",", i_203_))
                            string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_203_))
                                    .append(",-").append(string_202_.substring(i_203_ + 1, string_202_.length()))
                                    .append("").toString();
                }
                if (axis == 5 && string_202_.startsWith("p(")) {
                    int i_204_ = string_202_.indexOf(",", 0);
                    i_204_ = string_202_.indexOf(",", i_204_ + 1);
                    if (i_204_ >= 0)
                        if (string_202_.startsWith(",-", i_204_))
                            string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_204_))
                                    .append(",").append(string_202_.substring(i_204_ + 2, string_202_.length()))
                                    .append("").toString();
                        else if (string_202_.startsWith(",", i_204_))
                            string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_204_))
                                    .append(",-").append(string_202_.substring(i_204_ + 1, string_202_.length()))
                                    .append("").toString();
                }
                output = new StringBuilder().append(output).append("").append(string_202_).append("\n").toString();
            }
            output = new StringBuilder().append(output).append("\n// End of mirror").toString();
            text.insert(output, text.getSelectionEnd());
        }
        try {
            f51.remake(text.getText());
            countPolys();
        } catch (final Exception er) {
            System.err.println("Error loading ContO: " + er);
            JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                    + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
            er.printStackTrace();
            er.printStackTrace();
        }
    }

    // Display a file chooser so the user can select a file to load.
    // Then load the file into the editing area. Does nothing if
    // the user cancels the file chooser.
    void loadFile() {
        String line;
        final File file = LiveO.contofile;

        try {
            // Open the file.
            final BufferedReader input = new BufferedReader(new FileReader(file));

            // Clear the editing area
            text.setText("");

            // Fill up the ediitng area with the contents of the file being
            // read.
            line = input.readLine();
            while (line != null) {
                text.append(line + "\n");
                line = input.readLine();
            }

            // Close the file
            input.close();
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Can't load file " + e.getMessage());
        }
    }

    void setDiv(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;
                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("div(")) {
                        benis2 = "div(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "div(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                            + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setiDiv(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;

                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("idiv(")) {
                        benis2 = "idiv(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "idiv(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                            + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setiWid(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;
                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("iwid(")) {
                        benis2 = "iwid(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "iwid(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er);
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setScaleX(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;
                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("ScaleX(")) {
                        benis2 = "ScaleX(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "ScaleX(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                            + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setScaleY(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;
                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("ScaleY(")) {
                        benis2 = "ScaleY(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "ScaleY(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                            + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setScaleZ(final int d) {
        if (d > 0)
            try {
                final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
                String benis2 = reader.readLine();
                String fullbenis2 = "";
                boolean caughtdiv = false;
                while (benis2 != null) {
                    benis2 = benis2.trim();

                    if (benis2.startsWith("ScaleZ(")) {
                        benis2 = "ScaleZ(" + d + ")";
                        caughtdiv = true;
                    }

                    fullbenis2 = fullbenis2 + benis2 + "\r\n";
                    benis2 = reader.readLine();
                }
                if (!caughtdiv)
                    fullbenis2 = "ScaleZ(" + d + ")\r\n" + fullbenis2; //prepend
                text.setText(fullbenis2);
                try {
                    f51.remake(text.getText());
                    countPolys();
                } catch (final Exception er) {
                    System.err.println("Error loading ContO: " + er);
                    JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er
                            + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                    er.printStackTrace();
                }
                //saveFile();
            } catch (final IOException e) {
            }
    }

    void setColor(final Color c, final boolean second) {
        final String cstring = "(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
        String oldfirstcolor = "(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
        String oldsecondcolor = "(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";

        try {
            final BufferedReader reader = new BufferedReader(new StringReader(text.getText()));
            String benis2 = reader.readLine();
            String fullbenis2 = "";
            while (benis2 != null) {
                benis2 = benis2.trim();
                //String ob2 = benis2;
                System.out.println(benis2);
                if (benis2.startsWith("1stColor(")) {
                    oldfirstcolor = "(" + getvalue("1stColor", benis2, 0) + "," + getvalue("1stColor", benis2, 1) + ","
                            + getvalue("1stColor", benis2, 2) + ")";
                    if (!second)
                        benis2 = "1stColor" + cstring;
                }
                if (benis2.startsWith("2ndColor(")) {
                    oldsecondcolor = "(" + getvalue("2ndColor", benis2, 0) + "," + getvalue("2ndColor", benis2, 1) + ","
                            + getvalue("2ndColor", benis2, 2) + ")";
                    if (second)
                        benis2 = "2ndColor" + cstring;
                }
                if (benis2.startsWith("c" + oldfirstcolor) && !second)
                    benis2 = "c" + cstring;
                if (benis2.startsWith("c" + oldsecondcolor) && second)
                    benis2 = "c" + cstring;
                fullbenis2 = fullbenis2 + benis2 + "\r\n";
                benis2 = reader.readLine();
            }
            text.setText(fullbenis2);
            try {
                f51.remake(text.getText());
                countPolys();

            } catch (final Exception er) {
                System.err.println("Error loading ContO: " + er);
                JOptionPane.showMessageDialog(RunApp.frame, "Error loading ContO: " + er);
                er.printStackTrace();
            }
            //saveFile();
        } catch (final IOException e) {
        }
    }
}

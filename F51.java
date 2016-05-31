
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

import java.awt.AlphaComposite;
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
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

final class F51 extends JPanel implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {
    /**
     *
     */
    private static final long serialVersionUID = -765181569317530304L;
    
    public static final byte MOUSE_RELEASED = -1;
    public static final byte MOUSE_OFF = 0;
    public static final byte MOUSE_DOWN = 1;
    public static final byte MOUSE_PRESSED = 2;

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

        axis = false;
        shift = false;
        control = false;

        doComponentStuff();
        if (!Beans.isDesignTime()) { // if we are not in design mode
            addKeyListener(this);
            addMouseListener(this);
            addMouseWheelListener(this);
            addMouseMotionListener(this);
            setFocusable(true);
            requestFocus();
            requestFocusInWindow();
            begin();
            laterComponentStuff();
        } else { // set some placeholders
            setBackground(new Color(0, 255, 255));
            setForeground(new Color(0, 255, 255));
        }
    }

    private void doComponentStuff() {
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

    private void laterComponentStuff() {

        final ActionListener animate = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                repaint();
            }
        };
        final ActionListener count = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent ae) {
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
        try {
            whileTrueLoop();
        } catch (final Exception e) {
            e.printStackTrace();
            if (!Beans.isDesignTime())
                System.exit(3);
        }

        g.drawImage(offImage, 0, 0, null);
        //g.drawString(""+ o.x, 10, 100);
        //g.drawString(""+ o.y, 10, 120);
        //g.drawString(""+ o.z, 10, 140);
    }

    static File contofile = new File("./o.rad");

    public void remake(final String text) throws Exception {
        final int storeowxz = o.wxz;
        final int storeoxz = o.xz;
        final int storeoxy = o.xy;
        final int storeozy = o.zy;
        final int storeoy = o.y;
        final int storeoz = o.z;
        o = new ContO(text, 350, 150, 600);
        o.wxz = storeowxz;
        o.xz = storeoxz;
        o.xy = storeoxy;
        o.zy = storeozy;
        o.y = storeoy;
        o.z = storeoz;
    }

    static File overlayfile = new File("./o.rad");
    private static ContO overlay = null;
    boolean drawOverlay = false;

    void remakeOverlay() throws Exception {
        overlay = new ContO(new DataInputStream(new FileInputStream(overlayfile)), 350, 150, 600);
        overlay.wxz = o.wxz;
        overlay.xz = o.xz;
        overlay.xy = o.xy;
        overlay.zy = o.zy;
        overlay.y = o.y;
        overlay.z = o.z;
    }

    private void drawOverlay() {
        //updates
        overlay.wxz = o.wxz;
        overlay.xz = o.xz;
        overlay.xy = o.xy;
        overlay.zy = o.zy;
        overlay.y = o.y;
        overlay.z = o.z;

        //draws
        ((Graphics2D) rd).setComposite(AlphaComposite.getInstance(3, 0.6F));
        Medium.hideoutlines = true;
        overlay.d(rd);
        Medium.hideoutlines = false;
        ((Graphics2D) rd).setComposite(AlphaComposite.getInstance(3, 1.0F));
    }

    private void begin() {
        Medium.init();
        try {
            final Scanner s = new Scanner(contofile);
            final String content = s.useDelimiter("\\Z").next();
            s.close();
            o = new ContO(content, 350, 150, 600);
        } catch (final FileNotFoundException e) {
            RunApp.postMsg("Hey! You need an \"o.rad\" file in your LiveO folder! Sorry!");
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (o == null)
                if (!Beans.isDesignTime())
                    System.exit(3);
                else return;
        }
        o.y = 120;
        o.z += 200;
        Medium.y -= 300;
        Medium.zy += 10;
    }

    private void whileTrueLoop() {
        Medium.d(rd);
        o.d(rd);
        if (drawOverlay && overlay != null)
            drawOverlay();

        if (Medium.autorotate)
            if (Medium.autorotateDirection)
                o.xz -= Medium.autorotateCoarseness;
            else
                o.xz += Medium.autorotateCoarseness;

        float movement_mult = 1F;
        if (shift && control)
            movement_mult = 15F;
        else if (shift)
            movement_mult = 5F;
        else if (control)
            movement_mult = 0.5F;

        if (axis)
            Medium.axis(rd, o);
        if (show3)
            Medium.d3p(rd);
        if (forward)
            o.wxz -= Medium.movementCoarseness * movement_mult;
        if (back)
            o.wxz += Medium.movementCoarseness * movement_mult;
        if (rotr)
            o.xz -= Medium.movementCoarseness * movement_mult;
        if (rotl)
            o.xz += Medium.movementCoarseness * movement_mult;
        if (left)
            o.xy -= Medium.movementCoarseness * movement_mult;
        if (right)
            o.xy += Medium.movementCoarseness * movement_mult;
        if (up)
            o.zy -= Medium.movementCoarseness * movement_mult;
        if (down)
            o.zy += Medium.movementCoarseness * movement_mult;
        if (plus)
            o.y += Medium.movementCoarseness * movement_mult;
        if (minus)
            o.y -= Medium.movementCoarseness * movement_mult;
        if (in)
            o.z += (Medium.movementCoarseness + 5) * movement_mult;
        if (out)
            o.z -= (Medium.movementCoarseness + 5) * movement_mult;
        if (aa)
            ((Graphics2D) rd).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        else
            ((Graphics2D) rd).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        if (Medium.passthru == false)
            if (o.y > 235)
                o.y = 235;

        rd.setColor(new Color(0, 0, 0));
        
        if (mouses == MOUSE_PRESSED) {
            mouses = MOUSE_DOWN;
        } else if (mouses == MOUSE_RELEASED) {
            mouses = MOUSE_OFF;
        }
    }

    private void switchmode() {
        if (Medium.mode == 0)
            Medium.mode = 1;
        else
            Medium.mode = 0;
    }

    private Graphics rd;
    private BufferedImage offImage;
    ContO o;
    boolean aa;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;
    private boolean forward;
    private boolean back;
    private boolean rotl;
    private boolean rotr;
    private boolean plus;
    private boolean minus;
    private boolean in;
    private boolean out;
    boolean show3;
    private boolean axis;
    static boolean trans;

    private boolean shift;
    private boolean control;
    static byte mouses;
    
    /** the mouse's X coordinate */
    static int xm;
    
    /** the mouse's Y coordinate */
    static int ym;
    
    /** the Plane index which the mouse is inside */
    static int mouseInPoly;
    
    /** the mouseInPoly Plane's point which the mouse is closest to */
    static int mouseInPoint;

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final int i = e.getKeyCode();
        if (i == KeyEvent.VK_X)
            axis = true;
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
        if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY || i == KeyEvent.VK_OPEN_BRACKET)
            in = true;
        if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE || i == KeyEvent.VK_CLOSE_BRACKET)
            out = true;
        if (i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A)
            left = true;
        if (i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D)
            right = true;
        if (i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S)
            down = true;
        if (i == KeyEvent.VK_UP || i == KeyEvent.VK_W)
            up = true;
        if (i == KeyEvent.VK_O)
            trans = !trans;
        if (i == KeyEvent.VK_U)
            Medium.wire = !Medium.wire;
        if (i == KeyEvent.VK_P)
            Medium.pointwire = !Medium.pointwire;
        if (i == KeyEvent.VK_T)
            show3 = !show3;
        if (i == KeyEvent.VK_Z)
            switchmode();
        if (i == KeyEvent.VK_SHIFT)
            shift = true;
        if (i == KeyEvent.VK_CONTROL)
            control = true;
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int i = e.getKeyCode();
        if (i == KeyEvent.VK_X)
            axis = false;
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
        if (i == KeyEvent.VK_ASTERISK || i == KeyEvent.VK_MULTIPLY || i == KeyEvent.VK_OPEN_BRACKET)
            in = false;
        if (i == KeyEvent.VK_SLASH || i == KeyEvent.VK_DIVIDE || i == KeyEvent.VK_CLOSE_BRACKET)
            out = false;
        if (i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A)
            left = false;
        if (i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D)
            right = false;
        if (i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S)
            down = false;
        if (i == KeyEvent.VK_UP || i == KeyEvent.VK_W)
            up = false;
        if (i == KeyEvent.VK_SHIFT)
            shift = false;
        if (i == KeyEvent.VK_CONTROL)
            control = false;
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        float movement_mult = 1F;
        if (shift && control)
            movement_mult = 15F;
        else if (shift)
            movement_mult = 5F;
        else if (control)
            movement_mult = 0.5F;

        final int notches = e.getWheelRotation();
        if (notches < 0) {
            if (Medium.pushpull)
                o.z += Medium.movementCoarseness * movement_mult;
            else
                o.z -= Medium.movementCoarseness * movement_mult;
        } else if (Medium.pushpull)
            o.z -= Medium.movementCoarseness * movement_mult;
        else
            o.z += Medium.movementCoarseness * movement_mult;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        mouses = MOUSE_PRESSED;
        requestFocus();
        requestFocusInWindow();
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        mouses = MOUSE_RELEASED;
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        xm = e.getX();
        ym = e.getY();
    }

}

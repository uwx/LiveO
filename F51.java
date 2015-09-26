// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   F51.java

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class F51 extends Applet
    implements Runnable
{
    public F51()
    {
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
    }

    private AudioClip getSound(String s)
    {
        AudioClip audioclip = getAudioClip(getCodeBase(), s);
        audioclip.play();
        Thread.yield();
        audioclip.stop();
        return audioclip;
    }

    public void init()
    {
        //Dimension dimension = getSize();
        offImage = createImage(700, 460);
        if(offImage != null)
            rd = offImage.getGraphics();
    }

    /*public boolean keyDown(Event event, int i)
    {
        if(i == 56)
            forward = true;
        if(i == 50)
            back = true;
        if(i == 119)
            if(o.wire)
                o.wire = false;
            else
                o.wire = true;
        if(i == 54)
            rotr = true;
        if(i == 52)
            rotl = true;
        if(i == 43)
            plus = true;
        if(i == 45)
            minus = true;
        if(i == 42)
            in = true;
        if(i == 47)
            out = true;
        if(i == 1006)
            left = true;
        if(i == 1007)
            right = true;
        if(i == 1005)
            down = true;
        if(i == 1004)
            up = true;
        if(i == 86 || i == 111)
            trans = !trans;
        return false;
    }

    public boolean keyUp(Event event, int i)
    {
        if(i == 56)
            forward = false;
        if(i == 50)
            back = false;
        if(i == 54)
            rotr = false;
        if(i == 52)
            rotl = false;
        if(i == 43)
            plus = false;
        if(i == 45)
            minus = false;
        if(i == 42)
            in = false;
        if(i == 47)
            out = false;
        if(i == 1006)
            left = false;
        if(i == 1007)
            right = false;
        if(i == 1005)
            down = false;
        if(i == 1004)
            up = false;
        return false;
    }
*/
    public void paint(Graphics g)
    {
        g.drawImage(offImage, 0, 0, this);
    }
    
    public void remake()
    {
        int storeowxz = o.wxz;
        int storeoxz = o.xz;
        int storeoxy = o.xy;
        int storeozy = o.zy;
        int storeoy = o.y;
        int storeoz = o.z;
    	o = new ContO("o", medium, 350, 150, 600, this);
        o.wxz = storeowxz;
        o.xz = storeoxz;
        o.xy = storeoxy;
        o.zy = storeozy;
        o.y = storeoy;
        o.z = storeoz;
    }

    public void run()
    {
        gamer.setPriority(10);
        medium = new Medium();
        o = new ContO("o", medium, 350, 150, 600, this);
        o.y = 120;
        o.z += 200;
        medium.y -= 300;
        medium.zy += 10;
        do
        {
            medium.d(rd);
            o.d(rd);
            if(show3)
                medium.d3p(rd);
            if(forward)
                o.wxz += 5;
            if(back)
                o.wxz -= 5;
            if(rotr)
                o.xz -= 5;
            if(rotl)
                o.xz += 5;
            if(left)
                o.xy -= 5;
            if(right)
                o.xy += 5;
            if(up)
                o.zy -= 5;
            if(down)
                o.zy += 5;
            if(plus)
                o.y += 5;
            if(minus)
                o.y -= 5;
            if(in)
                o.z += 10;
            if(out)
                o.z -= 10;
            rd.setColor(new Color(0, 0, 0));
            repaint();
            try
            {
                Thread.sleep(10L);
            }
            catch(InterruptedException interruptedexception) { }
        } while(true);
    }

    public void switchmode()
    {
        if(medium.mode == 0)
            medium.mode = 1;
        else
            medium.mode = 0;
    }

    public void showthree()
    {
        if(show3)
            show3 = false;
        else
            show3 = true;
    }

    public void start()
    {
        if(gamer == null)
            gamer = new Thread(this);
        gamer.start();
    }

    public void stop()
    {
        if(gamer != null)
            gamer.stop();
        gamer = null;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    Graphics rd;
    Image offImage;
    Thread gamer;
    ContO o;
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

}

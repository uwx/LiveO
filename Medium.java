// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Medium.java

import java.awt.Color;
import java.awt.Graphics;

public class Medium
{

    public Medium()
    {
        tr = new Trackers();
        isun = false;
        focus_point = 400;
        ground = 250;
        skyline = -300;
        cx = 350;
        cy = 287;
        cz = 50;
        xz = 0;
        zy = 0;
        x = 0;
        y = 0;
        z = 0;
        w = 700;
        h = 475;
        tart = 0;
        yart = -100;
        zart = 0;
        ztgo = 0;
        mode = 0;
        lxp = new int[3];
        lyp = new int[3];
        td = false;
        vxz = 0;
        adv = -500;
        vert = false;
    }

    public void behinde(ContO conto)
    {
        int i = conto.zy;
        int j = conto.xz;
        for(; i > 360; i -= 360);
        for(; i < 0; i += 360);
        if(i > 90 && i < 270)
        {
            tart += (180 - tart) / 3;
            yart += (100 - yart) / 4;
        } else
        {
            tart -= tart / 3;
            yart += (-100 - yart) / 4;
        }
        j += tart;
        if(i > 90)
            i = 180 - i;
        if(i < -90)
            i = -180 - i;
        int k = conto.y + (int)((double)((conto.y + yart) - conto.y) * Math.cos((double)conto.zy * 0.017453292519943295D) - (double)(conto.z - 600 - conto.z) * Math.sin((double)conto.zy * 0.017453292519943295D));
        int l = conto.z + (int)((double)((conto.y + yart) - conto.y) * Math.sin((double)conto.zy * 0.017453292519943295D) + (double)(conto.z - 600 - conto.z) * Math.cos((double)conto.zy * 0.017453292519943295D));
        int i1 = conto.x + (int)((double)(-(l - conto.z)) * Math.sin((double)conto.xz * 0.017453292519943295D));
        int j1 = conto.z + (int)((double)(l - conto.z) * Math.cos((double)conto.xz * 0.017453292519943295D));
        zy = -i;
        xz = -j;
        x += (i1 - cx - x) / 3;
        z += (double)(j1 - cz - z) / 1.5D;
        y += (double)(k - cy - y) / 1.5D;
    }

    public void infront(ContO conto)
    {
        int i = conto.zy;
        int j = conto.xz;
        for(; i > 360; i -= 360);
        for(; i < 0; i += 360);
        if(i > 90 && i < 270)
        {
            tart += (180 - tart) / 3;
            yart += (100 - yart) / 3;
        } else
        {
            tart -= tart / 3;
            yart += (-100 - yart) / 3;
        }
        j += tart;
        if(i > 90)
            i = 180 - i;
        if(i < -90)
            i = -180 - i;
        int k = conto.y + (int)((double)((conto.y + yart) - conto.y) * Math.cos((double)conto.zy * 0.017453292519943295D) - (double)((conto.z + 800) - conto.z) * Math.sin((double)conto.zy * 0.017453292519943295D));
        int l = conto.z + (int)((double)((conto.y + yart) - conto.y) * Math.sin((double)conto.zy * 0.017453292519943295D) + (double)((conto.z + 800) - conto.z) * Math.cos((double)conto.zy * 0.017453292519943295D));
        int i1 = conto.x + (int)((double)(-(l - conto.z)) * Math.sin((double)conto.xz * 0.017453292519943295D));
        int j1 = conto.z + (int)((double)(l - conto.z) * Math.cos((double)conto.xz * 0.017453292519943295D));
        zy = i;
        xz = -(j + 180);
        x += (i1 - cx - x) / 3;
        z += (double)(j1 - cz - z) / 1.5D;
        y += (double)(k - cy - y) / 1.5D;
    }

    public void left(ContO conto)
    {
        int i = conto.y;
        int j = conto.x + (int)((double)((conto.x + 600) - conto.x) * Math.cos((double)conto.xz * 0.017453292519943295D));
        int k = conto.z + (int)((double)((conto.x + 600) - conto.x) * Math.sin((double)conto.xz * 0.017453292519943295D));
        zy = 0;
        xz = -(conto.xz + 90);
        x += (double)(j - cx - x) / 1.5D;
        z += (double)(k - cz - z) / 1.5D;
        y += (double)(i - cy - y) / 1.5D;
    }

    public void right(ContO conto)
    {
        int i = conto.y;
        int j = conto.x + (int)((double)(conto.x - 600 - conto.x) * Math.cos((double)conto.xz * 0.017453292519943295D));
        int k = conto.z + (int)((double)(conto.x - 600 - conto.x) * Math.sin((double)conto.xz * 0.017453292519943295D));
        zy = 0;
        xz = -(conto.xz - 90);
        x += (j - cx - x) / 3;
        z += (double)(k - cz - z) / 1.5D;
        y += (double)(i - cy - y) / 1.5D;
    }

    public void watch(ContO conto)
    {
        if(!td)
        {
            y = conto.y + (int)((double)(conto.y - 300 - conto.y) * Math.cos((double)conto.zy * 0.017453292519943295D) - (double)((conto.z + 3000) - conto.z) * Math.sin((double)conto.zy * 0.017453292519943295D));
            int i = conto.z + (int)((double)(conto.y - 300 - conto.y) * Math.sin((double)conto.zy * 0.017453292519943295D) + (double)((conto.z + 3000) - conto.z) * Math.cos((double)conto.zy * 0.017453292519943295D));
            x = conto.x + (int)((double)((conto.x + 400) - conto.x) * Math.cos((double)conto.xz * 0.017453292519943295D) - (double)(i - conto.z) * Math.sin((double)conto.xz * 0.017453292519943295D));
            z = conto.z + (int)((double)((conto.x + 400) - conto.x) * Math.sin((double)conto.xz * 0.017453292519943295D) + (double)(i - conto.z) * Math.cos((double)conto.xz * 0.017453292519943295D));
            td = true;
        }
        char c = '\0';
        if(conto.x - x - cx > 0)
            c = '\264';
        int j = -(int)((double)(90 + c) + Math.atan((double)(conto.z - z) / (double)(conto.x - x - cx)) / 0.017453292519943295D);
        c = '\0';
        if(conto.y - y - cy < 0)
            c = '\uFF4C';
        int k = (int)Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx));
        int l = (int)((double)(90 + c) - Math.atan((double)k / (double)(conto.y - y - cy)) / 0.017453292519943295D);
        xz = j;
        zy += (l - zy) / 5;
        if((int)Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx) + (conto.y - y - cy) * (conto.y - y - cy)) > 3500)
            td = false;
    }

    public void around(ContO conto, int i)
    {
        byte byte0 = 1;
        if(i == 6000)
            byte0 = 2;
        y = conto.y + adv;
        x = conto.x + (int)((double)(((conto.x - i) + adv * byte0) - conto.x) * Math.cos((double)vxz * 0.017453292519943295D));
        z = conto.z + (int)((double)(((conto.x - i) + adv * byte0) - conto.x) * Math.sin((double)vxz * 0.017453292519943295D));
        if(i == 6000)
        {
            if(!vert)
                adv -= 10;
            else
                adv += 10;
            if(adv < -900)
                vert = true;
            if(adv > 1200)
                vert = false;
        } else
        {
            if(!vert)
                adv -= 2;
            else
                adv += 2;
            if(adv < -500)
                vert = true;
            if(adv > 150)
                vert = false;
            if(adv > 300)
                adv = 300;
        }
        vxz += 2;
        if(vxz > 360)
            vxz -= 360;
        char c = '\0';
        int j = y;
        if(j > 0)
            j = 0;
        if(conto.y - j - cy < 0)
            c = '\uFF4C';
        int k = (int)Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx));
        int l = (int)((double)(90 + c) - Math.atan((double)k / (double)(conto.y - j - cy)) / 0.017453292519943295D);
        xz = -vxz + 90;
        zy += (l - zy) / 10;
    }

    public void d3p(Graphics g)
    {
        g.setColor(new Color(255, 0, 0));
        for(int i = 0; i < 3; i++)
            g.fillOval(lxp[i], lyp[i], 4, 4);

        g.setColor(new Color(255, 255, 0));
        for(int j = 0; j < 3; j++)
            g.drawOval(lxp[j], lyp[j], 4, 4);

    }

    public void d(Graphics g)
    {
        if(zy > 90)
            zy = 90;
        if(zy < -90)
            zy = -90;
        if(y > 0)
            y = 0;
        ground = 250 - y;
        int ai[] = new int[4];
        int ai1[] = new int[4];
        int i = cgrnd[0];
        int j = cgrnd[1];
        int k = cgrnd[2];
        int l = h;
        for(int i1 = 0; i1 < 8; i1++)
        {
            int k1 = fade[i1];
            int i2 = ground;
            if(zy != 0)
            {
                i2 = cy + (int)((double)(ground - cy) * Math.cos((double)zy * 0.017453292519943295D) - (double)(fade[i1] - cz) * Math.sin((double)zy * 0.017453292519943295D));
                k1 = cz + (int)((double)(ground - cy) * Math.sin((double)zy * 0.017453292519943295D) + (double)(fade[i1] - cz) * Math.cos((double)zy * 0.017453292519943295D));
            }
            ai[0] = 0;
            ai1[0] = ys(i2, k1);
            if(ai1[0] < 0)
                ai1[0] = 0;
            ai[1] = 0;
            ai1[1] = l;
            ai[2] = w;
            ai1[2] = l;
            ai[3] = w;
            ai1[3] = ai1[0];
            l = ai1[0];
            if(i1 > 0)
            {
                i = (i * 3 + cfade[0]) / 4;
                j = (j * 3 + cfade[1]) / 4;
                k = (k * 3 + cfade[2]) / 4;
            }
            if(ai1[0] < h && ai1[1] > 0)
            {
                g.setColor(new Color(i, j, k));
                g.fillPolygon(ai, ai1, 4);
            }
        }

        i = csky[0];
        j = csky[1];
        k = csky[2];
        int j1 = 0;
        for(int l1 = 0; l1 < 8; l1++)
        {
            int j2 = fade[l1];
            int k2 = skyline;
            if(zy != 0)
            {
                k2 = cy + (int)((double)(skyline - cy) * Math.cos((double)zy * 0.017453292519943295D) - (double)(fade[l1] - cz) * Math.sin((double)zy * 0.017453292519943295D));
                j2 = cz + (int)((double)(skyline - cy) * Math.sin((double)zy * 0.017453292519943295D) + (double)(fade[l1] - cz) * Math.cos((double)zy * 0.017453292519943295D));
            }
            ai[0] = 0;
            ai1[0] = ys(k2, j2);
            if(ai1[0] > h)
                ai1[0] = h;
            ai[1] = 0;
            ai1[1] = j1;
            ai[2] = w;
            ai1[2] = j1;
            ai[3] = w;
            ai1[3] = ai1[0];
            j1 = ai1[0];
            if(l1 > 0)
            {
                i = (i * 3 + cfade[0]) / 4;
                j = (j * 3 + cfade[1]) / 4;
                k = (k * 3 + cfade[2]) / 4;
            }
            if(ai1[0] > 0 && ai1[1] < h)
            {
                g.setColor(new Color(i, j, k));
                g.fillPolygon(ai, ai1, 4);
            }
        }

        ai[0] = 0;
        ai1[0] = j1;
        ai[1] = 0;
        ai1[1] = l;
        ai[2] = w;
        ai1[2] = l;
        ai[3] = w;
        ai1[3] = j1;
        if(ai1[0] < h && ai1[1] > 0)
        {
            g.setColor(new Color(cfade[0], cfade[1], cfade[2]));
            g.fillPolygon(ai, ai1, 4);
        }
    }

    public int ys(int i, int j)
    {
        if(j < 10)
            j = 10;
        return ((j - focus_point) * (cy - i)) / j + i;
    }

    Trackers tr;
    boolean isun;
    int focus_point;
    int ground;
    int skyline;
    int csky[] = {
        145, 200, 255
    };
    int cgrnd[] = {
        180, 180, 180
    };
    int cfade[] = {
        255, 255, 255
    };
    int fade[] = {
        3000, 5000, 7000, 9000, 11000, 13000, 15000, 17000
    };
    int mom[];
    int cx;
    int cy;
    int cz;
    int xz;
    int zy;
    int x;
    int y;
    int z;
    int w;
    int h;
    int tart;
    int yart;
    int zart;
    int ztgo;
    int mode;
    int lxp[];
    int lyp[];
    boolean td;
    int vxz;
    int adv;
    boolean vert;
}

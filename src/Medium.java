
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Medium.java

import java.awt.Color;
import java.awt.Graphics;

final class Medium {
    
    private Medium() {}

    static void init() {
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
        mode = 0;
        lxp = new int[3];
        lyp = new int[3];
        lightson = true;
        skyState = true;
        infiniteDistance = false;
    }

    public static void d3p(final Graphics g) {
        g.setColor(new Color(255, 0, 0));
        for (int i = 0; i < 3; i++)
            g.fillOval(lxp[i], lyp[i], 4, 4);

        g.setColor(new Color(255, 255, 0));
        for (int j = 0; j < 3; j++)
            g.drawOval(lxp[j], lyp[j], 4, 4);

    }

    private static int xs(final int i, int i_338_) {
        if (i_338_ < cz)
            i_338_ = cz;
        return (i_338_ - focus_point) * (cx - i) / i_338_ + i;
    }

    private static void rot(final int[] is, final int[] is_331_, final int i, final int i_332_, final int i_333_,
            final int i_334_) {
        if (i_333_ != 0)
            for (int i_335_ = 0; i_335_ < i_334_; i_335_++) {
                final int i_336_ = is[i_335_];
                final int i_337_ = is_331_[i_335_];
                is[i_335_] = i + (int) ((i_336_ - i) * cos(i_333_) - (i_337_ - i_332_) * sin(i_333_));
                is_331_[i_335_] = i_332_ + (int) ((i_336_ - i) * sin(i_333_) + (i_337_ - i_332_) * cos(i_333_));
            }
    }

    public static void axis(final Graphics g, final ContO o) { ///I guess this needs to be "fixed", mang

        final int[] x_array = {
                50 + adna[0], -50 - adna[1], 0, 0, 0, 0
        };
        final int[] y_array = {
                0, 0, 50 + adna[2], -50 - adna[3], 0, 0
        };
        final int[] z_array = {
                0, 0, 0, 0, 50 + adna[4], -50 - adna[5]
        };
        for (int i_12_ = 0; i_12_ < 6; i_12_++) {
            x_array[i_12_] += o.x - Medium.x;
            y_array[i_12_] += o.y - Medium.y;
            z_array[i_12_] += o.z - Medium.z;
        }

        rot(x_array, y_array, o.x - Medium.x, o.y - Medium.y, o.xy, 6);

        rot(y_array, z_array, o.y - Medium.y, o.z - Medium.z, o.zy, 6);

        rot(x_array, z_array, o.x - Medium.x, o.z - Medium.z, o.xz, 6);

        rot(x_array, z_array, Medium.cx, Medium.cz, Medium.xz, 6);

        rot(y_array, z_array, Medium.cy, Medium.cz, Medium.zy, 6);

        final int[] is_13_ = new int[6];
        final int[] is_14_ = new int[6];
        for (int i_15_ = 0; i_15_ < 6; i_15_++) {
            is_13_[i_15_] = xs(x_array[i_15_], z_array[i_15_]);
            is_14_[i_15_] = ys(y_array[i_15_], z_array[i_15_]);
        }
        g.setColor(new Color(0, 150, 0));
        g.drawString("X+", is_13_[0] - 7, is_14_[0] + 4);
        g.drawString("-X", is_13_[1] - 5, is_14_[1] + 4);
        g.drawLine(is_13_[0], is_14_[0], is_13_[1], is_14_[1]);
        g.setColor(new Color(150, 0, 0));
        g.drawString("Y+", is_13_[2] - 7, is_14_[2] + 4);
        g.drawString("-Y", is_13_[3] - 5, is_14_[3] + 4);
        g.drawLine(is_13_[2], is_14_[2], is_13_[3], is_14_[3]);
        g.setColor(new Color(0, 0, 150));
        g.drawString("Z+", is_13_[4] - 7, is_14_[4] + 4);
        g.drawString("-Z", is_13_[5] - 5, is_14_[5] + 4);
        g.drawLine(is_13_[4], is_14_[4], is_13_[5], is_14_[5]);
        for (int i_16_ = 0; i_16_ < 6; i_16_++) {
            if (Math.abs(is_14_[i_16_] - 207) * 1.91F > Math.abs(is_13_[i_16_] - 350)) {
                if (Math.abs(Math.abs(is_14_[i_16_] - 207) - 170) > 10)
                    if (Math.abs(is_14_[i_16_] - 207) < 170)
                        adna[i_16_] += 10;
                    else
                        adna[i_16_] -= 10;
            } else if (Math.abs(Math.abs(is_13_[i_16_] - 350) - 338) > 10)
                if (Math.abs(is_13_[i_16_] - 350) < 338)
                    adna[i_16_] += 10;
                else
                    adna[i_16_] -= 10;
            if (adna[i_16_] > 276)
                adna[i_16_] = 276;
            if (adna[i_16_] < 0)
                adna[i_16_] = 0;
        }
    }

    public static void d(final Graphics g) {
        if (zy > 90)
            zy = 90;
        if (zy < -90)
            zy = -90;
        if (y > 0)
            y = 0;
        ground = 250 - y;

        /////sky code

        if(skyState){
        	final int ai[] = new int[4];
            final int ai1[] = new int[4];
            int i = cgrnd[0];
            int j = cgrnd[1];
            int k = cgrnd[2];
            int l = h;
            for (int i1 = 0; i1 < 8; i1++) {
                int k1 = fade[i1];
                int i2 = ground;
                if (zy != 0) {
                    i2 = cy + (int) ((ground - cy) * Math.cos(zy * 0.017453292519943295D)
                            - (fade[i1] - cz) * Math.sin(zy * 0.017453292519943295D));
                    k1 = cz + (int) ((ground - cy) * Math.sin(zy * 0.017453292519943295D)
                            + (fade[i1] - cz) * Math.cos(zy * 0.017453292519943295D));
                }
                ai[0] = 0;
                ai1[0] = ys(i2, k1);
                if (ai1[0] < 0)
                    ai1[0] = 0;
                ai[1] = 0;
                ai1[1] = l;
                ai[2] = w;
                ai1[2] = l;
                ai[3] = w;
                ai1[3] = ai1[0];
                l = ai1[0];
                if (i1 > 0) {
                    i = (i * 3 + cfade[0]) / 4;
                    j = (j * 3 + cfade[1]) / 4;
                    k = (k * 3 + cfade[2]) / 4;
                }
                if (ai1[0] < h && ai1[1] > 0) {
                    g.setColor(new Color(i, j, k));
                    g.fillPolygon(ai, ai1, 4);
                }
            }

            i = csky[0];
            j = csky[1];
            k = csky[2];
            int j1 = 0;
            for (int l1 = 0; l1 < 8; l1++) {
                int j2 = fade[l1];
                int k2 = skyline;
                if (zy != 0) {
                    k2 = cy + (int) ((skyline - cy) * Math.cos(zy * 0.017453292519943295D)
                            - (fade[l1] - cz) * Math.sin(zy * 0.017453292519943295D));
                    j2 = cz + (int) ((skyline - cy) * Math.sin(zy * 0.017453292519943295D)
                            + (fade[l1] - cz) * Math.cos(zy * 0.017453292519943295D));
                }
                ai[0] = 0;
                ai1[0] = ys(k2, j2);
                if (ai1[0] > h)
                    ai1[0] = h;
                ai[1] = 0;
                ai1[1] = j1;
                ai[2] = w;
                ai1[2] = j1;
                ai[3] = w;
                ai1[3] = ai1[0];
                j1 = ai1[0];
                if (l1 > 0) {
                    i = (i * 3 + cfade[0]) / 4;
                    j = (j * 3 + cfade[1]) / 4;
                    k = (k * 3 + cfade[2]) / 4;
                }
                if (ai1[0] > 0 && ai1[1] < h) {
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
            if (ai1[0] < h && ai1[1] > 0) {
                g.setColor(new Color(cfade[0], cfade[1], cfade[2]));
                g.fillPolygon(ai, ai1, 4);
            }
        }else{
			g.setColor(Color.green);
			g.fillRect(0, 0, w, h);
        }

    }

    private static float sin(int i) {
        for (/**/; i >= 360; i -= 360) {
            /* empty */
        }
        for (/**/; i < 0; i += 360) {
            /* empty */
        }
        return tsin[i];
    }

    private static float cos(int i) {
        for (/**/; i >= 360; i -= 360) {
            /* empty */
        }
        for (/**/; i < 0; i += 360) {
            /* empty */
        }
        return tcos[i];
    }

    private static int ys(final int i, int j) {
        if (j < 10)
            j = 10;
        return (j - focus_point) * (cy - i) / j + i;
    }

    static int focus_point;
    static int ground;
    private static int skyline;
    
    static final int csky[] = {
            170, 220, 255
    };
    static final int cgrnd[] = {
            205, 200, 200
    };
    static final int cfade[] = {
            255, 220, 220
    };
    static final int fade[] = {
            3000, 5000, 7000, 9000, 11000, 13000, 15000, 17000
    };
    
    static int cx;
    static int cy;
    static int cz;
    static int xz;
    static int zy;
    
    static int x;
    static int y;
    static int z;
    static int w;
    static int h;
    static int mode;
    
    static int lxp[];
    static int lyp[];

    static boolean infiniteDistance;
    static boolean skyState;

    static boolean hideoutlines = false;
    static boolean pushpull = true;
    static boolean passthru = true;

    static int movementCoarseness = 5;

    static int autorotateCoarseness = 2;
    static boolean autorotateDirection;
    static boolean autorotate;

    static boolean lightson;
    static boolean pointwire = false;
    static boolean wire = false;
    static boolean snapEnabled = true;
    static boolean fullBright = true;


    private static int[] adna = {
            276, 276, 276, 276, 276, 276
    };

    private static final float[] tsin = new float[360];
    private static final float[] tcos = new float[360];

    static final short[] snap = {0, 0, 0};



    public static void setfade(final int r, final int g, final int b) {
        cfade[0] = (short) (r + r * (snap[0] / 100.0F));
        if (cfade[0] > 255) {
            cfade[0] = 255;
        }
        if (cfade[0] < 0) {
            cfade[0] = 0;
        }
        cfade[1] = (short) (g + g * (snap[1] / 100.0F));
        if (cfade[1] > 255) {
            cfade[1] = 255;
        }
        if (cfade[1] < 0) {
            cfade[1] = 0;
        }
        cfade[2] = (short) (b + b * (snap[2] / 100.0F));
        if (cfade[2] > 255) {
            cfade[2] = 255;
        }
        if (cfade[2] < 0) {
            cfade[2] = 0;
        }
    }

    public static void setgrnd(final int r, final int g, final int b) {
        cgrnd[0] = (short) (r + r * (snap[0] / 100.0F));
        if (cgrnd[0] > 255) {
            cgrnd[0] = 255;
        }
        if (cgrnd[0] < 0) {
            cgrnd[0] = 0;
        }
        cgrnd[1] = (short) (g + g * (snap[1] / 100.0F));
        if (cgrnd[1] > 255) {
            cgrnd[1] = 255;
        }
        if (cgrnd[1] < 0) {
            cgrnd[1] = 0;
        }
        cgrnd[2] = (short) (b + b * (snap[2] / 100.0F));
        if (cgrnd[2] > 255) {
            cgrnd[2] = 255;
        }
        if (cgrnd[2] < 0) {
            cgrnd[2] = 0;
        }
    }

    public static void setsky(final int r, final int g, final int b) {
        csky[0] = (int) (r + r * (snap[0] / 100.0F));
        if (csky[0] > 255) {
            csky[0] = 255;
        }
        if (csky[0] < 0) {
            csky[0] = 0;
        }
        csky[1] = (int) (g + g * (snap[1] / 100.0F));
        if (csky[1] > 255) {
            csky[1] = 255;
        }
        if (csky[1] < 0) {
            csky[1] = 0;
        }
        csky[2] = (int) (b + b * (snap[2] / 100.0F));
        if (csky[2] > 255) {
            csky[2] = 255;
        }
        if (csky[2] < 0) {
            csky[2] = 0;
        }
    }

    public static void setsnap(final short r, final short g, final short b) {
        snap[0] = r;
        snap[1] = g;
        snap[2] = b;
    }
}

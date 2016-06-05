
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Plane.java

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;

final class Plane {

    /**
     * Setting to true makes the outline glow render faster, but not look as nice.
     */
    private static final boolean OUTLINE_GLOW_FAST = false;

    public Plane(final int ai[], final int ai1[], final int ai2[], final int i, final int ai3[],
            final boolean flag, final int j, final int k, final int l, final int i1, final byte light,
            final boolean hidepoly, final boolean randomcolor, final boolean randoutline, final boolean customstroke,
            final int strokewidth, final int strokecap, final int strokejoin, final int strokemtlimit) {
        this.randoutline = randoutline;
        this.randomcolor = randomcolor;
        this.hidepoly = hidepoly;
        this.light = light;
        //stroke
        this.customstroke = customstroke;
        this.strokewidth = strokewidth;
        this.strokecap = strokecap;
        this.strokejoin = strokejoin;
        this.strokemtlimit = strokemtlimit;
        c = new int[3];
        hsb = new float[3];
        glass = false;
        gr = 0;
        fs = 0;
        wx = 0;
        wz = 0;
        deltaf = 1.0F;
        projf = 1.0F;
        av = 0;
        imlast = false;
        n = i;
        ox = new int[n];
        oz = new int[n];
        oy = new int[n];
        for (int j1 = 0; j1 < n; j1++) {
            ox[j1] = ai[j1];
            oy[j1] = ai2[j1];
            oz[j1] = ai1[j1];
        }

        glass = flag;
        if (!glass)
            for (int k1 = 0; k1 < 3; k1++)
                c[k1] = ai3[k1];
        else
            for (int l1 = 0; l1 < 3; l1++)
                c[l1] = Medium.csky[l1];
        Color.RGBtoHSB(c[0], c[1], c[2], hsb);
        gr = j;
        fs = k;
        wx = l;
        wz = i1;
        for (int i2 = 0; i2 < 3; i2++)
            for (int j2 = 0; j2 < 3; j2++)
                if (j2 != i2)
                    deltaf *= (float) (Math.sqrt((ox[j2] - ox[i2]) * (ox[j2] - ox[i2])
                            + (oy[j2] - oy[i2]) * (oy[j2] - oy[i2]) + (oz[j2] - oz[i2]) * (oz[j2] - oz[i2])) / 100D);

        deltaf /= 3F;

        this.x = new int[n];
        this.z = new int[n];
        this.y = new int[n];
    }

    void loadprojf() {
        projf = 1.0F;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (j != i)
                    projf *= (float) (Math.sqrt((ox[i] - ox[j]) * (ox[i] - ox[j]) + (oz[i] - oz[j]) * (oz[i] - oz[j]))
                            / 100D);

        projf /= 3F;
    }

    public void d(final Graphics2D g, final int i, final int j, final int k, final int l, final int i1, final int j1,
            final int k1, boolean toofar, int im) {
        /*final int out[][] = RunApp.make2d(ai, ai1, ai2, n, m);

        for (int i6 = 0; i6 < 3; i6++) {
            m.lxp[i6] = out[i6][0];
            m.lyp[i6] = out[i6][1];
        }*/

        for (int l1 = 0; l1 < n; l1++) {
            x[l1] = ox[l1] + i;
            y[l1] = oy[l1] + j;
            z[l1] = oz[l1] + k;
        }

        if (wx != 0)
            rot(x, z, wx + i, wz + k, k1, n);
        rot(x, y, i, j, i1, n);
        rot(y, z, j, k, j1, n);
        rot(x, z, i, k, l, n);
        if (i1 != 0 || j1 != 0 || l != 0) {
            projf = 1.0F;
            for (int i2 = 0; i2 < 3; i2++)
                for (int j2 = 0; j2 < 3; j2++)
                    if (j2 != i2)
                        projf *= (float) (Math
                                .sqrt((x[i2] - x[j2]) * (x[i2] - x[j2]) + (z[i2] - z[j2]) * (z[i2] - z[j2]))
                                / 100D);

            projf /= 3F;
        }
        rot(x, z, Medium.cx, Medium.cz, Medium.xz, n);
        boolean flag2 = false;
        final int ai3[] = new int[n];
        final int ai4[] = new int[n];
        int k2 = 500;
        for (int l2 = 0; l2 < n; l2++) {
            ai3[l2] = xs(x[l2], z[l2]);
            ai4[l2] = ys(y[l2], z[l2]);
        }

        int i3 = 0;
        int j3 = 1;
        for (int k3 = 0; k3 < n; k3++)
            for (int j4 = 0; j4 < n; j4++)
                if (k3 != j4 && Math.abs(ai3[k3] - ai3[j4]) - Math.abs(ai4[k3] - ai4[j4]) < k2) {
                    j3 = k3;
                    i3 = j4;
                    k2 = Math.abs(ai3[k3] - ai3[j4]) - Math.abs(ai4[k3] - ai4[j4]);
                }

        if (ai4[i3] < ai4[j3]) {
            final int l3 = i3;
            i3 = j3;
            j3 = l3;
        }
        if (spy(x[i3], z[i3]) > spy(x[j3], z[j3])) {
            flag2 = true;
            int i4 = 0;
            for (int k4 = 0; k4 < n; k4++)
                if (z[k4] < 50 && y[k4] > Medium.cy)
                    flag2 = false;
                else if (y[k4] == y[0])
                    i4++;

            if (i4 == n && y[0] > Medium.cy)
                flag2 = false;
        }
        rot(y, z, Medium.cy, Medium.cz, Medium.zy, n);
        boolean flag3 = true;
        final int xPoints[] = new int[n];
        final int yPoints[] = new int[n];
        int l4 = 0;
        int i5 = 0;
        int j5 = 0;
        int k5 = 0;
        for (int l5 = 0; l5 < n; l5++) {
            xPoints[l5] = xs(x[l5], z[l5]);
            yPoints[l5] = ys(y[l5], z[l5]);
            if (yPoints[l5] < 0 || z[l5] < 10)
                l4++;
            if (yPoints[l5] > Medium.h || z[l5] < 10)
                i5++;
            if (xPoints[l5] < 0 || z[l5] < 10)
                j5++;
            if (xPoints[l5] > Medium.w || z[l5] < 10)
                k5++;
        }

        if (j5 == n || l4 == n || i5 == n || k5 == n) {
            flag3 = false;
            toofar = true;
        }
        if (flag3) {
            if (imlast)
                for (int i6 = 0; i6 < 3; i6++) {
                    Medium.lxp[i6] = xPoints[i6];
                    Medium.lyp[i6] = yPoints[i6];
                }
            int j6 = 1;
            byte byte0 = 1;
            byte byte1 = 1;
            if (Math.abs(yPoints[0] - yPoints[1]) > Math.abs(yPoints[2] - yPoints[1])) {
                byte0 = 0;
                byte1 = 2;
            } else {
                byte0 = 2;
                byte1 = 0;
                j6 *= -1;
            }
            if (yPoints[1] > yPoints[byte0])
                j6 *= -1;
            if (xPoints[1] > xPoints[byte1])
                j6 *= -1;
            int l6 = gr;
            if (fs != 0) {
                j6 *= fs;
                if (j6 == -1) {
                    l6 += 40;
                    if (Medium.mode == 0)
                        flag3 = false;
                }
            }
            int j7 = 0;
            int l7 = 0;
            int j8 = 0;
            int k8 = 0;
            int l8 = 0;
            int i9 = 0;
            for (int j9 = 0; j9 < n; j9++) {
                int l9 = 0;
                int j10 = 0;
                int l10 = 0;
                int i11 = 0;
                int j11 = 0;
                int k11 = 0;
                for (int l11 = 0; l11 < n; l11++) {
                    if (y[j9] >= y[l11])
                        l9++;
                    if (y[j9] <= y[l11])
                        j10++;
                    if (x[j9] >= x[l11])
                        l10++;
                    if (x[j9] <= x[l11])
                        i11++;
                    if (z[j9] >= z[l11])
                        j11++;
                    if (z[j9] <= z[l11])
                        k11++;
                }

                if (l9 == n)
                    j7 = y[j9];
                if (j10 == n)
                    l7 = y[j9];
                if (l10 == n)
                    j8 = x[j9];
                if (i11 == n)
                    k8 = x[j9];
                if (j11 == n)
                    l8 = z[j9];
                if (k11 == n)
                    i9 = z[j9];
            }
            if(!Medium.infiniteDistance){
            	final int k9 = (j7 + l7) / 2;
                final int i10 = (j8 + k8) / 2;
                final int k10 = (l8 + i9) / 2;
                av = (int) Math.sqrt((Medium.cy - k9) * (Medium.cy - k9) + (Medium.cx - i10) * (Medium.cx - i10) + k10 * k10 + l6 * l6 * l6);
                if (av > Medium.fade[7] || av == 0) {
                    flag3 = false;
                    toofar = true;
                }
                if (l6 > 0 && av > 2500)
                    flag3 = false;
                if (l6 > 0 && av > 1000)
                    toofar = true;
                if (av > 2000)
                    toofar = true;
            }
        }
        if (flag3 && !Medium.wire && !Medium.pointwire) {
            float f = 1.0F;
            if (!Medium.fullBright)
                f = (float) (projf / deltaf + 0.5D);
        	if (f > 1.0F)
                f = 1.0F;
            if (f < 0.5D || flag2)
                f = 0.5F;
            if (toofar)
                f = (float) (f * 0.90000000000000002D);

            //new Color(c[0], c[1], c[2]);
            final Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * f);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            if (Medium.snapEnabled) {
                red = (short) (red + red * (Medium.snap[0] / 100.0F));
                if (red > 255) {
                    red = 255;
                }
                if (red < 0) {
                    red = 0;
                }
                green = (short) (green + green * (Medium.snap[1] / 100.0F));
                if (green > 255) {
                    green = 255;
                }
                if (green < 0) {
                    green = 0;
                }
                blue = (short) (blue + blue * (Medium.snap[2] / 100.0F));
                if (blue > 255) {
                    blue = 255;
                }
                if (blue < 0) {
                    blue = 0;
                }
            }

            // POLY MOUSE-TRACE
            Polygon p = new Polygon(xPoints, yPoints, n);
            if (p.contains(F51.xm, F51.ym)) {
                F51.mouseInPoly = im;
                
                // TRACE POINT
                int maxdist = Integer.MAX_VALUE;
                int maxdist_index = -1;
                
                for (int index = 0; index < p.npoints; index++) {
                    
                    int pdist = distance(p.xpoints[index], p.ypoints[index], F51.xm, F51.ym);
                    if (pdist < maxdist) {
                        maxdist = pdist;
                        maxdist_index = index;
                    }
                    
                    //System.out.println("Currently at: " + coords[0] + " " + coords[1]);
                }
                
                F51.mouseInPoint = maxdist_index;

                red = 100;
                green = 255;
                blue = 100;
            }

            if (!Medium.infiniteDistance) {
            	for (int i8 = 0; i8 < 8; i8++)
                if (av > Medium.fade[i8]) {
                    red = (red * 3 + Medium.cfade[0]) / 4;
                    green = (green * 3 + Medium.cfade[1]) / 4;
                    blue = (blue * 3 + Medium.cfade[2]) / 4;
                }
            }
            if (!randomcolor)
                g.setColor(new Color(red, green, blue));
            else
                g.setColor(Color.getHSBColor((float) Math.random(), (float) Math.random(), (float) Math.random()));
            
            g.fillPolygon(p);

            if (OUTLINE_GLOW_FAST) {
                final int[] axPoints = new int[n];
                final int[] ayPoints = new int[n];
                final int[] centroid = centroid(p);
                
                for (int ii = 3, imult = 9; ii > 0; ii--, imult = ii * 3) {
                    for (int l5 = 0; l5 < n; l5++) {
                        if (xPoints[l5] > centroid[0]) {
                            axPoints[l5] = xPoints[l5] + imult;
                        } else {
                            axPoints[l5] = xPoints[l5] - imult;
                        }
    
                        if (yPoints[l5] > centroid[1]) {
                            ayPoints[l5] = yPoints[l5] + imult;
                        } else {
                            ayPoints[l5] = yPoints[l5] - imult;
                        }
                    }
                    
                    g.setColor(new Color(red, green, blue, 50 - ii * 7));
                    g.fillPolygon(axPoints, ayPoints, n);
                }
            } else {
                AffineTransform t = g.getTransform();
                for (int ii = 3, ij = 0; ii > 0; ii--, ij++) {
                    g.setTransform(tsc[ij]);
                    
                    g.setColor(new Color(red, green, blue, 50 - ii * 7));
                    
                    g.fillPolygon(p);
                }
                g.setTransform(t);

            }
        }
        if (!toofar && !hidepoly && !Medium.hideoutlines) {
            int k6;
            int i7;
            int k7;
            if (Medium.lightson && light != 0) {
                k6 = c[0];
                if (k6 > 255)
                    k6 = 255;
                if (k6 < 0)
                    k6 = 0;
                i7 = c[1];
                if (i7 > 255)
                    i7 = 255;
                if (i7 < 0)
                    i7 = 0;
                k7 = c[2];
                if (k7 > 255)
                    k7 = 255;
                if (k7 < 0)
                    k7 = 0;
                g.setColor(new Color(k6, i7, k7));
            } else if (randoutline)
                g.setColor(Color.getHSBColor((float) Math.random(), (float) Math.random(), (float) Math.random()));
            else
                g.setColor(new Color(0, 0, 0));
            if (customstroke)
                g.setStroke(new BasicStroke(strokewidth, strokecap, strokejoin, strokemtlimit));
            else
                g.setStroke(new BasicStroke());
                        
            if (Medium.pointwire)
                for (int z = 0; z < n; z++)
                    g.fillRect(xPoints[z], yPoints[z], 1, 1);
            else
                g.drawPolygon(xPoints, yPoints, n);
        }
    }

    public void s(final Graphics g, final int i, final int j, final int k, final int l, final int i1, final int j1) {
        if (gr <= 0 && av < Medium.fade[7] && av != 0) {
            final int ai[] = new int[n];
            final int ai1[] = new int[n];
            final int ai2[] = new int[n];
            for (int k1 = 0; k1 < n; k1++) {
                ai[k1] = ox[k1] + i;
                ai2[k1] = oy[k1] + j;
                ai1[k1] = oz[k1] + k;
            }

            rot(ai, ai2, i, j, i1, n);
            rot(ai2, ai1, j, k, j1, n);
            rot(ai, ai1, i, k, l, n);
            int l1 = (int) (Medium.cgrnd[0] / 1.5D);
            int i2 = (int) (Medium.cgrnd[1] / 1.5D);
            int j2 = (int) (Medium.cgrnd[2] / 1.5D);
            for (int k2 = 0; k2 < n; k2++)
                ai2[k2] = Medium.ground;

            for (int l2 = 0; l2 < Trackers.nt; l2++)
                if (Trackers.in[l2]) {
                    int i3 = 0;
                    for (int j3 = 0; j3 < n; j3++)
                        if (Math.abs(ai[j3] - Trackers.x[l2]) < Trackers.radx[l2]
                                && Math.abs(ai1[j3] - Trackers.z[l2]) < Trackers.radz[l2])
                            i3++;

                    if (i3 == n) {
                        for (int k3 = 0; k3 < n; k3++)
                            ai2[k3] = Trackers.y[l2];

                        if (Trackers.xy[l2] != 0) {
                            for (int l3 = 0; l3 < n; l3++) {
                                ai[l3] -= i;
                                ai[l3] = (int) (ai[l3] * (1.0D / Math.cos(Trackers.xy[l2] * 0.017453292519943295D)));
                                ai[l3] += i;
                            }

                            rot(ai, ai2, Trackers.x[l2], Trackers.y[l2], Trackers.xy[l2], n);
                        }
                        if (Trackers.zy[l2] != 0) {
                            for (int i4 = 0; i4 < n; i4++) {
                                ai1[i4] -= k;
                                ai1[i4] = (int) (ai1[i4] * (1.0D / Math.cos(Trackers.zy[l2] * 0.017453292519943295D)));
                                ai1[i4] += k;
                            }

                            rot(ai1, ai2, Trackers.z[l2], Trackers.y[l2], Trackers.zy[l2], n);
                        }
                        l1 = (int) (Trackers.c[l2][0] / 1.5D);
                        i2 = (int) (Trackers.c[l2][1] / 1.5D);
                        j2 = (int) (Trackers.c[l2][2] / 1.5D);
                    }
                }

            rot(ai, ai1, Medium.cx, Medium.cz, Medium.xz, n);
            rot(ai2, ai1, Medium.cy, Medium.cz, Medium.zy, n);
            boolean flag1 = false;
            final int ai3[] = new int[n];
            final int ai4[] = new int[n];
            for (int j4 = 0; j4 < n; j4++) {
                ai3[j4] = xs(ai[j4], ai1[j4]);
                ai4[j4] = ys(ai2[j4], ai1[j4]);
                if (ai4[j4] > 0 && ai4[j4] < Medium.h && ai3[j4] > 0 && ai3[j4] < Medium.w && ai1[j4] > 10)
                    flag1 = true;
            }

            if (flag1 && Medium.skyState) {
                for (int k4 = 0; k4 < 8; k4++)
                    if (av > Medium.fade[k4]) {
                        l1 = (l1 * 3 + Medium.cfade[0]) / 4;
                        i2 = (i2 * 3 + Medium.cfade[1]) / 4;
                        j2 = (j2 * 3 + Medium.cfade[2]) / 4;
                    }

                g.setColor(new Color(l1, i2, j2));
                g.fillPolygon(ai3, ai4, n);
            }
        }
    }

    private int xs(final int i, int j) {
        if (j < 10)
            j = 10;
        return (j - Medium.focus_point) * (Medium.cx - i) / j + i;
    }

    private int ys(final int i, int j) {
        if (j < 10)
            j = 10;
        return (j - Medium.focus_point) * (Medium.cy - i) / j + i;
    }

    public static void rot(final int ai[], final int ai1[], final int i, final int j, final int k, final int l) {
        if (k != 0)
            for (int i1 = 0; i1 < l; i1++) {
                final int j1 = ai[i1];
                final int k1 = ai1[i1];
                ai[i1] = i + (int) ((j1 - i) * Math.cos(k * 0.017453292519943295D)
                        - (k1 - j) * Math.sin(k * 0.017453292519943295D));
                ai1[i1] = j + (int) ((j1 - i) * Math.sin(k * 0.017453292519943295D)
                        + (k1 - j) * Math.cos(k * 0.017453292519943295D));
            }
    }

    private static int spy(final int i, final int j) {
        return (int) Math.sqrt((i - Medium.cx) * (i - Medium.cx) + j * j);
    }
    
    private static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
    }

    int ox[];
    int oy[];
    int oz[];
    int n;
    int c[];
    private float hsb[];
    boolean glass;
    boolean randomcolor;
    boolean hidepoly;
    boolean randoutline;
    byte light;
    int gr;
    int fs;
    int wx;
    int wz;
    private float deltaf;
    private float projf;
    int av;
    boolean imlast;
    boolean customstroke;
    int strokewidth;
    int strokecap;
    int strokejoin;
    int strokemtlimit;
        
    private final int x[];
    private final int z[];
    private final int y[];
    
    private static final AffineTransform tsc[] = !OUTLINE_GLOW_FAST ? new AffineTransform[3] : new AffineTransform[0];
    
    static {
        if (!OUTLINE_GLOW_FAST) {
            for (int i = 0; i < 3; i++) {
                tsc[i] = new AffineTransform();
                tsc[i].translate(Medium.w / 2, Medium.h / 2);
                tsc[i].scale(1.0 + (i * 0.05), 1.0 + (i * 0.05));
                tsc[i].translate(-(Medium.w / 2), -(Medium.h / 2));
            }
        }
    }

    public static int[] centroid(Polygon p) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < p.npoints - 1; i++) {
            x += p.xpoints[i];
            y += p.ypoints[i];
        }
        if (p.xpoints[0] != p.xpoints[p.npoints - 1] || p.ypoints[0] != p.ypoints[p.npoints - 1]) {
            x += p.xpoints[p.npoints - 1];
            y += p.ypoints[p.npoints - 1];
        }

        x = (int) ((float)x/p.npoints); // - 1?
        y = (int) ((float)y/p.npoints);

        return new int[] {x, y};
    }
    
    public static int[] centroid(int[] xpoints, int[] ypoints, int npoints) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < npoints - 1; i++) {
            x += xpoints[i];
            y += ypoints[i];
        }
        if (xpoints[0] != xpoints[npoints - 1] || ypoints[0] != ypoints[npoints - 1]) {
            x += xpoints[npoints - 1];
            y += ypoints[npoints - 1];
        }

        x = (int) ((float)x/npoints);
        y = (int) ((float)y/npoints);

        return new int[] {x, y};
    }
}

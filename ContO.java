
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:  ContO.java

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class ContO {

	public int g_div;
	public int g_idiv;
	public int g_iwid;
	public int g_scalex;
	public int g_scaley;
	public int g_scalez;

    public ContO(final String s, final Medium medium, final int i, final int j, final int k) throws Exception {
        npl = 0;
        x = 0;
        y = 0;
        z = 0;
        xz = 0;
        xy = 0;
        zy = 0;
        wxz = 0;
        dist = 0;
        maxR = 0;
        disp = 0;
        shadow = false;
        stonecold = false;
        loom = false;
        grounded = 1;
        colides = false;
        rcol = 0;
        pcol = 0;
        track = -2;
        out = false;
        nhits = 0;
        maxhits = -1;
        grat = 0;
        m = medium;
        p = new Plane[0x186a0];
        x = i;
        y = j;
        z = k;
        boolean flag = false;
        int nPoints = 0;
        float div = 1.0F;
        final int pointX[] = new int[350];
        final int pointY[] = new int[350];
        final int pointZ[] = new int[350];
        final int color[] = new int[3];
        boolean glass = false;
        final Wheels wheels = new Wheels();
        int gr = 1;
        int fs = 0;

        float wid = 1.0F;
        boolean hidepoly = false;
        boolean randomcolor = false;
        boolean randoutline = false;
        byte light = 0;

        boolean customstroke = false;
        int strokewidth = 1;
        int strokecap = BasicStroke.CAP_BUTT;
        int strokejoin = BasicStroke.JOIN_MITER;
        int strokemtlimit = 10;

        final float nfmm_scale[] = {
                1.0F, 1.0F, 1.0F
        };

        //final boolean hasTracks = false;
        //final boolean inTrack = false;

        try {
            //final File fl = new File(s);
            //System.out.println(s.getPath());
            //final DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            final BufferedReader datainputstream = new BufferedReader(new StringReader(s));
            do {
                String s1;
                if ((s1 = datainputstream.readLine()) == null)
                    break;
                final String line = new StringBuilder().append("").append(s1.trim()).toString();
                if (line.startsWith("<p>") && RunApp.showModel) {
                    flag = true;
                    nPoints = 0;
                    gr = 0;
                    fs = 0;
                    light = 0;
                    hidepoly = false;
                    randomcolor = false;
                    randoutline = false;
                    customstroke = false;
                    strokewidth = 1;
                    strokecap = BasicStroke.CAP_BUTT;
                    strokejoin = BasicStroke.JOIN_MITER;
                    strokemtlimit = 10;
                }
                if (flag) {
                    if (line.startsWith("gr("))
                        gr = getvalue("gr", line, 0);
                    if (line.startsWith("fs("))
                        fs = getvalue("fs", line, 0);
                    if (line.startsWith("glass"))
                        glass = true;
                    if (line.startsWith("c(")) {
                        if (glass)
                            glass = false;
                        color[0] = getvalue("c", line, 0);
                        color[1] = getvalue("c", line, 1);
                        color[2] = getvalue("c", line, 2);
                    }
                    if (line.startsWith("p(")) {
                        pointX[nPoints] = (int) ((int) (getvalue("p", line, 0) * div * wid) * nfmm_scale[0]);
                        pointY[nPoints] = (int) ((int) (getvalue("p", line, 1) * div) * nfmm_scale[1]);
                        pointZ[nPoints] = (int) ((int) (getvalue("p", line, 2) * div) * nfmm_scale[2]);
                        int maxKeks = (int)Math.sqrt(pointX[nPoints] * pointX[nPoints] + pointY[nPoints] * pointY[nPoints] + pointZ[nPoints] * pointZ[nPoints]);
                        if(maxKeks > maxR)
                        {
                        	//System.out.println(maxKeks);
                            maxR = maxKeks;
                        }
                        nPoints++;
                    }
                    if (line.startsWith("random()") || line.startsWith("rainbow()"))
                        randomcolor = true;
                    if (line.startsWith("randoutline()"))
                        randoutline = true;
                    if (line.startsWith("lightF"))
                        light = 1;
                    if (line.startsWith("lightB"))
                        light = 2;
                    if (line.startsWith("light()"))
                        light = 1;
                    if (line.startsWith("noOutline()"))
                        hidepoly = true;
                    if (line.startsWith("customOutline"))
                        customstroke = true;
                    if (line.startsWith("$outlineW("))
                        strokewidth = getvalue("$outlineW", line, 0);
                    if (line.startsWith("$outlineCap(")) {
                        if (line.startsWith("$outlineCap(butt)"))
                            strokecap = BasicStroke.CAP_BUTT;
                        if (line.startsWith("$outlineCap(round)"))
                            strokecap = BasicStroke.CAP_ROUND;
                        if (line.startsWith("$outlineCap(square)"))
                            strokecap = BasicStroke.CAP_SQUARE;
                    }
                    if (line.startsWith("$outlineJoin(")) {
                        if (line.startsWith("$outlineJoin(bevel)"))
                            strokejoin = BasicStroke.JOIN_BEVEL;
                        if (line.startsWith("$outlineJoin(miter)"))
                            strokejoin = BasicStroke.JOIN_MITER;
                        if (line.startsWith("$outlineJoin(round)"))
                            strokejoin = BasicStroke.JOIN_ROUND;
                    }
                    if (line.startsWith("$outlineMtlimit("))
                        strokemtlimit = getvalue("$outlineMtlimit", line, 0);

                }
                if (line.startsWith("</p>") && RunApp.showModel) {
                    p[npl] = new Plane(m, pointX, pointZ, pointY, nPoints, color, glass, gr, fs, 0, 0, light, hidepoly,
                            randomcolor, randoutline, customstroke, strokewidth, strokecap, strokejoin, strokemtlimit);
                    npl++;
                    flag = false;
                }
                if (line.startsWith("w") && RunApp.showModel)
                    npl += wheels.make(m, p, npl, (int) (getvalue("w", line, 0) * div * nfmm_scale[0]),
                            (int) (getvalue("w", line, 1) * div * nfmm_scale[1]),
                            (int) (getvalue("w", line, 2) * div * nfmm_scale[2]), getvalue("w", line, 3),
                            (int) (getvalue("w", line, 4) * div * nfmm_scale[0]), (int) (getvalue("w", line, 5) * div));
                        //npl += wheels.make(applet, m, p, npl, (int)((float)getvalue("w", s1, 0) * f * f1 * nfmm_scale[0]), (int)((float)getvalue("w", s1, 1) * f * nfmm_scale[1]), (int)((float)getvalue("w", s1, 2) * f * nfmm_scale[2]), getvalue("w", s1, 3), (int)((float)getvalue("w", s1, 4) * f * f1), (int)((int)getvalue("w", s1, 5) * f), i1);

                /*if (s2.startsWith("<track>"))
                  track = -1;
                if (track == -1) {
                  if (s2.startsWith("c")) {
                    m.tr.c[m.tr.nt][0] = getvalue("c", s2, 0);
                    m.tr.c[m.tr.nt][1] = getvalue("c", s2, 1);
                    m.tr.c[m.tr.nt][2] = getvalue("c", s2, 2);
                  }
                  if (s2.startsWith("xy"))
                    m.tr.xy[m.tr.nt] = getvalue("xy", s2, 0);
                  if (s2.startsWith("zy"))
                    m.tr.zy[m.tr.nt] = getvalue("zy", s2, 0);
                  if (s2.startsWith("radx"))
                    m.tr.radx[m.tr.nt] = (int) (getvalue("radx", s2, 0) * f);
                  if (s2.startsWith("radz"))
                    m.tr.radz[m.tr.nt] = (int) (getvalue("radz", s2, 0) * f);
                }
                if (s2.startsWith("</track>")) {
                  track = m.tr.nt;
                  m.tr.nt++;
                }*/
                if (m.tr.nt + 1 > m.tr.xy.length)
                    throw new RuntimeException("increase tracks()");
                if (line.startsWith("<track>")) {
                    m.tr.notwall[m.tr.nt] = false;
                    m.tr.dam[m.tr.nt] = 1;
                    m.tr.skd[m.tr.nt] = 0;
                    m.tr.y[m.tr.nt] = 0;
                    m.tr.x[m.tr.nt] = 0;
                    m.tr.z[m.tr.nt] = 0;
                    m.tr.xy[m.tr.nt] = 0;
                    m.tr.zy[m.tr.nt] = 0;
                    m.tr.rady[m.tr.nt] = 0;
                    m.tr.radx[m.tr.nt] = 0;
                    m.tr.radz[m.tr.nt] = 0;
                    m.tr.c[m.tr.nt][0] = 0;
                    m.tr.c[m.tr.nt][1] = 0;
                    m.tr.c[m.tr.nt][2] = 0;
                    track = -1;
                }
                if (track == -1) {
                    if (line.startsWith("c")) {
                        m.tr.c[m.tr.nt][0] = getvalue("c", line, 0);
                        m.tr.c[m.tr.nt][1] = getvalue("c", line, 1);
                        m.tr.c[m.tr.nt][2] = getvalue("c", line, 2);
                    }
                    if (line.startsWith("xy"))
                        m.tr.xy[m.tr.nt] = getvalue("xy", line, 0);
                    if (line.startsWith("zy"))
                        m.tr.zy[m.tr.nt] = getvalue("zy", line, 0);
                    if (line.startsWith("radx"))
                        m.tr.radx[m.tr.nt] = (int) (getvalue("radx", line, 0) * div);
                    if (line.startsWith("rady"))
                        m.tr.rady[m.tr.nt] = (int) (getvalue("rady", line, 0) * div);
                    if (line.startsWith("radz"))
                        m.tr.radz[m.tr.nt] = (int) (getvalue("radz", line, 0) * div);
                    if (line.startsWith("ty"))
                        m.tr.y[m.tr.nt] = (int) (getvalue("ty", line, 0) * div);
                    if (line.startsWith("tx"))
                        m.tr.x[m.tr.nt] = (int) (getvalue("tx", line, 0) * div);
                    if (line.startsWith("tz"))
                        m.tr.z[m.tr.nt] = (int) (getvalue("tz", line, 0) * div);
                    if (line.startsWith("skid"))
                        m.tr.skd[m.tr.nt] = getvalue("skid", line, 0);
                    if (line.startsWith("dam"))
                        m.tr.dam[m.tr.nt] = 3;
                    if (line.startsWith("notwall"))
                        m.tr.notwall[m.tr.nt] = true;
                }
                if (line.startsWith("</track>")) {
                    //

                    final int x1 = m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt];
                    final int x2 = m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt];
                    final int y1 = m.tr.y[m.tr.nt] - m.tr.rady[m.tr.nt];
                    final int y2 = m.tr.y[m.tr.nt] + m.tr.rady[m.tr.nt];
                    final int z1 = m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt];
                    final int z2 = m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt];

                    /*
                     * x = 200
                     * y = 100
                     * z = 300
                     *
                    <p>
                    p(-x,y,z)
                    p(-x,y,-z)
                    p(-x,-y,-z)
                    p(-x,-y,z) // human eyes careful
                    </p>

                    <p>
                    p(x,-y,z)
                    p(x,y,z)
                    p(-x,y,z)
                    p(-x,-y,z)
                    </p>

                    <p>
                    p(x,-y,z)
                    p(x,y,z) // human eyes careful
                    p(x,y,-z)
                    p(x,-y,-z)
                    </p>

                    <p>
                    p(x,y,-z)
                    p(-x,y,-z)
                    p(-x,-y,-z)
                    p(x,-y,-z)
                    </p>
                    */

                    final int ggr = 0;
                    //if (RunApp.solidsApproachScreen)
                    //    ggr = -51;
                    if (RunApp.showSolids) {
                        final int[] pc = {
                                255, 0, 0
                        };
                        final int[] px = {
                                x1, x1, x1, x1,
                        };
                        final int[] py = {
                                y2, y2, y1, y1,
                        };
                        final int[] pz = {
                                z2, z1, z1, z2,
                        };

                        p[npl] = new Plane(m, px, pz, py, 4, pc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] apc = {
                                0, 255, 0
                        };
                        final int[] apx = {
                                x2, x2, x1, x1,
                        };
                        final int[] apy = {
                                y1, y2, y2, y1,
                        };
                        final int[] apz = {
                                z2, z2, z2, z2,
                        };
                        p[npl] = new Plane(m, apx, apz, apy, 4, apc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] bpc = {
                                0, 0, 255
                        };
                        final int[] bpx = {
                                x2, x2, x2, x2,
                        };
                        final int[] bpy = {
                                y1, y2, y2, y1,
                        };
                        final int[] bpz = {
                                z2, z2, z1, z1,
                        };
                        p[npl] = new Plane(m, bpx, bpz, bpy, 4, bpc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] cpc = {
                                255, 255, 255
                        };
                        final int[] cpx = {
                                x2, x1, x1, x2,
                        };
                        final int[] cpy = {
                                y2, y2, y1, y1,
                        };
                        final int[] cpz = {
                                z1, z1, z1, z1,
                        };
                        p[npl] = new Plane(m, cpx, cpz, cpy, 4, cpc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;
                    }

                    /* Track Flats / Faces */
                    /* Captures RadX and RadZ, RadY can be interpreted/determined by model */
                    if (RunApp.showTrackFaces)
                        if ((m.tr.zy[m.tr.nt] == 90 || m.tr.zy[m.tr.nt] == -90) && m.tr.xy[m.tr.nt] == 0) {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        } else if ((m.tr.xy[m.tr.nt] == 90 || m.tr.xy[m.tr.nt] == -90) && m.tr.zy[m.tr.nt] == 0) {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        } else {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);
                            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        }
                    track = m.tr.nt;
                    m.tr.nt++;
                }
                m.tr.prepare();

                if (line.startsWith("MaxRadius"))
                    maxR = (int) (getvalue("MaxRadius", line, 0) * div);
                if (line.startsWith("disp"))
                    disp = getvalue("disp", line, 0);
                if (line.startsWith("shadow"))
                    shadow = true;
                if (line.startsWith("loom"))
                    loom = true;
                if (line.startsWith("out"))
                    out = true;
                if (line.startsWith("hits"))
                    maxhits = getvalue("hits", line, 0);
                if (line.startsWith("colid")) {
                    colides = true;
                    rcol = getvalue("colid", line, 0);
                    pcol = getvalue("colid", line, 1);
                }
                if (line.startsWith("grounded"))
                    grounded = getvalue("grounded", line, 0);
                if (line.startsWith("div")){
                    div = getvalue("div", line, 0) / 10F;
                    g_div = (int)getvalue("div", line, 0);
                }
                if (line.startsWith("idiv")){
                    div = getvalue("idiv", s1, 0) / 100F;
                    g_idiv = (int)getvalue("idiv", line, 0);
                }
                if (line.startsWith("iwid")){
                    wid = getvalue("iwid", s1, 0) / 100F;
                    g_iwid = (int)getvalue("iwid", line, 0);
                }
                if (line.startsWith("ScaleX")){
                    nfmm_scale[0] = getvalue("ScaleX", s1, 0) / 100F;
                    g_scalex = (int)getvalue("ScaleX", line, 0);
                }
                if (line.startsWith("ScaleY")){
                    nfmm_scale[1] = getvalue("ScaleY", s1, 0) / 100F;
                    g_scaley = (int)getvalue("ScaleY", line, 0);
                }
                if (line.startsWith("ScaleZ")){
                    nfmm_scale[2] = getvalue("ScaleZ", s1, 0) / 100F;
                    g_scalez = (int)getvalue("ScaleZ", line, 0);
                }
                if (line.startsWith("stonecold"))
                    stonecold = true;
            } while (true);
            datainputstream.close();
        } catch (final Exception exception) {
            throw exception;
        }
        System.out.println(new StringBuilder().append("polygantos: ").append(npl).toString());
        grat = wheels.ground;
        p[npl - 1].imlast = true;
    }

    public ContO(final DataInputStream s, final Medium medium, final int i, final int j, final int k) throws Exception {
        npl = 0;
        x = 0;
        y = 0;
        z = 0;
        xz = 0;
        xy = 0;
        zy = 0;
        wxz = 0;
        dist = 0;
        maxR = 0;
        disp = 0;
        shadow = false;
        stonecold = false;
        loom = false;
        grounded = 1;
        colides = false;
        rcol = 0;
        pcol = 0;
        track = -2;
        out = false;
        nhits = 0;
        maxhits = -1;
        grat = 0;
        m = medium;
        p = new Plane[0x186a0];
        x = i;
        y = j;
        z = k;
        boolean flag = false;
        int nPoints = 0;
        float div = 1.0F;
        final int pointX[] = new int[350];
        final int pointY[] = new int[350];
        final int pointZ[] = new int[350];
        final int color[] = new int[3];
        boolean glass = false;
        final Wheels wheels = new Wheels();
        int gr = 1;
        int fs = 0;

        float wid = 1.0F;
        boolean hidepoly = false;
        boolean randomcolor = false;
        boolean randoutline = false;
        byte light = 0;

        boolean customstroke = false;
        int strokewidth = 1;
        int strokecap = BasicStroke.CAP_BUTT;
        int strokejoin = BasicStroke.JOIN_MITER;
        int strokemtlimit = 10;

        final float nfmm_scale[] = {
                1.0F, 1.0F, 1.0F
        };

        //final boolean hasTracks = false;
        //final boolean inTrack = false;

        try {
            //final File fl = new File(s);
            //System.out.println(s.getPath());
            //final DataInputStream datainputstream = new DataInputStream(new FileInputStream(s));
            final BufferedReader datainputstream = new BufferedReader(new InputStreamReader(s));
            do {
                String s1;
                if ((s1 = datainputstream.readLine()) == null)
                    break;
                final String line = new StringBuilder().append("").append(s1.trim()).toString();
                if (line.startsWith("<p>") && RunApp.showModel) {
                    flag = true;
                    nPoints = 0;
                    gr = 0;
                    fs = 0;
                    light = 0;
                    hidepoly = false;
                    randomcolor = false;
                    randoutline = false;
                    customstroke = false;
                    strokewidth = 1;
                    strokecap = BasicStroke.CAP_BUTT;
                    strokejoin = BasicStroke.JOIN_MITER;
                    strokemtlimit = 10;
                }
                if (flag) {
                    if (line.startsWith("gr("))
                        gr = getvalue("gr", line, 0);
                    if (line.startsWith("fs("))
                        fs = getvalue("fs", line, 0);
                    if (line.startsWith("glass"))
                        glass = true;
                    if (line.startsWith("c(")) {
                        if (glass)
                            glass = false;
                        color[0] = getvalue("c", line, 0);
                        color[1] = getvalue("c", line, 1);
                        color[2] = getvalue("c", line, 2);
                    }
                    if (line.startsWith("p(")) {
                        pointX[nPoints] = (int) ((int) (getvalue("p", line, 0) * div * wid) * nfmm_scale[0]);
                        pointY[nPoints] = (int) ((int) (getvalue("p", line, 1) * div) * nfmm_scale[1]);
                        pointZ[nPoints] = (int) ((int) (getvalue("p", line, 2) * div) * nfmm_scale[2]);
                        int maxKeks = (int)Math.sqrt(pointX[nPoints] * pointX[nPoints] + pointY[nPoints] * pointY[nPoints] + pointZ[nPoints] * pointZ[nPoints]);
                        if(maxKeks > maxR)
                        {
                            //System.out.println(maxKeks);
                            maxR = maxKeks;
                        }
                        nPoints++;
                    }
                    if (line.startsWith("random()") || line.startsWith("rainbow()"))
                        randomcolor = true;
                    if (line.startsWith("randoutline()"))
                        randoutline = true;
                    if (line.startsWith("lightF"))
                        light = 1;
                    if (line.startsWith("lightB"))
                        light = 2;
                    if (line.startsWith("light()"))
                        light = 1;
                    if (line.startsWith("noOutline()"))
                        hidepoly = true;
                    if (line.startsWith("customOutline"))
                        customstroke = true;
                    if (line.startsWith("$outlineW("))
                        strokewidth = getvalue("$outlineW", line, 0);
                    if (line.startsWith("$outlineCap(")) {
                        if (line.startsWith("$outlineCap(butt)"))
                            strokecap = BasicStroke.CAP_BUTT;
                        if (line.startsWith("$outlineCap(round)"))
                            strokecap = BasicStroke.CAP_ROUND;
                        if (line.startsWith("$outlineCap(square)"))
                            strokecap = BasicStroke.CAP_SQUARE;
                    }
                    if (line.startsWith("$outlineJoin(")) {
                        if (line.startsWith("$outlineJoin(bevel)"))
                            strokejoin = BasicStroke.JOIN_BEVEL;
                        if (line.startsWith("$outlineJoin(miter)"))
                            strokejoin = BasicStroke.JOIN_MITER;
                        if (line.startsWith("$outlineJoin(round)"))
                            strokejoin = BasicStroke.JOIN_ROUND;
                    }
                    if (line.startsWith("$outlineMtlimit("))
                        strokemtlimit = getvalue("$outlineMtlimit", line, 0);

                }
                if (line.startsWith("</p>") && RunApp.showModel) {
                    p[npl] = new Plane(m, pointX, pointZ, pointY, nPoints, color, glass, gr, fs, 0, 0, light, hidepoly,
                            randomcolor, randoutline, customstroke, strokewidth, strokecap, strokejoin, strokemtlimit);
                    npl++;
                    flag = false;
                }
                if (line.startsWith("w") && RunApp.showModel)
                    npl += wheels.make(m, p, npl, (int) (getvalue("w", line, 0) * div * nfmm_scale[0]),
                            (int) (getvalue("w", line, 1) * div * nfmm_scale[1]),
                            (int) (getvalue("w", line, 2) * div * nfmm_scale[2]), getvalue("w", line, 3),
                            (int) (getvalue("w", line, 4) * div * nfmm_scale[0]), (int) (getvalue("w", line, 5) * div));
                        //npl += wheels.make(applet, m, p, npl, (int)((float)getvalue("w", s1, 0) * f * f1 * nfmm_scale[0]), (int)((float)getvalue("w", s1, 1) * f * nfmm_scale[1]), (int)((float)getvalue("w", s1, 2) * f * nfmm_scale[2]), getvalue("w", s1, 3), (int)((float)getvalue("w", s1, 4) * f * f1), (int)((int)getvalue("w", s1, 5) * f), i1);

                /*if (s2.startsWith("<track>"))
                  track = -1;
                if (track == -1) {
                  if (s2.startsWith("c")) {
                    m.tr.c[m.tr.nt][0] = getvalue("c", s2, 0);
                    m.tr.c[m.tr.nt][1] = getvalue("c", s2, 1);
                    m.tr.c[m.tr.nt][2] = getvalue("c", s2, 2);
                  }
                  if (s2.startsWith("xy"))
                    m.tr.xy[m.tr.nt] = getvalue("xy", s2, 0);
                  if (s2.startsWith("zy"))
                    m.tr.zy[m.tr.nt] = getvalue("zy", s2, 0);
                  if (s2.startsWith("radx"))
                    m.tr.radx[m.tr.nt] = (int) (getvalue("radx", s2, 0) * f);
                  if (s2.startsWith("radz"))
                    m.tr.radz[m.tr.nt] = (int) (getvalue("radz", s2, 0) * f);
                }
                if (s2.startsWith("</track>")) {
                  track = m.tr.nt;
                  m.tr.nt++;
                }*/
                if (m.tr.nt + 1 > m.tr.xy.length)
                    throw new RuntimeException("increase tracks()");
                if (line.startsWith("<track>")) {
                    m.tr.notwall[m.tr.nt] = false;
                    m.tr.dam[m.tr.nt] = 1;
                    m.tr.skd[m.tr.nt] = 0;
                    m.tr.y[m.tr.nt] = 0;
                    m.tr.x[m.tr.nt] = 0;
                    m.tr.z[m.tr.nt] = 0;
                    m.tr.xy[m.tr.nt] = 0;
                    m.tr.zy[m.tr.nt] = 0;
                    m.tr.rady[m.tr.nt] = 0;
                    m.tr.radx[m.tr.nt] = 0;
                    m.tr.radz[m.tr.nt] = 0;
                    m.tr.c[m.tr.nt][0] = 0;
                    m.tr.c[m.tr.nt][1] = 0;
                    m.tr.c[m.tr.nt][2] = 0;
                    track = -1;
                }
                if (track == -1) {
                    if (line.startsWith("c")) {
                        m.tr.c[m.tr.nt][0] = getvalue("c", line, 0);
                        m.tr.c[m.tr.nt][1] = getvalue("c", line, 1);
                        m.tr.c[m.tr.nt][2] = getvalue("c", line, 2);
                    }
                    if (line.startsWith("xy"))
                        m.tr.xy[m.tr.nt] = getvalue("xy", line, 0);
                    if (line.startsWith("zy"))
                        m.tr.zy[m.tr.nt] = getvalue("zy", line, 0);
                    if (line.startsWith("radx"))
                        m.tr.radx[m.tr.nt] = (int) (getvalue("radx", line, 0) * div);
                    if (line.startsWith("rady"))
                        m.tr.rady[m.tr.nt] = (int) (getvalue("rady", line, 0) * div);
                    if (line.startsWith("radz"))
                        m.tr.radz[m.tr.nt] = (int) (getvalue("radz", line, 0) * div);
                    if (line.startsWith("ty"))
                        m.tr.y[m.tr.nt] = (int) (getvalue("ty", line, 0) * div);
                    if (line.startsWith("tx"))
                        m.tr.x[m.tr.nt] = (int) (getvalue("tx", line, 0) * div);
                    if (line.startsWith("tz"))
                        m.tr.z[m.tr.nt] = (int) (getvalue("tz", line, 0) * div);
                    if (line.startsWith("skid"))
                        m.tr.skd[m.tr.nt] = getvalue("skid", line, 0);
                    if (line.startsWith("dam"))
                        m.tr.dam[m.tr.nt] = 3;
                    if (line.startsWith("notwall"))
                        m.tr.notwall[m.tr.nt] = true;
                }
                if (line.startsWith("</track>")) {
                    //

                    final int x1 = m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt];
                    final int x2 = m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt];
                    final int y1 = m.tr.y[m.tr.nt] - m.tr.rady[m.tr.nt];
                    final int y2 = m.tr.y[m.tr.nt] + m.tr.rady[m.tr.nt];
                    final int z1 = m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt];
                    final int z2 = m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt];

                    /*
                     * x = 200
                     * y = 100
                     * z = 300
                     *
                    <p>
                    p(-x,y,z)
                    p(-x,y,-z)
                    p(-x,-y,-z)
                    p(-x,-y,z) // human eyes careful
                    </p>

                    <p>
                    p(x,-y,z)
                    p(x,y,z)
                    p(-x,y,z)
                    p(-x,-y,z)
                    </p>

                    <p>
                    p(x,-y,z)
                    p(x,y,z) // human eyes careful
                    p(x,y,-z)
                    p(x,-y,-z)
                    </p>

                    <p>
                    p(x,y,-z)
                    p(-x,y,-z)
                    p(-x,-y,-z)
                    p(x,-y,-z)
                    </p>
                    */

                    final int ggr = 0;
                    //if (RunApp.solidsApproachScreen)
                    //    ggr = -51;
                    if (RunApp.showSolids) {
                        final int[] pc = {
                                255, 0, 0
                        };
                        final int[] px = {
                                x1, x1, x1, x1,
                        };
                        final int[] py = {
                                y2, y2, y1, y1,
                        };
                        final int[] pz = {
                                z2, z1, z1, z2,
                        };

                        p[npl] = new Plane(m, px, pz, py, 4, pc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] apc = {
                                0, 255, 0
                        };
                        final int[] apx = {
                                x2, x2, x1, x1,
                        };
                        final int[] apy = {
                                y1, y2, y2, y1,
                        };
                        final int[] apz = {
                                z2, z2, z2, z2,
                        };
                        p[npl] = new Plane(m, apx, apz, apy, 4, apc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] bpc = {
                                0, 0, 255
                        };
                        final int[] bpx = {
                                x2, x2, x2, x2,
                        };
                        final int[] bpy = {
                                y1, y2, y2, y1,
                        };
                        final int[] bpz = {
                                z2, z2, z1, z1,
                        };
                        p[npl] = new Plane(m, bpx, bpz, bpy, 4, bpc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;

                        final int[] cpc = {
                                255, 255, 255
                        };
                        final int[] cpx = {
                                x2, x1, x1, x2,
                        };
                        final int[] cpy = {
                                y2, y2, y1, y1,
                        };
                        final int[] cpz = {
                                z1, z1, z1, z1,
                        };
                        p[npl] = new Plane(m, cpx, cpz, cpy, 4, cpc, false, ggr, 0, 0, 0, (byte) 0, false,
                                false /*rndcolor*/, false, false, 0, 0, 0, 0);
                        npl++;
                    }

                    /* Track Flats / Faces */
                    /* Captures RadX and RadZ, RadY can be interpreted/determined by model */
                    if (RunApp.showTrackFaces)
                        if ((m.tr.zy[m.tr.nt] == 90 || m.tr.zy[m.tr.nt] == -90) && m.tr.xy[m.tr.nt] == 0) {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        } else if ((m.tr.xy[m.tr.nt] == 90 || m.tr.xy[m.tr.nt] == -90) && m.tr.zy[m.tr.nt] == 0) {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        } else {
                            final int[] px = {
                                    m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt],
                                    m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]
                            };
                            final int[] py = {
                                    m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]
                            };
                            final int[] pz = {
                                    m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt],
                                    m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]
                            }; // may need changing
                            final int[] pc = {
                                    255, 255, 0
                            };

                            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);
                            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

                            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false,
                                    false /*rndcolor*/, false, false, 0, 0, 0, 0);
                            npl++;
                        }
                    track = m.tr.nt;
                    m.tr.nt++;
                }
                m.tr.prepare();

                if (line.startsWith("MaxRadius"))
                    maxR = (int) (getvalue("MaxRadius", line, 0) * div);
                if (line.startsWith("disp"))
                    disp = getvalue("disp", line, 0);
                if (line.startsWith("shadow"))
                    shadow = true;
                if (line.startsWith("loom"))
                    loom = true;
                if (line.startsWith("out"))
                    out = true;
                if (line.startsWith("hits"))
                    maxhits = getvalue("hits", line, 0);
                if (line.startsWith("colid")) {
                    colides = true;
                    rcol = getvalue("colid", line, 0);
                    pcol = getvalue("colid", line, 1);
                }
                if (line.startsWith("grounded"))
                    grounded = getvalue("grounded", line, 0);
                if (line.startsWith("div")){
                    div = getvalue("div", line, 0) / 10F;
                    g_div = (int)getvalue("div", line, 0);
                }
                if (line.startsWith("idiv")){
                    div = getvalue("idiv", s1, 0) / 100F;
                    g_idiv = (int)getvalue("idiv", line, 0);
                }
                if (line.startsWith("iwid")){
                    wid = getvalue("iwid", s1, 0) / 100F;
                    g_iwid = (int)getvalue("iwid", line, 0);
                }
                if (line.startsWith("ScaleX")){
                    nfmm_scale[0] = getvalue("ScaleX", s1, 0) / 100F;
                    g_scalex = (int)getvalue("ScaleX", line, 0);
                }
                if (line.startsWith("ScaleY")){
                    nfmm_scale[1] = getvalue("ScaleY", s1, 0) / 100F;
                    g_scaley = (int)getvalue("ScaleY", line, 0);
                }
                if (line.startsWith("ScaleZ")){
                    nfmm_scale[2] = getvalue("ScaleZ", s1, 0) / 100F;
                    g_scalez = (int)getvalue("ScaleZ", line, 0);
                }
                if (line.startsWith("stonecold"))
                    stonecold = true;
            } while (true);
            datainputstream.close();
        } catch (final Exception exception) {
            throw exception;
        }
        System.out.println(new StringBuilder().append("polygantos: ").append(npl).toString());
        grat = wheels.ground;
        p[npl - 1].imlast = true;
    }

    public ContO(final Medium medium, final ContO conto, final int i, final int j, final int k) {
        npl = 0;
        x = 0;
        y = 0;
        z = 0;
        xz = 0;
        xy = 0;
        zy = 0;
        wxz = 0;
        dist = 0;
        maxR = 0;
        disp = 0;
        shadow = false;
        loom = false;
        stonecold = conto.stonecold;
        grounded = 1;
        colides = false;
        rcol = 0;
        pcol = 0;
        track = -2;
        out = false;
        nhits = 0;
        maxhits = -1;
        grat = 0;
        m = medium;
        npl = conto.npl;
        maxR = conto.maxR;
        disp = conto.disp;
        loom = conto.loom;
        colides = conto.colides;
        maxhits = conto.maxhits;
        out = conto.out;
        rcol = conto.rcol;
        pcol = conto.pcol;
        shadow = conto.shadow;
        grounded = conto.grounded;
        p = new Plane[conto.npl];
        x = i;
        y = j;
        z = k;
        for (int l = 0; l < npl; l++)
            p[l] = new Plane(m, conto.p[l].ox, conto.p[l].oz, conto.p[l].oy, conto.p[l].n, conto.p[l].c,
                    conto.p[l].glass, conto.p[l].gr, conto.p[l].fs, conto.p[l].wx, conto.p[l].wz, conto.p[l].light,
                    conto.p[l].hidepoly, conto.p[l].randomcolor, conto.p[l].randoutline, conto.p[l].customstroke,
                    conto.p[l].strokewidth, conto.p[l].strokecap, conto.p[l].strokejoin, conto.p[l].strokemtlimit);

    }

    public void d(final Graphics g) {
        if (dist != 0)
            dist = 0;
        //if (track != -2)
        //	m.tr.in[track] = false;
        if (!out) {
        	//System.out.println(maxR);
            final int i = m.cx + (int) ((x - m.x - m.cx) * Math.cos(m.xz * 0.017453292519943295D)
                    - (z - m.z - m.cz) * Math.sin(m.xz * 0.017453292519943295D));
            final int j = m.cz + (int) ((x - m.x - m.cx) * Math.sin(m.xz * 0.017453292519943295D)
                    + (z - m.z - m.cz) * Math.cos(m.xz * 0.017453292519943295D));
            final int k = m.cz + (int) ((y - m.y - m.cy) * Math.sin(m.zy * 0.017453292519943295D)
                    + (j - m.cz) * Math.cos(m.zy * 0.017453292519943295D));
            if (xs(i + maxR, k) > 0 && xs(i - maxR, k) < m.w && k > -maxR && k < m.fade[7]
                    && xs(i + maxR, k) - xs(i - maxR, k) > disp) {
                if (shadow) {
                    final int l = m.cy + (int) ((m.ground - m.cy) * Math.cos(m.zy * 0.017453292519943295D)
                            - (j - m.cz) * Math.sin(m.zy * 0.017453292519943295D));
                    final int j1 = m.cz + (int) ((m.ground - m.cy) * Math.sin(m.zy * 0.017453292519943295D)
                            + (j - m.cz) * Math.cos(m.zy * 0.017453292519943295D));
                    if (ys(l + maxR, j1) > 0 && ys(l - maxR, j1) < m.h)
                        for (int k1 = 0; k1 < npl; k1++)
                            p[k1].s(g, x - m.x, y - m.y, z - m.z, xz, xy, zy);
                }
                final int i1 = m.cy + (int) ((y - m.y - m.cy) * Math.cos(m.zy * 0.017453292519943295D)
                        - (j - m.cz) * Math.sin(m.zy * 0.017453292519943295D));
                if (ys(i1 + maxR, k) > 0 && ys(i1 - maxR, k) < m.h) {
                    final int ai[] = new int[npl];
                    for (int l1 = 0; l1 < npl; l1++) {
                        ai[l1] = 0;
                        for (int j2 = 0; j2 < npl; j2++)
                            if (p[l1].av != p[j2].av) {
                                if (p[l1].av < p[j2].av)
                                    ai[l1]++;
                            } else if (l1 > j2)
                                ai[l1]++;

                    }

                    for (int i2 = 0; i2 < npl; i2++)
                        for (int k2 = 0; k2 < npl; k2++)
                            if (ai[k2] == i2) {
                                if (F51.trans && p[k2].glass)
                                    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(3, 0.7F));
                                p[k2].d((Graphics2D) g, x - m.x, y - m.y, z - m.z, xz, xy, zy, wxz, stonecold);
                                if (F51.trans && p[k2].glass)
                                    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(3, 1.0F));
                            }

                    dist = (int) Math.sqrt((int) Math.sqrt((m.x + m.cx - x) * (m.x + m.cx - x) + (m.z - z) * (m.z - z)
                            + (m.y + m.cy - y) * (m.y + m.cy - y))) * grounded;
                }
            }
        }
        /*if (dist != 0 && track != -2) {
        	m.tr.in[track] = true;
        	m.tr.x[track] = x - m.x;
        	m.tr.y[track] = y - m.y;
        	m.tr.z[track] = z - m.z;
        }*/
    }

    public void reset() {
        nhits = 0;
        xz = 0;
        xy = 0;
        zy = 0;
    }

    public void loadrots(final boolean flag) {
        if (!flag)
            reset();
        for (int i = 0; i < npl; i++) {
            Plane.rot(p[i].ox, p[i].oy, 0, 0, xy, p[i].n);
            Plane.rot(p[i].oy, p[i].oz, 0, 0, zy, p[i].n);
            Plane.rot(p[i].ox, p[i].oz, 0, 0, xz, p[i].n);
            p[i].loadprojf();
        }

        if (flag)
            reset();
    }

    public void tryexp(final ContO conto) {
        if (!out) {
            final int i = getpy(conto.x, conto.y, conto.z);
            if (i < maxR / 10 * (maxR / 10) + conto.maxR / 10 * (conto.maxR / 10) && i > 0) {
                if (pcol != 0)
                    for (int j = 0; j < npl; j++)
                        for (int k = 0; k < p[j].n && (conto.x - (x + p[j].ox[k])) * (conto.x - (x + p[j].ox[k]))
                                + (conto.y - (y + p[j].oy[k])) * (conto.y - (y + p[j].oy[k]))
                                + (conto.z - (z + p[j].oz[k])) * (conto.z - (z + p[j].oz[k])) >= conto.maxR * 10 / pcol
                                        * (conto.maxR * 10 / pcol); k++)
                            ;
                if (rcol != 0)
                    if (i < maxR / (10 * rcol) * (maxR / (10 * rcol)) + conto.maxR / 10 * (conto.maxR / 10))
                        ;
            }
        }
    }

    public int xs(final int i, int j) {
        if (j < 10)
            j = 10;
        return (j - m.focus_point) * (m.cx - i) / j + i;
    }

    public int ys(final int i, int j) {
        if (j < 10)
            j = 10;
        return (j - m.focus_point) * (m.cy - i) / j + i;
    }

    public int getvalue(final String s, final String s1, final int i) {
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

    public int getpy(final int i, final int j, final int k) {
        return (i - x) / 10 * ((i - x) / 10) + (j - y) / 10 * ((j - y) / 10) + (k - z) / 10 * ((k - z) / 10);
    }

    Medium m;
    Plane p[];

    F51 f51;
    int npl;
    int x;
    int y;
    int z;
    int xz;
    int xy;
    int zy;
    int wxz;
    int dist;
    int maxR;
    int disp;
    boolean shadow;
    boolean loom;
    boolean stonecold;
    int grounded;
    boolean colides;
    int rcol;
    int pcol;
    int track;
    boolean out;
    int nhits;
    int maxhits;
    int grat;
}

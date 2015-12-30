
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

	public ContO(final String s, final Medium medium, final int i, final int j, final int k)
			throws Exception {
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

		final float nfmm_scale[] = { 1.0F, 1.0F, 1.0F };

    boolean hasTracks = false;
    boolean inTrack = false;

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
					p[npl] = new Plane(m, pointX, pointZ, pointY, nPoints, color, glass, gr, fs, 0, 0, light, hidepoly, randomcolor,
							randoutline, customstroke, strokewidth, strokecap, strokejoin, strokemtlimit);
					npl++;
					flag = false;
				}
				if (line.startsWith("w") && RunApp.showModel) {
					npl += wheels.make( m, p, npl, (int) (getvalue("w", line, 0) * div * nfmm_scale[0]),
							(int) (getvalue("w", line, 1) * div * nfmm_scale[1]),
							(int) (getvalue("w", line, 2) * div * nfmm_scale[2]), getvalue("w", line, 3),
							(int) (getvalue("w", line, 4) * div * nfmm_scale[0]), (int) (getvalue("w", line, 5) * div));
					//npl += wheels.make(applet, m, p, npl, (int)((float)getvalue("w", s1, 0) * f * f1 * nfmm_scale[0]), (int)((float)getvalue("w", s1, 1) * f * nfmm_scale[1]), (int)((float)getvalue("w", s1, 2) * f * nfmm_scale[2]), getvalue("w", s1, 3), (int)((float)getvalue("w", s1, 4) * f * f1), (int)((int)getvalue("w", s1, 5) * f), i1);
				}

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

            int x1 = m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt];
            int x2 = m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt];
            int y1 = m.tr.y[m.tr.nt] - m.tr.rady[m.tr.nt];
            int y2 = m.tr.y[m.tr.nt] + m.tr.rady[m.tr.nt];
            int z1 = m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt];
            int z2 = m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt];

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

            int ggr = 0;
            //if (RunApp.solidsApproachScreen)
            //    ggr = -51;
            if (RunApp.showSolids) {
                int[] pc = { 255, 0, 0 };
                int[] px = { x1, x1 , x1 , x1 ,};
                int[] py = { y2, y2 , y1 , y1 ,};
                int[] pz = { z2, z1 , z1 , z2 ,};

                p[npl] = new Plane(m, px, pz, py, 4, pc, false, ggr, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
                npl++;

                int[] apc = { 0, 255, 0 };
                int[] apx = { x2, x2 , x1 , x1 ,};
                int[] apy = { y1, y2 , y2 , y1 ,};
                int[] apz = { z2, z2 , z2 , z2 ,};
                p[npl] = new Plane(m, apx, apz, apy, 4, apc, false, ggr, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
                npl++;

                int[] bpc = { 0, 0, 255 };
                int[] bpx = { x2, x2 , x2 , x2 ,};
                int[] bpy = { y1, y2 , y2 , y1 ,};
                int[] bpz = { z2, z2 , z1 , z1 ,};
                p[npl] = new Plane(m, bpx, bpz, bpy, 4, bpc, false, ggr, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
                npl++;

                int[] cpc = { 255, 255, 255 };
                int[] cpx = { x2, x1 , x1 , x2 ,};
                int[] cpy = { y2, y2 , y1 , y1 ,};
                int[] cpz = { z1, z1 , z1 , z1 ,};
                p[npl] = new Plane(m, cpx, cpz, cpy, 4, cpc, false, ggr, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
                npl++;
            }

            /* Track Flats / Faces */
        	/* Captures RadX and RadZ, RadY can be interpreted/determined by model */
        	if(RunApp.showTrackFaces)
        	{
	        	if((m.tr.zy[m.tr.nt] == 90 || m.tr.zy[m.tr.nt] == -90) && m.tr.xy[m.tr.nt] == 0)
	        	{
		            int[] px = { m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]};
		            int[] py = { m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]};
		            int[] pz = { m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.rady[m.tr.nt]}; // may need changing
		            int[] pc = { 255, 255, 0 };

		            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

		            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
		            npl++;
	        	}
	        	else if((m.tr.xy[m.tr.nt] == 90 || m.tr.xy[m.tr.nt] == -90) && m.tr.zy[m.tr.nt] == 0)
	        	{
	        		int[] px = { m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.rady[m.tr.nt]};
		            int[] py = { m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]};
		            int[] pz = { m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]}; // may need changing
		            int[] pc = { 255, 255, 0 };

		            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);

		            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
		            npl++;
	        	} else
	        	{
	        		int[] px = { m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] - m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt], m.tr.x[m.tr.nt] + m.tr.radx[m.tr.nt]};
		            int[] py = { m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt], m.tr.y[m.tr.nt]};
		            int[] pz = { m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] + m.tr.radz[m.tr.nt], m.tr.z[m.tr.nt] - m.tr.radz[m.tr.nt]}; // may need changing
		            int[] pc = { 255, 255, 0 };

		            Plane.rot(py, px, m.tr.y[m.tr.nt], m.tr.x[m.tr.nt], -m.tr.xy[m.tr.nt], 4);
		            Plane.rot(py, pz, m.tr.y[m.tr.nt], m.tr.z[m.tr.nt], -m.tr.zy[m.tr.nt], 4);

		            p[npl] = new Plane(m, px, pz, py, 4, pc, false, 0, 0, 0, 0, (byte) 0, false, false /*rndcolor*/, false, false, 0, 0, 0, 0);
		            npl++;
	        	}
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
				if (line.startsWith("div"))
					div = getvalue("div", line, 0) / 10F;
				if (line.startsWith("idiv"))
					div = getvalue("idiv", s1, 0) / 100F;
				if (line.startsWith("iwid"))
					wid = getvalue("iwid", s1, 0) / 100F;
				if (line.startsWith("ScaleX"))
					nfmm_scale[0] = getvalue("ScaleX", s1, 0) / 100F;
				if (line.startsWith("ScaleY"))
					nfmm_scale[1] = getvalue("ScaleY", s1, 0) / 100F;
				if (line.startsWith("ScaleZ"))
					nfmm_scale[2] = getvalue("ScaleZ", s1, 0) / 100F;
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

	public ContO(final DataInputStream s, final Medium medium, final int i, final int j, final int k) {
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
		int l = 0;
		float f = 1.0F;
		final int ai[] = new int[350];
		final int ai1[] = new int[350];
		final int ai2[] = new int[350];
		final int ai3[] = new int[3];
		boolean flag1 = false;
		final Wheels wheels = new Wheels();
		int i1 = 1;
		int j1 = 0;

		float f1 = 1.0F;
		boolean hidepoly = false;
		boolean randomcolor = false;
		boolean randoutline = false;
		byte light = 0;

		boolean customstroke = false;
		int strokewidth = 1;
		int strokecap = BasicStroke.CAP_BUTT;
		int strokejoin = BasicStroke.JOIN_MITER;
		int strokemtlimit = 10;

		final float nfmm_scale[] = { 1.0F, 1.0F, 1.0F };

		try {
			final BufferedReader r = new BufferedReader(new InputStreamReader(s));
			String s1;
			while ((s1 = r.readLine()) != null) {
				final String s2 = new StringBuilder().append("").append(s1.trim()).toString();
				if (s2.startsWith("<p>")) {
					flag = true;
					l = 0;
					i1 = 0;
					j1 = 0;
					light = 0;
					hidepoly = false;
					randomcolor = false;
					randoutline = false;
				}
				if (flag) {
					if (s2.startsWith("gr"))
						i1 = getvalue("gr", s2, 0);
					if (s2.startsWith("fs"))
						j1 = getvalue("fs", s2, 0);
					if (s2.startsWith("glass"))
						flag1 = true;
					if (s2.startsWith("c")) {
						if (flag1)
							flag1 = false;
						ai3[0] = getvalue("c", s2, 0);
						ai3[1] = getvalue("c", s2, 1);
						ai3[2] = getvalue("c", s2, 2);
					}
					if (s2.startsWith("p")) {
						ai[l] = (int) ((int) (getvalue("p", s2, 0) * f * f1) * nfmm_scale[0]);
						ai1[l] = (int) ((int) (getvalue("p", s2, 1) * f) * nfmm_scale[0]);
						ai2[l] = (int) ((int) (getvalue("p", s2, 2) * f) * nfmm_scale[0]);
						l++;
					}
					if (s2.startsWith("random()") || s2.startsWith("rainbow()"))
						randomcolor = true;
					if (s2.startsWith("randoutline()"))
						randoutline = true;
					if (s2.startsWith("lightF"))
						light = 1;
					if (s2.startsWith("lightB"))
						light = 2;
					if (s2.startsWith("light()"))
						light = 1;
					if (s2.startsWith("noOutline()"))
						hidepoly = true;
					if (s2.startsWith("customOutline"))
						customstroke = true;
					if (s2.startsWith("outlineW("))
						strokewidth = getvalue("outlineW", s2, 0);
					if (s2.startsWith("outlineCap(")) {
						if (s2.startsWith("outlineCap(butt)"))
							strokecap = BasicStroke.CAP_BUTT;
						if (s2.startsWith("outlineCap(round)"))
							strokecap = BasicStroke.CAP_ROUND;
						if (s2.startsWith("outlineCap(square)"))
							strokecap = BasicStroke.CAP_SQUARE;
					}
					if (s2.startsWith("outlineJoin(")) {
						if (s2.startsWith("outlineCap(bevel)"))
							strokejoin = BasicStroke.JOIN_BEVEL;
						if (s2.startsWith("outlineCap(miter)"))
							strokejoin = BasicStroke.JOIN_MITER;
						if (s2.startsWith("outlineCap(round)"))
							strokejoin = BasicStroke.JOIN_ROUND;
					}
					if (s2.startsWith("outlineMtlimit("))
						strokemtlimit = getvalue("outlineW", s2, 0);
				}
				if (s2.startsWith("</p>")) {
					p[npl] = new Plane(m, ai, ai2, ai1, l, ai3, flag1, i1, j1, 0, 0, light, hidepoly, randomcolor,
							randoutline, customstroke, strokewidth, strokecap, strokejoin, strokemtlimit);
					npl++;
					flag = false;
				}
				if (s2.startsWith("w"))
					npl += wheels.make(m, p, npl, (int) (getvalue("w", s2, 0) * f), (int) (getvalue("w", s2, 1) * f),
							(int) (getvalue("w", s2, 2) * f), getvalue("w", s2, 3), (int) (getvalue("w", s2, 4) * f),
							(int) (getvalue("w", s2, 5) * f));
				if (s2.startsWith("<track>"))
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
				}
				if (s2.startsWith("MaxRadius"))
					maxR = (int) (getvalue("MaxRadius", s2, 0) * f);
				if (s2.startsWith("disp"))
					disp = getvalue("disp", s2, 0);
				if (s2.startsWith("shadow"))
					shadow = true;
				if (s2.startsWith("loom"))
					loom = true;
				if (s2.startsWith("out"))
					out = true;
				if (s2.startsWith("hits"))
					maxhits = getvalue("hits", s2, 0);
				if (s2.startsWith("colid")) {
					colides = true;
					rcol = getvalue("colid", s2, 0);
					pcol = getvalue("colid", s2, 1);
				}
				if (s2.startsWith("grounded"))
					grounded = getvalue("grounded", s2, 0);
				if (s2.startsWith("div"))
				{
					f = getvalue("div", s2, 0) / 10F;
					maxR = 300;
				}
				if (s2.startsWith("idiv"))
					f = getvalue("idiv", s1, 0) / 100F;
				if (s2.startsWith("iwid"))
					f1 = getvalue("iwid", s1, 0) / 100F;
				if (s2.startsWith("ScaleX"))
					nfmm_scale[0] = getvalue("ScaleX", s1, 0) / 100F;
				if (s2.startsWith("ScaleY"))
					nfmm_scale[1] = getvalue("ScaleY", s1, 0) / 100F;
				if (s2.startsWith("ScaleZ"))
					nfmm_scale[2] = getvalue("ScaleZ", s1, 0) / 100F;
				if (s2.startsWith("stonecold"))
					stonecold = true;
			}
			s.close();
		} catch (final Exception exception) {
			exception.printStackTrace();
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
		if (dist != 0) {
			dist = 0;
			//if (track != -2)
			//	m.tr.in[track] = false;
		}
		if (!out) {
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
			p[i].rot(p[i].ox, p[i].oy, 0, 0, xy, p[i].n);
			p[i].rot(p[i].oy, p[i].oz, 0, 0, zy, p[i].n);
			p[i].rot(p[i].ox, p[i].oz, 0, 0, xz, p[i].n);
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

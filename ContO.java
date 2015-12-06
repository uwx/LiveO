
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   ContO.java

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.net.URL;

public class ContO {

	public ContO(final String s, final Medium medium, final int i, final int j, final int k, final Applet applet) {
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
		wire = false;
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

        float nfmm_scale[] = {
            1.0F, 1.0F, 1.0F
        };

		try {
			final URL url = new URL(applet.getCodeBase(), (new StringBuilder()).append(s).append(".rad").toString());
			System.out.println(url);
			final DataInputStream datainputstream = new DataInputStream(url.openStream());
			do {
				String s1;
				if ((s1 = datainputstream.readLine()) == null)
					break;
				final String s2 = (new StringBuilder()).append("").append(s1.trim()).toString();
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
						ai1[l] = (int) ((int) (getvalue("p", s2, 1) * f) * nfmm_scale[1]);
						ai2[l] = (int) ((int) (getvalue("p", s2, 2) * f) * nfmm_scale[2]);
						l++;
					}
                    if(s2.startsWith("random()") || s2.startsWith("rainbow()"))
						randomcolor = true;
                    if(s2.startsWith("randoutline()"))
						randoutline = true;
					if(s2.startsWith("lightF"))
						light = 1;
	                if(s2.startsWith("lightB"))
						light = 2;
	                if(s2.startsWith("light()"))
						light = 1;
	                if(s2.startsWith("noOutline()"))
						hidepoly = true;
				}
				if (s2.startsWith("</p>")) {
					p[npl] = new Plane(m, ai, ai2, ai1, l, ai3, flag1, i1, j1, 0, 0, light, hidepoly, randomcolor, randoutline);
					npl++;
					flag = false;
				}
				if (s2.startsWith("w"))
					npl += wheels.make(applet, m, p, npl, (int) (getvalue("w", s2, 0) * f),
							(int) (getvalue("w", s2, 1) * f), (int) (getvalue("w", s2, 2) * f), getvalue("w", s2, 3),
							(int) (getvalue("w", s2, 4) * f), (int) (getvalue("w", s2, 5) * f),
							(int) (getvalue("w", s2, 6) * f));
					//npl += wheels.make(applet, m, p, npl, (int)((float)getvalue("w", s1, 0) * f * f1 * nfmm_scale[0]), (int)((float)getvalue("w", s1, 1) * f * nfmm_scale[1]), (int)((float)getvalue("w", s1, 2) * f * nfmm_scale[2]), getvalue("w", s1, 3), (int)((float)getvalue("w", s1, 4) * f * f1), (int)((int)getvalue("w", s1, 5) * f), i1);

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
					f = getvalue("div", s2, 0) / 10F;
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
			} while (true);
			datainputstream.close();
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		System.out.println((new StringBuilder()).append("polygantos: ").append(npl).toString());
		grat = wheels.ground;
		p[npl - 1].imlast = true;
	}

	public ContO(final DataInputStream s, final Medium medium, final int i, final int j, final int k, final Applet applet) {
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
		wire = false;
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

        float nfmm_scale[] = {
            1.0F, 1.0F, 1.0F
        };

		try {
			do {
				String s1;
				if ((s1 = s.readLine()) == null)
					break;
				final String s2 = (new StringBuilder()).append("").append(s1.trim()).toString();
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
                    if(s2.startsWith("random()") || s2.startsWith("rainbow()"))
						randomcolor = true;
                    if(s2.startsWith("randoutline()"))
						randoutline = true;
					if(s2.startsWith("lightF"))
						light = 1;
	                if(s2.startsWith("lightB"))
						light = 2;
	                if(s2.startsWith("light()"))
						light = 1;
	                if(s2.startsWith("noOutline()"))
						hidepoly = true;
				}
				if (s2.startsWith("</p>")) {
					p[npl] = new Plane(m, ai, ai2, ai1, l, ai3, flag1, i1, j1, 0, 0, light, hidepoly, randomcolor, randoutline);
					npl++;
					flag = false;
				}
				if (s2.startsWith("w"))
					npl += wheels.make(applet, m, p, npl, (int) (getvalue("w", s2, 0) * f),
							(int) (getvalue("w", s2, 1) * f), (int) (getvalue("w", s2, 2) * f), getvalue("w", s2, 3),
							(int) (getvalue("w", s2, 4) * f), (int) (getvalue("w", s2, 5) * f),
							(int) (getvalue("w", s2, 6) * f));
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
					f = getvalue("div", s2, 0) / 10F;
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
			} while (true);
			s.close();
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		System.out.println((new StringBuilder()).append("polygantos: ").append(npl).toString());
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
		wire = false;
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
					conto.p[l].glass, conto.p[l].gr, conto.p[l].fs, conto.p[l].wx, conto.p[l].wz, conto.p[l].light, conto.p[l].hidepoly, conto.p[l].randomcolor, conto.p[l].randoutline);

	}

	public void d(final Graphics g) {
		if (dist != 0) {
			dist = 0;
			if (track != -2)
				m.tr.in[track] = false;
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
							p[k1].s(g, x - m.x, y - m.y, z - m.z, xz, xy, zy, loom);
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
								p[k2].d(g, x - m.x, y - m.y, z - m.z, xz, xy, zy, wxz, wire, loom, stonecold);
								if (F51.trans && p[k2].glass)
									((Graphics2D) g).setComposite(AlphaComposite.getInstance(3, 1.0F));
							}

					dist = (int) Math.sqrt((int) Math.sqrt(((m.x + m.cx) - x) * ((m.x + m.cx) - x)
							+ (m.z - z) * (m.z - z) + ((m.y + m.cy) - y) * ((m.y + m.cy) - y))) * grounded;
				}
			}
		}
		if (dist != 0 && track != -2) {
			m.tr.in[track] = true;
			m.tr.x[track] = x - m.x;
			m.tr.y[track] = y - m.y;
			m.tr.z[track] = z - m.z;
		}
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
			if (i < (maxR / 10) * (maxR / 10) + (conto.maxR / 10) * (conto.maxR / 10) && i > 0) {
				if (pcol != 0)
					for (int j = 0; j < npl; j++)
						for (int k = 0; k < p[j].n && (conto.x - (x + p[j].ox[k])) * (conto.x - (x + p[j].ox[k]))
								+ (conto.y - (y + p[j].oy[k])) * (conto.y - (y + p[j].oy[k]))
								+ (conto.z - (z + p[j].oz[k]))
										* (conto.z - (z + p[j].oz[k])) >= ((conto.maxR * 10) / pcol)
												* ((conto.maxR * 10) / pcol); k++)
							;
				if (rcol != 0)
					if (i < (maxR / (10 * rcol)) * (maxR / (10 * rcol)) + (conto.maxR / 10) * (conto.maxR / 10))
						;
			}
		}
	}

	public int xs(final int i, int j) {
		if (j < 10)
			j = 10;
		return ((j - m.focus_point) * (m.cx - i)) / j + i;
	}

	public int ys(final int i, int j) {
		if (j < 10)
			j = 10;
		return ((j - m.focus_point) * (m.cy - i)) / j + i;
	}

	public int getvalue(final String s, final String s1, final int i) {
		int k = 0;
		String s3 = "";
		for (int j = s.length() + 1; j < s1.length(); j++) {
			final String s2 = (new StringBuilder()).append("").append(s1.charAt(j)).toString();
			if (s2.equals(",") || s2.equals(")")) {
				k++;
				j++;
			}
			if (k == i)
				s3 = (new StringBuilder()).append(s3).append(s1.charAt(j)).toString();
		}

		return Integer.valueOf(s3).intValue();
	}

	public int getpy(final int i, final int j, final int k) {
		return ((i - x) / 10) * ((i - x) / 10) + ((j - y) / 10) * ((j - y) / 10) + ((k - z) / 10) * ((k - z) / 10);
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
	boolean wire;
}

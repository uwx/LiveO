
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Wheels.java

import java.awt.BasicStroke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Wheels {

	static String wheelfile = "throwbacks.rad";

	public Wheels() {
		ground = 0;
	}

	public int make(final Medium medium, final Plane aplane[], int i, final int j, final int k, final int l,
			final int i1, final int j1, final int k1) {
		int l1 = 0;
		int wheelNum = 0;
		final float f = j1 / 10F;
		final float f1 = k1 / 10F;
		if (i1 == 11)
			l1 = (int) (j + 4F * f);
		try {
			final BufferedReader br = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File("./wheels/" + wheelfile))));
			final int tmx[] = new int[100];
			final int tmy[] = new int[100];
			final int tmz[] = new int[100];
			final int color[] = { 128, 128, 128 };
			int gr = 0;
			float scale = 1.0F;
			int npts = 0;
			if (ground < (int) (k + 12F * f1 + 1.0F))
				ground = (int) (k + 12F * f1 + 1.0F);
			boolean inPlane = false;
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("div"))
					scale = getValue(line, 0) / 10F;
				else if (line.startsWith("<p>"))
					inPlane = true;
				if (inPlane)
					if (line.startsWith("p")) {
						tmx[npts] = (int) (j - getValue(line, 0) * scale * f);
						tmy[npts] = (int) (k + getValue(line, 1) * scale * f1);
						tmz[npts] = (int) (l + getValue(line, 2) * scale * f1);
						npts++;
					} else if (line.startsWith("gr"))
						gr = (int) getValue(line, 0);
					else if (line.startsWith("fs"))
						l1 = (int) getValue(line, 0);
					else if (line.startsWith("c")) {
						color[0] = (int) getValue(line, 0);
						color[1] = (int) getValue(line, 1);
						color[2] = (int) getValue(line, 2);
					} else if (line.equals("</p>")) {
						aplane[i] = new Plane(medium, tmx, tmz, tmy, npts, color, false, gr, l1, j, l, (byte) 0, false,
								false, false, false, 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10);
						i++;
						npts = 0;
						gr = 0;
						l1 = 0;
						wheelNum++;
						inPlane = false;
					}
			}
			br.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return wheelNum;
	}

	public float getValue(String s1, final int i) {
		s1 = s1.substring(s1.indexOf("(") + 1, s1.indexOf(")"));
		final String parts[] = s1.split(",");
		return Float.parseFloat(parts[i]);
	}

	int ground;
}

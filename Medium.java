
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Medium.java

import java.awt.Color;
import java.awt.Graphics;

public class Medium {

	public Medium() {
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
		lightson = true;
	}

	public void behinde(final ContO conto) {
		int i = conto.zy;
		int j = conto.xz;
		for (; i > 360; i -= 360)
			;
		for (; i < 0; i += 360)
			;
		if (i > 90 && i < 270) {
			tart += (180 - tart) / 3;
			yart += (100 - yart) / 4;
		} else {
			tart -= tart / 3;
			yart += (-100 - yart) / 4;
		}
		j += tart;
		if (i > 90)
			i = 180 - i;
		if (i < -90)
			i = -180 - i;
		final int k = conto.y + (int) (((conto.y + yart) - conto.y) * Math.cos(conto.zy * 0.017453292519943295D)
				- (conto.z - 600 - conto.z) * Math.sin(conto.zy * 0.017453292519943295D));
		final int l = conto.z + (int) (((conto.y + yart) - conto.y) * Math.sin(conto.zy * 0.017453292519943295D)
				+ (conto.z - 600 - conto.z) * Math.cos(conto.zy * 0.017453292519943295D));
		final int i1 = conto.x + (int) ((-(l - conto.z)) * Math.sin(conto.xz * 0.017453292519943295D));
		final int j1 = conto.z + (int) ((l - conto.z) * Math.cos(conto.xz * 0.017453292519943295D));
		zy = -i;
		xz = -j;
		x += (i1 - cx - x) / 3;
		z += (j1 - cz - z) / 1.5D;
		y += (k - cy - y) / 1.5D;
	}

	public void infront(final ContO conto) {
		int i = conto.zy;
		int j = conto.xz;
		for (; i > 360; i -= 360)
			;
		for (; i < 0; i += 360)
			;
		if (i > 90 && i < 270) {
			tart += (180 - tart) / 3;
			yart += (100 - yart) / 3;
		} else {
			tart -= tart / 3;
			yart += (-100 - yart) / 3;
		}
		j += tart;
		if (i > 90)
			i = 180 - i;
		if (i < -90)
			i = -180 - i;
		final int k = conto.y + (int) (((conto.y + yart) - conto.y) * Math.cos(conto.zy * 0.017453292519943295D)
				- ((conto.z + 800) - conto.z) * Math.sin(conto.zy * 0.017453292519943295D));
		final int l = conto.z + (int) (((conto.y + yart) - conto.y) * Math.sin(conto.zy * 0.017453292519943295D)
				+ ((conto.z + 800) - conto.z) * Math.cos(conto.zy * 0.017453292519943295D));
		final int i1 = conto.x + (int) ((-(l - conto.z)) * Math.sin(conto.xz * 0.017453292519943295D));
		final int j1 = conto.z + (int) ((l - conto.z) * Math.cos(conto.xz * 0.017453292519943295D));
		zy = i;
		xz = -(j + 180);
		x += (i1 - cx - x) / 3;
		z += (j1 - cz - z) / 1.5D;
		y += (k - cy - y) / 1.5D;
	}

	public void left(final ContO conto) {
		final int i = conto.y;
		final int j = conto.x + (int) (((conto.x + 600) - conto.x) * Math.cos(conto.xz * 0.017453292519943295D));
		final int k = conto.z + (int) (((conto.x + 600) - conto.x) * Math.sin(conto.xz * 0.017453292519943295D));
		zy = 0;
		xz = -(conto.xz + 90);
		x += (j - cx - x) / 1.5D;
		z += (k - cz - z) / 1.5D;
		y += (i - cy - y) / 1.5D;
	}

	public void right(final ContO conto) {
		final int i = conto.y;
		final int j = conto.x + (int) ((conto.x - 600 - conto.x) * Math.cos(conto.xz * 0.017453292519943295D));
		final int k = conto.z + (int) ((conto.x - 600 - conto.x) * Math.sin(conto.xz * 0.017453292519943295D));
		zy = 0;
		xz = -(conto.xz - 90);
		x += (j - cx - x) / 3;
		z += (k - cz - z) / 1.5D;
		y += (i - cy - y) / 1.5D;
	}

	public void watch(final ContO conto) {
		if (!td) {
			y = conto.y + (int) ((conto.y - 300 - conto.y) * Math.cos(conto.zy * 0.017453292519943295D)
					- ((conto.z + 3000) - conto.z) * Math.sin(conto.zy * 0.017453292519943295D));
			final int i = conto.z + (int) ((conto.y - 300 - conto.y) * Math.sin(conto.zy * 0.017453292519943295D)
					+ ((conto.z + 3000) - conto.z) * Math.cos(conto.zy * 0.017453292519943295D));
			x = conto.x + (int) (((conto.x + 400) - conto.x) * Math.cos(conto.xz * 0.017453292519943295D)
					- (i - conto.z) * Math.sin(conto.xz * 0.017453292519943295D));
			z = conto.z + (int) (((conto.x + 400) - conto.x) * Math.sin(conto.xz * 0.017453292519943295D)
					+ (i - conto.z) * Math.cos(conto.xz * 0.017453292519943295D));
			td = true;
		}
		char c = '\0';
		if (conto.x - x - cx > 0)
			c = '\264';
		final int j = -(int) (90 + c
				+ Math.atan((double) (conto.z - z) / (double) (conto.x - x - cx)) / 0.017453292519943295D);
		c = '\0';
		if (conto.y - y - cy < 0)
			c = '\uFF4C';
		final int k = (int) Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx));
		final int l = (int) (90 + c - Math.atan((double) k / (double) (conto.y - y - cy)) / 0.017453292519943295D);
		xz = j;
		zy += (l - zy) / 5;
		if ((int) Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx)
				+ (conto.y - y - cy) * (conto.y - y - cy)) > 3500)
			td = false;
	}

	public void around(final ContO conto, final int i) {
		byte byte0 = 1;
		if (i == 6000)
			byte0 = 2;
		y = conto.y + adv;
		x = conto.x + (int) ((((conto.x - i) + adv * byte0) - conto.x) * Math.cos(vxz * 0.017453292519943295D));
		z = conto.z + (int) ((((conto.x - i) + adv * byte0) - conto.x) * Math.sin(vxz * 0.017453292519943295D));
		if (i == 6000) {
			if (!vert)
				adv -= 10;
			else
				adv += 10;
			if (adv < -900)
				vert = true;
			if (adv > 1200)
				vert = false;
		} else {
			if (!vert)
				adv -= 2;
			else
				adv += 2;
			if (adv < -500)
				vert = true;
			if (adv > 150)
				vert = false;
			if (adv > 300)
				adv = 300;
		}
		vxz += 2;
		if (vxz > 360)
			vxz -= 360;
		char c = '\0';
		int j = y;
		if (j > 0)
			j = 0;
		if (conto.y - j - cy < 0)
			c = '\uFF4C';
		final int k = (int) Math.sqrt((conto.z - z) * (conto.z - z) + (conto.x - x - cx) * (conto.x - x - cx));
		final int l = (int) (90 + c - Math.atan((double) k / (double) (conto.y - j - cy)) / 0.017453292519943295D);
		xz = -vxz + 90;
		zy += (l - zy) / 10;
	}

	public void d3p(final Graphics g) {
		g.setColor(new Color(255, 0, 0));
		for (int i = 0; i < 3; i++)
			g.fillOval(lxp[i], lyp[i], 4, 4);

		g.setColor(new Color(255, 255, 0));
		for (int j = 0; j < 3; j++)
			g.drawOval(lxp[j], lyp[j], 4, 4);

	}

	public void d(final Graphics g) {
		if (zy > 90)
			zy = 90;
		if (zy < -90)
			zy = -90;
		if (y > 0)
			y = 0;
		ground = 250 - y;
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
	}

	public int ys(final int i, int j) {
		if (j < 10)
			j = 10;
		return ((j - focus_point) * (cy - i)) / j + i;
	}

	boolean lightson;
	Trackers tr;
	boolean isun;
	int focus_point;
	int ground;
	int skyline;
	int csky[] = { 145, 200, 255 };
	int cgrnd[] = { 180, 180, 180 };
	int cfade[] = { 255, 255, 255 };
	int fade[] = { 3000, 5000, 7000, 9000, 11000, 13000, 15000, 17000 };
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

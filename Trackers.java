// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Trackers.java

public class Trackers {

	public void prepare() {
		for (int i = 0; i < nt; i++)
			in[i] = false;

	}

    int[][] c = new int[95000][3];
    int[] dam = new int[95000];
    boolean[] decor = new boolean[95000];
    int ncx = 0;
    int ncz = 0;
    boolean[] notwall = new boolean[95000];
    int nt = 0;
    int[] radx = new int[95000];
    int[] rady = new int[95000];
    int[] radz = new int[95000];
    int[][][] sect = null;
    int[] skd = new int[95000];
    int sx = 0;
    int sz = 0;
    int[] x = new int[95000];
    int[] xy = new int[95000];
    int[] y = new int[95000];
    int[] z = new int[95000];
    int[] zy = new int[95000];
	boolean in[];
}

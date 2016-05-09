// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Trackers.java

final class Trackers {
    
    private Trackers() {}

    static void prepare() {
        for (int i = 0; i < nt; i++)
            in[i] = false;
    }

    static int[][] c = new int[95000][3];
    static int[] dam = new int[95000];
    static boolean[] notwall = new boolean[95000];
    static int nt = 0;
    static int[] radx = new int[95000];
    static int[] rady = new int[95000];
    static int[] radz = new int[95000];
    static int[] skd = new int[95000];
    static int[] x = new int[95000];
    static int[] xy = new int[95000];
    static int[] y = new int[95000];
    static int[] z = new int[95000];
    static int[] zy = new int[95000];
    static boolean in[] = new boolean[95000];
}

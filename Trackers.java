// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Trackers.java


public class Trackers
{

    public Trackers()
    {
        x = new int[100];
        y = new int[100];
        z = new int[100];
        xy = new int[100];
        zy = new int[100];
        c = new int[100][3];
        radx = new int[100];
        radz = new int[100];
        in = new boolean[100];
        nt = 0;
    }

    public void prepare()
    {
        for(int i = 0; i < nt; i++)
            in[i] = false;

    }

    int x[];
    int y[];
    int z[];
    int xy[];
    int zy[];
    int c[][];
    int radx[];
    int radz[];
    boolean in[];
    int nt;
}

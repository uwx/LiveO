// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Wheels.java

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Wheels
{

    public Wheels()
    {
        ground = 0;
    }

    public int make(Applet applet, Medium medium, Plane aplane[], int i, int j, int k, int l, 
            int i1, int j1, int k1, int m1)
    {
        int l1 = 0;
        int wheelNum = 0;
        float f = (float)j1 / 10F;
        float f1 = (float)k1 / 10F;
        if(i1 == 11)
            l1 = (int)((float)j + 4F * f);
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader((new URL(applet.getCodeBase(), "wheel.rad")).openStream()));
            int tmx[] = new int[100];
            int tmy[] = new int[100];
            int tmz[] = new int[100];
            int color[] = {
                128, 128, 128
            };
            int gr = 0;
            float scale = 1.0F;
            int npts = 0;
            if(ground < (int)((float)k + 12F * f1 + 1.0F))
                ground = (int)((float)k + 12F * f1 + 1.0F);
            boolean inPlane = false;
            String line;
            while((line = br.readLine()) != null) 
            {
                if(line.startsWith("div"))
                    scale = getValue(line, 0) / 10F;
                else
                if(line.startsWith("<p>"))
                    inPlane = true;
                if(inPlane)
                    if(line.startsWith("p"))
                    {
                        tmx[npts] = (int)((float)j - getValue(line, 0) * scale * f);
                        tmy[npts] = (int)((float)k + getValue(line, 1) * scale * f1);
                        tmz[npts] = (int)((float)l + getValue(line, 2) * scale * f1);
                        npts++;
                    } else
                    if(line.startsWith("gr"))
                        gr = (int)getValue(line, 0);
                    else
                    if(line.startsWith("fs"))
                        l1 = (int)getValue(line, 0);
                    else
                    if(line.startsWith("c"))
                    {
                        color[0] = (int)getValue(line, 0);
                        color[1] = (int)getValue(line, 1);
                        color[2] = (int)getValue(line, 2);
                    } else
                    if(line.equals("</p>"))
                    {
                        aplane[i] = new Plane(medium, tmx, tmz, tmy, npts, color, false, gr, l1, j, l);
                        i++;
                        npts = 0;
                        gr = 0;
                        l1 = 0;
                        wheelNum++;
                        inPlane = false;
                    }
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return wheelNum;
    }

    public float getValue(String s1, int i)
    {
        s1 = s1.substring(s1.indexOf("(") + 1, s1.indexOf(")"));
        String parts[] = s1.split(",");
        return Float.parseFloat(parts[i]);
    }

    int ground;
}

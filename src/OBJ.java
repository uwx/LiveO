import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import javax.swing.JOptionPane;

public final class OBJ {
    private static final int MAX_OBJ_FACES = 600;
    private static final int MAX_OBJ_VERTS = 6000;
    
    private static boolean multiplyObjValuesBy10 = false;
    private static boolean objfacend = false;
    private static boolean tutok = false;

    public static void importObj(File objFile) {
        String tPath = objFile.getPath();
        File mtlFile;
        if (tPath.endsWith(".obj")) { //gets the mtl file name. TODO support for mtllib
            mtlFile = new File(tPath.substring(0, tPath.length() - 4) + ".mtl");
        } else {
            mtlFile = new File(tPath + ".mtl");
        }
        System.out.println("mtlfile: " + mtlFile);
        
        HashMap<String, Color> materials = new HashMap<String, Color>();
        
        if (mtlFile.exists()) {
            try {
                multiplyObjValuesBy10 = false;
                
                final BufferedReader bufferedreader = new BufferedReader(new FileReader(mtlFile));
                String currentMaterialName = "";
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    if (line.startsWith("newmtl ")) {
                        currentMaterialName = line.substring("newmtl ".length());
                        System.out.println("currentMaterialName " + currentMaterialName);
                    }
                    if (line.startsWith("Kd ")) {
                        materials.put(currentMaterialName, new Color(objvaluef(line, 0), objvaluef(line, 1), objvaluef(line, 2)));
                    }
                }
                bufferedreader.close();
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(RunApp.frame, "Unable to load material file! Error Details:\n" + e, "LiveO", 1);
            }
        }
        
        // From the Car Maker: setCursor(new Cursor(3));
        // From the Car Maker: int rectW = 0;
        // From the Car Maker: if (tutok) {
        // From the Car Maker:     rectW = -70;
        // From the Car Maker: }
        // From the Car Maker: rd.setColor(new Color(225, 225, 225));
        // From the Car Maker: rd.fillRect(116, 246 + rectW, 468, 50);
        // From the Car Maker: rd.setColor(new Color(0, 0, 0));
        // From the Car Maker: rd.setFont(new Font("Arial", 1, 13));
        // From the Car Maker: ftm = rd.getFontMetrics();
        // From the Car Maker: rd.drawString("Reading " + objFile.getName() + ", please wait...", 350 - ftm.stringWidth("Reading " + objFile.getName() + ", please wait...") / 2, 276 + rectW);
        // From the Car Maker: repaint();
        final int[] vertX = new int[MAX_OBJ_VERTS];
        final int[] vertY = new int[MAX_OBJ_VERTS];
        final int[] vertZ = new int[MAX_OBJ_VERTS];
        int objVertices = 0;
        final int[][] face = new int[MAX_OBJ_FACES][100];
        final Color[] faceColor = new Color[MAX_OBJ_FACES];
        Color currentColor = new Color(200, 200, 220);
        final int[] totalFaceVerts = new int[MAX_OBJ_FACES];
        int objFaces = 0;
        if (objFile.exists()) {
            // read OBJ file
            try {
                final BufferedReader bufferedreader = new BufferedReader(new FileReader(objFile));
                boolean tooManyVertices = false;
                boolean tooManyFaces = false;
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    if (line.startsWith("v "))
                        if (objVertices < MAX_OBJ_VERTS) {
                            multiplyObjValuesBy10 = true;
                            vertX[objVertices] = objvalue(line, 0);
                            vertY[objVertices] = objvalue(line, 1);
                            vertZ[objVertices] = objvalue(line, 2);
                            objVertices++;
                        } else {
                            tooManyVertices = true;
                        }
                    if (line.startsWith("f "))
                        if (objFaces < MAX_OBJ_FACES) {
                            multiplyObjValuesBy10 = false;
                            objfacend = false;
                            for (totalFaceVerts[objFaces] = 0; !objfacend && totalFaceVerts[objFaces] < 100; totalFaceVerts[objFaces]++) {
                                face[objFaces][totalFaceVerts[objFaces]] = objvalue(line, totalFaceVerts[objFaces]);
                            }
                            faceColor[objFaces] = currentColor;
                            objFaces++;
                        } else {
                            tooManyFaces = true;
                        }
                    if (line.startsWith("usemtl ")) {
                        currentColor = materials.get(line.substring("usemtl ".length()));//assign to local var since we can have multiple faces per color
                        System.out.println("switching to material " + line.substring("usemtl ".length()) + " and it is null=" + ( currentColor == null));
                    }
                }
                if (tooManyVertices) {
                    JOptionPane.showMessageDialog(RunApp.frame, "Warning!\nThe number of Vertices in file " + objFile.getName() + " exceeded the maximum of " + MAX_OBJ_VERTS + " that the LiveO can read!     \n\nPlease choose a simpler model to import.\n \n", "LiveO", JOptionPane.ERROR_MESSAGE);
                }
                if (tooManyFaces) {
                    JOptionPane.showMessageDialog(RunApp.frame, "Warning!\nThe number of Faces in file " + objFile.getName() + " exceeded the maximum of " + MAX_OBJ_FACES + " that the LiveO can read!     \n\nPlease choose a simpler model to import.\n \n", "LiveO", JOptionPane.ERROR_MESSAGE);
                }
                bufferedreader.close();
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(RunApp.frame, "Unable to load file! Error Details:\n" + e, "LiveO", JOptionPane.ERROR_MESSAGE);
            }
            // From the Car Maker: rd.setColor(new Color(225, 225, 225));
            // From the Car Maker: rd.fillRect(116, 246 + rectW, 468, 50);
            // From the Car Maker: rd.setColor(new Color(0, 0, 0));
            // From the Car Maker: rd.setFont(new Font("Arial", 1, 13));
            // From the Car Maker: ftm = rd.getFontMetrics();
            // From the Car Maker: rd.drawString("Importing " + objFile.getName() + ", please wait...", 350 - ftm.stringWidth("Importing " + objFile.getName() + ", please wait...") / 2, 276 + rectW);
            // From the Car Maker: repaint();
            
            // create car
            String carName = objFile.getName();
            if (carName.endsWith(".obj")) {
                carName = carName.substring(0, carName.length() - 4);
            }
            StringBuilder c = new StringBuilder();
            c.append("\n// imported car: " + carName + "\n---------------------\n\n// Please read the helpful information about importing cars found at:\n// http://www.needformadness.com/developer/extras.html\n\n\n");
            for (int curFace = 0; curFace < objFaces; curFace++) {
                c.append("<p>\n");
                if (faceColor[curFace] != null)
                    c.append("c(").append(faceColor[curFace].getRed()).append(",").append(faceColor[curFace].getGreen()).append(",").append(faceColor[curFace].getBlue()).append(")\n\n");
                else
                    c.append("c(200,200,220)\n\n");
                for (int curVert = 0; curVert < totalFaceVerts[curFace]; curVert++)
                    if (face[curFace][curVert] < MAX_OBJ_VERTS) {
                        final int _f = face[curFace][curVert];
                        c.append("p(").append(vertX[_f]).append(",").append(-vertY[_f]).append(",").append(vertZ[_f]).append(")\n");
                    }
                c.append("</p>\n\n");
            }
            c.append("\n\n\n\n");

            // populate texteditor
            
            // save car to file
            boolean errored = false;
            int saveImportedCar = JOptionPane.showConfirmDialog(RunApp.frame, "Save this car to " + carName + ".rad?", "LiveO", JOptionPane.YES_NO_OPTION);
            if (saveImportedCar == JOptionPane.YES_OPTION) {
                if (!RunApp.carfolder.exists()) {
                    if (RunApp.carfolder.mkdirs()) {
                        JOptionPane.showMessageDialog(RunApp.frame, "Failed to create car file, your car folder is missing and could not be created.\n", "LiveO", JOptionPane.ERROR_MESSAGE);
                        errored = true;
                    }
                } else if (!RunApp.carfolder.isDirectory()) {
                    JOptionPane.showMessageDialog(RunApp.frame, "Failed to create car file, your car folder is missing or invalid.\n", "LiveO", JOptionPane.ERROR_MESSAGE);
                    errored = true;
                }
                File carFile = new File(RunApp.carfolder + "/" + carName + ".rad");
                
                if (!errored) {
                    int popupStatus = JOptionPane.YES_OPTION;
                    if (carFile.exists()) {
                        popupStatus = JOptionPane.showConfirmDialog(RunApp.frame, "Another car with the name '" + carName + "' already exists, replace it?      \n", "LiveO", JOptionPane.YES_NO_OPTION);
                    }
                    if (popupStatus == JOptionPane.YES_OPTION) {
                        try {
                            final BufferedWriter writer = new BufferedWriter(new FileWriter(carFile));
                            writer.write(c.toString());
                            writer.close();
                            if (carFile.exists()) {
                                carFile.delete();
                            } else {
                                errored = true;
                                JOptionPane.showMessageDialog(RunApp.frame, "Failed to replace car, its file may be in use by the system.\n", "LiveO", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (final Exception e) {
                            errored = true;
                            JOptionPane.showMessageDialog(RunApp.frame, "Failed to replace car! Error Details:\n" + e + (e.getCause() != null ? "\nCaused by: " + e.getCause() : ""), "LiveO", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        }
                    } else {
                        errored = true;
                    }
                }
                
                if (!errored) {
                    int openCar = JOptionPane.showConfirmDialog(RunApp.frame, "Open imported file " + carName + ".rad in LiveO?", "LiveO", JOptionPane.YES_NO_OPTION);
                    if (openCar == JOptionPane.YES_OPTION) {
                        LiveO.contofile = carFile;
                        
                        try {
                            RunApp.t.loadFile();
                            RunApp.t.countPolys();
                            RunApp.applet.remake(RunApp.t.text.getText());
                        } catch (Exception e) {
                            System.err.println("Error loading ContO: " + e);
                            RunApp.postMsg("Error loading ContO: " + e + "\r\nIf you're sure this isn't your fault, tell rafa something went wrong and give him the full console log");
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(RunApp.frame, "Error, " + objFile.getName() + " is missing or invalid.", "LiveO", JOptionPane.ERROR_MESSAGE);
        }
        // From the Car Maker: setCursor(new Cursor(0));
    }
    

    private static float objvaluef(final String string, final int i) {
        float j = 0;
        try {
            int k = 2;
            int l = 0;
            int m = 0;
            String s2 = "";
            StringBuilder s3b = new StringBuilder();
            boolean bool = false;
            for (; k < string.length() && m != 2; k++) {
                s2 = String.valueOf(string.charAt(k));
                if (s2.equals(" ")) {
                    if (bool) {
                        l++;
                        bool = false;
                    }
                    if (m == 1 || l > i) {
                        m = 2;
                    }
                } else {
                    if (l == i) {
                        s3b.append(s2);
                        m = 1;
                    }
                    bool = true;
                }
            }
            String s3 = s3b.toString();
            if (k >= string.length()) {
                objfacend = true;
            }
            if (s3.equals("")) {
                s3 = "0";
            }
            final int bkslsh = s3.indexOf("/", 0);
            if (bkslsh != -1) {
                s3 = s3.substring(0, bkslsh);
            }
            j = Float.parseFloat(s3); //keep float.valueof here
            if (j < 0) {
                j = 0;
            }
        } catch (final Exception e) {
            
        }
        return j;
    }


    private static int objvalue(final String string, final int i) {
        int j = 0;
        try {
            int k = 2;
            int l = 0;
            int m = 0;
            String s2 = "";
            final StringBuilder s3b = new StringBuilder();
            boolean bool = false;
            for (; k < string.length() && m != 2; k++) {
                s2 = String.valueOf(string.charAt(k));
                if (s2.equals(" ")) {
                    if (bool) {
                        l++;
                        bool = false;
                    }
                    if (m == 1 || l > i)
                        m = 2;
                } else {
                    if (l == i) {
                        s3b.append(s2);
                        m = 1;
                    }
                    bool = true;
                }
            }
            String s3 = s3b.toString();
            if (k >= string.length())
                objfacend = true;
            if (s3.equals(""))
                s3 = "0";
            if (multiplyObjValuesBy10) {
                j = (int) (Float.parseFloat(s3) * 10.0F);
            } else {
                final int bkslsh = s3.indexOf("/", 0);
                if (bkslsh != -1)
                    s3 = s3.substring(0, bkslsh);
                j = ((int) Float.parseFloat(s3)) - 1; //keep float.valueof here
                if (j < 0) {
                    j = 0;
                }
            }
        } catch (final Exception e) {
            
        }
        return j;
    }
}

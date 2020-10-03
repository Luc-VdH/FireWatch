

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataProcessing {
    //boolean array true for, temp, CO
    private boolean [] in;
    private double [][] temp = new double[1800][900];
    private double [][] co = new double[1800][900];
    private double x;
    private double y;
    private int intX = 0;
    private int intY = 0;

    public float [][] hotspots = new float[1800][900];

    public DataProcessing(String file, boolean [] in){
        this.in=in;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            System.out.println("file error");
        }




        while (sc.hasNext()){
            String [] scLine = sc.nextLine().split(",");
            y = Double.parseDouble(scLine[0]);
            y = y + 90;
            intY = (int)(y*5);
            x = Double.parseDouble(scLine[1]);
            x = x + 180;
            intX = (int)(x*5);
            //System.out.println(scLine[13]);
            if (in[0]) {
                co[intX][intY] = Double.parseDouble(scLine[2]);
            }
            if(in[1]) {
                temp[intX][intY] = Double.parseDouble(scLine[3]);
            }
        }

        sc.close();

    }

    public void findHotspots(){
        for (int i = 0; i < 1800; i++){
            for (int j = 0; j < 900; j++){
                //System.out.println(temp[i][j]);
                if(temp[i][j] > 303.3 && co[i][j] > 2180801908453360000.0){
                    hotspots[i][j] = 1.0f;
                }
            }
        }
    }
}

package input;

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
            int intY = (int)(y*5);
            x = Double.parseDouble(scLine[1]);
            x = x + 180;
            int intX = (int)(x*5);
            //System.out.println(scLine[13]);
            temp[intX][intY] = Double.parseDouble(scLine[2]);
            co[intX][intY] = Double.parseDouble(scLine[3]);

        }

        sc.close();

    }
}

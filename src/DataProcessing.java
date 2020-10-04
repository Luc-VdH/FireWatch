/**
 * @author Liam Watson and Luc Van Den Handel
 * This class processes the input data to identify the wildfire hotspots
 */

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

    /**
     * Constructor method for data processing, used to determine wildfire hotspots based on input data
     * @param file file name (src/input/<file name>)
     * @param in boolean array representing which data is available in the order [total carbon monoxide, temperature]
     *           - true representing the presence of the data in the input file
     */
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

    /**
     * Runs the identification of the hotspots populating the global 2D float array hotspots
     */
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

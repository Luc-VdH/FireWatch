/*
@author UCT Computer Science Dept. and Luc Van Den Handel
This class represents a terrain modeled by a grid of tempVals s.
*/


//package FlowSkeleton;

import java.io.File;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Terrain {

	float [][] tempVals ; // regular grid of tempVals  values
	int dimx = 1800;
	int dimy = 900;// data dimensions
	BufferedImage img; // greyscale image for displaying the terrain top-down
	private float [][] grey = new float[dimx][dimy];

	ArrayList<Integer> permute;	// permuted list of integers in range [0, dimx*dimy)
	
	
/*
Gets the number of points in the grid (x*y)
@return integer containing number of points	
*/
	
	int dim(){
		return dimx*dimy;
	}
	
/*
Gets the x dimention of the grid.
@return x dimention        
*/
	int getDimX(){
		return dimx;
	}
	
/*
Gets the y dimention of the grid
@return y dimention       
*/
	int getDimY(){
		return dimy;
	}
	
/*
Gets the greyscale image of the terrain with any water overlaid atop it
@return image of terrain and water        
*/
	public BufferedImage getImage() {
		  return img;
	}
	
/*
Converts a position in the list as a 2D point 
@param pos integer of position in list
@param ind integer array to accept x and y values        
*/
	void locate(int pos, int [] ind)
	{
		ind[0] = (int) pos / dimy; // x
		ind[1] = pos % dimy; // y	
	}
	
/*
Converts and stores greyscale values of each tempVals  point and popualtes image of the terrain without any water
*/	
	void deriveImage()
	{

		img = new BufferedImage(dimx, dimy, BufferedImage.TYPE_INT_ARGB);
		float maxh = -10000.0f, minh = 10000.0f;
		
		// determine range of tempVals s
		for(int x=0; x < dimx; x++)
			for(int y=0; y < dimy; y++) {
				float h = tempVals [x][y];
				if(h > maxh)
					maxh = h;
				if(h < minh)
					minh = h;
			}
		
		for(int x=0; x < dimx; x++)
			for(int y=0; y < dimy; y++) {
				 	
				// find normalized tempVals  value in range
				float val = (tempVals [x][y] - minh) / (maxh - minh);
				grey[x][y] = val;
				if(val != 0) {
					Color col = new Color(val, 0, 0);
					img.setRGB(x, y, col.getRGB());
				}
				 	
			}
			
		
			
	}
	
/*
Retrieves greyscale values and calculates blue value from water array to populate image with greyscale terrain with blue water overlaid atop it.
@param water 2D array of water object to be drawn onto the terrain        
*/	
	void deriveWaterImage(Water [][] water){
		 img = new BufferedImage(dimy, dimx, BufferedImage.TYPE_INT_ARGB);
                float maxh = -10000.0f, minh = 10000.0f;
                
         /*       // determine range of tempVals s
                for(int x=0; x < dimx; x++)
                        for(int y=0; y < dimy; y++) {
                                float h = tempVals [x][y];
                                if(h > maxh)
                                        maxh = h;
                                if(h < minh)
                                        minh = h;
                        }
          */      
                for(int x=0; x < dimx; x++)
                        for(int y=0; y < dimy; y++) {
                                 if(water[x][y].getLevel() >0){
                                 	
                                 	int max = water[x][y].getLevel() * 30;
                                 	int r = 0;
                                 	int g = 0;
                                 	int b = 0;
                                 	if(max <= 510){
                                 		if(max >= 255){
                                 			b = 255 -(max-255);
                                 		}else{
                                 			b = 255;
                                 			r = (int)((255-max)/2.0);
                                 			g = (int)((255-max)/2.0);
                                 		}	
                                 	}
                                 	
                                 	
                                 	     
                                        Color blue = new Color(r,g,b);
                                        img.setRGB(x, y, blue.getRGB());
                                 }else{ 
                                        // find normalized tempVals  value in range
                                        //float val = (tempVals [x][y] - minh) / (maxh - minh);
                                        float val = grey[x][y];
                                        Color col = new Color(val, val, val, 1.0f);
                                        img.setRGB(x, y, col.getRGB());
                                 }      
                                        
                        }
                        
	}
/*
Generates a permuted list of tempVals s to randomise which points are processed by the threads
*/	
	void genPermute() {
		permute = new ArrayList<Integer>();
		for(int idx = 0; idx < dim(); idx++)
			permute.add(idx);
		java.util.Collections.shuffle(permute);
	}
	
/*
Gets a 2D point in the grid from a point in the permutes list 
@param i index in list
@param loc integer array to store x and y coords       
*/
	void getPermute(int i, int [] loc) {
		locate(permute.get(i), loc);
	}
	
/*
Reads a data file to optain grid of tempVals s
@param fileName name of file to be read.        
*/
	void readData(String fileName){
		double x = 0;
		double y = 0;
		int intX = 0;
		int intY = 0;
		try{ 
			Scanner sc = new Scanner(new File(fileName));
			
			// read grid dimensions
			// x and y correpond to columns and rows, respectively.
			// Using image coordinate system where top left is (0, 0).

			
			// populate tempVals  grid
			tempVals = new float[dimx][dimy];
			sc.nextLine();
			

			while (sc.hasNext()){
				String [] scLine = sc.nextLine().split(",");
				y = Double.parseDouble(scLine[0]);
				y = y + 90;
				intY = (int)(y*5);
				x = Double.parseDouble(scLine[1]);
				x = x + 180;
				intX = (int)(x*5);
				//System.out.println(scLine[13]);
				tempVals[intX][intY] = Float.parseFloat(scLine[13]);

			}
				
			sc.close(); 
			
			// create randomly permuted list of indices for traversal 
			genPermute(); 
			
			// generate greyscale tempVals field image
			deriveImage();
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (java.util.InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
		}
	}
}
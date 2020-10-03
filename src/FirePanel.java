/*
* @author UCT Computer Science Dept. and Luc Van Den Handel
* This class produces a panel with the greyscale image of the terrain and water, the run method continuously iterates the water flow processing to allow the simulation to function. 
*/


//package FlowSkeleton;
import java.util.concurrent.atomic.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ForkJoinPool;


public class FirePanel extends JPanel implements Runnable{
	Terrain land;
	

	
	//private AtomicInteger step = new AtomicInteger(0);
	Water [][] wArr;
	AtomicInteger step = new AtomicInteger(0);
        AtomicBoolean keepRunning = new AtomicBoolean(true);
        AtomicBoolean paused = new AtomicBoolean(true);
        
        /*
	* The constructor method for a FlowPanel object
	* @param terrain the Terrain object which stores the terrain represented by the greyscale image.
	*/	
	FirePanel(Terrain terrain) {
		land=terrain;
		wArr = new Water[terrain.getDimX()][terrain.getDimY()];
		
		for(int i = 0; i < terrain.getDimX(); i++){
                        for(int j = 0; j < terrain.getDimY(); j++){
                                wArr[i][j] = new Water(0);
                        }
                }
		
				
	}
		
	/*
	* Gets the image from the Terrain object and prints it on the panel.
	* @param g Graphics object used for printing
	*/		
	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		
		
		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}
	}
	/*
	* This method runs continuously during program operation constantly iterating, determining the flow of the water
	*/	
	public void run() {	
		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
		
		
		//super.add(output);
		keepRunning.set(true);
		while(keepRunning.get()){
			//super.add(output);
			if(paused.get() == false){

				
				FlowThread f = new FlowThread(land, wArr, 0, land.dim());
				ForkJoinPool.commonPool().invoke(f);
				land.deriveWaterImage(wArr);
				//Graphics g = this.getGraphics();
				//this.paintComponent(g);
				repaint();		
				
			}
		 
			
			
			
			
		}
				
		repaint();
	}
	/*
	* Used for the cases when the simulation is paused but water still needs to be painted onto the panel.
	*/	
	public void rePaintWater(){
		land.deriveWaterImage(wArr);
		repaint();
		//Graphics g = this.getGraphics();
		//this.paintComponent(g);
	}
	
	
	
	

}
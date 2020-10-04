/*
* @author Liam Watson and Luc Van Den Handel, based on class by the UCT Dept. of Computer Science
* This class produces a panel with the greyscale image of the terrain and water, the run method continuously iterates the water flow processing to allow the simulation to function. 
*/



import java.util.concurrent.atomic.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ForkJoinPool;


public class FirePanel extends JPanel implements Runnable{
	Terrain land;
	

	
	//private AtomicInteger step = new AtomicInteger(0);

	AtomicInteger step = new AtomicInteger(0);
        AtomicBoolean keepRunning = new AtomicBoolean(true);
        AtomicBoolean paused = new AtomicBoolean(true);
        
        /*
	* The constructor method for a FlowPanel object
	* @param terrain the Terrain object which stores the terrain represented by the greyscale image.
	*/	
	FirePanel(Terrain terrain) {
		land=terrain;

		

		
				
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

				

				land.deriveImage();
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
		land.deriveImage();
		repaint();
		//Graphics g = this.getGraphics();
		//this.paintComponent(g);
	}
	
	
	
	

}
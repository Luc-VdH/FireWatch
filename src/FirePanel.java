/**
* @author Liam Watson and Luc Van Den Handel, based on class by the UCT Dept. of Computer Science
* This class produces a panel with the world image with hotspots overleyed atop it, contains a run method to host further live updating functionality.
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
        
        /**
	* The constructor method for a FlowPanel object
	* @param terrain the Terrain object which stores the terrain world image and identified hotspots.
	*/	
	FirePanel(Terrain terrain) {
		land=terrain;

		

		
				
	}
		
	/**
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
	/**
	* This method contains a framework for future additions such as live updating of the world image and hotspots
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

	
	
	
	

}
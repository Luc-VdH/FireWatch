/*
@author UCT Computer Science Dept. and Luc Van Den Handel
This main class hosts the main method for the running of the water flow simulation program/
*/



import java.util.concurrent.atomic.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;

public class FireWatch implements MouseListener{
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static FirePanel fp;
	

	
	 

/*
Constructor method for a FireWatch object, takes no parameters.
*/	
	FireWatch(){}
	
/*
Sets up the user interface.
@param frameX x-width of the frame
@param frameY y-width of the frame
@param landdata the terrain object represting the world and hotsopts.
*/		
	public static void setupGUI(int frameX,int frameY,Terrain landdata){
		
		Dimension fsize = new Dimension(800, 800);
		JFrame frame = new JFrame("FireWatch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
    	
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
   
		fp = new FirePanel(landdata);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		g.add(fp);
		Thread fpt = new Thread(fp);
		
		// to do: add a MouseListener, buttons and ActionListeners on those buttons
		FireWatch m = new FireWatch();
		g.addMouseListener(m);



	   	
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
		JButton endB = new JButton("End");;
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to stop
				fp.keepRunning.set(false);
				while(fpt.isAlive()){
				}
				frame.dispose();
				System.exit(0);
			}
		});

		b.add(endB);
		//b.add(output);
		g.add(b);
		//Thread fpt = new Thread(fp);
		
    	
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
		frame.setLocationRelativeTo(null);  // center window on screen
		frame.add(g); //add contents to window
		frame.setContentPane(g);
		frame.setVisible(true);
		fpt.start();
	}
	
/*
Main method used to run the program, takes the name of the file containing the grid of heights as an argument
@param args string array of arguments        
*/		
	public static void main(String[] args) {
		Terrain landdata = new Terrain();
		
		// check that number of command line arguments is correct
		if(false)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar FireWatch.java intputfilename");
			System.exit(0);
		}
				
		// landscape information from file supplied as argument
		// 
		landdata.readData("src/input/MOP02J-20200322-L2V18.0.3.csv", "src/input/World.jpg");
		
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		
		
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata));
		
		// to do: initialise and start simulation
	}
/*
Mouse clicked event - does nothing
@param e MouseEvent        
*/	
	public void mouseClicked(MouseEvent e) {  
                          
        }
/*
Mouse entered event - does nothing
@param e MouseEvent        
*/          
        public void mouseEntered(MouseEvent e) {  
                  
        }
/*
Mouse exited event - does nothing
@param e MouseEvent        
*/                 
        public void mouseExited(MouseEvent e) {
        
        }
/*
Mouse pressed event adds 3 to the water level of the points of a 7x7 square centered around the position of the mouse at the time of event. 
@param e MouseEvent        
*/          
        public void mousePressed(MouseEvent e) {  
                //System.out.println(e.getX() + " " +  e.getY());
                
                int nowX = e.getX();
                int nowY = e.getY();
                

        }
/*
Mouse released event - does nothing
@param e MouseEvent        
*/          
        public void mouseReleased(MouseEvent e) {  
                  
        }
}



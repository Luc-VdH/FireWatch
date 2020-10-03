import java.util.ArrayList;
import java.util.concurrent.*;
/*
* @author Luc Van Den Handel
* This class facilitates the thread functionality, recursively dividing up the grid untill a sequential cut-off is reached where a nested for loop processes the points. 
*/

class FlowThread extends RecursiveAction{
    //variable declaration
    Terrain  map;
    Water[][] water;
    int start = 0;
    int end = 0;
    ArrayList<Integer> permute;
    float [][] land;
    int [] point = new int[2];
    int x = 0;
    int y = 0;
    float curWaterLevel = 0f;
    float othrWaterLevel = 0f;
    
    /*
    * Constructor method for a FlowThread object, populates local variables
    * @param map terrain object containing grid of heights
    * @param water 2D array of water objects to be processed
    * @param start the start index of portion of the list of points
    * @param end the end index of portion of the list of points
    */    
    public FlowThread(Terrain map, Water[][] water, int start, int end){
        this.map = map;
        this.water = water;
        this.start = start;
        this.end = end;
        this.permute = map.permute;
        this.land = map.tempVals;
        
        
        
    }  
/*
computes the flow of the water points or splits into more threads depending on the number of points to process.
*/    
    protected void compute(){
        if(end - start < (map.dim()/4)){
            for(int i = start; i < end; i++){
                map.getPermute(i, point);
                x = point[0];
                y = point[1];
                
                if (!(x != 0 && y != 0 && x < map.getDimX()-1 && y < map.getDimY()-1)){
                    
                    water[x][y].setLevel(0); //this line was commented out during the testing of the amount of water in the model vs the amount of water added.
                    
                }else{
                
                    if(water[x][y].getLevel() > 0){
                        synchronized (water){
/*                        synchronized(water[x][y-1]){
                        synchronized(water[x][y]){
                        synchronized(water[x][y+1]){
                        synchronized(water[x-1][y]){
                        synchronized(water[x+1][y]){
                        synchronized(water[x-1][y-1]){
                        synchronized(water[x+1][y+1]){
                        synchronized(water[x-1][y+1]){
*/                        synchronized(water[x+1][y-1]){
                            curWaterLevel = (float)(water[x][y].getLevel()*0.01) + land[x][y];
                            float low = 9999999f;
                            int lowX = 0;
                            int lowY = 0;
                            boolean flow = false;
                    
                            for(int j = -1; j <= 1; j++){
                                for(int k = -1; k <=1; k++){
                            
                                    othrWaterLevel = (float)(water[x + j][y + k].getLevel()*0.01) + land[x + j][y + k];
                                    if( j != 0 && k != 0){
                                        if(curWaterLevel > othrWaterLevel && othrWaterLevel < low){
                                            low = othrWaterLevel;
                                            lowX = x + j;
                                            lowY = y + k;
                                            flow = true;
                                        }
                                    }
                                }	
                            }
                    
                            if(flow){
                                // water[x][y].setLevel(water[x][y].getLevel() - 1);
                                //water[lowX][lowY].setLevel(water[lowX][lowY].getLevel() + 1);
                                water[x][y].addToLevel(-1);
                                water[lowX][lowY].addToLevel(1);
                            }
                        }
/*                        }
                        }
                        }
                        }
                        }
                        }
                        }
                        }
*/                        }
                    }
                }
            }
        }else{
            FlowThread f1 = new FlowThread(map, water, start, (end + start)/2);
            FlowThread f2 = new FlowThread(map, water, (start + end)/2, end);
            
            f1.fork();
            f2.compute();
            f1.join();
        }
    }
    
      
    
    
    
}
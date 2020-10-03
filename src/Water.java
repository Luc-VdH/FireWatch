/*
@author Luc Van Den Handel
This class stores an indevidal point of water        
*/

public class Water{

    private int level = 0;
/*
Constructs a water point with the given water level
@param level level of water point        
*/    
    public Water(int level){
        this.level = level;
    }
/*
Gets the level of the water object (thread safe)
@return level of water        
*/    
    synchronized int getLevel(){
        return level;
    }
/*
Sets the water level of the water point (thread safe)
@param level new water level        
*/    
    synchronized void setLevel(int level){
        this.level = level;
    }
/*
Adds an integer value to the current water level (thread safe)
@param add value to be added to the water level        
*/    
    synchronized void addToLevel(int add){
        level = level + add;
    }

}

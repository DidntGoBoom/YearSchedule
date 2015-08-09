import java.util.*;

/**
 * Dumb class to fix totally broken Observable which should be an interface not a class idiots.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SDDVObs extends Observable
{
    /**
     * Constructor for objects of class SDDVObs
     */
    private Object realObs;
    
    public SDDVObs(Object obsthingie)
    {
        realObs = obsthingie;
    }
    
    public void doyourThing()
    {
        setChanged();
        notifyObservers(realObs);
    }
}

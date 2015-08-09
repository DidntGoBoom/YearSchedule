
/**
 * Write a description of class DebugFacility here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DebugFacility
{
    // instance variables - replace the example below with your own
    static private int level=0;
    static public final int NO_DEBUG = 0;
    static public final int ALL_DEBUG = 99;
    
    static public int getLevel() { return level; }
    static public void setLevel(int l) { level = l; }
    
    static public void sendMessage(int i, String m)
    {
        i = Math.min(i, ALL_DEBUG-1);
        if( i <= level )
        {
            System.out.println(m);
        }
    }
}

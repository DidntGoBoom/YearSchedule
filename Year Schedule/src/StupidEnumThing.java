import java.util.*;

class StupidEnumThing implements Enumeration<Object>
{
    ArrayList<?> theAL;
    int counter=0;
    
    public StupidEnumThing(ArrayList<?> list)
    {
        theAL = list;
    }
    
    public boolean hasMoreElements()
    {
        return counter < theAL.size();
    }
    
    public Object nextElement()
    {
        return theAL.get(counter++);
    }
}
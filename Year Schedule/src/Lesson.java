import java.util.*;
import org.joda.time.*;

/**
 * Write a description of class Lesson here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lesson extends MetaLeaf
{
    // private Unit theUnit; the unit is now the parent through MetaLeaf
    
    private ArrayList<SchoolDay> sDays=new ArrayList<SchoolDay>();
    static final Lesson NoLesson = new Lesson();

    /**
     * Constructor for objects of class Lesson
     */
    public Lesson()
    {
        setName("No Lesson");
        setDuration(0);
        setDescription("No Description");
        setUnit(Unit.NoUnit);
    }
    
    public Lesson( String n, int d, String ds, Unit u)
    {
        setName(n);
        setDuration(d);
        setDescription(ds);
        setUnit(u);

        for( int i=0; i<mlDuration; i++ )
        {
            sDays.add(new SchoolDay(this));
            // Do other SchoolDay stuff
        }
    }
    
    public void setUnit(Unit u) { theParent = u; }
    public Unit getUnit() { return (Unit)getParent(); }

    public int getCurrentDayNum(LocalDate d)
    {
        if( mlDuration == 1 )
        {
            return 1;
        }

        int diff=1;
        for( LocalDate ld = startDate; !ld.equals(endDate); ld = SchoolCalendar.GetCalendar().getNextDay(ld) )
        {
            if( ld.equals(d) )
            {
                break;
            }
            diff++;
        }
        return diff;
    }

    public String toString(boolean b)
    {
        if( !b ) 
            return toString();
        return "Lesson "+mlName+" is "+mlDuration+" days long, starting on "+startDate;
    }

    public String toString() { return "Lesson "+mlName; }

    @Override
    public String saveYourself()
    {
        String me = mlComment + "L" + mlSequence + ";"+mlDuration+";" + mlName+";"+mlDesc+"\n";
        return me;
    }
}

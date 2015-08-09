import java.io.FileNotFoundException;

import org.joda.time.LocalDate;

/**
 * Creates windows for both APCS and ECS calendars
 * 
 */
public class CalendarSet
{
    private SchoolCalendar myCal;
    private ClassCalendar ecsCal;
    private ClassCalendar apcsCal;

    public static void main( String[] argv) throws Exception
    {
    	new CalendarSet();
    }
    /**
     * Constructor for objects of class CalendarSet
     */
    public CalendarSet() throws FileNotFoundException
    {
        // initialise instance variables
        myCal = SchoolCalendar.GetCalendar();
        ecsCal = new ClassCalendar("ecs_curr.txt");
        CCGUIView ecsView = new CCGUIView(ecsCal);
        ecsView.display();
        ecsView.showWindow(true);

        apcsCal = new ClassCalendar("apcs_curr.txt");
        CCGUIView apcsView = new CCGUIView(apcsCal);
        apcsView.display();
        apcsView.showWindow(true);
        
        LocalDate today = new LocalDate();
        System.out.println("Date "+today);
        
        System.out.println("Today is "+myCal.dayNumber(today));
        System.out.println("4 daysis "+myCal.getNthDay(today, 4));
    }
}
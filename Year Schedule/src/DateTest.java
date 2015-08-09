import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Write a description of class DateTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DateTest
{
    DateTest()
    {
        DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime time = dateStringFormat.parseDateTime("11/3/2013");
        System.out.println(time);
        LocalDate date = time.toLocalDate();
        System.out.println(date+" "+date.dayOfWeek().get());
    }
    
    static void dt2()
    {
        LocalDate date = new LocalDate();
        System.out.println(date+" "+date.dayOfWeek().get());
    }
}

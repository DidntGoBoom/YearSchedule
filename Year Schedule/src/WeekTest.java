import org.joda.time.*;
import org.joda.time.format.*;

public class WeekTest
{
    static void main()
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("y-M-d");
        LocalDate d = fmt.parseLocalDate("2014-1-1");
        for( int i=0; i<52; i++ )
        {
            int w = d.getWeekOfWeekyear();
            System.out.println("Date "+d+" Week number "+w);
            d.plusDays(7);
        }
    }
}
            
import java.io.*;
import java.util.*;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Write a description of class TheCalendar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

class SchoolCalendar
{
    public final static String FirstDayOfSchool=new String("FDOS");
    public final static String Semester1End=new String("SEM1END");
    public final static String Semester2Begin=new String("SEM2BEGIN");
    public final static String LastDayOfSchool=new String("LDOS");

    private static int __days=0;
    public final static int iFDOS = __days++;
    public final static int iSEM1BEGIN = __days++;
    public final static int iSEM1END = __days++;
    public final static int iSEM2BEGIN = __days++;
    public final static int iLDOS = __days++;
    public final static int iSEM2END = __days++;
    public final static int iNUMSDAYS = __days;
    static int DebugLevel=1;
    
    protected class BloodyDateThing 
    {
        String sDay;
        LocalDate theDay;
        
        protected BloodyDateThing(LocalDate d, int l, String y)
        {
            // year coming in -1900
            theDay = d;
        }
        
        protected BloodyDateThing(int y, int m,  int d, int l, String day)
        {
            theDay = new LocalDate(y, m, d);
        }
        
        public LocalDate getLocalDate() { return theDay; }
        
        @Override
        public boolean equals(Object other)
        {
            if (other == null) return false;
            if (other == this) return true;
            if (!(other instanceof BloodyDateThing))return false;
            BloodyDateThing d = (BloodyDateThing)other;
            return theDay.equals(d.getLocalDate());
        }
    }
    
    protected class HoliDay extends BloodyDateThing
    {
        int sLength;
        String sDay;

        protected HoliDay( LocalDate d, int l, String y)
        {
            super( d, l, y);
            setLength(l);
        }

        public void setLength( int l) { sLength = l; }

        int getLength() { return sLength; }

        public String toString() { return "HoliDay "+super.toString(); }
    }

    protected class _SchoolDay extends BloodyDateThing
    {
        int sType;

        protected _SchoolDay( LocalDate d, int t)
        {
            super( d, 1, "");
            setType(t);
        }

        public void setType( int t) { sType = t; }

        int getType() { return sType; }

        public String toString() { return "_SchoolDay "+super.toString(); }
    }
    
    static SchoolCalendar theCalendar=null;
    
    static void TestMe() throws FileNotFoundException
    {
        SchoolCalendar c = GetCalendar();
        c.dumpCalendar();
        LocalDate fd = c.getFirstDayOfSchool().getLocalDate();
        LocalDate ld = c.getLastDayOfSchool().getLocalDate();
        System.out.println("First day "+fd);
        System.out.println("First week "+fd.getWeekOfWeekyear());
        System.out.println("Last  day "+ld);
        System.out.println("Last  week "+ld.getWeekOfWeekyear());
        System.out.println("Number of weeks is: "+GetNumberWeeks());
        LocalDate d1 = new LocalDate(2013, 10, 1);
        LocalDate d2 = new LocalDate(2013, 12, 31);
        LocalDate d3 = new LocalDate(2014, 1, 1);
        LocalDate d4 = new LocalDate(2014, 4, 1);
        System.out.println("Number of weeks is: "+GetNumberWeeks(d1, d2));
        System.out.println("Number of weeks is: "+GetNumberWeeks(d1, d3));
        System.out.println("Number of weeks is: "+GetNumberWeeks(d1, d4));
        System.out.println("Number of weeks is: "+GetNumberWeeks(d2, d3));
    }
    
    static void PrintDebugMessage(int l, Object s)
    {
        if( l < DebugLevel )
        {
            System.out.println( s.toString());
        }
    }
    
    void dumpCalendar()
    {
        int i=1;
        for( LocalDate d : syDays )
        {
            PrintDebugMessage(0,i++ +" "+ d);
        }
    }

    static SchoolCalendar GetCalendar(String year)
    {
        try
        {
            if( theCalendar == null )
                theCalendar = new SchoolCalendar(year);
        }
        catch( Exception e )
        {
            System.out.println("No calendar files.");
        }
        return theCalendar;
    }

    static SchoolCalendar GetCalendar()
    {
        try
        {
            if( theCalendar == null )
                theCalendar = new SchoolCalendar();
        }
        catch( Exception e )
        {
            System.out.println("No calendar files.");
        }
        return theCalendar;
    }
    
    public static boolean IsValidDate(LocalDate d)
    {
        boolean before = (d.compareTo( GetCalendar().getFirstDayOfSchool().getLocalDate())) < 0;
        boolean after = (d.compareTo( GetCalendar().getLastDayOfSchool().getLocalDate())) > 0;
        return before || after;
    }

    /**
     * Constructor for objects of class TheCalendar
     */
    protected ArrayList<HoliDay> hdCal = new ArrayList<HoliDay>();
    protected _SchoolDay[] syCal = new _SchoolDay[iNUMSDAYS];
    
    protected ArrayList<LocalDate> syDays = new ArrayList<LocalDate>();

    protected SchoolCalendar(String year) throws FileNotFoundException
    {
        readHoliDays("Holidays-"+year+".txt");
        read_SchoolDays("SchoolDays-"+year+".txt");
        buildCalendar();
    }
    
    protected SchoolCalendar() throws FileNotFoundException
    {
        readHoliDays("Holidays-14-15.txt");
        read_SchoolDays("SchoolDays-14-15.txt");
        buildCalendar();
    }
    
    public _SchoolDay getFirstDayOfSchool() { return syCal[iFDOS]; }
    public _SchoolDay getLastDayOfSchool() { return syCal[iLDOS]; }
    
    private void read_SchoolDays(String inFileName) throws FileNotFoundException
    {
        int dbg = DebugLevel;
        DebugLevel = 0;

        File inFile = new File(inFileName);
        Scanner in = new Scanner(inFile);
        String word;
        DateTimeFormatter fmt = DateTimeFormat.forPattern("y-M-d");

        while( in.hasNext() /*&& (i++ < 30) */)
        {
            LocalDate d=null;
            _SchoolDay sd=null;
            word = in.nextLine();
            String[] result = word.split(";");

            d = fmt.parseLocalDate(result[0]);
            PrintDebugMessage(1, "Parsed date: "+d);
            sd = new _SchoolDay( d, getType(result[1]));
            syCal[getType(result[1])] = sd;

            PrintDebugMessage(1, "School Day is: "+sd);
        }
        in.close();
        DebugLevel = dbg;
    }

    private void readHoliDays(String inFileName) throws FileNotFoundException
    {
        int dbg = DebugLevel;
        DebugLevel = 2;

        File inFile = new File(inFileName);
        Scanner in = new Scanner(inFile);
        String word;

        DateTimeFormatter fmt = DateTimeFormat.forPattern("y-M-d");
        /*
         * Loop through holiday file
         */
        while( in.hasNext() /*&& (i++ < 30) */)
        {
            /*
             * For each line in the file, read the date, 
             * create a LocalDate, then read the count and
             * add as many localdates as required.  Use 
             * only weekdays - weekends are handled separately
             */
            LocalDate d=null;
            HoliDay hd=null;
            word = in.nextLine();
            String[] result = word.split(";");
            d = fmt.parseLocalDate(result[0]);
            PrintDebugMessage(1, "Parsed date: "+d);
            hd = new HoliDay( d, Integer.parseInt(result[1]), result[2]);
            hdCal.add( hd);
            PrintDebugMessage(1, "Holiday "+hd);
            
            int count=hd.getLength();
            for( int ii=1; ii<count; )
            {
                d = d.plusDays(1);
                
                if( !IsWeekend(d) )
                {
                    hd = new HoliDay( d, Integer.parseInt(result[1]), result[2]);
                    hdCal.add( hd);
                    ii++;
                }
                PrintDebugMessage(1, "Holiday "+hd);
            }
        }
        in.close();
        DebugLevel = dbg;
    }
    
    public static int GetWeekForDate(LocalDate now)
    {
        return GetNumberWeeks(GetCalendar().getFirstDayOfSchool().getLocalDate(), now);
    }
    
    public static int GetNumberWeeks()
    {
        return GetNumberWeeks(GetCalendar().getFirstDayOfSchool().getLocalDate(),
                        GetCalendar().getLastDayOfSchool().getLocalDate());
    }

    public static int GetNumberWeeks(LocalDate fd, LocalDate ld)
    {
        fd.toString();
        ld.toString();
        int fw = fd.getWeekOfWeekyear();
        int lw = ld.getWeekOfWeekyear();
        if( fw <= lw )
        {
            return lw - fw;
        }
        else
        {
            int nw = (new LocalDate(fd.getWeekyear(), 12, 31)).getWeekOfWeekyear();
            if( nw == 1 )
            {
                nw = (new LocalDate(fd.getWeekyear(), 12, 24)).getWeekOfWeekyear();
            }
            return (lw + nw) - fw;
        }
    }
    
    private void buildCalendar()
    {
        int num=1;
        LocalDate sd = syCal[iFDOS].getLocalDate();
        LocalDate ld = syCal[iLDOS].getLocalDate();
        syDays.add(sd);
        PrintDebugMessage(1, "First day is: "+sd);

        while(  !ld.equals(sd) )
        {
            PrintDebugMessage(1, "Whiling "+ld+" and "+sd);
            // get next day
            sd = getNextDay(sd);
            if( sd == null )
            {
                break;
            }
            // add to list
            syDays.add(sd);
            num++;
        }
        
        PrintDebugMessage(1, "Processed "+num+" days");
    }
    
    public LocalDate getNthDay(LocalDate d, int n)
    {
        LocalDate nth = d;
        for( int ii=0; ii<n; ii++ )
        {
            nth = getNextDay(nth);
        }
        return nth;
    }
    
    public LocalDate getNextDay(LocalDate d)
    {
        LocalDate newDay = d.plusDays(1);
        PrintDebugMessage(1, "Day before: "+d);
        PrintDebugMessage(1, "Day after:  "+newDay);
        while( isHoliday(newDay) || IsWeekend(newDay) )
        {
            newDay = newDay.plusDays(1);
            if( newDay.compareTo(syCal[iLDOS].getLocalDate()) > 0 )
                return null;
        }
        PrintDebugMessage(1, "Day after:  "+newDay);
        return newDay;
    }
    
    public LocalDate getPreviousDay(LocalDate d)
    {
        LocalDate newDay = d.minusDays(1);
        PrintDebugMessage(1, "Day before: "+d);
        PrintDebugMessage(1, "Day after:  "+newDay);
        while( isHoliday(newDay) || IsWeekend(newDay) )
        {
            newDay = newDay.minusDays(1);
            if( newDay.compareTo(syCal[iLDOS].getLocalDate()) > 0 )
                return null;
        }
        PrintDebugMessage(1, "Day after:  "+newDay);
        return newDay;
    }
    
    public static boolean IsHoliday(LocalDate d)
    {
        return theCalendar.isHoliday(d);
    }
    
    public boolean isHoliday(LocalDate d)
    {
        for( HoliDay testD : hdCal ) 
        {
            if( testD.getLocalDate().equals(d) )
            {
                PrintDebugMessage(1, "Holiday - "+d);
                return true;
            }
        }
        return false;
    }

    static public boolean IsWeekend(LocalDate d)
    {
        switch( d.getDayOfWeek() )
        {
        case DateTimeConstants.SUNDAY:
        case DateTimeConstants.SATURDAY:
            PrintDebugMessage(1, "Weekend - "+d);
            return true;
        case DateTimeConstants.MONDAY:
        case DateTimeConstants.TUESDAY:
        case DateTimeConstants.WEDNESDAY:
        case DateTimeConstants.THURSDAY:
        case DateTimeConstants.FRIDAY:
            break;
        }
        return false;
    }

    private int getType(String s)
    {
        if( s.equals(FirstDayOfSchool) )
            return iFDOS;
        if( s.equals(Semester1End) )
            return iSEM1END;
        if( s.equals(Semester2Begin) )
            return iSEM2BEGIN;
        if( s.equals(LastDayOfSchool) )
            return iLDOS;
        return -1;
    }
    
    public int dayNumber(LocalDate day)
    {
        ListIterator<LocalDate> it=syDays.listIterator();
        int index=0;
        
        while( it.hasNext() )
        {
            index++;
            if( it.next().equals(day) )
            {
                return index;
            }
        }
        
        return -1;
    }
    
    static public int GetDOWNumber(LocalDate d)
    {
        switch( d.getDayOfWeek() )
        {
        case DateTimeConstants.SUNDAY:
            return 0;
        case DateTimeConstants.MONDAY:
            return 1;
        case DateTimeConstants.TUESDAY:
            return 2;
        case DateTimeConstants.WEDNESDAY:
            return 3;
        case DateTimeConstants.THURSDAY:
            return 4;
        case DateTimeConstants.FRIDAY:
            return 5;
        case DateTimeConstants.SATURDAY:
        default:
            return 6;
        }
    }
}
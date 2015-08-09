import java.io.*;
import org.joda.time.*;

/**
 * Write a description of class CTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CTest
{
    // instance variables - replace the example below with your own
    @SuppressWarnings("unused")
	private SchoolCalendar myCal;
    private ClassCalendar apcsCal;

    /**
     * Constructor for objects of class CTest
     */
    public CTest() throws FileNotFoundException
    {
        // initialise instance variables
        myCal = SchoolCalendar.GetCalendar();
        apcsCal = new ClassCalendar("ecs_curr.ccal");
        //apcsCal = new ClassCalendar("apcs_curr.ccal");
        CCGUIView apcsView = new CCGUIView(apcsCal);
        apcsView.display();
        apcsView.showWindow(true);
        
        System.out.println("\n\n*******\n"+apcsCal.saveYourself()+"\n*******\n\n");
        
        new LocalDate();
    }

    public CTest(int i) throws FileNotFoundException
    {
        this(i, true);
    }

    public CTest(int i, boolean gui) throws FileNotFoundException
    {
        DebugFacility.setLevel(0);
        // initialise instance variables
        myCal = SchoolCalendar.GetCalendar();
        String name = "apcs_curr.ccal";
        switch( i )
        {
        case 0:
            name = "ecs_curr.ccal";
            break;
        case 1:
            name = "apcs_curr.ccal";
            break;
        case 2:
            name = "aplew.ccal";
            break;
        case 4:
            name = "ied.ccal";
            break;
        }
        apcsCal = new ClassCalendar(name);
        if( gui )
        {
            CCGUIView apcsView = new CCGUIView(apcsCal);
            apcsView.display();
            apcsView.showWindow(true);
        }
        else
        {
            CCHtmlView hv = new CCHtmlView(apcsCal);
            hv.doHTML();
        }
        
        //System.out.println("\n\n*******\n"+apcsCal.saveYourself()+"\n*******\n\n");
        
        new LocalDate();
    }
    
    public CTest(String s) throws FileNotFoundException
    {
        // initialise instance variables
        myCal = SchoolCalendar.GetCalendar();
        apcsCal = new ClassCalendar(s+".ccal");
        CCGUIView apcsView = new CCGUIView(apcsCal);
        apcsView.display();
        apcsView.showWindow(true);
        
        System.out.println("\n\n*******\n"+apcsCal.saveYourself()+"\n*******\n\n");
        
        new LocalDate();
    }
    
    static void TestECS() throws Exception
    {
        new CTest();
    }
    
    static void TestLew() throws Exception
    {
        new CTest(2);
    }
    
    static void TestLewH() throws Exception
    {
        new CTest(2, false);
    }
    
    static void TestIED() throws Exception
    {
        new CTest(4);
    }
    
    static void TestIEDH() throws Exception
    {
        new CTest(4, false);
    }
    
    static void TestAPCS() throws Exception
    {
        new CTest(1);
    }
}
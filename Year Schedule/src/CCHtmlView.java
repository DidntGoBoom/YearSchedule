import java.io.*;
import org.joda.time.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * CCHtmlView is the view portion of the Class Calendar 
 * suite.  It presents a calendar grid, populates it and 
 * controls scrolling and other UI functions.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CCHtmlView
{
    // describe each of the attributes declared below
    private ClassCalendar myCal;
    private FileWriter hFile;

    /**
     * Constructor for objects of class CCHtmlView
     *
     * Describe how the constructor is used.  Add @param fields if required
     */
    public CCHtmlView(ClassCalendar c)
    {
        setCal(c);

        File calFile = myCal.getCalendarFile();
        calFile.getAbsolutePath();
        File parentDir = calFile.getAbsoluteFile().getParentFile();
        JFileChooser jfc = new JFileChooser(parentDir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "HTML Files", "html");
        jfc.setFileFilter(filter);
        int returnVal = jfc.showSaveDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
            //doSaveFile(jfc.getSelectedFile());
            String jName = jfc.getSelectedFile().getName()+".html";
            System.out.println("You chose to open this file: " + jName);
            System.out.println("Save Thingie");
            // doSaveFile( jfc.getSelectedFile());
            doSaveFile( new File( jName));
        }
    }

    boolean doSaveFile(File saveFile)
    {
        try
        {
            if( saveFile.exists() )
            {
                return false;
            }

            saveFile.createNewFile();
            hFile = new FileWriter(saveFile);

            doHTML();
            hFile.close();
        }
        catch( Exception e)
        {
            JOptionPane.showMessageDialog(null, "Oh Crap", "File Save Failed"+e.getMessage(), JOptionPane.WARNING_MESSAGE);
        }
        return true;
    }

    protected void setCal(ClassCalendar c)
    {
        myCal = c;
        SchoolCalendar.GetNumberWeeks();
        doUpdateStuff();
    }

    protected void doUpdateStuff()
    {
    }

    private void output(String s) // throws IOException
    {
        try
        {
        if( hFile != null )
        {
            hFile.write(s+"\n");
        }
        else
        {
            System.out.println(s);
        }
    }
    catch( IOException e)
    {
    }
    }

    private void startTable()
    {
        output("<table border=1>");
    }

    private void startRow()
    {
        output("<tr>");
    }

    private void makeCell(String s)
    {
        output("<td>"+s+"</td>");
    }

    private void endRow()
    {
        output("</tr>");
    }

    private void endTable()
    {
        output("</table>");
    }

    protected void showIt(LocalDate d)
    {
        doHTML();
    }

    public void doHTML()
    {
        LocalDate cal1 = myCal.getSchoolCalendar().getFirstDayOfSchool().getLocalDate();
        cal1 = cal1.minusDays(cal1.dayOfWeek().get());
        System.out.println("First day is: "+cal1);
        LocalDate theEnd = myCal.getSchoolCalendar().getLastDayOfSchool().getLocalDate();
        int dayNum = 0;

        System.out.println("Starting cal on: "+cal1.toString("MMM dd"));
        Unit u = myCal.getUnitForDate(cal1);
        MetaLeaf m = myCal.getLeafForDate(cal1);
        System.out.print('\u000C');
        DebugFacility.setLevel(DebugFacility.NO_DEBUG);

        startTable();
        startRow();

        while( cal1.compareTo(theEnd) < 0 )
        {
            myCal.getSchoolDayForDate(cal1);

            if( SchoolCalendar.IsWeekend(cal1) )
            {
                makeCell(makeCenteredCell("Weekend!!", "style=\"color: brown;\""));
            }
            else if( SchoolCalendar.IsHoliday(cal1) )
            {
                makeCell(makeCenteredCell("Holiday!!", "style=\"color: red;\""));
            }
            else if( SchoolCalendar.IsValidDate(cal1) )
            {
                makeCell(makeCenteredCell("Not School Day", "style=\"color: grey;\""));
            }
            else
            {
                u = myCal.getUnitForDate(cal1);
                m = myCal.getLeafForDate(cal1);
                if( m != null )
                {
                    makeCell(makeLessonCell("U"+u.getNumber()+ " " +  cal1.toString(), u.getName()+"<br>"+m.getName(), m.getDescription()));
                }
                else
                {
                    makeCell(makeCenteredCell("No Lesson"));
                }
            }
            if( dayNum % 7 == 6 )
            {
                endRow();
                startRow();
            }
            dayNum++;
            cal1 = cal1.plusDays(1);//does something
        }
        endRow();
        endTable();
    }

    protected String makeCenteredCell(String x)
    {
        String lc;

        lc = "<div align=center>" + x + "</div><br>";

        return lc;
    }

    protected String makeCenteredCell(String x, String y)
    {
        String lc;

        lc = "<div align=center " + y + ">" + x + "</div><br>";

        return lc;
    }

    protected String makeLessonCell(String n, String d, String u, String x)
    {
        String lc;
        lc = "<div align=left>" + n + "</div>";
        lc += "<div align=right>" + d + "</div><br>";
        lc += "<div align=center>" + u + "</div><br>";
        lc += "<div align=left>" + x + "</div><br>";

        return lc;
    }

    protected String makeLessonCell(String d, String u, String x)
    {
        String lc;
        lc = "<div align=justify>" + d + "</div><br>";
        lc += "<div align=center>" + u + "</div><br>";
        lc += "<div align=left>" + x + "</div><br>";

        return lc;
    }
}
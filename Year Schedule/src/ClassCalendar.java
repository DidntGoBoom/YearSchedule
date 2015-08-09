import java.io.*;
import java.util.*;
import javax.swing.tree.*;
import org.joda.time.*;

/**
 * ClassCalendar keeps track of the calendar for a single class
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ClassCalendar extends MetaNode
{
    ArrayList<Unit> units = new ArrayList<Unit>();
    ArrayList<SchoolDay> sDays = new ArrayList<SchoolDay>();
    private SchoolCalendar theCal = SchoolCalendar.GetCalendar();
    private String cName;
    static final ClassCalendar NoCCal = new ClassCalendar();
    String cComment="";
    File inFile;

    static void TestMe() throws FileNotFoundException
    {
        ClassCalendar ecsCal = new ClassCalendar("ecs_curr.txt");
        LocalDate d=new LocalDate();
        DebugFacility.sendMessage(1, "School Day for "+d+" "+ecsCal.getSchoolDayForDate(d));
        d = new LocalDate(2013, 10, 05);
        DebugFacility.sendMessage(1, "School Day for "+d+" "+ecsCal.getSchoolDayForDate(d));
    }

    public ClassCalendar() 
    {
        // create new Class Calendar
    }

    public ClassCalendar(File iF) throws FileNotFoundException
    {
        makeCurriculum(iF);
    }

    public ClassCalendar(String inFileName) throws FileNotFoundException
    {
        // initialise instance variables
        File in = new File(inFileName);
        makeCurriculum(in);
        makeCalendar();
    }

    public File getCalendarFile() { return inFile; }

    public SchoolCalendar getSchoolCalendar() { return theCal; }

    public String toString() { return cName; }

    public String getName() { return cName; }

    public String getCourseName() { return toString(); }

    public int getNumUnits() { return units.size(); }

    public void setCourseName(String name) { cName = name; }

    private void makeCurriculum(File iFile) throws FileNotFoundException
    {
        int nErrors=0;
        ArrayList<String> errors = new ArrayList<String>();
        inFile = iFile;
        Scanner in = new Scanner(inFile);
        String comment="";
        String word;
        Unit cUnit=null;

        while( in.hasNext() )
        {
            String[] result;
            word = in.nextLine();
            char type = word.charAt(0);
            result = word.split(";");

            switch( type )
            {
                case '#':
                    comment += "#;" + result[1] + '\n';
                    break;
                case 'C':
                    cName = result[1];
                    if( comment.length() > 0 )
                    {
                        cComment = comment;
                        comment = "";
                    }
                    DebugFacility.sendMessage(1, "Class name is: "+cName);
                    break;
                case 'U':
                    Unit un = new Unit( result[0], (result.length > 1 ? result[1] : ""), result.length > 2 ? result[2] : "", this);
                    addUnit(un);
                    if( comment.length() > 0 )
                    {
                        un.setComment(comment);
                        comment = "";
                    }
                    cUnit = un;
                    break;
                case 'P':
                    Project p = new Project( result[0], (result.length > 1 ? result[1] : ""), result.length > 2 ? result[2] : "", this);
                    addUnit(p);
                    if( comment.length() > 0 )
                    {
                        p.setComment(comment);
                        comment = "";
                    }
                    cUnit = p;
                    break;
                case 'S':
                    CaseStudy cs = new CaseStudy( result[0], (result.length > 1 ? result[1] : ""), result.length > 2 ? result[2] : "", this);
                    addUnit(cs);
                    if( comment.length() > 0 )
                    {
                        cs.setComment(comment);
                        comment = "";
                    }
                    cUnit = cs;
                    break;
                case 'L':
                    Lesson ls = new Lesson( result[0], Integer.parseInt(result[1]), result.length > 2 ? result[2] : "", cUnit);
                    cUnit.addLesson( ls);
                    if( comment.length() > 0 )
                    {
                        ls.setComment(comment);
                        comment = "";
                    }
                    DebugFacility.sendMessage(1, "Lesson is: "+ls);
                    break;
                case 'X':
                {
                    Exam ex = new Exam( result[0], result.length > 2 ? result[2] : "", cUnit);
                    cUnit.addExam( ex);
                    if( comment.length() > 0 )
                    {
                        ex.setComment(comment);
                        comment = "";
                    }
                    DebugFacility.sendMessage(1, "Exam is: "+ex);
                    break;
                }
                default:
                nErrors++;
                    errors.add(word);
                    break;
            } // end switch
        } // end while
        DebugFacility.sendMessage(0, "\n\n*****\n");
        if( nErrors > 0 )
        {
            DebugFacility.sendMessage(0, "\n\n*****\n");
            for( String s : errors )
            {
                DebugFacility.sendMessage(0, s);
            }
            DebugFacility.sendMessage(0, "\n\n*****\n");
        }
        in.close();
    }

    public void addUnit(Unit u)
    {
        units.add( u);
    }

    private void makeCalendar()
    {
        LocalDate alpha=theCal.getFirstDayOfSchool().getLocalDate();
        DebugFacility.sendMessage(1, "Starting date is "+alpha);

        for( Unit u : units )
        {
            u.setStartDate(alpha);
            DebugFacility.sendMessage(1, "Unit starting date is "+alpha+" "+u.getStartDate());
            DebugFacility.sendMessage(1, "Unit: "+u);
            for( MetaLeaf m : u.getLessons() )
            {
                if( m instanceof Lesson )
                {
                    Lesson l = (Lesson)m;
                    l.setStartDate(alpha);
                    DebugFacility.sendMessage(1, "  Lesson "+l+" "+l.getDuration());
                    DebugFacility.sendMessage(1, "Lesson starting date is "+alpha+" "+l.getStartDate());
                    alpha = theCal.getNthDay(alpha, l.getDuration()-1);
                    l.setEndDate(alpha);
                    DebugFacility.sendMessage(1, "Lesson ending date is "+alpha+" "+l.getEndDate());
                    alpha = theCal.getNextDay(alpha);
                    int i=1;
                    for(  LocalDate d=l.getStartDate(); i<=l.getDuration(); i++ )
                    {
                        if( getSchoolDayForDate(d) == null )
                        {
                            sDays.add(new SchoolDay(d, l, u));
                        }
                        d = theCal.getNextDay(d);
                        if( d == null ) break;
                    }
                }
                else if( m instanceof Exam )
                {
                    Exam x = (Exam)m;
                    x.setStartDate(alpha);
                    DebugFacility.sendMessage(1, "  Exam "+x+" "+x.getDuration());
                    DebugFacility.sendMessage(1, "Exam starting date is "+alpha+" "+x.getStartDate());
                    alpha = theCal.getNthDay(alpha, x.getDuration()-1);
                    x.setEndDate(alpha);
                    DebugFacility.sendMessage(1, "Lesson ending date is "+alpha+" "+x.getEndDate());
                    alpha = theCal.getNextDay(alpha);
                    int i=1;
                    for(  LocalDate d=x.getStartDate(); i<=x.getDuration(); i++ )
                    {
                        if( getSchoolDayForDate(d) == null )
                        {
                            sDays.add(new SchoolDay(d, x, u));
                        }
                        d = theCal.getNextDay(d);
                        if( d == null ) break;
                    }
                }
            }
            u.setEndDate(theCal.getPreviousDay(alpha)); //theCal.getNthDay(lastDate, uDur)); // XXX alpha - 1 
            //alpha = theCal.getNextDay(alpha);
        }
        alpha=theCal.getFirstDayOfSchool().getLocalDate();
        DebugFacility.sendMessage(1, "Starting date at the end is "+alpha);
    }

    void dumpCalendar()
    {
        for( Unit u : units )
        {
            DebugFacility.sendMessage(1, "Unit is "+u);
            for( MetaLeaf m : u.getLessons() )
            {
                Lesson l = (Lesson)m;
                DebugFacility.sendMessage(1, "Lesson is "+l.toString(true));
            }
        }
    }

    public Unit getUnitForDate(LocalDate d)
    {
        for( Unit u : units )
        {
            if( !d.isAfter(u.getEndDate()) )
            {
                return u;
            }
        }

        return null;
    }

    public SchoolDay getSchoolDayForDate(LocalDate d)
    {
        for( SchoolDay sd : sDays )
        {
            if( sd.dateEquals(d) )
            {
                return sd;
            }
        }
        return null;
    }

    public MetaLeaf getLeafForDate(LocalDate d)
    {
        Unit u=getUnitForDate(d);
        if( u == null )
        {
            DebugFacility.sendMessage(1, "No unit for "+d);
            return null;
        }
        DebugFacility.sendMessage(1, "Unit is "+u);

        for( MetaLeaf m : u.getLessons() )
        {
            // Lesson l = (Lesson)m;
            DebugFacility.sendMessage(1, "Lesson starting date is "+m.getStartDate());
            DebugFacility.sendMessage(1, "The date is "+d);
            DebugFacility.sendMessage(1, "Lesson ending date is "+m.getEndDate());
            if( !d.isAfter(m.getEndDate()) )
            {
                return m;
            }
        }
        return null;
    }

   // @Override
    public String saveYourself()
    {
        String me = cComment + "C;"+cName+"\n";
        for( Unit u : units )
        {
            me += u.saveYourself();
        }
        return me;
    }

    /**
     * 
     */

    /**
     * TreeNode Stuff
     */
    public Enumeration<?> children()
    {
        return new StupidEnumThing(units);
    }

    public boolean getAllowsChildren()
    {
        return true;
    }

    public TreeNode getChildAt( int childIndex)
    {
        if( childIndex > units.size() )
        {
            return null;
        }
        return units.get(childIndex);
    }

    public int getChildCount()
    {
        return units.size();
    }

    public int getIndex(TreeNode node)
    {
        return units.indexOf(node);
    }

    public TreeNode getParent()
    {
        return null;
    }

    public boolean isLeaf()
    {
        return false;
    }
}
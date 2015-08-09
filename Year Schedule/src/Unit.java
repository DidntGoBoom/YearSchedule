import java.util.*;
import javax.swing.tree.*;
import org.joda.time.*;

/**
 * Write a description of class Unit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Unit extends MetaNode
{
    static final String GENERIC_NAME="Unit";

    // instance variables - replace the example below with your own
    protected int uNum;
    protected String uShortName="U";
    protected String uGenericName="Unit";
    protected String uAbbr;
    protected String uDesc; 
    protected String uComment="";
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int len;
    ArrayList<MetaLeaf> lessons = new ArrayList<MetaLeaf>();
    protected ClassCalendar uCal;
    static final Unit NoUnit = new Unit();

    /**
     * Constructor for objects of class Unit
     */
    public Unit(int n, String a, String d, ClassCalendar cc)
    {
        super(false);

        uNum = n;
        uAbbr = a;
        uDesc = d;
        uCal = cc;
    }

    public Unit( String n, String a, String d, ClassCalendar cc)
    {
        uNum = Integer.parseInt(n.substring(1));
        uAbbr = a;
        uDesc = d;
        uCal = cc;
    }

    public Unit()
    {
        uNum = 0;
        uAbbr = "No Unit";
        uDesc = "No Unit";
        uCal = new ClassCalendar();
        // setStartDate(new LocalDate());
        // setEndDate(new LocalDate());
    }

    public boolean equals(Unit u2)
    {
        if( u2 == null ) return false;
        if( u2 == this ) return true;
        if( uNum != u2.getNumber() ) return false;
        if( uAbbr != u2.getName() ) return false;
        return true;
        /*
         * 
        (uDesc.equals(u2.getDescr())) &&
        startDate.equals(u2.getStartDate()) &&
        endDate.equals(u2.getEndDate());                
         */
    }

    public void setStartDate( LocalDate d) { startDate = d;}
    public void setEndDate( LocalDate d) { endDate = d;}
    public void setName( String n) { uAbbr = n; }
    public void setDesc( String n) { uDesc = n; }
    public void setComment( String s) { uComment = s; }

    //public void add( Lesson l) { lessons.add(l); } // deprecated - use addLesson
    public void setLength(int l) { len = l; }

    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getNumber() { return uNum; }
    public String getShortNumberName() { return uShortName + uNum; }
    public String getNumberName() { return GENERIC_NAME + uNum; }
    public String getGenericName() { return GENERIC_NAME; }
    public String getName() { return uAbbr; }
    public String getComment() { return uComment; }
    public ArrayList<MetaLeaf> getLessons() { return lessons; }
    public String getDescr() { return uDesc; }
    public ClassCalendar getCCal() { return uCal; }
    public int getNumLessons() { return lessons.size(); }
    public String getShortName() { return uShortName+uNum; }

    public String toString() { return getGenericName() + " "+uNum+": "+uAbbr; }

    public void addLesson( Lesson l) 
    { 
        lessons.add(l);
        l.setSequence(getNumLessons()+1);
    }

    public void addExam( Exam t) 
    { 
        lessons.add(t);
        t.setSequence(getNumLessons()+1);
    }

    @Override
    public String saveYourself()
    {
        String me = uComment + "U"+uNum+";"+uAbbr+";"+uDesc+"\n";
        for( MetaLeaf m : lessons )
        {
            me += m.saveYourself();
        }
        return me;
    }

    /**
     * TreeNode Stuff
     */
    public Enumeration<?> children()
    {
        return new StupidEnumThing(lessons);
    }

    public TreeNode getChildAt( int childIndex)
    {
        if( childIndex > lessons.size() )
        {
            return null;
        }
        return (TreeNode)lessons.get(childIndex); // FIX THIS
    }

    public int getChildCount()
    {
        return lessons.size();
    }

    public int getIndex(TreeNode node)
    {
        return lessons.indexOf(node);
    }

    public TreeNode getParent()
    {
        return null;
    }
}
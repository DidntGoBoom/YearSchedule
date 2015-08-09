import org.joda.time.*;

/**
 * Write a description of class SchoolDay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SchoolDay
{
    // instance variables - replace the example below with your own
    private LocalDate theDate;
    private final MetaLeaf theLeaf;
    private Unit theUnit;
    private int yearDay;

    /**
     * Constructor for objects of class SchoolDay
     */
    public SchoolDay(LocalDate d, MetaLeaf m, Unit u)
    {
        setDate(d);
        theLeaf = m;
        theUnit = u;
        // initialise instance variables
    }
    
    public SchoolDay(Lesson l)
    {
        this( null, l, null);
        // initialise instance variables
    }
    
    public SchoolDay(LocalDate d)
    {
        this( d, null, null);
        // initialise instance variables
    }
    
    public SchoolDay()
    {
        this(new LocalDate(1970, 1, 1), null, null);
    }
    
    public void setDate(LocalDate d) { theDate = d; }
    public boolean dateEquals(LocalDate d) { return theDate.equals(d); }
    public LocalDate getDate() { return theDate; }
    public MetaLeaf getLeaf() { return theLeaf; }
    public Unit getUnit() { return theUnit; }
    public int getYearDay() { return yearDay; }
}
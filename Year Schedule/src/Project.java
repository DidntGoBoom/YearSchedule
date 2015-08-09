

/**
 * case studies are just a special kind of Unit.  May not need a separate class, but ...
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Project extends Unit
{
    // instance variables - replace the example below with your own
    
    static final String SHORT_NAME="P";
    static final String GENERIC_NAME="Project";
    public String getGenericName() { return GENERIC_NAME; }
    
    /**
     * Constructor for objects of class Project
     */
    public Project(int n, String a, String d, ClassCalendar cc)
    {
        super(n, a, d, cc);
        uShortName = SHORT_NAME;
    }

    public Project( String n, String a, String d, ClassCalendar cc)
    {
        super( Integer.parseInt(n.substring(1)), a, d, cc);
        uShortName = SHORT_NAME;
    }

    public Project()
    {
        super();
        uAbbr = "No Project";
        uDesc = "No Project";
        uShortName = SHORT_NAME;
    }

    public boolean equals(Project u2)
    {
        if( u2 == null ) return false;
        if( u2 == this ) return true;
        if( uNum != u2.getNumber() ) return false;
        if( uAbbr != u2.getName() ) return false;
        return true;
    }
}
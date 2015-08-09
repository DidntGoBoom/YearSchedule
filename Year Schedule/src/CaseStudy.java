

/**
 * case studies are just a special kind of Unit.  May not need a separate class, but ...
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaseStudy extends Unit
{
    static final String GENERIC_NAME="Case Study";
    public String getGenericName() { return GENERIC_NAME; }
    
    /**
     * Constructor for objects of class CaseStudy
     */
    public CaseStudy(int n, String a, String d, ClassCalendar cc)
    {
        super(n, a, d, cc);
        uShortName = "CS";
    }

    public CaseStudy( String n, String a, String d, ClassCalendar cc)
    {
        super( Integer.parseInt(n.substring(1)), a, d, cc);
        uShortName = "CS";
    }

    public CaseStudy()
    {
        super();
        uAbbr = "No Case Study";
        uDesc = "No Case Study";
        uShortName = "CS";
    }
    
    public boolean equals(CaseStudy u2)
    {
        if( u2 == null ) return false;
        if( u2 == this ) return true;
        if( uNum != u2.getNumber() ) return false;
        if( uAbbr != u2.getName() ) return false;
        return true;
    }
}
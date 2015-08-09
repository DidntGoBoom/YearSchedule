public class Exam extends MetaLeaf
{
    static final Exam NoExam = new Exam();
    public void setUnit(Unit u) { theParent = u; }
    public Unit getUnit() { return (Unit)getParent(); }

    public Exam()
    {
        this("No Exam", Unit.NoUnit);
    }
    
    public Exam(String n, Unit u)
    {
        this(n, "No Desc", u);
    }
    
    public Exam(String n, String ds, Unit u)
    {
        setName(n);
        setDescription(ds);
        setUnit(u);
        setDuration(1);
    }

    public String toString() { return "Exam "+mlName; }

    @Override
    public String saveYourself()
    {
        String me = mlComment + "X" + mlSequence + ";"+mlDuration+";" + mlName+";"+mlDesc+"\n";
        return me;
    }
}
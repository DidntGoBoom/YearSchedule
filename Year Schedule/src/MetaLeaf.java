import java.util.*;
import javax.swing.tree.*;
import org.joda.time.*;

public abstract class MetaLeaf extends MetaNode
{
    protected int mlSequence;
    protected String mlName;
    protected int mlDuration;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected String mlDesc;
    protected String mlComment="";
    protected TreeNode theParent;
    
    public void setSequence(int n) { mlSequence = n; }
    public void setName(String n) { mlName = n; }
    public void setComment( String s) { mlComment = s; }
    public void setDuration(int d) { mlDuration = d; }
    public void setDescription(String d) { mlDesc = d; }
    public void setStartDate( LocalDate d) { startDate = d;}
    public void setEndDate( LocalDate d) { endDate = d;}

    public String getName() { return mlName; }
    public String getComment() { return mlComment; }
    public int getDuration() { return mlDuration; }
    public String getDescription() { return mlDesc; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public TreeNode getParent() { return theParent; }
    
    MetaLeaf()
    {
        this(null);
    }

    MetaLeaf(TreeNode p)
    {
        amILeafy = true;
        theParent = p;
    }

    public Enumeration<?> children()
    {
        return null;
    }

    public boolean getAllowsChildren()
    {
        return false;
    }

    public TreeNode getChildAt( int childIndex)
    {
        return null;
    }

    public int getChildCount()
    {
        return 0;
    }

    public int getIndex(TreeNode node)
    {
        return -1;
    }

    public boolean isLeaf()
    {
        return true;
    }
}
import javax.swing.tree.*;

public abstract class MetaNode implements TreeNode, SaveableThing
{
    boolean amILeafy;  // if true, the node is a leaf

    MetaNode()
    {
        amILeafy = false;
    }

    MetaNode(boolean l)
    {
        amILeafy = l;
    }

    /**
     * TreeNode Stuff
     */
    public boolean getAllowsChildren()
    {
        return !amILeafy;
    }

    /**
     * Override if not leafy
     */
    public int getChildCount()
    {
        return -1;
    }

    public boolean isLeaf()
    {
        return amILeafy;
    }
}

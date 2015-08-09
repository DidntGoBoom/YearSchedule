import javax.swing.tree.*;


public abstract class MetaTreeThing extends DefaultTreeModel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4033384471763995888L;

	public MetaTreeThing(TreeNode root)
    {
        super(root);
    }
}
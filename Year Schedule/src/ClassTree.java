import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class ClassTree extends JTree
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1875709804812532085L;
	static Dimension pSize=null;
    
    {
        JTree.DynamicUtilTreeNode xyzzy;
        String yyy="0";
        String[] zzz={"1","2","3","4"};
        
        xyzzy=new JTree.DynamicUtilTreeNode(yyy, zzz);
        JTree jt = new JTree(xyzzy);
        pSize = jt.getPreferredSize();
        pSize = new Dimension( (int)pSize.getWidth(), (int)(pSize.getHeight()*1.5));
        System.out.println("Pref Size is: "+pSize);
    }
    
    public ClassTree( ClassTreeThing ctt)
    {
        super(ctt);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
//    public Dimension getPreferredSize() { return pSize; }
//    public Dimension getMinimumSize() { return getPreferredSize(); }
}
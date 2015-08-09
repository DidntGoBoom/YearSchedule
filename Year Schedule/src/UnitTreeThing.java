public class UnitTreeThing extends MetaTreeThing
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7396183317349489126L;
	private Unit theRoot;
    public Unit getUnit() { return theRoot; }
    
    public UnitTreeThing(Unit root)
    {
        super(root);
        theRoot = root;
    }
}
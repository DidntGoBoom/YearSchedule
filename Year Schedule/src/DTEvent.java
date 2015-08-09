import java.awt.*;

public class DTEvent extends AWTEvent
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5453051823694261847L;
	static final int OK_EVENT = RESERVED_ID_MAX + 0xf0000 + 1;
    static final int CANCEL_EVENT = RESERVED_ID_MAX + 0xf0000 + 2;
    
    public DTEvent(Object o, int id)
    {
        super( o, id);
    }
}

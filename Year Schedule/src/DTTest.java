import javax.swing.*;
import java.awt.event.*;

public class DTTest extends JPanel implements DTEventListener, ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7981007449027291426L;

	public static void main( String args[])
    {
        DTTest dtt = new DTTest();
        dtt.add(new JLabel("Huh??"));
        
        DialogThing ccp=new DialogThing(dtt);
        ccp.addDTEventListener(dtt);

        if( args.length > 0 )
        {
            ccp.addMenuBar(new MenuThing(dtt));
        }
        
        ccp.showIt();
        System.out.println("The New Calendar So there!!");
    }

    public void dtEventPerformed(DTEvent e)
    {
        switch(e.getID() )
        {
        case DTEvent.OK_EVENT:
            System.out.println("It Performed OK");
            break;
        case DTEvent.CANCEL_EVENT:
            System.out.println("It Performed CANCEL");
            break;
        }
    }

    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("Action stuff");
    } 

}
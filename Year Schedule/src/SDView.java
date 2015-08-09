import java.awt.event.*;
import org.joda.time.*;

/**
 * Write a description of class SDView here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SDView extends DateLabel implements MouseListener
{
    // instance variables - replace the example below with your own

    /**
	 * 
	 */
	private static final long serialVersionUID = -5521158583370922703L;
	SchoolDay sDay;
    /**
     * Constructor for objects of class SDView
     */
    public SDView(String s)
    {
        this( new LocalDate(), s);
    }
    
    public SDView(SchoolDay d)
    {
        this(d.getDate(), "hello");
        sDay = d;
    }
    
    public SDView(LocalDate d, String s)
    {
        this(d, s, null);
    }
    
    public SDView(LocalDate d, String s, String cs)
    {
        super(d, s, cs);
        addMouseListener(this);
    }
    
    public void setSchoolDay(SchoolDay d)
    {
        sDay = d;
        if( sDay == null )
        {
        }
        setDate(d.getDate());
    }
    
    public void setSchoolDay(LocalDate d)
    {
        sDay = null;
        setDate(d);
    }

    public void mouseClicked(MouseEvent e)
    {
        if( sDay != null )
        {
            DebugFacility.sendMessage(1, "Mouse clicked: "+this);
            if( e.isPopupTrigger() )
            {
                DebugFacility.sendMessage(1, "Popup Triggered");
            }
            if (e.getClickCount() == 2 && !e.isConsumed()) 
            {
                 //handle double click event.
                 e.consume();
                 DebugFacility.sendMessage(1, "Double Clicked");
                 LessonDetailView lDV = new LessonDetailView(sDay);
                 lDV.doShow();
            }

            switch( e.getButton() )
            {
            case MouseEvent.BUTTON1:
                DebugFacility.sendMessage(1, "Button 1");
                break;
            case MouseEvent.BUTTON2:
                DebugFacility.sendMessage(1, "Button 2");
                break;
            case MouseEvent.BUTTON3:
                DebugFacility.sendMessage(1, "Button 3");
                break;
            }
        }
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }
}
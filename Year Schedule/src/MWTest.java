import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MWTest extends JPanel implements Scrollable, 
        AdjustmentListener, MouseWheelListener //ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1871313661877016037L;
	JLabel l;
    JFrame f;
    JPanel p;
    JScrollBar jsb;
    
    public MWTest()
    {
        f = new JFrame("MWTest");
        p = new JPanel();
        
        jsb = new JScrollBar();
        jsb.addAdjustmentListener(this);
        // mEv=new MouseWheelEvent(frame, 0, 0, 0, 0, 0, 0, false, 0, 0, 0);
        f.addMouseWheelListener(this);
        f.add(p, BorderLayout.CENTER);
        f.add(jsb, BorderLayout.EAST);

        l = new JLabel("b");
        p.add(l);
        
        f.setVisible(true);
        
        SwingUtilities.invokeLater(new Runnable()
            {
                @SuppressWarnings("deprecation")
				@Override
                public void run()
                {
                    f.show(true);
                }
            });
    }
    
    public Dimension getPreferredScrollableViewportSize() 
    {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
    int orientation,
    int direction) 
    {
        return (int)(50);
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
    int orientation,
    int direction) 
    {
        return (int)(getPreferredSize().getHeight());
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public void setMaxUnitIncrement(int pixels) {
        //        maxUnitIncrement = pixels;
    }
    
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        // if( !mEv.equals(e) )
        {
            jsb.getValue();
            e.getWheelRotation();
            String rStr = e.paramString();
            ((Object)e).toString();
            System.out.println(rStr);
            
            //jsb.setValue(jsb.getValue() + e.getWheelRotation());
            System.out.println("Hoo Woo "+jsb.getValue()+" : "+e.getWheelRotation());
        }
        // mEv = e;
    }

    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        System.out.println("Woo Hoo "+e.paramString());
    }
    
}
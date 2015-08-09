import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * 
 */
public class DialogThing extends JDialog implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6912940982314094169L;
	JComponent jComp;
    Dimension dIm;
    static final String OK_Message="OK";
    static final int OK = 0;
    static final int CANCEL = 1;
    JMenuBar jMBar = null;

    DialogThing m_dialog;
    int     m_dialogSelection;
    ArrayList<DTEventListener> listeners=new ArrayList<DTEventListener>();

    /**
     * 
     */
    public DialogThing()
    {
    }
    
    public DialogThing(JComponent jc)
    {
        this(jc, new Dimension(480, 640));
    }

    public DialogThing(JComponent jc, Dimension dim)
    {
        jComp = jc;
        dIm = dim;
    }

    public void addMenuBar(JMenuBar jmb)
    {
        jMBar = jmb;
    }

    public void addDTEventListener(DTEventListener l)
    {
        if( !listeners.contains(l) )
        {
            listeners.add(l);
        }
    }

    public void stateChanged(ChangeEvent e)
    {
        System.out.println("Something happened to "+e);
    }

    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("Action stuff");
    } 

    public static void main( String args[])
    {
        JPanel test = new JPanel();
        test.add(new JLabel("Huh??"));

        DialogThing ccp=new DialogThing(test);

        ccp.showIt();
        System.out.println("The New Calendar So there!!");
    }

    public void sendEvent(DTEvent ae)
    {
        for( DTEventListener l : listeners )
        {
            l.dtEventPerformed(ae);
        }
    }

    private void showMe()
    {
        m_dialog = this;
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    m_dialog.setVisible(false);
                    m_dialog.dispose();
                    m_dialogSelection = OK;
                    System.out.println( "OK Option selected" );
                    DTEvent ae = new DTEvent( m_dialog, DTEvent.OK_EVENT);
                    m_dialog.sendEvent(ae);
                }}
        );
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    m_dialog.setVisible(false);
                    m_dialog.dispose();
                    DTEvent ae = new DTEvent( m_dialog, DTEvent.CANCEL_EVENT);
                    m_dialog.sendEvent(ae);
                }}
        );

        JPanel panel2 = new JPanel();
        panel2.setLayout( new FlowLayout() );

        panel2.add( ok);
        panel2.add( cancel);

        BorderLayout mgr = new BorderLayout();
        mgr.setHgap(10);
        mgr.setVgap(10);
        m_dialog.setLayout(mgr);

        // A little border around the tree so it doesn't look so bad.
        Border b = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        jComp.setBorder(b);
        if( jMBar != null )
        {
            m_dialog.add(jMBar, BorderLayout.NORTH);
        }
        m_dialog.add(jComp, BorderLayout.CENTER);
        m_dialog.add(panel2, BorderLayout.SOUTH);
        m_dialog.setSize((int)dIm.getWidth(), (int)dIm.getHeight());
        m_dialog.setVisible(true);
    }

    // describe each of the attributes declared below
    public void showIt()
    {
        try
        {
            if( SwingUtilities.isEventDispatchThread() )
            {
                if( jComp != null )
                    showMe();
            }
            else
            {
                SwingUtilities.invokeAndWait(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if( jComp != null )
                                showMe();
                        }
                    }
                );
            }
        }
        catch( Exception e )
        {
        }
        System.out.println("Shown it");
    }

    public void valueChanged(TreeSelectionEvent e)
    {
    }
}
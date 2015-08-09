import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class adf here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CCTextView extends JScrollPane 
implements AdjustmentListener, ActionListener//, MouseListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8562792790905654200L;
	JFrame frame;
    private JMenuItem jmf_n;
    private JMenuItem jmf_s;
    private JMenuItem jmf_x;
    
    public CCTextView()
    {
        super( new JTextArea(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        
                        makeWindow();
    }

    private void makeWindow()
    {
        frame = new JFrame("Calendar Text View");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar jmb = new JMenuBar();
        JMenu jmf = new JMenu("File");
        jmb.add(jmf);
        jmf_n = new JMenuItem("New calendar ...");
        jmf.add(jmf_n);
        jmf_n.addActionListener(this);
        jmf_s = new JMenuItem("Save calendar ...");
        jmf.add(jmf_s);
        jmf_s.addActionListener(this);
        jmf.addSeparator();
        jmf_x = new JMenuItem("Exit");
        jmf.add(jmf_x);
        jmf_x.addActionListener(this);

        // + 5 because 2 pad at beginning and 2 pad at end, 
        // plus one one for fencepost
        frame.add(this, BorderLayout.CENTER);
        //frame.add(jsb, BorderLayout.EAST);

        frame.setJMenuBar(jmb);
        frame.pack();
    }

    public void showWindow(boolean showIt)
    {
        //5. Show it.
        frame.setVisible(showIt);
    }

    public void display()
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    showIt();
                }
            });
    }

    protected void showIt()
    {

    }
    
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        //System.out.println("Woo Hoo "+e.paramString());
        //LocalDate fd = SchoolCalendar.GetCalendar().getFirstDayOfSchool().getLocalDate();
        //LocalDate cal1 = fd.plusDays(DGColumns*e.getValue());
        //display(cal1);
    }

    public void actionPerformed(ActionEvent e) 
    {
        Object o = e.getSource();

        if( o.equals(jmf_s) )
        {
        }
        else if( o.equals(jmf_n) )
        {
        }
        else if( o.equals(jmf_x) )
        {
            System.out.println("Exit Thingie");
            System.exit(0);
        }
    }

}

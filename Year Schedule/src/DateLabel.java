import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import org.joda.time.*;

/**
 * Write a description of class DateLabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DateLabel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -755851798083325874L;
	// instance variables - replace the example below with your own
    JLabel j1;
    JLabel j2;
    JLabel j3=null;
    LocalDate theDate=new LocalDate();
    static Color BROWN=new Color(0xa52a2a);
    static Color BEIGE=new Color(0xd55a5a);
    static Color BISQUE=new Color(0xFFE4C4);
    static Color SLATE_GREY=new Color(0x708090);

    public static void testme()
    {
        JFrame frame = new JFrame("FrameDemo");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DateLabel mainpain = new DateLabel("b");
        mainpain.setBackground(BISQUE);

        frame.add(mainpain, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);
    }
    
    public static void testme2()
    {
        JFrame frame = new JFrame("FrameDemo");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DateLabel mainpain = new DateLabel("xyzzy", "7x");
        mainpain.setBackground(BISQUE);
        mainpain.setClassLabel("X1", "x2");

        frame.add(mainpain, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);
    }
    
    public static void testme3()
    {
        JFrame frame = new JFrame("FrameDemo");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DateLabel mainpain = new DateLabel("xyzzy");
        mainpain.setBackground(BISQUE);

        frame.add(mainpain, BorderLayout.CENTER);

        frame.pack();

        frame.setVisible(true);
        mainpain.setClassLabel("7x");
    }
    
    /**
     * Constructor for objects of class DateLabel
     */
    public DateLabel(String s)
    {
        this(new LocalDate(), s);
    }
    
    public DateLabel(String s, String cs)
    {
        this(new LocalDate(), s, cs);
    }
    
    public DateLabel( LocalDate d, String s)
    {
        this(new LocalDate(), s, null);
    }
    
    public DateLabel( LocalDate d, String s, String cs)
    {
        // initialise instance variables
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        j1 = new JLabel(d.toString("MMM dd"), JLabel.CENTER);
        j1.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        j2 = new JLabel(s, JLabel.CENTER);
        j2.setBorder(BorderFactory.createLineBorder(BROWN));
        j2.setBackground(SLATE_GREY);
        
        setLayout(new BorderLayout());
        j3 = new JLabel(cs);
        j3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        j3.setBackground(BEIGE);
        jp.add(j3, BorderLayout.LINE_START);

        setClassLabel(cs);
        jp.add(j1, BorderLayout.LINE_END);
        add(jp, BorderLayout.PAGE_START);
        add(j2, BorderLayout.CENTER);
    }
    
    public void setDate(LocalDate d) { theDate = d; j1.setText(d.toString("MMM dd")); }
    public void setLabelString(String s) { j2.setText(s); }
    
    public void setClassLabel(String s)
    {
        if( s == null || s.length() == 0 )
        {
            j3.setVisible(false);
            j3.setToolTipText("");
        }
        else
        {
            j3.setText(s);
            j3.setVisible(true);
            j3.setToolTipText("");
        }
    }
    
    public void setClassLabel(String s, String s1)
    {
        if( s == null || s.length() == 0 )
        {
            j3.setVisible(false);
            j3.setToolTipText("");
        }
        else
        {
            j3.setText(s);
            j3.setVisible(true);
            j3.setToolTipText(s1);
        }
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(100, 75);
    }
}
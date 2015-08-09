import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import org.joda.time.*;

/**
 * CCView is the view portion of the Class Calendar 
 * suite.  It presents a calendar grid, populates it and 
 * controls scrolling and other UI functions.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CCGUIView extends JPanel implements Scrollable, 
        AdjustmentListener, ActionListener, MouseWheelListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7706327826451400665L;
	// describe each of the attributes declared below
    private static Border todayBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static Border notTodayBorder = BorderFactory.createEmptyBorder(0,10,10,10); 

    private ClassCalendar myCal;
    private int DGRows=5;
    private int DGColumns=7;
    private SDView[][] dayGrid = new SDView[DGRows][DGColumns];
    private JFrame frame;
    private JPanel panel;
    private JScrollBar jsb;
    private int nWeeks;
    
    
    // private MouseWheelEvent mEv;

    private JMenuItem jmf_n;
    private JMenuItem jmf_e;
    private JMenuItem jmf_s;
    private JMenuItem jmf_x;
    /**
     * Constructor for objects of class CCView
     *
     * Describe how the constructor is used.  Add @param fields if required
     */
    public CCGUIView(ClassCalendar c)
    {
        setCal(c);
    }

    protected void setCal(ClassCalendar c)
    {
        myCal = c;
        nWeeks = SchoolCalendar.GetNumberWeeks();
        doUpdateStuff();
        makeWindow();
    }

    protected void doUpdateStuff()
    {
    }
    
    private void makeWindow()
    {
        frame = new JFrame("Calendar");
        panel = new JPanel();

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int week=SchoolCalendar.GetWeekForDate(new LocalDate());

        JMenuBar jmb = new JMenuBar();
        JMenu jmf = new JMenu("File");
        jmb.add(jmf);
        jmf_n = new JMenuItem("New calendar ...");
        jmf.add(jmf_n);
        jmf_n.addActionListener(this);
        jmf_e = new JMenuItem("Edit calendar ...");
        jmf.add(jmf_e);
        jmf_e.addActionListener(this);
        jmf_s = new JMenuItem("Save calendar ...");
        jmf.add(jmf_s);
        jmf_s.addActionListener(this);
        jmf.addSeparator();
        jmf_x = new JMenuItem("Exit");
        jmf.add(jmf_x);
        jmf_x.addActionListener(this);

        // + 5 because 2 pad at beginning and 2 pad at end, 
        // plus one one for fencepost
        jsb = new JScrollBar(JScrollBar.VERTICAL, 
                week, DGRows, 0, nWeeks + 5);
        jsb.addAdjustmentListener(this);
        // mEv=new MouseWheelEvent(frame, 0, 0, 0, 0, 0, 0, false, 0, 0, 0);
        panel.addMouseWheelListener(this);
        jsb.addMouseWheelListener(this);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(jsb, BorderLayout.EAST);

        frame.setJMenuBar(jmb);
        panel.setLayout(new GridLayout(DGRows, DGColumns));

        //For each component to be added to this container:
        //...Create the component...

        for( int i=0; i<DGRows; i++ )
        {
            for( int j=0; j<DGColumns; j++ )
            {
                dayGrid[i][j] = new SDView("Middle");
                panel.add(dayGrid[i][j]);
            }
        }

        //4. Size the frame.
        frame.pack();
    }

    public void showWindow(boolean showIt)
    {
        //5. Show it.
        frame.setVisible(showIt);
    }

    public void display()
    {
        display(new LocalDate());
    }

    public void display(final LocalDate d)
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    showIt(d);
                }
            });
    }

    protected void showIt(LocalDate d)
    {

        LocalDate cal1 = d.minusDays(14+SchoolCalendar.GetDOWNumber(d));
        int dayNum = 0;

        frame.setTitle(myCal.getCourseName()+" Calendar");

        System.out.println("Starting cal on: "+cal1.toString("MMM dd"));
        Unit u = myCal.getUnitForDate(cal1);
        MetaLeaf m = myCal.getLeafForDate(cal1);
        System.out.println("Unit is "+u);

        while( dayNum < DGRows*DGColumns )
        {
            SDView dL = dayGrid[dayNum/7][dayNum%7];
            SchoolDay sD = myCal.getSchoolDayForDate(cal1);
            DebugFacility.sendMessage(5, "Setting Date Thing "+dayNum%7+" "+dayNum/7+" "+cal1.toString("MMM dd"));
            if( cal1.equals(new LocalDate()) )
            {
                dL.setBorder(todayBorder); 
            }
            else
            {
                dL.setBorder(notTodayBorder); 
            }

            if( SchoolCalendar.IsWeekend(cal1) )
            {
                int nWeek = SchoolCalendar.GetWeekForDate(cal1);
                dL.setLabelString("<HTML>Weekend!!<BR><BR>Week "+nWeek+"</HTML>");
                dL.setBackground(SDView.BISQUE);
                dL.setDate(cal1);
            }
            else if( SchoolCalendar.IsHoliday(cal1) )
            {
                dL.setLabelString("Holiday!!");
                dL.setBackground(SDView.SLATE_GREY);
                dL.setDate(cal1);
            }
            else if( SchoolCalendar.IsValidDate(cal1) )
            {
                dL.setLabelString("Not School Day");
                dL.setBackground(SDView.SLATE_GREY);
                dL.setDate(cal1);
            }
            else
            {
                u = myCal.getUnitForDate(cal1);
                m = myCal.getLeafForDate(cal1);
                if( sD == null )
                {
                    dL.setSchoolDay(new SchoolDay(cal1));
                    dL.setBackground(new Color(0xdad0c6)); //(SDView.faf0e6);
                }
                else
                {
                    dL.setSchoolDay(sD);
                    dL.setBackground(new Color(0xfaf0e6)); //(SDView.faf0e6);
                }
                int dn = SchoolCalendar.GetCalendar().dayNumber(cal1);
                if( m != null )
                {
                    dL.setLabelString("<HTML>"+dn+": "+m.getName()+"<BR>"+m.getDescription()+"</HTML>");
                    dL.setClassLabel( u.getShortName(), u.getName());
                }
                else
                {
                    dL.setLabelString("No Lesson");
                }
            }
            dayNum++;
            cal1 = cal1.plusDays(1);
        }
    }

    public Dimension getPreferredSize()
    {
        Dimension pref=dayGrid[0][0].getPreferredSize();
        int wid = DGColumns*(int)pref.getWidth();
        int hei = DGRows*(int)pref.getHeight();
        System.out.println("Preferred Size is: "+wid+" by "+hei);
        return new Dimension(wid, hei);
    }

    /**
     * Scrollable Stuff Below
     */
    public Dimension getPreferredScrollableViewportSize() 
    {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
    int orientation,
    int direction) 
    {
        return (int)(getPreferredSize().getHeight()/DGRows);
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
            
            jsb.setValue(jsb.getValue() + e.getWheelRotation());
            System.out.println("Hoo Woo "+jsb.getValue()+" : "+e.getWheelRotation());
        }
        // mEv = e;
    }

    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        System.out.println("Woo Hoo "+e.paramString());
        LocalDate fd = SchoolCalendar.GetCalendar().getFirstDayOfSchool().getLocalDate();
        LocalDate cal1 = fd.plusDays(DGColumns*e.getValue());
        display(cal1);
    }

    public void actionPerformed(ActionEvent e) 
    {
        Object o = e.getSource();

        if( o.equals(jmf_s) )
        {
            File calFile = myCal.getCalendarFile();
            calFile.getAbsolutePath();
            File parentDir = calFile.getAbsoluteFile().getParentFile();
            JFileChooser jfc = new JFileChooser(parentDir);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Class Calendar Files", "ccal");
            jfc.setFileFilter(filter);
            int returnVal = jfc.showSaveDialog(frame);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                doSaveFile(jfc.getSelectedFile());
                System.out.println("You chose to open this file: " +
                    jfc.getSelectedFile().getName());
                System.out.println("Save Thingie");
            }
        }
        else if( o.equals(jmf_e) )
        {
            /*
             * Fix Later
            File calFile = myCal.getCalendarFile();
            String dir = calFile.getAbsolutePath();
            File parentDir = calFile.getAbsoluteFile().getParentFile();
            JFileChooser jfc = new JFileChooser(parentDir);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Class Calendar Files", "ccal");
            jfc.setFileFilter(filter);
            int returnVal = jfc.showSaveDialog(frame);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                doSaveFile(jfc.getSelectedFile());
                System.out.println("You chose to create this file: " +
                    jfc.getSelectedFile().getName());
                System.out.println("New Save Thingie");
            }
            System.out.println("New Thingie");
            String className = JOptionPane.showInputDialog("Please input the class name");

            SchoolCalendar theCal = SchoolCalendar.GetCalendar();
            ClassEditor ce = new ClassEditor(className);
            ce.doShow();
            ClassCalendar newCal = ce.getCalendar();
            System.out.println("The new cal is "+newCal.saveYourself());
             */
        }
        else if( o.equals(jmf_n) )
        {
            /*
             * Fix this later too
            File calFile = myCal.getCalendarFile();
            String dir = calFile.getAbsolutePath();
            File parentDir = calFile.getAbsoluteFile().getParentFile();
            JFileChooser jfc = new JFileChooser(parentDir);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Class Calendar Files", "ccal");
            jfc.setFileFilter(filter);
            int returnVal = jfc.showSaveDialog(frame);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                doSaveFile(jfc.getSelectedFile());
                System.out.println("You chose to create this file: " +
                    jfc.getSelectedFile().getName());
                System.out.println("New Save Thingie");
            }
            System.out.println("New Thingie");
            String className = JOptionPane.showInputDialog("Please input the class name");

            SchoolCalendar theCal = SchoolCalendar.GetCalendar();
            ClassEditor ce = new ClassEditor(className);
            ce.doShow();
            ClassCalendar newCal = ce.getCalendar();
            System.out.println("The new cal is "+newCal.saveYourself());
             */
        }
        else if( o.equals(jmf_x) )
        {
            System.out.println("Exit Thingie");
            System.exit(0);
        }
    }

    boolean doSaveFile(File saveFile)
    {
        try
        {
            if( saveFile.exists() )
            {
                return false;
            }

            saveFile.createNewFile();
            FileWriter fw=new FileWriter(saveFile);
            fw.write( myCal.saveYourself());
            fw.close();
        }
        catch( Exception e)
        {
            JOptionPane.showMessageDialog(null, "Oh Crap", "File Save Failed", JOptionPane.WARNING_MESSAGE);
        }
        return true;
    }
}
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Write a description of class LessonDetailView here.
 * This will be the documentation for your class LessonDetailView.  Please describe 
 * how the class works and how someone may use it.  There is an example on schoolloop in the locker
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LessonDetailView extends JPanel implements ActionListener, DTEventListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4296784035128529613L;

	/**
     * Will only test minimal pane
     */
    static void TestMe()
    {
        LessonDetailView sdv=new LessonDetailView(new SchoolDay(new LocalDate()));

        sdv.doShow();
    }

    LDVBlob oldBlob;
    LDVBlob newBlob;
    JLabel sdDateLabel;
    JTextField sdDateField;
    JLabel sdDayOfLabel;
    NumericTF sdThisDay;
    JLabel sdOf;
    NumericTF sdTotalDay;
    JLabel sdUnitLabel;
    JTextField sdUnitField;
    JLabel dateFieldLabel;
    JTextField lDescField;
    JLabel anAttLabel;
    JTextField anAttField;
    JLabel anotherAttLabel;
    JTextArea anotherAttField;
    JTextField notarealfield;
    JLabel sdSDateLabel;
    JTextField sdSDateField;
    JLabel sdEDateLabel;
    JTextField sdEDateField;
    DialogThing dt;

    protected class LDVBlob
    {
        LocalDate bSDay;
        int bThisDay;
        int bTotalDay;
        Lesson bLesson;
        int bLDuration;
        LocalDate bStartDay;
        LocalDate bEndDay;
        Unit bUnit;
        String bLessonName;
        String bLessonDesc;

        LDVBlob( JTextField s1, NumericTF s2, NumericTF s3, JTextField s4, JTextField s5, JTextField s6, JTextArea s7,
        JTextField s8, JTextField s9, JTextField s10)
        {
            s1.getText();
            bSDay = parseDate(s1.getText());
            bThisDay = Integer.parseInt(s2.getText());
            bLDuration = Integer.parseInt(s3.getText());
            bUnit = null;
            bLesson = null;
            bLessonName = null;
            bLessonDesc = null;
            bStartDay = parseDate(s9.getText());
            bEndDay = parseDate(s10.getText());
        }

        protected LocalDate parseDate(String s)
        {
            DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
            DateTime time = dateStringFormat.parseDateTime(s);
            System.out.println(time);
            return time.toLocalDate();
        }
    }

    // describe each of the attributes declared below
    SchoolDay sDay;
    SDDVObs sObs;
    JPanel panel;
    Lesson theLesson;
    LessonDetailView ldv=this;
    boolean maxPayne=false;

    /**
     * Constructor for objects of class LessonDetailView
     *
     * Describe how the constructor is used.  Add @param fields if required
     */
    public LessonDetailView(SchoolDay sd)
    {
        sDay = sd;
        sObs = new SDDVObs(this);
        

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel bigp = new JPanel(new BorderLayout());
        JLabel lsTitleLabel;

        add(bigp);

        if( sDay == null || sDay.getLeaf() == null )
        {
            panel = createMinimalPanel();
            lsTitleLabel = new JLabel("Hello??");
            maxPayne = false;
        }
        else
        {
            theLesson = (Lesson)sDay.getLeaf();
            panel = createDetailsPanel();
            lsTitleLabel = new JLabel("<html><div text-align:center>"+theLesson.getName()+"</div></html>");
            maxPayne = true;
        }

        bigp.add(lsTitleLabel, BorderLayout.NORTH);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        bigp.add(panel);

        this.setVisible(true);
    }

    /**
     * Build the minimal panel - if no lesson on the date
     */
    private JPanel createMinimalPanel() 
    {
        JPanel panel=new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel sdDateLabel = new JLabel("Date: ");
        JTextField sdDateField = new JTextField(sDay.getDate().toString("MM/dd/YYYY"));

        JLabel sdUnitLabel = new JLabel("Unit:");
        JTextField sdUnitField = new JTextField("No Unit");

        JLabel dateFieldLabel = new JLabel("Lesson:");
        JTextField lDescField = new JTextField("No Lesson");

        lDescField.setMinimumSize(lDescField.getPreferredSize());

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbcl = new GridBagConstraints();
        GridBagConstraints gbcr = new GridBagConstraints();

        int i=0;

        gbcl.insets = new Insets(8,2,8,2);
        gbcl.anchor = GridBagConstraints.EAST;
        gbcl.gridx = 0;
        gbcl.gridwidth = 1;
        gbcl.fill = GridBagConstraints.NONE;

        gbcr.insets = new Insets(2,2,2,2);
        gbcr.anchor = GridBagConstraints.WEST;
        gbcr.gridx = 1;
        gbcr.gridwidth = GridBagConstraints.REMAINDER;
        gbcr.fill = GridBagConstraints.HORIZONTAL;
        gbcr.weightx = 100;

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdDateLabel,  gbcl);
        panel.add(sdDateField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdUnitLabel,  gbcl);
        panel.add(sdUnitField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(dateFieldLabel,  gbcl);
        panel.add(lDescField,  gbcr);
        
        return panel;
    }

    public void doShow()
    {
        dt = new DialogThing(this);
        dt.addDTEventListener(this);
        updateGUI();
        dt.showIt();
    }

    private void updateGUI()
    {
        if( !maxPayne )
        {
            return;
        }
        /*
         * Update createDetailsPanel contents.  Requires sDay to be updated.
         */
        theLesson = (Lesson)sDay.getLeaf();

        System.out.println("Old date value: "+(sdDateField.getText() == null ? "" : sdDateField.getText()));
        sdDateField.setText(sDay.getDate().toString("MM/dd/YYYY"));
        System.out.println("New date value: "+sdDateField.getText());
        sdThisDay.setText(Integer.toString(theLesson.getCurrentDayNum(sDay.getDate())));
        sdTotalDay.setText(Integer.toString(theLesson.getDuration()));
        sdUnitField.setText(theLesson.getUnit().toString());
        lDescField.setText(theLesson.toString());
        anAttField.setText(theLesson.getName()+": "+theLesson.getDescription());
        anotherAttField.setText(theLesson.getDescription()); 
        sdSDateField.setText(theLesson.getStartDate().toString("MM/dd/YYYY"));
        sdEDateField.setText(theLesson.getEndDate().toString("MM/dd/YYYY"));

        oldBlob = new LDVBlob(
            sdDateField,
            sdThisDay,
            sdTotalDay,
            sdUnitField,
            lDescField,
            anAttField,
            anotherAttField,
            notarealfield,
            sdSDateField,
            sdEDateField);
            
        if( SchoolCalendar.GetCalendar().getNextDay(sDay.getDate()) == null )
        {
            
        }
    }

    private JPanel createDetailsPanel() 
    {
        JPanel panel = new JPanel();

        sdDateLabel = new JLabel("<html><div text-align:right>Date: </div></html>");
        sdDateField = new JTextField(sDay.getDate().toString("MM/dd/YYYY"));

        JPanel sdDayOfPanel = new JPanel();
        sdDayOfLabel = new JLabel("<html><div text-align:right>Day: </div></html>");
        sdThisDay = new NumericTF(0); // (""); // FIX THIS!!!!
        sdOf = new JLabel(" of ");
        sdTotalDay = new NumericTF(0); // (""); // FIX THIS!!!!
        sdTotalDay.setEditable(false);
        sdDayOfPanel.add(sdThisDay);
        sdDayOfPanel.add(sdOf);
        sdDayOfPanel.add(sdTotalDay);

        sdUnitLabel = new JLabel("<html><div text-align:right>Unit: </div></html>");
        sdUnitField = new JTextField(theLesson.getUnit().toString());

        dateFieldLabel = new JLabel("<html><div text-align:right>Lesson: </div></html>");
        lDescField = new JTextField(theLesson.toString());

        anAttLabel = new JLabel("<html><div text-align:right>Lesson Name: </div></html>");
        anAttField = new JTextField(theLesson.getName()+": "+theLesson.getDescription());
        anotherAttLabel = new JLabel("<html><div text-align:right>Lesson<br>Description:</div></html>");
        anotherAttField = new JTextArea(theLesson.getDescription(), 3, 1); 
        anotherAttField.setMinimumSize(anotherAttField.getPreferredSize());

        notarealfield = new JTextField("Long name to reserve space");
        notarealfield.setMinimumSize(notarealfield.getPreferredSize());

        sdSDateLabel = new JLabel("<html><div text-align:right>Start Date:</div></html>");
        sdSDateField = new JTextField(theLesson.getStartDate().toString("MM/dd/YYYY"));

        sdEDateLabel = new JLabel("<html><div text-align:right>End Date:</div></html>");
        sdEDateField = new JTextField(theLesson.getEndDate().toString("MM/dd/YYYY"));

        panel.setLayout(new GridBagLayout());
        updateGUI();

        GridBagConstraints gbcl = new GridBagConstraints();
        GridBagConstraints gbcr = new GridBagConstraints();

        int i=0;

        gbcl.insets = new Insets(2,2,2,2);
        gbcl.anchor = GridBagConstraints.EAST;
        gbcl.gridx = 0;
        gbcl.gridwidth = 1;
        gbcl.fill = GridBagConstraints.NONE;

        gbcr.insets = new Insets(2,2,2,2);
        gbcr.anchor = GridBagConstraints.WEST;
        gbcr.gridx = 1;
        gbcr.gridwidth = GridBagConstraints.REMAINDER;
        gbcr.fill = GridBagConstraints.HORIZONTAL;
        gbcr.weightx = 100;

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdDateLabel,  gbcl);
        panel.add(sdDateField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdDayOfLabel,  gbcl);
        panel.add(sdDayOfPanel, gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdUnitLabel,  gbcl);
        panel.add(sdUnitField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(dateFieldLabel,  gbcl);
        panel.add(lDescField,  gbcr); 

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(anAttLabel,  gbcl);
        panel.add(anAttField,  gbcr); 

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(anotherAttLabel,  gbcl);
        panel.add(new JScrollPane(anotherAttField),  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdSDateLabel,  gbcl);
        panel.add(sdSDateField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        panel.add(sdEDateLabel,  gbcl);
        panel.add(sdEDateField,  gbcr);

        JPanel arrowPanel = new JPanel(new GridLayout(1, 3));
        JButton prevBtn = new JButton("Previous");
        // JLabel arrowLabel = new JLabel("");
        JButton editBtn = new JButton("Edit ...");
        JButton nextBtn = new JButton("Next");
        arrowPanel.add(prevBtn);
        arrowPanel.add(editBtn);
        arrowPanel.add(nextBtn);
        arrowPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        prevBtn.addActionListener(this);
        prevBtn.setActionCommand("previous");
        editBtn.addActionListener(this);
        editBtn.setActionCommand("edit");
        nextBtn.addActionListener(this);
        nextBtn.setActionCommand(YESConstants.BtnNEXT);

        JPanel mediumP = new JPanel(new  BorderLayout());
        mediumP.add(panel, BorderLayout.CENTER);
        mediumP.add(arrowPanel, BorderLayout.SOUTH);

        return mediumP;
    }

    public void actionPerformed(ActionEvent e) 
    {
        if( e.getActionCommand().equals("previous") )
        {
            sDay = theLesson.getUnit().getCCal().getSchoolDayForDate(SchoolCalendar.GetCalendar().getPreviousDay(sDay.getDate()));
        }
        else if( e.getActionCommand().equals("edit") )
        {
            LessonDetailEditor lEditor = new LessonDetailEditor( theLesson.getUnit().getCCal(), theLesson);
            DialogThing lDT = new DialogThing(lEditor);
            
            lDT.addDTEventListener( this);
            lDT.showIt();
        }
        else if( e.getActionCommand().equals("next") )
        {
            LocalDate nextDay = SchoolCalendar.GetCalendar().getNextDay(sDay.getDate());
            if( nextDay == null )
            {
                return;
            }
            sDay = theLesson.getUnit().getCCal().getSchoolDayForDate(nextDay);
        }
        else
        {
            return;
        }
        updateGUI();
        dt.showIt();
    }

    public void dtEventPerformed(DTEvent e)
    {
        DialogThing theDT = (DialogThing)e.getSource();
        if( theDT.equals(dt) )
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
        else
        {
            switch(e.getID() )
            {
            case DTEvent.OK_EVENT:
                System.out.println("Lesson Editor Performed OK");
                //System.out.println("The New Calendar Is:\n"+getCalendar().saveYourself()+"\nSo there!!");
                break;
            case DTEvent.CANCEL_EVENT:
                System.out.println("Lesson Editor Performed CANCEL");
                break;
            }
        }
    }
}
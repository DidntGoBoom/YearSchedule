import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Write a description of class LessonDetailEditor here.
 * This will be the documentation for your class LessonDetailEditor.  Please describe 
 * how the class works and how someone may use it.  There is an example on schoolloop in the locker
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LessonDetailEditor extends MetaEditor implements ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1295967093303063173L;
	/**
     * Will only test minimal pane
     */
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
    
    public static final LessonDetailEditor NoLDEditor = new LessonDetailEditor();

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
            if( s == null || s.equals("") )
            {
                return new LocalDate();
            }
            DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
            DateTime time = dateStringFormat.parseDateTime(s);
            System.out.println(time);
            return time.toLocalDate();
        }
    }
    
    LessonDetailEditor() {}

    /*
     * 
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
     */

    // describe each of the attributes declared below
    // SchoolDay sDay;
    SDDVObs sObs;
    JPanel panel;
    Lesson theLesson;
    LessonDetailEditor ldv=this;

    /**
     * Constructor for objects of class LessonDetailEditor
     *
     * Describe how the constructor is used.  Add @param fields if required
     */
    public LessonDetailEditor(ClassCalendar cCal, Lesson l) //, SchoolDay sd)
    {
        sObs = new SDDVObs(this);
        theLesson = l;

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel bigp = new JPanel(new BorderLayout());
        JLabel lsTitleLabel;

        add(bigp);

        bigp.add(createDetailsPanel());
        lsTitleLabel = new JLabel("<html><div text-align:center>"+theLesson.getName()+"</div></html>");
        
        add(lsTitleLabel, BorderLayout.NORTH);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }

    private void updateGUI()
    {
        /*
         * Update createDetailsPanel contents.  Requires sDay to be updated.
         */
        System.out.println("Old date value: "+(sdDateField.getText() == null ? "" : sdDateField.getText()));
        //sdDateField.setText(sDay.getDate().toString("MM/dd/YYYY"));
        System.out.println("New date value: "+sdDateField.getText());
        //sdThisDay.setText(Integer.toString(theLesson.getCurrentDayNum(sDay.getDate())));
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
    }

    private JPanel createDetailsPanel() 
    {
        JPanel panel = new JPanel();

        sdDateLabel = new JLabel("<html><div text-align:right>Date: </div></html>");
        sdDateField = new JTextField();//sDay.getDate().toString("MM/dd/YYYY"));

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
        JLabel arrowLabel = new JLabel("");
        JButton nextBtn = new JButton("Next");
        arrowPanel.add(prevBtn);
        arrowPanel.add(arrowLabel);
        arrowPanel.add(nextBtn);
        arrowPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        prevBtn.addActionListener(this);
        prevBtn.setActionCommand("previous");
        nextBtn.addActionListener(this);
        nextBtn.setActionCommand("next");

        JPanel mediumP = new JPanel(new  BorderLayout());
        mediumP.add(panel, BorderLayout.CENTER);
        mediumP.add(arrowPanel, BorderLayout.SOUTH);

        return mediumP;
    }
    
    public String saveYourself()
    {
        return "";
    }

    public void actionPerformed(ActionEvent e) 
    {
        /*
         * 
        if( e.getActionCommand().equals("previous") )
        {
            sDay = theLesson.getUnit().getCCal().getSchoolDayForDate(SchoolCalendar.GetCalendar().getPreviousDay(sDay.getDate()));
        }
        else if( e.getActionCommand().equals("next") )
        {
            sDay = theLesson.getUnit().getCCal().getSchoolDayForDate(SchoolCalendar.GetCalendar().getNextDay(sDay.getDate()));
        }
        else
        {
            return;
        }
        showIt();
         */
    }
}
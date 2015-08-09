import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import org.joda.time.*;

/**
 * Write a description of class SDDetailView here.
 * This will be the documentation for your class SDDetailView.  Please describe 
 * how the class works and how someone may use it.  There is an example on schoolloop in the locker
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SDDetailView extends JPanel implements DTEventListener //JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6554647583496521377L;

	protected class DetailStuff
    {
        Unit savedU;
        Lesson savedL;
        
        protected DetailStuff(Lesson l, Unit u)
        {
            //savedU = u.clone();
            //savedL = l.clone();
        }
    }
    
    SchoolDay sDay;
    SDDVObs sObs;
    DialogThing dt;
    //JPanel panel;

    private JPanel createDetailsPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        Lesson l = (Lesson)sDay.getLeaf();
        Unit u = sDay.getUnit();

        JLabel sdDateLabel = new JLabel("<html><div text-align:right>Date: </div></html>");
        JTextField sdDateField = new JTextField(sDay.getDate().toString("MM/dd/YYYY"));

        JLabel sdUnitLabel = new JLabel("<html><div text-align:right>"+u.getGenericName()+"</div></html>");
        JTextField sdUnitField = new JTextField(u.toString());

        JLabel dateFieldLabel = new JLabel("<html><div text-align:right>Lesson: </div></html>");
        JTextField lDescField = new JTextField(l.toString());

        JLabel anAttLabel = new JLabel("<html><div text-align:right>Lesson Name: </div></html>");
        JTextField anAttField = new JTextField(l.getName()+": "+l.getDescription());
        JLabel anotherAttLabel = new JLabel("<html><div text-align:right>Lesson<br>Description:</div></html>");
        JTextArea anotherAttField = new JTextArea(l.getDescription(), 3, 1); 
        anotherAttField.setMinimumSize(anotherAttField.getPreferredSize());

        JTextField notarealfield = new JTextField("Long name to reserve space");
        notarealfield.setMinimumSize(notarealfield.getPreferredSize());

        JLabel sdSDateLabel = new JLabel("<html><div text-align:right>Start Date:</div></html>");
        JTextField sdSDateField = new JTextField(l.getStartDate().toString("MM/dd/YYYY"));

        JLabel sdEDateLabel = new JLabel("<html><div text-align:right>End Date:</div></html>");
        JTextField sdEDateField = new JTextField(l.getEndDate().toString("MM/dd/YYYY"));

        panel.setLayout(new GridBagLayout());

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

        return panel;
    }

    private JPanel createMinimalPanel() 
    {
        JPanel panel = new JPanel();
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

    static void TestMe()
    {
        SDDetailView sdv=new SDDetailView(new SchoolDay(new LocalDate()));
        //sdv.setVisible(true);

        sdv.doShow();
    }

    void doShowingThing(JPanel panel)
    {
        int selection = JOptionPane.showConfirmDialog(
                null, panel, "Input Form : "
            , JOptionPane.OK_CANCEL_OPTION
            , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION) 
        {
            LessonDetailView ldv = new LessonDetailView(sDay);
            ldv.setVisible(true);
            //JOptionPane.showMessageDialog(null, "String Thingie", "Thing Stringie", JOptionPane.PLAIN_MESSAGE);
        }
        else if (selection == JOptionPane.CANCEL_OPTION)
        {
            // Do something here.
        }
    }

    public void doShow()
    {
        dt = new DialogThing(this);
        dt.addDTEventListener(this);
        // updateGUI();
        dt.showIt();
    }

    /**
     * Constructor for objects of class SDDetailView
     *
     * Describe how the constructor is used.  Add @param fields if required
     */
    public SDDetailView(SchoolDay sd)
    {
        sDay = sd;
        sObs = new SDDVObs(this);

        if( sDay.getLeaf() == null )
        {
            createMinimalPanel();
        }
        else
        {
            createDetailsPanel();
        }
    }

    public void dtEventPerformed(DTEvent e)
    {
        switch(e.getID() )
        {
        case DTEvent.OK_EVENT:
            System.out.println("It Performed OK");
            //System.out.println("The New Calendar Is:\n"+getCalendar().saveYourself()+"\nSo there!!");
            break;
        case DTEvent.CANCEL_EVENT:
            System.out.println("It Performed CANCEL");
            break;
        }
    }
}

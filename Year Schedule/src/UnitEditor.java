import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Write a description of class SDDetailView here.
 * This will be the documentation for your class SDDetailView.  Please describe 
 * how the class works and how someone may use it.  There is an example on schoolloop in the locker
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UnitEditor extends MetaEditor // implements AddObservable //JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9150727413756555500L;
	ClassCalendar myCal;
    JTabbedPane tPane;
    // ??? static final Unit theUnit=Unit.NoUnit;
    String uName="";
    SchoolDay sDay;
    SDDVObs sObs;
    JButton doLesson;
    JButton doExam;
    Unit theUnit=Unit.NoUnit;
    UnitTreeThing uTreeThing;
    UnitTree uTree;
    CEDialog ceDialog;

    JTextField ueUnitField;
    Document ueUnitDoc;
    JTextField ueUDescField;
    Document ueUDescDoc;
    
    public static final UnitEditor NoUnitEditor = new UnitEditor();

    public Unit getUnit() { return theUnit; }
    
    public UnitEditor() {}

    public UnitEditor(CEDialog c) 
    {
        ceDialog = c;
        theUnit = new Unit();
        uName = "";
        uTreeThing = new UnitTreeThing(theUnit);
    }

    public void setUnit( Unit u) 
    {
        theUnit = u;
        try
        {
            if( !uTreeThing.getUnit().equals(u) )
            {
                uName = theUnit.getName();
                uTreeThing = new UnitTreeThing(theUnit);
                uTree.setModel(uTreeThing);
                uTreeThing.reload();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        if( ueUnitField != null )
        {
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ueUnitField.setText(uName);
                    }
                });
        }
    }

    public JPanel createUnitPanel()
    {
        return createUnitPanel(new Unit());
    }

    public JPanel createUnitPanel(Unit u)
    {
        if( u != null && !u.equals(theUnit) )
        {
            setUnit(u);
        }

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel sdClassLabel = new JLabel("Class Name: ");
        JTextField sdClassField = new JTextField(myCal == null ? "" : myCal.getName());
        sdClassField.setEnabled(false);

        JLabel ueUnitLabel = new JLabel("Unit:");
        ueUnitField = new JTextField(uName);
        ueUnitDoc = ueUnitField.getDocument();
        ueUnitDoc.addDocumentListener(this);

        JLabel ueUDescLabel = new JLabel("Description: ");
        ueUDescField = new JTextField(uName);
        ueUDescDoc = ueUDescField.getDocument();
        ueUDescDoc.addDocumentListener(this);

        uTree = new UnitTree(uTreeThing);
        uTree.addTreeSelectionListener(ceDialog);
        JScrollPane jUTree = new JScrollPane(uTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        doLesson = new JButton("Add Lesson");
        doLesson.setActionCommand(CEDialog.DoNewLesson);
        doLesson.addActionListener(ceDialog);

        doExam = new JButton("Add Exam");
        doExam.setActionCommand(CEDialog.DoNewExam);
        doExam.addActionListener(ceDialog);

        setLayout(new GridBagLayout());

        GridBagConstraints gbcl = new GridBagConstraints();
        GridBagConstraints gbcr = new GridBagConstraints();

        int i=0;

        gbcl.insets = new Insets(8,2,8,2);
        gbcl.anchor = GridBagConstraints.NORTHEAST;
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
        add(sdClassLabel,  gbcl);
        add(sdClassField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(ueUnitLabel,  gbcl);
        add(ueUnitField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(ueUDescLabel,  gbcl);
        add(ueUDescField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(jUTree,  gbcr);
        uTree.setBackground(Color.RED);

        gbcl.gridy = gbcr.gridy = i++;
        JPanel panel2 = new JPanel();
        panel2.setLayout( new FlowLayout() );

        panel2.add( doLesson);
        panel2.add( doExam);

        add(panel2,  gbcr);

        return this;
    }

    public String saveYourself()
    {
        return theUnit.saveYourself();
    }

    void doStuffChanged(DocumentEvent e, String t)
    {
        if( ueUnitDoc.equals(e.getDocument()) )
        {
            theUnit.setName(t);
            System.out.println("Changing name "+t);
        }
        else if( ueUDescDoc.equals(e.getDocument()) )
        {
            theUnit.setDesc(t);
            System.out.println("Changing desc "+t);
        }
        uTreeThing.reload();
    }
}
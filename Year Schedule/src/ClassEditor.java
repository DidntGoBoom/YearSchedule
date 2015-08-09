import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ClassEditor extends MetaEditor implements TreeSelectionListener//, HierarchyListener 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1032688246372407150L;
	ClassCalendar myCal;
    String cName="the class";
    Document sdClassDoc;
    ClassTreeThing cTreeThing;
    ClassTree cTree;
    Unit uCurrent=Unit.NoUnit;
    Lesson lCurrent=Lesson.NoLesson;
    Exam eCurrent = Exam.NoExam;
    MenuThing mBar;
    CEDialog ceDialog;
    
    public void hierarchyListener(HierarchyEvent e) {}
    public void hierarchyChanged(HierarchyEvent e) {}

    public ClassCalendar getCalendar() { return myCal; }

    /**
     * 
     */
    public ClassEditor(CEDialog d)
    {
    }
    
    public ClassEditor(String name, CEDialog d)
    {
        cName = name;
        ceDialog = d;
        myCal = new ClassCalendar();
        myCal.setCourseName(cName);
        cTreeThing = new ClassTreeThing(myCal);
        // doTheCEThing();
    }

    public ClassEditor(ClassCalendar cal, CEDialog d)
    {
        myCal = cal;
        ceDialog = d;
        cTreeThing = new ClassTreeThing(myCal);
        createClassPanel();
        // doTheCEThing();
    }

    private void createClassPanel() 
    {
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel sdClassLabel = new JLabel("Class Name: ");
        JTextField sdClassField = new JTextField(cName);
        sdClassDoc = sdClassField.getDocument();
        sdClassDoc.addDocumentListener(this);

        cTree = new ClassTree(cTreeThing);
        cTree.addTreeSelectionListener(this);
        System.out.println("Class Editor - ceDialog is: "+ceDialog);
        // cTree.addTreeSelectionListener(ceDialog);
        JScrollPane jCTree = new JScrollPane(cTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton doUnit = new JButton("Add Unit");
        doUnit.setActionCommand(CEDialog.DoNewUnit);
        doUnit.addActionListener(ceDialog);
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
        add(jCTree,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(doUnit,  gbcr);
    }

    protected void addUnit(Unit u)
    {
        /*
         * 
        myCal.addUnit(u);
        cTreeThing.reload();
        if( uPane != null )
        {
            uPane.setUnit(u);
        }
        // cTree.addSelectionPath(new TreePath(u)); // causes JTree to go blooie
         */
    }

    public void dtEventPerformed(DTEvent e)
    {
        switch(e.getID() )
        {
            case DTEvent.OK_EVENT:
            System.out.println("It Performed OK");
            // System.out.println("The New Calendar Is:\n"+getCalendar().saveYourself()+"\nSo there!!");
            break;
            case DTEvent.CANCEL_EVENT:
            System.out.println("It Performed CANCEL");
            break;
        }
    }

    public void valueChanged(TreeSelectionEvent e)
    {
        System.out.println("Got the tree "+e);
    }

    void doStuffChanged(DocumentEvent e, String s)
    {
    }

    public String saveYourself()
    {
        return myCal.saveYourself();
    }
}
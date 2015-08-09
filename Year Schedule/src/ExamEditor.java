import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExamEditor extends MetaEditor // implements AddObservable //JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1349084426570712289L;
	JTabbedPane tPane;
    Unit theUnit;
    String cName;
    String uName;
    String lName;
    SchoolDay sDay;
    SDDVObs sObs;
    JButton doUnit;
    JTextField leNameField;
    JTextArea leDescArea;
    NumericTF leSeqField;
    NumericTF leDurField;
    Document leNameDoc;
    Document leDescDoc;
    Document leDurDoc;
    Exam theExam=Exam.NoExam;

    public JButton getDoButton() { return doUnit; }

    /**
     * Exam Number/Name
     * Exam Description
     * Number of Days
     */
    public ExamEditor()
    {
        this(Unit.NoUnit);
    }
    
    public ExamEditor(Unit u)
    {
        setUnit(u);
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel leClassLabel = new JLabel("Class Name: ");
        JTextField leClassField = new JTextField(cName);
        leClassField.setEditable(false);

        JLabel leUnitLabel = new JLabel("Unit:");
        JTextField leUnitField = new JTextField(uName);
        leUnitField.setEditable(false);

        JLabel leNameLabel = new JLabel("<html><div text-align:right>Exam Name: </div></html>");
        leNameField = new JTextField(theExam.getName());
        leNameDoc = leNameField.getDocument();
        System.out.println("\nDocument is: "+leNameDoc.getClass()+"\n");
        leNameDoc.addDocumentListener(this);

        JLabel leSeqLabel = new JLabel("<html><div text-align:right>Exam Number: </div></html>");
        leSeqField = new NumericTF(theUnit.getNumLessons()+1);
        leSeqField.setEditable(false);

        JLabel leDescLabel = new JLabel("<html><div text-align:right>Exam<br>Description:</div></html>");
        leDescArea = new JTextArea(theExam.getDescription(), 3, 1); 
        leDescArea.setMinimumSize(leDescArea.getPreferredSize());
        leDescDoc = leDescArea.getDocument();
        leDescDoc.addDocumentListener(this);

        JLabel leDurLabel = new JLabel("<html><div text-align:right>Exam Length: </div></html>");
        leDurField = new NumericTF(1);
        leDurDoc = leDurField.getDocument();
        leDurDoc.addDocumentListener(this);

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
        add(leClassLabel,  gbcl);
        add(leClassField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(leUnitLabel,  gbcl);
        add(leUnitField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(leSeqLabel,  gbcl);
        add(leSeqField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(leNameLabel,  gbcl);
        add(leNameField,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(leDescLabel,  gbcl);
        add(leDescArea,  gbcr);

        gbcl.gridy = gbcr.gridy = i++;
        add(leDurLabel,  gbcl);
        add(leDurField,  gbcr);
    }
    
    private void updateFields()
    {
        // leClassField.setText(cName);
        // leClassField.setEditable(false);

        // leUnitField.setText(uName);
        // leUnitField.setEditable(false);

        leNameField.setText(theExam.getName());

        // leSeqField = new NumericTF(theUnit.getNumExams()+1);

        leDescArea.setText(theExam.getDescription()); 

        leDurField.setIntValue(theExam.getDuration());
    }
    
    public void setUnit(Unit u)
    {
        theUnit = u;
        cName = (u == null) ? "No Class" : theUnit.getCCal().getName();
        uName = (u == null) ? "No Unit" : theUnit.getName();
        theExam = Exam.NoExam;
    }

    public void setExam( Exam l) 
    {
        if( l == null )
        {
            theExam = Exam.NoExam;
        }
        else
        {
            theExam = l;
            
        }
        updateFields();
    }
    
    public String saveYourself()
    {
        return theExam.saveYourself();
    }
    
    void doStuffChanged(DocumentEvent e, String t)
    {
        if( leDurDoc.equals(e.getDocument()) )
        {
            System.out.println("Changing duration "+t);
            theExam.setDuration(leDurField.getIntValue());
            System.out.println("Dur Check: \n'"+theExam.saveYourself()+"'\n");
        }
        else if( leNameDoc.equals(e.getDocument()) )
        {
            System.out.println("Changing name "+t);
            theExam.setName(t);
        }
        else if( leDescDoc.equals(e.getDocument()) )
        {
            System.out.println("Changing description "+t);
            theExam.setDescription(t);
        }
    }
}
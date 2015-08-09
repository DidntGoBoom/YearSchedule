import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.*;
import javax.swing.text.Document;

public class NumericTFTester extends MetaEditor // implements DocumentListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3773985433446874134L;
	Document leSeqDoc;
    Document leNameDoc;

    static public void testme()
    {
        NumericTFTester tf=new NumericTFTester();
        
        JTextField leNameField = new JTextField("??");
        tf.leNameDoc = leNameField.getDocument();
        tf.leNameDoc.addDocumentListener(tf);

        NumericTF leSeqField = new NumericTF(0);
        tf.leSeqDoc = leSeqField.getDocument();
        tf.leSeqDoc.addDocumentListener(tf);

        JPanel panel = new JPanel();
        panel.add(leNameField);
        panel.add(leSeqField);

        JOptionPane.showMessageDialog(null, panel);
    }

    void doStuffChanged(DocumentEvent e, String t)
    {
        System.out.println("Sequence Document: "+leSeqDoc+"'");
        System.out.println("Name Document: "+leNameDoc+"'");
        System.out.println("Event Document: "+e.getDocument()+"'");
        if( leSeqDoc.equals(e.getDocument()) )
        {
            System.out.println("Changing sequence "+t);
        }
        else if( leNameDoc.equals(e.getDocument()) )
        {
            System.out.println("Changing name "+t);
        }
    }
    
    public String saveYourself()
    {
        return "";
    }

    /*
     * 
    public void insertUpdate(DocumentEvent e)
    {
        doStuffChanged(e, getDocString(e));
    }

    public void removeUpdate(DocumentEvent e)
    {
        doStuffChanged(e, getDocString(e));
    }

    public void changedUpdate(DocumentEvent e)
    {
        doStuffChanged(e, getDocString(e));
    }

    String getDocString(DocumentEvent e)
    {
        Document d = e.getDocument();
        String t="";
        try
        {
            t = d.getText(0, d.getLength());
        }
        catch(Exception x)
        {
        }
        return t;
    }
     */
}
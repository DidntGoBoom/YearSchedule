import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

abstract class MetaEditor extends JPanel implements DocumentListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1237699379366182035L;

	public abstract String saveYourself();
    void doStuffChanged(DocumentEvent e, String d) { }
    
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
}
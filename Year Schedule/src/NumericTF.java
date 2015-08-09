import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class NumericTF extends JTextField
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 422518682853566508L;

	static boolean testInt(String text) 
    {
        try 
        {
            Integer.parseInt(text);
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    public NumericTF(int x)
    {
        super((new Integer(x)).toString());
        PlainDocument doc = (PlainDocument) getDocument();
        doc.setDocumentFilter(new MyIntFilter());
        setHorizontalAlignment(JTextField.RIGHT);
    }

    public void setIntValue(int i) 
    {
        setText( new String(Integer.toString(i)));
    }

    public int getIntValue()
    {
        if( testInt(getText()) )
        {
            return Integer.parseInt(getText());
        }
        else
        {
            return 0;
        }
    }

    class MyIntFilter extends DocumentFilter 
    {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (NumericTF.testInt(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (NumericTF.testInt(sb.toString())) 
            {
                super.replace(fb, offset, length, text, attrs);
            } 
            else 
            {
                // warn the user and don't allow the insert
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException 
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (NumericTF.testInt(sb.toString())) 
            {
                super.remove(fb, offset, length);
            } 
            else 
            {
                // warn the user and don't allow the insert
            }
        }

    }

    public static void main(String[] args) 
    {
        NumericTF textField = new NumericTF(10);
        textField.setColumns(30);

        JPanel panel = new JPanel();
        panel.add(textField);

        JOptionPane.showMessageDialog(null, panel);
        System.out.println("Value is: "+textField.getIntValue());
    }
}
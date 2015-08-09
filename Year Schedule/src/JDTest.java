import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class JDTest
{
    private double[] marks;
    private JTextField[] marksField;
    public JDTest()
    {
        marks = new double[3];
        marksField = new JTextField[3];
        marksField[0] = new JTextField(10);
        marksField[1] = new JTextField(10);
        marksField[2] = new JTextField(10);
    }

    private void displayGUI()
    {
        int selection = JOptionPane.showConfirmDialog(
                null, createMinimalPanel(), "Input Form : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION) 
        {
            for ( int i = 0; i < 3; i++)
            {
                marks[i] = Double.valueOf(marksField[i].getText());             
            }
            Arrays.sort(marks);
            double average = (marks[1] + marks[2]) / 2.0;
            JOptionPane.showMessageDialog(null
                    , "Average is : " + Double.toString(average)
                    , "Average : "
                    , JOptionPane.PLAIN_MESSAGE);
        }
        else if (selection == JOptionPane.CANCEL_OPTION)
        {
            // Do something here.
        }
    }

    private JPanel createMinimalPanel() 
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel sdDateLabel = new JLabel("Date: ");
        JTextField sdDateField = new JTextField("MM/dd/YYYY");

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

    @SuppressWarnings("unused")
	private JPanel getPanel()
    {
        JPanel basePanel = new JPanel();
        //basePanel.setLayout(new BorderLayout(5, 5));
        basePanel.setOpaque(true);
        basePanel.setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 5, 5));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel mLabel1 = new JLabel("Enter Marks 1 : ");
        JLabel mLabel2 = new JLabel("Enter Marks 2 : ");
        JLabel mLabel3 = new JLabel("Enter Marks 3 : ");

        centerPanel.add(mLabel1);
        centerPanel.add(marksField[0]);
        centerPanel.add(mLabel2);
        centerPanel.add(marksField[1]);
        centerPanel.add(mLabel3);
        centerPanel.add(marksField[2]);

        basePanel.add(centerPanel);

        return basePanel;
    }
    
    public static  void testMe()
    {
        new JDTest().displayGUI();
    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new JDTest().displayGUI();
            }
        });
    }
}
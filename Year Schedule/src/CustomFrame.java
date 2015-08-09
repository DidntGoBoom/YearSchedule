import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class CustomFrame extends JFrame
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 314028435625539715L;
	private int labelCounter = 0;
    private int maxLabels = 3;
    private Box box;
    private JPanel pane;

    public CustomFrame()
    {
        super("Custom JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        JScrollPane scr = new JScrollPane(pane);
        add(scr);

        for(int i = 1; i <= 15; i++) addNewLabel("Label " + i);

        setSize(200,130);
        setVisible(true);
    }

    private void addNewLabel(String s)
    {
        if(labelCounter % maxLabels == 0)
        {
            box = Box.createVerticalBox();
            box.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        }
        box.add(new JLabel(s));
        pane.add(box);
        labelCounter++;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new CustomFrame();
            }
        });

    }
}
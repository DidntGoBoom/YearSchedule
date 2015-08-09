import javax.swing.*;
import java.awt.event.*;

public class MenuThing extends JMenuBar
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8719193738766093004L;
	static final String MenuThing_NEW = "new_menu_item";
    static final String MenuThing_OPEN = "open_menu_item";
    static final String MenuThing_SAVE = "save_menu_item";
    static final String MenuThing_EXIT = "exit_menu_item";
    private JMenuItem jmf_n;
    private JMenuItem jmf_e;
    private JMenuItem jmf_s;
    private JMenuItem jmf_x;

    public MenuThing(ActionListener al)
    {
        JMenu jmf = new JMenu("File");
        add(jmf);
        jmf_n = new JMenuItem("New calendar ...");
        jmf.add(jmf_n);
        jmf_n.setActionCommand(MenuThing_NEW);
        jmf_n.addActionListener(al);
        jmf_e = new JMenuItem("Open calendar ...");
        jmf_e.setActionCommand(MenuThing_OPEN);
        jmf.add(jmf_e);
        jmf_e.addActionListener(al);
        jmf_s = new JMenuItem("Save calendar ...");
        jmf.add(jmf_s);
        jmf_s.addActionListener(al);
        jmf.addSeparator();
        jmf_x = new JMenuItem("Exit");
        jmf.add(jmf_x);
        jmf_x.addActionListener(al);

    }
    
    public JMenuItem getNewItem() { return jmf_n; }
    public JMenuItem getEditItem() { return jmf_e; }
    public JMenuItem getSaveItem() { return jmf_s; }
    public JMenuItem getExitItem() { return jmf_x; }
}
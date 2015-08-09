import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

public class CEDialog extends DialogThing implements ChangeListener, DTEventListener, 
                        PropertyChangeListener, TreeSelectionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -676455280602554628L;
	JTabbedPane tPane;
    MenuThing mBar=null;
    ClassEditor cPane=null;
    UnitEditor uPane=null;
    LessonEditor lPane=null;
    LessonDetailEditor ldPane=null;
    ExamEditor ePane=null;
    String cName = "";
    String uName = "";
    String lName = "";
    String eName = "";

    final public static String DoNewUnit = "new_unit";
    final public static String DoNewLesson = "new_lesson";
    final public static String DoNewExam = "new_exam";

    public ClassCalendar getCalendar() { return cPane.getCalendar(); }

    public CEDialog()
    { 
        this("", null);
    }

    public CEDialog(String cName)
    {
        this(cName, null);
    }

    public CEDialog(ClassCalendar cc)
    {
        this("", cc);
    }

    public CEDialog(String cName, ClassCalendar cc)
    {
        super(new JTabbedPane());
        if( cc != null )
        {
            doTheUsedCEThing(cc);
        }
        else
        {
            doTheNewCEThing(cName);
        }
        addMenuBar(new MenuThing(this));
        addDTEventListener(this);
    }

    void doTheNewCEThing(String cName)
    {
        tPane = (JTabbedPane)jComp;

        cPane = new ClassEditor( cName, this);
        tPane.add("Class", cPane); // ???? ("Class "+cName, this);

        uPane = new UnitEditor( this);

        tPane.add("Unit", uPane); // .createUnitPanel(uPane.getUnit()));
        tPane.setEnabledAt( tPane.indexOfComponent(uPane), false);

        lPane = new LessonEditor(uPane.getUnit());
        tPane.add("Lesson", lPane);
        tPane.setEnabledAt( tPane.indexOfComponent(lPane), false);

        ldPane = LessonDetailEditor.NoLDEditor;
        tPane.add("Lesson Detail", ldPane);
        tPane.setEnabledAt( tPane.indexOfComponent(ldPane), false);

        ePane = new ExamEditor(uPane.getUnit());
        tPane.add("Exam", ePane);
        tPane.setEnabledAt( tPane.indexOfComponent(ePane), false);

        System.out.println("CEditor "+getPreferredSize());

        tPane.addChangeListener(this);
        addPropertyChangeListener(this);

        add(tPane);
    }

    void doTheUsedCEThing(ClassCalendar cc)
    {
        tPane = (JTabbedPane)jComp;

        cPane = new ClassEditor( cc, this);
        tPane.add("Class", cPane); // ???? ("Class "+cName, this);

        uPane = new UnitEditor( this);

        tPane.add("Unit", uPane); // .createUnitPanel(uPane.getUnit()));
        tPane.setEnabledAt( tPane.indexOfComponent(uPane), false);

        lPane = new LessonEditor(uPane.getUnit());
        tPane.add("Lesson", lPane);
        tPane.setEnabledAt( tPane.indexOfComponent(lPane), false);

        ldPane = LessonDetailEditor.NoLDEditor;
        tPane.add("Lesson Detail", ldPane);
        tPane.setEnabledAt( tPane.indexOfComponent(ldPane), false);

        ePane = new ExamEditor(uPane.getUnit());
        tPane.add("Exam", ePane);
        tPane.setEnabledAt( tPane.indexOfComponent(ePane), false);

        System.out.println("CEditor "+getPreferredSize());

        tPane.addChangeListener(this);
        // ???? addHierarchyListener(this);
        addPropertyChangeListener(this);

        add(tPane);
    }

    public void actionPerformed(ActionEvent e) 
    {
        /*
         * actionPerformed does way too much stuff.  It listens to all buttons - add unit/lesson/exam, 
         * menu buttons, etc.  Would be nice to switch here, but impossible ...
         */
        if (DoNewUnit.equals(e.getActionCommand())) 
        {
            /*
             * Doesn't matter where it is called from, makes a new unit in the current ClassCalendar.
             * 
             */
            String uName = JOptionPane.showInputDialog("Please input the unit name");
            if( uName != null )
            {
                Unit newU = new Unit(cPane.getCalendar().getNumUnits(), uName, "", cPane.getCalendar());
                cPane.addUnit(newU);
                uPane.setUnit(newU);
                enableUPane( true);
            }
            else
            {
                System.out.println("No input");
            }

            //uPane.getDoButton().setActionCommand(DoNewLesson);
            //uPane.getDoButton().addActionListener(this);
            System.out.println("Action Thingie");
        }
        else if (DoNewLesson.equals(e.getActionCommand())) 
        {
            String lName = JOptionPane.showInputDialog("Please input the lesson name");
            if( lName != null )
            {
                Lesson newL = new Lesson(lName, 1, "", uPane.getUnit());
                uPane.getUnit().addLesson(newL);
                enableLPane(true);
                lPane.setLesson(newL);
            }
            else
            {
                System.out.println("No input");
            }

            System.out.println("Action Thingie");
        }
        else if (DoNewExam.equals(e.getActionCommand())) 
        {
            String eName = JOptionPane.showInputDialog("Please input the exam name");
            Exam newE = new Exam(eName, "", uPane.getUnit());
            uPane.getUnit().addExam(newE);
            tPane.setEnabledAt( tPane.indexOfComponent(ePane), true);
            ePane.setExam(newE);

            System.out.println("Action Thingie");
        }
        else if (MenuThing.MenuThing_NEW.equals(e.getActionCommand())) 
        {
            String eName = JOptionPane.showInputDialog("New Menu Thing");
            Exam newE = new Exam(eName, "", uPane.getUnit());
            uPane.getUnit().addExam(newE);
            tPane.setEnabledAt( tPane.indexOfComponent(ePane), true);
            ePane.setExam(newE);

            System.out.println("Action Thingie");
        }
        else if (MenuThing.MenuThing_OPEN.equals(e.getActionCommand()))
        {
            /*
             * Later ...
            File parentDir;
            File calFile = myCal.getCalendarFile();
            if( calFile != null )
            {
                String dir = calFile.getAbsolutePath();
                parentDir = calFile.getAbsoluteFile().getParentFile();
            }
            else
            {
                parentDir = Paths.get("").toAbsolutePath().toFile();
            }
            System.out.println("Path chosen is: "+parentDir);
            JFileChooser jfc = new JFileChooser(parentDir);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Class Calendar Files", "ccal");
            jfc.setFileFilter(filter);
            int returnVal = jfc.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                myCal = new ClassCalendar(jfc.getSelectedFile());
                doTheCEThing();
                System.out.println("You chose to open this file: " + jfc.getSelectedFile().getName());
                System.out.println("Save Thingie");

                System.out.println("Path chosen is: "+parentDir);
                JFileChooser jfc = new JFileChooser(parentDir);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Class Calendar Files", "ccal");
                jfc.setFileFilter(filter);
                int returnVal = jfc.showOpenDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION) 
                {
                    System.out.println("It went boom. " + jfc.getSelectedFile().getName());
                }
            }
             */
            System.out.println("Menu Thing "+e.getActionCommand());
            System.out.println("Opening "+cPane.getCalendar().saveYourself());
        }
        else if (MenuThing.MenuThing_SAVE.equals(e.getActionCommand())) 
        {
            System.out.println("Menu Thing "+e.getActionCommand());
            // System.out.println("Saving "+cPane.getCalendar().saveYourself());
        }
        else if (MenuThing.MenuThing_EXIT.equals(e.getActionCommand())) 
        {
            // System.out.println("Menu Thing "+e.getActionCommand());
        }
    } 

    public void dtEventPerformed(DTEvent e)
    {
        switch(e.getID() )
        {
            case DTEvent.OK_EVENT:
            System.out.println("It Performed OK");
            // System.out.println("The New Calendar Is:\n"+cPane.getCalendar().saveYourself()+"\nSo there!!");
            break;
            case DTEvent.CANCEL_EVENT:
            System.out.println("It Performed CANCEL");
            break;
        }
    }

    public void propertyChange(PropertyChangeEvent e)
    {
        System.out.println("*** Property Change: "+e.getPropertyName());
    }

    public void stateChanged(ChangeEvent e)
    {
        System.out.println("State Changed: "+e);
    }

    void enableUPane(boolean on) { tPane.setEnabledAt( tPane.indexOfComponent(uPane), on); }

    void enableLPane(boolean on) { tPane.setEnabledAt( tPane.indexOfComponent(lPane), on); }

    void enableEPane(boolean on) { tPane.setEnabledAt( tPane.indexOfComponent(ePane), on); }

    void selectUnit(Unit u)
    {
        System.out.println("Unit "+u);
        enableUPane(true);
        enableLPane(false);
        enableEPane(false);
        uPane.setUnit(u);
    }

    void selectLesson(Lesson l)
    {
        System.out.println("********************** selecting lesson "+l.getName()+ "**********************");
        System.out.println("Lesson "+l);
        enableUPane(true);
        enableLPane(true);
        enableEPane(false);
        lPane.setLesson(l);
    }

    void selectExam(Exam e)
    {
        System.out.println("Exam "+e);
        enableUPane(true);
        enableLPane(false);
        enableEPane(true);
        ePane.setExam(e);
    }

    void selectNoUnit()
    {
        uPane.setUnit( Unit.NoUnit);
        enableUPane(false);
        selectNoLeaf();
    }

    void selectNoLeaf()
    {
        lPane.setLesson( Lesson.NoLesson);
        ePane.setExam( Exam.NoExam);        
        enableLPane(false);
        enableEPane(false);
    }

    public void valueChanged(TreeSelectionEvent e)
    {
        tPane.getSelectedComponent();
        System.out.println("\n***** Tree Selection *****\n"+e.getPaths()+"\n*****\n");

        JTree jt = (JTree)e.getSource();
        Object node = jt.getLastSelectedPathComponent();
        if( jt instanceof ClassTree )
        {
            if( node instanceof Unit )
            {
                selectUnit((Unit)node);
            }
            else if( node instanceof Lesson )
            {
                selectLesson((Lesson)node);
            }
            else if( node instanceof Exam )
            {
                selectExam((Exam)node);
            }
            else
            {
                selectNoUnit();
            }
        }
        else if( jt instanceof UnitTree )
        {
            if( node instanceof Lesson )
            {
                selectLesson((Lesson)node);
            }
            else if( node instanceof Exam )
            {
                selectExam((Exam)node);
            }
            else
            {
                selectNoLeaf();
            }
        }

        System.out.println("Tree Selected "+e);
    }

    public void doShow()
    {
        DialogThing dt = new DialogThing(tPane);
        mBar = new MenuThing(this);
        dt.addMenuBar(mBar);
        dt.addDTEventListener(this);
        dt.showIt();
    }

    public static void main2(String[] argv)
    {
        String className = JOptionPane.showInputDialog("Please input the class name");
        CEDialog ceD=new CEDialog(className);

        ceD.showIt();
    }

    public static void main(String[] argv)
    {
        CEDialog ceD=null;
        try
        {
            ceD=new CEDialog(new ClassCalendar("aplew.ccal"));
        }
        catch( Exception e )
        {
            System.out.println("Oops ...");
            System.exit(-1);
        }

        ceD.showIt();
    }

}
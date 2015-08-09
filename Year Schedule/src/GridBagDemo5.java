import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class GridBagDemo5 extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094938317358469752L;

	/**
	 * constructor. 
	 */
	public GridBagDemo5(){
		initGUI();
	}
	
	
	public void initGUI() {
		
		setTitle("");

		//JPanel bigp = new JPanel(new VerticalFlowLayout());
		JPanel bigp = new JPanel(new BorderLayout());
		this.getContentPane().add(bigp);
        
		JLabel jl = new JLabel("Hello??");
		bigp.add(jl, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridBagLayout());
		bigp.add(panel, BorderLayout.CENTER);
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		
		panel.add(createPanel(), gbc);
		
		this.pack();
		
		this.setVisible(true);
	}

	private JPanel createPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel thingNameLabel = new JLabel("Thing Name");
		JLabel anAttributeLabel = new JLabel("An Attribute");
		JLabel dateFieldLabel = new JLabel("Date Field");
		JLabel anAttLabel = new JLabel("An Att");
		JLabel anotherAttLabel = new JLabel("Another Att");
		JLabel anotherAtt2Label = new JLabel("Another Att");
		
		JTextField thingNameField = new JTextField("");
		JTextField anAttributeField = new JTextField("");
		JTextField dateFieldField = new JTextField("");
		JTextField anAttField = new JTextField("");
		JTextArea anotherAttField = new JTextArea(3, 1); 
		JTextField anotherAtt2Field = new JTextField("", 10);
		
		anotherAtt2Field.setMinimumSize(anotherAtt2Field.getPreferredSize());

		JCheckBox checkbox1 = new JCheckBox("A Checkbox");
		JCheckBox checkbox2 = new JCheckBox("A Checkbox");

		panel.setLayout(new GridBagLayout());
	
		GridBagConstraints gbc = new GridBagConstraints();
		
		int i=0;
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(thingNameLabel,  gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;		
		panel.add(thingNameField,  gbc);		
		
		i++;
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(checkbox1,  gbc);

		i++;
				
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anAttributeLabel,  gbc);
				
		gbc.gridx = 1;
		gbc.gridy = i;		
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(anAttributeField,  gbc);		
		
		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(dateFieldLabel,  gbc);

		gbc.gridx = 1;
		gbc.gridy = i;		
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(dateFieldField,  gbc);		
		
		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anAttLabel,  gbc);
				
		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(anAttField,  gbc);		

		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anotherAttLabel,  gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(new JScrollPane(anotherAttField),  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anotherAtt2Label,  gbc);

		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(anotherAtt2Field,  gbc);
		

		gbc.gridx = 2;
		gbc.gridy = i;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(checkbox2,  gbc);
		
		return panel;
	}

	public static void main(String[] args) {
		GridBagDemo5 frame = new GridBagDemo5();
		
		
		frame.pack();
		frame.setVisible(true);
	}
}


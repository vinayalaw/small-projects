/* Simple applet that has a single input text field
 * and a single output text field. It also has a label.
 * When the user presses return, the action is done.
 * The intent of this code is to make input and output reusable.
 * 
 * In this case, the action is PostFixEvaluator.evaluate(input field text)
 * 
 * @author Beth Katz
 */

import java.awt.event.*; 
import java.awt.*;
import javax.swing.*;

public class PostfixApplet extends JApplet implements ActionListener { 
	private static final long serialVersionUID = 1L;
	private final int AppletWIDTH = 300;
	private final int AppletHEIGHT = 200;
	private JTextField inputField;
	private JTextArea outputField;
	private final String labelText = "Enter postfix expression";
	private final int OutputROWS = 5;
	private final int OutputCOLUMNS = 25;
	private final int TextSIZE = 14;

	public void init()  
	{ 
		// set up layout of components
		JPanel contentPane = new JPanel(new BorderLayout( ));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		createTextItems(contentPane);
		contentPane.setOpaque(true);
		setContentPane(contentPane);
		setSize(AppletWIDTH, AppletHEIGHT);
	}

	/**
	 * Creates the text items including places to enter text and labels.
	 * Labels cannot be changed by the user.
	 * Text can be changed by the user unless it's not editable.
	 * @param widgetPanel is the JPanel that will hold the text items.
	 */
	private void createTextItems(JPanel widgetPanel) {
		widgetPanel.add(Box.createRigidArea(new Dimension(0,5)));

		JPanel panel = new JPanel();
		widgetPanel.add(panel);

		JLabel inputLabel = new JLabel();
		panel.add(inputLabel);
		inputLabel.setText(labelText);
		inputLabel.setFont(new Font("Times", Font.BOLD, 18));

		inputField = new JTextField("", OutputCOLUMNS-5);
		panel.add(inputField);
		inputField.setFont(new Font("Times", Font.PLAIN, TextSIZE));
		inputField.setEditable(true);
		inputField.addActionListener(this);

		outputField = new JTextArea("", OutputROWS, OutputCOLUMNS);
		panel.add(outputField);
		outputField.setEditable(false);
		outputField.setFont(new Font("Times", Font.PLAIN, TextSIZE));
		outputField.setBorder(BorderFactory.createLineBorder(Color.black));
		outputField.setLineWrap(true);
		outputField.setWrapStyleWord(true);
	}

	public void actionPerformed(ActionEvent evt) {
		String text = inputField.getText();
		inputField.selectAll();
		outputField.setText( text + "\nresults in\n" 
				+ PostfixEvaluator.evaluate(text) );
	}

}

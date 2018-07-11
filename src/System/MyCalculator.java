package System;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A panel with calculator buttons and a result display
 */
public class MyCalculator extends JFrame {
	private JButton display;
	private JPanel panel;
	private double result;
	private String lastCommand;
	private boolean start;

	public MyCalculator() {
		
		int width = 640,height = 480;
        setTitle("计算器");
        setSize(width,height);
        //更换Java咖啡图标
      	ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));	
      	this.setIconImage(imageIcon.getImage());	
        //设置软件呈现的位置在屏幕中间
        setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-width)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-height)/2);
		
        result = 0;
		lastCommand = "=";
		start = true;

		// add the display
		display = new JButton("0");
		display.setEnabled(false);
		add(display, BorderLayout.NORTH);

		ActionListener insert = new InsertAction();
		ActionListener command = new CommandAction();
		// add the button in a 4 * 4 grid
		panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4));

		addButton("7", insert);
		addButton("8", insert);
		addButton("9", insert);
		addButton("/", command);

		addButton("4", insert);
		addButton("5", insert);
		addButton("6", insert);
		addButton("*", command);

		addButton("1", insert);
		addButton("2", insert);
		addButton("3", insert);
		addButton("-", command);

		addButton("0", insert);
		addButton(".", insert);
		addButton("=", command);
		addButton("+", command);
		add(panel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	/**
	 * Adds a button to the center panel.
	 * @param label the button label
	 * @param listener the button listener
	 */
	private void addButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		panel.add(button);
	}

	/**
	 * This action inserts the button action string to the end of the display text
	 */
	private class InsertAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String input = event.getActionCommand();
			if(start) {
				display.setText("");
				start = false;
			}
			display.setText(display.getText() + input);
		}
	}

	/**
	 * This action executes the command that the button action string denotes.
	 */
	private class CommandAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			if(start) {
				if(command.equals("-")) {
					display.setText(command);
					start = false;
				}
				else lastCommand = command;
			}
			else {
				calculate(Double.parseDouble(display.getText()));
				lastCommand = command;
				start = true;
			}
		}
	}

	/**
	 * Carries out the pending calculation.
	 * @param x the value to be accumulated with the perior result
	 */
	public void calculate(double x) {
		if(lastCommand.equals("+"))	result += x;
		else if(lastCommand.equals("-")) result -= x;
		else if(lastCommand.equals("*")) result *= x;
		else if(lastCommand.equals("/")) result /= x;
		else if(lastCommand.equals("=")) result = x;
		display.setText("" + result);
	}
	
}
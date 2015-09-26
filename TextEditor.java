import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

/*
 * A simple Text Editor.  This demonstrates the use of a 
 * JFileChooser for the user to select a file to read from or write to.
 * It also demonstrates reading and writing text files.
 */
public class TextEditor implements ActionListener {
	// Size of editing text area.
	private static final int NUM_ROWS = 25;
	private static final int NUM_COLS = 50;
	private int fontsize = 24;

	// Buttons to save and load files.
	private JButton saveButton, loadButton, newButton;

	// Area where the user does the editing
	private JTextArea text;
	private JTextField textField;
	private JLabel lblFontSize;
	private F51 f51;

	// Creates the GUI
	public TextEditor(F51 f51) {
		this.f51 = f51;
		JFrame frame = new JFrame("Editor");
		JPanel buttonPanel = new JPanel();
		saveButton = new JButton("Save");
		loadButton = new JButton("Reload");
		newButton = new JButton("New");
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(newButton);

		text = new JTextArea(NUM_ROWS, NUM_COLS);
		text.setFont(new Font("Courier New", Font.PLAIN, fontsize));
		JScrollPane textScroller = new JScrollPane(text);
		Container contentPane = frame.getContentPane();
		contentPane.add(textScroller, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.NORTH);
		
		lblFontSize = new JLabel("Font size");
		buttonPanel.add(lblFontSize);
		
		textField = new JTextField();
		textField.setToolTipText("Enter to apply");
		textField.setText("24");
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int fsize = Integer.parseInt(textField.getText());
					text.setFont(new Font("Courier New", Font.PLAIN, fsize));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid number " + textField.getText());
				}
			}
		});
		buttonPanel.add(textField);
		textField.setColumns(3);

		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		newButton.addActionListener(this);

		frame.pack();
		frame.setVisible(true);
		
		loadFile();
	}

	// Listener for button clicks that loads the
	// specified files and puts it in the
	// editor.
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == saveButton) {
			saveFile();
		} else if (event.getSource() == loadButton) {
			loadFile();
		} else {
			newFile();
		}
	}
	
	private void newFile() {
		int opt = JOptionPane.showConfirmDialog(null, "This will discard all unsaved changes!", "Create new file", JOptionPane.OK_CANCEL_OPTION);
		
		if (opt == JOptionPane.OK_OPTION)
			text.setText("MaxRadius(300)\r\nshadow()\r\ndiv(24)\r\n\r\n\r\n\r\n\r\n\r\nw(-33,0,55,11,10,10,1)\r\nw(33,0,55,11,-10,10,1)\r\nw(-31,-1,-60,1,16,11,1)\r\nw(31,-1,-60,1,-16,11,1)");
	}

	// Display a file chooser so the user can select a file
	// to save to. Then write the contents of the text area
	// to that file. Does nothing if the user cancels out
	// of the file chooser.
	private void saveFile() {
		File file = new File(".\\o.rad");

		try {
			// Now write to the file
			PrintWriter output = new PrintWriter(new FileWriter(file));
			output.println(text.getText());
			output.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't save file "
					+ e.getMessage());
		}
		
		f51.remake();
	}

	// Display a file chooser so the user can select a file to load.
	// Then load the file into the editing area. Does nothing if
	// the user cancels the file chooser.
	private void loadFile() {
		String line;
		File file = new File(".\\o.rad");

		try {
			// Open the file.
			BufferedReader input = new BufferedReader(new FileReader(file));

			// Clear the editing area
			text.setText("");

			// Fill up the ediitng area with the contents of the file being
			// read.
			line = input.readLine();
			while (line != null) {
				text.append(line + "\n");
				line = input.readLine();
			}

			// Close the file
			input.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Can't load file "
					+ e.getMessage());
		}
	}
}

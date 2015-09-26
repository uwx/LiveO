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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * A simple Text Editor.  This demonstrates the use of a
 * JFileChooser for the user to select a file to read from or write to.
 * It also demonstrates reading and writing text files.
 */
public class TextEditor implements ActionListener {
	// Size of editing text area.
	private static final int NUM_ROWS = 25;
	private static final int NUM_COLS = 50;
	private final int fontsize = 24;

	// Buttons to save and load files.
	private final JButton saveButton, loadButton, newButton;

	// Area where the user does the editing
	private final JTextArea text;
	private final JTextField textField;
	private final JLabel lblFontSize;
	private final F51 f51;
	private final JButton btnMirrorXAxis;
	private final JButton btnMirrorYAxis;
	private final JButton btnMirrorZAxis;

	// Creates the GUI
	public TextEditor(final F51 f51) {
		this.f51 = f51;
		final JFrame frame = new JFrame("Editor");
		final JPanel buttonPanel = new JPanel();
		saveButton = new JButton("Save");
		loadButton = new JButton("Reload");
		newButton = new JButton("New");
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(newButton);

		text = new JTextArea(NUM_ROWS, NUM_COLS);
		text.setFont(new Font("Courier New", Font.PLAIN, fontsize));
		final JScrollPane textScroller = new JScrollPane(text);
		final Container contentPane = frame.getContentPane();
		contentPane.add(textScroller, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.NORTH);

		lblFontSize = new JLabel("Font size");
		buttonPanel.add(lblFontSize);

		textField = new JTextField();
		textField.setToolTipText("Enter to apply");
		textField.setText("24");
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					final int fsize = Integer.parseInt(textField.getText());
					text.setFont(new Font("Courier New", Font.PLAIN, fsize));
				} catch (final Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid number " + textField.getText());
				}
			}
		});
		buttonPanel.add(textField);
		textField.setColumns(3);

		btnMirrorXAxis = new JButton("Mirror X Axis");
		btnMirrorXAxis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				mirror(3);
			}
		});
		buttonPanel.add(btnMirrorXAxis);

		btnMirrorYAxis = new JButton("Mirror Y Axis");
		btnMirrorYAxis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				mirror(4);
			}
		});
		buttonPanel.add(btnMirrorYAxis);

		btnMirrorZAxis = new JButton("Mirror Z Axis");
		btnMirrorZAxis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				mirror(5);
			}
		});
		buttonPanel.add(btnMirrorZAxis);

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
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getSource() == saveButton)
			saveFile();
		else if (event.getSource() == loadButton)
			loadFile();
		else
			newFile();
	}

	private void newFile() {
		final int opt = JOptionPane.showConfirmDialog(null, "This will discard all unsaved changes!", "Create new file",
				JOptionPane.OK_CANCEL_OPTION);

		if (opt == JOptionPane.OK_OPTION)
			text.setText(
					"MaxRadius(300)\r\nshadow()\r\ndiv(24)\r\n\r\n\r\n\r\n\r\n\r\nw(-33,0,55,11,10,10,1)\r\nw(33,0,55,11,-10,10,1)\r\nw(-31,-1,-60,1,16,11,1)\r\nw(31,-1,-60,1,-16,11,1)");
	}

	// Display a file chooser so the user can select a file
	// to save to. Then write the contents of the text area
	// to that file. Does nothing if the user cancels out
	// of the file chooser.
	private void saveFile() {
		final File file = new File(".\\o.rad");

		try {
			// Now write to the file
			final PrintWriter output = new PrintWriter(new FileWriter(file));
			output.println(text.getText());
			output.close();
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Can't save file " + e.getMessage());
		}

		f51.remake();
	}

	private void mirror(final int axis) {
		if (axis == 3 || axis == 4 || axis == 5) {
			final String string = new StringBuilder().append("").append(text.getSelectedText()).append("\n").toString();
			String output = "\n\n";
			output = output + "// Mirror of the polygons above along the ";
			if (axis == 3)
				output = output + "X axis:";
			if (axis == 4)
				output = output + "Y axis:";
			if (axis == 5)
				output = output + "Z axis:";
			output = new StringBuilder().append(output).append("\n\n").toString();
			int i_200_ = 0;
			int i_201_ = string.indexOf("\n", 0);
			while (i_201_ != -1 && i_200_ < string.length()) {
				String string_202_ = string.substring(i_200_, i_201_);
				string_202_ = string_202_.trim();
				i_200_ = i_201_ + 1;
				i_201_ = string.indexOf("\n", i_200_);
				if (string_202_.startsWith("fs(-"))
					string_202_ = new StringBuilder().append("fs(")
							.append(string_202_.substring(4, string_202_.length())).append("").toString();
				else if (string_202_.startsWith("fs("))
					string_202_ = new StringBuilder().append("fs(-")
							.append(string_202_.substring(3, string_202_.length())).append("").toString();
				if (axis == 3)
					if (string_202_.startsWith("p(-"))
						string_202_ = new StringBuilder().append("p(")
								.append(string_202_.substring(3, string_202_.length())).append("").toString();
					else if (string_202_.startsWith("p("))
						string_202_ = new StringBuilder().append("p(-")
								.append(string_202_.substring(2, string_202_.length())).append("").toString();
				if (axis == 4 && string_202_.startsWith("p(")) {
					final int i_203_ = string_202_.indexOf(",", 0);
					if (i_203_ >= 0)
						if (string_202_.startsWith(",-", i_203_))
							string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_203_))
									.append(",").append(string_202_.substring(i_203_ + 2, string_202_.length()))
									.append("").toString();
						else if (string_202_.startsWith(",", i_203_))
							string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_203_))
									.append(",-").append(string_202_.substring(i_203_ + 1, string_202_.length()))
									.append("").toString();
				}
				if (axis == 5 && string_202_.startsWith("p(")) {
					int i_204_ = string_202_.indexOf(",", 0);
					i_204_ = string_202_.indexOf(",", i_204_ + 1);
					if (i_204_ >= 0)
						if (string_202_.startsWith(",-", i_204_))
							string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_204_))
									.append(",").append(string_202_.substring(i_204_ + 2, string_202_.length()))
									.append("").toString();
						else if (string_202_.startsWith(",", i_204_))
							string_202_ = new StringBuilder().append("").append(string_202_.substring(0, i_204_))
									.append(",-").append(string_202_.substring(i_204_ + 1, string_202_.length()))
									.append("").toString();
				}
				output = new StringBuilder().append(output).append("").append(string_202_).append("\n").toString();
			}
			output = new StringBuilder().append(output).append("\n// End of mirror").toString();
			text.insert(output, text.getSelectionEnd());
		}
	}

	// Display a file chooser so the user can select a file to load.
	// Then load the file into the editing area. Does nothing if
	// the user cancels the file chooser.
	private void loadFile() {
		String line;
		final File file = new File(".\\o.rad");

		try {
			// Open the file.
			final BufferedReader input = new BufferedReader(new FileReader(file));

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
		} catch (final IOException e) {
			JOptionPane.showMessageDialog(null, "Can't load file " + e.getMessage());
		}
	}
}

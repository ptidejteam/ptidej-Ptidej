package com.jmonkey.office.lexi.support;


//Java API Imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
// jMonkey imports
// import com.jmonkey.office.Lexi;

public final class FontChooser extends JDialog implements ActionListener, ListSelectionListener, ItemListener {
	JList fontList = null;
	JTextArea prevArea = null;
	JComboBox sizeBox = null;

	JCheckBox boldBox = null;
	JCheckBox italicBox = null;
	JCheckBox bItalic = null;
	JCheckBox plainBox = null;

	//Lexi _OWNER = null;

	// Add this to store the font
	// while the window is destroyed.
	private Font _PICKED_FONT = null;

	//this.addActionListener(this);.


	public FontChooser(JFrame owner) {
		this(owner, "Pick your Font...", true);
	}
	/**
	* Display the FontChooser and return the slected font.
	* @param owner javax.swing.JFrame the owner frame.
	* @return java.awt.Font The selected font, or null.
	*/


	public FontChooser(JFrame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.setSize(700, 500);
		this.init(owner);
	}
	/**
	* Handle the actions associated with the 'ok' and the 'cancel' buttons
	*/

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("can-button")) {
			this.dispose();
		}

		if (e.getActionCommand().equals("ok-button")) {
			this.doExit();
		}
	}
	public static final Font display(JFrame owner) {
		Font myfont = null;
		FontChooser fc = new FontChooser(owner, "Font Chooser", true);
		fc.setVisible(true);
		myfont = fc.getSelectedFont();
		return myfont;
	}
	public void doExit() {
		if (fontList.getSelectedValue() == null) {
			if (boldBox.isSelected() == true) {
				//_OWNER.setBold();
				_PICKED_FONT = new Font("TimesRoman", Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			if (italicBox.isSelected() == true) {
				//_OWNER.setItalic();
				_PICKED_FONT = new Font("TimesRoman", Font.ITALIC, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			if (plainBox.isSelected() == true) {
				//_OWNER.setRegular();
				_PICKED_FONT = new Font("TimesRoman", Font.PLAIN, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			//_OWNER.setNewFont("TimesRoman", Integer.parseInt(sizeBox.getSelectedItem().toString()));
			//_PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));

		} else {
			if (boldBox.isSelected() == true) {
				//_OWNER.setBold();
				_PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			if (italicBox.isSelected() == true) {
				//_OWNER.setItalic();
				_PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.ITALIC, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			if (plainBox.isSelected() == true) {
				//_OWNER.setRegular();
				_PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.PLAIN, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			//_OWNER.setNewFont(fontList.getSelectedValue().toString(), Integer.parseInt(sizeBox.getSelectedItem().toString()));
			//_PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));

		}
		this.dispose();
	}
	public final Font getSelectedFont() {
		return _PICKED_FONT;
	}
	/**
	* Sets us up with the panels needed to create this font chooser
	*/
	private void init(Component c) {
		JPanel main = new JPanel();
		JPanel buttonPanes = new JPanel();
		JPanel listPanes = new JPanel();
		JPanel fontPanes = new JPanel();
		JPanel optionPanes = new JPanel();
		JPanel previewPanes = new JPanel();
		main.setLayout(new BorderLayout());
		buttonPanes.setLayout(new FlowLayout());
		fontPanes.setLayout(new BorderLayout());
		optionPanes.setLayout(new GridLayout(3, 2));

		this.addWindowListener(new java.awt.event.WindowAdapter() {
					   			public void windowClosing(java.awt.event.WindowEvent e) {
					   				doExit();
					   			}
					   		});

		String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i = 0; i < families.length; i++) {
			if(families[i].indexOf(".") == -1) {
				// we have to test to a "." so
				// we don't get duplicates, there
				// appears to be a bug in the VM
			}

		}         // FontPane Stuff
		fontList = new JList(families);
		fontList.addListSelectionListener(this);
		JScrollPane scroller = new JScrollPane(fontList);
		listPanes.add(scroller);

		fontPanes.add(listPanes, BorderLayout.WEST);
		fontPanes.add(optionPanes, BorderLayout.EAST);
		// Options Pane
		String[] sizes = {"8", "10", "12", "14", "16", "18", "20",
				  		"24", "26", "28", "30", "36", "40", "46", "52", "58",
				  		"64", "70", "76"};

		sizeBox = new JComboBox();

		for (int k=0; k < sizes.length; k++) {
			sizeBox.addItem(sizes[k]);
		}
		sizeBox.setEditable(true);
		sizeBox.setSelectedItem("12");
		sizeBox.setSize(15, 15);
		sizeBox.addItemListener(this);

		optionPanes.add(sizeBox);
		optionPanes.add(new JSeparator());
		boldBox = new JCheckBox("Bold", false);
		boldBox.addItemListener(this);
		boldBox.setEnabled(false);
		italicBox = new JCheckBox("Italic", false);
		italicBox.addItemListener(this);
		italicBox.setEnabled(false);
		plainBox = new JCheckBox("Regular", true);
		plainBox.addItemListener(this);
		plainBox.setEnabled(true);
		//bItalic = new JCheckBox("Bold Italic", false);
		optionPanes.add(plainBox);
		optionPanes.add(italicBox);
		optionPanes.add(boldBox);
		//optionPanes.add(bItalic);



		// Button Pane
		JButton okB = new JButton("OK");
		okB.setActionCommand("ok-button");
		okB.addActionListener(this);
		JButton cancelB = new JButton("Cancel");
		cancelB.setActionCommand("can-button");
		cancelB.addActionListener(this);
		buttonPanes.add(okB);
		buttonPanes.add(cancelB);

		// Preview Pane
		prevArea = new JTextArea("The Quick Brown Fox...");
		prevArea.setSize(200, 200);
		prevArea.setEditable(false);
		previewPanes.add(prevArea);

		// Add Stuff to Main
		main.add(fontPanes, BorderLayout.NORTH);
		main.add(previewPanes, BorderLayout.CENTER);
		main.add(buttonPanes, BorderLayout.SOUTH);

		this.getContentPane().add(main);
		this.pack();
		// set the position of the dialog.
		this.setLocationRelativeTo(c);
	}
	/**
	* Handles the list of fonts and the changes
	*/
	public void itemStateChanged(ItemEvent iEv) {
		if (iEv.getItem() == plainBox && iEv.getStateChange() == ItemEvent.SELECTED) {
			boldBox.setEnabled(false);
			italicBox.setEnabled(false);
		}
		if (iEv.getItem() == plainBox && iEv.getStateChange() == ItemEvent.DESELECTED) {
			boldBox.setEnabled(true);
			italicBox.setEnabled(true);
		}
		if (iEv.getItem() == sizeBox) {
			Font newFont;
			if (fontList.getSelectedValue() == null) {
				newFont = new Font("TimesRoman", Font.PLAIN, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			} else {
				newFont = new Font(fontList.getSelectedValue().toString(), Font.PLAIN, Integer.parseInt(sizeBox.getSelectedItem().toString()));
			}
			prevArea.setFont(newFont);
			prevArea.repaint();
		}

	}
	/**
	* Handles the changes in the font size ComboBox
	*/

	public void valueChanged(ListSelectionEvent listEv) {

		Font newFont;
		if (fontList.getSelectedValue() == null) {
			newFont = new Font("TimesRoman", Font.PLAIN, Integer.parseInt(sizeBox.getSelectedItem().toString()));
		} else {
			newFont = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));
		}
		prevArea.setFont(newFont);
		prevArea.repaint();
	}
}

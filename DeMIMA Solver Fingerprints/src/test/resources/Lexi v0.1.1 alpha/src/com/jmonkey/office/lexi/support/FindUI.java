package com.jmonkey.office.lexi.support;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
* @version 1.0 
* @author Matthew Schmidt
* Modified EditorActionManager to work with this
* 06/09/99
*/
public class FindUI extends JFrame implements ActionListener {
	JTextField findWhat;
	JButton okB, cancelB;
	JRadioButton up, down;
	int isFirstClick = 1;
	String editText;
	JEditorPane _EDITOR = null;

	public FindUI(String text, JEditorPane edit) {
		super("Find text...");

		editText = text;
		_EDITOR = edit;

		this.getContentPane().setLayout(new BorderLayout());
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

		JPanel left = new JPanel();
		JLabel find = new JLabel("Text to find:");

		findWhat = new JTextField("", 20);

		left.add(find);
		left.add(findWhat);

		okB = new JButton("Find Next");
		okB.addActionListener(this);
		okB.setActionCommand("ok");

		cancelB = new JButton("Cancel");
		cancelB.addActionListener(this);
		cancelB.setActionCommand("cancel");

		right.add(okB);
		right.add(cancelB);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		JPanel botCenter = new JPanel();

		botCenter.setBorder(new LineBorder(Color.gray, 1));

		up = new JRadioButton("Up");
		up.addActionListener(this);
		up.setActionCommand("up");

		down = new JRadioButton("Down");
		down.addActionListener(this);
		down.setActionCommand("down");
		down.setSelected(true);

		botCenter.add(up);
		botCenter.add(down);
		bottom.add(botCenter, BorderLayout.CENTER);

		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(right, BorderLayout.EAST);
		this.getContentPane().add(bottom, BorderLayout.SOUTH);

		this.setSize(400, 120);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("ok")) {
			if (isFirstClick == 1) {
				/*	    
				StringSearch searcher = new StringSearch(findWhat.getText(), editText);
				int pos = searcher.first();
				if (pos != SearchIterator.DONE && pos = searcher.next()) {
				System.out.println("Found match at " + pos + ", length is " + searcher.getMatchLength());
				} */

				System.out.println(
					"We searched for: `"
						+ findWhat.getText()
						+ "' in\n"
						+ editText);
				System.out.println(
					"\n======\nIndex at: "
						+ editText.indexOf(findWhat.getText())
						+ "\n======");
				if (_EDITOR != null) {
					_EDITOR.setCaretPosition(
						editText.indexOf(findWhat.getText()));
					_EDITOR.requestFocus();
				}
				else {
				}
				isFirstClick = 0;
			}
			else {
				if (up.isSelected()) {
					//searcher.previous();	    
				}
				else if (down.isSelected()) {
					//searcher.next();	    
				}
			}

		}
		else if (e.getActionCommand().equals("cancel")) {
			//this.removeActionListener();
			this.dispose();
		}
		else if (e.getActionCommand().equals("up")) {
			down.setSelected(false);
		}
		else if (e.getActionCommand().equals("down")) {
			up.setSelected(false);
		}
	}
	public static void main(String[] args) {
		new FindUI("The quick brown fox jumped over the dark red fence", null);

	}
}

package com.jmonkey.office.lexi.support;

// AWT Imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jmonkey.office.lexi.support.images.Loader;

/**
* A nice dialog so the user can choose what type of file to make
* @author Matthew Schmidt
* @version 1.0 Revision 0
*/
public class NewDialog extends JFrame {
	JButton plain, rtf, html;
	JButton ok, cancel;

	public NewDialog(FileActionListener listen) {
		super("Start a new File..");
		this.setSize(300, 200);
		this.setLocation(45, 20);
		// Make the left panel
		JPanel left = new JPanel();
		left.setLayout(new FlowLayout());
		left.setBackground(Color.black);
		// Make the buttons
		plain = new JButton(new ImageIcon(Loader.load("new_document16.gif")));
		rtf = new JButton(new ImageIcon(Loader.load("new_document16.gif")));
		html = new JButton(new ImageIcon(Loader.load("new_document16.gif")));
		// Add the buttons to the panel
		left.add(plain);
		left.add(rtf);
		left.add(html);
		// Make a panel to the right.
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBackground(Color.black);
		// Make the buttons
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		// Add the buttons to the other panel
		right.add(ok);
		right.add(cancel);
		// Make our Frame show up
		this.getContentPane().setBackground(Color.black);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(right, BorderLayout.EAST);
		this.setVisible(true);
	}
}

package com.jmonkey.office.lexi.support;


// Java AWT API
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Splash extends JWindow {
    JLabel _VERSION_DATE = new JLabel((new Date()).toString());
    JLabel _VERSION = new JLabel("Version: 0.0.0");
    JLabel _AUTHOR = new JLabel("Author: Authors Name");
    JLabel _COPYRIGHT = new JLabel("GPL");
    JLabel _TITAL = new JLabel("Application Title");
    JLabel _DESCRIPTION = new JLabel("Description...");
    ImageIcon _IMAGE;
    JPanel image;
    
    int width;
    int height;

    public Splash(int w, int h) {
	super();
	width=w;
	height=h;
	this.init();
    }
    public final JLabel getAuthor() {
	return _AUTHOR;
    }
    public final JLabel getCopyright() {
	return _COPYRIGHT;
    }
    public final JLabel getDescription() {
	return _DESCRIPTION;
    }
    public final ImageIcon getImage() {
	return _IMAGE;
    }
    public final JLabel getTital() {
	return _TITAL;
    }
    public final JLabel getVersion() {
	return _VERSION;
    }
    public final JLabel getVersionDate() {
	return _VERSION_DATE;
    }
    public final void hideSplash() {
	try {
	    // Close and dispose Window in AWT thread
	    SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			if(isVisible()) {
			    setVisible(false);
			    dispose();
			}
		    }
		});
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }
    private void init() {
	// Set the Look & Feel for the app.
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	}

	// Setup label params.
	_VERSION_DATE.setHorizontalAlignment(SwingConstants.RIGHT);
	_VERSION_DATE.setVerticalAlignment(SwingConstants.TOP);
	_VERSION_DATE.setFont(new Font("Dialog", Font.ITALIC, 10));
	_VERSION_DATE.setOpaque(false);

	_VERSION.setHorizontalAlignment(SwingConstants.RIGHT);
	_VERSION.setVerticalAlignment(SwingConstants.BOTTOM);
	_VERSION.setFont(new Font("Dialog", Font.BOLD, 20));
	_VERSION.setOpaque(false);

	_AUTHOR.setHorizontalAlignment(SwingConstants.RIGHT);
	_AUTHOR.setVerticalAlignment(SwingConstants.CENTER);
	_AUTHOR.setFont(new Font("Dialog", Font.ITALIC, 12));
	_AUTHOR.setBorder(BorderFactory.createEmptyBorder(1, 4, 1, 4));
	_AUTHOR.setOpaque(false);

	_COPYRIGHT.setHorizontalAlignment(SwingConstants.LEFT);
	_COPYRIGHT.setVerticalAlignment(SwingConstants.CENTER);
	_COPYRIGHT.setFont(new Font("Dialog", Font.ITALIC, 10));
	_COPYRIGHT.setBorder(BorderFactory.createEmptyBorder(1, 4, 1, 4));
	_COPYRIGHT.setOpaque(false);

	_TITAL.setHorizontalAlignment(SwingConstants.LEFT);
	_TITAL.setVerticalAlignment(SwingConstants.CENTER);
	_TITAL.setFont(new Font("Dialog", Font.BOLD, 30));
	_TITAL.setOpaque(false);

	_DESCRIPTION.setHorizontalAlignment(SwingConstants.LEFT);
	_DESCRIPTION.setVerticalAlignment(SwingConstants.TOP);
	_DESCRIPTION.setFont(new Font("Dialog", Font.PLAIN, 10));
	_DESCRIPTION.setOpaque(false);

	JPanel content = new JPanel();
	content.setLayout(new BorderLayout());
	content.setBorder(BorderFactory.createRaisedBevelBorder());

	JPanel spacer = new JPanel();
	spacer.setLayout(new BorderLayout());
	spacer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	JPanel title = new JPanel();
	title.setLayout(new BorderLayout());

	JPanel version = new JPanel();
	version.setLayout(new GridLayout(2, 1));

	image=new JPanel();

	title.add(_TITAL, BorderLayout.NORTH);
	title.add(_DESCRIPTION, BorderLayout.CENTER);

	version.add(_VERSION);
	version.add(_VERSION_DATE);

	spacer.add(version, BorderLayout.SOUTH);
	spacer.add(title, BorderLayout.NORTH);
	spacer.add(image,BorderLayout.CENTER);

	content.add(_AUTHOR, BorderLayout.NORTH);
	content.add(_COPYRIGHT, BorderLayout.SOUTH);
	content.add(spacer, BorderLayout.CENTER);


	//is.setOpaque(false);
	this.setContentPane(content);
	this.setSize(width, height);

	Dimension WindowSize = this.getSize();
	Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds((ScreenSize.width - WindowSize.width) / 2, (ScreenSize.height - WindowSize.height) / 2, WindowSize.width, WindowSize.height);
    }

    public static void main(String[] args) {
	Splash s = new Splash(400,200);
	s.showSplash();
    }
    public final void setImage(Image image) { 
	_IMAGE=new ImageIcon(image.getScaledInstance(width,(int)(height/(200/75)), Image.SCALE_SMOOTH)); 
    }

    public void paint(Graphics g) {
	super.paint(g);
	if(_IMAGE!= null) {
	    _IMAGE.paintIcon(image, image.getGraphics(), 0, 0);
	}
    }

    public final void showSplash() {
	if(!this.isVisible()) {
	    this.setVisible(true);
	}
    }
}

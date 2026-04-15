package com.jmonkey.office.lexi.support;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;


public class ButtonBorder extends CompoundBorder {

	static ButtonBevelBorder bbb = new ButtonBevelBorder();
	static Border ebb = BorderFactory.createEmptyBorder(2,2,2,2);

	static class ButtonBevelBorder extends BevelBorder {

		public ButtonBevelBorder() {
			super( RAISED );
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			bevelType = RAISED;
			if (c instanceof AbstractButton) {
				ButtonModel model = ((AbstractButton)c).getModel();
				boolean pressed = model.isPressed();
				bevelType = (pressed ? LOWERED : RAISED);
			}
			super.paintBorder(c, g, x, y, width, height);
		}
	}

	public ButtonBorder() {
		super( bbb, ebb );
	}
	public static void main(String args[]) {
		JFrame frame = new JFrame("Bevel Button Border");
		Border border = new ButtonBorder();
		JButton helloButton = new JButton ("Hello");
		helloButton.setBorder(border);
		JButton worldButton = new JButton ("World");
		Container contentPane = frame.getContentPane();
		contentPane.add(helloButton, BorderLayout.NORTH);
		contentPane.add(worldButton, BorderLayout.SOUTH);
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
}

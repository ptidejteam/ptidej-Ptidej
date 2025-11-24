package com.jmonkey.office.lexi.support;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ImageCellRenderer extends JLabel implements ListCellRenderer {
 
	 public ImageCellRenderer() {
		 setOpaque(true);
	 } 
public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	setText(value.toString());
	setBackground(isSelected ? Color.red : Color.white);
	setForeground(isSelected ? Color.white : Color.black);
	return this;
}
}  

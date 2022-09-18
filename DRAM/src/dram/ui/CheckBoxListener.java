/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package dram.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JCheckBox;

/**
 * @author Samah
 */
public class CheckBoxListener implements ActionListener, ItemListener {
	static Vector vectorChoices = new Vector();
	public void itemStateChanged(ItemEvent evt) {
		
		JCheckBox source = (JCheckBox) evt.getSource();
		if (source.isSelected()) {
			vectorChoices.add(source.getText());
			System.out.println("selected " + source.getText());

		} else {
			vectorChoices.remove(source.getText());
			System.out.println("deselected " + source.getText());
		}
		for (int i = 0; i < vectorChoices.size(); i++) {
			System.out.print(" itemStateChanged   " + vectorChoices.elementAt(i));
		}
		System.out.println("\n");
	}

	public void actionPerformed(ActionEvent e) {
//		for (int i = 0; i < vectorChoices.size(); i++) {
//			System.out.println("eeee  " + vectorChoices.elementAt(i));
//		}

	}
	public Vector getvectorChoices() {
		return vectorChoices;
	}
	public void setvectorChoices(Vector v) {
		for (int i = 0; i < v.size(); i++) {
			System.out.println("eeee  " + v.elementAt(i));
		}
		vectorChoices = v;
	}
	public Vector removevectorChoices() {
		vectorChoices.removeAllElements();
		return vectorChoices;
	}
	
}

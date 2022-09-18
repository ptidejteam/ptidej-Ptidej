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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Samah
 */
public class DialogGrouping {
	CheckBoxListener checkBoxListener = new CheckBoxListener();

	public static void main(String[] args) {
		//DialogGrouping pp = new DialogGrouping();
	}

	public DialogGrouping(
		Vector vectorVertices,
		JFrame frame,
		final Vector vectorClassName) {
		for (int i = 0; i < vectorClassName.size(); i++) {
			System.out.println(" btok classname  "
					+ vectorClassName.elementAt(i));
		}
		final JDialog dialog = new JDialog(frame);
		JScrollPane scrollPane;
		//dialog.setSize(600, 400);
		dialog.setBounds(200, 200, 600, 400);

		JPanel topPane1 = new JPanel();
		JPanel topPane = new JPanel();

		int valLigneGridLayout = vectorVertices.size() / 2;
		topPane.setLayout(new GridLayout(valLigneGridLayout, 2));

		for (int i = 0; i < vectorVertices.size(); i++) {
			//System.out.print("vecteur " + vector.elementAt(i));
			JCheckBox jCheckBox;
			String label = (String) (vectorVertices.elementAt(i)).toString();
			if (vectorClassName.contains(label)) {
				jCheckBox = new JCheckBox(label, true);
			}
			else {
				jCheckBox = new JCheckBox(label);
			}
			//jCheckBox.setPreferredSize(new Dimension(300, 40));
			jCheckBox.addItemListener(this.checkBoxListener);
			topPane.add(jCheckBox);
		}
		//		scrollPane= new JScrollPane(topPane,
		//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane = new JScrollPane(topPane);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		topPane1.add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton btok = new JButton("ok");
		btok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//				for (int i = 0;
				//					i < checkBoxListener.getvectorChoices().size();
				//					i++) {
				//					System.out.println(
				//						" btok "
				//							+ checkBoxListener.getvectorChoices().elementAt(i));
				//				}

				dialog.setVisible(false);
			}

		});

		bottomPane.add(btok);

		JButton btCancel = new JButton("cancel");
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				for (int i = 0; i < vectorClassName.size(); i++) {
					System.out.println(" btcancel classname  "
							+ vectorClassName.elementAt(i));
				}
				DialogGrouping.this.checkBoxListener
					.setvectorChoices(vectorClassName);
				dialog.setVisible(false);
			}

		});
		bottomPane.add(btCancel);

		dialog.setTitle("Grouping Class");
		dialog.setModal(true);
		dialog.setResizable(false);
		dialog.getContentPane().add(topPane1, BorderLayout.NORTH);
		dialog.getContentPane().add(bottomPane, BorderLayout.SOUTH);

		dialog.pack();
		dialog.setVisible(true);

	}

}

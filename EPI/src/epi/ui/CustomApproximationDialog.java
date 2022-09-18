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
package epi.ui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import epi.solver.Approximation;

/**
 * @author OlivierK
 *
 */
public class CustomApproximationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8984135596111164615L;
	public static void main(final String[] args) {
		final CustomApproximationDialog inst = new CustomApproximationDialog();
		inst.setVisible(true);
	}
	private Window owner;
	private final Approximation customApproximation;
	private JList jList1;
	private JButton finishButton;
	private JLabel jLabel1;
	private JComboBox jComboBox1;
	private JButton removeButton;
	private JButton addButton;
	private JList jList2;
	private JScrollPane list1ScrollPane;
	private JScrollPane list2ScrollPane;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel jLabel2;

	private final DefaultListModel approximationListModel;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public CustomApproximationDialog() {
		super();
		this.customApproximation = new Approximation();
		this.approximationListModel = new DefaultListModel();
		this.setTitle("Edit - Custom approximation");
		this.initGUI();
	}

	public CustomApproximationDialog(final Dialog owner, final boolean modal) {
		super(owner, "Edit - Custom approximation", modal);
		this.customApproximation = new Approximation();
		this.approximationListModel = new DefaultListModel();
		this.owner = owner;
		this.initGUI();
	}

	public CustomApproximationDialog(final Frame owner, final boolean modal) {
		super(owner, "Edit - Custom approximation", modal);
		this.customApproximation = new Approximation();
		this.approximationListModel = new DefaultListModel();
		this.owner = owner;
		this.initGUI();
	}

	public Approximation getApproximation() {
		return this.customApproximation;
	}

	private void initGUI() {
		try {
			this.setLocationRelativeTo(this.owner);
			this.getContentPane().setLayout(null);
			this.setResizable(false);
			{
				final DefaultListModel existingRelList = new DefaultListModel();
				existingRelList.addElement("containerComposition");
				existingRelList.addElement("containerAggregation");
				existingRelList.addElement("composition");
				existingRelList.addElement("aggregation");
				existingRelList.addElement("association");
				existingRelList.addElement("useRelationship");
				existingRelList.addElement("creation");
				existingRelList.addElement("inheritance");
				existingRelList.addElement("pathInheritance");
				existingRelList.addElement("null");

				this.jList1 = new JList(existingRelList);
				this.jList1
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				this.list1ScrollPane = new JScrollPane(this.jList1);
				this.list1ScrollPane.setBounds(17, 90, 131, 194);
				this.getContentPane().add(this.list1ScrollPane);
			}
			{
				this.jList2 = new JList(this.approximationListModel);
				this.jList2
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				this.jList2.setPreferredSize(new java.awt.Dimension(129, 192));
				this.list2ScrollPane = new JScrollPane(this.jList2);
				this.list2ScrollPane.setBounds(238, 90, 131, 194);
				this.getContentPane().add(this.list2ScrollPane);
			}
			{
				this.jLabel1 = new JLabel();
				this.getContentPane().add(this.jLabel1);
				this.jLabel1.setText("Available relationships");
				this.jLabel1.setBounds(17, 60, 134, 30);
				this.jLabel1.setFont(new java.awt.Font("sansserif", 0, 11));
			}
			{
				this.finishButton = new JButton();
				this.getContentPane().add(this.finishButton);
				this.finishButton.setText("Finish");
				this.finishButton.setBounds(162, 280, 61, 30);
				this.finishButton.addActionListener(new ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						CustomApproximationDialog.this.setVisible(false);
					}
				});
			}
			{
				this.addButton = new JButton();
				this.getContentPane().add(this.addButton);
				this.addButton.setBounds(165, 167, 55, 30);
				this.addButton.setText(">>");
				this.addButton.setSize(54, 30);
				this.addButton.setFont(new java.awt.Font("sansserif", 1, 12));
				this.addButton.addActionListener(new ActionListener() {

					public void actionPerformed(final ActionEvent e) {
						int index =
							CustomApproximationDialog.this.jList1
								.getSelectedIndex(); //get selected index

						if (index == -1) {
							return;
						}
						if (CustomApproximationDialog.this.approximationListModel
							.contains(CustomApproximationDialog.this.jList1
								.getSelectedValue())) {
							return;
						}
						if (CustomApproximationDialog.this.jComboBox1
							.getSelectedItem()
							.toString() == "") {
							return;
						}

						CustomApproximationDialog.this.approximationListModel
							.insertElementAt(
								CustomApproximationDialog.this.jList1
									.getSelectedValue(),
								CustomApproximationDialog.this.approximationListModel
									.size());

						//ensure the item is visible.
						CustomApproximationDialog.this.jList2
							.ensureIndexIsVisible(CustomApproximationDialog.this.approximationListModel
								.size());

						//						List relationshipList = new Vector();
						//						for (Enumeration enum = approximationListModel.elements() ; enum.hasMoreElements() ;) {
						//        					relationshipList.add(enum.nextElement());
						//     					}
						//						customApproximation.setApproximationList(jComboBox1.getSelectedItem().toString(), relationshipList);
						CustomApproximationDialog.this.customApproximation
							.addElement(
								CustomApproximationDialog.this.jComboBox1
									.getSelectedItem()
									.toString(),
								CustomApproximationDialog.this.jList1
									.getSelectedValue()
									.toString());

						if (CustomApproximationDialog.this.approximationListModel
							.getSize() == 0) {
							//removeButton.setEnabled(false);				
						}
						else {
							if (index == CustomApproximationDialog.this.approximationListModel
								.getSize()) {
								index--;
							}
							CustomApproximationDialog.this.jList2
								.setSelectedIndex(index);
							CustomApproximationDialog.this.jList2
								.ensureIndexIsVisible(index);
						}
					}
				});
			}
			{
				this.removeButton = new JButton();
				this.getContentPane().add(this.removeButton);
				this.removeButton.setText("<<");
				this.removeButton.setBounds(165, 205, 55, 30);
				this.removeButton
					.setFont(new java.awt.Font("sansserif", 1, 12));
				this.removeButton.addActionListener(new ActionListener() {

					public void actionPerformed(final ActionEvent e) {

						int index =
							CustomApproximationDialog.this.jList2
								.getSelectedIndex();

						final List relationshipList = new Vector();
						// Modification the 26 October 2011 by Sébastien Colladon (INF6306) for compatibility with java 1.6
						for (final Enumeration anEnumeration =
							CustomApproximationDialog.this.approximationListModel
								.elements(); anEnumeration.hasMoreElements();) {
							relationshipList.add(anEnumeration.nextElement());
						}
						relationshipList
							.remove(CustomApproximationDialog.this.jList2
								.getSelectedValue());
						CustomApproximationDialog.this.customApproximation
							.setApproximationList(
								CustomApproximationDialog.this.jComboBox1
									.getSelectedItem()
									.toString(),
								relationshipList);

						CustomApproximationDialog.this.approximationListModel
							.remove(index);

						if (CustomApproximationDialog.this.approximationListModel
							.getSize() == 0) {
							//removeButton.setEnabled(false);				
						}
						else {
							if (index == CustomApproximationDialog.this.approximationListModel
								.getSize()) {
								index--;
							}
							CustomApproximationDialog.this.jList2
								.setSelectedIndex(index);
							CustomApproximationDialog.this.jList2
								.ensureIndexIsVisible(index);
						}
					}
				});
			}
			{
				final Vector relationshipVector = new Vector();
				relationshipVector.add("");
				for (final Enumeration e =
					this.customApproximation.getApproximationList().keys(); e
					.hasMoreElements();) {
					relationshipVector.add(e.nextElement());
				}

				final ComboBoxModel jComboBox1Model =
					new DefaultComboBoxModel(relationshipVector);
				this.jComboBox1 = new JComboBox();
				this.getContentPane().add(this.jComboBox1);
				this.jComboBox1.setModel(jComboBox1Model);
				this.jComboBox1.setBounds(107, 15, 160, 26);
				this.jComboBox1.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent e) {
						CustomApproximationDialog.this.approximationListModel
							.clear();
						if (CustomApproximationDialog.this.jComboBox1
							.getSelectedItem() != "") {
							final List relation =
								CustomApproximationDialog.this.customApproximation
									.getApproximationList(CustomApproximationDialog.this.jComboBox1
										.getSelectedItem()
										.toString());
							final Iterator relationIter = relation.iterator();
							while (relationIter.hasNext()) {
								CustomApproximationDialog.this.approximationListModel
									.addElement(relationIter.next());
							}
						}
						else {

						}
					}
				});
			}
			{
				this.jLabel2 = new JLabel();
				this.getContentPane().add(this.jLabel2);
				this.jLabel2.setText("Relationship type");
				this.jLabel2.setBounds(17, 13, 105, 30);
			}
			{
				this.jLabel3 = new JLabel();
				this.getContentPane().add(this.jLabel3);
				this.jLabel3.setText("Selected relationships for");
				this.jLabel3.setBounds(238, 44, 202, 30);
			}
			{
				this.jLabel4 = new JLabel();
				this.getContentPane().add(this.jLabel4);
				this.jLabel4.setText("approximation");
				this.jLabel4.setBounds(238, 60, 131, 30);
			}

			this.pack();
			this.setSize(394, 351);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

}

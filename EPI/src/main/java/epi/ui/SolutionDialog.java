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

import java.awt.Frame;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import epi.solver.Logger;

/**
 * @author OlivierK
 *
 */
public class SolutionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5615395582401233848L;
	public static void main(final String[] args) {
		final SolutionDialog inst = new SolutionDialog();
		inst.setVisible(true);
	}
	private Window owner;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JPanel jPanel1;
	private JTextField jTextField6;
	private JLabel jLabel8;
	private JPanel jPanel2;
	private JTextField jTextField7;
	private JLabel jLabel7;
	private JTextField jTextField5;
	private JLabel jLabel5;
	private JTextField jTextField3;
	private JTextField jTextField2;

	private JTextField jTextField1;
	private String solverType = "";
	private String stringConstructionTime = "";
	private String identificationTime = "";
	private int numberOfEntities = 0;
	private int numberOfSolutionsWithGhosts = 0;

	private int numberOfSolutionsWithoutGhost = 0;

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

	//	public SolutionDialog(final Dialog owner, final boolean modal) {
	//		super(owner, "Summary", modal);
	//		this.initGUI();
	//	}

	public SolutionDialog() {
		super();
		this.setTitle("Summary");
		this.initGUI();
	}

	public SolutionDialog(final Frame owner, final boolean modal) {
		super(owner, "Summary", modal);
		this.initGUI();
	}

	private void initGUI() {
		try {
			this.setLocationRelativeTo(this.owner);
			this.getContentPane().setLayout(null);
			this.setResizable(false);
			{
				this.jLabel1 = new JLabel();
				this.getContentPane().add(this.jLabel1);
				this.jLabel1.setText("Solver Type:");
				this.jLabel1.setBounds(15, 13, 63, 30);
			}
			{
				this.jTextField1 = new JTextField();
				this.getContentPane().add(this.jTextField1);
				this.jTextField1.setBounds(81, 13, 266, 30);
				this.jTextField1.setEditable(false);
			}
			{
				this.jLabel2 = new JLabel();
				this.getContentPane().add(this.jLabel2);
				this.jLabel2.setText("Number of entities:");
				this.jLabel2.setBounds(15, 60, 91, 30);
			}
			{
				this.jTextField2 = new JTextField();
				this.getContentPane().add(this.jTextField2);
				this.jTextField2.setBounds(111, 61, 69, 30);
				this.jTextField2.setEditable(false);
			}
			{
				this.jTextField3 = new JTextField();
				this.getContentPane().add(this.jTextField3);
				this.jTextField3.setBounds(211, 127, 69, 30);
				this.jTextField3.setEditable(false);
			}
			{
				this.jLabel4 = new JLabel();
				this.getContentPane().add(this.jLabel4);
				this.jLabel4
					.setText("String representation construction time:");
				this.jLabel4.setBounds(15, 125, 189, 30);
			}
			{
				this.jLabel5 = new JLabel();
				this.getContentPane().add(this.jLabel5);
				this.jLabel5.setText("Design pattern identification time:");
				this.jLabel5.setBounds(15, 163, 159, 30);
			}
			{
				this.jTextField5 = new JTextField();
				this.getContentPane().add(this.jTextField5);
				this.jTextField5.setBounds(211, 166, 69, 30);
				this.jTextField5.setEditable(false);
			}
			{
				this.jLabel7 = new JLabel();
				this.getContentPane().add(this.jLabel7);
				this.jLabel7.setText("Number of solutions (without ghost):");
				this.jLabel7.setBounds(15, 271, 188, 30);
			}
			{
				this.jLabel8 = new JLabel();
				this.getContentPane().add(this.jLabel8);
				this.jLabel8.setText("Number of solutions (with ghost):");
				this.jLabel8.setBounds(15, 236, 170, 30);
			}
			{
				this.jTextField6 = new JTextField();
				this.getContentPane().add(this.jTextField6);
				this.jTextField6.setBounds(211, 233, 69, 30);
				this.jTextField6.setEditable(false);
			}
			{
				this.jTextField7 = new JTextField();
				this.getContentPane().add(this.jTextField7);
				this.jTextField7.setBounds(211, 275, 69, 30);
				this.jTextField7.setEditable(false);
			}
			{
				this.jPanel1 = new JPanel();
				this.getContentPane().add(this.jPanel1);
				this.jPanel1.setBounds(1, 103, 353, 106);
				this.jPanel1.setLayout(null);
				this.jPanel1.setBorder(BorderFactory.createTitledBorder(
					new LineBorder(new java.awt.Color(0, 0, 0), 1, false),
					"Computation times",
					TitledBorder.LEADING,
					TitledBorder.TOP,
					new java.awt.Font("sansserif", 0, 12)));
			}
			{
				this.jPanel2 = new JPanel();
				this.getContentPane().add(this.jPanel2);
				this.jPanel2.setBounds(1, 211, 354, 106);
				this.jPanel2.setBorder(BorderFactory.createTitledBorder(
					new LineBorder(new java.awt.Color(0, 0, 0), 1, false),
					"Solutions",
					TitledBorder.LEADING,
					TitledBorder.TOP,
					new java.awt.Font("sansserif", 0, 12)));
			}
			this.pack();
			this.setSize(363, 355);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void setData(final Logger logger) {
		this.numberOfSolutionsWithoutGhost =
			logger.getNumberOfSolutionsWithoutGhost();
		this.numberOfSolutionsWithGhosts =
			logger.getNumberOfSolutionsWithGhosts();
		this.identificationTime = logger.getIdentificationTime();
		this.stringConstructionTime = logger.getStringConstructionTime();
		this.numberOfEntities = logger.getNumberOfEntities();
		this.solverType = logger.getSolverTypeString();
	}
	public void setIdentificationTime(final String time) {
		this.identificationTime = time;
	}
	public void setNumberOfEntities(final int noe) {
		this.numberOfEntities = noe;
	}
	public void setNumberOfSolutionsWithGhosts(final int nb) {
		this.numberOfSolutionsWithGhosts = nb;
	}
	public void setNumberOfSolutionsWithoutGhost(final int nb) {
		this.numberOfSolutionsWithoutGhost = nb;
	}
	public void setSolverType(final String solverType) {
		this.solverType = solverType;
	}

	public void setStringConstructionTime(final String time) {
		this.stringConstructionTime = time;
	}

	public void show() {
		this.updateFields();
		super.setVisible(true);
	}

	public void updateFields() {
		this.jTextField2.setText("" + this.numberOfEntities);
		this.jTextField1.setText("" + this.solverType);
		this.jTextField3.setText("" + this.stringConstructionTime);
		this.jTextField7.setText("" + this.numberOfSolutionsWithoutGhost);
		this.jTextField5.setText("" + this.identificationTime);
		this.jTextField6.setText("" + this.numberOfSolutionsWithGhosts);

	}
}

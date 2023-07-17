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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import util.io.ProxyConsole;
import epi.solver.Approximation;
import epi.solver.BitVectorSolver;
import epi.solver.EPISolver;
import epi.solver.Logger;
import epi.solver.NFASolver;
import epi.solver.OptimisedBitVectorSolver;
import epi.solver.Solution;
import epi.solver.StringBuilder;
import epi.solver.problem.Problem;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
/**
 * @author OlivierK
 *
 */
public class EPIDialog extends JDialog {
	private static final long serialVersionUID = 7715905495828370171L;
	// This method returns the selected radio button in a button group
	public static JRadioButton getSelection(final ButtonGroup group) {
		for (final Enumeration e = group.getElements(); e.hasMoreElements();) {
			final JRadioButton b = (JRadioButton) e.nextElement();
			if (b.getModel() == group.getSelection()) {
				return b;
			}
		}
		return null;
	}
	public static void main(final String[] args) {
		//	final ICodeLevelModel codeLevelModel =
		//		Factory.getInstance().createCodeLevelModel("Model");
		//	EPIDialog inst = new EPIDialog(null, false, codeLevelModel, );
		//	inst.setVisible(true);
	}
	private final Frame owner;
	private JPanel approximationPanel;
	private JRadioButton defaultApproxRadioButton;
	private JRadioButton noApproximationRadioButton;
	private JRadioButton customApproxRadioButton;
	private JButton solveButton;
	private JRadioButton noMetricsButton;
	private JRadioButton yesMetricsButton;
	private JPanel solvePanel;
	private JButton approxEditButton;
	private JComboBox patternComboBox;
	private JTextField patternString;
	private JPanel patternPanel;
	private JRadioButton solver3RadioButton;
	private JRadioButton solver2RadioButton;
	private JRadioButton solver1RadioButton;
	private JPanel solverPanel;
	private JPanel metricsPanel;
	private ButtonGroup solverGroup;
	private ButtonGroup metricsGroup;
	private ButtonGroup approxGroup;
	private ComboBoxModel patternComboBoxModel;
	private final IIdiomLevelModel model;
	private CustomApproximationDialog customApproximation;
	private boolean solved = false;
	private Solution[] solutions;

	private Logger logger;

	private final String progString;

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

	public EPIDialog(
		final Frame owner,
		final boolean modal,
		final IIdiomLevelModel model,
		final String prof) {

		super(owner, "Efficient Pattern Identification", modal);
		this.model = model;
		this.owner = owner;
		this.initGUI();
		this.progString = prof;
	}

	// This method compute the variables domains if domains reduction is selected
	public Hashtable computeDomains(final String patternName) {
		Hashtable domains = new Hashtable();
		Problem result = null;
		try {
			final Class patternProblemClass =
				Class.forName("epi.solver.problem." + patternName);
			final Class[] types = { ICodeLevelModel.class };
			final java.lang.reflect.Constructor constructor =
				patternProblemClass.getConstructor(types);
			final Object[] params = { this.model };
			result = (Problem) constructor.newInstance(params);
			domains = result.getDomains();
		}
		catch (final Exception ex) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("Cannot create Problem using name: " + patternName);
			ex.printStackTrace();
		}
		return domains;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public Solution[] getSolutions() {
		return this.solutions;
	}
	private void initGUI() {
		try {
			this.setLocationRelativeTo(this.owner);
			this.getContentPane().setLayout(new BorderLayout());
			this.setResizable(false);

			// PATTERN PANEL
			{
				this.patternPanel = new JPanel();
				this.getContentPane().add(this.patternPanel);
				this.patternPanel.setPreferredSize(new java.awt.Dimension(
					597,
					109));
				this.patternPanel.setLayout(null);
				this.patternPanel.setBorder(BorderFactory.createTitledBorder(
					null,
					"Design Pattern",
					TitledBorder.LEADING,
					TitledBorder.TOP,
					new java.awt.Font("sansserif", 1, 14)));
				this.patternPanel.setBounds(1, 155, 596, 109);
				{
					this.patternString = new JTextField();
					this.patternPanel.add(this.patternString);
					this.patternString.setBounds(10, 64, 576, 34);
					this.patternString.setSelectedTextColor(new java.awt.Color(
						0,
						0,
						0));
					this.patternString.setFont(new java.awt.Font(
						"sansserif",
						0,
						12));
				}
				{
					this.patternComboBoxModel =
						new DefaultComboBoxModel(new String[] { "", "Adapter",
								"Composite", "AbstractFactory", "Facade",
								"Visitor", "Observer" });
					this.patternComboBox = new JComboBox();
					this.patternComboBox
						.addActionListener(new ActionListener() {
							public void actionPerformed(final ActionEvent e) {
								if (EPIDialog.this.patternComboBoxModel
									.getSelectedItem() != "") {
									EPIDialog.this.patternString.setText(StringBuilder
										.buildPatternString(EPIDialog.this.patternComboBoxModel
											.getSelectedItem()
											.toString()));
									EPIDialog.this.solveButton.setEnabled(true);
								}
								else {
									EPIDialog.this.patternString.setText("");
									EPIDialog.this.solveButton
										.setEnabled(false);
								}
							}
						});
					this.patternPanel.add(this.patternComboBox);
					this.patternComboBox.setModel(this.patternComboBoxModel);
					this.patternComboBox.setBounds(10, 28, 161, 26);
				}
			}

			// SOLVER PANEL
			{
				this.solverPanel = new JPanel();
				this.getContentPane().add(this.solverPanel);
				this.solverPanel.setBorder(BorderFactory.createTitledBorder(
					null,
					"Solver",
					TitledBorder.LEADING,
					TitledBorder.TOP,
					new java.awt.Font("sansserif", 1, 14)));
				this.solverPanel.setLayout(null);
				this.solverPanel.setBounds(1, 1, 340, 94);
				{
					this.solver1RadioButton = new JRadioButton();
					this.solverPanel.add(this.solver1RadioButton);
					this.solver1RadioButton
						.setText("Solver 1 - Optimised bit-vector algorithm");
					this.solver1RadioButton.setBounds(26, 20, 291, 23);
				}
				{
					this.solver2RadioButton = new JRadioButton();
					this.solverPanel.add(this.solver2RadioButton);
					this.solver2RadioButton
						.setText("Solver 2 - Bit-vector algorithm");
					this.solver2RadioButton.setBounds(26, 40, 169, 23);
				}
				{
					this.solver3RadioButton = new JRadioButton();
					this.solverPanel.add(this.solver3RadioButton);
					this.solver3RadioButton
						.setText("Solver 3 - Automaton algorithm");
					this.solver3RadioButton.setBounds(26, 60, 250, 23);
				}
				this.solverGroup = new ButtonGroup();
				this.solverGroup.add(this.solver1RadioButton);
				this.solver1RadioButton.setSelected(true);
				this.solverGroup.add(this.solver2RadioButton);
				this.solverGroup.add(this.solver3RadioButton);
			}

			// METRICS PANEL
			{
				this.metricsPanel = new JPanel();
				this.getContentPane().add(this.metricsPanel);
				this.metricsPanel.setBorder(BorderFactory.createTitledBorder(
					null,
					"Domains reduction",
					TitledBorder.LEADING,
					TitledBorder.TOP,
					new java.awt.Font("sansserif", 1, 14)));
				this.metricsPanel.setLayout(null);
				this.metricsPanel.setBounds(1, 95, 340, 60);
				{
					this.yesMetricsButton = new JRadioButton();
					this.metricsPanel.add(this.yesMetricsButton);
					this.yesMetricsButton.setText("With metrics");
					this.yesMetricsButton.setBounds(26, 20, 94, 30);
				}
				{
					this.noMetricsButton = new JRadioButton();
					this.metricsPanel.add(this.noMetricsButton);
					this.noMetricsButton.setText("No domains reduction");
					this.noMetricsButton.setBounds(175, 20, 140, 30);
				}
				this.metricsGroup = new ButtonGroup();
				this.metricsGroup.add(this.yesMetricsButton);
				this.metricsGroup.add(this.noMetricsButton);
				this.noMetricsButton.setSelected(true);
			}

			// APPROXIMATION PANEL
			{
				this.approximationPanel = new JPanel();
				this.getContentPane().add(this.approximationPanel);
				this.approximationPanel.setLayout(null);
				this.approximationPanel.setBorder(BorderFactory
					.createTitledBorder(
						null,
						"Approximation",
						TitledBorder.LEADING,
						TitledBorder.TOP,
						new java.awt.Font("sansserif", 1, 14)));
				this.approximationPanel.setBounds(341, 1, 256, 154);
				{
					this.noApproximationRadioButton = new JRadioButton();
					this.approximationPanel
						.add(this.noApproximationRadioButton);
					this.noApproximationRadioButton.setText("No Approximation");
					this.noApproximationRadioButton.setBounds(24, 28, 196, 30);
				}
				{
					this.defaultApproxRadioButton = new JRadioButton();
					this.approximationPanel.add(this.defaultApproxRadioButton);
					this.defaultApproxRadioButton
						.setText("Default Approximation");
					this.defaultApproxRadioButton.setBounds(24, 58, 193, 30);
				}
				{
					this.customApproxRadioButton = new JRadioButton();
					this.approximationPanel.add(this.customApproxRadioButton);
					this.customApproxRadioButton
						.setText("Custom Approximation");
					this.customApproxRadioButton.setBounds(24, 88, 134, 30);
				}
				{
					this.approxEditButton = new JButton();
					this.approximationPanel.add(this.approxEditButton);
					this.approxEditButton.setText("Edit");
					this.approxEditButton.setBounds(166, 90, 51, 30);
					this.approxEditButton
						.addActionListener(new ActionListener() {
							public void actionPerformed(final ActionEvent e) {
								if (EPIDialog
									.getSelection(EPIDialog.this.approxGroup) != EPIDialog.this.customApproxRadioButton) {
									EPIDialog.this.approxGroup.setSelected(
										EPIDialog.this.customApproxRadioButton
											.getModel(),
										true);
								}
								if (EPIDialog.this.customApproximation == null) {
									EPIDialog.this.customApproximation =
										new CustomApproximationDialog(
											EPIDialog.this,
											true);
								}
								EPIDialog.this.customApproximation
									.setVisible(true);
							}
						});
				}
				this.approxGroup = new ButtonGroup();
				this.approxGroup.add(this.noApproximationRadioButton);
				this.noApproximationRadioButton.setSelected(true);
				this.approxGroup.add(this.defaultApproxRadioButton);
				this.approxGroup.add(this.customApproxRadioButton);

			}
			{
				this.solveButton = new JButton();
				this.getContentPane().add(this.solveButton);
				this.solveButton.setText("Solve");
				this.solveButton.setBounds(271, 270, 69, 30);
				this.solveButton.setEnabled(false);
				this.solveButton.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent e) {
						if (EPIDialog.this.model.getNumberOfConstituents() > 0) {
							EPIDialog.this.logger =
								new Logger(EPIDialog.this.patternComboBoxModel
									.getSelectedItem()
									.toString()
										+ " for "
										+ EPIDialog.this.model.getDisplayName());
							EPIDialog.this.logger
								.setStringConstructionStartTime(System
									.currentTimeMillis());

							EPIDialog.this.logger
								.setStringConstructionEndTime(System
									.currentTimeMillis());
							Approximation approx = new Approximation();

							if (EPIDialog
								.getSelection(EPIDialog.this.approxGroup) == EPIDialog.this.defaultApproxRadioButton) {
								approx.setDefaultApproximation();
							}
							else if (EPIDialog
								.getSelection(EPIDialog.this.approxGroup) == EPIDialog.this.customApproxRadioButton) {
								approx =
									EPIDialog.this.customApproximation
										.getApproximation();
							}

							EPISolver solver;

							EPIDialog.this.logger
								.setStringMatchingStartTime(System
									.currentTimeMillis());
							Hashtable domains = null;
							if (EPIDialog
								.getSelection(EPIDialog.this.metricsGroup) == EPIDialog.this.yesMetricsButton) {
								domains =
									EPIDialog.this
										.computeDomains(EPIDialog.this.patternComboBoxModel
											.getSelectedItem()
											.toString());
							}
							if (EPIDialog
								.getSelection(EPIDialog.this.solverGroup) == EPIDialog.this.solver1RadioButton) {
								EPIDialog.this.logger
									.setSolverType(EPISolver.WITH_CONSTRAINTS);
								solver =
									new OptimisedBitVectorSolver(
										EPIDialog.this.progString,
										EPIDialog.this.patternString.getText(),
										EPIDialog.this.patternComboBoxModel
											.getSelectedItem()
											.toString(), approx, domains);
								EPIDialog.this.solutions =
									solver.computeSolutions();
							}
							else if (EPIDialog
								.getSelection(EPIDialog.this.solverGroup) == EPIDialog.this.solver2RadioButton) {
								EPIDialog.this.logger
									.setSolverType(EPISolver.WITHOUT_CONSTRAINT);
								solver =
									new BitVectorSolver(
										EPIDialog.this.progString,
										EPIDialog.this.patternString.getText(),
										EPIDialog.this.patternComboBoxModel
											.getSelectedItem()
											.toString(), approx, domains);
								EPIDialog.this.solutions =
									solver.computeSolutions();
							}
							else if (EPIDialog
								.getSelection(EPIDialog.this.solverGroup) == EPIDialog.this.solver3RadioButton) {
								EPIDialog.this.logger
									.setSolverType(EPISolver.AUTOMATON_SOLVER);
								solver =
									new NFASolver(
										EPIDialog.this.progString,
										EPIDialog.this.patternString.getText(),
										EPIDialog.this.patternComboBoxModel
											.getSelectedItem()
											.toString(), approx, domains);
								EPIDialog.this.solutions =
									solver.computeSolutions();
							}
							EPIDialog.this.logger
								.setNumberOfEntities(EPIDialog.this.model
									.getNumberOfConstituents());
							EPIDialog.this.logger
								.setStringMatchingEndTime(System
									.currentTimeMillis());
							ProxyConsole
								.getInstance()
								.debugOutput()
								.println(
									EPIDialog.this.logger
										.getStringConstructionTime());
							ProxyConsole
								.getInstance()
								.debugOutput()
								.println(
									EPIDialog.this.logger
										.getIdentificationTime());
							EPIDialog.this.logger
								.setNumberOfSolutions(EPIDialog.this.solutions.length);
							final Solution[] solWithoutGhost =
								Solution.getSolutionsWithoutGhost(
									EPIDialog.this.solutions,
									EPIDialog.this.model);
							EPIDialog.this.logger
								.setNumberOfGhostSolutions(EPIDialog.this.solutions.length
										- solWithoutGhost.length);
							EPIDialog.this.solved = true;
							EPIDialog.this.dispose();
						}
						else {
							JOptionPane.showMessageDialog(
								null,
								"The model is empty!",
								"The model is empty!",
								JOptionPane.ERROR_MESSAGE);
						}

					}
				});
			}
			{
				this.solvePanel = new JPanel();
				this.getContentPane().add(this.solvePanel);
				this.solvePanel.setBounds(2, 244, 596, 86);
			}

			this.pack();
			this.setSize(604, 332);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isSolved() {
		return this.solved;
	}
}

/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ptidej.viewer.helper.RepositoriesAccessor;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.designsmell.detection.IDesignSmellDetection;
import util.io.ProxyConsole;

class WindowMain extends JFrame {
	private static final long serialVersionUID = 4363051315547313387L;
	private static final int TEXT_FIELD_SIZE = 13;
	private static final int WARNING_DURATION = 2;
	private static final Dimension WINDOW_DIMENSION = new Dimension(800, 300);

	private final Business business;
	private JTextArea detectionSummaryResults;
	private JTextArea detectionDetailedResults;
	private JTextField infoField;
	private int messageDuration;
	private JTextArea modelInformation;
	private final MouseAdapter mouseAdapter = new MouseAdapter() {
		public void mouseEntered(final MouseEvent mouseEvent) {
			WindowMain.this.informUser(WindowMain.this
				.getHelpOf((JComponent) mouseEvent.getComponent()));
		}
		public void mouseExited(final MouseEvent mouseEvent) {
			WindowMain.this.informUser("");
		}
	};

	WindowMain() {
		this.business = new Business(this);

		this.getContentPane().setLayout(
			new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this
			.setLocation(
				(int) (Constants.SCREEN_DIMENSION.getWidth() / 2 - WindowMain.WINDOW_DIMENSION
					.getWidth() / 2),
				(int) (Constants.SCREEN_DIMENSION.getHeight() / 2 - WindowMain.WINDOW_DIMENSION
					.getHeight() / 2));
		this.setResizable(false);
		this.setSize(WindowMain.WINDOW_DIMENSION);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent windowEvent) {
				WindowMain.this.business.quit();
			}
		});

		this.setTitle(Constants.PROGRAM_NAME);

		// Yann 2003/07/05: Ugly!
		// Is there anyway to remove the declaration of
		// the infoField *outside* of this constructor,
		// not to pollute the class declaration?
		this.infoField = new JTextField();
		this.infoField.setEditable(false);

		this.createMenus();
		this.createButtons();

		this.business.about();
	}
	private void createButtons() {
		final JPanel bundleTitlePanel = new JPanel();
		bundleTitlePanel.setLayout(new BorderLayout());
		final JPanel bundlePanel = new JPanel();
		bundlePanel.setLayout(new BoxLayout(bundlePanel, BoxLayout.X_AXIS));
		final JPanel configurationTitlePanel = new JPanel();
		configurationTitlePanel.setLayout(new BorderLayout());
		final JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.X_AXIS));
		this.getContentPane().add(bundleTitlePanel);
		this.getContentPane().add(bundlePanel);
		this.getContentPane().add(configurationTitlePanel);
		this.getContentPane().add(resultsPanel);
		this.getContentPane().add(this.infoField);

		// +-------------------+
		// | Model Information |
		// +-------------------+

		final JPanel modelInformationPanel = new JPanel();
		modelInformationPanel.setLayout(new GridBagLayout());
		resultsPanel.add(modelInformationPanel);

		final JLabel bibtexFileLabel = new JLabel("Model Information");
		modelInformationPanel.add(bibtexFileLabel, new GridBagConstraints(
			0,
			0,
			1,
			1,
			1.0,
			0.0,
			GridBagConstraints.CENTER,
			GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0),
			0,
			0));

		this.modelInformation =
			new JTextArea(
				WindowMain.TEXT_FIELD_SIZE,
				WindowMain.TEXT_FIELD_SIZE);
		this.modelInformation.setEditable(false);
		this.modelInformation.addMouseListener(this.mouseAdapter);
		this.modelInformation
			.setToolTipText("Some statistics on the currently loaded model");
		final JScrollPane modelInformationScrollPane =
			new JScrollPane(this.modelInformation);
		modelInformationPanel.add(
			modelInformationScrollPane,
			new GridBagConstraints(
				0,
				1,
				1,
				1,
				1.0,
				1.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0),
				0,
				0));

		//	final JButton addBibtexFileButton = new JButton("Add");
		//	addBibtexFileButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent actionEvent) {
		//			//	Main.this.addBase();
		//		}
		//	});
		//	addBibtexFileButton.addMouseListener(this.mouseAdapter);
		//	addBibtexFileButton
		//		.setToolTipText("Add a BibTeX base to extract entries from");
		//	originPanel.add(addBibtexFileButton, new GridBagConstraints(
		//		0,
		//		2,
		//		1,
		//		1,
		//		1.0,
		//		0.0,
		//		GridBagConstraints.CENTER,
		//		GridBagConstraints.HORIZONTAL,
		//		new Insets(0, 0, 0, 0),
		//		0,
		//		0));
		//
		//	final JButton removeBibtexFileButton = new JButton("Remove");
		//	removeBibtexFileButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent actionEvent) {
		//			//	Main.this.removeBase();
		//		}
		//	});
		//	removeBibtexFileButton.addMouseListener(this.mouseAdapter);
		//	removeBibtexFileButton
		//		.setToolTipText("Remove the selected BibTeX base from the list");
		//	originPanel.add(removeBibtexFileButton, new GridBagConstraints(
		//		0,
		//		3,
		//		1,
		//		1,
		//		1.0,
		//		0.0,
		//		GridBagConstraints.CENTER,
		//		GridBagConstraints.HORIZONTAL,
		//		new Insets(0, 0, 0, 0),
		//		0,
		//		0));

		// +-------------------+
		// | Detection Results |
		// +-------------------+

		final JPanel detectionResultsPanel = new JPanel();
		detectionResultsPanel.setLayout(new BoxLayout(
			detectionResultsPanel,
			BoxLayout.Y_AXIS));
		resultsPanel.add(detectionResultsPanel);

		final JLabel detectionResultsLabel = new JLabel("Detection Results");
		detectionResultsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		detectionResultsLabel.setPreferredSize(new Dimension(500, 16));
		detectionResultsPanel.add(detectionResultsLabel);

		final JPanel detectionResultsInnerPanel = new JPanel();
		detectionResultsInnerPanel.setLayout(new BoxLayout(
			detectionResultsInnerPanel,
			BoxLayout.X_AXIS));
		detectionResultsInnerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		detectionResultsPanel.add(detectionResultsInnerPanel);

		this.detectionSummaryResults =
			new JTextArea(
				WindowMain.TEXT_FIELD_SIZE,
				WindowMain.TEXT_FIELD_SIZE);
		this.detectionSummaryResults.setEditable(false);
		this.detectionSummaryResults.addMouseListener(this.mouseAdapter);
		this.detectionSummaryResults
			.setToolTipText("Numerical results of the detection");
		final JScrollPane detectionResultsScrollPane =
			new JScrollPane(this.detectionSummaryResults);
		detectionResultsInnerPanel.add(detectionResultsScrollPane);

		this.detectionDetailedResults =
			new JTextArea(
				WindowMain.TEXT_FIELD_SIZE,
				WindowMain.TEXT_FIELD_SIZE);
		this.detectionDetailedResults.setEditable(false);
		this.detectionDetailedResults.addMouseListener(this.mouseAdapter);
		this.detectionDetailedResults
			.setToolTipText("Detailed results of the last detection");
		final JScrollPane detectionDetailedResultsScrollPane =
			new JScrollPane(this.detectionDetailedResults);
		detectionResultsInnerPanel.add(detectionDetailedResultsScrollPane);

		//	final JButton addRegexButton = new JButton("Add");
		//	addRegexButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent actionEvent) {
		//			//	Main.this.addRegex();
		//		}
		//	});
		//	addRegexButton.addMouseListener(this.mouseAdapter);
		//	addRegexButton
		//		.setToolTipText("Add a new regular expression to choose entries with");
		//	detectionResultsPanel.add(addRegexButton, new GridBagConstraints(
		//		0,
		//		2,
		//		1,
		//		1,
		//		1.0,
		//		0.0,
		//		GridBagConstraints.CENTER,
		//		GridBagConstraints.HORIZONTAL,
		//		new Insets(0, 0, 0, 0),
		//		0,
		//		0));
		//
		//	final JButton removeRegexButton = new JButton("Remove");
		//	removeRegexButton.addActionListener(new ActionListener() {
		//		public void actionPerformed(final ActionEvent actionEvent) {
		//			//	Main.this.removeRegex();
		//		}
		//	});
		//	removeRegexButton.addMouseListener(this.mouseAdapter);
		//	removeRegexButton
		//		.setToolTipText("Remove the selected regular expression from the list");
		//	detectionResultsPanel.add(removeRegexButton, new GridBagConstraints(
		//		0,
		//		3,
		//		1,
		//		1,
		//		1.0,
		//		0.0,
		//		GridBagConstraints.CENTER,
		//		GridBagConstraints.HORIZONTAL,
		//		new Insets(0, 0, 0, 0),
		//		0,
		//		0));
	}
	private void createMenuItem(
		final JMenu aMenu,
		final String aName,
		final String aToolTipText,
		final ActionListener anActionListener,
		final boolean isEnabled) {

		final JMenuItem item = new JMenuItem(aName);
		item.addActionListener(anActionListener);
		item.addMouseListener(this.mouseAdapter);
		item.setToolTipText(aToolTipText);
		item.setEnabled(isEnabled);
		aMenu.add(item);
	}
	private void createMenus() {
		final JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		final JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		final JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				WindowMain.this.business.quit();
			}
		});
		quitItem.addMouseListener(this.mouseAdapter);
		quitItem.setToolTipText("Quit " + Constants.PROGRAM_NAME);
		fileMenu.add(quitItem);

		final JMenu modelMenu = new JMenu("Create Model");
		menuBar.add(modelMenu);
		this.createMenuItem(
			modelMenu,
			"From AOL Code Model",
			"Create a model by selecting an AOL code model",
			new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business
						.createModel(Constants.MODEL_AOL_SOURCE_CODE);
				}
			},
			false);
		this.createMenuItem(
			modelMenu,
			"From C++ Source Code",
			"Create a model by selecting the root path of a set of C++ files",
			new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business
						.createModel(Constants.MODEL_CPP_SOURCE_CODE);
				}
			},
			false);
		this.createMenuItem(
			modelMenu,
			"From C# Source Code",
			"Create a model by selecting the root path of a set of C# files",
			new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business
						.createModel(Constants.MODEL_CSH_SOURCE_CODE);
				}
			},
			false);
		this
			.createMenuItem(
				modelMenu,
				"From Java Bytecode",
				"Create a model by selecting the root path of a set of Java classfiles",
				new ActionListener() {
					public void actionPerformed(final ActionEvent actionEvent) {
						WindowMain.this.business
							.createModel(Constants.MODEL_JAVA_BYTECODE);
					}
				},
				true);
		this.createMenuItem(
			modelMenu,
			"From Java JAR File",
			"Create a model by selecting the root path of a Java JAR file",
			new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business
						.createModel(Constants.MODEL_JAVA_JAR_FILE);
				}
			},
			false);
		this.createMenuItem(
			modelMenu,
			"From Java Source Code",
			"Create a model by selecting the root path of a set of Java files",
			new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business
						.createModel(Constants.MODEL_JAVA_SOURCE_CODE);
				}
			},
			false);

		final JMenu designSmellsMenu = new JMenu("Detect Design Smells");
		menuBar.add(designSmellsMenu);

		final IDesignSmellDetection[] designSmellDetections =
			RepositoriesAccessor.getInstance().getDesignSmellDetections();
		for (int i = 0; i < designSmellDetections.length; i++) {
			final IDesignSmellDetection codeSmellDetection =
				designSmellDetections[i];
			final String smellName = (String) codeSmellDetection.getName();
			final JMenuItem item = new JMenuItem(smellName);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business.detectDesignSmell(smellName);
				}
			});
			item.addMouseListener(this.mouseAdapter);
			item.setToolTipText("Detect " + smellName);
			designSmellsMenu.add(item);
		}

		final JMenu codeSmellsMenu = new JMenu("Detect Code Smells");
		menuBar.add(codeSmellsMenu);

		final ICodeSmellDetection[] codeSmellDetections =
			RepositoriesAccessor.getInstance().getCodeSmellDetections();
		for (int i = 0; i < codeSmellDetections.length; i++) {
			final ICodeSmellDetection codeSmellDetection =
				codeSmellDetections[i];
			final String smellName = (String) codeSmellDetection.getName();
			final JMenuItem item = new JMenuItem(smellName);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent actionEvent) {
					WindowMain.this.business.detectCodeSmell(smellName);
				}
			});
			item.addMouseListener(this.mouseAdapter);
			item.setToolTipText("Detect " + smellName);
			codeSmellsMenu.add(item);
		}

		menuBar.add(Box.createHorizontalGlue());

		final JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		final JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				WindowMain.this.business.about();
			}
		});
		aboutItem.addMouseListener(this.mouseAdapter);
		aboutItem.setToolTipText("About " + Constants.PROGRAM_NAME);
		helpMenu.add(aboutItem);

		final JMenuItem helpItem = new JMenuItem("Help");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				WindowMain.this.business.help();
			}
		});
		helpItem.addMouseListener(this.mouseAdapter);
		helpItem.setToolTipText("Help on using " + Constants.PROGRAM_NAME);
		helpMenu.add(helpItem);
	}
	void displayDetectionResults(final String aMessage, final String someResults) {
		this.detectionSummaryResults.setText(this.detectionSummaryResults
			.getText()
				+ aMessage + '\n');
		this.detectionDetailedResults.setText(someResults);
	}
	void displayModelInformation(final String aMessage) {
		this.modelInformation.setText(this.modelInformation.getText()
				+ aMessage + '\n');
	}
	private String getHelpOf(final JComponent component) {
		return component.getToolTipText();
	}
	void informUser(final String aMessage) {
		if (this.messageDuration == 0) {
			this.infoField.setForeground(Color.BLUE);
			this.infoField.setText(aMessage);
		}
		else if (this.messageDuration > 0) {
			this.messageDuration--;
		}
	}
	void warnUser(final String aMessage) {
		this.infoField.setForeground(Color.RED);
		this.infoField.setText(aMessage);
		this.messageDuration = WindowMain.WARNING_DURATION;
	}
	void displayException(final Exception anException) {
		this.warnUser("An exception occurred: " + anException.toString()
				+ ", please see " + Constants.PROGRAM_FILE_NAME + ".log file");
		//	final StringWriter writer = new StringWriter();
		//	anException.printStackTrace(new PrintWriter(writer));
		//	this.displayModelInformation(writer.getBuffer().toString());
		anException.printStackTrace(ProxyConsole.getInstance().errorOutput());
		ProxyConsole.getInstance().errorOutput().flush();
	}
}

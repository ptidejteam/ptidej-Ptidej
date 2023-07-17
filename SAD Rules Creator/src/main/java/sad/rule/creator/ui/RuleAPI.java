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
/**
 * 
 */
package sad.rule.creator.ui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author Family
 *
 */
public class RuleAPI extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new RuleAPI();
	}
	//private Container contener;
	private final GridBagLayout gbTrace;

	private final GridBagConstraints gbConstraints;
	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
		"Root Node");
	private final DefaultTreeModel ruleCardTreeModel = new DefaultTreeModel(
		this.rootNode);
	private final JTree ruleCardTree = new JTree(this.ruleCardTreeModel);

	private final ScrollPane ruleCardTreeScrool = new ScrollPane();

	private final TextArea rulecardText = new TextArea();

	private final Panel panel = new Panel();
	private final Button browse = new Button();

	private final Button load = new Button();
	//private DefaultListModel listModel = new DefaultListModel();

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */

	private final TextField fileToLoad = new TextField();

	/**
	 * This is the default constructor
	 */
	public RuleAPI() {
		super();
		//		contener = getContentPane();
		this.gbTrace = new GridBagLayout();
		this.setLayout(this.gbTrace);
		this.gbConstraints = new GridBagConstraints();

		this.ruleCardTree.setEditable(false);
		this.ruleCardTree.getSelectionModel().setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.ruleCardTree.setShowsRootHandles(true);
		this.ruleCardTreeScrool.add(this.ruleCardTree);

		//rulecardTextScrool.add(rulecardText);
		//rulecardTextPanel.add(rulecardText);

		this.gbConstraints.fill = GridBagConstraints.BOTH;
		this.addComponent(this.browse, 0, 0, 1, 1);
		this.addComponent(this.fileToLoad, 0, 1, 6, 1);
		this.gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		this.addComponent(this.load, 0, 6, 1, 1);

		this.addComponent(this.ruleCardTreeScrool, 1, 0, 3, 6);
		this.gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		this.addComponent(this.panel, 1, 3, 5, 6);

		this.gbConstraints.gridwidth = GridBagConstraints.REMAINDER;
		this.addComponent(this.rulecardText, 4, 0, 8, 6);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent evenement) {
				System.exit(0);
			}

		});

		this.initialize();

	}

	public void addComponent(
		final Component aComponent,
		final int line,
		final int column,
		final int largeur,
		final int hauteur) {
		this.gbConstraints.gridx = column;
		this.gbConstraints.gridy = line;
		this.gbConstraints.gridwidth = largeur;
		this.gbConstraints.gridheight = hauteur;
		this.gbTrace.setConstraints(aComponent, this.gbConstraints);
		this.add(aComponent);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(650, 600);
		this.setVisible(true);
		this.setTitle("Rule Card Viewer");
	}

} //  @jve:decl-index=0:visual-constraint="308,171"

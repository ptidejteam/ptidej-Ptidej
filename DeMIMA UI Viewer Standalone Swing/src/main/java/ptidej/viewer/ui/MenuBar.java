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
package ptidej.viewer.ui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import padl.motif.DesignMotifsRepository;
import padl.motif.IDesignMotifModel;
import ptidej.ui.layout.IUILayout;
import ptidej.viewer.action.ActionsRepository;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.LookAndFeelMenu;

// TODO Use ptidej.viewer.widget.Menu and ptidej.viewer.widget.MenuItem!
// For example, for the overriding fireActionPerformed(...) that handles
// busy cursor automatically.
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private static final ActionsRepository actionsRepository =
		ActionsRepository.getInstance();
	private final static boolean HAS_ICON = true;
	private final static boolean HAS_MNEMONIC = true;
	private final static boolean PEERS_HAVE_ICONS = true;

	private static MenuBar UniqueInstance;
	public static MenuBar getUniqueInstance() {
		if (MenuBar.UniqueInstance == null) {
			MenuBar.UniqueInstance = new MenuBar();
		}
		return MenuBar.UniqueInstance;
	}

	private JMenu menuToolbars;
	private MenuBar() {
		this.menuFiles();
		this.menuMotifs();
		this.menuTools();
		this.menuViews();
		this.menuSettings();
		this.menuWindows();
		this.menuHelp();
	}
	private void addAction(
		final AbstractButton ab,
		final String strKey,
		final String strGroup) {

		final AbstractAction action =
			actionsRepository.getAction(strKey, strGroup);
		ab.addActionListener(action);
	}
	private JMenu addMenu(
		final JMenu parentMenu,
		final String strKey,
		final boolean hasMnemonic,
		final boolean hasIcon,
		final boolean havePeersIcons) {

		final JMenu currentMenu = new JMenu();
		currentMenu.setText(Resources.getMenuText(strKey, MenuBar.class));

		if (hasIcon) {
			currentMenu.setIcon(ptidej.ui.Utils.getIcon(Resources
				.getMenuIconPath(strKey, MenuBar.class)));
		}
		else if (havePeersIcons) {
			currentMenu.setIcon(ptidej.ui.Utils.getIcon(Resources.getIconPath(
				"EMPTY",
				MenuBar.class)));
		}
		if (hasMnemonic) {
			currentMenu.setMnemonic(Resources.getMenuMnemonic(
				strKey,
				MenuBar.class).charAt(0));
		}

		if (parentMenu != null) {
			parentMenu.add(currentMenu);
		}
		else {
			this.add(currentMenu);
		}

		return currentMenu;
	}
	private JMenuItem addMenuItem(
		final JMenu parentMenu,
		final String strKey,
		final String strGroup,
		final boolean hasMnemonic,
		final int keyCode,
		final int modifiers,
		final boolean hasIcon,
		final boolean havePeersIcons) {

		return this.addMenuItem(
			parentMenu,
			strKey,
			strGroup,
			hasMnemonic,
			keyCode,
			modifiers,
			hasIcon,
			havePeersIcons,
			true);
	}
	private JMenuItem addMenuItem(
		final JMenu parentMenu,
		final String strKey,
		final String strGroup,
		final boolean hasMnemonic,
		final int keyCode,
		final int modifiers,
		final boolean hasIcon,
		final boolean havePeersIcons,
		final boolean isEnabled) {

		final JMenuItem currentItem = new JMenuItem();
		currentItem.setActionCommand(strKey);
		currentItem.setText(Resources.getItemText(strKey, MenuBar.class));
		currentItem.setEnabled(isEnabled);

		if (hasIcon) {
			currentItem.setIcon(ptidej.ui.Utils.getIcon(Resources
				.getItemIconPath(strKey, MenuBar.class)));
		}
		else if (havePeersIcons) {
			currentItem.setIcon(ptidej.ui.Utils.getIcon(Resources.getIconPath(
				Resources.EMPTY,
				MenuBar.class)));
		}
		if (hasMnemonic) {
			final String mnemonic =
				Resources.getItemMnemonic(strKey, MenuBar.class);
			if (mnemonic.length() > 0) {
				currentItem.setMnemonic(mnemonic.charAt(0));
			}
		}
		if ((keyCode != -1) && (modifiers != -1)) {
			currentItem.setAccelerator(KeyStroke.getKeyStroke(
				keyCode,
				modifiers));
		}
		if (strKey.endsWith(Resources.HELP)) {
			currentItem.setToolTipText(Resources.getLink(strKey));
		}
		if (parentMenu != null) {
			parentMenu.add(currentItem);
		}
		else {
			this.add(currentItem);
		}
		this.addAction(currentItem, strKey, strGroup);

		return currentItem;
	}
	private JRadioButtonMenuItem addRadioMenuItem(
		final JMenu parentMenu,
		final String strKey,
		final String strGroup,
		final ButtonGroup bg,
		final boolean hasMnemonic,
		final int keyCode,
		final int modifiers,
		final boolean hasIcon,
		final boolean havePeersIcons) {

		final JRadioButtonMenuItem currentItem = new JRadioButtonMenuItem();
		currentItem.setActionCommand(strKey);
		currentItem.setText(Resources.getRadioText(strKey, MenuBar.class));
		bg.add(currentItem);

		if (hasIcon) {
			currentItem.setIcon(ptidej.ui.Utils.getIcon(Resources
				.getRadioIconPath(strKey, MenuBar.class)));
		}
		else if (havePeersIcons) {
			currentItem.setIcon(ptidej.ui.Utils.getIcon(Resources.getIconPath(
				Resources.EMPTY,
				MenuBar.class)));
		}
		if (hasMnemonic) {
			currentItem.setMnemonic(Resources.getRadioMnemonic(
				strKey,
				MenuBar.class).charAt(0));
		}
		if (keyCode != -1) {
			currentItem.setAccelerator(KeyStroke.getKeyStroke(
				keyCode,
				modifiers));
		}
		if (parentMenu != null) {
			parentMenu.add(currentItem);
		}
		else {
			this.add(currentItem);
		}
		this.addAction(currentItem, strKey, strGroup);

		return currentItem;
	}
	public JCheckBoxMenuItem addToolBarItem(
		final String strText,
		final boolean isVisible) {

		final JCheckBoxMenuItem currentItem =
			new JCheckBoxMenuItem(strText, isVisible);
		this.menuToolbars.add(currentItem);
		return currentItem;
	}

	private void menuFiles() {
		final JMenu menuFile =
			this.addMenu(
				null,
				Resources.FILE,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		// TODO To reestablish when multilingual projects are really possible.
		//	this.addMenuItem(
		//		menuFile,
		//		Constants.NEW_GRAPHICAL_PROJECT,
		//		Constants.FILE,
		//		HAS_MNEMONIC,
		//		KeyEvent.VK_N,
		//		InputEvent.CTRL_DOWN_MASK,
		//		HAS_ICON,
		//		PEERS_HAVE_ICONS);
		//	this.addMenuItem(
		//		menuFile,
		//		Constants.NEW_HIERARCHICAL_PROJECT,
		//		Constants.FILE,
		//		HAS_MNEMONIC,
		//		KeyEvent.VK_N,
		//		InputEvent.CTRL_DOWN_MASK,
		//		HAS_ICON,
		//		PEERS_HAVE_ICONS);
		//	menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.LOAD_GRAPHICAL_PROJECT,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_O,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuFile,
			Resources.LOAD_HIERARCHICAL_PROJECT,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_O,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuFile,
			Resources.LOAD_DUAL_HIERARCHICAL_PROJECT,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_O,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuFile,
			Resources.LOAD_EXTRINSIC,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			PEERS_HAVE_ICONS);
		menuFile.addSeparator();
		final JMenu menuAdd =
			this.addMenu(
				menuFile,
				Resources.ADD,
				HAS_MNEMONIC,
				HAS_ICON,
				PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.AOL_CODE_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.AOL_IDIOM_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.ASPECTJ_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.CPP_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.JAVA_CLASS_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.JAVA_ARCHIVE_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.JAVA_SOURCE_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.MSE_FILE,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuAdd,
			Resources.ECLIPSE_JDT_PROJECT,
			Resources.FILE,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.EXPORT_SVG,
			Resources.FILE,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			PEERS_HAVE_ICONS);
		menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.CLOSE_ALL,
			Resources.FILE,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuFile,
			Resources.CLOSE_ACTIVE,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_F4,
			InputEvent.CTRL_DOWN_MASK,
			!HAS_ICON,
			PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuFile,
			Resources.CLOSE_ALL_EXPECT_ACTIVE,
			Resources.FILE,
			!HAS_MNEMONIC,
			KeyEvent.VK_F4,
			InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK,
			!HAS_ICON,
			PEERS_HAVE_ICONS);
		menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.SAVE_ALL,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_S,
			InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS,
			false);
		this.addMenuItem(
			menuFile,
			Resources.SAVE_ACTIVE,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_S,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS,
			false);
		menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.PRINT,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_P,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS,
			false);
		menuFile.addSeparator();
		this.addMenuItem(
			menuFile,
			Resources.EXIT,
			Resources.FILE,
			HAS_MNEMONIC,
			KeyEvent.VK_Q,
			InputEvent.CTRL_DOWN_MASK,
			HAS_ICON,
			PEERS_HAVE_ICONS);
	}
	private void menuHelp() {
		final JMenu menuHelp =
			this.addMenu(
				null,
				Resources.HELP,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final JMenu menuHelpTopics =
			this.addMenu(
				menuHelp,
				Resources.HELP_TOPICS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuHelpTopics,
			Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_SOLVER_3,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuHelpTopics,
			Resources.PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_SOLVER_4,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuHelpTopics,
			Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_METRICAL_SOLVER_4,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuHelpTopics,
			Resources.EPI_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.EPI,
			!HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		//	menuHelpTopics.addSeparator();
		//	this.manageMenuItem(
		//		menuHelpTopics,
		//		PtidejConstants.SYSTEMATIC_UML_HELP,
		//		PtidejConstants.TOOLS,
		//		!HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	menuHelpTopics.addSeparator();
		//	this.manageMenuItem(
		//		menuHelpTopics,
		//		PtidejConstants.ADJACENCY_MATRIX_INFOVIS_HELP,
		//		PtidejConstants.TOOLS,
		//		!HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuHelpTopics,
		//		PtidejConstants.ADJACENCY_MATRIX_OADYMPPAC_HELP,
		//		PtidejConstants.TOOLS,
		//		!HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	menuHelpTopics.addSeparator();
		//	this.manageMenuItem(
		//		menuHelpTopics,
		//		PtidejConstants.DOTTY_HELP,
		//		PtidejConstants.TOOLS,
		//		!HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuHelpTopics,
		//		PtidejConstants.SUGIBIB_HELP,
		//		PtidejConstants.TOOLS,
		//		!HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);

		this.addMenuItem(
			menuHelp,
			Resources.USAGE,
			Resources.HELP,
			!HAS_MNEMONIC,
			KeyEvent.VK_F1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);

		menuHelp.addSeparator();
		this.addMenuItem(
			menuHelp,
			Resources.ABOUT,
			Resources.HELP,
			HAS_MNEMONIC,
			KeyEvent.VK_A,
			InputEvent.CTRL_DOWN_MASK,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
	}
	private void menuMotifs() {
		final JMenu menuMotifs =
			this.addMenu(
				null,
				Resources.DESIGN_MOTIFS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final JMenu menuDesignMotifs =
			this.addMenu(
				menuMotifs,
				Resources.DESIGN_MOTIFS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final ButtonGroup bgPattern = new ButtonGroup();
		final DesignMotifsRepository patternRepository =
			DesignMotifsRepository.getInstance();
		final IDesignMotifModel[] patterns =
			patternRepository.getDesignMotifs();
		for (int i = 0; i < patterns.length; i++) {
			this.addRadioMenuItem(
				menuDesignMotifs,
				patterns[i].getDisplayName(),
				Resources.DESIGN_MOTIFS,
				bgPattern,
				HAS_MNEMONIC,
				-1,
				-1,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		}

		// Yann 2006/07/19: Good start!
		// I make sure at least one of the 
		// radio-button is selected.
		final Enumeration enumeration = bgPattern.getElements();
		if (enumeration.hasMoreElements()) {
			final JRadioButtonMenuItem button =
				(JRadioButtonMenuItem) enumeration.nextElement();
			button.doClick();
		}

		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.ABSTRACT_FACTORY,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.ADAPTER,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.CHAIN_OF_RESPONSIBILITY,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.COMMAND,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.COMPOSITE,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.DECORATOR,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.FACADE,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.FACTORY_METHOD,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.FLY_WEIGHT,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.GOOD_INHERITANCE,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.MEDIATOR,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.MEMENTO,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.OBSERVER,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.PROTOTYPE,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.PROXY,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.SINGLETON,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.STATE,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.TEMPLATE_METHOD,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageRadioMenuItem(
		//		menuPattern,
		//		Constants.VISITOR,
		//		Constants.PATTERN,
		//		bgPattern,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);

		final JMenu menuSolvers =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_SOLVERS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final ButtonGroup bgSolver = new ButtonGroup();
		this.addRadioMenuItem(
			menuSolvers,
			Resources.SOLVER_PROBLEM_AUTOMATIC,
			Resources.PTIDEJ_SOLVERS,
			bgSolver,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addRadioMenuItem(
			menuSolvers,
			Resources.SOLVER_COMBINATORIAL_AUTOMATIC,
			Resources.PTIDEJ_SOLVERS,
			bgSolver,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addRadioMenuItem(
			menuSolvers,
			Resources.SOLVER_SIMPLE_AUTOMATIC,
			Resources.PTIDEJ_SOLVERS,
			bgSolver,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		menuMotifs.addSeparator();

		final JMenu menuPtidejSolver3 =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_SOLVER_3,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final ButtonGroup bgProblem = new ButtonGroup();
		final JMenu menuProblem =
			this.addMenu(
				menuPtidejSolver3,
				Resources.PROBLEM,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final JRadioButtonMenuItem radioAC4Problem =
			this.addRadioMenuItem(
				menuProblem,
				Resources.PROBLEM_AC4,
				Resources.PTIDEJ_SOLVER_3,
				bgProblem,
				HAS_MNEMONIC,
				-1,
				-1,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final JRadioButtonMenuItem radioCustomProblem =
			this.addRadioMenuItem(
				menuProblem,
				Resources.PROBLEM_CUSTOM,
				Resources.PTIDEJ_SOLVER_3,
				bgProblem,
				HAS_MNEMONIC,
				-1,
				-1,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		bgProblem.add(radioAC4Problem);
		bgProblem.add(radioCustomProblem);
		menuPtidejSolver3.addSeparator();
		this.addMenuItem(
			menuPtidejSolver3,
			Resources.GENERATE_PROGRAM_MODEL,
			Resources.PTIDEJ_SOLVER_3,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuPtidejSolver3,
			Resources.GENERATE_SOLVER_EXECUTION_DATA,
			Resources.PTIDEJ_SOLVER_3,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		menuPtidejSolver3.addSeparator();

		final JMenu menuSolver3SimilarMicroArchs =
			this.addMenu(
				menuPtidejSolver3,
				Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuSolver3SimilarMicroArchs,
			Resources.PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE,
			Resources.PTIDEJ_SOLVER_3,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuSolver3SimilarMicroArchs,
			Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_SOLVER_3,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);

		final JMenu menuPtidejSolver4 =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_SOLVER_4,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);

		final JMenu menuSolver4SimilarMicroArchs =
			this.addMenu(
				menuPtidejSolver4,
				Resources.PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuSolver4SimilarMicroArchs,
			Resources.PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE,
			Resources.PTIDEJ_SOLVER_4,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuSolver4SimilarMicroArchs,
			Resources.PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_SOLVER_4,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);

		final JMenu menuMetricalPtidejSolver4 =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_METRICAL_SOLVER_4,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);

		final JMenu menuMetricalSolver4SimilarMicroArchs =
			this.addMenu(
				menuMetricalPtidejSolver4,
				Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuMetricalSolver4SimilarMicroArchs,
			Resources.METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE,
			Resources.PTIDEJ_METRICAL_SOLVER_4,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuMetricalSolver4SimilarMicroArchs,
			Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.PTIDEJ_METRICAL_SOLVER_4,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);

		final JMenu menuEPI =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_EPI,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);

		final JMenu menuEPISimilarMicroArchs =
			this.addMenu(
				menuEPI,
				Resources.EPI_SIMILAR_MICRO_ARCHITECTURE,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuEPISimilarMicroArchs,
			Resources.EPI_FIND_SIMILAR_MICRO_ARCHITECTURE,
			Resources.EPI,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuEPISimilarMicroArchs,
			Resources.EPI_SIMILAR_MICRO_ARCHITECTURE_HELP,
			Resources.EPI,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		menuMotifs.addSeparator();

		final JMenu menuPtidejSolvers =
			this.addMenu(
				menuMotifs,
				Resources.PTIDEJ_SOLVERS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuPtidejSolvers,
			Resources.LOAD_SIMILAR_MICRO_ARCHITECTURES,
			Resources.PTIDEJ_SOLVERS,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuPtidejSolvers,
			Resources.LIST_SIMILAR_MICRO_ARCHITECTURES,
			Resources.PTIDEJ_SOLVERS,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuPtidejSolvers,
			Resources.REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES,
			Resources.PTIDEJ_SOLVERS,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		menuPtidejSolvers.addSeparator();
		this.addMenuItem(
			menuPtidejSolvers,
			Resources.MODIFY_PROGRAM_MODEL,
			Resources.PTIDEJ_SOLVERS,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
	}
	private void menuSettings() {
		final JMenu menuSettings =
			this.addMenu(
				null,
				Resources.SETTINGS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		menuSettings.add(LookAndFeelMenu.getUniqueInstance());
		final JMenu menuLayouts =
			this.addMenu(
				menuSettings,
				Resources.LAYOUT_TOPICS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		final ButtonGroup bgGraphLayouts = new ButtonGroup();
		final ptidej.ui.layout.UILayoutsRepository graphLayoutRepository =
			ptidej.ui.layout.UILayoutsRepository.getInstance();
		final IUILayout[] graphLayouts = graphLayoutRepository.getUILayouts();
		for (int i = 0; i < graphLayouts.length; i++) {
			this.addRadioMenuItem(
				menuLayouts,
				graphLayouts[i].getName(),
				Resources.GRAPH_LAYOUT,
				bgGraphLayouts,
				HAS_MNEMONIC,
				-1,
				-1,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		}
	}
	private void menuTools() {
		//------------------
		//	final JMenu menuTools =
		//		this.manageMenu(
		//			null,
		//			PtidejConstants.TOOLS,
		//			HAS_MNEMONIC,
		//			!HAS_ICON,
		//			!PEERS_HAVE_ICONS);
		//
		//	final JMenu menuUML =
		//		this.manageMenu(
		//			menuTools,
		//			PtidejConstants.UML,
		//			HAS_MNEMONIC,
		//			!HAS_ICON,
		//			!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuUML,
		//		PtidejConstants.SYSTEMATIC_UML,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuUML,
		//		PtidejConstants.SYSTEMATIC_UML_HELP,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.AOL_MODELS_COMPARISION,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//
		//	final JMenu menuModelDiffHighlighter =
		//		this.manageMenu(
		//			menuTools,
		//			PtidejConstants.MODEL_DIFFERENCE_HIGHLIGHTER,
		//			HAS_MNEMONIC,
		//			!HAS_ICON,
		//			!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuModelDiffHighlighter,
		//		PtidejConstants.MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuModelDiffHighlighter,
		//		PtidejConstants.MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//
		//	final JMenu menuSugiBib =
		//		this.manageMenu(
		//			menuTools,
		//			PtidejConstants.SUGIBIB,
		//			HAS_MNEMONIC,
		//			!HAS_ICON,
		//			!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuSugiBib,
		//		PtidejConstants.SUGIBIB,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuSugiBib,
		//		PtidejConstants.SUGIBIB_HELP,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.POM_BASED_METRICS,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenu(
		//		menuTools,
		//		PtidejConstants.ADJACENCY_MATRIX,
		//		HAS_MNEMONIC,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.ADJACENCY_MATRIX_OADYMPPAC,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.ADJACENCY_MATRIX_OADYMPPAC_HELP,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	menuTools.addSeparator();
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.ADJACENCY_MATRIX_INFOVIS,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuTools,
		//		PtidejConstants.ADJACENCY_MATRIX_INFOVIS_HELP,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//
		//	final JMenu menuDotty =
		//		this.manageMenu(
		//			menuTools,
		//			PtidejConstants.DOTTY,
		//			HAS_MNEMONIC,
		//			!HAS_ICON,
		//			!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuDotty,
		//		PtidejConstants.DOTTY,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
		//	this.manageMenuItem(
		//		menuDotty,
		//		PtidejConstants.DOTTY_HELP,
		//		PtidejConstants.TOOLS,
		//		HAS_MNEMONIC,
		//		-1,
		//		-1,
		//		!HAS_ICON,
		//		!PEERS_HAVE_ICONS);
	}
	private void menuViews() {
		final JMenu menuView =
			this.addMenu(
				null,
				Resources.VIEW,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.menuToolbars =
			this.addMenu(
				menuView,
				Resources.TOOLBARS,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
	}
	private void menuWindows() {
		final JMenu menuWindow =
			this.addMenu(
				null,
				Resources.WINDOW,
				HAS_MNEMONIC,
				!HAS_ICON,
				!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuWindow,
			Resources.MINIMIZE_ALL,
			Resources.WINDOW,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS);
		this.addMenuItem(
			menuWindow,
			Resources.CASCADE,
			Resources.WINDOW,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS,
			false);
		this.addMenuItem(
			menuWindow,
			Resources.TILE_VERTICALLY,
			Resources.WINDOW,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS,
			true);
		this.addMenuItem(
			menuWindow,
			Resources.TILE_HORIZONTALLY,
			Resources.WINDOW,
			HAS_MNEMONIC,
			-1,
			-1,
			!HAS_ICON,
			!PEERS_HAVE_ICONS,
			true);
	}
}

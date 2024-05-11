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
package ptidej.viewer.widget;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import padl.kernel.IAbstractModel;
import ptidej.ui.kernel.builder.Builder;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.IViewer;
import ptidej.viewer.action.ActionsRepository;
import ptidej.viewer.action.AntiPatternChoiceAction;
import ptidej.viewer.layout.PercentLayout;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import util.help.Browser;
import util.help.IHelpURL;

public class EmbeddedPanel extends JPanel implements IViewer {
	private static final long serialVersionUID = 1L;

	private static final Insets INSETS = new Insets(2, 2, 2, 2);

	private ActionsRepository actions = ActionsRepository.getInstance();
	private GridBagConstraints gbc;

	public EmbeddedPanel() {
		this.setLayout(new GridBagLayout());
	}
	private AbstractAction addAction(
		final AbstractButton anAbstractButton,
		final String aKey,
		final String aGroup) {

		final AbstractAction action = this.actions.getAction(aKey, aGroup);
		anAbstractButton.addActionListener(action);
		if (anAbstractButton instanceof JCheckBox) {
			anAbstractButton.addItemListener((ItemListener) action);
		}

		return action;
	}
	public void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isSourceModelListerner) {

		this.addButton(
			buttonLabel,
			listener,
			isEnabled,
			false,
			isSourceModelListerner);
	}
	public void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner) {

		final Button button = new Button(buttonLabel);
		button.addActionListener(listener);
		button.setEnabled(isEnabled);

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(button);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(button);
		}

		this.add(button, this.getInsetConstraint());
	}

	public void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner,
		final IHelpURL helpURL) {

		final Button button = new Button(buttonLabel);
		button.addActionListener(listener);
		button.setEnabled(isEnabled);

		final Button help = new Button("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Browser.displayURL(helpURL.getHelpURL());
			}
		});
		help.setEnabled(true);

		final Panel panel = new Panel(new PercentLayout());
		panel.add(button, new PercentLayout.Constraint(0, 0, 75, 100));
		panel.add(help, new PercentLayout.Constraint(76, 0, 24, 100));

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(button);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(button);
		}

		this.add(panel, this.getInsetConstraint());
	}
	public void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isSourceModelListerner,
		final IHelpURL helpURL) {

		this.addButton(
			buttonLabel,
			listener,
			isEnabled,
			false,
			isSourceModelListerner,
			helpURL);
	}
	public void addButton(
		final String aKey,
		final Class aClass,
		final String strGroup,
		final boolean isEnabled,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner) {

		final Button button = new Button(Resources.getButtonText(aKey, aClass));
		button.setActionCommand(aKey);
		button.setEnabled(isEnabled);

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(button);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(button);
		}

		this.addAction(button, aKey, strGroup);
		this.add(button, this.getInsetConstraint());
	}
	public void addButton(
		final String aKey,
		final Class aClass,
		final String strGroup,
		final boolean isEnabled,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner,
		final IHelpURL helpURL) {

		final Button button = new Button(Resources.getButtonText(aKey, aClass));
		button.setActionCommand(aKey);
		button.setEnabled(isEnabled);

		final Button help = new Button("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Browser.displayURL(helpURL.getHelpURL());
			}
		});
		help.setEnabled(true);

		final Panel panel = new Panel(new PercentLayout());
		panel.add(button, new PercentLayout.Constraint(0, 0, 75, 100));
		panel.add(help, new PercentLayout.Constraint(76, 0, 24, 100));

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(button);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(button);
		}

		this.addAction(button, aKey, strGroup);
		this.add(panel, this.getInsetConstraint());
	}
	public void addCheckBox(
		final String aKey,
		final Class aClass,
		final String strGroup,
		final boolean isSelected,
		final boolean isEnabled,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner) {

		final CheckBox checkBox =
			new CheckBox(Resources.getCheckboxText(aKey, aClass));
		checkBox.setSelected(isSelected);
		checkBox.setEnabled(isEnabled);
		checkBox.setActionCommand(aKey);

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(checkBox);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(checkBox);
		}

		this.addAction(checkBox, aKey, strGroup);
		this.add(checkBox, this.getInsetConstraint());
	}

	public void addCheckBox(
		final String checkBoxText,
		final String strGroup,
		final boolean isSelected,
		final ActionListener listener) {

		final CheckBox checkBox = new CheckBox(checkBoxText);
		checkBox.setSelected(isSelected);
		checkBox.setActionCommand(checkBoxText);
		checkBox.addActionListener(listener);

		this.add(checkBox, this.getInsetConstraint());
	}
	public void addCheckBox(
		final String aKey,
		final String strGroup,
		final boolean isSelected,
		final AntiPatternChoiceAction action,
		final Button actionButton) {

		final JCheckBox chk = new JCheckBox(aKey);
		chk.addActionListener(action);
		chk.setSelected(isSelected);

		final Panel panel = new Panel(new PercentLayout());
		panel.add(chk, new PercentLayout.Constraint(0, 0, 75, 100));
		panel.add(actionButton, new PercentLayout.Constraint(76, 0, 24, 100));
		this.add(panel, this.getInsetConstraint());
	}
	public void addCollapsablePanel(
		final String aKey,
		final Class aClass,
		final JPanel someContent) {

		final JPanel panel =
			new CollapsablePanel(
				Resources.getPanelTitle(aKey, aClass),
				someContent);

		this.add(panel, this.getInsetConstraint());
	}
	public void addCollapsablePanel(
		final String aTitle,
		final JPanel someContent) {

		final JPanel panel = new CollapsablePanel(aTitle, someContent);
		this.add(panel, this.getInsetConstraint());
	}
	public void addLabel(final String aKey, final Class aClass) {
		final JLabel label = new JLabel();
		label.setBackground(this.getBackground());
		label.setText(Resources.getRichText(aKey, aClass));

		this.add(label, this.getInsetConstraint());
	}
	public void addRadioButton(
		final String aKey,
		final Class aClass,
		final String strGroup,
		final ButtonGroup bg,
		final boolean isSelected,
		final boolean isRuleCardListerner,
		final boolean isSourceModelListerner) {

		final RadioButton radio =
			new RadioButton(Resources.getRadioText(aKey, aClass));
		radio.setSelected(isSelected);
		radio.setActionCommand(aKey);

		if (isRuleCardListerner) {
			DesktopPane.getInstance().addRuleCardListener(radio);
		}
		if (isSourceModelListerner) {
			DesktopPane.getInstance().addSourceModelListener(radio);
		}

		bg.add(radio);
		this.addAction(radio, aKey, strGroup);
		this.add(radio, this.getInsetConstraint());
	}
	public void addSource(
		final IAbstractModel anAbstractModel,
		final Builder aBuilder) {

		// Yann 2007/04/22: Source files!
		// I do not forget to update some of
		// the pieces of the representation
		// that cannot be recomputed.
		final IRepresentation oldRepresentation =
			DesktopPane.getInstance().getAbstractRepresentationWindow();

		// TODO: Should create the same window type as the original one!
		DesktopPane.getInstance().createHierarchicalModelWindow();

		final Iterator iteratorSourceFileTypes =
			oldRepresentation.getSourceFileTypes().iterator();
		while (iteratorSourceFileTypes.hasNext()) {
			final String sourceFileType =
				(String) iteratorSourceFileTypes.next();
			final Iterator iteratorSourceFiles =
				oldRepresentation.getSourceFiles(sourceFileType).iterator();
			while (iteratorSourceFiles.hasNext()) {
				final String sourceFile = (String) iteratorSourceFiles.next();
				DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()
					.addSourceFile(sourceFileType, sourceFile);
			}
		}

		DesktopPane
			.getInstance()
			.getAbstractRepresentationWindow()
			.addOccurrences(oldRepresentation.getOccurrences());

		// Yann 2007/06/13: Update...
		// I do not forget to notify listeners of the changes.
		// Sixth...
		//	DesktopPane.getInstance().notifySourceModelAvailability(
		//		new SourceAndGraphModelEvent(DesktopPane.getInstance().getModelWindow()));
	}
	public JTextPane addTextPane(final String aKey, final Class aClass) {
		final JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setText(Resources.getRichText(aKey, aClass));
		textPane.setSelectionColor(super.getBackground());
		textPane.setSelectedTextColor(super.getBackground().darker());
		textPane.setBackground(super.getBackground());
		this.add(textPane, this.getInsetConstraint());

		return textPane;
	}
	public GridBagConstraints getInsetConstraint() {
		if (this.gbc != null) {
			this.gbc.gridy++;
		}
		else {
			this.gbc = new GridBagConstraints();
			this.gbc.weightx = 1.0;
			this.gbc.fill = GridBagConstraints.HORIZONTAL;
			this.gbc.gridy = 0;
			this.gbc.insets = EmbeddedPanel.INSETS;
		}

		return this.gbc;
	}
	public IRepresentation getRepresentation() {
		return DesktopPane.getInstance().getAbstractRepresentationWindow();
	}
}

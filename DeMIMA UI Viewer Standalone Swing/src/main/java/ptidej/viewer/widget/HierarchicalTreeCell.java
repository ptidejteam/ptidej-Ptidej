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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.kernel.builder.Builder;
import ptidej.viewer.awt.ActionListener;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/03
 */
public class HierarchicalTreeCell extends JPanel {
	private static final long serialVersionUID = 1L;

	private final IConstituent constituent;
	private HierarchicalTreeCellCheckbox displayCheckbox;
	private ItemListener displayListener;
	private HierarchicalTreeCellCheckbox selectionCheckbox;
	private ItemListener selectionListener;
	private HierarchicalTreeCellCheckbox specialCheckbox;

	public HierarchicalTreeCell(
		final Builder aBuilder,
		final AWTCanvas anAWTCanvas,
		final Canvas aCanvas,
		final ModelGraph aModelGraph,
		final IConstituent aConstituent,
		final IFirstClassEntity theParentFirstClassEntity,
		final ItemListener aDisplayListener,
		final ItemListener aSelectionListener) {

		this.constituent = aConstituent;

		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		if (aConstituent == theParentFirstClassEntity) {
			this.displayCheckbox = new HierarchicalTreeCellCheckbox(this);
			this.displayListener = aDisplayListener;
			this.displayCheckbox.addItemListener(aDisplayListener);
			this.add(this.displayCheckbox, BorderLayout.WEST);

			this.selectionCheckbox = new HierarchicalTreeCellCheckbox(this);
			this.selectionListener = aSelectionListener;
			this.selectionCheckbox.addItemListener(aSelectionListener);
			this.add(this.selectionCheckbox, BorderLayout.CENTER);
		}

		// Yann 2014/03/29: Goto considered playful!
		// I set the name so that the action listener knows
		// and moves the canvas onto the proper constituent.
		this.setName(theParentFirstClassEntity.getDisplayID());
		//	final JButton goToButton = new JButton("->");
		final JButton goToButton =
			new JButton(ptidej.ui.Utils.getIcon("GoTo.gif"));
		goToButton.setFocusPainted(false);
		goToButton.setBorder(BorderFactory.createEmptyBorder());
		goToButton.setContentAreaFilled(false);
		goToButton.setActionCommand(ActionListener.GO_TO);
		goToButton.addActionListener(new ActionListener(
			anAWTCanvas,
			aCanvas,
			aModelGraph));
		this.add(goToButton);

		final HierarchicalTreeCellLabel label = new HierarchicalTreeCellLabel();
		// Yann 2013/07/28: Beautification
		// I beautify the display of fields and variables
		// by showing their types and names, not just names.
		// TODO Unify the API of IField and IParameter so
		// they both use IEntity...
		// Determine the correct text to display
		// Yann 2014/06/20: Polymorphism and extensibility!
		// I can replace all this ugly code with a call to 
		// the current builder, to allow different texts
		// for different programming languages.
		final String labelText = aBuilder.getLabelText(aConstituent);
		label.setText(labelText);

		// Determine the correct icon to display
		// Yann 2013/08/08: Polymorphism and extensibility!
		// I can replace all this ugly code with a call to 
		// the current builder, to allow different icons
		// for different programming languages.
		//	if (constituent instanceof IField) {
		//		if (constituent.isPrivate()) {
		//			label.setIcon(HierarchicalTreeCell.FIELD_PRIVATE_ICON);
		//		}
		//		else if (constituent.isProtected()) {
		//			label.setIcon(HierarchicalTreeCell.FIELD_PROTECTED_ICON);
		//		}
		//		else if (constituent.isPublic()) {
		//			label.setIcon(HierarchicalTreeCell.FIELD_PUBLIC_ICON);
		//		}
		//		else {
		//			label.setIcon(HierarchicalTreeCell.FIELD_DEFAULT_ICON);
		//		}
		//	}
		//	else if (constituent instanceof IOperation) {
		//		if (constituent.isPrivate()) {
		//			label.setIcon(HierarchicalTreeCell.METHOD_PRIVATE_ICON);
		//		}
		//		else if (constituent.isProtected()) {
		//			label.setIcon(HierarchicalTreeCell.METHOD_PROTECTED_ICON);
		//		}
		//		else if (constituent.isPublic()) {
		//			label.setIcon(HierarchicalTreeCell.METHOD_PUBLIC_ICON);
		//		}
		//		else {
		//			label.setIcon(HierarchicalTreeCell.METHOD_DEFAULT_ICON);
		//		}
		//	}
		//	else if (constituent instanceof IMemberClass) {
		//		if (constituent.isPrivate()) {
		//			label.setIcon(HierarchicalTreeCell.CLASS_PRIVATE_ICON);
		//		}
		//		else if (constituent.isProtected()) {
		//			label.setIcon(HierarchicalTreeCell.CLASS_PROTECTED_ICON);
		//		}
		//		else if (constituent.isPublic()) {
		//			label.setIcon(HierarchicalTreeCell.CLASS_PUBLIC_ICON);
		//		}
		//		else {
		//			label.setIcon(HierarchicalTreeCell.CLASS_DEFAULT_ICON);
		//		}
		//	}
		//	else if (constituent instanceof IMemberInterface) {
		//		if (constituent.isPrivate()) {
		//			label.setIcon(HierarchicalTreeCell.INTERFACE_PRIVATE_ICON);
		//		}
		//		else if (constituent.isProtected()) {
		//			label.setIcon(HierarchicalTreeCell.INTERFACE_PROTECTED_ICON);
		//		}
		//		else if (constituent.isPublic()) {
		//			label.setIcon(HierarchicalTreeCell.INTERFACE_PUBLIC_ICON);
		//		}
		//		else {
		//			label.setIcon(HierarchicalTreeCell.INTERFACE_DEFAULT_ICON);
		//		}
		//	}
		//	else if (constituent instanceof IRelationship) {
		//		label.setIcon(HierarchicalTreeCell.RELATIONSHIP_ICON);
		//	}
		//	else if (constituent instanceof IClass) {
		//		label.setIcon(HierarchicalTreeCell.CLASS_ICON);
		//	}
		//	else if (constituent instanceof IInterface) {
		//		label.setIcon(HierarchicalTreeCell.INTERFACE_ICON);
		//	}
		//	else if (constituent instanceof IGhost) {
		//		label.setIcon(HierarchicalTreeCell.GHOST_ICON);
		//	}
		//	else if (constituent instanceof IMethodInvocation) {
		//		label.setIcon(HierarchicalTreeCell.RELATIONSHIP_ICON);
		//	}
		final Icon labelIcon = aBuilder.getLabelIcon(aConstituent);
		label.setIcon(labelIcon);

		this.add(label, BorderLayout.EAST);
	}
	public HierarchicalTreeCell(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final ItemListener aDisplayListener,
		final ItemListener aSelectionListener) {

		this(
			aBuilder,
			anAbstractModel,
			aDisplayListener,
			aSelectionListener,
			null);
	}
	public HierarchicalTreeCell(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final ItemListener aDisplayListener,
		final ItemListener aSelectionListener,
		final ItemListener aSpecialListener) {

		// Yann 2013/09/28: NullObject!
		// I simulate a null-object by creating a default package
		// just for making sure that there is a constituent.
		this.constituent = Factory.getInstance().createPackageDefault();

		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		this.displayCheckbox = new HierarchicalTreeCellCheckbox(this);
		this.displayCheckbox.addItemListener(aDisplayListener);
		this.add(this.displayCheckbox);

		this.selectionCheckbox = new HierarchicalTreeCellCheckbox(this);
		this.selectionCheckbox.addItemListener(aSelectionListener);
		this.add(this.selectionCheckbox);

		if (aSpecialListener != null) {
			this.specialCheckbox = new HierarchicalTreeCellCheckbox(this);
			this.specialCheckbox.addItemListener(aSpecialListener);
			this.add(this.specialCheckbox);
		}

		final HierarchicalTreeCellLabel label = new HierarchicalTreeCellLabel();
		label.setText(anAbstractModel.getDisplayName());
		this.add(label);
	}
	public IConstituent getConstituent() {
		return this.constituent;
	}
	public void setDisplayedWithoutNotification(final boolean isDisplayed) {
		if (this.displayCheckbox != null) {
			this.displayCheckbox.removeItemListener(this.displayListener);
			this.displayCheckbox.mustChangeCursor(false);
			this.displayCheckbox.setSelected(isDisplayed);
			this.displayCheckbox.mustChangeCursor(true);
			this.displayCheckbox.addItemListener(this.displayListener);
		}
	}
	public void setSelectedWithoutNotification(final boolean isSelected) {
		if (this.selectionCheckbox != null) {
			this.selectionCheckbox.removeItemListener(this.selectionListener);
			this.selectionCheckbox.mustChangeCursor(false);
			this.selectionCheckbox.setSelected(isSelected);
			this.selectionCheckbox.mustChangeCursor(true);
			this.selectionCheckbox.addItemListener(this.selectionListener);
		}
	}
	public void setSpecialedWithoutNotification(final boolean isSelected) {
		if (this.specialCheckbox != null) {
			this.specialCheckbox.removeItemListener(this.selectionListener);
			this.specialCheckbox.mustChangeCursor(false);
			this.specialCheckbox.setSelected(isSelected);
			this.specialCheckbox.mustChangeCursor(true);
			this.specialCheckbox.addItemListener(this.selectionListener);
		}
	}
}

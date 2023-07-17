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
package ptidej.viewer.action;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import padl.kernel.IAbstractModel;
import ptidej.ui.IVisibility;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.panel.OptionPanel;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import ptidej.viewer.widget.Button;
import ptidej.viewer.widget.CheckBox;
import ptidej.viewer.widget.CollapsablePanel;
import ptidej.viewer.widget.EmbeddedPanel;

public final class OptionAction extends AbstractAction implements ItemListener {
	private static final long serialVersionUID = 5369010006810614379L;

	private static OptionAction UniqueInstance;
	public static OptionAction getInstance() {
		if (OptionAction.UniqueInstance == null) {
			OptionAction.UniqueInstance = new OptionAction();
		}
		return OptionAction.UniqueInstance;
	}

	private OptionAction() {
	}
	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();
		if (action.equals(Resources.SELECT_ALL)) {
			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();
			final IAbstractModel abstractModel = window.getSourceModel();
			boolean justDoIt;
			if (abstractModel.getNumberOfConstituents() > Resources.NUMBER_OF_ENTITIES_FOR_WARNING) {
				justDoIt = Utils.warnAboutLongOperation();
			}
			else {
				justDoIt = true;
			}

			if (justDoIt) {
				this.selectAllCheckboxes((Button) e.getSource(), true);
			}
		}
		else if (action.equals(Resources.UNSELECT_ALL)) {
			this.selectAllCheckboxes((Button) e.getSource(), false);
		}
	}
	private void concatenateVisibility(
		final int aVisibility,
		final boolean addVisibility) {

		final AbstractRepresentationWindow window =
			DesktopPane.getInstance().getAbstractRepresentationWindow();
		int currentVisibleElements = window.getVisibleElements();

		if (addVisibility) {
			currentVisibleElements |= aVisibility;
		}
		else {
			currentVisibleElements &= (aVisibility ^ -1);
		}

		window.setVisibleElements(currentVisibleElements);
	}
	public void itemStateChanged(final ItemEvent anItemEvent) {
		// Yann 2014/06/20: Honour!
		// I must honour the enabling or not of this action.
		if (this.isEnabled()) {
			final CheckBox checkBox = (CheckBox) anItemEvent.getSource();
			final boolean isSelected =
				(anItemEvent.getStateChange() == ItemEvent.SELECTED);
			final String action = checkBox.getActionCommand();

			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();
			final IAbstractModel abstractModel = window.getSourceModel();
			boolean justDoIt;
			if (isSelected
					&& abstractModel.getNumberOfConstituents() > Resources.NUMBER_OF_ENTITIES_FOR_WARNING) {

				justDoIt = Utils.warnAboutLongOperation();
			}
			else {
				justDoIt = true;
			}

			if (justDoIt) {
				// Yann 2014/06/20: Hack!!!
				OptionAction.getInstance().setEnabled(false);
				checkBox.setSelected(isSelected);
				// Yann 2014/06/20: Hack!!!
				OptionAction.getInstance().setEnabled(true);

				if (action.equals(Resources.CREATION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.CREATION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.CREATION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.CREATION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.USE_NAMES)) {
					this.concatenateVisibility(
						IVisibility.USE_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.USE_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.USE_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.ASSOCIATION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.ASSOCIATION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.ASSOCIATION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.ASSOCIATION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.COMPOSITION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.COMPOSITION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.COMPOSITION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.COMPOSITION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.AGGREGATION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.AGGREGATION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.AGGREGATION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.AGGREGATION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.CONTAINER_COMPOSITION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.CONTAINER_COMPOSITION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.CONTAINER_COMPOSITION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.CONTAINER_AGGREGATION_NAMES)) {
					this.concatenateVisibility(
						IVisibility.CONTAINER_AGGREGATION_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.CONTAINER_AGGREGATION_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.HIERARCHY_NAMES)) {
					this.concatenateVisibility(
						IVisibility.HIERARCHY_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.HIERARCHY_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.HIERARCHY_DISPLAY_ELEMENTS,
						isSelected);
				}
				else if (action.equals(Resources.FIELD_NAMES)) {
					this.concatenateVisibility(
						IVisibility.FIELD_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.METHOD_NAMES)) {
					this.concatenateVisibility(
						IVisibility.METHOD_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.FULLY_QUALIFIED_NAMES)) {
					this.concatenateVisibility(
						IVisibility.FULLY_QUALIFIED_NAMES,
						isSelected);
				}
				else if (action.equals(Resources.GHOST_ENTITIES_DISPLAY)) {
					this.concatenateVisibility(
						IVisibility.GHOST_ENTITIES_DISPLAY,
						isSelected);
				}
			}
		}
	}
	private void selectAllCheckboxes(final Button source, final boolean aStatus) {
		final OptionPanel optionPanel =
			(OptionPanel) source
				.getParent()
				.getParent()
				.getParent()
				.getParent()
				.getParent();
		// TODO: Should all this mechanics be there?
		final EmbeddedPanel panel =
			(EmbeddedPanel) ((CollapsablePanel) ((EmbeddedPanel) optionPanel
				.getComponent(0)).getComponent(1)).getComponent(1);
		for (int i = 0; i < panel.getComponents().length; i++) {
			final AbstractButton ab = (AbstractButton) panel.getComponents()[i];
			if (ab instanceof CheckBox) {
				ab.setSelected(aStatus);
			}
		}
	}
}

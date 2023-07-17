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
package ptidej.viewer.ui.panel;

import java.awt.BorderLayout;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import ptidej.ui.IVisibility;
import ptidej.viewer.action.ActionsRepository;
import ptidej.viewer.action.OptionAction;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.Button;
import ptidej.viewer.widget.CheckBox;
import ptidej.viewer.widget.CollapsablePanel;
import ptidej.viewer.widget.EmbeddedPanel;

public final class OptionPanel extends JPanel implements IGraphModelListener {
	private static final long serialVersionUID = 1L;
	public OptionPanel() {
		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		final JPanel groupPanel = new JPanel();
		final ButtonGroup group = new ButtonGroup();
		final Button button1 =
			this.createRadioButton(
				Resources.SELECT_ALL,
				Resources.VIEWER_OPTIONS,
				false);
		group.add(button1);
		groupPanel.add(button1);
		final Button button2 =
			this.createRadioButton(
				Resources.UNSELECT_ALL,
				Resources.VIEWER_OPTIONS,
				false);
		group.add(button2);
		groupPanel.add(button2);
		final EmbeddedPanel globalPanel = new EmbeddedPanel();
		globalPanel.add(groupPanel, globalPanel.getInsetConstraint());
		panel.addCollapsablePanel("Global", globalPanel);

		final EmbeddedPanel optionPanel = new EmbeddedPanel();
		optionPanel.addCheckBox(
			Resources.CREATION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.CREATION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.USE_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.USE_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.ASSOCIATION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.ASSOCIATION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.AGGREGATION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.AGGREGATION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.COMPOSITION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.COMPOSITION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.CONTAINER_AGGREGATION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.CONTAINER_AGGREGATION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.CONTAINER_COMPOSITION_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.CONTAINER_COMPOSITION_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.HIERARCHY_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.HIERARCHY_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.FIELD_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.METHOD_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.FULLY_QUALIFIED_NAMES,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		optionPanel.addCheckBox(
			Resources.GHOST_ENTITIES_DISPLAY,
			OptionPanel.class,
			Resources.VIEWER_OPTIONS,
			false,
			false,
			false,
			true);
		panel.addCollapsablePanel("Individual", optionPanel);
	}
	private Button createRadioButton(
		final String strKey,
		final String strGroup,
		final boolean isSelected) {

		final Button radio =
			new Button(Resources.getRadioText(strKey, OptionPanel.class));
		radio.setSelected(isSelected);
		radio.setEnabled(false);
		radio.setActionCommand(strKey);

		DesktopPane.getInstance().addSourceModelListener(radio);

		final AbstractAction action =
			ActionsRepository.getInstance().getAction(strKey, strGroup);
		radio.addActionListener(action);

		return radio;
	}
	public void graphModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		// Yann 2014/06/20: Hack!!!
		// I must tell the OptionAction not to show a warning
		// when the ItemEvent is coming from a change of model.
		OptionAction.getInstance().setEnabled(false);

		final int visibility =
			((AbstractRepresentationWindow) aSourceModelEvent
				.getRepresentation()).getVisibleElements();
		final EmbeddedPanel panel =
			(EmbeddedPanel) ((CollapsablePanel) ((EmbeddedPanel) this
				.getComponent(0)).getComponent(1)).getComponent(1);
		for (int i = 0; i < panel.getComponents().length; i++) {
			final CheckBox checkBox = (CheckBox) panel.getComponents()[i];
			final String action = checkBox.getActionCommand();

			if (action.equals(Resources.CREATION_NAMES)
					&& (visibility & IVisibility.CREATION_NAMES) == IVisibility.CREATION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.CREATION_DISPLAY)
					&& (visibility & IVisibility.CREATION_DISPLAY_ELEMENTS) == IVisibility.CREATION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.USE_NAMES)
					&& (visibility & IVisibility.USE_NAMES) == IVisibility.USE_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.USE_DISPLAY)
					&& (visibility & IVisibility.USE_DISPLAY_ELEMENTS) == IVisibility.USE_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.ASSOCIATION_NAMES)
					&& (visibility & IVisibility.ASSOCIATION_NAMES) == IVisibility.ASSOCIATION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.ASSOCIATION_DISPLAY)
					&& (visibility & IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.COMPOSITION_NAMES)
					&& (visibility & IVisibility.COMPOSITION_NAMES) == IVisibility.COMPOSITION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.COMPOSITION_DISPLAY)
					&& (visibility & IVisibility.COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.COMPOSITION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.AGGREGATION_NAMES)
					&& (visibility & IVisibility.AGGREGATION_NAMES) == IVisibility.AGGREGATION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.AGGREGATION_DISPLAY)
					&& (visibility & IVisibility.AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.AGGREGATION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.CONTAINER_COMPOSITION_NAMES)
					&& (visibility & IVisibility.CONTAINER_COMPOSITION_NAMES) == IVisibility.CONTAINER_COMPOSITION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.CONTAINER_COMPOSITION_DISPLAY)
					&& (visibility & IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.CONTAINER_AGGREGATION_NAMES)
					&& (visibility & IVisibility.CONTAINER_AGGREGATION_NAMES) == IVisibility.CONTAINER_AGGREGATION_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.CONTAINER_AGGREGATION_DISPLAY)
					&& (visibility & IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.HIERARCHY_NAMES)
					&& (visibility & IVisibility.HIERARCHY_NAMES) == IVisibility.HIERARCHY_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.HIERARCHY_DISPLAY)
					&& (visibility & IVisibility.HIERARCHY_DISPLAY_ELEMENTS) == IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.FIELD_NAMES)
					&& (visibility & IVisibility.FIELD_NAMES) == IVisibility.FIELD_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.METHOD_NAMES)
					&& (visibility & IVisibility.METHOD_NAMES) == IVisibility.METHOD_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.FULLY_QUALIFIED_NAMES)
					&& (visibility & IVisibility.FULLY_QUALIFIED_NAMES) == IVisibility.FULLY_QUALIFIED_NAMES) {

				checkBox.setSelected(true);
			}
			else if (action.equals(Resources.GHOST_ENTITIES_DISPLAY)
					&& (visibility & IVisibility.GHOST_ENTITIES_DISPLAY) == IVisibility.GHOST_ENTITIES_DISPLAY) {

				checkBox.setSelected(true);
			}
			else {
				checkBox.setSelected(false);
			}
		}

		OptionAction.getInstance().setEnabled(true);
	}
	public void graphModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {

		// Yann 2014/06/20: Hack!!!
		// this.graphModelAvailable(aSourceModelEvent);
		// Yann 2015/02/06: Hack => Bug
		// Because I did not do anything, the visibility the graph was not set correctly.
		final int visibility =
			((AbstractRepresentationWindow) aSourceModelEvent
				.getRepresentation()).getVisibleElements();
		aSourceModelEvent
			.getRepresentation()
			.getSourceGraph()
			.setVisibleElements(visibility);
	}
	public void graphModelUnavailable() {
	}
}

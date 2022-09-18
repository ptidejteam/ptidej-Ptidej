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
/*
 * (c) Copyright 2001, 2002 Yann-Gaël Guéhéneuc, Ecole des Mines de Nantes and
 * Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of Yann-Gaël
 * Guéhéneuc, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * YANN-GAEL GUEHENEUC IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer.awt.entities;

import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import ptidej.ui.ISelectable;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.kernel.Entity;
import ptidej.viewer.awt.ActionListener;
import ptidej.viewer.awt.IAWTRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.layout.PercentLayout;
import ptidej.viewer.layout.TFlowLayout;
import ptidej.viewer.layout.VFlowLayout;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2007/08/19
 */
public final class ListPanel extends Panel {
	private static final Label NO_GRAPH_MODEL_LABEL = new Label(
		Constants.NO_GRAPH_MODEL_LABEL,
		Label.CENTER);
	private static final long serialVersionUID = 1L;

	private static ListPanel UniqueInstance;
	public static ListPanel getInstance() {
		if (ListPanel.UniqueInstance == null) {
			ListPanel.UniqueInstance = new ListPanel();
		}
		return ListPanel.UniqueInstance;
	}

	private final Map mapEntitiesLabels;
	private IAWTRepresentation representation;
	private final ScrollPane scrollPane;

	private ListPanel() {
		this.setLayout(new PercentLayout());

		// I build the ScrollPane into which all the buttons and labels
		// will be displayed.
		this.scrollPane = new ScrollPane();
		this.scrollPane.getHAdjustable().setUnitIncrement(20);
		this.scrollPane.getVAdjustable().setUnitIncrement(20);
		this.add(this.scrollPane, new PercentLayout.Constraint(0, 0, 100, 100));

		// I store the head labels to change 
		// their colours as selection goes...
		this.mapEntitiesLabels = new HashMap();

		// Yann 2013/09/28: Initialisation!
		this.graphModelUnavailable();
	}
	public void constituentSelected(final GraphEvent aGraphEvent) {
		final ISelectable selectable = (ISelectable) aGraphEvent.getSource();
		if (selectable instanceof Entity) {
			final Label label =
				(Label) this.mapEntitiesLabels.get(((Entity) selectable)
					.getName());
			// Yann 2013/09/29: Missing entity...
			// It is possible that this window does not have the entity
			// in its map because only part of all the entities are 
			// shown in the window while another has been selected,
			// for example when using the dual hierarchical window...
			if (label != null) {
				label.setForeground(Constants.HIGHLIGHT_COLOR);
			}
		}
	}
	public void constituentUnSelected(final GraphEvent aGraphEvent) {
		final ISelectable selectable = (ISelectable) aGraphEvent.getSource();
		if (selectable instanceof Entity) {
			final Label label =
				(Label) this.mapEntitiesLabels.get(((Entity) selectable)
					.getName());
			// Yann 2013/09/29: Missing entity...
			// It is possible that this window does not have the entity
			// in its map because only part of all the entities are 
			// shown in the window while another has been selected,
			// for example when using the dual hierarchical window...
			if (label != null) {
				label.setForeground(Constants.NORMAL_COLOR);
			}
		}
	}
	public void graphModelAvailable(final SourceAndGraphModelEvent aSourceEvent) {
		final IAWTRepresentation newRepresentation =
			(IAWTRepresentation) aSourceEvent.getRepresentation();
		this.representation = newRepresentation;

		// I build the new content.
		final Entity[] entities =
			this.representation.getSourceGraph().listEntities();

		if (entities.length > 0) {
			// I remove all the current components of the window.
			this.scrollPane.removeAll();

			// Yann 2004/03/26: Sort.
			// I sort the array of solutions according to
			// their degree of similarity. This changes
			// the original array of entities!
			Arrays.sort(entities, new Comparator() {
				public int compare(final Object s1, final Object s2) {
					return ((Entity) s1).getName().compareTo(
						((Entity) s2).getName());
				}
			});

			final Panel entityPanel =
				new Panel(new VFlowLayout(TFlowLayout.VCENTER));
			this.scrollPane.add(entityPanel);

			// I build the different instances of Panel, Button,
			// and Label that will display the solutions and the
			// popup menus that go with them.
			for (int i = 0; i < entities.length; i++) {
				final Entity entity = entities[i];
				final String entityName = entity.getName();
				final Label entityLabel = new Label(entityName);
				if (entity.isSelected()) {
					entityLabel.setForeground(Constants.HIGHLIGHT_COLOR);
				}
				else {
					entityLabel.setForeground(Constants.NORMAL_COLOR);
				}
				// I create an instance of PopupMenu that will be displayed
				// when clicking on the solution number and percentage.
				// This instance of PopupMenu, as for name the hash code
				// (unique) number of the Solution with which it is associated.
				final PopupMenu goToMenu = new PopupMenu();
				final MenuItem menuItem = new MenuItem(ActionListener.GO_TO);
				menuItem.addActionListener(new ActionListener(
					this.representation.getAWTCanvas(),
					this.representation.getCanvas(),
					this.representation.getSourceGraph()));
				goToMenu.add(menuItem);
				entityLabel.add(goToMenu);
				this.mapEntitiesLabels.put(entityName, entityLabel);
				entityPanel.add(entityLabel);
				entityPanel.validate();

				entityLabel.addMouseListener(new MouseAdapter() {
					public void mousePressed(final MouseEvent e) {
						if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
							entity.isSelected(!entity.isSelected());
						}
						else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
							final Label origin = ((Label) e.getSource());
							goToMenu.setName(entity.getName());
							// No need to complicate things!
							//	goToMenu.show(origin, origin.getX()
							//			- (int) ListPanel.this.scrollPane
							//				.getScrollPosition()
							//				.getX(), ((Label) e.getSource()).getY()
							//			- (int) ListPanel.this.scrollPane
							//				.getScrollPosition()
							//				.getY());
							goToMenu.show(origin, e.getX(), e.getY());
						}
					}
				});
			}
		}

		this.scrollPane.validate();

		// I must refresh the window.
		this.scrollPane.validate();
		this.scrollPane.repaint();
	}
	public void graphModelChanged(final SourceAndGraphModelEvent aSourceEvent) {
		this.graphModelUnavailable();
		this.graphModelAvailable(aSourceEvent);
	}
	public void graphModelUnavailable() {
		this.scrollPane.add(ListPanel.NO_GRAPH_MODEL_LABEL);

		// I must refresh the window.
		this.scrollPane.validate();
		this.scrollPane.repaint();
	}
}

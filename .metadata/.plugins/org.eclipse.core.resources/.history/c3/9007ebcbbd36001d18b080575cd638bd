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
package ptidej.viewer.awt.occurrences;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import padl.kernel.IGhost;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceComponent;
import ptidej.ui.IDrawable;
import ptidej.ui.ISelectable;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.canvas.event.CanvasEvent;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.ModelGraph;
import ptidej.viewer.GroupOccurrence;
import ptidej.viewer.awt.ActionListener;
import ptidej.viewer.awt.IAWTRepresentation;
import ptidej.viewer.awt.Separator;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.layout.PercentLayout;
import ptidej.viewer.layout.TFlowLayout;
import ptidej.viewer.layout.VFlowLayout;
import util.multilingual.MultilingualManager;

public final class ListPanel extends Panel implements IGraphModelListener {
	private static final Label NO_OCCURRENCES_LABEL = new Label(
		Constants.NO_OCCURRENCES_LABEL,
		Label.CENTER);

	private static final long serialVersionUID = 1L;
	public static class PanelListener extends ActionListener {
		public PanelListener(
			final AWTCanvas anAWTCanvas,
			final Canvas aCanvas,
			final ModelGraph aModelGraph) {

			super(anAWTCanvas, aCanvas, aModelGraph);
		}
		public void actionPerformed(final ActionEvent e) {
			super.actionPerformed(e);

			final PopupMenu popupMenu =
				(PopupMenu) ((MenuItem) e.getSource()).getParent();
			final String sourceName = popupMenu.getName();

			// First, I get the panel enclosing all the solutions.
			final Panel panel =
				(Panel) ((Component) ((PopupMenu) ((MenuItem) e.getSource())
					.getParent()).getParent()).getParent().getParent();

			// Then, I get all the components in this panel: Either
			// other panels containing labels (solutions) or
			// separators.
			final Component[] components = panel.getComponents();

			if (e.getActionCommand().equals(
				ptidej.viewer.awt.ActionListener.GO_TO)) {
				final Constituent[] constituents =
					this.modelGraph.listEntities();

				for (int i = 0; i < constituents.length; i++) {
					if (constituents[i].getName().equals(sourceName)) {
						this.awtCanvas.goTo(constituents[i].getPosition());
					}
				}
			}
			else if (e.getActionCommand().equals(Constants.FOLD_UNFOLD)) {

				for (int j = 0; j < components.length; j++) {
					// For each component in the panel, if its name
					// matches the name of a solution (hash-code)
					// then I make its subcomponents (labels)
					// invisible but the first one (solution name).
					if (components[j].getName().equals(sourceName)) {
						final Component[] subComponents =
							((Panel) components[j]).getComponents();
						for (int k = 1; k < subComponents.length; k++) {
							subComponents[k].setVisible(!subComponents[k]
								.isVisible());
						}
					}
				}

				// Finally, I look for the enclosing Frame and pack it.
				Component frame = panel;
				while (!(frame instanceof Frame)) {
					frame = frame.getParent();
				}
				((Frame) frame).pack();
			}
			else if (e.getActionCommand().equals(Constants.FOLD_UNFOLD_ALL)) {

				boolean isFirstComponentChecked = false;
				boolean firstComponentVisibility = false;
				for (int j = 0; j < components.length; j++) {
					// For each component in the panel,
					// if is not a separator, then I make its
					// subcomponents invisble.
					if (components[j] instanceof Panel) {
						final Component[] subComponents =
							((Panel) components[j]).getComponents();
						for (int k = 1; k < subComponents.length; k++) {
							if (!isFirstComponentChecked) {
								isFirstComponentChecked = true;
								firstComponentVisibility =
									!subComponents[k].isVisible();
							}
							subComponents[k]
								.setVisible(firstComponentVisibility);
						}
					}
				}

				// Finally, I look for the enclosing Frame and pack it.
				Component frame = panel;
				while (!(frame instanceof Frame)) {
					frame = frame.getParent();
				}
				((Frame) frame).pack();
			}
			else if (e.getActionCommand().equals(Constants.FOLD_UNFOLD_OBJECT)) {

				// Yann 2004/08/08: Fold!
				// I unfold all the components before
				// (eventually) folding them.
				this.unFoldAll(components);

				for (int j = 0; j < components.length; j++) {
					// For each component in the panel,
					// if is not a separator, then I check if it
					// contains Object, if it does, I make all
					// the subcomponents invisible.
					if (components[j] instanceof Panel) {
						boolean doesContainObject = false;
						final Component[] subComponents =
							((Panel) components[j]).getComponents();
						for (int k = 1; k < subComponents.length
								&& !doesContainObject; k++) {

							doesContainObject =
								(((Label) subComponents[k]).getText().indexOf(
									" Object") > 0)
										|| (((Label) subComponents[k])
											.getText()
											.indexOf(".Object") > 0);
						}
						if (doesContainObject) {
							for (int k = 1; k < subComponents.length; k++) {

								subComponents[k].setVisible(!subComponents[k]
									.isVisible());
							}
						}
					}
				}

				// Finally, I look for the enclosing Frame and pack it.
				Component frame = panel;
				while (!(frame instanceof Frame)) {
					frame = frame.getParent();
				}
				((Frame) frame).pack();
			}
			else if (e.getActionCommand().equals(Constants.FOLD_UNFOLD_GHOSTS)) {

				// Yann 2004/08/08: Fold!
				// I unfold all the components before
				// (eventually) folding them.
				this.unFoldAll(components);

				for (int j = 0; j < components.length; j++) {
					// For each component in the panel,
					// if is not a separator, then I check if it
					// contains Object, if it does, I make all
					// the subcomponents invisible.
					if (components[j] instanceof Panel) {
						boolean doesContainGhost = false;
						final Component[] subComponents =
							((Panel) components[j]).getComponents();
						for (int k = 1; k < subComponents.length
								&& !doesContainGhost; k++) {

							final String actorName =
								((Label) subComponents[k]).getText();
							doesContainGhost =
								this.modelGraph
									.getAbstractModel()
									.getTopLevelEntityFromID(
										actorName.substring(actorName
											.lastIndexOf('=') + 2)) instanceof IGhost;
						}
						if (doesContainGhost) {
							for (int k = 1; k < subComponents.length; k++) {

								subComponents[k].setVisible(!subComponents[k]
									.isVisible());
							}
						}
					}
				}

				// Finally, I look for the enclosing Frame and pack it.
				Component frame = panel;
				while (!(frame instanceof Frame)) {
					frame = frame.getParent();
				}
				((Frame) frame).pack();
			}
			else if (e.getActionCommand().equals(Constants.SHOW_HIDE)) {
				// The instance of MenuItem that has been selected
				// as, for name, the hash code number of the corresponding
				// instance of Solution.
				final int hashCode = Integer.parseInt(sourceName);
				final IDrawable[] drawable =
					this.canvas.getBackgroundElements();

				for (int i = 0; i < drawable.length; i++) {
					if (drawable[i] instanceof GroupOccurrence
							&& ((GroupOccurrence) drawable[i])
								.getSolution()
								.hashCode() == hashCode) {

						final ISelectable selectable =
							(ISelectable) drawable[i];
						selectable.isSelected(!selectable.isSelected());

						// I must set the size of the AWTCanvas accordingly to the
						// new Canvas size to make sure that the Scrollbars are
						// updated correctly.
						this.awtCanvas.setSize(this.canvas.getDimension());

						// I stop the iteration.
						i = drawable.length;
					}
				}
			}

			// Finally, I refresh the whole stuff (i.e., the instances of
			// the ScrollPane, of the AWTCanvas, and of the Canvas.
			this.awtCanvas.getParent().repaint();
		}
		private void unFoldAll(final Component[] components) {
			for (int j = 0; j < components.length; j++) {
				// For each component in the panel,
				// if is not a separator, then I check if it
				// contains Object, if it does, I make all
				// the subcomponents invisible.
				if (components[j] instanceof Panel) {
					final Component[] subComponents =
						((Panel) components[j]).getComponents();
					for (int k = 1; k < subComponents.length; k++) {
						subComponents[k].setVisible(true);
					}
				}
			}
		}
	}
	//	private static SolutionPanel UniqueInstance;
	//	public static SolutionPanel getConstraintResultFrame(
	//		final IAWTRepresentation aRepresentation,
	//		final SolutionListener actionListener) {
	//
	//		if (SolutionPanel.UniqueInstance == null) {
	//			SolutionPanel.UniqueInstance =
	//				new SolutionPanel(aRepresentation, actionListener);
	//		}
	//		return SolutionPanel.UniqueInstance;
	//	}

	private final Map mapSolutionNumberLabels;
	private IAWTRepresentation representation;
	private final ScrollPane scrollPane;

	public ListPanel(final IAWTRepresentation anAWTRepresentation) {
		this.setLayout(new PercentLayout());

		this.representation = anAWTRepresentation;

		// I build the ScrollPane into which all the buttons and labels
		// will be displayed.
		this.scrollPane = new ScrollPane();
		this.scrollPane.setBackground(Color.lightGray);
		this.scrollPane.getHAdjustable().setUnitIncrement(20);
		this.scrollPane.getVAdjustable().setUnitIncrement(20);
		this.add(this.scrollPane, new PercentLayout.Constraint(0, 0, 100, 100));

		// I stock the head labels to change their
		// colors as selection goes...
		this.mapSolutionNumberLabels = new HashMap();

		// I build the window content.
		this.build();
	}
	public void backgroundElementSelected(final CanvasEvent aCanvasEvent) {
		final Occurrence solution =
			((GroupOccurrence) aCanvasEvent.getSource()).getSolution();
		final Label label = (Label) this.mapSolutionNumberLabels.get(solution);
		// Yann 2013/09/29: Missing solution...
		// It is possible that this window does not have the solution
		// in its map because only part of all the solutions are 
		// shown in the window while another has been selected,
		// for example when using the dual hierarchical window...
		if (label != null) {
			label.setForeground(Constants.HIGHLIGHT_COLOR);
		}
	}
	public void backgroundElementUnSelected(final CanvasEvent aCanvasEvent) {
		final Occurrence solution =
			((GroupOccurrence) aCanvasEvent.getSource()).getSolution();
		final Label label = (Label) this.mapSolutionNumberLabels.get(solution);
		// Yann 2013/09/29: Missing solution...
		// It is possible that this window does not have the solution
		// in its map because only part of all the solutions are 
		// shown in the window while another has been selected,
		// for example when using the dual hierarchical window...
		if (label != null) {
			label.setForeground(Constants.NORMAL_COLOR);
		}
	}
	private void build() {
		// I get the solutions from my parent.
		final Occurrence[] solutions = this.representation.getOccurrences();
		final IDrawable[] drawables =
			this.representation.getCanvas().getBackgroundElements();

		// I create an instance of ScrollPane into which I mut
		// several instances of Panel, Button, and Label.
		if (solutions != null && solutions.length > 0) {
			final Panel solutionPanel =
				new Panel(new VFlowLayout(TFlowLayout.VCENTER));
			this.scrollPane.add(solutionPanel);

			// Yann 2004/05/14: Too much fun!
			// I now create only one instance of each popup
			// menus instead of one per label (which would
			// make Ptidej crash with big numbers of solutions!).

			final PanelListener panelListener =
				new PanelListener(
					this.representation.getAWTCanvas(),
					this.representation.getCanvas(),
					this.representation.getSourceGraph());

			// I create an instance of PopupMenu that will be displayed
			// when clicking on the solution number and percentage.
			// This instance of PopupMenu, as for name the hash code
			// (unique) number of the Solution with which it is associated.
			final PopupMenu solutionMenu = new PopupMenu();
			MenuItem menuItem = new MenuItem(Constants.SHOW_HIDE);
			menuItem.addActionListener(panelListener);
			solutionMenu.add(menuItem);
			menuItem = new MenuItem(Constants.FOLD_UNFOLD);
			menuItem.addActionListener(panelListener);
			solutionMenu.add(menuItem);
			menuItem = new MenuItem(Constants.FOLD_UNFOLD_ALL);
			menuItem.addActionListener(panelListener);
			solutionMenu.add(menuItem);
			menuItem = new MenuItem(Constants.FOLD_UNFOLD_OBJECT);
			menuItem.addActionListener(panelListener);
			solutionMenu.add(menuItem);
			menuItem = new MenuItem(Constants.FOLD_UNFOLD_GHOSTS);
			menuItem.addActionListener(panelListener);
			solutionMenu.add(menuItem);

			// Yann 2004/03/26: Sort.
			// I sort the array of solutions according to
			// their degree of similarity.
			Arrays.sort(solutions, new Comparator() {
				public int compare(final Object s1, final Object s2) {
					return ((Occurrence) s2).getConfidence()
							- ((Occurrence) s1).getConfidence();
				}
			});

			// I build the different instances of Panel, Button,
			// and Label that will display the solutions and the
			// popup menus that go with them.
			final StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < solutions.length; i++) {
				final Panel panel =
					new Panel(new VFlowLayout(TFlowLayout.VCENTER));
				panel.setName(Integer.toString(solutions[i].hashCode()));
				solutionPanel.add(panel);

				// I create an instance of Label for the solution header.
				buffer.setLength(0);
				buffer.append(MultilingualManager.getString(
					"Lbl_SOLUTION_LABEL",
					ListPanel.class,
					new Object[] { Integer.toString(solutions[i].getNumber()),
							Integer.toString(solutions[i].getConfidence()),
							solutions[i].getDisplayName() }));
				/*
				buffer.append("Micro-architecture ");
				buffer.append(Integer.toString(solutions[i].getNumber()));
				buffer.append(" similar at ");
				buffer.append(Integer.toString(solutions[i].getPercentage()));
				buffer.append("% with ");
				buffer.append(solutions[i].getName());
				*/
				final Label solutionLabel = new Label(buffer.toString());
				final int solutionNumber = i;

				// Yann 2004/08/08: Search!
				// I search among all the background constituents
				// the group solution corresponding to the current
				// solution to see if it is selected or not.
				// TODO: Need to improve the efficiency of the
				// Solution-Window related algorithms big time!
				boolean found = false;
				for (int j = 0; j < drawables.length && !found; j++) {
					if ((drawables[j] instanceof GroupOccurrence)
							&& ((GroupOccurrence) drawables[j])
								.getSolution()
								.equals(solutions[i])) {

						final ISelectable selectable =
							(ISelectable) drawables[j];
						if (selectable.isSelected()) {
							solutionLabel
								.setForeground(Constants.HIGHLIGHT_COLOR);
						}
						else {
							solutionLabel.setForeground(Constants.NORMAL_COLOR);
						}
						found = true;
					}
				}
				solutionLabel.addMouseListener(new MouseAdapter() {
					public void mousePressed(final MouseEvent e) {
						if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

							solutionMenu.setName(Integer
								.toString(solutions[solutionNumber].hashCode()));
							solutionMenu.show(solutionLabel, e.getX(), e.getY());
						}
					}
				});
				solutionLabel.add(solutionMenu);
				panel.add(solutionLabel);
				panel.validate();
				this.mapSolutionNumberLabels.put(solutions[i], solutionLabel);

				// I create instances of Label for the solution components.
				final Iterator enumeration =
					solutions[i].getComponents().iterator();
				while (enumeration.hasNext()) {
					final OccurrenceComponent solutionComponent =
						(OccurrenceComponent) enumeration.next();

					// I create an instance of Label.
					buffer.setLength(0);
					buffer.append(solutionComponent.getName());
					buffer.append(" = ");
					buffer.append(solutionComponent.getValue());

					// Yann 2003/04/30: Beauty is in the eye of...
					// I use a tokenizer to cut multiple lines.
					final StringTokenizer tokenizer =
						new StringTokenizer(buffer.toString(), "\n");

					if (tokenizer.countTokens() == 1) {
						final Label componentLabel =
							new Label(buffer.toString());
						// I create an instance of PopupMenu that will be displayed
						// when clicking on the solution number and percentage.
						// This instance of PopupMenu, as for name the hash code
						// (unique) number of the Solution with which it is associated.
						// TODO Add a popup menu only to component corresponding to entities in the model, not to the name of the motif, etc.
						final PopupMenu goToMenu = new PopupMenu();
						menuItem = new MenuItem(ActionListener.GO_TO);
						menuItem.addActionListener(panelListener);
						goToMenu.add(menuItem);
						componentLabel.addMouseListener(new MouseAdapter() {
							public void mousePressed(final MouseEvent e) {
								if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
									goToMenu.setName(solutionComponent
										.getDisplayValue());
									goToMenu.show(
										componentLabel,
										e.getX(),
										e.getY());
								}
							}
						});
						componentLabel.add(goToMenu);
						panel.add(componentLabel);
						panel.validate();
					}
					else {
						while (tokenizer.hasMoreTokens()) {
							final Label componentLabel =
								new Label(tokenizer.nextToken().replaceAll(
									"\t",
									"    "));
							panel.add(componentLabel);
						}
					}
				}

				if (i < solutions.length - 1) {
					solutionPanel.add(new Separator());
				}
			}
		}
		else {
			this.scrollPane.add(ListPanel.NO_OCCURRENCES_LABEL);
		}
	}
	public void constituentSelected(final GraphEvent aGraphEvent) {
	}
	public void constituentUnSelected(final GraphEvent aGraphEvent) {
	}
	public void foregroundElementSelected(final CanvasEvent aCanvasEvent) {
	}
	public void foregroundElementUnSelected(final CanvasEvent aCanvasEvent) {
	}
	public void graphModelAvailable(final SourceAndGraphModelEvent aSourceEvent) {
		this.graphModelChanged(aSourceEvent);
	}
	public void graphModelChanged(final SourceAndGraphModelEvent aSourceEvent) {
		this.representation =
			(IAWTRepresentation) aSourceEvent.getRepresentation();

		// I remove all the current components of the window.
		this.scrollPane.removeAll();

		// I build the new content.
		this.build();

		// I must refresh the window.
		this.scrollPane.validate();
		this.scrollPane.repaint();
	}
	public void graphModelUnavailable() {
		this.representation = null;
	}
}

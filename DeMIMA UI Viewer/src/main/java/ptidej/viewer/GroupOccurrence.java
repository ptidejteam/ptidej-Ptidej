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
package ptidej.viewer;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import ptidej.ui.Constants;
import ptidej.ui.IVisibility;
import ptidej.ui.RGB;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.event.MouseEvent;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.IUILayout;
import ptidej.ui.occurrence.IGroupOccurrenceTip;
import ptidej.ui.occurrence.IOccurrencePrimitiveFactory;
import ptidej.ui.primitive.IButton;

public final class GroupOccurrence extends Constituent implements IVisibility {
	private final Canvas canvas;
	private final ModelGraph currentModel;
	private IGroupOccurrenceTip groupOccurrenceTip;
	private List groupRectangleButtons = new ArrayList();
	private final IUILayout layout;
	private final Occurrence occurrence;
	private final OccurrenceBuilder solutionBuilder;

	public GroupOccurrence(
		final Canvas canvas,
		final IOccurrencePrimitiveFactory primitiveFactory,
		final Occurrence solution,
		final OccurrenceBuilder solutionBuilder,
		final ModelGraph modelGraph,
		final IUILayout layout) {

		super(primitiveFactory);

		this.canvas = canvas;
		this.occurrence = solution;
		this.solutionBuilder = solutionBuilder;
		this.currentModel = modelGraph;
		this.layout = layout;

		this.canvas.GroupSolutionsNumber++;
		this.canvas.GroupSolutionsCount.put(this, Integer.valueOf(
			this.canvas.GroupSolutionsNumber));
	}
	public void build() {
		this.groupRectangleButtons.clear();

		// Yann 2007/03/30: Sign!
		// I now look for a sign in the solution,
		// to decide whether the solution is
		// positive (green) or negative (red).
		// In case I find no sign but the name
		// contains design motif, I assume it
		// is a positive solution.
		boolean isPositive;
		if (this.occurrence.getComponent(Occurrence.SIGN) != null
				&& this.occurrence
					.getComponent(Occurrence.SIGN)
					.getDisplayValue()
					.equals("Positive")
				|| this.occurrence.getComponent(Occurrence.SIGN) == null
				&& this.occurrence.getComponent(Occurrence.NAME) != null
				&& String.valueOf(
					this.occurrence
						.getComponent(Occurrence.NAME)
						.getDisplayValue()).indexOf("Design Motif") > -1) {

			// Yann 2007/04/02: Noname :-)
			// I must test if a Name exists because of the antipatterns
			isPositive = true;
		}
		else {
			isPositive = false;
		}

		final List components = this.occurrence.getComponents();
		for (int i = 0; i < components.size(); i++) {
			final OccurrenceComponent component =
				(OccurrenceComponent) components.get(i);
			// final String componentName = component.getName();
			final String componentValue = component.getDisplayValue();

			// Yann 2003/12/09: Ghost.
			// I do not create a group rectangle button for Ghost entities.
			//	if (!componentName.equals("Name")
			//		&& !componentName.equals("XCommand")
			//		&& this.currentModel.getEntity(componentValue) != null) {
			if (this.currentModel.getEntity(componentValue) != null) {
				int count = 1;
				if (this.canvas.EntityRolesCount.containsKey(componentValue)) {
					count =
						((Integer) this.canvas.EntityRolesCount
							.get(componentValue)).intValue() + 1;
				}
				this.canvas.EntityRolesCount.put(componentValue, Integer.valueOf(
					count));

				final IButton button;
				if (isPositive) {
					button =
						this
							.getPrimitiveFactory()
							.createButton(
								Integer.toString(((Integer) this.canvas.GroupSolutionsCount
									.get(this)).intValue()),
								this
									.getComponentPosition(componentValue, count),
								false,
								RGB
									.computePositivePercentagedColor(this.occurrence
										.getConfidence()));
				}
				else {
					button =
						this
							.getPrimitiveFactory()
							.createButton(
								Integer.toString(((Integer) this.canvas.GroupSolutionsCount
									.get(this)).intValue()),
								this
									.getComponentPosition(componentValue, count),
								false,
								RGB
									.computeNegativePercentagedColor(this.occurrence
										.getConfidence()));
				}

				// Yann 2003/12/09: Selection.
				// I now rebuild each button on change. The 
				// new buttons must be selected as requiered.
				button.setSelected(this.isSelected());
				this.groupRectangleButtons.add(button);
			}
		}

		// Yann 2014/05/09: Silly me!
		// It is well possible that no button was created because
		// the condition this.currentModel.getEntity(componentValue) 
		// is always null if the source graph is not draw! So, I must
		// check first before setting position or something...
		if (this.groupRectangleButtons.size() > 0) {
			this.setPosition(((IButton) this.groupRectangleButtons.get(0))
				.getPosition());

			this.reComputeDimension();
		}
		else {
			// I must remove the GroupOccurrenceTip from the Canvas
			// if there is any... by actually simulating a selection...
			// this.canvas.removeFromForeground(this.groupOccurrenceTip);
			this.isSelected(false);
		}
	}
	private Point getComponentPosition(final String entityName, final int count) {
		final Constituent constituent = this.currentModel.getEntity(entityName);

		// Yann 2003/12/09: Ghost.
		// This test is unnecessary, I must use Ghost entities instead.
		//	if (constituent == null) {
		//		return Constants.NULL_POSITION;
		//	}

		final Point point = new Point(constituent.getPosition());
		point.translate(
			-Constants.SOLUTION_FRAME_GAP.width,
			-Constants.SOLUTION_FRAME_GAP.height / 2 - Constants.FONT_HEIGHT);
		point.translate((count - 1) * Constants.FONT_WIDTH * 3, 0);

		return point;
	}
	public int getPercentage() {
		return this.occurrence.getConfidence();
	}
	public Occurrence getSolution() {
		return this.occurrence;
	}
	public boolean isShowable() {
		return this.groupRectangleButtons.size() > 0;
	}
	public void paint(final int xOffset, final int yOffset) {
		final Iterator allButtons = this.groupRectangleButtons.iterator();
		while (allButtons.hasNext()) {
			final IButton button = (IButton) allButtons.next();
			button.paint(xOffset, yOffset);
		}
	}
	public boolean processMouseEvent(MouseEvent me) {
		final int nbOfButtons = this.groupRectangleButtons.size();
		IButton button = null;
		boolean hasBeenPressed = false;

		// I check if this GroupSolution has been clicked.
		for (int buttonNumber = 0; buttonNumber < nbOfButtons
				&& hasBeenPressed == false; buttonNumber++) {

			button = (IButton) this.groupRectangleButtons.get(buttonNumber);
			hasBeenPressed = button.processMouseEvent(me);
		}

		// If one GroupSolutionButton of this GroupSolution has been clicked.
		if (hasBeenPressed) {
			this.isSelected(!this.isSelected());
			this.groupOccurrenceTip.setPosition(button.getDestination());
		}

		return hasBeenPressed;
	}
	private void reComputeDimension() {
		// Yann 2014/05/09: Dimension of the tip vs. group occurrence!
		// I should not forget that the dimension of the group
		// occurrence is that of the buttons plus the tip...

		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;
		int temp;

		final Iterator allButtons = this.groupRectangleButtons.iterator();
		while (allButtons.hasNext()) {
			final IButton button = (IButton) allButtons.next();

			temp = button.getPosition().x;
			minX = temp < minX ? temp : minX;

			temp = button.getPosition().y;
			minY = temp < minY ? temp : minY;

			temp = button.getPosition().x + button.getDimension().width;
			maxX = temp > maxX ? temp : maxX;

			temp = button.getPosition().y + button.getDimension().height;
			maxY = temp > maxY ? temp : maxY;
		}

		if (this.isSelected()) {
			temp =
				this.groupOccurrenceTip.getPosition().x
						+ this.groupOccurrenceTip.getDimension().width;
			maxX = temp > maxX ? temp : maxX;

			temp =
				this.groupOccurrenceTip.getPosition().y
						+ this.groupOccurrenceTip.getDimension().height;
			maxY = temp > maxY ? temp : maxY;
		}

		this.setDimension(new Dimension(maxX - minX, maxY - minY));
	}
	protected void setDimensionSpecifics(final Dimension dimension) {
	}
	protected void setPositionSpecifics(final Point position) {
	}
	protected void setSelectedSpecifics(boolean isSelected) {
		// I check if this GroupSolution had been selected already.
		if (isSelected) {
			// I get the position of the extreme right bottom button.
			final Point tipPosition =
				((IButton) this.groupRectangleButtons
					.get(this.groupRectangleButtons.size() - 1))
					.getDestination();

			// I build a tip if needed.
			if (this.groupOccurrenceTip == null) {
				// Yann 2007/04/02: Ghosts again...
				// I meet an interesting problem: most models
				// do not include any ghost entities. Thus,
				// no Ghost exists and when doing the layout
				// with the visibility set to show Ghosts, the
				// layout algorithm may not work properly.
				// Moreover, I cannot just prevent Ghost to be
				// shown at all because some models may contain
				// ghosts... I choose this solution for the
				// sake of simplicity.
				final LaidoutModelGraph modelGraph =
					new LaidoutModelGraph(
						Builder.getCurrentBuilder((IOccurrencePrimitiveFactory) this
							.getPrimitiveFactory()),
						this.solutionBuilder.getMicroArchitectureModel(
							this.occurrence,
							this.currentModel.getAbstractModel()),
						this.layout);
				modelGraph.construct();
				modelGraph.setVisibleElements(this.getVisibleElements());
				modelGraph.build();
				this.groupOccurrenceTip =
					((IOccurrencePrimitiveFactory) this.getPrimitiveFactory())
						.createGroupOccurrenceTip(tipPosition,
						// modelGraph,
							this.toString(),
							Constants.FOREGROUND_COLOR);
			}
			else {
				this.groupOccurrenceTip.setPosition(tipPosition);
			}
			this.canvas.addToForeground(this.groupOccurrenceTip);
			this.reComputeDimension();
		}
		else {
			this.canvas.removeFromForeground(this.groupOccurrenceTip);
			this.reComputeDimension();
		}

		// Accordingly, I "press" or "unpress" the GroupSolutionButtons.
		final Iterator allButtons = this.groupRectangleButtons.iterator();
		while (allButtons.hasNext()) {
			final IButton button = (IButton) allButtons.next();
			button.setSelected(isSelected);
		}
	}
	protected void setVisibleElementsSpecifics(final int visibility) {
		if (this.groupOccurrenceTip != null) {
			this.groupOccurrenceTip.setVisibleElements(visibility);
		}
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Micro-architecture ");
		buffer.append(this.occurrence.getNumber());
		buffer.append(" similar at ");
		buffer.append(this.occurrence.getConfidence());
		buffer.append("% with ");
		buffer.append(this.occurrence.getName());
		buffer.append('\n');

		final Iterator allComponents =
			this.occurrence.getComponents().iterator();
		while (allComponents.hasNext()) {
			buffer.append('\t');
			buffer.append(((OccurrenceComponent) allComponents.next())
				.toString());
			buffer.append('\n');
		}

		return buffer.toString();
	}
}

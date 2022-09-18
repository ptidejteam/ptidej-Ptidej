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
package ptidej.ui.kernel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import ptidej.ui.Constants;
import ptidej.ui.IVisibility;
import ptidej.ui.event.ISelectionListener;
import ptidej.ui.event.MouseEvent;
import ptidej.ui.kernel.builder.Builder;

public class ModelGraph implements IVisibility {
	private static final Set EMPTY_SET = new HashSet();

	private IAbstractModel abstractModel;
	private Builder builder;
	private Dimension dimension;
	// I manage a list of constituents (entities + elements)
	// to make sure I can paint the elements first, the entities second.
	protected Constituent[] graphModelConstituents;
	private String graphModelName;
	private final List listOfSelectionListeners = new ArrayList();
	private Set setOfEntitiesToDisplay;
	private Set setOfEntitiesToSelect;
	private int visibility;

	public ModelGraph(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel) {

		this.reset(aBuilder, anAbstractModel, null, null);
	}
	public ModelGraph(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final Set aSetOfEntitiesToDisplay) {

		this.reset(aBuilder, anAbstractModel, aSetOfEntitiesToDisplay, null);
	}
	public ModelGraph(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final Set aSetOfEntitiesToDisplay,
		final Set aSetOfEntitiesToSelect) {

		this.reset(
			aBuilder,
			anAbstractModel,
			aSetOfEntitiesToDisplay,
			aSetOfEntitiesToSelect);
	}
	public void addSelectionListener(
		final ISelectionListener aSelectionListener) {
		// Yann 2014/03/28: Reset!
		// Because now I can reset a ModelGraph, I must store
		// any listeners so that I can attach them back to the
		// graphModelConstituents when doing reset() and then
		// construct().
		// Yann 2014/04/25: Weirdness!
		// This code existed because ModelGraph was a subclass
		// of Constituent... which it does not need to be! So,
		// I remove the subclass relation and cleanup this mess.
		//	super.addSelectionListener(aSelectionListener);
		this.listOfSelectionListeners.add(aSelectionListener);

		// Yann 2004/08/08: Events!
		// The source graph listens to its 
		// constituents and forwards any event...
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i]
				.addSelectionListener(aSelectionListener);
		}
	}
	public boolean areElementsSelected() {
		boolean isSelected = true;
		for (int i = 0; i < this.graphModelConstituents.length
				&& isSelected == true; i++) {

			isSelected |= this.graphModelConstituents[i].isSelected();
		}
		return isSelected;
	}
	public void areElementsSelected(final boolean isSelected) {
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i].isSelected(isSelected);
		}
	}
	public final void build() {
		// Yann 2013/07/07: Better split
		// Nothing to do here because the
		// layouting is delegated to the
		// LayoutedModelGraph class :-)
		// Yann 2014/03/28: Actually...
		// First, I make this a template method, safer and nicer:
		this.buildSpecifics();

		// Yann 2014/04/25: Size!
		// Now, I compute the size of this model graph
		// based on the position and dimensions of all
		// its constituents.
		this.computeDimension();

		// Then, I keep track of the selected entities to reselect
		// them if needed after, for example, reseting the model.
		final Iterator iterator = this.setOfEntitiesToSelect.iterator();
		while (iterator.hasNext()) {
			final IConstituent firstClassEntity =
				(IConstituent) iterator.next();
			final Constituent graphConstituent =
				this.getEntity(firstClassEntity.getDisplayID());
			if (graphConstituent != null && !graphConstituent.isSelected()) {
				// We are not trying to work with an entity that
				// is not being displayed currently (e.g., a Ghost).
				// We are not looping by selecting again and over
				// again an already-selected constituent...
				graphConstituent.isSelected(true);
			}
		}
	}
	protected void buildSpecifics() {
		// Noting to do.
	}
	private void computeDimension() {
		int width = 0;
		int height = 0;

		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			final Constituent constituent = this.graphModelConstituents[i];

			final int cx = constituent.getPosition().x;
			final int cy = constituent.getPosition().y;
			final int cwidth = constituent.getDimension().width;
			final int cheight = constituent.getDimension().height;

			int tempWidth = cx + cwidth + Constants.CANVAS_INNER_GAP.width;
			width = width < tempWidth ? tempWidth : width;

			int tempHeight = cy + cheight + Constants.CANVAS_INNER_GAP.height;
			height = height < tempHeight ? tempHeight : height;
		}

		this.dimension.setSize(width, height);
	}
	public void construct() {
		// First, I reset the builder.
		this.builder.initialize();

		// First, I build the graphic entities.
		final List modelEntityList =
			new ArrayList(this.abstractModel.getNumberOfTopLevelEntities());
		final Iterator iterator =
			this.abstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			// Yann 2005/10/07: Packages!
			// A model may now contain entities and packages.
			// Yann 2005/10/12: Iterator!
			// I have now an iterator able to iterate over a
			// specified type of constituent of a list.
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (this.setOfEntitiesToDisplay.contains(firstClassEntity)) {
				// Yann 2004/12/16: Builder!
				// I want to add more flexibility when building graphs from models.
				// I extract all the building stuff out of this class and put it in
				// a nice Builder class that everyone can subclass.
				//		if (pEntity instanceof IClass) {
				//			entity =
				//				new Class(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IClass) pEntity);
				//		}
				//		/* 2004/08/10: Sébastien Robidoux, Ward Flores */
				//		else if (pEntity instanceof IStructure) {
				//			entity =
				//				new Structure(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IStructure) pEntity);
				//		}
				//		else if (pEntity instanceof IUnion) {
				//			entity =
				//				new Union(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IUnion) pEntity);
				//		}
				//		/*END*/
				//		/* 2004/08/19: Ward Flores */
				//		else if (pEntity instanceof IEnum) {
				//			entity =
				//				new Enum(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IEnum) pEntity);
				//		}
				//		else if (pEntity instanceof IGlobalField) {
				//			entity =
				//				new GlobalField(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IGlobalField) pEntity);
				//		}
				//		/*END*/
				//		else if (pEntity instanceof IInterface) {
				//			entity =
				//				new Interface(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IInterface) pEntity);
				//		}
				//		else if (pEntity instanceof IGhost) {
				//			entity =
				//				new Ghost(
				//					this.getPrimitiveFactory(),
				//					this.builder,
				//					(IGhost) pEntity);
				//		}
				//		else {
				//			// Yann 2004/16/12: Null!
				//			// This should never happens!
				//			entity = null;
				//		}
				final Entity graphicEntity =
					this.builder.getEntity(firstClassEntity);

				// Yann 2014/03/28: Reset!
				// Because now I can reset a ModelGraph, I must store
				// any listeners so that I can attach them back to the
				// graphModelConstituents when doing reset() and then
				// construct().
				final Iterator iteratorOnSelectionListeners =
					this.getIteratorOnSelectionListeners();
				while (iteratorOnSelectionListeners.hasNext()) {
					final ISelectionListener selectionListener =
						(ISelectionListener) iteratorOnSelectionListeners
							.next();
					graphicEntity.addSelectionListener(selectionListener);
				}

				// Yann 2002/12/15: Ghost are invisible!
				// It is possible that no entity is created if
				// the entity is a ghost and the visibility is
				// set to false!
				// Yann 2004/08/08: Ghost again...
				// Managing the null value is cumbersome, I check earlier.
				// Yann 2004/12/16: Clean!
				// Ghosts are taken care of when doint the layout and painting
				// only, this removes many ugly checks like the following:
				//	if (!(entity instanceof Ghost
				//		&& (this.getVisibleElements()
				//			& IVisibility.GHOST_ENTITIES_DISPLAY)
				//			== 0)) {

				modelEntityList.add(graphicEntity);
			}
		}

		this.graphModelConstituents = new Entity[modelEntityList.size()];
		modelEntityList.toArray(this.graphModelConstituents);

		// Then I build all the associated elements.
		// And I can safely construct the hierarchies: if an exception occurs,
		// it means that a superclass is not defined or something like that...
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			((Entity) this.graphModelConstituents[i]).computeElements();
			((Entity) this.graphModelConstituents[i]).computeHierarchies();
		}
	}
	// TODO: This method should not exist!
	public IAbstractModel getAbstractModel() {
		return this.abstractModel;
	}
	public Dimension getDimension() {
		return this.dimension;
	}
	public Entity getEntity(final String name) {
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			// Yann 2007/03/14: Legacy!
			// For some legacy reasons... this.graphModelConstituents
			// actually contains any drawable constituent... including
			// fields, constructors instead of using a nice Composite.
			// So when looking for an entity, I must perform a type
			// chech else return strange things.
			// TODO: Implement the Composite design pattern
			if (this.graphModelConstituents[i] instanceof Entity
					&& this.graphModelConstituents[i].getName().equals(name)) {
				return (Entity) this.graphModelConstituents[i];
			}
		}
		return null;
	}
	public Iterator getIteratorOnSelectionListeners() {
		return this.listOfSelectionListeners.iterator();
	}
	public String getName() {
		return this.graphModelName;
	}
	public int getVisibleElements() {
		return this.visibility;
	}
	// TODO: Should this method really exist?
	public Entity[] listEntities() {
		// TODO: Implement a caching mechanism.
		final List entityList = new ArrayList();
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			// Yann 2007/03/14: Legacy!
			// For some legacy reasons... this.graphModelConstituents
			// actually contains any drawable constituent... including
			// fields, constructors instead of using a nice Composite.
			// So when looking for an entity, I must perform a type
			// chech else return strange things.
			// TODO: Implement the Composite design pattern
			if (this.graphModelConstituents[i] instanceof Entity) {
				entityList.add(this.graphModelConstituents[i]);
			}
		}
		final Entity[] entityArray = new Entity[entityList.size()];
		entityList.toArray(entityArray);
		return entityArray;
	}
	public void paint(final int xOffset, final int yOffset) {
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i].paint(xOffset, yOffset);
		}
	}
	public boolean processMouseEvent(final MouseEvent me) {
		boolean hasChanged = false;
		int i;
		for (i = this.graphModelConstituents.length - 1; i > 0
				&& hasChanged == false; i--) {

			hasChanged = this.graphModelConstituents[i].processMouseEvent(me);
		}
		return hasChanged;
	}
	public void removeSelectionListener(
		final ISelectionListener aSelectionListener) {

		// Yann 2014/03/28: Reset!
		// Because now I can reset a ModelGraph, I must store
		// any listeners so that I can attach them back to the
		// graphModelConstituents when doing reset() and then
		// construct().
		// Yann 2014/04/25: Weirdness!
		// This code existed because ModelGraph was a subclass
		// of Constituent... which it does not need to be! So,
		// I remove the subclass relation and cleanup this mess.
		//	super.addSelectionListener(aSelectionListener);

		this.listOfSelectionListeners.remove(aSelectionListener);

		// Yann 2004/08/08: Events!
		// The source graph listens to its 
		// constituents and forwards any event...
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i]
				.removeSelectionListener(aSelectionListener);
		}
	}
	public void reset(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final Set aSetOfEntitiesToDisplay,
		final Set aSetOfEntitiesToSelect) {

		this.abstractModel = anAbstractModel;
		this.builder = aBuilder;
		this.dimension = new Dimension(Constants.NULL_DIMENSION);
		this.graphModelName = anAbstractModel.getDisplayName();
		if (aSetOfEntitiesToDisplay != null) {
			this.setOfEntitiesToDisplay = new HashSet(aSetOfEntitiesToDisplay);
		}
		else {
			this.setOfEntitiesToDisplay = ModelGraph.EMPTY_SET;
		}
		if (aSetOfEntitiesToSelect != null) {
			this.setOfEntitiesToSelect = new HashSet(aSetOfEntitiesToSelect);
		}
		else {
			this.setOfEntitiesToSelect = ModelGraph.EMPTY_SET;
		}
	}
	public final void setVisibleElements(final int someVisibleElements) {
		// Yann 2002/12/15: Ghost are invisible!
		// I add ghost entities if needed here by constructing the model.

		if ((this.getVisibleElements()
				& IVisibility.GHOST_ENTITIES_DISPLAY) != (someVisibleElements
						& IVisibility.GHOST_ENTITIES_DISPLAY)) {

			this.construct();
		}

		// Yann 2015/02/06: Hack => Bug
		// See the class OptionPanel!
		// Because I did not do anything, the visibility the graph was not set correctly.
		this.visibility = someVisibleElements;

		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i]
				.setVisibleElements(someVisibleElements);
		}
	}
}

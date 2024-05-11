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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import padl.kernel.IAbstractModel;
import ptidej.ui.Constants;
import ptidej.ui.IVisibility;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Ghost;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.IUILayout;

public class LaidoutModelGraph extends ModelGraph {
	private IUILayout graphModelLayout;

	public LaidoutModelGraph(
		final Builder aBuilder,
		final IAbstractModel aModel,
		final IUILayout aLayout) {

		super(aBuilder, aModel);
		this.graphModelLayout = aLayout;
	}
	public LaidoutModelGraph(
		final Builder aBuilder,
		final IAbstractModel aModel,
		final Set aSetOfEntitiesToDisplay,
		final IUILayout aLayout) {

		super(aBuilder, aModel, aSetOfEntitiesToDisplay);
		this.graphModelLayout = aLayout;
	}
	protected void buildSpecifics() {
		super.buildSpecifics();

		// I reset the dimensions and compute the set of allEntities.
		final List entities = new ArrayList();
		for (int i = 0; i < this.graphModelConstituents.length; i++) {
			this.graphModelConstituents[i]
				.setPosition(Constants.DEFAULT_POSITION);

			// This is where I decide whether to show ghosts or not! 
			if (this.graphModelConstituents[i] instanceof Entity
					&& !(this.graphModelConstituents[i] instanceof Ghost && (this.graphModelConstituents[i]
						.getVisibleElements() & IVisibility.GHOST_ENTITIES_DISPLAY) == 0)) {

				entities.add(this.graphModelConstituents[i]);
			}
		}
		final Entity[] allEntities = new Entity[entities.size()];
		entities.toArray(allEntities);

		// I do the layout (oh yeah, baby, yeah...)
		// (This means that I do the build and then set up the positions.)
		this.graphModelConstituents =
			this.graphModelLayout.doLayout(allEntities);
	}
	public IUILayout getGraphLayout() {
		return this.graphModelLayout;
	}
	public void reset(
		final Builder aBuilder,
		final IAbstractModel anAbstractModel,
		final Set aSetOfEntitiesToDisplay,
		final Set aSetOfEntitiesToSelect,
		final IUILayout aLayout) {

		super.reset(
			aBuilder,
			anAbstractModel,
			aSetOfEntitiesToDisplay,
			aSetOfEntitiesToSelect);
		this.graphModelLayout = aLayout;
	}
	public void setGraphLayout(final IUILayout aLayout) {
		this.graphModelLayout = aLayout;
	}
}

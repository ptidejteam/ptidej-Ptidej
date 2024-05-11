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
package ptidej.ui.analysis.repository.comparator;

import java.util.HashMap;
import java.util.Map;
import padl.kernel.IConstituentOfModel;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/17
 */
public class AnalysesBuilder extends Builder {
	private static Map UniqueInstances;
	public static ptidej.ui.kernel.builder.Builder getCurrentBuilder(
		final IPrimitiveFactory aPrimitiveFactory) {

		if (AnalysesBuilder.UniqueInstances == null) {
			AnalysesBuilder.UniqueInstances = new HashMap();
		}
		if (AnalysesBuilder.UniqueInstances.get(aPrimitiveFactory) == null) {
			AnalysesBuilder.UniqueInstances.put(
				aPrimitiveFactory,
				new AnalysesBuilder(aPrimitiveFactory));
		}

		return (AnalysesBuilder) AnalysesBuilder.UniqueInstances
			.get(aPrimitiveFactory);
	}

	protected AnalysesBuilder(IPrimitiveFactory aPrimitiveFactory) {
		super(aPrimitiveFactory);
	}
	protected Entity createEntity(final IConstituentOfModel anEntity) {
		Entity entity;

		if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.AddedClass) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.AddedClass(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.RemovedClass) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.RemovedClass(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.ModifiedClass) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.ModifiedClass(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.AddedInterface) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.AddedInterface(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.RemovedInterface) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.RemovedInterface(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else if (anEntity instanceof ptidej.ui.analysis.repository.comparator.model.ModifiedInterface) {
			entity =
				new ptidej.ui.analysis.repository.comparator.ui.ModifiedInterface(
					this.getPrimitiveFactory(),
					this,
					anEntity);
		}
		else {
			entity = super.createEntity(anEntity);
		}

		return entity;
	}
}

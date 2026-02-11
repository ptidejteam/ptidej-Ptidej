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
package padl.util;

import padl.event.AnalysisEvent;
import padl.event.ElementEvent;
import padl.event.EntityEvent;
import padl.event.IModelListener;
import padl.event.IdentificationEvent;
import padl.event.InvokeEvent;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IField;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IUseRelationship;
import util.io.ProxyConsole;

public class ModelStatistics implements IModelListener {
	private int numberOfAggregationsMany;
	private int numberOfAggregationsOne;
	private int numberOfAssociations;
	private int numberOfClasses;
	private int numberOfCompositions;
	private int numberOfContainerAggregationsMany;
	private int numberOfContainerAggregationsOne;
	private int numberOfContainerCompositions;
	private int numberOfCreationRelationships;
	private int numberOfFields;
	private int numberOfGhosts;
	private int numberOfInterfaces;
	private int numberOfInvocations;
	private int numberOfMethods;
	private int numberOfUseRelationships;

	public void elementAdded(final ElementEvent elementEvent) {
		final IConstituentOfEntity pElement = elementEvent.getElement();

		if (pElement instanceof IComposition) {
			this.numberOfCompositions++;
		}
		else if (pElement instanceof IContainerComposition) {
			this.numberOfContainerCompositions++;
		}
		else if (pElement instanceof IAggregation) {
			if (((IAggregation) pElement).getCardinality() == 1) {
				this.numberOfAggregationsOne++;
			}
			else {
				this.numberOfAggregationsMany++;
			}
		}
		else if (pElement instanceof IContainerAggregation) {
			if (((IContainerAggregation) pElement).getCardinality() == 1) {
				this.numberOfContainerAggregationsOne++;
			}
			else {
				this.numberOfContainerAggregationsMany++;
			}
		}
		else if (pElement instanceof IAssociation) {
			this.numberOfAssociations++;
		}
		else if (pElement instanceof ICreation) {
			this.numberOfCreationRelationships++;
		}
		else if (pElement instanceof IUseRelationship) {
			this.numberOfUseRelationships++;
		}
		else if (pElement instanceof IField) {
			this.numberOfFields++;
		}
		else if (pElement instanceof IMethod) {
			this.numberOfMethods++;
		}
	}
	public void elementAnalyzed(final AnalysisEvent analisysEvent) {
	}
	public void elementIdentified(final IdentificationEvent recognitionEvent) {
	}
	public void elementRemoved(final ElementEvent elementEvent) {
		final IConstituentOfEntity pElement = elementEvent.getElement();

		if (pElement instanceof IComposition) {
			this.numberOfCompositions--;
		}
		else if (pElement instanceof IContainerComposition) {
			this.numberOfContainerCompositions--;
		}
		else if (pElement instanceof IAggregation) {
			if (((IAggregation) pElement).getCardinality() == 1) {
				this.numberOfAggregationsOne--;
			}
			else {
				this.numberOfAggregationsMany--;
			}
		}
		else if (pElement instanceof IContainerAggregation) {
			if (((IContainerAggregation) pElement).getCardinality() == 1) {
				this.numberOfContainerAggregationsOne--;
			}
			else {
				this.numberOfContainerAggregationsMany--;
			}
		}
		else if (pElement instanceof IAssociation) {
			this.numberOfAssociations--;
		}
		else if (pElement instanceof ICreation) {
			this.numberOfCreationRelationships--;
		}
		else if (pElement instanceof IUseRelationship) {
			this.numberOfUseRelationships--;
		}
		else if (pElement instanceof IField) {
			this.numberOfFields--;
		}
		else if (pElement instanceof IMethod) {
			this.numberOfMethods--;
		}
	}
	public void elementSkipped(final AnalysisEvent analisysEvent) {
	}
	public void entityAdded(final EntityEvent entityEvent) {
		if (entityEvent.getEntity() instanceof IClass) {
			this.numberOfClasses++;
		}
		else if (entityEvent.getEntity() instanceof IGhost) {
			this.numberOfGhosts++;
		}
		else if (entityEvent.getEntity() instanceof IInterface) {
			this.numberOfInterfaces++;
		}
	}
	public void entityIdentified(final IdentificationEvent recognitionEvent) {
		ProxyConsole.getInstance().normalOutput().print("Identified: ");
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println(recognitionEvent.getConstituentName());
	}
	public void entityRemoved(final EntityEvent entityEvent) {
		if (entityEvent.getEntity() instanceof IClass) {
			this.numberOfClasses--;
		}
		else if (entityEvent.getEntity() instanceof IGhost) {
			this.numberOfGhosts--;
		}
		else if (entityEvent.getEntity() instanceof IInterface) {
			this.numberOfInterfaces--;
		}
	}
	public void entitySkipped(final AnalysisEvent analysisEvent) {
		ProxyConsole.getInstance().normalOutput().print("Skipping:   ");
		ProxyConsole.getInstance().normalOutput().println(analysisEvent);
	}
	public void invokeAdded(final InvokeEvent invokeEvent) {
		this.numberOfInvocations++;
	}
	public void invokeAnalyzed(final AnalysisEvent analisysEvent) {
	}
	public void invokeIdentified(final IdentificationEvent recognitionEvent) {
	}
	public void invokeRemoved(final InvokeEvent invokeEvent) {
		this.numberOfInvocations--;
	}
	public void invokeSkipped(final AnalysisEvent analisysEvent) {
	}
	public void reset() {
		this.numberOfClasses = 0;
		this.numberOfGhosts = 0;
		this.numberOfInterfaces = 0;
		this.numberOfAssociations = 0;
		this.numberOfAggregationsMany = 0;
		this.numberOfAggregationsOne = 0;
		this.numberOfCompositions = 0;
		this.numberOfContainerAggregationsMany = 0;
		this.numberOfContainerAggregationsOne = 0;
		this.numberOfContainerCompositions = 0;
		this.numberOfCreationRelationships = 0;
		this.numberOfUseRelationships = 0;
		this.numberOfFields = 0;
		this.numberOfMethods = 0;
		this.numberOfInvocations = 0;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("Number of classes: ");
		buffer.append(this.numberOfClasses);
		buffer.append("\nNumber of ghosts: ");
		buffer.append(this.numberOfGhosts);
		buffer.append("\nNumber of interfaces: ");
		buffer.append(this.numberOfInterfaces);
		buffer.append("\nNumber of association relationships: ");
		buffer.append(this.numberOfAssociations);
		buffer.append("\nNumber of aggregation relationships [1,n]: ");
		buffer.append(this.numberOfAggregationsMany);
		buffer.append("\nNumber of aggregation relationships [1,1]: ");
		buffer.append(this.numberOfAggregationsOne);
		buffer.append("\nNumber of composition relationships: ");
		buffer.append(this.numberOfCompositions);
		buffer
			.append("\nNumber of container-aggregation relationships [1,n]: ");
		buffer.append(this.numberOfContainerAggregationsMany);
		buffer
			.append("\nNumber of container-aggregation relationships [1,1]: ");
		buffer.append(this.numberOfContainerAggregationsOne);
		buffer.append("\nNumber of container-composition relationships: ");
		buffer.append(this.numberOfContainerCompositions);
		buffer.append("\nNumber of creation relationships: ");
		buffer.append(this.numberOfCreationRelationships);
		buffer.append("\nNumber of use relationships: ");
		buffer.append(this.numberOfUseRelationships);
		buffer.append("\nNumber of fields: ");
		buffer.append(this.numberOfFields);
		buffer.append("\nNumber of methods: ");
		buffer.append(this.numberOfMethods);
		buffer.append("\nNumber of message sends: ");
		buffer.append(this.numberOfInvocations);

		return buffer.toString();
	}
}

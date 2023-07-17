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

import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;
import padl.util.Util;
import ptidej.ui.analysis.repository.comparator.model.ModifiedClass;
import ptidej.ui.analysis.repository.comparator.model.ModifiedInterface;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/20
 */
public final class Manager {
	private static Manager UniqueInstance;
	public static Manager getUniqueInstance() {
		if (Manager.UniqueInstance == null) {
			Manager.UniqueInstance = new Manager();
		}
		return Manager.UniqueInstance;
	}

	private int numberOfAddedElements;
	private int numberOfAddedEntities;
	private int numberOfAddedMethods;
	private int numberOfModifiedEntities;
	private int numberOfRemovedElements;
	private int numberOfRemovedEntities;
	private int numberOfRemovedMethods;

	private Manager() {
	}
	public int getNumberOfAddedElements() {
		return this.numberOfAddedElements;
	}
	public int getNumberOfAddedEntities() {
		return this.numberOfAddedEntities;
	}
	public int getNumberOfAddedMethods() {
		return this.numberOfAddedMethods;
	}
	public int getNumberOfModifiedEntities() {
		return this.numberOfModifiedEntities;
	}
	public int getNumberOfRemovedElements() {
		return this.numberOfRemovedElements;
	}
	public int getNumberOfRemovedEntities() {
		return this.numberOfRemovedEntities;
	}
	public int getNumberOfRemovedMethods() {
		return this.numberOfRemovedMethods;
	}

	public void handleAddedElement(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final IElement anElement) {

		anEntity.addConstituent(anElement);
		anElement.setDisplayName("<<added>> " + anElement.getDisplayName());
		this.numberOfAddedElements++;

		this.handleModifiedEntity(anAbstractModel, anEntity);
	}
	public void handleAddedEntity(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		if (anEntity instanceof IClass) {
			anAbstractModel
				.addConstituent(new ptidej.ui.analysis.repository.comparator.model.AddedClass(
					(IClass) anEntity));
			this.numberOfAddedEntities++;
		}
		else if (anEntity instanceof IInterface) {
			anAbstractModel
				.addConstituent(new ptidej.ui.analysis.repository.comparator.model.AddedInterface(
					(IInterface) anEntity));
			this.numberOfAddedEntities++;
		}
		else {
			anAbstractModel.addConstituent(anEntity);
			anEntity.setDisplayName("<<added>> " + anEntity.getDisplayName());
			this.numberOfAddedEntities++;
		}
	}
	public void handleAddedEntity(
		final IAbstractModel anAbstractModel,
		final String anEntityID) {

		this.handleAddedEntity(
			anAbstractModel,
			Factory.getInstance().createClass(
				anEntityID.toCharArray(),
				Util.computeSimpleName(anEntityID.toCharArray())));
	}
	public void handleAddedMethod(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final IMethod aMethod) {

		aMethod.setDisplayName("<<added>> " + aMethod.getDisplayName());
		this.numberOfAddedMethods++;

		this.handleModifiedEntity(anAbstractModel, anEntity);
	}
	public void handleAddedMethod(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final String aMethodName) {

		final IMethod method =
			Factory.getInstance().createMethod(
				aMethodName.toCharArray(),
				aMethodName.toCharArray());
		method.setDisplayName("<<added>> " + method.getDisplayName());
		anEntity.addConstituent(method);
		this.numberOfAddedMethods++;

		this.handleModifiedEntity(anAbstractModel, anEntity);
	}
	public void handleModifiedEntity(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		if (anEntity instanceof IClass) {
			anAbstractModel.removeConstituentFromID(anEntity.getID());
			anAbstractModel
				.addConstituent(new ModifiedClass((IClass) anEntity));
			this.numberOfModifiedEntities++;
		}
		else if (anEntity instanceof IInterface) {
			anAbstractModel.removeConstituentFromID(anEntity.getID());
			anAbstractModel.addConstituent(new ModifiedInterface(
				(IInterface) anEntity));
			this.numberOfModifiedEntities++;
		}
		else {
			anEntity
				.setDisplayName("<<modified>> " + anEntity.getDisplayName());
			this.numberOfModifiedEntities++;
		}
	}
	public void handleRemovedElement(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final IElement anElement) {

		anElement.setDisplayName("<<removed>> " + anElement.getDisplayName());
		this.numberOfRemovedElements++;

		this.handleModifiedEntity(anAbstractModel, anEntity);
	}
	public void handleRemovedEntity(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		if (anEntity instanceof IClass) {
			anAbstractModel.removeConstituentFromID(anEntity.getID());
			anAbstractModel
				.addConstituent(new ptidej.ui.analysis.repository.comparator.model.RemovedClass(
					(IClass) anEntity));
			this.numberOfRemovedEntities++;
		}
		else if (anEntity instanceof IInterface) {
			anAbstractModel.addConstituent(anEntity);
			anAbstractModel
				.addConstituent(new ptidej.ui.analysis.repository.comparator.model.RemovedInterface(
					(IInterface) anEntity));
			this.numberOfRemovedEntities++;
		}
		else {
			anEntity.setDisplayName("<<removed>> " + anEntity.getDisplayName());
			this.numberOfRemovedEntities++;
		}
	}
	public void handleRemovedMethod(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final String aMethodName) {

		try {
			final IMethod method =
				(IMethod) anEntity.getConstituentFromName(aMethodName
					.toCharArray());
			method.setDisplayName("<<removed>> " + method.getDisplayName());
			this.numberOfRemovedMethods++;

			this.handleModifiedEntity(anAbstractModel, anEntity);
		}
		catch (final NullPointerException e) {
			System.err.print(MultilingualManager.getString(
				"Err_METHODE_NOT_FOUND_IN_ENTITY",
				Manager.class,
				new Object[] { aMethodName, anEntity.getName() }));
		}
	}
	public void reset() {
		this.numberOfAddedElements = 0;
		this.numberOfAddedEntities = 0;
		this.numberOfAddedMethods = 0;
		this.numberOfModifiedEntities = 0;
		this.numberOfRemovedElements = 0;
		this.numberOfRemovedEntities = 0;
		this.numberOfRemovedMethods = 0;
	}
}

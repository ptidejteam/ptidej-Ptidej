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
package padl.util.adapter;

import java.util.Iterator;
import java.util.List;
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFactory;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.ModelDeclarationException;
import padl.visitor.IGenerator;
import padl.visitor.IWalker;

/**
 * 
 * @author Yann
 * @since  2009/03/07
 *
 */

public class IdiomLevelModelAdapter implements IIdiomLevelModel {
	private static final long serialVersionUID = 7176911949482060583L;

	private final ICodeLevelModel wrappedCodeLevelModel;

	public IdiomLevelModelAdapter(final ICodeLevelModel aCodeLevelModel) {
		this.wrappedCodeLevelModel = aCodeLevelModel;
	}

	public void addConstituent(final IConstituent aConstituent) {
		if (aConstituent instanceof IConstituentOfModel) {
			this.addConstituent((IConstituentOfModel) aConstituent);
		}
		else {
			throw new ModelDeclarationException(this.getClass().getName()
					+ " can only add IConstituentOfModel");
		}
	}

	public void addConstituent(final IConstituentOfModel aConstituent) {
		this.wrappedCodeLevelModel.addConstituent(aConstituent);
	}

	public void addModelListener(final IModelListener aModelListener) {
		this.wrappedCodeLevelModel.addModelListener(aModelListener);
	}

	public void addModelListeners(final List aListOfModelListeners) {
		this.wrappedCodeLevelModel.addModelListeners(aListOfModelListeners);
	}

	public Object clone() throws CloneNotSupportedException {
		return new IdiomLevelModelAdapter(
			(ICodeLevelModel) this.wrappedCodeLevelModel.clone());
	}

	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.wrappedCodeLevelModel.doesContainConstituentWithID(anID);
	}

	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.wrappedCodeLevelModel.doesContainConstituentWithName(aName);
	}

	public boolean doesContainTopLevelEntityWithID(final char[] anID) {
		return this.wrappedCodeLevelModel.doesContainTopLevelEntityWithID(anID);
	}

	public void fireModelChange(final String anEventType, final IEvent anEvent) {
		this.wrappedCodeLevelModel.fireModelChange(anEventType, anEvent);
	}

	public String generate(final IGenerator aBuilder) {
		return this.wrappedCodeLevelModel.generate(aBuilder);
	}

	public Iterator getConcurrentIteratorOnConstituents() {
		return this.wrappedCodeLevelModel.getConcurrentIteratorOnConstituents();
	}

	public Iterator getConcurrentIteratorOnConstituents(
		final Class aConstituentType) {
		return this.wrappedCodeLevelModel
			.getConcurrentIteratorOnConstituents(aConstituentType);
	}

	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedCodeLevelModel
			.getConcurrentIteratorOnConstituents(aFilter);
	}

	public IConstituent getConstituentFromID(final char[] anID) {
		return this.wrappedCodeLevelModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromID(final String anID) {
		return this.wrappedCodeLevelModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromName(final char[] aName) {
		return this.wrappedCodeLevelModel.getConstituentFromName(aName);
	}

	public IConstituent getConstituentFromName(final String aName) {
		return this.wrappedCodeLevelModel.getConstituentFromName(aName
			.toCharArray());
	}

	public String getDisplayName() {
		return this.wrappedCodeLevelModel.getDisplayName();
	}

	public String getDisplayPath() {
		return this.wrappedCodeLevelModel.getDisplayPath();
	}

	public IFactory getFactory() {
		return this.wrappedCodeLevelModel.getFactory();
	}

	public Iterator getIteratorOnConstituents() {
		return this.wrappedCodeLevelModel.getIteratorOnConstituents();
	}

	public Iterator getIteratorOnConstituents(final Class aConstituentType) {
		return this.wrappedCodeLevelModel
			.getIteratorOnConstituents(aConstituentType);
	}

	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedCodeLevelModel.getIteratorOnConstituents(aFilter);
	}

	public Iterator getIteratorOnModelListeners() {
		return this.wrappedCodeLevelModel.getIteratorOnModelListeners();
	}

	public Iterator getIteratorOnTopLevelEntities() {
		return this.wrappedCodeLevelModel.getIteratorOnTopLevelEntities();
	}

	public char[] getName() {
		return this.wrappedCodeLevelModel.getName();
	}

	public int getNumberOfConstituents() {
		return this.wrappedCodeLevelModel.getNumberOfConstituents();
	}

	public int getNumberOfConstituents(final Class aConstituentType) {
		return this.wrappedCodeLevelModel
			.getNumberOfConstituents(aConstituentType);
	}

	public int getNumberOfTopLevelEntities() {
		return this.wrappedCodeLevelModel.getNumberOfTopLevelEntities();
	}

	public int getNumberOfTopLevelEntities(final Class aClass) {
		return this.wrappedCodeLevelModel.getNumberOfTopLevelEntities(aClass);
	}

	public char[] getPath() {
		return this.wrappedCodeLevelModel.getPath();
	}

	public IFirstClassEntity getTopLevelEntityFromID(final char[] anID) {
		return this.wrappedCodeLevelModel.getTopLevelEntityFromID(anID);
	}

	public IFirstClassEntity getTopLevelEntityFromID(final String anID) {
		return this.wrappedCodeLevelModel.getTopLevelEntityFromID(anID);
	}

	public void moveIn(final IAbstractModel aDestinationModel) {
		this.wrappedCodeLevelModel.moveIn(aDestinationModel);
	}

	public void removeConstituentFromID(final char[] anID) {
		this.wrappedCodeLevelModel.removeConstituentFromID(anID);
	}

	public void removeModelListener(final IModelListener aModelListener) {
		this.wrappedCodeLevelModel.removeModelListener(aModelListener);
	}

	public void removeModelListeners(final List aListOfModelListeners) {
		this.wrappedCodeLevelModel.removeModelListeners(aListOfModelListeners);
	}

	public void removeTopLevelEntityFromID(final char[] anID) {
		this.wrappedCodeLevelModel.removeTopLevelEntityFromID(anID);
	}

	public Object walk(final IWalker aWalker) {
		return this.wrappedCodeLevelModel.walk(aWalker);
	}
}

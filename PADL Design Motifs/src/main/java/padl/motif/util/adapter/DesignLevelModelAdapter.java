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
package padl.motif.util.adapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFactory;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.exception.ModelDeclarationException;
import padl.motif.IDesignMotifModel;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.IDesignLevelModelCreator;
import padl.visitor.IGenerator;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * 
 * @author Yann
 * @since  2009/03/07
 *
 */

public class DesignLevelModelAdapter implements IDesignLevelModel {
	private static final long serialVersionUID = -265422879837345649L;

	private final IIdiomLevelModel wrappedIdiomLevelModel;

	public DesignLevelModelAdapter(final IIdiomLevelModel anIdiomLevelModel) {
		this.wrappedIdiomLevelModel = anIdiomLevelModel;
	}

	public void addConstituent(final IConstituent aConstituent) {
		if (aConstituent instanceof IConstituentOfModel) {
			this.addConstituent((IConstituentOfModel) aConstituent);
		}
		else if (aConstituent instanceof IDesignMotifModel) {
			this.addConstituent((IDesignMotifModel) aConstituent);
		}
		else {
			throw new ModelDeclarationException(this.getClass().getName()
					+ " can only add IConstituentOfModel");
		}
	}

	public void addConstituent(final IConstituentOfModel aConstituent) {
		this.wrappedIdiomLevelModel.addConstituent(aConstituent);
	}

	public void addConstituent(final IDesignMotifModel aDesignMotifModel) {
		// Yann 2009/03/07: Parents!
		// I must call the addConstituent() method of the
		// super-class of the wrapped idiom-level model
		// because only this method (invisible to the
		// outside world) can accept a DesignMotideModel.
		final Class thisClass = this.wrappedIdiomLevelModel.getClass();
		final Class abstractLevelModel = thisClass.getSuperclass();
		final Class abstractContainer = abstractLevelModel.getSuperclass();
		try {
			final Method addConstituentMethod = abstractContainer.getMethod(
					"addConstituent", new Class[] { IConstituent.class });
			addConstituentMethod.invoke(this.wrappedIdiomLevelModel,
					new Object[] { aDesignMotifModel });
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void addModelListener(final IModelListener aModelListener) {
		this.wrappedIdiomLevelModel.addModelListener(aModelListener);
	}

	public void addModelListeners(final List aListOfModelListeners) {
		this.wrappedIdiomLevelModel.addModelListeners(aListOfModelListeners);
	}

	public Object clone() throws CloneNotSupportedException {
		return this.wrappedIdiomLevelModel.clone();
	}

	public void create(final IDesignLevelModelCreator aDesignLevelModelCreator)
			throws CreationException {

		throw new ModelDeclarationException(
				"A DesignLevelModelAdapter cannot be created using a builder");
	}

	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.wrappedIdiomLevelModel.doesContainConstituentWithID(anID);
	}

	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.wrappedIdiomLevelModel
				.doesContainConstituentWithName(aName);
	}

	public boolean doesContainTopLevelEntityWithID(final char[] anID) {
		return this.wrappedIdiomLevelModel
				.doesContainTopLevelEntityWithID(anID);
	}

	public void fireModelChange(final String anEventType,
			final IEvent anEvent) {
		this.wrappedIdiomLevelModel.fireModelChange(anEventType, anEvent);
	}

	public String generate(final IGenerator aBuilder) {
		return this.wrappedIdiomLevelModel.generate(aBuilder);
	}

	public Iterator getConcurrentIteratorOnConstituents() {
		return this.wrappedIdiomLevelModel
				.getConcurrentIteratorOnConstituents();
	}

	public Iterator getConcurrentIteratorOnConstituents(
			final Class aConstituentType) {
		return this.wrappedIdiomLevelModel
				.getConcurrentIteratorOnConstituents(aConstituentType);
	}

	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedIdiomLevelModel
				.getConcurrentIteratorOnConstituents(aFilter);
	}

	public IConstituent getConstituentFromID(final char[] anID) {
		return this.wrappedIdiomLevelModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromID(final String anID) {
		return this.wrappedIdiomLevelModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromName(final char[] aName) {
		return this.wrappedIdiomLevelModel.getConstituentFromName(aName);
	}

	public IConstituent getConstituentFromName(final String aName) {
		return this.wrappedIdiomLevelModel
				.getConstituentFromName(aName.toCharArray());
	}

	public String getDisplayName() {
		return this.wrappedIdiomLevelModel.getDisplayName();
	}

	public String getDisplayPath() {
		return this.wrappedIdiomLevelModel.getDisplayPath();
	}

	public IFactory getFactory() {
		return this.wrappedIdiomLevelModel.getFactory();
	}

	public Iterator getIteratorOnConstituents() {
		return this.wrappedIdiomLevelModel.getIteratorOnConstituents();
	}

	public Iterator getIteratorOnConstituents(final Class aConstituentType) {
		return this.wrappedIdiomLevelModel
				.getIteratorOnConstituents(aConstituentType);
	}

	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedIdiomLevelModel.getIteratorOnConstituents(aFilter);
	}

	public Iterator getIteratorOnModelListeners() {
		return this.wrappedIdiomLevelModel.getIteratorOnModelListeners();
	}

	public Iterator getIteratorOnTopLevelEntities() {
		return this.wrappedIdiomLevelModel.getIteratorOnTopLevelEntities();
	}

	public char[] getName() {
		return this.wrappedIdiomLevelModel.getName();
	}

	public int getNumberOfConstituents() {
		return this.wrappedIdiomLevelModel.getNumberOfConstituents();
	}

	public int getNumberOfConstituents(final Class aConstituentType) {
		return this.wrappedIdiomLevelModel
				.getNumberOfConstituents(aConstituentType);
	}

	public int getNumberOfTopLevelEntities() {
		return this.wrappedIdiomLevelModel.getNumberOfTopLevelEntities();
	}

	public int getNumberOfTopLevelEntities(final Class aClass) {
		return this.wrappedIdiomLevelModel.getNumberOfTopLevelEntities(aClass);
	}

	public char[] getPath() {
		return this.wrappedIdiomLevelModel.getPath();
	}

	public IFirstClassEntity getTopLevelEntityFromID(final char[] anID) {
		return this.wrappedIdiomLevelModel.getTopLevelEntityFromID(anID);
	}

	public IFirstClassEntity getTopLevelEntityFromID(final String anID) {
		return this.wrappedIdiomLevelModel.getTopLevelEntityFromID(anID);
	}

	public void moveIn(final IAbstractModel aDestinationModel) {
		this.wrappedIdiomLevelModel.moveIn(aDestinationModel);
	}

	public void removeConstituentFromID(final char[] anID) {
		this.wrappedIdiomLevelModel.removeConstituentFromID(anID);
	}

	public void removeModelListener(final IModelListener aModelListener) {
		this.wrappedIdiomLevelModel.removeModelListener(aModelListener);
	}

	public void removeModelListeners(final List aListOfModelListeners) {
		this.wrappedIdiomLevelModel.removeModelListeners(aListOfModelListeners);
	}

	public void removeTopLevelEntityFromID(final char[] anID) {
		this.wrappedIdiomLevelModel.removeTopLevelEntityFromID(anID);
	}

	public Object walk(final IWalker aWalker) {
		return this.wrappedIdiomLevelModel.walk(aWalker);
	}
}

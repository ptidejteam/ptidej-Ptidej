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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFactory;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.motif.IDesignMotifModel;
import padl.motif.detector.IDetector;
import padl.motif.kernel.IMotifFactory;
import padl.motif.kernel.impl.MotifFactory;
import padl.visitor.IGenerator;
import padl.visitor.IVisitor;
import padl.visitor.IWalker;

public abstract class DesignMotifModelAdapter
		implements IDesignMotifModel, Serializable {

	private static final long serialVersionUID = 4626515704514146831L;

	private IDesignMotifModel wrappedDesignMotifModel;

	public DesignMotifModelAdapter(final char[] actorID) {
		this.wrappedDesignMotifModel = ((IMotifFactory) MotifFactory
				.getInstance()).createDesignMotifModel(actorID);
	}

	public DesignMotifModelAdapter(final IDesignMotifModel aDesignMotifModel) {
		this.wrappedDesignMotifModel = aDesignMotifModel;
	}

	public void accept(final IVisitor aVisitor) {
		this.wrappedDesignMotifModel.accept(aVisitor);
	}

	public final void addConstituent(final IConstituent aConstituent) {

		this.wrappedDesignMotifModel.addConstituent(aConstituent);
	}

	public final void addConstituent(final IConstituentOfModel aConstituent) {

		this.wrappedDesignMotifModel.addConstituent(aConstituent);
	}

	public void addExtension(final IConstituentExtension anExtension) {
		this.wrappedDesignMotifModel.addExtension(anExtension);
	}

	public void addModelListener(final IModelListener aModelListener) {
		this.wrappedDesignMotifModel.addModelListener(aModelListener);
	}

	public void addModelListeners(final List aListOfModelListeners) {
		this.wrappedDesignMotifModel.addModelListeners(aListOfModelListeners);
	}

	public final Object clone() throws CloneNotSupportedException {
		return this.wrappedDesignMotifModel.clone();
	}

	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.wrappedDesignMotifModel.doesContainConstituentWithID(anID);
	}

	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.wrappedDesignMotifModel
				.doesContainConstituentWithName(aName);
	}

	public boolean doesContainTopLevelEntityWithID(final char[] anID) {
		return this.wrappedDesignMotifModel
				.doesContainTopLevelEntityWithID(anID);
	}

	public void endCloneSession() {
		this.wrappedDesignMotifModel.endCloneSession();
	}

	public boolean equals(final Object obj) {
		if (!(obj instanceof IConstituent)) {
			return false;
		}
		return Arrays.equals(this.getID(), ((IConstituent) obj).getID());
	}

	public void fireModelChange(final String anEventType,
			final IEvent anEvent) {
		this.wrappedDesignMotifModel.fireModelChange(anEventType, anEvent);
	}

	public final String generate(final IGenerator aBuilder) {
		this.wrappedDesignMotifModel.generate(aBuilder);
		return aBuilder.getCode();
	}

	public int getClassification() {
		return this.wrappedDesignMotifModel.getClassification();
	}

	public IConstituent getClone() {
		return this.wrappedDesignMotifModel.getClone();
	}

	public String[] getCodeLines() {
		return this.wrappedDesignMotifModel.getCodeLines();
	}

	public String getComment() {
		return this.wrappedDesignMotifModel.getComment();
	}

	public Iterator getConcurrentIteratorOnConstituents() {
		return this.wrappedDesignMotifModel
				.getConcurrentIteratorOnConstituents();
	}

	public Iterator getConcurrentIteratorOnConstituents(
			final Class aConstituentType) {
		return this.wrappedDesignMotifModel
				.getConcurrentIteratorOnConstituents(aConstituentType);
	}

	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedDesignMotifModel.getIteratorOnConstituents(aFilter);
	}

	public IConstituent getConstituentFromID(final char[] anID) {
		return this.wrappedDesignMotifModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromID(final String anID) {
		return this.wrappedDesignMotifModel.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromName(final char[] aName) {
		return this.wrappedDesignMotifModel.getConstituentFromName(aName);
	}

	public IConstituent getConstituentFromName(final String aName) {
		return this.wrappedDesignMotifModel.getConstituentFromID(aName);
	}

	public final IDetector getDetector() {
		return this.wrappedDesignMotifModel.getDetector();
	}

	public String getDisplayID() {
		return this.wrappedDesignMotifModel.getDisplayID();
	}

	public String getDisplayName() {
		return this.wrappedDesignMotifModel.getDisplayName();
	}

	public String getDisplayPath() {
		return this.wrappedDesignMotifModel.getDisplayPath();
	}

	public IConstituentExtension getExtension(final char[] anExtensionName) {
		return this.wrappedDesignMotifModel.getExtension(anExtensionName);
	}

	public IFactory getFactory() {
		return this.wrappedDesignMotifModel.getFactory();
	}

	public char[] getID() {
		return this.wrappedDesignMotifModel.getID();
	}

	public String getIntent() {
		return this.wrappedDesignMotifModel.getIntent();
	}

	public Iterator getIteratorOnConstituents() {
		return this.wrappedDesignMotifModel.getIteratorOnConstituents();
	}

	public Iterator getIteratorOnConstituents(final Class aConstituentType) {
		return this.wrappedDesignMotifModel
				.getIteratorOnConstituents(aConstituentType);
	}

	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedDesignMotifModel.getIteratorOnConstituents(aFilter);
	}

	public Iterator getIteratorOnModelListeners() {
		return this.wrappedDesignMotifModel.getIteratorOnModelListeners();
	}

	public Iterator getIteratorOnTopLevelEntities() {
		return this.wrappedDesignMotifModel.getIteratorOnTopLevelEntities();
	}

	public char[] getName() {
		return this.wrappedDesignMotifModel.getName();
	}

	public int getNumberOfConstituents() {
		return this.wrappedDesignMotifModel.getNumberOfConstituents();
	}

	public int getNumberOfConstituents(final Class aConstituentType) {
		return this.wrappedDesignMotifModel
				.getNumberOfConstituents(aConstituentType);
	}

	public int getNumberOfTopLevelEntities() {
		return this.wrappedDesignMotifModel.getNumberOfTopLevelEntities();
	}

	public int getNumberOfTopLevelEntities(final java.lang.Class aClass) {
		return this.wrappedDesignMotifModel.getNumberOfTopLevelEntities(aClass);
	}

	public char[] getPath() {
		return this.wrappedDesignMotifModel.getPath();
	}

	public IFirstClassEntity getTopLevelEntityFromID(final char[] anID) {
		return this.wrappedDesignMotifModel.getTopLevelEntityFromID(anID);
	}

	public IFirstClassEntity getTopLevelEntityFromID(final String anID) {
		return this.wrappedDesignMotifModel.getTopLevelEntityFromID(anID);
	}

	public int getVisibility() {
		return this.wrappedDesignMotifModel.getVisibility();
	}

	public int getWeight() {
		return this.wrappedDesignMotifModel.getWeight();
	}

	public int hashCode() {
		return this.getID().hashCode();
	}

	public boolean isAbstract() {
		return this.wrappedDesignMotifModel.isAbstract();
	}

	public boolean isFinal() {
		return this.wrappedDesignMotifModel.isFinal();
	}

	public boolean isPrivate() {
		return this.wrappedDesignMotifModel.isPrivate();
	}

	public boolean isProtected() {
		return this.wrappedDesignMotifModel.isProtected();
	}

	public boolean isPublic() {
		return this.wrappedDesignMotifModel.isPublic();
	}

	public boolean isStatic() {
		return this.wrappedDesignMotifModel.isStatic();
	}

	public void moveIn(final IAbstractModel anAbstractModel) {
		this.wrappedDesignMotifModel.moveIn(anAbstractModel);
	}

	public void performCloneSession() {
		this.wrappedDesignMotifModel.performCloneSession();
	}

	public void removeConstituentFromID(final char[] anID) {
		this.wrappedDesignMotifModel.removeConstituentFromID(anID);
	}

	public void removeModelListener(final IModelListener aModelListener) {
		this.wrappedDesignMotifModel.removeModelListener(aModelListener);
	}

	public void removeModelListeners(final List aListOfModelListeners) {
		this.wrappedDesignMotifModel
				.removeModelListeners(aListOfModelListeners);
	}

	public void removeTopLevelEntityFromID(final char[] anID) {
		this.wrappedDesignMotifModel.removeTopLevelEntityFromID(anID);
	}

	public void resetCodeLines() {
		this.wrappedDesignMotifModel.resetCodeLines();
	}

	public void setAbstract(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setAbstract(aBoolean);
	}

	public void setClassification(final int aClassification) {
		this.wrappedDesignMotifModel.setClassification(aClassification);
	}

	public void setCodeLines(final String someCode) {
		this.wrappedDesignMotifModel.setCodeLines(someCode);
	}

	public void setCodeLines(final String[] someCode) {
		this.wrappedDesignMotifModel.setCodeLines(someCode);
	}

	public void setComment(final String aComment) {
		this.wrappedDesignMotifModel.setComment(aComment);
	}

	public final void setDetector(final IDetector aPatternDetector) {
		this.wrappedDesignMotifModel.setDetector(aPatternDetector);
	}

	public void setDisplayName(final String aName) {
		this.wrappedDesignMotifModel.setDisplayName(aName);
	}

	public void setFinal(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setFinal(aBoolean);
	}

	public void setIntent(final String anIntent) {
		this.wrappedDesignMotifModel.setIntent(anIntent);
	}

	public void setName(final char[] aName) {
		this.wrappedDesignMotifModel.setName(aName);
	}

	public void setPrivate(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setPrivate(aBoolean);
	}

	public void setProtected(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setProtected(aBoolean);
	}

	public void setPublic(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setPublic(aBoolean);
	}

	public void setStatic(final boolean aBoolean) {
		this.wrappedDesignMotifModel.setStatic(aBoolean);
	}

	public void setVisibility(final int aVisibility) {
		this.wrappedDesignMotifModel.setVisibility(aVisibility);
	}

	public void setWeight(final int aWeight) {
		this.wrappedDesignMotifModel.setWeight(aWeight);
	}

	public void startCloneSession() {
		this.wrappedDesignMotifModel.startCloneSession();
	}

	public String toString() {
		return this.wrappedDesignMotifModel.toString();
	}

	public String toString(final int aTab) {
		return this.wrappedDesignMotifModel.toString(aTab);
	}

	public final Object walk(final IWalker aWalker) {
		this.wrappedDesignMotifModel.walk(aWalker);
		return aWalker.getResult();
	}
}

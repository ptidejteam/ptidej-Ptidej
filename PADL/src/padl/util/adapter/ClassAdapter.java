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
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IElement;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterfaceActor;
import padl.kernel.impl.Factory;
import padl.visitor.IVisitor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/17
 */
public abstract class ClassAdapter implements IClass {
	private static final long serialVersionUID = 5999424242925482925L;

	private IClass wrappedClass;

	public ClassAdapter(final char[] anID, final char[] aName) {
		this.wrappedClass = Factory.getInstance().createClass(anID, aName);
	}
	public ClassAdapter(final IClass aClass) {
		this.wrappedClass = aClass;
	}
	public void accept(final IVisitor aVisitor) {
		this.wrappedClass.accept(aVisitor);
	}
	public void addConstituent(final IConstituent aConstituent) {
		this.wrappedClass.addConstituent(aConstituent);
	}
	public void addConstituent(final IConstituentOfEntity aConstituent) {
		this.wrappedClass.addConstituent(aConstituent);
	}
	public void addConstituent(final IElement anElement) {
		this.wrappedClass.addConstituent(anElement);
	}
	public void addExtension(final IConstituentExtension anExtension) {
		this.wrappedClass.addExtension(anExtension);
	}
	public void addImplementedInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.addImplementedInterface(anInterface);
	}
	public void addInheritedEntity(final IFirstClassEntity anEntity) {
		this.wrappedClass.addInheritedEntity(anEntity);
	}
	public void assumeAllInterfaces() {
		this.wrappedClass.assumeAllInterfaces();
	}
	public void assumeInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.assumeInterface(anInterface);
	}
	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.wrappedClass.doesContainConstituentWithID(anID);
	}
	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.wrappedClass.doesContainConstituentWithName(aName);
	}
	public void endCloneSession() {
		this.wrappedClass.endCloneSession();
	}
	public void fireModelChange(final String anEventType, final IEvent anEvent) {
		this.fireModelChange(anEventType, anEvent);
	}
	public IConstituent getClone() {
		return this.wrappedClass.getClone();
	}
	public String[] getCodeLines() {
		return this.wrappedClass.getCodeLines();
	}
	public String getComment() {
		return this.wrappedClass.getComment();
	}
	public Iterator getConcurrentIteratorOnConstituents() {
		return this.getConcurrentIteratorOnConstituents();
	}
	public Iterator getConcurrentIteratorOnConstituents(
		final Class aConstituentType) {
		return this.getConcurrentIteratorOnConstituents(aConstituentType);
	}
	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		return this.getConcurrentIteratorOnConstituents(aFilter);
	}
	public IConstituent getConstituentFromID(final char[] anID) {
		return this.wrappedClass.getConstituentFromID(anID);
	}
	public IConstituent getConstituentFromID(final String anID) {
		return this.wrappedClass.getConstituentFromID(anID);
	}
	public IConstituent getConstituentFromName(final char[] aName) {
		return this.wrappedClass.getConstituentFromName(aName);
	}
	public IConstituent getConstituentFromName(final String aName) {
		return this.wrappedClass.getConstituentFromName(aName.toCharArray());
	}
	public String getDisplayID() {
		return this.wrappedClass.getDisplayID();
	}
	public String getDisplayName() {
		return this.wrappedClass.getDisplayName();
	}
	public String getDisplayPath() {
		return this.wrappedClass.getDisplayPath();
	}
	public IConstituentExtension getExtension(final char[] anExtensionName) {
		return this.wrappedClass.getExtension(anExtensionName);
	}
	public char[] getID() {
		return this.wrappedClass.getID();
	}
	public IInterfaceActor getImplementedInterface(final char[] aName) {
		return this.wrappedClass.getImplementedInterface(aName);
	}
	public IFirstClassEntity getInheritedEntityFromID(final char[] anID) {
		return this.wrappedClass.getInheritedEntityFromID(anID);
	}
	public IFirstClassEntity getInheritedEntityFromName(final char[] aName) {
		return this.wrappedClass.getInheritedEntityFromName(aName);
	}
	public Iterator getIteratorOnConstituents() {
		return this.wrappedClass.getIteratorOnConstituents();
	}
	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.wrappedClass.getIteratorOnConstituents(aFilter);
	}
	public Iterator getIteratorOnConstituents(
		final java.lang.Class aConstituentType) {
		return this.wrappedClass.getIteratorOnConstituents(aConstituentType);
	}
	public Iterator getIteratorOnImplementedInterfaces() {
		return this.wrappedClass.getIteratorOnImplementedInterfaces();
	}
	public Iterator getIteratorOnInheritedEntities() {
		return this.wrappedClass.getIteratorOnInheritedEntities();
	}
	public Iterator getIteratorOnInheritedEntities(final IFilter aFilter) {
		return this.wrappedClass.getIteratorOnInheritedEntities(aFilter);
	}
	public Iterator getIteratorOnInheritingEntities() {
		return this.wrappedClass.getIteratorOnInheritingEntities();
	}
	public Iterator getIteratorOnInheritingEntities(final IFilter aFilter) {
		return this.wrappedClass.getIteratorOnInheritingEntities(aFilter);
	}
	public char[] getName() {
		return this.wrappedClass.getName();
	}
	public int getNumberOfConstituents() {
		return this.getNumberOfConstituents();
	}
	public int getNumberOfConstituents(final Class aConstituentType) {
		return this.getNumberOfConstituents(aConstituentType);
	}
	public int getNumberOfImplementedInterfaces() {
		return this.wrappedClass.getNumberOfImplementedInterfaces();
	}
	public int getNumberOfInheritedEntities() {
		return this.wrappedClass.getNumberOfInheritedEntities();
	}
	public int getNumberOfInheritingEntities() {
		return this.wrappedClass.getNumberOfInheritingEntities();
	}
	public char[] getPath() {
		return this.wrappedClass.getPath();
	}
	public String getPurpose() {
		return this.wrappedClass.getPurpose();
	}
	public int getVisibility() {
		return this.wrappedClass.getVisibility();
	}
	public int getWeight() {
		return this.wrappedClass.getWeight();
	}
	public boolean isAboveInHierarchy(final IFirstClassEntity anEntity) {
		return this.wrappedClass.isAboveInHierarchy(anEntity);
	}
	public boolean isAbstract() {
		return this.wrappedClass.isAbstract();
	}
	public boolean isFinal() {
		return this.wrappedClass.isFinal();
	}
	public boolean isForceAbstract() {
		return this.wrappedClass.isForceAbstract();
	}
	public boolean isPrivate() {
		return this.wrappedClass.isPrivate();
	}
	public boolean isProtected() {
		return this.wrappedClass.isProtected();
	}
	public boolean isPublic() {
		return this.wrappedClass.isPublic();
	}
	public boolean isStatic() {
		return this.wrappedClass.isStatic();
	}
	public void performCloneSession() {
		this.wrappedClass.performCloneSession();
	}
	public void removeConstituentFromID(final char[] anID) {
		this.wrappedClass.removeConstituentFromID(anID);
	}
	public void removeImplementedInterface(final IInterfaceActor anInterface) {
		this.wrappedClass.removeImplementedInterface(anInterface);
	}
	public void removeInheritedEntity(final IFirstClassEntity anEntity) {
		this.wrappedClass.removeInheritedEntity(anEntity);
	}
	public void removeModelListener(final IModelListener aModelListener) {
		this.removeModelListener(aModelListener);
	}
	public void resetCodeLines() {
		this.wrappedClass.resetCodeLines();
	}
	public void setAbstract(final boolean aBoolean) {
		this.wrappedClass.setAbstract(aBoolean);
	}
	public void setCodeLines(final String someCode) {
		this.wrappedClass.setCodeLines(someCode);
	}
	public void setCodeLines(final String[] someCode) {
		this.wrappedClass.setCodeLines(someCode);
	}
	public void setComment(final String aComment) {
		this.wrappedClass.setComment(aComment);
	}
	public void setDisplayName(final String aName) {
		this.wrappedClass.setDisplayName(aName);
	}
	public void setFinal(final boolean aBoolean) {
		this.wrappedClass.setFinal(aBoolean);
	}
	public void setName(final char[] aName) {
		this.wrappedClass.setName(aName);
	}
	public void setPrivate(final boolean aBoolean) {
		this.wrappedClass.setPrivate(aBoolean);
	}
	public void setProtected(final boolean aBoolean) {
		this.wrappedClass.setProtected(aBoolean);
	}
	public void setPublic(final boolean aBoolean) {
		this.wrappedClass.setPublic(aBoolean);
	}
	public void setPurpose(final String aPurpose) {
		this.wrappedClass.setPurpose(aPurpose);
	}
	public void setStatic(final boolean aBoolean) {
		this.wrappedClass.setStatic(aBoolean);
	}
	public void setVisibility(final int aVisibility) {
		this.wrappedClass.setVisibility(aVisibility);
	}
	public void setWeight(final int aWeight) {
		this.wrappedClass.setWeight(aWeight);
	}
	public void startCloneSession() {
		this.wrappedClass.startCloneSession();
	}
	public String toString() {
		return this.wrappedClass.toString();
	}
	public String toString(final int aTab) {
		return this.wrappedClass.toString(aTab);
	}
}

package padl.kernel;

import java.util.Iterator;
import java.util.List;

import padl.event.IEvent;
import padl.event.IModelListener;
import padl.visitor.IVisitor;

public abstract class FirstClassAdapter
		implements IFirstClassEntity, IObservable {

	private static final long serialVersionUID = -6851921712769126770L;

	private List modelListeners;

	private IFirstClassEntity wrappedEntity;

	protected FirstClassAdapter(IFirstClassEntity entity) {
		this.wrappedEntity = entity;
	}

	public void accept(final IVisitor aVisitor) {
		wrappedEntity.accept(aVisitor);
	}

	public void addConstituent(final IConstituent aConstituent) {
		wrappedEntity.addConstituent(aConstituent);
	}

	@Override
	public void addConstituent(IConstituentOfEntity anElement) {
		wrappedEntity.addConstituent(anElement);
	}

	public void addConstituent(final IElement anElement) {
		wrappedEntity.addConstituent(anElement);
	}

	public void addExtension(final IConstituentExtension anExtension) {
		wrappedEntity.addExtension(anExtension);
	}

	@Override
	public void addInheritedEntity(IFirstClassEntity anEntity) {
		wrappedEntity.addInheritedEntity(anEntity);
	}

	public final void addModelListener(final IModelListener aModelListener) {
		this.modelListeners.add(aModelListener);
	}

	public void addModelListeners(final List aListOfModelListeners) {
		this.modelListeners.addAll(aListOfModelListeners);
	}

	public boolean doesContainConstituentWithID(final char[] anID) {
		return wrappedEntity.doesContainConstituentWithID(anID);
	}

	public boolean doesContainConstituentWithName(final char[] aName) {
		return wrappedEntity.doesContainConstituentWithName(aName);
	}

	public void endCloneSession() {
		wrappedEntity.endCloneSession();
	}

	public void fireModelChange(final String anEventType,
			final IEvent anEvent) {
		this.fireModelChange(anEventType, anEvent);
	}

	@Override
	public IConstituent getClone() {
		return wrappedEntity.getClone();
	}

	public String[] getCodeLines() {
		return wrappedEntity.getCodeLines();
	}

	public String getComment() {
		return wrappedEntity.getComment();
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
		return wrappedEntity.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromID(final String anID) {
		return wrappedEntity.getConstituentFromID(anID);
	}

	public IConstituent getConstituentFromName(final char[] aName) {
		return wrappedEntity.getConstituentFromName(aName);
	}

	public IConstituent getConstituentFromName(final String aName) {
		return wrappedEntity.getConstituentFromName(aName.toCharArray());
	}

	public String getDisplayID() {
		return wrappedEntity.getDisplayID();
	}

	public String getDisplayName() {
		return wrappedEntity.getDisplayName();
	}

	public String getDisplayPath() {
		return wrappedEntity.getDisplayPath();
	}

	public IConstituentExtension getExtension(final char[] anExtensionName) {
		return wrappedEntity.getExtension(anExtensionName);
	}

	public char[] getID() {
		return wrappedEntity.getID();
	}

	@Override
	public IFirstClassEntity getInheritedEntityFromID(char[] anID) {
		return wrappedEntity.getInheritedEntityFromID(anID);
	}

	@Override
	public IFirstClassEntity getInheritedEntityFromName(char[] aName) {
		return wrappedEntity.getInheritedEntityFromName(aName);
	}

	public Iterator getIteratorOnConstituents() {
		return wrappedEntity.getIteratorOnConstituents();
	}

	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return wrappedEntity.getIteratorOnConstituents(aFilter);
	}

	public Iterator getIteratorOnConstituents(
			final java.lang.Class aConstituentType) {
		return wrappedEntity.getIteratorOnConstituents(aConstituentType);
	}

	@Override
	public Iterator getIteratorOnInheritedEntities() {
		return wrappedEntity.getIteratorOnInheritedEntities();
	}

	@Override
	public Iterator getIteratorOnInheritedEntities(IFilter aFilter) {
		return wrappedEntity.getIteratorOnInheritedEntities(aFilter);
	}

	@Override
	public Iterator getIteratorOnInheritingEntities() {
		return wrappedEntity.getIteratorOnInheritingEntities();
	}

	@Override
	public Iterator getIteratorOnInheritingEntities(IFilter aFilter) {
		return wrappedEntity.getIteratorOnInheritingEntities(aFilter);
	}

	public Iterator getIteratorOnModelListeners() {
		return this.modelListeners.iterator();
	}

	public char[] getName() {
		return wrappedEntity.getName();
	}

	public int getNumberOfConstituents() {
		return this.getNumberOfConstituents();
	}

	public int getNumberOfConstituents(final Class aConstituentType) {
		return this.getNumberOfConstituents(aConstituentType);
	}

	@Override
	public int getNumberOfInheritedEntities() {
		return wrappedEntity.getNumberOfInheritedEntities();
	}

	@Override
	public int getNumberOfInheritingEntities() {
		return wrappedEntity.getNumberOfInheritingEntities();
	}

	public char[] getPath() {
		return wrappedEntity.getPath();
	}

	@Override
	public String getPurpose() {
		return wrappedEntity.getPurpose();
	}

	public int getVisibility() {
		return wrappedEntity.getVisibility();
	}

	public int getWeight() {
		return wrappedEntity.getWeight();
	}

	@Override
	public boolean isAboveInHierarchy(IFirstClassEntity anEntity) {
		return wrappedEntity.isAboveInHierarchy(anEntity);
	}

	public boolean isAbstract() {
		return wrappedEntity.isAbstract();
	}

	public boolean isFinal() {
		return wrappedEntity.isFinal();
	}

	public boolean isPrivate() {
		return wrappedEntity.isPrivate();
	}

	public boolean isProtected() {
		return wrappedEntity.isProtected();
	}

	public boolean isPublic() {
		return wrappedEntity.isPublic();
	}

	public boolean isStatic() {
		return wrappedEntity.isStatic();
	}

	public void performCloneSession() {
		wrappedEntity.performCloneSession();
	}

	public void removeConstituentFromID(final char[] anID) {
		wrappedEntity.removeConstituentFromID(anID);
	}

	@Override
	public void removeInheritedEntity(IFirstClassEntity anEntity) {
		wrappedEntity.removeInheritedEntity(anEntity);
	}

	public void removeModelListener(final IModelListener aModelListener) {
		this.removeModelListener(aModelListener);
	}

	public void removeModelListeners(final List aListOfModelListeners) {
		this.modelListeners.removeAll(aListOfModelListeners);
	}

	public void resetCodeLines() {
		wrappedEntity.resetCodeLines();
	}

	public void setAbstract(final boolean aBoolean) {
		wrappedEntity.setAbstract(aBoolean);
	}

	public void setCodeLines(final String someCode) {

		wrappedEntity.setCodeLines(someCode);
	}

	public void setCodeLines(final String[] someCode) {
		wrappedEntity.setCodeLines(someCode);
	}

	public void setComment(final String aComment) {
		wrappedEntity.setComment(aComment);
	}

	public void setDisplayName(final String aName) {
		wrappedEntity.setDisplayName(aName);
	}

	public void setFinal(final boolean aBoolean) {
		wrappedEntity.setFinal(aBoolean);
	}

	public void setName(final char[] aName) {
		wrappedEntity.setName(aName);
	}

	public void setPrivate(final boolean aBoolean) {
		wrappedEntity.setPrivate(aBoolean);
	}

	public void setProtected(final boolean aBoolean) {
		wrappedEntity.setProtected(aBoolean);
	}

	public void setPublic(final boolean aBoolean) {
		wrappedEntity.setPublic(aBoolean);
	}

	@Override
	public void setPurpose(String aPurpose) {
		wrappedEntity.setPurpose(aPurpose);
	}

	public void setStatic(final boolean aBoolean) {
		wrappedEntity.setStatic(aBoolean);
	}

	public void setVisibility(final int aVisibility) {
		wrappedEntity.setVisibility(aVisibility);
	}

	public void setWeight(final int aWeight) {
		wrappedEntity.setWeight(aWeight);
	}

	public void startCloneSession() {
		wrappedEntity.startCloneSession();
	}

	public String toString() {
		return wrappedEntity.toString();
	}

	public String toString(final int aTab) {
		return wrappedEntity.toString(aTab);
	}
}

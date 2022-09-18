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
package padl.kernel.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.apache.commons.lang.ArrayUtils;
import padl.event.ElementEvent;
import padl.event.EntityEvent;
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.event.InvokeEvent;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IFilter;
import padl.kernel.INavigable;
import padl.kernel.IObservable;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.exception.ModelDeclarationException;
import padl.path.IConstants;
import padl.util.Util;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/04/09
 */
// Sebastien Colladon 23/04/2012 : Change the visibility to public to
// allow other project to extend from this class in the particular case 
// of Eclipse bundle loader (avoid IllegalAccessError).
// TODO Is "public" necessary?
public abstract class AbstractGenericContainerOfConstituents
		implements Serializable {

	private static class GenericObservable implements Serializable {
		private static final long serialVersionUID = 198074990982735832L;
		// Yann 2010/10/10: DB4O
		// Used to be:
		//	private final List listOfModelListeners =
		// I removed the final to make DB4O works...
		// TODO: Understand how to keep it final with DB4O!
		private Set listOfModelListeners = new HashSet(
			GenericContainerConstants.INITIAL_SIZE_GENERIC_OBSERVABLE);

		public final boolean addModelListener(
			final IModelListener aModelListener) {

			return this.listOfModelListeners.add(aModelListener);
		}
		@SuppressWarnings("unused")
		public final boolean addModelListeners(
			final List aListOfModelListeners) {

			final Iterator iterator = aListOfModelListeners.iterator();
			boolean result = false;
			while (iterator.hasNext()) {
				result |=
					this.addModelListener((IModelListener) iterator.next());
			}
			return result;
		}
		public final void fireModelChange(
			final String eventType,
			final IEvent modelEvent) {

			if (this.listOfModelListeners.size() > 0) {
				// First, I look for the method corresponding to the event type.
				final Method[] methods =
					IModelListener.class.getDeclaredMethods();
				Method eventMethod = null;
				for (int i = 0; i < methods.length
						&& eventMethod == null; i++) {
					if (methods[i].getName().equals(eventType)) {
						eventMethod = methods[i];
					}
				}

				// Second, I notify the listeners with the appropriate event.
				if (eventMethod != null) {
					final Iterator iterator =
						this.listOfModelListeners.iterator();
					while (iterator.hasNext()) {
						final IModelListener listener =
							(IModelListener) iterator.next();
						if (listener != null) {
							try {
								eventMethod.invoke(
									listener,
									new Object[] { modelEvent });
							}
							catch (final IllegalAccessException iae) {
								iae.printStackTrace(
									ProxyConsole.getInstance().errorOutput());
							}
							catch (final InvocationTargetException ite) {
								ite.printStackTrace(
									ProxyConsole.getInstance().errorOutput());
							}
						}
					}
				}
			}
		}
		public Iterator getIteratorOnModelListeners() {
			return this.listOfModelListeners.iterator();
		}
		protected List getModelListeners() {
			return new ArrayList(this.listOfModelListeners);
		}
		public final void removeModelListener(
			final IModelListener aModelListener) {

			this.listOfModelListeners.remove(aModelListener);
		}
		public final void removeModelListeners(
			final List aListOfModelListeners) {

			final Iterator iterator = aListOfModelListeners.iterator();
			while (iterator.hasNext()) {
				this.removeModelListener((IModelListener) iterator.next());
			}
		}
	}
	private class NonConcurrentIterator implements Iterator, Serializable {
		private static final long serialVersionUID = 1209788683542287364L;

		// Cannot be Singleton and member class!?
		//	private static NonConcurrentConstituentIterator UniqueInstance;
		//	public Iterator getInstance() {
		//		if (NonConcurrentConstituentIterator.UniqueInstance == null) {
		//			NonConcurrentConstituentIterator.UniqueInstance =
		//				new NonConcurrentConstituentIterator();
		//		}
		//		return NonConcurrentConstituentIterator.UniqueInstance;
		//	}

		private int expectedModCount = 0;
		private int index = 0;
		public NonConcurrentIterator() {
		}

		public boolean hasNext() {
			final boolean hasNext =
				this.index < AbstractGenericContainerOfConstituents.this.size;
			if (!hasNext) {
				AbstractGenericContainerOfConstituents.this.nciIterating--;
			}
			else {
				AbstractGenericContainerOfConstituents.this.nciIterating++;
			}
			return hasNext;
		}
		public Object next() {
			try {
				if (this.expectedModCount != AbstractGenericContainerOfConstituents.this.nciModCount) {
					AbstractGenericContainerOfConstituents.this.nciIterating--;
					throw new ConcurrentModificationException();
				}
				final Object next =
					AbstractGenericContainerOfConstituents.this.constituents[this.index];
				this.index++;
				AbstractGenericContainerOfConstituents.this.nciIterating++;
				return next;
			}
			catch (final IndexOutOfBoundsException e) {
				AbstractGenericContainerOfConstituents.this.nciIterating--;
				throw new NoSuchElementException();
			}
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		private void reset() {
			this.expectedModCount =
				AbstractGenericContainerOfConstituents.this.nciModCount;
			this.index = 0;
		}
	}

	private static final long serialVersionUID = -2006341987649472498L;

	// Yann 2004/12/13: Performances
	// When loading huge AOL file, it seems that:
	// this.doesContainConstituentWithID(aConstituent.getConstituentID())
	// is a performance bottleneck because of the many (maaany!)
	// instances of Iterator built. I cannot replace the list by a
	// hashtable which key is the actor ID because the list must be
	// sorted to ensure similar behavior on different platforms.
	// So, I cache the actor in a second list.
	//
	// Yann 2009/04/28: Performances (bis)
	// I replaced these caches by using maps of constituents.
	// private List actorsIDCache = new ArrayList();
	// private List actorsNameCache = new ArrayList();
	//
	// Yann 2009/04/28: Performances (bis)
	// I use a TreeMap that is an ordered HashMap...
	// to make it easier to test the code and to
	// guarantee that, whatever the insertion, the
	// constituent will be ordered.
	//
	// Yann 2009/05/03: Performances (ter)
	// I now manage my own array of constituents,
	// I make it grow from prime number to prime
	// number using an indice.
	protected IConstituent[] constituents;
	// protected ConstituentIterator constituentIterator;
	// Yann 2010/10/10: DB4O
	// Used to be:
	// protected final INavigable containerConsitituent;
	// I removed the final to make DB4O works...
	// TODO Understand how to keep it final with DB4O!
	// TODO Why "protected" fields?
	protected INavigable containerConsitituent;
	private int indexInPrimeNumbersArray;
	private NonConcurrentIterator nciInstance;
	private int nciIterating;
	private int nciModCount;
	// Yann 2013/07/18: Fragile-base class!
	// There is no reason for this class to inherit from GenericObservable,
	// it better delegates appropriate calls to an instance of this class.
	// Besides, delegation allows implementing a "smart" algorithm to
	// propagate the adding of a listener to constituents including in this
	// container :-) See the test TestListeners.
	private GenericObservable observable;
	protected int size;
	private int uniqueID;

	public AbstractGenericContainerOfConstituents(
		final INavigable aContainerConstituent) {

		this.containerConsitituent = aContainerConstituent;
		this.nciInstance = new NonConcurrentIterator();
		this.observable = new GenericObservable();
		this.resetListOfConstituents();
	}
	public AbstractGenericContainerOfConstituents(
		final INavigable aContainerConstituent,
		final int anInitialCapacity) {

		this.containerConsitituent = aContainerConstituent;
		this.nciInstance = new NonConcurrentIterator();
		this.observable = new GenericObservable();
		this.resetListOfConstituents(anInitialCapacity);
	}
	public void addConstituent(final IConstituent aConstituent) {
		// Yann 2009/05/06: Path
		// After having removed a constituent from a model,
		// I make sure that I "reset" its path so that I can
		// add it to another model. Also, I will test when
		// adding a constituent if it does not include path
		// information to prevent two models to share
		// constituents, maybe the clone process should be
		// involved too... I'll think about it later.
		// Yann 2010/04/29: Path!
		// I share the same default package across
		// models but it does not matter because the
		// model name does not matter.
		if (ArrayUtils
			.contains(aConstituent.getPath(), IConstants.ABSTRACT_MODEL_SYMBOL)
				&& !Arrays.equals(
					aConstituent.getID(),
					Constants.DEFAULT_PACKAGE_ID)) {

			final StringBuffer buffer = new StringBuffer();
			buffer.append("Shared constituent: ");
			buffer.append(aConstituent.getID());
			buffer.append(" (");
			buffer.append(aConstituent.getClass());
			buffer.append(") in ");
			buffer.append(this.getClass());
			buffer.append(
				"\nRemove this consituent from its origin model first before adding it to the current model.");
			throw new ModelDeclarationException(buffer.toString());
		}

		if (this.doesContainConstituentWithID(aConstituent.getID())) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(this.getClass().getName());
			buffer.append(" reports a duplicate constituent \"");
			buffer.append(aConstituent.getID());
			buffer.append("\" (");
			buffer.append(aConstituent.getClass().getName());
			buffer.append(") in \"");
			buffer.append(this.containerConsitituent.getPath());
			buffer.append("\" (");
			buffer.append(this.containerConsitituent.getClass().getName());
			buffer.append(')');
			throw new ModelDeclarationException(buffer.toString());
		}

		this.directlyAddConstituent(aConstituent);
	}
	public void addModelListener(final IModelListener aModelListener) {
		// Yann 2016/09/18: Recursion!
		// I make sure that I recurse into the sub-constituents of
		// this constituent iff it was the first time that I added
		// this particular listener to this constituent.
		if (this.observable.addModelListener(aModelListener)) {
			// Yann 2013/07/18: Delegation!
			// Now that I properly delegate to a GenericObservable object,
			// I can use this delegation to also properly propagate the
			// addition of any listener. I don't need to use a visitor
			// because the addModelListener(IModelListener) will recursively
			// add the listener to all the children of each constituent.
			// Yann 2015/09/01: Listeners!
			// Finally, getting it right: model listeners are for models 
			// *only* but are propagated to all constituents of the models!
			for (int i = 0; i < this.size; i++) {
				final IConstituent constituent = this.constituents[i];
				if (constituent instanceof IObservable) {
					((IObservable) constituent)
						.addModelListener(aModelListener);
				}
				if (constituent instanceof IPrivateModelObservable) {
					((IPrivateModelObservable) constituent)
						.addModelListener(aModelListener);
				}
			}
		}
	}
	public void addModelListeners(final List aListOfModelListeners) {
		final Iterator iterator = aListOfModelListeners.iterator();
		while (iterator.hasNext()) {
			final IModelListener listener = (IModelListener) iterator.next();
			this.addModelListener(listener);
		}
	}
	private void addUniqueIDToEnclosedConstituent(
		final IConstituent aConstituent) {
		// Yann 2006/02/24: UniqueID...
		// I need a UniqueID for IMethodInvocation and IParemeter in IMethod
		// and for any IRelationship in IEntity. So, when using the method
		// concretelyAddConstituent(), I take care of the unique ID.

		this.uniqueID++;
		if (this instanceof AbstractGenericContainerOfConstituents
				&& aConstituent instanceof Constituent) {

			final char[] uniqueID = String.valueOf(this.uniqueID).toCharArray();
			final int endIndexExclusive =
				aConstituent.getDisplayID().indexOf(Constants.NUMBER_SEPARATOR);
			final char[] constituentID;
			if (endIndexExclusive > 0) {
				constituentID = ArrayUtils
					.subarray(aConstituent.getID(), 0, endIndexExclusive);
			}
			else {
				constituentID = aConstituent.getID();
			}
			final char[] newID = new char[constituentID.length
					+ Constants.NUMBER_SEPARATOR.length() + uniqueID.length];
			System.arraycopy(constituentID, 0, newID, 0, constituentID.length);
			System.arraycopy(
				Constants.NUMBER_SEPARATOR.toCharArray(),
				0,
				newID,
				constituentID.length,
				Constants.NUMBER_SEPARATOR.length());
			System.arraycopy(
				uniqueID,
				0,
				newID,
				constituentID.length + Constants.NUMBER_SEPARATOR.length(),
				uniqueID.length);
			((Constituent) aConstituent).setID(newID);

			// Yann 2009/06/06: Path!
			// I don't forget to update the path accordingly...
			((Constituent) aConstituent).setPath(newID);
		}
	}
	private void broadcastAdditionOfConstituent(
		final IConstituent aConstituent) {
		// Yann 2004/04/09: Listener!
		// It is now the responsibility of this class to manage
		// the listeners and to send the appropriate events when
		// a change occurs.
		// Yann 2006/03/08: Listeners...
		// I recursively add the listeners to the constituent that I add...
		if (aConstituent instanceof IObservable) {
			((IObservable) aConstituent)
				.addModelListeners(this.observable.getModelListeners());
		}
		if (aConstituent instanceof IPrivateModelObservable) {
			((IPrivateModelObservable) aConstituent)
				.addModelListeners(this.observable.getModelListeners());
		}

		// Yann 2005/10/07: Packages!
		// I should distinguish among entities and packages...
		// Yann 2015/07/16: Law and Order!
		// Aha, this code was too simplistic... The example was
		// adding a IGlobalFunction (from CPP), which is both
		// an entity and a method, into a class... I do not want
		// to add this method as a top-level entity, because I
		// want to add it to a class, not a package or model...
		// So, rather than to trigger the notification on the
		// type of the added constituent, I do that now in the 
		// type of the receiving container and in reverse order.
		if (this.containerConsitituent instanceof IOperation) {
			this.fireModelChange(
				IModelListener.INVOKE_ADDED,
				new InvokeEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfOperation) aConstituent));
		}
		else if (this.containerConsitituent instanceof IEntity) {
			this.fireModelChange(
				IModelListener.ELEMENT_ADDED,
				new ElementEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfEntity) aConstituent));
		}
		else if (this.containerConsitituent instanceof IPackage) {
			this.fireModelChange(
				IModelListener.ENTITY_ADDED,
				new EntityEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfModel) aConstituent));
		}
		else if (this.containerConsitituent instanceof IAbstractModel) {
			this.fireModelChange(
				IModelListener.ENTITY_ADDED,
				new EntityEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfModel) aConstituent));
		}
	}
	private void broadcastRemovalOfConstituent(
		final IConstituent aConstituent) {
		// Yann 2004/04/09: Listener!
		// It is now the responsibility of this class to manage
		// the listeners and to send the appropriate events when
		// a change occurs.
		// Yann 2006/03/08: Listeners...
		// I recursively remove the listeners to the constituent that I
		// remove...
		if (aConstituent instanceof IObservable) {
			((IObservable) aConstituent)
				.removeModelListeners(this.observable.getModelListeners());
		}
		if (aConstituent instanceof IPrivateModelObservable) {
			((IPrivateModelObservable) aConstituent)
				.removeModelListeners(this.observable.getModelListeners());
		}

		// Yann 2005/10/07: Packages!
		// I should distinguish among entities and packages...
		if (aConstituent instanceof IConstituentOfModel) {
			this.fireModelChange(
				IModelListener.ENTITY_REMOVED,
				new EntityEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfModel) aConstituent));
		}
		else if (aConstituent instanceof IConstituentOfEntity) {
			this.fireModelChange(
				IModelListener.ELEMENT_REMOVED,
				new ElementEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfEntity) aConstituent));
		}
		else if (aConstituent instanceof IConstituentOfOperation) {
			this.fireModelChange(
				IModelListener.INVOKE_REMOVED,
				new InvokeEvent(
					(IContainer) this.containerConsitituent,
					(IConstituentOfOperation) aConstituent));
		}
	}
	private final void directlyAddConstituent(final IConstituent aConstituent) {
		// Yann 2004/12/20: Test and order II!
		// This method is used to add method invocations to
		// constructors and methods only because method
		// invocations must not be sorted.

		final int minCapacity = this.size + 1;
		final int oldCapacity = this.constituents.length;
		if (minCapacity > oldCapacity) {
			this.indexInPrimeNumbersArray++;
			int newCapacity;
			if (this.indexInPrimeNumbersArray < GenericContainerConstants.NUMBER_OF_PRIME_NUMBERS) {
				newCapacity =
					GenericContainerConstants.PRIME_NUMBERS[this.indexInPrimeNumbersArray];
			}
			else {
				newCapacity = oldCapacity
						+ GenericContainerConstants.PRIME_NUMBERS[GenericContainerConstants.NUMBER_OF_PRIME_NUMBERS
								/ 2];
			}
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}

			final IConstituent[] oldData = this.constituents;
			this.constituents = new IConstituent[newCapacity];
			System.arraycopy(oldData, 0, this.constituents, 0, this.size);
		}
		this.constituents[this.size] = aConstituent;
		this.size++;
		this.nciModCount++;

		this.updatePathUponAddition(this.containerConsitituent, aConstituent);
		this.directlyAddConstituentExtra(aConstituent);
		this.broadcastAdditionOfConstituent(aConstituent);
	}
	protected void directlyAddConstituentExtra(
		final IConstituent aConstituent) {
		// Default behaviour: do nothing.
	}
	// Sébastien Colladon 24/04/2012 : Visibility set to public...If you have
	// more than one ClassLoader then the only methods you can invoke on classes
	// from other ClassLoaders are public ones because private, package and
	// protected methods are all inaccessible due to being in a different
	// package (even when their package has the same name).
	public final void directlyAddConstituentWithUniqueID(
		final IConstituent aConstituent) {

		// Yann 2006/02/24: UniqueID...
		// I need a UniqueID for IMethodInvocation and IParemeter in IMethod
		// and for any IRelationship in IEntity. So, when using the method
		// concretelyAddConstituent(), I take care of the unique ID.
		this.addUniqueIDToEnclosedConstituent(aConstituent);
		this.directlyAddConstituent(aConstituent);
	}
	public boolean doesContainConstituentWithID(final char[] anID) {
		for (int i = 0; i < this.size; i++) {
			final IConstituent constituent = this.constituents[i];
			if (Arrays.equals(constituent.getID(), anID)) {
				return true;
			}
		}
		return false;
	}
	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.getConstituentFromName(aName) != null;
	}
	public void fireModelChange(
		final String anEventType,
		final IEvent anEvent) {
		this.observable.fireModelChange(anEventType, anEvent);
	}
	public Iterator getConcurrentIteratorOnConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// (The name of the method is deliberate for consistency
		// and also as a joke :-).)
		final IConstituent[] copyOfConstituents = new IConstituent[this.size];
		System
			.arraycopy(this.constituents, 0, copyOfConstituents, 0, this.size);
		return Arrays.asList(copyOfConstituents).iterator();
	}
	public Iterator getConcurrentIteratorOnConstituents(final IFilter aFilter) {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		final IConstituent[] copyOfConstituents = new IConstituent[this.size];
		System
			.arraycopy(this.constituents, 0, copyOfConstituents, 0, this.size);
		return Util.getFilteredConstituentsIterator(
			copyOfConstituents,
			this.size,
			aFilter);
	}
	public Iterator getConcurrentIteratorOnConstituents(
		final java.lang.Class aConstituentType) {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// (The name of the method is deliberate for consistency
		// and also as a joke :-).)
		final IConstituent[] copyOfConstituents = new IConstituent[this.size];
		System
			.arraycopy(this.constituents, 0, copyOfConstituents, 0, this.size);
		return Util.getTypedConstituentsIterator(
			copyOfConstituents,
			this.size,
			aConstituentType);
	}
	public IConstituent getConstituentFromID(final char[] anID) {
		// Yann 2004/12/13: Performances
		// I cached the actorID to improve performance
		// (to avoid creating instances of Iterator over and over again).
		// I can now you the cached value to give me the index of the
		// constituent in the list of actors.
		// Yann 2009/04/29: Dirty!
		// I went back to this solution of using an iterator because
		// I am now storing the iterator :-)
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (Arrays.equals(constituent.getID(), anID)) {
				return constituent;
			}
		}
		return null;
	}
	public IConstituent getConstituentFromID(final String anID) {
		return this.getConstituentFromID(anID.toCharArray());
	}
	public IConstituent getConstituentFromName(final char[] aName) {
		// Yann 2007/11/21: Performances
		// I cached the actorName to improve performance
		// (to avoid creating instances of Iterator over and over again).
		// I can now you the cached value to give me the index of the
		// constituent in the list of actors.
		// Yann 2009/04/29: Dirty!
		// I went back to this solution of using an iterator because
		// I am now storing the iterator :-)
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (Arrays.equals(constituent.getName(), aName)) {
				return constituent;
			}
		}
		return null;
	}
	public IConstituent getConstituentFromName(final String aName) {
		return this.getConstituentFromName(aName.toCharArray());
	}
	public Iterator getIteratorOnConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// I should implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		//	this.constituentIterator.reset();
		//	return this.constituentIterator;
		// Yann 2013/07/11: Embedded iterators!
		// I cannot all the time return the same instance of the
		// iterator for the case where two iterations are embedded
		// in one another... So, I keep track whether someone is
		// iterating and, if there is someone, I create a new
		// instance. This is a different case than comodification
		// and thus cannot be treated similarly. 
		if (this.nciIterating > 0) {
			this.nciInstance = new NonConcurrentIterator();
			this.nciInstance.reset();
			return this.nciInstance;
		}
		else {
			this.nciInstance.reset();
			return this.nciInstance;
		}
	}
	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// TODO: When using an array, the following implementation
		// is terribly inefficient!! Include directly the filter here.
		return Util.getFilteredConstituentsIterator(
			this.constituents,
			this.size,
			aFilter);
	}
	public Iterator getIteratorOnConstituents(
		final java.lang.Class aConstituentType) {

		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// TODO: When using an array, the following implementation
		// is terribly inefficient!! Include directly the filter here.
		return Util.getTypedConstituentsIterator(
			this.constituents,
			this.size,
			aConstituentType);
	}
	public Iterator getIteratorOnModelListeners() {
		return this.observable.getIteratorOnModelListeners();
	}
	public int getNumberOfConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		return this.size;
	}
	public int getNumberOfConstituents(final java.lang.Class aConstituentType) {
		// Duc-Loc 2006/01/25: Consistency
		final Iterator iterator = Util.getTypedConstituentsIterator(
			this.constituents,
			this.size,
			aConstituentType);

		int size = 0;
		while (iterator.hasNext()) {
			iterator.next();
			size++;
		}

		return size;
		// Ask Yann why getTypedConstituentsList() is set to private arrrggg!!!
		// Because, we don't want to give access to sensible information :-)
	}
	public void removeConstituentFromID(final char[] anID) {
		// Yann 2013/07/11: Double search
		// Why search for the constituent and then iterate through them all again...? 
		//	this.removeConstituentFromID0(this.getConstituentFromID(anID));
		this.removeConstituentFromID0(anID);
	}
	// /**
	// * This method returns a list of all the
	// * actors (instances of Entity) added to
	// *
	// * this model.
	// *
	// * @deprecated This method is dangerous because it exposes the
	// * inner working of all the constituents that are able to contains
	// * other constituents. Thus, it is now replaced with the two
	// * getIterator() and getIterator(Class aConstituentType) methods
	// * (and related methods).
	// */
	// public List listOfConstituents() {
	// return this.listOfConstituents;
	// }
	private final void removeConstituentFromID0(final char[] anID) {
		// Salima and Yann 2009/08/07: Null constituent and improvement
		// Why is this method protected when it is called only from
		//	removeConstituentFromID(final char[] anID)
		// changed to private!
		// We now check for a null value for the parameter, in case someone
		// tries to remove a constituent that does not exist in the model.
		int index = -1;
		for (int i = 0; i < this.size; i++) {
			if (Arrays.equals(this.constituents[i].getID(), anID)) {
				index = i;
				break;
			}
		}
		if (index > -1) {
			final IConstituent constituent = this.constituents[index];
			if (index < this.size - 1) {
				System.arraycopy(
					this.constituents,
					index + 1,
					this.constituents,
					index,
					this.size - index - 1);
			}
			this.size--;
			this.nciModCount++;

			// Yann 2015/05/24: Nullification!
			// I do not forget to nullify the position of the constituent
			// that I removed!!! Else, whenever I sort the array, I would
			// get very weird results: a small array containing only the
			// same constituent.
			this.constituents[this.size] = null;

			// Yann 2004/04/09: Listener!
			// It is now the responsibility of this class to manage
			// the listeners and to send the appropriate events when
			// a change occurs.
			// if (this instanceof IAbstractLevelModel) {
			// this.fireModelChange(
			// IModelListener.ENTITY_REMOVED,
			// new EntityEvent(
			// (IAbstractLevelModel) this,
			// (IFirstClassEntity) aConstituent));
			// }
			// else if (this instanceof IFirstClassEntity) {
			// this.fireModelChange(
			// IModelListener.ELEMENT_REMOVED,
			// new ElementEvent(
			// (IFirstClassEntity) this,
			// (IElement) aConstituent));
			// }
			// Yann 2011/06/11: Top-level entities...
			this.broadcastRemovalOfConstituent(constituent);
			this.updatePathUponRemoval(constituent);
		}
		else {
			throw new NoSuchElementException();
		}
	}
	public void removeModelListener(final IModelListener aModelListener) {
		this.observable.removeModelListener(aModelListener);
	}
	public void removeModelListeners(final List modelListeners) {
		this.observable.removeModelListeners(modelListeners);
	}
	public void resetListOfConstituents() {
		this.resetListOfConstituents(1);
	}
	private void resetListOfConstituents(final int anInitialCapacity) {
		// Yann 2004/04/09: Clone!
		// I make sure that a clone of an abstract level
		// model gets a new instance of an array list.
		// (See also method AbstractLevelModel.clone().)
		this.constituents =
			new IConstituent[GenericContainerConstants.PRIME_NUMBERS[this.indexInPrimeNumbersArray]];
		this.size = 0;
	}
	private void updatePathUponAddition(
		final INavigable aContainer,
		final IConstituent aConstituent) {

		final char[] containerPath = aContainer.getPath();
		final int startIndexInclusive = ArrayUtils.lastIndexOf(
			aConstituent.getPath(),
			((Constituent) aConstituent).getPathSymbol()) + 1;
		final int endIndexExclusive = aConstituent.getPath().length;
		final char[] constituentPath = ArrayUtils.subarray(
			aConstituent.getPath(),
			startIndexInclusive,
			endIndexExclusive);

		final char[] newPath =
			new char[containerPath.length + 1 + constituentPath.length];
		System.arraycopy(containerPath, 0, newPath, 0, containerPath.length);
		newPath[containerPath.length] =
			((Constituent) aConstituent).getPathSymbol();
		System.arraycopy(
			constituentPath,
			0,
			newPath,
			containerPath.length + 1,
			constituentPath.length);
		((Constituent) aConstituent).setPath(newPath);

		// Yann 2013/05/22: Propagation!
		// I should not forget to propagate this change 
		// iff this constituent is a container, silly!
		// I must use instanceof because there is no
		// class Container per se but Constituent plays
		// its role...
		if (aConstituent instanceof IContainer) {
			final Iterator iterator =
				((IContainer) aConstituent).getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Constituent constituent = (Constituent) iterator.next();
				this.updatePathUponAddition(
					(INavigable) aConstituent,
					constituent);
			}
		}
	}
	private void updatePathUponRemoval(final IConstituent aConstituent) {
		// Yann 2009/05/06: Path
		// After having removed a constituent from a model,
		// I make sure that I "reset" its path so that I can
		// add it to another model. Also, I will test when
		// adding a constituent if it does not include path
		// information to prevent two models to share
		// constituents, maybe the clone process should be
		// involved too... I'll think about it later.
		final int startIndexInclusive = ArrayUtils.lastIndexOf(
			aConstituent.getPath(),
			((Constituent) aConstituent).getPathSymbol()) + 1;
		final int endIndexExclusive = aConstituent.getPath().length;
		((Constituent) aConstituent).setPath(
			ArrayUtils.subarray(
				aConstituent.getPath(),
				startIndexInclusive,
				endIndexExclusive));

		// Yann 2013/05/22: Propagation!
		// I should not forget to propagate this change 
		// iff this constituent is a container, silly!
		// I must use instanceof because there is no
		// class Container per se but Constituent plays
		// its role...
		if (aConstituent instanceof IContainer) {
			final Iterator iterator =
				((IContainer) aConstituent).getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final Constituent constituent = (Constituent) iterator.next();
				this.updatePathUponRemoval(constituent);
			}
		}
	}
}

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import padl.kernel.Constants;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import padl.visitor.IVisitor;
import util.io.ProxyConsole;
import util.lang.Modifier;
import util.multilingual.MultilingualManager;
import com.ibm.toad.cfparse.utils.Access;

// TODO: Should we provide to people extending PADL some default
// implementation of the different Constituent of the meta-model?
// Maybe class "Constituent" could be public, or classes "Elements"
// and "Entities"?
public abstract class Constituent implements IConstituent {
	private static IVisitor cachedCurrentVisitor;
	private static final Map cachedAcceptClassNames = new HashMap();
	private static final Map cachedCorrespondingInterfaceNames = new HashMap();
	private static final List cachedNotFoundClassNames = new ArrayList();
	private static final long serialVersionUID = -3089376780834846438L;

	private Constituent clone;
	// Yann 2009/04/29: Useless?
	// It seems that the cloning protocol changed
	// so much that these variable are not useful.
	// private List clonedBoundEventList = new ArrayList(0);
	// private List clonedVetoEventList = new ArrayList(0);
	private String[] codeLines;
	private String comment;
	// Yann 2004/04/09: Why "extends AbstractContainer"?
	// From the point of view of the model (defined by the
	// interfaces in the kernel), only certain constituent
	// may hold other constituent (by implementing IContainer).
	// From the point of view of the implementation and to
	// avoid duplication, any constituent may hold other
	// constituent. This is not a problem because a model
	// can only be manipulated through the interfaces.
	private char[] id;
	private char[] name;
	private char[] path;
	private int visibility = Access.ACC_PUBLIC;
	private int weight = Constants.MANDATORY;
	private int numberOfExtensions;
	private IConstituentExtension[] extensions;
	private int indexInPrimeNumbersArray;

	public Constituent(final char[] anID) {
		if (anID == null) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ACTOR_ID_NULL",
				IConstituent.class));
		}
		this.id = anID;
		this.setName(anID);
		this.setPath(anID);
	}

	public void accept(final IVisitor visitor) {
		this.accept(visitor, "visit");
	}

	protected void accept(final IVisitor visitor, final String methodName) {
		// Yann 2015/05/24: Cache reset...
		// If the visitor has changed, then I reset the cache.
		if (cachedCurrentVisitor != visitor) {
			cachedAcceptClassNames.clear();
			cachedCorrespondingInterfaceNames.clear();
			cachedCurrentVisitor = visitor;
			cachedNotFoundClassNames.clear();
		}

		this.accept(this.getClass(), visitor, methodName, true);
	}
	private boolean accept(
		final java.lang.Class currentReceiver,
		final IVisitor visitor,
		final String methodName,
		final boolean shouldRecurse) {

		// Yann 2003/12/05: Interfaces!
		// I must match a class a the kernel to the
		// corresponding interface:
		// padl.kernel.kernel.impl.Association
		// ->
		// padl.kernel.IAssociation
		// Yann 2010/06/21: Performance
		// The CreatorJCT spent a lot of time in this method,
		// in which the call to String.replaceAll() was
		// accounting for most of the time spent. I added a
		// cache to avoid doing the same computations again
		// and over again.
		final String className = currentReceiver.getName();
		if (!Constituent.cachedAcceptClassNames.containsKey(className)) {
			Constituent.cachedAcceptClassNames.put(
				className,
				className.replaceAll(".impl.", ".I"));
		}
		String acceptClassName = (String) cachedAcceptClassNames.get(className);

		java.lang.Class argument = null;
		if (!Constituent.cachedNotFoundClassNames.contains(acceptClassName)) {
			if (Constituent.cachedCorrespondingInterfaceNames
				.containsKey(acceptClassName)) {

				acceptClassName =
					(String) Constituent.cachedCorrespondingInterfaceNames
						.get(acceptClassName);
			}

			try {
				// Old : doesn't work with bundle
				// final java.lang.Class[] argument =
				// new java.lang.Class[] { java.lang.Class
				// .forName(acceptClassName) };
				// TODO understand the difference between forName and loadClass...
				argument =
					visitor
						.getClass()
						.getClassLoader()
						.loadClass(acceptClassName);
			}
			catch (final ClassNotFoundException e) {
				Constituent.cachedNotFoundClassNames.add(acceptClassName);
				visitor.unknownConstituentHandler(methodName, this);
				return false;
			}
		}
		else {
			return false;
		}

		try {
			final Method method =
				visitor.getClass().getMethod(
					methodName,
					new java.lang.Class[] { argument });
			method.invoke(visitor, new Object[] { this });
			return true;
		}
		catch (final Exception e) {
			// Yann 2004/04/10: New constituents!
			// In case I add new constituent and forget to update
			// the method in the IVisitor interface, I forward such
			// exceptions (to fail the appropriate tests).
			// System.err.println(MultilingualManager.getString(
			// "Exception_ACCEPT_METHOD",
			// Constituent.class,
			// new Object[] { methodName, acceptMethodName }));
			// e.printStackTrace();
			// Yann 2008/10/13: Fall back!
			// Following Mathieu's suggesting, the visiting of
			// an unknown constituent (by the visitor) won't
			// fail anymore but "gracefully" call an handler
			// method that should deal with it!
			//
			// Yann 2013/06/17: Interesting!
			// Here is an interesting mix between bypassing the 
			// look-up mechanisms of the compiler and of the
			// virtual machine: ICPPClass is a sub-interface of
			// IClass, so if a IVisitor does not know what to do
			// with ICPPClass, it may know with IClass...
			// WARNING! I only consider the immediate super-
			// interfaces even though the code is recursive...
			boolean foundACandidateReceiver = false;
			if (shouldRecurse) {
				final java.lang.Class[] interfaces = argument.getInterfaces();
				int i = 0;
				while (i < interfaces.length && !foundACandidateReceiver) {
					final java.lang.Class interfase = interfaces[i];
					foundACandidateReceiver =
						this.accept(interfase, visitor, methodName, false);
					i++;
				}
				if (foundACandidateReceiver) {
					i = i - 1;
					Constituent.cachedCorrespondingInterfaceNames.put(
						acceptClassName,
						interfaces[i].getName());
					return true;
				}
				else {
					Constituent.cachedNotFoundClassNames.add(acceptClassName);
					visitor.unknownConstituentHandler(methodName + '('
							+ argument.getName() + ')', this);
				}
			}
		}

		return false;
	}
	public final void addExtension(final IConstituentExtension anExtension) {
		if (anExtension instanceof IConstituentExtension) {
			final int minCapacity = this.numberOfExtensions + 1;
			final int oldCapacity = this.extensions.length;
			if (minCapacity > oldCapacity) {
				final IConstituentExtension[] oldData = this.extensions;
				this.indexInPrimeNumbersArray++;
				int newCapacity =
					GenericContainerConstants.PRIME_NUMBERS[this.indexInPrimeNumbersArray];
				if (newCapacity < minCapacity)
					newCapacity = minCapacity;
				this.extensions = new IConstituentExtension[newCapacity];
				System.arraycopy(
					oldData,
					0,
					this.extensions,
					0,
					this.numberOfExtensions);
			}
			this.extensions[this.numberOfExtensions] = anExtension;
			this.numberOfExtensions++;

			anExtension.setExtendedConstituent(this);
		}
		else {
			throw new ModelDeclarationException(anExtension
				.getClass()
				.getName() + " must be a subtype of IConstituentExtension");
		}
	}
	public void endCloneSession() {
		this.clone = null;
		// this.clonedBoundEventList.clear();
		// this.clonedVetoEventList.clear();
	}
	public boolean equals(final Object obj) {
		if (!(obj instanceof IConstituent)) {
			return false;
		}
		return Arrays.equals(this.getID(), ((IConstituent) obj).getID());
	}
	public IConstituent getClone() {
		return this.clone;
	}
	public String[] getCodeLines() {
		return this.codeLines;
	}
	public String getComment() {
		return this.comment;
	}
	public String getDisplayID() {
		return String.valueOf(this.getID());
	}
	public String getDisplayName() {
		return String.valueOf(this.getName());
	}
	public String getDisplayPath() {
		return String.valueOf(this.getPath());
	}
	public final IConstituentExtension getExtension(final char[] anExtensionName) {
		for (int i = 0; i < this.extensions.length; i++) {
			final IConstituentExtension extension = this.extensions[i];
			if (Arrays.equals(extension.getName(), anExtensionName)) {
				return extension;
			}
		}
		return null;
	}
	public char[] getID() {
		return this.id;
	}
	public char[] getName() {
		return this.name;
	}
	public char[] getPath() {
		return this.path;
	}
	protected abstract char getPathSymbol();
	public int getVisibility() {
		return this.visibility;
	}
	public int getWeight() {
		return this.weight;
	}
	public int hashCode() {
		// Yann 2013/09/11: I got caught!
		// I naively thought that the following call
		//	return this.getID().hashCode();
		// was correct to implement the hashCode but
		// it is not so! See
		//	http://stackoverflow.com/questions/744735/java-array-hashcode-implementation
		return ArrayUtils.hashCode(this.getID());
	}
	public boolean isAbstract() {
		return Access.isAbstract(this.visibility);
	}
	public boolean isFinal() {
		return Access.isFinal(this.visibility);
	}
	public boolean isPrivate() {
		return Access.isPrivate(this.visibility);
	}
	public boolean isProtected() {
		return Access.isProtected(this.visibility);
	}
	public boolean isPublic() {
		return Access.isPublic(this.visibility);
	}
	public boolean isStatic() {
		return Access.isStatic(this.visibility);
	}
	/**
	 * This methods is used by the clone protocol.
	 */
	public void performCloneSession() {
	}
	public void resetCodeLines() {
		this.setCodeLines("");
	}
	public void setAbstract(final boolean aBoolean) {
		this
			.setVisibility(aBoolean ? (this.getVisibility() | Access.ACC_ABSTRACT)
					: (this.getVisibility() & ~Access.ACC_ABSTRACT));
	}
	public void setCodeLines(final String someCode) {
		final List listOfLines = new ArrayList();
		final StringTokenizer tokeniser = new StringTokenizer(someCode, "\n\r");
		while (tokeniser.hasMoreTokens()) {
			final String line = (String) tokeniser.nextElement();
			listOfLines.add(line);
		}
		final String[] arrayOfLines = new String[listOfLines.size()];
		listOfLines.toArray(arrayOfLines);
		this.setCodeLines(arrayOfLines);
	}
	public void setCodeLines(String[] code) {
		if (this.isAbstract()) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ELEM_CODE_DEF",
				IConstituent.class));
		}
		this.codeLines = code;
	}
	public void setComment(final String aComment) {
		this.comment = aComment;
	}
	public void setDisplayName(final String aName) {
		this.name = aName.toCharArray();
	}
	public void setFinal(final boolean aBoolean) {
		this.setVisibility(aBoolean ? (this.getVisibility() | Access.ACC_FINAL)
				: (this.getVisibility() & ~Access.ACC_FINAL));
	}
	protected void setID(final char[] anID) {
		this.id = anID;
	}
	public void setName(final char[] aName) {
		this.name = aName;
	}
	void setPath(final char[] aPath) {
		this.path = aPath;
	}
	public void setPrivate(final boolean aBoolean) {
		this
			.setVisibility(aBoolean ? ((this.getVisibility() | Access.ACC_PRIVATE) & ~(Access.ACC_PUBLIC | Access.ACC_PROTECTED))
					: (this.getVisibility() & ~Access.ACC_PRIVATE));
	}
	public void setProtected(final boolean aBoolean) {
		this
			.setVisibility(aBoolean ? ((this.getVisibility() | Access.ACC_PROTECTED) & ~(Access.ACC_PUBLIC | Access.ACC_PRIVATE))
					: (this.getVisibility() & ~Access.ACC_PROTECTED));
	}
	public void setPublic(final boolean aBoolean) {
		this
			.setVisibility(aBoolean ? ((this.getVisibility() | Access.ACC_PUBLIC) & ~(Access.ACC_PRIVATE | Access.ACC_PROTECTED))
					: (this.getVisibility() & ~Access.ACC_PUBLIC));
	}
	public void setStatic(final boolean aBoolean) {
		this
			.setVisibility(aBoolean ? (this.getVisibility() | Access.ACC_STATIC)
					: (this.getVisibility() & ~Access.ACC_STATIC));
	}
	public void setVisibility(final int visibility) {
		if (this.getCodeLines() != null && Access.isAbstract(visibility)) {
			// Why test getCodeLines() != null here ?
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ELEM_ABSTRACT",
				IConstituent.class));
		}
		this.visibility = visibility;
	}
	public void setWeight(final int weight) {
		this.weight = weight;
	}
	/**
	 * This method performs a shallow copy.
	 */
	public void startCloneSession() {
		try {
			final Constituent tmpObject = (Constituent) super.clone();
			this.clone = tmpObject;
			tmpObject.clone = null;

			// Yann 2010/10/03: Paths!
			// The "path" of each cloned constituent
			// must be reset of course because the
			// clone does not yet belong to any model.
			// Yann 2013/10/24: Miracle?!
			// It is a miracle that cloning did work sometimes...
			// Of course the clone should start with it ID as
			// minimum path to make sure that it has a path!
			//	this.clone.setPath(ArrayUtils.EMPTY_CHAR_ARRAY);
			this.clone.setPath(this.id);
		}
		catch (final CloneNotSupportedException cnse) {
			cnse.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public String toString() {
		return this.toString(0);
	}
	public String toString(final int tab) {
		final StringBuffer codeEq = new StringBuffer();
		Util.addTabs(tab, codeEq);
		if (this.getComment() != null) {
			codeEq.append("/* ");
			codeEq.append(this.getComment());
			codeEq.append(" */\n");
			Util.addTabs(tab, codeEq);
		}
		codeEq.append(Modifier.toString(this.getVisibility()));
		return codeEq.toString();
	}
}

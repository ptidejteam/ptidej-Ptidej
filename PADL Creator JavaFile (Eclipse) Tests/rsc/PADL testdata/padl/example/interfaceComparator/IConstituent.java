package padl.kernel;

import java.io.Serializable;
import padl.kernel.exception.ModelDeclarationException;

/**
 * - startCloneSession() is somewhat equivallent to a shallow
 *   copy of the constituent. After this protocol is executed,
 *   it is guaranteed that all constituents have been
 *   shallow-copied. No assumption is made about the link among
 *   constituents.
 * - performCloneSession() updates the links among constituents,
 *   using the isCloned() and getClone() methods. After this
 *   protocol is executed, it is guaranteed that all the links
 *   have been updated, somewhat like a deep copy;
 * - endCloneSession() finished the updates and cleans the possible
 *   temporary values, mainly it sets to null all the "clone"
 *   instance variable.
 * 
 * @author Yann-Gaël Guéhéneuc
 */
public interface IConstituent extends Cloneable, INavigable, Serializable {
	void accept(final IVisitor aVisitor);
	void endCloneSession();
	boolean equals(final Object anObject);
	/**
	 * This method return a clone (deep copy) of the constituent.
	 */
	IConstituent getClone();
	String[] getCodeLines();
	String getComment();
	// Yann 2004/05/21: Flexibility with names.
	/**
	 * The method getDisplayName() returns the name to be
	 * displayed by graphical front-end, while the getName()
	 * method returns the (fully-qualified) name of the
	 * constituent, and getID() return a unique identifier.
	 * (By default, getDisplayName() and getName() are identical.)
	 */
	String getDisplayName();
	String getDisplayID();
	char[] getID();
	char[] getName();
	int getVisibility();
	int getWeight();
	int hashCode();
	boolean isAbstract();
	boolean isFinal();
	boolean isPrivate();
	boolean isProtected();
	boolean isPublic();
	boolean isStatic();
	void performCloneSession();
	void resetCodeLines() throws ModelDeclarationException;
	void setAbstract(final boolean aBoolean)
		throws ModelDeclarationException;
	void setCodeLines(final String someCode)
		throws ModelDeclarationException;
	void setCodeLines(final String[] someCode) throws ModelDeclarationException;
	void setComment(final String aComment);
	void setDisplayName(final String aName);
	void setFinal(final boolean aBoolean);
	void setName(final char[] aName);
	void setPrivate(final boolean aBoolean);
	void setProtected(final boolean aBoolean);
	void setPublic(final boolean aBoolean);
	void setStatic(final boolean aBoolean);
	void setVisibility(final int aVisibility)
		throws ModelDeclarationException;
	void setWeight(final int aWeight);
	void startCloneSession();
	String toString();
	String toString(final int aTab);
}

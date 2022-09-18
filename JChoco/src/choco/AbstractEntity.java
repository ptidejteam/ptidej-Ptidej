/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.AbstractEntity.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco;

/**
 * An overall root abstract class.
 */
public abstract class AbstractEntity implements Entity {
	/**
	 * All objects may be linked to an external object
	 */

	public Object hook = null;

	/**
	 * The (optimization or decision) problem to which the entity belongs.
	 */

	public Problem problem;

	protected AbstractEntity() {
		;
	}

	protected AbstractEntity(final Problem pb) {
		this.problem = pb;
	}

	/**
	 * Retrieves the problem of the entity
	 */

	public Problem getProblem() {
		return this.problem;
	}

	public String pretty() {
		return this.toString();
	}

}

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredIntVectorTrail.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * Implements a trail with the history of all the stored search vectors.
 */
public class StoredIntVectorTrail implements ITrailStorage {
	/**
	 * The current environment.
	 */

	private final Environment environment;

	/**
	 * All the stored search vectors.
	 */

	private StoredIntVector[] vectorStack;

	/**
	 * Indices of the previous values in the stored vectors.
	 */

	private int[] indexStack;

	/**
	 * Previous values of the stored vector elements.
	 */

	private int[] valueStack;

	/**
	 * World stamps associated to the previous values
	 */

	private int[] stampStack;

	/**
	 * The last world an search vector was modified in.
	 */

	private int currentLevel;

	/**
	 * Starts of levels in all the history arrays.
	 */

	private int[] worldStartLevels;

	/**
	 * capacity of the trailing stack (in terms of number of updates that can be stored)
	 */
	private int maxUpdates = 0;

	/**
	 * Constructs a trail for the specified environment with the
	 * specified numbers of updates and worlds.
	 */

	public StoredIntVectorTrail(
		final Environment env,
		final int nUpdates,
		final int nWorlds) {
		this.environment = env;
		this.currentLevel = 0;
		this.vectorStack = new StoredIntVector[nUpdates];
		this.indexStack = new int[nUpdates];
		this.valueStack = new int[nUpdates];
		this.stampStack = new int[nUpdates];
		this.worldStartLevels = new int[nWorlds];
	}

	/**
	 * Returns the current size of the stack.
	 */

	public int getSize() {
		return this.currentLevel;
	}

	private void resizeUpdateCapacity() {
		final int newCapacity = this.maxUpdates * 3 / 2;
		// first, copy the stack of variables
		final StoredIntVector[] tmp1 = new StoredIntVector[newCapacity];
		System.arraycopy(this.vectorStack, 0, tmp1, 0, this.vectorStack.length);
		this.vectorStack = tmp1;
		// then, copy the stack of former values
		final int[] tmp2 = new int[newCapacity];
		System.arraycopy(this.valueStack, 0, tmp2, 0, this.valueStack.length);
		this.valueStack = tmp2;
		// then, copy the stack of world stamps
		final int[] tmp3 = new int[newCapacity];
		System.arraycopy(this.stampStack, 0, tmp3, 0, this.stampStack.length);
		this.stampStack = tmp3;
		// then, copy the stack of indices
		final int[] tmp4 = new int[newCapacity];
		System.arraycopy(this.indexStack, 0, tmp4, 0, this.indexStack.length);
		this.indexStack = tmp4;

		// last update the capacity
		this.maxUpdates = newCapacity;
	}

	public void resizeWorldCapacity(final int newWorldCapacity) {
		final int[] tmp = new int[newWorldCapacity];
		System.arraycopy(
			this.worldStartLevels,
			0,
			tmp,
			0,
			this.worldStartLevels.length);
		this.worldStartLevels = tmp;
	}
	/**
	 * Reacts on the modification of an element in a stored search vector.
	 */

	public void savePreviousState(
		final StoredIntVector vect,
		final int index,
		final int oldValue,
		final int oldStamp) {
		this.vectorStack[this.currentLevel] = vect;
		this.indexStack[this.currentLevel] = index;
		this.stampStack[this.currentLevel] = oldStamp;
		this.valueStack[this.currentLevel] = oldValue;
		this.currentLevel++;
		if (this.currentLevel == this.maxUpdates) {
			this.resizeUpdateCapacity();
		}
	}

	/**
	 * Comits a world: merging it with the previous one.
	 */

	public void worldCommit() {
		// TODO
	}

	/**
	 * Moving down to the previous world.
	 */

	public void worldPop() {
		while (this.currentLevel > this.worldStartLevels[this.environment.currentWorld]) {
			this.currentLevel--;
			final StoredIntVector v = this.vectorStack[this.currentLevel];
			v._set(
				this.indexStack[this.currentLevel],
				this.valueStack[this.currentLevel],
				this.stampStack[this.currentLevel]);
		}
	}

	/**
	 * Moving up to the next world.
	 */

	public void worldPush() {
		this.worldStartLevels[this.environment.currentWorld + 1] =
			this.currentLevel;
	}
}

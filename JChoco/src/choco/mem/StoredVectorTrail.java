// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredVectorTrail.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * Implements storage of historical values for backtrackable integers
 */
public class StoredVectorTrail implements ITrailStorage {
	/** reference towards the overall environment (responsible for all memory management) */
	public Environment environment;

	/** stack of backtrackable vectors */
	private StoredVector[] vectorStack;

	/** stack of indices */
	private int[] indexStack;

	/** stack of values (former values that need be restored upon backtracking */
	private Object[] valueStack;

	/** a pointer indicating the level of the last entry */
	private int currentLevel;

	/** a stack of pointers (for each start of a world) */
	private int[] worldStartLevels;

	/**
	 * capacity of the trailing stack (in terms of number of updates that can be stored)
	 */
	private int maxUpdates = 0;

	/** constructor: building a trail with predefined size
	 * @param nUpdates maximal number of updates that will be stored
	 * @param nWorlds  maximal number of worlds that will be stored
	 */
	public StoredVectorTrail(
		final Environment env,
		final int nUpdates,
		final int nWorlds) {
		this.environment = env;
		this.currentLevel = 0;
		this.vectorStack = new StoredVector[nUpdates];
		this.indexStack = new int[nUpdates];
		this.valueStack = new Object[nUpdates];
		this.worldStartLevels = new int[nWorlds];
	}

	public int getSize() {
		return 0;
	}

	/** pushing a former entry onto the world stack */
	void notifyUpdate(
		final StoredVector vect,
		final int index,
		final Object formerValue) {
		this.vectorStack[this.currentLevel] = vect;
		this.indexStack[this.currentLevel] = index;
		this.valueStack[this.currentLevel] = formerValue;
		this.currentLevel++;
		if (this.currentLevel == this.maxUpdates) {
			this.resizeUpdateCapacity();
		}
	}

	private void resizeUpdateCapacity() {
		final int newCapacity = this.maxUpdates * 3 / 2;
		// first, copy the stack of variables
		final StoredVector[] tmp1 = new StoredVector[newCapacity];
		System.arraycopy(this.vectorStack, 0, tmp1, 0, this.vectorStack.length);
		this.vectorStack = tmp1;
		// then, copy the stack of former values
		final Object[] tmp2 = new Object[newCapacity];
		System.arraycopy(this.valueStack, 0, tmp2, 0, this.valueStack.length);
		this.valueStack = tmp2;
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

	/** comitting a world: merging it with the previous one */
	public void worldCommit() {
		// TODO
		;
	}

	/** moving down to the previous world */
	public void worldPop() {
		while (this.currentLevel > this.worldStartLevels[this.environment.currentWorld]) {
			this.currentLevel--;
			final StoredVector v = this.vectorStack[this.currentLevel];
			v._set(
				this.indexStack[this.currentLevel],
				this.valueStack[this.currentLevel]);
		}
	}

	/** moving up to the next world */
	public void worldPush() {
		this.worldStartLevels[this.environment.currentWorld + 1] =
			this.currentLevel;
	}
}

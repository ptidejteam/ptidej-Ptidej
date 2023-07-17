package choco.mem;

/**
 * A backtrackable float variable trail storing past values of all the float variables.
 */
public class StoredFloatTrail implements ITrailStorage {
	/**
	 * Reference towards the overall environment
	 * (responsible for all memory management).
	 */

	private final Environment environment;

	/**
	 * Stack of backtrackable search variables.
	 */

	private StoredFloat[] variableStack;

	/**
	 * Stack of values (former values that need be restored upon backtracking).
	 */

	private double[] valueStack;

	/**
	 * Stack of timestamps indicating the world where the former value
	 * had been written.
	 */

	private int[] stampStack;

	/**
	 * Points the level of the last entry.
	 */

	private int currentLevel;

	/**
	 * A stack of pointers (for each start of a world).
	 */

	private int[] worldStartLevels;

	/**
	 * capacity of the trailing stack (in terms of number of updates that can be stored)
	 */
	private int maxUpdates = 0;

	/** Constructs a trail with predefined size.
	 * @param nUpdates maximal number of updates that will be stored
	 * @param nWorlds  maximal number of worlds that will be stored
	 */

	public StoredFloatTrail(
		final Environment env,
		final int nUpdates,
		final int nWorlds) {
		this.environment = env;
		this.currentLevel = 0;
		this.maxUpdates = nUpdates;
		this.variableStack = new StoredFloat[nUpdates];
		this.valueStack = new double[nUpdates];
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
		final StoredFloat[] tmp1 = new StoredFloat[newCapacity];
		System.arraycopy(
			this.variableStack,
			0,
			tmp1,
			0,
			this.variableStack.length);
		this.variableStack = tmp1;
		// then, copy the stack of former values
		final double[] tmp2 = new double[newCapacity];
		System.arraycopy(this.valueStack, 0, tmp2, 0, this.valueStack.length);
		this.valueStack = tmp2;
		// then, copy the stack of world stamps
		final int[] tmp3 = new int[newCapacity];
		System.arraycopy(this.stampStack, 0, tmp3, 0, this.stampStack.length);
		this.stampStack = tmp3;
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
	 * Reacts when a StoredInt is modified: push the former value & timestamp
	 * on the stacks.
	 */

	public void savePreviousState(
		final StoredFloat v,
		final double oldValue,
		final int oldStamp) {
		this.valueStack[this.currentLevel] = oldValue;
		this.variableStack[this.currentLevel] = v;
		this.stampStack[this.currentLevel] = oldStamp;
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
			final StoredFloat v = this.variableStack[this.currentLevel];
			v._set(
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

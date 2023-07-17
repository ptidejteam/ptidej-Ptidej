// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredInt.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * A class implementing backtrackable integers.
 */
public final class StoredInt implements IStateInt {
	/**
	 * The current {@link choco.mem.Environment}.
	 */

	private final Environment environment;

	/**
	 * Current value of the search.
	 */

	private int currentValue;

	/**
	 * The last world the search was moidified in.
	 */

	int worldStamp;

	/**
	 *  The current {@link choco.mem.StoredIntTrail}.
	 */

	private final StoredIntTrail trail;

	/**
	 * Constructs a stored search with an unknown initial value.
	 * Note: this constructor should not be used directly: one should instead
	 * use the Environment factory
	 */

	public StoredInt(final Environment env) {
		this(env, IStateInt.UNKNOWN_INT);
	}

	/**
	 * Constructs a stored search with an initial value.
	 * Note: this constructor should not be used directly: one should instead
	 * use the Environment factory
	 */

	public StoredInt(final Environment env, final int i) {
		this.environment = env;
		this.currentValue = i;
		this.worldStamp = env.currentWorld;
		this.trail =
			(StoredIntTrail) this.environment.getTrail(Environment.INT_TRAIL);
	}

	/** Modifies the value without storing the former value on the trailing stack.
	 * @param y the new value
	 * @param wstamp the stamp of the world in which the update is performed */

	void _set(final int y, final int wstamp) {
		this.currentValue = y;
		this.worldStamp = wstamp;
	}

	/**
	 * modifying a StoredInt by an increment
	 * @param delta
	 */
	public void add(final int delta) {
		this.set(this.get() + delta);
	}

	/**
	 * Returns the current value.
	 */

	public int get() {
		return this.currentValue;
	}

	/**
	 * Retrieving the environment
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

	/**
	 * Checks if a value is currently stored.
	 */

	public boolean isKnown() {
		return this.currentValue != IStateInt.UNKNOWN_INT;
	}

	/**
	 * Modifies the value and stores if needed the former value on the
	 * trailing stack.
	 */

	public void set(final int y) {
		if (y != this.currentValue) {
			if (this.worldStamp < this.environment.currentWorld) {
				this.trail.savePreviousState(
					this,
					this.currentValue,
					this.worldStamp);
				this.worldStamp = this.environment.currentWorld;
			}
			this.currentValue = y;
		}
	}

}

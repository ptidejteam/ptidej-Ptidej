package choco.mem;

/**
 * A class implementing a backtrackable float variable.
 */
public class StoredFloat {
	/**
	 * The current {@link choco.mem.Environment}.
	 */

	private final Environment environment;

	/**
	 * Current value of the search.
	 */

	private double currentValue;

	/**
	 * The last world the search was moidified in.
	 */

	int worldStamp;

	/**
	 *  The current {@link choco.mem.StoredIntTrail}.
	 */

	private final StoredFloatTrail trail;

	/**
	 * Constructs a stored search with an unknown initial value.
	 * Note: this constructor should not be used directly: one should instead
	 * use the Environment factory
	 */

	public StoredFloat(final Environment env) {
		this(env, Double.NaN);
	}

	/**
	 * Constructs a stored search with an initial value.
	 * Note: this constructor should not be used directly: one should instead
	 * use the Environment factory
	 */

	public StoredFloat(final Environment env, final double d) {
		this.environment = env;
		this.currentValue = d;
		this.worldStamp = env.currentWorld;
		this.trail =
			(StoredFloatTrail) this.environment
				.getTrail(Environment.FLOAT_TRAIL);
	}

	/** Modifies the value without storing the former value on the trailing stack.
	 * @param y the new value
	 * @param wstamp the stamp of the world in which the update is performed */

	void _set(final double y, final int wstamp) {
		this.currentValue = y;
		this.worldStamp = wstamp;
	}

	/**
	 * modifying a StoredInt by an increment
	 * @param delta
	 */
	public void add(final double delta) {
		this.set(this.get() + delta);
	}

	/**
	 * Returns the current value.
	 */

	public double get() {
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
		return this.currentValue != Double.NaN;
	}

	/**
	 * Modifies the value and stores if needed the former value on the
	 * trailing stack.
	 */

	public void set(final double y) {
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

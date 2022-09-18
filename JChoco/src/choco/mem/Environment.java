/** -------------------------------------------------
 *                   J-CHOCO
 *   Copyright (C) F. Laburthe, 1999-2003
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.mem.Environment.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco.mem;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import choco.util.LightFormatter;

/**
 * The root class for managing memory and sessions.
 * <p/>
 * A environment is associated to each problem.
 * It is responsible for managing backtrackable data.
 */
// new comment
public class Environment {

	/**
	 * Index of the {@link choco.mem.StoredIntTrail} for storing integers.
	 */

	public final static int INT_TRAIL = 0;

	/**
	 * Index of the {@link choco.mem.StoredVectorTrail} for storing vectors.
	 */

	public final static int VECTOR_TRAIL = 1;

	/**
	 * Index of the {@link choco.mem.StoredIntVectorTrail} for storing
	 * search vectors.
	 *
	 * @see #trails
	 */

	public final static int INT_VECTOR_TRAIL = 2;

	/**
	 * Index of the {@link choco.mem.StoredFloatTrail} for storing
	 * search vectors.
	 *
	 * @see #trails
	 */

	public final static int FLOAT_TRAIL = 3;

	/**
	 * The maximum numbers of objects that a
	 * {@link choco.mem.ITrailStorage} can handle.
	 */

	private final int maxHist;

	/**
	 * The maximum numbers of worlds that a
	 * {@link choco.mem.ITrailStorage} can handle.
	 */

	private int maxWorld;

	/**
	 * The current world number (should be less
	 * than <code>maxWorld</code>).
	 */

	public int currentWorld = 0;

	/**
	 * Contains all the {@link choco.mem.ITrailStorage} trails for
	 * storing different kinds of data.
	 */

	protected ITrailStorage[] trails;

	/**
	 * Reference to the root Logger object (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco");

	/**
	 * Constructs a new <code>Environment</code> with
	 * the default stack sizes : 50000 and 1000.
	 */

	public Environment() {
		this.maxHist = 50000;
		this.maxWorld = 1000;
		this.trails =
			new ITrailStorage[] {
					new StoredIntTrail(this, this.maxHist, this.maxWorld),
					new StoredVectorTrail(this, this.maxHist, this.maxWorld),
					new StoredIntVectorTrail(this, this.maxHist, this.maxWorld),
					new StoredFloatTrail(this, this.maxHist, this.maxWorld) };
		this.setDefaultHandler();
	}

	/**
	 * returns the size of the trail used for storing integers
	 */

	public int getIntTrailSize() {
		return this.trails[Environment.INT_TRAIL].getSize();
	}

	/**
	 * returns the size of the trail used for storing search arrays
	 */

	public int getIntVectorTrailSize() {
		return this.trails[Environment.INT_VECTOR_TRAIL].getSize();
	}

	/**
	 * Returns the <code>i</code>th trail in the trail array.
	 *
	 * @param i index of the trail.
	 */

	public ITrailStorage getTrail(final int i) {
		return this.trails[i];
	}

	/**
	 * returns the size of the trail
	 */

	public int getTrailSize() {
		int s = 0;
		for (int i = 0; i < this.trails.length; i++) {
			s += this.trails[i].getSize();
		}
		return s;
	}

	/**
	 * Returns the world number.
	 *
	 * @see #currentWorld
	 */

	public int getWorldIndex() {
		return this.currentWorld;
	}

	/**
	 * Factory pattern: new StoredBitSetVector objects are created by the environment
	 */
	public StoredBitSet makeBitSet(final int size) {
		return new StoredBitSet(this, size);
	}

	public StoredBitSet makeBitSet(final int[] entries) {
		return new StoredBitSet(this, 0); // TODO
	}

	/**
	 * Factory pattern: new IStateFloat objects are created by the environment
	 * (no initial value is assigned to the backtrackable search)
	 */

	public StoredFloat makeFloat() {
		return new StoredFloat(this);
	}

	/**
	 * Factory pattern: new IStateFloat objects are created by the environment
	 *
	 * @param initialValue the initial value of the backtrackable search
	 */

	public StoredFloat makeFloat(final double initialValue) {
		return new StoredFloat(this, initialValue);
	}

	/**
	 * Factory pattern: new IStateInt objects are created by the environment
	 * (no initial value is assigned to the backtrackable search)
	 */

	public IStateInt makeInt() {
		return new StoredInt(this);
	}

	/**
	 * Factory pattern: new IStateInt objects are created by the environment
	 *
	 * @param initialValue the initial value of the backtrackable search
	 */

	public IStateInt makeInt(final int initialValue) {
		return new StoredInt(this, initialValue);
	}

	/**
	 * Factory pattern: new IStateIntVector objects are created by the environment.
	 * Creates an empty vector
	 */

	public StoredIntVector makeIntVector() {
		return new StoredIntVector(this);
	}

	/**
	 * Factory pattern: new IStateIntVector objects are created by the environment
	 *
	 * @param size         the number of entries in the vector
	 * @param initialValue the common initial value for all entries (backtrackable integers)
	 */

	public StoredIntVector makeIntVector(final int size, final int initialValue) {
		return new StoredIntVector(this, size, initialValue);
	}

	/**
	 * Factory pattern: new IStateIntVector objects are created by the environment
	 *
	 * @param entries an array to be copied as set of initial contents of the vector
	 */

	public StoredIntVector makeIntVector(final int[] entries) {
		return new StoredIntVector(entries);
	}

	private void resizeWorldCapacity(final int newWorldCapacity) {
		for (int i = 0; i < this.trails.length; i++) {
			this.trails[i].resizeWorldCapacity(newWorldCapacity);
		}
		this.maxWorld = newWorldCapacity;
	}

	public void setDefaultHandler() {
		// define default levels, take into account only info, warning and severe messages

		Logger.getLogger("choco").setLevel(Level.ALL);
		Logger.getLogger("choco.search").setLevel(Level.INFO);
		Logger.getLogger("choco.prop").setLevel(Level.SEVERE);
		Logger.getLogger("choco.mem").setLevel(Level.SEVERE);
		Logger.getLogger("choco.integer").setLevel(Level.SEVERE);
		Logger.getLogger("choco.util").setLevel(Level.SEVERE);
		Logger.getLogger("choco.test").setLevel(Level.ALL);
		Logger.getLogger("choco.test.mem").setLevel(Level.SEVERE);
		Logger.getLogger("choco.test.integer").setLevel(Level.SEVERE);
		Logger.getLogger("choco.test.util").setLevel(Level.SEVERE);
		Logger.getLogger("choco.test.search").setLevel(Level.SEVERE);

		final StreamHandler sh =
			new StreamHandler(System.out, new LightFormatter());
		this.setHandler(sh);
	}

	private void setHandler(final Handler h) {
		// remove existing handler on choco logger and define choco handler
		// the handler defined here could be reused by other packages
		final Handler[] handlers = Environment.logger.getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			Environment.logger.removeHandler(handlers[i]);
		}
		// by default, handle (so print) only severe message, it could be modified in other package
		h.setLevel(Level.SEVERE);
		Environment.logger.addHandler(h);
		Environment.logger.setUseParentHandlers(false);
	}

	/**
	 * Accepts all modifications since the previous choice point in the search tree.
	 */

	public void worldCommit() {
		for (int i = 0; i < this.trails.length; i++) {
			final ITrailStorage trail = this.trails[i];
			trail.worldCommit();
		}
		this.currentWorld--;
	}

	/**
	 * Backtracks to the previous choice point in the search tree.
	 */
	public void worldPop() {
		for (int i = 0; i < this.trails.length; i++) {
			final ITrailStorage trail = this.trails[i];
			trail.worldPop();
		}
		this.currentWorld--;
	}

	/**
	 * Starts a new branch in the search tree.
	 */

	public void worldPush() {
		for (int i = 0; i < this.trails.length; i++) {
			final ITrailStorage trail = this.trails[i];
			trail.worldPush();
		}
		this.currentWorld++;
		if (this.currentWorld + 1 == this.maxWorld) {
			this.resizeWorldCapacity(this.maxWorld * 3 / 2);
		}
	}
}

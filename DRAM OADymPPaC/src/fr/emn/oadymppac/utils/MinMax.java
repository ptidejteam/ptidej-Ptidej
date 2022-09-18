package fr.emn.oadymppac.utils;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class MinMax {
	float min;
	float max;

	public MinMax() {
		this.min = Float.POSITIVE_INFINITY;
		this.max = Float.NEGATIVE_INFINITY;
	}

	public MinMax(final float min, final float max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Returns the max.
	 * @return float
	 */
	public float getMax() {
		return this.max;
	}

	/**
	 * Returns the min.
	 * @return float
	 */
	public float getMin() {
		return this.min;
	}

	/**
	 * Sets the max.
	 * @param max The max to set
	 */
	public void setMax(final float max) {
		this.max = max;
	}

	/**
	 * Sets the min.
	 * @param min The min to set
	 */
	public void setMin(final float min) {
		this.min = min;
	}

}

package fr.emn.oadymppac.graph.aggregation;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class performs the union between the elements 
 * of the passed arrays of objects. It can handle primitive
 * type arrays.
 */
public class ArrayUnion implements AggregationFunction {

	public final String PROPERTY_NAME = "Union";

	/**
	 * This method merges the elements of the passed 
	 * arrays and returns an array of the same type. It 
	 * handles arrays of <code>Object</code>s as well 
	 * as primitive types.
	 *
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#compute(Object[])
	 */
	public Object compute(final Object[] arrays) {
		// first, we handle primitive types
		if (arrays[0] instanceof short[]) {
			return this.shortUnion(arrays);
		}
		if (arrays[0] instanceof long[]) {
			return this.longUnion(arrays);
		}
		if (arrays[0] instanceof int[]) {
			return this.intUnion(arrays);
		}
		if (arrays[0] instanceof float[]) {
			return this.floatUnion(arrays);
		}
		if (arrays[0] instanceof double[]) {
			return this.doubleUnion(arrays);
		}

		// then we handle other arrays
		if (!(arrays[0] instanceof Array)) {
			return null;
		}

		Object[] array;
		final HashSet elements = new HashSet();
		for (int i = 0; i < arrays.length; i++) {
			array = (Object[]) arrays[i];
			for (int j = 0; j < array.length; j++) {
				elements.add(array[j]);
			}
		}
		return elements.toArray(new Object[elements.size()]);
	}

	/**
	 * This method performs the merger of the passed
	 * arrays of doubles.
	 * 
	 * @param arrays
	 * @return
	 */
	double[] doubleUnion(final Object[] arrays) {
		final HashSet objects = new HashSet();
		double[] tmp;
		for (int i = 0; i < arrays.length; i++) {
			tmp = (double[]) arrays[i];
			for (int j = 0; j < tmp.length; j++) {
				objects.add(Double.valueOf(tmp[j]));
			}
		}
		tmp = new double[objects.size()];
		final Iterator iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			tmp[i] = ((Double) iter.next()).doubleValue();
			i++;
		}
		return tmp;
	}

	/**
	 * This method performs the merger of the passed
	 * arrays of floats.
	 * 
	 * @param arrays
	 * @return
	 */
	float[] floatUnion(final Object[] arrays) {
		final HashSet objects = new HashSet();
		float[] tmp;
		for (int i = 0; i < arrays.length; i++) {
			tmp = (float[]) arrays[i];
			for (int j = 0; j < tmp.length; j++) {
				objects.add(Float.valueOf(tmp[j]));
			}
		}
		tmp = new float[objects.size()];
		final Iterator iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			tmp[i] = ((Float) iter.next()).floatValue();
			i++;
		}
		return tmp;
	}

	/**
	 * This method returns the number of elements in 
	 * the passed array of arrays.
	 * 
	 * @param arrays
	 * @return
	 */
	int getCount(final Object[] arrays) {
		int count = 0;
		for (int i = 0; i < arrays.length; i++) {
			count += Array.getLength(arrays[i]);
		}
		return count;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregationFunction#getFunctionDescription()
	 */
	public String getFunctionDescription() {
		return this.PROPERTY_NAME;
	}

	/**
	 * This method performs the merger of the passed
	 * arrays of ints.
	 * 
	 * @param arrays
	 * @return
	 */
	int[] intUnion(final Object[] arrays) {
		final HashSet objects = new HashSet();
		int[] tmp;
		for (int i = 0; i < arrays.length; i++) {
			tmp = (int[]) arrays[i];
			for (int j = 0; j < tmp.length; j++) {
				objects.add(Integer.valueOf(tmp[j]));
			}
		}
		tmp = new int[objects.size()];
		final Iterator iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			tmp[i] = ((Integer) iter.next()).intValue();
			i++;
		}
		return tmp;
	}

	/**
	 * This method performs the merger of the passed
	 * arrays of longs.
	 * 
	 * @param arrays
	 * @return
	 */
	long[] longUnion(final Object[] arrays) {
		final HashSet objects = new HashSet();
		long[] tmp;
		for (int i = 0; i < arrays.length; i++) {
			tmp = (long[]) arrays[i];
			for (int j = 0; j < tmp.length; j++) {
				objects.add(Long.valueOf(tmp[j]));
			}
		}
		tmp = new long[objects.size()];
		final Iterator iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			tmp[i] = ((Long) iter.next()).longValue();
			i++;
		}
		return tmp;
	}

	/**
	 * This method performs the merger of the passed
	 * arrays of shorts.
	 * 
	 * @param arrays
	 * @return
	 */
	short[] shortUnion(final Object[] arrays) {
		final HashSet objects = new HashSet();
		short[] tmp;
		for (int i = 0; i < arrays.length; i++) {
			tmp = (short[]) arrays[i];
			for (int j = 0; j < tmp.length; j++) {
				objects.add(Short.valueOf(tmp[j]));
			}
		}
		tmp = new short[objects.size()];
		final Iterator iter = objects.iterator();
		int i = 0;
		while (iter.hasNext()) {
			tmp[i] = ((Short) iter.next()).shortValue();
			i++;
		}
		return tmp;
	}
}

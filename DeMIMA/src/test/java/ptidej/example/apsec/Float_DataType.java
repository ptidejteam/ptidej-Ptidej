/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
/*
 * @(#)Float.java	1.80 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package ptidej.example.apsec;

/**
 * The <code>Float</code> class wraps a value of primitive type
 * <code>float</code> in an object. An object of type
 * <code>Float</code> contains a single field whose type is
 * <code>float</code>.
 * <p>
 * In addition, this class provides several methods for converting a
 * <code>float</code> to a <code>String</code> and a
 * <code>String</code> to a <code>float</code>, as well as other
 * constants and methods useful when dealing with a
 * <code>float</code>.
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @version 1.80, 01/23/03
 * @since JDK1.0 
 */
public final class Float_DataType extends Number implements Comparable {
	/**
	 * A constant holding the positive infinity of type
	 * <code>float</code>. It is equal to the value returned by
	 * <code>Float.intBitsToFloat(0x7f800000)</code>.
	 */
	public static final float POSITIVE_INFINITY = 1.0f / 0.0f;

	/**
	 * A constant holding the negative infinity of type
	 * <code>float</code>. It is equal to the value returned by
	 * <code>Float.intBitsToFloat(0xff800000)</code>.
	 */
	public static final float NEGATIVE_INFINITY = -1.0f / 0.0f;

	/** 
	 * A constant holding a Not-a-Number (NaN) value of type
	 * <code>float</code>.  It is equivalent to the value returned by
	 * <code>Float.intBitsToFloat(0x7fc00000)</code>.
	 */
	public static final float NaN = 0.0f / 0.0f;

	/**
	 * A constant holding the largest positive finite value of type
	 * <code>float</code>, (2-2<sup>-23</sup>)&middot;2<sup>127</sup>.
	 * It is equal to the value returned by
	 * <code>Float.intBitsToFloat(0x7f7fffff)</code>.
	 */
	public static final float MAX_VALUE = 3.4028235e+38f;

	/**
	 * A constant holding the smallest positive nonzero value of type
	 * <code>float</code>, 2<sup>-149</sup>. It is equal to the value
	 * returned by <code>Float.intBitsToFloat(0x1)</code>.
	 */
	public static final float MIN_VALUE = 1.4e-45f;

	/**
	 * The <code>Class</code> instance representing the primitive type
	 * <code>float</code>.
	 *
	 * @since JDK1.1 
	 */
	public static final Class TYPE = null; // Class.getPrimitiveClass("float");

	/** use serialVersionUID from JDK 1.0.2 for interoperability */
	private static final long serialVersionUID = -2671257302660747028L;

	/**
	 * Compares the two specified <code>float</code> values. The sign
	 * of the integer value returned is the same as that of the
	 * integer that would be returned by the call:
	 * <pre>
	 *    Float.valueOf(f1).compareTo(Float.valueOf(f2))
	 * </pre>
	 *
	 * @param   f1        the first <code>float</code> to compare.
	 * @param   f2        the second <code>float</code> to compare.
	 * @return  the value <code>0</code> if <code>f1</code> is
	 *		numerically equal to <code>f2</code>; a value less than
	 *          <code>0</code> if <code>f1</code> is numerically less than
	 *		<code>f2</code>; and a value greater than <code>0</code>
	 *		if <code>f1</code> is numerically greater than
	 *		<code>f2</code>.
	 * @since 1.4 
	 */
	public static int compare(final float f1, final float f2) {
		if (f1 < f2) {
			return -1; // Neither val is NaN, thisVal is smaller
		}
		if (f1 > f2) {
			return 1; // Neither val is NaN, thisVal is larger
		}

		final int thisBits = Float_DataType.floatToIntBits(f1);
		final int anotherBits = Float_DataType.floatToIntBits(f2);

		return thisBits == anotherBits ? 0 : // Values are equal
				thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
						1; // (0.0, -0.0) or (NaN, !NaN)
	}

	/**
	 * Returns a representation of the specified floating-point value
	 * according to the IEEE 754 floating-point "single format" bit
	 * layout.
	 * <p>
	 * Bit 31 (the bit that is selected by the mask 
	 * <code>0x80000000</code>) represents the sign of the floating-point 
	 * number. 
	 * Bits 30-23 (the bits that are selected by the mask 
	 * <code>0x7f800000</code>) represent the exponent. 
	 * Bits 22-0 (the bits that are selected by the mask 
	 * <code>0x007fffff</code>) represent the significand (sometimes called 
	 * the mantissa) of the floating-point number. 
	 * <p>If the argument is positive infinity, the result is 
	 * <code>0x7f800000</code>. 
	 * <p>If the argument is negative infinity, the result is 
	 * <code>0xff800000</code>. 
	 * <p>If the argument is NaN, the result is <code>0x7fc00000</code>. 
	 * <p>
	 * In all cases, the result is an integer that, when given to the 
	 * {@link #intBitsToFloat(int)} method, will produce a floating-point 
	 * value the same as the argument to <code>floatToIntBits</code>
	 * (except all NaN values are collapsed to a single
	 * &quot;canonical&quot; NaN value).
	 * 
	 * @param   value   a floating-point number.
	 * @return the bits that represent the floating-point number.  
	 */
	public static native int floatToIntBits(float value);

	/**
	 * Returns a representation of the specified floating-point value
	 * according to the IEEE 754 floating-point "single format" bit
	 * layout, preserving Not-a-Number (NaN) values.
	 * <p>
	 * Bit 31 (the bit that is selected by the mask 
	 * <code>0x80000000</code>) represents the sign of the floating-point 
	 * number. 
	 * Bits 30-23 (the bits that are selected by the mask 
	 * <code>0x7f800000</code>) represent the exponent. 
	 * Bits 22-0 (the bits that are selected by the mask 
	 * <code>0x007fffff</code>) represent the significand (sometimes called 
	 * the mantissa) of the floating-point number. 
	 * <p>If the argument is positive infinity, the result is 
	 * <code>0x7f800000</code>. 
	 * <p>If the argument is negative infinity, the result is 
	 * <code>0xff800000</code>.
	 * <p>
	 * If the argument is NaN, the result is the integer representing
	 * the actual NaN value.  Unlike the <code>floatToIntBits</code>
	 * method, <code>intToRawIntBits</code> does not collapse all the
	 * bit patterns encoding a NaN to a single &quot;canonical&quot;
	 * NaN value.
	 * <p>
	 * In all cases, the result is an integer that, when given to the
	 * {@link #intBitsToFloat(int)} method, will produce a
	 * floating-point value the same as the argument to
	 * <code>floatToRawIntBits</code>.
	 * @param   value   a floating-point number.
	 * @return the bits that represent the floating-point number.
	 */
	public static native int floatToRawIntBits(float value);

	/**
	 * Returns the <code>float</code> value corresponding to a given
	 * bit represention.
	 * The argument is considered to be a representation of a
	 * floating-point value according to the IEEE 754 floating-point
	 * "single format" bit layout.
	 * <p>
	 * If the argument is <code>0x7f800000</code>, the result is positive
	 * infinity.
	 * <p>
	 * If the argument is <code>0xff800000</code>, the result is negative
	 * infinity.
	 * <p>
	 * If the argument is any value in the range
	 * <code>0x7f800001</code> through <code>0x7fffffff</code> or in
	 * the range <code>0xff800001</code> through
	 * <code>0xffffffff</code>, the result is a NaN.  No IEEE 754
	 * floating-point operation provided by Java can distinguish
	 * between two NaN values of the same type with different bit
	 * patterns.  Distinct values of NaN are only distinguishable by
	 * use of the <code>Float.floatToRawIntBits</code> method.
	 * <p>
	 * In all other cases, let <i>s</i>, <i>e</i>, and <i>m</i> be three 
	 * values that can be computed from the argument: 
	 * <blockquote><pre>
	 * int s = ((bits &gt;&gt; 31) == 0) ? 1 : -1;
	 * int e = ((bits &gt;&gt; 23) & 0xff);
	 * int m = (e == 0) ?
	 *                 (bits & 0x7fffff) &lt;&lt; 1 :
	 *                 (bits & 0x7fffff) | 0x800000;
	 * </pre></blockquote>
	 * Then the floating-point result equals the value of the mathematical 
	 * expression <i>s</i>&middot;<i>m</i>&middot;2<sup><i>e</i>-150</sup>.
	 *<p>
	 * Note that this method may not be able to return a
	 * <code>float</code> NaN with exactly same bit pattern as the
	 * <code>int</code> argument.  IEEE 754 distinguishes between two
	 * kinds of NaNs, quiet NaNs and <i>signaling NaNs</i>.  The
	 * differences between the two kinds of NaN are generally not
	 * visible in Java.  Arithmetic operations on signaling NaNs turn
	 * them into quiet NaNs with a different, but often similar, bit
	 * pattern.  However, on some processors merely copying a
	 * signaling NaN also performs that conversion.  In particular,
	 * copying a signaling NaN to return it to the calling method may
	 * perform this conversion.  So <code>intBitsToFloat</code> may
	 * not be able to return a <code>float</code> with a signaling NaN
	 * bit pattern.  Consequently, for some <code>int</code> values,
	 * <code>floatToRawIntBits(intBitsToFloat(start))</code> may
	 * <i>not</i> equal <code>start</code>.  Moreover, which
	 * particular bit patterns represent signaling NaNs is platform
	 * dependent; although all NaN bit patterns, quiet or signaling,
	 * must be in the NaN range identified above.
	 *
	 * @param   bits   an integer.
	 * @return  the <code>float</code> floating-point value with the same bit
	 *          pattern.
	 */
	public static native float intBitsToFloat(int bits);

	/**
	 * Returns <code>true</code> if the specified number is infinitely
	 * large in magnitude, <code>false</code> otherwise.
	 *
	 * @param   v   the value to be tested.
	 * @return  <code>true</code> if the argument is positive infinity or
	 *          negative infinity; <code>false</code> otherwise.
	 */
	static public boolean isInfinite(final float v) {
		return v == Float_DataType.POSITIVE_INFINITY
				|| v == Float_DataType.NEGATIVE_INFINITY;
	}

	/**
	 * Returns <code>true</code> if the specified number is a
	 * Not-a-Number (NaN) value, <code>false</code> otherwise.
	 *
	 * @param   v   the value to be tested.
	 * @return  <code>true</code> if the argument is NaN;
	 *          <code>false</code> otherwise.
	 */
	static public boolean isNaN(final float v) {
		return v != v;
	}

	/**
	 * Returns a new <code>float</code> initialized to the value
	 * represented by the specified <code>String</code>, as performed
	 * by the <code>valueOf</code> method of class <code>Float</code>.
	 *
	 * @param      s   the string to be parsed.
	 * @return the <code>float</code> value represented by the string
	 *         argument.
	 * @exception  NumberFormatException  if the string does not contain a
	 *               parsable <code>float</code>.
	 * @see        Float#valueOf(String)
	 * @since 1.2
	 */
	public static float parseFloat(final String s) throws NumberFormatException {
		return 0f; // FloatingDecimal.readJavaFormatString(s).floatValue();
	}

	/**
	 * Returns a string representation of the <code>float</code>
	 * argument. All characters mentioned below are ASCII characters.
	 * <ul>
	 * <li>If the argument is NaN, the result is the string
	 * &quot;<code>NaN</code>&quot;.
	 * <li>Otherwise, the result is a string that represents the sign and 
	 *     magnitude (absolute value) of the argument. If the sign is
	 *     negative, the first character of the result is
	 *     '<code>-</code>' (<code>'&#92;u002D'</code>); if the sign is
	 *     positive, no sign character appears in the result. As for
	 *     the magnitude <i>m</i>:
	 * <ul>
	 * <li>If <i>m</i> is infinity, it is represented by the characters 
	 *     <code>"Infinity"</code>; thus, positive infinity produces
	 *     the result <code>"Infinity"</code> and negative infinity
	 *     produces the result <code>"-Infinity"</code>.
	 * <li>If <i>m</i> is zero, it is represented by the characters 
	 *     <code>"0.0"</code>; thus, negative zero produces the result
	 *     <code>"-0.0"</code> and positive zero produces the result
	 *     <code>"0.0"</code>.
	 * <li> If <i>m</i> is greater than or equal to 10<sup>-3</sup> but 
	 *      less than 10<sup>7</sup>, then it is represented as the
	 *      integer part of <i>m</i>, in decimal form with no leading
	 *      zeroes, followed by '<code>.</code>'
	 *      (<code>'&#92;u002E'</code>), followed by one or more
	 *      decimal digits representing the fractional part of
	 *      <i>m</i>.
	 * <li> If <i>m</i> is less than 10<sup>-3</sup> or greater than or
	 *      equal to 10<sup>7</sup>, then it is represented in
	 *      so-called "computerized scientific notation." Let <i>n</i>
	 *      be the unique integer such that 10<sup><i>n</i> </sup>&lt;= 
	 *      <i>m</i> &lt; 10<sup><i>n</i>+1</sup>; then let <i>a</i> 
	 *      be the mathematically exact quotient of <i>m</i> and 
	 *      10<sup><i>n</i></sup> so that 1 &lt;= <i>a</i> &lt; 10.
	 *      The magnitude is then represented as the integer part of
	 *      <i>a</i>, as a single decimal digit, followed by
	 *      '<code>.</code>' (<code>'&#92;u002E'</code>), followed by
	 *      decimal digits representing the fractional part of
	 *      <i>a</i>, followed by the letter '<code>E</code>'
	 *      (<code>'&#92;u0045'</code>), followed by a representation
	 *      of <i>n</i> as a decimal integer, as produced by the
	 *      method <code>{@link
	 *      Integer#toString(int)}</code>.
	 * </ul>
	 * </ul>
	 * How many digits must be printed for the fractional part of
	 * <i>m</i> or <i>a</i>? There must be at least one digit
	 * to represent the fractional part, and beyond that as many, but
	 * only as many, more digits as are needed to uniquely distinguish
	 * the argument value from adjacent values of type
	 * <code>float</code>. That is, suppose that <i>x</i> is the
	 * exact mathematical value represented by the decimal
	 * representation produced by this method for a finite nonzero
	 * argument <i>f</i>. Then <i>f</i> must be the <code>float</code>
	 * value nearest to <i>x</i>; or, if two <code>float</code> values are
	 * equally close to <i>x</i>, then <i>f</i> must be one of
	 * them and the least significant bit of the significand of
	 * <i>f</i> must be <code>0</code>.
	 * <p>
	 * To create localized string representations of a floating-point
	 * value, use subclasses of {@link java.text.NumberFormat}.
	 *
	 * @param   f   the float to be converted.
	 * @return a string representation of the argument.
	 */
	public static String toString(final float f) {
		return null; // new FloatingDecimal(f).toJavaFormatString();
	}

	/**
	 * Returns a <code>Float</code> object holding the
	 * <code>float</code> value represented by the argument string
	 * <code>s</code>.
	 * <p>
	 * If <code>s</code> is <code>null</code>, then a 
	 * <code>NullPointerException</code> is thrown. 
	 * <p>
	 * Leading and trailing whitespace characters in <code>s</code>
	 * are ignored. The rest of <code>s</code> should constitute a
	 * <i>FloatValue</i> as described by the lexical syntax rules:
	 * <blockquote><i>
	 * <dl>
	 * <dt>FloatValue:
	 * <dd><i>Sign<sub>opt</sub></i> <code>NaN</code>
	 * <dd><i>Sign<sub>opt</sub></i> <code>Infinity</code>
	 * <dd>Sign<sub>opt</sub> FloatingPointLiteral
	 * </dl>
	 * </i></blockquote>
	 * where <i>Sign</i> and <i>FloatingPointLiteral</i> are as
	 * defined in <a href="http://java.sun.com/docs/books/jls/second_edition/html/lexical.doc.html#230798">&sect;3.10.2</a> 
	 * of the <a href="http://java.sun.com/docs/books/jls/html/">Java 
	 * Language Specification</a>. If <code>s</code> does not have the
	 * form of a <i>FloatValue</i>, then a
	 * <code>NumberFormatException</code> is thrown. Otherwise,
	 * <code>s</code> is regarded as representing an exact decimal
	 * value in the usual "computerized scientific notation"; this
	 * exact decimal value is then conceptually converted to an
	 * "infinitely precise" binary value that is then rounded to type
	 * <code>float</code> by the usual round-to-nearest rule of IEEE
	 * 754 floating-point arithmetic, which includes preserving the
	 * sign of a zero value.  Finally, a <code>Float</code> object
	 * representing this <code>float</code> value is returned.
	 * <p>
	 * To interpret localized string representations of a
	 * floating-point value, use subclasses of {@link
	 * java.text.NumberFormat}.
	 *
	 * <p>Note that trailing format specifiers, specifiers that
	 * determine the type of a floating-point literal
	 * (<code>1.0f</code> is a <code>float</code> value;
	 * <code>1.0d</code> is a <code>double</code> value), do
	 * <em>not</em> influence the results of this method.  In other
	 * words, the numerical value of the input string is converted
	 * directly to the target floating-point type.  In general, the
	 * two-step sequence of conversions, string to <code>double</code>
	 * followed by <code>double</code> to <code>float</code>, is
	 * <em>not</em> equivalent to converting a string directly to
	 * <code>float</code>.  For example, if first converted to an
	 * intermediate <code>double</code> and then to
	 * <code>float</code>, the string<br>
	 * <code>"1.00000017881393421514957253748434595763683319091796875001d"</code><br>
	 * results in the <code>float</code> value
	 * <code>1.0000002f</code>; if the string is converted directly to
	 * <code>float</code>, <code>1.000000<b>1</b>f</code> results.
	 *
	 * @param      s   the string to be parsed.
	 * @return     a <code>Float</code> object holding the value
	 *             represented by the <code>String</code> argument.
	 * @exception  NumberFormatException  if the string does not contain a
	 *               parsable number.  
	 */
	public static Float_DataType valueOf(final String s)
			throws NumberFormatException {
		return new Float_DataType(null); // FloatingDecimal.readJavaFormatString(s).floatValue());
	}

	/**
	 * The value of the Float.
	 *
	 * @serial
	 */
	private final float value;

	/**
	 * Constructs a newly allocated <code>Float</code> object that
	 * represents the argument converted to type <code>float</code>.
	 *
	 * @param   value   the value to be represented by the <code>Float</code>.
	 */
	public Float_DataType(final double value) {
		this.value = (float) value;
	}

	/**
	 * Constructs a newly allocated <code>Float</code> object that
	 * represents the primitive <code>float</code> argument.
	 *
	 * @param   value   the value to be represented by the <code>Float</code>.
	 */
	public Float_DataType(final float value) {
		this.value = value;
	}

	/**
	 * Constructs a newly allocated <code>Float</code> object that 
	 * represents the floating-point value of type <code>float</code> 
	 * represented by the string. The string is converted to a 
	 * <code>float</code> value as if by the <code>valueOf</code> method. 
	 *
	 * @param      s   a string to be converted to a <code>Float</code>.
	 * @exception  NumberFormatException  if the string does not contain a
	 *               parsable number.
	 * @see        Float#valueOf(String)
	 */
	public Float_DataType(final String s) throws NumberFormatException {
		// REMIND: this is inefficient
		this(Float_DataType.valueOf(s).floatValue());
	}

	/**
	 * Returns the value of this <code>Float</code> as a
	 * <code>byte</code> (by casting to a <code>byte</code>).
	 *
	 * @return  the <code>float</code> value represented by this object
	 *          converted to type <code>byte</code>
	 */
	public byte byteValue() {
		return (byte) this.value;
	}

	/**
	 * Compares two <code>Float</code> objects numerically.  There are
	 * two ways in which comparisons performed by this method differ
	 * from those performed by the Java language numerical comparison
	 * operators (<code>&lt;, &lt;=, ==, &gt;= &gt;</code>) when
	 * applied to primitive <code>float</code> values:
	 * <ul><li>
	 *		<code>Float.NaN</code> is considered by this method to
	 *		be equal to itself and greater than all other
	 *		<code>float</code> values
	 *		(including <code>Float.POSITIVE_INFINITY</code>).
	 * <li>
	 *		<code>0.0f</code> is considered by this method to be greater
	 *		than <code>-0.0f</code>.
	 * </ul>
	 * This ensures that <code>Float.compareTo(Object)</code> (which
	 * forwards its behavior to this method) obeys the general
	 * contract for <code>Comparable.compareTo</code>, and that the
	 * <i>natural order</i> on <code>Float</code>s is <i>consistent
	 * with equals</i>.
	 *
	 * @param   anotherFloat   the <code>Float</code> to be compared.
	 * @return  the value <code>0</code> if <code>anotherFloat</code> is
	 *		numerically equal to this <code>Float</code>; a value
	 *		less than <code>0</code> if this <code>Float</code>
	 *		is numerically less than <code>anotherFloat</code>;
	 *		and a value greater than <code>0</code> if this
	 *		<code>Float</code> is numerically greater than
	 *		<code>anotherFloat</code>.
	 *		
	 * @since   1.2
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(final Float_DataType anotherFloat) {
		return Float_DataType.compare(this.value, anotherFloat.value);
	}

	/**
	 * Compares this <code>Float</code> object to another object.  If
	 * the object is a <code>Float</code>, this function behaves like
	 * <code>compareTo(Float)</code>.  Otherwise, it throws a
	 * <code>ClassCastException</code> (as <code>Float</code> objects
	 * are comparable only to other <code>Float</code> objects).
	 *
	 * @param   o the <code>Object</code> to be compared.
	 * @return the value <code>0</code> if the argument is a
	 *		<code>Float</code> numerically equal to this
	 *		<code>Float</code>; a value less than <code>0</code>
	 *		if the argument is a <code>Float</code> numerically
	 *		greater than this <code>Float</code>; and a value
	 *		greater than <code>0</code> if the argument is a
	 *		<code>Float</code> numerically less than this
	 *		<code>Float</code> .
	 * @exception <code>ClassCastException</code> if the argument is not a
	 *		  <code>Float</code>.
	 * @see     Comparable
	 * @since 1.2
	 */
	public int compareTo(final Object o) {
		return this.compareTo((Float_DataType) o);
	}

	/**
	 * Returns the <code>double</code> value of this
	 * <code>Float</code> object.
	 * 
	 * @return the <code>float</code> value represented by this 
	 *         object is converted to type <code>double</code> and the 
	 *         result of the conversion is returned.  
	 */
	public double doubleValue() {
		return this.value;
	}

	/**

	 * Compares this object against the specified object.  The result
	 * is <code>true</code> if and only if the argument is not
	 * <code>null</code> and is a <code>Float</code> object that
	 * represents a <code>float</code> with the same value as the
	 * <code>float</code> represented by this object. For this
	 * purpose, two <code>float</code> values are considered to be the
	 * same if and only if the method {@link #floatToIntBits(float)}
	 * returns the identical <code>int</code> value when applied to
	 * each.
	 * <p>
	 * Note that in most cases, for two instances of class
	 * <code>Float</code>, <code>f1</code> and <code>f2</code>, the value
	 * of <code>f1.equals(f2)</code> is <code>true</code> if and only if
	 * <blockquote><pre>
	 *   f1.floatValue() == f2.floatValue()
	 * </pre></blockquote>
	 * <p>
	 * also has the value <code>true</code>. However, there are two exceptions:
	 * <ul>
	 * <li>If <code>f1</code> and <code>f2</code> both represent
	 *     <code>Float.NaN</code>, then the <code>equals</code> method returns
	 *     <code>true</code>, even though <code>Float.NaN==Float.NaN</code>
	 *     has the value <code>false</code>.
	 * <li>If <code>f1</code> represents <code>+0.0f</code> while
	 *     <code>f2</code> represents <code>-0.0f</code>, or vice
	 *     versa, the <code>equal</code> test has the value
	 *     <code>false</code>, even though <code>0.0f==-0.0f</code>
	 *     has the value <code>true</code>.
	 * </ul>
	 * This definition allows hash tables to operate properly.
	 *
	 * @param obj the object to be compared
	 * @return  <code>true</code> if the objects are the same;
	 *          <code>false</code> otherwise.
	 * @see Float#floatToIntBits(float)
	 */
	public boolean equals(final Object obj) {
		return obj instanceof Float_DataType
				&& Float_DataType.floatToIntBits(((Float_DataType) obj).value) == Float_DataType
					.floatToIntBits(this.value);
	}

	/**
	 * Returns the <code>float</code> value of this <code>Float</code>
	 * object.
	 *
	 * @return the <code>float</code> value represented by this object 
	 */
	public float floatValue() {
		return this.value;
	}

	/**
	 * Returns a hash code for this <code>Float</code> object. The
	 * result is the integer bit representation, exactly as produced
	 * by the method {@link #floatToIntBits(float)}, of the primitive
	 * <code>float</code> value represented by this <code>Float</code>
	 * object.
	 *
	 * @return a hash code value for this object.  
	 */
	public int hashCode() {
		return Float_DataType.floatToIntBits(this.value);
	}

	/**
	 * Returns the value of this <code>Float</code> as an
	 * <code>int</code> (by casting to type <code>int</code>).
	 *
	 * @return  the <code>float</code> value represented by this object
	 *          converted to type <code>int</code>
	 */
	public int intValue() {
		return (int) this.value;
	}

	/**
	 * Returns <code>true</code> if this <code>Float</code> value is
	 * infinitely large in magnitude, <code>false</code> otherwise.
	 *
	 * @return  <code>true</code> if the value represented by this object is
	 *          positive infinity or negative infinity;
	 *          <code>false</code> otherwise.
	 */
	public boolean isInfinite() {
		return Float_DataType.isInfinite(this.value);
	}

	/**
	 * Returns <code>true</code> if this <code>Float</code> value is a
	 * Not-a-Number (NaN), <code>false</code> otherwise.
	 *
	 * @return  <code>true</code> if the value represented by this object is
	 *          NaN; <code>false</code> otherwise.
	 */
	public boolean isNaN() {
		return Float_DataType.isNaN(this.value);
	}

	/**
	 * Returns value of this <code>Float</code> as a <code>long</code>
	 * (by casting to type <code>long</code>).
	 *
	 * @return  the <code>float</code> value represented by this object
	 *          converted to type <code>long</code>
	 */
	public long longValue() {
		return (long) this.value;
	}

	/**
	 * Returns the value of this <code>Float</code> as a
	 * <code>short</code> (by casting to a <code>short</code>).
	 *
	 * @return  the <code>float</code> value represented by this object
	 *          converted to type <code>short</code>
	 * @since JDK1.1
	 */
	public short shortValue() {
		return (short) this.value;
	}

	/**
	 * Returns a string representation of this <code>Float</code> object.
	 * The primitive <code>float</code> value represented by this object
	 * is converted to a <code>String</code> exactly as if by the method
	 * <code>toString</code> of one argument.
	 *
	 * @return  a <code>String</code> representation of this object.
	 * @see Float#toString(float)
	 */
	public String toString() {
		return String.valueOf(this.value);
	}
}

package com.jmonkey.export;

import java.awt.Color;
/**
 *
 * This class provides C-like formatting functions that allow
 * programmers to convert an integer or floating point number
 * into a string with a specified, width, precision and format. 
 * For instance this might be used to format monetary data to 
 * two decimal places.<P>
 *
 * Each number is passed to a format() method along with formatting 
 * instructions. The format() method returns a formatted string. <P>
 *
 * There are a number of possible things one can do when a number
 * will not fit inside the specified format. You can throw an exception,
 * truncate the number, return an error string, or expand the width.
 * Here I've chosen to expand the width. <P>
 *
 * The rounding of these precisions still needs work. Currently 
 * excess digits are merely truncated.<P>
 *
 * @Version: 1.0 Revision 1
 * @Author: Brill Pappin <I>Portions from <CODE>cafeaulait.io.Formatter</CODE> by Elliotte Rusty Harold (elharo@sunsite.unc.edu)</I>
 */

public class Format {
	private static char[] lowercases =
		{
			'\000',
			'\001',
			'\002',
			'\003',
			'\004',
			'\005',
			'\006',
			'\007',
			'\010',
			'\011',
			'\012',
			'\013',
			'\014',
			'\015',
			'\016',
			'\017',
			'\020',
			'\021',
			'\022',
			'\023',
			'\024',
			'\025',
			'\026',
			'\027',
			'\030',
			'\031',
			'\032',
			'\033',
			'\034',
			'\035',
			'\036',
			'\037',
			'\040',
			'\041',
			'\042',
			'\043',
			'\044',
			'\045',
			'\046',
			'\047',
			'\050',
			'\051',
			'\052',
			'\053',
			'\054',
			'\055',
			'\056',
			'\057',
			'\060',
			'\061',
			'\062',
			'\063',
			'\064',
			'\065',
			'\066',
			'\067',
			'\070',
			'\071',
			'\072',
			'\073',
			'\074',
			'\075',
			'\076',
			'\077',
			'\100',
			'\141',
			'\142',
			'\143',
			'\144',
			'\145',
			'\146',
			'\147',
			'\150',
			'\151',
			'\152',
			'\153',
			'\154',
			'\155',
			'\156',
			'\157',
			'\160',
			'\161',
			'\162',
			'\163',
			'\164',
			'\165',
			'\166',
			'\167',
			'\170',
			'\171',
			'\172',
			'\133',
			'\134',
			'\135',
			'\136',
			'\137',
			'\140',
			'\141',
			'\142',
			'\143',
			'\144',
			'\145',
			'\146',
			'\147',
			'\150',
			'\151',
			'\152',
			'\153',
			'\154',
			'\155',
			'\156',
			'\157',
			'\160',
			'\161',
			'\162',
			'\163',
			'\164',
			'\165',
			'\166',
			'\167',
			'\170',
			'\171',
			'\172',
			'\173',
			'\174',
			'\175',
			'\176',
			'\177' };
	/**
	* The Separator cahr used in universal paths.
	*/
	public static final char UNIVERSAL_SEPARATOR_CHAR = '/';

	/**
	* Do not allow instances to be created.
	*/

	private Format() {
		super();
	}
	/**
	* fast lower case conversion. Only works on ascii (not unicode)
	* @author Jesper Jørgensen, Caput
	* @param s the string to convert
	* @return a lower case version of s
	*/
	public static String asciiToLowerCase(String s) {
		char[] c = s.toCharArray();
		for (int i = c.length; i-- > 0;) {
			if (c[i] <= 127)
				c[i] = lowercases[c[i]];
		}
		return (new String(c));
	}
	/**
	 * Converts a type Color to an HTML hex colour string 
	 * in the format "#RRGGBB"
	 * @param colour Color
	 * @return String
	 */
	public static final String colorToHex(Color colour) {
		String colorstr = new String("#");

		// Red
		String str = Integer.toHexString(colour.getRed());
		if (str.length() > 2)
			throw new Error("invalid red value");
		else if (str.length() < 2)
			colorstr += "0" + str;
		else
			colorstr += str;

		// Green
		str = Integer.toHexString(colour.getGreen());
		if (str.length() > 2)
			throw new Error("invalid green value");
		else if (str.length() < 2)
			colorstr += "0" + str;
		else
			colorstr += str;

		// Blue
		str = Integer.toHexString(colour.getBlue());
		if (str.length() > 2)
			throw new Error("invalid green value");
		else if (str.length() < 2)
			colorstr += "0" + str;
		else
			colorstr += str;
		return colorstr.toUpperCase();
	}
	/**
	* It would be nice to add a method to java.lang.String
	* to return an escape safe version of the string so that
	* you can write it to [eg] property files without
	* worrying about reading back in incorrect escaped
	* characters created by any \ characters in the string.<BR>
	* This Method will attempt to do that.
	* @param s String
	* @return String
	*/

	public static final String escapeSafe(String s) {
		StringBuffer b = new StringBuffer();
		int counter = 0;
		for (counter = 0; counter < s.length(); counter++) {
			if (s.charAt(counter) == '\\') {
				b.append("\\\\");
			}
			else {
				b.append(s.charAt(counter));
			}
		}
		return b.toString();
	}
	/**
	* Converts an array of hash bytes into a hex string.
	* @param byte hash[]
	* @return String
	* @version 1.0
	* @author Julian Yip Aug. 1998 (Modified Brill Pappin Aug. 1998)
	*/
	public static final String hashToHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		for (int i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10)
				buf.append("0");
			buf.append(Integer.toHexString((int) hash[i] & 0xff));
		}
		return buf.toString();
	}
	/**
	 * Converts an HTML "#FFFFFF" hex string to a java.awt.Color
	 * @param value String
	 * @return Color
	 */
	public static final Color hexToColor(String value) {
		if (value.length() != 7) {
			throw new Error("invalid hex color string length");
		}
		else if (value.startsWith("#")) {
			// String str = "0x" + value.substring(1, value.length());
			Color c = Color.decode(value);
			return c;
		}
		return null;
	}
	/**
	 * Converts a 2 position hex number "NN" string into an integer
	 * @param hex String
	 * @return int
	 */
	public static final int hexToInt(String hex) {
		if (hex.length() != 2)
			throw new Error("invalid hex string " + hex);
		int pos1 = Character.digit(hex.charAt(0), 16) * 16;
		int pos0 = Character.digit(hex.charAt(1), 16);
		return (pos0 + pos1);
	}
	/**
	* Copy input string to output string keeping wanted characters only.
	* @return java.lang.String
	* @param input java.lang.String
	* @param wantedChars java.lang.String
	* @author Ian MacMillan Aug. 1998 
	*/
	public final static String keepChars(String input, String wantedChars) {

		char[] cArr = new char[input.length()];
		char curChar = ' ';

		/* for each input char */
		int ox = 0;
		for (int n = 0; n < input.length(); n++) {
			/* is current char wanted */
			curChar = input.charAt(n);
			if (wantedChars.indexOf(curChar) >= 0) {
				cArr[ox] = curChar;
				ox++;
			}
		}

		return new String(cArr, 0, ox);
	}
	/**
	* Converts a universal path into a native path.
	* @return java.lang.String
	* @param universalPath java.lang.String
	*/
	public static final String nativePath(String universalPath) {
		return universalPath.replace('/', java.io.File.separatorChar);
	}
	/**
	* Strips the chars in the String argument, out of the input string and returns the resulting string.
	* @return java.lang.String
	* @param input java.lang.String
	* @param chars java.lang.String
	*/
	public final static String removeChars(String input, String remChars) {
		char[] cArr = new char[input.length()];
		char curChar = ' ';
		/* for each input char */
		int ox = 0;
		for (int n = 0; n < input.length(); n++) {
			/* is current char wanted */
			curChar = input.charAt(n);
			if (remChars.indexOf(curChar) < 0) {
				cArr[ox] = curChar;
				ox++;
			}
		}
		return new String(cArr, 0, ox);
	}
	/**
	* Works like String.trim() but removes the quotes at the beginning and end of a string.
	* This method will do nothing if it does not find quotes <U>at both ends</U> of the string.<BR>
	* "xxxxx" returns xxxxx.
	* @param s String
	* @return String
	*/
	public static final String removeSurroundingQuotes(String s) {
		String result = new String(s.trim());
		if (s.startsWith("\"") && s.endsWith("\"")) {
			result = new String(s.substring(1, s.length() - 1));
		}
		return result;
	}
	/**
	* replace substrings within string.
	*/
	public static String replace(String s, String sub, String with) {
		StringBuffer buf = new StringBuffer(s.length() * 2);
		int c = 0;
		int i = 0;
		while ((i = s.indexOf(sub, c)) != -1) {
			buf.append(s.substring(c, i));
			buf.append(with);
			c = i + sub.length();
		}
		if (c < s.length())
			buf.append(s.substring(c, s.length()));
		return buf.toString();
	}
	/**
	* This method was created in VisualAge.
	* @return java.lang.String
	* @param universalPath java.lang.String
	* @depricated use nativePath(String) instead.
	*/
	public static final String systemPath(String universalPath) {
		return Format.nativePath(universalPath);
	}
	/**
	* converts a native path into a universal one.
	* @return java.lang.String
	* @param systemPath java.lang.String
	*/
	public static final String universalPath(String nativePath) {
		String newPath = null;
		int index = nativePath.indexOf(java.io.File.separator);
		if (index >= 0 && (index + 1) < nativePath.length()) {
			newPath = nativePath.substring(index + 1, nativePath.length());
		}
		else {
			newPath = nativePath;
		}
		return newPath.replace(java.io.File.separatorChar, '/');
	}
}

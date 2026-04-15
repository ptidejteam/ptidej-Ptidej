package com.jmonkey.export;

import java.awt.Color;
import java.io.File;

/**
 * Insert the type's description here.
 * Creation date: (18/05/2000 1:30:33 AM)
 * @author: 
 */
public final class Runtime {
/**
 * Runtime constructor comment.
 */
public Runtime() {
	super();
}
	/**
	* This method checks a directory to make sure it 
	* exists. If it doesn't, it is created.
	* @return java.io.File the ensured directory object.
	* @param directory java.io.File the directory to ensure.
	*/
	public final static File ensureDirectory( File directory ) {
		if ( !directory.exists() || ( directory.exists() && !directory.isDirectory() ) ) {
			directory.mkdirs();
		}
		return directory;
	}
	/**
	* This method checks a directory to make sure it 
	* exists. If it doesn't, it is created.
	* @return java.lang.String the absolute path to the ensured directory.
	* @param directory java.lang.String the directory to ensure.
	*/
	public final static String ensureDirectory( String directory ) {
		java.io.File dir = new java.io.File( directory );
		if ( !dir.exists() || ( dir.exists() && !dir.isDirectory() ) ) {
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}
	/**
	* Given any color, return white or black; whichever contrasts  
	* better.  Constants taken from question 9 of the color faq at
	*  http://www.inforamp.net/~poynton/notes/colour_and_gamma/ColorFAQ.html       
	*/
	public static final Color getContrastingTextColor( Color c ) {
		final double brightness = c.getRed() * 0.2125 + c.getGreen() * 0.7145 + c.getBlue() * 0.0721;
		return brightness < 128.0 ? Color.white : Color.black;
	}
}

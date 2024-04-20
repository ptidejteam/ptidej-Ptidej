/*
 * @(#)ColorMap.java 5.1
 *
 */

package CH.ifa.draw.util;

import java.awt.Color;


class ColorEntry {
	public String 	fName;
	public Color 	fColor;

	ColorEntry(String name, Color color) {
	    fColor = color;
	    fName = name;
	}
}


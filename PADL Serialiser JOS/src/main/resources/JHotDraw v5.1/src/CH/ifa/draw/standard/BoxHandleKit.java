/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.util.Vector;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;

/**
 * A set of utility methods to create Handles for the common
 * locations on a figure's display box.
 * @see Handle
 */

 // TBD: use anonymous inner classes (had some problems with JDK 1.1)

public class BoxHandleKit {

	/**
	 * Fills the given Vector with handles at each corner of a
	 * figure.
	 */
	static public void addCornerHandles(Figure f, Vector handles) {
		handles.addElement(southEast(f));
		handles.addElement(southWest(f));
		handles.addElement(northEast(f));
		handles.addElement(northWest(f));
	}

	/**
	 * Fills the given Vector with handles at each corner
	 * and the north, south, east, and west of the figure.
	 */
	static public void addHandles(Figure f, Vector handles) {
		addCornerHandles(f, handles);
		handles.addElement(south(f));
		handles.addElement(north(f));
		handles.addElement(east(f));
		handles.addElement(west(f));
	}

	static public Handle south(Figure owner) {
		return new SouthHandle(owner);
	}

	static public Handle southEast(Figure owner) {
		return new SouthEastHandle(owner);
	}

	static public Handle southWest(Figure owner) {
		return new SouthWestHandle(owner);
	}

	static public Handle north(Figure owner) {
		return new NorthHandle(owner);
	}

	static public Handle northEast(Figure owner) {
		return new NorthEastHandle(owner);
	}

	static public Handle northWest(Figure owner) {
		return new NorthWestHandle(owner);
	}

	static public Handle east(Figure owner) {
		return new EastHandle(owner);
	}
	static public Handle west(Figure owner) {
		return new WestHandle(owner);
	}
}


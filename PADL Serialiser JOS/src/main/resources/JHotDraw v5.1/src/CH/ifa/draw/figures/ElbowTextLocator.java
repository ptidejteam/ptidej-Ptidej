/*
 * @(#)ElbowConnection.java 5.1
 *
 */

package CH.ifa.draw.figures;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.AbstractLocator;

class ElbowTextLocator extends AbstractLocator {
	public Point locate(Figure owner) {
		Point p = owner.center();
		Rectangle r = owner.displayBox();
		return new Point(p.x, p.y-10); // hack
	}
}


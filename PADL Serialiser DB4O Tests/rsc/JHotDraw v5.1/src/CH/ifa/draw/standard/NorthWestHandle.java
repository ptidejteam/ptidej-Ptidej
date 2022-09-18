/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

class NorthWestHandle extends LocatorHandle {
	NorthWestHandle(Figure owner) {
		super(owner, RelativeLocator.northWest());
	}

	public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
		Rectangle r = owner().displayBox();
		owner().displayBox(
			new Point(Math.min(r.x + r.width, x), Math.min(r.y + r.height, y)),
			new Point(r.x + r.width, r.y + r.height)
		);
	}
}


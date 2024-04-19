/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

class NorthEastHandle extends LocatorHandle {
	NorthEastHandle(Figure owner) {
		super(owner, RelativeLocator.northEast());
	}

	public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
		Rectangle r = owner().displayBox();
		owner().displayBox(
			new Point(r.x, Math.min(r.y + r.height, y)),
			new Point(Math.max(r.x, x), r.y + r.height)
		);
	}
}


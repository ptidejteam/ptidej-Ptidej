/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

class NorthHandle extends LocatorHandle {
	NorthHandle(Figure owner) {
		super(owner, RelativeLocator.north());
	}

	public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
		Rectangle r = owner().displayBox();
		owner().displayBox(
			new Point(r.x, Math.min(r.y + r.height, y)),
			new Point(r.x + r.width, r.y + r.height)
		);
	}
}


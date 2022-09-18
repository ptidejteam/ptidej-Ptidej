/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.awt.Point;
import java.awt.Rectangle;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;

class SouthHandle extends LocatorHandle {
	SouthHandle(Figure owner) {
		super(owner, RelativeLocator.south());
	}

	public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
		Rectangle r = owner().displayBox();
		owner().displayBox(
			new Point(r.x, r.y),
			new Point(r.x + r.width, Math.max(r.y, y))
		);
	}
}


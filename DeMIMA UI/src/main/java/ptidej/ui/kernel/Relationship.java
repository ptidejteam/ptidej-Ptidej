/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.kernel;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import ptidej.ui.Constants;
import ptidej.ui.RGB;
import ptidej.ui.primitive.IPrimitive;
import ptidej.ui.primitive.IPrimitiveFactory;
import ptidej.ui.primitive.ISymbol;

public abstract class Relationship extends Element {
	private static Point centralPoint(
		final Point position,
		final Dimension dimension) {

		return new Point(position.x + dimension.width / 2, position.y
				+ dimension.height / 2);
	}
	private static Point[] findIntersectionPoints(
		final Point origin,
		final Dimension dimension,
		final Point position,
		final Point destination) {

		final List intersections = new ArrayList(2);

		// This method returns the two extremity points
		// of the line that is intersected by the
		// aggregation line. They will be used to compute
		// the intersection point and place the appropriate
		// symbol (aggregation or compostion).
		final Point topLeftCorner = new Point(origin.x, origin.y);
		final Point bottomLeftCorner =
			new Point((int) origin.x, (int) (origin.y + dimension.height));
		final Point topRightCorner =
			new Point((int) (origin.x + dimension.width), (int) origin.y);
		final Point bottomRightCorner =
			new Point(
				(int) (origin.x + dimension.width),
				(int) (origin.y + dimension.height));
		Point intersection = null;

		intersection =
			Relationship.intersectionPoint(
				topLeftCorner,
				topRightCorner,
				position,
				destination);
		if (Relationship.pointOnLine(
			intersection,
			topLeftCorner,
			topRightCorner)) {

			intersections.add(intersection);
		}
		intersection =
			Relationship.intersectionPoint(
				topRightCorner,
				bottomRightCorner,
				position,
				destination);
		if (Relationship.pointOnLine(
			intersection,
			topRightCorner,
			bottomRightCorner)) {

			intersections.add(intersection);
		}
		intersection =
			Relationship.intersectionPoint(
				bottomLeftCorner,
				bottomRightCorner,
				position,
				destination);
		if (Relationship.pointOnLine(
			intersection,
			bottomLeftCorner,
			bottomRightCorner)) {

			intersections.add(intersection);
		}
		intersection =
			Relationship.intersectionPoint(
				topLeftCorner,
				bottomLeftCorner,
				position,
				destination);
		if (Relationship.pointOnLine(
			intersection,
			topLeftCorner,
			bottomLeftCorner)) {

			intersections.add(intersection);
		}

		Point[] intersectionPoints = new Point[intersections.size()];
		intersections.toArray(intersectionPoints);
		return intersectionPoints;
	}
	private static boolean inBetween(
		final Point centralPoint,
		final Point originCentralPoint,
		final Point targetCentralPoint) {

		boolean result = true;

		if (originCentralPoint.x <= targetCentralPoint.x) {
			if (originCentralPoint.x <= centralPoint.x
					&& centralPoint.x <= targetCentralPoint.x) {

				result &= true;
			}
			else {
				result &= false;
			}
		}
		if (targetCentralPoint.x <= originCentralPoint.x) {
			if (targetCentralPoint.x <= centralPoint.x
					&& centralPoint.x <= originCentralPoint.x) {

				result &= true;
			}
			else {
				result &= false;
			}
		}

		if (originCentralPoint.y <= targetCentralPoint.y) {
			if (originCentralPoint.y <= centralPoint.y
					&& centralPoint.y <= targetCentralPoint.y) {

				result &= true;
			}
			else {
				result &= false;
			}
		}
		if (targetCentralPoint.y <= originCentralPoint.y) {
			if (targetCentralPoint.y <= centralPoint.y
					&& centralPoint.y <= originCentralPoint.y) {

				result &= true;
			}
			else {
				result &= false;
			}
		}

		return result;
	}

	private static Point intersectionPoint(
		final int x1l1,
		final int y1l1,
		final int x2l1,
		final int y2l1,
		final int x1l2,
		final int y1l2,
		final int x2l2,
		final int y2l2) {

		// I use Constants.FACTOR to replace any float calculus 
		// by int calculus.
		// (Is it faster? But it's Palm compliant!)
		int xi = 0;
		int yi = 0;

		// First, I compute the slope of the lines.
		if (x2l1 - x1l1 != 0 && x2l2 - x1l2 != 0) {
			if (y2l1 - y1l1 != 0 && y2l2 - y1l2 != 0) {

				int a1 = (y2l1 - y1l1) * Constants.FACTOR / (x2l1 - x1l1);
				int a2 = (y2l2 - y1l2) * Constants.FACTOR / (x2l2 - x1l2);

				// Now I compute the a in y = a + bx
				int b1 = y1l1 * Constants.FACTOR - a1 * x1l1;
				int b2 = y1l2 * Constants.FACTOR - a2 * x1l2;

				// And finally the intersection.
				xi = (b2 - b1) / (a1 - a2);
				yi = (a1 * xi + b1) / Constants.FACTOR;
			}
			else if (y2l1 - y1l1 == 0) {
				yi = y1l1;
				if (y2l2 - y1l2 == 0) {
					xi = 0;
				}
				else {
					// TODO: Apparently, there is a problem in
					// using the Constants.FACTOR constant in
					// the following computations. I use float
					// instead. This whole method should be
					// revised thoroughly.
					float a2 = (y1l2 - y2l2) / (float) (x1l2 - x2l2);
					float b2 = y2l2 - a2 * x2l2;
					xi = Math.round((yi - b2) / a2);
				}
			}
			else if (y2l2 - y1l2 == 0) {
				yi = y1l2;
				if (y2l1 - y1l1 == 0) {
					xi = 0;
				}
				else {
					int a1 = (y2l1 - y1l1) * Constants.FACTOR / (x2l1 - x1l1);
					int b1 = y1l1 * Constants.FACTOR - a1 * x1l1;
					xi = (Constants.FACTOR * yi - b1) / a1;
				}
			}
		}
		else {
			if (x1l1 - x2l1 == 0) {
				xi = x1l1;
				if (x1l2 - x2l2 == 0) {
					yi = 0;
				}
				else {
					int a2 = (y2l2 - y1l2) * Constants.FACTOR / (x2l2 - x1l2);
					int b2 = y1l2 * Constants.FACTOR - a2 * x1l2;
					yi = (a2 * xi + b2) / Constants.FACTOR;
				}
			}
			else if (x1l2 - x2l2 == 0) {
				xi = x1l2;
				if (x1l1 - x2l1 == 0) {
					yi = 0;
				}
				else {
					int a1 = (y2l1 - y1l1) * Constants.FACTOR / (x2l1 - x1l1);
					int b1 = y1l1 * Constants.FACTOR - a1 * x1l1;
					yi = (a1 * xi + b1) / Constants.FACTOR;
				}
			}
		}

		return new Point(xi, yi);
	}
	private static Point intersectionPoint(
		final Point p1,
		final Point p2,
		final Point p3,
		final Point p4) {

		return Relationship.intersectionPoint(
			(int) p1.x,
			(int) p1.y,
			(int) p2.x,
			(int) p2.y,
			(int) p3.x,
			(int) p3.y,
			(int) p4.x,
			(int) p4.y);
	}
	private static boolean pointOnLine(
		final Point p1,
		final Point a1,
		final Point a2) {

		if (a1.x - Constants.ROUNDING <= p1.x
				&& p1.x <= a2.x + Constants.ROUNDING
				&& a1.y - Constants.ROUNDING <= p1.y
				&& p1.y <= a2.y + Constants.ROUNDING) {

			return true;
		}
		return false;
	}

	private final int cardinality;
	private Point destination;
	private IPrimitive line;
	private Entity originEntity;
	private ISymbol originSymbol;
	private Entity targetEntity;
	private ISymbol targetSymbol;

	public Relationship(
		final IPrimitiveFactory primitiveFactory,
		final int cardinality,
		final Entity origin,
		final Entity target) {

		super(primitiveFactory);

		this.cardinality = cardinality;
		this.originEntity = origin;
		this.targetEntity = target;
	}
	public final void build() {
		this.setPosition(new Point(
			(int) (this.getOriginEntity().getPosition().x + this
				.getOriginEntity()
				.getDimension().width / 2),
			(int) (this.getOriginEntity().getPosition().y + this
				.getOriginEntity()
				.getDimension().height / 2)));
		this.destination =
			new Point(
				(int) (this.getTargetEntity().getPosition().x + this
					.getTargetEntity()
					.getDimension().width / 2),
				(int) (this.getTargetEntity().getPosition().y + this
					.getTargetEntity()
					.getDimension().height / 2));
		this.setDimension(new Dimension(this.destination.x
				- this.getPosition().x, this.destination.y
				- this.getPosition().y));

		this.line = this.getLine();
		this.targetSymbol = this.getTargetSymbol();
		this.originSymbol = this.getOriginSymbol();
	}
	protected Point findIntersectionPointWithOrigin() {
		final Point[] intersectionPoints =
			Relationship.findIntersectionPoints(
				this.getOriginEntity().getPosition(),
				this.getOriginEntity().getDimension(),
				this.getPosition(),
				this.getDestination());

		// I only keep the closet intersection point.
		final Point originCentralPoint =
			Relationship.centralPoint(
				this.getOriginEntity().getPosition(),
				this.getOriginEntity().getDimension());
		final Point targetCentralPoint =
			Relationship.centralPoint(
				this.getTargetEntity().getPosition(),
				this.getTargetEntity().getDimension());

		// Yann 2014/03/28: Equals!
		// If the origin and target points are equal, then I return one of them.
		if (originCentralPoint.equals(targetCentralPoint)) {
			return originCentralPoint;
		}

		for (int i = 0; i < intersectionPoints.length; i++) {
			if (Relationship.inBetween(
				intersectionPoints[i],
				originCentralPoint,
				targetCentralPoint)) {

				return intersectionPoints[i];
			}
		}

		// I return null if there is no closest point!?.
		// Yann 2014/04/24: Mmmh!
		// I now return the central point of the origin
		// entity because it does not really matter,
		// except for not having a null value.
		return originCentralPoint;
	}
	protected final Point findIntersectionPointWithTarget() {
		// Yann 2014/04/09: Position vs. Origin
		// The position could be the position of the last
		// intermediary point, to draw nice symbols aligned
		// with this last point while the origin is that of
		// the origin entity.
		return this.findIntersectionPointWithTarget(
			this.getPosition(),
			this.getDimension());
	}
	protected final Point findIntersectionPointWithTarget(
		final Point origin,
		final Dimension dimension) {

		final Point[] intersectionPoints =
			Relationship.findIntersectionPoints(
				this.getTargetEntity().getPosition(),
				this.getTargetEntity().getDimension(),
				origin,
				this.getDestination());

		// I only keep the closet intersection point.
		final Point originCentralPoint = origin;
		// Relationship.centralPoint(origin, dimension);
		final Point targetCentralPoint =
			Relationship.centralPoint(
				this.getTargetEntity().getPosition(),
				this.getTargetEntity().getDimension());

		// Yann 2014/03/28: Equals!
		// If the origin and target points are equal, then I return one of them.
		if (originCentralPoint.equals(targetCentralPoint)) {
			return originCentralPoint;
		}

		for (int i = 0; i < intersectionPoints.length; i++) {
			if (Relationship.inBetween(
				intersectionPoints[i],
				originCentralPoint,
				targetCentralPoint)) {

				return intersectionPoints[i];
			}
		}

		// I return null if there is no closest point!?.
		// Yann 2014/04/24: Mmmh!
		// I now return the central point of the target
		// entity because it does not really matter,
		// except for not having a null value.
		return targetCentralPoint;
	}
	protected final int getCardinality() {
		return this.cardinality;
	}
	protected final RGB getColor() {
		final RGB color;
		if (this.getTargetEntity() instanceof Ghost) {
			color = Constants.GHOST_ENTITY_DISPLAY_COLOR;
		}
		else {
			color = Constants.FOREGROUND_COLOR;
		}
		return color;
	}
	public Point getDestination() {
		return this.destination;
	}
	protected abstract IPrimitive getLine();
	public final String getName() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getSymbol());
		buffer.append((this.getCardinality() > 1) ? "* " : " ");
		// buffer.append(this.getTypeAndFieldNames());
		buffer.append(this.getTargetEntity().getName());
		return buffer.toString();
	}
	public Entity getOriginEntity() {
		return this.originEntity;
	}
	protected abstract ISymbol getOriginSymbol();
	protected abstract String getSymbol();
	public Entity getTargetEntity() {
		return this.targetEntity;
	}
	protected abstract ISymbol getTargetSymbol();
	public abstract int getVisibilityDisplay();
	abstract int getVisibilityName();
	public final void paint(final int xOffset, final int yOffset) {
		if ((this.getVisibleElements() & this.getVisibilityDisplay()) == this
			.getVisibilityDisplay()) {

			this.line.paint(xOffset, yOffset);

			if (this.targetSymbol != null) {
				this.targetSymbol.paint(xOffset, yOffset);
			}
			if (this.originSymbol != null) {
				this.originSymbol.paint(xOffset, yOffset);
			}
		}
	}
	public String toString() {
		return this.getName();
	}
}

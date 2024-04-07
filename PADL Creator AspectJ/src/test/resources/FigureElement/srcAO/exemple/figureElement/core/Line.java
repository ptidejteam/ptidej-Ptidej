/**
 * 
 */
package exemple.figureElement.core;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class Line implements FigureElement {

	private Point p1;
	private Point p2;
	
	public Line (Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * @return Returns the p1.
	 */
	public Point getP1() {
		return p1;
	}

	/**
	 * @param p1 The p1 to set.
	 */
	public void setP1(Point p1) {
		this.p1 = p1;
	}

	/**
	 * @return Returns the p2.
	 */
	public Point getP2() {
		return p2;
	}

	/**
	 * @param p2 The p2 to set.
	 */
	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	
	public String getType() {
		return "Line";
	}

	public String toString () {
		return "(" + this.p1 + "," + this.p2 + ")";
	}
}

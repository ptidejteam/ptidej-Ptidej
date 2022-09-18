/**
 * 
 */
package exemple.figureElement.core;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class Point implements FigureElement {

	private int x;
	private int y;
	
	public Point (int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return Returns the x.
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x The x to set.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return Returns the y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y The y to set.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
	public String getType() {
		return "Point";
	}

	public String toString () {
		return "(" + this.x + "," + this.y + ")";
	}
		
}

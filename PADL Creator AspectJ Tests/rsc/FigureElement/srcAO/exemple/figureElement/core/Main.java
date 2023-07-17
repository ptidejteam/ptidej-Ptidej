package exemple.figureElement.core;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Point p1 = new Point (0,0);
		Point p2 = new Point (2,2);
		
		Line l = new Line (p1, p2);
		
		System.out.println("P1: " + p1 + "\nP2:" + p2 + "\nL: " + l);
		
		l.setP1(new Point (-1,-1));
		
		p1.setX(10);
		p1.setY(10);
		
		System.out.println("P1: " + p1 + "\nP2:" + p2 + "\nL: " + l);
	}

}

/**
 * 
 */
package exemple.figureElement.display;

import java.awt.Color;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class DisplayManager {

	private static DisplayManager INSTANCE = null;
	
	private DisplayManager () {
		//constructor code
	}
	
	public static DisplayManager getDisplayManager () {
		if(DisplayManager.INSTANCE == null)
			DisplayManager.INSTANCE = new DisplayManager ();
		return DisplayManager.INSTANCE;
	}
	
	public void update() {
		//update code
		System.out.println("Display updated");
	}
	
	public void init(DisplayableFigure df){
		//init code
		Color tmp = this.getSystemColor(df.getType());
		df.setColor(tmp);
		System.out.println("DisplayManager init: " + df.getType() + " with color= " + df.getColor());
	}
	
	public Color getSystemColor(String type){
		if(type.equals("Point"))
			return Color.BLUE;
		if(type.equals("Line"))
			return Color.RED;
		
		return Color.BLACK;
	}
}

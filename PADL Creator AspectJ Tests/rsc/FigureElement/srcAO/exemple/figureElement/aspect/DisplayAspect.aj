package exemple.figureElement.aspect;

import java.awt.Color;

import exemple.figureElement.core.Point;
import exemple.figureElement.core.Line;
import exemple.figureElement.core.FigureElement;
import exemple.figureElement.display.DisplayManager;
import exemple.figureElement.display.DisplayableFigure;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public aspect DisplayAspect {

	private java.awt.Color FigureElement.color = Color.BLACK;
	
	declare parents : FigureElement implements DisplayableFigure;
	
	public void FigureElement.setColor(Color color){
		this.color = color;
	}
	
	public Color FigureElement.getColor(){
		return this.color;
	}
	
	pointcut pointStateChanges(Point p):
		target(p) && 
        call(public void Point.set*(..));
	
	pointcut lineStateChanges(Line l):
		target(l) &&
        call(public void Line.set*(..));
	
	pointcut newFE(FigureElement fe) :
		this(fe) &&
		execution((Point || Line).new(..));
	
	after(Point p): pointStateChanges(p) {
		this.updateDisplay(p);
    }
	
	after(Line l): lineStateChanges(l) {
		this.updateDisplay(l);
    }
	
	after(FigureElement fe): newFE(fe) {
		this.init(fe);
	}
	
	private void init(FigureElement fe){
		DisplayManager.getDisplayManager().init(fe);
	}
	
	private void updateDisplay(FigureElement fe){
		System.out.println("DisplayAspect: a " + fe.getType() + " has changed.\n\t Notifying DisplayManger.");
        DisplayManager.getDisplayManager().update();
	}
}

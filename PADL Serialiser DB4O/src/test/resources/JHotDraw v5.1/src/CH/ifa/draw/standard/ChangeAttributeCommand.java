/*
 * @(#)ChangeAttributeCommand.java 5.1
 *
 */

package CH.ifa.draw.standard;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureEnumeration;
import CH.ifa.draw.util.Command;

/**
 * Command to change a named figure attribute.
 */
public  class ChangeAttributeCommand
		extends Command {

	private DrawingView fView;
	private String      fAttribute;
	private Object      fValue;

   /**
	* Constructs a change attribute command.
	* @param name the command name
	* @param attributeName the name of the attribute to be changed
	* @param value the new attribute value
	* @param view the target view
	*/
	public ChangeAttributeCommand(String name, String attributeName,
						   Object value, DrawingView view) {
		super(name);
		fAttribute = attributeName;
		fValue = value;
		fView = view;
	}

	public void execute() {
		FigureEnumeration k = fView.selectionElements();
		while (k.hasMoreElements()) {
			Figure f = k.nextFigure();
			f.setAttribute(fAttribute, fValue);
		}
		fView.checkDamage();
	}

	public boolean isExecutable() {
		return fView.selectionCount() > 0;
	}

}



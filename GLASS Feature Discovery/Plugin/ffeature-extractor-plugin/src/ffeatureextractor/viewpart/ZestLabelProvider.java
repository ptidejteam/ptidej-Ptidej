package ffeatureextractor.viewpart;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;

public class ZestLabelProvider extends LabelProvider implements IColorProvider {
	/*@Override
	public Image getImage(Object element) {
		return Display.getCurrent().getSystemImage(SWT.ICON_WARNING);
	}*/
	@Override
	public String getText(Object element) {
		if (element instanceof Node) {
			Node myNode = (Node) element;
			//dispaly the name of node
			//return myNode.getName();
			//display the hash code of the node
			return String.valueOf(myNode.getID());
		}
		
		if (element instanceof EntityConnectionData) {
			EntityConnectionData test = (EntityConnectionData) element;
			return "";
		}
		throw new RuntimeException("Wrong type: " + element.getClass().toString());
	}

	@Override
	public Color getBackground(Object element) {
		Color color = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE); 
		if (element instanceof Node) {
			Node myNode = (Node) element;
			if (myNode.getTypes() != null) {
				if (myNode.getTypes().size()>0) {
					if (myNode.getTypes().get(0).toString().contains("ADHOC")) {
						color =  Display.getCurrent().getSystemColor(SWT.COLOR_RED);
					}
					if (myNode.getTypes().get(0).toString().contains("INTERFACE")) {
						color =  Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
					}
					if (myNode.getTypes().get(0).toString().contains("CLASS_SUBCLASS")) {
						color =  new Color( Display.getCurrent(),255,165,0);
					}
					if (myNode.getTypes().get(0).toString().contains("AGGREGATIONS")) {
						color =  Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
					}
			}else 
				color =  Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
			}
		}
		 
		return color;
	}

	@Override
	public Color getForeground(Object element) {
		// TODO Auto-generated method stub
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}
	
	
	public Object getData(Object element) {
		return (((Node)element));
	}
	
	
}

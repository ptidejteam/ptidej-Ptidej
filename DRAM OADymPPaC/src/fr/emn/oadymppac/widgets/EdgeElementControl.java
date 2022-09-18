/*
 * Created on 9 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac.widgets;

import java.awt.Component;
import javax.swing.JButton;

/**
 * @author Mohammad Ghoniem
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EdgeElementControl extends NodeLinkElementControl {
	private static final long serialVersionUID = 6165970741436552026L;

	/**
	 * @param type
	 * @param nodeLink
	 */
	public EdgeElementControl(final String type, final JNodeLinkManager nodeLink) {
		super(type, nodeLink);
		final JButton arrowBtn = new JButton("hide/show arrow");
		arrowBtn.setActionCommand("hide/show arrow");
		arrowBtn.addActionListener(nodeLink.getGrappaListener());
		arrowBtn.setName("arrowBtn");
		arrowBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(arrowBtn);
	}

}

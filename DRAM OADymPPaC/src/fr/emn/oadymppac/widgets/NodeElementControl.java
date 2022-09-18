package fr.emn.oadymppac.widgets;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * A specific control panel for grappa nodes.
 */
public class NodeElementControl extends NodeLinkElementControl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5476800835724323994L;

	/**
	 * Constructor for NodeElementControl.
	 * @param type
	 * @param nodeLink
	 */
	public NodeElementControl(final String type, final JNodeLinkManager nodeLink) {
		super(type, nodeLink);
		final JSlider nwidthSlider =
			new JSlider(SwingConstants.HORIZONTAL, 0, 10, 1);
		nwidthSlider.setMajorTickSpacing(5);
		nwidthSlider.setMinorTickSpacing(1);
		nwidthSlider.setPaintTicks(true);
		nwidthSlider.setPaintLabels(true);
		//nwidthSlider.setSnapToTicks(true);
		nwidthSlider.setBorder(BorderFactory.createTitledBorder("node Width"));
		nwidthSlider.addChangeListener(nodeLink.getGrappaListener());
		nwidthSlider.setName("nodewidthSlider");
		nwidthSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(nwidthSlider);
	}

}

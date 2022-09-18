package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * This class puts together a control panel that handles graphic 
 * attributes of Grappa elements, especially nodes and edges.
 */
public class NodeLinkElementControl extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239849618695297796L;
	JButton hideShowBtn;
	JComboBox colorCombo; // a JColorChooser may be more appropriate
	String btnLabel, btnCommand, colorCommand;
	JSlider widthSlider;
	String type;

	public NodeLinkElementControl(
		final String type,
		final JNodeLinkManager nodeLink) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder(type + " control"));
		this.type = type;

		if (type.equals("nodes")) {
			this.btnLabel = "hide/show nodes";
			this.btnCommand = "hideNodes";
			this.colorCommand = "nodeColor";
		}
		else if (type.equals("edges")) {
			this.btnLabel = "hide/show edges";
			this.btnCommand = "hideEdges";
			this.colorCommand = "edgeColor";
		}
		else {
			System.err
				.println("Element type: " + type + " is not handled yet.");
		}

		this.hideShowBtn = new JButton(this.btnLabel);
		this.hideShowBtn.setActionCommand(this.btnCommand);
		this.hideShowBtn.addActionListener(nodeLink.getGrappaListener());
		this.hideShowBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(this.hideShowBtn);

		final String[] colors =
			{ "black", "white", "grey", "red", "green", "yellow", "magenta",
					"blue" };
		this.colorCombo = new JComboBox(colors);
		this.colorCombo.setActionCommand(this.colorCommand);
		this.colorCombo.addItemListener(nodeLink.getGrappaListener());

		this.colorCombo.setPreferredSize(new Dimension(100, 50));
		this.colorCombo.setMaximumSize(new Dimension(100, 50));
		this.colorCombo.setMinimumSize(new Dimension(100, 50));

		this.colorCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.colorCombo.setBorder(BorderFactory
			.createTitledBorder("foreground color"));

		this.add(this.colorCombo);

		this.widthSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 10, 1); // replace the last value by the Grappa default value
		this.widthSlider.setMajorTickSpacing(5);
		this.widthSlider.setMinorTickSpacing(1);
		this.widthSlider.setPaintTicks(true);
		this.widthSlider.setPaintLabels(true);
		this.widthSlider.setSnapToTicks(true);
		this.widthSlider.setBorder(BorderFactory
			.createTitledBorder("Line Width"));
		this.widthSlider.addChangeListener(nodeLink.getGrappaListener());
		this.widthSlider.setName(type + "linewidthSlider");
		this.widthSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(this.widthSlider);

	}

}

/*
 * Created on 29 juil. 2003
 *
 */
package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import att.grappa.GrappaConstants;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This panel makes it possible to control which graph 
 * elements are selectable i.e. the ones that react to user
 * interaction.
 */
public class NodeLinkSelectionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8048078776007782376L;
	JNodeLinkManager visualization;

	/**
	 * Default constructor.
	 */
	public NodeLinkSelectionPanel(final JNodeLinkManager vis) {
		super();
		this.visualization = vis;
		final JCheckBox nodeBox =
			new JCheckBox(
				"nodes",
				this.visualization.isTypeSelectable(GrappaConstants.NODE));
		final JCheckBox edgeBox =
			new JCheckBox(
				"edges",
				this.visualization.isTypeSelectable(GrappaConstants.EDGE));

		nodeBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent ev) {
				NodeLinkSelectionPanel.this.visualization.makeTypeSelectable(
					nodeBox.isSelected(),
					GrappaConstants.NODE);
			}
		});
		edgeBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent ev) {
				NodeLinkSelectionPanel.this.visualization.makeTypeSelectable(
					edgeBox.isSelected(),
					GrappaConstants.EDGE);
			}
		});

		this.setBorder(BorderFactory.createTitledBorder("Selectable Items"));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(nodeBox);
		this.add(edgeBox);
	}

}

package fr.emn.oadymppac.widgets;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * A control panel for dot based visualizations. 
 * It comprises node controls and edge controls. 
 */
public class NodeLinkControlPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8416785066357370960L;
	JNodeLinkManager nodeLink;

	public NodeLinkControlPanel(final JNodeLinkManager nodeLink) {
		this.nodeLink = nodeLink;
		final JPanel mainTab = new JPanel();
		this.initMainTab(mainTab);
		this.addTab("Main", new JScrollPane(mainTab));
		this.addTab("Util", new SwingUtilityPanel(nodeLink));
	}

	void initMainTab(final JPanel panel) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final NodeElementControl nodeCtrl =
			new NodeElementControl("nodes", this.nodeLink);
		panel.add(nodeCtrl);

		final EdgeElementControl edgeCtrl =
			new EdgeElementControl("edges", this.nodeLink);
		panel.add(edgeCtrl);

		final JToggleButton normalBtn = new JToggleButton("NORMAL");
		normalBtn.setSelected(this.nodeLink.mode == JNodeLinkManager.NORMAL);
		normalBtn.setActionCommand("switchMode");
		normalBtn.addActionListener(this.nodeLink.getGrappaListener());

		final JToggleButton pathBtn = new JToggleButton("PATH BUILDING");
		pathBtn
			.setSelected(this.nodeLink.mode == JNodeLinkManager.PATH_BUILDING);
		pathBtn.setActionCommand("switchMode");
		pathBtn.addActionListener(this.nodeLink.getGrappaListener());

		final ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(normalBtn);
		modeGroup.add(pathBtn);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setBorder(BorderFactory.createTitledBorder("mode control"));
		buttonPanel.add(normalBtn);
		buttonPanel.add(pathBtn);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(buttonPanel);

		final NodeLinkSelectionPanel nlsPanel =
			new NodeLinkSelectionPanel(this.nodeLink);
		panel.add(nlsPanel);
	}
}

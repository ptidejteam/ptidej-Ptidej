package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 *
 * This panel provides the required controls for an <code>IcicleTreeBrowser</code>.
 */
public class TreeControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6181955537889287268L;
	IcicleTreeBrowser browser;

	/**
	 * Constructor for TreeControlPanel.
	 */
	public TreeControlPanel(final IcicleTreeBrowser browser) {
		super();
		this.browser = browser;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final ButtonGroup btnGroup = new ButtonGroup();

		final JRadioButton childCountBtn = new JRadioButton("child count");
		final JRadioButton leafCountBtn = new JRadioButton("leaf count");
		final JRadioButton customWeightBtn = new JRadioButton("custom weight");
		final JRadioButton expandedNodeBtn = new JRadioButton("expanded node");
		customWeightBtn.setEnabled(false);
		customWeightBtn.setToolTipText("not tested yet!");

		childCountBtn
			.setSelected(StateNode.DEFAULT_WEIGHT == StateNode.WEIGHT_CHILD_COUNT);
		leafCountBtn
			.setSelected(StateNode.DEFAULT_WEIGHT == StateNode.WEIGHT_LEAF_COUNT);
		customWeightBtn
			.setSelected(StateNode.DEFAULT_WEIGHT == StateNode.WEIGHT_STORED_VALUE);
		expandedNodeBtn
			.setSelected(StateNode.DEFAULT_WEIGHT == StateNode.WEIGHT_EXPANDED_NODE_COUNT);

		btnGroup.add(childCountBtn);
		btnGroup.add(leafCountBtn);
		btnGroup.add(customWeightBtn);
		btnGroup.add(expandedNodeBtn);

		childCountBtn.setActionCommand("WEIGHT_CHILD_COUNT");
		leafCountBtn.setActionCommand("WEIGHT_LEAF_COUNT");
		customWeightBtn.setActionCommand("WEIGHT_STORED_VALUE");
		expandedNodeBtn.setActionCommand("WEIGHT_EXPANDED_NODE_COUNT");

		childCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setWeightGetter(StateNode.WEIGHT_CHILD_COUNT);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		leafCountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setWeightGetter(StateNode.WEIGHT_LEAF_COUNT);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		customWeightBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setWeightGetter(StateNode.WEIGHT_STORED_VALUE);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		expandedNodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setWeightGetter(StateNode.WEIGHT_EXPANDED_NODE_COUNT);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});

		final JSlider rowSlider =
			new JSlider(
				0,
				IcicleTreeBrowser.DEFAULT_ROW_HEIGHT * 4,
				IcicleTreeBrowser.DEFAULT_ROW_HEIGHT);
		rowSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent event) {
				if (event.getSource() instanceof JSlider) {
					browser.setRowHeight(((JSlider) event.getSource())
						.getValue());
				}
			}
		});

		final JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

		btnPanel.add(childCountBtn);
		btnPanel.add(leafCountBtn);
		btnPanel.add(customWeightBtn);
		btnPanel.add(expandedNodeBtn);

		btnPanel.setBorder(BorderFactory.createTitledBorder("Node weight"));
		btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		rowSlider.setBorder(BorderFactory.createTitledBorder("Row Height"));
		rowSlider.setAlignmentX(Component.LEFT_ALIGNMENT);

		final JCheckBox leafAlignBox = new JCheckBox("align leaves", false);
		leafAlignBox.setActionCommand("align leaves");
		leafAlignBox.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent event) {
				if (event.getSource() instanceof JCheckBox) {
					if (((JCheckBox) event.getSource())
						.getActionCommand()
						.equals("align leaves")) {
						browser.setAlignLeaves(((JCheckBox) event.getSource())
							.isSelected());
					}
				}
			}
		});

		final JCheckBox hideFrames = new JCheckBox("hide frames", false);
		hideFrames.setActionCommand("hide frames");
		hideFrames.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent event) {
				if (event.getSource() instanceof JCheckBox) {
					if (((JCheckBox) event.getSource())
						.getActionCommand()
						.equals("hide frames")) {
						browser.setDrawFrames(!((JCheckBox) event.getSource())
							.isSelected());
					}
				}
			}
		});

		final ButtonGroup colorGroup = new ButtonGroup();

		final JRadioButton defaultColors =
			new JRadioButton("leaf vs. non leaf");
		final JRadioButton greyScale = new JRadioButton("grey scale");
		final JRadioButton rainbowDown = new JRadioButton("rainbow TTB");
		final JRadioButton rainbowAccross = new JRadioButton("rainbow LTR");
		final JRadioButton rainbowIntensity =
			new JRadioButton("rainbow LTR + intensity gradient");
		final JRadioButton rainbowAccrossLeaves =
			new JRadioButton("rainbow from leaves");

		colorGroup.add(defaultColors);
		colorGroup.add(greyScale);
		colorGroup.add(rainbowDown);
		colorGroup.add(rainbowAccross);
		colorGroup.add(rainbowIntensity);
		colorGroup.add(rainbowAccrossLeaves);

		defaultColors
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.LEAF_NON_LEAF);
		greyScale
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.INTENSITY_TOP_DOWN);
		rainbowDown
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.RAINBOW_TOP_DOWN);
		rainbowAccross
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.RAINBOW_ACCROSS);
		rainbowIntensity
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.RAINBOW_AND_INTENSITY);
		rainbowAccrossLeaves
			.setSelected(StateNode.DEFAULT_COLOR == StateNode.RAINBOW_ACCROSS_LEAVES);

		defaultColors.setActionCommand("LEAF_NON_LEAF");
		greyScale.setActionCommand("INTENSITY_TOP_DOWN");
		rainbowDown.setActionCommand("RAINBOW_TOP_DOWN");
		rainbowAccross.setActionCommand("RAINBOW_ACCROSS");
		rainbowIntensity.setActionCommand("RAINBOW_AND_INTENSITY");
		rainbowAccrossLeaves.setActionCommand("RAINBOW_ACCROSS_LEAVES");

		defaultColors.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.DEFAULT_COLOR);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		greyScale.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.INTENSITY_TOP_DOWN);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		rainbowDown.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.RAINBOW_TOP_DOWN);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		rainbowAccross.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.RAINBOW_ACCROSS);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		rainbowIntensity.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.RAINBOW_AND_INTENSITY);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});
		rainbowAccrossLeaves.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				((StateNode) browser.getTreeModel().getRoot())
					.setColorGetter(StateNode.RAINBOW_ACCROSS_LEAVES);
				((DefaultTreeModel) browser.getTreeModel())
					.nodeChanged((StateNode) browser.getTreeModel().getRoot());
			}
		});

		final JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));

		colorPanel.add(defaultColors);
		colorPanel.add(greyScale);
		colorPanel.add(rainbowDown);
		colorPanel.add(rainbowAccross);
		colorPanel.add(rainbowIntensity);
		colorPanel.add(rainbowAccrossLeaves);

		colorPanel.setBorder(BorderFactory.createTitledBorder("Node colors"));
		colorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.add(btnPanel);
		this.add(rowSlider);
		this.add(leafAlignBox);
		this.add(hideFrames);
		this.add(colorPanel);
	}

}

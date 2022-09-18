package fr.emn.oadymppac.widgets;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Control Panel for Fisheye lenses
 * 
 * @version $Revision: 1.3 $
 * @author Jean-Daniel Fekete
 */
public class FisheyeControlPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1269186462207717660L;
	Fisheye lens;
	DefaultBoundedRangeModel radiusModel;

	public FisheyeControlPanel(final Fisheye lens) {
		this.lens = lens;
		final JPanel panel = this;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		final JSlider scale = new JSlider(SwingConstants.HORIZONTAL, 0, 10, 5);
		scale.setMajorTickSpacing(5);
		scale.setMinorTickSpacing(1);
		scale.setPaintTicks(true);
		scale.setPaintLabels(true);
		scale.setBorder(BorderFactory.createTitledBorder("Scale"));
		scale.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				FisheyeControlPanel.this.getLens().setScale(scale.getValue());
			}
		});
		scale.setValue((int) lens.getScale());
		panel.add(scale);

		final JSlider plateau =
			new JSlider(SwingConstants.HORIZONTAL, 0, 10, 5);
		plateau.setMajorTickSpacing(5);
		plateau.setMinorTickSpacing(1);
		plateau.setPaintTicks(true);
		plateau.setPaintLabels(true);
		plateau.setBorder(BorderFactory.createTitledBorder("Plateau"));
		plateau.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				FisheyeControlPanel.this.getLens().setPlateauScale(
					plateau.getValue());
			}
		});
		plateau.setValue((int) lens.getScale());
		panel.add(plateau);

		this.radiusModel =
			new DefaultBoundedRangeModel(
				(int) lens.getFocusRadius(),
				(int) lens.getContextRadius(),
				0,
				200);
		final RangeSlider radius = new RangeSlider(this.radiusModel);
		radius.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		radius.setEnabled(true);
		radius.setBorder(BorderFactory.createTitledBorder("Radius"));
		this.radiusModel.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				FisheyeControlPanel.this.getLens().setRadii(
					FisheyeControlPanel.this.radiusModel.getValue(),
					FisheyeControlPanel.this.radiusModel.getValue()
							+ FisheyeControlPanel.this.radiusModel.getExtent());
			}
		});

		panel.add(radius);

		final JPanel distancePanel = new JPanel();
		distancePanel.setLayout(new BoxLayout(distancePanel, BoxLayout.Y_AXIS));

		final ActionListener distanceListener = new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (e.getActionCommand().equals("L1")) {
					FisheyeControlPanel.this.getLens().setDistanceMetric(
						Fisheye.DISTANCE_L1);
				}
				else if (e.getActionCommand().equals("L2")) {
					FisheyeControlPanel.this.getLens().setDistanceMetric(
						Fisheye.DISTANCE_L2);
				}
				else if (e.getActionCommand().equals("LINF")) {
					FisheyeControlPanel.this.getLens().setDistanceMetric(
						Fisheye.DISTANCE_LINF);
				}
			}
		};

		distancePanel.setBorder(BorderFactory
			.createTitledBorder("Lens Distance"));

		ButtonGroup group = new ButtonGroup();

		JRadioButton radioButton =
			new JRadioButton(
				"L1",
				lens.getDistanceMetric() == Fisheye.DISTANCE_L1);
		radioButton.setActionCommand("L1");
		radioButton.addActionListener(distanceListener);
		group.add(radioButton);
		distancePanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"L2",
				lens.getDistanceMetric() == Fisheye.DISTANCE_L2);
		radioButton.setActionCommand("L2");
		radioButton.addActionListener(distanceListener);
		group.add(radioButton);
		distancePanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"LINF",
				lens.getDistanceMetric() == Fisheye.DISTANCE_LINF);
		radioButton.setActionCommand("LINF");
		radioButton.addActionListener(distanceListener);
		group.add(radioButton);

		distancePanel.add(radioButton);
		panel.add(distancePanel);

		final JPanel lensPanel = new JPanel();
		lensPanel.setLayout(new BoxLayout(lensPanel, BoxLayout.Y_AXIS));

		final ActionListener lensListener = new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (e.getActionCommand().equals("GAUSSIAN")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_GAUSSIAN);
				}
				else if (e.getActionCommand().equals("COSINE")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_COSINE);
				}
				else if (e.getActionCommand().equals("HEMISPHERE")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_HEMISPHERE);
				}
				else if (e.getActionCommand().equals("LINEAR")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_LINEAR);
				}
				else if (e.getActionCommand().equals("INVERSE_COSINE")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_INVERSE_COSINE);
				}
				else if (e.getActionCommand().equals("MANHATTAN")) {
					FisheyeControlPanel.this.getLens().setLensType(
						Fisheye.LENS_MANHATTAN);
				}
			}
		};

		lensPanel.setBorder(BorderFactory.createTitledBorder("Lens Types"));

		group = new ButtonGroup();

		radioButton =
			new JRadioButton(
				"GAUSSIAN",
				lens.getLensType() == Fisheye.LENS_GAUSSIAN);
		radioButton.setActionCommand("GAUSSIAN");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"COSINE",
				lens.getLensType() == Fisheye.LENS_COSINE);
		radioButton.setActionCommand("COSINE");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"HEMISPHERE",
				lens.getLensType() == Fisheye.LENS_HEMISPHERE);
		radioButton.setActionCommand("HEMISPHERE");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"LINEAR",
				lens.getLensType() == Fisheye.LENS_LINEAR);
		radioButton.setActionCommand("LINEAR");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"INVERSE_COSINE",
				lens.getLensType() == Fisheye.LENS_INVERSE_COSINE);
		radioButton.setActionCommand("INVERSE_COSINE");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		radioButton =
			new JRadioButton(
				"MANHATTAN",
				lens.getLensType() == Fisheye.LENS_MANHATTAN);
		radioButton.setActionCommand("MANHATTAN");
		radioButton.addActionListener(lensListener);
		group.add(radioButton);
		lensPanel.add(radioButton);

		panel.add(lensPanel);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		this.radiusModel.setValue((int) this.lens.getFocusRadius());
		this.radiusModel.setExtent((int) this.lens.getContextRadius()
				- this.radiusModel.getValue());
	}

	/**
	 * Returns the lens.
	 * 
	 * @return Fisheye
	 */
	public Fisheye getLens() {
		return this.lens;
	}
	/**
	 * Sets the lens.
	 * 
	 * @param lens The lens to set
	 */
	public void setLens(final Fisheye lens) {
		this.lens = lens;
	}

}
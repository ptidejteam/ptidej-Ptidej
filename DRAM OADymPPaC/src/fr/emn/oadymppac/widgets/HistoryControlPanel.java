/*
 * Created on 15 juil. 2003
 *
 */
package fr.emn.oadymppac.widgets;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import fr.emn.oadymppac.utils.IntervalList;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 * 
 * This class provides a control panel for history with regard to graphs with
 * history.
 */
public class HistoryControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7843186126332269603L;

	RangeSlider slider;

	JButton nextIntervalBtn, playNextBtn, stopBtn;

	JButton previousIntervalBtn, playPreviousBtn;

	JSlider speedSlider;

	JLabel boundDisplay;

	int orientation = SwingConstants.HORIZONTAL;

	IntervalList intervalList;

	public HistoryControlPanel() {
		this(SwingConstants.HORIZONTAL, new IntervalList());
	}

	/**
	 * Constructor.
	 */
	public HistoryControlPanel(final int orientation, final IntervalList list) {
		super();

		this.setOrientation(orientation);
		this.nextIntervalBtn = new JButton(">");
		this.nextIntervalBtn.setActionCommand("next");
		this.nextIntervalBtn.setToolTipText("next");
		this.playNextBtn = new JButton(">>");
		this.playNextBtn.setActionCommand("play next");
		this.playNextBtn.setToolTipText("fast forward");
		this.playPreviousBtn = new JButton("<<");
		this.playPreviousBtn.setActionCommand("play previous");
		this.playPreviousBtn.setToolTipText("fast rewind");
		this.previousIntervalBtn = new JButton("<");
		this.previousIntervalBtn.setActionCommand("previous");
		this.previousIntervalBtn.setToolTipText("previous");
		this.stopBtn = new JButton("||");
		this.stopBtn.setActionCommand("stop");
		this.stopBtn.setToolTipText("stop");
		this.slider =
			new RangeSlider(new DefaultBoundedRangeModel(0, 1, 0, Math.max(
				list.lastValue(),
				1)));
		this.slider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		this.slider.setEnabled(true);
		this.slider.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setBorder(BorderFactory.createTitledBorder("Time range"));
		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(
			buttonPane,
			orientation == SwingConstants.HORIZONTAL ? BoxLayout.X_AXIS
					: BoxLayout.Y_AXIS));
		buttonPane.add(this.playPreviousBtn);
		buttonPane.add(this.previousIntervalBtn);
		buttonPane.add(this.stopBtn);
		buttonPane.add(this.nextIntervalBtn);
		buttonPane.add(this.playNextBtn);
		buttonPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		this.speedSlider = new JSlider(1, 10, IntervalList.DEFAULT_FREQUENCY);
		this.speedSlider.setToolTipText("Number of frames per second");
		this.speedSlider.setSize(90, 50);
		this.speedSlider.setMaximumSize(new Dimension(90, 50));
		this.speedSlider.setMinorTickSpacing(1);
		this.speedSlider.setPaintTicks(true);
		this.speedSlider.setPaintLabels(true);
		this.speedSlider.setSnapToTicks(true);
		this.speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent e) {
				if (e.getSource() == HistoryControlPanel.this.speedSlider) {
					HistoryControlPanel.this.intervalList
						.setFrequency(HistoryControlPanel.this.speedSlider
							.getValue());
				}
			}
		});

		buttonPane.add(this.speedSlider);

		this.boundDisplay =
			new JLabel(this.slider.getModel().getValue()
					+ " - "
					+ (this.slider.getModel().getValue() + this.slider
						.getModel()
						.getExtent()));

		this.slider.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent ev) {
				if (ev.getSource() == HistoryControlPanel.this.slider
					.getModel()) {
					HistoryControlPanel.this.boundDisplay
						.setText(HistoryControlPanel.this.slider
							.getModel()
							.getValue()
								+ " - "
								+ (HistoryControlPanel.this.slider
									.getModel()
									.getValue() + HistoryControlPanel.this.slider
									.getModel()
									.getExtent()));
				}
				int counter =
					HistoryControlPanel.this.slider.getModel().getValue();
				//System.out.println("zz " + counter );
				//System.out.println(
				// HistoryControlPanel.this.getParent().getParent().getParent().getParent().getParent());

				HistoryControlPanel.this
					.getParent()
					.getParent()
					.getParent()
					.getParent()
					.getParent()
					.getParent()
					.getParent()
					.getParent()
					.getParent();
				final JSplitPane ff =
					(JSplitPane) HistoryControlPanel.this
						.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				//System.out.println(ff.getComponentCount());
				//ff.getComponent(0);
				//GLUtilityPanel utilTab = new GLUtilityPanel((Component)glmatrix);

				try {
					final File file = new File("C:\\AM" + counter + ".jpg");
					final Robot robot = new Robot();
					// capture de l'image selon un rectangle de 300*400 et de point de depart 0 0.
					final BufferedImage image =
						robot.createScreenCapture(new Rectangle(
							ff.getX(),
							ff.getY() + 50,
							ff.getWidth() - 480,
							ff.getHeight() - 10));
					//BufferedImage image=robot.createScreenCapture(new Rectangle(0,0,700,700));
					//File file=new File(dd+"/image" + zz + ".jpg");
					ImageIO.write(image, "jpeg", file);
				}
				catch (final AWTException ex) {
					System.out.print(ex);
				}
				catch (final IOException ex) {
					System.out.print(ex);
				}
				counter = counter + 1;

				//			File dd = new File("C:\\Documents and
				// Settings\\rachedsa\\Bureau\\zz56.tga");
				//// utilTab.saveComponent(dd);
				////
				//				TGATextureGrabber tg =
				//					new TGATextureGrabber(((GLDrawable)
				// ff.getComponent(0)).getGL());
				//				
				//				int w = ff.getComponent(0).getWidth();
				//				int h = ff.getComponent(0).getHeight();
				//				tg.grabPixels(GLFunc.GL_FRONT, 0, 0, w, h);
				//				tg.write2File(dd.toString());
				//				

				//.getParent().getParent().getParent().getParent());
			}
		});

		this.boundDisplay.setSize(new Dimension(90, 50));
		this.boundDisplay.setMaximumSize(new Dimension(90, 50));
		this.boundDisplay.setOpaque(true);
		this.boundDisplay.setBackground(Color.PINK);
		buttonPane.add(this.boundDisplay);

		this.setLayout(new BoxLayout(
			this,
			orientation == SwingConstants.HORIZONTAL ? BoxLayout.Y_AXIS
					: BoxLayout.X_AXIS));
		this.add(this.slider);
		this.add(buttonPane);
		this.setIntervalList(list);
	}

	public HistoryControlPanel(final IntervalList list) {
		this(SwingConstants.HORIZONTAL, list);
	}

	/**
	 * @return
	 */
	public IntervalList getIntervalList() {
		return this.intervalList;
	}

	/**
	 * @return
	 */
	public int getOrientation() {
		return this.orientation;
	}

	public void register(final ChangeListener listener) {
		this.slider.getModel().addChangeListener(listener);
	}

	private void removeListeners(final JButton btn) {
		final ActionListener[] listeners = btn.getActionListeners();
		for (int i = 0; i < listeners.length; i++) {
			btn.removeActionListener(listeners[i]);
		}
	}
	/**
	 * @param list
	 */
	public void setIntervalList(final IntervalList list) {
		this.removeListeners(this.nextIntervalBtn);
		this.removeListeners(this.previousIntervalBtn);
		this.intervalList = list;
		this.nextIntervalBtn.addActionListener(this.intervalList);
		this.previousIntervalBtn.addActionListener(this.intervalList);
		this.playNextBtn.addActionListener(this.intervalList);
		this.playPreviousBtn.addActionListener(this.intervalList);
		this.stopBtn.addActionListener(this.intervalList);
		list.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent event) {
				HistoryControlPanel.this.slider.getModel().setValue(
					HistoryControlPanel.this.intervalList.getMinimum());
				HistoryControlPanel.this.slider.getModel().setExtent(
					HistoryControlPanel.this.intervalList.getExtent());

				final int counter =
					HistoryControlPanel.this.slider.getModel().getValue();
				//System.out.println("zz " + counter);
				//				JFrame ff1 = (JFrame)
				// HistoryControlPanel.this.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
				final JSplitPane ff2 =
					(JSplitPane) HistoryControlPanel.this
						.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				final JPanel ff = (JPanel) ff2.getLeftComponent();
				final JPanel dd = (JPanel) ff.getComponent(1);
				dd.setBorder(BorderFactory
					.createTitledBorder("                Image " + counter));
				dd.getComponent(1);

				//dd.getGraphics().drawString("image"+counter,100,15);
				//dd.getGraphics().drawString("image"+counter,10,10);
				//dd.getGraphics().drawString(" r ",10,10);

				try {
					final JSplitPane ff3 =
						(JSplitPane) HistoryControlPanel.this
							.getParent()
							.getParent()
							.getParent()
							.getParent()
							.getParent();

					new File(
						"C:\\Documents and Settings\\rachedsa\\Bureau\\diffJunit\\"
								+ "/image" + counter + ".jpg");
					final Robot robot = new Robot();
					//					BufferedImage image = robot
					//					.createScreenCapture(new Rectangle(ff3.getX(), ff3
					//							.getY() + 50, ff3.getWidth() - 480, ff3
					//							.getHeight() - 10));
					robot.createScreenCapture(new Rectangle(
						ff3.getX() + 35,
						ff3.getY() + 85,
						ff3.getWidth() - 520,
						ff3.getHeight() - 33));
					//ImageIO.write(image, "jpeg", file);
				}
				catch (final AWTException ex) {
					System.out.print(ex);
				}
				//catch (IOException ex) {
				//					System.out.print(ex);
				//				}

				//counter = counter + 1;

				//				File file = new File(
				//						"C:\\Documents and Settings\\rachedsa\\Bureau\\imageOAdymppacerreuravectga\\"
				//								+ "/image" + counter + ".tga");
				//				//dd.getGraphics().drawString("image1",15,15);
				//				if (((GLDrawable) dd1).getGLContext().gljMakeCurrent() == false)
				//					System.out.println("cannot get gl context");
				//
				//				TGATextureGrabber tg = new TGATextureGrabber(((GLDrawable) dd1)
				//						.getGL());
				//				int w = dd1.getWidth();
				//				int h = dd1.getHeight();
				//				tg.grabPixels(GLFunc.GL_BACK, 0, 0, w, h);
				//tg.write2File(file.toString());
			}
		});
	}

	/**
	 * @param i
	 */
	public void setOrientation(final int i) {
		if (i == SwingConstants.HORIZONTAL || i == SwingConstants.VERTICAL) {
			this.orientation = i;
		}
	}

}
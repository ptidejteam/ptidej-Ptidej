/*
 * $Id: JHistoryViewer.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fr.emn.oadymppac.HistoryManager;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.TimeManager;
import fr.emn.oadymppac.event.ActivateEvent;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.DeactivateEvent;
import fr.emn.oadymppac.event.NewConstraintEvent;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.RejectEvent;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.SelectConstraintEvent;
import fr.emn.oadymppac.event.SelectUpdateEvent;
import fr.emn.oadymppac.event.SolutionEvent;
import fr.emn.oadymppac.event.SuspendEvent;
import fr.emn.oadymppac.event.TellEvent;
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.event.TrueEvent;
import fr.emn.oadymppac.event.WakeUpEvent;

/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2002</p>
 * <p>Société : </p>
 * @author non attribué
 * @version $Revision: 1.2 $
 */

public class JHistoryViewer extends JPanel implements ComponentListener,
		ActionListener, ItemListener, ListSelectionListener, TimeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8421008905368948544L;
	HashMap colorMap = new HashMap(30);
	HashMap filterMap = new HashMap(30);

	public static final int HEIGHT = 100;
	public static final int WIDTH = 800;

	HistoryManager historyManager;
	BasicSolverEvent currentEvent;

	String x_field;
	String y_field;
	String color_field = "class";
	String w_field;
	String h_field;

	public final static short VISU_BAR_CHART = 1;
	public final static short VISU_SCATTER_PLOT = 2;
	//    short visu_type = VISU_BAR_CHART;
	short visu_type = JHistoryViewer.VISU_SCATTER_PLOT;

	int start = 0;
	int end = 0;

	BasicSolverEvent pickedEvent;

	String field;

	String selection;

	public JHistoryViewer(final HistoryManager hm) {
		this.start = 0;
		this.end = hm.getLength();

		this.addComponentListener(this);
		//currentEvent = new BasicSolverEvent();
		this.setHistoryManager(hm);
		this.setPreferredSize(new Dimension(
			JHistoryViewer.WIDTH,
			JHistoryViewer.HEIGHT));
		//registerColors();
		this.registerFilters();
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent e) {
				JHistoryViewer.this.setToolTipText(JHistoryViewer.this
					.getToolTipText(e));
				System.out.println("Picked : "
						+ JHistoryViewer.this.pickedEvent.getClass().getName()
						+ " at depth : "
						+ JHistoryViewer.this.pickedEvent.getDepth());
			}
		});
	}
	public JHistoryViewer(final Solver solver) {
		this(HistoryManager.getHistoryManager(solver));
	}
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source instanceof JComboBox) {
			this.field =
				((JEventFieldSelector) ((JComboBox) source).getParent())
					.getLabel();
			this.selection = ((JComboBox) source).getSelectedItem().toString();
			if (this.selection.equals("")) {
				this.selection = null;
			}
			if (this.field.equals("x_field")) {
				this.setXField(this.selection);
			}
			else if (this.field.equals("y_field")) {
				this.setYField(this.selection);
			}
			else if (this.field.equals("w_field")) {
				this.setWidthField(this.selection);
			}
			else if (this.field.equals("h_field")) {
				this.setHeightField(this.selection);
			}
		}
		else if (source instanceof JRadioButton) {
			if (((JRadioButton) source).getActionCommand().equals(
				"VISU_BAR_CHART")) {
				this.setVisu_type(JHistoryViewer.VISU_BAR_CHART);
			}
			else if (((JRadioButton) source).getActionCommand().equals(
				"VISU_SCATTER_PLOT")) {
				this.setVisu_type(JHistoryViewer.VISU_SCATTER_PLOT);
			}
		}
		this.getParent().repaint();
	}
	/**
	 * Checks if the event <code>ev</code> must be filtered or not.
	 */
	public boolean checkFilter(final BasicSolverEvent ev) {
		return this.getEventFilter(ev.getClass().getName());
	}

	public void componentHidden(final ComponentEvent e) {
	}
	public void componentMoved(final ComponentEvent e) {
	}
	public void componentResized(final ComponentEvent e) {
		System.out.println("Display resized to " + this.getSize());
		//setPreferredSize(getSize());
		this.repaint();
	}

	public void componentShown(final ComponentEvent e) {
		this.repaint();
	}
	void findPickedEvent(final MouseEvent e) {
		final int picked_x = e.getX();
		final int picked_y = e.getY();
		if (this.historyManager.getLength() == 0) {
			return;
		}

		synchronized (this.historyManager) {
			float x_min; // smallest x value
			float x_max; // biggest x value

			float y_min; // smallest y value
			float y_max; // biggest y value

			float w_min; // smallest w value
			float w_max; // biggest w value

			float h_min; // smallest h value
			float h_max; // biggest h value

			/**
			     * if no event field is appointed to the x_axis
			     * the default bounds are 0 and the number of events in the history
			     */
			if (this.x_field == null) {
				x_min = 0;
				x_max = this.end;
			}
			else {
				x_min = Float.MAX_VALUE;
				x_max = -Float.MAX_VALUE;
			}
			/**
			     * if no event field is appointed to the y_axis
			     * the default bounds are 0 and 1.
			     */
			if (this.y_field == null) {
				y_min = 0;
				y_max = 1;
			}
			else {
				y_min = Float.MAX_VALUE;
				y_max = -Float.MAX_VALUE;
			}
			/**
			     * if no event field is appointed to the x_axis
			     * the default bounds are 0 and the number of events in the history
			     */
			if (this.w_field == null) {
				w_min = 0;
				w_max = 1;//historyManager.getLength();
			}
			else {
				w_min = Float.MAX_VALUE;
				w_max = -Float.MAX_VALUE;
			}
			/**
			     * if no event field is appointed to the y_axis
			     * the default bounds are 0 and 1.
			     */
			if (this.h_field == null) {
				h_min = 0;
				h_max = 1;
			}
			else {
				h_min = Float.MAX_VALUE;
				h_max = -Float.MAX_VALUE;
			}
			/**
			     * if the x_axis or y_axis are associated to an event field,
			     * the bounds are determined and then the scaling factor
			     */
			if (this.x_field != null || this.y_field != null) {
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext();) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					if (this.x_field != null) {
						final float x = ev.getFloatValue(this.x_field);

						if (x < x_min) {
							x_min = x;
						}
						if (x > x_max) {
							x_max = x;
						}
					}
					if (this.y_field != null) {
						final float y = ev.getFloatValue(this.y_field);

						if (y < y_min) {
							y_min = y;
						}
						if (y > y_max) {
							y_max = y;
						}
					}
					if (this.w_field != null) {
						final float w = ev.getFloatValue(this.w_field);

						if (w < w_min) {
							w_min = w;
						}
						if (w > w_max) {
							w_max = w;
						}
					}
					if (this.h_field != null) {
						final float h = ev.getFloatValue(this.h_field);

						if (h < h_min) {
							h_min = h;
						}
						if (h > h_max) {
							h_max = h;
						}
					}
				}
			}

			/**
			 * A rectangle can be as wide as and as high as 10% of the display
			 * area respective dimensions.
			 * -> This amount should be configurable, perhaps with a slidebar.
			 */
			final float w_scale = this.getSize().width / (10 * (w_max - w_min));
			final float h_scale =
				this.getSize().height / (10 * (h_max - h_min));

			/**
			 * In order to prevent shapes from being displayed accross
			 * the borders of the display area, we take margins equivalent to
			 * the dimensions of the maximum shape width and height.
			 */
			final float x_scale =
				(this.getSize().width - w_max * w_scale) / (x_max - x_min);
			final float y_scale =
				(this.getSize().height - h_max * h_scale) / (y_max - y_min);

			float x = 0;
			float y = 1;
			boolean foundPickedEvent = false;
			/**
			     * Several representations can be displayed : for instance,
			     * a bar chart or a scatter plot. Visual attributes like
			     * x and y coordinates as well as width and height are computed
			     * from the retrieved attributes of the events.
			     */
			if (this.visu_type == JHistoryViewer.VISU_BAR_CHART) {
				final Rectangle2D.Float rect = new Rectangle2D.Float();
				rect.height = (y - y_min) * y_scale;
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext() && !foundPickedEvent;) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					if (this.x_field != null) {
						x = ev.getFloatValue(this.x_field);

					}
					else {
						x = x + 1;
					}
					rect.x = (x - x_min) * x_scale;
					rect.width = x_scale;

					rect.y = 0;
					if (this.y_field != null) {
						y = ev.getFloatValue(this.y_field);
						rect.height = (y - y_min) * y_scale;
					}
					if (!this.checkFilter(ev)) {
						if (rect
							.contains(new Point2D.Float(picked_x, picked_y))) {
							foundPickedEvent = true;
							this.pickedEvent = ev;
						}
					}
				}
			}
			if (this.visu_type == JHistoryViewer.VISU_SCATTER_PLOT) {
				final Rectangle2D.Float rect = new Rectangle2D.Float();
				rect.height = 2;//(h - h_min) * h_scale;
				rect.width = 2;
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext();) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					if (this.x_field != null) {
						x = ev.getFloatValue(this.x_field);
					}
					else {
						x = x + 1;
					}
					rect.x = (x - x_min) * x_scale;
					//rect.width = (w - w_min) * w_scale;
					if (this.w_field != null) {
						rect.width = ev.getFloatValue(this.w_field) * w_scale;
					}

					if (this.h_field != null) {
						rect.height = ev.getFloatValue(this.h_field) * h_scale;
					}

					rect.y = 0;
					if (this.y_field != null) {
						y = ev.getFloatValue(this.y_field);
						//			rect.height= (y - y_min) * y_scale;
						rect.y = (y - y_min) * y_scale;
					}
					if (!this.checkFilter(ev)) {
						if (rect
							.contains(new Point2D.Float(picked_x, picked_y))) {
							foundPickedEvent = true;
							this.pickedEvent = ev;
						}
					}
				}
			}
		}
	}

	public Color getEventColor(final BasicSolverEvent ev) {
		return this.getEventColor(ev.getClass().getName());
	}

	public Color getEventColor(final String className) {
		final Color c = (Color) this.colorMap.get(className);
		if (c == null) {
			System.err.println("Cannot get event color for " + className);
		}
		return c;
	}
	/**
	 * This method retrieves the filter associated to the events of
	 * type <code>className</code>.
	 */
	public boolean getEventFilter(final String className) {
		final Boolean b = (Boolean) this.filterMap.get(className);
		if (b == null) {
			System.err.println("Cannot get event filter for " + className);
		}
		return b.booleanValue();
	}
	public HistoryManager getHistoryManager() {
		return this.historyManager;
	}
	public Point getToolTipLocation(final MouseEvent e) {
		final int x = e.getX();
		final int y = e.getY();
		final int length = this.getToolTipText().length();
		return new Point(x - length / 2, y);
	}

	public String getToolTipText(final MouseEvent e) {
		this.findPickedEvent(e);
		return this.pickedEvent.getClass().getName();
	};
	public short getVisu_type() {
		return this.visu_type;
	};
	/**
	 * When a filter is added or removed, the filter collection is updated
	 * and the display is refreshed.
	 */
	public void itemStateChanged(final ItemEvent e) {
		final String className =
			"fr.emn.oadymppac.event." + ((JCheckBox) e.getSource()).getName();
		final boolean state = this.getEventFilter(className);
		this.putEventFilter(className, Boolean.valueOf(!state));
		this.getParent().repaint();
	};
	public synchronized void paint(final Graphics g) {
		if (this.historyManager.getLength() == 0) {
			return;
		}

		final Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		synchronized (this.historyManager) {
			float x_min; // smallest x value
			float x_max; // biggest x value

			float y_min; // smallest y value
			float y_max; // biggest y value

			float w_min; // smallest w value
			float w_max; // biggest w value

			float h_min; // smallest h value
			float h_max; // biggest h value

			/**
			     * if no event field is appointed to the x_axis
			     * the default bounds are 0 and the number of events in the history
			     */
			if (this.x_field == null) {
				x_min = this.start;
				x_max = this.end;
			}
			else {
				x_min = Float.MAX_VALUE;
				x_max = -Float.MAX_VALUE;
			}
			/**
			     * if no event field is appointed to the y_axis
			     * the default bounds are 0 and 1.
			     */
			if (this.y_field == null) {
				y_min = 0;
				y_max = 1;
			}
			else {
				y_min = Float.MAX_VALUE;
				y_max = -Float.MAX_VALUE;
			}

			/**
			     * if no event field is appointed to the x_axis
			     * the default bounds are 0 and the number of events in the history
			     */
			if (this.w_field == null) {
				w_min = 0;
				w_max = 1;
			}
			else {
				w_min = Float.MAX_VALUE;
				w_max = -Float.MAX_VALUE;
			}
			/**
			     * if no event field is appointed to the y_axis
			     * the default bounds are 0 and 1.
			     */
			if (this.h_field == null) {
				h_min = 0;
				h_max = 1;
			}
			else {
				h_min = Float.MAX_VALUE;
				h_max = -Float.MAX_VALUE;
			}

			/**
			     * if the x_axis or y_axis are associated to an event field,
			     * the bounds are determined and then the scaling factor
			     */
			if (this.x_field != null || this.y_field != null) {
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext();) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					if (this.x_field != null) {
						final float x = ev.getFloatValue(this.x_field);

						if (x < x_min) {
							x_min = x;
						}
						if (x > x_max) {
							x_max = x;
						}
					}
					if (this.y_field != null) {
						final float y = ev.getFloatValue(this.y_field);

						if (y < y_min) {
							y_min = y;
						}
						if (y > y_max) {
							y_max = y;
						}
					}
					if (this.w_field != null) {
						final float w = ev.getFloatValue(this.w_field);

						if (w < w_min) {
							w_min = w;
						}
						if (w > w_max) {
							w_max = w;
						}
					}
					if (this.h_field != null) {
						final float h = ev.getFloatValue(this.h_field);

						if (h < h_min) {
							h_min = h;
						}
						if (h > h_max) {
							h_max = h;
						}
					}
				}
			}
			/**
			 * A rectangle can be as wide as and as high as 10% of the display
			 * area respective dimensions.
			 * -> This amount should be configurable, perhaps with a slidebar.
			 */
			final float w_scale = this.getSize().width / (10 * (w_max - w_min));
			final float h_scale =
				this.getSize().height / (10 * (h_max - h_min));

			/**
			 * In order to prevent shapes from being displayed accross
			 * the borders of the display area, we take margins equivalent to
			 * the dimensions of the maximum shape width and height.
			 */
			final float x_scale =
				(this.getSize().width - w_max * w_scale) / (x_max - x_min);
			final float y_scale =
				(this.getSize().height - h_max * h_scale) / (y_max - y_min);

			float x = 0;
			float y = 1;

			/**
			     * Several representations can be displayed : for instance,
			     * a bar chart or a scatter plot. Visual attributes like
			     * x and y coordinates as well as width and height are computed
			     * from the retrieved attributes of the events.
			     */
			if (this.visu_type == JHistoryViewer.VISU_BAR_CHART) {
				final Rectangle2D.Float rect = new Rectangle2D.Float();
				rect.height = (y - y_min) * y_scale;
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext();) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					g2.setColor(EventColorManager.getEventColorManager(
						this.historyManager.getSolver()).getEventColor(ev));
					if (this.x_field != null) {
						x = ev.getFloatValue(this.x_field);
					}
					else {
						x = x + 1;
					}
					rect.x = (x - x_min) * x_scale;
					rect.width = x_scale;

					rect.y = 0;
					if (this.y_field != null) {
						y = ev.getFloatValue(this.y_field);
						rect.height = (y - y_min) * y_scale;
					}
					if (!this.checkFilter(ev)) {
						g2.fill(rect);
					}
				}
			}
			if (this.visu_type == JHistoryViewer.VISU_SCATTER_PLOT) {
				final Rectangle2D.Float rect = new Rectangle2D.Float();
				rect.height = 2;//(h - h_min) * h_scale;
				rect.width = 2;
				for (final Iterator i =
					this.historyManager.iterator(this.start, this.end); i
					.hasNext();) {
					final BasicSolverEvent ev = (BasicSolverEvent) i.next();
					g2.setColor(EventColorManager.getEventColorManager(
						this.historyManager.getSolver()).getEventColor(ev));
					if (this.x_field != null) {
						x = ev.getFloatValue(this.x_field);
					}
					else {
						x = x + 1;
					}
					rect.x = (x - x_min) * x_scale;
					//rect.width = (w - w_min) * w_scale;
					if (this.w_field != null) {
						rect.width = ev.getFloatValue(this.w_field) * w_scale;
					}

					if (this.h_field != null) {
						rect.height = ev.getFloatValue(this.h_field) * h_scale;
					}

					rect.y = 0;
					if (this.y_field != null) {
						y = ev.getFloatValue(this.y_field);
						//			rect.height= (y - y_min) * y_scale;
						rect.y = (y - y_min) * y_scale;
					}
					if (!this.checkFilter(ev)) {
						g2.fill(rect);
					}
				}
			}
		}
	};

	public void putEventColor(final BasicSolverEvent ev, final Color c) {
		this.putEventColor(ev.getClass().getName(), c);
	}
	public void putEventColor(final String className, final Color c) {
		this.colorMap.put(className, c);
	}

	/**
	 * This method adds a filter for the events of the same type
	 * as <code>ev</code>.
	 */
	public void putEventFilter(final BasicSolverEvent ev, final Boolean b) {
		this.filterMap.put(ev.getClass().getName(), b);
	}

	/*    protected void registerColors () {
	putEventColor(NewVariableEvent.class.getName(), Color.cyan.brighter());
	putEventColor(NewConstraintEvent.class.getName(), Color.cyan);
	putEventColor(ActivateEvent.class.getName(), Color.pink);
	putEventColor(DeactivateEvent.class.getName(), Color.pink.darker());
	putEventColor(ReduceEvent.class.getName(), Color.blue);
	putEventColor(RejectEvent.class.getName(), Color.red);
	putEventColor(RestoreEvent.class.getName(), Color.orange);
	putEventColor(SelectConstraintEvent.class.getName(), Color.lightGray);
	putEventColor(SelectUpdateEvent.class.getName(), Color.darkGray);
	putEventColor(SuspendEvent.class.getName(), Color.magenta);
	putEventColor(SolutionEvent.class.getName(), Color.green);
	putEventColor(TellEvent.class.getName(), Color.black);
	putEventColor(TrueEvent.class.getName(), Color.white);
	putEventColor(WakeUpEvent.class.getName(), Color.yellow);
	}*/
	/**
	 * This method adds a filter for the events of type <code>className</code>.
	 */
	public void putEventFilter(final String className, final Boolean b) {
		this.filterMap.put(className, b);
	}

	/*    public void adjustmentValueChanged(AdjustmentEvent e){
	        start = ((TMRangeSlider)e.getAdjustable()).getLeftValue();
	        end =  ((TMRangeSlider)e.getAdjustable()).getRightValue();
	        getParent().repaint();
	    }*/

	/**
	 * This method initializes the filter collection by putting an entry for
	 * every event type and setting the key to false i.e. the events are not
	 * filtered by default.
	 */
	protected void registerFilters() {
		this.putEventFilter(NewVariableEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(NewConstraintEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(ActivateEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(DeactivateEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(ReduceEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(RejectEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(RestoreEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(
			SelectConstraintEvent.class.getName(),
			Boolean.FALSE);
		this.putEventFilter(SelectUpdateEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(SuspendEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(SolutionEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(TellEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(TrueEvent.class.getName(), Boolean.FALSE);
		this.putEventFilter(WakeUpEvent.class.getName(), Boolean.FALSE);
	}

	public void setHeightField(final String str) {
		this.h_field = str;
	}

	public void setHistoryManager(final HistoryManager hm) {
		this.historyManager = hm;
	}

	public void setVisu_type(final short s) {
		this.visu_type = s;
	}

	public void setWidthField(final String str) {
		this.w_field = str;
	}

	public void setXField(final String str) {
		this.x_field = str;
	}
	public void setYField(final String str) {
		this.y_field = str;
	}

	// the time manager can be retrieved from the TimeEvent itself.
	public void timeChanged(final TimeEvent e) {
		this.start =
			TimeManager
				.getTimeManager(this.getHistoryManager().getSolver())
				.getLeftValue();
		this.end =
			TimeManager
				.getTimeManager(this.getHistoryManager().getSolver())
				.getRightValue();
		this.getParent().repaint();
	}

	public void valueChanged(final ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		final JList theList = (JList) e.getSource();
		final ListModel model = theList.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			final String className =
				"fr.emn.oadymppac.event." + (String) model.getElementAt(i);
			if (theList.isSelectedIndex(i)) {
				this.putEventFilter(className, Boolean.TRUE);
			}
			else {
				this.putEventFilter(className, Boolean.FALSE);
			}
		}
		this.getParent().repaint();
	}
}

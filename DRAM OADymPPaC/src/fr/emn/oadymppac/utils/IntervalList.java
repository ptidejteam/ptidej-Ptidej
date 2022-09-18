package fr.emn.oadymppac.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Created on 15 juil. 2003
 */

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 *
 * This class handles a sequence of integers sorted in 
 * ascending order. These integers correspond to the bounds
 * of successive time intervals. This is a utility class
 * for handling graphs with history. 
 */
public class IntervalList implements ActionListener {
	//int i =0;
	ArrayList values;
	ListIterator listIterator;
	int currentIndex;
	Vector listeners = new Vector();
	ChangeEvent event = new ChangeEvent(this);
	boolean verbose = false;
	final int ACTION_NEXT = 0;
	final int ACTION_PREVIOUS = 1;
	final int ACTION_PLAY_NEXT = 2;
	final int ACTION_PLAY_PREVIOUS = 3;
	final int ACTION_STOP = 4;
	int animMode = this.ACTION_NEXT;
	public static final int DEFAULT_FREQUENCY = 3;
	int frequency = IntervalList.DEFAULT_FREQUENCY;
	Action updateIntervalAction = new AbstractAction() {
		private static final long serialVersionUID = -4009452614100125285L;

		public void actionPerformed(final ActionEvent e) {
			if (IntervalList.this.animMode == IntervalList.this.ACTION_NEXT) {
				IntervalList.this.timer.setRepeats(false);
				this.doNext();
			}
			else if (IntervalList.this.animMode == IntervalList.this.ACTION_PLAY_NEXT) {
				//System.out.println("Slide " + i);
				//i = i + 1;
				IntervalList.this.timer.setRepeats(true);
				this.doNext();
			}
			else if (IntervalList.this.animMode == IntervalList.this.ACTION_PREVIOUS) {
				IntervalList.this.timer.setRepeats(false);
				this.doPrevious();
			}
			else if (IntervalList.this.animMode == IntervalList.this.ACTION_PLAY_PREVIOUS) {
				IntervalList.this.timer.setRepeats(true);
				this.doPrevious();
			}
			else if (IntervalList.this.animMode == IntervalList.this.ACTION_STOP) {
				this.doStop();
			}
		}

		public void doNext() {
			if (IntervalList.this.hasNext()) {
				IntervalList.this.nextValue();
				IntervalList.this.fireStateChanged();
			}
			else {
				this.doStop();
			}
		}

		public void doPrevious() {
			if (IntervalList.this.hasPrevious()) {
				IntervalList.this.previousValue();
				IntervalList.this.fireStateChanged();
			}
			else {
				this.doStop();
			}
		}

		public void doStop() {
			IntervalList.this.timer.stop();
		}
	};

	Timer timer = new Timer(1000 / this.frequency, this.updateIntervalAction);

	/**
	 * Default constructor.
	 */
	public IntervalList() {
		this.values = new ArrayList();
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("next")) {
			this.animMode = this.ACTION_NEXT;
			this.timer.start();
		}
		else if (e.getActionCommand().equals("previous")) {
			this.animMode = this.ACTION_PREVIOUS;
			this.timer.start();
		}
		else if (e.getActionCommand().equals("play next")) {
			this.animMode = this.ACTION_PLAY_NEXT;
			this.timer.start();
		}
		else if (e.getActionCommand().equals("play previous")) {
			this.animMode = this.ACTION_PLAY_PREVIOUS;
			this.timer.start();
		}
		else if (e.getActionCommand().equals("stop")) {
			this.animMode = this.ACTION_STOP;
			this.timer.stop();
		}
	}

	/**
	 * Adds the given change listener.
	 * @param l
	 */
	public void addChangeListener(final ChangeListener l) {
		this.listeners.add(l);
	}

	/**
	 * Adds <code>val</code> at the appropriate position in the list.
	 * 
	 * @param val
	 */
	public void addValue(final int val) {
		if (this.verbose) {
			System.out.println("adding value : " + val);
		}
		final Integer value = Integer.valueOf(val);
		if (this.values.contains(value)) {
			return;
		}

		if (val > this.lastValue()) {
			this.values.add(value);
		}
		else if (val < this.firstValue()) {
			this.values.add(0, value);
		}
		else {
			// else search the insertion point and insert the value

			// binary search of insertion point
			int max = this.values.size() - 1;
			int min = 0;
			int mid = 0;
			while (min < max - 1) {
				mid = (min + max) / 2;
				if (((Integer) this.values.get(mid)).intValue() < val) {
					min = mid;
				}
				else {
					max = mid;
					//System.out.println("min="+min+" mid="+mid+" max="+max);
				}
			}
			this.values.add(max, value);
		}
	}

	/**
	 * Creates <code>listIterator</code> if it is null. 
	 * It does nothing otherwise.
	 */
	public void createListIterator() {
		if (this.listIterator == null) {
			this.listIterator = this.values.listIterator();
		}
	}

	/**
	 * Notifies all it's listeners that its state has changed.
	 */
	public void fireStateChanged() {
		for (int i = 0; i < this.listeners.size(); i++) {
			((ChangeListener) this.listeners.elementAt(i))
				.stateChanged(this.event);
		}

	}

	/**
	 * Returns the first value in the list.
	 * 
	 * @return
	 */
	public int firstValue() {
		if (this.values.size() > 0) {
			return ((Integer) this.values.get(0)).intValue();
		}
		return -1;
	}

	/**
	 * Returns the extent of the current slice i.e.
	 * the difference between the maximum bound and
	 * the minimum bound.
	 * @return
	 */
	public int getExtent() {
		if (this.verbose) {
			System.out.println("extent is : "
					+ (this.getMaximum() - this.getMinimum()));
			System.out.println("bounds are " + this.getMinimum() + " - "
					+ this.getMaximum());
		}
		return this.getMaximum() - this.getMinimum();
	}

	/**
	 * @return
	 */
	public int getFrequency() {
		return this.frequency;
	}

	/**
	 * Returns the current maximum bound (the value next 
	 * to the current value).
	 * 
	 * @return
	 */
	public int getMaximum() {
		this.createListIterator();

		if (this.listIterator.hasNext()) {
			return ((Integer) this.values.get(Math.min(
				this.currentIndex + 1,
				this.values.size() - 1))).intValue();
		}
		return this.lastValue();
	}

	/**
	 * Returns the current minimum bound (i.e. the 
	 * current value).
	 * 
	 * @return
	 */
	public int getMinimum() {
		this.createListIterator();

		if (this.listIterator.hasPrevious()) {
			return ((Integer) this.values.get(this.currentIndex)).intValue();
		}

		return this.firstValue();
	}

	public boolean hasNext() {
		this.createListIterator();
		return this.listIterator.hasNext()
				&& this.listIterator.nextIndex() < this.values.size() - 1;
	}

	public boolean hasPrevious() {
		this.createListIterator();
		return this.listIterator.hasPrevious();
	}

	/**
	 * @return
	 */
	public boolean isVerbose() {
		return this.verbose;
	}

	/**
	 * Returns the last value in the list.
	 * 
	 * @return
	 */
	public int lastValue() {
		if (this.values.size() > 0) {
			return ((Integer) this.values.get(this.values.size() - 1))
				.intValue();
		}
		return -1;
	}

	/**
	 * Returns the next value in the list in ascending order.
	 * 
	 * @return
	 */
	public int nextValue() {
		this.createListIterator();

		if (this.hasNext()) {
			this.currentIndex = this.listIterator.nextIndex();
			final int res = ((Integer) this.listIterator.next()).intValue();
			return res;
		}

		return -1;
	}

	/**
	 * Returns the previous value in the list.
	 * 
	 * @return
	 */
	public int previousValue() {
		this.createListIterator();

		if (this.listIterator.hasPrevious()) {
			this.currentIndex = this.listIterator.previousIndex();
			final int res = ((Integer) this.listIterator.previous()).intValue();
			return res;
		}
		return -1;
	}

	/**
	 * Clears all change listeners.
	 */
	public void removeAllListeners() {
		this.listeners.clear();
	}

	/**
	 * Removes the given change listener.
	 * 
	 * @param l
	 */
	public void removeChangeListener(final ChangeListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Removes <code>val</code> from the list.
	 * 
	 * @param val
	 */
	public void removeValue(final int val) {
		this.values.remove(Integer.valueOf(val));
	}

	/**
	 * @param i
	 */
	public void setFrequency(final int i) {
		this.frequency = i;
		this.updateTimer();
	}

	/**
	 * @param b
	 */
	public void setVerbose(final boolean b) {
		this.verbose = b;
	}

	private void updateTimer() {
		this.timer.setDelay(1000 / this.frequency);
	}

}

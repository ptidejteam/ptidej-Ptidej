package fr.emn.oadymppac.widgets;

import javax.swing.SwingUtilities;

/**
 * This is the 3rd version of SwingWorker (also known as
 * SwingWorker 3), an abstract class that you subclass to
 * perform GUI-related work in a dedicated thread.  For
 * instructions on and examples of using this class, see:
 * 
 * http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 *
 * Note that the API changed slightly in the 3rd version:
 * You must now invoke start() on the SwingWorker after
 * creating it.
 */
public abstract class SwingWorker {
	/** 
	 * Class to maintain reference to current worker thread
	 * under separate synchronization control.
	 */
	private static class ThreadVar {
		private Thread thread;
		ThreadVar(final Thread t) {
			this.thread = t;
		}
		synchronized void clear() {
			this.thread = null;
		}
		synchronized Thread get() {
			return this.thread;
		}
	}

	private Object value; // see getValue(), setValue()

	private final ThreadVar threadVar;

	/**
	 * Start a thread that will call the <code>construct</code> method
	 * and then exit.
	 */
	public SwingWorker() {
		final Runnable doFinished = new Runnable() {
			public void run() {
				SwingWorker.this.finished();
			}
		};

		final Runnable doConstruct = new Runnable() {
			public void run() {
				try {
					SwingWorker.this.setValue(SwingWorker.this.construct());
				}
				finally {
					SwingWorker.this.threadVar.clear();
				}

				SwingUtilities.invokeLater(doFinished);
			}
		};

		final Thread t = new Thread(doConstruct);
		this.threadVar = new ThreadVar(t);
	}

	/** 
	 * Compute the value to be returned by the <code>get</code> method. 
	 */
	public abstract Object construct();

	/**
	 * Called on the event dispatching thread (not on the worker thread)
	 * after the <code>construct</code> method has returned.
	 */
	public void finished() {
	}

	/**
	 * Return the value created by the <code>construct</code> method.  
	 * Returns null if either the constructing thread or the current
	 * thread was interrupted before a value was produced.
	 * 
	 * @return the value created by the <code>construct</code> method
	 */
	public Object get() {
		while (true) {
			final Thread t = this.threadVar.get();
			if (t == null) {
				return this.getValue();
			}
			try {
				t.join();
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt(); // propagate
				return null;
			}
		}
	}

	/** 
	 * Get the value produced by the worker thread, or null if it 
	 * hasn't been constructed yet.
	 */
	protected synchronized Object getValue() {
		return this.value;
	}

	/**
	 * A new method that interrupts the worker thread.  Call this method
	 * to force the worker to stop what it's doing.
	 */
	public void interrupt() {
		final Thread t = this.threadVar.get();
		if (t != null) {
			t.interrupt();
		}
		this.threadVar.clear();
	}

	/** 
	 * Set the value produced by worker thread 
	 */
	private synchronized void setValue(final Object x) {
		this.value = x;
	}

	/**
	 * Start the worker thread.
	 */
	public void start() {
		final Thread t = this.threadVar.get();
		if (t != null) {
			t.start();
		}
	}
}

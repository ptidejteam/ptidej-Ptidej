//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.prop;

import java.util.Iterator;
import org.junit.Assert;
import choco.palm.integer.PalmBitSetIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.prop.VarEvent;
import choco.prop.VarEventQueue;
import choco.util.PriorityQueue;

public class PalmVarEventQueue extends VarEventQueue {
	public void assertValidQueue() {
		// TODO : TESTS
		final Iterator it = this.getQueue().iterator();
		while (it.hasNext()) {
			final PalmIntVarEvent evt = (PalmIntVarEvent) it.next();
			Assert.assertTrue(evt.getEventType() != VarEvent.EMPTYEVENT);
		}
	}

	// TODO : dans Choco ?
	public boolean contains(final Object obj) {
		return this.queue.contains(obj);
	}

	public PriorityQueue getQueue() {
		return this.queue;
	}

	/**
	 * Resets all the events of the queue.
	 */

	public void reset() {
		this.resetPopping();
		/*for (Iterator iterator = queue.iterator(); iterator.hasNext();) {
		  PalmIntVarEvent event = (PalmIntVarEvent) iterator.next();
		  //event.reset();
		} */
	}

	/**
	 * Reset the last event if a contradiction occured when it was handled.
	 */

	public void resetPopping() {
		if (this.lastPopped != null
				&& ((PalmIntVarEvent) this.lastPopped).isPopping) {
			((PalmIntVarEvent) this.lastPopped).isPopping = false;
			((PalmIntVarEvent) this.lastPopped).reset();
			if (!this.queue.contains(this.lastPopped)) {
				this.queue.add(this.lastPopped);
			}
		}

	}

	/**
	 * Updates variable explanations.
	 */

	public void restoreVariableExplanations() {
		for (final Iterator iterator = this.queue.iterator(); iterator
			.hasNext();) {
			final PalmIntVarEvent event = (PalmIntVarEvent) iterator.next();
			event.restoreVariableExplanation();
			// removal chain has to be checked to avoid inconsistent state after a value restoration
			// cf scenario 1
			if (((PalmIntVar) event.getModifiedVar()).hasEnumeratedDomain()) {
				((PalmBitSetIntDomain) ((PalmIntVar) event.getModifiedVar())
					.getDomain()).checkRemovalChain();
			}
		}
	}
}

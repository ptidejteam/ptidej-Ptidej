// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.test.integer.IntVarEventTest.java, last modified by FLABURTHE 2 janv. 2004 */
package choco.test.integer;

import java.util.HashSet;
import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.constraints.AbstractBinIntConstraint;
import choco.integer.constraints.EqualXYC;
import choco.integer.var.IntVarEvent;
import choco.prop.ChocEngine;
import choco.prop.PropagationEvent;
import choco.util.IntIterator;

public class IntVarEventTest extends TestCase {
	class LocalConstraintClass extends AbstractBinIntConstraint {
		public LocalConstraintClass(
			final choco.integer.IntVar x,
			final choco.integer.IntVar y) {
			this.v0 = x;
			this.v1 = y;
		}

		public void awakeOnInf(final int idx) {
		}

		public void awakeOnSup(final int idx) {
		}

		public boolean isSatisfied() {
			return false;
		}

		public void propagate() {
		}
	}

	private final Logger logger = Logger
		.getLogger("choco.test.integer.IntVarEventTest");
	private Problem pb;
	private ChocEngine pe;
	private choco.integer.var.IntDomainVar x;
	private choco.integer.var.IntDomainVar y;
	private choco.integer.var.IntDomainVar z;
	private Constraint c1;
	private Constraint c2;
	PropagationEvent evt;

	public IntVarEventTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("IntVarEvent Testing...");
		this.pb = new Problem();
		this.pe = this.pb.getChocEngine();
		this.x =
			(choco.integer.var.IntDomainVar) this.pb.makeBoundIntVar(
				"X",
				0,
				100);
		this.y =
			(choco.integer.var.IntDomainVar) this.pb.makeBoundIntVar(
				"Y",
				0,
				100);
		this.z =
			(choco.integer.var.IntDomainVar) this.pb.makeBoundIntVar(
				"Z",
				0,
				100);
		this.c1 = new LocalConstraintClass(this.x, this.y);
		this.c2 = new LocalConstraintClass(this.y, this.z);
	}

	protected void tearDown() {
		this.c1 = null;
		this.c2 = null;
		this.x = null;
		this.y = null;
		this.z = null;
		this.pb = null;
		this.pe = null;
	}

	public void test1() {
		Assert.assertEquals(0, this.pe.getNbPendingEvents());
		this.pb.post(this.c1);
		this.pb.post(this.c2);
		Assert.assertEquals(2, this.pe.getNbPendingEvents());
		final HashSet expectedSet = new HashSet();
		expectedSet.add(this.c1);
		expectedSet.add(this.c2);
		final HashSet tmp = new HashSet();
		tmp.add(this.pe.getPendingEvent(0).getModifiedObject());
		tmp.add(this.pe.getPendingEvent(1).getModifiedObject());
		Assert.assertEquals(expectedSet, tmp);
		try {
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		Assert.assertEquals(0, this.pe.getNbPendingEvents());
		this.x.getDomain().updateInf(1);
		this.pe.postUpdateInf(this.x, 1);
		this.y.getDomain().updateSup(95);
		this.pe.postUpdateSup(this.y, 1);
		this.y.getDomain().updateInf(3);
		this.pe.postUpdateInf(this.y, 0); // and not a value above 1, such as 2 !!

		Assert.assertEquals(2, this.pe.getNbPendingEvents());
		this.evt = this.pe.getPendingEvent(0);
		Assert.assertEquals(this.evt.getModifiedObject(), this.x);
		Assert.assertEquals(
			IntVarEvent.INCINFbitvector,
			((IntVarEvent) this.evt).getEventType());
		Assert.assertEquals(1, ((IntVarEvent) this.evt).getCause());

		this.evt = this.pe.getPendingEvent(1);
		Assert.assertEquals(this.evt.getModifiedObject(), this.y);
		Assert.assertEquals(
			IntVarEvent.BOUNDSbitvector,
			((IntVarEvent) this.evt).getEventType());
		Assert.assertEquals(-1, ((IntVarEvent) this.evt).getCause());
	}

	/**
	 * tests that a bound event on a variable with two constraints and no cause, yields two propagation steps
	 */
	public void test2() {
		this.c1 = new EqualXYC(this.x, this.y, 2);
		this.c2 = new EqualXYC(this.y, this.z, 1);
		Assert.assertEquals(0, this.pe.getNbPendingEvents());
		this.pb.post(this.c1);
		this.pb.post(this.c2);
		Assert.assertEquals(2, this.pe.getNbPendingEvents());
		final HashSet expectedSet = new HashSet();
		expectedSet.add(this.c1);
		expectedSet.add(this.c2);
		final HashSet tmp = new HashSet();
		tmp.add(this.pe.getPendingEvent(0).getModifiedObject());
		tmp.add(this.pe.getPendingEvent(1).getModifiedObject());
		Assert.assertEquals(expectedSet, tmp);
		try {
			this.pb.propagate();
		}
		catch (final ContradictionException e) {
			Assert.assertTrue(false);
		}
		Assert.assertEquals(0, this.pe.getNbPendingEvents());

		this.y.getDomain().updateSup(90);
		this.pe.postUpdateSup(this.y, 0);
		this.y.getDomain().updateInf(10);
		this.pe.postUpdateInf(this.y, 1);
		Assert.assertEquals(1, this.pe.getNbPendingEvents());
		this.evt = this.pe.getPendingEvent(0);
		Assert.assertEquals(this.evt.getModifiedObject(), this.y);
		Assert.assertEquals(
			IntVarEvent.BOUNDSbitvector,
			((IntVarEvent) this.evt).getEventType());

		final IntIterator cit =
			((IntVarEvent) this.evt)
				.getAllListenersButIterator(((IntVarEvent) this.evt).getCause());
		Assert.assertTrue(cit.hasNext());
		Assert.assertEquals(0, cit.next());
		Assert.assertTrue(cit.hasNext());
		Assert.assertEquals(1, cit.next());
	}

}

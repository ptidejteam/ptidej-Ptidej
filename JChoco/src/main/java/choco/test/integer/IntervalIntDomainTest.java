package choco.test.integer;

import java.util.logging.Logger;
import org.junit.Assert;
import junit.framework.TestCase;
import choco.Problem;
import choco.integer.var.IntervalIntDomain;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntervalIntDomainTest.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2007/09/01 00:05:05 $
// Last Contributor:   $Author: guehene $

public class IntervalIntDomainTest extends TestCase {
	private final Logger logger = Logger
		.getLogger("choco.test.integer.IntervalIntDomainTest");

	private Problem pb;
	private choco.integer.IntVar y;
	IntervalIntDomain yDom;
	public IntervalIntDomainTest(final String name) {
		super(name);
	}

	protected void setUp() {
		this.logger.fine("BitSetIntDomain Testing...");
		this.pb = new Problem();
		this.y = this.pb.makeBoundIntVar("Y", 1, 15);
		this.yDom = (IntervalIntDomain) this.y.getDomain();
	}

	protected void tearDown() {
		this.yDom = null;
		this.y = null;
		this.pb = null;
	}

	/**
	 * testing read and write on bounds with backtracking
	 */
	public void test1() {
		this.logger.finer("test1");

		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(15, this.yDom.getSize());
		this.logger.finest("First step passed");

		this.pb.worldPush();
		this.yDom.updateInf(2);
		this.yDom.updateInf(3);
		Assert.assertEquals(3, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(13, this.yDom.getSize());
		this.logger.finest("Second step passed");

		this.pb.worldPush();
		this.yDom.updateSup(13);
		this.yDom.updateInf(4);
		Assert.assertEquals(4, this.yDom.getInf());
		Assert.assertEquals(13, this.yDom.getSup());
		Assert.assertEquals(10, this.yDom.getSize());
		this.logger.finest("Third step passed");

		this.pb.worldPop();
		Assert.assertEquals(3, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(13, this.yDom.getSize());

		this.pb.worldPop();
		Assert.assertEquals(1, this.yDom.getInf());
		Assert.assertEquals(15, this.yDom.getSup());
		Assert.assertEquals(15, this.yDom.getSize());
	}

	/**
	 * testing freeze and release for the delta domain
	 */
	public void test2() {
		this.logger.finer("test2");

		this.yDom.freezeDeltaDomain();
		Assert.assertTrue(this.yDom.releaseDeltaDomain());

		this.yDom.updateInf(2);
		this.yDom.updateSup(12);
		this.yDom.freezeDeltaDomain();
		this.yDom.updateInf(3);
		Assert.assertTrue(!this.yDom.releaseDeltaDomain());

		this.yDom.freezeDeltaDomain();
		Assert.assertTrue(this.yDom.releaseDeltaDomain());
	}

}

/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package modec.test.visitor.example;

//package src.VISITOR;

class OrderVisitor implements VisitorInterface {
	private double orderTotal;

	public OrderVisitor() {
	}
	public void visit(NonCaliforniaOrder inp_order) {
		this.orderTotal = this.orderTotal + inp_order.getOrderAmount();
	}
	public void visit(CaliforniaOrder inp_order) {
		this.orderTotal =
			this.orderTotal + inp_order.getOrderAmount()
					+ inp_order.getAdditionalTax();
	}
	public void visit(OverseasOrder inp_order) {
		this.orderTotal =
			this.orderTotal + inp_order.getOrderAmount()
					+ inp_order.getAdditionalSH();
	}
	public double getOrderTotal() {
		return this.orderTotal;
	}
}

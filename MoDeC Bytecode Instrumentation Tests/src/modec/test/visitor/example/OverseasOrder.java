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
public class OverseasOrder implements Order {
  private double orderAmount;
  private double additionalSH;

  public OverseasOrder() {
  }
  public OverseasOrder(double inp_orderAmount,
      double inp_additionalSH) {
    this.orderAmount = inp_orderAmount;
    this.additionalSH = inp_additionalSH;
  }
  public double getOrderAmount() {
    return this.orderAmount;
  }
  public double getAdditionalSH() {
    return this.additionalSH;
  }
  public void accept(OrderVisitor v) {
    v.visit(this);
  }
}

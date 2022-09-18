//package src.VISITOR;
import java.util.*;

class OrderVisitor implements VisitorInterface {
  private Vector orderObjList;
  private double orderTotal;

  public OrderVisitor() {
    orderObjList = new Vector();
  }
  public void visit(NonCaliforniaOrder inp_order) {
    orderTotal = orderTotal + inp_order.getOrderAmount();
  }
  public void visit(CaliforniaOrder inp_order) {
    orderTotal = orderTotal + inp_order.getOrderAmount() +
                 inp_order.getAdditionalTax();
  }
  public void visit(OverseasOrder inp_order) {
    orderTotal = orderTotal + inp_order.getOrderAmount() +
                 inp_order.getAdditionalSH();
  }
  public double getOrderTotal() {
    return orderTotal;
  }
}

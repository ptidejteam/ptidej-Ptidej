/*
 * Created on 04-05-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pom.test.rsc;

/**
 * @author zaidifar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class H extends A {

	public String chrs;
	public Integer myInt;
	public Object obj;
	public Float myFloat;

	public H() {
	}

	public void computeResult(int anInt, String aStr, Integer aNumber) {
		double res = this.myInt.doubleValue() + this.myFloat.doubleValue();
		System.out.println(res);
	}

	public void computeSum() {
		displaySum(100, true);
		for (int i = 0; i < this.myInt.intValue(); i++) {
			computeResult(5, "", this.myInt);
			displaySum(100, true);
		}
		computeLine(100, 5, 4, "");
		displaySum(100, true);
		this.fooA1();
	}

	public void displaySum(int nbTimes, boolean debug) {
		computeSum();
		System.out.println(this.chrs + this.toString());
		this.fooA1();
		this.fooA2();
		this.fooA2();
	}

	public void computeLine(int nbTimes, int tps, int tvq, String msg) {
		while (nbTimes > 0) {
			float res = tps * tvq * this.myInt.intValue();
			this.fooA2();
			this.fooA1();
			System.out.println(msg + res);
		}
	}

	public String toString() {
		computeLine(100, 5, 4, "");
		computeLine(100, 5, 4, "");
		computeLine(100, 5, 4, "");
		return this.myInt.toString();
	}

	public void fooNothing() {
	}
}

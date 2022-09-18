package pom.test.rsc;

/**
 * @author Farouk Zaidi
 * @since  2004/04/01
 */
public class C implements D {
	private int a;
	protected int p;
	protected String str;
	public static Integer q;
	public C() {
		super();
	}
	public void fooD() {
		this.a = this.p + this.str.length();
		this.p = this.a - 4;
		Integer localRef = Integer.valueOf(this.p);
		System.out.println(localRef);
	}
	public void myComputingD(final int _a, final int _p, final String _s) {
		C aRef = new C();
		aRef.myComputingD(this.a, this.p, this.str);
		aRef.fooD();
	}

}

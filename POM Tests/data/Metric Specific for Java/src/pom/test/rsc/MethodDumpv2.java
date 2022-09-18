package pom.test.rsc;

/**
 * @author Farouk Zaidi
 * @since  2004/03/24
 */
class MyFather {
	protected Integer p1;
}
public class MethodDumpv2 extends MyFather {
	public Integer p;
	public String str;
	public static Integer a = Integer.valueOf(5);
	public static Integer stat_p;
	float f3 = 4f;
	int MyInt = 0;
	public MethodDumpv2() {
		super();
	}
	public void doNothing() {
		this.p.compareTo(this.p1);
		this.p = Integer.valueOf(54);
		this.p.compareTo(Integer.valueOf(this.p.intValue()));
		this.p.compareTo(this.p = Integer.valueOf(this.p.intValue()));

		//	if (true || this.p.intValue() == 15) {
		if (this.p.intValue() == 15) {
			this.p.intValue();
		}
		Integer a1 = null, a2 = null, a3 = null;
		Integer.valueOf(this.p.compareTo(a3 =
			a2 = a1 = Integer.valueOf(this.p.intValue())));
		a3.byteValue();

		int _a = 10;
		int c = _a + 100 + _a * _a;
		_a = this.p.compareTo(this.p);
		int b = Integer.parseInt("5");
		b = (int) (Math.random());
		int d = _a++;
		b = (int) (Math.max(this.p.intValue(), 145));

		for (int i = 0; i < this.p.intValue(); i++) {
			float lp = Math.min(this.p.floatValue(), a.floatValue());
			lp -= 5f;
			short s = (short) lp;
			System.out.println(s);

			if (this.p instanceof Number) {
			}
			lp =
				this.p instanceof Number ? a.floatValue() : (float) Math
					.random();
			float f1 = 5f, f2 = 7f;
			lp = f1 = f2 = this.f3;
			this.f3 = lp + f1 + lp + f2 + this.f3;
		}

		System.out.println(" " + a1 + a2 + a3 + b + c + d);
	}
}

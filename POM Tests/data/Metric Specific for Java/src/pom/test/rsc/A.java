package pom.test.rsc;

/**
 * @author Farouk Zaidi
 * @since  2004/04/01
 */
public class A {
	B b = new B();
	public A() {
		super();
	}
	public void fooA1() {
		this.b.foo1();
		this.b.fooD();
		this.b.myComputingD(5, B.d.intValue(), this.b.myStr);
		Integer myInt = Integer.valueOf(10);
		int a = 15 + myInt.intValue();
		System.out.println(a);
	}
	public void fooA2() {
		int aLocalVar = D.counter;
		this.b.equals(new C());
		this.b.foo4();
		this.b.foo3();
		this.b.foo6();
		System.out.println(aLocalVar);
	}
}

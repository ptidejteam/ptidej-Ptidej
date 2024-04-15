package pom.test.rsc;

public class MethodDump implements MyInterface {

	public Integer p;

	public Integer p1;

	public String str;

	public MethodDump() {
	}

	public void doNothing() {
		this.str.toString();
		this.p.intValue();
		this.str.hashCode();
	}

	public void foo1() {
		this.p.floatValue();
		this.p1.byteValue();
	}

	public void foo2() {
		this.str.trim();
		this.p1.byteValue();
	}

	public void foo() {
	}

}

interface MyInterface {

	public void doNothing();
}
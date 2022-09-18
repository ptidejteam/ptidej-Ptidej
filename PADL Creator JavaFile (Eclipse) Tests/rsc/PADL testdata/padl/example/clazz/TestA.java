package padl.example.clazz;

import java.util.Iterator;
import padl.example.clazz1.Test2A;

public class TestA extends Test2A {

	TestB a2;

	Iterator iter;

	Iterator[] TabIter;

	String s;

	public TestB getA2() {
		return a2;
	}

	public TestA(int a, TestB a2, Iterator iter, Iterator[] tabIter) {

	}

	public void setA2(TestB _a2) {
		a2 = _a2;
	}

}

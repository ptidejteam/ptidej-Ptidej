package padl.example.clazz2;

import java.util.Iterator;
import padl.example.clazz1.Test2A;

public class TestA extends Test2A {

	int a;

	TestB a2;

	Iterator iter;

	Iterator[] TabIter;

	public TestB getA2() {
		return a2;
	}

	
	public void TestA(){
		
	}
	
	
	public TestA(int a, TestB a2, Iterator iter, Iterator[] tabIter) {
		super();
		this.a = a;
		this.a2 = a2;
		this.iter = iter;
		this.TabIter = tabIter;
	}


	public void setA2(TestB _a2) {
		a2 = _a2;
	}

	void m1(final int k, final float a, String s) {

		final int locale;
		final Object oLocal = new Object();

		final Object oLocalTab = new Object[3];

		class ClassInMethod {

			int f1;
			Object j1;
		}
	}

	class MemberClass {

		String memberClassField;
		MemberClass(String aMemberClassField) {
			memberClassField = aMemberClassField;
		}
	}

}

package  padl.example.eclipse.duplication.memberClasses;

import java.util.List;
import padl.example.interfaz.MyInterface;

public class TestB extends C implements MyInterface {

	class MemberClass {

		int dim;
		void m1(int k, String s, List l) {
			this.dim = k / 3;
		}

	}
	
	class MemberClass {

		int dim;
		void m1(int k, String s, List l) {
			this.dim = k / 3;
		}

	}

	@Override
	public void interfMethod1() {

	}

	@Override
	public String interfMethod2(int id) {

		return null;
	}

	@Override
	public void interfMethod3(String s1, String s2) {

	}
}

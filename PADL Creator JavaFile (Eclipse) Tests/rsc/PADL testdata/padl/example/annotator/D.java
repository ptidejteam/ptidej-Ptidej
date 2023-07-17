package padl.example.annotator;

public class D {

	C nonStaticField;
	static C staticField;

	static void staticCallingMethod(C parameter) {
		C localVariable = new C();
		C localVariable1;
		localVariable1 = new C();
		C.staticMethod();
		localVariable.nonStaticMethod();
		localVariable.staticMethod();
		parameter.nonStaticMethod();
		parameter.staticMethod();
		staticField.nonStaticMethod();
		staticField.staticMethod();

	}

	void nonstaticCallingMethod(C parameter) {
		C localVariable = new C();
		C.staticMethod();
		localVariable.nonStaticMethod();
		localVariable.staticMethod();
		parameter.nonStaticMethod();
		parameter.staticMethod();
		staticField.nonStaticMethod();
		staticField.staticMethod();
		nonStaticField.nonStaticMethod();
		staticField.staticMethod();
	}
}

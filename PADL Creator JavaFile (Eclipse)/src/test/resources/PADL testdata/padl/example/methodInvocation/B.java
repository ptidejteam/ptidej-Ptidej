package padl.example.methodinvocation;

public class B {
	A a;
	public B(){
		 a=new A("m");
	}
	
	public void m(){
		a.print();
		a.setM("b");
		
	}
	
	public void m1(A _a){
		_a.print();
		_a.setM("k");
		
	}

}

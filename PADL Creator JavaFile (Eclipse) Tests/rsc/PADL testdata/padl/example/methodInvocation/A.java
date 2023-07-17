package padl.example.methodinvocation;

public class A {
	
	String m;
	
	
	public static void main(){
		A a=new A("ma");
		a.print();
		a.setM("mo");
		a.print();
	}
	
	public A(String _m){
		
		this.m=_m;
	}
	
	
	public void setM(String _m){
		this.m=_m;
	}

	public void print(){
		System.out.println(m);
	}
}

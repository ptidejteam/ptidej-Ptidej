package padl.example.annotator;

public class C {
	String s;
	public C(){
		super();
		s=new String("ttttt");
	}
	
	public static void staticMethod(){
		System.out.println();
	
	
	}
	
	
	public void nonStaticMethod(){
		this.staticMethod();
		C.staticMethod();
	s.toString();
	super.toString();
		this.s.toString();
		this.s="";
	}
	
	
	
	

}

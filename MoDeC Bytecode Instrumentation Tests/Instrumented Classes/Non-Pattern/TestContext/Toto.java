package modec.test.nonpattern.example;

public class Toto
{
	private void foo(int b)
	{
		int a = b;
		
		System.out.println(a);
	}
	
	public static void main(String [] args)
	{
		Toto t = new Toto();
		t.foo(2);	
	}

}
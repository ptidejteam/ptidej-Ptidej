/**
 * From Benjamin Strauss <bnstraus@hawaii.edu>
 */

package padl.example.defaultmethods;

public interface Example1 {
	public default void ExampleDefaultMethod() {
		System.out.print("Default Method called");
	}

	public static void main(String[] args) {
		System.out.print("Main Called");
	}
}

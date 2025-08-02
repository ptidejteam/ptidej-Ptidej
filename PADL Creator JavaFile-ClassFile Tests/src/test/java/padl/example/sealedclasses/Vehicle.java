/**
 * From https://www.baeldung.com/java-sealed-classes-interfaces
 */

package padl.example.sealedclasses;

public sealed class Vehicle permits Bus, Bicycle, Car {
	public void ExampleMethod() {
		System.out.print("Method called");
	}

	public static void main(String[] args) {
		System.out.print("Main Called");
	}
}

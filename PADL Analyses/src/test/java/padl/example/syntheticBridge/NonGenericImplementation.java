package padl.example.syntheticBridge;

public class NonGenericImplementation implements GenericInterface<String> {

	// A synthetic bridge should appear in the classfile
	// to deal with type erasure
	@Override
	public void foo(String t) {
	}

}

package padl.kernel;

public final class Cardinality {

	public static final Cardinality One = new Cardinality(1);
	public static final Cardinality Many = new Cardinality(2);

	private final int value;

	private Cardinality(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		return Integer.toString(this.value);
	}
	
	public static Cardinality valueOf(final String name) {
		if (name == null) {
			throw new NullPointerException("Name cannot be null");
		}

		if (name.equals("One")) {
			return One;
		}
		else if (name.equals("Many")) {
			return Many;
		}

		throw new IllegalArgumentException(
			"No enum constant padl.kernel.Cardinality." + name);
	}
}
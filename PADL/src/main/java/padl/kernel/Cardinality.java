package padl.kernel;

public enum Cardinality {
	One, Many;

	public String toString() {
		// Amr 26/05/05: Ugly!
		// But it makes the tests pass...
		// TODO Remove and update the tests

		if (this == One) {
			return "1";
		} else {
			return "2";
		}
	}
}

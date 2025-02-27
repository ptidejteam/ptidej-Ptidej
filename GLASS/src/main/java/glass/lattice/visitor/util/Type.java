package glass.lattice.visitor.util;

public class Type {

	private String _typename;
	private boolean _type_is_array;

	public Type(String typename) {
		_typename = typename;
		_type_is_array = false;
	}

	public Type(String typename, boolean type_is_array) {
		_typename = typename;
		_type_is_array = type_is_array;
	}

	public String getTypename() {
		return _typename;
	}

	public boolean isArray() {
		return _type_is_array;
	}

	public void updateTypeName(String new_typename) {
		_typename = new_typename;
	}

	public boolean isResolved() {
		return _typename.charAt(0) != 'Q';
	}
}

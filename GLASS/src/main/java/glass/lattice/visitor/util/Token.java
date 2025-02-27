package glass.lattice.visitor.util;

public class Token {
	private String _chars;
	private boolean _java_source_format;

	public Token(String chars, boolean java_source_format) {
		_chars = chars;
		_java_source_format = java_source_format;
	}

	public String getChars() {
		return _chars;
	}

	public boolean isArray() {
		return _chars.equals("[");
	}

	public boolean isGenericSeparator() {
		if (_java_source_format) {
			return _chars.equals(",");
		} else {
			return _chars.equals(";");
		}
	}

	public boolean isGenericStart() {
		return _chars.equals("<");
	}

	public boolean isGenericStop() {
		return _chars.equals(">");
	}

	public boolean isTypeName() {
		return _chars.matches("[a-zA-Z0-9.]+");
	}
}

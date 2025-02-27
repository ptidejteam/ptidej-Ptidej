package glass.lattice.visitor.util;


public class Parser {

	private boolean _array_as_node;

	public Parser() {
		this._array_as_node = false;
	}

	public Parser(boolean array_as_node) {
		this._array_as_node = array_as_node;
	}

	private void parseGeneric(Input input, Node parent) throws Exception {
		Token next_token = input.consume();
		if (!next_token.isGenericStart()) {
			throw new Exception("Unexpected token");
		}

		boolean valid = true;
		while (valid) {
			next_token = input.peek();

			if (next_token.isGenericSeparator()) {
				// Eat the separator
				input.consume();

				// Check the next one right now, it could be the end
				next_token = input.peek();
				if (next_token.isGenericStop()) {
					input.consume();
					valid = false;
				}
			} else if (next_token.isGenericStop()) {
				input.consume();
				valid = false;
			} else if (next_token.isTypeName() || next_token.isArray()) {
				parseType(input, parent);
			} else {
				throw new Exception("Unexpected token while parsing generic");
			}
		}
	}

	@SuppressWarnings("unused")
	private Node parseType(Input input, Node parent) throws Exception {
		Token next_token = input.peek();
		boolean type_is_array = false;
		if (next_token.isArray()) {
			// Eat the token
			input.consume();

			if (_array_as_node) {
				// We want the 'array' information as a separate node; create it
				parent = new Node(new Type("***", true), parent);
			} else {
				// No special nodes, simply remember that this type is an array
				type_is_array = true;
			}
		}

		// Generate the node for the current type
		Node current = new Node(new Type(input.consume().getChars(), type_is_array), parent);

		// Check if there is anything after the type
		next_token = input.peek();
		if (next_token != null) {
			if (next_token.isGenericStart()) {
				parseGeneric(input, current);
			} else if (next_token.isGenericSeparator()) {
				// Simply ignore, it'll be handled somewhere else
				// assuming correct format
			} else if (next_token.isGenericStop()) {
				// Simply ignore, it'll be handled somewhere else
				// assuming correct format
			} else {
				throw new Exception("Unexpected token after type");
			}
		}

		// All done
		return current;
	}

	public Node parse(String string, boolean java_source_format) throws Exception {
		// Tokenize the input
		Input input = new Input(string, java_source_format);

		// Take the first token, assume its a type and create the root node
		Node root = parseType(input, null);

		return root;
	}

}

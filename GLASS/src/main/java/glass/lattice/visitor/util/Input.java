package glass.lattice.visitor.util;

import java.util.ArrayList;
import java.util.List;

public class Input {
	private List<Token> _tokens;
	private int _index;
	private boolean _java_source_format;

	public Input(String input, boolean java_source_format) {
		this._index = 0;
		this._java_source_format = java_source_format;

		this._tokens = tokenize(input);
	}

	private List<Token> tokenize(String input) {
		List<Token> result = new ArrayList<Token>();

		String token = "";
		for (int index = 0; index < input.length(); index++) {
			String current = "" + input.charAt(index);

			// If we find a character that is not a valid continuation of the last one
			// let's push the tokens
			if (current.matches("[a-zA-Z0-9.]+")) {
				// Still a valid character, remember it
				token = token + current;
			} else {
				// If we already remembered something from previous iterations, push
				if (!token.equals("")) {
					result.add(new Token(token, this._java_source_format));
					token = "";
				}

				// Push the stop character
				result.add(new Token(current, this._java_source_format));
			}
		}

		// Make sure we add any remaining stuff in the buffer
		if (!token.equals("")) {
			result.add(new Token(token, this._java_source_format));
		}

		return result;
	}

	public boolean hasData() {
		return this._index < this._tokens.size();
	}

	public Token peek() {
		if (hasData()) {
			return this._tokens.get(this._index);
		}
		return null;
	}

	public Token consume() {
		Token next = peek();
		this._index = this._index + 1;
		return next;
	}
}

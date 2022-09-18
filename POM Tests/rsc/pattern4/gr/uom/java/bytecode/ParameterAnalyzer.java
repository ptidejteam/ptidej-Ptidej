package gr.uom.java.bytecode;

import java.util.ArrayList;
import java.util.List;

public class ParameterAnalyzer {
	private List<String> parameters;

	public ParameterAnalyzer(String params) {
		this.parameters = new ArrayList<String>();
		String[] tokens = params.split(", ");
		ArrayList<String> initialTokens = new ArrayList<String>();
		for(int i=0; i<tokens.length; i++)
			initialTokens.add(tokens[i]);
		handleTokens(initialTokens);
	}

	public List<String> getParameters() {
		return parameters;
	}

	private void handleTokens(List<String> tokens) {
		int position = 0;
		String currentToken = null;
		String nextToken = null;
		for(String token : tokens) {
			currentToken = token;
			int numberOfOpeningTags = getNumberOfCharactersInsideString(token, '<');
			int numberOfClosingTags = getNumberOfCharactersInsideString(token, '>');
			if(numberOfOpeningTags == numberOfClosingTags) {
				parameters.add(token);
			}
			else {
				nextToken = tokens.get(position+1);
				break;
			}
			position++;
		}
		if(nextToken != null) {
			String newToken = currentToken.concat(", ").concat(nextToken);
			tokens.remove(position+1);
			tokens.set(position, newToken);
			for(int i=0; i<position; i++)
				tokens.remove(0);
			handleTokens(tokens);
		}
	}

	private int getNumberOfCharactersInsideString(String sourceString, char lookFor) {
		int count = 0;
		for (int i = 0; i < sourceString.length(); i++) {
			final char c = sourceString.charAt(i);
			if (c == lookFor) {
				count++;
			}
		}
		return count;
	}
}

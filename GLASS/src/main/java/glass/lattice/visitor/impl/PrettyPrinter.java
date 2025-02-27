package glass.lattice.visitor.impl;

import glass.lattice.visitor.AbstractVisitor2;
import glass.lattice.visitor.util.Node;

public class PrettyPrinter extends AbstractVisitor2{

	private String _output;
	private boolean _clean_output;
	private boolean _output_signature_format;

	public PrettyPrinter(boolean clean_output, boolean output_signature_format) {
		super(false);
		_clean_output = clean_output;
		_output_signature_format = output_signature_format;
	}

	@Override
	protected void onNode(Node node) {
		String typename = node.getType().getTypename();

		if (_clean_output) {
			if (!typename.equals("") && typename.charAt(0) == 'Q') {
				typename = typename.substring(1);
			}
		}

		_output += typename;
	}

	@Override
	protected void beforeNode(Node node) {
		Node parent = node.getParent();
		if (parent != null) {
			if (node.isParentFirstChild()) {
				_output += "<";
			} else {
				if (_output_signature_format) {
					_output += ";";
				} else {
					_output += ",";
				}
			}
		}
	}

	@Override
	protected void afterNode(Node node) {
		Node parent = node.getParent();
		if (parent != null && node.isParentLastChild()) {
			if (_output_signature_format) {
				_output += ";>";
			} else {
				_output += ">";
			}
		}
	}

	public String getOutput() {
		return _output;
	}

	@Override
	protected void onVisitStart(Node node) {
		_output = "";

		if (!node.isRoot()) {
			throw new RuntimeException("Can't start visit in PrettyPrinter with non-root node ");
		}
	}
}

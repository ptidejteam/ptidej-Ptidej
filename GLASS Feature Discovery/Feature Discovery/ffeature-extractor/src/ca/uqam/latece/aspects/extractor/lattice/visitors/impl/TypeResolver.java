package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import org.eclipse.jdt.core.IType;

public class TypeResolver extends AbstractVisitor2 {

	private IType _context;

	public TypeResolver(IType context) {
		super(true);
		_context = context;
	}

	@Override
	protected void onNode(Node node) throws Exception {
		Type type = node.getType();

		// If the type is resolved, bail out
		if (type.isResolved()) {
			return;
		}

		// Extract the sub-tree
		Node subtree = node.asSubTree();

		// Get the *full* name of the sub-tree by pretty-printing it (and cleaning it)
		PrettyPrinter printer = new PrettyPrinter(true, false);
		printer.visit(subtree);
		String full_name = printer.getOutput();

		// Resolve the type using the context
		String[][] resolved = _context.resolveType(full_name);

		// Validate the resolve and bail out if something went wrong
		if (resolved == null) {
			throw new Exception("Could not resolve type: " + full_name);
		} else if (resolved.length != 1) {
			throw new Exception("Too many choices for resolved type " + full_name);
		}

		// Re-combine the resolved to get the full typename
		String resolved_typename = resolved[0][0] + "." + resolved[0][1];

		// Parse the resolved type (to extract the new type name)
		Parser parser = new Parser();
		Node root = parser.parse(resolved_typename, true);

		// Patch the current type
		type.updateTypeName(root.getType().getTypename());
	}
}

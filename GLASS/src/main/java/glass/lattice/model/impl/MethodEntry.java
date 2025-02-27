package glass.lattice.model.impl;

import java.util.HashSet;
import java.util.Set;

import glass.ast.IMethod;

public final class MethodEntry {
	/**
	 * method is one of the implementations used as a reference point does
	 * not matter which. We can't even take the highest defined method
	 * because we can have several inheritance trees in the same project
	 */
	public IMethod method = null;

	/**
	 * all the implementations (all IMethod objects) with the same
	 * signature, including method itself
	 */
	public Set<IMethod> allImplementations;

	public MethodEntry() {
		allImplementations = new HashSet<IMethod>();
	}

	public MethodEntry(IMethod aMethod) {
		this();
		addImplementation(aMethod);
	}

	public void addImplementation(IMethod anImplementation) {
		if (anImplementation == null)
			return;

		// if this is the first, then set it
		if (method == null)
			method = anImplementation;

		// add it to the set of all implementations
		allImplementations.add(anImplementation);
	}
}

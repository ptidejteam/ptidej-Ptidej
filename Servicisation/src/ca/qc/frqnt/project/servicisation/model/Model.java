package ca.qc.frqnt.project.servicisation.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Model {
	private final Set<Constituent> constituents;

	public Model() {
		this.constituents = new HashSet<Constituent>();
	}
	public void addConstituent(final Constituent aConstituent) {
		this.constituents.add(aConstituent);
	}
	public Iterator<Constituent> getIteratorOnConstituents() {
		return this.constituents.iterator();
	}
}

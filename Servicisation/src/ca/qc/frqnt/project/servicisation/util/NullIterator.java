package ca.qc.frqnt.project.servicisation.util;

import java.util.Iterator;

public class NullIterator<E> implements Iterator<E> {

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public E next() {
		throw new RuntimeException("This method should never be called");
	}

}

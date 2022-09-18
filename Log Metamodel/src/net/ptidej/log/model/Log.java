package net.ptidej.log.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Log {
	private final Set<Entry> entries = new LinkedHashSet<Entry>();

	public void addEntry(final Entry anEntry) {
		this.entries.add(anEntry);
	}

	public Iterator<Entry> iteratorOnEntries() {
		return this.entries.iterator();
	}
}

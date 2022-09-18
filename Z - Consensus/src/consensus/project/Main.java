package consensus.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
	final List<String> list = new ArrayList<String>();

	void yann() {
		for (final Iterator<String> iterator = this.list.iterator(); iterator
			.hasNext();) {
			String s = (String) iterator.next();
			System.out.println(s);
		}
	}
	void mashael() {
		final Iterator<String> iterator = this.list.iterator();
		while (iterator.hasNext()) {
			String s = (String) iterator.next();
			System.out.println(s);
		}
	}
	void fabio() {
		for (final String s : this.list) {
			System.out.println(s);
		}
	}
}

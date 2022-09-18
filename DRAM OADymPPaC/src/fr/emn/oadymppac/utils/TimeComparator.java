package fr.emn.oadymppac.utils;

import java.util.Comparator;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.ConstraintManager;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.Variable;
import fr.emn.oadymppac.VariableManager;

/**
 * Comparator for row/column adjacency matrix control
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public class TimeComparator implements Comparator {
	Solver solver;
	VariableManager vmanager;
	ConstraintManager cmanager;

	public TimeComparator(final Solver solver) {
		this.solver = solver;
		this.vmanager = VariableManager.getVariableManager(solver);
		this.cmanager = ConstraintManager.getConstraintManager(solver);
	}

	/**
	 * @see java.util.Comparator#compare(Object, Object)
	 */
	public int compare(final Object o1, final Object o2) {
		final String name1 = (String) o1;
		final String name2 = (String) o2;

		return this.getTime(name1) - this.getTime(name2);
	}
	/**
	 * @see java.util.Comparator#equals(Object)
	 */
	public boolean equals(final Object obj) {
		return false;
	}

	int getTime(final String name) {
		final Variable v = this.vmanager.getVariable(name);
		if (v != null) {
			return v.getN();
		}

		final Constraint c = this.cmanager.getConstraint(name);
		if (c != null) {
			return c.getN();
		}
		return 0;
	}
}
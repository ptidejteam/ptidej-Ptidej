/*
 * $Id: Constraint.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac;

import java.util.Vector;

/**
 * The <code>Constraint</code> class implements the local view of a
 * constraint.  It also provides methods for managing the creation
 * and withdrawal of constraints that occur during solving and
 * backtracking.
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public class Constraint implements Cloneable, Identifiable {
	/** Generator of static ids. */
	static int next_id;
	/** The event number that created the variable. */
	int n;
	/** The name of the constraint. */
	String cname;
	/** The variables used by the constraint. */
	String[] variables;
	/** The activation conditions for each variable. */
	short[][] types;
	/**
	 * True when constraint is given by user, false if specified by
	 * solver.
	 */
	boolean user;
	/** The external representation. */
	String externalRep;
	/** The internal representation */
	String internalRep;
	/** Is the constraint active. */
	boolean active;
	/** Unique number associated with this variable. */
	int id;
	short type;

	/**
	 * Constructor.
	 * @param n DOCUMENT ME!
	 * @param cname DOCUMENT ME!
	 * @param user DOCUMENT ME!
	 * @param updateList DOCUMENT ME!
	 */
	public Constraint(
		final int n,
		final String cname,
		final boolean user,
		final UpdateList updateList) {
		this(n, cname, user, updateList, null, null);
	}

	/**
	 * Creates a new Constraint object.
	 *
	 * @param n DOCUMENT ME!
	 * @param cname DOCUMENT ME!
	 * @param user DOCUMENT ME!
	 * @param updateList DOCUMENT ME!
	 * @param externalRep DOCUMENT ME!
	 * @param internalRep DOCUMENT ME!
	 */
	public Constraint(
		final int n,
		final String cname,
		final boolean user,
		final UpdateList updateList,
		final String externalRep,
		final String internalRep) {
		this.n = n;
		this.cname = cname;
		this.user = user;
		this.externalRep = externalRep;
		this.internalRep = internalRep;
		this.id = Constraint.next_id++;

		if (updateList == null) {
			return;
		}

		final int len = updateList.getLength();
		final Vector vars = new Vector(len);
		final Vector types = new Vector(len);
		int i;

		for (i = 0; i < len; i++) {
			final Update u = updateList.getUpdate(i);
			final String vname = u.getVName();
			int index = vars.indexOf(vname);

			if (index < 0) {
				index = vars.size();
				vars.add(vname);
				types.add(null);
			}

			final short type = u.getType();

			if (type != SolverConstants.TYPE_NONE) {
				Vector t;

				if (types.elementAt(index) == null) {
					t = new Vector(2);
					types.setElementAt(t, index);
				}
				else {
					t = (Vector) types.elementAt(index);
				}

				t.add(Short.valueOf(type));
			}
		}

		this.variables = new String[vars.size()];
		this.types = new short[vars.size()][];

		for (i = 0; i < vars.size(); i++) {
			this.variables[i] = (String) vars.elementAt(i);

			if (types.elementAt(i) != null) {
				final Vector a = (Vector) types.elementAt(i);
				this.types[i] = new short[a.size()];

				for (int j = 0; j < a.size(); j++) {
					this.types[i][j] = ((Short) a.elementAt(j)).shortValue();
				}
			}
		}
	}

	/**
	 * Get the value of cname.
	 * 
	 * @return value of cname.
	 */
	public String getCName() {
		return this.cname;
	}

	/**
	 * Get the value of externalRep.
	 * 
	 * @return value of externalRep.
	 */
	public String getExternalRep() {
		return this.externalRep;
	}

	/**
	 * Get the value of id.
	 * 
	 * @return value of id.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get the value of internalRep.
	 * 
	 * @return value of internalRep.
	 */
	public String getInternalRep() {
		return this.internalRep;
	}

	/**
	 * Returns the n.
	 * 
	 * @return int
	 */
	public int getN() {
		return this.n;
	}

	/**
	 * Get the types of the variable.
	 * 
	 * @param vname the name of the variable.
	 * 
	 * @return type values or null
	 */
	public short[] getType(final String vname) {
		return this.types[this.indexOf(vname)];
	}

	/**
	 * Get the types of the variable at index.
	 * 
	 * @param index index of the variable.
	 * 
	 * @return type values or null
	 */
	public short[] getTypeAt(final int index) {
		return this.types[index];
	}

	/**
	 * Get the value of nth variable.
	 * 
	 * @param index index of the variable.
	 * 
	 * @return name of variable.
	 */
	public String getVariableAt(final int index) {
		return this.variables[index];
	}

	/**
	 * Return the variable count.
	 * @return DOCUMENT ME!
	 */
	public int getVariableCount() {
		return this.variables == null ? 0 : this.variables.length;
	}

	/**
	 * Return the index of a variable.
	 * @param vname DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int indexOf(String vname) {
		vname = vname.intern(); // variable names are supposed to be interned

		for (int i = 0; i < this.variables.length; i++) {
			if (vname == this.variables[i]) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Get the value of active.
	 * 
	 * @return value of active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Get the value of user.
	 * 
	 * @return value of user.
	 */
	public boolean isUser() {
		return this.user;
	}

	/**
	 * Set the value of active.
	 * 
	 * @param v  Value to assign to active.
	 */
	public void setActive(final boolean v) {
		this.active = v;
	}

	/**
	 * Set the value of cname.
	 * 
	 * @param v  Value to assign to cname.
	 */
	public void setCName(final String v) {
		this.cname = v;
	}

	/**
	 * Set the value of externalRep.
	 * 
	 * @param v  Value to assign to externalRep.
	 */
	public void setExternalRep(final String v) {
		this.externalRep = v;
	}

	/**
	 * Set the value of internalRep.
	 * 
	 * @param v  Value to assign to internalRep.
	 */
	public void setInternalRep(final String v) {
		this.internalRep = v;
	}

	/**
	 * Sets the n.
	 * 
	 * @param n The n to set
	 */
	public void setN(final int n) {
		this.n = n;
	}

	/**
	 * Set the types of the variable at index.
	 * 
	 * @param v  Values to assign to type.
	 * @param index index of the variable.
	 */
	public void setTypeAt(final short[] v, final int index) {
		this.types[index] = v;
	}

	/**
	 * Set the types of the variable.
	 * 
	 * @param v  Values to assign to type.
	 * @param vname name of the variable.
	 */
	public void setTypeAt(final short[] v, final String vname) {
		this.types[this.indexOf(vname)] = v;
	}

	/**
	 * Set the value of user.
	 * 
	 * @param v  Value to assign to user.
	 */
	public void setUser(final boolean v) {
		this.user = v;
	}

	/**
	 * Set the value of the nth variable.
	 * 
	 * @param vname  name to assign to variable.
	 * @param index index of the variable.
	 */
	public void setVariableAt(final String vname, final int index) {
		this.variables[index] = vname;
	}
}
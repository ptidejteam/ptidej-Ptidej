package choco.bool;

// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.bool.BoolConstraint.java, last modified by François march, 17th, 2004 */

/**
 * Boolean constraints are composite constraints who maintain for each sub-constraint:
 *   a status {unknown, true, false} indicating whether the subconstraint has been proven true or false
 *   a targetStatus {unknown, true, false} indicating whether the subconstraint should be
 *     true (in which case it is propagated) or false (in which case its opposite is propagated)
 */
public interface BoolConstraint {

	/**
	 *  returns the current status of one of its subconstraints
	 * @param constIdx the index of the subconstraint
	 * @return Boolean.TRUE if the subconstraint is entailed, Boolean.FALSE if it is violated, NULL otherwise
	 */
	public Boolean getStatus(int constIdx);

	/**
	 *  returns the current target status of one of its subconstraints
	 * @param constIdx the index of the subconstraint
	 * @return Boolean.TRUE if the subconstraint must be satisfied (thus propagated),
	 *         Boolean.FALSE if it must be violated (thus counter-propagated), NULL otherwise
	 */
	public Boolean getTargetStatus(int constIdx);

	/**
	 * updates the status of one of its subconstraints
	 * @param constIdx the index of the subconstraint
	 * @param st true if the subconstraint is entailed, false if it is violated
	 */
	public void setStatus(int constIdx, boolean st);

	/**
	 * updates the target status of one of its subconstraints
	 * @param constIdx the index of the subconstraint
	 * @param st true if the subconstraint must be satisfied (thus propagated),
	 *         false if it must be violated (thus counter-propagated)
	 */
	public void setTargetStatus(int constIdx, boolean st);
}

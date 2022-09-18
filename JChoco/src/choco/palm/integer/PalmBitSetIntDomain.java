//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer;

import java.util.BitSet;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.integer.var.BitSetIntDomain;
import choco.integer.var.IntDomainVar;
import choco.mem.IStateInt;
import choco.palm.explain.Explanation;
import choco.palm.integer.constraints.PalmAssignment;
import choco.palm.integer.constraints.PalmNotEqualXC;
import choco.palm.integer.explain.RemovalExplanation;
import choco.palm.prop.PalmEngine;
import choco.palm.prop.StructureMaintainer;
import choco.util.IntIterator;

public class PalmBitSetIntDomain extends BitSetIntDomain implements
		PalmIntDomain {
	protected class RepairIntDomainIterator implements IntIterator {
		protected BitSetIntDomain domain;
		protected int currentIndex = -1;

		private RepairIntDomainIterator(final BitSetIntDomain dom) {
			this.domain = dom;
			this.currentIndex = -1;
		}

		public boolean hasNext() {
			if (this.currentIndex == -1) {
				return PalmBitSetIntDomain.this.firstRestValueToBePropagated != -1;
			}
			else {
				return PalmBitSetIntDomain.this.restoreChain[this.currentIndex] != -1;
			}
		}

		public int next() {
			if (this.currentIndex == -1) {
				this.currentIndex =
					PalmBitSetIntDomain.this.firstRestValueToBePropagated;
			}
			else {
				this.currentIndex =
					PalmBitSetIntDomain.this.restoreChain[this.currentIndex];
			}
			return this.currentIndex + PalmBitSetIntDomain.this.offset;
		}

		public void remove() {
			if (this.currentIndex == -1) {
				throw new IllegalStateException();
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * A list of explanations for value withdrawals.
	 */

	public RemovalExplanation[] explanationOnVal;

	/**
	 * Decision constraints on the variable for branching purpose.
	 */

	public Constraint[] decisionConstraints;

	/**
	 * Negation of decision constraints on the variable for branching purpose.
	 */

	public Constraint[] negDecisionConstraints;

	/**
	 * A linked list for restoration events.
	 */

	int[] restoreChain;

	/**
	 * The begin of the <code>restoreChain</code> linked list.
	 */

	int firstRestValueToBePropagated = -1;

	/**
	 * States wether a value will be restored.
	 */

	BitSet willBeRestored;

	public PalmBitSetIntDomain(final IntDomainVar v, final int a, final int b) {
		super(v, a, b);
		this.explanationOnVal = new RemovalExplanation[b - a + 1];
		this.restoreChain = new int[b - a + 1];
		this.willBeRestored = new BitSet();
		this.decisionConstraints = new Constraint[b - a + 1];
		this.negDecisionConstraints = new Constraint[b - a + 1];
	}

	private boolean add(final int x) {
		// ajoute la valeur si necessaire.
		final int i = x - this.offset;
		if (!this.contents.get(i)) {
			this.addIndex(i);
			return true;
		}
		else {
			return false;
		}
	}

	protected void addIndex(final int i) {
		this.contents.set(i);
		if (!this.willBeRestored.get(i)) {
			// verifie d'abord que l'evenement de restauration n'est pas deja la avant de ne l'ajouter.
			this.restoreChain[i] = this.firstRestValueToBePropagated;
			this.firstRestValueToBePropagated = i;
			this.willBeRestored.set(i);
		}
		this.size.add(1);
	}

	/**
	 * When restoration are raised, some value removal can be inappropiate. This removes such past events.
	 */

	public void checkRemovalChain() {
		int cidx = this.firstIndexToBePropagated;
		int precidx = -1;
		while (cidx != -1) {
			if (this.contents.get(cidx)) { // Un element dans le domaine n'a pas a etre propager en removeVal
				//clear chain
				if (cidx == this.firstIndexToBePropagated) {
					this.firstIndexToBePropagated = this.chain[cidx];
				}
				else {
					this.chain[precidx] = this.chain[cidx];
				}

			}
			else {
				precidx = cidx;
			}

			cidx = this.chain[cidx];
		}
	}

	/**
	 * Checks if the value is in the domain. Basically it checks if the value is in the original domain and if this case
	 * only calls the super method.
	 */

	public boolean contains(final int val) {
		// Yann 2010/07/21: Equivalent code.
		// I replace call to methods with internal field  
		// accesses to gain some performances...
		// if (val < this.getOriginalInf() || val > this.getOriginalSup()) {
		if (val < this.offset || val > this.offset + this.chain.length - 1) {
			return false;
		}
		return super.contains(val);
	}

	/**
	 * Returns all the value currently in the domain.
	 */

	public int[] getAllValues() {
		final int[] ret = new int[this.getSize()];
		int idx = 0;
		for (int val = this.contents.nextSetBit(0); val >= 0; val =
			this.contents.nextSetBit(val + 1)) {
			ret[idx] = val + this.offset;
			idx++;
		}
		return ret;
	}

	/**
	 * Returns the decision constraint assigning the domain to the specified value. The constraint is created if
	 * it is not yet created.
	 */

	public Constraint getDecisionConstraint(final int val) {
		Constraint cons = this.decisionConstraints[val - this.getOriginalInf()];
		if (cons != null) {
			return cons;
		}
		else {
			cons = new PalmAssignment(this.variable, val);
			this.decisionConstraints[val - this.getOriginalInf()] = cons;
			this.negDecisionConstraints[val - this.getOriginalInf()] =
				new PalmNotEqualXC(this.variable, val);
			return cons;
		}
	}

	/**
	 * Returns the negated decision constraint.
	 */

	public Constraint getNegDecisionConstraint(final int val) {
		return this.negDecisionConstraints[val - this.getOriginalInf()];
	}

	/**
	 * Returns the original lower bound.
	 */

	public int getOriginalInf() {
		return this.offset;
	}

	/**
	 * Returns the original upper bound.
	 */

	public int getOriginalSup() {
		return this.offset + this.chain.length - 1;
	}

	/**
	 * Returns an iterator on restored values.
	 */

	public IntIterator getRepairIterator() {
		return new PalmBitSetIntDomain.RepairIntDomainIterator(this);
	}

	/**
	 * If restoration handling completes, the chain must reinitlized to null.
	 */

	public void releaseRepairDomain() {
		this.firstRestValueToBePropagated = -1;
		this.willBeRestored.clear();
	}

	private boolean removeVal(
		final int value,
		final choco.palm.explain.Explanation e) {
		if (this.contains(value)) {
			this.explanationOnVal[value - this.getOriginalInf()] =
				e.makeRemovalExplanation(value, (PalmIntVar) this.variable);
			this.remove(value);
			if (this.getSize() == 1) {
				this.variable.value.set(this.getInf());
			}
			else if (this.getSize() == 0) {
				this.variable.value.set(IStateInt.UNKNOWN_INT);
			}
			StructureMaintainer.updateDataStructures(
				this.variable,
				PalmIntDomain.VAL,
				value,
				0);
			return true;
		}
		return false;
	}

	/**
	 * Removes a value and posts the event.
	 */

	public boolean removeVal(
		final int value,
		final int idx,
		final choco.palm.explain.Explanation e) {

		if (this.removeVal(value, e)) {
			((PalmEngine) this.getProblem().getPropagationEngine())
				.postRemoveVal(this.variable, value, idx);
			if (this.getSize() == 0) {
				((PalmEngine) this.getProblem().getPropagationEngine())
					.raisePalmContradiction((PalmIntVar) this.variable);
			}
			return true;
		}
		return false;
	}

	/**
	 * When a lower bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnInf() {
	}

	/**
	 * When an upper bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnSup() {
	}

	/**
	 * When a value is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnVal(final int val) {
		this.explanationOnVal[val - this.getOriginalInf()] = null;
	}

	/**
	 * If a contradiction occure during restoration handling, the chain must be reinitialized in the previous state.
	 */

	public void resetRemovalChain() {
		this.firstIndexToBePropagated = this.firstIndexBeingPropagated;
		this.firstIndexBeingPropagated = -1;
	}

	/**
	 * Restores a lower bound and posts the event.
	 */

	public void restoreInf(final int newValue) {
		System.err
			.println("restoreInf should not be called on a BitSetIntdomain !");
	}

	/**
	 * Restores an upper bound and posts the event.
	 */

	public void restoreSup(final int newValue) {
		System.err
			.println("restoreSup should not be called on a BitSetIntdomain !");
	}

	/**
	 * Restores a value and posts the event.
	 */

	public void restoreVal(final int val) {
		this.add(val);
		/*if (val < this.inf) {
		            this.inf = val;
		        }
		        if (val > this.sup) {
		            this.sup = val;
		        } */
		if (this.getInf() == this.getSup()) {
			this.variable.value.set(this.getInf());
		}
		else {
			this.variable.value.set(IStateInt.UNKNOWN_INT);
		}
		StructureMaintainer.updateDataStructuresOnRestore(
			this.variable,
			PalmIntDomain.VAL,
			val,
			0);
		//((PalmIntVar) this.variable).updateDataStructuresOnRestore(PalmIntVar.VAL, val, 0);
		((PalmEngine) this.getProblem().getPropagationEngine()).postRestoreVal(
			(PalmIntVar) this.variable,
			val);
	}

	// Private Stuf ...

	/**
	 * Allows to get an explanation for the domain or a bound of the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.INF</code>, <code>PalmIntDomain.SUP</code>, or <code>PalmIntDomain.DOM</code>
	 */

	public void self_explain(
		final int select,
		final choco.ConstraintCollection expl) {
		switch (select) {
			case DOM :
				for (int i = this.getOriginalInf(); i <= this.getOriginalSup(); i++) {
					this.self_explain(PalmIntDomain.VAL, i, expl);
				}
				break;
			case INF :
				for (int i = this.getOriginalInf(); i <= this.getInf() - 1; i++) {
					this.self_explain(PalmIntDomain.VAL, i, expl);
				}
				break;
			case SUP :
				for (int i = this.getSup() + 1; i <= this.getOriginalSup(); i++) {
					this.self_explain(PalmIntDomain.VAL, i, expl);
				}
				break;
			default :
				Logger
					.getLogger("choco.palm")
					.warning(
						"PaLM: VAL needs another parameter in self_explain (IntVar)");
		}
	}

	/**
	 * Allows to get an explanation for a value removal from the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.VAL</code>
	 */

	public void self_explain(
		final int select,
		final int x,
		final choco.ConstraintCollection expl) {
		if (select == PalmIntDomain.VAL) {
			// Yann 2010/07/21: Equivalent code.
			// I replace call to methods with internal field  
			// accesses to gain some performances...
			// int realVal = x - this.getOriginalInf(); //this.bucket.getOffset();
			final int realVal = x - this.offset; //this.bucket.getOffset();
			ConstraintCollection expla = null;
			if (realVal >= 0
			// Yann 2010/07/21: Equivalent code.
			// I replace call to methods with internal field  
			// accesses to gain some performances...
			// && (realVal < this.getOriginalSup() - this.getOriginalInf()
					&& realVal < this.chain.length) { //this.bucket.size())) {
				expla = this.explanationOnVal[realVal];
			}
			if (expla != null) {
				expl.merge(expla);
			}
		}
		else {
			Logger
				.getLogger("choco.palm")
				.warning(
					"PaLM: INF, SUP or DOM do not need a supplementary parameter in self_explain (IntVar)");
		}
	}

	/**
	 * Updates the lower bound and posts the event.
	 */

	public boolean updateInf(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		boolean rep = false;
		for (int i = this.getInf(); i < x; i++) {
			rep |= this.removeVal(i, idx, (Explanation) e.copy());
		}
		return rep;
	}

	/**
	 * Updates the upper bound and posts the event.
	 */

	public boolean updateSup(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		boolean rep = false;
		for (int i = x + 1; i <= this.getSup(); i++) {
			rep |= this.removeVal(i, idx, (Explanation) e.copy());
		}
		return rep;
	}
}

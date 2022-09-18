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

import choco.Constraint;
import choco.Problem;
import choco.integer.IntVar;
import choco.integer.var.IntDomainVar;
import choco.palm.explain.Explanation;
import choco.palm.prop.PalmIntVarEvent;

public class PalmIntVar extends IntDomainVar {
	public PalmIntVar(
		final Problem pb,
		final int domainType,
		final int inf,
		final int sup) {
		this(pb, "", domainType, inf, sup);
	}

	public PalmIntVar(
		final Problem pb,
		final String name,
		final int domainType,
		final int inf,
		final int sup) {
		super(pb, name, domainType, inf, sup);

		// Mise a jour de l'evenement
		this.event = null; // force GC
		this.event = new PalmIntVarEvent(this);

		// Mise a jour du domaine
		this.domain = null;
		if (domainType == IntVar.LIST) {
			this.domain = new PalmBitSetIntDomain(this, inf, sup);
		}
		else {
			this.domain = new PalmIntervalIntDomain(this, inf, sup);
		}

		//this.originalInf = inf;
		//this.originalSup = sup;
		//this.explanationOnVal = new RemovalExplanation[sup - inf + 1];
		//this.valueInteger.MAX_VALUE;
		//this.bucket = null;
		//this.inf = inf;
		//this.sup = sup;
		//this.prop = null;

		/*I_BasicEvent[] basicEvts = new I_BasicEvent[]{
		    new InstInt(), // Voir si il y a moyen de faire mieux !!
		    // Par exemple, red?finir les constantes...
		    new PalmIncInf(),
		    new PalmDecSup()
		};*/
		//if (domainType == IntVar.LIST)
		//this.bucket = new BitVectorIntDomain(this, inf, sup);
	}

	// Some delagation methods...

	public int[] getAllValues() {
		return ((PalmIntDomain) this.domain).getAllValues();
	}

	public Constraint getDecisionConstraint(final int val) {
		return ((PalmIntDomain) this.domain).getDecisionConstraint(val);
	}

	public Constraint getNegDecisionConstraint(final int val) {
		return ((PalmIntDomain) this.domain).getNegDecisionConstraint(val);
	}

	public boolean instantiate(
		final int value,
		final int idx,
		final choco.ConstraintCollection e) {
		boolean change = false;
		change |= this.updateInf(value, idx, (Explanation) e.copy());
		change |= this.updateSup(value, idx, (Explanation) e.copy());
		return change;
	}

	public boolean removeVal(
		final int value,
		final int idx,
		final choco.palm.explain.Explanation e) {
		return ((PalmIntDomain) this.domain).removeVal(value, idx, e);
	}

	public void resetExplanationOnInf() {
		((PalmIntDomain) this.domain).resetExplanationOnInf();
	}

	public void resetExplanationOnSup() {
		((PalmIntDomain) this.domain).resetExplanationOnSup();
	}

	public void resetExplanationOnVal(final int value) {
		((PalmIntDomain) this.domain).resetExplanationOnVal(value);
	}

	public void restoreInf(final int newValue) {
		((PalmIntervalIntDomain) this.domain).restoreInf(newValue);
	}

	public void restoreSup(final int newValue) {
		((PalmIntervalIntDomain) this.domain).restoreSup(newValue);
	}

	public void restoreVal(final int val) {
		((PalmBitSetIntDomain) this.domain).restoreVal(val);
	}

	public void self_explain(
		final int select,
		final choco.ConstraintCollection expl) {
		((PalmIntDomain) this.domain).self_explain(select, expl);
	}

	public void self_explain(
		final int select,
		final int x,
		final choco.ConstraintCollection expl) {
		((PalmIntDomain) this.domain).self_explain(select, x, expl);
	}

	public boolean updateInf(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		return ((PalmIntDomain) this.domain).updateInf(x, idx, e);
	}

	public boolean updateSup(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		return ((PalmIntDomain) this.domain).updateSup(x, idx, e);
	}

	/*public String toString(){
	    StringBuffer str = new StringBuffer();
	    if (this.bucket != null) {
	        str.append(this.name + ":");
	        if (this.isInstantiated()) str.append(this.getValue());
	        else str.append("[" + this.bucket.size() + "]" + this.bucket);
	    } else {
	        if (this.isInstantiated()) str.append(this.name + ":" + this.getValue());
	        else {
	            if (this.getInf() > this.getSup()) str.append(this.name + ":[]");
	            else str.append(this.name + ":[" + this.getInf() + ".." + this.getSup() + "]");
	        }
	    }
	    return str.toString();
	} */

	/*  public void updateDataStructures(int select, int newValue, int oldValue) {
	  this.updateDataStructuresOnVariable(select, newValue, oldValue);
	  this.updateDataStructuresOnConstraints(select, newValue, oldValue);
	}

	public void updateDataStructuresOnVariable(int select, int newValue, int oldValue) {
	  // A redefinir si necessaire
	}

	public void updateDataStructuresOnConstraints(int select, int newValue, int oldValue) {
	  Constraint[] constraints = this.getConstraints();
	  Integer[] indices = this.getIndices();

	  for (IntIterator cit = this.event.getAllListenersButIterator(-1); cit.hasNext();) {
	    int index = cit.next();
	    ((PalmIntVarListener) constraints[index]).updateDataStructuresOnConstraint(indices[index].intValue(), select, newValue, oldValue);
	  }
	} */

	/*public void updateDataStructuresOnRestore(int select, int newValue, int oldValue) {
	  this.updateDataStructuresOnRestoreVariable(select, newValue, oldValue);
	  this.updateDataStructuresOnRestoreConstraints(select, newValue, oldValue);
	}

	public void updateDataStructuresOnRestoreVariable(int select, int newValue, int oldValue) {
	  // A redefinir si necessaire
	}

	public void updateDataStructuresOnRestoreConstraints(int select, int newValue, int oldValue) {
	//AbstractVar v = getModifiedVar();
	  Constraint[] constraints = this.getConstraints();
	  Integer[] indices = this.getIndices();

	  for (IntIterator cit = this.event.getAllListenersButIterator(-1); cit.hasNext();) {
	    int index = cit.next();
	    ((PalmIntVarListener) constraints[index]).updateDataStructuresOnRestoreConstraint(indices[index].intValue(), select, newValue, oldValue);
	  }
	}   */

	/*public int getInf() {
	    if ((this.bucket != null) && (this.needInfComputation)) {
	        this.inf = this.bucket.getInf();
	        this.needInfComputation = false;
	    }
	    return this.inf;
	}*/

	/*public int getSup() {
	    if ((this.bucket != null) && (this.needSupComputation)) {
	        this.sup = this.bucket.getSup();
	        this.needSupComputation = false;
	    }
	    return this.sup;
	} */

	// C'etait inutile !
	//	public int firstValue() {
	/*if (this.bucket != null)
	    return this.bucket.firstElement();
	else return this.inf;   */
	//        return this.getInf();
	//	}

	/*		if (this.domain instanceof BitSetIntDomain) {
				switch(select) {
					case DOM : for(int i = this.originalInf; i <= this.originalSup; i++)
						this.self_explain(VAL, i, expl);
						break;
					case INF : for(int i = this.originalInf; i <= this.getInf() - 1; i++)
						this.self_explain(VAL, i, expl);
						break;
					case SUP : for(int i = this.getSup() + 1; i <= this.originalSup; i++)
						this.self_explain(VAL, i, expl);
						break;
					default: Logger.getLogger("choco.palm").warning("PaLM: VAL needs another parameter in self_explain (IntVar)");
				}
			}
			else {
				switch(select) {
					case DOM : this.self_explain(INF, expl); this.self_explain(SUP, expl);
						break;
					case INF : expl.merge((choco.palm.explain.Explanation) this.explanationOnInf.getLast());
						break;
					case SUP : expl.merge((choco.palm.explain.Explanation) this.explanationOnSup.getLast());
						break;
					default: Logger.getLogger("choco.palm").warning("PaLM: VAL needs another parameter in self_explain (IntVar)");
				}
			}
		}  */

	/*if (select == VAL) {
	    if (this.domain instanceof BitSetIntDomain) {
	        int realVal = x - this.originalInf; //this.bucket.getOffset();
	        choco.palm.explain.Explanation expla = null;
	        if ((realVal >= 0) && (realVal < this.originalSup - this.originalInf + 1)) {
	            expla = this.explanationOnVal[realVal];
	        }
	        if (expla != null) expl.merge(expla);
	    }
	    else {
	        if (x < this.getInf())
	            expl.merge((choco.palm.explain.Explanation) this.explanationOnInf.getLast());
	        else if (x > this.getSup())
	            expl.merge((choco.palm.explain.Explanation) this.explanationOnSup.getLast());
	    }
	}
	else {
	    Logger.getLogger("choco.palm").warning("PaLM: INF, SUP or DOM do not need a supplementary parameter in self_explain (IntVar)");
	}
	}     */

	// *************
	// * Domain restoration
	// *************

	// *************
	// * Domain modification
	// *************

	/*if (this.bucket != null) {
	    boolean rep = false;
	    for (int i = this.getInf(); i < x; i++) {
	        rep |= this.removeVal(i, idx, e.copy());
	    }
	    return rep;
	} else {
	    if (this.updateInf(x, e)) {
	        ((PalmEngine) this.getProblem().getPropagationEngine()).postUpdateInf(this, idx);
	        if (x > this.sup) {
	            this.value = Integer.MAX_VALUE;
	            ((PalmEngine) this.getProblem().getPropagationEngine()).raisePalmContradiction(this);
	        }
	        return true;
	    }
	}
	return false;
	}*/

	/*private boolean updateInf(int x, choco.palm.explain.Explanation e){
	if (x > this.inf) {
	int oldValue = this.inf;
	this.self_explain(INF, e);
	this.explanationOnInf.add(e.makeIncInfExplanation(this.inf, this));
	this.inf = x;
	if (this.inf == this.sup) {
	this.value = this.inf;
	}
	this.updateDataStructures(INF, x, oldValue);
	return true;
	}
	return false;
	}       */

	/*if (this.bucket != null) {
	    boolean rep = false;
	    for (int i = x + 1; i <= this.getSup(); i++) {
	        rep |= this.removeVal(i, idx, e.copy());
	    }
	    return rep;
	} else {
	    if (this.updateSup(x, e)) {
	        ((PalmEngine) this.getProblem().getPropagationEngine()).postUpdateSup(this, idx);
	        if (x < this.inf) {
	            this.value = Integer.MAX_VALUE;
	            ((PalmEngine) this.getProblem().getPropagationEngine()).raisePalmContradiction(this);
	        }
	        return true;
	    }
	}
	return false;
	}  */

	/*private boolean updateSup(int x, choco.palm.explain.Explanation e){
	if (x < this.sup) {
	int oldValue = this.sup;
	this.self_explain(SUP, e);
	this.explanationOnSup.add(e.makeDecSupExplanation(this.sup, this));
	this.sup = x;
	if (this.inf == this.sup) {
	this.value = this.inf;
	}
	this.updateDataStructures(SUP, x, oldValue);
	return true;
	}
	return false;
	}        */

	/*if (this.bucket != null) {
	    if (this.removeVal(value, e)) {
	        ((PalmEngine) this.getProblem().getPropagationEngine()).postRemoveVal(this, value, idx);
	        if (this.bucket.size() == 0) {
	            ((PalmEngine) this.getProblem().getPropagationEngine()).raisePalmContradiction(this);
	        }
	        return true;
	    }
	    return false;
	} else {
	    if (value == this.getInf()) {
	        this.updateInf(value + 1, idx, e);
	    } else if (value == this.getSup()) {
	        this.updateSup(value - 1, idx, e);
	    }     // manque un return true ? mais on s'en fout :)
	}
	return false;
	} */

	/*public boolean removeVal(int value, choco.palm.explain.Explanation e){
	if (this.bucket.containsValInDomain(value)) {
	this.explanationOnVal[value - this.originalInf] = e.makeRemovalExplanation(value, this);
	this.bucket.removeDomainVal(value);
	if (value == this.inf) {
	this.needInfComputation = true;
	}
	if (value == this.sup) {
	this.needSupComputation = true;
	}
	if (this.bucket.size() == 1) {
	this.value = this.bucket.firstElement();
	} else if (this.bucket.size() == 0) {
	this.value = Integer.MAX_VALUE;
	}
	this.updateDataStructures(VAL, value, 0);
	return true;
	}
	return false;
	}  */

	/*	public void setBucket(I_IntDomain bucket) {
			this.bucket = bucket;
		}*/

	/*public BitVectorIntDomain getBucket() {
	    return bucket;
	}*/

	/*public boolean hasBucket() {
	  return this.hasEnumeratedDomain();
	} */

	/* public int getDomainSize() {
	   if (this.hasBucket())
	       return bucket.size();
	   else return this.getSup() - this.getInf() + 1;
	   return domain.size();
	 } */

	/**
	 * Checks if the variables is instantiated to any value.
	 */

	/*public boolean isInstantiated() {
	    return (value != Integer.MAX_VALUE);
	}    */

	// Doit ?tre r??crit pour prendre en compte le bon bucket !
	/*public boolean canBeInstantiatedTo(int x) {
	    return (getInf() <= x &&
	            x <= getSup() &&
	            (bucket == null || bucket.containsValInDomain(x)));
	}   */

	//public boolean isInstantiatedTo(int val) {
	//    return val == value;
	//}
	// Normalement inutile de la red?finir car getValue est utilis?e...

	/**
	 * Gets the value of the variable if instantiated.
	 */

	/*public int getValue() {
	    return value;
	} */

	//  public Constraint assign(int val) {
	//    return this.getDecisionConstraint(val);
	/*    if (this.hasEnumeratedDomain()) {
	      Constraint constraint = this.instantiationConstraints[val - this.originalInf];
	      if (constraint != null) {
	        return constraint;
	      } else {
	        constraint = new PalmAssignment(this, val);
	        this.instantiationConstraints[val - this.originalInf] = constraint;
	        this.negInstantiationConstraints[val - this.originalInf] = new PalmNotEqualXC(this, val);
	        return constraint;
	      }
	    } else {
	      Constraint constraint = (Constraint) this.enumerationConstraints.get(Integer.valueOf(val - this.originalInf));
	      if (constraint != null) {
	        return constraint;
	      } else {
	        constraint = new PalmAssignment(this, val);
	        this.enumerationConstraints.put(Integer.valueOf(val - this.originalInf), constraint);
	        this.negEnumerationConstraints.put(Integer.valueOf(val - this.originalInf), new PalmNotEqualXC(this, val));
	        return constraint;
	      }
	    } */
	//  }
}

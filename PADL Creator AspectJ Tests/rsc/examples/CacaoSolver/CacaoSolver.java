import java.util.Vector;
import java.util.Enumeration;

/* ----------------------------------------------------------------------------
 * Cacao v1.0
 * --------------------------------------------------------------------------
 * Copyright (c) 2004, Remi Douence; OBASCO group; EMN/INRIA; France
 * Copyright (c) 2004, Narendra Jussien; PPC group; EMN; France
 * All rights reserved.
 * This software is licensed under the BSD license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the
 * distribution.  Neither the name of the OBASCO group EMN/INRIA, nor
 * the name of the PPC group EMN, nor the names of its contributors
 * may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * --------------------------------------------------------------------------
 */

// Cacao a tiny constraint solver in Java

class Removal {

   
}

class Relation {

}

class Inverse extends Relation {

}

class Problem {


}

class Pair {

}

class Set {

 
}

class CacaoSolver {

 
}

// aspects for explanations and backtrack in aspectj

aspect AspectBacktrack {

    // remember the problem

    Problem problem;

    pointcut newProblem():
	call(Problem.new(..));

    after() returning(Problem problem): newProblem() {
	this.problem = problem;
    }

    // repair in order to backtrack

    pointcut callRepair():
	call(void Problem.repair());

    void around() throws Exception: callRepair() {
	repair();
    }

    void repair() throws Exception {
	Set contradictionExplanation = new Set();
	Enumeration enum = problem.lastModifiedVariable.originalDomain.elements();
	while (enum.hasMoreElements()) {
	    Value value = (Value)(enum.nextElement());
	    contradictionExplanation.union(value.explanation);
	}
	if (contradictionExplanation.isEmpty()) {
	    // no solution
	    throw new Exception();
	} else {
	    // select the last (decision) constraint in the explanation
	    Enumeration enumExplain = contradictionExplanation.elements();
	    Relation decisionToUndo = (Relation)(enumExplain.nextElement());
	    while (enumExplain.hasMoreElements()) {
		Relation relation = (Relation)(enumExplain.nextElement());
		if (relation.timeStamp > decisionToUndo.timeStamp) {
		    decisionToUndo = relation;
		}
	    }
	    // remove this constraint from the problem
	    problem.originalRelations.remove(decisionToUndo);
	    // remove past effects
	    Enumeration enumVar = problem.variables.elements();
	    while (enumVar.hasMoreElements()) {
		Variable variable = (Variable)(enumVar.nextElement());
		Enumeration enumVal = variable.originalDomain.elements();
		while (enumVal.hasMoreElements()) {
		    Value value = (Value)(enumVal.nextElement());
		    if (value.explanation.member(decisionToUndo)) {
			// restore the value back in the domain
			variable.domain.add(value);
			// empty the explanation
			value.explanation = new Set(); 
			// prepare propagation
			problem.relationQueue.union(problem.relations(variable));
		    } 
		}		
	    }
	    try {
		problem.propagate();
	    } catch (Exception e) {
		repair();
	    }
	    contradictionExplanation.remove(decisionToUndo);
	    Enumeration enumRel = contradictionExplanation.elements();
	    boolean decisionsStillValid = true;
	    while (enumRel.hasMoreElements()) {
		Relation relation = (Relation)(enumRel.nextElement());
		decisionsStillValid = decisionsStillValid 
		    && problem.originalRelations.member(relation);
	    }
	    if (decisionsStillValid) {
		try {
		    // remove a value (equivalent to a non-decision relation)
		    Value value = (Value)(((Pair)(decisionToUndo.pairs.get())).fst);
		    decisionToUndo.var1.domain.remove(value);
		    // set explanation
		    value.explanation = contradictionExplanation;
		    // prepare propagation
		    problem.relationQueue.union(problem.relations(problem.lastModifiedVariable));
		    problem.propagate();
		} catch (Exception e) {
		    repair();
		}
	    } 
	}
    }
}

aspect AspectBuildSupports {

    // introductions
    Set Value.supports = new Set(); 

    // build direct access to in relation values
    pointcut inRelation(Relation relation, Value value1, Value value2):
	target(relation) &&
	args(value1, value2) &&
	call(Relation Relation.add(Value, Value));

    before(Relation relation, Value value1, Value value2): inRelation(relation, value1, value2) {
	value1.supports.add(new Support(relation, value2));
	// inverse supports built by inRelationInverse
    }

    // build support for reverse relation
    pointcut inRelationInverse():
	call(Inverse.new(Relation));

    after() returning(Inverse inverse): inRelationInverse() {
	Enumeration enum = inverse.pairs.elements();
	while (enum.hasMoreElements()) {
	    Pair pair = (Pair)(enum.nextElement());
	    Value val1 = (Value)(pair.fst);
	    Value val2 = (Value)(pair.snd);
	    val2.supports.add(new Support(inverse, val1));
	}
    }
}

class Support {

    Relation relation;
    Value value;

    Support(Relation relation, Value value) {
	this.relation = relation;
	this.value = value;
    }
}

aspect AspectExplanation {

    // remember the problem

    Problem problem;

    pointcut newProblem():
	call(Problem.new(..));

    after() returning(Problem problem): newProblem() {
	this.problem = problem;
    }

    // compute explanation

    // introductions

    Set Value.explanation = new Set();

    boolean Relation.isDecision() {
	return var1.equals(var2);
    }

    Variable Problem.lastModifiedVariable;

    pointcut removal(Relation r, Variable variable, Value value): 
	cflow(target(r) && call(Removal revise())) 
	&& args(variable, value) 
	&& call(Removal.new(Variable, Value));

    before(Relation relation, Variable variable, Value value): 
	removal(relation, variable, value) {
	// remember the last variable 
	this.problem.lastModifiedVariable = variable;
	// a set of decision constraints is now attached to its value !!! 
	if (relation.isDecision()) {
	    value.explanation.add(relation);
	}
	// and the explanations too
	Enumeration enum = value.supports.elements();
	while (enum.hasMoreElements()) {
	    Support support = (Support)(enum.nextElement());
	    if (support.relation == relation) 
		value.explanation.union(support.value.explanation);
	}
    }
}

aspect AspectOriginalDomain {

    // introduction
    Set Variable.originalDomain = new Set();

    pointcut newVariable(): call(Variable.new(String, Set));

    after() returning(Variable variable): newVariable() {
	variable.originalDomain = variable.domain.copy();
    }
}

aspect AspectTimeStampRelation {

    static int timeStampGenerator = 0;

    // introductions
    int Relation.timeStamp;

    // add a time stamp to relations

    pointcut newRelation():
	call(Relation.new(..));

    after() returning(Relation relation): newRelation() {
	relation.timeStamp = timeStampGenerator++;
    }
}

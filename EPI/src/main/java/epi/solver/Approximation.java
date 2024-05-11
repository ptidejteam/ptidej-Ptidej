/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package epi.solver;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author OlivierK
 *
 */
public class Approximation {

	private Hashtable approximationList = new Hashtable();

	public Approximation() {
		this.setNoApproximation();
	}
	public Approximation(final Hashtable anApproximationList) {
		this.approximationList = anApproximationList;
	}

	public void addElement(final String relationship, final String element) {
		((List) this.approximationList.get(relationship)).add(element);
	}

	public Hashtable getApproximationList() {
		return this.approximationList;
	}
	public List getApproximationList(final String relationship) {
		if (this.approximationList.containsKey(relationship)) {
			return (List) this.approximationList.get(relationship);
		}
		else {
			return null;
		}
	}
	public void setApproximationList(final Hashtable anApproximationList) {
		this.approximationList = anApproximationList;
	}

	public void setApproximationList(final String relationship, final List aList) {
		this.approximationList.put(relationship, aList);
	}

	//TODO: A refaire!!
	public void setDefaultApproximation() {
		final ArrayList temp1 = new ArrayList();
		temp1.add("containerComposition");
		temp1.add("containerAggregation");
		temp1.add("composition");
		temp1.add("aggregation");
		this.approximationList.put("containerComposition", temp1);
		final ArrayList temp2 = new ArrayList();
		temp2.add("containerAggregation");
		temp2.add("aggregation");
		this.approximationList.put("containerAggregation", temp2);
		final ArrayList temp3 = new ArrayList();
		temp3.add("composition");
		temp3.add("aggregation");
		this.approximationList.put("composition", temp3);
		final ArrayList temp4 = new ArrayList();
		temp4.add("aggregation");
		this.approximationList.put("aggregation", temp4);
		final ArrayList temp5 = new ArrayList();
		temp5.add("association");
		this.approximationList.put("association", temp5);
		final ArrayList temp6 = new ArrayList();
		temp6.add("useRelationship");
		this.approximationList.put("useRelationship", temp6);
		final ArrayList temp7 = new ArrayList();
		temp7.add("inheritance");
		temp7.add("pathInheritance");
		temp7.add("null");
		this.approximationList.put("inheritance", temp7);
		final ArrayList temp8 = new ArrayList();
		temp8.add("creation");
		this.approximationList.put("creation", temp8);
		final ArrayList temp9 = new ArrayList();
		temp9.add("inheritance");
		temp9.add("pathInheritance");
		this.approximationList.put("inheritance2", temp9);
	}
	public void setNoApproximation() {
		final ArrayList temp1 = new ArrayList();
		temp1.add("containerComposition");
		this.approximationList.put("containerComposition", temp1);

		final ArrayList temp2 = new ArrayList();
		temp2.add("containerAggregation");
		this.approximationList.put("containerAggregation", temp2);

		final ArrayList temp3 = new ArrayList();
		temp3.add("composition");
		this.approximationList.put("composition", temp3);

		final ArrayList temp4 = new ArrayList();
		temp4.add("aggregation");
		this.approximationList.put("aggregation", temp4);

		final ArrayList temp5 = new ArrayList();
		temp5.add("association");
		this.approximationList.put("association", temp5);

		final ArrayList temp6 = new ArrayList();
		temp6.add("useRelationship");
		this.approximationList.put("useRelationship", temp6);

		final ArrayList temp7 = new ArrayList();
		temp7.add("inheritance");
		this.approximationList.put("inheritance", temp7);

		final ArrayList temp9 = new ArrayList();
		temp9.add("inheritance2");
		this.approximationList.put("inheritance2", temp9);

		final ArrayList temp8 = new ArrayList();
		temp8.add("creation");
		this.approximationList.put("creation", temp8);
	}
}

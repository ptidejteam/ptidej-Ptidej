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
package mendel.family;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import mendel.Util;
import mendel.family.RoleTag.Tag;
import mendel.model.JClassEntity;
import mendel.model.JClassEntity.Group;

import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.Statistics;

public class Family { // works as a data holder
	
	private JClassEntity parent;
	
	private Map<String,Object> datafields;
	
	private Map<Tag, Set<JClassEntity>> familySets;
	
//	private Set<String> subclassingInterface;
	
//	private Prototype prototype;
	

	public Family(JClassEntity parent) {
		this.parent = parent;
		this.datafields = new HashMap<String, Object>();
		childrenStats();
	}
	
	/**
	 * 
	 */
	public void childrenStats() {
		List<JClassEntity> children = children();
		List<Number> noms = new Vector<Number>();
		for (JClassEntity classEntity : children) {
			noms.add(classEntity.count(Group.LOCAL));
		}
		BoxAndWhiskerItem calcul = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(noms);
		setData("median NOM", calcul.getMedian().floatValue());
		setData("IQR NOM", calcul.getQ3().floatValue() - calcul.getQ1().floatValue());

		setData("mean NOM", Statistics.calculateMean(noms));
		setData("dev NOM", Statistics.getStdDev(noms.toArray(new Number[0])));
	}
	
	public Set<String> dataKeys() {
		return this.datafields.keySet();
	}

	public Object getData(String key) {
		return this.datafields.get(key);
	}
	
	public void setData(String key, Object data) {
		this.datafields.put(key, data);
	}
	
	public JClassEntity parent() {
		return this.parent;
	}
	
	public List<JClassEntity> children() {
		return this.parent.getChildren();
	}
	
	public int familySize() {
		return this.parent.familySize();
	}
	
	public void setFamilySets(Map<Tag, Set<JClassEntity>> tagSet) {
		this.familySets = tagSet;
	}
	
	public Map<Tag, Set<JClassEntity>> familySets() {
		return this.familySets;
	}
	
	public void setBehaviorTag(Tag behaviorTag) {
		setData("behaviorTag", behaviorTag);
	}
	
	public Tag behaviorTag() {
		return (Tag) getData("behaviorTag");
	}
	
	public void setHomogeneity(float h) {
		setData("tagHomogeneity", h);
	}
	
	public float homogeneity() {
		return ((Float) getData("tagHomogeneity")).floatValue();
	}
	
	public String toString(boolean verbose) {
		StringBuffer tos = new StringBuffer(toString());
		tos.append(", " + parent().inheritanceDepth());
		tos.append((familySize()==1)? ", SINGLE":"");
		if( verbose ) {
			for (Tag tag : this.familySets.keySet()) {
				if( !this.familySets.get(tag).isEmpty() ) {
					tos.append("\n" + tag + ": ");
					for (JClassEntity entity : this.familySets.get(tag)) {
						tos.append(entity.getBaseName() + " ");
					}				
				}
			}
		}
		return tos.toString();
	}

	public String toString() {
		List<Object> data = Arrays.asList(new Object[] {
				"Family", 
				parent().getBaseName(), 
				familySize(), 
				getData("median NOM"),
				getData("IQR NOM"),
				behaviorTag(),
				homogeneity()
		});
		return Util.join(data, ", ", new StringBuffer()); 
	}

	public String toStringHeaders() {
		return "Family, parent name, NoC, med NoM, IQR NoM, SubBeh Tag, hgi, [DIT]";
	}

	public Prototype getPrototype(String key) {
		return (Prototype) getData(key);
	}

	public void setPrototype(Prototype prototype, String key) {
		setData(key, prototype);
	}
	
}

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
package pom.helper.xml;

import java.util.HashMap;

public final class MetricPropMap {

	private static MetricPropMap INSTANCE = null;

	private final HashMap map = new HashMap();

	private MetricPropMap() {
		//Set metrics prop
		//TODO: set Max and Min for all metrics
		this.map.put("CP", new MetricProp("CP", "none", "double"));
		this.map
			.put(
				"WMC1",
				new MetricProp(
					"WMC1",
					"Computes the weight of an entity considering the complexity of a method to be unity",
					"double",
					20,
					0));
		this.map.put("RFP", new MetricProp("RFP", "none", "double"));
		this.map.put("CLD", new MetricProp("CLD", "none", "double", 10, 0));
		this.map.put("PP", new MetricProp("PP", "none", "double"));
		this.map.put("NOC", new MetricProp(
			"NOC",
			"Returns the NOC (Number Of Children) of an entity",
			"double",
			15,
			0));
		this.map.put("CBOin", new MetricProp("CBOin", "none", "double"));
		this.map.put("CBOout", new MetricProp("CBOout", "none", "double"));
		this.map.put("NCM", new MetricProp("NCM", "none", "double", 100, 0));
		this.map.put("AID", new MetricProp("AID", "none", "double", 8, 0));
		this.map.put("connectivity", new MetricProp(
			"connectivity",
			"none",
			"double",
			3.5,
			0));
		this.map.put("NOA", new MetricProp("NOA", "none", "double", 15, 0));
		this.map
			.put(
				"WMC",
				new MetricProp(
					"WMC",
					"Computes the weight of an entity by computing the number of method invocations in each method",
					"double",
					150,
					0));
		this.map.put("EIP", new MetricProp("EIP", "none", "double"));
		this.map.put("SIX", new MetricProp(
			"SIX",
			"Returns the SIX (Specialisation IndeX) of an entity",
			"double"));
		this.map.put("REIP", new MetricProp("REIP", "none", "double"));
		this.map.put("LCOM5", new MetricProp("LCOM5", "none", "double", 2, 0));
		this.map.put("CBO", new MetricProp("CBO", "none", "double", 50, 0));
		this.map.put("DCMEC", new MetricProp("DCMEC", "none", "double", 25, 0));
		this.map.put("NOD", new MetricProp(
			"NOD",
			"Returns the NOD (Number Of Descendents) of an entity",
			"double",
			25,
			0));
		this.map.put("NCP", new MetricProp("NCP", "none", "double"));
		this.map.put("ICHClass", new MetricProp(
			"ICHClass",
			"none",
			"double",
			25,
			0));
		this.map.put("PIIR", new MetricProp("PIIR", "none", "double"));
		this.map.put("RRTP", new MetricProp("RRTP", "none", "double"));
		this.map.put("LCOM2", new MetricProp("LCOM2", "none", "double"));
		this.map.put("ACAIC", new MetricProp("ACAIC", "none", "double", 25, 0));
		this.map.put("DCAEC", new MetricProp("DCAEC", "none", "double", 25, 0));
		this.map.put("LCOM1", new MetricProp("LCOM1", "none", "double"));
		this.map.put("DIT", new MetricProp(
			"DIT",
			"Returns the DIT (Depth of inheritance tree) of the entity",
			"double",
			10,
			0));
		this.map.put("cohesionAttributes", new MetricProp(
			"cohesionAttributes",
			"none",
			"double",
			1,
			0));
		this.map.put("RTP", new MetricProp("RTP", "none", "double"));
		this.map.put("NMA", new MetricProp("NMA", "none", "double", 100, 0));
		this.map.put("NMI", new MetricProp("NMI", "none", "double", 100, 0));
		this.map.put("ACMIC", new MetricProp("ACMIC", "none", "double", 25, 0));
		this.map.put("NMO", new MetricProp(
			"NMO",
			"Returns the NMO (Number of Methods Overridden) of an entity",
			"double",
			50,
			0));
		this.map.put("RRFP", new MetricProp("RRFP", "none", "double"));
		this.map.put("NAD", new MetricProp(
			"NAD",
			"Number of Attributes Declared",
			"double",
			5,
			0));
		this.map.put("EIC", new MetricProp("EIC", "none", "double"));
		this.map.put("NOP", new MetricProp(
			"NOP",
			"Returns the NOP (Number Of Parents) of an entity",
			"double",
			50,
			0));
		this.map.put("RPII", new MetricProp("RPII", "none", "double"));
		this.map.put("NMD", new MetricProp(
			"NMD",
			"Number of Methods Declared",
			"double",
			5,
			0));
		this.map.put("Dummy", new MetricProp(
			"Dummy",
			"Dummy metric - use to sort under VERSO",
			"double"));
	}

	public static MetricPropMap getMetricPropMap() {
		if (MetricPropMap.INSTANCE == null)
			MetricPropMap.INSTANCE = new MetricPropMap();
		return MetricPropMap.INSTANCE;
	}

	public MetricProp getPropForMetricName(final String metricName) {
		return (MetricProp) this.map.get(metricName);
	}
}

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
package mendel.part.output;

import java.util.Map;
import java.util.Properties;

import mendel.Driver;
import mendel.IRepository;
import mendel.Util;
import mendel.metric.entity.EntityName;
import mendel.model.IEntity;
import mendel.model.JClassEntity;
import mendel.model.JInterfaceEntity;

/**
 * Suffix: dot.
 * 
 * Initializating Properties:
 * - graphname: name of graph (dot style, i.e. no space, alphanum characters) - default: projectname
 * - showInterfacesRelations: true = show interfaces and interface relationships with classes,
 * default false
 * - tags: list of tags represented by colors (paired with colors)
 * - colors: list of colors representing tags (paired with tags)
 * - see FileOutput for other properties
 * 
 * Input: Map<metric name, metric result>
 * Output: StringBuffer
 * 
 * 
 * TODO: factorization with DotOutput. We have a lack of hook in startMe for example: must call startme 
 * but cant redefine the graph file header.
 * 
 * @author deniersi
 *
 */
public class PointDotOutput extends FileOutput {

	private IRepository repository;
	
	private String graphname;

	private boolean showInterfacesRelations;

	private String[] tags;
	
	private String[] colors;
	
	public PointDotOutput() {
		setSuffix(".dot");
	}
	
	
	/* (non-Javadoc)
	 * @see mendel.output.FileOutput#initialize(java.util.Properties)
	 */
	@Override
	public void initialize(Properties properties) {
		super.initialize(properties);
		this.graphname = properties.getProperty("graphname");
		// false by default
		this.showInterfacesRelations = Boolean.valueOf(properties.getProperty("showInterfacesRelations")).booleanValue();
		this.tags = Util.extractValues(properties, "tags");
		this.colors = Util.extractValues(properties, "colors");
	}

	
	@Override
	public void initialize(Driver driver) {
		super.initialize(driver);
		this.repository = getDriver().getProject().getRepository();
		if( this.graphname==null ) {
			this.graphname = getDriver().getProject().getProjectname();	
		}		
	}


	@Override
	public void startMe() {
		super.startMe();
		StringBuffer graph = new StringBuffer("digraph " + this.graphname + " {\n");
		// default node color for nodes created only by relations (i.e. ghost)
		graph.append("node [fontname=Helvetica, shape=point, color=grey, fontcolor=grey, fixedsize=true]\n");
		write(graph);
	}

	@Override
	public void endMe() {
		write("}");
		super.endMe();
	}
	
	@Override
	public Object compute(Object data) {
		Map record = ((Map) data);
		String qName = (String) record.get(new EntityName().getName());
		JClassEntity entity = (JClassEntity) this.repository.getEntity(qName);

//		IEntity entity = (IEntity) data;
		StringBuffer graph = new StringBuffer();
		addNode(entity, record, graph);
		addRelations(entity, graph);
		return super.compute(graph);
	}


	/*
	 * Graph building methods
	 */

	public void addNode(IEntity entity, Map record, StringBuffer buffer) {
		buffer.append(getNodeId(entity));
		String color = nodeColor(record);
		buffer.append(buildPropertiesList(new String[] {
				"label=\"" + getNodeId(entity) + "\"", "color=\"" + color + "\"",
				"fontcolor=\"" + color + "\"" }));
	}

	public String getNodeId(IEntity entity) {
		return convertName(entity.getEntityName());
	}

	public String nodeColor(Map record) {
		for (int i = 0; i < this.tags.length; i++) {
			if( record.get(this.tags[i]).equals("Y") ) {
				return this.colors[i]; // first match returns
			}
		}
		return "black";
	}

	public StringBuffer buildMethodSets(IEntity entity) {
		if (entity instanceof JClassEntity) // code smell
			return buildMethodSets((JClassEntity) entity);
		else
			return buildMethodSets((JInterfaceEntity) entity);
	}

	public StringBuffer buildMethodSets(JClassEntity entity) {
		return new StringBuffer();
	}

	public StringBuffer buildMethodSets(JInterfaceEntity entity) {
		return new StringBuffer();
	}

	public void addRelations(IEntity entity, StringBuffer buffer) {
		if (entity instanceof JClassEntity) { // code smell...
			addInheritanceRelations((JClassEntity) entity, buffer);
		}
		if (this.showInterfacesRelations)
			addImplementationRelations(entity, buffer);
	}

	public void addInheritanceRelations(JClassEntity entity, StringBuffer buffer) {
		if (!entity.isRootClass()) {
			if( !entity.getSuperClass().isRootClass() ) {
				buffer.append(addEdge(getNodeId(entity), getNodeId(entity
						.getSuperClass()), "none"));				
			}
		}
	}

	public void addImplementationRelations(IEntity entity, StringBuffer buffer) {
		String startNode = getNodeId(entity);
		for (JInterfaceEntity superInt : entity.getSuperInterfaces()) {
			buffer.append(addEdge(startNode, getNodeId(superInt), "none"));
		}
	}

	/*
	 * Helper methods for Dot
	 */

	public StringBuffer addEdge(String startNode, String endNode,
			String arrowStyle) {
		StringBuffer buf = new StringBuffer("\t");
		buf.append(startNode).append(" -> ").append(endNode);
		buf.append(" [arrowhead=").append(arrowStyle).append("]\n");
		return buf;
	}

	public String convertName(final String name) {
		String converted = name.replace('.', '_');
		converted = converted.replace('$', '_');
		converted = converted.replaceAll(" ", "\\ ");
		return converted;
	}

	// public String convertSpaces(final String name) {
	// return name.replaceAll(" ", "\\ ");
	// }

	public StringBuffer buildPropertiesList(String[] properties) {
		StringBuffer buf = new StringBuffer(" [");
		if (properties.length > 0) {
			for (String prop : properties)
				buf.append(prop + ", ");
			buf.delete(buf.length() - 2, buf.length() - 1);
			// delete last two chars, that is the last ', '
		}
		buf.append("]\n");
		return buf;
	}

}

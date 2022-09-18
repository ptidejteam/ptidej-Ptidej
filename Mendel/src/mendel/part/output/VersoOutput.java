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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import mendel.Driver;
import mendel.IRepository;
import mendel.Util;
import mendel.metric.entity.EntityName;
import mendel.model.IEntity;
import mendel.model.JClassEntity;

import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerItem;


/**
 * Suffix: xml.
 * 
 * Initializating Properties:
 * - see superclass for properties
 * 
 * Input: Map
 * Output: StringBuffer
 * 
 * @author Simon Denier
 * @since Feb 15, 2008
 * 
 */
public class VersoOutput extends FileOutput {

	private IRepository repository;
	
	public VersoOutput() {
		setSuffix(".xml");
	}
	
	
	@Override
	public void initialize(Driver driver) {
		super.initialize(driver);
		this.repository = getDriver().getProject().getRepository();
	}



	@Override
	public void startMe() {
		super.startMe();
		write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		write("<DssDocument>");
		buildAssociations();
	}
	
	public void buildAssociations() {
		write(xmlEnclose("Associations", computeAssociations(new StringBuffer("\n"))));
	}
	
	public StringBuffer computeAssociations(StringBuffer buffer) {
		buffer.append(association("NVS", "Blue to Red")).append("\n");
		buffer.append(association("Local G Count", "height")).append("\n");
		buffer.append(association("NVI", "Twist")).append("\n");
		buffer.append(association("NVS", "sort")).append("\n");
		return buffer;
	}
	
	public StringBuffer association(String metric, String graph) {
		StringBuffer buffer = xmlAppend("metric", metric, new StringBuffer());
		xmlAppend("graph", graph, buffer);
		return xmlEnclose("asso", buffer);
	}


	@Override
	public void endMe() {
		write("</Objects>");
		write("</DssDocument>");
		super.endMe();
	}

	
	@Override
	public void run() {
		System.err.println("Warning! Should not run this tool until all input data is collected.");
		super.run();
	}

	
	/* (non-Javadoc)
	 * @see mendel.part.AbstractPart#computeAll()
	 */
	@Override
	public void computeAll() {
		StringBuffer buffer = computeDescriptions();
		xmlEnclose("ObjectProperties", buffer);
		buffer.append("\n<RelationProperties />\n");
		xmlEnclose("ObjRelStructure", buffer);
		buffer.append("\n<Objects>");
		write(buffer);
		super.computeAll();
	}


	public StringBuffer computeDescriptions() {
//		String[] minMax = findMinMax(getData(), "NVS");
		String limit = findUpperLimit(getData(), "NVS");
		StringBuffer buffer = propDescription("NVS", "double", "None", "0", limit);
//		minMax = findMinMax(getData(), "Local G Count");
		limit = findUpperLimit(getData(), "Local G Count");
		buffer.append(propDescription("Local G Count", "integer", "None", "0", limit));
//		minMax = findMinMax(getData(), "NVI");
		limit = findUpperLimit(getData(), "NVI");
		buffer.append(propDescription("NVI", "double", "None", "0", limit));
		return buffer;
	}
	
	
	/**
	 * @param data
	 * @param string
	 * @return
	 */
	public String[] findMinMax(Collection data, String key) {
		Double min = 0.0;
		Double max = 0.0;
		for (Iterator it = data.iterator(); it.hasNext();) {
			Map record = (Map) it.next();
			Double value = Double.valueOf((String) record.get(key));
			min = Math.min(value, min);
			max = Math.max(value, max);
		}
		return new String[] { min.toString(), max.toString() };
	}
	
	public String findUpperLimit(Collection data, String key) {
		String[] selectedData = (String[]) Util.selectDataByKey(data, key).toArray(new String[0]);
		Number[] numbers = Util.convertToNumbers(selectedData);
		BoxAndWhiskerItem stats = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(Arrays.asList(numbers));
		double q3 = stats.getQ3().doubleValue();
		double limit = q3 + 1.5 * (q3 - stats.getQ1().doubleValue());
//		double threshold = Statistics.calculateMean(numbers) + Statistics.getStdDev(numbers);
		return Double.valueOf(limit).toString();
	}


	public StringBuffer propDescription(String name, String type, String comment, String min, String max) {
		StringBuffer props = xmlAppend("PropName", name, new StringBuffer());
		xmlAppend("ProType", type, props);
		xmlAppend("ProComments", comment, props);
		xmlAppend("PropMin", min, props);
		xmlAppend("PropMax", max, props);
		xmlEnclose("Prop", props);
		return props.append("\n");
	}


	@Override
	public Object compute(Object data) {
		Map record = (Map) data;
		StringBuffer result = buildEntity(record);
		buildProperties(record, result);
		xmlEnclose("Object", result);
		return super.compute(result);
	}


	public StringBuffer buildEntity(Map record) {
		JClassEntity currentEntity = getEntityFromRecord(record);
		return buildEntity(currentEntity);
	}

	public JClassEntity getEntityFromRecord(Map record) {
		String qName = (String) record.get(new EntityName().getName());
		return (JClassEntity) this.repository.getEntity(qName);
	}


	public StringBuffer buildEntity(JClassEntity currentEntity) {
		StringBuffer result = new StringBuffer();
		
		xmlAppend("Name", getObjectId(currentEntity), result);
		xmlAppend("Type", getObjectType(currentEntity), result);
		
		StringBuffer sint = computeEntityList("Interface", currentEntity.getSuperInterfaces(), new StringBuffer());
		xmlAppend("ImplementedInterfaces", sint, result);
		xmlAppend("Parents", xmlEnclose("Parent", new StringBuffer(getObjectId(currentEntity.getSuperClass()))), result);
		StringBuffer subs = computeEntityList("Child", currentEntity.getChildren(), new StringBuffer());
		xmlAppend("Children", subs, result);
		xmlAppend("UmlTargets", "", result);
		return result;
	}


	public String getObjectId(IEntity entity) {
		return entity.getEntityName();
	}


	public String getObjectType(IEntity entity) {
		return "Class";
	}
	

	public StringBuffer computeEntityList(String tag, Collection entities, StringBuffer result) {
		for (Iterator it = entities.iterator(); it.hasNext();) {
			xmlAppend(tag, getObjectId((IEntity) it.next()), result);
		}
		return result;
	}

	/**
	 * @param record
	 * @param result
	 * @return 
	 */
	public StringBuffer buildProperties(Map record, StringBuffer result) {
		return result.append(xmlEnclose("ObjectProperties", computeProperties(record, new StringBuffer())));
	}


	public StringBuffer computeProperties(Map record, StringBuffer props) {
		props.append(property(record, "NVI"));
		props.append(property(record, "NVS"));
		props.append(property(record, "Local G Count"));
		return props;
	}

	public StringBuffer property(String metric, String value) {
		return xmlEnclose("Prop", xmlAppend("ProValue", value, xmlAppend("PropName", metric, new StringBuffer())));
	}
	
	public StringBuffer property(Map record, String name) {
		return property(name, record.get(name).toString());
//		return xmlEnclose("Prop", xmlAppend("ProValue", record.get(name).toString(), xmlAppend("PropName", name, new StringBuffer())));
	}


	public StringBuffer xmlEnclose(String tag, StringBuffer content) {
		content.insert(0, "<" + tag + ">");
		content.append("</" + tag + ">");
		return content;
	}
	
	public StringBuffer xmlAppend(String tag, String value, StringBuffer content) {
		return xmlAppend(tag, new StringBuffer(value), content);
	}
	
	public StringBuffer xmlAppend(String tag, StringBuffer value, StringBuffer content) {
		return content.append(xmlEnclose(tag, value));
	}
		
}

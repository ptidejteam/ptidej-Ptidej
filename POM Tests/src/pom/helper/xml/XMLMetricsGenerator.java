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
/**
 * 
 */
package pom.helper.xml;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import padl.kernel.ICodeLevelModel;
import pom.metrics.IMetric;
import pom.metrics.MetricsRepository;

/**
 * Based on Karim Dhambri's PomMetricsXmlGenerator.
 * @author Jean-Yves Guyomarc'h
 * @since 2006/02/27
 *
 */
public final class XMLMetricsGenerator {

	public static void generateXMLOutput(
		final ICodeLevelModel codeLevelModel,
		final PrintWriter out,
		final String xslSheet) {

		XMLMetricsGenerator.generateXMLOutput(
			codeLevelModel,
			out,
			xslSheet,
			MetricsRepository.getInstance().getUnaryMetrics(),
			false,
			false);
	}

	public static void generateXMLOutput(
		final ICodeLevelModel codeLevelModel,
		final PrintWriter out,
		final String xslSheet,
		final IMetric[] someMetrics) {

		XMLMetricsGenerator.generateXMLOutput(
			codeLevelModel,
			out,
			xslSheet,
			someMetrics,
			false,
			false);
	}

	public static void generateXMLOutput(
		final ICodeLevelModel codeLevelModel,
		final PrintWriter out,
		final String xslSheet,
		final IMetric[] someMetrics,
		final boolean considerAspects,
		final boolean considerGhosts) {

		//	final Repository metrics = Repository
		//		.getInstance(codeLevelModel);

		final Iterator mainIterator =
			codeLevelModel.getIteratorOnConstituents();

		out.println(XMLMetricsGenerator.xmlHeader(
			codeLevelModel.getDisplayName(),
			someMetrics,
			xslSheet));

		while (mainIterator.hasNext()) {
			//	final IConstituentOfModel entityConstituent = (IConstituentOfModel) mainIterator
			//		.next();

			//			if (entityConstituent instanceof IAspect) {
			//				if (considerAspects)
			//					XMLMetricsGenerator.computeEntity(
			//						(IEntity) entityConstituent,
			//						out,
			//						metrics,
			//						listOfMetrics);
			//			}
			//			else {
			//				if (entityConstituent instanceof IGhost) {
			//					if (considerGhosts)
			//						XMLMetricsGenerator.computeEntity(
			//							(IEntity) entityConstituent,
			//							out,
			//							metrics,
			//							listOfMetrics);
			//				}
			//				else
			//					XMLMetricsGenerator.computeEntity(
			//						(IEntity) entityConstituent,
			//						out,
			//						metrics,
			//						listOfMetrics);
			//
			//			}
		}

		out.print("\t</Objects>\n</DssDocument>");

		out.close();
	}

	public static void generateXMLOutput(
		final String codeLevelModelName,
		final List /*MetricResult*/results,
		final PrintWriter out,
		final String xslSheet,
		final IMetric[] someMetrics) {

		out.println(XMLMetricsGenerator.xmlHeader(
			codeLevelModelName,
			someMetrics,
			xslSheet));

		final Iterator iterResults = results.iterator();
		while (iterResults.hasNext()) {
			final MetricResult entityResult = (MetricResult) iterResults.next();
			XMLMetricsGenerator.printEntityResult(
				entityResult,
				out,
				someMetrics);
		}
		out.print("\t</Objects>\n</DssDocument>");
		out.close();
	}

	private static void printEntityResult(
		final MetricResult entityResult,
		final PrintWriter out,
		final IMetric[] someMetrics) {

		out.println("\t\t<Object>");
		out.println("\t\t\t<Name>" + entityResult.getEntityName() + "</Name>");

		//finds parent

		out.println("\t\t\t<Parents>");
		out.println("\t\t\t</Parents>");

		//finds children

		out.println("\t\t\t<Children>");
		out.println("\t\t\t</Children>");
		out.println("\t\t\t<UmlTargets>");
		out.println("\t\t\t</UmlTargets>");
		out.println("\t\t\t<ObjectProperties>");
		double[] values = entityResult.getValues();
		for (int i = 0; i < values.length; i++) {
			out.print("\t\t\t\t<Prop>\n\t\t\t\t\t<PropName>"
					+ someMetrics[i].getName()
					+ "</PropName>\n\t\t\t\t\t<ProValue>" + values[i]
					+ "</ProValue>\n\t\t\t\t</Prop>\n");
		}
		out.println("\t\t\t</ObjectProperties>");
		out.println("\t\t</Object>");
	}

	//	private static void computeEntity(
	//		final IEntity entity,
	//		final PrintWriter out,
	//		final Repository metricRepository,
	//		final List listOfMetrics) {
	//
	//		Iterator iter;
	//
	//		out.println("\t\t<Object>");
	//		out.println("\t\t\t<Name>" + entity.getName() + "</Name>");
	//
	//		//finds parent
	//
	//		out.println("\t\t\t<Parents>");
	//		iter = entity.listOfInheritedEntities().iterator();
	//
	//		while (iter.hasNext()) {
	//			out.println("\t\t\t\t<Parent>"
	//				+ ((IEntity) iter.next()).getName() + "</Parent>");
	//		}
	//
	//		out.println("\t\t\t</Parents>");
	//
	//		//finds children
	//
	//		out.println("\t\t\t<Children>");
	//
	//		iter = entity.listOfInheritingEntities().iterator();
	//
	//		while (iter.hasNext()) {
	//
	//			out.println("\t\t\t\t<Child>"
	//				+ ((IEntity) iter.next()).getName() + "</Child>");
	//
	//		}
	//
	//		out.println("\t\t\t</Children>");
	//
	//		out.println("\t\t\t<UmlTargets>");
	//
	//		Set targets = new HashSet();
	//
	//		//iter = entity.listOfConstituents().iterator();
	//
	//		iter = entity.getIteratorOnConstituents();
	//
	//		while (iter.hasNext()) {
	//
	//			final IElement actor = (IElement) iter.next();
	//
	//			if (actor instanceof IUseRelationship) {
	//
	//				IUseRelationship rel = (IUseRelationship) actor;
	//
	//				if (!(rel.getTargetEntity().getName().startsWith("java."))) {
	//					targets.add(rel.getTargetEntity().getName());
	//				}
	//			}
	//		}
	//
	//		iter = targets.iterator();
	//
	//		while (iter.hasNext()) {
	//			out.print("\t\t\t\t<Target>" + (String) iter.next()
	//				+ "</Target>\n");
	//		}
	//
	//		out.println("\t\t\t</UmlTargets>");
	//
	//		out.println("\t\t\t<ObjectProperties>");
	//
	//		//UNARY METRICS
	//		Iterator iterMetrics = listOfMetrics.iterator();
	//
	//		while (iterMetrics.hasNext()) {
	//			String currentMetric = (String) iterMetrics.next();
	//			out.print("\t\t\t\t<Prop>\n\t\t\t\t\t<PropName>" +
	//
	//			currentMetric +
	//
	//			"</PropName>\n\t\t\t\t\t<ProValue>" +
	//
	//			metricRepository.compute(currentMetric, entity) +
	//
	//			"</ProValue>\n\t\t\t\t</Prop>\n");
	//
	//		}
	//
	//		out.println("\t\t\t</ObjectProperties>");
	//
	//		out.println("\t\t</Object>");
	//
	//	}

	private static String xmlHeader(
		final String test_name,
		final IMetric[] someMetrics,
		final String xslSheet) {

		final StringBuffer buffer = new StringBuffer();

		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		if (xslSheet != null) {
			buffer.append("<?xml-stylesheet type=\"text/xsl\" href=\" "
					+ xslSheet + "\"?>");
		}

		buffer.append("<DssDocument>\n" +

		"<title>" + test_name + "</title>" + XMLMetricsGenerator.getTimeStamp()
				+ "\t<Associations>\n" +

				"\t\t<asso>\n" +

				"\t\t\t<metric>CBO</metric>\n" +

				"\t\t\t<graph>Blue to Red</graph>\n" +

				"\t\t</asso>\n" +

				"\t\t<asso>\n" +

				"\t\t\t<metric>WMC</metric>\n" +

				"\t\t\t<graph>height</graph>\n" +

				"\t\t</asso>\n" +

				"\t\t<asso>\n" +

				"\t\t\t<metric>LCOM5</metric>\n" +

				"\t\t\t<graph>Twist</graph>\n" +

				"\t\t</asso>\n" +

				"\t\t<asso>\n" +

				"\t\t\t<metric>CBO</metric>\n" +

				"\t\t\t<graph>sort</graph>\n" +

				"\t\t</asso>\n" +

				"\t</Associations>\n");

		// On ajoute les propriétés de chaque métriques
		buffer.append("\t<ObjRelStructure>\n" + "\t\t<ObjectProperties>\n");

		final MetricPropMap metricPropMap = MetricPropMap.getMetricPropMap();

		for (int i = 0; i < someMetrics.length; i++) {
			final IMetric metric = someMetrics[i];
			final MetricProp prop =
				metricPropMap.getPropForMetricName(metric.getName());

			buffer.append("\t\t\t<Prop>\n" + "\t\t\t\t<PropName>"
					+ prop.getName() + "</PropName>\n" + "\t\t\t\t<ProType>"
					+ prop.getType() + "</ProType>\n" + "\t\t\t\t<ProComments>"
					+ prop.getComment() + "</ProComments>\n");
			if (prop.getMin() != -1.0)
				buffer.append("\t\t\t\t<PropMin>" + prop.getMin()
						+ "</PropMin>\n");
			if (prop.getMax() != -1.0)
				buffer.append("\t\t\t\t<PropMax>" + prop.getMax()
						+ "</PropMax>\n");
			buffer.append("\t\t\t</Prop>\n");

		}

		buffer.append("\t\t</ObjectProperties>\n" +

		"\t\t<RelationProperties />\n" +

		"\t</ObjRelStructure>\n" +

		"\t<Objects>\n");

		return buffer.toString();
	}

	private static String getTimeStamp() {
		StringBuffer timestamp = new StringBuffer("");
		Calendar cal = Calendar.getInstance();

		timestamp.append("<timestamp>");
		timestamp.append("<year>" + cal.get(Calendar.YEAR) + "</year>");
		timestamp
			.append("<month>" + (cal.get(Calendar.MONTH) + 1) + "</month>");
		timestamp.append("<day>" + cal.get(Calendar.DAY_OF_MONTH) + "</day>");
		timestamp.append("<hour>" + cal.get(Calendar.HOUR_OF_DAY) + "</hour>");
		timestamp.append("<minute>" + cal.get(Calendar.MINUTE) + "</minute>");
		timestamp.append("<second>" + cal.get(Calendar.SECOND) + "</second>");
		timestamp.append("</timestamp>");

		return timestamp.toString();
	}
}

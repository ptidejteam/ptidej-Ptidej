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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IRelationship;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.DummyRelationship;
import padl.kernel.impl.Factory;
import padl.motif.repository.Adapter;
import padl.motif.repository.Composite;
import padl.motif.repository.Facade;
import padl.motif.repository.Observer;
import padl.motif.repository.Proxy;
import padl.motif.repository.Visitor;
import padl.util.Util;
import util.io.ProxyConsole;
import epi.algorithm.ChinesePostman;
import epi.algorithm.IChinesePostmanListener;
import epi.algorithm.ITransportationSimplexListener;
import epi.algorithm.TransportationSimplex;
import epi.model.Matrix;
import epi.model.MatrixBuilder;

/**
 * @author OlivierK
 *
 */
public class StringBuilder {

	private static Matrix buildAdjacencyMatrix(
		final IAbstractModel anAbstractModel) {
		// Build an adjacency matrix with the (out-degree - in-degree) of each class.
		////TODO:BUG in PADL
		//  List tempList = new ArrayList();
		//  Iterator a = anAbstractModel.getIteratorOnConstituents();
		//  while(a.hasNext()){
		//	  String id = ((IConstituent)a.next()).getID();
		//	  if(id.indexOf('$') > 0)
		//		  tempList.add(id);
		//  }
		//  Iterator b = tempList.iterator();
		//  while(b.hasNext()){
		//	anAbstractModel.removeConstituentFromID(((String)b.next()));
		//  }
		////TODO:BUG in PADL

		final MatrixBuilder builder =
			new MatrixBuilder(anAbstractModel.getNumberOfTopLevelEntities());
		anAbstractModel.walk(builder);
		return (Matrix) builder.getResult();
	}

	public static String buildModelString(final IAbstractModel abstractModel) {
		return StringBuilder.createStringRepresentation(abstractModel);
	}

	public static String buildPatternString(final String aMotif) {
		if (aMotif.equals("Abstract Factory")) {
			return "AbstractFactory inheritance ConcreteFactory creation Product dummyRelationship ConcreteFactory association Product dummyRelationship AbstractProduct inheritance Product";
		}
		else if (aMotif.equals("Adapter")) {
			return "Client association EPI_Abstract_Target inheritance Adapter association Adaptee ignorance Adapter dummyRelationship EPI_Abstract_Target ignorance Client";
		}
		else if (aMotif.equals("Composite")) {
			return "Composite containerComposition EPI_Abstract_Component inheritance Composite dummyRelationship EPI_Abstract_Component inheritance Leaf";
		}

		IAbstractModel aModel;
		try {
			aModel = StringBuilder.getPatternModel(aMotif);
		}
		catch (final Exception e) {
			aModel = null;
		}
		if (aModel != null) {
			return StringBuilder.createStringRepresentation(aModel);
		}
		else {
			return "";
		}
	}

	public static String buildProgramString(final String path) {
		return StringBuilder.buildProgramString(path, null);
	}

	public static String buildProgramString(
		final String path,
		final String[] packageNames) {
		final ICodeLevelModel aModel =
			StringBuilder.createModel(path, packageNames);
		return StringBuilder.buildModelString(aModel);
	}

	private static String computeRelationshipName(final IElement anElement) {
		return String.valueOf(Util.minimizeFirstLetter(Util.computeSimpleName(
			anElement.getClass().getName()).toCharArray()));
	}
	private static IAbstractModel createEulerianModel(
		final IAbstractModel anAbstractModel,
		final Map degreesDifferences,
		final Matrix EPIMatrix) {

		// Using the table of differences between the out-degree and
		// the in-degree of each entity, I build the data required by the
		// transportation simplex.
		//
		// If the difference is positive: The vertex possesses more out than
		// in relationships, it must then play the role of destination to
		// receive dummy relationships.
		//
		// If the difference is negative: The vertex possesses more in than
		// out relationships, it must then play the role of source to send
		// dummy relationships
		//
		// A typical input is:
		//     "4 2 1 1\n1 1\n1 1\n1 1\n1 1\n1 1 2 1\n4 1\n"
		final List sources = new ArrayList();
		final List destinations = new ArrayList();
		final Iterator iterator = degreesDifferences.keySet().iterator();
		while (iterator.hasNext()) {
			final String entity = (String) iterator.next();
			final int difference =
				((Integer) degreesDifferences.get(entity)).intValue();
			if (difference > 0) {
				destinations.add(entity);
			}
			else if (difference < 0) {
				sources.add(entity);
			}
		}

		final StringBuffer inputBuffer = new StringBuffer();
		inputBuffer.append(sources.size());
		inputBuffer.append(' ');
		inputBuffer.append(destinations.size());
		inputBuffer.append(" 1 1\n");
		for (int i = 0; i < sources.size(); i++) {
			for (int j = 0; j < destinations.size(); j++) {
				inputBuffer.append("1 ");
			}
			inputBuffer.append('\n');
		}

		final Iterator sourceIterator = sources.iterator();
		while (sourceIterator.hasNext()) {
			final String entity = (String) sourceIterator.next();
			final int difference =
				Math.abs(((Integer) degreesDifferences.get(entity)).intValue());
			inputBuffer.append(difference);
			inputBuffer.append(' ');
		}
		inputBuffer.append('\n');

		final Iterator destinationIterator = destinations.iterator();
		while (destinationIterator.hasNext()) {
			final String entity = (String) destinationIterator.next();
			final int difference =
				((Integer) degreesDifferences.get(entity)).intValue();
			inputBuffer.append(difference);
			inputBuffer.append(' ');
		}
		inputBuffer.append('\n');

		// I clone the idiom-level model.
		final IAbstractModel eulerianIdiomLevelModel;
		// TODO: I must create a temporary model. Is there a more elegant way to deal with final and exceptions?
		//IAbstractModel temp = null;
		//try {
		//	temp = (IAbstractModel) anAbstractModel.clone();
		//} catch (final CloneNotSupportedException cnse) {
		//	cnse.printStackTrace();
		//} finally {
		//TODO: temp
		eulerianIdiomLevelModel = anAbstractModel; //temp;
		//}
		// I solve the transportation simplex and as I get new flows, I add
		// dummy relationships among the entities of the original idiom-level
		// model (I clone it first).
		final ITransportationSimplexListener listener =
			new ITransportationSimplexListener() {
				public void addToOutput(final String aString) {
				}
				public void newFlow(
					final int source,
					final int destination,
					final double flow) {

					final IFirstClassEntity originEntity =
						eulerianIdiomLevelModel
							.getTopLevelEntityFromID((String) sources
								.get(source));
					final IFirstClassEntity targetEntity =
						eulerianIdiomLevelModel
							.getTopLevelEntityFromID((String) destinations
								.get(destination));
					originEntity.addConstituent(new DummyRelationship(
						targetEntity));
					ProxyConsole
						.getInstance()
						.debugOutput()
						.print("Adding dummy relationship between ");
					ProxyConsole
						.getInstance()
						.debugOutput()
						.print(originEntity.getID());
					ProxyConsole.getInstance().debugOutput().print(" and ");
					ProxyConsole
						.getInstance()
						.debugOutput()
						.println(targetEntity.getID());
					EPIMatrix.incrementValue(originEntity, targetEntity);
				}
			};
		new TransportationSimplex(inputBuffer.toString(), listener).solve();

		return eulerianIdiomLevelModel;
	}

	private static ICodeLevelModel createModel(
		final String path,
		final String[] packageNames) {
		String[] computePaths = null;

		if (packageNames != null) {
			computePaths = new String[packageNames.length];
			for (int i = 0; i < packageNames.length; i++) {
				computePaths[i] = path + packageNames[i].replace('.', '/');
			}
		}
		else {
			computePaths = new String[1];
			computePaths[0] = path;
		}

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("Model");
		//Factory.getUniqueInstance().createIdiomLevelModel("Model");
		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				computePaths,
				true));

			if (packageNames != null) {
				// TODO: This code must be tested!
				final Iterator entities =
					codeLevelModel.getIteratorOnTopLevelEntities();
				while (entities.hasNext()) {
					final IFirstClassEntity firstClassEntity =
						(IFirstClassEntity) entities.next();
					final String entityName = firstClassEntity.getDisplayName();
					final String packageName =
						entityName.substring(0, entityName.lastIndexOf('.'));
					boolean toBeRemoved = true;
					for (int i = 0; i < packageNames.length && toBeRemoved; i++) {
						if (packageName.equals(packageNames[i])) {
							toBeRemoved = false;
						}
					}
					if (toBeRemoved) {
						codeLevelModel.removeTopLevelEntityFromID(entityName
							.toCharArray());
					}
				}
			}
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return codeLevelModel;
	}

	private static String createStringRepresentation(final IAbstractModel aModel) {
		final Matrix matrix = StringBuilder.buildAdjacencyMatrix(aModel);
		final Map differences = matrix.computeDifference();

		// TODO: We should remove the IMemberClass, IMemberInterace, and
		// IMemberGhost *before* building the Eulerian graph.

		final IAbstractModel eulerianIdiomLevelModel =
			StringBuilder.createEulerianModel(aModel, differences, matrix);

		return StringBuilder.createStringRepresentation(
			eulerianIdiomLevelModel,
			matrix);
	}

	private static String createStringRepresentation(
		final IAbstractModel anAbstractModel,
		final Matrix EPIMatrix) {

		final List entities = Util.getListOfAllEntities(anAbstractModel);
		final StringBuffer buffer = new StringBuffer();

		final IChinesePostmanListener listener = new IChinesePostmanListener() {
			public void addToOutput(final String aString) {
			}
			public void newLeg(
				final int source,
				final int destination,
				final String name) {

				if (((IFirstClassEntity) entities.get(source)).isAbstract()
						|| (IFirstClassEntity) entities.get(source) instanceof padl.kernel.IInterface) {
					buffer.append("EPI_Abstract_"
							+ ((IFirstClassEntity) entities.get(source))
								.getDisplayID());
				}
				else {
					buffer.append(((IFirstClassEntity) entities.get(source))
						.getID());
				}
				buffer.append(' ');
				buffer.append(name);
				buffer.append(' ');
			}
		};

		// final Vector entitiesToRemove = new Vector();
		final Iterator entityIter =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (entityIter.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) entityIter.next();
			if (EPIMatrix.getInRelationNb(firstClassEntity) == 0) {
				ProxyConsole
					.getInstance()
					.debugOutput()
					.println("Removed: " + firstClassEntity.getDisplayName());
				anAbstractModel.removeTopLevelEntityFromID(firstClassEntity
					.getID());
			}
		}
		// Yann 2008/01/15: Illegal call!
		// The legal way to remove an entity is ONLY to
		// call the remove* method on the abstract model,
		// to make sure any cache is updated properly.
		// entities.removeAll(entitiesToRemove);

		final ChinesePostman chinesePostman =
			new ChinesePostman(entities.size(), listener);
		final Iterator entityIterator = entities.iterator();
		while (entityIterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) entityIterator.next();

			final Iterator inheritanceIterator =
				firstClassEntity.getIteratorOnInheritingEntities();
			while (inheritanceIterator.hasNext()) {
				final IFirstClassEntity targetEntity =
					(IFirstClassEntity) inheritanceIterator.next();
				if (targetEntity instanceof IMemberClass
						|| targetEntity instanceof IMemberInterface
						|| targetEntity instanceof IMemberGhost
						|| targetEntity instanceof IGhost
						|| firstClassEntity instanceof IMemberClass
						|| firstClassEntity instanceof IMemberInterface
						|| firstClassEntity instanceof IMemberGhost
						|| firstClassEntity instanceof IGhost) {
					;
				}
				else {
					chinesePostman.addArc(
						"inheritance",
						entities.indexOf(firstClassEntity),
						entities.indexOf(targetEntity),
						// TODO: Inheritances must be relationships in PADL!
						100);
				}
			}

			final Iterator elementIterator =
				firstClassEntity.getIteratorOnConstituents();
			while (elementIterator.hasNext()) {
				final IElement element = (IElement) elementIterator.next();
				if (element instanceof IRelationship) {

					final IFirstClassEntity targetEntity =
						((IRelationship) element).getTargetEntity();
					if (targetEntity instanceof IMemberClass
							|| targetEntity instanceof IMemberInterface
							|| targetEntity instanceof IMemberGhost
							|| targetEntity instanceof IGhost
							|| firstClassEntity instanceof IMemberClass
							|| firstClassEntity instanceof IMemberInterface
							|| firstClassEntity instanceof IMemberGhost
							|| firstClassEntity instanceof IGhost) {
						;
					}
					else {
						chinesePostman.addArc(StringBuilder
							.computeRelationshipName(element), entities
							.indexOf(firstClassEntity), entities
							.indexOf(((IRelationship) element)
								.getTargetEntity()), element.getWeight());
					}
				}
			}
		}

		chinesePostman.solve(true);
		chinesePostman.printCPT(0);

		// I complete the output with the first vertex which is also the last.
		final String stringRepresentation = buffer.toString();
		final StringTokenizer st = new StringTokenizer(stringRepresentation);
		if (st.hasMoreTokens()) {
			buffer.append(st.nextToken());
		}
		return buffer.toString();
	}
	final static IAbstractModel getPatternModel(final String aMotifName) {
		//TODO: A faire d'une autre facon!!! 
		if (aMotifName == "Adapter") {
			return new Adapter();
		}
		else if (aMotifName == "Composite") {
			return new Composite();
		}
		else if (aMotifName == "Visitor") {
			return new Visitor();
		}
		else if (aMotifName == "Facade") {
			return new Facade();
		}
		else if (aMotifName == "Observer") {
			return new Observer();
		}
		else if (aMotifName == "Proxy") {
			return new Proxy();
		}
		else {
			return null;
		}
	}

	private StringBuilder() {
	}

}

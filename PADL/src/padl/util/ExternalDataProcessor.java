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
package padl.util;

import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IComposition;
import padl.kernel.IContainerAggregation;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2002/10/08
 */
public final class ExternalDataProcessor {
	private static void addAggregationRelationship(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anOriginEntity,
		final String aRelationshipName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		final IAggregation aggregation =
			anAbstractModel.getFactory().createAggregationRelationship(
				aRelationshipName.toCharArray(),
				aTargetEntity,
				aCardinality);
		anOriginEntity.addConstituent(aggregation);
	}
	private static void addCompositionRelationship(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anOriginEntity,
		final String aRelationshipName,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		final IComposition composition =
			anAbstractModel.getFactory().createCompositionRelationship(
				aRelationshipName.toCharArray(),
				aTargetEntity,
				aCardinality);
		anOriginEntity.addConstituent(composition);
	}
	private static void convertAggregationInComposition(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anOriginEntity,
		final IFirstClassEntity aTargetEntity) {

		final Iterator iterator =
			anOriginEntity.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IElement element = (IElement) iterator.next();

			if (element instanceof IAssociation) {
				final IAssociation association = (IAssociation) element;
				if (association.getTargetEntity().isAboveInHierarchy(
					aTargetEntity)) {

					anOriginEntity.removeConstituentFromID(association.getID());
					if (association instanceof IContainerAggregation) {
						anOriginEntity
							.addConstituent(anAbstractModel
								.getFactory()
								.createContainerCompositionRelationship(
									association));
					}
					else if (association instanceof IAggregation) {
						anOriginEntity.addConstituent(anAbstractModel
							.getFactory()
							.createCompositionRelationship(association));
					}
				}
			}
		}
	}
	public static void process(
		final Properties someProperties,
		final IAbstractModel anAbstractModel) {

		final Iterator iterator = someProperties.keySet().iterator();
		while (iterator.hasNext()) {
			final String key = (String) iterator.next();
			if (key.startsWith("Relation")) {
				final String command = someProperties.getProperty(key);
				final int parenthesisIndex = command.indexOf('(');
				final String name = command.substring(0, parenthesisIndex);
				final String arguments =
					command.substring(
						parenthesisIndex + 1,
						command.length() - 1);

				if (name.equals("addAggregationRelationship")
						|| name.equals("addCompositionRelationship")) {

					final StringTokenizer tokenizer =
						new StringTokenizer(arguments, "(,)");
					final IFirstClassEntity originEntity =
						(IFirstClassEntity) anAbstractModel
							.getConstituentFromName(tokenizer
								.nextToken()
								.trim()
								.toCharArray());
					final String relationshipName =
						tokenizer.nextToken().trim();
					final IFirstClassEntity targetEntity =
						(IFirstClassEntity) anAbstractModel
							.getConstituentFromName(tokenizer
								.nextToken()
								.trim()
								.toCharArray());
					final Integer cardinality =
						Integer.valueOf(tokenizer.nextToken().trim());

					if (originEntity != null && targetEntity != null) {
						if (name.equals("addAggregationRelationship")) {
							ExternalDataProcessor.addAggregationRelationship(
								anAbstractModel,
								originEntity,
								relationshipName,
								targetEntity,
								cardinality.intValue());
						}
						else if (name.equals("addCompositionRelationship")) {
							ExternalDataProcessor.addCompositionRelationship(
								anAbstractModel,
								originEntity,
								relationshipName,
								targetEntity,
								cardinality.intValue());
						}
					}
				}
				else if (name.equals("convertAggregationInComposition")) {
					final StringTokenizer tokenizer =
						new StringTokenizer(arguments, ",");
					final IFirstClassEntity originEntity =
						(IFirstClassEntity) anAbstractModel
							.getConstituentFromName(tokenizer
								.nextToken()
								.trim()
								.toCharArray());
					final IFirstClassEntity targetEntity =
						(IFirstClassEntity) anAbstractModel
							.getConstituentFromName(tokenizer
								.nextToken()
								.trim()
								.toCharArray());

					if (originEntity != null && targetEntity != null) {
						ExternalDataProcessor.convertAggregationInComposition(
							anAbstractModel,
							originEntity,
							targetEntity);
					}
				}
			}
		}
	}
}

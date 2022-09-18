/*******************************************************************************
 * Copyright (c) 2001-2015 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.xmiclassdiagram;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.CreationException;
import padl.kernel.exception.ModelDeclarationException;
import padl.kernel.impl.Factory;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.IDesignLevelModelCreator;
import padl.path.Finder;
import padl.path.FormatException;
import padl.path.IConstants;
import util.io.ProxyConsole;
import util.lang.Modifier;
import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;

public final class XMICreator implements IDesignLevelModelCreator {
	private interface IBuilder {
		String getTypeOfConstituent();
	}
	private interface IComplexBinaryClassRelationshipBuilder extends IBuilder {
		void instantiateTypeOfConstituent(
			final IFirstClassEntity sourceEntity,
			final IFirstClassEntity targetEntity,
			final String elementID,
			final int elementVisbility,
			final int elementType);
	}
	private interface ISimpleBinaryClassRelationshipBuilder extends IBuilder {
		String getSourceAttributeName();
		String getTargetAttributeName();
		void instantiateTypeOfConstituent(
			final IFirstClassEntity sourceEntity,
			final IFirstClassEntity targetEntity,
			final String elementID,
			final int elementVisbility);
	}

	private interface IUnaryBuilder extends IBuilder {
		IConstituent instantiateTypeOfConstituent(
			final String elementID,
			final String elementName);
	}
	private static final String DIRECTORY_BASE =
		"../PADL Creator XMI/lib/SDMetricsOpenCore/src/com/sdmetrics/resources/";
	private static final String UNDEFINED_TYPE = "UNDEFINED_TYPE";
	private static final int ASSOCIATION = 1;
	private static final int AGGREGATION = 2;
	private static final int COMPOSITION = 3;

	private final String xmiFile;
	public XMICreator(final String aPathToSomeXMIFile) {
		this.xmiFile = aPathToSomeXMIFile;
	}

	private void build(
		final MetaModel aMetaModel,
		final Model aModel,
		final ICodeLevelModel aCodeLevelModel,
		final IUnaryBuilder aBuilder) {

		final MetaModelElement metaModelElement =
			aMetaModel.getType(aBuilder.getTypeOfConstituent());
		final List<ModelElement> elements =
			aModel.getAcceptedElements(metaModelElement);
		for (final ModelElement me : elements) {
			final ModelElement elementContext = me.getRefAttribute("context");
			final String elementID = me.getPlainAttribute("id");
			final String elementName = me.getPlainAttribute("name");
			String elementVisibility;
			try {
				elementVisibility = me.getPlainAttribute("visibility");
			}
			catch (final IllegalArgumentException e) {
				elementVisibility = "public";
			}

			final StringBuffer elementContextPADLised = new StringBuffer();
			elementContextPADLised.append(IConstants.ABSTRACT_MODEL_SYMBOL);
			elementContextPADLised.append(elementContext
				.getFullName()
				.substring(1)
				.replace('.', IConstants.ELEMENT_SYMBOL));
			elementContextPADLised.append(IConstants.ELEMENT_SYMBOL);

			IContainer container;
			try {
				container =
					Finder.findContainer(
						elementContextPADLised.toString(),
						aCodeLevelModel);
			}
			catch (final FormatException e) {
				container =
					this.createMissingConstituentsAndReturnContainer(
						elementContextPADLised.toString(),
						aCodeLevelModel);
			}

			final IConstituent constituent =
				aBuilder.instantiateTypeOfConstituent(elementID, elementName);
			constituent.setVisibility(Modifier.fromString(elementVisibility));
			// Yann 2015/05/05: Law and order!
			// It is well possible that I created a (ghost)package with a given
			// name because this name appeared in the path of a member class...
			// and now I am trying to create and add this class! So, I must
			// "transform" the ghost-package into this class by moving everything
			// inside to the class and then removing the package and then adding
			// the newly created class.
			if (container.doesContainConstituentWithID(constituent.getID())) {
				final IContainer oldConstituent =
					(IContainer) container.getConstituentFromID(constituent
						.getID());
				if (!oldConstituent.getClass().equals(constituent.getClass())) {
					final Iterator<?> iterator =
						oldConstituent.getConcurrentIteratorOnConstituents();
					while (iterator.hasNext()) {
						final IConstituent innerConstituent =
							(IConstituent) iterator.next();
						oldConstituent.removeConstituentFromID(innerConstituent
							.getID());
						((IContainer) constituent)
							.addConstituent(innerConstituent);
					}
					container.removeConstituentFromID(constituent.getID());
					try {
						container.addConstituent(constituent);
					}
					catch (final ModelDeclarationException e) {
						ProxyConsole
							.getInstance()
							.errorOutput()
							.println("Uh oh...");
					}
				}
			}
			else {
				try {
					container.addConstituent(constituent);
				}
				catch (final ModelDeclarationException e) {
					ProxyConsole
						.getInstance()
						.errorOutput()
						.println("Uh oh...");
				}
			}
		}
	}
	private IContainer createMissingConstituentsAndReturnContainer(
		final String elementContextPADLised,
		final ICodeLevelModel aCodeLevelModel) {

		// Yann 2015/04/16: "Logical View"
		// If I cannot find the container, it is possible that it is this
		// infamous "Logical View", which is undefined in XMI files but
		// still used... So, I create a ghost-package...
		final StringTokenizer tokenizer =
			new StringTokenizer(
				elementContextPADLised,
				String.valueOf(IConstants.ELEMENT_SYMBOL));
		// I remove the model's name from the tokens.
		tokenizer.nextToken();
		IContainer container = aCodeLevelModel;
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();
			final char[] newContainerID = token.toCharArray();
			if (!container.doesContainConstituentWithID(newContainerID)) {
				final IContainer newContainer;
				if (container instanceof IAbstractLevelModel) {
					newContainer =
						Factory
							.getInstance()
							.createPackageGhost(newContainerID);
				}
				else if (container instanceof IPackage) {
					// Yann 2015/05/05: Tricky!
					// The only means (sloppy) to distinguish between nest
					// package ghosts and a terminal Ghost is to check if
					// there are more tokens...
					if (tokenizer.hasMoreTokens()) {
						newContainer =
							Factory.getInstance().createPackageGhost(
								newContainerID);
					}
					else {
						newContainer =
							Factory.getInstance().createGhost(
								newContainerID,
								newContainerID);
					}
				}
				else if (container instanceof IEntity) {
					newContainer =
						Factory.getInstance().createMemberGhost(
							newContainerID,
							newContainerID);
				}
				else {
					newContainer = null;
				}
				container.addConstituent((IConstituent) newContainer);
				container = newContainer;
			}
			else {
				container =
					(IContainer) container.getConstituentFromID(newContainerID);
			}
		}
		return container;
	}

	private void buildCBCRelationships(
		final MetaModel aMetaModel,
		final Model aModel,
		final ICodeLevelModel aCodeLevelModel,
		final IComplexBinaryClassRelationshipBuilder aBuilder) {

		final MetaModelElement metaModelElement =
			aMetaModel.getType(aBuilder.getTypeOfConstituent());
		final List<ModelElement> elements =
			aModel.getAcceptedElements(metaModelElement);
		for (final ModelElement me : elements) {
			final ModelElement[] relations =
				me.getRelations("context").toArray(new ModelElement[0]);

			// Yann 2015/04/16: Order!
			// I must order the AssociationEnds based on their ID,
			// kind of stupid but no other information is available
			// regarding the direction of the relationship.
			if (relations[1].getPlainAttribute("id").compareTo(
				relations[0].getPlainAttribute("id")) < 0) {

				final ModelElement tempModelElement = relations[0];
				relations[0] = relations[1];
				relations[1] = tempModelElement;
			}

			if (relations.length > 2) {
				throw new RuntimeException(
					new CreationException(
						"PADL does not handle multi-relationships yet, only binary-class relationships"));
			}

			final String elementID = me.getPlainAttribute("id");
			int elementVisibility;
			try {
				elementVisibility =
					Modifier.fromString(me.getPlainAttribute("visibility"));
			}
			catch (final IllegalArgumentException e) {
				elementVisibility = Modifier.PUBLIC;
			}
			final String elementTypeAttribute =
				relations[0].getPlainAttribute("aggregation");
			// Ugly code! 
			// TODO use some transformation to make associations behave like dependencies or generalisations...
			int elementType;
			if (elementTypeAttribute.equals("aggregate")) {
				elementType = XMICreator.AGGREGATION;
			}
			else if (elementTypeAttribute.equals("composite")) {
				elementType = XMICreator.COMPOSITION;
			}
			else {
				elementType = XMICreator.ASSOCIATION;
			}

			// Source
			final ModelElement source =
				relations[0].getRefAttribute("associationendtype");
			final StringBuffer sourcePADLised = new StringBuffer();
			sourcePADLised.append(IConstants.ABSTRACT_MODEL_SYMBOL);
			sourcePADLised.append(source
				.getFullName()
				.substring(1)
				.replace('.', IConstants.ELEMENT_SYMBOL));
			sourcePADLised.append(IConstants.ELEMENT_SYMBOL);

			IFirstClassEntity sourcePADLIFirstClassEntity;
			try {
				sourcePADLIFirstClassEntity =
					(IFirstClassEntity) Finder.findContainer(
						sourcePADLised.toString(),
						aCodeLevelModel);
			}
			catch (final FormatException e) {
				sourcePADLIFirstClassEntity =
					(IFirstClassEntity) this
						.createMissingConstituentsAndReturnContainer(
							sourcePADLised.toString(),
							aCodeLevelModel);
			}

			// Target
			final ModelElement target =
				relations[1].getRefAttribute("associationendtype");
			final StringBuffer targetPADLised = new StringBuffer();
			targetPADLised.append(IConstants.ABSTRACT_MODEL_SYMBOL);
			targetPADLised.append(target
				.getFullName()
				.substring(1)
				.replace('.', IConstants.ELEMENT_SYMBOL));
			targetPADLised.append(IConstants.ELEMENT_SYMBOL);

			IFirstClassEntity targetPADLIFirstClassEntity;
			try {
				targetPADLIFirstClassEntity =
					(IFirstClassEntity) Finder.findContainer(
						targetPADLised.toString(),
						aCodeLevelModel);
			}
			catch (final FormatException e) {
				targetPADLIFirstClassEntity =
					(IFirstClassEntity) this
						.createMissingConstituentsAndReturnContainer(
							targetPADLised.toString(),
							aCodeLevelModel);
			}

			((IComplexBinaryClassRelationshipBuilder) aBuilder)
				.instantiateTypeOfConstituent(
					sourcePADLIFirstClassEntity,
					targetPADLIFirstClassEntity,
					elementID,
					elementVisibility,
					elementType);
		}
	}
	private void buildSBCRelationships(
		final MetaModel aMetaModel,
		final Model aModel,
		final ICodeLevelModel aCodeLevelModel,
		final ISimpleBinaryClassRelationshipBuilder aBuilder) {

		final MetaModelElement metaModelElement =
			aMetaModel.getType(aBuilder.getTypeOfConstituent());
		final List<ModelElement> elements =
			aModel.getAcceptedElements(metaModelElement);
		for (final ModelElement me : elements) {
			final String elementID = me.getPlainAttribute("id");
			int elementVisibility;
			try {
				elementVisibility =
					Modifier.fromString(me.getPlainAttribute("visibility"));
			}
			catch (final IllegalArgumentException e) {
				elementVisibility = Modifier.PUBLIC;
			}

			// Source
			final ModelElement genchild =
				me.getRefAttribute(aBuilder.getSourceAttributeName());
			final StringBuffer genchildPADLised = new StringBuffer();
			genchildPADLised.append(IConstants.ABSTRACT_MODEL_SYMBOL);
			genchildPADLised.append(genchild
				.getFullName()
				.substring(1)
				.replace('.', IConstants.ELEMENT_SYMBOL));
			genchildPADLised.append(IConstants.ELEMENT_SYMBOL);

			IFirstClassEntity genchildPADLIFirstClassEntity;
			try {
				genchildPADLIFirstClassEntity =
					(IFirstClassEntity) Finder.findContainer(
						genchildPADLised.toString(),
						aCodeLevelModel);
			}
			catch (final FormatException e) {
				genchildPADLIFirstClassEntity =
					(IFirstClassEntity) this
						.createMissingConstituentsAndReturnContainer(
							genchildPADLised.toString(),
							aCodeLevelModel);
			}

			// Target
			final ModelElement genparent =
				me.getRefAttribute(aBuilder.getTargetAttributeName());
			final StringBuffer genparentPADLised = new StringBuffer();
			genparentPADLised.append(IConstants.ABSTRACT_MODEL_SYMBOL);
			genparentPADLised.append(genparent
				.getFullName()
				.substring(1)
				.replace('.', IConstants.ELEMENT_SYMBOL));
			genparentPADLised.append(IConstants.ELEMENT_SYMBOL);

			IFirstClassEntity genparentPADLIFirstClassEntity;
			try {
				genparentPADLIFirstClassEntity =
					(IFirstClassEntity) Finder.findContainer(
						genparentPADLised.toString(),
						aCodeLevelModel);
			}
			catch (final FormatException e) {
				// Yann 2015/05/05: Logical View has parent???
				// Yes, it happens that en entity wants for parent the 
				// ghost-package "Logical View".. whatever... Just ignore ;-)
				if (genparentPADLised.toString().endsWith("Logical View||")) {
					return;
				}
				genparentPADLIFirstClassEntity =
					(IFirstClassEntity) this
						.createMissingConstituentsAndReturnContainer(
							genparentPADLised.toString(),
							aCodeLevelModel);
			}

			((ISimpleBinaryClassRelationshipBuilder) aBuilder)
				.instantiateTypeOfConstituent(
					genchildPADLIFirstClassEntity,
					genparentPADLIFirstClassEntity,
					elementID,
					elementVisibility);
		}
	}

	@Override
	public void create(final IDesignLevelModel aDesignLevelModel)
			throws CreationException {

		try {
			final XMLParser parser = new XMLParser();
			final MetaModel metaModel = new MetaModel();
			parser.parse(
				XMICreator.DIRECTORY_BASE + "metamodel.xml",
				metaModel.getSAXParserHandler());

			final XMITransformations trans = new XMITransformations(metaModel);
			parser.parse(
				XMICreator.DIRECTORY_BASE + "xmiTrans1_1.xml",
				trans.getSAXParserHandler());

			final Model model = new Model(metaModel);
			final XMIReader xmiReader = new XMIReader(trans, model);
			parser.parse(this.xmiFile, xmiReader);

			// this.print(metaModel, model);

			final MetaModelElement metaModelElement =
				metaModel.getType("model");
			final List<ModelElement> elements =
				model.getAcceptedElements(metaModelElement);
			String modelName = "";
			for (final ModelElement me : elements) {
				final String elementName = me.getName();
				if (!elementName.isEmpty()) {
					modelName = elementName;
					break;
				}
			}
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(modelName);

			// Entities and elements...
			this.build(metaModel, model, codeLevelModel, new IUnaryBuilder() {
				@Override
				public String getTypeOfConstituent() {
					return "package";
				}
				@Override
				public IConstituent instantiateTypeOfConstituent(
					final String elementID,
					final String elementName) {

					final IPackage packaje =
						Factory.getInstance().createPackage(
							elementName.toCharArray());
					return packaje;
				}
			});
			this.build(metaModel, model, codeLevelModel, new IUnaryBuilder() {
				@Override
				public String getTypeOfConstituent() {
					return "interface";
				}
				@Override
				public IConstituent instantiateTypeOfConstituent(
					final String elementID,
					final String elementName) {

					final IInterface interfaze =
						Factory.getInstance().createInterface(
							elementName.toCharArray(),
							elementName.toCharArray());
					return interfaze;
				}
			});
			this.build(metaModel, model, codeLevelModel, new IUnaryBuilder() {
				@Override
				public String getTypeOfConstituent() {
					return "class";
				}
				@Override
				public IConstituent instantiateTypeOfConstituent(
					final String elementID,
					final String elementName) {

					final IClass clazz =
						Factory.getInstance().createClass(
							elementName.toCharArray(),
							elementName.toCharArray());
					return clazz;
				}
			});
			this.build(metaModel, model, codeLevelModel, new IUnaryBuilder() {
				@Override
				public String getTypeOfConstituent() {
					return "attribute";
				}
				@Override
				public IConstituent instantiateTypeOfConstituent(
					final String elementID,
					final String elementName) {

					final IField field =
						Factory.getInstance().createField(
							elementID.toCharArray(),
							elementName.toCharArray(),
							XMICreator.UNDEFINED_TYPE.toCharArray(),
							1);
					return field;
				}
			});
			this.build(metaModel, model, codeLevelModel, new IUnaryBuilder() {
				@Override
				public String getTypeOfConstituent() {
					return "operation";
				}
				@Override
				public IConstituent instantiateTypeOfConstituent(
					final String elementID,
					final String elementName) {

					final IMethod method =
						Factory.getInstance().createMethod(
							elementID.toCharArray(),
							elementName.toCharArray());
					// Yann 15/04/20: Code-lines for LOC!
					// To prevent NullPointerException, I set the numbers 
					// of lines of code of newly-created method to 1.
					method.setCodeLines(new String[1]);
					return method;
				}
			});

			// Binary relations...
			this.buildSBCRelationships(
				metaModel,
				model,
				codeLevelModel,
				new ISimpleBinaryClassRelationshipBuilder() {
					@Override
					public String getSourceAttributeName() {
						return "genchild";
					}
					@Override
					public String getTargetAttributeName() {
						return "genparent";
					}
					@Override
					public String getTypeOfConstituent() {
						return "generalization";
					}
					@Override
					public void instantiateTypeOfConstituent(
						final IFirstClassEntity sourceEntity,
						final IFirstClassEntity targetEntity,
						final String elementID,
						final int elementVisbility) {

						// TODO Implement a method "isInheritingDirectlyFromID" in PADL
						if (sourceEntity.getInheritedEntityFromID(targetEntity
							.getID()) == null
								&& !sourceEntity.equals(targetEntity)) {

							sourceEntity.addInheritedEntity(targetEntity);
						}
					}
				});
			this.buildSBCRelationships(
				metaModel,
				model,
				codeLevelModel,
				new ISimpleBinaryClassRelationshipBuilder() {
					@Override
					public String getSourceAttributeName() {
						return "depclient";
					}
					@Override
					public String getTargetAttributeName() {
						return "depsupplier";
					}
					@Override
					public String getTypeOfConstituent() {
						return "dependency";
					}
					@Override
					public void instantiateTypeOfConstituent(
						final IFirstClassEntity sourceEntity,
						final IFirstClassEntity targetEntity,
						final String elementID,
						final int elementVisbility) {

						final IUseRelationship useRelationship =
							Factory.getInstance().createUseRelationship(
								elementID.toCharArray(),
								targetEntity,
								1);
						useRelationship.setVisibility(elementVisbility);
						sourceEntity.addConstituent(useRelationship);
					}
				});
			this.buildCBCRelationships(
				metaModel,
				model,
				codeLevelModel,
				new IComplexBinaryClassRelationshipBuilder() {
					@Override
					public String getTypeOfConstituent() {
						return "association";
					}
					@Override
					public void instantiateTypeOfConstituent(
						final IFirstClassEntity sourceEntity,
						final IFirstClassEntity targetEntity,
						final String elementID,
						final int elementVisbility,
						final int elementType) {

						IAssociation association;
						if (elementType == XMICreator.AGGREGATION) {
							association =
								Factory
									.getInstance()
									.createAggregationRelationship(
										elementID.toCharArray(),
										targetEntity,
										1);
						}
						else if (elementType == XMICreator.COMPOSITION) {
							association =
								Factory
									.getInstance()
									.createCompositionRelationship(
										elementID.toCharArray(),
										targetEntity,
										1);
						}
						else {
							association =
								Factory
									.getInstance()
									.createAssociationRelationship(
										elementID.toCharArray(),
										targetEntity,
										1);
						}
						association.setVisibility(elementVisbility);
						sourceEntity.addConstituent(association);
					}
				});

			// Copy of the content of the "new", temporary code-level model
			// into the one given by the user to implement the Builder pattern.
			final Iterator<?> iterator =
				codeLevelModel.getConcurrentIteratorOnConstituents();
			while (iterator.hasNext()) {
				final IConstituent constituent = (IConstituent) iterator.next();
				codeLevelModel.removeConstituentFromID(constituent.getID());
				aDesignLevelModel.addConstituent(constituent);
			}
		}
		catch (final Exception e) {
			throw new CreationException(e);
		}
	}
	@SuppressWarnings("unused")
	private void print(final MetaModel metaModel, final Model model) {
		// Iterate over all model element types in the metamodel
		for (final MetaModelElement type : metaModel) {
			System.out.println("Elements of type: " + type.getName());

			// iterate over all model elements of the current type
			final List<ModelElement> elements = model.getAcceptedElements(type);
			for (final ModelElement me : elements) {
				System.out.println("  Element: " + me.getFullName() + " ");

				// write out the value of each attribute of the element
				final Collection<String> attributeNames =
					type.getAttributeNames();
				for (final String attr : attributeNames) {
					System.out.print("     Attribute '" + attr);
					if (type.isSetAttribute(attr)) {
						System.out.println("' has set value "
								+ me.getSetAttribute(attr));
					}
					else if (type.isRefAttribute(attr)) {
						System.out.print("' references ");
						ModelElement referenced = me.getRefAttribute(attr);
						System.out.println((referenced == null) ? "nothing"
								: referenced.getFullName());
					}
					else {
						System.out.println("' has value: "
								+ me.getPlainAttribute(attr));
					}
				}
			}
		}
	}
}

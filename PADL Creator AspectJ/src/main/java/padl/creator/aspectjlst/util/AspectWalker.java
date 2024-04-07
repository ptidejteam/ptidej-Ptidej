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
package padl.creator.aspectjlst.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.aspectj.asm.HierarchyWalker;
import org.aspectj.asm.IHierarchy;
import org.aspectj.asm.IProgramElement;
import org.aspectj.asm.IProgramElement.Accessibility;
import org.aspectj.asm.IRelationship;
import org.aspectj.asm.IRelationshipMap;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IAspectJFactory;
import padl.aspectj.kernel.IInterTypeDeclareParents;
import padl.aspectj.kernel.IInterTypeElement;
import padl.aspectj.kernel.IInterTypeMethod;
import padl.creator.aspectjlst.AspectCreator;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IEntity;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;
import padl.util.Util;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @author Jean-Yves
 * @since 2004/08/30
 */
public class AspectWalker extends HierarchyWalker {
	private static int NUMITDP = 0;

	private ICodeLevelModel codeLevelModel;
	private IAspect currentAspect;
	private IAspectJFactory factory;
	private final IHierarchy hierarchy;
	private final IRelationshipMap imap;
	private final HashMap mapImportEntity;
	private boolean walkingOverAspect = false;

	public AspectWalker(final IHierarchy ihierarchy, final IRelationshipMap imap) {
		this.imap = imap;
		this.hierarchy = ihierarchy;

		this.mapImportEntity = new HashMap();
	}
	public void addCodeLevelModel(final ICodeLevelModel aCodeLevelModel) {
		this.codeLevelModel = aCodeLevelModel;
		this.factory = (IAspectJFactory) this.codeLevelModel.getFactory();
	}
	private IConstituentOfEntity createAdvice(final IProgramElement node) {
		IConstituentOfEntity element = null;
		element = this.factory.createAdvice(node.toLabelString().toCharArray());
		element.setName(node.toLabelString().toCharArray());
		//		TODO: setRelationShip
		return element;
	}
	private IConstituentOfEntity createConstructor(final IProgramElement node) {
		IConstituentOfEntity element = null;
		element =
			this.factory.createConstructor(node.getName().toCharArray(), node
				.getName()
				.toCharArray());
		return element;
	}
	private IConstituentOfEntity createDeclareParent(final IProgramElement node) {
		IInterTypeDeclareParents element = null;
		//TODO: fix duplicate actorID exception
		element =
			this.factory
				.createInterTypeDeclareParents((node.toLabelString() + AspectWalker.NUMITDP++)
					.toCharArray());
		element.setName(node.toLabelString().toCharArray());
		this.setITRelationShip(node, element);
		//Get entity declared
		final StringTokenizer st = new StringTokenizer(node.getDetails(), " ");

		String name = null;

		while (st.hasMoreTokens()) {
			name = st.nextToken();
		}

		if (name != null) {
			if (this.codeLevelModel.getConstituentFromName(name) != null) {
				element
					.setDeclaredParent((IFirstClassEntity) this.codeLevelModel
						.getConstituentFromName(name));
			}
			else {
				if (this.mapImportEntity.get(name) != null) {
					element
						.setDeclaredParent((IFirstClassEntity) this.mapImportEntity
							.get(name));
				}
				else {
					//					logger.warn(
					//						"Target " + name + " not found in the model.");
				}
			}
		}
		return element;
	}
	private IConstituentOfEntity createField(final IProgramElement node) {
		IConstituentOfEntity element = null;
		//		 TODO: check the FIELD creation (Cardinality)
		element =
			this.factory.createField(node.getName().toCharArray(), node
				.getName()
				.toCharArray(), node.getCorrespondingType().toCharArray(), 1);
		element.setName(node.getName().toCharArray());
		return element;
	}
	private IConstituentOfEntity createITConstructor(final IProgramElement node) {
		IConstituentOfEntity element = null;
		element =
			this.factory.createInterTypeConstructor(node
				.getName()
				.toCharArray());
		element.setName(node.getName().toCharArray());
		return element;
	}
	private IConstituentOfEntity createITField(final IProgramElement node) {
		IInterTypeElement element = null;
		element =
			this.factory.createInterTypeField(
				node.getName().toCharArray(),
				node.getCorrespondingType().toCharArray());
		element.setName(node.getName().toCharArray());
		this.setITRelationShip(node, element);

		return element;
	}
	private IConstituentOfEntity createITMethod(final IProgramElement node) {
		IInterTypeMethod element = null;
		element =
			this.factory.createInterTypeMethode(node.getName().toCharArray());
		element.setName(node.getName().toCharArray());

		this.setITRelationShip(node, element);

		//Create the method
		final String[] signature =
			this.processMethodSignature(node.toLabelString());
		//		logger.debug("Method name: " + signature[1]);
		//		logger.debug("Method name: " + signature[1] + signature[2]);
		final IMethod it_method =
			this.factory.createMethod(
				(signature[1] + signature[2]).toCharArray(),
				(signature[1] + signature[2]).toCharArray());

		it_method.setName(signature[1].toCharArray());

		this.setVisibility(node, it_method);

		//		logger.debug("Method visibility: " + it_method.getVisibility());

		it_method.setReturnType(node.getCorrespondingType().toCharArray());

		//		logger.debug("Method RType: " + it_method.getReturnType());

		final Iterator ite_param_type = node.getParameterTypes().iterator();
		//	Iterator ite_param_name = node.getParameterNames().iterator();

		final StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("Method Params: ");
		while (ite_param_type.hasNext()) {
			// Yann 2009/05/02: Parameters!
			// Parameters do not use String anymore :-)
			final String paramType = String.valueOf(ite_param_type.next());
			IEntity entity =
				this.codeLevelModel.getTopLevelEntityFromID(paramType);
			if (entity == null) {
				if (Util.isPrimtiveType(paramType.toCharArray())) {
					entity =
						this.factory.createPrimitiveEntity(paramType
							.toCharArray());
				}
				else {
					entity =
						this.factory.createGhost(
							paramType.toCharArray(),
							paramType.toCharArray());
					this.codeLevelModel.addConstituent(entity);
				}
			}
			final IParameter param =
				this.factory.createParameter(entity, Constants.CARDINALITY_ONE);
			it_method.addConstituent(param);
			paramBuffer.append(param.getTypeName());
			paramBuffer.append(" ");
			paramBuffer.append(param.getName());
			paramBuffer.append(", ");
		}

		//		logger.debug(paramBuffer.toString());
		element.setMethod(it_method);
		return element;
	}
	private IConstituentOfEntity createMethod(final IProgramElement node) {
		IConstituentOfEntity element = null;
		element =
			this.factory.createMethod(node.getName().toCharArray(), node
				.getName()
				.toCharArray());
		return element;
	}
	private IConstituentOfEntity createPointcut(final IProgramElement node) {
		IConstituentOfEntity element = null;
		element = this.factory.createPointcut(node.getName().toCharArray());
		element.setName(node.getName().toCharArray());
		//		TODO: setRelationShip?
		return element;
	}
	//For JUNIT TEST
	public HashMap getImportMap() {
		return this.mapImportEntity;
	}
	protected void postProcess(final IProgramElement node) {
		final String kind = node.getKind().toString();
		if (kind.equals(IProgramElement.Kind.ASPECT.toString())) {
			this.walkingOverAspect = false;
			final IPackage enclosingPackage;
			if (!this.codeLevelModel
				.doesContainConstituentWithID(AspectCreator.ASPECT_PACKAGE_ID
					.toCharArray())) {

				enclosingPackage =
					Factory.getInstance().createPackage(
						AspectCreator.ASPECT_PACKAGE_ID.toCharArray());
				this.codeLevelModel.addConstituent(enclosingPackage);
			}
			else {
				enclosingPackage =
					(IPackage) this.codeLevelModel
						.getConstituentFromID(AspectCreator.ASPECT_PACKAGE_ID);
			}
			enclosingPackage.addConstituent(this.currentAspect);
		}
	}
	protected void preProcess(final IProgramElement node) {
		this.factory = (IAspectJFactory) this.codeLevelModel.getFactory();

		final String kind = node.getKind().toString();

		if (kind.equals(IProgramElement.Kind.IMPORT_REFERENCE.toString())
				&& !node.getName().equals("import declarations")) {
			//Permet de sauvegarder les entités importées par les aspects afin d'y accéder plus facilement.
			this.saveImportDeclaration(node);
		}
		if (kind.equals(IProgramElement.Kind.ASPECT.toString())) {
			this.walkingOverAspect = true;
			this.currentAspect =
				this.factory.createAspect((node.getPackageName() + "." + node
					.getName()).toCharArray());
			this.currentAspect.setName((node.getPackageName() + "." + node
				.getName()).toCharArray());
			this.setVisibility(node, this.currentAspect);
		}
		if (this.walkingOverAspect()) {
			IConstituentOfEntity currentConstituent = null;

			if (kind.equals(IProgramElement.Kind.POINTCUT.toString())) {
				currentConstituent = this.createPointcut(node);
			}
			if (kind.equals(IProgramElement.Kind.ADVICE.toString())) {
				currentConstituent = this.createAdvice(node);
			}
			if (kind.equals(IProgramElement.Kind.FIELD.toString())) {
				currentConstituent = this.createField(node);
			}
			if (kind.equals(IProgramElement.Kind.METHOD.toString())) {
				currentConstituent = this.createMethod(node);
			}
			if (kind.equals(IProgramElement.Kind.CONSTRUCTOR.toString())) {
				currentConstituent = this.createConstructor(node);
			}
			if (kind.equals(IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR
				.toString())) {
				currentConstituent = this.createITConstructor(node);
			}
			if (kind.equals(IProgramElement.Kind.INTER_TYPE_FIELD.toString())) {
				currentConstituent = this.createITField(node);
			}
			if (kind.equals(IProgramElement.Kind.INTER_TYPE_METHOD.toString())) {
				currentConstituent = this.createITMethod(node);
			}
			if (kind.equals(IProgramElement.Kind.DECLARE_PARENTS.toString())) {
				currentConstituent = this.createDeclareParent(node);
			}
			if (currentConstituent != null) {
				this.setVisibility(node, currentConstituent);
				this.currentAspect.addConstituent(currentConstituent);
			}
		}
	}
	private String[] processMethodSignature(final String signature) {
		final String[] process = signature.split("(\\.|\\()");

		if (process.length != 3) {
			System.err.println("Error on the signture process of " + signature);
		}
		else {
			process[2] = "(" + process[2];
		}
		return process;
	}
	private void saveImportDeclaration(final IProgramElement node) {
		final IFirstClassEntity anEntity =
			this.codeLevelModel.getTopLevelEntityFromID(node.toLabelString());

		if (anEntity != null) {
			final StringTokenizer st =
				new StringTokenizer(String.valueOf(anEntity.getName()), ".");
			String name = null;

			while (st.hasMoreTokens()) {
				name = st.nextToken();
			}

			if (name != null) {
				this.mapImportEntity.put(name, anEntity);
			}
		}
	}
	private void setITRelationShip(
		final IProgramElement node,
		final IInterTypeElement it_element) {
		//			Set Target
		final List relations = this.imap.get(node);

		//		logger.debug("Extracting target for " + it_element.getName());

		if (relations != null) {
			//get handle
			final IRelationship rel = (IRelationship) relations.get(0);
			final List targets = rel.getTargets();
			final String handle = (String) targets.get(0);

			//get IProgramElement
			final IProgramElement target =
				this.hierarchy.findElementForHandle(handle);

			//get packagename+name
			if (target != null) {
				final String target_name =
					target.getPackageName() + "." + target.getName();

				//get Constituent from ICodeLevelModel
				final IConstituent target_entity =
					this.codeLevelModel.getConstituentFromName(target_name);

				//set Target
				if (target_entity != null
						&& target_entity instanceof IFirstClassEntity) {
					//					logger.debug("Target found: " + target_entity.getName());
					it_element
						.setTargetEntity((IFirstClassEntity) target_entity);
				}
				//				else {
				//					logger.warn(
				//						"Target " + target_name + " not found in the model.");
				//				}
			}
		}
		//		else {
		//			logger.warn("No relations found for " + it_element.getName());
		//		}
	}
	private void setVisibility(
		final IProgramElement node,
		final IConstituent element) {

		if (node.getAccessibility().equals(Accessibility.PUBLIC)) {
			element.setVisibility(Access.ACC_PUBLIC);
		}
		if (node.getAccessibility().equals(Accessibility.PROTECTED)) {
			element.setVisibility(Access.ACC_PROTECTED);
		}
		if (node.getAccessibility().equals(Accessibility.PRIVATE)) {
			element.setVisibility(Access.ACC_PRIVATE);
		}
	}

	private boolean walkingOverAspect() {
		return this.walkingOverAspect;
	}
}

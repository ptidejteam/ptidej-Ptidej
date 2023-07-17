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
package padl.creator.msefile;

import java.util.HashMap;
import java.util.Map;
import padl.creator.msefile.misc.Attribute;
import padl.creator.msefile.misc.Element;
import padl.creator.msefile.misc.Package;
import padl.creator.msefile.misc.Value;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.IUseRelationship;
import padl.kernel.impl.Factory;

/**
 * @author Yann-Gaël Guéhéneuc, Foutse Khomh
 * @since  2007/06/10
 */
public class FAMIXBuilder implements MSEBuilder {
	public ICodeLevelModel build(final Element[] someElements) {
		final ICodeLevelModel aLevelModel =
			Factory.getInstance().createCodeLevelModel(
				"Code-level model built using MSECreator");

		// Yann 2007/06/09: Law and Order...
		// I must iterate over the elements first to build the 
		// appropriate classes, then the methods and fields,
		// and finally the method invocations and other stuff.

		// First, I create of map of ids of elements.
		//	final Map ids = new HashMap();
		//	for (int i = 0; i < someElements.length; i++) {
		//		final Element element = someElements[i];
		//		ids.put(this.getID(element), element);
		//	}

		// Second, I create a map of packages, needed to
		// then build the classes with correct names.
		final Map packages = new HashMap();
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Package")) {
				final String id = this.getID(element);
				final String name = this.getName(element);
				final String idref = this.getPackagedInRef(element);
				if (idref == null) {
					packages.put(id, new Package(name));
				}
				else {
					packages.put(id, new Package(name, idref));
				}
			}
		}

		// Third, I build the classes and interfaces.
		final Map entities = new HashMap();

		// Yann 2009/05/02: Super!
		// I must create the default "root" of the hierarchy
		// because all classes, parameters... refer to it...
		{
			final IFirstClassEntity firstClassEntity =
				Factory.getInstance().createGhost(
					"java.lang.Object".toCharArray(),
					"Object".toCharArray());
			entities.put("java.lang.Object", firstClassEntity);
			aLevelModel.addConstituent(firstClassEntity);
		}

		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Class")) {
				final String id = this.getID(element);
				final String idref = this.getPackagedInRef(element);
				final String packageName =
					((Package) packages.get(idref)).getFullName(packages);
				final String name = this.getName(element);

				final IFirstClassEntity firstClassEntity;
				if (this.getInterfaceness(element).equals("true")) {
					firstClassEntity =
						Factory.getInstance().createInterface(
							(packageName + '.' + name).toCharArray(),
							name.toCharArray());
				}
				else {
					firstClassEntity =
						Factory.getInstance().createClass(
							(packageName + '.' + name).toCharArray(),
							name.toCharArray());
					if (this.getAbstractness(element).equals("true")) {
						((IClass) firstClassEntity).setAbstract(true);
					}
				}

				entities.put(id, firstClassEntity);
				aLevelModel.addConstituent(firstClassEntity);
			}
		}

		// Fourth, I can now move on to building methods and fields.
		final Map methods = new HashMap();
		final Map methodsowners = new HashMap();
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Method")) {
				final String id = this.getID(element);
				final String idref = this.getBelongsToRef(element);
				final String name = this.getName(element) + id;
				final String accessQualifier =
					this.getAccessControlQualifier(element);

				final IOperation method;
				if (this.getGetterness(element).equals("getter")) {
					if (this.getSignature(element).indexOf(':') > 0) {
						method =
							Factory.getInstance().createSetter(
								(name + ':').toCharArray(),
								(name + ':').toCharArray());
					}
					else {
						method =
							Factory.getInstance().createGetter(
								name.toCharArray(),
								name.toCharArray());
					}
				}
				else {
					method =
						Factory.getInstance().createMethod(
							name.toCharArray(),
							name.toCharArray());
				}

				if (this.getHasClassScope(element).equals("true")) {
					method.setAbstract(true);
				}
				if (accessQualifier.equals("public")) {
					method.setPublic(true);
				}
				else if (accessQualifier.equals("protected")) {
					method.setProtected(true);
				}
				else if (accessQualifier.equals("private")) {
					method.setPrivate(true);
				}

				methods.put(id, method);
				methodsowners.put(id, idref);
				((IFirstClassEntity) entities.get(idref))
					.addConstituent(method);
			}
		}
		// I add parameters to methods
		final Map parameters = new HashMap();
		final Map parameterowners = new HashMap();

		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.FormalParameter")) {
				final String id = this.getID(element);
				final String idref = this.getBelongsToRef(element);
				final String name = this.getName(element);

				final IParameter parameter =
					Factory.getInstance().createParameter(
						(IEntity) entities.get("java.lang.Object"),
						name.toCharArray(),
						Constants.CARDINALITY_ONE);
				((IOperation) methods.get(idref)).addConstituent(parameter);
				parameters.put(id, parameter);
				parameterowners.put(id, idref);
			}
		}
		// we build now fields
		final Map fields = new HashMap();
		final Map fieldowners = new HashMap();
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Attribute")) {
				final String id = this.getID(element);
				final String idref = this.getBelongsToRef(element);
				final String name = this.getName(element) + id;
				final String accessQualifier =
					this.getAccessControlQualifier(element);

				final IField field =
					Factory.getInstance().createField(
						name.toCharArray(),
						name.toCharArray(),
						"java.lang.Object".toCharArray(),
						1);
				// to complete type and cardinality

				if (this.getHasClassScope(element).equals("true")) {
					field.setStatic(true);
				}

				if (accessQualifier.equals("public")) {

					field.setPublic(true);
				}
				else if (accessQualifier.equals("protected")) {

					field.setProtected(true);
				}
				else if (accessQualifier.equals("private")) {
					field.setPrivate(true);
				}

				fields.put(id, field);
				fieldowners.put(id, idref);
				((IFirstClassEntity) entities.get(idref)).addConstituent(field);
			}
		}

		// Fifth, I link classes with inheritance links.
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.InheritanceDefinition")) {
				final String subclassRef = this.getSubclassRef(element);
				final IFirstClassEntity subclass =
					(IFirstClassEntity) entities.get(subclassRef);
				final String superclassRef = this.getSuperclassRef(element);
				final IFirstClassEntity superclass =
					(IFirstClassEntity) entities.get(superclassRef);

				subclass.addInheritedEntity(superclass);
			}
		}

		// Sixth, I build now invocations.
		final Map invocations = new HashMap();
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Invocation")) {
				final String id = this.getID(element);
				final String idref = this.getInvokedByRef(element);
				final String candidateref = this.getCandidate(element);
				final String variableref = this.getreveivVar(element);
				final String invokeref = this.getInvokes(element);

				final IMethodInvocation invocation =
					Factory.getInstance().createMethodInvocation(
						1,
						1,
						1,
						(IFirstClassEntity) entities.get(methodsowners
							.get(invokeref)),
						(IFirstClassEntity) entities.get(methodsowners
							.get(idref)));

				// This second invocation refers to the case where we have more than one candidate
				final IMethodInvocation invocation1 =
					Factory.getInstance().createMethodInvocation(
						1,
						1,
						1,
						(IFirstClassEntity) entities.get(methodsowners
							.get(candidateref)),
						(IFirstClassEntity) entities.get(methodsowners
							.get(idref)));

				//invocation.setID(id);

				invocation.setCalledMethod((IOperation) methods.get(invokeref));

				//invocation1.setID(id + "s");

				invocation.addCallingField((IField) fields.get(variableref));
				invocation1.setCalledMethod((IOperation) methods
					.get(candidateref));

				invocation1.addCallingField((IField) fields.get(variableref));

				if ((IOperation) methods.get(idref) != null) {
					((IOperation) methods.get(idref))
						.addConstituent(invocation);
					invocations.put(id, invocation);

					((IOperation) methods.get(idref))
						.addConstituent(invocation1);
					invocations.put(id + "s", invocation1);
				}
			}
		}
		//Seventh, I build accesses now
		//Here I considered only access to the attributes of classes and parameters of methods, I did'nt considered
		//accesses to local variables of a method.
		final Map accesses = new HashMap();
		for (int i = 0; i < someElements.length; i++) {
			final Element element = someElements[i];
			if (element.getType().equals("FAMIX.Access")) {
				final String id = this.getID(element);
				final String accessesref = this.getaccesses(element);
				final String accesseInref = this.getaccessedIn(element);

				if ((IFirstClassEntity) entities.get(fieldowners
					.get(accessesref)) != null) {
					final IUseRelationship access =
						Factory.getInstance().createUseRelationship(
							id.toCharArray(),
							(IFirstClassEntity) entities.get(fieldowners
								.get(accessesref)),
							1);

					if (this.getreadWriteAccess(element).equals("true")) {
						access.setFinal(false);
					}

					if ((IFirstClassEntity) entities.get(methodsowners
						.get(accesseInref)) != null) {
						((IFirstClassEntity) entities.get(methodsowners
							.get(accesseInref))).addConstituent(access);
					}
					accesses.put(id, access);
				}
				//we now deal with access to parameters

				if ((IFirstClassEntity) entities.get(parameterowners
					.get(accessesref)) != null) {
					final IUseRelationship accessPar =
						Factory.getInstance().createUseRelationship(
							id.toCharArray(),
							(IFirstClassEntity) entities.get(parameterowners
								.get(accessesref)),
							1);

					if (this.getreadWriteAccess(element).equals("true")) {
						accessPar.setFinal(false);
					}

					if ((IFirstClassEntity) entities.get(methodsowners
						.get(accesseInref)) != null) {
						((IFirstClassEntity) entities.get(methodsowners
							.get(accesseInref))).addConstituent(accessPar);
					}
					accesses.put(id, accessPar);
				}
			}
		}

		return aLevelModel;
	}
	private String getAbstractness(final Element anElement) {
		final Attribute id = anElement.getAttribute("isAbstract");
		return id.getValue("primitive").getValue();
	}
	private String getAccessControlQualifier(final Element anElement) {
		final Attribute id = anElement.getAttribute("accessControlQualifier");
		return id.getValue("primitive").getValue();
	}
	private String getaccessedIn(final Element anElement) {
		final Attribute id = anElement.getAttribute("accessedIn");
		return id.getValue("idref").getValue();

	}
	private String getaccesses(final Element anElement) {
		final Attribute id = anElement.getAttribute("accesses");

		return id.getValue("idref").getValue();

	}
	private String getBelongsToRef(final Element anElement) {
		final Attribute id = anElement.getAttribute("belongsTo");
		return id.getValue("idref").getValue();
	}
	private String getCandidate(final Element anElement) {
		final Attribute id = anElement.getAttribute("candidate");
		if (id != null) {
			return id.getValue("idref").getValue();
		}
		else {
			return null;
		}

	}
	private String getGetterness(final Element anElement) {
		final Attribute kind = anElement.getAttribute("kind");
		if (kind == null) {
			return "";
		}
		else {
			return kind.getValue("primitive").getValue();
		}
	}
	private String getHasClassScope(final Element anElement) {
		final Attribute id = anElement.getAttribute("hasClassScope");
		return id.getValue("primitive").getValue();
	}
	private String getID(final Element anElement) {
		final Attribute id = anElement.getAttribute("id");
		return id.getValue("primitive").getValue();
	}
	private String getInterfaceness(final Element anElement) {
		final Attribute id = anElement.getAttribute("isAbstract");
		return id.getValue("primitive").getValue();
	}
	private String getInvokedByRef(final Element anElement) {
		final Attribute id = anElement.getAttribute("invokedBy");
		return id.getValue("idref").getValue();

	}
	private String getInvokes(final Element anElement) {
		final Attribute id = anElement.getAttribute("invokes");
		return id.getValue("primitive").getValue();

	}

	private String getName(final Element anElement) {
		final Attribute id = anElement.getAttribute("name");
		return id.getValue("primitive").getValue();
	}
	private String getPackagedInRef(final Element anElement) {
		final Attribute attribute = anElement.getAttribute("packagedIn");
		if (attribute != null) {
			final Value idref = attribute.getValue("idref");
			return idref.getValue();
		}
		else {
			return null;
		}
	}
	private String getreadWriteAccess(final Element anElement) {
		final Attribute id = anElement.getAttribute("readWriteAccess");
		return id.getValue("primitive").getValue();
	}
	private String getreveivVar(final Element anElement) {
		final Attribute id = anElement.getAttribute("receivingVariable");
		if (id != null) {
			return id.getValue("idref").getValue();
		}
		else {
			return null;
		}

	}
	private String getSignature(final Element anElement) {
		final Attribute id = anElement.getAttribute("signature");
		return id.getValue("primitive").getValue();
	}
	private String getSubclassRef(final Element anElement) {
		final Attribute id = anElement.getAttribute("subclass");
		return id.getValue("idref").getValue();
	}
	private String getSuperclassRef(final Element anElement) {
		final Attribute id = anElement.getAttribute("superclass");
		return id.getValue("idref").getValue();
	}

}

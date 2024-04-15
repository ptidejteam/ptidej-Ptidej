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
package padl.creator.javafile.eclipse.visitor;

import java.io.PrintWriter;
import java.util.Iterator;

import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;
import util.io.NullWriter;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.WriterOutputStream;

public class PADLPrinterVisitor implements IWalker {
	private String currentEntity;
	private String currentPackage = "./result1/";
	private boolean inFile;
	private PrintWriter writer;

	/**
	 * 
	 */
	public PADLPrinterVisitor() {
		this.inFile = false;
	}

	/**
	 * 
	 * @param _inFile
	 */
	public PADLPrinterVisitor(final boolean _inFile) {
		this.inFile = _inFile;
	}

	@Override
	public void close(final IAbstractModel anAbstractModel) {
		this.writer.close();
	}

	@Override
	public void close(final IClass aClass) {
		this.printTopEntityClose(aClass);
	}

	@Override
	public void close(final IConstructor aConstructor) {
		this.writer.println();
		this.writer.println(
				"		End of constructor " + aConstructor.getDisplayName());
	}

	@Override
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}

	@Override
	public void close(final IGetter aGetter) {
		this.writer.println();
		this.writer.println("		End of getter " + aGetter.getDisplayName());
	}

	@Override
	public void close(final IGhost aGhost) {
		this.printTopEntityClose(aGhost);
	}

	@Override
	public void close(final IInterface anInterface) {
		this.printTopEntityClose(anInterface);
	}

	@Override
	public void close(final IMemberClass aMemberClass) {
		this.printTopEntityClose(aMemberClass);
	}

	@Override
	public void close(final IMemberGhost aMemberGhost) {
		this.printTopEntityClose(aMemberGhost);
	}

	@Override
	public void close(final IMemberInterface aMemberInterface) {
		this.printTopEntityClose(aMemberInterface);
	}

	@Override
	public void close(final IMethod aMethod) {
		this.writer.println();
		this.writer.println("		End of method " + aMethod.getDisplayName());
	}

	@Override
	public void close(final IPackage aPackage) {
	}

	@Override
	public void close(final IPackageDefault aPackage) {
	}

	@Override
	public void close(final ISetter aSetter) {
		this.writer.println();
		this.writer.println("		End of setter " + aSetter.getDisplayName());
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Object getResult() {
		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public void open(final IAbstractModel anAbstractModel) {
		if (this.inFile) {
			this.writer = new PrintWriter(new WriterOutputStream(ProxyDisk
					.getInstance().fileTempOutput(this.currentEntity)));
		}
		else {
			// TODO: Remove, unnecessary with Output.getInstance().normalOutput()
			this.writer = new PrintWriter(new NullWriter());
		}
	}

	@Override
	public void open(final IClass aClass) {
		this.printTopEntityOpen(aClass);
	}

	@Override
	public void open(final IConstructor aConstructor) {
		this.writer.println();
		this.writer.println(
				"		Start of constructor " + aConstructor.toString());
	}

	@Override
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}

	@Override
	public void open(final IGetter aGetter) {
		this.writer.println();
		this.writer.println("		Statr of getter " + aGetter.toString());
	}

	@Override
	public void open(final IGhost aGhost) {
		this.printTopEntityOpen(aGhost);
	}

	@Override
	public void open(final IInterface anInterface) {
		this.printTopEntityOpen(anInterface);
	}

	@Override
	public void open(final IMemberClass aMemberClass) {
		this.printTopEntityOpen(aMemberClass);
	}

	@Override
	public void open(final IMemberGhost aMemberGhost) {
		this.printTopEntityOpen(aMemberGhost);
	}

	@Override
	public void open(final IMemberInterface aMemberInterface) {
		this.printTopEntityOpen(aMemberInterface);
	}

	@Override
	public void open(final IMethod aMethod) {
		this.writer.println();
		this.writer.println("		Start of Method " + aMethod.getDisplayID());

	}

	@Override
	public void open(final IPackage aPackage) {
		this.currentPackage = this.currentPackage + aPackage.getDisplayName()
				+ "/";
	}

	@Override
	public void open(final IPackageDefault aPackage) {
		this.currentPackage = this.currentPackage + aPackage.getDisplayName()
				+ "/";
	}

	@Override
	public void open(final ISetter aSetter) {
		this.writer.println();
		this.writer.println("		Start of setter " + aSetter.toString());

	}

	void printTopEntityClose(final IFirstClassEntity entity) {
		this.writer.println();
		this.writer.println("	End of type " + entity.getClass().toString()
				+ " " + entity.getDisplayName());
		if (this.inFile) {
			if (!entity.getDisplayID().contains("$")) {
				this.writer.close();
			}
		}
	}

	void printTopEntityOpen(final IFirstClassEntity entity) {
		if (!entity.getDisplayID().contains("$")) {
			final String name = entity.getDisplayName();
			//	final int lastIndex = name.lastIndexOf('.');

			this.currentEntity = this.currentPackage + name;

			this.currentEntity = this.currentEntity.replace('>', '+');
			this.currentEntity = this.currentEntity.replace('<', '-');
			this.currentEntity = this.currentEntity.replace('[', '-');
			this.currentEntity = this.currentEntity.replace('[', '+');
		}

		this.writer.println();
		this.writer.println("	Start of type " + entity.getClass().toString()
				+ " entity.toString()" + entity.toString());
		this.writer.println(" 		ID " + entity.getDisplayID());
		final Iterator<?> iter = entity.getIteratorOnInheritedEntities();

		this.writer.println();
		this.writer.println("		Super-classes and interfaces");
		while (iter.hasNext()) {
			final IFirstClassEntity superEntity = (IFirstClassEntity) iter
					.next();

			this.writer.println("			" + superEntity.toString());
		}

	}

	@Override
	public void reset() {

	}

	@Override
	public final void unknownConstituentHandler(final String aCalledMethodName,
			final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
				.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
				.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	@Override
	public void visit(final IAggregation anAggregation) {

	}

	@Override
	public void visit(final IAssociation anAssociation) {

	}

	@Override
	public void visit(final IComposition aComposition) {

	}

	@Override
	public void visit(final IContainerAggregation aContainerAggregation) {

	}

	@Override
	public void visit(final IContainerComposition aContainerComposition) {

	}

	@Override
	public void visit(final ICreation aCreation) {

		this.writer.println();
		this.writer.println(" Creation " + aCreation.toString());

	}

	@Override
	public void visit(final IField aField) {

		this.writer.println();
		this.writer.println("		Field " + aField.toString()
				+ ", cardinality " + aField.getCardinality());

	}

	@Override
	public void visit(final IMethodInvocation aMethodInvocation) {

		this.writer.println();
		this.writer.println("MethodINVOCATIIIIIIIIIIIIIIIIIIION");
		this.writer.println(aMethodInvocation.toString());
		this.writer.println("Type " + aMethodInvocation.getType());
		/*this.writer.println("MethodINVOCATIIIIIIIIIIIIIIIIIIION cardinality = "
				+ aMethodInvocation.getCardinality()+" ID= "+aMethodInvocation.getDisplayID()+" name= "+" comment :"+aMethodInvocation.getComment());
		if (aMethodInvocation.getFieldDeclaringEntity() != null) {
			this.writer.println(" declaring entity    "
					+ aMethodInvocation.getFieldDeclaringEntity().toString());
		}
		final IField field = aMethodInvocation.getFirstCallingField();
		if (field != null) {
			this.writer.println(" invocationField " + field.toString());
			//this.writer.println(aMethodInvocation.toString());
			//when I add the field, I have pb with the toString
		}*/
	}

	@Override
	public void visit(final IParameter aParameter) {

		this.writer.println();
		this.writer.println("			Parameter :" + aParameter.toString()
				+ ", cardinality" + aParameter.getCardinality());

	}

	@Override
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	@Override
	public void visit(final IUseRelationship aUse) {

	}
}

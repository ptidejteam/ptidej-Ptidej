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
package padl.pagerank.utils;

import java.util.Iterator;
import padl.cpp.kernel.ICPPClass;
import padl.cpp.kernel.ICPPGhost;
import padl.cpp.kernel.ICPPMemberClass;
import padl.cpp.kernel.ICPPMemberGhost;
import padl.cpp.kernel.IDestructor;
import padl.cpp.kernel.IEnum;
import padl.cpp.kernel.IGlobalField;
import padl.cpp.kernel.IGlobalFunction;
import padl.cpp.kernel.IMemberStructure;
import padl.cpp.kernel.IStructure;
import padl.cpp.kernel.IUnion;
import padl.cpp.visitor.ICPPGenerator;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IOperation;
import padl.kernel.IPackageDefault;
import padl.kernel.impl.Factory;

public class InputDataGeneratorWith9RelationsForCPP extends
		InputDataGeneratorWith9Relations implements ICPPGenerator {

	public InputDataGeneratorWith9RelationsForCPP(
		boolean withGhosts,
		boolean withMembers) {

		super(withGhosts, withMembers);
	}
	public void close(final ICPPClass p) {
		this.close((IConstituent) p);
	}
	public void close(final ICPPGhost p) {
		this.close((IConstituent) p);
	}
	public void close(final ICPPMemberClass p) {
		this.close((IConstituent) p);
	}
	public void close(final ICPPMemberGhost p) {
		this.close((IConstituent) p);
	}
	public void close(final IDestructor p) {
		this.close((IOperation) p);
	}
	public void close(final IEnum p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IGlobalField p) {
		// Do nothing because I already treat a global field as a field in open(IGlobalField).  
	}
	public void close(final IGlobalFunction p) {
		this.close((IOperation) p);
	}
	public void close(final IMemberStructure p) {
		this.close((IConstituent) p);
	}
	public void close(final IStructure p) {
		this.close((IFirstClassEntity) p);
	}
	public void close(final IUnion p) {
		this.close((IFirstClassEntity) p);
	}
	public void open(final IAbstractModel aModel) {
		super.open(aModel);

		final IPackageDefault defaultPackage =
			(IPackageDefault) aModel
				.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
		final char[] dummyClassID = "DummyForGlobalFunctions".toCharArray();
		// TODO The follow line should have the same effect as the enabled "if"
		// but it does not because the dummyClass added to the default package
		// does not seem to be added to the top-level entities?
		// if (!aModel.doesContainTopLevelEntityWithID(dummyClassID)) {
		if (!defaultPackage.doesContainConstituentWithID(dummyClassID)) {
			// Yann 2015/07/07: Creation of a Dummy ghost to attach global functions
			final IClass dummyClass =
				Factory.getInstance().createClass(dummyClassID, dummyClassID);
			defaultPackage.addConstituent(dummyClass);

			// Yann 2015/07/16: I now remove all the global functions from the top-level
			// entities and add them back into the dummy class to satisfy MadMatch parser.
			final Iterator interator = aModel.getIteratorOnTopLevelEntities();
			while (interator.hasNext()) {
				final IConstituent constituent =
					(IConstituent) interator.next();
				if (constituent instanceof IGlobalFunction) {
					aModel.removeTopLevelEntityFromID(constituent.getID());
					dummyClass.addConstituent(constituent);
				}
			}
		}
	}
	public void open(final ICPPClass p) {
		this.open((IClass) p);
	}
	public void open(final ICPPGhost p) {
		this.open((IGhost) p);
	}
	public void open(ICPPMemberClass p) {
		this.open((IMemberClass) p);
	}
	public void open(final ICPPMemberGhost p) {
		this.open((IMemberGhost) p);
	}
	public void open(final IDestructor p) {
		this.open((IOperation) p);
	}
	public void open(final IEnum p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IGlobalField p) {
		this.visit(p);
	}
	public void open(final IGlobalFunction p) {
		this.open((IOperation) p);
	}
	public void open(IMemberStructure p) {
		this.open((IStructure) p);
	}
	public void open(final IStructure p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IUnion p) {
		this.open((IFirstClassEntity) p);
	}
	protected boolean isInterestingTargetEntity(final IConstituent aConstituent) {
		return !(aConstituent instanceof IGlobalFunction)
				&& super.isInterestingTargetEntity(aConstituent);
	}
}

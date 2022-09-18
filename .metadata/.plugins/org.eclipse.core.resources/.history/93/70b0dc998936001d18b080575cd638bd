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
package padl.creator.classfile.relationship;

import java.util.List;
import java.util.Map;
import padl.creator.classfile.util.ExtendedMethodInfo;
import padl.creator.classfile.util.ExtendedMethodInvocation;
import padl.creator.classfile.util.Utils;
import padl.event.IModelListener;
import padl.event.InvokeEvent;
import padl.kernel.Constants;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class Context implements Cloneable {
	private static Map MapOfIDsEntities;
	public static void initialise(final Map aMapOfIDsEntities) {
		Context.MapOfIDsEntities = aMapOfIDsEntities;
	}
	private int cardinality = Constants.CARDINALITY_ONE;
	private ConstantPool constantPool;
	private IFirstClassEntity currentEntity;
	private final IAbstractLevelModel currentModel;
	private boolean internalFieldDeclaration = false;
	private char[] invocationSiteName = null;
	private boolean invocationSiteStatic = false;
	private List messageTypes;
	private ExtendedMethodInfo methodInfo;
	private char[] methodName;

	private byte[] rawCode;
	public Context(final Context oldContext) {
		this.currentEntity = oldContext.currentEntity;
		this.currentModel = oldContext.currentModel;
		this.methodInfo = oldContext.methodInfo;
		this.constantPool = oldContext.constantPool;
		this.messageTypes = oldContext.messageTypes;
		this.rawCode = oldContext.rawCode;
		this.methodName = oldContext.methodName;
	}
	public Context(final IAbstractLevelModel anAbstractLevelModel) {
		this.currentModel = anAbstractLevelModel;
	}
	//	public void addEntity(final IEntity entity) {
	//		try {
	//			this.currentModel.addConstituent(entity);
	//		}
	//		catch (final ModelDeclarationException mde) {
	//			// Yann 2006/02/10: Duplicate...
	//			// I solved most of the problems of duplicate
	//			// actors by reversing the loops in
	//			// createElements()
	//			mde.printStackTrace(Output.getInstance().errorOutput());
	//		}
	//	}
	//	public void addGhost(final String aName) {
	//		try {
	//			this.currentModel
	//				.addConstituent(Factory.getInstance().createGhost(aName));
	//		}
	//		catch (final ModelDeclarationException mde) {
	//			// Yann 2006/02/10: Duplicate...
	//			// I solved most of the problems of duplicate
	//			// actors by reversing the loops in
	//			// createElements()
	//			mde.printStackTrace(Output.getInstance().errorOutput());
	//		}
	//	}
	public void addMethodInvocation(
		final ExtendedMethodInvocation methodInvocation) {
		if (!this.messageTypes.contains(methodInvocation)) {
			this.currentModel.fireModelChange(
				IModelListener.INVOKE_RECOGNIZED,
				new InvokeEvent(
					methodInvocation.getOriginEntity(),
					methodInvocation.getMethodInvocation()));
			this.messageTypes.add(methodInvocation);
		}
	}
	public IFirstClassEntity getEntityFromID(final char[] anID) {
		//	final IEntity entity = (IEntity) this.mapOfIDsEntities.get(anID);
		//	final IEntity entity2 =
		//		Utils.searchForEntity(this.currentModel, anID);
		return Utils.getEntityOrCreateGhost(
			this.currentModel,
			anID,
			Context.MapOfIDsEntities);
	}
	public int getCardinality() {
		return this.cardinality;
	}
	public ConstantPool getConstantPool() {
		return this.constantPool;
	}
	public IFirstClassEntity getCurrentEntity() {
		return this.currentEntity;
	}
	public IOperation getCurrentMethod() {
		return (IOperation) this.currentEntity
			.getConstituentFromID(this.methodName);
	}
	public IAbstractLevelModel getCurrentModel() {
		return this.currentModel;
	}
	public char[] getInvocationSiteName() {
		return this.invocationSiteName;
	}
	public int getMethodAccess() {
		return this.methodInfo.getVisibility();
	}
	public List getMethodInvocations() {
		return this.messageTypes;
	}
	public char[] getMethodName() {
		return this.methodName;
	}
	public byte[] getRawCode() {
		return this.rawCode;
	}
	public void initialise(
		final IFirstClassEntity anEntity,
		final ExtendedMethodInfo aMethodInfo,
		final List someMessageTypes) {

		this.currentEntity = anEntity;
		this.methodInfo = aMethodInfo;
		this.messageTypes = someMessageTypes;

		this.constantPool = this.methodInfo.getDeclaringClassConstantPool();
		//	if (Utils.isSpecialMethod(this.methodInfo.getName())) {
		this.methodName = Utils.computeSignature(this.methodInfo);
		//	}
		//	else {
		//		this.methodName = this.methodInfo.getName();
		//	}

		final AttrInfoList attributeInfoList = this.methodInfo.getAttributes();
		final CodeAttrInfo codeAttributeInfo =
			(CodeAttrInfo) attributeInfoList.get("Code");
		if (codeAttributeInfo != null) {
			this.rawCode = codeAttributeInfo.getCode();
		}
		else {
			this.rawCode = new byte[0];
		}
	}
	public boolean isInternalFieldDeclaration() {
		return this.internalFieldDeclaration;
	}
	public boolean isInvocationSiteStatic() {
		return this.invocationSiteStatic;
	}
	public void setCardinality(final int cardinality) {
		this.cardinality = cardinality;
	}
	public void setInternalFieldDeclaration(final boolean isInternal) {
		this.internalFieldDeclaration = isInternal;
	}
	public void setInvocationSiteName(final char[] invocationSiteName) {
		this.invocationSiteName = invocationSiteName;
	}
	public void setInvocationSiteStatic(final boolean isStatic) {
		this.invocationSiteStatic = isStatic;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		return buffer.toString();
	}
}

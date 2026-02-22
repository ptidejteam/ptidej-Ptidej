/**
 * @author Mathieu Lemoine
 * @created 2008-08-18
 * 
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package padl.creator.javafile.javac.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import jct.kernel.*;
import jct.util.IJCTContainer;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IEntity;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;
import padl.statement.kernel.IIfInstruction;
import padl.statement.kernel.ISwitchInstruction;
import padl.statement.kernel.impl.StatementFactory;
import padl.util.Util;
import util.io.ProxyConsole;

/**
 * This interface is a default for a Visiting facility
 *
 * @author Mathieu Lemoine
 * @author Yann-Gaël Guéhéneuc
 */
public class JCTtoPADLTranslator implements IJCTVisitor<IConstituent, Object> {
	private final IFactory factory;
	private final ICodeLevelModel model;
	private final Map<IJCTElement, IConstituent> translator = new HashMap<IJCTElement, IConstituent>();
	private final StringBuffer errorMessage = new StringBuffer();
	private final Set<String> errorMessages = new HashSet<>();

	public JCTtoPADLTranslator(final ICodeLevelModel aCodeLevelModel) {
		this.model = aCodeLevelModel;
		this.factory = aCodeLevelModel.getFactory();
	}

	private void copyComments(final IJCTSourceCodePart aSourceCodePart,
			final IConstituent aConstituent) {

		final StringBuffer comments = new StringBuffer();
		final Iterator<IJCTComment> iterator = aSourceCodePart.getComments()
				.iterator();
		while (iterator.hasNext()) {
			final IJCTComment comment = (IJCTComment) iterator.next();
			comments.append(comment.getText());
			if (iterator.hasNext()) {
				comments.append('\n');
			}
		}
		aConstituent.setComment(comments.toString());
	}

	public ICodeLevelModel getModel() {
		return this.model;
	}

	private IPackage getPackageFromModel(final String aName,
			final boolean isGhost) {

		if (aName == null) {
			return this.factory.createPackageDefault();
		}

		final String padlName = null == aName ? "" : aName;
		final StringTokenizer tokeniser = new StringTokenizer(padlName, ".");
		IContainer currentContainer = this.model;
		IPackage p = null;

		while (tokeniser.hasMoreTokens()) {
			final String subpackageName = (String) tokeniser.nextToken();

			if (currentContainer.doesContainConstituentWithName(
					subpackageName.toCharArray())) {

				p = (IPackage) currentContainer
						.getConstituentFromName(subpackageName.toCharArray());
				currentContainer = p;
			}
			else {
				// TODO: Replace all the ternary operations by "if"s, much clearer, as also suggested by http://www.devdaily.com/java/edu/pj/pj010018/
				if (currentContainer == this.model) {
					if (aName == null) {
						p = this.factory.createPackageDefault();
					}
					else if (isGhost) {
						p = this.factory.createPackageGhost(
								subpackageName.toCharArray());
					}
					else {
						p = this.factory
								.createPackage(subpackageName.toCharArray());
					}
					((IAbstractModel) currentContainer).addConstituent(p);
					currentContainer = p;
				}
				else {
					if (isGhost) {
						p = this.factory.createPackageGhost(
								subpackageName.toCharArray());
					}
					else {
						p = this.factory
								.createPackage(subpackageName.toCharArray());
					}
					((IPackage) currentContainer).addConstituent(p);
					currentContainer = p;
				}
			}
		}

		return p;
	}

	public IConstituent visitAnd(final IJCTAnd aJCTAnd,
			final Object aParameter) {
		return null;
	}

	public IConstituent visitAndAssignment(
			final IJCTAndAssignment aJCTAndAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitArrayAccess(final IJCTArrayAccess aJCTArrayAccess,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitArrayType(final IJCTArrayType aJCTArrayType,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitAssert(final IJCTAssert aJCTAssert,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitAssignment(final IJCTAssignment aJCTAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitBitwiseComplement(
			final IJCTBitwiseComplement aJCTBitwiseComplement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitBlock(final IJCTBlock aJCTBlock,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitBooleanLiteral(
			final IJCTBooleanLiteral aJCTBooleanLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitBreak(final IJCTBreak aJCTBreak,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitCase(final IJCTCase aJCTCase,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitCast(final IJCTCast aJCTCast,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitCatch(final IJCTCatch aJCTCatch,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitCharacterLiteral(
			final IJCTCharacterLiteral aJCTCharacterLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitClass(final IJCTClass aJCTClass,
			final Object aParameter) {

		if (this.translator.get(aJCTClass) != null) {
			return this.translator.get(aJCTClass);
		}

		final char[] id = aJCTClass.getFQN().toCharArray();
		final char[] name = aJCTClass.getName().toCharArray();
		// TODO: This is the most ridiculous use of the ?: operator... replace with proper conditionals.
		final IFirstClassEntity c = aJCTClass.isStatic() == null // The casts are just a workaround for a bug of javac
				? (aJCTClass.getIsGhost() ? this.factory.createGhost(id, name)
						: (IFirstClassEntity) (aJCTClass.getIsInterface()
								? this.factory.createInterface(id, name)
								: this.factory.createClass(id, name)))
				: (aJCTClass.getIsGhost()
						? (IFirstClassEntity) this.factory.createMemberGhost(id,
								name)
						: (aJCTClass.getIsInterface()
								? (IFirstClassEntity) this.factory
										.createMemberInterface(id, name)
								: this.factory.createMemberClass(id, name)));

		this.translator.put(aJCTClass, c);

		for (final JCTModifiers m : aJCTClass.getModifiers()) {
			switch (m) {
			case ABSTRACT:
				c.setAbstract(true);
				break;
			case FINAL:
				c.setFinal(true);
				break;
			case PRIVATE:
				c.setPrivate(true);
				break;
			case PROTECTED:
				c.setProtected(true);
				break;
			case PUBLIC:
				c.setPublic(true);
				break;
			case STATIC:
				c.setStatic(true);
				break;
			default:
			}
		}

		if (null != aJCTClass.getDirectSuperClass()) {
			final IFirstClassEntity inherited = (IFirstClassEntity) aJCTClass
					.getDirectSuperClass().getSelector().getElement()
					.accept(this, aParameter);
			if (c != inherited) {
				c.addInheritedEntity(inherited);
			}
		}

		for (final IJCTClassType type : aJCTClass
				.getDirectlyImplementedInterfaces()) {

			if (aJCTClass.getIsInterface()) {
				c.addInheritedEntity((IFirstClassEntity) type.getSelector()
						.getElement().accept(this, aParameter));
			}
			else {
				final IFirstClassEntity interfaceActor = (IFirstClassEntity) type
						.getSelector().getElement().accept(this, aParameter);
				((IClass) c).addImplementedInterface(
						(IInterface) interfaceActor);
			}
		}

		boolean hasAnyDeclaredConstructor = false;
		for (final IJCTClassMember cm : aJCTClass.getDeclaredMembers()) {
			final IConstituentOfEntity entity = (IConstituentOfEntity) cm
					.accept(this, aParameter);
			// TODO: This test should not exist!
			if (entity != null
					&& !c.doesContainConstituentWithID(entity.getID())) {

				c.addConstituent(entity);
				if (entity instanceof IConstructor) {
					hasAnyDeclaredConstructor = true;
				}
			}
		}

		this.copyComments(aJCTClass, c);

		// Yann 2011/03/05: Default constructor
		// To make sure constituent are as similar as possible
		// from Java source as from Class files, I add a
		// default constructor if needed.
		if (!hasAnyDeclaredConstructor) {
			final IConstructor constructor = this.factory
					.createConstructor("<init>()".toCharArray(), name);
			c.addConstituent(constructor);
		}

		return c;
	}

	public IConstituent visitClassType(final IJCTClassType aJCTClassType,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitComment(final IJCTComment aJCTComment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitCompilationUnit(
			final IJCTCompilationUnit aJCTCompilationUnit,
			final Object aParameter) {

		final IPackage packaje = (IPackage) aParameter;
		for (final IJCTClass c : aJCTCompilationUnit.getClazzs()) {
			final IConstituentOfModel entity = (IConstituentOfModel) c
					.accept(this, aParameter);

			if (!packaje.doesContainConstituentWithID(entity.getID())) {
				packaje.addConstituent(entity);
			}
		}
		return null;
	}

	public IConstituent visitConditionalAnd(
			final IJCTConditionalAnd aJCTConditionalAnd,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitConditionalOperator(
			final IJCTConditionalOperator aJCTConditionalOperator,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitConditionalOr(
			final IJCTConditionalOr aJCTConditionalOr,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitContinue(final IJCTContinue aJCTContinue,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitDivide(final IJCTDivide aJCTDivide,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitDivideAssignment(
			final IJCTDivideAssignment aJCTDivideAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitDoubleLiteral(
			final IJCTDoubleLiteral aJCTDoubleLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitDoWhile(final IJCTDoWhile aJCTDoWhile,
			final Object aParameter) {

		try {
			if (null != this.translator.get(aJCTDoWhile)) {
				return this.translator.get(aJCTDoWhile);
			}

			final IIfInstruction f = ((StatementFactory) StatementFactory
					.getInstance())
					.createIfInstruction(aJCTDoWhile.toString().toCharArray());

			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitEmptyStatement(
			final IJCTEmptyStatement aJCTEmptyStatement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitEnhancedFor(final IJCTEnhancedFor aJCTEnhancedFor,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitEqualTo(final IJCTEqualTo aJCTEqualTo,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitErroneousExpression(
			final IJCTErroneousExpression aJCTErroneousExpression,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitErroneousSelector(
			final IJCTErroneousSelector<?> aJCTErroneousSelector,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitExpressionStatement(
			final IJCTExpressionStatement aJCTExpressionStatement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitField(final IJCTField aJCTField,
			final Object aParameter) {

		try {
			if (null != this.translator.get(aJCTField)) {
				return this.translator.get(aJCTField);
			}

			final IField f = this.factory.createField(
					aJCTField.getID().toCharArray(),
					aJCTField.getName().toCharArray(),
					aJCTField.getType().getSourceCode().toCharArray(), 1);

			f.setName(aJCTField.getName().toCharArray());
			if (Util.isArrayOrCollection(f.getType())) {
				f.setCardinality(Constants.CARDINALITY_MANY);
			}

			for (final JCTModifiers mod : aJCTField.getModifiers()) {
				switch (mod) {
				case ABSTRACT:
					f.setAbstract(true);
					break;
				case FINAL:
					f.setFinal(true);
					break;
				case PRIVATE:
					f.setPrivate(true);
					break;
				case PROTECTED:
					f.setProtected(true);
					break;
				case PUBLIC:
					f.setPublic(true);
					break;
				case STATIC:
					f.setStatic(true);
					break;
				default:
				}
			}

			this.copyComments(aJCTField, f);
			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitFloatLiteral(
			final IJCTFloatLiteral aJCTFloatLiteral, final Object aParameter) {

		return null;
	}

	public IConstituent visitFor(final IJCTFor aJCTFor,
			final Object aParameter) {
		try {
			if (null != this.translator.get(aJCTFor)) {
				return this.translator.get(aJCTFor);
			}

			final IIfInstruction f = ((StatementFactory) StatementFactory
					.getInstance())
					.createIfInstruction(aJCTFor.toString().toCharArray());

			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitGreaterThan(final IJCTGreaterThan aJCTGreaterThan,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitGreaterThanOrEqual(
			final IJCTGreaterThanOrEqual aJCTGreaterThanOrEqual,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitIf(final IJCTIf aJCTIf, final Object aParameter) {
		try {
			if (null != this.translator.get(aJCTIf)) {
				return this.translator.get(aJCTIf);
			}

			final IIfInstruction f = ((StatementFactory) StatementFactory
					.getInstance())
					.createIfInstruction(aJCTIf.toString().toCharArray());

			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitImport(final IJCTImport aJCTImport,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitInstanceOf(final IJCTInstanceOf aJCTInstanceOf,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitIntegerLiteral(
			final IJCTIntegerLiteral aJCTIntegerLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitIntersectionType(
			final IJCTIntersectionType aJCTIntersectionType,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLabel(final IJCTLabel aJCTLabel,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLeftShift(final IJCTLeftShift aJCTLeftShift,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLeftShiftAssignment(
			final IJCTLeftShiftAssignment aJCTLeftShiftAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLessThan(final IJCTLessThan aJCTLess,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLessThanOrEqual(
			final IJCTLessThanOrEqual aJCTLessThanOrEqual,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitLogicalComplement(
			final IJCTLogicalComplement aJCTLogicalComplement, final Object p) {

		return null;
	}

	public IConstituent visitLongLiteral(final IJCTLongLiteral aJCTLongLiteral,
			final Object aParameter) {

		return null;
	}

	public <Element extends IJCTClassMember> IConstituent visitMemberSelector(
			final IJCTMemberSelector<Element> aJCTMemberSelector,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitMethod(final IJCTMethod aJCTMethod,
			final Object aParameter) {

		if (null != this.translator.get(aJCTMethod)) {
			return this.translator.get(aJCTMethod);
		}

		final IOperation m;
		if (aJCTMethod.getName().equals("<init>")) {
			m = this.factory.createConstructor(aJCTMethod.getID().toCharArray(),
					aJCTMethod.getName().toCharArray());
		}
		else {
			m = this.factory.createMethod(aJCTMethod.getID().toCharArray(),
					aJCTMethod.getName().toCharArray());
		}

		for (final JCTModifiers mod : aJCTMethod.getModifiers()) {
			switch (mod) {
			case ABSTRACT:
				m.setAbstract(true);
				break;
			case FINAL:
				m.setFinal(true);
				break;
			case PRIVATE:
				m.setPrivate(true);
				break;
			case PROTECTED:
				m.setProtected(true);
				break;
			case PUBLIC:
				m.setPublic(true);
				break;
			case STATIC:
				m.setStatic(true);
				break;
			default:
			}
		}

		m.setName(aJCTMethod.getName().toCharArray());

		if (aJCTMethod.getReturnType() != null && m instanceof IMethod) {
			((IMethod) m).setReturnType(
					aJCTMethod.getReturnType().getSourceCode().toCharArray());
		}

		for (final IJCTVariable v : aJCTMethod.getParameters()) {
			IEntity entity = (IEntity) this.model.getTopLevelEntityFromID(
					v.getType().getSourceCode().toCharArray());
			// TODO: This test should not exist!
			// Yann 2010/06/21: Silly test?
			// Indeed, the null test below forces to look
			// for the same class again and over again, in
			// particular, when analysing Juzzle, to look
			// for java.lang.Void about 22,000 times! I now
			// add to the model the missing entity to avoid
			// unnecessary loop-ups. However, because the
			// v.getType() is rather poor, I must add check
			// manually what is being added.
			if (entity == null) {
				if (v.getType().getSourceCode().equals("java.lang.Void")) {
					// I assume that this is the first 
					// time that this class is met...
					// TODO: Is it always true?
					final IPackage packageJavaLang = this
							.getPackageFromModel("java.lang", false);
					entity = Factory.getInstance().createGhost(
							"java.lang.Void".toCharArray(),
							"Void".toCharArray());
					packageJavaLang.addConstituent(entity);
				}
				else {
					this.errorMessage.setLength(0);
					this.errorMessage.append(this.getClass().getName());
					this.errorMessage.append(" is missing source-code type: ");
					this.errorMessage.append(v.getType().getSourceCode());

					final String errorString = this.errorMessage.toString();
					if (!this.errorMessages.contains(errorString)) {
						this.errorMessages.add(errorString);
						ProxyConsole.getInstance().debugOutput()
								.println(errorString);
					}
				}
			}

			if (entity != null) {
				m.addConstituent((IConstituentOfOperation) this.factory
						.createParameter(entity, 0));
			}
		}

		for (final IJCTStatement statement : aJCTMethod.getBody()
				.getStatements()) {

			final IConstituentOfOperation operation = (IConstituentOfOperation) statement
					.accept(this, aParameter);
			// TODO: This test should not exist!
			if (operation != null) {
				m.addConstituent(operation);
			}
		}

		final String sourceCode = aJCTMethod.getBody().getSourceCode();
		if (sourceCode != null && !m.isAbstract()) {
			m.setCodeLines(sourceCode);
		}

		this.copyComments(aJCTMethod, m);

		return m;
	}

	public IConstituent visitMethodInvocation(
			final IJCTMethodInvocation aJCTMethodInvocation,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitMinus(final IJCTMinus aJCTMinus,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitMinusAssignment(
			final IJCTMinusAssignment aJCTMinusAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitMultiply(final IJCTMultiply aJCTMultiply,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitMultiplyAssignment(
			final IJCTMultiplyAssignment aJCTMultiplyAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitNewArray(final IJCTNewArray aJCTNewArray,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitNewClass(final IJCTNewClass aJCTNewClass,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitNotEqualTo(final IJCTNotEqualTo aJCTNotEqualTo,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitNullLiteral(final IJCTNullLiteral aJCTNullLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitOr(final IJCTOr aJCTOr, final Object aParameter) {
		return null;
	}

	public IConstituent visitOrAssignment(
			final IJCTOrAssignment aJCTOrAssignment, final Object aParameter) {

		return null;
	}

	public IConstituent visitOther(final IJCTElement aJCTElement,
			final Object aParameter) {

		return null;
	}

	@SuppressWarnings("unchecked")
	public IConstituent visitPackage(final IJCTPackage aJCTPackage,
			final Object aParameter) {

		// Yann 2009/07/22: Use?
		// Why is there an Object passed as parameter?
		// TODO: Looks weird, smells weird... probably to be removed!

		final IPackage p = this.getPackageFromModel(aJCTPackage.getName(),
				aJCTPackage.getIsGhost());

		for (final IJCTCompilationUnit cu : ((IJCTContainer<IJCTCompilationUnit>) aJCTPackage)
				.getEnclosedElements()) {
			if (null != cu) {
				cu.accept(this, p);
			}
		}

		// Yann 2009/07/22: Why?
		// Why return null here?
		return null;
	}

	public IConstituent visitParameter(final IJCTParameter aJCTParameter,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitParenthesis(final IJCTParenthesis AJCTParenthesis,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPlus(final IJCTPlus aJCTPlus,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPlusAssignment(
			final IJCTPlusAssignment aJCTPlusAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPostfixDecrement(
			final IJCTPostfixDecrement aJCTPostfixDecrement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPostfixIncrement(
			final IJCTPostfixIncrement aJCTPostfixIncrement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPrefixDecrement(
			final IJCTPrefixDecrement aJCTPrefixDecrement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPrefixIncrement(
			final IJCTPrefixIncrement aJCTPrefixIncrement,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitPrimitiveType(
			final IJCTPrimitiveType aJCTPrimitiveType,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitRemainder(final IJCTRemainder aJCTRemainder,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitRemainderAssignment(
			final IJCTRemainderAssignment aJCTRemainderAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitReturn(final IJCTReturn aJCTReturn,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitRightShift(final IJCTRightShift aJCTRightShift,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitRightShiftAssignment(
			final IJCTRightShiftAssignment aJCTRightShiftAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitRootNode(final IJCTRootNode aJCTRootNote,
			final Object aParameter) {

		// Yann 2009/07/22: Use?
		// Why is there an Object passed as parameter?
		// Looks weird, smells weird... probably to be removed!

		// Yann 2009/08/06: Dumb!
		// This is the most badly written implementation of the
		// Visitor design pattern of recent years... Why is it
		// that the visitor must take care of visiting the elements?
		// It goes againts the point of having a Visitor: let the
		// data structure decides how it should be visited. This 
		// dumbness is remarkable in the lack of easy what to treat
		// comments, which can occur on each and every element, thus
		// forcing the duplication of much code!
		// TODO: Implement the Visitor design pattern correctly!
		for (final IJCTPackage p : aJCTRootNote.getPackages()) {
			p.accept(this, aParameter);
		}

		// Yann 2009/07/22: Why?
		// Why return null here?
		return null;
	}

	public <Element extends IJCTIdentifiable> IConstituent visitSimpleIdentifier(
			final IJCTSimpleSelector<Element> aJCTSimpleSelector,
			final Object aParameter) {

		return null;
	}

	public <Element extends IJCTIdentifiable> IConstituent visitSimpleSelector(
			final IJCTSimpleSelector<Element> aJCTSimpleSelector,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitStringLiteral(
			final IJCTStringLiteral aJCTStringLiteral,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitSwitch(final IJCTSwitch aJCTSwitch,
			final Object aParameter) {

		try {
			if (null != this.translator.get(aJCTSwitch)) {
				return this.translator.get(aJCTSwitch);
			}

			final ISwitchInstruction f = ((StatementFactory) StatementFactory
					.getInstance()).createSwitchInstruction(
							aJCTSwitch.toString().toCharArray(),
							aJCTSwitch.getCases().size());

			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitSynchronized(final IJCTSynchronized t,
			final Object aParameter) {
		return null;
	}

	public IConstituent visitThrow(final IJCTThrow aJCTThrow,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitTry(final IJCTTry aJCTTry,
			final Object aParameter) {
		return null;
	}

	public IConstituent visitUnaryMinus(final IJCTUnaryMinus aJCTUnaryMinus,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitUnaryPlus(final IJCTUnaryPlus aJCTUnaryPlus,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitUnsignedRightShift(
			final IJCTUnsignedRightShift aJCTUnsignedRightShift,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitUnsignedRightShiftAssignment(
			final IJCTUnsignedRightShiftAssignment aJCTUnsignedRightShiftAssignment,
			final Object aParameter) {

		return null;
	}

	public IConstituent visitVariable(final IJCTVariable aJCTVariable,
			final Object aParameter) {

		//	try {
		//		if (null != this.translator.get(aJCTVariable)) {
		//			return this.translator.get(aJCTVariable);
		//		}
		//
		//		final IField f =
		//			this.factory.createField(
		//				aJCTVariable.getID().toCharArray(),
		//				aJCTVariable.getType().getSourceCode().toCharArray(),
		//				1);
		//
		//		f.setName(aJCTVariable.getName().toCharArray());
		//
		//		for (final JCTModifiers mod : aJCTVariable.getModifiers()) {
		//			switch (mod) {
		//				case ABSTRACT :
		//					f.setAbstract(true);
		//					break;
		//				case FINAL :
		//					f.setFinal(true);
		//					break;
		//				case PRIVATE :
		//					f.setPrivate(true);
		//					break;
		//				case PROTECTED :
		//					f.setProtected(true);
		//					break;
		//				case PUBLIC :
		//					f.setPublic(true);
		//					break;
		//				case STATIC :
		//					f.setStatic(true);
		//					break;
		//				default :
		//			}
		//		}
		//
		//		this.copyComments(aJCTVariable, f);
		//
		//		return f;
		//	}
		//	catch (final ModelDeclarationException ex) {
		//		throw new IllegalStateException(ex);
		//	}

		return null;
	}

	public IConstituent visitWhile(final IJCTWhile aJCTWhile,
			final Object aParameter) {

		try {
			if (null != this.translator.get(aJCTWhile)) {
				return this.translator.get(aJCTWhile);
			}

			final IIfInstruction f = ((StatementFactory) StatementFactory
					.getInstance())
					.createIfInstruction(aJCTWhile.toString().toCharArray());

			return f;
		}
		catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public IConstituent visitXor(final IJCTXor aJCTXor,
			final Object aParameter) {
		return null;
	}

	public IConstituent visitXorAssignment(
			final IJCTXorAssignment aJCTXorAssignment,
			final Object aParameter) {

		return null;
	}
}

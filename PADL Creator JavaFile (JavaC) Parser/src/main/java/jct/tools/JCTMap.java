/**
 * @author Mathieu Lemoine
 * @created 2008-12-15 (月)
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

package jct.tools;

import java.util.ArrayList;
import java.util.List;

import jct.kernel.*;
import jct.util.IJCTContainer;

/**
 * This class is just an helper to implement an {@link jct.kernel.IJCTVisitor}
 * with a default browsing algorithm of the JCT.
 *
 * To visit the enclosed elements, just call super.visit*(t, p).
 * If you want to modify the returned result of the visit methods, you can
 * override reduce*, the default implementation just return the first value.
 */
public class JCTMap<R, P> implements IJCTVisitor<R, P> {
	protected R reduceAnd(final R left, final R right) {
		return left;
	}

	protected R reduceAndAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceArrayAccess(final R array, final R index) {
		return array;
	}

	protected R reduceAssert(final R condition, final R detail) {
		return condition;
	}

	protected R reduceAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceBlock(final List<R> results) {
		return results.size() > 0 ? results.get(0) : null;
	}

	protected R reduceCase(final R expr, final List<R> body) {
		return expr;
	}

	protected R reduceCatch(final R param, final R body) {
		return param;
	}

	protected R reduceClass(final List<R> results) {
		return results.size() > 0 ? results.get(0) : null;
	}

	protected R reduceCompilationUnit(final List<R> imports,
			final List<R> classes, final List<R> comments) {
		return imports.size() > 0 ? imports.get(0)
				: (classes.size() > 0 ? classes.get(0)
						: (comments.size() > 0 ? comments.get(0) : null));
	}

	protected R reduceConditionalAnd(final R left, final R right) {
		return left;
	}

	protected R reduceConditionalOperator(final R condition, final R then,
			final R relse) {
		return condition;
	}

	protected R reduceConditionalOr(final R left, final R right) {
		return left;
	}

	protected R reduceDivide(final R left, final R right) {
		return left;
	}

	protected R reduceDivideAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceDoWhile(final R condition, final R body) {
		return condition;
	}

	protected R reduceEnhancedFor(final R var, final R it, final R body) {
		return var;
	}

	protected R reduceEqualTo(final R left, final R right) {
		return left;
	}

	protected R reduceErroneousExpression(final List<R> results) {
		return results.size() > 0 ? results.get(0) : null;
	}

	protected R reduceFor(final List<R> inits, final R condition,
			final List<R> updates, final R body) {
		return inits.size() > 0 ? inits.get(0) : condition;
	}

	protected R reduceGreaterThan(final R left, final R right) {
		return left;
	}

	protected R reduceGreaterThanOrEqual(final R left, final R right) {
		return left;
	}

	protected R reduceIf(final R cond, final R then, final R relse) {
		return cond;
	}

	protected R reduceInstanceOf(final R expr, final R type) {
		return expr;
	}

	protected R reduceIntersectionType(final List<R> results) {
		return results.size() > 0 ? results.get(0) : null;
	}

	protected R reduceLeftShift(final R left, final R right) {
		return left;
	}

	protected R reduceLeftShiftAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceLessThan(final R left, final R right) {
		return left;
	}

	protected R reduceLessThanOrEqual(final R left, final R right) {
		return left;
	}

	protected R reduceMemberSelector(final R expr, final R member) {
		return expr;
	}

	protected R reduceMethod(final R returnType, final List<R> exception,
			final List<R> params, final R body) {
		return returnType;
	}

	protected R reduceMethodInvocation(final R selector, final List<R> params) {
		return selector;
	}

	protected R reduceMinus(final R left, final R right) {
		return left;
	}

	protected R reduceMinusAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceMultiply(final R left, final R right) {
		return left;
	}

	protected R reduceMultiplyAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceNewArray(final R element, final List<R> dims,
			final List<R> inits) {
		return element;
	}

	protected R reduceNewClass(final R declared, final R selector,
			final List<R> parameters) {
		return declared;
	}

	protected R reduceNotEqualTo(final R left, final R right) {
		return left;
	}

	protected R reduceOr(final R left, final R right) {
		return left;
	}

	protected R reduceOrAssignment(final R var, final R val) {
		return var;
	}

	protected R reducePackage(final R declaration, final List<R> cus) {
		return declaration;
	}

	protected R reducePlus(final R left, final R right) {
		return left;
	}

	protected R reducePlusAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceRootNode(final List<R> results) {
		return results.size() > 0 ? results.get(0) : null;
	}

	protected R reduceRemainder(final R left, final R right) {
		return left;
	}

	protected R reduceRemainderAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceRightShift(final R left, final R right) {
		return left;
	}

	protected R reduceRightShiftAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceSwitch(final R expr, final List<R> cases,
			final R rdefault) {
		return expr;
	}

	protected R reduceSynchronized(final R expr, final R body) {
		return expr;
	}

	protected R reduceTry(final R tblock, final List<R> catches,
			final R rfinally) {
		return tblock;
	}

	protected R reduceCast(final R type, final R expr) {
		return type;
	}

	protected R reduceUnsignedRightShift(final R left, final R right) {
		return left;
	}

	protected R reduceUnsignedRightShiftAssignment(final R var, final R val) {
		return var;
	}

	protected R reduceVariable(final R type, final R init) {
		return type;
	}

	protected R reduceField(final R type, final R init) {
		return type;
	}

	protected R reduceParameter(final R type, final R init) {
		return type;
	}

	protected R reduceWhile(final R condition, final R body) {
		return condition;
	}

	protected R reduceXor(final R left, final R right) {
		return left;
	}

	protected R reduceXorAssignment(final R var, final R val) {
		return var;
	}

	public R visitAnd(final IJCTAnd t, final P p) {
		return this.reduceAnd(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitAndAssignment(final IJCTAndAssignment t, final P p) {
		return this.reduceAndAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitArrayAccess(final IJCTArrayAccess t, final P p) {
		return this.reduceArrayAccess(t.getArray().accept(this, p),
				t.getIndex().accept(this, p));
	}

	public R visitArrayType(final IJCTArrayType t, final P p) {
		if (null != t.getUnderlyingType())
			return t.getUnderlyingType().accept(this, p);
		else
			return null;
	}

	public R visitAssert(final IJCTAssert t, final P p) {
		return this.reduceAssert(t.getCondition().accept(this, null),
				t.getDetail().accept(this, null));
	}

	public R visitAssignment(final IJCTAssignment t, final P p) {
		return this.reduceAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitBitwiseComplement(final IJCTBitwiseComplement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitBlock(final IJCTBlock t, final P p) {
		final List<R> results = new ArrayList<R>(t.getStatements().size());

		for (final IJCTStatement s : t.getStatements())
			results.add(s.accept(this, p));

		return this.reduceBlock(results);
	}

	public R visitBooleanLiteral(final IJCTBooleanLiteral t, final P p) {
		return null;
	}

	public R visitBreak(final IJCTBreak t, final P p) {
		return null;
	}

	public R visitCase(final IJCTCase t, final P p) {
		R expr = null;
		final List<R> results = new ArrayList<R>(t.getStatements().size());

		if (null != t.getLabel())
			expr = t.getLabel().accept(this, p);

		for (final IJCTStatement s : t.getStatements())
			results.add(s.accept(this, p));

		return this.reduceCase(expr, results);
	}

	public R visitCatch(final IJCTCatch t, final P p) {
		return this.reduceCatch(t.getVariable().accept(this, p),
				t.getBody().accept(this, p));
	}

	public R visitCharacterLiteral(final IJCTCharacterLiteral t, final P p) {
		return null;
	}

	public R visitClass(final IJCTClass t, final P p) {
		final List<R> results = new ArrayList<R>(t.getDeclaredMembers().size());

		for (final IJCTClassMember cm : t.getDeclaredMembers())
			results.add(cm.accept(this, p));

		return this.reduceClass(results);
	}

	public R visitClassType(final IJCTClassType t, final P p) {
		return t.getSelector().accept(this, p);
	}

	public R visitCompilationUnit(final IJCTCompilationUnit t, final P p) {
		final List<R> imports = new ArrayList<R>(t.getImporteds().size());
		final List<R> classes = new ArrayList<R>(t.getClazzs().size());
		final List<R> comments = new ArrayList<R>(t.getComments().size());

		for (final IJCTImport i : t.getImporteds())
			imports.add(i.accept(this, p));

		for (final IJCTClass c : t.getClazzs())
			classes.add(c.accept(this, p));

		for (final IJCTComment c : t.getComments())
			comments.add(c.accept(this, p));

		return this.reduceCompilationUnit(imports, classes, comments);
	}

	public R visitConditionalAnd(final IJCTConditionalAnd t, final P p) {
		return this.reduceConditionalAnd(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitConditionalOperator(final IJCTConditionalOperator t,
			final P p) {
		return this.reduceConditionalOperator(t.getCondition().accept(this, p),
				t.getThenExpression().accept(this, p),
				t.getElseExpression().accept(this, p));
	}

	public R visitConditionalOr(final IJCTConditionalOr t, final P p) {
		return this.reduceConditionalOr(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitContinue(final IJCTContinue t, final P p) {
		return null;
	}

	public R visitDivide(final IJCTDivide t, final P p) {
		return this.reduceDivide(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitDivideAssignment(final IJCTDivideAssignment t, final P p) {
		return this.reduceDivideAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitDoubleLiteral(final IJCTDoubleLiteral t, final P p) {
		return null;
	}

	public R visitDoWhile(final IJCTDoWhile t, final P p) {
		return this.reduceDoWhile(t.getCondition().accept(this, p),
				t.getBody().accept(this, p));
	}

	public R visitEmptyStatement(final IJCTEmptyStatement t, final P p) {
		return null;
	}

	public R visitEnhancedFor(final IJCTEnhancedFor t, final P p) {
		return this.reduceEnhancedFor(t.getVariable().accept(this, p),
				t.getIterable().accept(this, p), t.getBody().accept(this, p));
	}

	public R visitEqualTo(final IJCTEqualTo t, final P p) {
		return this.reduceEqualTo(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitErroneousExpression(final IJCTErroneousExpression t,
			final P p) {
		final List<R> results = new ArrayList<R>(
				((IJCTContainer<? extends IJCTElement>) t).getEnclosedElements()
						.size());

		for (final IJCTExpression e : ((IJCTContainer<? extends IJCTExpression>) t)
				.getEnclosedElements())
			results.add(e.accept(this, p));

		return this.reduceErroneousExpression(results);
	}

	public R visitErroneousSelector(final IJCTErroneousSelector<?> t,
			final P p) {
		return null;
	}

	public R visitExpressionStatement(final IJCTExpressionStatement t,
			final P p) {
		return t.getExpression().accept(this, p);
	}

	public R visitFloatLiteral(final IJCTFloatLiteral t, final P p) {
		return null;
	}

	public R visitFor(final IJCTFor t, final P p) {
		final List<R> initializers = new ArrayList<R>(
				t.getInitializers().size());
		R condition;
		final List<R> updates = new ArrayList<R>(t.getUpdaters().size());

		for (final IJCTStatement s : t.getInitializers())
			initializers.add(s.accept(this, p));

		condition = t.getCondition().accept(this, p);

		for (final IJCTExpressionStatement e : t.getUpdaters())
			updates.add(e.accept(this, p));

		return this.reduceFor(initializers, condition, updates,
				t.getBody().accept(this, p));
	}

	public R visitGreaterThan(final IJCTGreaterThan t, final P p) {
		return this.reduceGreaterThan(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual t,
			final P p) {
		return this.reduceGreaterThanOrEqual(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitIf(final IJCTIf t, final P p) {
		return this.reduceIf(t.getCondition().accept(this, p),
				t.getThenStatement().accept(this, p),
				null != t.getElseStatement()
						? t.getElseStatement().accept(this, p)
						: null);
	}

	public R visitImport(final IJCTImport t, final P p) {
		return null;
	}

	public R visitInstanceOf(final IJCTInstanceOf t, final P p) {
		return this.reduceInstanceOf(t.getOperand().accept(this, p),
				t.getType().accept(this, p));
	}

	public R visitIntegerLiteral(final IJCTIntegerLiteral t, final P p) {
		return null;
	}

	public R visitIntersectionType(final IJCTIntersectionType t, final P p) {
		final List<R> results = new ArrayList<R>(t.getTypes().size());

		for (final IJCTType aType : t.getTypes())
			results.add(aType.accept(this, p));

		return this.reduceIntersectionType(results);
	}

	public R visitLabel(final IJCTLabel t, final P p) {
		return t.getStatement().accept(this, p);
	}

	public R visitLeftShift(final IJCTLeftShift t, final P p) {
		return this.reduceLeftShift(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitLeftShiftAssignment(final IJCTLeftShiftAssignment t,
			final P p) {
		return this.reduceLeftShiftAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitLessThan(final IJCTLessThan t, final P p) {
		return this.reduceLessThan(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitLessThanOrEqual(final IJCTLessThanOrEqual t, final P p) {
		return this.reduceLessThanOrEqual(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitLogicalComplement(final IJCTLogicalComplement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitLongLiteral(final IJCTLongLiteral t, final P p) {
		return null;
	}

	public <Element extends IJCTClassMember> R visitMemberSelector(
			final IJCTMemberSelector<Element> t, final P p) {
		return this.reduceMemberSelector(
				t.getQualifyingExpression().accept(this, p),
				t.getMemberSelector().accept(this, p));
	}

	public R visitMethod(final IJCTMethod t, final P p) {
		final R returnType = t.getReturnType().accept(this, p);
		final List<R> exceptions = new ArrayList<R>(
				t.getThrownExceptions().size());
		final List<R> parameters = new ArrayList<R>(t.getParameters().size());

		for (final IJCTClassType ct : t.getThrownExceptions())
			exceptions.add(ct.accept(this, p));

		for (final IJCTVariable aVariable : t.getParameters())
			parameters.add(aVariable.accept(this, p));

		return this.reduceMethod(returnType, exceptions, parameters,
				null != t.getBody() ? t.getBody().accept(this, p) : null);
	}

	public R visitMethodInvocation(final IJCTMethodInvocation t, final P p) {
		final R selector = t.getMethodSelector().accept(this, p);
		final List<R> parameters = new ArrayList<R>(t.getArguments().size());

		for (final IJCTExpression e : t.getArguments())
			parameters.add(e.accept(this, p));

		return this.reduceMethodInvocation(selector, parameters);
	}

	public R visitMinus(final IJCTMinus t, final P p) {
		return this.reduceMinus(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitMinusAssignment(final IJCTMinusAssignment t, final P p) {
		return this.reduceMinusAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitMultiply(final IJCTMultiply t, final P p) {
		return this.reduceMultiply(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitMultiplyAssignment(final IJCTMultiplyAssignment t,
			final P p) {
		return this.reduceMultiplyAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitNewArray(final IJCTNewArray t, final P p) {
		final R element = (null == t.getElementType() ? null
				: t.getElementType().accept(this, p));
		final List<R> dimensions = new ArrayList<R>(t.getDimensions().size());
		final List<R> initializers = new ArrayList<R>(
				t.getInitializers().size());

		for (final IJCTExpression e : t.getDimensions())
			dimensions.add(e.accept(this, p));

		for (final IJCTExpression e : t.getInitializers())
			initializers.add(e.accept(this, p));

		return this.reduceNewArray(element, dimensions, initializers);
	}

	public R visitNewClass(final IJCTNewClass t, final P p) {
		R declaredClass = null;
		R selectingExpression = null;
		final List<R> parameters = new ArrayList<R>(t.getArguments().size());

		if (null != t.getAnnonymousClass())
			declaredClass = t.getAnnonymousClass().accept(this, p);

		if (null != t.getSelectingExpression())
			selectingExpression = t.getSelectingExpression().accept(this, p);

		for (final IJCTExpression e : t.getArguments())
			parameters.add(e.accept(this, p));

		return this.reduceNewClass(declaredClass, selectingExpression,
				parameters);
	}

	public R visitNotEqualTo(final IJCTNotEqualTo t, final P p) {
		return this.reduceNotEqualTo(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitNullLiteral(final IJCTNullLiteral t, final P p) {
		return null;
	}

	public R visitOr(final IJCTOr t, final P p) {
		return this.reduceOr(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitOrAssignment(final IJCTOrAssignment t, final P p) {
		return this.reduceOrAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));

	}

	public R visitOther(final IJCTElement t, final P p) {
		return null;
	}

	public R visitPackage(final IJCTPackage t, final P p) {
		R declaration = null;
		final List<R> cus = new ArrayList<R>(t.getCompilationUnits().size());

		if (null != t.getPackageDeclaration())
			declaration = t.getPackageDeclaration().accept(this, p);

		for (final IJCTCompilationUnit cu : t.getCompilationUnits())
			cus.add(cu.accept(this, p));

		return this.reducePackage(declaration, cus);
	}

	public R visitParenthesis(final IJCTParenthesis t, final P p) {
		return t.getExpression().accept(this, p);
	}

	public R visitPlus(final IJCTPlus t, final P p) {
		return this.reducePlus(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitPlusAssignment(final IJCTPlusAssignment t, final P p) {
		return this.reducePlusAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitPostfixDecrement(final IJCTPostfixDecrement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitPostfixIncrement(final IJCTPostfixIncrement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitPrefixDecrement(final IJCTPrefixDecrement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitPrefixIncrement(final IJCTPrefixIncrement t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitPrimitiveType(final IJCTPrimitiveType t, final P p) {
		return null;
	}

	public R visitRootNode(final IJCTRootNode t, final P param) {
		final List<R> results = new ArrayList<R>(t.getPackages().size());

		for (final IJCTPackage p : t.getPackages())
			results.add(p.accept(this, param));

		return this.reduceRootNode(results);
	}

	public R visitRemainder(final IJCTRemainder t, final P p) {
		return this.reduceRemainder(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitRemainderAssignment(final IJCTRemainderAssignment t,
			final P p) {
		return this.reduceRemainderAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitReturn(final IJCTReturn t, final P p) {
		return null != t.getReturnedExpression()
				? t.getReturnedExpression().accept(this, p)
				: null;
	}

	public R visitRightShift(final IJCTRightShift t, final P p) {
		return this.reduceRightShift(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitRightShiftAssignment(final IJCTRightShiftAssignment t,
			final P p) {
		return this.reduceRightShiftAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public <Element extends IJCTIdentifiable> R visitSimpleIdentifier(
			final IJCTSimpleSelector<Element> t, final P p) {
		return null;
	}

	public <Element extends IJCTIdentifiable> R visitSimpleSelector(
			final IJCTSimpleSelector<Element> t, final P p) {
		return null;
	}

	public R visitStringLiteral(final IJCTStringLiteral t, final P p) {
		return null;
	}

	public R visitSwitch(final IJCTSwitch t, final P p) {
		final R expression = t.getExpression().accept(this, p);
		final List<R> cases = new ArrayList<R>(t.getCases().size());

		for (final IJCTCase c : t.getCases())
			cases.add(c.accept(this, p));

		return this.reduceSwitch(expression, cases,
				null != t.getDefaultCase() ? t.getDefaultCase().accept(this, p)
						: null);
	}

	public R visitSynchronized(final IJCTSynchronized t, final P p) {
		return this.reduceSynchronized(
				t.getSynchronizedObject().accept(this, p),
				t.getBody().accept(this, p));
	}

	public R visitThrow(final IJCTThrow t, final P p) {
		return t.getThrownException().accept(this, p);
	}

	public R visitTry(final IJCTTry t, final P p) {
		final R tblock = t.getTryBlock().accept(this, p);
		final List<R> catches = new ArrayList<R>(t.getCatchBlocks().size());

		for (final IJCTCatch c : t.getCatchBlocks())
			catches.add(c.accept(this, p));

		return this.reduceTry(tblock, catches,
				null != t.getFinallyBlock()
						? t.getFinallyBlock().accept(this, p)
						: null);
	}

	public R visitCast(final IJCTCast t, final P p) {
		return this.reduceCast(t.getType().accept(this, p),
				t.getOperand().accept(this, p));
	}

	public R visitUnaryMinus(final IJCTUnaryMinus t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitUnaryPlus(final IJCTUnaryPlus t, final P p) {
		return t.getOperand().accept(this, p);
	}

	public R visitUnsignedRightShift(final IJCTUnsignedRightShift t,
			final P p) {
		return this.reduceUnsignedRightShift(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitUnsignedRightShiftAssignment(
			final IJCTUnsignedRightShiftAssignment t, final P p) {
		return this.reduceUnsignedRightShiftAssignment(
				t.getVariable().accept(this, p), t.getValue().accept(this, p));
	}

	public R visitVariable(final IJCTVariable t, final P p) {
		return this.reduceVariable(
				null != t.getType() ? t.getType().accept(this, p) : null,
				null != t.getInitialValue()
						? t.getInitialValue().accept(this, p)
						: null);
	}

	public R visitWhile(final IJCTWhile t, final P p) {
		return this.reduceWhile(t.getCondition().accept(this, p),
				t.getBody().accept(this, p));
	}

	public R visitXor(final IJCTXor t, final P p) {
		return this.reduceXor(t.getLeftOperand().accept(this, p),
				t.getRightOperand().accept(this, p));
	}

	public R visitXorAssignment(final IJCTXorAssignment t, final P p) {
		return this.reduceXorAssignment(t.getVariable().accept(this, p),
				t.getValue().accept(this, p));
	}

	public R visitField(final IJCTField t, final P p) {
		return this.reduceField(
				null != t.getType() ? t.getType().accept(this, p) : null,
				null != t.getInitialValue()
						? t.getInitialValue().accept(this, p)
						: null);
	}

	public R visitParameter(final IJCTParameter t, final P p) {
		return this.reduceParameter(
				null != t.getType() ? t.getType().accept(this, p) : null,
				null != t.getInitialValue()
						? t.getInitialValue().accept(this, p)
						: null);
	}

	public R visitComment(final IJCTComment commentElement,
			final P additionalParameter) {
		return null;
	}
}

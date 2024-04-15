/**
 * @author Mathieu Lemoine
 * @created 2008-10-26 (日)
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jct.kernel.IJCTAnd;
import jct.kernel.IJCTAndAssignment;
import jct.kernel.IJCTArrayAccess;
import jct.kernel.IJCTArrayType;
import jct.kernel.IJCTAssert;
import jct.kernel.IJCTAssignment;
import jct.kernel.IJCTBitwiseComplement;
import jct.kernel.IJCTBlock;
import jct.kernel.IJCTBooleanLiteral;
import jct.kernel.IJCTBreak;
import jct.kernel.IJCTCase;
import jct.kernel.IJCTCast;
import jct.kernel.IJCTCatch;
import jct.kernel.IJCTCharacterLiteral;
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTComment;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTConditionalAnd;
import jct.kernel.IJCTConditionalOperator;
import jct.kernel.IJCTConditionalOr;
import jct.kernel.IJCTContinue;
import jct.kernel.IJCTDivide;
import jct.kernel.IJCTDivideAssignment;
import jct.kernel.IJCTDoWhile;
import jct.kernel.IJCTDoubleLiteral;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTEmptyStatement;
import jct.kernel.IJCTEnhancedFor;
import jct.kernel.IJCTEqualTo;
import jct.kernel.IJCTErroneousExpression;
import jct.kernel.IJCTErroneousSelector;
import jct.kernel.IJCTExpressionStatement;
import jct.kernel.IJCTField;
import jct.kernel.IJCTFloatLiteral;
import jct.kernel.IJCTFor;
import jct.kernel.IJCTGreaterThan;
import jct.kernel.IJCTGreaterThanOrEqual;
import jct.kernel.IJCTIdentifiable;
import jct.kernel.IJCTIf;
import jct.kernel.IJCTImport;
import jct.kernel.IJCTInstanceOf;
import jct.kernel.IJCTIntegerLiteral;
import jct.kernel.IJCTIntersectionType;
import jct.kernel.IJCTLabel;
import jct.kernel.IJCTLeftShift;
import jct.kernel.IJCTLeftShiftAssignment;
import jct.kernel.IJCTLessThan;
import jct.kernel.IJCTLessThanOrEqual;
import jct.kernel.IJCTLogicalComplement;
import jct.kernel.IJCTLongLiteral;
import jct.kernel.IJCTMemberSelector;
import jct.kernel.IJCTMethod;
import jct.kernel.IJCTMethodInvocation;
import jct.kernel.IJCTMinus;
import jct.kernel.IJCTMinusAssignment;
import jct.kernel.IJCTMultiply;
import jct.kernel.IJCTMultiplyAssignment;
import jct.kernel.IJCTNewArray;
import jct.kernel.IJCTNewClass;
import jct.kernel.IJCTNotEqualTo;
import jct.kernel.IJCTNullLiteral;
import jct.kernel.IJCTOr;
import jct.kernel.IJCTOrAssignment;
import jct.kernel.IJCTPackage;
import jct.kernel.IJCTParameter;
import jct.kernel.IJCTParenthesis;
import jct.kernel.IJCTPlus;
import jct.kernel.IJCTPlusAssignment;
import jct.kernel.IJCTPostfixDecrement;
import jct.kernel.IJCTPostfixIncrement;
import jct.kernel.IJCTPrefixDecrement;
import jct.kernel.IJCTPrefixIncrement;
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRemainder;
import jct.kernel.IJCTRemainderAssignment;
import jct.kernel.IJCTReturn;
import jct.kernel.IJCTRightShift;
import jct.kernel.IJCTRightShiftAssignment;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSimpleSelector;
import jct.kernel.IJCTStringLiteral;
import jct.kernel.IJCTSwitch;
import jct.kernel.IJCTSynchronized;
import jct.kernel.IJCTThrow;
import jct.kernel.IJCTTry;
import jct.kernel.IJCTUnaryMinus;
import jct.kernel.IJCTUnaryPlus;
import jct.kernel.IJCTUnsignedRightShift;
import jct.kernel.IJCTUnsignedRightShiftAssignment;
import jct.kernel.IJCTVariable;
import jct.kernel.IJCTVisitor;
import jct.kernel.IJCTWhile;
import jct.kernel.IJCTXor;
import jct.kernel.IJCTXorAssignment;
import jct.util.IJCTContainer;

/**
 * This class is a simple pretty printer
 *
 * @author Mathieu Lemoine
 */
public class JCTPrettyPrinter implements IJCTVisitor<Void, Void> {
	private final File directory;

	private File currentDirectory;
	private Writer currentFile;

	public JCTPrettyPrinter(final File directory) {
		if (directory.exists() && !directory.isDirectory())
			throw new IllegalArgumentException(
					"directory (" + directory + ") must be a directory.");

		if (!directory.exists() && !directory.mkdirs())
			throw new IllegalStateException(
					"Cannot create the directory " + directory);

		this.directory = directory;
	}

	public Void visitRootNode(final IJCTRootNode t, final Void v) {
		for (final IJCTPackage p : t.getPackages())
			p.accept(this, v);

		return null;
	}

	public Void visitPackage(final IJCTPackage t, final Void p) {
		if (t.isUnnamed())
			this.currentDirectory = this.directory;
		else {
			this.currentDirectory = new File(this.directory,
					t.getName().replace('.', File.separatorChar));

			if (this.currentDirectory.exists()
					&& !this.currentDirectory.isDirectory())
				throw new IllegalStateException("package directory ("
						+ this.currentDirectory + ") is not a directory.");

			if (!this.currentDirectory.exists()
					&& !this.currentDirectory.mkdirs())
				throw new IllegalStateException(
						"Cannot create the directory " + this.currentDirectory);
		}

		for (final IJCTCompilationUnit cu : ((IJCTContainer<? extends IJCTCompilationUnit>) t)
				.getEnclosedElements())
			if (null != cu)
				cu.accept(this, p);

		return null;
	}

	public Void visitCompilationUnit(final IJCTCompilationUnit t,
			final Void p) {
		try {
			final File file = new File(this.currentDirectory,
					t.getSourceFile().getName());
			file.delete();
			this.currentFile = new FileWriter(file);

			t.getSourceCode(this.currentFile);

			this.currentFile.close();
		}
		catch (final IOException e) {
			throw new IllegalStateException(e);
		}

		return null;
	}

	private Void visitElement(final IJCTElement t, final Void p) {
		try {
			t.getSourceCode(this.currentFile);
		}
		catch (final IOException e) {
			throw new IllegalStateException(e);
		}

		return null;
	}

	public Void visitAnd(final IJCTAnd t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitAndAssignment(final IJCTAndAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitArrayAccess(final IJCTArrayAccess t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitArrayType(final IJCTArrayType t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitAssert(final IJCTAssert t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitAssignment(final IJCTAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitBitwiseComplement(final IJCTBitwiseComplement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitBlock(final IJCTBlock t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitBooleanLiteral(final IJCTBooleanLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitBreak(final IJCTBreak t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitCase(final IJCTCase t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitCatch(final IJCTCatch t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitCharacterLiteral(final IJCTCharacterLiteral t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitClass(final IJCTClass t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitClassType(final IJCTClassType t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitConditionalAnd(final IJCTConditionalAnd t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitConditionalOperator(final IJCTConditionalOperator t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitConditionalOr(final IJCTConditionalOr t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitContinue(final IJCTContinue t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitDivide(final IJCTDivide t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitDivideAssignment(final IJCTDivideAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitDoubleLiteral(final IJCTDoubleLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitDoWhile(final IJCTDoWhile t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitEmptyStatement(final IJCTEmptyStatement t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitEnhancedFor(final IJCTEnhancedFor t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitEqualTo(final IJCTEqualTo t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitErroneousExpression(final IJCTErroneousExpression t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitErroneousSelector(final IJCTErroneousSelector t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitExpressionStatement(final IJCTExpressionStatement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitFloatLiteral(final IJCTFloatLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitFor(final IJCTFor t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitGreaterThan(final IJCTGreaterThan t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitIf(final IJCTIf t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitImport(final IJCTImport t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitInstanceOf(final IJCTInstanceOf t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitIntegerLiteral(final IJCTIntegerLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitIntersectionType(final IJCTIntersectionType t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLabel(final IJCTLabel t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLeftShift(final IJCTLeftShift t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLeftShiftAssignment(final IJCTLeftShiftAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLessThan(final IJCTLessThan t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLessThanOrEqual(final IJCTLessThanOrEqual t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLogicalComplement(final IJCTLogicalComplement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitLongLiteral(final IJCTLongLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public <Element extends IJCTClassMember> Void visitMemberSelector(
			final IJCTMemberSelector<Element> t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMethod(final IJCTMethod t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMethodInvocation(final IJCTMethodInvocation t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMinus(final IJCTMinus t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMinusAssignment(final IJCTMinusAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMultiply(final IJCTMultiply t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitMultiplyAssignment(final IJCTMultiplyAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitNewArray(final IJCTNewArray t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitNewClass(final IJCTNewClass t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitNotEqualTo(final IJCTNotEqualTo t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitNullLiteral(final IJCTNullLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitOr(final IJCTOr t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitOrAssignment(final IJCTOrAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitOther(final IJCTElement t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitParenthesis(final IJCTParenthesis t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPlus(final IJCTPlus t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPlusAssignment(final IJCTPlusAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPostfixDecrement(final IJCTPostfixDecrement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPostfixIncrement(final IJCTPostfixIncrement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPrefixDecrement(final IJCTPrefixDecrement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPrefixIncrement(final IJCTPrefixIncrement t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitPrimitiveType(final IJCTPrimitiveType t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitRemainder(final IJCTRemainder t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitRemainderAssignment(final IJCTRemainderAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitReturn(final IJCTReturn t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitRightShift(final IJCTRightShift t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitRightShiftAssignment(final IJCTRightShiftAssignment t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public <Element extends IJCTIdentifiable> Void visitSimpleIdentifier(
			final IJCTSimpleSelector<Element> t, final Void p) {
		return this.visitElement(t, p);
	}

	public <Element extends IJCTIdentifiable> Void visitSimpleSelector(
			final IJCTSimpleSelector<Element> t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitStringLiteral(final IJCTStringLiteral t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitSwitch(final IJCTSwitch t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitSynchronized(final IJCTSynchronized t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitThrow(final IJCTThrow t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitTry(final IJCTTry t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitCast(final IJCTCast t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitUnaryMinus(final IJCTUnaryMinus t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitUnaryPlus(final IJCTUnaryPlus t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitUnsignedRightShift(final IJCTUnsignedRightShift t,
			final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitUnsignedRightShiftAssignment(
			final IJCTUnsignedRightShiftAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitVariable(final IJCTVariable t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitWhile(final IJCTWhile t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitXor(final IJCTXor t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitXorAssignment(final IJCTXorAssignment t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitField(final IJCTField t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitParameter(final IJCTParameter t, final Void p) {
		return this.visitElement(t, p);
	}

	public Void visitComment(final IJCTComment t, final Void p) {
		return this.visitElement(t, p);
	}
}

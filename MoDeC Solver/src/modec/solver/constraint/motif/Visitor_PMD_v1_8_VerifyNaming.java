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
package modec.solver.constraint.motif;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import metamodel.scenarioDiagram.ScenarioDiagram;
import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.ParameterCalleeSameType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;

/**
 * @author Janice Ng
 */
public class Visitor_PMD_v1_8_VerifyNaming {

	public static void main(final String[] args) {

		long timeStart = System.currentTimeMillis();
		System.out.println("Start time : " + timeStart);

		// -------------------------------------------------------------------------------------------------------------
		//  SOLUTION À OBTENIR POUR ../MoDeC Bytecode Instrumentation Tests/JHotDraw_Visitor.trace                     -
		// -------------------------------------------------------------------------------------------------------------		
		//	v_accept1 = <OPERATION> public void visit (org.jhotdraw.framework.FigureVisitor visitor)                   -
		//	v_visitConcreteElement1 = <OPERATION> public void visitFigure (org.jhotdraw.framework.Figure hostFigure)   -
		//	v_operation1 = <OPERATION> public void addToContainer (org.jhotdraw.framework.FigureChangeListener c)      -                                                            -
		//	v_anObjStruct1 = org.jhotdraw.contrib.zoom.ZoomDrawingView 5819561                                         -
		//	v_aConcreteElement1 = org.jhotdraw.samples.javadraw.AnimationDecorator 12839271                            -
		//	v_aConcreteVisitor1 = org.jhotdraw.standard.InsertIntoDrawingVisitor 1621736                               -
		//	v_operation1Caller = org.jhotdraw.samples.javadraw.BouncingDrawing 6626965                                 -
		//                                                                                                             -
		//	v_accept2 = <OPERATION> public void visit (org.jhotdraw.framework.FigureVisitor visitor)                   -
		//	v_visitConcreteElement2 = <OPERATION> public void visitFigure (org.jhotdraw.framework.Figure hostFigure)   -
		//	v_operation2 = <OPERATION> public void removeFromContainer (org.jhotdraw.framework.FigureChangeListener c) -                                                           -
		//	v_anObjStruct2 = org.jhotdraw.standard.CutCommand 11197591                                                 -
		//	v_aConcreteElement2 = org.jhotdraw.samples.javadraw.AnimationDecorator 24934792                            -
		//	v_aConcreteVisitor2 = org.jhotdraw.standard.DeleteFromDrawingVisitor 318747                                -
		//	v_operation2Caller = org.jhotdraw.samples.javadraw.BouncingDrawing 6626965                                 -
		// -------------------------------------------------------------------------------------------------------------

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_PMD_Visitor_VerifyNaming_shorten.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);

		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
		sd.determineDestinationCalledMessages(
			componentsMessages,
			allClassifiers);
		sd.determineClassifierIdx(allClassifiers);
		sd.determineIdxClassifier(allClassifiers);
		sd.determineMessageContainer(componentsMessages);

		final List concreteVisitorType = new ArrayList();
		concreteVisitorType
			.add("net.sourceforge.pmd.ast.JavaParserVisitorAdapter");
		concreteVisitorType.add("net.sourceforge.pmd.AbstractRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.AccessorClassGenerationRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.AtLeastOneConstructorRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.strictexception.AvoidCatchingThrowable");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.AvoidDeeplyNestedIfStmtsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.AvoidDuplicateLiteralsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.AvoidReassigningParametersRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.BeanMembersShouldSerializeRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.ClassNamingConventionsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.CloseConnectionRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.ConstructorCallsOverridableMethodRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.CouplingBetweenObjectsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.CyclomaticComplexityRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.DoubleCheckedLockingRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.DuplicateImportsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.ExceptionAsFlowControlRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.strictexception.ExceptionSignatureDeclaration");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.strictexception.ExceptionTypeChecking");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.IdempotentOperationsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.ImportFromSamePackageRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.junit.JUnitAssertionsShouldIncludeMessageRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.LooseCouplingRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.MethodNamingConventionsRule");
		concreteVisitorType.add("test.net.sourceforge.pmd.MyRule");
		concreteVisitorType
			.add("nnet.sourceforge.pmd.rules.design.NullAssignmentRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.OnlyOneReturnRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.PositionalIteratorRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.SimplifyBooleanReturnsRule");
		concreteVisitorType.add("net.sourceforge.pmd.stat.StatisticalRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.ExcessiveLengthRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.LongClassRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.LongMethodRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.ExcessiveNodeCountRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.ExcessiveImportsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.ExcessivePublicCountRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.LongParameterListRule");
		concreteVisitorType
			.add("test.net.sourceforge.pmd.stat.MockStatisticalRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.SwitchDensityRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.StringConcatenationRule");
		concreteVisitorType.add("net.sourceforge.pmd.rules.StringToStringRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.SuspiciousOctalEscapeRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.SymbolTableTestRule");
		concreteVisitorType.add("test.net.sourceforge.pmd.jaxen.TestRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.UnnecessaryConversionTemporaryRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.UnusedFormalParameterRule");
		concreteVisitorType.add("net.sourceforge.pmd.rules.UnusedImportsRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.UnusedLocalVariableRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.UnusedPrivateFieldRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.UnusedPrivateMethodRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.design.UseSingletonRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.rules.VariableNamingConventionsRule");
		concreteVisitorType.add("net.sourceforge.pmd.rules.XPathRule");
		concreteVisitorType
			.add("net.sourceforge.pmd.symboltable.BasicScopeCreationVisitor");
		concreteVisitorType
			.add("net.sourceforge.pmd.symboltable.DeclarationFinder");
		concreteVisitorType
			.add("nnet.sourceforge.pmd.symboltable.SymbolFacade");

		final List concreteElementType = new ArrayList();
		concreteElementType.add("net.sourceforge.pmd.ast.AccessNode");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTClassDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTConstructorDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTEnumDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTFieldDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTFormalParameter");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTInterfaceDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTLocalVariableDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTMethodDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTNestedClassDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTNestedInterfaceDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTAdditiveExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTAllocationExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTAndExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTArgumentList");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTArguments");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTArrayDimsAndInits");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTArrayInitializer");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTAssertStatement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTAssignmentOperator");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTBlock");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTBlockStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTBooleanLiteral");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTBreakStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTCastExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTCastLookahead");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTClassBody");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTClassBodyDeclaration");
		concreteElementType.add("test.net.sourceforge.pmd.symboltable.MyCB");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTCompilationUnit");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTConditionalAndExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTConditionalExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTConditionalOrExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTContinueStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTDoStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTEmptyStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTEnumElement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTEqualityExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTExclusiveOrExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTExplicitConstructorInvocation");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTForInit");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTFormalParameters");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTWhileStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTForUpdate");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTIfStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTIfStatement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTInclusiveOrExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTInitializer");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTInstanceOfExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTInterfaceMemberDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTLabeledStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTLiteral");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTMethodDeclarationLookahead");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTMethodDeclarator");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTMultiplicativeExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTName");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTNameList");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTNullLiteral");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTPackageDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTPostfixExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTPreDecrementExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTPreIncrementExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTPrimaryExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTPrimaryPrefix");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTPrimarySuffix");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTPrimitiveType");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTRelationalExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTResultType");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTReturnStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTShiftExpression");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTStatement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTStatementExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTStatementExpressionList");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTSwitchLabel");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTSwitchStatement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTSynchronizedStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTThrowStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTTryStatement");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTType");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTTypeDeclaration");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTUnaryExpression");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTUnaryExpressionNotPlusMinus");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTUnmodifiedClassDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTUnmodifiedInterfaceDeclaration");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTVariableDeclarator");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTVariableDeclaratorId");
		concreteElementType
			.add("test.net.sourceforge.pmd.symboltable.MyASTVariableDeclaratorId");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTVariableInitializer");
		concreteElementType.add("net.sourceforge.pmd.ast.ASTWhileStatement");
		concreteElementType
			.add("net.sourceforge.pmd.ast.ASTInstanceOfExpression");

		final Map subclasses = new HashMap();
		subclasses.put(
			"net.sourceforge.pmd.ast.JavaParserVisitor",
			concreteVisitorType);
		subclasses.put(
			"net.sourceforge.pmd.ast.SimpleNode",
			concreteElementType);

		System.out.println(nbMessages + " " + nbClassifiers);

		// VISITOR 1 
		final IntVar v_anObjStruct =
			problem.makeBoundIntVar("anObjectStructure", 0, nbClassifiers);

		final IntVar v_aConcreteElement =
			problem.makeBoundIntVar("aConcreteElement", 0, nbClassifiers);

		final IntVar v_aConcreteVisitor =
			problem.makeBoundIntVar("aConcreteVisitor", 0, nbClassifiers);

		final IntVar v_accept =
			problem.makeBoundIntVar("accept", 0, nbMessages);

		final IntVar v_visitConcreteElement =
			problem.makeBoundIntVar("visitConcreteElement", 0, nbMessages);

		final IntVar v_operation =
			problem.makeBoundIntVar("operation", 0, nbMessages);

		//		final IntVar v_operation1Caller =
		//			problem.makeBoundIntVar("operation1Caller", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteElement, 0));
		problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteVisitor, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteVisitor,
			v_aConcreteElement,
			0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement, v_operation1Caller, 0));

		problem.post(new MessageFollows(
			v_accept,
			v_visitConcreteElement,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_visitConcreteElement,
			v_operation,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_accept,
			v_operation,
			sd,
			componentsMessages));
		problem.post(new Caller(
			v_anObjStruct,
			v_accept,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_accept,
			v_aConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteElement,
			v_visitConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_visitConcreteElement,
			v_aConcreteVisitor,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteVisitor,
			v_operation,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_operation,
			v_aConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new ParameterCalleeSameType(
			v_accept,
			v_visitConcreteElement,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new ParameterCalleeSameType(
			v_visitConcreteElement,
			v_accept,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new IsContainedInMessage(
			v_operation,
			v_visitConcreteElement,
			sd,
			componentsMessages));

		//		// VISITOR 2
		//		final IntVar v_anObjStruct2 =
		//			problem.makeBoundIntVar("anObjectStructure2", 0, nbClassifiers);
		//
		//		final IntVar v_aConcreteElement2 =
		//			problem.makeBoundIntVar("aConcreteElement2", 0, nbClassifiers);
		//
		//		final IntVar v_accept2 =
		//			problem.makeBoundIntVar("accept2", 0, nbMessages);
		//
		//		final IntVar v_visitConcreteElement2 =
		//			problem.makeBoundIntVar("visitConcreteElement2", 0, nbMessages);
		//
		//		final IntVar v_operation2 =
		//			problem.makeBoundIntVar("operation2", 0, nbMessages);
		//
		//		final IntVar v_aConcreteVisitor2 =
		//			problem.makeBoundIntVar("aConcreteVisitor2", 0, nbClassifiers);
		//
		//		final IntVar v_operation2Caller =
		//			problem.makeBoundIntVar("operation2Caller", 0, nbClassifiers);
		//
		//		problem.post(
		//			new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteElement2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteVisitor2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteVisitor2, v_aConcreteElement2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement2, v_operation2Caller, 0));
		//
		//		problem.post(
		//			new MessageFollows(
		//				v_accept2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new MessageFollows(
		//				v_visitConcreteElement2,
		//				v_operation2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new MessageFollows(
		//				v_accept2,
		//				v_operation2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new Caller(
		//				v_anObjStruct2,
		//				v_accept2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_accept2,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Caller(
		//				v_aConcreteElement2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_visitConcreteElement2,
		//				v_aConcreteVisitor2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		//		problem.post(
		//		//			new Caller(
		//		//				v_aConcreteVisitor2,
		//		//				v_operation2,
		//		//				sd,
		//		//				componentsMessages,
		//		//				allClassifiers));
		//		problem.post(
		//			new Caller(
		//				v_operation2Caller,
		//				v_operation2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_operation2,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//
		//		problem.post(
		//			new ParameterCalleeSameType(
		//				v_accept2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				subclasses));
		//
		//		problem.post(
		//			new ParameterCalleeSameType(
		//				v_visitConcreteElement2,
		//				v_accept2,
		//				sd,
		//				componentsMessages,
		//				subclasses));
		//
		//		problem.post(
		//			new IsContainedInMessage(
		//				v_operation2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages));
		//
		//		// CHECK
		//		problem.post(new PalmNotEqualXYC(v_operation1, v_operation2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteVisitor1, v_aConcreteVisitor2, 0));
		//
		//		problem.post(new PalmNotEqualXYC(v_anObjStruct1, v_anObjStruct2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement1, v_aConcreteElement2, 0));
		//
		//		//		problem.post(
		//		//			new SameType(
		//		//				v_anObjStruct1,
		//		//				v_anObjStruct2,
		//		//				sd,
		//		//				componentsMessages,
		//		//				allClassifiers));
		//		problem.post(
		//			new SameType(
		//				v_aConcreteElement1,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//
		//		problem.post(
		//			new SameType(
		//				v_operation1Caller,
		//				v_operation2Caller,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));

		problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {

			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					try {
						final Writer fstream =
							ProxyDisk
								.getInstance()
								.fileAbsoluteOutput(
									"../MoDeC Bytecode Instrumentation Tests/Solution_PMD_Visitor_VerifyNaming_shorten.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [accept]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [visitConcreteElement]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "OPERATION [operation]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "PARTICIPANT [anObjStruct]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aConcreteElement]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aConcreteVisitor]: "
								+ sd.getIdxClassifier(solution.getValue(2))
								//								+ "\nPARTICIPANT [operation1Caller]: "
								//								+ sd.getIdxClassifier(solution.getValue(6))
								//								+ "\nOPERATION [accept2]: "
								//								+ sd.getIdxMessage(solution.getValue(9))
								//								+ "OPERATION [visitConcreteElement2]:"
								//								+ sd.getIdxMessage(solution.getValue(10))
								//								+ "OPERATION [operation2]: "
								//								+ sd.getIdxMessage(solution.getValue(11))
								//								+ "PARTICIPANT [anObjStruct2]: "
								//								+ sd.getIdxClassifier(solution.getValue(7))
								//								+ "\nPARTICIPANT [aConcreteElement2]: "
								//								+ sd.getIdxClassifier(solution.getValue(8))
								//								+ "\nPARTICIPANT [aConcreteVisitor2]: "
								//								+ sd.getIdxClassifier(solution.getValue(12))
								//								+ "\nPARTICIPANT [operation2Caller]: "
								//								+ sd.getIdxClassifier(solution.getValue(13))
								+ "\n\n");

						out.close();
					}
					catch (Exception e) { //Catch exception if any
						System.err.println("Error in LogToFile.write: "
								+ e.getMessage());
					}
				}
			}
		});

		problem.solve(true);
		System.out.println("*** the end : "
				+ problem.getPalmSolver().solutions.size());
		long timeEnd = System.currentTimeMillis();
		System.out.println("Start time : " + timeEnd);
		System.out.println("Duree : " + (timeEnd - timeStart) + "ms.");
	}
}

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
import metamodel.scenariodiagram.ScenarioDiagram;

/**
 * @author Janice Ng
 */
public class Visitor_JRefactory_v2_6_24_PrettyPrinter {

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
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_JRefactory_Visitor_PrettyPrinter_XPretty.trace");

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
		concreteVisitorType.add("org.acm.seguin.parser.ChildrenVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.field.AddFieldVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.AddImplementedInterfaceVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.AddMethodVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.FindLocalVariableDeclVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.IdentifyMethodVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.ChangeMethodScopeVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.RemoveMethodVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.RenameParameterVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.MoveMethodVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.field.RemoveFieldVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.field.RenameFieldVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.RenameParentVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.RenameTypeVisitor");
		concreteVisitorType
			.add("org.acm.seguin.tools.international.StringListVisitor");
		concreteVisitorType
			.add("org.acm.seguin.parser.query.CompareParseTreeVisitor");
		concreteVisitorType.add("org.acm.seguin.parser.query.EqualTree");
		concreteVisitorType.add("org.acm.seguin.summary.LineCountVisitor");
		concreteVisitorType.add("org.acm.seguin.summary.SummaryLoadVisitor");
		concreteVisitorType.add("org.acm.seguin.pretty.PrettyPrintVisitor");
		concreteVisitorType.add("org.acm.seguin.pretty.SpecialTokenVisitor");
		concreteVisitorType.add("org.acm.seguin.tools.stub.StubPrintVisitor");

		final List concreteElementType = new ArrayList();
		concreteElementType.add("org.acm.seguin.parser.ast.SimpleNode");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTAdditiveExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTAllocationExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTAndExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTArgumentList");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTArguments");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTArrayDimsAndInits");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTArrayInitializer");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTAssignmentOperator");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTBlock");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTBlockStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTBooleanLiteral");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTBreakStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTCastExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTCastLookahead");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTClassBody");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTClassBodyDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTClassDeclaration");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTCompilationUnit");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTConditionalAndExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTConditionalExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTConditionalOrExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTConstructorDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTContinueStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTDoStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTEmptyStatement");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTEqualityExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTExclusiveOrExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTFieldDeclaration");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTForInit");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTFormalParameter");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTFormalParameters");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTForStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTForUpdate");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTIfStatement");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTImportDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTInclusiveOrExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTInitializer");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTInstanceOfExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTInterfaceBody");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTInterfaceDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTLabeledStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTLiteral");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTLocalVariableDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTMethodDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTMethodDeclarationLookahead");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTMethodDeclarator");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTMultiplicativeExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTName");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTNameList");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTNestedClassDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTNullLiteral");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTPackageDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTPostfixExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTPreDecrementExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTPreIncrementExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTPrimaryExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTPrimaryPrefix");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTPrimarySuffix");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTPrimitiveType");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTRelationalExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTResultType");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTReturnStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTShiftExpression");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTStatement");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTStatementExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTStatementExpressionList");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTSwitchLabel");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTSwitchStatement");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTSynchronizedStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTThrowStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTTryStatement");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTType");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTTypeDeclaration");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTUnaryExpression");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTUnaryExpressionNotPlusMinus");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTVariableDeclarator");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTVariableDeclaratorId");
		concreteElementType
			.add("org.acm.seguin.parser.ast.ASTVariableInitializer");
		concreteElementType.add("org.acm.seguin.parser.ast.ASTWhileStatement");

		final Map subclasses = new HashMap();
		subclasses.put(
			"org.acm.seguin.parser.JavaParserVisitor",
			concreteVisitorType);
		subclasses.put("org.acm.seguin.parser.Node", concreteElementType);

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
									"../MoDeC Bytecode Instrumentation Tests/Solution_JRefactory_Visitor_PrettyPrinter_XPretty.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [accept1]: "
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
		System.out.println("End time : " + timeEnd);
		System.out.println("Duree : " + (timeEnd - timeStart) + "ms.");
	}
}

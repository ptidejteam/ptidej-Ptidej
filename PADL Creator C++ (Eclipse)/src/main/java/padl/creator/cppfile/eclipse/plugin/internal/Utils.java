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
package padl.creator.cppfile.eclipse.plugin.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.cdt.core.dom.ast.ASTNodeFactoryFactory;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IArrayType;
import org.eclipse.cdt.core.dom.ast.IBasicType;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IPointerType;
import org.eclipse.cdt.core.dom.ast.IProblemType;
import org.eclipse.cdt.core.dom.ast.IQualifierType;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.ITypedef;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBase;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPConstructor;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPEnumeration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunctionType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMember;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPParameterPackType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPReferenceType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateTypeParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPVariable;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import padl.cpp.kernel.IDestructor;
import padl.kernel.Constants;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IStatement;
import padl.statement.kernel.IStatementFactory;
import padl.statement.kernel.impl.StatementFactory;
import util.io.ProxyConsole;

public class Utils {
	static final String ANONYMOUS = "Anonymous_";
	static final char BACK_SLASH = '\\';
	private static Map<ICPPFunction, char[]> CacheCPPFunctionsSignatures =
		new HashMap<>();
	private static Map<String, char[]> CacheCPPNamesPADLNames = new HashMap<>();
	private final static List<String> CacheErrorMessages =
		new ArrayList<String>();
	private static Map<String, char[]> CacheIDsPurifiedIDs = new HashMap<>();
	static final char[][] CONTAINER_NAMES = { "list".toCharArray(),
			"map".toCharArray(), "vector".toCharArray(), "tree".toCharArray(),
			"queue".toCharArray(), "collection".toCharArray(),
			"set".toCharArray(), "deque".toCharArray(), "stack".toCharArray() };
	private static final IASTStatement EMPTY_AST_STATEMENT =
		ASTNodeFactoryFactory.getDefaultCPPNodeFactory().newNullStatement();
	public static final FileFilter FILTER_OF_HIDDEN_FILES = new FileFilter() {
		public boolean accept(final File aFilePath) {
			if (aFilePath.getName().startsWith(".")) {
				return false;
			}
			else {
				return true;
			}
		}
	};
	static final char[][] PRIMITIVE_NAMES = { "eInt".toCharArray(),
			"u_long".toCharArray(), "u_char".toCharArray(),
			"u_int".toCharArray(), "u_short".toCharArray(),
			"byte".toCharArray(), "eChar".toCharArray(),
			"eBoolean".toCharArray(), "eDouble".toCharArray(),
			"eFloat".toCharArray(), "eVoid".toCharArray(),
			"tchar".toCharArray(), "char".toCharArray(), "bool".toCharArray(),
			"short".toCharArray(), "int".toCharArray(), "__int8".toCharArray(),
			"__int16".toCharArray(), "__int32".toCharArray(),
			"__int64".toCharArray(), "long".toCharArray(),
			"float".toCharArray(), "double".toCharArray(),
			"long double".toCharArray(), "wchar_t".toCharArray(),
			"__wchar_t".toCharArray(), "void".toCharArray() };
	static final String PROBLEM_TYPE = "ProblemType";
	static final String REMPLACEMENT = "\\\\\\\\";
	// Yann 2014/06/27: Ah, C++ and its varags...
	// I should not use '.' as a separator because of
	//	debug_msg_internal(int level, const char *fmt, ...)
	// TODO Fix this problem!
	static final char SEPARATOR = '.';
	static final char SLASH = '/';
	static void addStatementsToFunction(
		final IASTStatement statement,
		final IOperation anOperation) {

		Utils.addStatementsToFunction0(statement, anOperation);

		// I don't forget to update the variable holding code lines...
		final List<String> listOfStatements = new ArrayList<String>();
		final Iterator<?> iterator =
			anOperation.getIteratorOnConstituents(IStatement.class);
		while (iterator.hasNext()) {
			final IStatement s = (IStatement) iterator.next();
			listOfStatements.add(s.toString());
		}
		final String[] arrayOfStatements =
			listOfStatements.toArray(new String[0]);
		anOperation.setCodeLines(arrayOfStatements);
	}
	static void addStatementsToFunction(final IOperation anOperation) {
		Utils.addStatementsToFunction(Utils.EMPTY_AST_STATEMENT, anOperation);
	}
	static void addStatementsToFunction0(
		final IASTStatement statement,
		final IOperation anOperation) {

		final IStatementFactory factory =
			(IStatementFactory) StatementFactory.getInstance();
		final IStatement padlStatement;

		if (statement instanceof IASTCompoundStatement) {
			final IASTStatement[] statements =
				((IASTCompoundStatement) statement).getStatements();
			for (int i = 0; i < statements.length; i++) {
				final IASTStatement s = statements[i];
				Utils.addStatementsToFunction0(s, anOperation);
			}
		}
		else if (statement instanceof IASTIfStatement) {
			final IASTExpression expression =
				((IASTIfStatement) statement).getConditionExpression();
			if (expression != null) {
				// null if the statement has "condition declaration instead of condition expression"
				// whatever that means...
				padlStatement = factory
					.createIfInstruction(expression.toString().toCharArray());
				anOperation.addConstituent(padlStatement);
			}

			Utils.addStatementsToFunction0(
				((IASTIfStatement) statement).getThenClause(),
				anOperation);
			Utils.addStatementsToFunction0(
				((IASTIfStatement) statement).getElseClause(),
				anOperation);
		}
		else if (statement instanceof IASTSwitchStatement) {
			final IASTSwitchStatement switchStatement =
				(IASTSwitchStatement) statement;
			padlStatement = factory.createSwitchInstruction(
				switchStatement
					.getControllerExpression()
					.toString()
					.toCharArray(),
				switchStatement.getFileLocation().getEndingLineNumber()
						- switchStatement
							.getFileLocation()
							.getStartingLineNumber());
			anOperation.addConstituent(padlStatement);

			Utils.addStatementsToFunction0(
				((IASTSwitchStatement) statement).getBody(),
				anOperation);
		}
		else if (statement != null) {
			// null can occur with "empty" else blocks...
			padlStatement =
				factory.createStatement(statement.toString().toCharArray());
			anOperation.addConstituent(padlStatement);
		}
	}
	static char[] buildGlobalFunctionID(final char[] id) {
		return ArrayUtils.addAll(id, "()".toCharArray());
	}
	static char[] buildID(final char[] fieldTypeName, final char[] fieldName) {
		char[] id = ArrayUtils.add(fieldTypeName, Utils.SEPARATOR);
		id = ArrayUtils.addAll(id, fieldName);
		id = Utils.purifyID(id);
		return id;
	}
	static char[] computeSignature(final ICPPASTFunctionDefinition aDefintion) {
		return ((ICPPASTFunctionDeclarator) aDefintion.getDeclarator())
			.getRawSignature()
			.toCharArray();
	}
	static char[] computeSignature(final ICPPFunction aCPPFunction) {
		if (!Utils.CacheCPPFunctionsSignatures.containsKey(aCPPFunction)) {
			String id = aCPPFunction.toString();
			id = id.replaceAll("\\?", Utils.PROBLEM_TYPE);
			Utils.CacheCPPFunctionsSignatures
				.put(aCPPFunction, id.toCharArray());
		}
		return Utils.CacheCPPFunctionsSignatures.get(aCPPFunction);
	}
	static char[] convertSeparators(final char[] aName) {
		final String name = String.valueOf(aName);
		if (!Utils.CacheCPPNamesPADLNames.containsKey(name)) {
			final StringBuffer buffer = new StringBuffer(aName.length);
			for (int i = 0; i < aName.length; i++) {
				final char c = aName[i];
				if (c == ':') {
					buffer.append('.');
					i++;
				}
				else {
					buffer.append(c);
				}
			}
			Utils.CacheCPPNamesPADLNames
				.put(name, buffer.toString().toCharArray());
		}
		return Utils.CacheCPPNamesPADLNames.get(name);
	}
	static void copyFile(final File sourceFile, final File destFile)
			throws IOException {

		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;
		try {
			final FileInputStream fileInputStream = new FileInputStream(sourceFile);
			source = fileInputStream.getChannel();
			final FileOutputStream fileOutputStream = new FileOutputStream(destFile);
			destination = fileOutputStream.getChannel();
			destination.transferFrom(source, 0, source.size());
			fileInputStream.close();
			fileOutputStream.close();
		}
		finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
	static char[] createAnonymousName(int aCounter) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(Utils.ANONYMOUS);
		buffer.append(aCounter);
		return buffer.toString().toCharArray();
	}
	static Set<ITranslationUnit> findTranslationUnits(
		final ICElement[] children) {
		final Set<ITranslationUnit> translationUnits =
			new HashSet<ITranslationUnit>();

		for (int i = 0; i < children.length; i++) {
			final ICElement icElement = children[i];
			if (icElement instanceof ICContainer) {
				try {
					translationUnits.addAll(
						Utils.findTranslationUnits(
							((ICContainer) icElement).getChildren()));
				}
				catch (final CModelException e) {
					e.printStackTrace();
				}
			}
			else {
				// I cannot directly ask a translation unit for its
				// content because, to the best of my understanding,
				// it is limited to top-level entities, for example
				// IMethod does not provide any means to access the
				// content of a method...
				// See http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.cdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fcdt%2Fcore%2Fmodel%2FIMethod.html
				// ...and, besides, it does not provide access to the
				// internals of functions and methods, to collect
				// method invocations.
				translationUnits.add((ITranslationUnit) icElement);
			}
		}

		return translationUnits;
	}
	static IASTStatement getBody(
		final Set<IASTTranslationUnit> someASTTranslationUnit,
		final ICPPFunction function) {

		// It is possible not to find a body for things like
		// implicit constructors... So, I am checking in case!
		// http://stackoverflow.com/a/12340762/2256758
		if (function instanceof ICPPConstructor
				&& ((ICPPConstructor) function).isImplicit()) {

			return Utils.EMPTY_AST_STATEMENT;
		}

		final IScope functionScope = function.getFunctionScope();
		if (functionScope != null) {
			final ICPPASTFunctionDefinition enclosingFunction = Utils
				.getEnclosingFunction((IASTName) functionScope.getScopeName());
			if (enclosingFunction != null) {
				return enclosingFunction.getBody();
			}
		}

		Iterator<IASTTranslationUnit> iterator =
			someASTTranslationUnit.iterator();
		while (iterator.hasNext()) {
			final IASTTranslationUnit translationUnit =
				(IASTTranslationUnit) iterator.next();
			final IASTName[] names =
				translationUnit.getDefinitionsInAST(function);

			if (names.length == 0) {
				// Treat the next translation unit.
			}
			else if (names.length == 1) {
				final ICPPASTFunctionDefinition enclosingFunction =
					Utils.getEnclosingFunction(names[0]);
				return enclosingFunction.getBody();
			}
			else {
				return Utils.EMPTY_AST_STATEMENT;
			}
		}

		// For some function binding, getDefinitionsInAST() 
		// does not find the corresponding function definition! 
		// Weird behaviour of CDT again... So, just in case, I
		// try all by myself...
		iterator = someASTTranslationUnit.iterator();
		while (iterator.hasNext()) {
			final IASTTranslationUnit translationUnit =
				(IASTTranslationUnit) iterator.next();
			final IASTDeclaration[] declarations =
				translationUnit.getDeclarations();
			for (int i = 0; i < declarations.length; i++) {
				final IASTDeclaration declaration = declarations[i];
				if (declaration instanceof ICPPASTFunctionDefinition) {
					final ICPPASTFunctionDefinition defintion =
						(ICPPASTFunctionDefinition) declaration;

					final char[] functionSignature =
						Utils.getSimpleName(Utils.computeSignature(function));
					final char[] definitionSignature =
						Utils.getSimpleName(Utils.computeSignature(defintion));

					if (Arrays.equals(functionSignature, definitionSignature)) {
						return defintion.getBody();
					}
				}
			}
		}

		// In case no translation unit contains the declaration
		// because, for example, we are analysing partial pieces
		// of C++ code...
		return Utils.EMPTY_AST_STATEMENT;
	}
	static int getCardinality(final ICPPVariable aVariable) {
		final IType type = aVariable.getType();
		if (type instanceof IArrayType) {
			return Constants.CARDINALITY_MANY;
		}
		else if (type instanceof IPointerType) {
			return Constants.CARDINALITY_MANY;
		}
		else if (type instanceof IProblemType) {
			// Could we do better?
		}
		return Constants.CARDINALITY_ONE;
	}
	static int getCardinality(
		final IVariable aCPPVariable,
		final ICPPClassType aCPPClassType) {

		int cardinality = Constants.CARDINALITY_ONE;

		if (aCPPVariable.getType() instanceof IArrayType) {
			cardinality = Constants.CARDINALITY_MANY;
		}
		else if (aCPPClassType != null) {
			cardinality = Utils.getDeepCardinality(aCPPClassType);
		}

		return cardinality;
	}
	static int getDeepCardinality(final ICPPClassType theType) {
		if (Utils.isContainerName(theType.getNameCharArray())) {
			return Constants.CARDINALITY_MANY;
		}
		else if (theType.getBases() != null) {
			for (final ICPPBase base : theType.getBases()) {
				if (base.getBaseClass() instanceof ICPPClassType) {
					if (Utils.getDeepCardinality(
						(ICPPClassType) base
							.getBaseClass()) == Constants.CARDINALITY_MANY) {
						return Constants.CARDINALITY_MANY;
					}
				}
			}
		}

		return Constants.CARDINALITY_ONE;
	}
	static ICPPASTFunctionDefinition getEnclosingFunction(
		final IASTNode aNode) {
		if (aNode instanceof ICPPASTFunctionDefinition) {
			return (ICPPASTFunctionDefinition) aNode;
		}
		else if (aNode == null) {
			return null;
		}
		else {
			return Utils.getEnclosingFunction(aNode.getParent());
		}
	}
	static IType getInterestingType(final IType aType) {
		if (aType instanceof ICPPEnumeration) {
			return (ICPPEnumeration) aType;
		}
		else if (aType instanceof ITypedef) {
			return Utils.getInterestingType(((ITypedef) aType).getType());
		}
		else if (aType instanceof ICPPClassType) {
			return aType;
		}
		else if (aType instanceof IBasicType) {
			return aType;
		}
		else if (aType instanceof IArrayType) {
			return Utils.getInterestingType(((IArrayType) aType).getType());
		}
		else if (aType instanceof IPointerType) {
			return Utils.getInterestingType(((IPointerType) aType).getType());
		}
		else if (aType instanceof ICPPParameterPackType) {
			return Utils
				.getInterestingType(((ICPPParameterPackType) aType).getType());
		}
		else if (aType instanceof ICPPReferenceType) {
			return Utils
				.getInterestingType(((ICPPReferenceType) aType).getType());
		}
		else if (aType instanceof IQualifierType) {
			return Utils.getInterestingType(((IQualifierType) aType).getType());
		}
		else if (aType instanceof IProblemType) {
			return aType;
		}
		else if (aType instanceof ICPPFunctionType) {
			return Utils
				.getInterestingType(((ICPPFunctionType) aType).getReturnType());
		}
		else if (aType instanceof ICPPTemplateTypeParameter) {
			return aType;
		}
		else {
			Utils.reportUnknownType(
				Utils.class,
				"type",
				aType.toString(),
				aType.getClass());
			return null;
		}
	}
	static String getInterestingTypeName(final IType aType) {
		final IType interestingType = Utils.getInterestingType(aType);
		final String typeName;
		if (interestingType instanceof IBasicType) {
			typeName = ((IBasicType) interestingType).toString();
		}
		else if (interestingType instanceof IProblemType
				|| interestingType == null) {

			typeName = Utils.PROBLEM_TYPE;
		}
		else {
			typeName = ((IBinding) interestingType).getName();
		}
		return typeName;
	}
	static int getMethodInvocationType(
		final IOperation callingMethod,
		final IOperation calledMethod,
		final Boolean isFromField) {

		int type = IMethodInvocation.OTHER;

		if (calledMethod instanceof IDestructor) {
			type = IMethodInvocation.INSTANCE_CREATION;
		}
		else if (callingMethod.isStatic() && calledMethod.isStatic()) {
			type = IMethodInvocation.CLASS_CLASS;
		}
		else if (callingMethod.isStatic() && !calledMethod.isStatic()) {
			if (isFromField) {
				type = IMethodInvocation.CLASS_INSTANCE_FROM_FIELD;
			}
			else {
				type = IMethodInvocation.CLASS_INSTANCE;
			}
			type = IMethodInvocation.CLASS_INSTANCE;
		}
		else if (!callingMethod.isStatic() && calledMethod.isStatic()) {
			type = IMethodInvocation.INSTANCE_CLASS;
		}
		else if (!callingMethod.isStatic() && !calledMethod.isStatic()) {
			if (isFromField) {
				type = IMethodInvocation.INSTANCE_INSTANCE_FROM_FIELD;
			}
			else {
				type = IMethodInvocation.INSTANCE_INSTANCE;
			}
		}
		return type;
	}
	static char[][] getQualifiedName(final char[] aName) {
		final char[] name = Utils.convertSeparators(aName);
		final List<char[]> parts = new ArrayList<char[]>();
		int start = -1;
		int end = -1;
		// Yann 2014/06/27: Method names and parameters...
		// For the case
		//	location(enum QLibraryInfo::LibraryLocation)
		// I must also end if a parenthesis is before the next separator.
		while ((end = ArrayUtils.indexOf(name, Utils.SEPARATOR, start)) > -1
				&& end > ArrayUtils.indexOf(name, '(', start)) {

			final char[] part = ArrayUtils.subarray(name, start, end);
			start = end + 1;
			parts.add(part);
		}
		parts.add(ArrayUtils.subarray(name, start, name.length));
		final char[][] qualifiedName = new char[parts.size()][];
		parts.toArray(qualifiedName);
		return qualifiedName;
	}
	//	private static String[] getQualifiedName(final IASTName[] someNames) {
	//		final String[] names = new String[someNames.length];
	//		for (int i = 0; i < someNames.length; i++) {
	//			final IASTName name = someNames[i];
	//			names[i] = name.toString();
	//		}
	//		return names;
	//	}
	static char[] getQualifiedName(final char[][] qualifiedNamedComponents) {
		char[] tmp = qualifiedNamedComponents[0];
		for (int i = 1; i < qualifiedNamedComponents.length; i++) {
			char[] cs = qualifiedNamedComponents[i];
			tmp = ArrayUtils.add(tmp, Utils.SEPARATOR);
			tmp = ArrayUtils.addAll(tmp, cs);
		}
		return Utils.purifyID(tmp);
	}
	static char[][] getQualifiedName(
		final ICPPASTQualifiedName aQualifiedName) {
		final IASTName[] names = aQualifiedName.getNames();
		final char[][] qualifiedNameArray = new char[names.length][];
		for (int i = 0; i < names.length; i++) {
			final IASTName name = names[i];
			qualifiedNameArray[i] = name.toCharArray();
		}
		return qualifiedNameArray;
	}
	static char[][] getQualifiedType(final char[] aName) {
		final List<char[]> parts = new ArrayList<char[]>();
		int start = -1;
		int end = -1;
		while ((end = ArrayUtils.indexOf(aName, Utils.SEPARATOR, start)) > -1) {
			final char[] part = ArrayUtils.subarray(aName, start, end);
			start = end + 1;
			parts.add(part);
		}
		final char[][] qualifiedName = new char[parts.size()][];
		parts.toArray(qualifiedName);
		return qualifiedName;
	}
	static char[] getSimpleName(final char[] qualifiedNamed) {
		return Utils.getSimpleName(Utils.getQualifiedName(qualifiedNamed));
	}
	static char[] getSimpleName(final char[][] qualifiedNamedComponents) {
		return Utils.purifyID(
			qualifiedNamedComponents[qualifiedNamedComponents.length - 1]);
	}
	/**
	 * Permet de savoir si une classe est abstraite ou non Pour savoir, en
	 * C++ il suffit qu'une méthode soit déclaré virtuelle pur pour que la
	 * classe soit abstraite (non instantiable)
	 * 
	 * @param aCPPClass
	 *            La classe dont on souhaite savoir si elle est abstraite ou non
	 * @return true si la classe est abstraite, false sinon.
	 */
	static Boolean isAbstract(final ICPPClassType aCPPClass) {
		// On parcourt toutes les méthodes déclarées par la classe
		for (final ICPPMethod method : aCPPClass.getDeclaredMethods()) {
			// Si une méthode est virtuelle pure on renvoie true
			if (method.isPureVirtual()) {
				return true;
			}
		}

		// Si on a rien trouvé c'est que la classe n'est pas abstraite !!
		return false;
	}
	static Boolean isContainerName(final char[] aName) {
		if (aName == null || aName.length == 0) {
			return false;
		}
		for (final char[] containerName : Utils.CONTAINER_NAMES) {
			if (Arrays.equals(containerName, aName)) {
				return true;
			}
		}
		return false;
	}
	static boolean isInterface(final ICPPClassType cppEntity) {
		for (final ICPPMethod function : cppEntity.getAllDeclaredMethods()) {
			if (!function.isPureVirtual()) {
				return false;
			}
		}

		return true;
	}
	static Boolean isPrimitiveName(final char[] aName) {
		if (aName == null || aName.length == 0) {
			return false;
		}
		for (final char[] primitiveName : Utils.PRIMITIVE_NAMES) {
			if (Arrays.equals(primitiveName, aName)) {
				return true;
			}
		}
		return false;
	}
	static boolean isQualifiedName(final char[] aName) {
		return ArrayUtils.indexOf(aName, Utils.SEPARATOR) > -1;
	}
	static boolean isStatic(char[] aName) {
		return String.valueOf(aName).indexOf("const ") > -1;
	}
	private static char[] purifyID(final char[] anID) {
		final String id = String.valueOf(anID);
		if (!Utils.CacheIDsPurifiedIDs.containsKey(id)) {
			final StringBuffer buffer = new StringBuffer(anID.length);
			for (int i = 0; i < anID.length; i++) {
				final char c = anID[i];
				if (c == Utils.BACK_SLASH) {
					buffer.append(Utils.REMPLACEMENT);
				}
				else if (c == Utils.SLASH) {
					buffer.append(Utils.REMPLACEMENT);
				}
				else {
					buffer.append(c);
				}
			}
			Utils.CacheIDsPurifiedIDs.put(id, buffer.toString().toCharArray());
		}
		return Utils.CacheIDsPurifiedIDs.get(id);
	}
	static char[] removeQualifiers(char[] aName) {
		char cleanedName[] = ArrayUtils.subarray(
			aName,
			ArrayUtils.lastIndexOf(aName, ' ') + 1,
			aName.length);
		int indexOfLessThan = ArrayUtils.indexOf(cleanedName, '<');
		if (indexOfLessThan > -1) {
			cleanedName = ArrayUtils.subarray(cleanedName, 0, indexOfLessThan);
		}
		return cleanedName;
	}
	static void reportUnknownType(
		final Class<?> aReporter,
		final String aMessage,
		final char[] aTypeName,
		final Class<?> aTypeType) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aReporter.getName());
		buffer.append(" does not know what to do with ");
		buffer.append(aMessage);
		buffer.append(" \"");
		buffer.append(
			ArrayUtils.subarray(aTypeName, 0, Math.min(aTypeName.length, 16)));
		buffer.append("...\" (");
		buffer.append(aTypeType.getName());
		buffer.append(')');

		final String message = buffer.toString();

		if (!Utils.CacheErrorMessages.contains(message)) {
			Utils.CacheErrorMessages.add(message);
			ProxyConsole.getInstance().debugOutput().print(message);
		}
	}
	static void reportUnknownType(
		final Class<?> aReporter,
		final String aMessage,
		final String aTypeName,
		final Class<?> aTypeType) {

		Utils.reportUnknownType(
			aReporter,
			aMessage,
			aTypeName.toCharArray(),
			aTypeType);
	}
	static void setConst(final IField modelField, final ICPPVariable field) {
		if (field.getType() instanceof IQualifierType) {
			modelField.setFinal(((IQualifierType) field.getType()).isConst());
		}
		else if (field.getType() instanceof IPointerType) {
			modelField.setFinal(((IPointerType) field.getType()).isConst());
		}
	}
	static void setVisibility(
		final IConstituent aConstituent,
		final ICPPMember aMember) {

		if (aMember.isStatic()) {
			aConstituent.setStatic(true);
		}

		switch (aMember.getVisibility()) {
			case ICPPMember.v_protected :
				aConstituent.setProtected(true);
				break;

			case ICPPMember.v_public :
				aConstituent.setPublic(true);
				break;

			case ICPPMember.v_private :
			default :
				aConstituent.setPrivate(true);
				break;
		}
	}
}

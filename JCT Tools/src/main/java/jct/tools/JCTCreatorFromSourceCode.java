/**
 * @author Mathieu Lemoine
 * @created 2008-08-10 (日)
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import com.sun.source.tree.AnnotatedTypeTree;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.AnyPatternTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.ArrayTypeTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BindingPatternTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ConstantCaseLabelTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DeconstructionPatternTree;
import com.sun.source.tree.DefaultCaseLabelTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ErroneousTree;
import com.sun.source.tree.ExportsTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.IntersectionTypeTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LambdaExpressionTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.ModuleTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.OpensTree;
import com.sun.source.tree.PackageTree;
import com.sun.source.tree.ParameterizedTypeTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.PatternCaseLabelTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ProvidesTree;
import com.sun.source.tree.RequiresTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.StringTemplateTree;
import com.sun.source.tree.SwitchExpressionTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.TypeParameterTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.UnionTypeTree;
import com.sun.source.tree.UsesTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.tree.WildcardTree;
import com.sun.source.tree.YieldTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Pair;

import jct.kernel.*;
import jct.kernel.impl.JCTFactory;
import util.io.ProxyConsole;

interface Setter<T> {
	public void set(T value);
}

/**
 * This class can be use to create a JavaC AST (JCT) from a bunch of java source files
 *
 * @author Mathieu Lemoine
 */
public class JCTCreatorFromSourceCode
		implements TreeVisitor<IJCTElement, Object>,
		ElementVisitor<IJCTIdentifiable, Object>,
		TypeVisitor<IJCTType, Object> {

	/**
	 * Create a JCT from a bunch of Java Source Code Files
	 * @param aName Name of the created JCT
	 * @param extractActualSourceCode whether actual source code will be extracted from the source code files (if possible) and put in JCT, put false if you don't need actual source code information
	 * @param diag Diagnostic Listener, the compilation errors generated by JavaC will be sent to this object. You may pass null safely.
	 * @param options Options as you would pass them to JavaC.
	 * @param files Files to compile
	 * @return a newly created JCT
	 */
	public static IJCTRootNode createJCT(final String aName,
			final boolean extractActualSourceCode,
			final DiagnosticListener<? super JavaFileObject> diag,
			final Iterable<String> options, final File... files)
			throws IOException {

		// Yann 2013/05/14: Old version...
		// Throw NullPointerException with tests
		// due to wrong path in the files, not
		// very user-friendly!
		final JavacTool comp = JavacTool.create();
		final StandardJavaFileManager fm = comp.getStandardFileManager(null,
				null, null);
		final Iterable<? extends JavaFileObject> obj = fm
				.getJavaFileObjects(files);
		final JavacTask task = comp.getTask(null, fm, diag, options, null, obj);
		//	final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//	final DiagnosticCollector<JavaFileObject> diagnostics =
		//		new DiagnosticCollector<JavaFileObject>();
		//	final StandardJavaFileManager fileManager =
		//		compiler.getStandardFileManager(diagnostics, null, null);
		//	final Iterable<? extends JavaFileObject> compilationUnits =
		//		fileManager.getJavaFileObjects(files);
		//	final JavaCompiler.CompilationTask task =
		//		compiler.getTask(
		//			null,
		//			fileManager,
		//			diagnostics,
		//			null,
		//			null,
		//			compilationUnits);
		//	boolean success = task.call();
		//	fileManager.close();

		final JavacElements elements = (JavacElements) task.getElements();
		final JavacTrees trees = JavacTrees.instance(task);

		final Set<PackageElement> packages = new HashSet<PackageElement>();
		for (final Element s : task.analyze())
			packages.add(elements.getPackageOf(s));

		final IJCTRootNode JCTRootNode = JCTFactory.createJCT(aName);
		final IJCTFactory JCTFactory = JCTRootNode.getFactory();
		final Map<CompilationUnitTree, IJCTPackage> cup = new HashMap<CompilationUnitTree, IJCTPackage>();

		for (final PackageElement p : packages) {
			final IJCTPackage aJCTPackage = JCTFactory.createPackage(
					p.isUnnamed() ? null : p.getQualifiedName().toString(),
					false);
			JCTRootNode.addPackage(aJCTPackage);

			final Pair<JCTree, JCTree.JCCompilationUnit> pt = elements
					.getTreeAndTopLevel(p, null, null);
			if (null != pt)
				aJCTPackage
						.setPackageDeclaration(JCTFactory.createCompilationUnit(
								new File(pt.snd.getSourceFile().toUri())));

			final Set<CompilationUnitTree> cus = new HashSet<CompilationUnitTree>();
			for (final Element e : p.getEnclosedElements()) {
				final Pair<JCTree, JCTree.JCCompilationUnit> et = elements
						.getTreeAndTopLevel(e, null, null);
				if (null != et && null != et.snd)
					cus.add(et.snd);
			}

			for (final CompilationUnitTree cu : cus)
				if (null != cu)
					cup.put(cu, aJCTPackage);
		}

		final JCTCreatorFromSourceCode jctCreator = new JCTCreatorFromSourceCode(
				JCTRootNode, elements, trees, cup.keySet(),
				extractActualSourceCode);

		for (final Map.Entry<CompilationUnitTree, IJCTPackage> e : cup
				.entrySet())
			e.getValue().addCompilationUnit(
					(IJCTCompilationUnit) e.getKey().accept(jctCreator, null));

		// Add Ghost Package Declaration Compilation Unit
		{
			final Pattern package_line = Pattern
					.compile("^\\s*package\\s+([^\\s;]+)\\s*;\\s*$");
			final String comment_start = "/*";
			final String comment_end = "*/";

			for (final File file : files)
				if ("package-info.java".equals(file.getName()))
					try {
						final BufferedReader reader = new BufferedReader(
								new FileReader(file));
						String line;
						boolean in_code = true;
						file_reading: while (null != (line = reader.readLine()))
							if (in_code) {
								final Matcher m = package_line.matcher(line);
								if (m.matches()) {
									for (final IJCTPackage p : JCTRootNode
											.getPackages())
										if (!p.isUnnamed() && m.group(1)
												.equals(p.getName())) {
											if (null == p
													.getPackageDeclaration())
												p.setPackageDeclaration(
														JCTFactory
																.createCompilationUnit(
																		file));
											break file_reading;
										}

									final IJCTPackage p = JCTFactory
											.createPackage(m.group(1), true);
									JCTRootNode.addPackage(p);
									p.setPackageDeclaration(JCTFactory
											.createCompilationUnit(file));
									break file_reading;
								}

								if (line.contains(comment_start))
									in_code = false;
							}
							else if (line.contains(comment_end))
								in_code = true;
					}
					catch (final IOException e) {
					}
		}

		// Add Ghost Classes and Packages
		while (jctCreator.classes.entrySet().size() > 0) {
			final Iterator<Map.Entry<String, IJCTClass>> it = jctCreator.classes
					.entrySet().iterator();
			final Map.Entry<String, IJCTClass> e = it.next();
			it.remove();

			if (null != e.getValue().getEnclosingElement())
				continue;

			e.getValue().setIsGhost(true);

			int index = e.getKey().lastIndexOf('$');
			if (-1 != index) // Inner Class
			{
				final String enclosingClassBName = e.getKey().substring(0,
						index);
				final IJCTClassType enclosingClassType = JCTRootNode.getType(
						"L" + enclosingClassBName, IJCTClassType.class);

				IJCTClass enclosingClass = enclosingClassType == null ? null
						: enclosingClassType.getSelector().getElement();

				if (null == enclosingClass)
					enclosingClass = jctCreator.classes
							.get(enclosingClassBName);

				if (null == enclosingClass) {
					index = enclosingClassBName.lastIndexOf('$');

					if (-1 == index)
						index = enclosingClassBName.lastIndexOf('.');

					++index;

					final String name = enclosingClassBName.substring(index);
					enclosingClass = JCTFactory.createClass(name, false, false);
					jctCreator.classes.put(enclosingClassBName, enclosingClass);
					jctCreator.classeNames.put(enclosingClass,
							enclosingClassBName);
				}

				enclosingClass.addDeclaredMember(e.getValue());
			}
			else // Top Level Class
			{
				index = e.getKey().lastIndexOf('.');
				IJCTPackage p = null;
				if (-1 == index) {
					p = JCTFactory.createPackage(null, false);
					JCTRootNode.addPackage(p);
				}
				else {
					final String packageName = e.getKey().substring(0, index);
					for (final IJCTPackage ip : JCTRootNode.getPackages())
						if (packageName.equals(ip.getName())) {
							p = ip;
							break;
						}

					if (null == p) {
						p = JCTFactory.createPackage(packageName, true);
						JCTRootNode.addPackage(p);
					}
				}

				final IJCTCompilationUnit cu = JCTFactory.createCompilationUnit(
						new File(e.getKey().replace('.', File.separatorChar)
								+ ".class"));
				p.addCompilationUnit(cu);

				cu.addClazz(e.getValue());
			}
		}

		if (extractActualSourceCode) {
			JCTRootNode.accept(new OffsetTranslator());
		}

		return JCTRootNode;
	}

	private final boolean extractActualSourceCode;
	private final IJCTRootNode rootNode;
	private final IJCTFactory factory;

	private final JavacElements javacElements;
	private final SourcePositions positions;

	private final Map<String, IJCTLabel> labels = new HashMap<String, IJCTLabel>();
	private final Map<String, IJCTClass> classes = new HashMap<String, IJCTClass>();
	private final Map<IJCTClass, String> classeNames = new HashMap<IJCTClass, String>();

	private final Map<Element, IJCTIdentifiable> identifiables = new HashMap<Element, IJCTIdentifiable>();

	private final Set<CompilationUnitTree> cus;

	protected JCTCreatorFromSourceCode(final IJCTRootNode root,
			final JavacElements elements, final JavacTrees trees,
			final Set<CompilationUnitTree> cus,
			final boolean extractActualSourceCode) {
		this.rootNode = root;
		this.factory = root.getFactory();
		this.javacElements = elements;
		this.positions = trees.getSourcePositions();
		this.cus = cus;
		this.extractActualSourceCode = extractActualSourceCode;
	}

	private <T extends IJCTIdentifiable & IJCTSourceCodePart> IJCTIdentifiable putSourceCodePosition(
			final T part, final Element e) {
		if (!this.extractActualSourceCode)
			return part;

		final Pair<JCTree, JCTree.JCCompilationUnit> tree = this.javacElements
				.getTreeAndTopLevel(e, null, null);
		if (null != tree) {
			final int offset = (int) this.positions.getStartPosition(tree.snd,
					tree.fst);
			if (Diagnostic.NOPOS != offset) {
				part.setStoredSourceCodeOffset(offset);
				final int end = (int) this.positions.getEndPosition(tree.snd,
						tree.fst);
				if (Diagnostic.NOPOS != end) {
					part.setStoredSourceCodeLength(end - offset);
				}
			}
		}

		return part;
	}

	private IJCTSourceCodePart putSourceCodePosition(
			final IJCTSourceCodePart part, final Tree t) {
		if (!this.extractActualSourceCode)
			return part;

		for (final CompilationUnitTree cu : this.cus) {
			final int offset = (int) this.positions.getStartPosition(cu, t);
			if (Diagnostic.NOPOS != offset) {
				part.setStoredSourceCodeOffset(offset);
				final int end = (int) this.positions.getEndPosition(cu, t);
				if (Diagnostic.NOPOS != end) {
					part.setStoredSourceCodeLength(end - offset);
				}
				break;
			}
		}

		return part;
	}

	public IJCTType visit(final TypeMirror t) {
		return this.visit(t, null);
	}

	public IJCTType visit(final TypeMirror t, final Object p) {
		throw new IllegalArgumentException("Unknown type " + t);
	}

	public IJCTType visitArray(final ArrayType t, final Object p) {
		TypeMirror aTypeMirror = t.getComponentType();

		String name;

		while (TypeKind.TYPEVAR == aTypeMirror.getKind())
			aTypeMirror = ((Type.TypeVar) aTypeMirror).getUpperBound();

		final IJCTType aJCTType = aTypeMirror.accept(this, p);

		if (JCTKind.CLASS_TYPE == aJCTType.getKind())
			name = "L" + ((Symbol.ClassSymbol) ((DeclaredType) aTypeMirror)
					.asElement()).flatname.toString();
		else
			name = aJCTType.getTypeName();

		return this.rootNode.registerArrayType(aJCTType, name);
	}

	public IJCTType visitDeclared(final DeclaredType t, final Object p) {
		return ((IJCTClass) t.asElement().accept(this, p)).createClassType();
	}

	public IJCTType visitError(final ErrorType t, final Object p) {
		return this.visit(t, p);
	}

	public IJCTType visitExecutable(final ExecutableType t, final Object p) {
		return this.visit(t, p);
	}

	public IJCTType visitNoType(final NoType t, final Object p) {
		return this.rootNode.getType(JCTPrimitiveTypes.VOID);
	}

	public IJCTType visitNull(final NullType t, final Object p) {
		return this.visit(t, p);
	}

	public IJCTType visitPrimitive(final PrimitiveType t, final Object p) {
		IJCTType aType;

		switch (t.getKind()) {
		case INT:
			aType = this.rootNode.getType(JCTPrimitiveTypes.INTEGER);
			break;
		case CHAR:
			aType = this.rootNode.getType(JCTPrimitiveTypes.CHARACTER);
			break;
		default:
			aType = this.rootNode
					.getType(JCTPrimitiveTypes.valueOf(t.getKind().toString()));
			break;
		}

		return aType;
	}

	public IJCTType visitTypeVariable(final TypeVariable t, final Object p) {
		return ((IJCTClass) t.asElement().accept(this, p)).createClassType();
	}

	public IJCTType visitUnknown(final TypeMirror t, final Object p) {
		return this.visit(t, p);
	}

	public IJCTType visitUnion(UnionType t, Object o) {
		return null;
	}

	public IJCTType visitIntersection(IntersectionType t, Object o) {
		return null;
	}

	public IJCTType visitWildcard(final WildcardType t, final Object p) {
		return t.getExtendsBound().accept(this, p);
	}

	public IJCTIdentifiable visit(final Element e) {
		return this.visit(e, null);
	}

	public IJCTIdentifiable visit(final Element e, final Object p) {
		throw new IllegalArgumentException("Unknown element");
	}

	public IJCTIdentifiable visitPackage(final PackageElement e,
			final Object param) {
		final IJCTIdentifiable i = this.identifiables.get(e);
		if (null != i)
			return i;

		final Tree t = this.javacElements.getTree(e);
		if (null != t) {
			final IJCTElement element = t.accept(this, param);
			if (element instanceof IJCTPackage) {
				this.identifiables.put(e, (IJCTPackage) element);
				return (IJCTPackage) element;
			}
		}

		final IJCTPackage p = e.isUnnamed()
				? this.factory.createPackage(null, false)
				: this.factory.createPackage(e.getQualifiedName().toString(),
						false);

		this.identifiables.put(e, p);
		this.rootNode.addPackage(p);

		for (final Element aElement : e.getEnclosedElements())
			aElement.accept(this, param);

		return p;
	}

	public IJCTIdentifiable visitType(final TypeElement e, final Object p) {
		final IJCTIdentifiable i = this.identifiables.get(e);
		if (null != i)
			return i;

		final Tree t = this.javacElements.getTree(e);
		if (null != t) {
			final IJCTIdentifiable aJCTIdentifiable = (IJCTIdentifiable) t
					.accept(this, p);
			this.identifiables.put(e, aJCTIdentifiable);
			return aJCTIdentifiable;
		}

		final IJCTClass c = this.factory
				.createClass(e.getSimpleName().toString(),
						ElementKind.INTERFACE == e.getKind()
								|| ElementKind.ANNOTATION_TYPE == e.getKind(),
						false);

		this.classes.put(this.javacElements.getBinaryName(e).toString(), c);
		this.classeNames.put(c, this.javacElements.getBinaryName(e).toString());

		this.identifiables.put(e, c);

		while (c.getModifiers().size() > 0) {
			c.removeModifier(c.getModifiers().iterator().next());
		}

		for (final Modifier mod : e.getModifiers())
			try {
				c.addModifier(
						JCTModifiers.valueOf(mod.toString().toUpperCase()));
			}
			catch (final IllegalArgumentException iae) {
				ProxyConsole.getInstance().errorOutput()
						.print(this.getClass().getName());
				ProxyConsole.getInstance().errorOutput()
						.print(" must handle modifier: ");
				ProxyConsole.getInstance().errorOutput().println(mod);
			}

		if (null != e.getSuperclass()
				&& TypeKind.NONE != e.getSuperclass().getKind()) {
			final IJCTClassType aJCTClassType = (IJCTClassType) e
					.getSuperclass().accept(this, p);
			if (aJCTClassType.getSelector().getElement().getIsInterface())
				c.addDirectlyImplementedInterface(aJCTClassType);
			else
				c.setDirectSuperClass(aJCTClassType);
		}

		for (final TypeMirror aTypeMirror : e.getInterfaces())
			c.addDirectlyImplementedInterface(
					(IJCTClassType) aTypeMirror.accept(this, p));

		for (final Element anElement : e.getEnclosedElements()) {
			final IJCTElement aJCTElement = anElement.accept(this, p);
			switch (aJCTElement.getKind()) {
			case CLASS:
			case METHOD:
			case VARIABLE:
				c.addDeclaredMember((IJCTClassMember) aJCTElement);
				break;
			case BLOCK: {
				final IJCTMethod init;
				if (anElement.getModifiers().contains(Modifier.STATIC)) {
					init = this.factory.createMethod("<clinit>");
					init.addModifier(JCTModifiers.STATIC);
				}
				else
					init = this.factory.createMethod(">init<");

				init.addModifier(JCTModifiers.PRIVATE);
				init.addModifier(JCTModifiers.FINAL);
				init.setBody((IJCTBlock) aJCTElement);
				c.addDeclaredMember(init);
			}
				break;
			default:
				throw new AssertionError(
						"The elements authorized in a class are only class members and blocks (initializers).");
			}
		}

		return c;
	}

	public IJCTIdentifiable visitVariable(final VariableElement e,
			final Object p) {

		if (null != e.getEnclosingElement())
			e.getEnclosingElement().accept(this, p);

		final IJCTIdentifiable i = this.identifiables.get(e);
		if (null != i)
			return i;

		final Tree t = this.javacElements.getTree(e);
		if (null != t) {
			final IJCTIdentifiable aJCTIdentifiable = (IJCTIdentifiable) t
					.accept(this, p);
			this.identifiables.put(e, aJCTIdentifiable);
			return aJCTIdentifiable;
		}

		final IJCTVariable v = this.factory
				.createVariable(e.getSimpleName().toString());
		v.setType(this.rootNode.getType("Ljava.lang.Object",
				IJCTClassType.class));
		this.identifiables.put(e, v);

		while (v.getModifiers().size() > 0) {
			v.removeModifier(v.getModifiers().iterator().next());
		}

		for (final Modifier m : e.getModifiers())
			v.addModifier(JCTModifiers.valueOf(m.toString().toUpperCase()));

		return this.putSourceCodePosition(v, e);
	}

	public IJCTIdentifiable visitExecutable(final ExecutableElement e,
			final Object p) {
		if (null != e.getEnclosingElement())
			e.getEnclosingElement().accept(this, p);

		final IJCTIdentifiable i = this.identifiables.get(e);
		if (null != i)
			return i;

		final Tree t = this.javacElements.getTree(e);
		if (null != t) {
			final IJCTIdentifiable aJCTIdentifiable = (IJCTIdentifiable) t
					.accept(this, p);
			this.identifiables.put(e, aJCTIdentifiable);
			return aJCTIdentifiable;
		}

		final IJCTMethod m = this.factory
				.createMethod(e.getSimpleName().toString());
		this.identifiables.put(e, m);

		if (null != e.asType() && null != e.getReturnType())
			m.setReturnType(e.getReturnType().accept(this, p));

		while (m.getModifiers().size() > 0) {
			m.removeModifier(m.getModifiers().iterator().next());
		}

		for (final Modifier mod : e.getModifiers())
			try {
				m.addModifier(
						JCTModifiers.valueOf(mod.toString().toUpperCase()));
			}
			catch (final IllegalArgumentException iae) {
				ProxyConsole.getInstance().errorOutput()
						.print(this.getClass().getName());
				ProxyConsole.getInstance().errorOutput()
						.print(" must handle modifier: ");
				ProxyConsole.getInstance().errorOutput().println(mod);
			}

		if (null != e.asType()) {
			for (final VariableElement v : e.getParameters())
				m.addParameter((IJCTParameter) v.accept(this, p));

			for (final TypeMirror aTypeMirror : e.getThrownTypes())
				m.addThrownException(
						(IJCTClassType) aTypeMirror.accept(this, p));
		}

		return this.putSourceCodePosition(m, e);
	}

	public IJCTIdentifiable visitTypeParameter(final TypeParameterElement e,
			final Object p) {
		if (null != e.getEnclosingElement())
			e.getEnclosingElement().accept(this, p);

		for (final TypeMirror aTypeMirror : e.getBounds()) {
			final IJCTType t = aTypeMirror.accept(this, p);
			if (JCTKind.CLASS_TYPE == t.getKind())
				return ((IJCTClassType) t).getSelector().getElement();
		}

		return this.rootNode.getType("Ljava.lang.Object", IJCTClassType.class)
				.getSelector().getElement();
	}

	public IJCTIdentifiable visitUnknown(final Element e, final Object p) {
		return null;
	}

	public IJCTElement visitOther(final Tree o, final Object p) {
		return null;
	}

	public IJCTElement visitYield(YieldTree node, Object o) {
		return null;
	}

	public IJCTElement error(final Tree o, final Object p) {
		throw new IllegalStateException("Unknown Tree Part ==> " + o.getKind()
				+ " :: " + o + " (argument :" + p + ")");
	}

	public IJCTElement visitAnnotatedType(AnnotatedTypeTree node, Object o) {
		return null;
	}

	public IJCTElement visitAnnotation(final AnnotationTree node,
			final Object p) {
		return this.visitOther(node, p);
	}

	public IJCTElement visitArrayAccess(final ArrayAccessTree node,
			final Object p) {
		return this.putSourceCodePosition(
				this.factory.createArrayAccess(
						(IJCTExpression) node.getExpression().accept(this, p),
						(IJCTExpression) node.getIndex().accept(this, p)),
				node);
	}

	public IJCTElement visitArrayType(final ArrayTypeTree node,
			final Object p) {
		final IJCTElement t = node.getType().accept(this, p);

		IJCTType underlyingType = null;

		String underlyingTypeName = null;

		if (t instanceof IJCTType) {
			underlyingType = (IJCTType) t;
			if (JCTKind.CLASS_TYPE == t.getKind())
				underlyingTypeName = this.javacElements.getBinaryName(
						(Symbol.ClassSymbol) ((JCTree.JCIdent) node
								.getType()).sym)
						.toString();
		}
		else if (t instanceof IJCTSelector<?>
				&& ((IJCTSelector<?>) t).getElement() instanceof IJCTClass) {
			Tree type = node.getType();

			if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
				type = ((ParameterizedTypeTree) type).getType();

			underlyingType = ((IJCTClass) ((IJCTSelector<?>) t).getElement())
					.createClassType();
			underlyingTypeName = "L"
					+ this.classeNames.get(((IJCTSelector<?>) t).getElement());
		}

		if (null != underlyingType)
			return this.rootNode.registerArrayType(underlyingType,
					underlyingTypeName);

		if (JCTKind.ERRONEOUS_SELECTOR == t.getKind()) {
			ProxyConsole.getInstance().debugOutput().println(
					"ArrayType with erroneous selector " + t.getSourceCode());
			return this.rootNode.registerArrayType(null, t.getSourceCode());
		}

		return null;
	}

	public IJCTElement visitAssert(final AssertTree node, final Object p) {
		final IJCTAssert r = this.factory.createAssert(
				(IJCTExpression) node.getCondition().accept(this, p));
		if (null != node.getDetail())
			r.setDetail((IJCTExpression) node.getDetail().accept(this, p));
		return this.putSourceCodePosition(r, node);
	}

	public IJCTElement visitAssignment(final AssignmentTree node,
			final Object p) {
		return this.putSourceCodePosition(
				this.factory.createAssignment(
						(IJCTExpression) node.getVariable().accept(this, p),
						(IJCTExpression) node.getExpression().accept(this, p)),
				node);
	}

	public IJCTElement visitBinary(final BinaryTree node, final Object p) {
		final IJCTExpression left = (IJCTExpression) node.getLeftOperand()
				.accept(this, p);
		final IJCTExpression right = (IJCTExpression) node.getRightOperand()
				.accept(this, p);
		IJCTBinaryOperator result;
		switch (node.getKind()) {
		case EQUAL_TO:
			result = this.factory.createEqualTo(left, right);
			break;
		case NOT_EQUAL_TO:
			result = this.factory.createNotEqualTo(left, right);
			break;
		case CONDITIONAL_AND:
			result = this.factory.createConditionalAnd(left, right);
			break;
		case CONDITIONAL_OR:
			result = this.factory.createConditionalOr(left, right);
			break;
		case LESS_THAN:
			result = this.factory.createLessThan(left, right);
			break;
		case LESS_THAN_EQUAL:
			result = this.factory.createLessThanOrEqual(left, right);
			break;
		case GREATER_THAN:
			result = this.factory.createGreaterThan(left, right);
			break;
		case GREATER_THAN_EQUAL:
			result = this.factory.createGreaterThanOrEqual(left, right);
			break;

		case AND:
			result = this.factory.createAnd(left, right);
			break;
		case DIVIDE:
			result = this.factory.createDivide(left, right);
			break;
		case LEFT_SHIFT:
			result = this.factory.createLeftShift(left, right);
			break;
		case MINUS:
			result = this.factory.createMinus(left, right);
			break;
		case MULTIPLY:
			result = this.factory.createMultiply(left, right);
			break;
		case OR:
			result = this.factory.createOr(left, right);
			break;
		case PLUS:
			result = this.factory.createPlus(left, right);
			break;
		case REMAINDER:
			result = this.factory.createRemainder(left, right);
			break;
		case RIGHT_SHIFT:
			result = this.factory.createRightShift(left, right);
			break;
		case UNSIGNED_RIGHT_SHIFT:
			result = this.factory.createUnsignedRightShift(left, right);
			break;
		case XOR:
			result = this.factory.createXor(left, right);
			break;
		default:
			throw new IllegalStateException(
					"A Binary Operator must have a binary operator kind");
		}
		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitBlock(final BlockTree node, final Object p) {
		final IJCTBlock aJCTBlock = this.factory.createBlock();
		for (final StatementTree statement : node.getStatements())
			aJCTBlock.addStatement((IJCTStatement) statement.accept(this, p));
		return this.putSourceCodePosition(aJCTBlock, node);
	}

	public IJCTElement visitBreak(final BreakTree node, final Object p) {
		final IJCTBreak aJCTBreak = this.factory.createBreak();
		final Name name = node.getLabel();
		if (name != null)
			aJCTBreak.setLabel(this.labels.get(name.toString()));
		return this.putSourceCodePosition(aJCTBreak, node);
	}

	public IJCTElement visitCase(final CaseTree node, final Object p) {
		final IJCTCase aJCTCase = this.factory.createCase();
		if (null != node.getExpression())
			aJCTCase.setLabel(
					(IJCTExpression) node.getExpression().accept(this, p));
		for (final StatementTree statement : node.getStatements())
			aJCTCase.addStatement((IJCTStatement) statement.accept(this, p));
		return this.putSourceCodePosition(aJCTCase, node);
	}

	public IJCTElement visitCatch(final CatchTree node, final Object p) {
		final IJCTCatch aJCTCatch = this.factory.createCatch(
				(IJCTVariable) node.getParameter().accept(this, p));
		aJCTCatch.setBody((IJCTBlock) node.getBlock().accept(this, p));
		return this.putSourceCodePosition(aJCTCatch, node);
	}

	public IJCTElement visitClass(final ClassTree node, final Object p) {
		final IJCTClass c = (IJCTClass) this.identifiables
				.get(((JCTree.JCClassDecl) node).sym);

		if (c != null)
			return c;

		final IJCTClass aJCTClass = this.factory.createClass(
				node.getSimpleName().toString(),
				((JCTree.JCClassDecl) node).sym.isInterface(), false);

		this.classes.put(this.javacElements
				.getBinaryName(((JCTree.JCClassDecl) node).sym).toString(),
				aJCTClass);
		this.classeNames.put(aJCTClass, this.javacElements
				.getBinaryName(((JCTree.JCClassDecl) node).sym).toString());

		this.identifiables.put(((JCTree.JCClassDecl) node).sym, aJCTClass);

		if (!aJCTClass.getIsInterface() && null != node.getExtendsClause())
			aJCTClass.setDirectSuperClass(
					(IJCTClassType) ((JCTree.JCClassDecl) node).sym
							.getSuperclass().accept(this, p));

		for (final Type implemented : ((JCTree.JCClassDecl) node).sym
				.getInterfaces())
			aJCTClass.addDirectlyImplementedInterface(
					(IJCTClassType) implemented.accept(this, p));

		for (final Modifier m : node.getModifiers().getFlags())
			aJCTClass.addModifier(
					JCTModifiers.valueOf(m.toString().toUpperCase()));

		for (final Tree member : node.getMembers()) {
			final IJCTElement aJCTElement = member.accept(this, p);
			switch (aJCTElement.getKind()) {
			case CLASS:
			case METHOD:
			case VARIABLE:
				aJCTClass.addDeclaredMember((IJCTClassMember) aJCTElement);
				break;
			case BLOCK: {
				final IJCTMethod init;
				if (((BlockTree) member).isStatic()) {
					init = this.factory.createMethod("<clinit>");
					init.addModifier(JCTModifiers.STATIC);
				}
				else
					init = this.factory.createMethod(">init<");

				init.addModifier(JCTModifiers.PRIVATE);
				init.addModifier(JCTModifiers.FINAL);
				init.setBody((IJCTBlock) aJCTElement);
				aJCTClass.addDeclaredMember(init);
			}
				break;
			default:
				throw new AssertionError(
						"The elements authorized in a class are only class members and blocks (initializers).");
			}
		}

		return this.putSourceCodePosition(aJCTClass, node);
	}

	public IJCTElement visitCompilationUnit(final CompilationUnitTree node,
			final Object p) {

		final IJCTCompilationUnit aJCTCompilationUnit = this.factory
				.createCompilationUnit(new File(node.getSourceFile().toUri()));

		for (final Tree t : node.getTypeDecls())
			aJCTCompilationUnit.addClazz((IJCTClass) t.accept(this, p));

		for (final ImportTree i : node.getImports()) {
			final IJCTImport aJCTImport = (IJCTImport) i.accept(this,
					aJCTCompilationUnit);
			if (null != aJCTImport)
				aJCTCompilationUnit.addImported(aJCTImport);
		}

		try {
			final char characters[] = new char[(int) aJCTCompilationUnit
					.getSourceFile().length()];
			final BufferedReader buffer = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							aJCTCompilationUnit.getSourceFile())));
			final int length = buffer.read(characters, 0, characters.length);
			buffer.close();
			aJCTCompilationUnit
					.setStoredSourceCode(new String(characters, 0, length));
		}
		catch (final IOException e) {
		}

		return aJCTCompilationUnit;
	}

	public IJCTElement visitCompoundAssignment(
			final CompoundAssignmentTree node, final Object p) {
		final IJCTExpression variable = (IJCTExpression) node.getVariable()
				.accept(this, p);
		final IJCTExpression value = (IJCTExpression) node.getExpression()
				.accept(this, p);
		IJCTAssignment result;
		switch (node.getKind()) {
		case AND_ASSIGNMENT:
			result = this.factory.createAndAssignment(variable, value);
			break;
		case DIVIDE_ASSIGNMENT:
			result = this.factory.createDivideAssignment(variable, value);
			break;
		case LEFT_SHIFT_ASSIGNMENT:
			result = this.factory.createLeftShiftAssignment(variable, value);
			break;
		case MINUS_ASSIGNMENT:
			result = this.factory.createMinusAssignment(variable, value);
			break;
		case MULTIPLY_ASSIGNMENT:
			result = this.factory.createMultiplyAssignment(variable, value);
			break;
		case OR_ASSIGNMENT:
			result = this.factory.createOrAssignment(variable, value);
			break;
		case PLUS_ASSIGNMENT:
			result = this.factory.createPlusAssignment(variable, value);
			break;
		case REMAINDER_ASSIGNMENT:
			result = this.factory.createRemainderAssignment(variable, value);
			break;
		case RIGHT_SHIFT_ASSIGNMENT:
			result = this.factory.createRightShiftAssignment(variable, value);
			break;
		case UNSIGNED_RIGHT_SHIFT_ASSIGNMENT:
			result = this.factory.createUnsignedRightShiftAssignment(variable,
					value);
			break;
		case XOR_ASSIGNMENT:
			result = this.factory.createXorAssignment(variable, value);
			break;
		default:
			throw new IllegalStateException(
					"An Assignment Operator must have an assignment kind");
		}
		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitConditionalExpression(
			final ConditionalExpressionTree node, final Object p) {
		return this
				.putSourceCodePosition(this.factory.createConditionalOperator(
						(IJCTExpression) node.getCondition().accept(this, p),
						(IJCTExpression) node.getTrueExpression().accept(this,
								p),
						(IJCTExpression) node.getFalseExpression().accept(this,
								p)),
						node);
	}

	public IJCTElement visitContinue(final ContinueTree node, final Object p) {
		final IJCTContinue aJCTContinue = this.factory.createContinue();
		final Name name = node.getLabel();
		if (name != null)
			aJCTContinue.setLabel(this.labels.get(name.toString()));
		return this.putSourceCodePosition(aJCTContinue, node);
	}

	public IJCTElement visitDoWhileLoop(final DoWhileLoopTree node,
			final Object p) {
		final IJCTDoWhile result = this.factory.createDoWhile(
				(IJCTExpression) node.getCondition().accept(this, p));
		result.setBody((IJCTStatement) node.getStatement().accept(this, p));

		if (JCTKind.PARENTHESIS == result.getCondition().getKind()) {
			final IJCTExpression expr = ((IJCTParenthesis) result
					.getCondition()).getExpression();
			((IJCTParenthesis) result.getCondition())
					.setExpression(this.factory.createBooleanLiteral(true));
			result.setCondition(expr);
		}

		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitEmptyStatement(final EmptyStatementTree node,
			final Object p) {
		return this.putSourceCodePosition(this.factory.createEmptyStatement(),
				node);
	}

	public IJCTElement visitEnhancedForLoop(final EnhancedForLoopTree node,
			final Object p) {
		return this.putSourceCodePosition(
				this.factory.createEnhancedFor(
						(IJCTVariable) node.getVariable().accept(this, p),
						(IJCTExpression) node.getExpression().accept(this, p),
						(IJCTStatement) node.getStatement().accept(this, p)),
				node);
	}

	public IJCTElement visitErroneous(final ErroneousTree node,
			final Object p) {
		final IJCTErroneousExpression aJCTErroneousExpression = this.factory
				.createErroneousExpression();
		for (final Tree t : node.getErrorTrees())
			aJCTErroneousExpression
					.addExpression((IJCTExpression) t.accept(this, p));
		return this.putSourceCodePosition(aJCTErroneousExpression, node);
	}

	public IJCTElement visitExpressionStatement(
			final ExpressionStatementTree node, final Object p) {
		return this.putSourceCodePosition(
				this.factory.createExpressionStatement(
						(IJCTExpression) node.getExpression().accept(this, p)),
				node);
	}

	public IJCTElement visitForLoop(final ForLoopTree node, final Object p) {
		final IJCTFor aJCTFor = this.factory.createFor(
				(IJCTExpression) node.getCondition().accept(this, p),
				(IJCTStatement) node.getStatement().accept(this, p));

		for (final StatementTree init : node.getInitializer())
			aJCTFor.addInitializer((IJCTStatement) init.accept(this, p));

		for (final ExpressionStatementTree update : node.getUpdate())
			aJCTFor.addUpdater(
					(IJCTExpressionStatement) update.accept(this, p));

		return this.putSourceCodePosition(aJCTFor, node);
	}

	public IJCTElement visitIdentifier(final IdentifierTree node,
			final Object p) {
		if (null != ((JCTree.JCIdent) node).sym)
			return this.putSourceCodePosition(this.factory.createSimpleSelector(
					(IJCTIdentifiable) ((JCTree.JCIdent) node).sym.accept(this,
							p)),
					node);
		return this.putSourceCodePosition(
				this.factory.createErroneousSelector(node.getName().toString()),
				node);
	}

	public IJCTElement visitIf(final IfTree node, final Object p) {
		final IJCTIf aJCTIf = this.factory.createIf(
				(IJCTExpression) node.getCondition().accept(this, p),
				(IJCTStatement) node.getThenStatement().accept(this, p));
		if (null != node.getElseStatement())
			aJCTIf.setElseStatement(
					(IJCTStatement) node.getElseStatement().accept(this, p));

		if (JCTKind.PARENTHESIS == aJCTIf.getCondition().getKind()) {
			final IJCTExpression expr = ((IJCTParenthesis) aJCTIf
					.getCondition()).getExpression();
			((IJCTParenthesis) aJCTIf.getCondition())
					.setExpression(this.factory.createBooleanLiteral(true));
			aJCTIf.setCondition(expr);
		}

		return this.putSourceCodePosition(aJCTIf, node);
	}

	public IJCTElement visitImport(final ImportTree node, final Object param) {
		final boolean isStatic = node.isStatic();
		final String globalIdentifier = node.getQualifiedIdentifier()
				.toString();
		final boolean isOnDemand = '*' == globalIdentifier
				.charAt(globalIdentifier.length() - 1);
		final String anIdentifier = isOnDemand
				? globalIdentifier.substring(0, globalIdentifier.length() - 2)
				: globalIdentifier;

		if (!isStatic)
			if (isOnDemand) // anIdentifier is a Package FQN
			{
				final IJCTPackage p = (IJCTPackage) this.javacElements
						.getPackageElement(anIdentifier).accept(this, param);
				return this.putSourceCodePosition(
						this.factory.createImport(p, isStatic, isOnDemand),
						node);
			}
			else // anIdentifier is a Class FQN
			{
				final IJCTClass c = (IJCTClass) this.javacElements
						.getTypeElement(anIdentifier).accept(this, param);
				return this.putSourceCodePosition(
						this.factory.createImport(c, isStatic, isOnDemand),
						node);
			}
		else if (isOnDemand) // anIdentifier is a Class FQN
		{
			final IJCTClass c = (IJCTClass) this.javacElements
					.getTypeElement(anIdentifier).accept(this, param);
			return this.putSourceCodePosition(
					this.factory.createImport(c, isStatic, isOnDemand), node);
		}
		else // anIdentifier is a static member FQN
		{
			final String classFQN = anIdentifier.substring(0,
					anIdentifier.lastIndexOf('.'));
			final String memberName = anIdentifier
					.substring(anIdentifier.lastIndexOf('.') + 1);

			final Symbol.ClassSymbol classSym = this.javacElements
					.getTypeElement(classFQN);
			final Symbol sym = classSym.members()
					.findFirst(this.javacElements.getName(memberName));
			final IJCTClassMember cm = (IJCTClassMember) sym.accept(this,
					param);
			return this.putSourceCodePosition(
					this.factory.createImport(cm, isStatic, isOnDemand), node);
		}
	}

	public IJCTElement visitInstanceOf(final InstanceOfTree node,
			final Object p) {
		final IJCTExpression e = (IJCTExpression) node.getExpression()
				.accept(this, p);
		IJCTType t;

		Tree type = node.getType();

		if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
			type = ((ParameterizedTypeTree) type).getType();

		switch (type.getKind()) {
		case ARRAY_TYPE:
			t = (IJCTType) type.accept(this, p);
			break;
		case MEMBER_SELECT: {
			final Symbol sym = ((JCTree.JCFieldAccess) type).sym;
			t = ((IJCTClass) sym.accept(this, p)).createClassType();
		}
			break;
		case IDENTIFIER: {
			final Symbol sym = ((JCTree.JCIdent) type).sym;
			t = ((IJCTClass) sym.accept(this, p)).createClassType();
		}
			break;
		default:
			throw new IllegalStateException("instance of \"(" + node
					+ ")\" unknown type kind ==> " + type.getKind());
		}

		return this.putSourceCodePosition(this.factory.createInstanceOf(e, t),
				node);
	}

	public IJCTElement visitLabeledStatement(final LabeledStatementTree node,
			final Object p) {
		final IJCTLabel jctLabel = this.factory.createLabel(
				node.getLabel().toString(),
				this.factory.createEmptyStatement());
		final IJCTLabel l = this.labels.put(jctLabel.getName(), jctLabel);
		jctLabel.setStatement(
				(IJCTStatement) node.getStatement().accept(this, p));
		this.labels.put(jctLabel.getName(), l);
		return this.putSourceCodePosition(jctLabel, node);
	}

	public IJCTElement visitLiteral(final LiteralTree node, final Object p) {
		final IJCTLiteral result;
		switch (node.getKind()) {
		case BOOLEAN_LITERAL:
			result = this.factory
					.createBooleanLiteral((Boolean) node.getValue());
			break;
		case CHAR_LITERAL:
			result = this.factory
					.createCharacterLiteral((Character) node.getValue());
			break;
		case DOUBLE_LITERAL:
			result = this.factory.createDoubleLiteral((Double) node.getValue());
			break;
		case FLOAT_LITERAL:
			result = this.factory.createFloatLiteral((Float) node.getValue());
			break;
		case INT_LITERAL:
			result = this.factory
					.createIntegerLiteral((Integer) node.getValue());
			break;
		case LONG_LITERAL:
			result = this.factory.createLongLiteral((Long) node.getValue());
			break;
		case NULL_LITERAL:
			result = this.factory.createNullLiteral();
			break;
		case STRING_LITERAL:
			result = this.factory.createStringLiteral((String) node.getValue());
			break;
		default:
			throw new AssertionError(
					"the type of a litteral must represent a litteral type.");
		}
		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitBindingPattern(BindingPatternTree node, Object o) {
		return null;
	}

	public IJCTElement visitDefaultCaseLabel(DefaultCaseLabelTree node,
			Object o) {
		return null;
	}

	public IJCTElement visitMemberSelect(final MemberSelectTree node,
			final Object p) {
		final IJCTExpression aJCTExpression = (IJCTExpression) node
				.getExpression().accept(this, p);
		final Symbol sym = ((JCTree.JCFieldAccess) node).sym;

		final IJCTIdentifiable e = sym.accept(this, p);

		if (e instanceof IJCTPackage)
			return this.putSourceCodePosition(
					this.factory.createSimpleSelector(e), node);
		//if(!(e instanceof IJCTClassMember))
		//    throw new ClassCastException(e.getClass().getCanonicalName() + " cannot be cast to " + IJCTClassMember.class.getCanonicalName() + " \n[" + e + "]");
		final IJCTClassMember m = (IJCTClassMember) e;

		return this.putSourceCodePosition(
				m.isStatic() == null || m.isStatic() == true
						? this.factory.createSimpleSelector(m)
						: this.factory.createMemberSelector(aJCTExpression, m),
				node);
	}

	public IJCTElement visitMemberReference(MemberReferenceTree node,
			Object o) {
		return null;
	}

	public IJCTElement visitMethod(final MethodTree node, final Object p) {
		IJCTMethod aJCTMethod = (IJCTMethod) this.identifiables
				.get(((JCTree.JCMethodDecl) node).sym);

		if (aJCTMethod != null)
			return aJCTMethod;

		aJCTMethod = this.factory.createMethod(node.getName().toString());

		this.identifiables.put(((JCTree.JCMethodDecl) node).sym, aJCTMethod);

		if (null != node.getReturnType()) {
			IJCTType t = null;

			Tree type = node.getReturnType();

			if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
				type = ((ParameterizedTypeTree) type).getType();

			switch (type.getKind()) {
			case PRIMITIVE_TYPE:
			case ARRAY_TYPE:
				t = (IJCTType) type.accept(this, p);
				break;
			case MEMBER_SELECT:
				t = ((IJCTClass) ((JCTree.JCFieldAccess) type).sym.accept(this,
						p)).createClassType();
				break;
			case IDENTIFIER:
				t = ((IJCTClass) ((JCTree.JCIdent) type).sym.accept(this, p))
						.createClassType();
				break;
			default:
				throw new IllegalStateException("instance of \"(" + node
						+ ")\" unknown type kind ==> " + type.getKind());
			}

			aJCTMethod.setReturnType(t);
		}

		while (aJCTMethod.getModifiers().size() > 0) {
			aJCTMethod.removeModifier(
					aJCTMethod.getModifiers().iterator().next());
		}

		for (final Modifier m : node.getModifiers().getFlags())
			aJCTMethod.addModifier(
					JCTModifiers.valueOf(m.toString().toUpperCase()));

		for (final VariableTree v : node.getParameters())
			aJCTMethod.addParameter((IJCTParameter) v.accept(this, p));

		if (null != node.getBody())
			aJCTMethod.setBody((IJCTBlock) node.getBody().accept(this, p));

		for (final ExpressionTree e : node.getThrows()) {
			IJCTClassType t;

			switch (e.getKind()) {
			case MEMBER_SELECT:
				t = ((IJCTClass) ((JCTree.JCFieldAccess) e).sym.accept(this, p))
						.createClassType();
				break;
			case IDENTIFIER:
				t = ((IJCTClass) ((JCTree.JCIdent) e).sym.accept(this, p))
						.createClassType();
				break;
			default:
				throw new IllegalStateException("method \"" + node
						+ "\" unknown exception type kind ==> " + e.getKind());
			}

			aJCTMethod.addThrownException(t);
		}

		return this.putSourceCodePosition(aJCTMethod, node);
	}

	public IJCTElement visitMethodInvocation(final MethodInvocationTree node,
			final Object p) {
		final IJCTMethodInvocation aJCTMethodInvocation = this.factory
				.createMethodInvocation((IJCTSelector<IJCTMethod>) node
						.getMethodSelect().accept(this, p));

		for (final ExpressionTree a : node.getArguments())
			aJCTMethodInvocation
					.addArgument((IJCTExpression) a.accept(this, p));

		return this.putSourceCodePosition(aJCTMethodInvocation, node);
	}

	public IJCTElement visitModifiers(final ModifiersTree node,
			final Object p) {
		return this.error(node, p);
	}

	public IJCTElement visitNewArray(final NewArrayTree node, final Object p) {
		// Recover underlying type, store the number of unspecified dimensions
		Tree type = node.getType();
		int unspecifiedDims = 0;
		IJCTType t = null;

		if (null != type) {
			while (Tree.Kind.ARRAY_TYPE == type.getKind()) {
				type = ((ArrayTypeTree) type).getType();
				++unspecifiedDims;
			}

			switch (type.getKind()) {
			case PRIMITIVE_TYPE:
				t = (IJCTType) type.accept(this, p);
				break;
			case MEMBER_SELECT:
				t = ((IJCTClass) ((JCTree.JCFieldAccess) type).sym.accept(this,
						p)).createClassType();
				break;
			case IDENTIFIER:
				t = ((IJCTClass) ((JCTree.JCIdent) type).sym.accept(this, p))
						.createClassType();
				break;
			default:
				throw new IllegalStateException("new array \"(" + node
						+ ")\" unknown type kind ==> " + type.getKind());
			}
		}

		final IJCTNewArray aJCTNewArray = this.factory.createNewArray(t);

		// sets dimensions
		for (final ExpressionTree d : node.getDimensions())
			aJCTNewArray.addDimension((IJCTExpression) d.accept(this, p));

		// computes the real number of unspecified dimensions :
		//  number of unspecified dimensions + (if (nullp ( node.getInitializers() )) 0 1)
		// set initializers
		if (null != node.getInitializers()) {
			++unspecifiedDims;
			for (final ExpressionTree i : node.getInitializers())
				aJCTNewArray.addInitializer((IJCTExpression) i.accept(this, p));
		}

		aJCTNewArray.setUnspecifiedDimensions(unspecifiedDims);

		return this.putSourceCodePosition(aJCTNewArray, node);
	}

	public IJCTElement visitNewClass(final NewClassTree node, final Object p) {
		Tree type = node.getIdentifier();

		if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
			type = ((ParameterizedTypeTree) type).getType();

		final IJCTClassType aJCTClassType;

		switch (type.getKind()) {
		case MEMBER_SELECT:
			aJCTClassType = ((IJCTClass) ((JCTree.JCFieldAccess) type).sym
					.accept(this, p)).createClassType();
			break;
		case IDENTIFIER:
			if (null == ((JCTree.JCIdent) type).sym)
				System.err
						.println(((JCTree.JCIdent) type).getName().toString());
			aJCTClassType = ((IJCTClass) ((JCTree.JCIdent) type).sym
					.accept(this, p)).createClassType();
			break;
		default:
			throw new IllegalStateException("new class \"" + node
					+ "\" unknown class identifier type kind ==> "
					+ type.getKind());
		}

		final IJCTNewClass aJCTNewClass = this.factory
				.createNewClass(aJCTClassType);

		for (final ExpressionTree a : node.getArguments())
			aJCTNewClass.addArgument((IJCTExpression) a.accept(this, p));

		if (null != node.getEnclosingExpression())
			aJCTNewClass.setSelectingExpression((IJCTExpression) node
					.getEnclosingExpression().accept(this, p));

		if (null != node.getClassBody()) {
			final IJCTClass c = (IJCTClass) node.getClassBody().accept(this, p);

			if (null != aJCTClassType.getSelector().getElement()
					&& aJCTClassType.getSelector().getElement()
							.getIsInterface())
				c.addDirectlyImplementedInterface(aJCTClassType);
			else
				c.setDirectSuperClass(aJCTClassType);

			aJCTNewClass.setAnnonymousClass(c);
		}

		return this.putSourceCodePosition(aJCTNewClass, node);
	}

	public IJCTElement visitLambdaExpression(LambdaExpressionTree node,
			Object o) {
		return null;
	}

	public IJCTElement visitPackage(PackageTree node, Object o) {
		return null;
	}

	public IJCTElement visitParameterizedType(final ParameterizedTypeTree node,
			final Object p) {
		return node.getType().accept(this, p);
	}

	public IJCTElement visitUnionType(UnionTypeTree node, Object o) {
		return null;
	}

	public IJCTElement visitIntersectionType(IntersectionTypeTree node,
			Object o) {
		return null;
	}

	public IJCTElement visitParenthesized(final ParenthesizedTree node,
			final Object p) {
		return this.putSourceCodePosition(
				this.factory.createParenthesis(
						(IJCTExpression) node.getExpression().accept(this, p)),
				node);
	}

	public IJCTElement visitPrimitiveType(final PrimitiveTypeTree node,
			final Object p) {
		JCTPrimitiveTypes t = null;
		final String name = node.getPrimitiveTypeKind().name().toUpperCase();

		if ("INT".equals(name))
			t = JCTPrimitiveTypes.INTEGER;
		else if ("CHAR".equals(name))
			t = JCTPrimitiveTypes.CHARACTER;
		else
			t = JCTPrimitiveTypes.valueOf(name);

		return this.rootNode.getType(t);
	}

	public IJCTElement visitReturn(final ReturnTree node, final Object p) {
		final IJCTReturn aJCTReturn = this.factory.createReturn();
		if (null != node.getExpression())
			aJCTReturn.setReturnedExpression(
					(IJCTExpression) node.getExpression().accept(this, p));
		return this.putSourceCodePosition(aJCTReturn, node);
	}

	public IJCTElement visitSwitch(final SwitchTree node, final Object p) {
		final IJCTSwitch aJCTSwitch = this.factory.createSwitch(
				(IJCTExpression) node.getExpression().accept(this, p));
		for (final CaseTree c : node.getCases())
			if (null == c.getExpression())
				aJCTSwitch.setDefaultCase((IJCTCase) c.accept(this, p));
			else
				aJCTSwitch.addCase((IJCTCase) c.accept(this, p));
		return this.putSourceCodePosition(aJCTSwitch, node);
	}

	public IJCTElement visitSwitchExpression(SwitchExpressionTree node,
			Object o) {
		return null;
	}

	public IJCTElement visitSynchronized(final SynchronizedTree node,
			final Object p) {
		final IJCTSynchronized aJCTSynchronized = this.factory
				.createSynchronized(
						(IJCTExpression) node.getExpression().accept(this, p));
		aJCTSynchronized.setBody((IJCTBlock) node.getBlock().accept(this, p));
		return this.putSourceCodePosition(aJCTSynchronized, node);
	}

	public IJCTElement visitThrow(final ThrowTree node, final Object p) {
		return this.putSourceCodePosition(
				this.factory.createThrow(
						(IJCTExpression) node.getExpression().accept(this, p)),
				node);
	}

	public IJCTElement visitTry(final TryTree node, final Object p) {
		final IJCTTry aJCTTry = this.factory.createTry();

		aJCTTry.setTryBlock((IJCTBlock) node.getBlock().accept(this, p));

		if (null != node.getFinallyBlock())
			aJCTTry.setFinallyBlock(
					(IJCTBlock) node.getFinallyBlock().accept(this, p));

		for (final CatchTree c : node.getCatches())
			aJCTTry.addCatchBlock((IJCTCatch) c.accept(this, p));

		return this.putSourceCodePosition(aJCTTry, node);
	}

	public IJCTElement visitTypeCast(final TypeCastTree node, final Object p) {
		IJCTType t = null;

		final IJCTExpression e = (IJCTExpression) node.getExpression()
				.accept(this, p);

		Tree type = node.getType();

		if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
			type = ((ParameterizedTypeTree) type).getType();

		switch (type.getKind()) {
		case ARRAY_TYPE:
		case PRIMITIVE_TYPE:
			t = (IJCTType) type.accept(this, p);
			break;
		case MEMBER_SELECT:
			t = ((IJCTClass) ((JCTree.JCFieldAccess) type).sym.accept(this, p))
					.createClassType();
			break;
		case IDENTIFIER:
			t = ((IJCTClass) ((JCTree.JCIdent) type).sym.accept(this, p))
					.createClassType();
			break;
		default:
			throw new IllegalStateException("cast \"(" + node
					+ ")\" unknown type kind ==> " + type.getKind());
		}

		final IJCTCast aJCTCast = this.factory.createCast(t, e);

		return this.putSourceCodePosition(aJCTCast, node);
	}

	public IJCTElement visitTypeParameter(final TypeParameterTree node,
			final Object p) {
		return this.visitOther(node, p);
	}

	public IJCTElement visitUnary(final UnaryTree node, final Object p) {
		final IJCTExpression operand = (IJCTExpression) node.getExpression()
				.accept(this, p);
		IJCTUnaryOperator result;
		switch (node.getKind()) {
		case BITWISE_COMPLEMENT:
			result = this.factory.createBitwiseComplement(operand);
			break;
		case UNARY_MINUS:
			result = this.factory.createUnaryMinus(operand);
			break;
		case UNARY_PLUS:
			result = this.factory.createUnaryPlus(operand);
			break;
		case PREFIX_DECREMENT:
			result = this.factory.createPrefixDecrement(operand);
			break;
		case PREFIX_INCREMENT:
			result = this.factory.createPrefixIncrement(operand);
			break;
		case POSTFIX_DECREMENT:
			result = this.factory.createPostfixDecrement(operand);
			break;
		case POSTFIX_INCREMENT:
			result = this.factory.createPostfixIncrement(operand);
			break;
		case LOGICAL_COMPLEMENT:
			result = this.factory.createLogicalComplement(operand);
			break;
		default:
			throw new IllegalStateException(
					"An unary operator must have a unary operator kind");
		}
		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitVariable(final VariableTree node, final Object p) {
		final IJCTVariable v = (IJCTVariable) this.identifiables
				.get(((JCTree.JCVariableDecl) node).sym);

		if (v != null)
			return v;

		IJCTType t = null;

		Tree type = node.getType();

		if (Tree.Kind.PARAMETERIZED_TYPE == type.getKind())
			type = ((ParameterizedTypeTree) type).getType();

		switch (type.getKind()) {
		case ARRAY_TYPE:
		case PRIMITIVE_TYPE:
			t = (IJCTType) type.accept(this, p);
			break;
		case MEMBER_SELECT:
			t = ((IJCTClass) ((JCTree.JCFieldAccess) type).sym.accept(this, p))
					.createClassType();
			break;
		case IDENTIFIER:
			t = ((IJCTClass) ((JCTree.JCIdent) type).sym.accept(this, p))
					.createClassType();
			break;
		default:
			throw new IllegalStateException("variable \"(" + node
					+ ")\" unknown type kind ==> " + type.getKind());
		}

		final IJCTVariable aJCTVariable = this.factory
				.createVariable(node.getName().toString());
		aJCTVariable.setType(t);

		this.identifiables.put(((JCTree.JCVariableDecl) node).sym,
				aJCTVariable);

		if (null != node.getInitializer())
			aJCTVariable.setInitialValue(
					(IJCTExpression) node.getInitializer().accept(this, p));

		for (final Modifier m : node.getModifiers().getFlags())
			aJCTVariable.addModifier(
					JCTModifiers.valueOf(m.toString().toUpperCase()));

		return this.putSourceCodePosition(aJCTVariable, node);
	}

	public IJCTElement visitWhileLoop(final WhileLoopTree node,
			final Object p) {
		final IJCTWhile result = this.factory.createWhile(
				(IJCTExpression) node.getCondition().accept(this, p));
		result.setBody((IJCTStatement) node.getStatement().accept(this, p));

		if (JCTKind.PARENTHESIS == result.getCondition().getKind()) {
			final IJCTExpression expr = ((IJCTParenthesis) result
					.getCondition()).getExpression();
			((IJCTParenthesis) result.getCondition())
					.setExpression(this.factory.createBooleanLiteral(true));
			result.setCondition(expr);
		}

		return this.putSourceCodePosition(result, node);
	}

	public IJCTElement visitWildcard(final WildcardTree node, final Object p) {
		return this.visitOther(node, p);
	}

	public IJCTElement visitModule(ModuleTree node, Object o) {
		return null;
	}

	public IJCTElement visitExports(ExportsTree node, Object o) {
		return null;
	}

	public IJCTElement visitOpens(OpensTree node, Object o) {
		return null;
	}

	public IJCTElement visitProvides(ProvidesTree node, Object o) {
		return null;
	}

	public IJCTElement visitRequires(RequiresTree node, Object o) {
		return null;
	}

	public IJCTElement visitUses(UsesTree node, Object o) {
		return null;
	}

	@Override
	public IJCTElement visitStringTemplate(StringTemplateTree node, Object p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IJCTElement visitAnyPattern(AnyPatternTree node, Object p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IJCTElement visitConstantCaseLabel(ConstantCaseLabelTree node,
			Object p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IJCTElement visitPatternCaseLabel(PatternCaseLabelTree node,
			Object p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IJCTElement visitDeconstructionPattern(
			DeconstructionPatternTree node, Object p) {
		// TODO Auto-generated method stub
		return null;
	}

}

class OffsetTranslator extends JCTMap<Void, IJCTSourceCodePart> {
	private void visitSourceCodePart(final IJCTSourceCodePart scp,
			final IJCTSourceCodePart enclosing) {

		// TODO: Fixed (?) bug when scp.getStoredSourceCodeOffset() == null.
		if (scp.getStoredSourceCodeOffset() != null) {
			scp.setStoredSourceCodeOffset(scp.getStoredSourceCodeOffset()
					- enclosing.getStoredSourceCodeOffset(
							enclosing.getEnclosingCompilationUnit()));
		}
		else {
			scp.setStoredSourceCodeOffset(enclosing.getStoredSourceCodeOffset(
					enclosing.getEnclosingCompilationUnit()));
		}
	}

	@Override
	public Void visitAnd(IJCTAnd andElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(andElement, additionalParameter);
		return super.visitAnd(andElement, andElement);
	}

	@Override
	public Void visitAndAssignment(IJCTAndAssignment andAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(andAssignmentElement, additionalParameter);
		return super.visitAndAssignment(andAssignmentElement,
				andAssignmentElement);
	}

	@Override
	public Void visitArrayAccess(IJCTArrayAccess arrayAccessElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(arrayAccessElement, additionalParameter);
		return super.visitArrayAccess(arrayAccessElement, arrayAccessElement);
	}

	@Override
	public Void visitAssert(IJCTAssert assertElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(assertElement, additionalParameter);
		return super.visitAssert(assertElement, assertElement);
	}

	@Override
	public Void visitAssignment(IJCTAssignment assignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(assignmentElement, additionalParameter);
		return super.visitAssignment(assignmentElement, assignmentElement);
	}

	@Override
	public Void visitBitwiseComplement(
			IJCTBitwiseComplement bitwiseComplementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(bitwiseComplementElement, additionalParameter);
		return super.visitBitwiseComplement(bitwiseComplementElement,
				bitwiseComplementElement);
	}

	@Override
	public Void visitBlock(IJCTBlock blockElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(blockElement, additionalParameter);
		return super.visitBlock(blockElement, blockElement);
	}

	@Override
	public Void visitBooleanLiteral(IJCTBooleanLiteral booleanLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(booleanLiteralElement, additionalParameter);
		return super.visitBooleanLiteral(booleanLiteralElement,
				booleanLiteralElement);
	}

	@Override
	public Void visitBreak(IJCTBreak breakElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(breakElement, additionalParameter);
		return super.visitBreak(breakElement, breakElement);
	}

	@Override
	public Void visitCase(IJCTCase caseElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(caseElement, additionalParameter);
		return super.visitCase(caseElement, caseElement);
	}

	@Override
	public Void visitCast(IJCTCast castElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(castElement, additionalParameter);
		return super.visitCast(castElement, castElement);
	}

	@Override
	public Void visitCatch(IJCTCatch catchElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(catchElement, additionalParameter);
		return super.visitCatch(catchElement, catchElement);
	}

	@Override
	public Void visitCharacterLiteral(
			IJCTCharacterLiteral characterLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(characterLiteralElement, additionalParameter);
		return super.visitCharacterLiteral(characterLiteralElement,
				characterLiteralElement);
	}

	@Override
	public Void visitClass(IJCTClass classElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(classElement, additionalParameter);
		return super.visitClass(classElement, classElement);
	}

	@Override
	public Void visitComment(IJCTComment commentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(commentElement, additionalParameter);
		return super.visitComment(commentElement, commentElement);
	}

	@Override
	public Void visitCompilationUnit(IJCTCompilationUnit compilationUnitElement,
			IJCTSourceCodePart additionalParameter) {
		if (null == compilationUnitElement.getStoredSourceCode())
			return null;

		return super.visitCompilationUnit(compilationUnitElement,
				compilationUnitElement);
	}

	@Override
	public Void visitConditionalAnd(IJCTConditionalAnd conditionalAndElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(conditionalAndElement, additionalParameter);
		return super.visitConditionalAnd(conditionalAndElement,
				conditionalAndElement);
	}

	@Override
	public Void visitConditionalOperator(
			IJCTConditionalOperator conditionalOperatorElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(conditionalOperatorElement,
				additionalParameter);
		return super.visitConditionalOperator(conditionalOperatorElement,
				conditionalOperatorElement);
	}

	@Override
	public Void visitConditionalOr(IJCTConditionalOr conditionalOrElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(conditionalOrElement, additionalParameter);
		return super.visitConditionalOr(conditionalOrElement,
				conditionalOrElement);
	}

	@Override
	public Void visitContinue(IJCTContinue continueElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(continueElement, additionalParameter);
		return super.visitContinue(continueElement, continueElement);
	}

	@Override
	public Void visitDivide(IJCTDivide divideElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(divideElement, additionalParameter);
		return super.visitDivide(divideElement, divideElement);
	}

	@Override
	public Void visitDivideAssignment(
			IJCTDivideAssignment divideAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(divideAssignmentElement, additionalParameter);
		return super.visitDivideAssignment(divideAssignmentElement,
				divideAssignmentElement);
	}

	@Override
	public Void visitDoWhile(IJCTDoWhile doWhileElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(doWhileElement, additionalParameter);
		return super.visitDoWhile(doWhileElement, doWhileElement);
	}

	@Override
	public Void visitDoubleLiteral(IJCTDoubleLiteral doubleLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(doubleLiteralElement, additionalParameter);
		return super.visitDoubleLiteral(doubleLiteralElement,
				doubleLiteralElement);
	}

	@Override
	public Void visitEmptyStatement(IJCTEmptyStatement emptyStatementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(emptyStatementElement, additionalParameter);
		return super.visitEmptyStatement(emptyStatementElement,
				emptyStatementElement);
	}

	@Override
	public Void visitEnhancedFor(IJCTEnhancedFor enhancedForElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(enhancedForElement, additionalParameter);
		return super.visitEnhancedFor(enhancedForElement, enhancedForElement);
	}

	@Override
	public Void visitEqualTo(IJCTEqualTo equalToElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(equalToElement, additionalParameter);
		return super.visitEqualTo(equalToElement, equalToElement);
	}

	@Override
	public Void visitErroneousExpression(
			IJCTErroneousExpression erroneousExpressionElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(erroneousExpressionElement,
				additionalParameter);
		return super.visitErroneousExpression(erroneousExpressionElement,
				erroneousExpressionElement);
	}

	@Override
	public Void visitErroneousSelector(
			IJCTErroneousSelector<?> erroneousSelectorElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(erroneousSelectorElement, additionalParameter);
		return super.visitErroneousSelector(erroneousSelectorElement,
				erroneousSelectorElement);
	}

	@Override
	public Void visitExpressionStatement(
			IJCTExpressionStatement expressionStatementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(expressionStatementElement,
				additionalParameter);
		return super.visitExpressionStatement(expressionStatementElement,
				expressionStatementElement);
	}

	@Override
	public Void visitField(IJCTField fieldElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(fieldElement, additionalParameter);
		return super.visitField(fieldElement, fieldElement);
	}

	@Override
	public Void visitFloatLiteral(IJCTFloatLiteral floatLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(floatLiteralElement, additionalParameter);
		return super.visitFloatLiteral(floatLiteralElement,
				floatLiteralElement);
	}

	@Override
	public Void visitFor(IJCTFor forElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(forElement, additionalParameter);
		return super.visitFor(forElement, forElement);
	}

	@Override
	public Void visitGreaterThan(IJCTGreaterThan greaterThanElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(greaterThanElement, additionalParameter);
		return super.visitGreaterThan(greaterThanElement, greaterThanElement);
	}

	@Override
	public Void visitGreaterThanOrEqual(
			IJCTGreaterThanOrEqual greaterThanOrEqualElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(greaterThanOrEqualElement,
				additionalParameter);
		return super.visitGreaterThanOrEqual(greaterThanOrEqualElement,
				greaterThanOrEqualElement);
	}

	@Override
	public Void visitIf(IJCTIf ifElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(ifElement, additionalParameter);
		return super.visitIf(ifElement, ifElement);
	}

	@Override
	public Void visitImport(IJCTImport importElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(importElement, additionalParameter);
		return super.visitImport(importElement, importElement);
	}

	@Override
	public Void visitInstanceOf(IJCTInstanceOf instanceOfElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(instanceOfElement, additionalParameter);
		return super.visitInstanceOf(instanceOfElement, instanceOfElement);
	}

	@Override
	public Void visitIntegerLiteral(IJCTIntegerLiteral integerLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(integerLiteralElement, additionalParameter);
		return super.visitIntegerLiteral(integerLiteralElement,
				integerLiteralElement);
	}

	@Override
	public Void visitLabel(IJCTLabel labelElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(labelElement, additionalParameter);
		return super.visitLabel(labelElement, labelElement);
	}

	@Override
	public Void visitLeftShift(IJCTLeftShift leftShiftElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(leftShiftElement, additionalParameter);
		return super.visitLeftShift(leftShiftElement, leftShiftElement);
	}

	@Override
	public Void visitLeftShiftAssignment(
			IJCTLeftShiftAssignment leftShiftAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(leftShiftAssignmentElement,
				additionalParameter);
		return super.visitLeftShiftAssignment(leftShiftAssignmentElement,
				leftShiftAssignmentElement);
	}

	@Override
	public Void visitLessThan(IJCTLessThan lessThanElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(lessThanElement, additionalParameter);
		return super.visitLessThan(lessThanElement, lessThanElement);
	}

	@Override
	public Void visitLessThanOrEqual(IJCTLessThanOrEqual lessThanOrEqualElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(lessThanOrEqualElement, additionalParameter);
		return super.visitLessThanOrEqual(lessThanOrEqualElement,
				lessThanOrEqualElement);
	}

	@Override
	public Void visitLogicalComplement(
			IJCTLogicalComplement logicalComplementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(logicalComplementElement, additionalParameter);
		return super.visitLogicalComplement(logicalComplementElement,
				logicalComplementElement);
	}

	@Override
	public Void visitLongLiteral(IJCTLongLiteral longLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(longLiteralElement, additionalParameter);
		return super.visitLongLiteral(longLiteralElement, longLiteralElement);
	}

	@Override
	public <Member extends IJCTClassMember> Void visitMemberSelector(
			IJCTMemberSelector<Member> memberSelectorElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(memberSelectorElement, additionalParameter);
		return super.visitMemberSelector(memberSelectorElement,
				memberSelectorElement);
	}

	@Override
	public Void visitMethod(IJCTMethod methodElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(methodElement, additionalParameter);
		return super.visitMethod(methodElement, methodElement);
	}

	@Override
	public Void visitMethodInvocation(
			IJCTMethodInvocation methodInvocationElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(methodInvocationElement, additionalParameter);
		return super.visitMethodInvocation(methodInvocationElement,
				methodInvocationElement);
	}

	@Override
	public Void visitMinus(IJCTMinus minusElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(minusElement, additionalParameter);
		return super.visitMinus(minusElement, minusElement);
	}

	@Override
	public Void visitMinusAssignment(IJCTMinusAssignment minusAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(minusAssignmentElement, additionalParameter);
		return super.visitMinusAssignment(minusAssignmentElement,
				minusAssignmentElement);
	}

	@Override
	public Void visitMultiply(IJCTMultiply multiplyElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(multiplyElement, additionalParameter);
		return super.visitMultiply(multiplyElement, multiplyElement);
	}

	@Override
	public Void visitMultiplyAssignment(
			IJCTMultiplyAssignment multiplyAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(multiplyAssignmentElement,
				additionalParameter);
		return super.visitMultiplyAssignment(multiplyAssignmentElement,
				multiplyAssignmentElement);
	}

	@Override
	public Void visitNewArray(IJCTNewArray newArrayElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(newArrayElement, additionalParameter);
		return super.visitNewArray(newArrayElement, newArrayElement);
	}

	@Override
	public Void visitNewClass(IJCTNewClass newClassElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(newClassElement, additionalParameter);
		return super.visitNewClass(newClassElement, newClassElement);
	}

	@Override
	public Void visitNotEqualTo(IJCTNotEqualTo notEqualToElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(notEqualToElement, additionalParameter);
		return super.visitNotEqualTo(notEqualToElement, notEqualToElement);
	}

	@Override
	public Void visitNullLiteral(IJCTNullLiteral nullLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(nullLiteralElement, additionalParameter);
		return super.visitNullLiteral(nullLiteralElement, nullLiteralElement);
	}

	@Override
	public Void visitOr(IJCTOr orElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(orElement, additionalParameter);
		return super.visitOr(orElement, orElement);
	}

	@Override
	public Void visitOrAssignment(IJCTOrAssignment orAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(orAssignmentElement, additionalParameter);
		return super.visitOrAssignment(orAssignmentElement,
				orAssignmentElement);
	}

	@Override
	public Void visitParameter(IJCTParameter parameterElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(parameterElement, additionalParameter);
		return super.visitParameter(parameterElement, parameterElement);
	}

	@Override
	public Void visitParenthesis(IJCTParenthesis parenthesisElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(parenthesisElement, additionalParameter);
		return super.visitParenthesis(parenthesisElement, parenthesisElement);
	}

	@Override
	public Void visitPlus(IJCTPlus plusElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(plusElement, additionalParameter);
		return super.visitPlus(plusElement, plusElement);
	}

	@Override
	public Void visitPlusAssignment(IJCTPlusAssignment plusAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(plusAssignmentElement, additionalParameter);
		return super.visitPlusAssignment(plusAssignmentElement,
				plusAssignmentElement);
	}

	@Override
	public Void visitPostfixDecrement(
			IJCTPostfixDecrement postfixDecrementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(postfixDecrementElement, additionalParameter);
		return super.visitPostfixDecrement(postfixDecrementElement,
				postfixDecrementElement);
	}

	@Override
	public Void visitPostfixIncrement(
			IJCTPostfixIncrement postfixIncrementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(postfixIncrementElement, additionalParameter);
		return super.visitPostfixIncrement(postfixIncrementElement,
				postfixIncrementElement);
	}

	@Override
	public Void visitPrefixDecrement(IJCTPrefixDecrement prefixDecrementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(prefixDecrementElement, additionalParameter);
		return super.visitPrefixDecrement(prefixDecrementElement,
				prefixDecrementElement);
	}

	@Override
	public Void visitPrefixIncrement(IJCTPrefixIncrement prefixIncrementElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(prefixIncrementElement, additionalParameter);
		return super.visitPrefixIncrement(prefixIncrementElement,
				prefixIncrementElement);
	}

	@Override
	public Void visitRemainder(IJCTRemainder remainderElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(remainderElement, additionalParameter);
		return super.visitRemainder(remainderElement, remainderElement);
	}

	@Override
	public Void visitRemainderAssignment(
			IJCTRemainderAssignment remainderAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(remainderAssignmentElement,
				additionalParameter);
		return super.visitRemainderAssignment(remainderAssignmentElement,
				remainderAssignmentElement);
	}

	@Override
	public Void visitReturn(IJCTReturn returnElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(returnElement, additionalParameter);
		return super.visitReturn(returnElement, returnElement);
	}

	@Override
	public Void visitRightShift(IJCTRightShift rightShiftElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(rightShiftElement, additionalParameter);
		return super.visitRightShift(rightShiftElement, rightShiftElement);
	}

	@Override
	public Void visitRightShiftAssignment(
			IJCTRightShiftAssignment rightShiftAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(rightShiftAssignmentElement,
				additionalParameter);
		return super.visitRightShiftAssignment(rightShiftAssignmentElement,
				rightShiftAssignmentElement);
	}

	@Override
	public <Identifiable extends IJCTIdentifiable> Void visitSimpleSelector(
			IJCTSimpleSelector<Identifiable> simpleSelectorElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(simpleSelectorElement, additionalParameter);
		return super.visitSimpleSelector(simpleSelectorElement,
				simpleSelectorElement);
	}

	@Override
	public Void visitStringLiteral(IJCTStringLiteral stringLiteralElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(stringLiteralElement, additionalParameter);
		return super.visitStringLiteral(stringLiteralElement,
				stringLiteralElement);
	}

	@Override
	public Void visitSwitch(IJCTSwitch switchElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(switchElement, additionalParameter);
		return super.visitSwitch(switchElement, switchElement);
	}

	@Override
	public Void visitSynchronized(IJCTSynchronized synchronizedElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(synchronizedElement, additionalParameter);
		return super.visitSynchronized(synchronizedElement,
				synchronizedElement);
	}

	@Override
	public Void visitThrow(IJCTThrow throwElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(throwElement, additionalParameter);
		return super.visitThrow(throwElement, throwElement);
	}

	@Override
	public Void visitTry(IJCTTry tryElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(tryElement, additionalParameter);
		return super.visitTry(tryElement, tryElement);
	}

	@Override
	public Void visitUnaryMinus(IJCTUnaryMinus unaryMinusElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(unaryMinusElement, additionalParameter);
		return super.visitUnaryMinus(unaryMinusElement, unaryMinusElement);
	}

	@Override
	public Void visitUnaryPlus(IJCTUnaryPlus unaryPlusElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(unaryPlusElement, additionalParameter);
		return super.visitUnaryPlus(unaryPlusElement, unaryPlusElement);
	}

	@Override
	public Void visitUnsignedRightShift(
			IJCTUnsignedRightShift unsignedRightShiftElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(unsignedRightShiftElement,
				additionalParameter);
		return super.visitUnsignedRightShift(unsignedRightShiftElement,
				unsignedRightShiftElement);
	}

	@Override
	public Void visitUnsignedRightShiftAssignment(
			IJCTUnsignedRightShiftAssignment unsignedRightShiftAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(unsignedRightShiftAssignmentElement,
				additionalParameter);
		return super.visitUnsignedRightShiftAssignment(
				unsignedRightShiftAssignmentElement,
				unsignedRightShiftAssignmentElement);
	}

	@Override
	public Void visitVariable(IJCTVariable variableElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(variableElement, additionalParameter);
		return super.visitVariable(variableElement, variableElement);
	}

	@Override
	public Void visitWhile(IJCTWhile whileElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(whileElement, additionalParameter);
		return super.visitWhile(whileElement, whileElement);
	}

	@Override
	public Void visitXor(IJCTXor xorElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(xorElement, additionalParameter);
		return super.visitXor(xorElement, xorElement);
	}

	@Override
	public Void visitXorAssignment(IJCTXorAssignment xorAssignmentElement,
			IJCTSourceCodePart additionalParameter) {
		this.visitSourceCodePart(xorAssignmentElement, additionalParameter);
		return super.visitXorAssignment(xorAssignmentElement,
				xorAssignmentElement);
	}
}

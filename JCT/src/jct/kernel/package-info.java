/**
 * <p>This package contains the interfaces representing all the elements of a Javac Bound AST
 * (JCT).</p>
 *
 * <h2>Misc informations:</h2>
 *
 * <p>The method {@link jct.kernel.IJCTFactory createJCT(String name)} is used to create a new
 * JCT. It must be called on the factory class of the implementation you want to use.</p>
 *
 * <p>It is strongly recommended that the implementations of {@link jct.kernel.IJCTElementContainer}
 * implements also {@link jct.util.IJCTContainer}. When it is the case, the elements included in the
 * enclosed elements are specified in the documentation of the getters. The elements in the enclosed
 * elements must be exactly the ones specified this way.<br>
 * The mention "Included in the enclosed elements." means that the element returned by the getter
 * must be in enclosed elements.<br>
 * The mention "Part of the enclosed elements." means that the elements within the collection
 * returned by the getter must be in enclosed elements. When the collection is a list, iteration
 * order must be preserved.<br>
 * As a matter of fact, elements representing a type ({@link jct.kernel.IJCTType} and its
 * sub-interfaces) are never part of enclosed elements.</p>
 *
 * <p>The interface {@link jct.kernel.IJCTVisitor} provide a easy way to browse a JCT.<br>
 * Visiting enclosed elements is the responsibility of the implementor.</p>
 *
 * <p>The interfaces {@link jct.kernel.IJCTPath} and {@link jct.kernel.IJCTPathPart}
 * provides a way to select precisely an element in any JCT.<br>
 * The implementations of those interfaces are implementation-independent and must be accessible to
 * clients (i.e. public classes with public constructor).
 * Path Specification is presented in the second section of this documentation.</p>
 *
 * <p>Since an interface can represent many kind of element, {@code instanceof} is not a
 * reliable idiom to retrieve the real kind of an element. The method
 * {@link jct.kernel.IJCTElement#getKind()} must be used instead.</p>
 *
 * <p>The getters (methods whose name starts with "get") of the interfaces
 * in this package never return {@code null} except when explicitly specified.</p>
 *
 * <p>Giving {@code null} as argument for a setter (method whose name starts
 * with "set", "add" or "remove") result in an undefined behaviour,
 * unless explicitly specified.</p>
 *
 * <p>A collection returned by a collection getter never contains any null element unless
 * one of the associated setters of the same interface can recieve a null argument.
 * When such a setter is present, the collection may or may not contain null elements.</p>
 *
 * <p>Modifying a collection returned by a collection getter result in an undefined behaviour.</p>
 *
 * <strong>You also would like to take a look at the "See also" section at the bottom of the
 * page.</strong>
 *
 * <h3>Orphans:</h3>
 *
 * <p>It is possible that some naive implementations cause memory leak caused by orphans
 * elements.<br>
 * Orphan elements' semantic is to keep a way to generate path and other kind of indirect reference
 * to an element which is not yet in the actual tree. If Orphan were not kept track of some function
 * calls would throw a {@link java.lang.NullPointerException NPE} or have weird behaviour.<br>
 * To definitely remove an element from a JCT instance, it must be removed of its enclosed elements,
 * and then from the orphan list, once this is done, the element will garbage collectable.<br>
 * Some implementation may use weak or soft references to reference Orphans, but keep in my that
 * some weird behaviour may happen then.</p>
 *
 * <h3>Source Code Part:</h3>
 *
 * <p>a {@link jct.kernel.IJCTSourceCodePart source code part} is a
 * {@link jct.kernel.IJCTCompilationUnit compilation unit} or any element directly or indirectly
 * enclosed in a compilation unit.<br>
 * The stored source code fields represents the excerpt of the compilation unit composing the
 * element.<br>
 * They are informative fields, and are totally independent of any other fields.</p>
 *
 * <h3>Expressions:</h3>
 *
 * <p>An {@link jct.kernel.IJCTExpression expression} is a part of a Java Statement having a return value. Expressions may be a lot of
 * different elements, including  method invocation, unary, binary and tertiary (conditional)
 * operator, cast or instanceof operators, etc.<br>
 * An expression is not a statement by itself, and any "top-level expression" must be enclosed in an
 * {@link jct.kernel.IJCTExpressionStatement expression statement}.</p>
 *
 * <h3>Types:</h3>
 *
 * <p>There are four kinds of types:
 * <ul>
 * <li>Intersection types: those types could only be returned as
 * {@link jct.kernel.IJCTExpression#getTypeResult result type} of an expression. They represent an
 * intersetion of many types.</li>
 * <li>Primitive types: those types represent Java Primitive Types, i.e. {@code char, byte, short,
 * int, long, float, double, void}</li>
 * <li>Class Type: those type represent an object type (class, interface, enum, ...)</li>
 * <li>Array Type: those type represent an array type, the type of elements is referenced via
 * {@link jct.kernel.IJCTArrayType#getUnderlyingType underlying type}.</li>
 * </ul></p>
 *
 * <h3>Erroneous Selector:</h3>
 *
 * <p>{@link jct.kernel.IJCTSelector Sxelectors} are divided in two kind,
 * ({@link jct.kernel.IJCTSimpleSelector simple} and
 * {@link jct.kernel.IJCTMemberSelector composed}).<br>
 * If a selector is not resolved, it is considered as
 * {@link jct.kernel.IJCTErroneousSelector erroneous} and it is impossible to classify it as simple
 * or composed. Therefore, it is represented by a special erroneous selector untill it is
 * resolved.<br>
 * Once the erroneous selector is resolved, it behaves exactly as the selector it has resolved to.</p>
 *
 * <h2>Paths: knowing and walking them...</h2>
 *
 * <p>There are two kinds of path:
 * <ul>
 * <li>Generic purpose paths, which reprensent a sequence of browsing step within a JCT instance, and
 * whom specification is presented in this section.</li>
 * <li>Specific purpose paths (for example a Path designating a position in a source code
 * file). There is no actual specification for those paths but they would respect the specification
 * of general purpose paths as much as possible</li>
 * </ul>
 * Paths are to be considered as immutable, unless the implementation or the interface explicitly
 * state otherwise, modifying any field of a path result in undefined behaviour.</p>
 *
 * <h3>Knowing the (general purpose) paths:</h3>
 *
 * <p>A general purpose path is an instance of {@link jct.kernel.IJCTPath}.<br>
 * The instance itself represents the path to the {@link jct.kernel.IJCTRootNode root node}.<br>
 * The path parts provide informations on the sequence of step to follow.</p>
 *
 * <p>Here is the specification of path parts:</p>
 *
 * <p>The result kind field of a path part is the kind of the element designated by the path
 * part.</p>
 *
 * <p>The index field value is:
 * <ul>
 * <li>if the element is {@link jct.kernel.IJCTStatement statement}, an
 * {@link jct.kernel.IJCTExpression expression}, a {@link jct.kernel.IJCTCase case} or a
 * {@link jct.kernel.IJCTCatch catch}, the value is
 * the index of the element within the enclosed elements of its encloser.
 * <li>otherwise, the value is implementation-dependent.</li>
 * </ul>
 * </p>
 *
 * <p>The value of the data field is:
 * <ul>
 * <li>if the element is an {@link jct.kernel.IJCTImport import}, the value is composed of three
 * parts:
 * <ol>
 * <li>the first part is '1' if the import is static, '0' otherwise.</li>
 * <li>the second part is '1' if the import is on demand, '0' otherwise.</li>
 * <li>the third part depends of the kind of imported element:
 * <ul>
 * <li>if the imported element is a {@link jct.kernel.IJCTPackage package}, it is its name.</li>
 * <li>if the imported element is a {@link jct.kernel.IJCTClass class}, it is its FQN.</li>
 * <li>otherwise (i.e. if the imported element is a
 * {@link jct.kernel.IJCTClassMember class member}), it is the FQN of the enclosing
 * {@link jct.kernel.IJCTClass class}, followed by a '.', followed by the name of the imported
 * element.</li>
 * </ul></ol>
 * <li>if the element is a {@link jct.kernel.IJCTMethod method}, the value is
 * the name of the method, followed by a '(', followed by the type name of each
 * argument, separated by ',', followed by a ')'.</li>
 * <li>if the element is an {@link jct.kernel.IJCTIdentifiable identifiable},
 * the value is the name of the element.</li>
 * <li>if the element is a {@link jct.kernel.IJCTType type}, the value is its typename.</li>
 * <li>if the element is a {@link jct.kernel.IJCTCompilationUnit compilation unit},
 * the value is either the absolute path or the name of its source file.</li>
 * <li>otherwise, the value is implementation-depedent.</li>
 * </ul></p>
 *
 * <p>Informative data field is not used to retreive the element and is defined as follow:
 * <ul>
 * <li>if the element is a {@link jct.kernel.IJCTPackage package}, its length is at least 1 and
 * {@code informativeData[0] & 0x01} returns 1 if the package is a ghost, 0 otherwise. anything else
 * is implementation-dependent.</li>
 * <li>if the element is a {@link jct.kernel.IJCTClass class}, its length is at least 1 and:
 * <ul>
 * <li>{@code informativeData[0] & 0x01} returns 0x01 if the class is a ghost, 0 otherwise.</li>
 * <li>{@code informativeData[0] & 0x02} returns 0x02 if the class is an interface, 0 otherwise.</li>
 * </ul>
 * Anything else is implementation-dependent.</li>
 * <li>otherwise, its length and value are completly implementation-dependent (it may even be
 * {@code null}).</li>
 * </ul></p>
 *
 * <p>Implementors are encouraged to provide an implementation of toString() which return a human
 * readable description of the paths.</p>
 *
 * <h3>Walking the paths:</h3>
 *
 * <p>Walking a specific purpose path is implementation-dependent.</p>
 *
 * <p>Walking a general purpose path is done via enclosed elements, each part of the path extract an
 * element of the enclosed elements of the result of the previous part.</p>
 *
 * <p>If the element is a {@link jct.kernel.IJCTClass class}, the returned element should be the
 * first class meet during a depth-first exploration of a tree in which classes are considering as
 * leaf, it is generated by successive retreiving of enclosed elements. For example, there is no
 * need to specify {@link jct.kernel.IJCTCompilationUnit compilation unit} within a path. Package
 * may be required because serveral classes may have the same simple name, but different FQN, in
 * this case, the path would be ambigous, which could result in a implementation dependent
 * result.</p>
 *
 * <p>If the element is a {@link jct.kernel.IJCTType type}, the encloser is considered to be the
 * root node.</p>
 *
 * <p>The implementation are allowed to use any information available in the path, implementation
 * independent or not, in order to optimize the walk operation. But the result must be
 * implementation indepent. That means that given a path and a JCT instance, the same element will
 * always be returned whatever may be the implementation used for the path or the instance.</p>
 *
 * <h2>License:</h2>
 *
 * <div>
 * Licensed under 3-clause BSD License:<br>
 * Copyright © 2009, Mathieu Lemoine<br>
 * All rights reserved.<br>
 *<br>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:<br>
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.<br>
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.<br>
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.<br>
 *<br>
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
 * </div>
 *
 * @see <a href="doc-files/composite-upper-part.png"><code>Description of the upper part composite (from root node
 * to class members)</code></a>
 * @see <a href="doc-files/identifiable-hierarchy.png"><code>Description of hierarchy of identifiable/importable
 * classes</code></a>
 * @see jct.kernel.IJCTElement Element : the root node of the whole interfaces hierarchy
 * @see jct.kernel.IJCTStatement Statement : the root node of the statements hierarchy
 * @see jct.kernel.IJCTExpression Expression : the root node of the expressions hierarchy
 */
package jct.kernel;


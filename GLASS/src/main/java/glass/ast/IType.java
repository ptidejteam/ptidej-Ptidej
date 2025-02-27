package glass.ast;

public interface IType {

	public boolean isAnonymous();
	public boolean isInterface();
	public IMethod[] getMethods();
	
	/*
	 * Returns the fields declared by this type in the order in which they appear
	 * in the source or class file. For binary types, this includes synthetic fields.
	 * This does not include the implicit fields representing record components.
	 */
	public IField[] getFields();
	
	/**
	 * (adapted from the jdt source code)
	 * 
	 * Returns the simple name of this type, unqualified by package or enclosing type.
	 * Note that the element name of an anonymous source type and lambda expressions
	 * is always empty.
	 * @return the simple name of this type
	 */
	public String getElementName();
	
	/**
	 * Returns the fully qualified name of this type,
	 * including qualification for any containing types and packages.
	 * This is the name of the package, followed by <code>'.'</code>,
	 * followed by the type-qualified name.
	 * <p>
	 * <b>Note</b>: The enclosing type separator used in the type-qualified
	 * name is <code>'$'</code>, not <code>'.'</code>.
	 * </p>
	 * for example, the fully qualified name of a class B defined as a member of
	 * a class A in a compilation unit A.java in a package x.y is "x.y.A$B".
	 * @return the fully qualified name of this type
	 */
	public String getFullyQualifiedName();
	
	/**
	 * Returns this type's fully qualified name using a '.' enclosing type separator
	 * followed by its type parameters between angle brackets if it is a generic type.
	 * For example, "p.X&lt;T&gt;", "java.util.Map&lt;java.lang.String, p.X&gt;"
	 * @return the fully qualified parameterized representation of this type
	 */
	public String getFullyQualifiedParameterizedName();
	
	/**
	 * Resolves the given type name within the context of this type (depending on the type hierarchy
	 * and its imports).
	 * <p>
	 * Multiple answers might be found in case there are ambiguous matches.
	 * </p>
	 * <p>
	 * Each matching type name is decomposed as an array of two strings, the first denoting the package
	 * name (dot-separated) and the second being the type name. The package name is empty if it is the
	 * default package. The type name is the type qualified name using a '.' enclosing type separator.
	 * </p>
	 * <p>
	 * Returns <code>null</code> if unable to find any matching type.
	 * </p>
	 *<p>
	 * For example, resolution of <code>"Object"</code> would typically return
	 * <code>{{"java.lang", "Object"}}</code>. Another resolution that returns
	 * <code>{{"", "X.Inner"}}</code> represents the inner type Inner defined in type X in the
	 * default package.
	 * </p>
	 *
	 * @param typeName the given type name
	 * @return the resolved type names or <code>null</code> if unable to find any matching type
	 */
	public String[][] resolveType(String typeName);
	public IType[] getAllSubtypes();
	public IType[] getAllSupertypes();
	public IType[] getImplementingClasses();
}

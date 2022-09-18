public static IJCTRootNode createJCT(final String name, final boolean initialized)
{
    final IJCTRootNode root = new JCTRootNode(name);

    if(!initialized)
        return root;

    /// Initializing
    final IJCTFactory f = root.getFactory();

    final IJCTPackage javaLang = f.createPackage(Constants.PACKAGE_JAVA_LANG, true);
    root.addPackage(javaLang);

    // Create java.long.Object class
    final IJCTCompilationUnit objectCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                          + Constants.CLASSNAME_OBJECT
                                                                          + Constants.CLASSFILE_EXTENSION));
    javaLang.addCompilationUnit(objectCU);

    final IJCTClass javaLangObject = f.createClass(Constants.CLASSNAME_OBJECT, false, true);
    objectCU.addClazz(javaLangObject);

    // Backpatch java.lang.Object extends clause and super field
    javaLangObject.setDirectSuperClass(javaLangObject.createClassType());
    javaLangObject.getSuperField().setType(javaLangObject.createClassType());

    // Create java.lang.Class class
    final IJCTCompilationUnit classCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                         + Constants.CLASSNAME_CLASS
                                                                         + Constants.CLASSFILE_EXTENSION));
    javaLang.addCompilationUnit(classCU);

    final IJCTClass javaLangClass = f.createClass(Constants.CLASSNAME_CLASS, false, true);
    classCU.addClazz(javaLangClass);

    //Backpatch java.lang.Object and java.lang.Class class fields
    javaLangObject.getClassField().setType(javaLangClass.createClassType());
    javaLangClass.getClassField().setType(javaLangClass.createClassType());

    // Create java.lang.Void class
    final IJCTCompilationUnit voidCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                        + Constants.CLASSNAME_VOID
                                                                        + Constants.CLASSFILE_EXTENSION));
    javaLang.addCompilationUnit(voidCU);

    final IJCTClass javaLangVoid = f.createClass(Constants.CLASSNAME_VOID, false, true);
    voidCU.addClazz(javaLangVoid);

    root.assumeInitialized();

    return root;
}

public static IJCTRootNode createJCT(final String name)
{ return JCTFactory.createJCT(name, true); }

public static final IJCTPath PATH_TO_OBJECT =
    JCTFactory.parsePath(new StringBuffer()
        .append(JCTPathPart.PART_SEPARATOR)
        .append(JCTKind.ROOT_NODE.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .append(JCTPathPart.PART_SEPARATOR)
        .append(JCTKind.PACKAGE.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append(Constants.PACKAGE_JAVA_LANG)
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .append(JCTPathPart.PART_SEPARATOR)
        .append(JCTKind.CLASS.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append(Constants.CLASSNAME_OBJECT)
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("01")
        .toString());

	public static final IJCTPath PATH_TO_VOID_CLASSTYPE =
		JCTFactory.parsePath(new StringBuffer()
        .append(JCTPathPart.PART_SEPARATOR)
        .append(JCTKind.ROOT_NODE.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .append(JCTPathPart.PART_SEPARATOR)
        .append(JCTKind.CLASS_TYPE.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append("null")
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append(Constants.CLASS_BINARYNAME_VOID)
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append("null")
        .toString());

public static IJCTPath parsePath(final String stringPath)
{
    final String[] parts = stringPath.split(JCTPathPart.PART_SEPARATOR);

    final IJCTPath path = new JCTPath();

    if (!"".equals(parts[0]))
        throw new IllegalArgumentException("Nothing must be present before the first "
                                           + JCTPathPart.PART_SEPARATOR + " : " + stringPath);

    IJCTPathPart part = JCTFactory.parsePathPart(parts[1]);

    if (JCTKind.ROOT_NODE != part.getResultKind())
        throw new IllegalArgumentException("The first part of a path must designate a ROOT_NODE element");

    if (2 == parts.length)
        return path;

    part = JCTFactory.parsePathPart(parts[2]);
    path.addPart(part);

    for (int i = 3; i < parts.length; ++i)
    {
        final JCTPathPart toAdd = JCTFactory.parsePathPart(parts[i]);
        part.addPart(toAdd);
        part = toAdd;
    }

    return path;
}

public static JCTPathPart parsePathPart(final String stringPart)
{
    final String[] kind_rest = stringPart.split(JCTPathPart.KIND_INDEX_SEPARATOR);

    if (kind_rest.length != 2)
        throw new IllegalArgumentException("The path is malformed (no or more than one '"
                                           + JCTPathPart.KIND_INDEX_SEPARATOR + "') : "
                                           + stringPart);

    final JCTKind kind = JCTKind.valueOf(kind_rest[0]); // Throw an exception if fail

    final String[] args = kind_rest[1].split(JCTPathPart.INDEX_DATA_SEPARATOR);

    if (args.length != 3)
        throw new IllegalArgumentException("The path is malformed (not enough or too many '"
                                           + JCTPathPart.INDEX_DATA_SEPARATOR + "') : "
                                           + stringPart);

    final Integer index = "null".equals(args[0])
        ? null
        : Integer.parseInt(args[0]);

    if (args[2].length() % 2 != 0)
        throw new IllegalArgumentException("The path is malforme (informative data string must have an even length).");

    final byte[] informativeData = "null".equals(args[2])
        ? null
        : new byte[args[2].length() / 2];

    if (null != informativeData)
        for (int i = 0; i < informativeData.length; ++i)
            informativeData[i] =
                (byte) (Short.parseShort(args[2].substring(i * 2, (i + 1) * 2), 16) & 0xFF);

    return new JCTPathPart(kind, index, args[1], informativeData);
}

package util.lang.visitor.converter;
import com.ibm.toad.cfparse.*;
import com.ibm.toad.cfparse.attributes.*;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.*;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Deprecated;
import org.apache.bcel.generic.Type;
import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import util.io.WriterOutputStream;
import util.lang.Modifier;

import java.io.*;

public class CFParseBCELConverterVisitor implements Visitor {

    private com.ibm.toad.cfparse.ClassFile currentClass;
    private org.apache.bcel.classfile.JavaClass aJavaClass;
    public CFParseBCELConverterVisitor(
            com.ibm.toad.cfparse.ClassFile currentClass,
            org.apache.bcel.classfile.JavaClass aJavaClass) {
        this.currentClass = currentClass;
        this.aJavaClass = aJavaClass;
    }

    @Override
    public void visitJavaClass(JavaClass aJavaClass) {
        currentClass.setAccess(aJavaClass.getAccessFlags());
        // No need to test the magic number because
        // "The first four bytes of every class file are always 0xCAFEBABE."
        // currentClass.setMagic();
        currentClass.setMajor(aJavaClass.getMajor());
        currentClass.setMinor(aJavaClass.getMinor());
        currentClass.setName(aJavaClass.getClassName());
        currentClass.setSuperName(aJavaClass.getSuperclassName());


        final Attribute[] attributes = aJavaClass.getAttributes();
        for (int i = 0; i < attributes.length; i++) {
            final Attribute attribute = attributes[i];

            if(attribute instanceof InnerClasses) continue;
            attribute.accept(this);
        }

        aJavaClass.getConstantPool().accept(this);

        final String[] interfaces = aJavaClass.getInterfaceNames();
        for (int index = 0; index < interfaces.length; index++) {
            final String interfaze = interfaces[index];

            final InterfaceList interfaceList = currentClass.getInterfaces();
            interfaceList.add(interfaze);
        }


        for (int i = 0; i < attributes.length; i++) {
            final Attribute attribute = attributes[i];
            if(attribute instanceof InnerClasses) {
                attribute.accept(this);
            }
        }

        final Method[] methods = aJavaClass.getMethods();
        for (int index = 0; index < methods.length; index++) {
            final Method method = methods[index];
            method.accept(this);
        }


        final Field[] fields = aJavaClass.getFields();
        for (int index = 0; index < fields.length; index++)
        {
            final Field field = fields[index];
            field.accept(this);
        }

    }

    @Override
    public void visitAnnotation(Annotations constant) {

    }

    @Override
    public void visitAnnotationDefault(AnnotationDefault constant) {

    }

    @Override
    public void visitAnnotationEntry(AnnotationEntry constant) {

    }

    @Override
    public void visitBootstrapMethods(BootstrapMethods constant) {
        currentClass.getAttrs().add("BootstrapMethods");
    }

    @Override
    public void visitCode(Code constant) {

    }

    @Override
    public void visitCodeException(CodeException constant) {

    }

    @Override
    public void visitConstantClass(ConstantClass constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final String className = constant
                .getBytes(bcelCP).replace('.', '/');
        final boolean found = CFParseBCELConverterVisitor.searchFor(cfparseCP,
                className, com.ibm.toad.cfparse.ConstantPool.ClassEntry.class);
        if (!found) {
            cfparseCP.addClass(className);
        }
    }

    @Override
    public void visitConstantDouble(ConstantDouble constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        cfparseCP.addDouble(constant.getBytes());
    }

    @Override
    public void visitConstantFieldref(ConstantFieldref constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final int classIndex = constant.getClassIndex();
        final ConstantClass classConstant = bcelCP
                .getConstant(classIndex);
        final ConstantUtf8 classNameUtf8 = bcelCP
                .getConstant(classConstant.getNameIndex());

        final int nameAndTypeIndex = constant.getNameAndTypeIndex();
        final ConstantNameAndType nameAndTypeConstant = bcelCP
                .getConstant(nameAndTypeIndex);

        cfparseCP.addField(classNameUtf8.getBytes().replace('.', '/')
                + ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
                + nameAndTypeConstant.getSignature(bcelCP));
    }

    @Override
    public void visitConstantFloat(ConstantFloat constant) {
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        cfparseCP.addFloat(constant.getBytes());
    }

    @Override
    public void visitConstantInteger(ConstantInteger constant) {
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        cfparseCP.addInteger(constant.getBytes());
    }

    @Override
    public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final int classIndex = constant.getClassIndex();
        final ConstantClass classConstant = bcelCP.getConstant(classIndex);
        final ConstantUtf8 classNameUtf8 = bcelCP
                .getConstant(classConstant.getNameIndex());

        final int nameAndTypeIndex = constant.getNameAndTypeIndex();
        final ConstantNameAndType nameAndTypeConstant = bcelCP
                .getConstant(nameAndTypeIndex);

        cfparseCP.addMethod(classNameUtf8.getBytes().replace('.', '/')
                + ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
                + nameAndTypeConstant.getSignature(bcelCP));
    }

    @Override
    public void visitConstantInvokeDynamic(ConstantInvokeDynamic constant) {

    }

    @Override
    public void visitConstantLong(ConstantLong constant) {

    }

    @Override
    public void visitConstantMethodHandle(ConstantMethodHandle constant) {

    }

    @Override
    public void visitConstantMethodref(ConstantMethodref constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final int classIndex = constant
                .getClassIndex();
        final ConstantClass classConstant = bcelCP
                .getConstant(classIndex);
        final ConstantUtf8 classNameUtf8 = bcelCP
                .getConstant(classConstant.getNameIndex());

        final int nameAndTypeIndex = constant.getNameAndTypeIndex();
        final ConstantNameAndType nameAndTypeConstant = bcelCP
                .getConstant(nameAndTypeIndex);

        cfparseCP.addMethod(classNameUtf8.getBytes().replace('.', '/')
                + ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
                + nameAndTypeConstant.getSignature(bcelCP));
    }

    @Override
    public void visitConstantMethodType(ConstantMethodType constant) {

    }

    @Override
    public void visitConstantModule(ConstantModule constantModule) {

    }

    @Override
    public void visitConstantNameAndType(ConstantNameAndType constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final ConstantNameAndType constantNameAndType = constant;
        final boolean found = CFParseBCELConverterVisitor.searchFor(cfparseCP,
                constantNameAndType.getName(bcelCP) + ' '
                        + constantNameAndType.getSignature(bcelCP),
                com.ibm.toad.cfparse.ConstantPool.NameAndTypeEntry.class);
        if (!found) {
            cfparseCP.addNameAndType(
                    constantNameAndType.getName(bcelCP),
                    constantNameAndType.getSignature(bcelCP));
        }
    }

    @Override
    public void visitConstantPackage(ConstantPackage constantPackage) {

    }

    @Override
    public void visitConstantPool(ConstantPool constantPool) {
        final Constant[] constants = constantPool.getConstantPool();
        for (int index = 1; index < constants.length; index++) {
            final Constant constant = constants[index];
            if (constant == null) {
                // Yann 24/11/24: Null-able?
                // For some reasons, BCEL constant pool contains null values,
                // which I just ignore but should probably try to understand.
                ProxyConsole.getInstance().errorOutput().println(
                        "util.lang.CFParseBCELConvertor.convertConstantPool(JavaClass, ConstantPool): null constant!?");
                continue;
            }
            constant.accept(this);
        }
    }

    @Override
    public void visitConstantString(ConstantString constant) {
        org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass.getConstantPool();
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        cfparseCP.addString((String) constant
                .getConstantValue(bcelCP));
    }

    @Override
    public void visitConstantUtf8(ConstantUtf8 constant) {
        com.ibm.toad.cfparse.ConstantPool cfparseCP = currentClass.getCP();
        final String utf8String = constant.getBytes();
        if (cfparseCP.find(1, utf8String) == -1) {
            cfparseCP.addUtf8(utf8String);
        }
    }

    @Override
    public void visitConstantValue(ConstantValue constant) {

    }

    @Override
    public void visitDeprecated(Deprecated deprecated) {
        currentClass.getAttrs().add("Deprecated");
    }

    @Override
    public void visitEnclosingMethod(EnclosingMethod constant) {
        currentClass.getAttrs().add("EnclosingMethod");
    }

    @Override
    public void visitExceptionTable(ExceptionTable constant) {

    }

    @Override
    public void visitField(Field field) {
        final String fieldType = field.getType().toString();
        final String fieldName = field.getName();
        final ConstantValue fieldValue = field.getConstantValue();

        StringBuffer fieldDeclaration = new StringBuffer();

        fieldDeclaration.append(Modifier.toString(field.getModifiers()));
        fieldDeclaration.append(' ');
        fieldDeclaration.append(fieldType);

        if (field.isStatic()) {
            fieldDeclaration.append(' ');
            fieldDeclaration.append(fieldName);
        }
        else {
            // Yann 2006/07/31: Bug in CFParse!
            // I must add a '_' in front of the field name
            // because CFParse eats the first letter away...
            fieldDeclaration.append(" _");
            fieldDeclaration.append(fieldName);
        }

        fieldDeclaration.append('=');
        if (fieldType.equals("java.lang.String")) {
            if (fieldValue == null) {
                fieldDeclaration.append("\"\"");
            }
            else {
                fieldDeclaration.append('\"');
                fieldDeclaration.append(fieldValue.toString());
                fieldDeclaration.append('\"');
            }
        }
        else if (fieldType.equals("boolean")) {
            if (fieldValue == null) {
                fieldDeclaration.append("false");
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("byte")) {
            if (fieldValue == null) {
                fieldDeclaration.append('0');
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("char")) {
            if (fieldValue == null) {
                fieldDeclaration.append("\'0\'");
            }
            else {
                fieldDeclaration.append('\'');
                fieldDeclaration.append(fieldValue.toString());
                fieldDeclaration.append('\'');
            }
        }
        else if (fieldType.equals("double")) {
            if (fieldValue == null) {
                fieldDeclaration.append('0');
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("float")) {
            if (fieldValue == null) {
                fieldDeclaration.append("0.0f");
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("int")) {
            if (fieldValue == null) {
                fieldDeclaration.append('0');
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("long")) {
            if (fieldValue == null) {
                fieldDeclaration.append('0');
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else if (fieldType.equals("short")) {
            if (fieldValue == null) {
                fieldDeclaration.append('0');
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }
        else {
            if (fieldValue == null) {
                fieldDeclaration.append("null");
            }
            else {
                fieldDeclaration.append(fieldValue.toString());
            }
        }

        final FieldInfo fieldInfo;
        if (field.isStatic()) {
            fieldInfo = currentClass.getFields().addStatic(currentClass,
                    fieldDeclaration.toString());
        }
        else {
            fieldInfo = currentClass.getFields().add(fieldDeclaration.toString());
        }
        if (field.isSynthetic()) {
            fieldInfo.getAttrs().add("Synthetic");
        }

    }

    @Override
    public void visitInnerClass(InnerClass innerClass) {

    }

    @Override
    public void visitInnerClasses(InnerClasses innerClasses) {
        final AttrInfoList attrInfoList = currentClass.getAttrs();
        final InnerClassesAttrInfo innerClassesAttrInfo = (InnerClassesAttrInfo) attrInfoList
                .add("InnerClasses");
        final InnerClass[] innerClassesArray = innerClasses
                .getInnerClasses();
        try {
            final StringWriter stringWriter = new StringWriter();
            final DataOutputStream dataOutput = new DataOutputStream(
                    new WriterOutputStream(stringWriter));
            dataOutput.writeInt(innerClasses.getLength());
            dataOutput.writeShort(
                    innerClasses.getInnerClasses().length);
            for (int j = 0; j < innerClassesArray.length; j++) {
                final InnerClass innerClass = innerClassesArray[j];

						/*
						final String innerClassName = ((ConstantUtf8) aJavaClass
								.getConstantPool()
								.getConstant(((ConstantClass) aJavaClass
										.getConstantPool()
										.getConstant(innerClass
												.getInnerClassIndex()))
										.getNameIndex()))
								.getBytes();
						*/

                // I can't just do that because the index in the
                // BCEL classfile are not the same as the ones in
                // under-construction CFParse classfile, although
                // they should... but could?
                // TODO Find a way to have the same indexes or recompute the right indexes
                // dataOutput.writeShort(innerClass.getInnerClassIndex());
                dataOutput.writeShort(0);
                // dataOutput.writeShort(innerClass.getOuterClassIndex());
                dataOutput.writeShort(0);
                // dataOutput.writeShort(innerClass.getInnerNameIndex());
                dataOutput.writeShort(0);
                dataOutput.writeShort(innerClass.getInnerAccessFlags());
            }
            dataOutput.close();

            final String stringInStream = stringWriter.toString();
            final StringReader stringReader = new StringReader(
                    stringInStream);
            final DataInputStream dataInput = new DataInputStream(
                    new ReaderInputStream(stringReader));
            innerClassesAttrInfo.read(dataInput);
            dataInput.close();
        }
        catch (final IOException ioe) {
            ioe.printStackTrace(
                    ProxyConsole.getInstance().errorOutput());
        }
    }



    @Override
    public void visitLineNumber(LineNumber constant) {

    }

    @Override
    public void visitLineNumberTable(LineNumberTable constant) {

    }

    @Override
    public void visitLocalVariable(LocalVariable constant) {

    }

    @Override
    public void visitLocalVariableTable(LocalVariableTable constant) {

    }

    @Override
    public void visitLocalVariableTypeTable(LocalVariableTypeTable constant) {

    }

    @Override
    public void visitMethod(Method method) {
        final StringBuffer methodSignature = new StringBuffer();
        if (method.getAccessFlags() > 0) {
            methodSignature
                    .append(Modifier.toString(method.getAccessFlags()));
            methodSignature.append(' ');
        }
        methodSignature.append(method.getReturnType().toString());
        methodSignature.append(' ');
        methodSignature.append(method.getName());
        methodSignature.append('(');
        final Type[] args = method.getArgumentTypes();
        for (int i = 0; i < args.length; i++) {
            methodSignature.append(args[i].toString());
            methodSignature.append(" a");
            methodSignature.append(i);
            if (i < args.length - 1) {
                methodSignature.append(", ");
            }
        }
        methodSignature.append(')');

        final MethodInfoList methodInfoList = currentClass.getMethods();
        final MethodInfo methodInfo = methodInfoList
                .add(methodSignature.toString());
        methodInfo.setAccess(method.getAccessFlags());
        final String[] argsString = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            final Type arg = args[i];
            argsString[i] = arg.toString();
        }
        methodInfo.setParams(argsString);

        // Yann 2006/07/31: Bug in CFParse!
        // I must add a dummy ')' because there is a bug
        // in the MethodInfo.setReturnType() method that
        // eats away one ')'....
        final com.ibm.toad.cfparse.ConstantPool cp = currentClass.getCP();
        final int idxDescriptor = cp.find(1, methodInfo.getDesc());
        final StringBuffer buffer = new StringBuffer(methodInfo.getDesc());
        buffer.insert(buffer.lastIndexOf(")"), ")");
        ((com.ibm.toad.cfparse.ConstantPool.Utf8Entry) cp.get(idxDescriptor))
                .setValue(buffer.toString());

        methodInfo.setReturnType(method.getReturnType().toString());

        // TODO: Add other attributes to the CFParse classfile
        // method from the method from BCEL!!
        final AttrInfoList attrInfoList = methodInfo.getAttrs();
        if (method.getCode() != null) {
            final CodeAttrInfo codeAttributeInfo = (CodeAttrInfo) attrInfoList
                    .get("Code");
            codeAttributeInfo.setCode(method.getCode().getCode());
            codeAttributeInfo.setMaxLocals(method.getCode().getMaxLocals());
            codeAttributeInfo.setMaxStack(method.getCode().getMaxStack());

            if (method.getLineNumberTable() != null) {
                final LineNumberAttrInfo lineNumberAttrInfo = (LineNumberAttrInfo) codeAttributeInfo
                        .getAttrs().add("LineNumberTable");
                try {
                    final StringWriter stringWriter = new StringWriter();
                    final DataOutputStream dataOutput = new DataOutputStream(
                            new WriterOutputStream(stringWriter));
                    dataOutput.writeInt(
                            method.getLineNumberTable().getLength());
                    final int tableLength = method.getLineNumberTable()
                            .getTableLength();
                    dataOutput.writeShort(tableLength);
                    for (int i = 0; i < tableLength; i++) {
                        method.getLineNumberTable().getLineNumberTable()[i]
                                .dump(dataOutput);
                    }
                    dataOutput.close();

                    final String stringInStream = stringWriter.toString();
                    final StringReader stringReader = new StringReader(
                            stringInStream);
                    final DataInputStream dataInput = new DataInputStream(
                            new ReaderInputStream(stringReader));
                    lineNumberAttrInfo.read(dataInput);
                    dataInput.close();
                }
                catch (final IOException ioe) {
                    ioe.printStackTrace(
                            ProxyConsole.getInstance().errorOutput());
                }
            }

            if (method.getLocalVariableTable() != null) {
                final LocalVariableAttrInfo localVariableAttrInfo = (LocalVariableAttrInfo) codeAttributeInfo
                        .getAttrs().add("LocalVariableTable");
                try {
                    final StringWriter stringWriter = new StringWriter();
                    final DataOutputStream dataOutput = new DataOutputStream(
                            new WriterOutputStream(stringWriter));
                    dataOutput.writeInt(
                            method.getLocalVariableTable().getLength());
                    final int tableLength = method.getLocalVariableTable()
                            .getTableLength();
                    dataOutput.writeShort(tableLength);
                    for (int i = 0; i < tableLength; i++) {
                        method.getLocalVariableTable()
                                .getLocalVariableTable()[i]
                                .dump(dataOutput);
                    }
                    dataOutput.close();

                    final String stringInStream = stringWriter.toString();
                    final StringReader stringReader = new StringReader(
                            stringInStream);
                    final DataInputStream dataInput = new DataInputStream(
                            new ReaderInputStream(stringReader));
                    localVariableAttrInfo.read(dataInput);
                    dataInput.close();
                }
                catch (final IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    @Override
    public void visitMethodParameters(MethodParameters constant) {

    }

    @Override
    public void visitParameterAnnotation(ParameterAnnotations constant) {

    }

    @Override
    public void visitParameterAnnotationEntry(ParameterAnnotationEntry constant) {

    }

    @Override
    public void visitSignature(Signature signature) {
        currentClass.getAttrs().add("Signature");
    }

    @Override
    public void visitSourceFile(SourceFile constant) {
        final String fileName = aJavaClass.getSourceFileName();
        final SourceFileAttrInfo sourceFileAttrInfo = (SourceFileAttrInfo) currentClass
                .getAttrs().add("SourceFile");
        sourceFileAttrInfo.set(fileName);
    }

    @Override
    public void visitStackMap(StackMap constant) {

    }

    @Override
    public void visitStackMapEntry(StackMapEntry constant) {

    }

    @Override
    public void visitSynthetic(Synthetic constant) {

    }

    @Override
    public void visitUnknown(Unknown constant) {
        // throw new RuntimeException("Unknown constant in constant pool");
        ProxyConsole.getInstance().errorOutput().print(
                "util.lang.CFParseBCELConvertor.convertConstantPool(JavaClass, ConstantPool): constant ");
        ProxyConsole.getInstance().errorOutput()
                .print(constant.getTag());
        ProxyConsole.getInstance().errorOutput().print(" (");
        ProxyConsole.getInstance().errorOutput()
                .print(Const.getConstantName(constant.getTag()));
        ProxyConsole.getInstance().errorOutput()
                .println(") is not (yet?) handled");
    }

    @Override
    public void visitNestHost(NestHost obj) {
        currentClass.getAttrs().add("NestHost");
    }

    @Override
    public void visitNestMembers(NestMembers obj) {
        currentClass.getAttrs().add("NestMembers");
    }

    private static boolean searchFor(
            final com.ibm.toad.cfparse.ConstantPool aCFParseCP,
            final String anAttributeName, final Class<?> anAttributeType) {

        boolean found = false;
        //	int iterator = 0;
        //	while (!found && iterator < aCFParseCP.length()) {
        //		final int classIndex =
        //			aCFParseCP.find(iterator * 2 + 1, anAttributeName);
        //		if (classIndex != -1
        //			&& aCFParseCP.get(classIndex).getClass().equals(
        //				anAttributeType)) {
        //
        //			found = true;
        //		}
        //		iterator = iterator + 1;
        //	}
        for (int i = 0; i < aCFParseCP.length() && !found; i++) {
            final ConstantPoolEntry entry = aCFParseCP.get(i);
            if (entry != null && entry.getClass().equals(anAttributeType)
                    && entry.getAsString().endsWith(anAttributeName)) {

                found = true;
            }
        }
        return found;
    }
}

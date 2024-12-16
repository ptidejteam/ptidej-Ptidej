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
package util.lang;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;
import com.ibm.toad.cfparse.InterfaceList;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.InnerClassesAttrInfo;
import com.ibm.toad.cfparse.attributes.LineNumberAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableAttrInfo;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;

import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import util.io.WriterOutputStream;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/27
 */
public class CFParseBCELConvertor {
	private static void addConstantPool(final JavaClass aJavaClass,
			final com.ibm.toad.cfparse.ConstantPool cfparseCP) {

		final org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass
				.getConstantPool();

		final Constant[] constants = bcelCP.getConstantPool();
		for (int index = 1; index < constants.length; index++) {
			final Constant constant = constants[index];

			if (constant instanceof ConstantClass) {
				final String className = ((ConstantClass) constant)
						.getBytes(bcelCP).replace('.', '/');
				final boolean found = CFParseBCELConvertor.searchFor(cfparseCP,
						className, ConstantPool.ClassEntry.class);
				if (!found) {
					cfparseCP.addClass(className);
				}
			}
			else if (constant instanceof ConstantFieldref) {
				final int classIndex = ((ConstantFieldref) constant)
						.getClassIndex();
				final ConstantClass classConstant = (ConstantClass) bcelCP
						.getConstant(classIndex);
				final ConstantUtf8 classNameUtf8 = (ConstantUtf8) bcelCP
						.getConstant(classConstant.getNameIndex());

				final int nameAndTypeIndex = ((ConstantFieldref) constant)
						.getNameAndTypeIndex();
				final ConstantNameAndType nameAndTypeConstant = (ConstantNameAndType) bcelCP
						.getConstant(nameAndTypeIndex);

				cfparseCP.addField(classNameUtf8.getBytes().replace('.', '/')
						+ ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
						+ nameAndTypeConstant.getSignature(bcelCP));
			}
			else if (constant instanceof ConstantInterfaceMethodref) {
				final int classIndex = ((ConstantInterfaceMethodref) constant)
						.getClassIndex();
				final ConstantClass classConstant = (ConstantClass) bcelCP
						.getConstant(classIndex);
				final ConstantUtf8 classNameUtf8 = (ConstantUtf8) bcelCP
						.getConstant(classConstant.getNameIndex());

				final int nameAndTypeIndex = ((ConstantInterfaceMethodref) constant)
						.getNameAndTypeIndex();
				final ConstantNameAndType nameAndTypeConstant = (ConstantNameAndType) bcelCP
						.getConstant(nameAndTypeIndex);

				cfparseCP.addMethod(classNameUtf8.getBytes().replace('.', '/')
						+ ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
						+ nameAndTypeConstant.getSignature(bcelCP));
			}
			else if (constant instanceof ConstantMethodref) {
				final int classIndex = ((ConstantMethodref) constant)
						.getClassIndex();
				final ConstantClass classConstant = (ConstantClass) bcelCP
						.getConstant(classIndex);
				final ConstantUtf8 classNameUtf8 = (ConstantUtf8) bcelCP
						.getConstant(classConstant.getNameIndex());

				final int nameAndTypeIndex = ((ConstantMethodref) constant)
						.getNameAndTypeIndex();
				final ConstantNameAndType nameAndTypeConstant = (ConstantNameAndType) bcelCP
						.getConstant(nameAndTypeIndex);

				cfparseCP.addMethod(classNameUtf8.getBytes().replace('.', '/')
						+ ' ' + nameAndTypeConstant.getName(bcelCP) + ' '
						+ nameAndTypeConstant.getSignature(bcelCP));
			}
			else if (constant instanceof ConstantDouble) {
				cfparseCP.addDouble(((ConstantDouble) constant).getBytes());
			}
			else if (constant instanceof ConstantFloat) {
				cfparseCP.addFloat(((ConstantFloat) constant).getBytes());
			}
			else if (constant instanceof ConstantInteger) {
				cfparseCP.addInteger(((ConstantInteger) constant).getBytes());
			}
			else if (constant instanceof ConstantLong) {
				cfparseCP.addLong(((ConstantLong) constant).getBytes());
			}
			else if (constant instanceof ConstantNameAndType) {
				final ConstantNameAndType constantNameAndType = (ConstantNameAndType) constant;
				final boolean found = CFParseBCELConvertor.searchFor(cfparseCP,
						constantNameAndType.getName(bcelCP) + ' '
								+ constantNameAndType.getSignature(bcelCP),
						ConstantPool.NameAndTypeEntry.class);
				if (!found) {
					cfparseCP.addNameAndType(
							constantNameAndType.getName(bcelCP),
							constantNameAndType.getSignature(bcelCP));
				}
			}
			else if (constant instanceof ConstantString) {
				cfparseCP.addString((String) ((ConstantString) constant)
						.getConstantValue(bcelCP));
			}
			else if (constant instanceof ConstantUtf8) {
				final String utf8String = ((ConstantUtf8) constant).getBytes();
				if (cfparseCP.find(1, utf8String) == -1) {
					cfparseCP.addUtf8(utf8String);
				}
			}
			else if (constant == null) {
				// Yann 24/11/24: Null-able?
				// For some reasons, BCEL constant pool contains null values,
				// which I just ignore but should probably try to understand.
				ProxyConsole.getInstance().errorOutput().println(
						"util.lang.CFParseBCELConvertor.convertConstantPool(JavaClass, ConstantPool): null constant!?");
			}
			else {
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
		}
	}

	private static void addFields(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final Field[] fields = aJavaClass.getFields();
		final StringBuffer fieldDeclaration = new StringBuffer();
		final FieldInfoList fieldInfoList = aClassFile.getFields();

		for (int index = 0; index < fields.length; index++) {
			final Field field = fields[index];
			final String fieldType = field.getType().toString();
			final String fieldName = field.getName();
			final ConstantValue fieldValue = field.getConstantValue();

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
				fieldInfo = fieldInfoList.addStatic(aClassFile,
						fieldDeclaration.toString());
			}
			else {
				fieldInfo = fieldInfoList.add(fieldDeclaration.toString());
			}
			if (field.isSynthetic()) {
				fieldInfo.getAttrs().add("Synthetic");
			}

			fieldDeclaration.setLength(0);
		}
	}

	private static void addInnerClasses(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final Attribute[] attributes = aJavaClass.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			final Attribute attribute = attributes[i];
			if (attribute instanceof InnerClasses) {
				final AttrInfoList attrInfoList = aClassFile.getAttrs();
				final InnerClassesAttrInfo innerClassesAttrInfo = (InnerClassesAttrInfo) attrInfoList
						.add("InnerClasses");
				final InnerClasses innerClassesAttr = (InnerClasses) attribute;
				final InnerClass[] innerClassesArray = innerClassesAttr
						.getInnerClasses();
				try {
					final StringWriter stringWriter = new StringWriter();
					final DataOutputStream dataOutput = new DataOutputStream(
							new WriterOutputStream(stringWriter));
					dataOutput.writeInt(innerClassesAttr.getLength());
					dataOutput.writeShort(
							innerClassesAttr.getInnerClasses().length);
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
		}
	}

	private static void addInterfaces(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		// I mustn't use aJavaClass.getInterfaces() because this method
		// looks for the interfaces in its repository and, if they are
		// not present, would throw a ClassNotFoundException.
		final String[] interfaces = aJavaClass.getInterfaceNames();
		for (int index = 0; index < interfaces.length; index++) {
			final String interfaze = interfaces[index];

			final InterfaceList interfaceList = aClassFile.getInterfaces();
			interfaceList.add(interfaze);
		}
	}

	private static void addMethods(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final Method[] methods = aJavaClass.getMethods();
		for (int index = 0; index < methods.length; index++) {
			final Method method = methods[index];

			// Yann 2006/07/31: BCEL can be pretty stupid too!
			// For some reason
			//		method.toString()
			// returns the Java-like signature of the method
			// with fully-qualified names for all types EXCEPT
			// types from the class libraries!?!? For example:
			//		doesContainConstituentWithID(String anID)
			// where you would expect
			//		doesContainConstituentWithID(java.lang.String anID).
			// BUT, if you ask the Type instance, it is fully
			// qualified... So, I reconstruct myself the method 
			// signature to avoid problems...
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

			final MethodInfoList methodInfoList = aClassFile.getMethods();
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
			final ConstantPool cp = aClassFile.getCP();
			final int idxDescriptor = cp.find(1, methodInfo.getDesc());
			final StringBuffer buffer = new StringBuffer(methodInfo.getDesc());
			buffer.insert(buffer.lastIndexOf(")"), ")");
			((ConstantPool.Utf8Entry) cp.get(idxDescriptor))
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
	}

	private static void addClassFileAttributes(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final Attribute[] attributes = aJavaClass.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			final Attribute attribute = attributes[i];
			if (attribute.getName().equals("BootstrapMethods")) {
				// TODO Complete the NestHost attribute with an index to the host class 
				aClassFile.getAttrs().add("BootstrapMethods");
			}
			else if (attribute.getName().equals("Deprecated")) {
				aClassFile.getAttrs().add("Deprecated");
			}
			else if (attribute.getName().equals("EnclosingMethod")) {
				// TODO Complete the EnclosingMethod attribute with indexes on the enclosing class and method of this classfile
				aClassFile.getAttrs().add("EnclosingMethod");
			}
			else if (attribute.getName().equals("NestHost")) {
				// TODO Complete the NestHost attribute with an index to the host class 
				aClassFile.getAttrs().add("NestHost");
			}
			else if (attribute.getName().equals("NestMembers")) {
				// TODO Complete the NestHost attribute with an index to the host class 
				aClassFile.getAttrs().add("NestMembers");
			}
			else if (attribute.getName().equals("Signature")) {
				// TODO Complete the Signature attribute with an index to the signature of this class 
				aClassFile.getAttrs().add("Signature");
			}
			else if (attribute.getName().equals("SourceFile")) {
				final String fileName = aJavaClass.getSourceFileName();
				final SourceFileAttrInfo sourceFileAttrInfo = (SourceFileAttrInfo) aClassFile
						.getAttrs().add("SourceFile");
				sourceFileAttrInfo.set(fileName);
			}
			else if (attribute.getName().equals("InnerClasses")) {
				// Will be treated later by addInnerClasses(...)
			}
			else {
				System.out.println("Nothing to do!?");
			}
		}
	}

	public static ClassFile convertClassFile(final JavaClass aJavaClass) {
		final ClassFile currentClass = new ClassFile();

		currentClass.setAccess(aJavaClass.getAccessFlags());
		// No need to test the magic number because
		// "The first four bytes of every class file are always 0xCAFEBABE."
		// currentClass.setMagic();
		currentClass.setMajor(aJavaClass.getMajor());
		currentClass.setMinor(aJavaClass.getMinor());
		currentClass.setName(aJavaClass.getClassName());
		currentClass.setSuperName(aJavaClass.getSuperclassName());

		CFParseBCELConvertor.addClassFileAttributes(currentClass, aJavaClass);

		CFParseBCELConvertor.addConstantPool(aJavaClass, currentClass.getCP());

		CFParseBCELConvertor.addInterfaces(currentClass, aJavaClass);
		CFParseBCELConvertor.addInnerClasses(currentClass, aJavaClass);
		CFParseBCELConvertor.addMethods(currentClass, aJavaClass);
		CFParseBCELConvertor.addFields(currentClass, aJavaClass);

		return currentClass;
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

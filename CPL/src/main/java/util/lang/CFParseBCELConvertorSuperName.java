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
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfoList;
import com.ibm.toad.cfparse.InterfaceList;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.LineNumberAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableAttrInfo;

import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import util.io.WriterOutputStream;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/27
 */
public class CFParseBCELConvertorSuperName {
	private static void addFields(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final Field[] fields = aJavaClass.getFields();
		for (int index = 0; index < fields.length; index++) {
			final Field field = fields[index];

			if (field.getName().indexOf('$') == -1) {
				final FieldInfoList fieldInfoList = aClassFile.getFields();
				if (field.isStatic()) {
					fieldInfoList.addStatic(aClassFile,
							Modifier.toString(field.getModifiers()) + ' '
									+ field.getType().toString() + " "
									+ field.getName() + '='
									+ field.getConstantValue());
				}
				else {
					// Yann 2006/07/31: Bug in CFParse!
					// I must add a '_' in front of the field
					// name because CFParse eats the first 
					// letter away...
					fieldInfoList.add(Modifier.toString(field.getModifiers())
							+ ' ' + field.getType().toString() + " _"
							+ field.getName());
				}
			}
		}
	}

	private static void addInterfaces(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		try {
			final JavaClass[] interfaces = aJavaClass.getInterfaces();
			for (int index = 0; index < interfaces.length; index++) {
				final JavaClass interfaze = interfaces[index];

				final InterfaceList interfaceList = aClassFile.getInterfaces();
				interfaceList.add(interfaze.getClassName());
			}
		}
		catch (final ClassNotFoundException cnfe) {
			cnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
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

	public static ClassFile convertClassFile(final JavaClass aJavaClass) {
		final ClassFile currentClass = new ClassFile();

		currentClass.setAccess(aJavaClass.getAccessFlags());
		// currentClass.setMagic();
		currentClass.setMajor(aJavaClass.getMajor());
		currentClass.setMinor(aJavaClass.getMinor());
		currentClass.setName(aJavaClass.getClassName());
		currentClass.setSuperName(aJavaClass.getSuperclassName());

		CFParseBCELConvertorSuperName.convertConstantPool(aJavaClass,
				currentClass.getCP());

		CFParseBCELConvertorSuperName.addInterfaces(currentClass, aJavaClass);
		CFParseBCELConvertorSuperName.addMethods(currentClass, aJavaClass);
		CFParseBCELConvertorSuperName.addFields(currentClass, aJavaClass);

		return currentClass;
	}

	private static com.ibm.toad.cfparse.ConstantPool convertConstantPool(
			final JavaClass aJavaClass,
			final com.ibm.toad.cfparse.ConstantPool cfparseCP) {

		final org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass
				.getConstantPool();

		final Constant[] constants = bcelCP.getConstantPool();
		for (int index = 1; index < constants.length; index++) {
			final Constant constant = constants[index];

			if (constant instanceof ConstantClass) {
				final String className = ((ConstantClass) constant)
						.getBytes(bcelCP).replace('.', '/');
				final boolean found = CFParseBCELConvertorSuperName.searchFor(cfparseCP,
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

				cfparseCP.addInterface(
						classNameUtf8.getBytes().replace('.', '/') + ' '
								+ nameAndTypeConstant.getName(bcelCP) + ' '
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
				final boolean found = CFParseBCELConvertorSuperName.searchFor(cfparseCP,
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
			else {
				// throw new RuntimeException("Unknown constant in constant pool");
				System.err.println("Unknown constant in constant pool");
			}
		}

		return cfparseCP;
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

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import org.apache.bcel.classfile.BootstrapMethods;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantInvokeDynamic;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantMethodHandle;
import org.apache.bcel.classfile.ConstantMethodType;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariableTypeTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.NestMembers;
import org.apache.bcel.classfile.RuntimeInvisibleAnnotations;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.generic.Type;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;
import com.ibm.toad.cfparse.InterfaceList;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.BCELUtils;
import com.ibm.toad.cfparse.attributes.BootstrapMethodsAttrInfo;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.ConstantValueAttrInfo;
import com.ibm.toad.cfparse.attributes.ExceptionAttrInfo;
import com.ibm.toad.cfparse.attributes.ExceptionsAttrInfo;
import com.ibm.toad.cfparse.attributes.InnerClassesAttrInfo;
import com.ibm.toad.cfparse.attributes.LineNumberAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableTypeAttrInfo;
import com.ibm.toad.cfparse.attributes.NestMembersAttrInfo;
import com.ibm.toad.cfparse.attributes.RuntimeInvisibleAnnotationsAttrInfo;
import com.ibm.toad.cfparse.attributes.SignatureAttrInfo;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;

import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import util.io.WriterOutputStream;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/27
 */
public class CFParseBCELConvertorAdhoc {
	private static void addAttributes(final ClassFile aClassFile,
			final JavaClass aJavaClass) {
		
		final Attribute[] attributes = aJavaClass.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			final Attribute attribute = attributes[i];
			
			if (attribute.getName().equals("BootstrapMethods")) {
			    aClassFile.getAttrs().add("BootstrapMethods");
			    handleBootstrapMethods((BootstrapMethods) attribute, aClassFile); 
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
				
				aClassFile.getAttrs().add("NestMembers");
				handleNestMembers((NestMembers) attribute, aClassFile);
			}

			else if (attribute.getName().equals("Signature")) {
			    handleSignature((Signature) attribute, aClassFile);
			}



			else if (attribute.getName().equals("SourceFile")) {
			
				final String fileName = aJavaClass.getSourceFileName();
				final SourceFileAttrInfo sourceFileAttrInfo = (SourceFileAttrInfo) aClassFile
						.getAttrs().add("SourceFile");
				sourceFileAttrInfo.set(fileName);
			}
			else if (attribute.getName().equals("InnerClasses")) {
			
				aClassFile.getAttrs().add("InnerClasses");
				CFParseBCELConvertorAdhoc.handleInnerClasses(
						(InnerClasses) attribute, aClassFile);
			}
			else {
				System.out.println("Nothing to do!?");
			}
		}
	}
	
	private static void handleSignature(final Signature sigAttr, final ClassFile aClassFile) {
	    final AttrInfoList attrList = aClassFile.getAttrs();
	    final SignatureAttrInfo sigInfo = (SignatureAttrInfo) attrList.add("Signature");

	    final String signature = sigAttr.getSignature(); 
	    final ConstantPool cp = aClassFile.getCP();

	    int sigIndex = cp.find(ConstantPool.CONSTANT_Utf8, signature);
	    if (sigIndex == -1) {
	        sigIndex = cp.addUtf8(signature);
	    }


	    try {
	        final StringWriter writer = new StringWriter();
	        final DataOutputStream out = new DataOutputStream(new WriterOutputStream(writer));
	        out.writeInt(2);
	        out.writeShort(sigIndex); // SignatureAttrInfo expects a u2 index
	        out.close();

	        final StringReader reader = new StringReader(writer.toString());
	        final DataInputStream in = new DataInputStream(new ReaderInputStream(reader));
	        sigInfo.read(in);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace(ProxyConsole.getInstance().errorOutput());
	    }
	}

	
//	Henrique 4/21/2025
	private static void handleBootstrapMethods(final BootstrapMethods attribute, final ClassFile aClassFile) {
		final AttrInfoList attrInfoList = aClassFile.getAttrs();
		AttrInfo attr = attrInfoList.get("BootstrapMethods");

		if (attr instanceof BootstrapMethodsAttrInfo bootstrapAttr) {
			try {
			
				final StringWriter writer = new StringWriter();
				final DataOutputStream out = new DataOutputStream(new WriterOutputStream(writer));

				final org.apache.bcel.classfile.BootstrapMethod[] methods = attribute.getBootstrapMethods();

				int byteLength = 2; 
				for (org.apache.bcel.classfile.BootstrapMethod m : methods) {
					byteLength += 4 + 2 * m.getNumBootstrapArguments();
				}

				out.writeInt(byteLength); // attribute_length
				out.writeShort(methods.length);
				for (org.apache.bcel.classfile.BootstrapMethod m : methods) {
					out.writeShort(m.getBootstrapMethodRef());
					out.writeShort(m.getNumBootstrapArguments());
					for (int arg : m.getBootstrapArguments()) {
						out.writeShort(arg);
					}
				}
				out.close();

				
				final StringReader reader = new StringReader(writer.toString());
				final DataInputStream in = new DataInputStream(new ReaderInputStream(reader));
				bootstrapAttr.read(in);
				in.close();

			} catch (IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}



	private static void handleNestMembers(final NestMembers aNestMembersAttr, final ClassFile aClassFile) {
//		HENRIQUE 4/11/2025
	
		final ConstantPool cfparseCP = aClassFile.getCP();
		final org.apache.bcel.classfile.ConstantPool bcelCP = aNestMembersAttr.getConstantPool();

		final AttrInfoList attrInfoList = aClassFile.getAttrs();
		final NestMembersAttrInfo nestMembersAttrInfo = (NestMembersAttrInfo) attrInfoList.get("NestMembers");

		try {
			final StringWriter stringWriter = new StringWriter();
			final DataOutputStream dataOutput = new DataOutputStream(new WriterOutputStream(stringWriter));

			final int[] members = aNestMembersAttr.getClasses();
			dataOutput.writeInt(2 + 2 * members.length); // attribute_length
			dataOutput.writeShort(members.length);

		
			for (int classIndex : members) {
				
				final String className = bcelCP
					.getConstantUtf8(((ConstantClass) bcelCP.getConstant(classIndex)).getNameIndex())
					.getBytes();

			
				int cfparseIndex = classIndex; 

				

				dataOutput.writeShort(cfparseIndex);
			}



			dataOutput.close();

			final StringReader stringReader = new StringReader(stringWriter.toString());
			final DataInputStream dataInput = new DataInputStream(new ReaderInputStream(stringReader));
			nestMembersAttrInfo.read(dataInput);
			dataInput.close();
		} catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}


	private static void addConstants(final ClassFile aClassFile,
			final JavaClass aJavaClass) {

		final ConstantPool cfparseCP = aClassFile.getCP();
		final org.apache.bcel.classfile.ConstantPool bcelCP = aJavaClass
				.getConstantPool();

		final Constant[] constants = bcelCP.getConstantPool();
	
		for (int index = 1; index < constants.length; index++) {
			final Constant constant = constants[index];
			
			// ClassEntry
			if (constant instanceof ConstantClass) {
				final String className = ((ConstantClass) constant)
						.getBytes(bcelCP).replace('.', '/');
				final boolean found = CFParseBCELConvertorAdhoc.searchFor(cfparseCP,
						className, ConstantPool.ClassEntry.class);
				if (!found) {
					
					cfparseCP.addClass(className);
					
				}
			}
			// DoubleEntry
			else if (constant instanceof ConstantDouble) {
				cfparseCP.addDouble(((ConstantDouble) constant).getBytes());
			}
			// FieldrefEntry
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
			// FloatEntry
			else if (constant instanceof ConstantFloat) {
				cfparseCP.addFloat(((ConstantFloat) constant).getBytes());
			}
			// IntegerEntry
			else if (constant instanceof ConstantInteger) {
				cfparseCP.addInteger(((ConstantInteger) constant).getBytes());
			}
			// InterfaceMethodrefEntry
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
			// InvokeDynamicEntry
			else if (constant instanceof ConstantInvokeDynamic) {
				// TODO Fix bootstrap method index
				final int nameAndTypeIndex = ((ConstantInvokeDynamic) constant)
						.getNameAndTypeIndex();
				final ConstantNameAndType nameAndTypeConstant = (ConstantNameAndType) bcelCP
						.getConstant(nameAndTypeIndex);
				final String name = nameAndTypeConstant.getName(bcelCP);
				final String signature = nameAndTypeConstant
						.getSignature(bcelCP);
				int indexNameAndType = cfparseCP.find(
						ConstantPool.CONSTANT_NameAndType, name, signature);
				if (indexNameAndType == -1) {
					indexNameAndType = cfparseCP.addNameAndType(name,
							signature);
				}
				cfparseCP.addInvokeDynamic(0, indexNameAndType);
			}
			// LongEntry
			else if (constant instanceof ConstantLong) {
				cfparseCP.addLong(((ConstantLong) constant).getBytes());
			}
			// MethodHandleEntry
			else if (constant instanceof ConstantMethodHandle) {
				final ConstantMethodHandle methodHandle = (ConstantMethodHandle) constant;
				final int referenceKind = methodHandle.getReferenceKind();
				final int referenceIndex = methodHandle.getReferenceIndex();
				if (referenceKind == Const.REF_getField
						|| referenceKind == Const.REF_getStatic
						|| referenceKind == Const.REF_putField
						|| referenceKind == Const.REF_putStatic) {

					// CONSTANT_Fieldref_info
					final ConstantFieldref cfr = bcelCP
							.getConstant(referenceIndex);
					final int classIndex = cfr.getClassIndex();
					final ConstantClass classConstant = (ConstantClass) bcelCP
							.getConstant(classIndex);
					final ConstantUtf8 classNameUtf8 = (ConstantUtf8) bcelCP
							.getConstant(classConstant.getNameIndex());

					final int nameAndTypeIndex = cfr.getNameAndTypeIndex();
					final ConstantNameAndType nameAndTypeConstant = (ConstantNameAndType) bcelCP
							.getConstant(nameAndTypeIndex);

					// "9padl/kernel/impl/Constituent cachedAcceptClassNames Ljava/util/Map;"
					// TODO Test
					final int newIndex = cfparseCP.find(9,
							classNameUtf8.getBytes() + " "
									+ nameAndTypeConstant.getName(bcelCP) + " "
									+ nameAndTypeConstant.getSignature(bcelCP));
					cfparseCP.addMethodHandle(referenceKind, newIndex);
				}
				else if (referenceKind == Const.REF_invokeVirtual
						|| referenceKind == Const.REF_newInvokeSpecial) {
					// CONSTANT_Methodref_info
					// TODO Implement
				}
				else if (referenceKind == Const.REF_invokeStatic
						|| referenceKind == Const.REF_invokeSpecial) {
					// CONSTANT_Methodref_info or CONSTANT_InterfaceMethodref_info
					// TODO Implement
				}
				else if (referenceKind == Const.REF_invokeInterface) {
					// CONSTANT_InterfaceMethodref_info
					// TODO Implement
				}
				else {
					// Impossible?
				}
			}
			// MethodrefEntry
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
			// MethodTypeEntry
			else if (constant instanceof ConstantMethodType) {
				// TODO Implement
			
			}
			// NameAndTypeEntry
			else if (constant instanceof ConstantNameAndType) {
				final ConstantNameAndType constantNameAndType = (ConstantNameAndType) constant;
				final boolean found = CFParseBCELConvertorAdhoc.searchFor(cfparseCP,
						constantNameAndType.getName(bcelCP) + ' '
								+ constantNameAndType.getSignature(bcelCP),
						ConstantPool.NameAndTypeEntry.class);
				if (!found) {
					cfparseCP.addNameAndType(
							constantNameAndType.getName(bcelCP),
							constantNameAndType.getSignature(bcelCP));
				}
			}
			// StringEntry
			else if (constant instanceof ConstantString) {
				// Yann 24/12/09: Escaping!
				// When addString() and addUtf8() are used, they escape the strings
				// given to them as parameters. I must thus escape the strings given
				// to find() to make sure that I look for the string added earlier.
				final String utf8String = ((ConstantString) constant)
						.getBytes(bcelCP);
				final String escapedString = CFParseBCELConvertorAdhoc
						.escape(utf8String);
				if (cfparseCP.find(ConstantPool.CONSTANT_String,
						escapedString) == -1) {
					cfparseCP.addString(utf8String);
				}
			}
			// Utf8Entry
			else if (constant instanceof ConstantUtf8) {
				final String utf8String = ((ConstantUtf8) constant).getBytes();
				final String escapedString = CFParseBCELConvertorAdhoc
						.escape(utf8String);
				if (cfparseCP.find(ConstantPool.CONSTANT_Utf8,
						escapedString) == -1) {
					cfparseCP.addUtf8(utf8String);
				}
			}
			else if (constant == null) {
				// Yann 24/11/24: Null-able?
				// BCEL constant pool contains null values,
				// which I just ignore because their pertain
				// to Double and Long, which require two ints.
				//	ProxyConsole.getInstance().errorOutput().println(
				//			"util.lang.CFParseBCELConvertor.convertConstants(JavaClass, ConstantPool): null constant!?");
			}
			else {
				// throw new RuntimeException("Unknown constant in constant pool");
				ProxyConsole.getInstance().errorOutput().print(
						"util.lang.CFParseBCELConvertor.convertConstants(JavaClass, ConstantPool): constant ");
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
				if (fieldType.equals("java.lang.String")) {
					if (fieldType.equals("java.lang.String")) {
					    if (fieldValue == null) {
					        fieldDeclaration.append("\"\"");
					    } else {
					      //Henrique 4/21/2025 fixed typo with a replace
					        int index1 = fieldValue.getConstantValueIndex();

					    
					        Constant c = aJavaClass.getConstantPool().getConstant(index1);
					        if (c instanceof ConstantString) {
					            ConstantString cs = (ConstantString) c;

					       
					            int stringIndex = cs.getStringIndex();
					            ConstantUtf8 utf8 = (ConstantUtf8) aJavaClass.getConstantPool().getConstant(stringIndex);

					         
					            String value = utf8.getBytes()
					                .replace("\\", "\\\\")
					                .replace("\"", "\\\"");

					            fieldDeclaration.append('"').append(value).append('"');
					        } else {
					           
					            fieldDeclaration.append("\"\"");
					        }
					    }
					}


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
			
			
			if (field.getConstantValue() != null) {
				AttrInfoList attrList = fieldInfo.getAttrs();
				ConstantValueAttrInfo constantAttr = (ConstantValueAttrInfo) attrList.get("ConstantValue");
				if (constantAttr == null) {
					constantAttr = (ConstantValueAttrInfo) attrList.add("ConstantValue");
				}

				
			
				int index2 = field.getConstantValue().getConstantValueIndex();
				Constant c = aJavaClass.getConstantPool().getConstant(index2);

				if (c instanceof ConstantInteger) {
					constantAttr.set(((ConstantInteger) c).getBytes());
				} else if (c instanceof ConstantLong) {
					constantAttr.set(((ConstantLong) c).getBytes());
				} else if (c instanceof ConstantFloat) {
					constantAttr.set(((ConstantFloat) c).getBytes());
				} else if (c instanceof ConstantDouble) {
					constantAttr.set(((ConstantDouble) c).getBytes());
				} else if (c instanceof ConstantString) {
					int stringIndex = ((ConstantString) c).getStringIndex();
					String value = ((ConstantUtf8) aJavaClass.getConstantPool().getConstant(stringIndex)).getBytes();
					constantAttr.set(value);
				} else {
					System.err.println("❌ Unsupported constant type: " + c.getClass());
				}
			}
			
			if (field.getGenericSignature() != null) {
			    AttrInfoList attrList = fieldInfo.getAttrs();
			    
		

			    SignatureAttrInfo signatureAttr = (SignatureAttrInfo) attrList.get("Signature");
			    if (signatureAttr == null) {
			        signatureAttr = (SignatureAttrInfo) attrList.add("Signature");
			    }

			    final String sig = field.getGenericSignature();

			    ConstantPool cp = aClassFile.getCP();
			    int sigIndex = cp.find(ConstantPool.CONSTANT_Utf8, sig);
			    if (sigIndex == -1) {
			        sigIndex = cp.addUtf8(sig);
			    }

			    try {
			        StringWriter writer = new StringWriter();
			        DataOutputStream out = new DataOutputStream(new WriterOutputStream(writer));
			        out.writeInt(2);        
			        out.writeShort(sigIndex); 
			        out.close();

			        StringReader reader = new StringReader(writer.toString());
			        DataInputStream in = new DataInputStream(new ReaderInputStream(reader));
			        signatureAttr.read(in);
			        in.close();
			    } catch (IOException e) {
			        e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			    }

			 
			}


			if (field.isSynthetic()) {
				// Not needed? -Someone
				//	Don't know if is needed but i made sure this works, so if is needed, it will be used - Henrique 4/22/2025		
					fieldInfo.getAttrs().add("Synthetic");
				fieldInfo
						.setAccess(fieldInfo.getAccess() + Const.ACC_SYNTHETIC);
			}


			fieldDeclaration.setLength(0);
			
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
			
			

			
			// Yann 24/12/10: Unnecessary and broken?
			// I shouldn't call this method because it breaks the constant
			// describing the signature of the method, for example "()V"
			// becomes "(V" after calling it!
			//		methodInfo.setReturnType(method.getReturnType().toString());

			// TODO: Add other attributes to the CFParse classfile
			// method from the method from BCEL and adjust indices!
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
					final LocalVariableAttrInfo localVariableAttrInfo = 
						(LocalVariableAttrInfo) codeAttributeInfo.getAttrs().add("LocalVariableTable");

					try {
						final StringWriter stringWriter = new StringWriter();
						final DataOutputStream dataOutput = new DataOutputStream(
							new WriterOutputStream(stringWriter));
					
						dataOutput.writeInt(method.getLocalVariableTable().getLength());
						final int tableLength = method.getLocalVariableTable().getTableLength();
						dataOutput.writeShort(tableLength);
						for (int i = 0; i < tableLength; i++) {
							method.getLocalVariableTable()
								.getLocalVariableTable()[i]
								.dump(dataOutput);
						}
						dataOutput.close();

						final StringReader stringReader = new StringReader(stringWriter.toString());
						final DataInputStream dataInput = new DataInputStream(
							new ReaderInputStream(stringReader));
						localVariableAttrInfo.read(dataInput);
						dataInput.close();

						
						

					} catch (IOException ioe) {
						ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
					
					
				}
				
				
				if (method.getExceptionTable() != null && method.getExceptionTable().getLength() > 0) {
				    try {
				        int[] bcelIndexes = method.getExceptionTable().getExceptionIndexTable();
				        String[] classNames = Arrays.stream(bcelIndexes)
				            .mapToObj(i -> method.getConstantPool().getConstantString(i, Const.CONSTANT_Class))
				            .toArray(String[]::new);

				        int[] cfparseIndexes = Arrays.stream(classNames)
				            .mapToInt(aClassFile.getCP()::addClass)
				            .toArray();

				       
				        ExceptionsAttrInfo exceptionsAttr = (ExceptionsAttrInfo) methodInfo.getAttrs().add("Exceptions");

				      
				        ByteArrayOutputStream baos = new ByteArrayOutputStream();
				        DataOutputStream out = new DataOutputStream(baos);

				        out.writeInt(2 + 2 * cfparseIndexes.length); // d_len
				        out.writeShort(cfparseIndexes.length); 
				        for (int index1 : cfparseIndexes) {
				            out.writeShort(index1);
				        }
				        out.close();

				       
				        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				        DataInputStream in = new DataInputStream(bais);
				        exceptionsAttr.read(in);
				        in.close();

				    } catch (IOException e) {
				        e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				    }
				}


				
				

				if (method.getGenericSignature() != null) {
					SignatureAttrInfo signatureAttr = (SignatureAttrInfo) methodInfo.getAttrs().add("Signature");
					try {
						StringWriter writer = new StringWriter();
						DataOutputStream out = new DataOutputStream(new WriterOutputStream(writer));
						int sigIndex = aClassFile.getCP().addUtf8(method.getGenericSignature());
						out.writeInt(2);
						out.writeShort(sigIndex);
						out.close();
						
						DataInputStream in = new DataInputStream(new ReaderInputStream(new StringReader(writer.toString())));
						signatureAttr.read(in);
						in.close();
					} catch (IOException e) {
						e.printStackTrace(ProxyConsole.getInstance().errorOutput());
					}
				}
				if (method.getAnnotationEntries() != null && method.getAnnotationEntries().length > 0) {
				    try {
				        AttrInfoList attrList = methodInfo.getAttrs();
				        RuntimeInvisibleAnnotationsAttrInfo annotationAttr =
				            (RuntimeInvisibleAnnotationsAttrInfo) attrList.add("RuntimeInvisibleAnnotations");

				        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				        DataOutputStream dataOut = new DataOutputStream(byteOut);

				        int numAnnotations = method.getAnnotationEntries().length;
				        dataOut.writeInt(2 + numAnnotations * 4); // total length: 2 + n * 4 (each annotation = 4 bytes)
				        dataOut.writeShort(numAnnotations);

				        for (org.apache.bcel.classfile.AnnotationEntry entry : method.getAnnotationEntries()) {
				            int typeIndex = aClassFile.getCP().addUtf8(entry.getAnnotationType());
				            dataOut.writeShort(typeIndex);        // annotation type index
				            dataOut.writeShort(0);                // num element_value_pairs (0)
				        }

				        dataOut.close();

				        DataInputStream dataIn = new DataInputStream(
				            new ByteArrayInputStream(byteOut.toByteArray()));
				        annotationAttr.read(dataIn);
				        dataIn.close();

				    } catch (IOException e) {
				        e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				    }
				}



				for (org.apache.bcel.classfile.Attribute attr : method.getCode().getAttributes()) {
					if (attr instanceof LocalVariableTypeTable localTypeTable) {
						
						LocalVariableTypeAttrInfo cfparseLocalType = 
							(LocalVariableTypeAttrInfo) codeAttributeInfo.getAttrs().add("LocalVariableTypeTable");

						try {
							StringWriter stringWriter = new StringWriter();
							DataOutputStream dataOutput = new DataOutputStream(new WriterOutputStream(stringWriter));

							dataOutput.writeInt(localTypeTable.getLength()); // attribute_length
							int tableLength = localTypeTable.getTableLength();
							dataOutput.writeShort(tableLength);
							for (int i = 0; i < tableLength; i++) {
								localTypeTable.getLocalVariableTypeTable()[i].dump(dataOutput);
							}
							dataOutput.close();

							StringReader stringReader = new StringReader(stringWriter.toString());
							DataInputStream dataInput = new DataInputStream(new ReaderInputStream(stringReader));
							cfparseLocalType.read(dataInput);
							dataInput.close();

						} catch (IOException ioe) {
							ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
						}
					}
					

				}
//				Reorder the ATTRIBUTES
//				Henrique 4/22/2025 for testing purposes, if ATTRIBUTES: is required to be followed by the below, then use this
//				SOmetimes is not required, why?
//				methodInfo.getAttrs().reorderByPriority(new String[] {
//						"Exceptions",
//					    "Signature",
//					    "RuntimeInvisibleAnnotations"
//					});
//				methodInfo.setAccess(method.getAccessFlags());

			}
		}
	}

	public static ClassFile convertClassFile(final JavaClass aJavaClass) {
		
		final ClassFile currentClass = new ClassFile();

		/*
		 * How the "parser" of CFParse proceeds:
		 *		this.d_magic = var3.readInt();
		 *		this.d_minorVersion = var3.readShort();
		 *		this.d_majorVersion = var3.readShort();
		 *		this.d_constants.read(var3);
		 *		this.d_accessFlags = var3.readShort();
		 *		this.d_thisClass = var3.readShort();
		 *		this.d_superClass = var3.readShort();
		 *		this.d_interfaces.read(var3);
		 *		this.d_fields.read(var3);
		 *		this.d_methods.read(var3);
		 *		this.d_attributes.read(var3);
		 */

		currentClass.setMagic(0xCAFEBABE);
		currentClass.setMinor(aJavaClass.getMinor());
		currentClass.setMajor(aJavaClass.getMajor());
		currentClass.setAccess(aJavaClass.getAccessFlags());
		currentClass.setName(aJavaClass.getClassName());
		currentClass.setSuperName(aJavaClass.getSuperclassName());

		CFParseBCELConvertorAdhoc.addConstants(currentClass, aJavaClass);
		CFParseBCELConvertorAdhoc.addInterfaces(currentClass, aJavaClass);
	CFParseBCELConvertorAdhoc.addMethods(currentClass, aJavaClass);
		CFParseBCELConvertorAdhoc.addFields(currentClass, aJavaClass);
		CFParseBCELConvertorAdhoc.addAttributes(currentClass, aJavaClass);

		return currentClass;
	}

	/**
	 * From https://stackoverflow.com/questions/2406121/how-do-i-escape-a-string-in-java
	 * 
	 * escape()
	 *
	 * Escape a give String to make it safe to be printed or stored.
	 *
	 * @param s The input String.
	 * @return The output String.
	 **/
	private static String escape(final String s) {
		return s.replace("\\", "\\\\").replace("\t", "\\t").replace("\b", "\\b")
				.replace("\n", "\\n").replace("\r", "\\r").replace("\f", "\\f")
				.replace("\'", "\\'") // <== not necessary
				.replace("\"", "\\\"");
	}

	private static void handleInnerClasses(final InnerClasses aInnerClassesAttr,
			final ClassFile aClassFile) {
		
		final ConstantPool cfparseCP = aClassFile.getCP();
		final org.apache.bcel.classfile.ConstantPool bcelCP = aInnerClassesAttr
				.getConstantPool();

		final AttrInfoList attrInfoList = aClassFile.getAttrs();
		final InnerClassesAttrInfo innerClassesAttrInfo = (InnerClassesAttrInfo) attrInfoList
				.get("InnerClasses");
		final InnerClass[] innerClassesArray = aInnerClassesAttr
				.getInnerClasses();
		try {
			final StringWriter stringWriter = new StringWriter();
			final DataOutputStream dataOutput = new DataOutputStream(
					new WriterOutputStream(stringWriter));
			dataOutput.writeInt(aInnerClassesAttr.getLength());
			dataOutput.writeShort(aInnerClassesAttr.getInnerClasses().length);
			for (int j = 0; j < innerClassesArray.length; j++) {
				final InnerClass innerClass = innerClassesArray[j];

				final String nameInnerClass = bcelCP
						.getConstantUtf8(((ConstantClass) bcelCP
								.getConstant(innerClass.getInnerClassIndex()))
								.getNameIndex())
						.getBytes();
				int indexInnerClass = cfparseCP
						.find(ConstantPool.CONSTANT_Class, nameInnerClass);
				if (indexInnerClass == -1) {
					indexInnerClass = cfparseCP.addClass(nameInnerClass);
				}
				dataOutput.writeShort(indexInnerClass);

				final int outerClassIndex = innerClass.getOuterClassIndex();
				final int indexOuterClass;
				if (outerClassIndex == 0) {
					indexOuterClass = 0;
				}
				else {
					final String nameOuterClass = bcelCP.getConstantUtf8(
							((ConstantClass) bcelCP.getConstant(
									innerClass.getOuterClassIndex()))
									.getNameIndex())
							.getBytes();
					indexOuterClass = cfparseCP
							.find(ConstantPool.CONSTANT_Class, nameOuterClass);
					if (indexOuterClass == -1) {
						cfparseCP.addClass(nameOuterClass);
					}
				}
				dataOutput.writeShort(indexOuterClass);

				final int innerNameIndex = innerClass.getInnerNameIndex();
				final int indexInnerName;
				if (innerNameIndex == 0) {
					indexInnerName = 0;
				}
				else {
					final String nameInnerName = bcelCP
							.getConstantUtf8(innerClass.getInnerNameIndex())
							.getBytes();
					indexInnerName = cfparseCP.find(ConstantPool.CONSTANT_Utf8,
							nameInnerName);
					if (indexOuterClass == -1) {
						cfparseCP.addUtf8(nameInnerName);
					}
				}
				dataOutput.writeShort(indexInnerName);

				dataOutput.writeShort(innerClass.getInnerAccessFlags());
			}
			dataOutput.close();

			final String stringInStream = stringWriter.toString();
			final StringReader stringReader = new StringReader(stringInStream);
			final DataInputStream dataInput = new DataInputStream(
					new ReaderInputStream(stringReader));
			innerClassesAttrInfo.read(dataInput);
			dataInput.close();
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
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

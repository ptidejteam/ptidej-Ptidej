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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.AnnotationDefault;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Annotations;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.BootstrapMethod;
import org.apache.bcel.classfile.BootstrapMethods;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
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
import org.apache.bcel.classfile.ConstantModule;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPackage;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.ConstantValue;
import org.apache.bcel.classfile.Deprecated;
import org.apache.bcel.classfile.EnclosingMethod;
import org.apache.bcel.classfile.ExceptionTable;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.InnerClass;
import org.apache.bcel.classfile.InnerClasses;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.LocalVariableTypeTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.MethodParameters;
import org.apache.bcel.classfile.NestMembers;
import org.apache.bcel.classfile.ParameterAnnotationEntry;
import org.apache.bcel.classfile.ParameterAnnotations;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.classfile.StackMap;
import org.apache.bcel.classfile.StackMapEntry;
import org.apache.bcel.classfile.Synthetic;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.classfile.Visitor;
import org.apache.bcel.generic.Type;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool.Utf8Entry;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.BootstrapMethodsAttrInfo;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.ConstantValueAttrInfo;
import com.ibm.toad.cfparse.attributes.ExceptionsAttrInfo;
import com.ibm.toad.cfparse.attributes.LineNumberAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableAttrInfo;
import com.ibm.toad.cfparse.attributes.LocalVariableTypeAttrInfo;
import com.ibm.toad.cfparse.attributes.NestMembersAttrInfo;
import com.ibm.toad.cfparse.attributes.RuntimeInvisibleAnnotationsAttrInfo;
import com.ibm.toad.cfparse.attributes.SignatureAttrInfo;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;
import com.ibm.toad.cfparse.attributes.StackMapTableAttrInfo;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/27
 * @author	Henrique De Freitas Serra
 * @since	2025/04/01
 */
public class CFParseBCELConvertorVisitor {
	private static class JavaClassVisitor implements Visitor {
		private final ClassFile currentClass;

		public JavaClassVisitor(final ClassFile aClassFile) {
			this.currentClass = aClassFile;
		}

		@Override
		public void visitAnnotation(final Annotations obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitAnnotationDefault(final AnnotationDefault obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitAnnotationEntry(final AnnotationEntry obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitBootstrapMethods(final BootstrapMethods obj) {
			final BootstrapMethodsAttrInfo attr = (BootstrapMethodsAttrInfo) this.currentClass
					.getAttrs().add("BootstrapMethods");

			final BootstrapMethod[] bcelMethods = obj.getBootstrapMethods();

			try {
				java.io.StringWriter stringWriter = new java.io.StringWriter();
				java.io.DataOutputStream dataOutput = new java.io.DataOutputStream(
						new util.io.WriterOutputStream(stringWriter));

				// Write total length
				int byteLength = 2; // u2 num_bootstrap_methods
				for (BootstrapMethod bm : bcelMethods) {
					byteLength += 4 + 2 * bm.getNumBootstrapArguments();
				}

				dataOutput.writeInt(byteLength);
				dataOutput.writeShort(bcelMethods.length);

				for (BootstrapMethod bm : bcelMethods) {
					dataOutput.writeShort(bm.getBootstrapMethodRef());
					dataOutput.writeShort(bm.getNumBootstrapArguments());
					for (int arg : bm.getBootstrapArguments()) {
						dataOutput.writeShort(arg);
					}
				}

				dataOutput.close();

				java.io.StringReader stringReader = new java.io.StringReader(
						stringWriter.toString());
				java.io.DataInputStream dataInput = new java.io.DataInputStream(
						new util.io.ReaderInputStream(stringReader));
				attr.read(dataInput);
				dataInput.close();

			}
			catch (IOException e) {
				e.printStackTrace(
						util.io.ProxyConsole.getInstance().errorOutput());
			}
		}

		@Override
		public void visitCode(final Code obj) {
			//			

		}

		@Override
		public void visitCodeException(final CodeException obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantClass(final ConstantClass obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantDouble(final ConstantDouble obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantFieldref(final ConstantFieldref obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantFloat(final ConstantFloat obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantInteger(final ConstantInteger obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantInterfaceMethodref(
				final ConstantInterfaceMethodref obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantInvokeDynamic(
				final ConstantInvokeDynamic obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantLong(final ConstantLong obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantMethodHandle(final ConstantMethodHandle obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantMethodref(final ConstantMethodref obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantMethodType(final ConstantMethodType obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantModule(final ConstantModule constantModule) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantNameAndType(final ConstantNameAndType obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantPackage(
				final ConstantPackage constantPackage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantPool(final ConstantPool obj) {
			// Nothing to do?
		}

		@Override
		public void visitConstantString(final ConstantString obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitConstantUtf8(final ConstantUtf8 obj) {
			try {
				final Constructor<Utf8Entry> constructor = Utf8Entry.class
						.getDeclaredConstructor(String.class);
				final Utf8Entry entry = constructor.newInstance(obj.getBytes());
				final java.lang.reflect.Method newElementMethod = com.ibm.toad.cfparse.ConstantPool.class
						.getMethod("addNewElement", ConstantPoolEntry.class);
				newElementMethod.invoke(this.currentClass.getCP(), entry);
			}
			catch (final NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {

				e.printStackTrace();
			}
		}

		@Override
		public void visitConstantValue(final ConstantValue obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitDeprecated(final Deprecated obj) {
			this.currentClass.getAttrs().add("Deprecated");
		}

		@Override
		public void visitEnclosingMethod(final EnclosingMethod obj) {
			this.currentClass.getAttrs().add("EnclosingMethod");
		}

		@Override
		public void visitExceptionTable(final ExceptionTable obj) {
			// Do nothing here
		}

		@Override
		public void visitField(final Field obj) {
			FieldInfo fieldInfo = new FieldInfo(this.currentClass.getCP());
			fieldInfo.setAccess(obj.getAccessFlags());

			int nameIdx = this.currentClass.getCP().addUtf8(obj.getName());
			int descIdx = this.currentClass.getCP().addUtf8(obj.getSignature());

			fieldInfo.d_idxName = nameIdx;
			fieldInfo.d_idxDescriptor = descIdx;

			for (Attribute attribute : obj.getAttributes()) {
				if (attribute instanceof ConstantValue constantValue) {
					int constValueIndex = constantValue.getConstantValueIndex();
					if (constValueIndex != 0) {
						Constant value = obj.getConstantPool()
								.getConstant(constValueIndex);

						ConstantValueAttrInfo cvAttr = (ConstantValueAttrInfo) fieldInfo
								.getAttrs().add("ConstantValue");

						// ✅ Now directly set the value
						switch (value.getTag()) {
						case Const.CONSTANT_Integer ->
							cvAttr.set(((ConstantInteger) value).getBytes());
						case Const.CONSTANT_Long ->
							cvAttr.set(((ConstantLong) value).getBytes());
						case Const.CONSTANT_Float ->
							cvAttr.set(((ConstantFloat) value).getBytes());
						case Const.CONSTANT_Double ->
							cvAttr.set(((ConstantDouble) value).getBytes());
						case Const.CONSTANT_String ->
							cvAttr.set(((ConstantString) value)
									.getBytes(obj.getConstantPool()));
						default -> throw new RuntimeException(
								"Unsupported ConstantValue type: "
										+ value.getTag());
						}
					}
				}
				if (attribute instanceof Signature sigAttrBCEL) {
					int sigUtf8Index = this.currentClass.getCP()
							.addUtf8(sigAttrBCEL.getSignature());

					SignatureAttrInfo sigAttr = (SignatureAttrInfo) fieldInfo
							.getAttrs().add("Signature");

					sigAttr.set(sigUtf8Index);
				}

			}

			this.currentClass.getFields().add(fieldInfo);

		}

		@Override
		public void visitInnerClass(final InnerClass obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitInnerClasses(final InnerClasses obj) {
			final com.ibm.toad.cfparse.attributes.InnerClassesAttrInfo attr = (com.ibm.toad.cfparse.attributes.InnerClassesAttrInfo) this.currentClass
					.getAttrs().add("InnerClasses");

			final org.apache.bcel.classfile.InnerClass[] bcelInnerClasses = obj
					.getInnerClasses();
			attr.d_numInnerClasses = bcelInnerClasses.length;
			attr.d_innerClasses = new int[bcelInnerClasses.length * 4];

			for (int i = 0; i < bcelInnerClasses.length; i++) {
				final org.apache.bcel.classfile.InnerClass ic = bcelInnerClasses[i];

				// Re-add constants into CFParse ConstantPool
				int innerClassIdx = 0;
				if (ic.getInnerClassIndex() != 0) {
					innerClassIdx = this.currentClass.getCP()
							.addClass(obj.getConstantPool().getConstantString(
									ic.getInnerClassIndex(),
									Const.CONSTANT_Class));
				}

				int outerClassIdx = 0;
				if (ic.getOuterClassIndex() != 0) {
					outerClassIdx = this.currentClass.getCP()
							.addClass(obj.getConstantPool().getConstantString(
									ic.getOuterClassIndex(),
									Const.CONSTANT_Class));
				}

				int innerNameIdx = 0;
				if (ic.getInnerNameIndex() != 0) {
					innerNameIdx = this.currentClass.getCP()
							.addUtf8(obj.getConstantPool().getConstantString(
									ic.getInnerNameIndex(),
									Const.CONSTANT_Utf8));
				}

				int accessFlags = ic.getInnerAccessFlags();

				attr.d_innerClasses[i * 4 + 0] = innerClassIdx;
				attr.d_innerClasses[i * 4 + 1] = outerClassIdx;
				attr.d_innerClasses[i * 4 + 2] = innerNameIdx;
				attr.d_innerClasses[i * 4 + 3] = accessFlags;
			}
		}

		@Override
		public void visitJavaClass(final JavaClass obj) {
			// Set class name
			this.currentClass.setName(obj.getClassName());
			this.currentClass.setAccess(obj.getAccessFlags());

			// Now manually visit attributes
			for (final Attribute attr : obj.getAttributes()) {
				attr.accept(this); // Accept this visitor
			}

			for (Field field : obj.getFields()) {
				field.accept(this); // <-- THIS calls visitField manually!
			}

			for (Method method : obj.getMethods()) {
				method.accept(this);
			}

		}

		@Override
		public void visitLineNumber(final LineNumber obj) {
			this.currentClass.getAttrs().add("LineNumber");

		}

		@Override
		public void visitLineNumberTable(final LineNumberTable obj) {
			this.currentClass.getAttrs().add("LineNumberTable");

		}

		@Override
		public void visitLocalVariable(final LocalVariable obj) {
			this.currentClass.getAttrs().add("LocalVariable");

		}

		@Override
		public void visitLocalVariableTable(final LocalVariableTable obj) {
			this.currentClass.getAttrs().add("LocalVariableTable");

		}

		@Override
		public void visitLocalVariableTypeTable(
				final LocalVariableTypeTable obj) {
			this.currentClass.getAttrs().add("LocalVariableTypeTable");

		}

		@Override
		public void visitMethod(final Method obj) {
			// 1. Construir assinatura manualmente
			final StringBuilder methodSignature = new StringBuilder();

			if (obj.getAccessFlags() > 0) {
				methodSignature.append(Modifier.toString(obj.getAccessFlags()));
				methodSignature.append(' ');
			}

			methodSignature.append(obj.getReturnType().toString());
			methodSignature.append(' ');
			methodSignature.append(obj.getName());
			methodSignature.append('(');

			final Type[] args = obj.getArgumentTypes();
			for (int i = 0; i < args.length; i++) {
				methodSignature.append(args[i].toString());
				methodSignature.append(" a").append(i);
				if (i < args.length - 1) {
					methodSignature.append(", ");
				}
			}
			methodSignature.append(')');

			// 2. Criar o MethodInfo via .add(signature)
			final MethodInfo methodInfo = this.currentClass.getMethods()
					.add(methodSignature.toString());
			methodInfo.setAccess(obj.getAccessFlags());
			final AttrInfoList attrInfoList = methodInfo.getAttrs();

			// Luca 25/12/01: Synthetic Bridge
			if (obj.isSynthetic()) {
				attrInfoList.add("Synthetic");
				methodInfo.setAccess(
						methodInfo.getAccess() | Const.ACC_SYNTHETIC);
			}

			// 3. if code exist add it
			if (obj.getCode() != null) {
				final CodeAttrInfo codeAttrInfo = (CodeAttrInfo) attrInfoList
						.add("Code");
				// Henrique 4/29/2025 IMPORTANT , needs to clear the previous code,
				//		        
				methodInfo.getAttrs().remove("Code");

				codeAttrInfo.setMaxStack(obj.getCode().getMaxStack());
				codeAttrInfo.setMaxLocals(obj.getCode().getMaxLocals());
				codeAttrInfo.setCode(obj.getCode().getCode());

				// add attributes if exist
				for (Attribute innerAttr : obj.getCode().getAttributes()) {
					AttrInfo cfAttr = codeAttrInfo.getAttrs()
							.add(innerAttr.getName());

					if (innerAttr instanceof LineNumberTable bcelLine
							&& cfAttr instanceof LineNumberAttrInfo cfLine) {
						for (LineNumber ln : bcelLine.getLineNumberTable()) {
							cfLine.add(ln.getLineNumber(), ln.getStartPC());
						}
					}
					else if (innerAttr instanceof LocalVariableTable bcelLocal
							&& cfAttr instanceof LocalVariableAttrInfo cfLocal) {
						cfLocal.setFromBCEL(bcelLocal);
					}
					else if (innerAttr instanceof LocalVariableTypeTable bcelLocalType
							&& cfAttr instanceof LocalVariableTypeAttrInfo cfLocalType) {
						cfLocalType.setFromBCEL(bcelLocalType);
					}
					else if (innerAttr instanceof StackMap bcelStackMap
							&& cfAttr instanceof StackMapTableAttrInfo cfStackMap) {

						cfStackMap.setFromBCEL(bcelStackMap);

					}
				}

			}

			// 4. add Exceptions if exists
			if (obj.getExceptionTable() != null
					&& obj.getExceptionTable().getLength() > 0) {
				try {
					int[] bcelIndexes = obj.getExceptionTable()
							.getExceptionIndexTable();
					String[] classNames = Arrays.stream(bcelIndexes)
							.mapToObj(i -> obj.getConstantPool()
									.getConstantString(i, Const.CONSTANT_Class))
							.toArray(String[]::new);

					int[] cfparseIndexes = Arrays.stream(classNames)
							.mapToInt(this.currentClass.getCP()::addClass)
							.toArray();

					ExceptionsAttrInfo exceptionsAttr = (ExceptionsAttrInfo) methodInfo
							.getAttrs().add("Exceptions");

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(baos);

					out.writeInt(2 + 2 * cfparseIndexes.length);
					out.writeShort(cfparseIndexes.length);
					for (int index1 : cfparseIndexes) {
						out.writeShort(index1);
					}
					out.close();

					ByteArrayInputStream bais = new ByteArrayInputStream(
							baos.toByteArray());
					DataInputStream in = new DataInputStream(bais);
					exceptionsAttr.read(in);
					in.close();

				}
				catch (IOException e) {
					e.printStackTrace(
							util.io.ProxyConsole.getInstance().errorOutput());
				}
			}

			// 5. add Signature if exists
			if (obj.getGenericSignature() != null) {
				try {
					SignatureAttrInfo sigAttr = (SignatureAttrInfo) methodInfo
							.getAttrs().add("Signature");

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(baos);

					int sigIndex = this.currentClass.getCP()
							.addUtf8(obj.getGenericSignature());
					out.writeInt(2);
					out.writeShort(sigIndex);
					out.close();

					ByteArrayInputStream bais = new ByteArrayInputStream(
							baos.toByteArray());
					DataInputStream in = new DataInputStream(bais);
					sigAttr.read(in);
					in.close();
				}
				catch (IOException e) {
					e.printStackTrace(
							util.io.ProxyConsole.getInstance().errorOutput());
				}
			}

			// 6. add RuntimeInvisibleAnnotations if exists
			if (obj.getAnnotationEntries() != null
					&& obj.getAnnotationEntries().length > 0) {
				try {
					RuntimeInvisibleAnnotationsAttrInfo annotationAttr = (RuntimeInvisibleAnnotationsAttrInfo) methodInfo
							.getAttrs().add("RuntimeInvisibleAnnotations");

					ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
					DataOutputStream dataOut = new DataOutputStream(byteOut);

					int numAnnotations = obj.getAnnotationEntries().length;
					dataOut.writeInt(2 + numAnnotations * 4);
					dataOut.writeShort(numAnnotations);

					for (org.apache.bcel.classfile.AnnotationEntry entry : obj
							.getAnnotationEntries()) {
						int typeIndex = this.currentClass.getCP()
								.addUtf8(entry.getAnnotationType());
						dataOut.writeShort(typeIndex);
						dataOut.writeShort(0); // zero element_value_pairs
					}

					dataOut.close();

					ByteArrayInputStream byteIn = new ByteArrayInputStream(
							byteOut.toByteArray());
					DataInputStream dataIn = new DataInputStream(byteIn);
					annotationAttr.read(dataIn);
					dataIn.close();
				}
				catch (IOException e) {
					e.printStackTrace(
							util.io.ProxyConsole.getInstance().errorOutput());
				}
			}

		}

		@Override
		public void visitMethodParameters(final MethodParameters obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitParameterAnnotation(final ParameterAnnotations obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitParameterAnnotationEntry(
				final ParameterAnnotationEntry obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitSignature(final Signature obj) {

			final String signatureString = obj.getSignature();

			int sigIndex = this.currentClass.getCP().find(1, signatureString);
			if (sigIndex == -1) {
				sigIndex = this.currentClass.getCP().addUtf8(signatureString);
			}

			final SignatureAttrInfo sigAttrInfo = (SignatureAttrInfo) this.currentClass
					.getAttrs().add("Signature");
			sigAttrInfo.set(sigIndex);
		}

		@Override
		public void visitNestMembers(final NestMembers obj) {

			final NestMembersAttrInfo attr = (NestMembersAttrInfo) this.currentClass
					.getAttrs().add("NestMembers");

			attr.setMembers(obj.getClasses());
		}

		@Override
		public void visitSourceFile(final SourceFile obj) {

			final String fileName = obj.getSourceFileName();
			final SourceFileAttrInfo sourceFileAttrInfo = (SourceFileAttrInfo) this.currentClass
					.getAttrs().add("SourceFile");
			sourceFileAttrInfo.set(fileName);
		}

		@Override
		public void visitStackMap(final StackMap obj) {
			// TODO Auto-generated method stub
			this.currentClass.getAttrs().add("StackMap");

		}

		@Override
		public void visitStackMapEntry(final StackMapEntry obj) {
			// TODO Auto-generated method stub
			this.currentClass.getAttrs().add("StackMapEntry");
		}

		@Override
		public void visitSynthetic(final Synthetic obj) {
			this.currentClass.getAttrs().add("Synthetic");
		}

		@Override
		public void visitUnknown(final Unknown obj) {
			this.currentClass.getAttrs().add(obj.getName());
		}
	}

	public static ClassFile convertClassFile(final JavaClass aJavaClass) {
		final ClassFile classFile = new ClassFile();
		final JavaClassVisitor visitor = new JavaClassVisitor(classFile);

		aJavaClass.accept(visitor);

		return classFile;
	}
}

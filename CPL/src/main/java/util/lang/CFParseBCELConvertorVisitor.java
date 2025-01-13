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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.bcel.classfile.AnnotationDefault;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.Annotations;
import org.apache.bcel.classfile.BootstrapMethods;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
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
import org.apache.bcel.classfile.ParameterAnnotationEntry;
import org.apache.bcel.classfile.ParameterAnnotations;
import org.apache.bcel.classfile.Signature;
import org.apache.bcel.classfile.SourceFile;
import org.apache.bcel.classfile.StackMap;
import org.apache.bcel.classfile.StackMapEntry;
import org.apache.bcel.classfile.Synthetic;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.classfile.Visitor;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool.Utf8Entry;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;

/**
 * @author	Yann-Gaël Guéhéneuc
 * @since	2006/07/27
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
			// TODO Auto-generated method stub

		}

		@Override
		public void visitCode(final Code obj) {
			// TODO Auto-generated method stub

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
			// TODO Auto-generated method stub

		}

		@Override
		public void visitEnclosingMethod(final EnclosingMethod obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitExceptionTable(final ExceptionTable obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitField(final Field obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitInnerClass(final InnerClass obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitInnerClasses(final InnerClasses obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitJavaClass(final JavaClass obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitLineNumber(final LineNumber obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitLineNumberTable(final LineNumberTable obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitLocalVariable(final LocalVariable obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitLocalVariableTable(final LocalVariableTable obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitLocalVariableTypeTable(
				final LocalVariableTypeTable obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitMethod(final Method obj) {
			// TODO Auto-generated method stub

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
			// TODO Auto-generated method stub

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

		}

		@Override
		public void visitStackMapEntry(final StackMapEntry obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitSynthetic(final Synthetic obj) {
			// TODO Auto-generated method stub

		}

		@Override
		public void visitUnknown(final Unknown obj) {
			// TODO Auto-generated method stub

		}
	}

	public static ClassFile convertClassFile(final JavaClass aJavaClass) {
		final ClassFile classFile = new ClassFile();
		final JavaClassVisitor visitor = new JavaClassVisitor(classFile);

		aJavaClass.accept(visitor);

		return classFile;
	}
}

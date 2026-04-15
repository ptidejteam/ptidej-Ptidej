/* Decompiler 984ms, total 1473ms, lines 1146 */
package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Hashtable;

import com.ibm.toad.cfparse.utils.CPUtils;

import util.io.ProxyConsole;

public final class ConstantPool {
	public final class ClassEntry extends ConstantPoolEntry {

		private int d_index;
		// $FF: synthetic field
		// TODO Remove all such fields, they are not necessary
		final ConstantPool this$0;

		ClassEntry(ConstantPool var1, int var2) {
			(this.this$0 = var1).getClass();
			this.d_index = var2;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.ClassEntry)) {
				return false;
			}
			else {
				ConstantPool.ClassEntry var2 = (ConstantPool.ClassEntry) var1;
				int[] var3 = var2.getIndices();
				// Yann 24/12/10: It takes two to know...
				// The original code use to assume that we're comparing entries
				// from the same constant pool! But I want also to be able to
				// compare entries between constant pools, so I must cast and
				// use var2 in the argument of equals().
				//		return this.this$0.get(this.d_index)
				//				.equals(this.this$0.get(var3[0]));
				return this.this$0.get(this.d_index)
						.equals(var2.this$0.get(var3[0]));
			}
		}

		public String getAsJava() {
			String var1 = this.this$0.getAsString(this.d_index);
			if (!var1.startsWith("[")) {
				return CPUtils.slashes2dots(this.getAsString());
			}
			else {
				StringBuffer var2 = new StringBuffer();
				CPUtils.descriptor2java(var1, 0, var2);
				return var2.toString();
			}
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_index);
		}

		public int[] getIndices() {
			return new int[] { this.d_index };
		}

		void setIndices(int[] var1) {
			this.d_index = var1[0];
		}

		public String toString() {
			return "Class: " + this.d_index + " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(7);
			var1.writeShort(this.d_index);
		}
	}

	public static final class DoubleEntry extends ConstantPoolEntry {
		private double d_double;

		DoubleEntry(double var1) {
			this.d_double = var1;
		}

		public boolean equals(Object var1) {
			// Yann 24/12/10: NaN
			// We must compare strings because NaN != NaN.
			return var1 instanceof ConstantPool.DoubleEntry
					&& this.getAsString().equals(
							((ConstantPool.DoubleEntry) var1).getAsString());
		}

		public String getAsJava() {
			return "D" + this.d_double;
		}

		public String getAsString() {
			return String.valueOf(this.d_double);
		}

		public double getValue() {
			return this.d_double;
		}

		public int hashCode() {
			return this.toString().hashCode();
		}

		public void setValue(double var1) {
			this.d_double = var1;
		}

		public String toString() {
			return "Double: " + this.d_double;
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(6);
			var1.writeDouble(this.d_double);
		}
	}

	public final class FieldrefEntry extends ConstantPoolEntry {
		private int d_idxClass;
		private int d_idxNameAndType;
		// $FF: synthetic field
		final ConstantPool this$0;

		FieldrefEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_idxClass = var2;
			this.d_idxNameAndType = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.FieldrefEntry)) {
				return false;
			}
			else {
				ConstantPool.FieldrefEntry var2 = (ConstantPool.FieldrefEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_idxClass)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxNameAndType)
								.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsJava() {
			ConstantPool.NameAndTypeEntry var1 = (ConstantPool.NameAndTypeEntry) this.this$0
					.get(this.d_idxNameAndType);
			String var2 = var1.getTypeAsJava();
			String var3 = var1.getNameAsJava();
			return var2 + " " + this.this$0.getAsJava(this.d_idxClass) + "."
					+ var3;
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_idxClass) + " "
					+ this.this$0.getAsString(this.d_idxNameAndType);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxClass, this.d_idxNameAndType };
		}

		void setIndices(int[] var1) {
			this.d_idxClass = var1[0];
			this.d_idxNameAndType = var1[1];
		}

		public String toString() {
			return "Fieldref: " + this.d_idxClass + " " + this.d_idxNameAndType
					+ " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(9);
			var1.writeShort(this.d_idxClass);
			var1.writeShort(this.d_idxNameAndType);
		}
	}

	public static final class FloatEntry extends ConstantPoolEntry {
		private float d_float;

		FloatEntry(float var1) {
			this.d_float = var1;
		}

		public boolean equals(Object var1) {
			return var1 instanceof ConstantPool.FloatEntry && this.getAsString()
					.equals(((ConstantPool.FloatEntry) var1).getAsString());
		}

		public String getAsJava() {
			return "F" + this.d_float;
		}

		public String getAsString() {
			return String.valueOf(this.d_float);
		}

		public float getValue() {
			return this.d_float;
		}

		public void setValue(float var1) {
			this.d_float = var1;
		}

		public String toString() {
			return "Float: " + this.d_float;
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(4);
			var1.writeFloat(this.d_float);
		}
	}

	public static final class IntegerEntry extends ConstantPoolEntry {
		private int d_integer;

		IntegerEntry(int var1) {
			this.d_integer = var1;
		}

		public boolean equals(Object var1) {
			return var1 instanceof ConstantPool.IntegerEntry
					&& this.getAsString().equals(
							((ConstantPool.IntegerEntry) var1).getAsString());
		}

		public String getAsJava() {
			return "#" + this.d_integer;
		}

		public String getAsString() {
			return String.valueOf(this.d_integer);
		}

		public int getValue() {
			return this.d_integer;
		}

		public void setValue(int var1) {
			this.d_integer = var1;
		}

		public String toString() {
			return "Integer: " + this.d_integer;
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(3);
			var1.writeInt(this.d_integer);
		}
	}

	public final class InterfaceMethodrefEntry extends ConstantPoolEntry {
		private int d_idxClass;
		private int d_idxNameAndType;
		// $FF: synthetic field
		final ConstantPool this$0;

		InterfaceMethodrefEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_idxClass = var2;
			this.d_idxNameAndType = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.InterfaceMethodrefEntry)) {
				return false;
			}
			else {
				ConstantPool.InterfaceMethodrefEntry var2 = (ConstantPool.InterfaceMethodrefEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_idxClass)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxNameAndType)
								.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsJava() {
			ConstantPool.NameAndTypeEntry var1 = (ConstantPool.NameAndTypeEntry) this.this$0
					.get(this.d_idxNameAndType);
			String var2 = var1.getTypeAsJava();
			int var3 = var2.indexOf(" ");
			String var4 = "";
			String var5;
			if (var3 == -1) {
				var5 = var2;
			}
			else {
				var4 = var2.substring(0, var3);
				var5 = var2.substring(var3 + 1);
			}

			String var6 = var1.getNameAsJava();
			return var4 + " " + this.this$0.getAsJava(this.d_idxClass) + "."
					+ var6 + var5;
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_idxClass) + " "
					+ this.this$0.getAsString(this.d_idxNameAndType);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxClass, this.d_idxNameAndType };
		}

		void setIndices(int[] var1) {
			this.d_idxClass = var1[0];
			this.d_idxNameAndType = var1[1];
		}

		public String toString() {
			return "InterfaceMethodref: " + this.d_idxClass + " "
					+ this.d_idxNameAndType + " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(11);
			var1.writeShort(this.d_idxClass);
			var1.writeShort(this.d_idxNameAndType);
		}
	}

	// Yann 24/11/28: "New" constant pool entry since Java 7 
	public final class InvokeDynamicEntry extends ConstantPoolEntry {
		private int d_idxBootstrapMethod;
		private int d_idxNameAndType;
		// $FF: synthetic field
		final ConstantPool this$0;

		InvokeDynamicEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_idxBootstrapMethod = var2;
			this.d_idxNameAndType = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.InvokeDynamicEntry)) {
				return false;
			}
			else {
				ConstantPool.InvokeDynamicEntry var2 = (ConstantPool.InvokeDynamicEntry) var1;
				int[] var3 = var2.getIndices();
				// We do not test the bootstrap method
				// because CFParse do not handle them
				// and they could be simply "null".
				// TODO Create the BootstrapMethods attribute and add this test back 
				/*
				return this.this$0.get(this.d_idxBootstrapMethod)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxNameAndType)
								.equals(var2.this$0.get(var3[1]));
				*/
				return this.this$0.get(this.d_idxNameAndType)
						.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsJava() {
			ConstantPool.NameAndTypeEntry var1 = (ConstantPool.NameAndTypeEntry) this.this$0
					.get(this.d_idxNameAndType);
			String var2 = var1.getTypeAsJava();
			String var3 = var1.getNameAsJava();
			return var2 + " " + this.this$0.getAsJava(this.d_idxBootstrapMethod)
					+ "." + var3;
		}

		public String getAsString() {
			// Yann 24/11/30: No BootstrapMethods Attribute
			// I don't create/populate the BootstrapMethods attribute,
			// so there's nothing to do right now with the index at
			// "this.d_idxBootstrapMethod".
			//		return this.this$0.getAsString(this.d_idxBootstrapMethod) + " "
			//				+ this.this$0.getAsString(this.d_idxNameAndType);
			// TODO Create the BootstrapMethods attribute
			return this.this$0.getAsString(this.d_idxNameAndType);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxBootstrapMethod,
					this.d_idxNameAndType };
		}

		void setIndices(int[] var1) {
			this.d_idxBootstrapMethod = var1[0];
			this.d_idxNameAndType = var1[1];
		}

		public String toString() {
			return "InvokeDynamic: " + this.d_idxBootstrapMethod + " "
					+ this.d_idxNameAndType + " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(18);
			var1.writeShort(this.d_idxBootstrapMethod);
			var1.writeShort(this.d_idxNameAndType);
		}
	}

	public static final class LongEntry extends ConstantPoolEntry {
		private long d_long;

		LongEntry(long var1) {
			this.d_long = var1;
		}

		public boolean equals(Object var1) {
			return var1 instanceof ConstantPool.LongEntry && this.getAsString()
					.equals(((ConstantPool.LongEntry) var1).getAsString());
		}

		public String getAsJava() {
			return "L" + this.d_long;
		}

		public String getAsString() {
			return String.valueOf(this.d_long);
		}

		public long getValue() {
			return this.d_long;
		}

		public void setValue(long var1) {
			this.d_long = var1;
		}

		public String toString() {
			return "Long: " + this.d_long;
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(5);
			var1.writeLong(this.d_long);
		}
	}

	// Yann 24/11/28: "New" constant pool entry since Java 7 
	public final class MethodHandleEntry extends ConstantPoolEntry {
		private int d_idxReference;
		private int d_Kind;
		// $FF: synthetic field
		final ConstantPool this$0;

		MethodHandleEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_Kind = var2;
			this.d_idxReference = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.NameAndTypeEntry)) {
				return false;
			}
			else {
				ConstantPool.NameAndTypeEntry var2 = (ConstantPool.NameAndTypeEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_Kind)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxReference)
								.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_Kind) + " "
					+ this.this$0.getAsString(this.d_idxReference);
		}

		public int[] getIndices() {
			return new int[] { this.d_Kind, this.d_idxReference };
		}

		String getNameAsJava() {
			return this.this$0.getAsString(this.d_idxReference);
		}

		String getTypeAsJava() {
			return CPUtils.internal2java(
					this.this$0.getAsString(this.d_idxReference));
		}

		void setIndices(int[] var1) {
			this.d_Kind = var1[0];
			this.d_idxReference = var1[1];
		}

		public String toString() {
			return "MethodHandle: " + this.d_Kind + " " + this.d_idxReference
					+ " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(15);
			var1.writeByte(this.d_Kind);
			var1.writeShort(this.d_idxReference);
		}
	}

	public final class MethodrefEntry extends ConstantPoolEntry {
		private int d_idxClass;
		private int d_idxNameAndType;
		// $FF: synthetic field
		final ConstantPool this$0;

		MethodrefEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_idxClass = var2;
			this.d_idxNameAndType = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.MethodrefEntry)) {
				return false;
			}
			else {
				ConstantPool.MethodrefEntry var2 = (ConstantPool.MethodrefEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_idxClass)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxNameAndType)
								.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsJava() {
			ConstantPool.NameAndTypeEntry var1 = (ConstantPool.NameAndTypeEntry) this.this$0
					.get(this.d_idxNameAndType);
			String var2 = var1.getTypeAsJava();
			int var3 = var2.indexOf(" ");
			String var4 = "";
			String var5;
			if (var3 == -1) {
				var5 = var2;
			}
			else {
				var4 = var2.substring(0, var3);
				var5 = var2.substring(var3 + 1);
			}

			String var6 = var1.getNameAsJava();
			return var4 + " " + this.this$0.getAsJava(this.d_idxClass) + "."
					+ var6 + var5;
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_idxClass) + " "
					+ this.this$0.getAsString(this.d_idxNameAndType);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxClass, this.d_idxNameAndType };
		}

		void setIndices(int[] var1) {
			this.d_idxClass = var1[0];
			this.d_idxNameAndType = var1[1];
		}

		public String toString() {
			return "Methodref: " + this.d_idxClass + " " + this.d_idxNameAndType
					+ " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(10);
			var1.writeShort(this.d_idxClass);
			var1.writeShort(this.d_idxNameAndType);
		}
	}

	// Yann 24/11/28: "New" constant pool entry since Java 7 
	public final class MethodTypeEntry extends ConstantPoolEntry {
		private int d_idxDescriptor;
		// $FF: synthetic field
		final ConstantPool this$0;

		MethodTypeEntry(ConstantPool var1, int var2) {
			(this.this$0 = var1).getClass();
			this.d_idxDescriptor = var2;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.MethodTypeEntry)) {
				return false;
			}
			else {
				ConstantPool.MethodTypeEntry var2 = (ConstantPool.MethodTypeEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_idxDescriptor)
						.equals(var2.this$0.get(var3[0]));
			}
		}

		public String getAsJava() {
			return this.getNameAsJava() + "." + this.getTypeAsJava();
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_idxDescriptor);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxDescriptor };
		}

		String getNameAsJava() {
			// TODO Is this method necessary?
			return this.this$0.getAsString(this.d_idxDescriptor);
		}

		String getTypeAsJava() {
			// TODO Is this method necessary?
			return CPUtils.internal2java(
					this.this$0.getAsString(this.d_idxDescriptor));
		}

		void setIndices(int[] var1) {
			this.d_idxDescriptor = var1[0];
		}

		public String toString() {
			return "MethodType: " + this.d_idxDescriptor + " ("
					+ this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(16);
			var1.writeShort(this.d_idxDescriptor);
		}
	}

	public final class NameAndTypeEntry extends ConstantPoolEntry {
		private int d_idxName;
		private int d_idxType;
		// $FF: synthetic field
		final ConstantPool this$0;

		NameAndTypeEntry(ConstantPool var1, int var2, int var3) {
			(this.this$0 = var1).getClass();
			this.d_idxName = var2;
			this.d_idxType = var3;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.NameAndTypeEntry)) {
				return false;
			}
			else {
				ConstantPool.NameAndTypeEntry var2 = (ConstantPool.NameAndTypeEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_idxName)
						.equals(var2.this$0.get(var3[0]))
						&& this.this$0.get(this.d_idxType)
								.equals(var2.this$0.get(var3[1]));
			}
		}

		public String getAsJava() {
			return this.getNameAsJava() + "." + this.getTypeAsJava();
		}

		public String getAsString() {
			return this.this$0.getAsString(this.d_idxName) + " "
					+ this.this$0.getAsString(this.d_idxType);
		}

		public int[] getIndices() {
			return new int[] { this.d_idxName, this.d_idxType };
		}

		String getNameAsJava() {
			return this.this$0.getAsString(this.d_idxName);
		}

		String getTypeAsJava() {
			return CPUtils
					.internal2java(this.this$0.getAsString(this.d_idxType));
		}

		void setIndices(int[] var1) {
			this.d_idxName = var1[0];
			this.d_idxType = var1[1];
		}

		public String toString() {
			return "NameAndType: " + this.d_idxName + " " + this.d_idxType
					+ " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(12);
			var1.writeShort(this.d_idxName);
			var1.writeShort(this.d_idxType);
		}
	}

	public final class StringEntry extends ConstantPoolEntry {
		private int d_index;
		// $FF: synthetic field
		final ConstantPool this$0;

		StringEntry(ConstantPool var1, int var2) {
			(this.this$0 = var1).getClass();
			this.d_index = var2;
		}

		public boolean equals(Object var1) {
			if (!(var1 instanceof ConstantPool.StringEntry)) {
				return false;
			}
			else {
				ConstantPool.StringEntry var2 = (ConstantPool.StringEntry) var1;
				int[] var3 = var2.getIndices();
				return this.this$0.get(this.d_index)
						.equals(var2.this$0.get(var3[0]));
			}
		}

		public String getAsJava() {
			return this.this$0.get(this.d_index).getAsJava();
		}

		public String getAsString() {
			return this.this$0.get(this.d_index).getAsString();
		}

		public int[] getIndices() {
			return new int[] { this.d_index };
		}

		void setIndices(int[] var1) {
			this.d_index = var1[0];
		}

		public String toString() {
			return "String: " + this.d_index + " (" + this.getAsJava() + ")";
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(8);
			var1.writeShort(this.d_index);
		}
	}

	public static final class Utf8Entry extends ConstantPoolEntry {
		private String d_rewrite;
		private String d_string;

		Utf8Entry(String var1) {
			this.d_string = var1;
			this.d_rewrite = this.rewrite(var1);
		}

		public boolean equals(Object var1) {
			return var1 instanceof ConstantPool.Utf8Entry && this.d_rewrite
					.equals(((ConstantPool.Utf8Entry) var1).d_rewrite);
		}

		public String getAsJava() {
			return "\"" + this.d_rewrite + "\"";
		}

		public String getAsString() {
			return this.d_rewrite;
		}

		public String getValue() {
			return this.d_string;
		}

		private String rewrite(String var1) {
			StringBuffer var2 = new StringBuffer(var1.length() + 10);

			for (int var3 = 0; var3 < var1.length(); ++var3) {
				switch (var1.charAt(var3)) {
				case '\b':
					var2.append('\\');
					var2.append('b');
					break;
				case '\t':
					var2.append('\\');
					var2.append('t');
					break;
				case '\n':
					var2.append('\\');
					var2.append('n');
					break;
				case '\f':
					var2.append('\\');
					var2.append('f');
					break;
				case '\r':
					var2.append('\\');
					var2.append('r');
					break;
				case '"':
					var2.append('\\');
					var2.append('"');
					break;
				case '\'':
					var2.append('\\');
					var2.append('n');
					break;
				case '\\':
					var2.append('\\');
					var2.append('\\');
					break;
				default:
					var2.append(var1.charAt(var3));
				}

			}

			return var2.toString();
		}

		public void setValue(String var1) {
			this.d_string = var1;
			this.d_rewrite = this.rewrite(var1);
		}

		public String toString() {
			return "Utf8: " + this.d_rewrite;
		}

		void write(DataOutputStream var1) throws IOException {
			var1.writeByte(1);
			var1.writeUTF(this.d_string);
		}
	}

	public static final int CONSTANT_Class = 7;
	public static final int CONSTANT_Double = 6;
	public static final int CONSTANT_Fieldref = 9;
	public static final int CONSTANT_Float = 4;
	public static final int CONSTANT_Integer = 2;
	public static final int CONSTANT_InterfaceMethodref = 11;
	public static final int CONSTANT_InvokeDynamic = 18;
	public static final int CONSTANT_Long = 5;
	public static final int CONSTANT_MethodHandle = 15;
	public static final int CONSTANT_Methodref = 10;
	public static final int CONSTANT_MethodType = 16;
	public static final int CONSTANT_NameAndType = 12;
	public static final int CONSTANT_String = 8;
	public static final int CONSTANT_Utf8 = 1;

	private ConstantPoolEntry[] d_constants;
	private HashMap d_hashC2i;
	private HashMap d_hashN2C;
	private int d_numConstants;

	public ConstantPool() {
		this.d_numConstants = 1;
		this.d_constants = new ConstantPoolEntry[1];
		this.d_constants[0] = new ConstantPool.Utf8Entry("<dummy Entry>");
		this.d_hashN2C = new HashMap();
		this.d_hashC2i = new HashMap();
	}

	public ConstantPool(InputStream var1) throws IOException {
		this();
		this.read(new DataInputStream(var1));
	}

	public int addClass(String className) {
		className = className.replace('.', '/'); // canonical form
		int utf8Index = this.find(ConstantPool.CONSTANT_Utf8, className);
		if (utf8Index == -1) {
			utf8Index = this.addUtf8(className);
		}

		int classIndex = this.find(ConstantPool.CONSTANT_Class, className);
		if (classIndex == -1) {

			classIndex = this.addNewElement(
					new ConstantPool.ClassEntry(this, utf8Index));
		}

		return classIndex;
	}

	public int addDouble(double var1) {
		return this.addNewElement(new ConstantPool.DoubleEntry(var1));
	}

	public int addField(String var1) {
		String var2 = var1.substring(0, var1.indexOf(" "));
		String var3 = var1.substring(var1.indexOf(" ") + 1,
				var1.lastIndexOf(" "));
		String var4 = var1.substring(var1.lastIndexOf(" ") + 1, var1.length());

		// Declaring class
		int var5 = this.find(7, var2);
		if (var5 == -1) {
			var5 = this.addClass(var2);
		}

		// Declared field
		int var6 = this.find(12, var3 + " " + var4);
		if (var6 == -1) {
			var6 = this.addNameAndType(var3, var4);
		}

		return this.addNewElement(
				new ConstantPool.FieldrefEntry(this, var5, var6));
	}

	public int addFloat(float var1) {
		return this.addNewElement(new ConstantPool.FloatEntry(var1));
	}

	public int addInteger(int var1) {
		return this.addNewElement(new ConstantPool.IntegerEntry(var1));
	}

	public int addInterface(String var1) {
		String var2 = var1.substring(0, var1.indexOf(" "));
		String var3 = var1.substring(var1.indexOf(" ") + 1,
				var1.lastIndexOf(" "));
		String var4 = var1.substring(var1.lastIndexOf(" ") + 1, var1.length());
		int var5 = this.find(7, var2);
		if (var5 == -1) {
			var5 = this.addClass(var2);
		}

		int var6 = this.find(12, var3 + " " + var4);
		if (var6 == -1) {
			var6 = this.addNameAndType(var3, var4);
		}

		return this.addNewElement(
				new ConstantPool.InterfaceMethodrefEntry(this, var5, var6));
	}

	public int addInvokeDynamic(final int bootstrapMethodIndex,
			final int nameAndTypeIndex) {

		return this.addNewElement(new ConstantPool.InvokeDynamicEntry(this,
				bootstrapMethodIndex, nameAndTypeIndex));
	}

	public int addLong(long var1) {
		return this.addNewElement(new ConstantPool.LongEntry(var1));
	}

	public int addMethod(String var1) {
		String var2 = var1.substring(0, var1.indexOf(" "));
		String var3 = var1.substring(var1.indexOf(" ") + 1,
				var1.lastIndexOf(" "));
		String var4 = var1.substring(var1.lastIndexOf(" ") + 1, var1.length());
		int var5 = this.find(7, var2);
		if (var5 == -1) {
			var5 = this.addClass(var2);
		}

		int var6 = this.find(12, var3 + " " + var4);
		if (var6 == -1) {
			var6 = this.addNameAndType(var3, var4);
		}

		return this.addNewElement(
				new ConstantPool.MethodrefEntry(this, var5, var6));
	}

	public int addMethodHandle(final int referenceKind,
			final int referenceIndex) {
		return this.addNewElement(new ConstantPool.MethodHandleEntry(this,
				referenceKind, referenceIndex));
	}

	public int addMethodType(final int utf8Entry) {
		return this.addNewElement(
				new ConstantPool.MethodTypeEntry(this, utf8Entry));
	}

	public int addNameAndType(String var1, String var2) {
		int var3 = this.find(1, var1);
		if (var3 == -1) {
			var3 = this.addUtf8(var1);
		}

		int var4 = this.find(1, var2);
		if (var4 == -1) {
			var4 = this.addUtf8(var2);
		}

		return this.addNewElement(
				new ConstantPool.NameAndTypeEntry(this, var3, var4));
	}

	private int addNewElement(ConstantPoolEntry var1) {
		// Yann 24/12/10: Duplications!
		// Do not add twice any entry...
		int index = this.find(var1);
		if (index > -1) {
			return index;
		}

		if (this.d_constants == null
				|| this.d_numConstants == this.d_constants.length) {
			this.resize();
		}

		this.d_constants[this.d_numConstants] = var1;
		int var2 = this.d_numConstants++;
		// Long and Double, why is this needed?
		// TODO Is this necessary? Could we do without it?
		if (var1 instanceof ConstantPool.LongEntry
				|| var1 instanceof ConstantPool.DoubleEntry) {
			if (this.d_numConstants == this.d_constants.length) {
				this.resize();
			}

			++this.d_numConstants;
		}

		this.d_hashN2C.put(this.getType(var1) + var1.getAsString(), var1);
		this.d_hashC2i.put(var1, Integer.valueOf(var2));
		return var2;
	}

	public int addString(String var1) {
		int var2 = this.find(1, var1);
		if (var2 == -1) {
			var2 = this.addUtf8(var1);
		}

		return this.addNewElement(new ConstantPool.StringEntry(this, var2));
	}

	public int addUtf8(String var1) {
		return this.addNewElement(new ConstantPool.Utf8Entry(var1));
	}

	public void editClass(int var1, String var2) {
		ConstantPoolEntry var3 = this.d_constants[var1];
		if (var3 instanceof ConstantPool.ClassEntry) {
			this.d_hashN2C.remove(this.getType(var3) + var3.getAsString());
			ConstantPool.Utf8Entry var4 = (ConstantPool.Utf8Entry) this.d_constants[var3
					.getIndices()[0]];
			this.d_hashN2C.remove(this.getType(var4) + var4.getAsString());
			var4.setValue(var2);
			this.d_hashN2C.put(this.getType(var4) + var4.getAsString(), var4);
			this.d_hashN2C.put(this.getType(var3) + var3.getAsString(), var3);
		}
	}

	public void editFieldName(int var1, String var2) {
		ConstantPoolEntry var3 = this.d_constants[var1];
		if (var3 instanceof ConstantPool.FieldrefEntry) {
			this.d_hashN2C.remove(this.getType(var3) + var3.getAsString());
			ConstantPoolEntry var4 = this.d_constants[var3.getIndices()[1]];
			ConstantPool.Utf8Entry var5 = (ConstantPool.Utf8Entry) this.d_constants[var4
					.getIndices()[0]];
			this.d_hashN2C.remove(this.getType(var5) + var5.getAsString());
			var5.setValue(var2);
			this.d_hashN2C.put(this.getType(var5) + var5.getAsString(), var5);
			this.d_hashN2C.put(this.getType(var3) + var3.getAsString(), var3);
		}
	}

	public void editInterfaceName(int var1, String var2) {
		ConstantPoolEntry var3 = this.d_constants[var1];
		if (var3 instanceof ConstantPool.InterfaceMethodrefEntry) {
			this.d_hashN2C.remove(this.getType(var3) + var3.getAsString());
			ConstantPoolEntry var4 = this.d_constants[var3.getIndices()[1]];
			ConstantPool.Utf8Entry var5 = (ConstantPool.Utf8Entry) this.d_constants[var4
					.getIndices()[0]];
			this.d_hashN2C.remove(this.getType(var5) + var5.getAsString());
			var5.setValue(var2);
			this.d_hashN2C.put(this.getType(var5) + var5.getAsString(), var5);
			this.d_hashN2C.put(this.getType(var3) + var3.getAsString(), var3);
		}
	}

	public void editMethodName(int var1, String var2) {
		ConstantPoolEntry var3 = this.d_constants[var1];
		if (var3 instanceof ConstantPool.MethodrefEntry) {
			this.d_hashN2C.remove(this.getType(var3) + var3.getAsString());
			ConstantPoolEntry var4 = this.d_constants[var3.getIndices()[1]];
			ConstantPool.Utf8Entry var5 = (ConstantPool.Utf8Entry) this.d_constants[var4
					.getIndices()[0]];
			this.d_hashN2C.remove(this.getType(var5) + var5.getAsString());
			var5.setValue(var2);
			this.d_hashN2C.put(this.getType(var5) + var5.getAsString(), var5);
			this.d_hashN2C.put(this.getType(var3) + var3.getAsString(), var3);
		}
	}

	public void editNameAndType(int var1, Hashtable var2, Hashtable var3) {
		ConstantPoolEntry var4 = this.d_constants[var1];
		if (var4 instanceof ConstantPool.NameAndTypeEntry) {
			ConstantPool.Utf8Entry var5 = (ConstantPool.Utf8Entry) this.d_constants[var4
					.getIndices()[0]];
			String var6 = (String) var2.get(var5.getAsString());
			if (var6 != null) {
				var5.setValue(var6);
			}

			var5 = (ConstantPool.Utf8Entry) this.d_constants[var4
					.getIndices()[1]];
			String var7 = var5.getAsString();
			int var8 = 0;
			// boolean var9 = false;
			StringBuffer var10 = new StringBuffer();

			while (true) {
				int var11 = var7.indexOf("L", var8);
				if (var11 == -1) {
					var10.append(var7.substring(var8, var7.length()));
					if (var10.toString() != null) {
						var5.setValue(var10.toString());
					}

					return;
				}

				int var12 = var7.indexOf(";", var11);
				var10.append(var7.substring(var8, var11 + 1));
				Hashtable var13 = (Hashtable) var3.get(
						var7.substring(var11 + 1, var12).replace('/', '.'));
				String var14 = var7.substring(var11 + 1, var12);
				if (var13 != null) {
					String var15 = (String) var13.get(
							var7.substring(var11 + 1, var12).replace('/', '.'));
					if (var15 != null) {
						var14 = var15;
					}

					// var9 = true;
				}

				var10.append(var14);
				var8 = var12;
			}
		}
	}

	public void editString(int var1, String var2) {
		ConstantPoolEntry var3 = this.d_constants[var1];
		if (var3 instanceof ConstantPool.StringEntry) {
			this.d_hashN2C.remove(this.getType(var3) + var3.getAsString());
			ConstantPool.Utf8Entry var4 = (ConstantPool.Utf8Entry) this.d_constants[var3
					.getIndices()[0]];
			this.d_hashN2C.remove(this.getType(var4) + var4.getAsString());
			var4.setValue(var2);
			this.d_hashN2C.put(this.getType(var4) + var4.getAsString(), var4);
			this.d_hashN2C.put(this.getType(var3) + var3.getAsString(), var3);
		}
	}

	public int find(ConstantPoolEntry var1) {
		return this.find(this.getType(var1), var1.getAsString());
	}

	public int find(double var1) {
		for (int var3 = 1; var3 < this.d_numConstants; ++var3) {
			if (this.getType(var3) == 6
					&& var1 == ((ConstantPool.DoubleEntry) this.get(var3))
							.getValue()) {
				return var3;
			}
		}

		return -1;
	}

	public int find(float var1) {
		for (int var2 = 1; var2 < this.d_numConstants; ++var2) {
			if (this.getType(var2) == 4
					&& var1 == ((ConstantPool.FloatEntry) this.get(var2))
							.getValue()) {
				return var2;
			}
		}

		return -1;
	}

	public int find(int var1) {
		for (int var2 = 1; var2 < this.d_numConstants; ++var2) {
			if (this.getType(var2) == 3
					&& var1 == ((ConstantPool.IntegerEntry) this.get(var2))
							.getValue()) {
				return var2;
			}
		}

		return -1;
	}

	public int find(int var1, String var2) {
		int var3 = -1;
		ConstantPoolEntry var4 = (ConstantPoolEntry) this.d_hashN2C
				.get(var1 + var2);
		if (var4 != null) {
			Integer var5 = (Integer) this.d_hashC2i.get(var4);
			if (var5 == null) {
				ProxyConsole.getInstance().errorOutput()
						.println("Whoops: " + var4);
				var3 = -1;
			}
			else {
				var3 = var5;
			}
		}

		return var3;
	}

	public int find(int var1, String var2, String var3) {
		int var4 = -1;
		ConstantPoolEntry var5 = (ConstantPoolEntry) this.d_hashN2C
				.get(var1 + var2 + ' ' + var3);
		if (var5 != null) {
			Integer var6 = (Integer) this.d_hashC2i.get(var5);
			if (var6 == null) {
				ProxyConsole.getInstance().errorOutput()
						.println("Whoops: " + var5);
				var4 = -1;
			}
			else {
				var4 = var6;
			}
		}

		return var4;
	}

	public ConstantPoolEntry get(int var1) {
		return this.d_constants[var1];
	}

	public String getAsJava(int var1) {
		return this.get(var1).getAsJava();
	}

	public String getAsString(int var1) {
		return this.get(var1).getAsString();
	}

	public int getType(ConstantPoolEntry var1) {
		if (var1 instanceof ConstantPool.ClassEntry) {
			return 7;
		}
		else if (var1 instanceof ConstantPool.FieldrefEntry) {
			return 9;
		}
		else if (var1 instanceof ConstantPool.MethodrefEntry) {
			return 10;
		}
		else if (var1 instanceof ConstantPool.InterfaceMethodrefEntry) {
			return 11;
		}
		else if (var1 instanceof ConstantPool.StringEntry) {
			return 8;
		}
		else if (var1 instanceof ConstantPool.IntegerEntry) {
			return 3;
		}
		else if (var1 instanceof ConstantPool.FloatEntry) {
			return 4;
		}
		else if (var1 instanceof ConstantPool.LongEntry) {
			return 5;
		}
		else if (var1 instanceof ConstantPool.DoubleEntry) {
			return 6;
		}
		else if (var1 instanceof ConstantPool.NameAndTypeEntry) {
			return 12;
		}
		else {
			return var1 instanceof ConstantPool.Utf8Entry ? 1 : -1;
		}
	}

	public int getType(int var1) {
		return var1 >= 0 && var1 <= this.d_numConstants
				? this.getType(this.get(var1))
				: -1;
	}

	public int length() {
		return this.d_numConstants;
	}

	void read(DataInputStream var1) throws IOException {
		this.d_numConstants = var1.readShort();
		this.d_constants = new ConstantPoolEntry[this.d_numConstants];
		this.d_constants[0] = new ConstantPool.Utf8Entry("<dummy Entry>");

		int var2;
		for (var2 = 1; var2 < this.d_numConstants; ++var2) {
			if (this.readConstant(var1, var2)) {
				++var2;
			}
		}

		for (var2 = 1; var2 < this.d_numConstants; ++var2) {
			if (this.d_constants[var2] != null) {
				this.d_hashN2C.put(
						this.getType(var2)
								+ this.d_constants[var2].getAsString(),
						this.d_constants[var2]);
				this.d_hashC2i.put(this.d_constants[var2],
						Integer.valueOf(var2));
			}
		}
	}

	private boolean readConstant(DataInputStream var1, int var2)
			throws IOException {
		byte var3 = var1.readByte();
		short var4;
		short var5;
		switch (var3) {
		case 1:
			String var15 = var1.readUTF();
			this.d_constants[var2] = new ConstantPool.Utf8Entry(var15);
			break;
		case 3:
			int var7 = var1.readInt();
			this.d_constants[var2] = new ConstantPool.IntegerEntry(var7);
			break;
		case 4:
			float var8 = var1.readFloat();
			this.d_constants[var2] = new ConstantPool.FloatEntry(var8);
			break;
		case 5:
			long var9 = var1.readLong();
			this.d_constants[var2] = new ConstantPool.LongEntry(var9);
			break;
		case 6:
			double var11 = var1.readDouble();
			this.d_constants[var2] = new ConstantPool.DoubleEntry(var11);
			break;
		case 7:
			var4 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.ClassEntry(this, var4);
			break;
		case 8:
			var4 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.StringEntry(this, var4);
			break;
		case 9:
			var4 = var1.readShort();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.FieldrefEntry(this, var4,
					var5);
			break;
		case 10:
			var4 = var1.readShort();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.MethodrefEntry(this, var4,
					var5);
			break;
		case 11:
			var4 = var1.readShort();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.InterfaceMethodrefEntry(
					this, var4, var5);
			break;
		case 12:
			var4 = var1.readShort();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.NameAndTypeEntry(this,
					var4, var5);
			break;
		case 15:
			byte var0 = var1.readByte();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.MethodHandleEntry(this,
					var0, var5);
			break;
		case 16:
			var4 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.MethodTypeEntry(this,
					var4);
			break;
		case 18:
			var4 = var1.readShort();
			var5 = var1.readShort();
			this.d_constants[var2] = new ConstantPool.InvokeDynamicEntry(this,
					var4, var5);
			break;

		// No constant pool tags or missing tags
		case 2:
		case 13:
		case 14:
		case 17:
		default:
			// throw new IOException("Unknown class file constant tag <" + var3 + "> in constant pool");
			// Yann 24/11/24: Ignoramus?
			// Can I just ignore the constants unknown by CFParse?
			ProxyConsole.getInstance().errorOutput().println(
					"com.ibm.toad.cfparse.ConstantPool.readConstant(...): Unknown class file constant tag <"
							+ var3 + "> in constant pool");
			break;
		}

		// Long and Double, why is this needed?
		// TODO Is this necessary? Could we do without it?
		return var3 == 6 || var3 == 5;
	}

	public void remove(int var1) {
		if (var1 >= 0 && var1 < this.d_numConstants - 1) {
			for (int var2 = var1; var2 < this.d_numConstants - 1; ++var2) {
				this.d_constants[var2] = this.d_constants[var2 + 1];
			}

			this.d_numConstants += -1;
		}
	}

	public void removeAll() {
		this.d_numConstants = 1;
		this.d_constants = new ConstantPoolEntry[1];
		this.d_constants[0] = new ConstantPool.Utf8Entry("<dummy Entry>");
		this.d_hashN2C = new HashMap();
		this.d_hashC2i = new HashMap();
	}

	private void resize() {
		ConstantPoolEntry[] var1 = new ConstantPoolEntry[this.d_numConstants
				+ 10];
		if (this.d_constants != null) {
			System.arraycopy(this.d_constants, 0, var1, 0, this.d_numConstants);
		}

		this.d_constants = var1;
	}

	public void sort(int[] var1) {
		int var4 = 0;

		for (int var5 = 1; var5 < this.d_numConstants; ++var5) {
			if (var1[var5] == -1) {
				++var4;
			}
		}

		ConstantPoolEntry[] var7 = new ConstantPoolEntry[this.d_numConstants
				- var4 + 1];
		var7[0] = new ConstantPool.Utf8Entry("<dummy Entry>");

		int var6;
		for (var6 = 1; var6 < this.d_numConstants; ++var6) {
			if (var1[var6] != -1) {
				ConstantPoolEntry var3 = this.get(var6);
				if (var3 == null) {
					var7[var1[var6]] = null;
				}
				else {
					int[] var2 = var3.getIndices();
					switch (this.getType(var3)) {
					case 7:
					case 8:
						var3.setIndices(new int[] { var1[var2[0]] });
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						var3.setIndices(
								new int[] { var1[var2[0]], var1[var2[1]] });
					}

					var7[var1[var6]] = var3;
				}
			}
		}

		this.d_numConstants -= var4;
		this.d_constants = var7;
		this.d_hashN2C = new HashMap();
		this.d_hashC2i = new HashMap();

		for (var6 = 1; var6 < this.d_numConstants; ++var6) {
			if (this.d_constants[var6] != null) {
				this.d_hashN2C.put(
						this.getType(var6)
								+ this.d_constants[var6].getAsString(),
						this.d_constants[var6]);
				this.d_hashC2i.put(this.d_constants[var6],
						Integer.valueOf(var6));
			}
		}

	}

	public String toString() {
		StringBuffer var1 = new StringBuffer("CONSTANT POOL:\n");

		for (int var2 = 1; var2 < this.d_numConstants; ++var2) {
			ConstantPoolEntry var3 = this.d_constants[var2];
			if (var3 != null) {
				var1.append("  " + var2 + ": " + var3.toString() + "\n");
			}
		}

		return var1.toString();
	}

	public BitSet uses() {
		BitSet var1 = new BitSet(this.d_numConstants);
		var1.set(0);

		for (int var2 = 1; var2 < this.d_numConstants; ++var2) {
			ConstantPoolEntry var3 = this.get(var2);
			if (var3 != null) {
				int[] var4 = var3.getIndices();
				if (var4 != null) {
					for (int var5 = 0; var5 < var4.length; ++var5) {
						var1.set(var4[var5]);
					}
				}
			}
			else {
				var1.set(var2);
			}
		}

		return var1;
	}

	public void write(DataOutputStream var1) throws IOException {
		var1.writeShort(this.d_numConstants);

		for (int var2 = 1; var2 < this.d_numConstants; ++var2) {
			ConstantPoolEntry var3 = this.d_constants[var2];
			if (var3 != null) {
				var3.write(var1);
			}
		}

	}
}

/**
 * Duplicate of class from CFParse library to
 * fix some problems with initialisers when 
 * adding fields.
 */
package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.attributes.ConstantValueAttrInfo;
import com.ibm.toad.cfparse.instruction.BaseInstruction;
import com.ibm.toad.cfparse.instruction.InstructionFormatException;
import com.ibm.toad.cfparse.instruction.MutableCodeSegment;
import com.ibm.toad.cfparse.instruction.StringInstructionFactory;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.cfparse.utils.BadJavaError;
import com.ibm.toad.cfparse.utils.CPUtils;

public final class FieldInfo {
	private final ConstantPool d_cp;
	private int d_accessFlags;
	private int d_idxName;
	private int d_idxDescriptor;
	private AttrInfoList d_attrs;
	FieldInfo(
		final ClassFile classfile,
		final ConstantPool constantpool,
		String s) {

		this.d_cp = constantpool;
		s = s.trim();
		this.d_accessFlags = Access.getFromString(s);
		if (!Access.isStatic(this.d_accessFlags)) {
			throw new BadJavaError(
				"Incorrect method for non-static initialiser");
		}
		int i;
		int j;
		for (i = 0; (j = s.indexOf(" ", i)) != -1; i = j + 1) {
			if (!Access.isFlag(s.substring(i, j).trim())) {
				break;
			}
		}
		s = s.substring(i, s.length());
		final int k = s.indexOf(" ");
		if (k == -1) {
			throw new BadJavaError("Bad Field Description : no whitespace");
		}
		final String s1 = s.substring(0, k).trim();
		int l = 0;
		final StringBuffer stringbuffer = new StringBuffer();
		final String s2 = s.substring(k + 1).trim();
		if (!Character.isJavaIdentifierStart(s2.charAt(0))) {
			throw new BadJavaError(
				"Bad Field Description : Invalid field identifier");
		}
		stringbuffer.append(s2.charAt(0));
		for (int i1 = 1; i1 < s2.length(); i1++) {
			if (Character.isJavaIdentifierPart(s2.charAt(i1))) {
				stringbuffer.append(s2.charAt(i1));
				continue;
			}
			l = i1;
			break;
		}
		if (l == 0) {
			throw new BadJavaError(
				"Bad Field Description : Static definition with no initialiser");
		}
		final String s3 = stringbuffer.toString();
		if (s3.equals("")) {
			throw new BadJavaError("Bad Field Description : no identifier");
		}
		final String s4 = CPUtils.java2internal(s1);
		this.d_idxName = constantpool.find(1, s3);
		if (this.d_idxName == -1) {
			this.d_idxName = constantpool.addUtf8(s3);
		}
		this.d_idxDescriptor = constantpool.find(1, s4);
		if (this.d_idxDescriptor == -1) {
			this.d_idxDescriptor = constantpool.addUtf8(s4);
		}
		this.d_attrs = new AttrInfoList(constantpool, 2);
		String s5 = s2.substring(l).trim();
		if (s5.charAt(0) != '=') {
			throw new BadJavaError(
				"Bad Field Description: Incorrect initializer");
		}
		s5 = s5.substring(1).trim();
		if (Access.isFinal(this.d_accessFlags)) {
			final ConstantValueAttrInfo constantvalueattrinfo =
				(ConstantValueAttrInfo) this.d_attrs.add("ConstantValue");
			if (s4.equals("B")) {
				final byte byte0 = Byte.parseByte(s5);
				constantvalueattrinfo.set(byte0);
			}
			else if (s4.equals("C")) {
				if (s5.charAt(0) != '\'') {
					throw new BadJavaError(
						"Bad Field Description: unquoted character");
				}
				constantvalueattrinfo.set(s5.charAt(1));
			}
			else if (s4.equals("D")) {
				final double d = Double.parseDouble(s5);
				constantvalueattrinfo.set(d);
			}
			else if (s4.equals("F")) {
				final float f = Float.parseFloat(s5);
				constantvalueattrinfo.set(f);
			}
			else if (s4.equals("I")) {
				final int j1 = Integer.parseInt(s5);
				constantvalueattrinfo.set(j1);
			}
			else if (s4.equals("J")) {
				final long l1 = Long.parseLong(s5);
				constantvalueattrinfo.set(l1);
			}
			else if (s4.equals("S")) {
				final short word0 = Short.parseShort(s5);
				constantvalueattrinfo.set(word0);
			}
			else if (s4.equals("Z")) {
				final boolean flag = Boolean.valueOf(s5).booleanValue();
				constantvalueattrinfo.set(flag ? 1 : 0);
			}
			else if (s4.equals("Ljava/lang/String;")) {
				if (!s5.equals("null")) {
					if (s5.charAt(0) != '"'
							|| s5.charAt(s5.length() - 1) != '"') {
						throw new BadJavaError(
							"Bad Field Description: unquoted String");
					}
					constantvalueattrinfo.set(s5.substring(1, s5.length() - 1));
				}
			}
			else if (s4.charAt(0) == 'L') {
				//    BaseInstruction baseinstruction8 = mutablecodesegment.create("ldc " + s5);
				//    vector.addElement(baseinstruction8);
				//    baseinstruction8 = mutablecodesegment.create("putstatic java.lang.String " + classfile.getName() + "." + s3);
				//    vector.addElement(baseinstruction8);
			}
			else if (s4.charAt(0) == '[') {
				//    BaseInstruction baseinstruction8 = mutablecodesegment.create("ldc " + s5);
				//    vector.addElement(baseinstruction8);
				//    baseinstruction8 = mutablecodesegment.create("putstatic java.lang.String " + classfile.getName() + "." + s3);
				//    vector.addElement(baseinstruction8);
			}
			else {
				throw new BadJavaError(
					"Cannot initialise final variable of type " + s1);
			}
		}
		else {
			final MethodInfoList methodinfolist = classfile.getMethods();
			MethodInfo methodinfo = null;
			for (int k1 = 0; k1 < methodinfolist.length(); k1++) {
				methodinfo = methodinfolist.get(k1);
				if (methodinfo.getName().equals("<clinit>")) {
					break;
				}
			}
			if (methodinfo == null) {
				methodinfo = methodinfolist.add("static void <clinit>()");
			}
			final CodeAttrInfo codeattrinfo =
				(CodeAttrInfo) methodinfo.getAttrs().get("Code");
			final MutableCodeSegment mutablecodesegment =
				new MutableCodeSegment(this.d_cp, codeattrinfo, false);
			mutablecodesegment
				.setInstructionFactory(new StringInstructionFactory());
			@SuppressWarnings("unchecked")
			final Vector<BaseInstruction> vector = mutablecodesegment.getInstructions();

			if (vector.lastElement().toString().trim().equals("return")) {
				vector.removeElement(vector.lastElement());
			}

			try {
				if (s4.equals("B")) {
					final byte byte1 = Byte.parseByte(s5);
					BaseInstruction baseinstruction =
						mutablecodesegment.create("bipush #" + byte1);
					vector.addElement(baseinstruction);
					baseinstruction =
						mutablecodesegment.create("putstatic byte "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction);
				}
				else if (s4.equals("C")) {
					if (s5.charAt(0) != '\'') {
						throw new BadJavaError(
							"Bad Field Description: unquoted character");
					}
					BaseInstruction baseinstruction1 =
						mutablecodesegment.create("bipush #" + s5.charAt(0));
					vector.addElement(baseinstruction1);
					baseinstruction1 =
						mutablecodesegment.create("putstatic char "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction1);
				}
				else if (s4.equals("D")) {
					final double d1 = Double.parseDouble(s5);
					BaseInstruction baseinstruction2 =
						mutablecodesegment.create("ldc2_w D" + d1);
					vector.addElement(baseinstruction2);
					baseinstruction2 =
						mutablecodesegment.create("putstatic double "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction2);
				}
				else if (s4.equals("F")) {
					final float f1 = Float.parseFloat(s5);
					BaseInstruction baseinstruction3 =
						mutablecodesegment.create("ldc F" + f1);
					vector.addElement(baseinstruction3);
					baseinstruction3 =
						mutablecodesegment.create("putstatic float "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction3);
				}
				else if (s4.equals("I")) {
					final int i2 = Integer.parseInt(s5);
					BaseInstruction baseinstruction4 =
						mutablecodesegment.create("bipush #" + i2);
					vector.addElement(baseinstruction4);
					baseinstruction4 =
						mutablecodesegment.create("putstatic int "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction4);
				}
				else if (s4.equals("J")) {
					final long l2 = Long.parseLong(s5);
					BaseInstruction baseinstruction5 =
						mutablecodesegment.create("ldc2_w L" + l2);
					vector.addElement(baseinstruction5);
					baseinstruction5 =
						mutablecodesegment.create("putstatic long "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction5);
				}
				else if (s4.equals("S")) {
					final short word1 = Short.parseShort(s5);
					BaseInstruction baseinstruction6 =
						mutablecodesegment.create("bipush #" + word1);
					vector.addElement(baseinstruction6);
					baseinstruction6 =
						mutablecodesegment.create("putstatic short "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction6);
				}
				else if (s4.equals("Z")) {
					final boolean flag1 = Boolean.valueOf(s5).booleanValue();
					BaseInstruction baseinstruction7 =
						mutablecodesegment.create(flag1 ? "iconst_1"
								: "iconst_0");
					vector.addElement(baseinstruction7);
					baseinstruction7 =
						mutablecodesegment.create("putstatic boolean "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction7);
				}
				else if (s4.equals("Ljava/lang/String;")) {
					BaseInstruction baseinstruction8 =
						mutablecodesegment.create("ldc " + s5);
					vector.addElement(baseinstruction8);
					baseinstruction8 =
						mutablecodesegment.create("putstatic java.lang.String "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction8);
				}
				else if (s4.charAt(0) == 'L') {
					BaseInstruction baseinstruction8 =
						mutablecodesegment.create("aconst_null");
					vector.addElement(baseinstruction8);
					baseinstruction8 =
						mutablecodesegment.create("putstatic " + s1 + " "
								+ classfile.getName() + "." + s3);
					vector.addElement(baseinstruction8);
				}
				else if (s4.charAt(0) == '[') {
					//    BaseInstruction baseinstruction8 = mutablecodesegment.create("ldc " + s5);
					//    vector.addElement(baseinstruction8);
					//    baseinstruction8 = mutablecodesegment.create("putstatic java.lang.String " + classfile.getName() + "." + s3);
					//    vector.addElement(baseinstruction8);
				}
				else {
					throw new BadJavaError(
						"Cannot initialise variable of type " + s1);
				}
				vector.addElement(mutablecodesegment.create("return"));
			}
			catch (final NumberFormatException _ex) {
				throw new BadJavaError("Unparsed initialiser <" + s5 + ">");
			}
			catch (final InstructionFormatException instructionformatexception) {
				throw new BadJavaError(
					"Bad Instruction in static initialiser <"
							+ instructionformatexception + ">");
			}
			codeattrinfo.setCode(mutablecodesegment.getCode());
			codeattrinfo.setExceptions(mutablecodesegment.getExcTable());
		}
	}
	FieldInfo(final ConstantPool constantpool) {
		this.d_cp = constantpool;
		this.d_accessFlags = -1;
		this.d_idxName = -1;
		this.d_idxDescriptor = -1;
		this.d_attrs = new AttrInfoList(constantpool, 2);
	}
	FieldInfo(final ConstantPool constantpool, String s) {
		this.d_cp = constantpool;
		s = s.trim();
		this.d_accessFlags = Access.getFromString(s);
		int i;
		int j;
		for (i = 0; (j = s.indexOf(" ", i)) != -1; i = j + 1) {
			if (!Access.isFlag(s.substring(i, j).trim())) {
				break;
			}
		}
		s = s.substring(i, s.length());
		final int k = s.indexOf(" ");
		if (k == -1) {
			throw new BadJavaError("Bad Field Description");
		}
		final String s1 = s.substring(0, k).trim();
		int l = 0;
		final StringBuffer stringbuffer = new StringBuffer();
		final String s2 = s.substring(k + 1).trim();
		if (!Character.isJavaIdentifierStart(s2.charAt(0))) {
			throw new BadJavaError("Invalid field identifier");
		}
		for (int i1 = 1; i1 < s2.length(); i1++) {
			if (Character.isJavaIdentifierPart(s2.charAt(i1))) {
				stringbuffer.append(s2.charAt(i1));
				continue;
			}
			l = i1;
			break;
		}
		if (Access.isStatic(this.d_accessFlags) && l > 0) {
			throw new BadJavaError("Incorrect method for static initialiser");
		}
		final String s3 = stringbuffer.toString();
		if (s3.equals("")) {
			throw new BadJavaError("Bad Field Description");
		}
		final String s4 = CPUtils.java2internal(s1);
		this.d_idxName = constantpool.find(1, s3);
		if (this.d_idxName == -1) {
			this.d_idxName = constantpool.addUtf8(s3);
		}
		this.d_idxDescriptor = constantpool.find(1, s4);
		if (this.d_idxDescriptor == -1) {
			this.d_idxDescriptor = constantpool.addUtf8(s4);
		}
		this.d_attrs = new AttrInfoList(constantpool, 2);
	}
	public int getAccess() {
		return this.d_accessFlags;
	}
	public AttrInfoList getAttrs() {
		return this.d_attrs;
	}
	public String getDesc() {
		return this.d_cp.getAsString(this.d_idxDescriptor);
	}
	public String getName() {
		return this.d_cp.getAsString(this.d_idxName);
	}
	public String getType() {
		return CPUtils.internal2java(this.d_cp
			.getAsString(this.d_idxDescriptor));
	}
	void read(final DataInputStream datainputstream) throws IOException {
		this.d_accessFlags = datainputstream.readShort();
		this.d_idxName = datainputstream.readShort();
		this.d_idxDescriptor = datainputstream.readShort();
		this.d_attrs.read(datainputstream);
	}
	public void setAccess(final int i) {
		this.d_accessFlags = i;
	}
	public void setAttrs(final AttrInfoList attrinfolist) {
		this.d_attrs = attrinfolist;
	}
	public void setName(final String s) {
		((ConstantPool.Utf8Entry) this.d_cp.get(this.d_idxName)).setValue(s);
	}
	public void setType(final String s) {
		final String s1 = CPUtils.java2internal(s);
		((ConstantPool.Utf8Entry) this.d_cp.get(this.d_idxDescriptor))
			.setValue(s1);
	}
	void sort(final int ai[]) {
		this.d_idxName = ai[this.d_idxName];
		this.d_idxDescriptor = ai[this.d_idxDescriptor];
		this.d_attrs.sort(ai);
	}
	public String toString() {
		final StringBuffer stringbuffer = new StringBuffer();
		if (Access.isPublic(this.d_accessFlags)) {
			stringbuffer.append("public ");
		}
		else if (Access.isPrivate(this.d_accessFlags)) {
			stringbuffer.append("private ");
		}
		else if (Access.isProtected(this.d_accessFlags)) {
			stringbuffer.append("protected ");
		}
		else if (Access.isFinal(this.d_accessFlags)) {
			stringbuffer.append("final ");
		}
		else if (Access.isStatic(this.d_accessFlags)) {
			stringbuffer.append("static ");
		}
		else if (Access.isVolatile(this.d_accessFlags)) {
			stringbuffer.append("volatile ");
		}
		else if (Access.isTransient(this.d_accessFlags)) {
			stringbuffer.append("transient ");
		}

		final String s = this.d_cp.getAsString(this.d_idxDescriptor);
		final String s1 = CPUtils.internal2java(s);
		stringbuffer.append(s1);
		stringbuffer.append(' ');
		stringbuffer.append(this.d_cp.getAsString(this.d_idxName));
		stringbuffer.append(";\n");
		stringbuffer.append(this.d_attrs);

		return stringbuffer.toString();
	}
	BitSet uses() {
		final BitSet bitset = new BitSet(this.d_cp.length());
		bitset.set(this.d_idxName);
		bitset.set(this.d_idxDescriptor);
		bitset.or(this.d_attrs.uses());
		return bitset;
	}
	void write(final DataOutputStream dataoutputstream) throws IOException {
		dataoutputstream.writeShort(this.d_accessFlags);
		dataoutputstream.writeShort(this.d_idxName);
		dataoutputstream.writeShort(this.d_idxDescriptor);
		this.d_attrs.write(dataoutputstream);
	}
}

/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: C:\Documents and Settings\Yann\Workspaces 2\Ptidej\CPL\cfparse.jar


	TOTAL TIME: 120 ms


	JAD REPORTED MESSAGES/ERRORS:

Method toString
Method <init>
Method <init>
Method <init>
Method read
Method getAccess
Method sort
Method setAccess
Method getDesc
Method write
Method getAttrs
Method setAttrs
Method uses
Method getName
Method getType
Method setName
Method setType

	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/

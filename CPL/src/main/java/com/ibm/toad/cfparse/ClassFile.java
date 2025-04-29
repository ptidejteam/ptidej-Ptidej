/* Decompiler 158ms, total 328ms, lines 234 */
package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.BitSet;

import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.SourceFileAttrInfo;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.cfparse.utils.CPUtils;

public final class ClassFile {
	private int d_magic;
	private int d_minorVersion;
	private int d_majorVersion;
	private int d_accessFlags;
	private int d_thisClass;
	private int d_superClass;
	private ConstantPool d_constants;
	private InterfaceList d_interfaces;
	public FieldInfoList d_fields;
	private MethodInfoList d_methods;
	private AttrInfoList d_attributes;

	public InterfaceList getInterfaces() {
		return this.d_interfaces;
	}

	public String toString() {
		StringBuffer var1 = new StringBuffer(
				"Magic: 0x" + Integer.toHexString(this.d_magic) + "\n"
						+ "Compiler Version: " + this.d_majorVersion + "."
						+ this.d_minorVersion + "\n");
		var1.append(this.d_constants.toString() + "Class: \n"
				+ Access.getClassAsString(this.d_accessFlags));
		String var2 = this.d_constants.getAsString(this.d_thisClass);
		String var3 = CPUtils.slashes2dots(var2);
		var1.append(var3);
		if (this.d_superClass != 0) {
			var2 = this.d_constants.getAsString(this.d_superClass);
			var3 = CPUtils.slashes2dots(var2);
			var1.append(" extends " + var3);
		}

		var1.append("\n");
		var1.append("" + this.d_interfaces + "\n" + this.d_fields + "\n"
				+ this.d_methods + "\n" + this.d_attributes + "\n");
	
		return var1.toString();
	}

	public ClassFile() {
		this.d_constants = new ConstantPool();
		this.d_interfaces = new InterfaceList(this.d_constants);
		this.d_fields = new FieldInfoList(this.d_constants);
		this.d_methods = new MethodInfoList(this.d_constants);
		this.d_attributes = new AttrInfoList(this.d_constants, 1);
		this.d_magic = -889275714;
		this.d_minorVersion = 3;
		this.d_majorVersion = 45;
		this.d_accessFlags = 33;
		this.d_thisClass = this.d_constants.addClass("Default");
		this.d_superClass = this.d_constants.addClass("java.lang.Object");
	}

	public ClassFile(String var1) throws FileNotFoundException, IOException {
		this((InputStream) (new FileInputStream(var1)));
	}

	public ClassFile(InputStream var1) throws IOException {
		this(var1, (ConstantPool) null);
	}

	// Yann 24/12/07: Equality is hard!
	// I create this method to compare to instances of ClassFile,
	// and it's hard to compare them because they can be "same",
	// but not one-to-one matching...
	@Override
	public boolean equals(final Object o) {

		if (!(o instanceof ClassFile)) {
			return false;
		}

		final ClassFile other = (ClassFile) o;

		// Classfile itself
		boolean equalClassFiles = true;
		equalClassFiles &= this.getAccess() == other.getAccess();
		equalClassFiles &= this.getMagic() == other.getMagic();
		equalClassFiles &= this.getMajor() == other.getMajor();
		equalClassFiles &= this.getMinor() == other.getMinor();
		equalClassFiles &= this.getName().equals(other.getName());
		// A classfile may have no "SourceFile" attribute
		if (this.getSourceFilename() != null) {
			equalClassFiles &= this.getSourceFilename()
					.equals(other.getSourceFilename());
		}

		// Superclass
		boolean equalSuperNames = true;
		if (!this.getName().equals("java.lang.Object")) {
			// Interestingly, for the class Object itself, CFParse will
			// have "" as its superclass, while BCEL will have the string
			// "java.lang.Object", which shows a better understanding of
			// Java object model (?). So, I don't test if its Object.
			equalSuperNames &= this.getSuperName().equals(other.getSuperName());
		}

		// Superinterfaces
		boolean equalSuperInterfaces = true;
		final InterfaceList interfaceListOfThis = this.getInterfaces();
		final InterfaceList interfacesListOfOther = other.getInterfaces();
		final int numInterfacesOfThis = interfaceListOfThis.length();
		final int numInterfacesOfOther = interfacesListOfOther.length();
		equalSuperInterfaces &= numInterfacesOfThis == numInterfacesOfOther;

		for (int i = 0; equalSuperInterfaces && i < numInterfacesOfThis; i++) {
			final String interfaceOfThis = interfaceListOfThis.get(i);
			final String interfaceOfOther = interfacesListOfOther.get(i);

			equalSuperInterfaces &= interfaceOfThis.equals(interfaceOfOther);
		}

		// Constant pool
		boolean equalConstants = true;
		final ConstantPool cpOfThis = this.getCP();
		final ConstantPool cpOfOther = other.getCP();
		final int numCPEntriesOfThis = cpOfThis.length();
		final int numCPEntriesOfOther = cpOfOther.length();
		// BCEL-built classfiles may have slightly different constants than compiler-generated ones don't
		// 	equalConstants &= numCPEntriesOfThis <= numCPEntriesOfOther;

		// I skip the first constant pool entry because it's always "<dummy Entry>"
		for (int i = 1; equalConstants && i < numCPEntriesOfThis; i++) {
			final ConstantPoolEntry cpEntryOfThis = cpOfThis.get(i);
			if (cpEntryOfThis == null) {
				continue;
			}
			final int index = cpOfOther.find(cpEntryOfThis);
			if (index > 0) {
				final ConstantPoolEntry cpEntryOfOther = cpOfOther.get(index);
				equalConstants &= cpEntryOfThis.equals(cpEntryOfOther);
			}
		}

		// Attributes
		boolean equalAttributes = true;
		final AttrInfoList attrListOfThis = this.getAttrs();
		final AttrInfoList attrListOfOther = other.getAttrs();
		final int numAttrsOfThis = attrListOfThis.length();
		final int numAttrsOfOther = attrListOfOther.length();
		equalAttributes &= numAttrsOfThis == numAttrsOfOther;

		for (int i = 0; equalAttributes && i < numAttrsOfThis; i++) {
			final AttrInfo attrInfoOfThis = attrListOfThis.get(i);
			final AttrInfo attrInfoOfOther = attrListOfOther.get(i);

			// TODO Remove these conditions by implementing fully util.lang.CFParseBCELConvertor.addAttributes(ClassFile, JavaClass)
			
				equalAttributes &= attrInfoOfThis.toString()
					.equals(attrInfoOfOther.toString());
			
		}

		// Fields
		boolean equalFields = true;
		final FieldInfoList fieldListOfThis = this.getFields();
		final FieldInfoList fieldListOfOther = other.getFields();
		final int numFieldsOfThis = fieldListOfThis.length();
		final int numFieldsOfOther = fieldListOfOther.length();
		equalFields &= numFieldsOfThis == numFieldsOfOther;

		for (int i = 0; equalFields && i < numFieldsOfThis; i++) {
			final FieldInfo fieldOfThis = fieldListOfThis.get(i);
			final FieldInfo fieldOfOther = fieldListOfOther.get(i);

			equalFields &= fieldOfThis.getAccess() == fieldOfOther.getAccess();
			equalFields &= fieldOfThis.getDesc().equals(fieldOfOther.getDesc());
			equalFields &= fieldOfThis.getName().equals(fieldOfOther.getName());
			equalFields &= fieldOfThis.getType().equals(fieldOfOther.getType());
		}

		// Methods
		boolean equalMethods = true;
		final MethodInfoList methodListOfThis = this.getMethods();
		final MethodInfoList methodListOfOther = other.getMethods();
		final int numMethodOfThis = methodListOfThis.length();
		final int numMethodOfOther = methodListOfOther.length();
		// BCEL-built classfiles may have a <clinit> than compiler-generated ones don't
		equalMethods &= numMethodOfThis <= numMethodOfOther;

		for (int i = 0; equalMethods && i < numMethodOfThis; i++) {
			final MethodInfo methodOfThis = methodListOfThis.get(i);
			final MethodInfo methodOfOther = methodListOfOther.get(i);

			// TODO Re-enable this test and fix the difference between CFParse and BCEL (BCEL has sometimes longer bytecode sizes)  
			// 	equalMethods &= methodOfThis.getAbout().equals(methodOfOther.getAbout());
			equalMethods &= methodOfThis.getAccess() == methodOfOther
					.getAccess();
			equalMethods &= methodOfThis.getDesc()
					.equals(methodOfOther.getDesc());
			equalMethods &= methodOfThis.getName()
					.equals(methodOfOther.getName());
			equalMethods &= Arrays.equals(methodOfThis.getParams(),
					methodOfOther.getParams());
			equalMethods &= methodOfThis.getReturnType()
					.equals(methodOfOther.getReturnType());
		}

		final boolean equal = equalClassFiles && equalSuperNames
				&& equalSuperInterfaces && equalConstants && equalAttributes
				&& equalFields && equalMethods;
		return equal;
	}

	public ClassFile(InputStream var1, ConstantPool var2) throws IOException {
		DataInputStream var3 = new DataInputStream(var1);
		this.d_constants = var2 == null ? new ConstantPool() : var2;
		this.d_interfaces = new InterfaceList(this.d_constants);
		this.d_fields = new FieldInfoList(this.d_constants);
		this.d_methods = new MethodInfoList(this.d_constants);
		this.d_attributes = new AttrInfoList(this.d_constants, 1);
		this.d_magic = var3.readInt();
		this.d_minorVersion = var3.readShort();
		this.d_majorVersion = var3.readShort();
		if (var2 == null) {
			this.d_constants.read(var3);
		}
		else {
			ConstantPool var4 = new ConstantPool();
			var4.read(var3);
		}

		this.d_accessFlags = var3.readShort();
		this.d_thisClass = var3.readShort();
		this.d_superClass = var3.readShort();
		this.d_interfaces.read(var3);
		this.d_fields.read(var3);
		this.d_methods.read(var3);
		this.d_attributes.read(var3);
	}

	public void sortCF(int[] var1) {
		this.d_thisClass = var1[this.d_thisClass];
		this.d_superClass = var1[this.d_superClass];
	}

	public int getAccess() {
		return this.d_accessFlags;
	}

	public String getSuperName() {
		return this.d_superClass == 0 ? ""
				: this.d_constants.getAsJava(this.d_superClass);
	}

	public void setSuperName(String var1) {
		var1 = var1.replace('.', '/');
		this.d_constants.editClass(this.d_superClass, var1);
	}

	public void setAccess(int var1) {
		this.d_accessFlags = var1;
	}

	public void sort(int[] var1) {
		this.d_constants.sort(var1);
		this.d_thisClass = var1[this.d_thisClass];
		this.d_superClass = var1[this.d_superClass];
		this.d_interfaces.sort(var1);
		this.d_fields.sort(var1);
		this.d_methods.sort(var1);
		this.d_attributes.sort(var1);
	}

	public int getMagic() {
		return this.d_magic;
	}

	public void setMagic(int var1) {
		this.d_magic = var1;
	}

	public MethodInfoList getMethods() {
		return this.d_methods;
	}

	public FieldInfoList getFields() {
		return this.d_fields;
	}

	public void write(String var1) throws FileNotFoundException, IOException {
		File var2 = new File(var1);
		File var3 = var2.getParentFile();
		if (var3 != null) {
			var3.mkdirs();
		}

		var2.createNewFile();
		DataOutputStream var4 = new DataOutputStream(
				new FileOutputStream(var2));
		this.write(var4);
	}

	public AttrInfoList getAttrs() {
		
		return this.d_attributes;
	}

	public void write(DataOutputStream var1) throws IOException {
		var1.writeInt(this.d_magic);
		var1.writeShort(this.d_minorVersion);
		var1.writeShort(this.d_majorVersion);
		this.d_constants.write(var1);
		var1.writeShort(this.d_accessFlags);
		var1.writeShort(this.d_thisClass);
		var1.writeShort(this.d_superClass);
		this.d_interfaces.write(var1);
		this.d_fields.write(var1);
		this.d_methods.write(var1);
		this.d_attributes.write(var1);
	}

	public BitSet usesCF() {
		BitSet var1 = new BitSet(this.d_constants.length());
		var1.set(this.d_thisClass);
		var1.set(this.d_superClass);
		return var1;
	}

	public int getMajor() {
		return this.d_majorVersion;
	}

	public void setMajor(int var1) {
		this.d_majorVersion = var1;
	}

	public BitSet uses() {
		BitSet var1 = new BitSet(this.d_constants.length());
		var1.set(this.d_thisClass);
		var1.set(this.d_superClass);
		var1.or(this.d_interfaces.uses());
		var1.or(this.d_constants.uses());
		var1.or(this.d_methods.uses());
		var1.or(this.d_fields.uses());
		var1.or(this.d_attributes.uses());
		return var1;
	}

	public String getName() {
		return this.d_constants.getAsJava(this.d_thisClass);
	}

	public void setName(String var1) {
		var1 = var1.replace('.', '/');
		this.d_constants.editClass(this.d_thisClass, var1);
	}

	public int getMinor() {
		return this.d_minorVersion;
	}

	public void setMinor(int var1) {
		this.d_minorVersion = var1;
	}

	public ConstantPool getCP() {
		return this.d_constants;
	}

	public void setCP(ConstantPool var1) {
		this.d_constants = var1;
	}

	public String getSourceFilename() {
		SourceFileAttrInfo var1 = (SourceFileAttrInfo) this.d_attributes
				.get("SourceFile");
		return var1 == null ? null : var1.get();
	}
}

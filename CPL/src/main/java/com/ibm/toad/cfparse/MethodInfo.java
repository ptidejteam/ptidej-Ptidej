/* Decompiler 217ms, total 3485ms, lines 261 */
package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

import com.ibm.toad.cfparse.ConstantPool.Utf8Entry;
import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.utils.Access;
import com.ibm.toad.cfparse.utils.BadJavaError;
import com.ibm.toad.cfparse.utils.CPUtils;

public final class MethodInfo {
	private ConstantPool d_cp;
	private int d_accessFlags;
	private int d_idxName;
	private int d_idxDescriptor;
	private AttrInfoList d_attrs;

	public void setReturnType(String var1) {
		String var2 = this.d_cp.getAsString(this.d_idxDescriptor);
		String var3 = var2.substring(0, var2.lastIndexOf(")"));
		StringBuffer var4 = new StringBuffer(var3);
		var4.append(CPUtils.java2internal(var1));
		((Utf8Entry) this.d_cp.get(this.d_idxDescriptor))
				.setValue(var4.toString());
	}

	public String getAbout() {
		if (this.d_attrs == null) {
			return "<unavailable>";
		}
		else {
			AttrInfo var1 = this.d_attrs.get("Code");
			if (var1 == null) {
				return "<unavailable>";
			}
			else {
				CodeAttrInfo var2 = (CodeAttrInfo) var1;
				return "max_stack: " + var2.getMaxStack() + " max_locals: "
						+ var2.getMaxLocals() + " #bytes "
						+ (var2.getCode() == null ? 0 : var2.getCode().length);
			}
		}
	}

	public String toString() {
		StringBuffer var1 = new StringBuffer();
		if (Access.isPublic(this.d_accessFlags)) {
			var1.append("public ");
		}

		if (Access.isPrivate(this.d_accessFlags)) {
			var1.append("private ");
		}

		if (Access.isProtected(this.d_accessFlags)) {
			var1.append("protected ");
		}

		if (Access.isFinal(this.d_accessFlags)) {
			var1.append("final ");
		}

		if (Access.isStatic(this.d_accessFlags)) {
			var1.append("static ");
		}

		if (Access.isVolatile(this.d_accessFlags)) {
			var1.append("volatile ");
		}

		if (Access.isTransient(this.d_accessFlags)) {
			var1.append("transient ");
		}

		if (Access.isSynchronized(this.d_accessFlags)) {
			var1.append("synchronized ");
		}

		if (Access.isNative(this.d_accessFlags)) {
			var1.append("native ");
		}

		if (Access.isAbstract(this.d_accessFlags)) {
			var1.append("abstract ");
		}

		String var2 = this.d_cp.getAsString(this.d_idxDescriptor);
		String var3 = CPUtils.method2java(var2);
		String var4 = "";
		int var6 = var3.indexOf(" ");
		String var5;
		if (var6 == -1) {
			var5 = var3;
		}
		else {
			var4 = var3.substring(0, var6);
			var5 = var3.substring(var6 + 1);
		}

		var1.append(var4 + " " + this.d_cp.getAsString(this.d_idxName) + var5
				+ "\n" + this.d_attrs);
		return var1.toString();
	}

	MethodInfo(ConstantPool var1) {
		this.d_cp = var1;
		this.d_accessFlags = -1;
		this.d_idxName = -1;
		this.d_idxDescriptor = -1;
		this.d_attrs = new AttrInfoList(var1, 2);
	}

	MethodInfo(ConstantPool var1, String var2) {
		this.d_cp = var1;
		this.d_accessFlags = Access.getFromString(var2);

		int var3;
		int var4;
		for (var3 = 0; (var4 = var2.indexOf(" ", var3)) != -1 && Access
				.isFlag(var2.substring(var3, var4).trim()); var3 = var4 + 1) {
		}

		String var5 = var2;
		var2 = CPUtils.java2method(var5.substring(var3));
		if (var2 == null) {
			throw new BadJavaError("Unparsable Method " + var5);
		}
		else {
			String var6 = var2.substring(0, var2.indexOf(" ")).trim();
			String var7 = var2.substring(var2.indexOf(" ")).trim();
			this.d_idxName = var1.find(1, var6);
			if (this.d_idxName == -1) {
				this.d_idxName = var1.addUtf8(var6);
			}

			this.d_idxDescriptor = var1.find(1, var7);
			if (this.d_idxDescriptor == -1) {
				this.d_idxDescriptor = var1.addUtf8(var7);
			}

			this.d_attrs = new AttrInfoList(var1, 2);
			if (!Access.isAbstract(this.d_accessFlags)
					&& !Access.isNative(this.d_accessFlags)) {
				this.d_attrs.add("Code");
			}

		}
	}

	void read(DataInputStream var1) throws IOException {
		this.d_accessFlags = var1.readShort();
		this.d_idxName = var1.readShort();
		this.d_idxDescriptor = var1.readShort();
		this.d_attrs.read(var1);
	}

	public int getAccess() {
		return this.d_accessFlags;
	}

	void sort(int[] var1) {
		this.d_idxName = var1[this.d_idxName];
		this.d_idxDescriptor = var1[this.d_idxDescriptor];
		this.d_attrs.sort(var1);
	}

	public void setAccess(int var1) {
		this.d_accessFlags = var1;
	}

	public String getDesc() {
		return this.d_cp.getAsString(this.d_idxDescriptor);
	}

	public void setDesc(String var1) {
		((Utf8Entry) this.d_cp.get(this.d_idxDescriptor)).setValue(var1);
	}

	void write(DataOutputStream var1) throws IOException {
		var1.writeShort(this.d_accessFlags);
		var1.writeShort(this.d_idxName);
		var1.writeShort(this.d_idxDescriptor);
		this.d_attrs.write(var1);
	}

	public AttrInfoList getAttrs() {
		return this.d_attrs;
	}

	public void setAttrs(AttrInfoList var1) {
		this.d_attrs = var1;
	}

	BitSet uses() {
		BitSet var1 = new BitSet(this.d_cp.length());
		var1.set(this.d_idxName);
		var1.set(this.d_idxDescriptor);
		var1.or(this.d_attrs.uses());
		return var1;
	}

	public String getName() {
		return this.d_cp.getAsString(this.d_idxName);
	}

	public void setName(String var1) {
		((Utf8Entry) this.d_cp.get(this.d_idxName)).setValue(var1);
	}

	public String[] getParams() {
		String var1 = this.d_cp.getAsString(this.d_idxDescriptor);
		String var2 = CPUtils.method2java(var1);
		int var3 = var2.indexOf(" ");
		String var4;
		if (var3 == -1) {
			var4 = var2;
		}
		else {
			var4 = var2.substring(var3 + 1);
		}

		Vector var5 = new Vector();
		if (var4.charAt(0) != '(') {
			System.out.println("Bad parameter String");
		}

		int var6 = var4.indexOf(")");
		int var7 = 1;
		if (var7 == var6) {
			return new String[0];
		}
		else {
			while (true) {
				int var8 = var4.indexOf(",", var7);
				if (var8 == -1) {
					var5.addElement(var4.substring(var7, var6).trim());
					String[] var9 = new String[var5.size()];
					var5.copyInto(var9);
					return var9;
				}

				var5.addElement(var4.substring(var7, var8).trim());
				var7 = var8 + 1;
			}
		}
	}

	public void setParams(String[] var1) {
		StringBuffer var2 = new StringBuffer("(");

		for (int var3 = 0; var3 < var1.length; ++var3) {
			var2.append(CPUtils.java2internal(var1[var3]));
		}

		var2.append(")");
		String var4 = this.d_cp.getAsString(this.d_idxDescriptor);
		var2.append(var4.substring(var4.lastIndexOf(")") + 1));
		((Utf8Entry) this.d_cp.get(this.d_idxDescriptor))
				.setValue(var2.toString());
	}

	public String getReturnType() {
		String var1 = this.d_cp.getAsString(this.d_idxDescriptor);
		String var2 = CPUtils.method2java(var1);
		int var3 = var2.indexOf(" ");
		return var3 == -1 ? "" : var2.substring(0, var3);
	}
}

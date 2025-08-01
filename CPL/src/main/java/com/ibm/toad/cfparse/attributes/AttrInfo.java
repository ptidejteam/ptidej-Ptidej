/* Decompiler 42ms, total 383ms, lines 61 */
package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;

public abstract class AttrInfo implements Comparable<AttrInfo> {
	protected ConstantPool d_cp;
	protected int d_depth;
	protected int d_idxName;
	protected int d_len;

	public String toString() {
		return this.sindent() + "Attribute: \nname: "
				+ this.d_cp.getAsString(this.d_idxName);
	}

	protected AttrInfo(ConstantPool var1, int var2, int var3) {
		this.d_cp = var1;
		this.d_depth = var3;
		this.d_idxName = var2;
	}

	protected String sindent() {
		String var1 = "";

		for (int var2 = 0; var2 < this.d_depth; ++var2) {
			var1 = var1 + "  ";
		}

		return var1;
	}

	protected abstract void read(DataInputStream var1) throws IOException;

	protected void sort(int[] var1) {
		this.d_idxName = var1[this.d_idxName];
	}

	protected int size() {
		return 6 + this.d_len;
	}

	protected abstract void write(DataOutputStream var1) throws IOException;

	public AttrInfoList getAttrs() {
		return null;
	}

	protected BitSet uses() {
		BitSet var1 = new BitSet(this.d_cp.length());
		var1.set(this.d_idxName);
		return var1;
	}

	public String getName() {
		return this.d_cp.getAsString(this.d_idxName);
	}

	@Override
	public int compareTo(final AttrInfo o) {
		return this.getClass().getName().compareTo(o.getClass().getName());
	}
}

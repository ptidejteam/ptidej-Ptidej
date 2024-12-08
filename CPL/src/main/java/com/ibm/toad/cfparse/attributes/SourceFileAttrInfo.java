/* Decompiler 39ms, total 177ms, lines 52 */
package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;

public final class SourceFileAttrInfo extends AttrInfo {
	private int d_idxCP;

	public void set(String var1) {
		this.d_idxCP = super.d_cp.addUtf8(var1);
	}

	public String get() {
		return super.d_cp.getAsString(this.d_idxCP);
	}

	public String toString() {
		return this.sindent() + "Attribute: "
				+ super.d_cp.getAsString(super.d_idxName) + ": "
				+ super.d_cp.getAsString(this.d_idxCP) + "\n";
	}

	SourceFileAttrInfo(ConstantPool var1, int var2, int var3) {
		super(var1, var2, var3);
	}

	// Yann: changed from protected to public
	public void read(DataInputStream var1) throws IOException {
		super.d_len = var1.readInt();
		// D.assert(super.d_len == 2, "d_len != 2 : " + super.d_len);
		this.d_idxCP = var1.readShort();
	}

	protected void sort(int[] var1) {
		super.sort(var1);
		this.d_idxCP = var1[this.d_idxCP];
	}

	protected void write(DataOutputStream var1) throws IOException {
		var1.writeShort(super.d_idxName);
		var1.writeInt(2);
		var1.writeShort(this.d_idxCP);
	}

	protected BitSet uses() {
		BitSet var1 = super.uses();
		var1.set(this.d_idxCP);
		return var1;
	}
}

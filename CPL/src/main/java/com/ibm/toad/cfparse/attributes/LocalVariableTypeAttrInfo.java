/**
 * Duplicate of class from CFParse library to
 * allow public access to method read().
 */
package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.utils.CPUtils;

public final class LocalVariableTypeAttrInfo extends AttrInfo {
	private int d_numVars;
	private int d_varTable[];

	LocalVariableTypeAttrInfo(final ConstantPool constantpool, final int i,
			final int j) {
		super(constantpool, i, j);
	}

	public int getEndPC(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return -1;
		}
		else {
			final int j = this.d_varTable[5 * i];
			final int k = this.d_varTable[5 * i + 1];
			return j + k;
		}
	}
	public void add(int startPC, int length, int nameIdx, int descIdx, int slot) {
	    if (this.d_varTable == null) {
	        this.d_varTable = new int[5];
	        this.d_numVars = 1;
	    } else {
	        int[] newTable = new int[this.d_varTable.length + 5];
	        System.arraycopy(this.d_varTable, 0, newTable, 0, this.d_varTable.length);
	        this.d_varTable = newTable;
	        this.d_numVars++;
	    }

	    int base = (this.d_numVars - 1) * 5;
	    this.d_varTable[base + 0] = startPC;
	    this.d_varTable[base + 1] = length;
	    this.d_varTable[base + 2] = nameIdx;
	    this.d_varTable[base + 3] = descIdx;
	    this.d_varTable[base + 4] = slot;

	    this.d_len = 2 + this.d_numVars * 10;
	}

	public void setFromBCEL(org.apache.bcel.classfile.LocalVariableTypeTable bcelTable) {
	    if (bcelTable == null) {
	        return;
	    }

	    org.apache.bcel.classfile.LocalVariable[] vars = bcelTable.getLocalVariableTypeTable();
	    this.d_numVars = vars.length;
	    this.d_varTable = new int[this.d_numVars * 5]; // Each variable uses 5 fields!

	    for (int i = 0; i < this.d_numVars; i++) {
	        this.d_varTable[i * 5 + 0] = vars[i].getStartPC();
	        this.d_varTable[i * 5 + 1] = vars[i].getLength();
	        this.d_varTable[i * 5 + 2] = this.d_cp.addUtf8(vars[i].getName());
	        this.d_varTable[i * 5 + 3] = this.d_cp.addUtf8(vars[i].getSignature());
	        this.d_varTable[i * 5 + 4] = vars[i].getIndex();
	    }

	    this.d_len = 2 + this.d_numVars * 10;
	}

	public int getStartPC(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return -1;
		}
		else {
			final int j = this.d_varTable[5 * i];
			return j;
		}
	}

	public String getVarName(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return "";
		}
		else {
			final int j = this.d_varTable[5 * i + 2];
			return super.d_cp.getAsString(j);
		}
	}

	public int getVarNum(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return -1;
		}
		else {
			final int j = this.d_varTable[5 * i + 4];
			return j;
		}
	}

	public String getVarType(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return "";
		}
		else {
			final int j = this.d_varTable[5 * i + 3];
			return CPUtils.internal2java(super.d_cp.getAsString(j));
		}
	}

	public int length() {
		return this.d_numVars;
	}

	// Yann: changed from protected to public
	public void read(final DataInputStream datainputstream) throws IOException {
		super.d_len = datainputstream.readInt();
		this.d_numVars = datainputstream.readShort();
		//	D.assert(
		//		super.d_len == 2 + this.d_numVars * 10,
		//		"d_len != 2 + (d_numVars * 10)\n" + super.d_len + " != 2 + ("
		//				+ this.d_numVars + "* 10)\n");
		this.d_varTable = new int[this.d_numVars * 5];
		for (int i = 0; i < this.d_varTable.length; i++) {
			this.d_varTable[i] = datainputstream.readShort();
		}
	}

	protected void sort(final int ai[]) {
		super.sort(ai);
		for (int i = 0; i < this.d_varTable.length; i++) {
			if (i % 5 == 2 || i % 5 == 3) {
				this.d_varTable[i] = ai[this.d_varTable[i]];
			}
		}
	}

	public String toString() {
		final StringBuffer stringbuffer = new StringBuffer(
				this.sindent() + "Attribute: "
						+ super.d_cp.getAsString(super.d_idxName) + ": \n");
		for (int i = 0; i < this.d_varTable.length;) {
			final int j = this.d_varTable[i++];
			final int k = this.d_varTable[i++];
			final int l = this.d_varTable[i++];
			final int i1 = this.d_varTable[i++];
			final int j1 = this.d_varTable[i++];
			final String s = CPUtils.internal2java(super.d_cp.getAsString(i1));
			stringbuffer.append(this.sindent() + "  " + s + " "
					+ super.d_cp.getAsString(l) + " pc=" + j + " length=" + k
					+ " slot=" + j1 + "\n");
		}
		return stringbuffer.toString();
	}

	protected BitSet uses() {
		final BitSet bitset = super.uses();
		for (int i = 0; i < this.d_varTable.length; i++) {
			if (i % 5 == 2 || i % 5 == 3) {
				bitset.set(this.d_varTable[i]);
			}
		}
		return bitset;
	}

	protected void write(final DataOutputStream dataoutputstream)
			throws IOException {
		dataoutputstream.writeShort(super.d_idxName);
		dataoutputstream.writeInt(super.d_len);
		dataoutputstream.writeShort(this.d_numVars);
		for (int i = 0; i < this.d_varTable.length; i++) {
			dataoutputstream.writeShort(this.d_varTable[i]);
		}
	}
}

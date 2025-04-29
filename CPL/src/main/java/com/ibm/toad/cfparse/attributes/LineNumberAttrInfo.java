/**
 * Duplicate of class from CFParse library to
 * allow public access to method read().
 */
package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ibm.toad.cfparse.ConstantPool;

public final class LineNumberAttrInfo extends AttrInfo {
	private int d_numVars;
	private int d_varTable[];

	LineNumberAttrInfo(final ConstantPool constantpool, final int i,
			final int j) {
		super(constantpool, i, j);
		this.d_numVars = 0;
	}

	public void add(final int i, final int j) {
		for (int k = 0; k < this.d_numVars; k++) {
			if (this.d_varTable[2 * k] == j) {
				this.d_varTable[2 * k + 1] = i;
				return;
			}
		}
		if (this.d_varTable == null
				|| this.d_numVars * 2 >= this.d_varTable.length) {
			this.resize();
		}
		this.d_varTable[this.d_numVars * 2] = j;
		this.d_varTable[this.d_numVars * 2 + 1] = i;
		this.d_numVars++;
	}

	public void clear() {
		this.d_numVars = 0;
		this.d_varTable = null;
	}
	public void setFromBCEL(org.apache.bcel.classfile.LineNumberTable table) {
	    if (table == null) {
	        this.clear();
	        return;
	    }

	    org.apache.bcel.classfile.LineNumber[] lines = table.getLineNumberTable();
	    this.d_numVars = lines.length;
	    this.d_varTable = new int[this.d_numVars * 2];

	    for (int i = 0; i < this.d_numVars; i++) {
	        this.d_varTable[i * 2] = lines[i].getStartPC();
	        this.d_varTable[i * 2 + 1] = lines[i].getLineNumber();
	    }

	    this.d_len = 2 + this.d_numVars * 4;
	}

	public int getLineNumber(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return -1;
		}
		else {
			return this.d_varTable[2 * i + 1];
		}
	}

	public int getStartPC(final int i) {
		if (i < 0 || i >= this.d_numVars) {
			return -1;
		}
		else {
			return this.d_varTable[2 * i];
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
		//		super.d_len == 2 + this.d_numVars * 4,
		//		"d_len != 2 + (d_numVars * 4)\n" + super.d_len + " != 2 + ("
		//				+ this.d_numVars + "* 4)\n");
		this.d_varTable = new int[this.d_numVars * 2];
		for (int i = 0; i < this.d_varTable.length; i++) {
			this.d_varTable[i] = datainputstream.readShort();
		}
	}

	private void resize() {
		final int ai[] = new int[this.d_numVars * 2 + 10];
		if (this.d_varTable != null) {
			System.arraycopy(this.d_varTable, 0, ai, 0, this.d_numVars * 2);
		}
		this.d_varTable = ai;
	}

	public void setLineNumber(final int i, final int j) {
		if (i < 0 || i >= this.d_numVars) {
			return;
		}
		else {
			this.d_varTable[2 * i + 1] = j;
			return;
		}
	}

	public void setStartPC(final int i, final int j) {
		if (i < 0 || i >= this.d_numVars) {
			return;
		}
		else {
			this.d_varTable[2 * i] = j;
			return;
		}
	}

	protected int size() {
		return 8 + this.d_numVars * 4;
	}

	public String toString() {
		final StringBuffer stringbuffer = new StringBuffer(
				this.sindent() + "Attribute: "
						+ super.d_cp.getAsString(super.d_idxName) + ": \n");
		for (int i = 0; i < this.d_numVars; i++) {
			final int j = this.d_varTable[i * 2];
			final int k = this.d_varTable[i * 2 + 1];
			stringbuffer
					.append(this.sindent() + "  line " + k + ": " + j + "\n");
		}
		return stringbuffer.toString();
	}

	protected void write(final DataOutputStream dataoutputstream)
			throws IOException {
		dataoutputstream.writeShort(super.d_idxName);
		dataoutputstream.writeInt(2 + this.d_numVars * 4);
		dataoutputstream.writeShort(this.d_numVars);
		for (int i = 0; i < this.d_numVars * 2; i++) {
			dataoutputstream.writeShort(this.d_varTable[i]);
		}
	}
}

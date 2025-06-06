package com.ibm.toad.cfparse.attributes;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ibm.toad.cfparse.ConstantPool;

/**
 *
 * Henrique, 2025-04-21
 */
public final class SignatureAttrInfo extends AttrInfo {
	private int d_index;

	public SignatureAttrInfo(ConstantPool cp, int nameIndex, int attrLen) {
		super(cp, nameIndex, attrLen);
	}

	public void set(final int index) {
		this.d_index = index;
	}

	public int get() {
		return this.d_index;
	}

	public void read(final DataInput input) throws IOException {
		this.d_len = input.readInt(); 
		this.d_index = input.readUnsignedShort();
	}

	public void write(final DataOutput output) throws IOException {
		output.writeInt(2); // length
		output.writeShort(this.d_index);
	}

	@Override
	public String getName() {
		return "Signature";
	}

	@Override
	public String toString() {
	    return "name: Signature: " + this.d_cp.getAsString(d_index);
	}


	@Override
	protected void read(DataInputStream input) throws IOException {
		this.read((DataInput) input); 
	}

	@Override
	protected void write(DataOutputStream output) throws IOException {
		this.write((DataOutput) output); 
	}

}

package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;

public class UnknownAttrInfo extends AttrInfo {
	private byte[] bytes;

	public UnknownAttrInfo(final ConstantPool cp, final int nameIndex,
			final int attrLen) {

		super(cp, nameIndex, attrLen);
	}

	@Override
	public void read(final DataInputStream in) throws IOException {
		this.d_len = in.readInt();
		this.bytes = new byte[this.d_len];
		in.read(this.bytes);
	}

	@Override
	protected void write(final DataOutputStream out) throws IOException {
		out.writeInt(super.d_len);
		out.write(this.bytes);
	}

	@Override
	protected void sort(final int[] mapping) {
	}

	@Override
	protected BitSet uses() {
		return new BitSet(); // or actual usage
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(
				"Attribute: UnknownAttribute\n");
		if (this.bytes != null) {
			sb.append(new String(this.bytes));
		}
		return sb.toString();
	}
}
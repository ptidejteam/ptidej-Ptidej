package com.ibm.toad.cfparse.attributes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ibm.toad.cfparse.ConstantPool;

public final class RuntimeVisibleAnnotationsAttrInfo extends AttrInfo {
	private byte[] data;

	public RuntimeVisibleAnnotationsAttrInfo(ConstantPool cp, int nameIndex,
			int attrLen) {

		super(cp, nameIndex, attrLen);
	}

	@Override
	public String getName() {
		return "RuntimeVisibleAnnotations";
	}

	public void setFromBCEL(
			org.apache.bcel.classfile.RuntimeInvisibleAnnotations bcelAttr) {

		if (bcelAttr == null) {
			this.data = new byte[0];
			this.d_len = 0;
			return;
		}

		// BCEL can serialize the full attribute
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final DataOutputStream dos = new DataOutputStream(baos);
			bcelAttr.dump(dos);
			dos.close();

			// Skip the first 6 bytes (attribute_name_index + attribute_length) because your AttrInfo handles them separately
			final byte[] fullData = baos.toByteArray();
			this.data = new byte[fullData.length - 6];
			System.arraycopy(fullData, 6, this.data, 0, this.data.length);

			this.d_len = this.data.length;
		}
		catch (final IOException e) {
			throw new RuntimeException(
					"Error copying RuntimeVisibleAnnotations", e);
		}
	}

	@Override
	public String toString() {
		return "name: RuntimeVisibleAnnotations  bytes (" + this.data.length
				+ ")";
	}

	public void read(final DataInputStream input) throws IOException {
		this.d_len = input.readInt();
		this.data = new byte[this.d_len];
		input.readFully(this.data);
	}

	protected void write(final DataOutputStream output) throws IOException {
		output.writeInt(this.data.length);
		output.write(this.data);
	}
}

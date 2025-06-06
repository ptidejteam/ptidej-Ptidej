package com.ibm.toad.cfparse.attributes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.classfile.StackMapEntry;

import com.ibm.toad.cfparse.ConstantPool;

public final class StackMapTableAttrInfo extends AttrInfo {
	private byte[] rawBytes;

	public StackMapTableAttrInfo(ConstantPool cp, int nameIdx, int attrLen) {
		super(cp, nameIdx, attrLen);
	}

	public void setRawBytes(byte[] data) {
		this.rawBytes = data;
		this.d_len = data.length;
	}

	@Override
	public void read(DataInputStream input) throws IOException {
		this.d_len = input.readInt();
		this.rawBytes = new byte[this.d_len];
		input.readFully(this.rawBytes);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {
		output.writeInt(this.d_len);
		output.write(this.rawBytes);
	}

	@Override
	public String getName() {
		return "StackMapTable";
	}
	public void setFromBCEL(org.apache.bcel.classfile.StackMap stackMap) {
		if (stackMap == null) return;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);

			// Write u2 number_of_entries
			StackMapEntry[] entries = stackMap.getStackMap();
			dos.writeShort(entries.length);

			// Write each stack_map_frame
			for (StackMapEntry entry : entries) {
				entry.dump(dos); // BCEL handles serialization internally
			}

			this.rawBytes = bos.toByteArray();
			this.d_len = this.rawBytes.length;

			dos.close();
			bos.close();

		} catch (IOException e) {
			throw new RuntimeException("Error while converting StackMap to bytes", e);
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.sindent()).append("Attribute: StackMapTable: bytes (").append(this.d_len).append("): ");
		for (byte b : this.rawBytes) {
			sb.append(String.format("%02x ", b));
		}
		return sb.toString();
	}
}


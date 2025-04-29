package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class BootstrapMethodsAttrInfo extends AttrInfo {
    private int[][] d_methods;

    public BootstrapMethodsAttrInfo(ConstantPool cp, int nameIndex, int attrLen) {
        super(cp, nameIndex, attrLen);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
		this.d_len = in.readInt();
		int count = in.readUnsignedShort();
		d_methods = new int[count][];

		for (int i = 0; i < count; i++) {
			int methodRef = in.readUnsignedShort();
			int numArgs = in.readUnsignedShort();
			int[] args = new int[2 + numArgs];
			args[0] = methodRef;
			args[1] = numArgs;
			for (int j = 0; j < numArgs; j++) {
				args[2 + j] = in.readUnsignedShort();
			}
			d_methods[i] = args;
		}
	}

    @Override
    protected void write(DataOutputStream out) throws IOException {
        out.writeShort(super.d_idxName);
        out.writeInt(super.d_len);
        // Omit actual d_methods write unless fully implemented
    }

    @Override
    protected void sort(int[] mapping) {
        // optional if attribute needs remapping
    }

    @Override
    protected BitSet uses() {
        return new BitSet(); // or actual usage
    }

    @Override
	public String toString() {
		if (d_methods == null) return "Attribute: BootstrapMethods (empty or not read)";
		StringBuilder sb = new StringBuilder("Attribute: BootstrapMethods\n");
		for (int[] entry : d_methods) {
			sb.append("  methodRef: ").append(entry[0])
			  .append(" numArgs: ").append(entry[1])
			  .append("\n");
		}
		return sb.toString().trim();
	}
}


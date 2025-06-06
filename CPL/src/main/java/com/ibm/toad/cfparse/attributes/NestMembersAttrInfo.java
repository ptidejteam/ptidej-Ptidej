package com.ibm.toad.cfparse.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

import com.ibm.toad.cfparse.ConstantPool;

public final class NestMembersAttrInfo extends AttrInfo {
	private int[] d_classes;

	public void setMembers(int[] indexes) {
		this.d_classes = indexes;
		this.d_len = 2 + indexes.length * 2; 
	}


	public int[] getMembers() {
		return d_classes;
	}

	public NestMembersAttrInfo(ConstantPool cp, int nameIndex, int attrLen) {
		super(cp, nameIndex, attrLen);
	}

	public void setMembersAndLength(int[] indexes) {
		this.setMembers(indexes);

		try {
			
			java.lang.reflect.Field lenField = AttrInfo.class.getDeclaredField("d_len");
			lenField.setAccessible(true);
			lenField.setInt(this, 2 + indexes.length * 2);
		} catch (Exception e) {
			throw new RuntimeException("Could not set d_len", e);
		}
	}

	public void read(DataInputStream var1) throws IOException {
	
		super.d_len = var1.readInt();
		int count = var1.readUnsignedShort();
		this.d_classes = new int[count];
		for (int i = 0; i < count; i++) {
			this.d_classes[i] = var1.readUnsignedShort();
		}
	}


	
	protected void write(DataOutputStream out) throws IOException {
		out.writeShort(super.d_idxName);
		out.writeInt(2 + 2 * d_classes.length); // attribute_length
		out.writeShort(d_classes.length);
		for (int idx : d_classes) {
			out.writeShort(idx);
		}
	}

	
	protected void sort(int[] mapping) {
		
		for (int i = 0; i < d_classes.length; i++) {
			d_classes[i] = mapping[d_classes[i]];
		}
	
	}

	protected BitSet uses() {
		BitSet used = super.uses();
		for (int idx : d_classes) {
			used.set(idx);
		}
		return used;
	}

	
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Attribute: \n");
	    sb.append("name: NestMembers  bytes (" + (2 + 2 * d_classes.length) + "):");

	    sb.append(String.format(" 0 %d", d_classes.length)); // write count
	    for (int idx : d_classes) {
	        sb.append(" 0").append(' ').append(idx);
	    }
	    sb.append("\n");
	    return sb.toString();
	}

}

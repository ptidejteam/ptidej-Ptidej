package com.ibm.toad.cfparse.attributes;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.ExceptionTable;

import com.ibm.toad.cfparse.ConstantPool;

/**
 * ExceptionsAttrInfo: represents the Exceptions attribute in a method.
 * Henrique, 2025-04-21
 */
public final class ExceptionsAttrInfo extends AttrInfo {

	private int[] exceptionIndexTable;

	
	
	public void setTable(ExceptionTable table) {
		if (table == null || table.getNumberOfExceptions() == 0) {
			this.exceptionIndexTable = new int[0];
			this.d_len = 2;
			return;
		}

		int[] bcelIndexes = table.getExceptionIndexTable();
		this.exceptionIndexTable = new int[bcelIndexes.length];

		for (int i = 0; i < bcelIndexes.length; i++) {
			
			
			String className = table.getConstantPool().getConstantString(bcelIndexes[i], Const.CONSTANT_Class);
			int cfIndex = this.d_cp.addClass(className); 
			this.exceptionIndexTable[i] = cfIndex;

			System.out.println("Index: " + cfIndex);
		}

		this.d_len = 2 + 2 * exceptionIndexTable.length;
	}

	public void setFromBCEL(ExceptionTable table, ConstantPool cp) {
	    if (table == null || table.getNumberOfExceptions() == 0) {
	        this.exceptionIndexTable = new int[0];
	        this.d_len = 2;
	        return;
	    }

	    int[] bcelIndexes = table.getExceptionIndexTable();
	    this.exceptionIndexTable = new int[bcelIndexes.length];

	    for (int i = 0; i < bcelIndexes.length; i++) {
	        String className = table.getConstantPool().getConstantString(bcelIndexes[i], Const.CONSTANT_Class);
	        int cfIndex = cp.addClass(className);
	        this.exceptionIndexTable[i] = cfIndex;
	    }

	    this.d_len = 2 + 2 * exceptionIndexTable.length;
	}



	public ExceptionsAttrInfo(ConstantPool cp, int nameIndex, int attrLen) {
		super(cp, nameIndex, attrLen);
		
	
		
	}
	@Override
	protected int size() {
	    return 6 + 2 * exceptionIndexTable.length;
	}
	public void setExceptions(int[] indices) {
		this.exceptionIndexTable = indices;
		
	
	}

	public int[] getExceptions() {
		return this.exceptionIndexTable;
	}

	public void read(DataInput input) throws IOException {
		ByteArrayOutputStream debugBuffer = new ByteArrayOutputStream();
		DataOutputStream debugOut = new DataOutputStream(debugBuffer);

		this.d_len = input.readInt();
		debugOut.writeInt(this.d_len); 

		int numberOfExceptions = input.readUnsignedShort();
		debugOut.writeShort(numberOfExceptions); 

		this.exceptionIndexTable = new int[numberOfExceptions];

		for (int i = 0; i < numberOfExceptions; i++) {
			int index = input.readUnsignedShort();
			debugOut.writeShort(index);
			this.exceptionIndexTable[i] = index;
		
		}

		debugOut.close();
		byte[] rawBytes = debugBuffer.toByteArray();

		
	}




	public void write(DataOutput output) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		DataOutputStream debugOut = new DataOutputStream(buffer);

		debugOut.writeInt(2 + 2 * exceptionIndexTable.length);
		debugOut.writeShort(exceptionIndexTable.length);
		for (int exceptionIndex : exceptionIndexTable) {
			debugOut.writeShort(exceptionIndex);
		}
		debugOut.close();

		byte[] rawBytes = buffer.toByteArray();
	
		output.write(rawBytes);

	}


	@Override
	public String getName() {
		return "Exceptions";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name: Exceptions  bytes (").append(2 * exceptionIndexTable.length).append("):");

		for (int index : exceptionIndexTable) {
		    String exceptionName = d_cp.getAsJava(index);
		    sb.append(" ").append(exceptionName);
		}


		return sb.toString();
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

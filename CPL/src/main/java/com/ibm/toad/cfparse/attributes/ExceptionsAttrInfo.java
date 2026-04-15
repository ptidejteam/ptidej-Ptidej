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

	public void setTable(final ExceptionTable table) {
		if (table == null || table.getNumberOfExceptions() == 0) {
			this.exceptionIndexTable = new int[0];
			this.d_len = 2;
			return;
		}

		final int[] bcelIndexes = table.getExceptionIndexTable();
		this.exceptionIndexTable = new int[bcelIndexes.length];
		for (int i = 0; i < bcelIndexes.length; i++) {
			final String className = table.getConstantPool()
					.getConstantString(bcelIndexes[i], Const.CONSTANT_Class);
			final int cfIndex = this.d_cp.addClass(className);
			this.exceptionIndexTable[i] = cfIndex;
		}

		this.d_len = 2 + 2 * exceptionIndexTable.length;
	}

	public void setFromBCEL(final ExceptionTable table, final ConstantPool cp) {
		if (table == null || table.getNumberOfExceptions() == 0) {
			this.exceptionIndexTable = new int[0];
			this.d_len = 2;
			return;
		}

		final int[] bcelIndexes = table.getExceptionIndexTable();
		this.exceptionIndexTable = new int[bcelIndexes.length];
		for (int i = 0; i < bcelIndexes.length; i++) {
			final String className = table.getConstantPool()
					.getConstantString(bcelIndexes[i], Const.CONSTANT_Class);
			final int cfIndex = cp.addClass(className);
			this.exceptionIndexTable[i] = cfIndex;
		}

		this.d_len = 2 + 2 * exceptionIndexTable.length;
	}

	public ExceptionsAttrInfo(final ConstantPool cp, final int nameIndex,
			final int attrLen) {
		super(cp, nameIndex, attrLen);
	}

	@Override
	protected int size() {
		return 6 + 2 * exceptionIndexTable.length;
	}

	public void setExceptions(final int[] indices) {
		this.exceptionIndexTable = indices;
	}

	public int[] getExceptions() {
		return this.exceptionIndexTable;
	}

	public void read(final DataInput input) throws IOException {
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final DataOutputStream dataOut = new DataOutputStream(byteOut);

		this.d_len = input.readInt();
		dataOut.writeInt(this.d_len);

		final int numberOfExceptions = input.readUnsignedShort();
		dataOut.writeShort(numberOfExceptions);

		this.exceptionIndexTable = new int[numberOfExceptions];
		for (int i = 0; i < numberOfExceptions; i++) {
			final int index = input.readUnsignedShort();
			dataOut.writeShort(index);
			this.exceptionIndexTable[i] = index;
		}

		dataOut.close();
	}

	public void write(final DataOutput output) throws IOException {
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final DataOutputStream dataOut = new DataOutputStream(byteOut);

		dataOut.writeInt(2 + 2 * exceptionIndexTable.length);
		dataOut.writeShort(exceptionIndexTable.length);
		for (int exceptionIndex : exceptionIndexTable) {
			dataOut.writeShort(exceptionIndex);
		}
		dataOut.close();

		final byte[] rawBytes = byteOut.toByteArray();
		output.write(rawBytes);
	}

	@Override
	public String getName() {
		return "Exceptions";
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("name: Exceptions  bytes (")
				.append(2 * exceptionIndexTable.length).append("):");

		for (int index : exceptionIndexTable) {
			String exceptionName = d_cp.getAsJava(index);
			sb.append(" ").append(exceptionName);
		}

		return sb.toString();
	}

	@Override
	protected void read(final DataInputStream input) throws IOException {
		this.read((DataInput) input);
	}

	@Override
	protected void write(final DataOutputStream output) throws IOException {
		this.write((DataOutput) output);
	}
}

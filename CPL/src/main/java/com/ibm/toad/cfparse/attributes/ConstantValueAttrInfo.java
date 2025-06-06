package com.ibm.toad.cfparse.attributes;

import com.ibm.toad.cfparse.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

/**
 * ConstantValueAttrInfo
 * Represents a ConstantValue attribute attached to a Field.
 * 
 * Created based on decompiled CFParse 1.1 behavior (Henrique 2025-04-28).
 */
public final class ConstantValueAttrInfo extends AttrInfo {

    private int d_idxCP;

    public ConstantValueAttrInfo(ConstantPool cp, int idxName, int depth) {
        super(cp, idxName, depth);
    }

    public void set(int value) {
        this.d_idxCP = this.d_cp.addInteger(value);
    }

    public void set(float value) {
        this.d_idxCP = this.d_cp.addFloat(value);
    }

    public void set(long value) {
        this.d_idxCP = this.d_cp.addLong(value);
    }

    public void set(double value) {
        this.d_idxCP = this.d_cp.addDouble(value);
    }

    public void set(String value) {
        this.d_idxCP = this.d_cp.addString(value);
    }

    public String get() {
        return this.d_cp.getAsString(this.d_idxCP);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sindent());
        sb.append("Attribute: ");
        sb.append(this.d_cp.getAsString(this.d_idxName));
        sb.append(": ");
        sb.append(this.d_cp.getAsString(this.d_idxCP));
        sb.append("\n");
        return sb.toString();
    }

    @Override
    protected void read(DataInputStream in) throws IOException {
        this.d_len = in.readInt();
        if (this.d_len != 2) {
            throw new IOException("d_len != 2 : " + this.d_len);
        }
        this.d_idxCP = in.readUnsignedShort();
    }

    @Override
    protected void write(DataOutputStream out) throws IOException {
        out.writeShort(this.d_idxName);
        out.writeInt(2); // Always 2 bytes for ConstantValue
        out.writeShort(this.d_idxCP);
    }

    @Override
    protected void sort(int[] mapping) {
        super.sort(mapping);
        this.d_idxCP = mapping[this.d_idxCP];
    }

    @Override
    protected BitSet uses() {
        BitSet bits = super.uses();
        bits.set(this.d_idxCP);
        return bits;
    }

    public int getType() {
        return this.d_cp.getType(this.d_idxCP);
    }
}

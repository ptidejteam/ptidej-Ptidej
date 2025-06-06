package com.ibm.toad.cfparse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

public final class FieldInfoList {
    private ConstantPool d_cp;
    private int d_numFields;
    private FieldInfo[] d_fields;

    public FieldInfoList(ConstantPool cp) {
        this.d_cp = cp;
        this.d_numFields = 0;
        this.d_fields = null;
    }

    public FieldInfo get(int index) {
        if (index < 0 || index >= d_numFields) return null;
        return d_fields[index];
    }

    public FieldInfo add(String s) {
        if (d_fields == null || d_numFields == d_fields.length) resize();
        FieldInfo field = new FieldInfo(d_cp, s);
        d_fields[d_numFields++] = field;
        return field;
    }

    public FieldInfo addStatic(ClassFile classfile, String s) {
        if (d_fields == null || d_numFields == d_fields.length) resize();
        FieldInfo field = new FieldInfo(classfile, d_cp, s);
        d_fields[d_numFields++] = field;
        return field;
    }

    // 🔥 NEW METHOD TO MATCH WHAT YOU NEED
    public void add(FieldInfo fieldInfo) {
        if (d_fields == null || d_numFields == d_fields.length) resize();
        d_fields[d_numFields++] = fieldInfo;
    }

    public int length() {
        return d_numFields;
    }

    public void remove(int index) {
        if (index < 0 || index >= d_numFields - 1) return;
        for (int i = index; i < d_numFields - 1; i++) {
            d_fields[i] = d_fields[i + 1];
        }
        d_numFields--;
    }

    public String getFieldName(int index) {
        if (index < 0 || index >= d_numFields) return null;
        return d_fields[index].getName();
    }

    public BitSet uses() {
        BitSet bits = new BitSet(d_cp.length());
        for (int i = 0; i < d_numFields; i++) {
            bits.or(d_fields[i].uses());
        }
        return bits;
    }

    public void read(DataInputStream in) throws IOException {
        d_numFields = in.readUnsignedShort();
        d_fields = new FieldInfo[d_numFields];
        for (int i = 0; i < d_numFields; i++) {
            d_fields[i] = new FieldInfo(d_cp);
            d_fields[i].read(in);
        }
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeShort(d_numFields);
        for (int i = 0; i < d_numFields; i++) {
            d_fields[i].write(out);
        }
    }

    public void sort(int[] mapping) {
        for (int i = 0; i < d_numFields; i++) {
            d_fields[i].sort(mapping);
        }
    }

    private void resize() {
        FieldInfo[] newFields = new FieldInfo[d_numFields + 10];
        if (d_fields != null) {
            System.arraycopy(d_fields, 0, newFields, 0, d_numFields);
        }
        d_fields = newFields;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FIELDS:\n");
        if (d_numFields == 0) {
            sb.append("  <none>\n");
        } else {
            for (int i = 0; i < d_numFields; i++) {
                sb.append("  ").append(d_fields[i].toString()).append("\n");
            }
        }
        return sb.toString();
    }
}

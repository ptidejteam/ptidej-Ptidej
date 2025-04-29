package com.ibm.toad.cfparse.attributes;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ibm.toad.cfparse.ConstantPool;

public final class RuntimeInvisibleAnnotationsAttrInfo extends AttrInfo {
    private byte[] data;

    public RuntimeInvisibleAnnotationsAttrInfo(ConstantPool cp, int nameIndex, int attrLen) {
        super(cp, nameIndex, attrLen);
    }

    public void read(DataInput input) throws IOException {
        this.d_len = input.readInt();
        this.data = new byte[this.d_len];
        input.readFully(this.data);
    }

    public void write(DataOutput output) throws IOException {
        output.writeInt(this.data.length);
        output.write(this.data);
    }

    @Override
    public String getName() {
        return "RuntimeInvisibleAnnotations";
    }
    public void setFromBCEL(org.apache.bcel.classfile.RuntimeInvisibleAnnotations bcelAttr) {
        if (bcelAttr == null) {
            this.data = new byte[0];
            this.d_len = 0;
            return;
        }

        // BCEL can serialize the full attribute
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream dos = new java.io.DataOutputStream(baos);
            bcelAttr.dump(dos);
            dos.close();
            byte[] fullData = baos.toByteArray();

            // Skip the first 6 bytes (attribute_name_index + attribute_length) because your AttrInfo handles them separately
            this.data = new byte[fullData.length - 6];
            System.arraycopy(fullData, 6, this.data, 0, this.data.length);

            this.d_len = this.data.length;
        } catch (IOException e) {
            throw new RuntimeException("Error copying RuntimeInvisibleAnnotations", e);
        }
    }

    @Override
    public String toString() {
        return "name: RuntimeInvisibleAnnotations  bytes (" + this.data.length + ")";
    }

 
    protected void read(DataInputStream input) throws IOException {
        read((DataInput) input);
    }


    protected void write(DataOutputStream output) throws IOException {
        write((DataOutput) output);
    }
}

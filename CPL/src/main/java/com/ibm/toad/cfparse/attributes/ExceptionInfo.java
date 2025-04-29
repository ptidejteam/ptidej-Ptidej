package com.ibm.toad.cfparse.attributes;

import java.io.*;

public class ExceptionInfo {
    private int startPC;
    private int endPC;
    private int handlerPC;
    private int catchType;

    public void read(DataInputStream in) throws IOException {
        startPC = in.readShort();
        endPC = in.readShort();
        handlerPC = in.readShort();
        catchType = in.readShort();
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeShort(startPC);
        out.writeShort(endPC);
        out.writeShort(handlerPC);
        out.writeShort(catchType);
    }

    public int getStartPC() { return startPC; }
    public int getEndPC() { return endPC; }
    public int getHandlerPC() { return handlerPC; }
    public int getCatchType() { return catchType; }

    public void setStartPC(int startPC) { this.startPC = startPC; }
    public void setEndPC(int endPC) { this.endPC = endPC; }
    public void setHandlerPC(int handlerPC) { this.handlerPC = handlerPC; }
    public void setCatchType(int catchType) { this.catchType = catchType; }

    @Override
    public String toString() {
        return "ExceptionInfo: startPC=" + startPC + ", endPC=" + endPC +
               ", handlerPC=" + handlerPC + ", catchType=" + catchType;
    }
}

package net.foxdenstudio.foxlang.compiler.structure.constants;

public abstract class ConstantPoolItem {
    short locInCP = -1;

    public abstract byte[] toBytes();

    public short getLocationInCP() {
        return (short) (locInCP); // Convert to index base 1
    }

    public ConstantPoolItem setLocationInCP(short locInCP) {
        this.locInCP = locInCP;
        return this;
    }
}

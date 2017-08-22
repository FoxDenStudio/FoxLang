package net.foxdenstudio.foxlangy.structure.constants;

public class ClassConstant implements IConstantPoolItem {

    private final int lead;

    public ClassConstant(int lead) {
        this.lead = lead;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[3];
        newByteArray[0] = 0x07;
        newByteArray[1] = 0x00;
        newByteArray[2] = ((byte) this.lead);
        return newByteArray;
    }
}

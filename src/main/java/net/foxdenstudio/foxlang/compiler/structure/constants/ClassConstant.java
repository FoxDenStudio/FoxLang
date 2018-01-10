package net.foxdenstudio.foxlang.compiler.structure.constants;

public class ClassConstant extends ConstantPoolItem {

    private final int lead;

    public ClassConstant(int lead) {
        this.lead = lead;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[3];
        newByteArray[0] = 0x07;
        newByteArray[1] = ((byte) (this.lead >> 8));
        newByteArray[2] = ((byte) this.lead);
        return newByteArray;
    }
}

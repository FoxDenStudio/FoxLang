package net.foxdenstudio.foxlangy.structure.constants;

public class MethodRefConstant implements IConstantPoolItem {
    private final int type;
    private final int info;

    public MethodRefConstant(int type, int info) {

        this.type = type;
        this.info = info;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[5];
        newByteArray[0] = 0x10;
        newByteArray[1] = 0x00;
        newByteArray[2] = ((byte) this.type);
        newByteArray[3] = 0x00;
        newByteArray[4] = ((byte) this.info);
        return newByteArray;
    }
}

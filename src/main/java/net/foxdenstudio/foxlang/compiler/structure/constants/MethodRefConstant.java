package net.foxdenstudio.foxlang.compiler.structure.constants;

public class MethodRefConstant extends ConstantPoolItem {
    private final int type;
    private final int info;

    public MethodRefConstant(int type, int info) {

        this.type = type;
        this.info = info;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[5];
        newByteArray[0] = 0x0a;
        newByteArray[1] = ((byte) (this.type >> 8));
        newByteArray[2] = ((byte) this.type);
        newByteArray[3] = ((byte) (this.info >> 8));
        newByteArray[4] = ((byte) this.info);
        return newByteArray;
    }
}

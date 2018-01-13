package net.foxdenstudio.foxlang.compiler.structure.constants;

public class MethodRefConstant extends ConstantPoolItem {
    private final ClassConstant type;
    private final NameAndTypeConstant info;

    public MethodRefConstant(ClassConstant type, NameAndTypeConstant info) {

        this.type = type;
        this.info = info;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[5];
        newByteArray[0] = 0x0a;
        newByteArray[1] = ((byte) (this.type.getLocationInCP() >> 8));
        newByteArray[2] = ((byte) this.type.getLocationInCP());
        newByteArray[3] = ((byte) (this.info.getLocationInCP() >> 8));
        newByteArray[4] = ((byte) this.info.getLocationInCP());
        return newByteArray;
    }
}

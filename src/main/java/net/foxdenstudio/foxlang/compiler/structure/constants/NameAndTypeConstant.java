package net.foxdenstudio.foxlang.compiler.structure.constants;

public class NameAndTypeConstant extends ConstantPoolItem {
    private final UTF8Constant name;
    private final UTF8Constant type;

    public NameAndTypeConstant(UTF8Constant name, UTF8Constant type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[5];
        newByteArray[0] = 0x0c;
        newByteArray[1] = ((byte) (this.name.getLocationInCP() >> 8));
        newByteArray[2] = ((byte) this.name.getLocationInCP());
        newByteArray[3] = ((byte) (this.type.getLocationInCP() >> 8));
        newByteArray[4] = ((byte) this.type.getLocationInCP());
        return newByteArray;

    }
}

package net.foxdenstudio.foxlangy.testing.structure.constants;

public class NameAndTypeConstant implements IConstantPoolItem {
    private final int name;
    private final int type;

    public NameAndTypeConstant(int name, int type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public byte[] toBytes() {
        byte[] newByteArray = new byte[5];
        newByteArray[0] = 0x0c;
        newByteArray[1] = 0x00;
        newByteArray[2] = ((byte) this.name);
        newByteArray[3] = 0x00;
        newByteArray[4] = ((byte) this.type);
        return newByteArray;

    }
}

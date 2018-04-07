package net.foxdenstudio.foxlang.compiler.structure.constants;

public class UTF8Constant extends ConstantPoolItem {

    private final String data;

    public UTF8Constant(final String data) {
        this.data = data;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = this.data.getBytes();
        byte[] newByteArray = new byte[1 + 2 + bytes.length];
        newByteArray[0] = 0x01;
        newByteArray[1] = ((byte) (bytes.length >> 8));
        newByteArray[2] = ((byte) bytes.length);
        System.arraycopy(bytes, 0, newByteArray, 3, bytes.length);
        return newByteArray;
    }

    public String getData() {
        return this.data;
    }
}

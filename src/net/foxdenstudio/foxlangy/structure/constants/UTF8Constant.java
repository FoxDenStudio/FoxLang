package net.foxdenstudio.foxlangy.structure.constants;

import java.util.Arrays;

public class UTF8Constant implements IConstantPoolItem {

    private final String data;

    public UTF8Constant(final String data) {
        this.data = data;
    }

    @Override
    public byte[] toBytes() {
        final byte[] bytes = this.data.getBytes();
        byte[] newByteArray = new byte[1 + 2 + bytes.length];
        newByteArray[0] = 0x01;
        newByteArray[1] = 0x00;
        newByteArray[2] = ((byte) bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            newByteArray[3 + i] = bytes[i];
        }
        System.out.println(bytes.length + " | " + Arrays.toString(newByteArray));
        return newByteArray;
    }
}

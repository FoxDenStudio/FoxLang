package net.foxdenstudio.foxlangy;

public enum JVMVersion {
    JVM5(0x31),JVM6(0x32),JVM7(0x33),JVM8(0x34),JVM9(0x35);

    private final int hex;

    JVMVersion(int hex) {
        this.hex = hex;
    }

    public int getHex() {
        return this.hex;
    }
}
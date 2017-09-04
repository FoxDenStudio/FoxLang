package net.foxdenstudio.foxlang.compiler;

import net.foxdenstudio.foxlang.compiler.structure.constants.IConstantPoolItem;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class JVMClassBuilder {
    private final JVMVersion targetJVMVersion = JVMVersion.JVM8;
    private final ArrayList<IConstantPoolItem> constantPoolItems;

    private final String className;

    public JVMClassBuilder(@Nonnull final String className) {
        this.className = className;
        this.constantPoolItems = new ArrayList<>();
    }

    public byte[] generateByteCode() {
        ByteArrayOutputStream byteCode = new ByteArrayOutputStream();
        writeClassHeader(byteCode);
        writeConstantPool(byteCode);

        return byteCode.toByteArray();
    }

    private void writeConstantPool(@Nonnull final ByteArrayOutputStream byteCode) {
        byteCode.write((constantPoolItems.size() >>> 8) & 0xFF);
        byteCode.write(constantPoolItems.size() & 0xFF);
    }

    private void writeClassHeader(@Nonnull final ByteArrayOutputStream byteCode) {
        //magic class code
        byteCode.write(0xCA);
        byteCode.write(0xFE);
        byteCode.write(0xBA);
        byteCode.write(0xBE);

        //version
        //  minor
        byteCode.write(0x00);
        byteCode.write(0x00);
        //  major
        byteCode.write(0x00);
        byteCode.write(this.targetJVMVersion.getByte());
    }
}

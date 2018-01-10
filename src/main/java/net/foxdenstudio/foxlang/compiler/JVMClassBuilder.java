package net.foxdenstudio.foxlang.compiler;

import net.foxdenstudio.foxlang.compiler.structure.constants.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nonnull;

public class JVMClassBuilder {
    private final JVMVersion targetJVMVersion = JVMVersion.JVM8;
    /**
     * DON'T FORGET REFERENCES ARE BASE 1
     */
    private final ArrayList<ConstantPoolItem> constantPoolItems;
    @Nonnull
    private final String classPackage;
    @Nonnull
    private final String className;
    private short constantPoolIndex = 0;

    public JVMClassBuilder(@Nonnull final String classPackage, @Nonnull final String className) {
        this.classPackage = classPackage.isEmpty() ? "foxlangscript" : classPackage;
        this.className = className;
        this.constantPoolItems = new ArrayList<>(3);

        final ConstantPoolItem initElem = new UTF8Constant("<init>");
        this.constantPoolItems.add(initElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem returnElem = new UTF8Constant("()V");
        this.constantPoolItems.add(returnElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem thisClassNameElem = new UTF8Constant(this.classPackage.replace('.', '/') + '/' + this.className);
        this.constantPoolItems.add(thisClassNameElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem objectClassNameElem = new UTF8Constant("java/lang/Object");
        this.constantPoolItems.add(objectClassNameElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem thisClassElem = new ClassConstant(thisClassNameElem.getLocationInCP());
        this.constantPoolItems.add(thisClassElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem objectClassElem = new ClassConstant(objectClassNameElem.getLocationInCP());
        this.constantPoolItems.add(objectClassElem.setLocationInCP(++constantPoolIndex));

        final ConstantPoolItem specInitMethodElem = new NameAndTypeConstant(initElem.getLocationInCP(), returnElem.getLocationInCP());
        this.constantPoolItems.add(specInitMethodElem.setLocationInCP(++constantPoolIndex));

        this.constantPoolItems.add(new MethodRefConstant(objectClassElem.getLocationInCP(), specInitMethodElem.getLocationInCP()));
        this.constantPoolItems.add(new UTF8Constant("Code"));
        this.constantPoolItems.add(new UTF8Constant("LineNumberTable"));
        this.constantPoolItems.add(new UTF8Constant("LocalVariableTable"));
        this.constantPoolItems.add(new UTF8Constant("this"));
        this.constantPoolItems.add(new UTF8Constant("L" + this.classPackage.replace('.', '/') + '/' + this.className + ";"));
        this.constantPoolItems.add(new UTF8Constant("SourceFile"));
        this.constantPoolItems.add(new UTF8Constant(this.className + ".java"));
    }

    public byte[] generateByteCode() {
        ByteArrayOutputStream byteCode = new ByteArrayOutputStream();
        writeClassHeader(byteCode);
        writeConstantPool(byteCode);
        writeCode(byteCode);
        return byteCode.toByteArray();
    }

    private void writeCode(@Nonnull final ByteArrayOutputStream byteCode) {

        //access flags
        byteCode.write(0x00);
        byteCode.write(0x21); //public & super

        //this class
        byteCode.write(0x00);
        byteCode.write(0x05);

        //super class
        byteCode.write(0x00);
        byteCode.write(0x06);

        //interface count
        byteCode.write(0x00);
        byteCode.write(0x00);

        //field count
        byteCode.write(0x00);
        byteCode.write(0x00);

        //method count
        byteCode.write(0x00);
        byteCode.write(0x01);

        //  method access flags
        byteCode.write(0x00);
        byteCode.write(0x01); //public

        //  method name_index
        byteCode.write(0x00);
        byteCode.write(0x01);

        //  method descriptor_index
        byteCode.write(0x00);
        byteCode.write(0x02);

        //  attribute count
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      attribute name_index
        byteCode.write(0x00);
        byteCode.write(0x09);

        //      attribute length
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x2f);

        //      stack size
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      local size
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      code length
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x05);

        //          code
        //              aload_0
        byteCode.write(0x2a);

        //              invokespecial
        byteCode.write(0xb7);
        byteCode.write(0x00);
        byteCode.write(0x07);

        //              return
        byteCode.write(0xb1);


        //      attribute count
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x02);

        //      attribute - Line number table
        byteCode.write(0x00);
        byteCode.write(0x0a);

        //          attribute length
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x06);

        //          attribute -line number table length
        byteCode.write(0x00);
        byteCode.write(0x01);

        //          line number initial pc 0 is line 3
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x03);


        //      attribute - local variable table
        byteCode.write(0x00);
        byteCode.write(0x0b);

        //          attribute length
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x0c);

        //          attribute - local variable table length
        byteCode.write(0x00);
        byteCode.write(0x01);

        //              start pc
        byteCode.write(0x00);
        byteCode.write(0x00);

        //              length
        byteCode.write(0x00);
        byteCode.write(0x05);

        //              name_index
        byteCode.write(0x00);
        byteCode.write(0x0c);

        //              descriptor_index
        byteCode.write(0x00);
        byteCode.write(0x0d);

        //              index
        byteCode.write(0x00);
        byteCode.write(0x00);


        //  class attribute count
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      class attribute index - sourcefile
        byteCode.write(0x00);
        byteCode.write(0x0e);

        //      class attribute size - sourcefile
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x02);

        //      class attribute value - sourcefile
        byteCode.write(0x00);
        byteCode.write(0x0f);

    }

    private void writeConstantPool(@Nonnull final ByteArrayOutputStream byteCode) {
        byteCode.write((constantPoolItems.size() + 1) >> 8);
        byteCode.write((constantPoolItems.size() + 1));

        for (final ConstantPoolItem constantPoolItem : this.constantPoolItems) {
            try {
                byteCode.write(constantPoolItem.toBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("\t\t[");
            for (byte b : constantPoolItem.toBytes()) {
                System.out.print("0x" + Integer.toHexString(b) + " ");
            }
            System.out.println("]");
        }
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

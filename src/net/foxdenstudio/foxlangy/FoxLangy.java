package net.foxdenstudio.foxlangy;

import net.foxdenstudio.foxlangy.structure.constants.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FoxLangy {

    final JVMVersion targetJVMVersion = JVMVersion.JVM9;
    final ArrayList<IConstantPoolItem> constantPoolItems;

    public FoxLangy() {
        this.constantPoolItems = new ArrayList<>();
        this.constantPoolItems.add(new UTF8Constant("<init>"));
        this.constantPoolItems.add(new UTF8Constant("()V"));
        this.constantPoolItems.add(new UTF8Constant("net/foxdenstudio/foxlangy/Test"));
        this.constantPoolItems.add(new UTF8Constant("java/lang/Object"));
        this.constantPoolItems.add(new ClassConstant(3));
        this.constantPoolItems.add(new ClassConstant(4));
        this.constantPoolItems.add(new NameAndTypeConstant(1, 2));
        this.constantPoolItems.add(new MethodRefConstant(6, 7));
        this.constantPoolItems.add(new UTF8Constant("Code"));
        this.constantPoolItems.add(new UTF8Constant("LineNumberTable"));
        this.constantPoolItems.add(new UTF8Constant("LocalVariableTable"));
        this.constantPoolItems.add(new UTF8Constant("this"));
        this.constantPoolItems.add(new UTF8Constant("Lnet/foxdenstudio/foxlangy/Test;"));
        this.constantPoolItems.add(new UTF8Constant("SourceFile"));
        this.constantPoolItems.add(new UTF8Constant("Test.java"));

        try (OutputStream outputStream = new FileOutputStream("script.class")) {
            this.writeHeader(outputStream);
            outputStream.write(0x00);
            final byte i = (byte) (this.constantPoolItems.size() + 1);
            outputStream.write(i);
            System.out.println(i);
            for (final IConstantPoolItem constantPoolItem : this.constantPoolItems) {
                outputStream.write(constantPoolItem.toBytes());
                System.out.println("\t\t" + Arrays.toString(constantPoolItem.toBytes()));
            }

            //access flags
            outputStream.write(0x00);
            outputStream.write(0x21); //public & super

            //this class
            outputStream.write(0x00);
            outputStream.write(0x05);

            //super class
            outputStream.write(0x00);
            outputStream.write(0x06);

            //interface count
            outputStream.write(0x00);
            outputStream.write(0x00);

            //field count
            outputStream.write(0x00);
            outputStream.write(0x00);

            //method count
            outputStream.write(0x00);
            outputStream.write(0x01);

            //  method access flags
            outputStream.write(0x00);
            outputStream.write(0x01); //public

            //  method name_index
            outputStream.write(0x00);
            outputStream.write(0x01);

            //  method descriptor_index
            outputStream.write(0x00);
            outputStream.write(0x02);

            //  attribute count
            outputStream.write(0x00);
            outputStream.write(0x01);

            //      attribute name_index
            outputStream.write(0x00);
            outputStream.write(0x09);

            //      attribute length
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x2f);

            //      stack size
            outputStream.write(0x00);
            outputStream.write(0x01);

            //      local size
            outputStream.write(0x00);
            outputStream.write(0x01);

            //      code length
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x05);

            //          code
            //              aload_0
            outputStream.write(0x2a);

            //              invokespecial
            outputStream.write(0xb7);
            outputStream.write(0x00);
            outputStream.write(0x07);

            //              return
            outputStream.write(0xb1);


            //      attribute count
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x02);

            //      attribute - Line number table
            outputStream.write(0x00);
            outputStream.write(0x0a);

            //          attribute length
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x06);

            //          attribute -line number table length
            outputStream.write(0x00);
            outputStream.write(0x01);

            //          line number initial pc 0 is line 3
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x03);


            //      attribute - local variable table
            outputStream.write(0x00);
            outputStream.write(0x0b);

            //          attribute length
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x0c);

            //          attribute - local variable table length
            outputStream.write(0x00);
            outputStream.write(0x01);

            //              start pc
            outputStream.write(0x00);
            outputStream.write(0x00);

            //              length
            outputStream.write(0x00);
            outputStream.write(0x05);

            //              name_index
            outputStream.write(0x00);
            outputStream.write(0x0c);

            //              descriptor_index
            outputStream.write(0x00);
            outputStream.write(0x0d);

            //              index
            outputStream.write(0x00);
            outputStream.write(0x00);


            //  class attribute count
            outputStream.write(0x00);
            outputStream.write(0x01);

            //      class attribute index - sourcefile
            outputStream.write(0x00);
            outputStream.write(0x0e);

            //      class attribute size - sourcefile
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x00);
            outputStream.write(0x02);

            //      class attribute value - sourcefile
            outputStream.write(0x00);
            outputStream.write(0x0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FoxLangy();
    }

    private void writeHeader(final OutputStream outputStream) throws IOException {
        //magic class code
        outputStream.write(0xCA);
        outputStream.write(0xFE);
        outputStream.write(0xBA);
        outputStream.write(0xBE);

        //version
        //  minor
        outputStream.write(0x00);
        outputStream.write(0x00);
        //  major
        outputStream.write(0x00);
        outputStream.write(this.targetJVMVersion.getHex());
    }
}
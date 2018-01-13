package net.foxdenstudio.foxlang.compiler;

import net.foxdenstudio.foxlang.compiler.structure.constants.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nonnull;

public class JVMClassBuilder {
    private final UTF8Constant initElem;
    private final UTF8Constant returnElem;
    private final UTF8Constant thisClassNameElem;
    private final UTF8Constant objectClassNameElem;
    private final UTF8Constant specialInterfaceClassNameElem;
    private final ClassConstant thisClassElem;
    private final ClassConstant objectClassElem;
    private final ClassConstant specialInterfaceClassElem;
    private final NameAndTypeConstant specInitMethodElem;
    private final MethodRefConstant initMethodRef;
    private final UTF8Constant codeAttrUTF;
    private final UTF8Constant lineNumberTable;
    private final UTF8Constant localVariableTable;
    private final UTF8Constant thisUTF;
    private final UTF8Constant thisClassLUTF;
    private final UTF8Constant sourceFileAttr;
    private final UTF8Constant sourceFileUTF;

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

        initElem = new UTF8Constant("<init>");
        returnElem = new UTF8Constant("()V");
        thisClassNameElem = new UTF8Constant(this.classPackage.replace('.', '/') + '/' + this.className);
        objectClassNameElem = new UTF8Constant("java/lang/Object");
        specialInterfaceClassNameElem = new UTF8Constant("net/foxdenstudio/foxlang/IInternalScript");
        thisClassElem = new ClassConstant(thisClassNameElem);
        objectClassElem = new ClassConstant(objectClassNameElem);
        specialInterfaceClassElem = new ClassConstant(specialInterfaceClassNameElem);
        specInitMethodElem = new NameAndTypeConstant(initElem, returnElem);
        initMethodRef = new MethodRefConstant(objectClassElem, specInitMethodElem);
        codeAttrUTF = new UTF8Constant("Code");
        lineNumberTable = new UTF8Constant("LineNumberTable");
        localVariableTable = new UTF8Constant("LocalVariableTable");
        thisUTF = new UTF8Constant("this");
        thisClassLUTF = new UTF8Constant("L" + this.classPackage.replace('.', '/') + '/' + this.className + ";");
        sourceFileAttr = new UTF8Constant("SourceFile");
        sourceFileUTF = new UTF8Constant(this.className + ".java");

        this.constantPoolItems = new ArrayList<>(3);
        this.constantPoolItems.add(initElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(returnElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(thisClassNameElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(objectClassNameElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(specialInterfaceClassNameElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(thisClassElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(objectClassElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(specialInterfaceClassElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(specInitMethodElem.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(initMethodRef.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(codeAttrUTF.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(lineNumberTable.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(localVariableTable.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(thisUTF.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(thisClassLUTF.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(sourceFileAttr.setLocationInCP(++constantPoolIndex));
        this.constantPoolItems.add(sourceFileUTF.setLocationInCP(++constantPoolIndex));
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
        byteCode.write(0x21);//0x20 /*super*/ & 0x01/*public*/);

        //this class
        byteCode.write(this.thisClassElem.getLocationInCP() >> 8);
        byteCode.write(this.thisClassElem.getLocationInCP());

        //super class
        byteCode.write(this.objectClassElem.getLocationInCP() >> 8);
        byteCode.write(this.objectClassElem.getLocationInCP());

        //interface count
        byteCode.write(0x00);
        byteCode.write(0x01);

        //My Special Interface
        byteCode.write(this.specialInterfaceClassElem.getLocationInCP() >> 8);
        byteCode.write(this.specialInterfaceClassElem.getLocationInCP());

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
        byteCode.write(this.initElem.getLocationInCP() >> 8);
        byteCode.write(this.initElem.getLocationInCP());

        //  method descriptor_index
        byteCode.write(this.returnElem.getLocationInCP() >> 8);
        byteCode.write(this.returnElem.getLocationInCP());

        //  attribute count (method)
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      attribute name_index
        byteCode.write(this.codeAttrUTF.getLocationInCP() >> 8);
        byteCode.write(this.codeAttrUTF.getLocationInCP()); //

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
        byteCode.write(this.specInitMethodElem.getLocationInCP() >> 8);
        byteCode.write(this.specInitMethodElem.getLocationInCP());

        //              return
        byteCode.write(0xb1);

        //Exception count
        byteCode.write(0x00);
        byteCode.write(0x00);

        //      attribute count
        byteCode.write(0x00);
        byteCode.write(0x02);
//        byteCode.write(0x01);

        //      attribute - Line number table
        byteCode.write(this.lineNumberTable.getLocationInCP() >> 8);
        byteCode.write(this.lineNumberTable.getLocationInCP());

        //          attribute byte length
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
        byteCode.write(this.localVariableTable.getLocationInCP() >> 8);
        byteCode.write(this.localVariableTable.getLocationInCP());

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
        byteCode.write(this.thisUTF.getLocationInCP() >> 8);
        byteCode.write(this.thisUTF.getLocationInCP());

        //              descriptor_index
        byteCode.write(this.thisClassLUTF.getLocationInCP() >> 8);
        byteCode.write(this.thisClassLUTF.getLocationInCP());

        //              index
        byteCode.write(0x00);
        byteCode.write(0x00);

        //  class attribute count
        byteCode.write(0x00);
        byteCode.write(0x01);

        //      class attribute index - sourcefile
        byteCode.write(this.sourceFileAttr.getLocationInCP() >> 8);
        byteCode.write(this.sourceFileAttr.getLocationInCP());

        //      class attribute size - sourcefile
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x00);
        byteCode.write(0x02);

        //      class attribute value - sourcefile
        byteCode.write(this.sourceFileUTF.getLocationInCP() >> 8);
        byteCode.write(this.sourceFileUTF.getLocationInCP());

    }

    private void writeConstantPool(@Nonnull final ByteArrayOutputStream byteCode) {
        // Write the size of the constant pool (2 bytes)
        byteCode.write((constantPoolItems.size() + 1) >> 8);
        byteCode.write((constantPoolItems.size() + 1));

        for (final ConstantPoolItem constantPoolItem : this.constantPoolItems) {
            try {
                // Write each constant pool item to the bytecode
                byteCode.write(constantPoolItem.toBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.print("\t\t[ ");
            for (byte b : constantPoolItem.toBytes()) {
                System.out.print("0x" + Integer.toHexString(b) + " ");
            }
            System.out.println("]");
        }
    }

    private void writeClassHeader(@Nonnull final ByteArrayOutputStream byteCode) {
        //magic header class code
        byteCode.write(0xCA);
        byteCode.write(0xFE);
        byteCode.write(0xBA);
        byteCode.write(0xBE);

        //version
        //  minor (Always 0?
        byteCode.write(0x00);
        byteCode.write(0x00);
        //  major
        byteCode.write(0x00);
        byteCode.write(this.targetJVMVersion.getByte());
    }
}

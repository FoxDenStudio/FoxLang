package net.foxdenstudio.foxlang;

import net.foxdenstudio.foxlang.compiler.JVMClassBuilder;
import net.foxdenstudio.foxlang.compiler.structure.constants.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Fox on 1/14/2018.
 * Project: FoxLang
 */
public class BaseClassBuilder {
    private static final String classPackage = "";
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

    public BaseClassBuilder() {
        final JVMClassBuilder jvmClassBuilder = new JVMClassBuilder("", "BaseClass");

        this.initElem = new UTF8Constant("<init>");
        this.returnElem = new UTF8Constant("()V");
        this.thisClassNameElem = new UTF8Constant("foxlangscript" + '/' + "BaseClass");
        this.objectClassNameElem = new UTF8Constant("java/lang/Object");
        this.specialInterfaceClassNameElem = new UTF8Constant("net/foxdenstudio/foxlang/IInternalScript");
        this.thisClassElem = new ClassConstant(this.thisClassNameElem);
        this.objectClassElem = new ClassConstant(this.objectClassNameElem);
        this.specialInterfaceClassElem = new ClassConstant(this.specialInterfaceClassNameElem);
        this.specInitMethodElem = new NameAndTypeConstant(this.initElem, this.returnElem);
        this.initMethodRef = new MethodRefConstant(this.objectClassElem, this.specInitMethodElem);
        this.codeAttrUTF = new UTF8Constant("Code");
        this.lineNumberTable = new UTF8Constant("LineNumberTable");
        this.localVariableTable = new UTF8Constant("LocalVariableTable");
        this.thisUTF = new UTF8Constant("this");
        this.thisClassLUTF = new UTF8Constant("L" + "foxlangscript" + '/' + "BaseClass" + ";");
        this.sourceFileAttr = new UTF8Constant("SourceFile");
        this.sourceFileUTF = new UTF8Constant("BaseClass" + ".fls");

        jvmClassBuilder.addConstantPoolItem(this.initElem);
        jvmClassBuilder.addConstantPoolItem(this.returnElem);
        jvmClassBuilder.addConstantPoolItem(this.thisClassNameElem);
        jvmClassBuilder.addConstantPoolItem(this.objectClassNameElem);
        jvmClassBuilder.addConstantPoolItem(this.specialInterfaceClassNameElem);
        jvmClassBuilder.addConstantPoolItem(this.thisClassElem);
        jvmClassBuilder.addConstantPoolItem(this.objectClassElem);
        jvmClassBuilder.addConstantPoolItem(this.specialInterfaceClassElem);
        jvmClassBuilder.addConstantPoolItem(this.specInitMethodElem);
        jvmClassBuilder.addConstantPoolItem(this.initMethodRef);
        jvmClassBuilder.addConstantPoolItem(this.codeAttrUTF);
        jvmClassBuilder.addConstantPoolItem(this.lineNumberTable);
        jvmClassBuilder.addConstantPoolItem(this.localVariableTable);
        jvmClassBuilder.addConstantPoolItem(this.thisUTF);
        jvmClassBuilder.addConstantPoolItem(this.thisClassLUTF);
        jvmClassBuilder.addConstantPoolItem(this.sourceFileAttr);
        jvmClassBuilder.addConstantPoolItem(this.sourceFileUTF);

        jvmClassBuilder.setThisClassElem(this.thisClassElem);
        jvmClassBuilder.setSuperClassElem(this.objectClassElem);
        jvmClassBuilder.setSourceFileAttr(this.sourceFileAttr);
        jvmClassBuilder.setSourceFileUTF(this.sourceFileUTF);
        jvmClassBuilder.registerMethod(byteCode -> {

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
        });

        try (final FileOutputStream fileOutputStream = new FileOutputStream("scripts/baseclass.class")) {
            for (final byte b : jvmClassBuilder.generateByteCode()) {
                fileOutputStream.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BaseClassBuilder();
    }
}

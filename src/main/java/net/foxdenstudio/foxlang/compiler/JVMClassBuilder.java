package net.foxdenstudio.foxlang.compiler;

import net.foxdenstudio.foxlang.compiler.structure.FieldHolder;
import net.foxdenstudio.foxlang.compiler.structure.constants.ClassConstant;
import net.foxdenstudio.foxlang.compiler.structure.constants.ConstantPoolItem;
import net.foxdenstudio.foxlang.compiler.structure.constants.UTF8Constant;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    private final List<ClassConstant> interfaceClassElems;
    private final List<FieldHolder> fields;
    private final List<Consumer<ByteArrayOutputStream>> methodCodeGenerators;
    private short constantPoolIndex = 0;
    private ClassConstant thisClassElem;
    private ClassConstant superClassElem;
    private UTF8Constant sourceFileAttr;
    private UTF8Constant sourceFileUTF;


    public JVMClassBuilder(@Nonnull final String classPackage, @Nonnull final String className) {
        this.classPackage = classPackage.isEmpty() ? "foxlangscript" : classPackage;
        this.className = className;

        this.constantPoolItems = new ArrayList<>();
        this.interfaceClassElems = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.methodCodeGenerators = new ArrayList<>();
    }

    public void registerMethod(final Consumer<ByteArrayOutputStream> element) {
        this.methodCodeGenerators.add(element);
    }

    public void addConstantPoolItem(final ConstantPoolItem constantPoolItem) {
        this.constantPoolItems.add(constantPoolItem.setLocationInCP(++this.constantPoolIndex));
    }

    public void setThisClassElem(final ClassConstant thisClassElem) {
        this.thisClassElem = thisClassElem;
    }

    public void setSuperClassElem(final ClassConstant superClassElem) {
        this.superClassElem = superClassElem;
    }

    public void setSourceFileAttr(final UTF8Constant sourceFileAttr) {
        this.sourceFileAttr = sourceFileAttr;
    }

    public void setSourceFileUTF(final UTF8Constant sourceFileUTF) {
        this.sourceFileUTF = sourceFileUTF;
    }

    public byte[] generateByteCode() {
        ByteArrayOutputStream byteCode = new ByteArrayOutputStream();
        this.writeClassHeader(byteCode);
        this.writeConstantPool(byteCode);
        this.writeCode(byteCode);
        return byteCode.toByteArray();
    }

    public ArrayList<ConstantPoolItem> getConstantPoolItems() {
        return this.constantPoolItems;
    }

    private void writeCode(@Nonnull final ByteArrayOutputStream byteCode) {

        //access flags
        byteCode.write(0x00);
        byteCode.write(0x21); // 0x20 /*super*/ & 0x01 /*public*/

        //this class
        byteCode.write(this.thisClassElem.getLocationInCP() >> 8);
        byteCode.write(this.thisClassElem.getLocationInCP());

        //super class
        byteCode.write(this.superClassElem.getLocationInCP() >> 8);
        byteCode.write(this.superClassElem.getLocationInCP());

        //interface count
        byteCode.write(this.interfaceClassElems.size() >> 8);
        byteCode.write(this.interfaceClassElems.size());

        for (final ClassConstant interfaceClassElem : this.interfaceClassElems) {
            byteCode.write(interfaceClassElem.getLocationInCP() >> 8);
            byteCode.write(interfaceClassElem.getLocationInCP());
        }

        //field count
        byteCode.write(this.fields.size() >> 8);
        byteCode.write(this.fields.size());

        for (final FieldHolder field : this.fields) {
            // Field Access Flags
            byteCode.write(field.getAccessFlags() >> 8);
            byteCode.write(field.getAccessFlags());

            // Field Name Index
            byteCode.write(field.getName().getLocationInCP() >> 8);
            byteCode.write(field.getName().getLocationInCP());

            // Field Descriptor Index
            byteCode.write(field.getDescriptor().getLocationInCP() >> 8);
            byteCode.write(field.getDescriptor().getLocationInCP());

            // Field Attribute Count // TODO: Make this actually do stuff
            byteCode.write(0x00);
            byteCode.write(0x00);

            // TODO: Write the attribute data properly as needed (only constant value?)
        }

        byteCode.write(this.methodCodeGenerators.size() >> 8);
        byteCode.write(this.methodCodeGenerators.size());

        for (final Consumer<ByteArrayOutputStream> methodCodeGenerator : this.methodCodeGenerators) {
            methodCodeGenerator.accept(byteCode);
        }

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
        byteCode.write((this.constantPoolItems.size() + 1) >> 8);
        byteCode.write((this.constantPoolItems.size() + 1));

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

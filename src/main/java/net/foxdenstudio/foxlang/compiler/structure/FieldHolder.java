package net.foxdenstudio.foxlang.compiler.structure;

import net.foxdenstudio.foxlang.compiler.structure.constants.UTF8Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fox on 1/15/2018.
 * Project: FoxLang
 */
public class FieldHolder {
    private final List<Void> attributes;
    private short accessFlags;
    private UTF8Constant name;
    private UTF8Constant descriptor;


    public FieldHolder() {
        this.accessFlags = 0x0001; // Public
        this.attributes = new ArrayList<>();
    }

    public FieldHolder(short accessFlags, UTF8Constant name, UTF8Constant descriptor) {
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = new ArrayList<>();
    }

    public short getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(short accessFlags) {
        this.accessFlags = accessFlags;
    }

    public UTF8Constant getName() {
        return this.name;
    }

    public void setName(UTF8Constant name) {
        this.name = name;
    }

    public UTF8Constant getDescriptor() {
        return this.descriptor;
    }

    public void setDescriptor(UTF8Constant descriptor) {
        this.descriptor = descriptor;
    }

    public List<Void> getAttributes() {
        return this.attributes;
    }
}
